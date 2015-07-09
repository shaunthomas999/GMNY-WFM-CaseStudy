package org.camunda.bpm.getstarted.gmny.ejb;

import java.io.IOException;




import java.math.BigDecimal;

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
  
  public double getAnnuity(LoanEntity loan) {
	  double interestRatePlusOne = (loan.getInterestRate() / 100.0 ) + 1.0;
      double interestRatePlusOneToThePowerOfX = (double) Math.pow(interestRatePlusOne, loan.getPeriod());
      double annuityPerYear = (loan.getAmount() * ((interestRatePlusOneToThePowerOfX * (loan.getInterestRate() / 100)) / (interestRatePlusOneToThePowerOfX - 1.0)));
      double annuityPerMonth = (Math.rint((annuityPerYear / 12) * 100 ) / 100.);
	  
	  return annuityPerMonth;
  }
  
  public double getInterestRate(LoanEntity loan) {
	  
	  BigDecimal intr = new BigDecimal(loan.getInterestRate());
	  intr = intr.setScale( 2, BigDecimal.ROUND_HALF_UP );
	  String dirtyConv = ""+intr;
	  return Double.parseDouble(dirtyConv);
  }
  
  public Long getPeriodInMonths(LoanEntity loan) {
	  return (long) loan.getPeriod() * 12;
  }
  
  
}