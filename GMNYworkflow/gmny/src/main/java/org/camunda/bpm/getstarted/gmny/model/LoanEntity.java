package org.camunda.bpm.getstarted.gmny.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import java.io.Serializable;
 
@Entity
public class LoanEntity implements Serializable {
 
  private static final long serialVersionUID = 1L;
 
  @Id
  @GeneratedValue
  protected Long id;
 
  @Version
  protected long version;
  
  @ManyToOne
  private FinancialProductEntity financialProduct;
  
  @ManyToOne CustomerEntity customerEntity;
 
  private long amount;
  private long period;
  private float interestRate;




public long getAmount() {
	return amount;
}


public void setAmount(long amount) {
	this.amount = amount;
}


public long getPeriod() {
	return period;
}


public void setPeriod(long period) {
	this.period = period;
}


public float getInterestRate() {
	return interestRate;
}


public void setInterestRate(float interestRate) {
	this.interestRate = interestRate;
}


public FinancialProductEntity getFinancialProduct() {
	return financialProduct;
}


public void setFinancialProduct(FinancialProductEntity financialProduct) {
	this.financialProduct = financialProduct;
}

public CustomerEntity getCustomerEntity() {
	return customerEntity;
}

public void setCustomerEntity(CustomerEntity customerEntity) {
	this.customerEntity = customerEntity;
}
  
}