package org.camunda.bpm.getstarted.gmny.webservice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

/*
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.gson.JsonElementValueReader;
*/


import org.camunda.bpm.getstarted.gmny.api.IWebserviceCreditHistory;
import org.camunda.bpm.getstarted.gmny.dto.DTOCreditHistory;

public class WebserviceCreditHistoryImpl implements IWebserviceCreditHistory{

	public DTOCreditHistory returnCreditHistory(HttpServletRequest request,
			DTOCreditHistory creditHistory) {
		// TODO Auto-generated method stub
		return null;
	}

}
