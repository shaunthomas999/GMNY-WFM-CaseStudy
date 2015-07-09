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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.Line;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.lang.Math;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.getstarted.gmny.model.CustomerEntity;
import org.camunda.bpm.getstarted.gmny.model.LoanEntity;
import org.camunda.bpm.getstarted.gmny.ejb.LoanServiceBean;

@Stateless
public class PdfServiceBean {
	
	public static void createPrivateLoanContract(CustomerEntity customer, LoanEntity loan) {

		Document document = new Document(PageSize.A4, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(25), Utilities.millimetersToPoints(10));
		Font fontAddressHeader = new Font(FontFamily.HELVETICA, 8, Font.UNDERLINE, new BaseColor(0, 0, 0));
		Font fontAddressBody = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontGmnyClaim = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, new BaseColor(0, 0, 0));
		Font fontGmnyHead = new Font(FontFamily.HELVETICA, 13, Font.BOLD, new BaseColor(0, 0, 0));
		Font fontGmnyAddress = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontGmnyAddressSmall = new Font(FontFamily.HELVETICA, 9, Font.ITALIC, new BaseColor(0, 0, 0));
		
		Font fontHeadline = new Font(FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(0, 0, 0));
		Font fontText = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontTextBold = new Font(FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(0, 0, 0));
		
		Font fontTextUnderline = new Font(FontFamily.HELVETICA, 11, Font.UNDERLINE, new BaseColor(0, 0, 0));
		
		Font fontTextMassive = new Font(FontFamily.HELVETICA, 22, Font.BOLD, new BaseColor(0, 0, 0));
		
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(customer.getId() + "_" + customer.getLastname() + ".pdf"));
            document.open();
        
            PdfContentByte canvas = writer.getDirectContent();

			
    		
			Phrase senderAddress = new Phrase();
			senderAddress.add(new Chunk("GMNY direct AG | Lothar-Matthaeus-Platz 2 | 48149 Muenster", fontAddressHeader));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, senderAddress, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(247), 0);		
            
	        Phrase receiverAddressGender = new Phrase();
	        receiverAddressGender.add(new Chunk(customer.getGender(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressGender, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(240), 0);
	        
	        Phrase receiverAddressName = new Phrase();
	        receiverAddressName.add(new Chunk(customer.getFirstname(), fontAddressBody));
	        receiverAddressName.add(new Chunk(" ", fontAddressBody));
	        receiverAddressName.add(new Chunk(customer.getLastname(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressName, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(236), 0);
	        
	        Phrase receiverAddressStreet = new Phrase();
	        receiverAddressStreet.add(new Chunk(customer.getStreet(), fontAddressBody));
	        receiverAddressStreet.add(new Chunk(" ", fontAddressBody));
	        receiverAddressStreet.add(new Chunk(customer.getStreetNumber(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressStreet, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(232), 0);
            
	        Phrase receiverAddressCity = new Phrase();
	        receiverAddressCity.add(new Chunk(customer.getZipCode(), fontAddressBody));
	        receiverAddressCity.add(new Chunk(" ", fontAddressBody));
	        receiverAddressCity.add(new Chunk(customer.getCity(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressCity, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(228), 0);
                 
	        Phrase gmnyClaim = new Phrase();
	        gmnyClaim.add(new Chunk("\"You can count on us\"", fontGmnyClaim));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, gmnyClaim, Utilities.millimetersToPoints(164), Utilities.millimetersToPoints(235), 0);
	        
	        Phrase gmnyHead = new Phrase();
	        gmnyHead.add(new Chunk("GMNY direct AG", fontGmnyHead));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyHead, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(224), 0);
	        
	        Phrase gmnyStreet = new Phrase();
	        gmnyStreet.add(new Chunk("Lothar-Matthaeus-Platz 2", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyStreet, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(218), 0);
	        
	        Phrase gmnyCity = new Phrase();
	        gmnyCity.add(new Chunk("48149 Muenster", fontGmnyAddress));
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
	        String dateg = ""+""+cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
	        
	        Phrase gmnyDate = new Phrase();
	        gmnyDate.add(new Chunk(datef, fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyDate, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(185), 0);
	        
	        URL logoSource = new URL("http://wemmer.ch/GMNY/logo.png");
	        Image logo = Image.getInstance(logoSource);
	        logo.scaleToFit(Utilities.millimetersToPoints(43), Utilities.millimetersToPoints(43));
	        logo.setAbsolutePosition(Utilities.millimetersToPoints(142), Utilities.millimetersToPoints(240));
	        document.add(logo);

	        Paragraph space = new Paragraph(Utilities.millimetersToPoints(115));
	        document.add(space);
	        
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("PRIVATE LOAN AGREEMENT / " + loan.getFinancialProduct().getProductName().toUpperCase(), fontHeadline));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Your customer ID: " + customer.getId(), fontTextBold));
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Dear " + customer.getGender() +" " + customer.getLastname(), fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(new Phrase("We are pleased to inform you that your loan request has been accepted. We are willing to make the loan according to the terms and conditions set out below. Please mail or fax us the signed agreement as soon as possible. If there are any questions left do not hesitate to contact us.", fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Sincerely yours,", fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("GMNY loan office", fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Please find attached:", fontTextUnderline));
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph(" - Private Loan Agreement", fontText));
	        
	        //Contract
	        
	        document.add(Chunk.NEXTPAGE);
	        
	        Paragraph hp0 = new Paragraph("PRIVATE LOAN AGREEMENT", fontTextMassive);
	        hp0.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp0);
	        
	        canvas.setLineWidth(2.0f);
	        canvas.moveTo(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(255));
	        canvas.lineTo(Utilities.millimetersToPoints(190), Utilities.millimetersToPoints(255));
	        canvas.stroke();
	        
	        document.add(Chunk.NEWLINE);
	        
	        document.add(new Phrase("The Private loan Agreement (\"The agreement\") shall be governed by the laws of the Federal Republic of Germany. The agreement is made and will be effective on ", fontText));
	        document.add(new Phrase(dateg, fontTextBold));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp1 = new Paragraph("B E T W E E N", fontHeadline);
	        hp1.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp1);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp2 = new Paragraph(customer.getFirstname() + " " + customer.getLastname() + ", " + customer.getStreet() + " " + customer.getStreetNumber() + ", " + customer.getZipCode() + " " + customer.getCity() + "   (\"The Borrower\")", fontTextBold);
	        hp2.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp2);
	        
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp3 = new Paragraph("A N D", fontHeadline);
	        hp3.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp3);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp4 = new Paragraph("GMNY direct AG, Lothar-Matthaeus-Platz 2, 48149 Muenster   (\"The Lender\").", fontTextBold);
	        hp4.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp4);
	        
	        canvas.setLineWidth(1.0f);
	        canvas.moveTo(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(180));
	        canvas.lineTo(Utilities.millimetersToPoints(190), Utilities.millimetersToPoints(180));
	        canvas.stroke();
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp5 = new Paragraph("TERMS AND CONDITIONS", fontTextBold);
	        hp5.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp5);
	        
	        canvas.moveTo(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(174));
	        canvas.lineTo(Utilities.millimetersToPoints(190), Utilities.millimetersToPoints(174));
	        canvas.stroke();
	        
	        document.add(Chunk.NEWLINE);
	       
	        document.add(new Phrase("The Lender hereby promises that the borrowed amount is to be paid within ", fontText));
	        document.add(new Phrase("14 business days", fontTextBold));
	        document.add(new Phrase(" after the signed agreement has been received.", fontText));
	        
	        document.add(Chunk.NEWLINE);
	        
	        document.add(new Phrase("The Borrower hereby promises to clear the loan amounting to ", fontText));
	        document.add(new Phrase("EUR " + loan.getAmount() + ",-", fontTextBold));
	        document.add(new Phrase(" including interests and other charges. Both parties intend to be legally bound to the following repayment schedule:", fontText));
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        
	        LoanServiceBean loanServiceBean = new LoanServiceBean();
	
	        
	        document.add(new Phrase("The Borrower agrees to repay the amount of ", fontText));
	        document.add(new Phrase("EUR " + loan.getAmount() + ",-", fontTextBold));
	        document.add(new Phrase(" in equal monthly installments (\"fixed-rate mortgage\") of ", fontText));
	        document.add(new Phrase("EUR " + loanServiceBean.getAnnuity(loan) + ",-", fontTextBold));
	        document.add(new Phrase(" due on the last week of each month. ", fontText));
	        document.add(new Phrase("The chosen product ", fontText));
	        document.add(new Phrase(loan.getFinancialProduct().getProductName().toUpperCase(), fontTextBold));
	        document.add(new Phrase(" commits the borrower to start with loan redemption ", fontText));
	        document.add(new Phrase("IN JANUARY OF THE UPCOMING YEAR", fontTextBold));
	        document.add(new Phrase(" after signing this agreement. ", fontText));
	        document.add(new Phrase(" The Borrower and the Lender agree on an ", fontText));
	        document.add(new Phrase("annual interest rate of " + loanServiceBean.getInterestRate(loan) + " %", fontTextBold));
	        document.add(new Phrase(" for the whole amortisation term of ", fontText));
	        document.add(new Phrase(loanServiceBean.getPeriodInMonths(loan) + " MONTHS", fontTextBold));
	        document.add(new Phrase(".", fontText));
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        PdfPTable table = new PdfPTable(2);

            PdfPCell cell1 = new PdfPCell(new Paragraph("AMOUNT:", fontTextBold));
            PdfPCell cell2 = new PdfPCell(new Paragraph("EUR "+loan.getAmount() + ",-", fontTextBold));

            table.addCell(cell1);
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Paragraph("AMORTISATION TERM:", fontTextBold));
            PdfPCell cell4 = new PdfPCell(new Paragraph(loanServiceBean.getPeriodInMonths(loan) + " MONTHS", fontTextBold));
            
            table.addCell(cell3);
            table.addCell(cell4);
            
            PdfPCell cell5 = new PdfPCell(new Paragraph("INTEREST RATE:", fontTextBold));
            PdfPCell cell6 = new PdfPCell(new Paragraph(loanServiceBean.getInterestRate(loan) + " % per annum", fontTextBold));
            
            table.addCell(cell5);
            table.addCell(cell6);
            
            PdfPCell cell7 = new PdfPCell(new Paragraph("MONTHLY INSTALLMENTS:", fontTextBold));
            PdfPCell cell8 = new PdfPCell(new Paragraph("EUR " + loanServiceBean.getAnnuity(loan) + ",-", fontTextBold));
            
            table.addCell(cell7);
            table.addCell(cell8);
            
            PdfPCell cell9 = new PdfPCell(new Paragraph("CHARGE:", fontTextBold));
            PdfPCell cell10 = new PdfPCell(new Paragraph("NO CHARGE", fontTextBold));
            
            table.addCell(cell9);
            table.addCell(cell10);

            document.add(table);
            
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
            
	        document.add(new Phrase("If the Borrower is unable make any installment on time, the Borrower shall be in default. In that case the Lender can then demand the payment of the entire remaining unpaid balance of this loan.", fontText));
            
	        canvas.moveTo(Utilities.millimetersToPoints(40), Utilities.millimetersToPoints(35));
	        canvas.lineTo(Utilities.millimetersToPoints(90), Utilities.millimetersToPoints(35));
	        canvas.stroke();
	        
	        Phrase sign1 = new Phrase();
	        sign1.add(new Chunk("- " + customer.getFirstname() + " " + customer.getLastname() + " (\"The Borrower\") -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, sign1, Utilities.millimetersToPoints(40), Utilities.millimetersToPoints(30), 0);
	        
	        canvas.moveTo(Utilities.millimetersToPoints(120), Utilities.millimetersToPoints(35));
	        canvas.lineTo(Utilities.millimetersToPoints(170), Utilities.millimetersToPoints(35));
	        canvas.stroke();
	        
	        Phrase sign2 = new Phrase();
	        sign2.add(new Chunk("- GMNY direct AG (\"The Lender\") -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, sign2, Utilities.millimetersToPoints(117), Utilities.millimetersToPoints(30), 0);
	        
	        Phrase pageNumber = new Phrase();
	        pageNumber.add(new Chunk("- Page 1/1 -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, pageNumber, Utilities.millimetersToPoints(95), Utilities.millimetersToPoints(8), 0);
	        
	      
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
	
	
	public static void createBusinessLoanContract(CustomerEntity customer, LoanEntity loan) {

		Document document = new Document(PageSize.A4, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(25), Utilities.millimetersToPoints(10));
		Font fontAddressHeader = new Font(FontFamily.HELVETICA, 8, Font.UNDERLINE, new BaseColor(0, 0, 0));
		Font fontAddressBody = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontGmnyClaim = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, new BaseColor(0, 0, 0));
		Font fontGmnyHead = new Font(FontFamily.HELVETICA, 13, Font.BOLD, new BaseColor(0, 0, 0));
		Font fontGmnyAddress = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontGmnyAddressSmall = new Font(FontFamily.HELVETICA, 9, Font.ITALIC, new BaseColor(0, 0, 0));
		
		Font fontHeadline = new Font(FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(0, 0, 0));
		Font fontText = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
		Font fontTextBold = new Font(FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(0, 0, 0));
		
		Font fontTextUnderline = new Font(FontFamily.HELVETICA, 11, Font.UNDERLINE, new BaseColor(0, 0, 0));
		
		Font fontTextMassive = new Font(FontFamily.HELVETICA, 22, Font.BOLD, new BaseColor(0, 0, 0));
		
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(customer.getId() + "_" + customer.getLastname() + ".pdf"));
            document.open();
        
            PdfContentByte canvas = writer.getDirectContent();

			
    		
			Phrase senderAddress = new Phrase();
			senderAddress.add(new Chunk("GMNY direct AG | Lothar-Matthaeus-Platz 2 | 48149 Muenster", fontAddressHeader));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, senderAddress, Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(247), 0);		
            
	        Phrase receiverAddressGender = new Phrase();
	        receiverAddressGender.add(new Chunk(customer.getGender(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressGender, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(240), 0);
	        
	        Phrase receiverAddressName = new Phrase();
	        receiverAddressName.add(new Chunk(customer.getFirstname(), fontAddressBody));
	        receiverAddressName.add(new Chunk(" ", fontAddressBody));
	        receiverAddressName.add(new Chunk(customer.getLastname(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressName, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(236), 0);
	        
	        Phrase receiverCompanyName = new Phrase();
	        receiverCompanyName.add(new Chunk("c/o", fontAddressBody));
	        receiverCompanyName.add(new Chunk(" ", fontAddressBody));
	        receiverCompanyName.add(new Chunk(customer.getOrgName(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverCompanyName, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(232), 0);
	        
	        Phrase receiverAddressStreet = new Phrase();
	        receiverAddressStreet.add(new Chunk(customer.getStreet(), fontAddressBody));
	        receiverAddressStreet.add(new Chunk(" ", fontAddressBody));
	        receiverAddressStreet.add(new Chunk(customer.getStreetNumber(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressStreet, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(228), 0);
            
	        Phrase receiverAddressCity = new Phrase();
	        receiverAddressCity.add(new Chunk(customer.getZipCode(), fontAddressBody));
	        receiverAddressCity.add(new Chunk(" ", fontAddressBody));
	        receiverAddressCity.add(new Chunk(customer.getCity(), fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, receiverAddressCity, Utilities.millimetersToPoints(22), Utilities.millimetersToPoints(224), 0);
                 
	        Phrase gmnyClaim = new Phrase();
	        gmnyClaim.add(new Chunk("\"You can count on us\"", fontGmnyClaim));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, gmnyClaim, Utilities.millimetersToPoints(164), Utilities.millimetersToPoints(235), 0);
	        
	        Phrase gmnyHead = new Phrase();
	        gmnyHead.add(new Chunk("GMNY direct AG", fontGmnyHead));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyHead, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(224), 0);
	        
	        Phrase gmnyStreet = new Phrase();
	        gmnyStreet.add(new Chunk("Lothar-Matthaeus-Platz 2", fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyStreet, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(218), 0);
	        
	        Phrase gmnyCity = new Phrase();
	        gmnyCity.add(new Chunk("48149 Muenster", fontGmnyAddress));
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
	        String dateg = ""+""+cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
	        
	        Phrase gmnyDate = new Phrase();
	        gmnyDate.add(new Chunk(datef, fontGmnyAddress));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gmnyDate, Utilities.millimetersToPoints(145), Utilities.millimetersToPoints(185), 0);
	        
	        URL logoSource = new URL("http://wemmer.ch/GMNY/logo.png");
	        Image logo = Image.getInstance(logoSource);
	        logo.scaleToFit(Utilities.millimetersToPoints(43), Utilities.millimetersToPoints(43));
	        logo.setAbsolutePosition(Utilities.millimetersToPoints(142), Utilities.millimetersToPoints(240));
	        document.add(logo);

	        Paragraph space = new Paragraph(Utilities.millimetersToPoints(115));
	        document.add(space);
	        
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("BUSINESS LOAN AGREEMENT / " + loan.getFinancialProduct().getProductName().toUpperCase(), fontHeadline));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Your customer ID: " + customer.getId(), fontTextBold));
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Dear " + customer.getGender() +" " + customer.getLastname(), fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(new Phrase("We are pleased to inform you that the loan request of " + customer.getOrgName() + " has been accepted. We are willing to make the loan according to the terms and conditions set out below. Please mail or fax us the signed agreement as soon as possible. If there are any questions left do not hesitate to contact us.", fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Sincerely yours,", fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("GMNY loan office", fontText));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Please find attached:", fontTextUnderline));
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph(" - Business Loan Agreement", fontText));
	        
	        //Contract
	        
	        document.add(Chunk.NEXTPAGE);
	        
	        Paragraph hp0 = new Paragraph("BUSINESS LOAN AGREEMENT", fontTextMassive);
	        hp0.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp0);
	        
	        canvas.setLineWidth(2.0f);
	        canvas.moveTo(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(255));
	        canvas.lineTo(Utilities.millimetersToPoints(190), Utilities.millimetersToPoints(255));
	        canvas.stroke();
	        
	        document.add(Chunk.NEWLINE);
	        
	        document.add(new Phrase("The Business loan Agreement (\"The agreement\") shall be governed by the laws of the Federal Republic of Germany. The agreement is made and will be effective on ", fontText));
	        document.add(new Phrase(dateg, fontTextBold));
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp1 = new Paragraph("B E T W E E N", fontHeadline);
	        hp1.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp1);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp2 = new Paragraph(customer.getOrgName() + ", " + customer.getStreet() + " " + customer.getStreetNumber() + ", " + customer.getZipCode() + " " + customer.getCity() + "   (\"The Borrower\")", fontTextBold);
	        hp2.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp2);
	        
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp3 = new Paragraph("A N D", fontHeadline);
	        hp3.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp3);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp4 = new Paragraph("GMNY direct AG, Lothar-Matthaeus-Platz 2, 48149 Muenster   (\"The Lender\").", fontTextBold);
	        hp4.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp4);
	        
	        canvas.setLineWidth(1.0f);
	        canvas.moveTo(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(180));
	        canvas.lineTo(Utilities.millimetersToPoints(190), Utilities.millimetersToPoints(180));
	        canvas.stroke();
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        Paragraph hp5 = new Paragraph("TERMS AND CONDITIONS", fontTextBold);
	        hp5.setAlignment(Element.ALIGN_CENTER);
	        document.add(hp5);
	        
	        canvas.moveTo(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(174));
	        canvas.lineTo(Utilities.millimetersToPoints(190), Utilities.millimetersToPoints(174));
	        canvas.stroke();
	        
	        document.add(Chunk.NEWLINE);
	       
	        document.add(new Phrase("The Lender hereby promises that the borrowed amount is to be paid within ", fontText));
	        document.add(new Phrase("14 business days", fontTextBold));
	        document.add(new Phrase(" after the signed agreement has been received.", fontText));
	        
	        document.add(Chunk.NEWLINE);
	        
	        document.add(new Phrase("The Borrower hereby promises to clear the loan amounting to ", fontText));
	        document.add(new Phrase("EUR " + loan.getAmount() + ",-", fontTextBold));
	        document.add(new Phrase(" including interests and other charges. Both parties intend to be legally bound to the following repayment schedule:", fontText));
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        
	        LoanServiceBean loanServiceBean = new LoanServiceBean();
	
	        
	        document.add(new Phrase("The Borrower agrees to repay the amount of ", fontText));
	        document.add(new Phrase("EUR " + loan.getAmount() + ",-", fontTextBold));
	        document.add(new Phrase(" in equal monthly installments (\"fixed-rate mortgage\") of ", fontText));
	        document.add(new Phrase("EUR " + loanServiceBean.getAnnuity(loan) + ",-", fontTextBold));
	        document.add(new Phrase(" due on the last week of each month. ", fontText));
	        document.add(new Phrase("The chosen product ", fontText));
	        document.add(new Phrase(loan.getFinancialProduct().getProductName().toUpperCase(), fontTextBold));
	        document.add(new Phrase(" commits the borrower to start with loan redemption ", fontText));
	        document.add(new Phrase("IN JANUARY OF THE UPCOMING YEAR", fontTextBold));
	        document.add(new Phrase(" after signing this agreement. ", fontText));
	        document.add(new Phrase(" The Borrower and the Lender agree on an ", fontText));
	        document.add(new Phrase("annual interest rate of " + loanServiceBean.getInterestRate(loan) + " %", fontTextBold));
	        document.add(new Phrase(" for the whole amortisation term of ", fontText));
	        document.add(new Phrase(loanServiceBean.getPeriodInMonths(loan) + " MONTHS", fontTextBold));
	        document.add(new Phrase(".", fontText));
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        PdfPTable table = new PdfPTable(2);

            PdfPCell cell1 = new PdfPCell(new Paragraph("AMOUNT:", fontTextBold));
            PdfPCell cell2 = new PdfPCell(new Paragraph("EUR "+loan.getAmount() + ",-", fontTextBold));

            table.addCell(cell1);
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Paragraph("AMORTISATION TERM:", fontTextBold));
            PdfPCell cell4 = new PdfPCell(new Paragraph(loanServiceBean.getPeriodInMonths(loan) + " MONTHS", fontTextBold));
            
            table.addCell(cell3);
            table.addCell(cell4);
            
            PdfPCell cell5 = new PdfPCell(new Paragraph("INTEREST RATE:", fontTextBold));
            PdfPCell cell6 = new PdfPCell(new Paragraph(loanServiceBean.getInterestRate(loan) + " % per annum", fontTextBold));
            
            table.addCell(cell5);
            table.addCell(cell6);
            
            PdfPCell cell7 = new PdfPCell(new Paragraph("MONTHLY INSTALLMENTS:", fontTextBold));
            PdfPCell cell8 = new PdfPCell(new Paragraph("EUR " + loanServiceBean.getAnnuity(loan) + ",-", fontTextBold));
            
            table.addCell(cell7);
            table.addCell(cell8);
            
            PdfPCell cell9 = new PdfPCell(new Paragraph("CHARGE:", fontTextBold));
            PdfPCell cell10 = new PdfPCell(new Paragraph("EUR 850,-", fontTextBold));
            
            table.addCell(cell9);
            table.addCell(cell10);

            document.add(table);
            
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
            
	        document.add(new Phrase("If the Borrower is unable make any installment on time, the Borrower shall be in default. In that case the Lender can then demand the payment of the entire remaining unpaid balance of this loan.", fontText));
            
	        canvas.moveTo(Utilities.millimetersToPoints(40), Utilities.millimetersToPoints(35));
	        canvas.lineTo(Utilities.millimetersToPoints(90), Utilities.millimetersToPoints(35));
	        canvas.stroke();
	        
	        Phrase sign1 = new Phrase();
	        sign1.add(new Chunk("- " + customer.getFirstname() + " " + customer.getLastname() + " -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, sign1, Utilities.millimetersToPoints(40), Utilities.millimetersToPoints(30), 0);
	        
	        Phrase sign1a = new Phrase();
	        sign1a.add(new Chunk("- " + customer.getOrgName() + " (\"The Borrower\") -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, sign1a, Utilities.millimetersToPoints(40), Utilities.millimetersToPoints(26), 0);
	        
	        canvas.moveTo(Utilities.millimetersToPoints(120), Utilities.millimetersToPoints(35));
	        canvas.lineTo(Utilities.millimetersToPoints(170), Utilities.millimetersToPoints(35));
	        canvas.stroke();
	        
	        Phrase sign2 = new Phrase();
	        sign2.add(new Chunk("- GMNY direct AG (\"The Lender\") -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, sign2, Utilities.millimetersToPoints(117), Utilities.millimetersToPoints(30), 0);
	        
	        Phrase pageNumber = new Phrase();
	        pageNumber.add(new Chunk("- Page 1/1 -", fontAddressBody));
	        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, pageNumber, Utilities.millimetersToPoints(95), Utilities.millimetersToPoints(8), 0);
	        
	      
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

}
