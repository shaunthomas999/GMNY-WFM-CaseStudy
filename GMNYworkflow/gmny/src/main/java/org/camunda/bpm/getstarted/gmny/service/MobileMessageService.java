package org.camunda.bpm.getstarted.gmny.service;


import org.camunda.bpm.engine.delegate.DelegateExecution;


public interface MobileMessageService {

	
	public void sendMessage(Long customerId, String message);

	public void sendApplied(DelegateExecution delegateExecution);
	
	public void sendStatus(DelegateExecution delegateExecution);
	
	public void sendResultStatus(DelegateExecution delegateExecution);

}
