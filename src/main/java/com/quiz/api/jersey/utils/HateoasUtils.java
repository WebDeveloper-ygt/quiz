package com.quiz.api.jersey.utils;

import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

public class HateoasUtils {
	 
	static Logger LOG = Logger.getLogger(HateoasUtils.class);
	public HateoasUtils() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	static private Links link;
	public static Links getDetailsById(UriInfo uriInfo,int id, String message) {
		//System.out.println(id);
		link = new Links();	
		try{
			String idLink =uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build().toString();
			link.setLink(idLink);
		}catch (Exception e) {
			LOG.error(e.getMessage());

		}
				
		link.setRef(message);
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

	
}
