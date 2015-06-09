package org.camunda.bpm.getstarted.gmny.ejb;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;


import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;


@Stateless
public class MailServiceBean {
	
	public static String replaceLines(String str) {
		return str.replaceAll("(\r\n|\n)", "<br />");
	}
	
	public static ClientResponse send(String to, String subject, String message) {
		Client client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter("api", "key-4ba0f5f986517cf7a300bb258a2b56d0"));
	    WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox8f4533092e2e4774811b769a9bb25be0.mailgun.org/messages");
	    MultivaluedMapImpl formData = new MultivaluedMapImpl();
	    formData.add("from", "GMNY <postmaster@sandbox8f4533092e2e4774811b769a9bb25be0.mailgun.org>");
	    formData.add("to", to);
	    formData.add("subject", subject);
	    formData.add("html", message);
	    System.out.println("Sending mail to " + to);
	    return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
	
	/*
	
	public static ClientResponse send(String to, String subject, HashMap<String, String> vars) throws IOException {
	    try {
			//configService = (ConfigService) new InitialContext().lookup("java:global/backend/ConfigServiceBean");
			HashMap<String, String> defaultVars = new HashMap<String, String>();
			defaultVars.put("greeting", "Hallo!");
			defaultVars.put("text", "testtext");
			defaultVars.put("homepage", "http://www.wemmer.ch");
			defaultVars.put("share", URLEncoder.encode("GMNY http://www.wemmer.ch", "UTF-8"));
			defaultVars.put("footer", "Sie erhalten diese E-Mail, da Sie GMNY Kunde sind!");
			defaultVars.put("buttonLink", "http://www.wemmer.ch");
			defaultVars.put("buttonTitle", "Zum Portal");
			System.out.println("Loading Mail-Template: /gmny/src/main/webapp/mail/mail.html");
			String message = Files.toString(new File("/gmny/src/main/webapp/mail/mail.html"), Charsets.UTF_8);
			vars.put("subject", subject);
			for (String key : vars.keySet()) {
				message = message.replace("{{" + key + "}}", vars.get(key));
			}
			for (String key : defaultVars.keySet()) {
				message = message.replace("{{" + key + "}}", defaultVars.get(key));
			}
			return send(to, subject, message);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	*/
}
