package net.oauth.signature.pem;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.infinities.skyport.util.PropertiesHolder;

public class PEMReaderTest {

	private PEMReader reader;


	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPEMReaderString() throws IOException {
		String ca = PropertiesHolder.CONFIG_FOLDER + File.separator + "cacert.pem";
		// URL url =
		// Thread.currentThread().getContextClassLoader().getResource("conf/ssl/certs/ca.pem");
		reader = new PEMReader(ca);
		String readerText = reader.getText();

		String caPem = Files.asCharSource(new File(ca), Charsets.UTF_8).read();
		assertEquals(caPem, readerText);
	}
}
