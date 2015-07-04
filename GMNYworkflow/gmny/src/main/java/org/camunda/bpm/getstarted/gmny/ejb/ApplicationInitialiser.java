package org.camunda.bpm.getstarted.gmny.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.getstarted.gmny.model.*;

@Startup @Singleton
public class ApplicationInitialiser {

  @PersistenceContext
  EntityManager em;
  
  @PostConstruct
  public void initialise() {
	  
	  	FinancialProductEntity carLoan = new FinancialProductEntity();
	    
	    carLoan.setProductName("Car Loan");
	    carLoan.setProductType("private");
	    carLoan.setProductDescription("Treat yourself to a new car!");
	    carLoan.setMinAmount((long) 1000);
	    carLoan.setMaxAmount((long) 120000);
	    carLoan.setMinInterestRate(4.9f);
	    carLoan.setMaxInterestRate(9.6f);
	    carLoan.setMinPeriod((long) 1);
	    carLoan.setMinPeriod((long) 6);
	    
	    em.persist(carLoan);
	    em.flush();
	  
	  	CustomerEntity markusL = new CustomerEntity();
	  
	  	markusL.setFirstname("Markus");
	    markusL.setLastname("Lewe");
	    markusL.setGender("Mr.");
	    markusL.setEmail("ichplakatieremuensterzu@rautschka.com");
	    markusL.setPhoneNumber("+49 251 418420");
	    markusL.setStreet("Mauritzstr.");
	    markusL.setStreetNumber("4-6");
	    markusL.setZipCode("48143");
	    markusL.setCity("Muenster");
	    
	    markusL.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())));
	    
	    Date today = new Date();
	    markusL.setRegistrationDate(today);

	    em.persist(markusL);
	    em.flush();
	    
	    CustomerEntity jochenK = new CustomerEntity();
	    
	  	jochenK.setFirstname("Jochen");
	  	jochenK.setLastname("Köhnke");
	  	jochenK.setGender("Mr.");
	  	jochenK.setEmail("plakatterrorinmuenster@rautschka.com");
	  	jochenK.setPhoneNumber("+49 251 77 0 99");
	  	jochenK.setStreet("Bahnhofstrasse");
	  	jochenK.setStreetNumber("9");
	  	jochenK.setZipCode("48143");
	  	jochenK.setCity("Muenster");
	    
	  	jochenK.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())));
	    
	  	jochenK.setRegistrationDate(today);

	    em.persist(jochenK);
	    em.flush();
	    
	    
	    LoanEntity markusLoan = new LoanEntity();
	    markusLoan.setAmount((long) 3000);
	    markusLoan.setFinancialProduct(carLoan);
	    markusLoan.setInterestRate(0.062f);
	    markusLoan.setPeriod((long) 3);
	    markusLoan.setCustomerEntity(markusL);
	    
	    em.persist(markusLoan);
	    em.flush();
	   
	    PdfServiceBean.createPrivateLoanContract(markusL, markusLoan);
	    
  }
  
  
}
