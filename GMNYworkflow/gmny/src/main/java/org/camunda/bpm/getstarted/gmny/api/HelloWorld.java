package org.camunda.bpm.getstarted.gmny.api;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;

@Path("api")
public class HelloWorld {
	
	@PersistenceContext
	private EntityManager entityManager;

    @GET
    @Path("helloworld")
    public Response getHelloWorld() {

        String value = "Hello World - YEAH";
        return Response.status(200).entity(value).build();
    }
    
    @GET
    @Path("/getCustomer/{id}")
    @Produces("application/json")
    public CustomerEntity getCustomer(@PathParam("id") long customerId) {
    	
    	CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
    	
    	return customer;
    }
    
    /*
    
    //pw check could be implemented like this...
    @GET
    @Path("/checkPassword/{id}/{pw}")
    public Response checkPassword(@PathParam("id") long customerId, @PathParam("pw") String password) {
    	
    	
    	String value = "Hello World - YEAH";
    	CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
    	
    	value = "fail";
    	
    	if (customer.getPassword() == password){
    		value = "pass";
    	}
    	
    	return Response.status(200).entity(value).build();
    }
    */
    
}
