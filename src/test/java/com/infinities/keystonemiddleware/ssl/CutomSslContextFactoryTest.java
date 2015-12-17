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
package com.infinities.keystonemiddleware.ssl;

import java.io.File;

import org.candlepin.thumbslug.ssl.SslPemException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.util.PropertiesHolder;

public class CutomSslContextFactoryTest {

	private String cert = PropertiesHolder.CONFIG_FOLDER + File.separator + "signing_cert.pem";
	private String key = PropertiesHolder.CONFIG_FOLDER + File.separator + "ssl" + File.separator + "private"
			+ File.separator + "signing_key.pem";
	private String ca = PropertiesHolder.CONFIG_FOLDER + File.separator + "cacert.pem";


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetClientContextStringString() throws SslPemException {
		CutomSslContextFactory.getClientContext(cert, key);
	}

	@Test
	public void testGetClientContextStringStringString() throws SslPemException {
		CutomSslContextFactory.getClientContext(cert, key, ca);
	}

}
