package org.camunda.bpm.getstarted.gmny.dto;

import java.util.ArrayList;
import java.util.Date;

import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class DTOCreditHistory {
	private Long id;
	private Long customerId;
	private String firstname;
	private String lastname;
	private String rating;
	
	
	public DTOCreditHistory() {	
	}
	
	public DTOCreditHistory(CreditHistoryEntity creditHistory) {
		
	}
	
	public Long getId() {
		return id;
	}
	 
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	 
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getFirstname() {
		  return firstname;
	}
		 
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	 
	public String getLastname() {
	    return lastname;
	}
	 
	public void setLastname(String lastname) {
	    this.lastname = lastname;
	}
	
	public String getRating(){
		return rating;
	}
	  
	public void setRating(String rating){
		this.rating = rating;
	}
	

}
