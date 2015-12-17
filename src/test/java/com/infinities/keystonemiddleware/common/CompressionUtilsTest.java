package com.infinities.keystonemiddleware.common;

import static org.junit.Assert.*;

import java.util.zip.DataFormatException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompressionUtilsTest {

	private String token = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12345678910";


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompress() {
		byte[] ret = CompressionUtils.compress(token.getBytes());
		assertTrue(ret.length > token.getBytes().length);
	}

	@Test
	public void testDecompress() throws DataFormatException {
		byte[] ret = CompressionUtils.compress(token.getBytes());
		byte[] ret2 = CompressionUtils.decompress(ret);
		String str = new String(ret2);
		assertEquals(token, str);
	}

}
