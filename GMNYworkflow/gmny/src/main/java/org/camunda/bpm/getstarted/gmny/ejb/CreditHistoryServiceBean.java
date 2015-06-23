package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
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
		
		System.out.println("*** Persist credit history ***");
		
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
	    
	    System.out.println("Saving credit history (Id, scoring, date): " + creditHistory.getId() + ", " + variables.get("scoring") + ", " + today);
	    
	    // load customer entity
	    CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, variables.get("customerId"));
	 
	    // Set customer attributes
	    customerEntity.setCreditHistoryId(creditHistory.getId());;
	    entityManager.merge(customerEntity);
	    System.out.println("TEST TEST TEST:");
	    System.out.println("FIRSTNAME: " + entityManager.find(CustomerEntity.class, variables.get("customerId")).getFirstname());
	    System.out.println("CREDIT HISTORY ID: " + entityManager.find(CustomerEntity.class, variables.get("customerId")).getCreditHistoryId());
	    
	    System.out.println(" ");
	  }
	
	
	//not yet needed
	  public CreditHistoryEntity getCreditHistory(Long creditHistoryId) {
		    // Load creditHistory entity from database
		    return entityManager.find(CreditHistoryEntity.class, creditHistoryId);
		  }
	
	public void performRiskAssessment(DelegateExecution delegateExecution) {
		
		System.out.println("*** Performing risk assessment ***");
		
		// Get relevant variables from process memory
		Map<String, Object> variables = delegateExecution.getVariables();
		Long scoring = (Long) variables.get("scoring");
		Long badDepts = (Long) variables.get("badDepts");
		Long consumerCredits = (Long) variables.get("consumerCredits");
		
		// apply business rules
		boolean flag = false;
		flag = (scoring >= 8 && badDepts == 0 && consumerCredits <= 2) ? true : false;
		
		// convert to a clear recommendation
		String recommendation = "LOAN APPROVAL NOT RECOMMENDED";
		recommendation = (flag == true) ? "LOAN APPROVAL RECOMMENDED" : "LOAN APPROVAL NOT RECOMMENDED";
		
		//set the process variable
		delegateExecution.setVariable("recommendation", recommendation);
		
		System.out.println("Outcome - recommendation: " + recommendation);
		System.out.println("");
		
	  }

	public void loadCreditHistory(DelegateExecution delegateExecution) {
		
		System.out.println("*** Look for recent credit history ***");
		
		// Get customerId from process memory
	    Map<String, Object> variables = delegateExecution.getVariables(); 
	    Long customerId = (Long) variables.get("customerId");
	    Long creditHistoryId = (Long) entityManager.find(CustomerEntity.class, customerId).getCreditHistoryId();
	    
	    if (creditHistoryId != null) {
	    	System.out.println("credit history found: " + creditHistoryId);
	    	
	    	Date receptionDate = (Date) entityManager.find(CreditHistoryEntity.class, creditHistoryId).getReceptionDate();
	    	Date today = new Date();
	    	
	    	int diffInDays = (int)( (today.getTime() - receptionDate.getTime()) / (1000 * 60 * 60 * 24) );
	    	
	    	if(diffInDays > 20){
	    		System.out.println("Credit history is out of date. Date of reception: " + receptionDate + " -> age: " + diffInDays);
	    		delegateExecution.setVariable("creditHistoryAvailable", false);
	    	}
	    	else{
	    		System.out.println("Credit history is younger than 20 days. Date of reception: " + receptionDate + " -> age: " + diffInDays);
	    		delegateExecution.setVariable("scoring", entityManager.find(CreditHistoryEntity.class, creditHistoryId).getScoring());
	    	  	delegateExecution.setVariable("badDepts", entityManager.find(CreditHistoryEntity.class, creditHistoryId).getBadDepts());
	    	  	delegateExecution.setVariable("consumerCredits", entityManager.find(CreditHistoryEntity.class, creditHistoryId).getConsumerCredits());
	    		delegateExecution.setVariable("creditHistoryAvailable", true);
	    		System.out.println("Credit history loaded from database");
	    	}
	    }
	    else {
	    	System.out.println("No credit history available: " + creditHistoryId);
	    	delegateExecution.setVariable("creditHistoryAvailable", false);
	    }
	    System.out.println(" ");
	}
	
	public void requestCreditHistory(DelegateExecution delegateExecution) {
		
		System.out.println("*** Request credit history from GTA ***");
		
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
		    
		    System.out.println(message);
		    ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, message);
		    
		    // check response status code
	        if (response.getStatus() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	        }
	
	        // display response
	        //String output = response.getEntity(String.class);
	        //System.out.println(output + "\n");
	        
	        System.out.println(response);
	        System.out.println(" ");
	        
		} catch (Exception e) {
        e.printStackTrace();
        }
	}
	
}