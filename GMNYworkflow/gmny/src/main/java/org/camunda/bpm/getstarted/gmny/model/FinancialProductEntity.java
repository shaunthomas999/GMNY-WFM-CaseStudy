package org.camunda.bpm.getstarted.gmny.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import java.io.Serializable;
 
@Entity
public class FinancialProductEntity implements Serializable {
 
  private static final long serialVersionUID = 1L;
 

 
  @Version
  protected long version;
  
  @Id
  private Long identifier;
 
  private String productType;
  private String productName;
  private String productDescription;
  
  
  private Double minInterestRate;
  private Double maxInterestRate;
  
  private Long minPeriod;
  private Long maxPeriod;
  
  private Long minAmount;
  private Long maxAmount;
  
public String getProductType() {
	return productType;
}
public void setProductType(String productType) {
	this.productType = productType;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getProductDescription() {
	return productDescription;
}
public void setProductDescription(String productDescription) {
	this.productDescription = productDescription;
}
public Double getMinInterestRate() {
	return minInterestRate;
}
public void setMinInterestRate(Double minInterestRate) {
	this.minInterestRate = minInterestRate;
}
public Double getMaxInterestRate() {
	return maxInterestRate;
}
public void setMaxInterestRate(Double maxInterestRate) {
	this.maxInterestRate = maxInterestRate;
}
public Long getMinPeriod() {
	return minPeriod;
}
public void setMinPeriod(Long minPeriod) {
	this.minPeriod = minPeriod;
}
public Long getMaxPeriod() {
	return maxPeriod;
}
public void setMaxPeriod(Long maxPeriod) {
	this.maxPeriod = maxPeriod;
}
public Long getMinAmount() {
	return minAmount;
}
public void setMinAmount(Long minAmount) {
	this.minAmount = minAmount;
}
public Long getMaxAmount() {
	return maxAmount;
}
public void setMaxAmount(Long maxAmount) {
	this.maxAmount = maxAmount;
}
public Long getIdentifier() {
	return identifier;
}
public void setIdentifier(Long identifier) {
	this.identifier = identifier;
}
  
}