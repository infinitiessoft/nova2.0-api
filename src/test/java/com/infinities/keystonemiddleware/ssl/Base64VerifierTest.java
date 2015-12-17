package com.infinities.keystonemiddleware.ssl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.BaseEncoding;

public class Base64VerifierTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsBase64Byte() {
		byte a = 'a';
		byte b = '#';
		assertFalse(Base64Verifier.isBase64(b));
		assertTrue(Base64Verifier.isBase64(a));
	}

	@Test
	public void testIsBase64String() {
		String text = "test!@#$%^&*()_+~";
		assertFalse(Base64Verifier.isBase64(text));
		assertTrue(Base64Verifier.isBase64(BaseEncoding.base64().encode(text.getBytes())));
	}

	@Test
	public void testIsBase64ByteArray() {
		String text = "test!@#$%^&*()_+~";
		byte[] b = text.getBytes();
		assertFalse(Base64Verifier.isBase64(b));
		assertTrue(Base64Verifier.isBase64(BaseEncoding.base64().encode(b).getBytes()));
	}

}
