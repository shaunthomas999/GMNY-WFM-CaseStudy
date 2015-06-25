package org.camunda.bpm.getstarted.gmny.ejb;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;

@Stateless
public class PdfServiceBean {
	
	public static void createWelcome(CustomerEntity customer) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream(customer.getId() + "_" + customer.getLastname() + ".pdf"));

            document.open();
            document.add(new Paragraph("A Hello World PDF document.zzzz"));
            document.add(new Paragraph(customer.getLastname()));
            document.close(); // no need to close PDFwriter?
            System.out.println("PDF PDF PDF");

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
