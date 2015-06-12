package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
import org.camunda.bpm.getstarted.gmny.service.CreditHistoryService;
import org.camunda.bpm.getstarted.gmny.service.CustomerService;

import javax.ejb.Stateless;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	    // Create new customer instance
	    CustomerEntity customerEntity = new CustomerEntity();
	 
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    // Set customer attributes from form
	    customerEntity.setFirstname((String) variables.get("firstname"));
	    customerEntity.setLastname((String) variables.get("lastname"));
	    customerEntity.setEmail((String) variables.get("email"));
	    customerEntity.setPhoneNumber((String) variables.get("phoneNumber"));
	    customerEntity.setStreet((String) variables.get("street"));
	    customerEntity.setStreetNumber((String) variables.get("streetNumber"));
	    customerEntity.setZipCode((String) variables.get("zipCode"));
	    customerEntity.setCity((String) variables.get("city"));
	    
	    System.out.println("Saving customer: " + variables.get("firstname") + ", " + variables.get("lastname") + ", " + variables.get("email"));
	    
	    // generate password
	    System.out.println("Generating random password");
	    customerEntity.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())));
	    
	    // set creation date
	    Date today = new Date();
	    customerEntity.setRegistrationDate(today);
	    
	    /*
	      Persist customer instance and flush. After the flush the
	      id of the customer instance is set.
	    */
	    entityManager.persist(customerEntity);
	    entityManager.flush();
	 
	    // Add newly created customer id as process variable
	    delegateExecution.setVariable("customerId", customerEntity.getId());
	    System.out.println("Customer saved with ID: " + customerEntity.getId());
	    
	    System.out.println("Data in database: firstname:" + entityManager.find(CustomerEntity.class, customerEntity.getId()).getFirstname());
	    
	  }

	public void loadCreditHistory(DelegateExecution delegateExecution) {
		// TODO Auto-generated method stub
		
	}

}
