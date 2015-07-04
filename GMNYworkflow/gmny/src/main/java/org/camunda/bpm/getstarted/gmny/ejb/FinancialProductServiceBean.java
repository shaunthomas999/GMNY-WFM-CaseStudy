package org.camunda.bpm.getstarted.gmny.ejb;

import java.io.IOException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.getstarted.gmny.model.FinancialProductEntity;


 
@Stateless
public class FinancialProductServiceBean {
 

  @PersistenceContext
  private EntityManager entityManager;
  
  public FinancialProductEntity getFinancialProduct(long identifier) {
	    FinancialProductEntity product = entityManager.find(FinancialProductEntity.class, identifier);
	    if (product == null)
			throw new IllegalArgumentException(String.format("Financial Product with ID %s not found AAAAA", identifier));
  	return product;
  }
  
}