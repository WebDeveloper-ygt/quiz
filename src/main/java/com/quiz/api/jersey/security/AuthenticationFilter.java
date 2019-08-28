package com.quiz.api.jersey.security;

import java.nio.charset.StandardCharsets;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.utils.ConstantUtils;
import com.quiz.api.jersey.utils.HateoasUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Provider
@Authenticate
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	private final Logger LOG = Logger.getLogger(AuthenticationFilter.class);

	@Context
	UriInfo uriInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String jwtToken = authHeader.substring("Bearer ".length()).trim();

		try {

			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(ConstantUtils.SECRET.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(jwtToken);
			LOG.info("#### valid token : " + jwtToken);
			LOG.info("#### valid token Details : " + parseClaimsJws.getBody().get("name"));
		} catch (Exception e) {
			// e.printStackTrace();
			requestContext.abortWith(HateoasUtils.unAuthorizedException(uriInfo));
		}

	}

}
