package org.camunda.bpm.getstarted.gmny.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.getstarted.gmny.model.*;

@Startup @Singleton
public class ApplicationInitialiser {

  @PersistenceContext
  EntityManager em;
  
  @EJB
  FinancialProductServiceBean financialProductServiceBean;
  
  
  @PostConstruct
  public void initialise() {
	  
	  //private
	  
	  	FinancialProductEntity carLoan = createFinancialProduct("Car Loan", "private", "Treate yourself to a new car!", (long) 1000, (long) 100000, 0.04, 0.15, (long) 1, (long) 4, (long) 1);    
	    em.persist(carLoan);
	    em.flush();
	    
	  	FinancialProductEntity debtConsolidationLoan = createFinancialProduct("Debt Consolidation Loan", "private", "Do a debt conversion and benefit from our favourable conditions.", (long) 10000, (long) 150000, 0.03, 0.075, (long) 2, (long) 8, (long) 2);    
	    em.persist(debtConsolidationLoan);
	    em.flush();
	    
	  	FinancialProductEntity homeImprovementLoan = createFinancialProduct("Home Improvement Loan", "private", "Renovate your home!", (long) 10000, (long) 150000, 0.03, 0.108, (long) 2, (long) 5, (long) 3);    
	    em.persist(homeImprovementLoan);
	    em.flush();
	    
	  	FinancialProductEntity holidayLoan = createFinancialProduct("Holiday Loan", "private", "Here comes the sun!", (long) 1000, (long) 8000, 0.046, 0.10, (long) 1, (long) 3, (long) 4);    
	    em.persist(holidayLoan);
	    em.flush();
	    
	  	FinancialProductEntity gmnyAmbitiousPrivateLoan = createFinancialProduct("GMNY Ambitious Private", "private", "Fulfil a special desire!", (long) 30000, (long) 300000, 0.024, 0.075, (long) 1, (long) 8, (long) 5);    
	    em.persist(gmnyAmbitiousPrivateLoan);
	    em.flush();
	    
	   // business
	    
	  	FinancialProductEntity startupLoan = createFinancialProduct("Startup Loan", "business", "Loans to small businesses from private sector", (long) 10000, (long) 500000, 0.018, 0.07, (long) 1, (long) 5, (long) 6);    
	    em.persist(startupLoan);
	    em.flush();
	    
	  	FinancialProductEntity professionalLoan = createFinancialProduct("Professional Loan", "business", "For doctors, dentists, lawyers, pharmacists, etc.", (long) 10000, (long) 500000, 0.05, 0.10, (long) 1, (long) 5, (long) 7);    
	    em.persist(professionalLoan);
	    em.flush();
	  
	  	FinancialProductEntity smallBusinessLoan = createFinancialProduct("Small Business Loan", "business", "Government-backed loans to small businesses from private sector", (long) 10000, (long) 500000, 0.058, 0.085, (long) 5, (long) 20, (long) 8);    
	    em.persist(smallBusinessLoan);
	    em.flush();
	    
	  	FinancialProductEntity constructionLoan = createFinancialProduct("Construction Financing", "business", "Loan for commercial construction", (long) 50000, (long) 5000000, 0.07, 0.08, (long) 5, (long) 25, (long) 9);    
	    em.persist(constructionLoan);
	    em.flush();
	    

	  	FinancialProductEntity gmnyAmbitiousBusinessLoan = createFinancialProduct("GMNY Ambitious Business", "business", "The customized loan product for our ambitious business customers.", (long) 500000, (long) 8000000, 0.027, 0.124, (long) 3, (long) 25, (long) 10);    

	    em.persist(gmnyAmbitiousBusinessLoan);
	    em.flush();
	    
	  	CustomerEntity markusL = new CustomerEntity();
	  
	  	markusL.setFirstname("Markus");
	    markusL.setLastname("Lewe");
	    markusL.setGender("Mr.");
	    markusL.setEmail("ichplakatieremuensterzu@rautschka.com");
	    markusL.setPhoneNumber("+4915152714866");
	    markusL.setStreet("Mauritzstr.");
	    markusL.setStreetNumber("4-6");
	    markusL.setZipCode("48143");
	    markusL.setCity("Muenster");
	    markusL.setCustomerType("private");
	    
	    markusL.setPassword((Long.toHexString(Double.doubleToLongBits(Math.random()))).substring(0, 6));
	    
	    Date today = new Date();
	    markusL.setRegistrationDate(today);

	    em.persist(markusL);
	    em.flush();
	    
	    CustomerEntity jochenK = new CustomerEntity();
	    
	  	jochenK.setFirstname("Jochen");
	  	jochenK.setLastname("Koehnke");
	  	jochenK.setGender("Mr.");
	  	jochenK.setEmail("plakatterrorinmuenster@rautschka.com");
	  	jochenK.setPhoneNumber("+49 251 77 0 99");
	  	jochenK.setStreet("Bahnhofstrasse");
	  	jochenK.setStreetNumber("9");
	  	jochenK.setZipCode("48143");
	  	jochenK.setCity("Muenster");
	  	jochenK.setCustomerType("private");
	    
	  	jochenK.setPassword((Long.toHexString(Double.doubleToLongBits(Math.random()))).substring(0, 6));
	    
	  	jochenK.setRegistrationDate(today);

	    em.persist(jochenK);
	    em.flush();
	    
	    
	    CustomerEntity edekaD = new CustomerEntity();
	    
	  	edekaD.setFirstname("Patrick");
	  	edekaD.setLastname("Delfmann");
	  	edekaD.setOrgName("EDEKA DELFMANN");
	  	edekaD.setGender("Mr.");
	  	edekaD.setEmail("edekadelfmann@rautschka.com");
	  	edekaD.setPhoneNumber("+49 251 77 0 99");
	  	edekaD.setStreet("Leonardocampus");
	  	edekaD.setStreetNumber("9");
	  	edekaD.setZipCode("48149");
	  	edekaD.setCity("Muenster");
	  	edekaD.setCustomerType("business");
	  	edekaD.setBusinessArea("Food");
	  	
	    
	  	edekaD.setPassword((Long.toHexString(Double.doubleToLongBits(Math.random()))).substring(0, 6));
	    
	  	edekaD.setRegistrationDate(today);

	    em.persist(edekaD);
	    em.flush();
	    
	    
	    LoanEntity markusLoan = new LoanEntity();
	    markusLoan.setAmount((long) 3000);
	    markusLoan.setFinancialProduct(carLoan);
	    markusLoan.setInterestRate(0.062);
	    markusLoan.setPeriod((long) 3);
	    markusLoan.setCustomerEntity(markusL);
	    
	    em.persist(markusLoan);
	    em.flush();
	   
	    PdfServiceBean.createPrivateLoanContract(markusL, markusLoan);
	    

	   System.out.println(financialProductServiceBean.getFinancialProduct(2).getProductName());
	    
  }
  
  private FinancialProductEntity createFinancialProduct(String productName, String productType, String productDescription, Long minAmount, Long maxAmount, Double minInterestRate, Double maxInterestRate, Long minPeriod, Long maxPeriod, Long identifier) {
	  
	  	FinancialProductEntity product = new FinancialProductEntity();
	    
	    product.setProductName(productName);
	    product.setProductType(productType);
	    product.setProductDescription(productDescription);
	    product.setMinAmount(minAmount);
	    product.setMaxAmount(maxAmount);
	    product.setMinInterestRate(minInterestRate);
	    product.setMaxInterestRate(maxInterestRate);
	    product.setMinPeriod(minPeriod);
	    product.setMaxPeriod(maxPeriod);
	    product.setIdentifier(identifier);
	    
	    return product;
  }
  
  
}
