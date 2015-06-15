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
		    
		    String message = "{\"messageName\" : \"CreditHistoryResponse\","
		    		+"\"correlationKeys\" : {"
		    		+ "\"firstname\" : {\"value\" : \"" + variables.get("firstname") + "\", \"type\": \"String\"},"
		    		+ "\"lastname\" : {\"value\" : \"" + variables.get("lastname") + "\", \"type\": \"String\"}"
		    		+"},"
		    		+"\"processVariables\" : {"
		    		+ "\"rating\" : {\"value\" : \"" + variables.get("rating") + "\", \"type\": \"String\"}"
		    		+"} }";
	        
			Client client = Client.create();
		    WebResource webResource = client.resource("http://localhost:8080/engine-rest/message");
		    
		    System.out.println("***** GTA answers to GMNY*****");
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
