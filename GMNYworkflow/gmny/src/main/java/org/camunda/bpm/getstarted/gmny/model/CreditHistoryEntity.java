package org.camunda.bpm.getstarted.gmny.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
public class CreditHistoryEntity implements Serializable {
 
  private static final long serialVersionUID = 1L;
 
  @Id
  @GeneratedValue
  protected Long id;
 
  @Version
  protected long version;
 
  protected CustomerEntity customer;
  protected String rating;
  
  public Long getId() {
    return id;
  }
 
  public void setId(Long id) {
    this.id = id;
  	}
 
  public long getVersion() {
    return version;
  	}
 
  public void setVersion(long version) {
    this.version = version;
  	}
  
  public CustomerEntity getCustomer(){
	  return customer;
  }
  
  public void setCustomer(CustomerEntity customer){
	  this.customer = customer;
  }
  
  public String getRating(){
	  return rating;
  }
  
  public void setRating(String rating){
	  this.rating = rating;
  }
  
 
}