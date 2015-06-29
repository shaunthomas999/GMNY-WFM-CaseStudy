package org.camunda.bpm.getstarted.gmny.ejb;

//import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
import org.camunda.bpm.getstarted.gmny.service.CustomerService;
 
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
    
    System.out.println("*** Persist customer ***");
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
    System.out.println(" ");
    PdfServiceBean.createWelcome(customerEntity);
  }
  
  public void generateTestData(){
	  
	// First customer
	// Create new customer instance
    CustomerEntity customerEntity1 = new CustomerEntity();
 
    // Set customer attributes
    customerEntity1.setFirstname("Max");
    customerEntity1.setLastname("Muster");
    customerEntity1.setEmail("max.muster@rautschka.com");
    customerEntity1.setPhoneNumber("+49 163 8475636");
    customerEntity1.setStreet("Moellmannsweg");
    customerEntity1.setStreetNumber("28");
    customerEntity1.setZipCode("48161");
    customerEntity1.setCity("Muenster");
    
    // generate password
    System.out.println("Generating random password");
    customerEntity1.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())));
    
    // set creation date
    Date today1 = new Date();
    customerEntity1.setRegistrationDate(today1);
    
    //Persist customer instance and flush. After the flush the id of the customer instance is set.
    entityManager.persist(customerEntity1);
    entityManager.flush();
    
    System.out.println("Customer saved with ID: " + customerEntity1.getId());
    
	// Create new credit history instance
	CreditHistoryEntity creditHistory = new CreditHistoryEntity();
    
	// Set credit history attributes from form
    creditHistory.setCustomerId((Long) customerEntity1.getId());
    creditHistory.setRequestId((String) "dummy");
    
    creditHistory.setScoring(new Long("10"));
    creditHistory.setBadDepts(new Long("1"));
    creditHistory.setConsumerCredits(new Long("2"));
    // set creation date
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try{
    	Date date = sdf.parse("06/06/2015");
    	creditHistory.setReceptionDate(date);
    }catch (Exception e) {	
	}
    entityManager.persist(creditHistory);
    entityManager.flush();
    System.out.println("Saving credit history (Id, scoring, date): " + creditHistory.getId() + ", " + creditHistory.getScoring() + ", " + creditHistory.getReceptionDate());
    
    customerEntity1.setCreditHistoryId(creditHistory.getId());;
    entityManager.merge(customerEntity1);
    
    // Second customer
    
    CustomerEntity customerEntity2 = new CustomerEntity();
    
    // Set customer attributes
    customerEntity2.setFirstname("Hans");
    customerEntity2.setLastname("Meier");
    customerEntity2.setEmail("meier@rautschka.com");
    customerEntity2.setPhoneNumber("+49 163 1231234");
    customerEntity2.setStreet("Leonardocampus");
    customerEntity2.setStreetNumber("1");
    customerEntity2.setZipCode("48162");
    customerEntity2.setCity("Muenster");
    
    // generate password
    System.out.println("Generating random password");
    customerEntity2.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())));
    
    // set creation date
    Date today2 = new Date();
    customerEntity2.setRegistrationDate(today2);
    
    //Persist customer instance and flush. After the flush the id of the customer instance is set.
    entityManager.persist(customerEntity2);
    entityManager.flush();
    
    System.out.println("Customer saved with ID: " + customerEntity2.getId());
  }
  
  public CustomerEntity getCustomer(Long customerId) {
    // Load customer entity from database
  	System.out.println("Customer found: " + entityManager.find(CustomerEntity.class, customerId));
    return entityManager.find(CustomerEntity.class, customerId);
  }
    
  public void loadCustomer(DelegateExecution delegateExecution) {    
	System.out.println("*** Load customer from database ***");
	
  	// Get customerId from process memory
    Map<String, Object> variables = delegateExecution.getVariables(); 
    Long customerId = (Long) variables.get("customerId");
    System.out.println("CustomerId from execution: " + customerId);
  
    // Load customer entity from database and save it in process memory
  	System.out.println("Loading customer into process memory: " + entityManager.find(CustomerEntity.class, customerId));
  	delegateExecution.setVariable("firstname", entityManager.find(CustomerEntity.class, customerId).getFirstname());
  	delegateExecution.setVariable("lastname", entityManager.find(CustomerEntity.class, customerId).getLastname());
  	delegateExecution.setVariable("email", entityManager.find(CustomerEntity.class, customerId).getEmail());
  	delegateExecution.setVariable("phoneNumber", entityManager.find(CustomerEntity.class, customerId).getPhoneNumber());
  	delegateExecution.setVariable("registrationDate", entityManager.find(CustomerEntity.class, customerId).getRegistrationDate());
  	System.out.println("Customer" + entityManager.find(CustomerEntity.class, customerId).getFirstname() + " " + entityManager.find(CustomerEntity.class, customerId).getLastname() + " loaded.");
  	System.out.println(" ");
  }
  
  public void sendEmailToCustomer(DelegateExecution delegateExecution) {
	  
	  System.out.println("*** Sending email to customer ***");
	  
  	// Get customerId from process memory
    Map<String, Object> variables = delegateExecution.getVariables();
    Long customerId = (Long) variables.get("customerId");
    String lastname = (String) variables.get("lastname");
    
    String identifier = customerId + "_" + lastname;
    
    // Send simple mail
    /*
    System.out.println(
		MailServiceBean.send(
				entityManager.find(CustomerEntity.class, customerId).getEmail(),
				"Willkommen bei GMNY",
				"Hey " + entityManager.find(CustomerEntity.class, customerId).getFirstname() + ", your password is: " + entityManager.find(CustomerEntity.class, customerId).getPassword()
	));
	*/
    
    // Send fancy mail
    HashMap<String, String> vars = new HashMap<String, String>();
	vars.put("greeting", "Dear " + entityManager.find(CustomerEntity.class, customerId).getFirstname() + " " + entityManager.find(CustomerEntity.class, customerId).getLastname() + "!");
    vars.put("text", "We are happy to welcome you as a customer of GMNY. You are now able to access our Online-Banking system! Your login crediantials are as follows: <br> Customer-ID: " + entityManager.find(CustomerEntity.class, customerId).getId() + "<br> Password: " + entityManager.find(CustomerEntity.class, customerId).getPassword());
    vars.put("buttonTitle", "Go to Online-Banking!");
	vars.put("buttonLink", "http://www.wemmer.ch");
	try {
		System.out.println(
			MailServiceBean.send(
					entityManager.find(CustomerEntity.class, customerId).getEmail(),
					"Welcome to GMNY",
					vars,
					identifier
			)
		);
	} catch (IOException e) {
		// Error when Sending the welcome mail
		e.printStackTrace();
	}
	
	System.out.println(" ");
	
  }
  
}