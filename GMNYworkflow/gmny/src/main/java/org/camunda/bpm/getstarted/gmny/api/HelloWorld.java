package org.camunda.bpm.getstarted.gmny.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("")
public class HelloWorld {

    @GET
    @Path("/helloworld")
    public Response getHelloWorld() {

        String value = "Hello World";

        return Response.status(200).entity(value).build();
    }
}
