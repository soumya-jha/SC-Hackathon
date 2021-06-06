package com.hackerrank.sample.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerationUtil {

	public static void generatePdf(AtomicInteger minBalanceCount, AtomicInteger noOfNewAccounts,
			AtomicInteger noOfAccountWithMaxDeposit) {

		Document document = new Document();

		try {
			File filepath = new File("C:/temp");
			if (!filepath.exists()) {
				new File("C:/temp").mkdir();
			}
			File file = new File("C:/temp/ReportPdf.pdf");
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/temp/ReportPdf.pdf"));
			document.open();

			PdfPTable headerTable = new PdfPTable(1);

			PdfPCell header = new PdfPCell();
			header.setBorderColor(BaseColor.BLACK);
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(1);
			header.setPhrase(new Phrase("Account Management Report"));
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerTable.addCell(header);

			document.add(headerTable);

			addTables(document, "Number of new accounts created", noOfNewAccounts);
			addTables(document, "Number of accounts with minimum balance", minBalanceCount);
			addTables(document, "Number of accounts with maximum deposit", noOfAccountWithMaxDeposit);

			writer.close();

		} catch (DocumentException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			document.close();
		}
	}

	private static void addTables(Document document, String title, AtomicInteger value) throws DocumentException {

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);

		float[] columnWidths = { 1f, 1f };
		table.setWidths(columnWidths);

		PdfPCell cell1 = new PdfPCell(new Paragraph(title));
		cell1.setBorderColor(BaseColor.BLACK);
		cell1.setPaddingLeft(10);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

		PdfPCell cell2 = new PdfPCell(new Paragraph(value.toString()));
		cell2.setBorderColor(BaseColor.BLACK);
		cell2.setPaddingLeft(10);
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

		table.addCell(cell1);
		table.addCell(cell2);

		document.add(table);
	}
}