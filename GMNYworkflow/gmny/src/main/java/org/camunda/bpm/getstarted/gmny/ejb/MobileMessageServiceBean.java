package org.camunda.bpm.getstarted.gmny.ejb;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.getstarted.gmny.ejb.CustomerServiceBean;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
import org.camunda.bpm.getstarted.gmny.service.MobileMessageService;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;


@Stateless
@Named
public class MobileMessageServiceBean implements MobileMessageService{
		
	public void sendMessage(Long customerId, String message) {
			
			System.out.println("* Trying to send push notification to customer: " + customerId.toString() + ", message: " + message + " *");
			
			try {
	
				Client client = Client.create();
				
				System.out.println("http://localhost:3000/send_pushAds_api/sendPushAds?customerId=" + customerId.toString() + "&loanApplicationStatus="+ message);

			    WebResource webResource = client.resource("http://localhost:3000/send_pushAds_api/sendPushAds?customerId=" + customerId.toString() + "&loanApplicationStatus="+ message);

			    System.out.println(message);
			    ClientResponse response = webResource.type(MediaType.TEXT_PLAIN).get(ClientResponse.class);
			   	        
		        System.out.println(response);
		        
			} catch (Exception e) {
	        e.printStackTrace();
	        }
		}
	
	public void sendApplied(DelegateExecution delegateExecution) {
		
		try {
			
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = "applied";
		    Long customerId = (Long) variables.get("customerId");
		    
		    sendMessage(customerId, message);
		    
		} catch (Exception e) {
	    e.printStackTrace();
	    }
	}
	
	public void sendStatus(DelegateExecution delegateExecution) {
		
		try {
			
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = (String) variables.get("applicationStatus");
		    Long customerId = (Long) variables.get("customerId");
		    
		    sendMessage(customerId, message);
		    
		} catch (Exception e) {
	    e.printStackTrace();
	    }
	}
	
	public void sendResultStatus(DelegateExecution delegateExecution) {
		
		try {
			
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = (String) variables.get("applicationResultStatus");
		    Long customerId = (Long) variables.get("customerId");
		    
		    sendMessage(customerId, message);
		    
		} catch (Exception e) {
	    e.printStackTrace();
	    }
	}

}
