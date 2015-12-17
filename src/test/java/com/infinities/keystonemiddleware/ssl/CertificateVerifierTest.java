package com.infinities.keystonemiddleware.ssl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.util.PropertiesHolder;

public class CertificateVerifierTest {

	private X509Certificate signercert;
	private X509Certificate cacert;


	@Before
	public void setUp() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		signercert = X509CertificateParser.parse(new File(PropertiesHolder.CONFIG_FOLDER + File.separator
				+ "signing_cert.pem"));
		cacert = X509CertificateParser.parse(new File(PropertiesHolder.CONFIG_FOLDER + File.separator + "cacert.pem"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateKeyChain() throws CertificateException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, NoSuchProviderException, CertificateVerificationException {
//		assertTrue(CertificateVerifier.validateKeyChain(signercert, cacert));

		Set<X509Certificate> additions = new HashSet<X509Certificate>();
		additions.add(cacert);
		CertificateVerifier.verifyCertificate(signercert, additions, true);
	}

	@Test
	public void testIsSelfSigned() throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
		assertFalse(CertificateVerifier.isSelfSigned(signercert));
		assertTrue(CertificateVerifier.isSelfSigned(cacert));
	}

}
