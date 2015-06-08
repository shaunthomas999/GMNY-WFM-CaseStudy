package org.camunda.bpm.getstarted.gmny.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
 
@Entity
public class CustomerEntity implements Serializable {
 
  private static final long serialVersionUID = 1L;
 
  @Id
  @GeneratedValue
  protected Long id;
 
  @Version
  protected long version;
 
  protected String firstname;
  protected String lastname;
  protected String email;
  protected String password;
 
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
 
}