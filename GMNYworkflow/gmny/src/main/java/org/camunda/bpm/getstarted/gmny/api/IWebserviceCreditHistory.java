package org.camunda.bpm.getstarted.gmny.api;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.camunda.bpm.getstarted.gmny.dto.DTOCreditHistory;

@Path("customer")
public interface IWebserviceCreditHistory {

	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public DTOCreditHistory returnCreditHistory(@Context HttpServletRequest request, DTOCreditHistory creditHistory);
	
	
}
