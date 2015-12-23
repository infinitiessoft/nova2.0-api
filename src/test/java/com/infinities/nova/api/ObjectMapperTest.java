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
package com.infinities.nova.api;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.infinities.nova.response.model.ServerAction.Pause;

public class ObjectMapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws JsonProcessingException, IOException {
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		// if using BOTH JAXB annotations AND Jackson annotations:
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")).enable(SerializationFeature.INDENT_OUTPUT)
				.setAnnotationIntrospector(new AnnotationIntrospectorPair(introspector, secondary));
		String text = "{\"pause\":null}";
		JsonNode node = mapper.readTree(text);
		mapper.treeToValue(node.get("pause"), Pause.class);
	}
}
