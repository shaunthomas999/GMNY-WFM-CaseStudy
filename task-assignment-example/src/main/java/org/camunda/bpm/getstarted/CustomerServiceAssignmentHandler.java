package org.camunda.bpm.getstarted;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;

public class CustomerServiceAssignmentHandler implements TaskListener {
	
	private final static Logger LOGGER = Logger.getLogger(CustomerServiceAssignmentHandler.class.getName());
	 
	public void notify(DelegateTask delegateTask) {
		LOGGER.log(Level.INFO, "Inside notify method");
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
		
		List<User> users = identityService.createUserQuery().memberOfGroup("customerService").list();
		LOGGER.info("Users : " + users);
		
		Random randomizer = new Random();
		User selectedUser = users.get(randomizer.nextInt(users.size()));
		LOGGER.info("Selected user : " + selectedUser);
		
		delegateTask.setAssignee(selectedUser.getId());
		LOGGER.info("User assigned to task");
	}

}