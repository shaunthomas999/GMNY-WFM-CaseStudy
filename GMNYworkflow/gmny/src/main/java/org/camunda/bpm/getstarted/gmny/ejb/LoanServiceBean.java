package org.camunda.bpm.getstarted.gmny.ejb;

import java.io.IOException;




import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.LoanEntity;

 
@Stateless
@Named
public class LoanServiceBean {
 

  @PersistenceContext
  private EntityManager entityManager;
  
  public Float getAnnuity(LoanEntity loan) {
	  float interestRatePlusOne = loan.getInterestRate() + 1.0f;
      float interestRatePlusOneToThePowerOfX = (float) Math.pow(interestRatePlusOne, loan.getPeriod());
      float annuityPerYear = (float) (loan.getAmount() * ((interestRatePlusOneToThePowerOfX * loan.getInterestRate()) / (interestRatePlusOneToThePowerOfX - 1.0f)));
      float annuityPerMonth = (float) (Math.rint((annuityPerYear / 12) * 100 ) / 100.);
	  
	  return annuityPerMonth;
  }
  
  public Float getInterestRate(LoanEntity loan) {
	  return loan.getInterestRate() * 100.0f;
  }
  
  public Long getPeriodInMonths(LoanEntity loan) {
	  return (long) loan.getPeriod() * 12;
  }
  
  
}