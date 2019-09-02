package com.quiz.api.jersey.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.security.AuthenticationController;

public class HateoasUtils {
	 
	private static final Logger LOG = Logger.getLogger(HateoasUtils.class);
	private HateoasUtils() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	static private Links link;
	static private List<Links> exceptionLink;
	static private CustomException exception;
	public static Links getDetailsById(UriInfo uriInfo,int id, String message) {
		System.out.println(id);
        System.out.println(uriInfo.getAbsolutePath());
		link = new Links();
		try{
			String idLink =uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build().toString();
            System.out.println(idLink);
			link.setLink(idLink);
		}catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		link.setRef(message);
        System.out.println(link);
		return link;
	}

	public static Links getSelfDetails(UriInfo uriInfo) {
		link = new Links();
		try {
		String userLink = uriInfo.getAbsolutePathBuilder().build().toString();
		link.setLink(userLink);
		}catch (Exception e) {
			LOG.error(e.getMessage());
		}
		link.setRef("self");
		return link;
	}

	public static Response unAuthorizedException(UriInfo uriInfo) {
		System.out.println(uriInfo.getBaseUriBuilder());
		UriBuilder path = uriInfo.getBaseUriBuilder().path(AuthenticationController.class);
		link = new Links();
		link.setLink(path.toString());
		link.setRef("issueToken");
		
		exceptionLink= new ArrayList<>();
		exceptionLink.add(link);
		exception=new CustomException("Unauthorized - You dont have permission", 401,
				"Your token is either invalid or expired ", exceptionLink);
		return Response.status(Status.UNAUTHORIZED).entity(exception).build();
	}

	public static Response userNotFound(UriInfo uriInfo) {
		exceptionLink= new ArrayList<>();
		exceptionLink.add(HateoasUtils.getSelfDetails(uriInfo));
		exception=new CustomException("Unauthorized - User not found", 404,
				"User for the given credentials is not found ", exceptionLink);
		return Response.status(Status.NOT_FOUND).entity(exception).build();
	}
}
