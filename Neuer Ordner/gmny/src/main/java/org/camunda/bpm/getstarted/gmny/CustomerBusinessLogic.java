package org.camunda.bpm.getstarted.gmny;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.IOException;
import java.util.Map;
 
@Stateless
@Named
public class CustomerBusinessLogic {
 
  // Inject the entity manager
  @PersistenceContext
  private EntityManager entityManager;
  
  // Inject task form available through the camunda cdi artifact
  @Inject
  private TaskForm taskForm;
 
  public void persistCustomer(DelegateExecution delegateExecution) {
    // Create new customer instance
    CustomerEntity customerEntity = new CustomerEntity();
 
    // Get all process variables
    Map<String, Object> variables = delegateExecution.getVariables();
 
    // Set customer attributes
    customerEntity.setFirstname((String) variables.get("firstname"));
    customerEntity.setLastname((String) variables.get("lastname"));
    System.out.println("Saving customer " + variables.get("firstname") + " " + variables.get("lastname"));
 
    /*
      Persist customer instance and flush. After the flush the
      id of the customer instance is set.
    */
    entityManager.persist(customerEntity);
    entityManager.flush();
 
    // Remove no longer needed process variables
    delegateExecution.removeVariables(variables.keySet());
 
    // Add newly created customer id as process variable
    delegateExecution.setVariable("customerId", customerEntity.getId());
    System.out.println("Customer saved with ID: " + customerEntity.getId());
    System.out.println("Data in database: " + entityManager.find(CustomerEntity.class, customerEntity.getId()));
    System.out.println("Data in database: firstname:" + entityManager.find(CustomerEntity.class, customerEntity.getId()).getFirstname());
  }
  
  public CustomerEntity getCustomer(Long customerId) {
	    // Load customer entity from database
	  	System.out.println("Customer found: " + entityManager.find(CustomerEntity.class, customerId));
	    return entityManager.find(CustomerEntity.class, customerId);
	  }
	 
	  /*
	    Merge updated customer entity and complete task form in one transaction. This ensures
	    that both changes will rollback if an error occurs during transaction.
	   */
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
 
}