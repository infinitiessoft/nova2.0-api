/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.keystonemiddleware.common;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.DataFormatException;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.infinities.keystonemiddleware.ssl.Base64Verifier;
import com.infinities.keystonemiddleware.ssl.CertificateVerificationException;
import com.infinities.keystonemiddleware.ssl.CertificateVerifier;
import com.infinities.keystonemiddleware.ssl.X509CertificateParser;

public class Cms {

	public enum Algorithm {
		md5, sha1, sha256, sha512;
	}


	private final static Logger logger = LoggerFactory.getLogger(Cms.class);
	public final static String PKI_ASN1_PREFIX = "MII";
	public final static String PKIZ_PREFIX = "PKIZ";
	public final static String PKIZ_CMS_FORM = "DER";
	public final static String PKI_ASN1_FORM = "PEM";
	public final static String BEGIN_MARKER = "-----BEGIN CMS-----";
	public final static String END_MARKER = "-----END CMS-----";
	private final static ConcurrentHashMap<String, X509Certificate> certMap =
			new ConcurrentHashMap<String, X509Certificate>();


	public static String cmsVerify(String formatted, String signingCertFileName, String caFileName)
			throws CertificateException, OperatorCreationException, NoSuchAlgorithmException, NoSuchProviderException,
			CertPathBuilderException, InvalidAlgorithmParameterException, CMSException, IOException,
			CertificateVerificationException {
		return cmsVerify(formatted, signingCertFileName, caFileName, PKI_ASN1_FORM);
	}

	public static String cmsVerify(String formatted, String signingCertFileName, String caFileName, String inform)
			throws CertificateException, OperatorCreationException, NoSuchAlgorithmException, NoSuchProviderException,
			CertPathBuilderException, InvalidAlgorithmParameterException, CMSException, IOException,
			CertificateVerificationException {
		logger.debug("data verify: {}", formatted);
		formatted = formatted.replace(BEGIN_MARKER, "").replace(END_MARKER, "").trim();
		logger.debug("after formatted data: {}", formatted);
		byte[] data = encodingForForm(formatted, inform);

		return verifySignature(data, signingCertFileName, caFileName);
	}

	private static byte[] encodingForForm(String formatted, String inform) {
		if (PKI_ASN1_FORM.equals(inform)) {
			return formatted.getBytes(Charsets.UTF_8);
		} else if (PKIZ_CMS_FORM.equals(inform)) {
			return Hex.encode(formatted.getBytes());
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String verifySignature(byte[] sigbytes, String signingCertFileName, String caFileName)
			throws CMSException, CertificateException, OperatorCreationException, NoSuchAlgorithmException,
			NoSuchProviderException, CertPathBuilderException, InvalidAlgorithmParameterException, IOException,
			CertificateVerificationException {
		logger.debug("signingCertFile: {}, caFile:{}", new Object[] { signingCertFileName, caFileName });
		Security.addProvider(new BouncyCastleProvider());
		X509Certificate signercert = generateCertificate(signingCertFileName);
		X509Certificate cacert = generateCertificate(caFileName);
		Set<X509Certificate> additionalCerts = new HashSet<X509Certificate>();
		additionalCerts.add(cacert);

		CertificateVerifier.verifyCertificate(signercert, additionalCerts, true); // .validateKeyChain(signercert,
																					// certs);
		if (Base64Verifier.isBase64(sigbytes)) {
			try {
				sigbytes = Base64.decode(sigbytes);
				logger.debug("Signature file is BASE64 encoded");
			} catch (Exception ioe) {
				logger.warn("Problem decoding from b64", ioe);
			}
		}

		// --- Use Bouncy Castle provider to verify included-content CSM/PKCS#7
		// signature ---
		try {
			logger.debug("sigbytes size: {}", sigbytes.length);
			CMSSignedData s = new CMSSignedData(sigbytes);
			Store<?> store = s.getCertificates();
			SignerInformationStore signers = s.getSignerInfos();
			Collection c = signers.getSigners();
			Iterator it = c.iterator();
			int verified = 0;

			while (it.hasNext()) {
				X509Certificate cert = null;
				SignerInformation signer = (SignerInformation) it.next();
				Collection certCollection = store.getMatches(signer.getSID());
				if (certCollection.isEmpty() && signercert == null)
					continue;
				else if (signercert != null) // use a signer cert file for
												// verification, if it was
												// provided
					cert = signercert;
				else { // use the certificates included in the signature for
						// verification
					Iterator certIt = certCollection.iterator();
					cert = (X509Certificate) certIt.next();
				}

				if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(cert)))
					verified++;
			}

			if (verified == 0) {
				logger.warn(" No signers' signatures could be verified !");
			} else if (signercert != null)
				logger.info("Verified a signature using signer certificate file  {}", signingCertFileName);
			else
				logger.info("Verified a signature using a certificate in the signature data");

			CMSProcessableByteArray cpb = (CMSProcessableByteArray) s.getSignedContent();
			byte[] rawcontent = (byte[]) cpb.getContent();

			return new String(rawcontent);
		} catch (Exception ex) {
			logger.error("Couldn't verify included-content CMS signature", ex);
			throw new RuntimeException("Couldn't verify included-content CMS signature", ex);
		}
	}

	private static X509Certificate generateCertificate(String certFilePath) throws CertificateException, IOException {
		X509Certificate cert = certMap.get(certFilePath);
		if (cert != null) {
			return cert;
		} else {
			synchronized (certMap) {
				cert = certMap.get(certFilePath);
				if (cert != null) {
					return cert;
				}
				File f = new File(certFilePath);
				logger.debug("cert file {} exist? {}", new Object[] { certFilePath, f.exists() });
				cert = X509CertificateParser.parse(f);
				certMap.put(certFilePath, cert);
				return cert;
			}
		}
	}

	public static boolean isPkiz(String userToken) {
		return userToken.startsWith(PKIZ_PREFIX);
	}

	public static boolean isAsn1Token(String userToken) {
		return userToken.startsWith(PKI_ASN1_PREFIX);
	}

	public static String pkizUncompress(String signedText) throws DataFormatException {
		String text = signedText.substring(PKIZ_PREFIX.length());
		byte[] unencoded = BaseEncoding.base64Url().decode(text);
		byte[] uncompressedByte = CompressionUtils.decompress(unencoded);
		return new String(uncompressedByte);
	}

	public static String tokenToCms(String signedText) {
		String copyOfText = signedText.replace('-', '/');
		String formatted = "-----BEGIN CMS-----\n";
		int lineLength = 64;
		while (copyOfText.length() > 0) {
			if (copyOfText.length() > lineLength) {
				formatted += copyOfText.substring(0, lineLength);
				copyOfText = copyOfText.substring(lineLength);
			} else {
				formatted += copyOfText;
				copyOfText = "";
			}
			formatted += "\n";
		}
		formatted += "-----END CMS-----\n";

		return formatted;
	}

	public static String cmsHashToken(String tokenid, Algorithm mode) {
		if (mode == null) {
			mode = Algorithm.md5;
		}

		if (Strings.isNullOrEmpty(tokenid)) {
			throw new NullPointerException("invalid tokenid");
		}

		if (isAsn1Token(tokenid) || isPkiz(tokenid)) {
			HashFunction hf = Hashing.md5();
			if (mode == Algorithm.sha1) {
				hf = Hashing.sha1();
			} else if (mode == Algorithm.sha256) {
				hf = Hashing.sha256();
			} else if (mode == Algorithm.sha512) {
				hf = Hashing.sha512();
			}
			HashCode hc = hf.newHasher().putString(tokenid).hash();
			return toHex(hc.asBytes());

		} else {
			return tokenid;
		}
	}

	private static String toHex(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			sb.append(String.format("%1$02X", b));
		}

		return sb.toString();
	}

	// public static void sign(String data, String signingCertFileName, String
	// caFileName) throws CertificateException,
	// IOException, NoSuchAlgorithmException, NoSuchProviderException,
	// CMSException {
	// CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
	// logger.debug("signingCertFile: {}, caFile:{}", new Object[] {
	// signingCertFileName, caFileName });
	// Security.addProvider(new BouncyCastleProvider());
	// X509Certificate signercert = generateCertificate(signingCertFileName);
	// // X509Certificate cacert = generateCertificate(caFileName);
	//
	// PrivateKey key = X509CertificateParser.parseKey(new File(caFileName));
	//
	// gen.addSigner(key, signercert, CMSSignedDataGenerator.DIGEST_SHA1);
	//
	// CMSProcessable p = new CMSProcessableByteArray(data.getBytes());
	// CMSSignedData signed = gen.generate(p, "BC");
	// System.out.println(new String(Base64.encode(signed.getEncoded())));
	// }

}
