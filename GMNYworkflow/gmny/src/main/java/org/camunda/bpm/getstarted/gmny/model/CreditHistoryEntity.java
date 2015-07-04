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
  protected String requestId;
  protected Date receptionDate;
  
  private Long scoring;
  private Long badDepts;
  private Long consumerCredits;
  
  private String rating;
  private Long badDeptsInPastTwoYears;
  private Long deptRatioWithNewCreditAmount;
  
  private Boolean recommendation;
  
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
  

	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}

	public Long getScoring() {
		return scoring;
	}

	public void setScoring(Long scoring) {
		this.scoring = scoring;
	}

	public Long getBadDepts() {
		return badDepts;
	}

	public void setBadDepts(Long badDepts) {
		this.badDepts = badDepts;
	}

	public Long getConsumerCredits() {
		return consumerCredits;
	}

	public void setConsumerCredits(Long consumerCredits) {
		this.consumerCredits = consumerCredits;
	}

	public Boolean getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Boolean recommendation) {
		this.recommendation = recommendation;
	}

	public Long getBadDeptsInPastTwoYears() {
		return badDeptsInPastTwoYears;
	}

	public void setBadDeptsInPastTwoYears(Long badDeptsInPastTwoYears) {
		this.badDeptsInPastTwoYears = badDeptsInPastTwoYears;
	}

	public Long getDeptRatioWithNewCreditAmount() {
		return deptRatioWithNewCreditAmount;
	}

	public void setDeptRatioWithNewCreditAmount(
			Long deptRatioWithNewCreditAmount) {
		this.deptRatioWithNewCreditAmount = deptRatioWithNewCreditAmount;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
  
}