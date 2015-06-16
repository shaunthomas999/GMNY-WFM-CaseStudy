package org.camunda.bpm.getstarted.gmny.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import java.io.Serializable;
import java.util.Date;
 
@Entity
public class CustomerEntity implements Serializable {
 
  private static final long serialVersionUID = 1L;
 
  @Id
  @GeneratedValue
  protected Long id;
 
  @Version
  protected long version;
 
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String phoneNumber;
  private String street;
  private String streetNumber;
  private String zipCode;
  private String city;
  private Date registrationDate;
  private Long creditHistoryId;
  private enum type{
	  business,
	  person
  };
 
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
  
  public String getEmail() {
	    return email;
	    }
	 
  public void setEmail(String email) {
    this.email = email;
    }
  
  public String getPassword() {
	    return password;
	  }
	 
  public void setPassword(String password) {
    this.password = password;
    }
	
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getStreetNumber() {
		return streetNumber;
	}
	
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getCreditHistoryId() {
		return creditHistoryId;
	}

	public void setCreditHistoryId(Long creditHistoryId) {
		this.creditHistoryId = creditHistoryId;
	}


 
}