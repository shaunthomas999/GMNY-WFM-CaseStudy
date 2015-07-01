package org.camunda.bpm.getstarted.gmny.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.Application;

import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;



@Path("/customer")
public class WebserviceCustomer extends Application {
	
	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {
 
		String result = "Restful example : " + msg;
 
		return Response.status(200).entity(result).build();
 
	}
	
	
	/*
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerEntity test() {
 
		CustomerEntity customer = new CustomerEntity();
		customer.setFirstname("API");
 
		return customer;
 
	}

	
	 * 
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(CustomerEntity customer) {

		
		
		String answer = "super";

		return Response.status(201).entity(answer).build();

	}

	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();
		
	}
	*/
	
}
