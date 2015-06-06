package org.camunda.bpm.getstarted;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.Task;

public class CustomerServiceAssignmentHandler implements TaskListener {
	
	private final static Logger LOGGER = Logger.getLogger(CustomerServiceAssignmentHandler.class.getName());
	 
	public void notify(DelegateTask delegateTask) {
		LOGGER.log(Level.INFO, "Inside notify method");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("customerService").list();
		LOGGER.info("Tasks : " + tasks);
	}

}