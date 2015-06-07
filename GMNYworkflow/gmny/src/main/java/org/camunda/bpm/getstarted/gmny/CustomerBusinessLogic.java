package org.camunda.bpm.getstarted.gmny;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.ejb.Stateless;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//import java.io.IOException;
import java.util.Map;
 
@Stateless
@Named
public class CustomerBusinessLogic {
 
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
 
    // Set customer attributes
    customerEntity.setFirstname((String) variables.get("firstname"));
    customerEntity.setLastname((String) variables.get("lastname"));
    customerEntity.setEmail((String) variables.get("email"));
    System.out.println("Saving customer: " + variables.get("firstname") + ", " + variables.get("lastname") + ", " + variables.get("email"));
    
    System.out.println("Generating random password");
    customerEntity.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())));
    
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
  }
  
  public void sendEmailToCustomer(DelegateExecution delegateExecution) {
  	// Get customerId from process memory
    Map<String, Object> variables = delegateExecution.getVariables();
    Long customerId = (Long) variables.get("customerId");
    
    // placeholder
    System.out.println("Sending email to: " + entityManager.find(CustomerEntity.class, customerId).getEmail() + "--- Hey " + entityManager.find(CustomerEntity.class, customerId).getFirstname() + ", your password is: " + entityManager.find(CustomerEntity.class, customerId).getPassword());
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