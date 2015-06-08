package org.camunda.bpm.getstarted.gmny;

import org.camunda.bpm.engine.cdi.BusinessProcess;
 


import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



//import java.io.IOException;
import java.io.Serializable;
 
@Named
@ConversationScoped
public class ApproveCustomersController implements Serializable {
 
  private static final long serialVersionUID = 1L;
 
  // Inject the BusinessProcess to access the process variables
  @Inject
  private BusinessProcess businessProcess;
 
  // Inject the EntityManager to access the persisted customer
  @PersistenceContext
  private EntityManager entityManager;
 
  // Inject the CustomerBusinessLogic to update the persisted customer
  @Inject
  private CustomerBusinessLogic customerBusinessLogic;
 
  // Caches the CustomerEntity during the conversation
  private CustomerEntity customerEntity;
 
  public CustomerEntity getCustomerEntity() {
	System.out.println("getting customer " + businessProcess.getVariable("customerId"));
    if (customerEntity == null) {
      // Load the customer entity from the database if not already cached
      customerEntity = customerBusinessLogic.getCustomer((Long) businessProcess.getVariable("customerId"));
    }
    return customerEntity;
  }
 
  /*
  public void submitForm() throws IOException {
    // Persist updated customer entity and complete task form
    customerBusinessLogic.mergeCustomerAndCompleteTask(customerEntity);
  }
  */
}