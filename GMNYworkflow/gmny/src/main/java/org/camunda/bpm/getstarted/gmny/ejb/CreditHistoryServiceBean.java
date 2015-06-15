package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;
import org.camunda.bpm.getstarted.gmny.service.CreditHistoryService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ejb.Stateless;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Stateless
@Named
public class CreditHistoryServiceBean implements CreditHistoryService{
	
	// Inject the entity manager
	@PersistenceContext
	private EntityManager entityManager;
	
	public void persistCreditHistory(DelegateExecution delegateExecution) {
	    // Create new credit history instance
		CreditHistoryEntity creditHistory = new CreditHistoryEntity();
	 
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    // Set credit history attributes from form
	    creditHistory.setCustomerId((Long) variables.get("customerId"));
	    creditHistory.setRequestId((String) variables.get("requestId"));
	    
	    creditHistory.setScoring((Long) variables.get("scoring"));
	    creditHistory.setBadDepts((Long) variables.get("badDepts"));
	    creditHistory.setConsumerCredits((Long) variables.get("consumerCredits"));

	      	    
	    // set creation date
	    Date today = new Date();
	    creditHistory.setReceptionDate(today);
	    
	    /*
	      Persist credit history instance and flush. After the flush the
	      id of the credit history instance is set.
	    */
	    entityManager.persist(creditHistory);
	    entityManager.flush();
	 
	    System.out.println("Saving credit history (Id, Scoring): " + creditHistory.getId() + ", " + variables.get("scoring"));
	    
	  }

	public void loadCreditHistory(DelegateExecution delegateExecution) {
		 // Get all process variables
		//placeholder
	    delegateExecution.setVariable("creditHistoryAvailable", false);
		
	}
	
	public void requestCreditHistory(DelegateExecution delegateExecution) {
		
		try {
			//Create and safe Id
		    delegateExecution.setVariable("requestId", delegateExecution.getId());
		    
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = "{\"variables\": {"
		    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
		    		+ "\"firstname\" : {\"value\" : \"" + variables.get("firstname") + "\", \"type\": \"String\"},"
		    		+ "\"lastname\" : {\"value\" : \"" + variables.get("lastname") + "\", \"type\": \"String\"}"
		    		+ "} }";
		    
			Client client = Client.create();
		    WebResource webResource = client.resource("http://localhost:8080/engine-rest/process-definition/key/APIsimulation/start");
		    
		    System.out.println("***** Credit History Request to GTA *****");
		    System.out.println(message);
		    ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, message);
		    
		    // check response status code
	        if (response.getStatus() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + response.getStatus());
	        }
	
	        // display response
	        String output = response.getEntity(String.class);
	        System.out.println("Output from Server .... ");
	        System.out.println(output + "\n");
		} catch (Exception e) {
        e.printStackTrace();
        }
	}
	
}
