package org.camunda.bpm.getstarted.gmny.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;

public interface CustomerService {
	
	public void persistCustomer(DelegateExecution delegateExecution);
	
	public CustomerEntity getCustomer(Long customerId);
	
	public void generateTestData();
	
	public void loadCustomer(DelegateExecution delegateExecution);
	
	public void sendEmailToCustomer(DelegateExecution delegateExecution);
	
}
