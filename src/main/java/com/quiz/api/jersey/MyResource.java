package com.quiz.api.jersey;


import com.quiz.api.jersey.model.UserBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("test")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        UserBean userBean = new UserBean();
        List<UserBean> userBeanList = new ArrayList<>();
        userBean.setEmailId("user@domain.com");
        userBean.setFirstName("user");
        userBeanList.add(userBean);
        return Response.status(Response.Status.OK).entity(new GenericEntity<List<UserBean>>(userBeanList){}).build();
    }
}
