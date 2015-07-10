package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
import org.camunda.bpm.getstarted.gmny.model.FinancialProductEntity;
import org.camunda.bpm.getstarted.gmny.model.LoanEntity;
import org.camunda.bpm.getstarted.gmny.service.CreditHistoryService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Stateless
@Named
public class CreditHistoryServiceBean implements CreditHistoryService{
	
	// Inject the entity manager
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	FinancialProductServiceBean financialProductServiceBean;
	
	public void finalizeContract(DelegateExecution delegateExecution) {
		
		Map<String, Object> variables = delegateExecution.getVariables();
		
		Long amount = (long) variables.get("amount");
		double interestRate = Double.parseDouble((String) variables.get("interestRate"));
		Long period = (long) variables.get("period");
		
		Long customerId = (long) variables.get("customerId");
		
		if (variables.get("customerType") == "private") {
		
		Long productId = (Long) variables.get("privateLoanType");
		
		
		FinancialProductEntity product = financialProductServiceBean.getFinancialProduct(productId);
		
		CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
		
	    LoanEntity loan = new LoanEntity();
	    loan.setAmount(amount);
	    loan.setPeriod(period);
	    loan.setInterestRate(interestRate);
	    loan.setCustomerEntity(customer);
	    loan.setFinancialProduct(product);
	    
	    entityManager.persist(loan);
	    entityManager.flush();
	    
	    PdfServiceBean.createPrivateLoanContract(customer, loan);
	    
		} else {
			Long productId = (Long) variables.get("businessLoanType");
			
			
			FinancialProductEntity product = financialProductServiceBean.getFinancialProduct(productId);
			
			CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
			
		    LoanEntity loan = new LoanEntity();
		    loan.setAmount(amount);
		    loan.setPeriod(period);
		    loan.setInterestRate(interestRate);
		    loan.setCustomerEntity(customer);
		    loan.setFinancialProduct(product);
		    
		    entityManager.persist(loan);
		    entityManager.flush();
		    
		    PdfServiceBean.createBusinessLoanContract(customer, loan);
			
		}
		
	}
	
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
	    
	    creditHistory.setRating((String) variables.get("rating"));
	    creditHistory.setBadDeptsInPastTwoYears((Long) variables.get("badDeptsInPastTwoYears"));
	    creditHistory.setDeptRatioWithNewCreditAmount((Long) variables.get("deptRatioWithNewCreditAmount"));
	    
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
	    
	    System.out.println("Credit History ID: " + entityManager.find(CustomerEntity.class, variables.get("customerId")).getCreditHistoryId());
	    
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
		
		if (variables.get("customerType") == "private") {
		
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
			
			//get the chosen financial product
			Long productId = (Long) variables.get("privateLoanType");
			FinancialProductEntity product = financialProductServiceBean.getFinancialProduct(productId);
			
			double interestSpread = product.getMaxInterestRate() - product.getMinInterestRate();
			double interestStep = interestSpread / 8.0;
			
			
			double individualInterestRate = product.getMaxInterestRate();
			String scoringDirty = variables.get("scoring").toString();
			int scoringInt = Integer.parseInt(scoringDirty);
			// int scoringInt = (int) variables.get("scoring");
			
			switch (scoringInt) {
				case 15: individualInterestRate = product.getMinInterestRate(); break;
				case 14: individualInterestRate = product.getMinInterestRate() + interestStep; break;
				case 13: individualInterestRate = product.getMinInterestRate() + (2.0 * interestStep); break;
				case 12: individualInterestRate = product.getMinInterestRate() + (3.0 * interestStep); break;
				case 11: individualInterestRate = product.getMinInterestRate() + (4.0 * interestStep); break;
				case 10: individualInterestRate = product.getMinInterestRate() + (5.0 * interestStep); break;
				case 9: individualInterestRate = product.getMinInterestRate() + (6.0 * interestStep); break;
				case 8: individualInterestRate = product.getMinInterestRate() + (7.0 * interestStep); break;
				default: individualInterestRate = product.getMaxInterestRate(); break;
			}
			
			String problems = "";
			
			BigDecimal intr = new BigDecimal(individualInterestRate * 100.0);
			intr = intr.setScale( 2, BigDecimal.ROUND_HALF_UP );

			delegateExecution.setVariable("interestRate", "" + intr);
			
			Long amount = (Long) variables.get("amount");
			Long period = (Long) variables.get("period");
			
			Long individualAmount;
			Long individualPeriod;
			
			
			
			if (amount <= product.getMaxAmount() && amount >= product.getMinAmount()) {
				individualAmount = amount;
				delegateExecution.setVariable("amount", individualAmount);
			} else {
				individualAmount = amount;
				delegateExecution.setVariable("amount", individualAmount);
				problems = problems + "WARNING: The amount of EUR " + amount + " is out of range for the " + product.getProductName() + ". ";
			}
			
			if (period <= product.getMaxPeriod() && period >= product.getMinPeriod()) {
				individualPeriod = period;
				delegateExecution.setVariable("period", individualPeriod);
			} else {
				individualPeriod = period;
				delegateExecution.setVariable("period", individualPeriod);
				problems = problems + "WARNING: The period of " + period + " years is out of range for the " + product.getProductName() + ". ";
			}
		
			delegateExecution.setVariable("problems", problems);
			delegateExecution.setVariable("privateLoanTypeText", product.getProductName());
			
			// --------
			// business
			// --------

		} else {
			
			System.out.println("*** BBBBBB 1 ***");
			
			String rating = (String) variables.get("rating");
			Long badDeptsInPastTwoYears = (Long) variables.get("badDeptsInPastTwoYears");
			String dirtyDeptRatioWithNewCreditAmount = "0.9";
			//String dirtyDeptRatioWithNewCreditAmount = (String) variables.get("deptRatioWithNewCreditAmount");
			double deptRatioWithNewCreditAmount = Double.parseDouble(dirtyDeptRatioWithNewCreditAmount);
			
			System.out.println("*** BBBBBB 2 ***");
			
			// apply business rules
			boolean flag = false;
			
			switch (rating) {
			case "AAA": flag = true; break;
			case "AA": flag = true; break;
			case "A": flag = true; break;
			case "BBB": flag = true; break;
			case "BB": flag = true; break;
			default: flag = false; break;
			}
			
			System.out.println("*** BBBBBB 3 ***");
			
			flag = (flag == true && badDeptsInPastTwoYears == 0 && deptRatioWithNewCreditAmount <= 0.9) ? true : false;
			
			System.out.println("*** BBBBBB 4 ***");
			
			// convert to a clear recommendation
			String recommendation = "LOAN APPROVAL NOT RECOMMENDED (BUSINESS RECOMMENDATION)";
			recommendation = (flag == true) ? "LOAN APPROVAL RECOMMENDED (BUSINESS RECOMMENDATION)" : "LOAN APPROVAL NOT RECOMMENDED (BUSINESS RECOMMENDATION)";
			
			System.out.println("*** BBBBBB 5 ***");
			
			//set the process variable
			delegateExecution.setVariable("recommendation", recommendation);
			
			System.out.println("Outcome - recommendation: " + recommendation);
			
			//get the chosen financial product
			Long productId = (Long) variables.get("businessLoanType");
			FinancialProductEntity product = financialProductServiceBean.getFinancialProduct(productId);
			
			double interestSpread = product.getMaxInterestRate() - product.getMinInterestRate();
			double interestStep = interestSpread / 8.0;
			
			
			double individualInterestRate = product.getMaxInterestRate();

			switch (rating) {
				case "AAA": individualInterestRate = product.getMinInterestRate(); break;
				case "AA": individualInterestRate = product.getMinInterestRate() + interestStep; break;
				case "A": individualInterestRate = product.getMinInterestRate() + (2.0 * interestStep); break;
				case "BBB": individualInterestRate = product.getMinInterestRate() + (4.0 * interestStep); break;
				case "BB": individualInterestRate = product.getMinInterestRate() + (7.0 * interestStep); break;
				default: individualInterestRate = product.getMaxInterestRate(); break;
			}
			
			String problems = "";
			
			delegateExecution.setVariable("interestRate", ""+(individualInterestRate * 100.0));
			
			Long amount = (Long) variables.get("amount");
			Long period = (Long) variables.get("period");
			
			Long individualAmount;
			Long individualPeriod;
			
			
			
			if (amount <= product.getMaxAmount() && amount >= product.getMinAmount()) {
				individualAmount = amount;
				delegateExecution.setVariable("amount", individualAmount);
			} else {
				individualAmount = amount;
				delegateExecution.setVariable("amount", individualAmount);
				problems = problems + "WARNING: The amount of EUR " + amount + " is out of range for the " + product.getProductName() + ". ";
			}
			
			if (period <= product.getMaxPeriod() && period >= product.getMinPeriod()) {
				individualPeriod = period;
				delegateExecution.setVariable("period", individualPeriod);
			} else {
				individualPeriod = period;
				delegateExecution.setVariable("period", individualPeriod);
				problems = problems + "WARNING: The period of " + period + " years is out of range for the " + product.getProductName() + ". ";
			}
			
			delegateExecution.setVariable("problems", problems);
			delegateExecution.setVariable("businessLoanTypeText", product.getProductName());
			
			
		
		}
		
		
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
	    	  	
	    	  	delegateExecution.setVariable("rating", entityManager.find(CreditHistoryEntity.class, creditHistoryId).getRating());
	    	  	delegateExecution.setVariable("badDeptsInPastTwoYears", entityManager.find(CreditHistoryEntity.class, creditHistoryId).getBadDeptsInPastTwoYears());
	    	  	delegateExecution.setVariable("deptRatioWithNewCreditAmount", entityManager.find(CreditHistoryEntity.class, creditHistoryId).getDeptRatioWithNewCreditAmount());
	    	  	 	  	
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
		    
		    String message = "";
		    
		    boolean privateClient = ("private".equals(variables.get("customerType")));
		    
		    if(privateClient){
		    	message = "{\"variables\": {"
			    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
			    		+ "\"customerId\" : {\"value\" : \"1\", \"type\": \"Long\"},"
			    		+ "\"name\" : {\"value\" : \"" + variables.get("firstname") + "\", \"type\": \"String\"},"
			    		+ "\"surname\" : {\"value\" : \"" + variables.get("lastname") + "\", \"type\": \"String\"},"
			    		+ "\"street\" : {\"value\" : \"" + variables.get("street") + " " + variables.get("streetNumber") + "\", \"type\": \"String\"},"
			    		+ "\"zip\" : {\"value\" : \"" + variables.get("zipCode") + "\", \"type\": \"String\"},"
			    		+ "\"city\" : {\"value\" : \"" + variables.get("city") + "\", \"type\": \"String\"},"
			    		+ "\"privateClient\" : {\"value\" : \"" + privateClient + "\", \"type\": \"Boolean\"}"
			    		+ "} }";
		    }
		    else{
		    	message = "{\"variables\": {"
			    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
			    		+ "\"customerId\" : {\"value\" : \"1\", \"type\": \"Long\"},"
			    		+ "\"name\" : {\"value\" : \"" + variables.get("orgName") + "\", \"type\": \"String\"},"
			    		+ "\"surname\" : {\"value\" : \"\", \"type\": \"String\"},"
			    		+ "\"street\" : {\"value\" : \"" + variables.get("street") + " " + variables.get("streetNumber") + "\", \"type\": \"String\"},"
			    		+ "\"zip\" : {\"value\" : \"" + variables.get("zipCode") + "\", \"type\": \"String\"},"
			    		+ "\"city\" : {\"value\" : \"" + variables.get("city") + "\", \"type\": \"String\"},"
			    		+ "\"privateClient\" : {\"value\" : \"" + privateClient + "\", \"type\": \"Boolean\"}"
			    		+ "} }";
		    }
		    
		    
			Client client = Client.create();
			
			//for testing
		    WebResource webResource = client.resource("http://localhost:8080/engine-rest/process-definition/key/HistoryAPIsimulation/start");
		    
		    //real GTA path
		    //WebResource webResource = client.resource("http://localhost:8080/GTA/rest/order/creditHistory");
		    
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

public void requestCreditHistoryCleanup(DelegateExecution delegateExecution) {
		
		System.out.println("*** Request credit history cleanup from GTA ***");
		
		try {
			//Create and safe Id
		    delegateExecution.setVariable("requestId", delegateExecution.getId());
		    
			// Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    String message = "";
		    
		    boolean privateClient = ("private".equals(variables.get("customerType")));
		    
		    if(privateClient){
		    	message = "{\"variables\": {"
			    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
			    		+ "\"customerId\" : {\"value\" : \"1\", \"type\": \"Long\"},"
			    		+ "\"name\" : {\"value\" : \"" + variables.get("firstname") + "\", \"type\": \"String\"},"
			    		+ "\"surname\" : {\"value\" : \"" + variables.get("lastname") + "\", \"type\": \"String\"},"
			    		+ "\"street\" : {\"value\" : \"" + variables.get("street") + " " + variables.get("streetNumber") + "\", \"type\": \"String\"},"
			    		+ "\"zip\" : {\"value\" : \"" + variables.get("zipCode") + "\", \"type\": \"String\"},"
			    		+ "\"city\" : {\"value\" : \"" + variables.get("city") + "\", \"type\": \"String\"},"
			    		+ "\"privateClient\" : {\"value\" : \"" + privateClient + "\", \"type\": \"Boolean\"}"
			    		+ "} }";
		    }
		    else{
		    	message = "{\"variables\": {"
			    		+ "\"requestId\" : {\"value\" : \"" + variables.get("requestId") + "\", \"type\": \"String\"},"
			    		+ "\"customerId\" : {\"value\" : \"1\", \"type\": \"Long\"},"
			    		+ "\"name\" : {\"value\" : \"" + variables.get("orgName") + "\", \"type\": \"String\"},"
			    		+ "\"surname\" : {\"value\" : \"\", \"type\": \"String\"},"
			    		+ "\"street\" : {\"value\" : \"" + variables.get("street") + " " + variables.get("streetNumber") + "\", \"type\": \"String\"},"
			    		+ "\"zip\" : {\"value\" : \"" + variables.get("zipCode") + "\", \"type\": \"String\"},"
			    		+ "\"city\" : {\"value\" : \"" + variables.get("city") + "\", \"type\": \"String\"},"
			    		+ "\"privateClient\" : {\"value\" : \"" + privateClient + "\", \"type\": \"Boolean\"}"
			    		+ "} }";
		    }
		    
			Client client = Client.create();
			
			//for testing
		    WebResource webResource = client.resource("http://localhost:8080/engine-rest/process-definition/key/CleanupAPIsimulation/start");
		    		    
		    //real GTA path
		    //WebResource webResource = client.resource("http://localhost:8080/GTA/rest/order/creditCleanup");
		    
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
