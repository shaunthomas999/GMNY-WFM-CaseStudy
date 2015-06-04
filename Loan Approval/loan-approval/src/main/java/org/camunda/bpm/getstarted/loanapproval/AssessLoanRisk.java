package org.camunda.bpm.getstarted.loanapproval; 
 
import org.camunda.bpm.engine.delegate.DelegateExecution; 
import org.camunda.bpm.engine.delegate.JavaDelegate; 
 
public class AssessLoanRisk implements JavaDelegate { 
   
  protected long Risk_Wt;  
  protected String Credit_Asst=""; 
   
   
  public void execute(DelegateExecution execution) throws Exception { 
     
    Credit_Asst= (String) execution.getVariable("Credit_Assessment"); 
    System.out.println((String) execution.getVariable("Credit_Assessment")); 
     
     
    if (("B").equals(Credit_Asst)) { 
      Risk_Wt=0;     
    } 
    else if (("BB").equals(Credit_Asst)){ 
      Risk_Wt=20; 
    } 
    else if (("BBB").equals(Credit_Asst)) { 
      Risk_Wt=40; 
    } 
    else if (("A").equals(Credit_Asst)) { 
      Risk_Wt=60; 
    } 
    else if (("AA").equals(Credit_Asst)) { 
      Risk_Wt=80; 
    } 
    else if (("AAA").equals(Credit_Asst)) { 
      Risk_Wt=100; 
    } 
    execution.setVariable("Risk_Weight", Risk_Wt); 
    System.out.println("Risk Weight" + Risk_Wt); 
    System.out.println("Credit_Assessment = " + Credit_Asst); 
  } 
} 