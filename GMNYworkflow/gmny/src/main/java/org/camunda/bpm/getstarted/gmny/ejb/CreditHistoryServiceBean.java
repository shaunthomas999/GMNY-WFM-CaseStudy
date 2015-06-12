package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;
import org.camunda.bpm.getstarted.gmny.service.CreditHistoryService;

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
	    // Create new credit history instance
		CreditHistoryEntity creditHistory = new CreditHistoryEntity();
	 
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    // Set credit history attributes from form
	    creditHistory.setCustomerId((Long) variables.get("customerId"));
	    creditHistory.setRating((String) variables.get("rating"));
	    creditHistory.setRequestId((Long) variables.get("requestId"));
	      	    
	    // set creation date
	    Date today = new Date();
	    creditHistory.setReceptionDate(today);
	    
	    /*
	      Persist credit history instance and flush. After the flush the
	      id of the credit history instance is set.
	    */
	    entityManager.persist(creditHistory);
	    entityManager.flush();
	 
	    System.out.println("Saving credit history (Id, Rating): " + creditHistory.getId() + ", " + variables.get("rating"));
	    
	  }

	public void loadCreditHistory(DelegateExecution delegateExecution) {
		// TODO Auto-generated method stub
		
	}
}
