package org.cf.card.ui.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.model.BillingInvoice;
import org.cf.card.ui.components.InvoiceHyperlinkCell;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIBillingAndInvoice;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.ReadWriteExcelUtils;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.cf.card.util.EnumPermission.Module;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * @author Pramod Maurya
 * @since : Jan 2, 2017
 *
 */
public class UnpaidBillOverallController extends BaseController {

	private static final Logger logger = Logger.getLogger(UnpaidBillOverallController.class);

	private static final String mainPageTitle = MessageResource.getText("mainpage.title.billing.unpaid");
	private static final String titleDescription = MessageResource.getText("mainpage.title.billing.refund.overall");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String iconURL = "/icons/unpaid_bill.png";
	// binding fxml elements
	@FXML
	private TextField totalBill;
	@FXML
	private TextField totalPaid;
	@FXML
	private TextField totalBalance;
	@FXML
	private TableView<UIBillingAndInvoice> unpaidBillOverallTableView;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> srNoColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, Hyperlink> invoiceHyperlink;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> totalBillColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> totalPaidColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> totalBalanceColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, String> billedByColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, String> invoiceDateColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, String> coupleDetailColumn;
	@FXML
	private DatePicker fromDate;
	@FXML
	private DatePicker toDate;

	// Variables
	private Float overallBill = null;
	private Float overallPaidBill = null;
	private Float overallBalance = null;

	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();

	private List<BillingInvoice> billingInvoiceList;
	private PrintTemplate<BillingInvoiceDto> printTemplate = new PrintTemplate<>();
	/* maintaining the session data for and CompanyDetail */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	@SuppressWarnings("unchecked")
	public void buildData() {
		resetFields();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		buildTableData();
		makeFieldsNonEditable();
	}

	private void buildTableData() {

		billingInvoiceList = billingAndInvoiceService.findOverallUnpaidBillOfInvoices();
		if (null != billingInvoiceList && billingInvoiceList.size() > 0) {
			String currencyType = companyDto.getBillingCurrency();
			overallBill = new Float(0.0);
			overallPaidBill = new Float(0.0);
			overallBalance = new Float(0.0);
			ObservableList<UIBillingAndInvoice> observableList = FXCollections.observableArrayList();
			List<UIBillingAndInvoice> col = new ArrayList<>();
			billingInvoiceList.forEach(new Consumer<BillingInvoice>() {
				int count = 1;

				@Override
				public void accept(BillingInvoice biObj) {
					Float totalBill = biObj.getTotalBill();
					Float totalPaid = biObj.getTotalPaid();
					Float totalBalance = biObj.getTotalBalance();
					overallBill = overallBill + totalBill;
					overallPaidBill = overallPaidBill + totalPaid;
					overallBalance = overallBalance + totalBalance;
					String totalBillAsString = Util.getValueUpto2Decimal(totalBill);
					String totalPaidAsString = Util.getValueUpto2Decimal(totalPaid);
					String totalBalanceAsString = Util.getValueUpto2Decimal(totalBalance);
					Long srNo = new Long(count);
					String firstName = biObj.getCouple().getWoman().getFirstName();
					String lastName = biObj.getCouple().getWoman().getSurname();
					String phoneNumber = biObj.getCouple().getWoman().getPhoneNumber();
					String age = Util.formatDate(IConstants.DATE_FORMAT, biObj.getCouple().getWoman().getdOB());
					String womanDetails = firstName + " " + lastName + "\nPhone : " + phoneNumber + "\nDOB : " + age;

					UIBillingAndInvoice invoives = new UIBillingAndInvoice(srNo, biObj.getInvoiceNumber(),
							currencyType + totalBillAsString, currencyType + totalPaidAsString,
							currencyType + totalBalanceAsString, biObj.getCreatedBy(),
							Util.formatDate(IConstants.DATE_FORMAT, biObj.getInvoiceCreateDate()), womanDetails);
					col.add(invoives);
					count++;
				}

			});
			observableList.setAll(col);
			try {
				srNoColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("srNo"));
				invoiceHyperlink.setCellValueFactory(
						new PropertyValueFactory<UIBillingAndInvoice, Hyperlink>("invoiceHyperlink"));
				invoiceHyperlink.setCellFactory(hyperlinkCellFactory);
				totalBillColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("totalBill"));
				totalPaidColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("totalPaid"));
				totalBalanceColumn
						.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("totalBalance"));
				billedByColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, String>("billedBy"));
				invoiceDateColumn
						.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, String>("invoiceDate"));

				coupleDetailColumn
						.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, String>("womanDetails"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			unpaidBillOverallTableView.setItems(observableList);
			String uiTotalBill = currencyType + Util.getValueUpto2Decimal(overallBill);
			String uiTotalPaid = currencyType + Util.getValueUpto2Decimal(overallPaidBill);
			String uiTotalBalance = currencyType + Util.getValueUpto2Decimal(overallBalance);
			totalBill.setText(uiTotalBill);
			totalPaid.setText(uiTotalPaid);
			totalBalance.setText(uiTotalBalance);

		}
	}

	private Callback<TableColumn<UIBillingAndInvoice, Hyperlink>, TableCell<UIBillingAndInvoice, Hyperlink>> hyperlinkCellFactory = new Callback<TableColumn<UIBillingAndInvoice, Hyperlink>, TableCell<UIBillingAndInvoice, Hyperlink>>() {
		@Override
		public TableCell<UIBillingAndInvoice, Hyperlink> call(TableColumn<UIBillingAndInvoice, Hyperlink> p) {
			return new InvoiceHyperlinkCell(login, mainApp);
		}
	};

	@FXML
	private void generateExcelPdfFileAction() {
		logger.info("generateExcelPdfFileAction method call of UnpaidBillOverall Screen");
		LocalDate fromLocalDate = fromDate.getValue();
		LocalDate toLocalDate = toDate.getValue();
		if (null != fromLocalDate && null != toLocalDate) {
			Date unpaidFromDateObj = Util.getDateFromLocalDate(fromLocalDate);
			Date unpaidToDateObj = Util.getDateFromLocalDate(toLocalDate);
			List<BillingInvoiceDto> listOfBillingInvoiceDtos = billingAndInvoiceService
					.findAllUnpaidBillBetweenDates(unpaidFromDateObj, unpaidToDateObj);
			
			logger.info("Getting all unpaid bill realeted to selected Date");
			if (null != listOfBillingInvoiceDtos && listOfBillingInvoiceDtos.size() > 0) {
				try {
					ReadWriteExcelUtils.writeToExcelWithXSSFForUnpaidOverall(listOfBillingInvoiceDtos, Module.UNPAID_BILL_OVERALL.getKey());
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				Notify alert = new Notify(AlertType.INFORMATION);
				alert.setContentText(MessageResource.getText("empty.record"));
				alert.showAndWait();
			}
		} else {
			Notify alert = new Notify(AlertType.INFORMATION);
			alert.setContentText(MessageResource.getText("select.date"));
			alert.showAndWait();
		}
		/*
		 * fromDate.getEditor().clear(); toDate.getEditor().clear();
		 */

	}

	@FXML
	private void printAction() {

		Printer printer = Printer.getDefaultPrinter();
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		// setting layout of page
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
				Printer.MarginType.DEFAULT);
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		List<Node> nodes = createUnpainOverallPrintTable(unpaidBillOverallTableView.getColumns(),
				unpaidBillOverallTableView.getItems(), pageLayout);

		int page = 1;
		if (nodes != null && nodes.size() > 0) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				for (Node tableBox : nodes) {
					BorderPane printPage = createPrintPage(tableBox, page, pageLayout);
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

	private List<Node> createUnpainOverallPrintTable(ObservableList<TableColumn<UIBillingAndInvoice, ?>> columnsOfTable,
			Collection<UIBillingAndInvoice> itemsofTable, PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableBox = null;
		double tableHeight = Double.POSITIVE_INFINITY;
		int coulmnCount = columnsOfTable.size();
		double labelWidth = pageLayout.getPrintableWidth() / coulmnCount;
		for (UIBillingAndInvoice record : itemsofTable) {
			// heigth of the each cell
			final double elementHeight = 15;
			// adding table on multiple pages
			// We are subtract 400 from totalPrintableHeight, here 400 =
			// (Page Header + TotalAmounts
			// CompanyRefund Policy). So printableHeight - 400 = result
			// height will for table.
			if (elementHeight + tableHeight > pageLayout.getPrintableHeight() - 530) {
				tableBox = new VBox();
				tableBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);

				HBox tableHeader = createTableHeader(columnsOfTable, labelWidth, pageLayout);

				// adding table columns in table vbox
				tableBox.getChildren().add(tableHeader);
				nodes.add(tableBox);
				tableHeight = 0;
			}
			tableHeight += elementHeight;
			HBox row = createTableRow(record, labelWidth, pageLayout);
			if (tableBox != null)
				tableBox.getChildren().add(row);
		}
		return nodes;

	}

	private HBox createTableHeader(ObservableList<TableColumn<UIBillingAndInvoice, ?>> columnsOfTable,
			double labelWidth, PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		double columnsWidth = 55;
		double srNoWidth = 28;
		double coupleInfoWidth = 80;
		double rowHeight = 20;
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIBillingAndInvoice, ?> cols : columnsOfTable) {
			// condition to avoid the last separator in table
			if (count == 1) {
				printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox, cols.getText(), srNoWidth,
						rowHeight, true);
			} else if (count == columnsOfTable.size()) {
				printTemplate.addTableRowToHBoxOverallScreen(headerHBox, cols.getText(), coupleInfoWidth, rowHeight,
						true);
			} else {
				printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox, cols.getText(), columnsWidth,
						rowHeight, true);
			}
			count++;
		}
		return headerHBox;

	}

	private HBox createTableRow(UIBillingAndInvoice record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		double columnsWidth = 55;
		double srNoWidth = 28;
		double coupleInfoWidth = 80;
		double rowHeight = 40;
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.setSnapToPixel(true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, String.valueOf(record.getSrNo()), srNoWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getInvoiceHyperlink().getText(),
				columnsWidth, rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getTotalBill(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getTotalPaid(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getTotalBalance(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getBilledBy(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getInvoiceDate(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxOverallScreen(rowHBox, record.getWomanDetails(), coupleInfoWidth, rowHeight,
				false);
		return rowHBox;
	}

	// Complete BorderPage for printing
	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());
		HBox headingHBox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headingHBox);

		// Setting the content at center of Border Pane
		VBox contentVerticalBox = new VBox();
		contentVerticalBox.getChildren().addAll(table);
		contentVerticalBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();
		billingInvoiceDto.setTotalPaid(overallPaidBill);
		billingInvoiceDto.setTotalBalance(overallBalance);
		billingInvoiceDto.setTotalBill(overallBill);
		HBox invoiceTotalAmounts = printTemplate.createParentHBoxForTotalBillTotalPaidAndTotalBalance(pageLayout,
				billingInvoiceDto, companyDto.getBillingCurrency());
		if (null != invoiceTotalAmounts) {
			contentVerticalBox.getChildren().add(invoiceTotalAmounts);
			contentVerticalBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		}
		root.setCenter(contentVerticalBox);

		// Setting the content at bottom of Border Pane
		HBox companyInfoAndRefundPolicyFooter = printTemplate.createCompanyInfoAndRefundPolicyFooter(
				companyDto.getCompanyFullName() + "\n" + companyDto.getCompanyAddress(),
				companyDto.getBillingRefundPolicy(), pageLayout);
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		VBox vBoxFooter = new VBox();
		vBoxFooter.getChildren().addAll(companyInfoAndRefundPolicyFooter, footerGrid);
		root.setBottom(vBoxFooter);
		return root;

	}

	private void resetFields() {
		billingInvoiceList = null;
		unpaidBillOverallTableView.getItems().clear();
		totalBill.setText(IConstants.emptyString);
		totalPaid.setText(IConstants.emptyString);
		totalBalance.setText(IConstants.emptyString);
		fromDate.getEditor().clear();
		toDate.getEditor().clear();
		fromDate.setValue(null);
		toDate.setValue(null);
	}

	private void makeFieldsNonEditable() {
		totalBill.setEditable(false);
		totalPaid.setEditable(false);
		totalBalance.setEditable(false);
		coupleDetailColumn.setEditable(false);
	}

}
