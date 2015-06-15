package org.camunda.bpm.getstarted.gmny.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CreditHistoryEntity;

import com.sun.jersey.api.client.ClientResponse;

public interface CreditHistoryService {

	public void persistCreditHistory(DelegateExecution delegateExecution);
	
	public void loadCreditHistory(DelegateExecution delegateExecution);
	
	public void requestCreditHistory(DelegateExecution delegateExecution);
}
