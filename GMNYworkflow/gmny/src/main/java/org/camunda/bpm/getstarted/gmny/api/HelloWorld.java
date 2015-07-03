package org.camunda.bpm.getstarted.gmny.api;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private final static Logger LOGGER = Logger.getLogger(HelloWorld.class.getName());
	
	@PersistenceContext
	private EntityManager entityManager;

    @GET
    @Path("helloworld")
    public Response getHelloWorld() {
    	LOGGER.log(Level.INFO, "\n\n** /helloworld - REST testing\n");
        
    	String value = "Hello World - YEAH";
        return Response.status(200).entity(value).build();
    }
    
    @GET
    @Path("getCustomer/{id}")
    @Produces("application/json")
    public CustomerEntity getCustomer(@PathParam("id") long customerId) {
    	LOGGER.log(Level.INFO, "\n\n** getCustomer REST end point\n");
    	
    	CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
    	
    	return customer;
    }
    
    //pw check could be implemented like this...
    @GET
    @Path("checkPassword/{id}/{pw}")
    public Response checkPassword(@PathParam("id") long customerId, @PathParam("pw") String password) {
    	LOGGER.log(Level.INFO, "\n\n** checkPassword REST end point\n");
    	
    	CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
    	LOGGER.log(Level.INFO, "CustomerEntity : " + customer);
    	LOGGER.log(Level.INFO, "Qry pw : " + password);
    	LOGGER.log(Level.INFO, "Entity pw : " + customer.getPassword());
    	
    	String value = "fail";
    	if (customer != null && password.equals(customer.getPassword())){
    		value = "pass";
    	}
    	
    	return Response.status(200).entity(value).build();
    }
    
}