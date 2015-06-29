package org.camunda.bpm.getstarted.gmny.ejb;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;

@Stateless
public class PdfServiceBean {
	
	public static void createWelcome(CustomerEntity customer) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(customer.getId() + "_" + customer.getLastname() + ".pdf"));
            document.open();
            document.add(new Paragraph("TESTTEST"));
            document.close();
            System.out.println("PDF PDF PDF");

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
