package org.camunda.bpm.getstarted.gmny.ejb;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Stateless
@Named
public class GtaSimulationServiceBean {
			
	public void respondeCreditHistory(DelegateExecution delegateExecution) {
		
		try {
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = "";
		    
		    if(variables.get("privateClient")== "true"){
		    	message = "{\"messageName\" : \"CreditHistoryResponse\","
			    		+"\"correlationKeys\" : {"
			    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
			    		+ "\"firstname\" : {\"value\" : \"" + variables.get("name") + "\", \"type\": \"String\"},"
			    		+ "\"lastname\" : {\"value\" : \"" + variables.get("surname") + "\", \"type\": \"String\"}"
			    		+"},"
			    		+"\"processVariables\" : {"
			    		+ "\"scoring\" : {\"value\" : \"" + variables.get("scoring") + "\", \"type\": \"Long\"},"
			    		+ "\"badDepts\" : {\"value\" : \"" + variables.get("badDepts") + "\", \"type\": \"Long\"},"
			    		+ "\"consumerCredits\" : {\"value\" : \"" + variables.get("consumerCredits") + "\", \"type\": \"Long\"}"
			    		+"} }";
		    }
		    else{
		    	message = "{\"messageName\" : \"CreditHistoryResponse\","
			    		+"\"correlationKeys\" : {"
			    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
			    		+ "\"firstname\" : {\"value\" : \"" + variables.get("name") + "\", \"type\": \"String\"},"
			    		+ "\"lastname\" : {\"value\" : \"" + variables.get("surname") + "\", \"type\": \"String\"}"
			    		+"},"
			    		+"\"processVariables\" : {"
			    		+ "\"rating\" : {\"value\" : \"" + variables.get("rating") + "\", \"type\": \"String\"},"
			    		+ "\"badDeptsInPastTwoYears\" : {\"value\" : \"" + variables.get("badDeptsInPastTwoYears") + "\", \"type\": \"Long\"},"
			    		+ "\"deptRatioWithNewCreditAmount\" : {\"value\" : \"" + variables.get("deptRatioWithNewCreditAmount") + "\", \"type\": \"Long\"}"
			    		+"} }";
		    }
		    		    
			Client client = Client.create();
		    WebResource webResource = client.resource("http://localhost:8080/engine-rest/message");
		    
		    System.out.println("***** GTA sends Credit History to GMNY*****");
			System.out.println(webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, message));
		    
		} catch (Exception e) {
        e.printStackTrace();
        }
	}
	
	public void respondeCleanup(DelegateExecution delegateExecution) {
		
		try {
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = "{\"messageName\" : \"CleanupResponse\","
		    		+"\"correlationKeys\" : {"
		    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
		    		+ "\"firstname\" : {\"value\" : \"" + variables.get("name") + "\", \"type\": \"String\"},"
		    		+ "\"lastname\" : {\"value\" : \"" + variables.get("surname") + "\", \"type\": \"String\"}"
		    		+"},"
		    		+"\"processVariables\" : {"
		    		+ "\"cleanupRecommendation\" : {\"value\" : \"" + variables.get("cleanupRecommendation") + "\", \"type\": \"String\"}"
		    		+"} }";
	        
			Client client = Client.create();
		    WebResource webResource = client.resource("http://localhost:8080/engine-rest/message");
		    
		    System.out.println("***** GTA sends Cleanup Recommendation to GMNY*****");
		    System.out.println(webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, message));
		    
		} catch (Exception e) {
        e.printStackTrace();
        }
	}

}
