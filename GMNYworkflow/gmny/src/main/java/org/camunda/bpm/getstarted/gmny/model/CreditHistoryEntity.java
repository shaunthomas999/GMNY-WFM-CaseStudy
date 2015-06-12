package org.camunda.bpm.getstarted.gmny.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;

@Entity
public class CreditHistoryEntity implements Serializable {
 
  private static final long serialVersionUID = 1L;
 
  @Id
  @GeneratedValue
  protected Long id;
 
  @Version
  protected long version;
 
  protected Long customerId;
  protected String rating;
  protected long requestId;
  protected Date receptionDate;
  
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
  
  public String getRating(){
	  return rating;
  }
  
  public void setRating(String rating){
	  this.rating = rating;
  }

	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}
  
}