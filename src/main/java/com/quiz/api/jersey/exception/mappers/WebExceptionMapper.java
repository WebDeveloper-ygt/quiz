package com.quiz.api.jersey.exception.mappers;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.utils.HateoasUtils;
import com.quiz.api.jersey.utils.Links;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Context
    UriInfo uriInfo;
    CustomException customException;
    List<Links> links;

    @Override
    public Response toResponse(WebApplicationException exception) {
        customException = new CustomException();
        links = new ArrayList<>();
        Response response = exception.getResponse();

        if (response.getStatus() == 405)
        {
            customException.setStatusCode(response.getStatus());
            customException.setDescription("Requested Method is not allowed for this resource");
            customException.setMessage(exception.getMessage());
            links.add(HateoasUtils.getSelfDetails());
            customException.setLinks(links);
        }
        return  Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(customException).build();
    }
}
