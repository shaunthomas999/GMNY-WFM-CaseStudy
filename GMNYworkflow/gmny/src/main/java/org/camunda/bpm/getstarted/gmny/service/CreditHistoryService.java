package org.camunda.bpm.getstarted.gmny.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;

public interface CreditHistoryService {

	public void persistCreditHistory(DelegateExecution delegateExecution);
	
	public void loadCreditHistory(DelegateExecution delegateExecution);
	
	public void requestCreditHistory(DelegateExecution delegateExecution);
	
	public void performRiskAssessment(DelegateExecution delegateExecution);
	
	public void requestCreditHistoryCleanup(DelegateExecution delegateExecution);
}
