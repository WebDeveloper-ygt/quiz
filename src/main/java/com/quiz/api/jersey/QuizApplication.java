package com.quiz.api.jersey;

import io.swagger.jaxrs.config.BeanConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class QuizApplication extends Application {

	public QuizApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[]{"http"});
		beanConfig.setHost("localhost");
		beanConfig.setBasePath("/QuizApi/api");
		beanConfig.setResourcePackage("com.quiz.api.jersey");
		beanConfig.setScan(true);
	}

	@Override
	public Map<String, Object> getProperties() {
		 Map<String, Object> properties= new HashMap<>();
		 properties.put("jersey.config.server.provider.packages", "com.quiz.api.jersey");
		return properties;
	}

	@Override
	public Set<Class<?>> getClasses() {

		Set<Class<?>> resources = new HashSet<>();

		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		return resources;
	}
}
