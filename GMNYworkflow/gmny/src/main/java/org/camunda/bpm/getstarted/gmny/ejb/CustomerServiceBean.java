package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
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
public class CustomerServiceBean implements CustomerService{
 
  // Inject the entity manager
  @PersistenceContext
  private EntityManager entityManager;
  
  // Inject task form available through the camunda cdi artifact
  //@Inject
  //private TaskForm taskForm;
 
  public void persistCustomer(DelegateExecution delegateExecution) {
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
  
  public CustomerEntity getCustomer(Long customerId) {
    // Load customer entity from database
  	System.out.println("Customer found: " + entityManager.find(CustomerEntity.class, customerId));
    return entityManager.find(CustomerEntity.class, customerId);
  }
  
  public void loadCustomer(DelegateExecution delegateExecution) {
  	// Get customerId from process memory
    Map<String, Object> variables = delegateExecution.getVariables();
    Long customerId = (Long) variables.get("customerId");
  
    // Load customer entity from database and save it in process memory
  	System.out.println("Loading customer into process memory: " + entityManager.find(CustomerEntity.class, customerId));
  	delegateExecution.setVariable("firstname", entityManager.find(CustomerEntity.class, customerId).getFirstname());
  	delegateExecution.setVariable("lastname", entityManager.find(CustomerEntity.class, customerId).getLastname());
  	delegateExecution.setVariable("email", entityManager.find(CustomerEntity.class, customerId).getEmail());
  	delegateExecution.setVariable("phoneNumber", entityManager.find(CustomerEntity.class, customerId).getPhoneNumber());
  	delegateExecution.setVariable("registrationDate", entityManager.find(CustomerEntity.class, customerId).getRegistrationDate());
  }
  
  public void sendEmailToCustomer(DelegateExecution delegateExecution) {
  	// Get customerId from process memory
    Map<String, Object> variables = delegateExecution.getVariables();
    Long customerId = (Long) variables.get("customerId");
    
    System.out.println(
		MailServiceBean.send(
				entityManager.find(CustomerEntity.class, customerId).getEmail(),
				"Willkommen bei GMNY",
				"Hey " + entityManager.find(CustomerEntity.class, customerId).getFirstname() + ", your password is: " + entityManager.find(CustomerEntity.class, customerId).getPassword()
	));
    
    
    /*
    HashMap<String, String> vars = new HashMap<String, String>();
	vars.put("greeting", "Dear " + entityManager.find(CustomerEntity.class, customerId).getFirstname() + "!");
	vars.put("text", "Wir freuen uns, Sie als Kunden begr&#252;&#223;en zu d&#252;rfen. Erkunden Sie doch gerade unser Onlinebankingportal! Ihre Zugangsdaten lauten: <br> Kundennummer: " + entityManager.find(CustomerEntity.class, customerId).getId() + "<br> Password: " + entityManager.find(CustomerEntity.class, customerId).getPassword());
	vars.put("buttonTitle", "Go to Online-Banking!");
	vars.put("buttonLink", "http://portal.crowdstrom.de/#/aktivieren/");
	try {
		System.out.println(
			MailServiceBean.send(
					entityManager.find(CustomerEntity.class, customerId).getFirstname() + " " + entityManager.find(CustomerEntity.class, customerId).getLastname() +" <" + entityManager.find(CustomerEntity.class, customerId).getEmail() + ">",
				"Willkommen bei GMNY",
				vars
			)
		);
	} catch (IOException e) {
		// Error when Sending the welcome mail
		e.printStackTrace();
	}
  */
  
  }
	 
	  /*
	    Merge updated customer entity and complete task form in one transaction. This ensures
	    that both changes will rollback if an error occurs during transaction.
	   */
  /*
	  public void mergeCustomerAndCompleteTask(CustomerEntity customerEntity) {
	    // Merge detached customer entity with current persisted state
	    entityManager.merge(customerEntity);
	    
	    try {
	      // Complete user task from
	      taskForm.completeTask();
	    } catch (IOException e) {
	      // Rollback both transactions on error
	      throw new RuntimeException("Cannot complete task", e);
	    }
	  }
 */
}