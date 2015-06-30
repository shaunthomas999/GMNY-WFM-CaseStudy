package org.camunda.bpm.getstarted.gmny.ejb;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;

@Stateless
public class PdfServiceBean {
	
	public static void createWelcome(CustomerEntity customer) {

		Document document = new Document(PageSize.A4, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(115), Utilities.millimetersToPoints(10));
		Font fontAddressHeader = new Font(FontFamily.HELVETICA, 7, Font.UNDERLINE, new BaseColor(0, 0, 0));
		Font fontAddressBody = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontGmnyClaim = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, new BaseColor(0, 0, 0));
		Font fontGmnyHead = new Font(FontFamily.HELVETICA, 13, Font.BOLD, new BaseColor(0, 0, 0));
		Font fontGmnyAddress = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontGmnyAddressSmall = new Font(FontFamily.HELVETICA, 9, Font.ITALIC, new BaseColor(0, 0, 0));
		
		Font fontText = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontTextBold = new Font(FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(0, 0, 0));
		
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(customer.getId() + "_" + customer.getLastname() + ".pdf"));
            document.open();
        
            PdfContentByte canvas = writer.getDirectContent();

			
    		
			Phrase senderAddress = new Phrase();
			senderAddress.add(new Chunk("GMNY direct AG | Lothar-Matthäus-Platz 2 | 48149 Münster", fontAddressHeader));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, senderAddress, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(247), 0);		
            
	        Phrase receiverAddressGender = new Phrase();
	        receiverAddressGender.add(new Chunk("Mr.", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressGender, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(240), 0);
	        
	        Phrase receiverAddressName = new Phrase();
	        receiverAddressName.add(new Chunk(customer.getFirstname(), fontAddressBody));
	        receiverAddressName.add(new Chunk(" ", fontAddressBody));
	        receiverAddressName.add(new Chunk(customer.getLastname(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressName, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(236), 0);
	        
	        Phrase receiverAddressStreet = new Phrase();
	        receiverAddressStreet.add(new Chunk(customer.getStreet(), fontAddressBody));
	        receiverAddressStreet.add(new Chunk(" ", fontAddressBody));
	        receiverAddressStreet.add(new Chunk(customer.getStreetNumber(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressStreet, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(232), 0);
            
	        Phrase receiverAddressCity = new Phrase();
	        receiverAddressCity.add(new Chunk(customer.getZipCode(), fontAddressBody));
	        receiverAddressCity.add(new Chunk(" ", fontAddressBody));
	        receiverAddressCity.add(new Chunk(customer.getCity(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressCity, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(228), 0);
                 
	        Phrase gmnyClaim = new Phrase();
	        gmnyClaim.add(new Chunk("\"You can count on us\"", fontGmnyClaim));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, gmnyClaim, Utilities.millimetersToPoints(164), Utilities.millimetersToPoints(235), 0);
	        
	        Phrase gmnyHead = new Phrase();
	        gmnyHead.add(new Chunk("GMNY direct AG", fontGmnyHead));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyHead, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(224), 0);
	        
	        Phrase gmnyStreet = new Phrase();
	        gmnyStreet.add(new Chunk("Lothar-Matthäus-Platz 2", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyStreet, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(218), 0);
	        
	        Phrase gmnyCity = new Phrase();
	        gmnyCity.add(new Chunk("48149 Münster", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyCity, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(213), 0);
	        
	        Phrase gmnyTel = new Phrase();
	        gmnyTel.add(new Chunk("phone   ", fontGmnyAddressSmall));
	        gmnyTel.add(new Chunk("+49 (0) 251 17 06 18", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyTel, Utilities.millimetersToPoints(147), Utilities.millimetersToPoints(206), 0);
	        
	        Phrase gmnyFax = new Phrase();
	        gmnyFax.add(new Chunk("fax        ", fontGmnyAddressSmall));
	        gmnyFax.add(new Chunk("+49 (0) 251 17 06 19", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyFax, Utilities.millimetersToPoints(147), Utilities.millimetersToPoints(201), 0);
	        
	        Phrase gmnyEmail = new Phrase();
	        gmnyEmail.add(new Chunk("e-mail   ", fontGmnyAddressSmall));
	        gmnyEmail.add(new Chunk("info@gmny.com", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyEmail, Utilities.millimetersToPoints(147), Utilities.millimetersToPoints(194), 0);
	        
	        Calendar cal = Calendar.getInstance();
	        String datef = ""+cal.get(Calendar.YEAR) + ", " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + cal.get(Calendar.DAY_OF_MONTH);

	        
	        Phrase gmnyDate = new Phrase();
	        gmnyDate.add(new Chunk(datef, fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyDate, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(185), 0);
	        
	        URL logoSource = new URL("http://wemmer.ch/GMNY/logo.png");
	        Image logo = Image.getInstance(logoSource);
	        logo.scaleToFit(Utilities.millimetersToPoints(43), Utilities.millimetersToPoints(43));
	        logo.setAbsolutePosition(Utilities.millimetersToPoints(142), Utilities.millimetersToPoints(240));
	        document.add(logo);
	        
	        document.add(new Paragraph("Your customer ID: " + customer.getId() + " (please state your customer ID whenever you contact us)", fontTextBold));
	        document.add(new Paragraph(" ", fontText));
	        document.add(new Paragraph(" ", fontText));
	        document.add(new Paragraph("Dear Mr. " + customer.getLastname(), fontText));
	        document.add(new Paragraph(" ", fontText));
	        document.add(new Paragraph("We are pleased to inform you that your loan request has been accepted. We are willing to make the loan according to the terms and conditions set out below (the \"Loan\").", fontText));
	        
            document.close();
            System.out.println("PDF PDF PDF");

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
