package com.quiz.api.jersey.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		// TODO Auto-generated method stub

		responseContext.getHeaders().add("X-Powered-By", "Jersey-Api");
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
	}

}
