package org.cf.card.ui.print.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIBillingAndInvoice;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.Util;

import javafx.collections.ObservableList;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BillingInvoiceDetailAndUnpaidPerCouplePrinting {

	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String titleDescription = MessageResource
			.getText("mainpage.title.billing.invoices.detail.description");
	private double printableWidth;
	private double printableHeight;
	private CompanyDto companyDto;
	private PrintTemplate<BillingInvoiceDto> printTemplate = new PrintTemplate<>();
	/* maintaining the session data for and CompanyDetail */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private Codes womanCode;
	private Codes manCode;
	private String mainPageTitle;
	private String iconURL;
	private BillingInvoiceDto billingInvoiceDto;

	@SuppressWarnings("unchecked")
	public void printActionForInvoicesAndUnpaidPerCouple(TableView<UIBillingAndInvoice> billingInvoiceTableView,
			Codes womanCode, Codes manCode, MainApp mainApp, String mainPageTitle, String iconURL,
			BillingInvoiceDto billingInvoiceDto) {
		resetFields();
		this.womanCode = womanCode;
		this.manCode = manCode;
		this.mainPageTitle = mainPageTitle;
		this.iconURL = iconURL;
		this.billingInvoiceDto = billingInvoiceDto;

		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			this.companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		Printer printer = Printer.getDefaultPrinter();
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		// setting layout of page
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
				Printer.MarginType.DEFAULT);
		printableWidth = pageLayout.getPrintableWidth();
		printableHeight = pageLayout.getPrintableHeight();
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		// Here we are creating printable table,If table size is large then left
		// data write on next page.

		List<Node> nodes = createPrintableTable(billingInvoiceTableView.getColumns(),
				billingInvoiceTableView.getItems());

		int page = 1;
		if (nodes != null && nodes.size() > 0) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				for (Node tableVBox : nodes) {
					BorderPane printPage = createPrintPage(tableVBox, page, pageLayout);
					printerJob.printPage(printPage);
					page++;
				}
				printerJob.endJob();
			}
		} else {
			Notify notify = new Notify(AlertType.INFORMATION, noDataAvailableMessage);
			notify.showAndWait();
		}

	}

	/**
	 * This method will return BorderPane which will include all contents that
	 * need to print. Creates the print page.
	 *
	 * @param table
	 *            the table
	 * @param page
	 *            the page
	 * @param pageLayout
	 *            the page layout
	 * @return the border pane
	 */
	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(printableHeight);
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headerHbox);

		// Setting the content at center of Border Pane
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		contentVBox.getChildren().add(patientGrid);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		Client woman = womanCode.getClient();
		HBox addressDetail = printTemplate.createAddressDetail(woman.getHomeAddress() + "\n Contact No.:"
				+ woman.getPhoneNumber() + "\n Age : " + Util.getAge(woman.getdOB()), pageLayout);
		contentVBox.getChildren().add(addressDetail);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		contentVBox.getChildren().add(table);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));

		HBox invoiceTotalAmounts = printTemplate.createParentHBoxForTotalBillTotalPaidAndTotalBalance(pageLayout,
				billingInvoiceDto, companyDto.getBillingCurrency());
		if (null != invoiceTotalAmounts) {
			contentVBox.getChildren().add(invoiceTotalAmounts);
			contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		}
		HBox companyInfoAndRefundPolicyFooter = printTemplate.createCompanyInfoAndRefundPolicyFooter(
				companyDto.getCompanyFullName() + "\n" + companyDto.getCompanyAddress(),
				companyDto.getBillingRefundPolicy(), pageLayout);
		root.setCenter(contentVBox);

		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		VBox vBoxFooter = new VBox();
		vBoxFooter.getChildren().addAll(companyInfoAndRefundPolicyFooter, footerGrid);
		root.setBottom(vBoxFooter);
		return root;
	}

	/**
	 * Creates the printable table.
	 *
	 * @param columns
	 *            the columns
	 * @param items
	 *            the items
	 * @return the list
	 */
	public List<Node> createPrintableTable(ObservableList<TableColumn<UIBillingAndInvoice, ?>> columns,
			Collection<UIBillingAndInvoice> items) {
		List<Node> nodes = null;
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = printableWidth / columnCount;
		if (null != items && items.size() > 0) {
			nodes = new ArrayList<Node>();
			for (UIBillingAndInvoice invoice : items) {
				// elementHeight would be the height of each cell
				final double elementHeight = 15;
				// adding table on multiple pages
				// We are subtract 440 from totalPrintableHeight, here 440 =
				// (Page Header + patientSection + BillingAddress + Total Amount
				// CompanyRefund Policy). So (printableHeight - 440)= result
				// height will for table.
				if (elementHeight + totalHeight > printableHeight - 440) {
					tableVBox = new VBox();
					tableVBox.setPrefWidth(printableWidth);
					tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
					HBox tableHeader = createTableHeader(columns, labelWidth);
					tableVBox.getChildren().add(tableHeader);
					nodes.add(tableVBox);
					totalHeight = 0;
				}
				HBox row = createTableRow(invoice, labelWidth);
				totalHeight += elementHeight;
				if (tableVBox != null)
					tableVBox.getChildren().add(row);
			}
		}
		return nodes;
	}

	/**
	 * Setting Column Names(Header) in HBox
	 */
	public HBox createTableHeader(ObservableList<TableColumn<UIBillingAndInvoice, ?>> columns, double labelWidth) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(printableWidth);
		headerHBox.setPrefHeight(20);
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIBillingAndInvoice, ?> cols : columns) {
			// This condition is for avoid last column remarks to from add.
			if (count < columns.size()) {
				// condition to avoid the last separator in table
				if (count == columns.size() - 1) {
					printTemplate.addTableHeaderRowToHBox(headerHBox, cols.getText(), labelWidth);
				} else {
					printTemplate.addTableHeaderRowToHBoxWithSeparator(headerHBox, cols.getText(), labelWidth);
				}
			}
			count++;
		}
		return headerHBox;
	}

	/**
	 * Creates the table row.
	 *
	 * @param record
	 *            the record
	 * @param labelWidth
	 *            the label width
	 * @return the h box
	 */
	public HBox createTableRow(UIBillingAndInvoice record, double labelWidth) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(printableWidth);
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getSrNo()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox,
				String.valueOf(record.getInvoiceHyperlink().getText()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getTotalBill()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getTotalPaid()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getTotalBalance()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getInvoiceDate()), labelWidth);
		printTemplate.addTableRowDataToHBox(rowHBox, String.valueOf(record.getBilledBy()), labelWidth);
		return rowHBox;
	}

	private void resetFields() {
		this.womanCode = null;
		this.manCode = null;
		this.mainPageTitle = null;
		this.iconURL = null;
		this.billingInvoiceDto = null;
	}

}
