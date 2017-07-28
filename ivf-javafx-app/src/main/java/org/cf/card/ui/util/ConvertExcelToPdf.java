package org.cf.card.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cf.card.dto.CompanyDto;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.cf.card.util.EnumPermission.Module;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertExcelToPdf {

	// Find out number of columns in the excel
	private static int numberOfColumns;

	// private static String FILE =
	// "C:/Users/insonix/Desktop/UnapaidOverall.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font tableHeaderRowFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font tableDataRowFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

	/*
	 * public static void main(String[] args) {
	 * 
	 * String excelFile =
	 * "C:\\Users\\insonix\\Documents\\UnapaidOverall_(06-02-17_17-44-06).xlsx";
	 * try { // readExcelSheet(excelFile); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	private static double totalBill;
	private static double totalPaid;
	private static double totalBalance;
	
	private static double totalRefundBill;

	/**
	 * Read excel sheet.
	 *
	 * @param excelFilePath
	 *            the excel file path
	 * @param pdfFileName
	 *            the pdf file name
	 * @param pdfPageHeader
	 *            the pdf page header
	 * @param pdfMetaData
	 *            the pdf meta data
	 * @param columnWidths
	 *            the column widths
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 */
	public static void readExcelSheet(String excelFilePath, String pdfFileName, String pdfPageHeader,
			List<String> pdfMetaData, float[] columnWidths, int moduleKey) throws IOException, DocumentException {
		new ConvertExcelToPdf().resetFields();
		File xlsFile = new File(excelFilePath);
		Workbook workbook;
		try {
			workbook = loadSpreadSheet(xlsFile);
			Document document = new Document(PageSize.A4.rotate());
			PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
			document.open();
			addMetaData(document, pdfMetaData);
			// addTitlePage(document);
			Anchor anchor = new Anchor(pdfPageHeader, catFont);
			// Second parameter is the number of the chapter
			Chapter catPart = new Chapter(new Paragraph(anchor), 1);
			Paragraph subPara = new Paragraph("Table", subFont);
			Section subCatPart = catPart.addSection(subPara);
			addEmptyLine(subPara, 2);
			Sheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			int temp = 0;
			PdfPTable table = new PdfPTable(columnWidths);

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				int cellNumber = 0;
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (temp == 0) {
							numberOfColumns = row.getLastCellNum();
							PdfPCell c1 = new PdfPCell(new Phrase(cell.getStringCellValue(), tableHeaderRowFont));
							c1.setBackgroundColor(GrayColor.LIGHT_GRAY);
							c1.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c1.setColspan(3);
							table.addCell(c1);
							table.setHeaderRows(1);
						} else {
							cellNumber = new ConvertExcelToPdf().checkEmptyCellAndAddCellContentToPDFTable(cellNumber,
									cell, table, moduleKey);
						}
						cellNumber++;
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellNumber = new ConvertExcelToPdf().checkEmptyCellAndAddCellContentToPDFTable(cellNumber, cell,
								table, moduleKey);
						cellNumber++;
						break;
					}
				}
				temp = 1;
				if (numberOfColumns != cellNumber) {
					for (int i = 0; i < (numberOfColumns - cellNumber); i++) {
						table.addCell(" ");
					}
				}

			}
			subCatPart.add(table);
			// Now add all this to the document
			document.add(catPart);
			if(Module.UNPAID_BILL_OVERALL.getKey() == moduleKey){
				// adding Overall Amount table
				document.add(new ConvertExcelToPdf().createTableForOverallUnpaidBillAmount());
			}else {
				document.add(new ConvertExcelToPdf().createTableForOverallRefundBillAmount());
			}
			
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates the table for overall UnpaidBill amount.
	 *
	 * @return the paragraph
	 * @throws BadElementException
	 *             the bad element exception
	 */
	private Paragraph createTableForOverallUnpaidBillAmount() throws BadElementException {
		CompanyDto companyDto = null;
		@SuppressWarnings("unchecked")
		SessionObject<String, CompanyDto> sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		Paragraph overallAmountParagraph = new Paragraph("Overall Amount", subFont);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 2);
		PdfPTable table = new PdfPTable(3);

		PdfPCell c1 = new PdfPCell(new Phrase("Overall Bill", tableHeaderRowFont));
		c1.setBackgroundColor(GrayColor.LIGHT_GRAY);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Overall Paid", tableHeaderRowFont));
		c1.setBackgroundColor(GrayColor.LIGHT_GRAY);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Overall Balance", tableHeaderRowFont));
		c1.setBackgroundColor(GrayColor.LIGHT_GRAY);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		PdfPCell overallBillValue = new PdfPCell(
				new Phrase(Util.getValueUpto2Decimal((float) totalBill), tableDataRowFont));
		overallBillValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell overallPaidValue = new PdfPCell(
				new Phrase(Util.getValueUpto2Decimal((float) totalPaid), tableDataRowFont));
		overallPaidValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell overallBalanceValue = new PdfPCell(
				new Phrase(Util.getValueUpto2Decimal((float) totalBalance), tableDataRowFont));
		overallBalanceValue.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.addCell(
				IConstants.WHITE_SPACE + companyDto.getBillingCurrency() + overallBillValue.getPhrase().getContent());
		table.addCell(
				IConstants.WHITE_SPACE + companyDto.getBillingCurrency() + overallPaidValue.getPhrase().getContent());
		table.addCell(IConstants.WHITE_SPACE + companyDto.getBillingCurrency()
				+ overallBalanceValue.getPhrase().getContent());

		overallAmountParagraph.add(table);
		return overallAmountParagraph;

	}
	
	
	
	/**
	 * Creates the table for overall RefundBill amount.
	 *
	 * @return the paragraph
	 * @throws BadElementException
	 *             the bad element exception
	 */
	private Paragraph createTableForOverallRefundBillAmount() throws BadElementException {
		CompanyDto companyDto = null;
		@SuppressWarnings("unchecked")
		SessionObject<String, CompanyDto> sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		Paragraph overallAmountParagraph = new Paragraph("Overall Amount", subFont);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 2);
		PdfPTable table = new PdfPTable(1);

		PdfPCell c1 = new PdfPCell(new Phrase("Overall Refund Bill", tableHeaderRowFont));
		c1.setBackgroundColor(GrayColor.LIGHT_GRAY);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		PdfPCell overallRefundBillValue = new PdfPCell(
				new Phrase(Util.getValueUpto2Decimal((float) totalRefundBill), tableDataRowFont));
		overallRefundBillValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(
				IConstants.WHITE_SPACE + companyDto.getBillingCurrency() + overallRefundBillValue.getPhrase().getContent());
		overallAmountParagraph.add(table);
		return overallAmountParagraph;

	}


	private static int checkEmptyCellAndAddCellContentToPDFTable(int cellNumber, Cell cell, PdfPTable table, int moduleKey) {

		if (cellNumber == cell.getColumnIndex()) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (String.valueOf(cell.getNumericCellValue()).contains("E")) {
					PdfPCell c1 = new PdfPCell(
							new Phrase(new BigDecimal(cell.getNumericCellValue()).toPlainString(), tableDataRowFont));
					c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(c1);
				} else {
					if (cellNumber == 0) {
						long l = (long) cell.getNumericCellValue();
						PdfPCell c1 = new PdfPCell(new Phrase(String.valueOf(l), tableDataRowFont));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
					} else if (cellNumber == 2) {
						if(Module.UNPAID_BILL_OVERALL.getKey() == moduleKey){
							double totalBillOfCell = cell.getNumericCellValue();
							totalBill = totalBill + totalBillOfCell;
						}else {
							double totalRefundBillOfCell = cell.getNumericCellValue();
							totalRefundBill = totalRefundBill + totalRefundBillOfCell;
						}
						
						PdfPCell c1 = new PdfPCell(
								new Phrase(Double.toString(cell.getNumericCellValue()), tableDataRowFont));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
					} else if (cellNumber == 3) {
						double totalPaidBillOfCell = cell.getNumericCellValue();
						totalPaid = totalPaid + totalPaidBillOfCell;
						PdfPCell c1 = new PdfPCell(
								new Phrase(Double.toString(cell.getNumericCellValue()), tableDataRowFont));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);

					} else if (cellNumber == 4) {
						double totalBalanceOfCell = cell.getNumericCellValue();
						totalBalance = totalBalance + totalBalanceOfCell;
						PdfPCell c1 = new PdfPCell(
								new Phrase(Double.toString(cell.getNumericCellValue()), tableDataRowFont));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);

					} else {
						PdfPCell c1 = new PdfPCell(
								new Phrase(Double.toString(cell.getNumericCellValue()), tableDataRowFont));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
					}

				}
			}
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				PdfPCell c1 = new PdfPCell(new Phrase(cell.getStringCellValue(), tableDataRowFont));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
			}

		} else {
			while (cellNumber < cell.getColumnIndex()) {
				table.addCell(" ");
				cellNumber++;
			}
			if (cellNumber == cell.getColumnIndex()) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					table.addCell(Double.toString(cell.getNumericCellValue()));
				}
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					table.addCell(cell.getStringCellValue());
				}
			}
			cellNumber = cell.getColumnIndex();
		}
		return cellNumber;
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document, List<String> pdfMetaData) {
		if (null != pdfMetaData && pdfMetaData.size() > 0) {
			for (int i = 0; i < pdfMetaData.size(); i++) {
				switch (i) {
				case 0:
					document.addTitle(pdfMetaData.get(i));
					break;
				case 1:
					document.addSubject(pdfMetaData.get(i));
					break;
				case 2:
					document.addKeywords(pdfMetaData.get(i));
					break;
				case 3:
					document.addAuthor(pdfMetaData.get(i));
					break;
				case 4:
					document.addCreator(pdfMetaData.get(i));
					break;
				default:
					break;
				}

			}
		}
	}

	/**
	 * Adds the empty line.
	 *
	 * @param paragraph
	 *            the paragraph
	 * @param number
	 *            the number
	 */
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	/**
	 * Load spread sheet.
	 *
	 * @param xlsFile
	 *            the xls file
	 * @return the workbook
	 * @throws Exception
	 *             the exception
	 */
	private static Workbook loadSpreadSheet(File xlsFile) throws Exception {
		Workbook workbook = null;
		String ext = getFileExtension(xlsFile.getName());
		if (ext.equalsIgnoreCase("xlsx")) {
			OPCPackage pkg = OPCPackage.open(xlsFile.getAbsolutePath());
			workbook = new XSSFWorkbook(pkg);
			pkg.close();
		} else if (ext.equalsIgnoreCase("xls")) {
			InputStream xlsFIS = new FileInputStream(xlsFile);
			workbook = new HSSFWorkbook(xlsFIS);
			xlsFIS.close();
		} else {
			throw new Exception("FILE EXTENSION NOT RECOGNIZED");
		}
		return workbook;
	}

	/**
	 * Gets the file extension.
	 *
	 * @param fileName
	 *            the file name
	 * @return the file extension
	 */
	private static String getFileExtension(String fileName) {
		String ext = IConstants.emptyString;
		int mid = fileName.lastIndexOf(".");
		ext = fileName.substring(mid + 1, fileName.length());
		System.out.println("File Extension --" + ext);
		return ext;
	}

	private void resetFields() {
		totalBill = 0;
		totalPaid = 0;
		totalBalance = 0;
		totalRefundBill = 0;

	}

}
