package com.infinities.nova.util.jackson;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class JacksonProvider extends com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider {

	public JacksonProvider() {
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		// if using BOTH JAXB annotations AND Jackson annotations:
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();

		ObjectMapper mapper =
				new ObjectMapper().registerModule(new Hibernate4Module())
						.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
						.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"))
						.enable(SerializationFeature.INDENT_OUTPUT)
						.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
						.setAnnotationIntrospector(new AnnotationIntrospectorPair(introspector, secondary));
		// mapper = mapper.setSerializationInclusion(Include)
		setMapper(mapper);
	}
}
