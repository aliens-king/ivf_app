package org.cf.card.ui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.Client;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.util.Constants.Extensions;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.scene.control.Alert.AlertType;

/**
 * The Class ReadWriteExcelUtils.
 *
 * @author sudhanshu
 */
public class ReadWriteExcelUtils {

	private static final Logger logger = Logger.getLogger(ReadWriteExcelUtils.class);

	/**
	 * Write to excel with XSSF for refund overall.
	 *
	 * @param refundInvoiceDtos
	 *            the refund invoice dtos
	 */
	public static void writeToExcelWithXSSFForRefundOverall(List<RefundInvoiceDto> refundInvoiceDtos , int moduleKey) {

		UIClientService clientService = new UIClientService();
		File excelFile = null;
		File pdfFile = null;
		String originalExcelFileName = null;
		String originalPdfFileName = null;
		String filecreatedDateAndTime = null;
		int count = 1;
		int sheetCounter = 1;

		// Getting path of desktop and saving Excel and PDF file there.
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		String refundBillOverallFolder = "RefundOverall_Bill";
		filecreatedDateAndTime = Util.formatDate(IConstants.DATE_WITH_TIME_WITH_UNDERSCORE,
				new Date(System.currentTimeMillis()));
		filecreatedDateAndTime = filecreatedDateAndTime.replaceAll(" ", "_");
		originalExcelFileName = Constants.EXECL_PDF_FILE_NAME_REFUND + "(" + filecreatedDateAndTime + ")"
				+ Extensions.XLSX.getStrValue();
		originalPdfFileName = Constants.EXECL_PDF_FILE_NAME_REFUND + "(" + filecreatedDateAndTime + ")"
				+ Extensions.PDF.getStrValue();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("RefundOverall");
		XSSFRow row = spreadsheet.createRow(0);

		XSSFCell cell;
		cell = row.createCell(0);
		cell.setCellValue("Sr.No");
		cell = row.createCell(1);
		cell.setCellValue("Invoice_NO.");
		cell = row.createCell(2);
		cell.setCellValue("Total_Refund");
		cell = row.createCell(3);
		cell.setCellValue("Created_By");
		cell = row.createCell(4);
		cell.setCellValue("Refund_Date");
		cell = row.createCell(5);
		cell.setCellValue("Woman_Info");

		// Setting Style to 1 row(Heading Row)
		CellStyle styles = workbook.createCellStyle();
		styles.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());// set
		// color
		styles.setFillPattern(CellStyle.ALIGN_CENTER);// Create style
		XSSFFont font = workbook.createFont();// Create font
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);// Make font bold
		styles.setFont(font);// set it to bold

		for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the
														// row
			row.setHeight((short) 400);
			row.getCell(i).setCellStyle(styles);// Set the style
		}

		ListIterator<RefundInvoiceDto> list = refundInvoiceDtos.listIterator();
		while (list.hasNext()) {
			RefundInvoiceDto refundInvoiceDto = (RefundInvoiceDto) list.next();
			row = spreadsheet.createRow(sheetCounter++);
			row.setHeight((short) 720);

			// id
			Cell cellId = row.createCell(0);
			cellId.setCellValue(count);

			// Refund Invoice Number
			Cell cellInvoiceNumber = row.createCell(1);
			cellInvoiceNumber.setCellValue(refundInvoiceDto.getRefundInvoiceNumber());
			XSSFDataFormat invoicenumberDataFormat = workbook.createDataFormat();
			CellStyle invoiceStyle = workbook.createCellStyle();
			invoiceStyle.setDataFormat(invoicenumberDataFormat.getFormat("0"));
			cellInvoiceNumber.setCellStyle(invoiceStyle);

			// TotalBIll
			Cell cellTotalBill = row.createCell(2);
			cellTotalBill.setCellValue(refundInvoiceDto.getTotalRefundBill());
			XSSFDataFormat billDataFormat = workbook.createDataFormat();
			CellStyle style = workbook.createCellStyle();
			style.setDataFormat(billDataFormat.getFormat("#,##0.00;[RED]-#,##0.00"));
			cellTotalBill.setCellStyle(style);

			// Billed By
			Cell cellCreatedBy = row.createCell(3);
			cellCreatedBy.setCellValue(refundInvoiceDto.getCreatedBy());

			// Date of refund
			Cell dateOfRefund = row.createCell(4);
			dateOfRefund.setCellValue(Util.formatDate(IConstants.DATE_FORMAT, refundInvoiceDto.getRefundCreateDate()));

			// Woman Info
			Cell coupleDetailCell = row.createCell(5);
			String clientDeatail = null;
			List<Client> clients = clientService.getClientsByCoupleId(refundInvoiceDto.getCoupleId());
			for (Client client : clients) {
				if (client.getGender().equals("Female"))
					clientDeatail = client.getFirstName() + " " + client.getSurname() + "\nPhone No: "
							+ client.getPhoneNumber() + "\nDOB: "
							+ Util.formatDate(IConstants.DATE_FORMAT, client.getdOB());
			}
			coupleDetailCell.setCellValue(clientDeatail);
			count++;
		}
		// Auto fit
		for (int i = 0; i < 8; i++) {
			spreadsheet.autoSizeColumn(i);
		}
		FileOutputStream out = null;
		try {
			excelFile = new File(desktop, refundBillOverallFolder + File.separator + originalExcelFileName);
			if (!excelFile.getParentFile().exists()) {
				excelFile.getParentFile().mkdirs();
			}
			excelFile.createNewFile();
			out = new FileOutputStream(excelFile);
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				if (excelFile.exists()) {
					pdfFile = new File(desktop, refundBillOverallFolder + File.separator + originalPdfFileName);
					pdfFile.createNewFile();
					float[] columnWidths = { 3, 5, 4, 4, 4, 6 };
					String pdfPageHeader = "Details of overall RefundBill.";
					List<String> pdfMetaData = new LinkedList<String>();
					pdfMetaData.add("RefunBill Overall");
					pdfMetaData.add("Using iText");
					pdfMetaData.add("Java, PDF, iText");
					pdfMetaData.add("Babatunde");
					pdfMetaData.add("Pramod Maurya");
					ConvertExcelToPdf.readExcelSheet(excelFile.getPath(), pdfFile.getPath(), pdfPageHeader, pdfMetaData,
							columnWidths, moduleKey);
				}
				Notify alert = new Notify(AlertType.INFORMATION);
				alert.setContentText(MessageResource.getText("file.generated"));
				alert.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Write to excel with XSSF for unpaid overall.
	 *
	 * @param billingInvoiceDtos
	 *            the billing invoice dtos
	 */
	public static void writeToExcelWithXSSFForUnpaidOverall(List<BillingInvoiceDto> billingInvoiceDtos, int moduleKey) {
		logger.info("Inside writeToExcelWithXSSFForUnpaidOverall method of ReadWriteEXcelUtils class");
		UIClientService clientService = new UIClientService();
		File excelFile = null;
		File pdfFile = null;
		String originalExcelFileName = null;
		String originalPdfFileName = null;
		String filecreatedDateAndTime = null;
		int count = 1;
		int sheetCounter = 1;

		try {

			logger.info("*****************Before get desktop path******************");
			// Getting path of desktop and saving Excel and PDF file there.
			File desktop = new File(System.getProperty("user.home"), "Desktop");
			logger.info("After get desktop path" + desktop.getPath());
			String unpaidBillOverallFolder = "UnapidOverall_Bill";
			filecreatedDateAndTime = Util.formatDate(IConstants.DATE_WITH_TIME_WITH_UNDERSCORE,
					new Date(System.currentTimeMillis()));
			filecreatedDateAndTime = filecreatedDateAndTime.replaceAll(" ", "_");
			originalExcelFileName = Constants.EXECL_PDF_FILE_NAME_UNPAID + "(" + filecreatedDateAndTime + ")"
					+ Extensions.XLSX.getStrValue();
			originalPdfFileName = Constants.EXECL_PDF_FILE_NAME_UNPAID + "(" + filecreatedDateAndTime + ")"
					+ Extensions.PDF.getStrValue();
			logger.info("***********Saving file path : " + originalExcelFileName + "*****************");
			XSSFWorkbook workbook = null;
			logger.info("XSSFWorkbook null object : " + workbook);
			try {
				workbook = new XSSFWorkbook();
				logger.info("Object of XSSFWorkbook created successfully. ");
			} catch (Throwable e) {
				logger.info("Inside catche block");
				logger.error(
						"***********Exception Message at time of create Object of  XSSFWorkbook:" + e.getMessage());
				logger.info("***********Caught exception at time of create Object of  XSSFWorkbook:" + e);
				e.printStackTrace();
			}
			logger.info("***********Got objet of XSSFWorkbook*****************");
			XSSFSheet spreadsheet = workbook.createSheet("UnpaidOverall");
			logger.info("***********Created spreadsheet*****************");
			XSSFRow row = spreadsheet.createRow(0);

			XSSFCell cell;
			cell = row.createCell(0);
			cell.setCellValue("Sr.No");
			cell = row.createCell(1);
			cell.setCellValue("Invoice_NO.");
			cell = row.createCell(2);
			cell.setCellValue("Total_Bill");
			cell = row.createCell(3);
			cell.setCellValue("Total_Paid");
			cell = row.createCell(4);
			cell.setCellValue("Total_Balance");
			cell = row.createCell(5);
			cell.setCellValue("Created_By");
			cell = row.createCell(6);
			cell.setCellValue("Invoice_Date");
			cell = row.createCell(7);
			cell.setCellValue("Woman_Info");

			// Setting Style to 1 row(Heading Row)
			CellStyle styles = workbook.createCellStyle();
			styles.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());// set
			// color
			styles.setFillPattern(CellStyle.ALIGN_CENTER);// Create style
			XSSFFont font = workbook.createFont();// Create font
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);// Make font bold
			styles.setFont(font);// set it to bold

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in
															// the
															// row
				row.setHeight((short) 400);
				row.getCell(i).setCellStyle(styles);// Set the style
			}

			ListIterator<BillingInvoiceDto> list = billingInvoiceDtos.listIterator();
			while (list.hasNext()) {
				BillingInvoiceDto billingInvoiceDto = (BillingInvoiceDto) list.next();
				row = spreadsheet.createRow(sheetCounter++);
				row.setHeight((short) 720);

				// id
				Cell cellId = row.createCell(0);
				cellId.setCellValue(count);

				// Invoice Number
				Cell cellInvoiceNumber = row.createCell(1);
				cellInvoiceNumber.setCellValue(billingInvoiceDto.getInvoiceNumber());
				XSSFDataFormat invoicenumberDataFormat = workbook.createDataFormat();
				CellStyle invoiceStyle = workbook.createCellStyle();
				invoiceStyle.setDataFormat(invoicenumberDataFormat.getFormat("0"));
				cellInvoiceNumber.setCellStyle(invoiceStyle);

				// TotalBIll
				Cell cellTotalBill = row.createCell(2);
				cellTotalBill.setCellValue(billingInvoiceDto.getTotalBill());
				XSSFDataFormat billDataFormat = workbook.createDataFormat();
				CellStyle style = workbook.createCellStyle();
				style.setDataFormat(billDataFormat.getFormat("#,##0.00;[RED]-#,##0.00"));
				cellTotalBill.setCellStyle(style);

				// Total Paid
				Cell cellTotalPaid = row.createCell(3);
				cellTotalPaid.setCellValue(billingInvoiceDto.getTotalPaid());
				XSSFDataFormat totalPaidFormat = workbook.createDataFormat();
				CellStyle totalPaidStyle = workbook.createCellStyle();
				totalPaidStyle.setDataFormat(totalPaidFormat.getFormat("#,##0.00;[RED]-#,##0.00"));
				cellTotalPaid.setCellStyle(totalPaidStyle);

				// Total Balance
				Cell cellTotalBalance = row.createCell(4);
				cellTotalBalance.setCellValue(billingInvoiceDto.getTotalBalance());
				XSSFDataFormat totalBalanceFormat = workbook.createDataFormat();
				CellStyle balanceStyle = workbook.createCellStyle();
				balanceStyle.setDataFormat(totalBalanceFormat.getFormat("#,##0.00;[RED]-#,##0.00"));
				cellTotalBalance.setCellStyle(balanceStyle);

				// Created By
				Cell cellCreatedBy = row.createCell(5);
				cellCreatedBy.setCellValue(billingInvoiceDto.getCreatedBy());

				// Created date
				row.createCell(6).setCellValue(
						Util.formatDate(IConstants.DATE_FORMAT, billingInvoiceDto.getInvoiceCreateDate()));

				Cell coupleDetailCell = row.createCell(7);

				String clientDeatail = null;
				List<Client> clients = clientService.getClientsByCoupleId(billingInvoiceDto.getCoupleId());
				for (Client client : clients) {
					if (client.getGender().equals("Female"))
						clientDeatail = client.getFirstName() + " " + client.getSurname() + "\nPhone No: "
								+ client.getPhoneNumber() + "\nDOB: "
								+ Util.formatDate(IConstants.DATE_FORMAT, client.getdOB());
				}
				coupleDetailCell.setCellValue(clientDeatail);
				count++;
			}
			// Auto fit
			for (int i = 0; i < 8; i++) {
				spreadsheet.autoSizeColumn(i);
			}
			FileOutputStream out = null;
			try {
				excelFile = new File(desktop, unpaidBillOverallFolder + File.separator + originalExcelFileName);
				if (!excelFile.getParentFile().exists()) {
					excelFile.getParentFile().mkdirs();
				}
				excelFile.createNewFile();
				out = new FileOutputStream(excelFile);
				workbook.write(out);
				logger.info("***********Excel file created Successfully*****************");

			} catch (Exception e) {
				logger.info("***********Caught exception :" + e);
				logger.error("***********Exception Message :" + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					out.close();
					if (excelFile.exists()) {
						pdfFile = new File(desktop, unpaidBillOverallFolder + File.separator + originalPdfFileName);
						pdfFile.createNewFile();
						float[] columnWidths = { 3, 5, 4, 4, 4, 5, 3, 6 };
						String pdfPageHeader = "Details of overall Unpaidbill.";
						List<String> pdfMetaData = new LinkedList<String>();
						pdfMetaData.add("UnpailBill Overall");
						pdfMetaData.add("Using iText");
						pdfMetaData.add("Java, PDF, iText");
						pdfMetaData.add("Babatunde");
						pdfMetaData.add("Pramod Maurya");
						ConvertExcelToPdf.readExcelSheet(excelFile.getPath(), pdfFile.getPath(), pdfPageHeader,
								pdfMetaData, columnWidths, moduleKey);
					}
					Notify alert = new Notify(AlertType.INFORMATION);
					alert.setContentText(MessageResource.getText("file.generated"));
					alert.showAndWait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
