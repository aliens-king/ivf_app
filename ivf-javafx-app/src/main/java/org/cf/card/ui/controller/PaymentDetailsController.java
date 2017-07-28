package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.PaymentDto;
import org.cf.card.model.Client;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.TextAreaCell;
import org.cf.card.ui.model.UIPaymentDetails;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.service.UIPaymentService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
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
 */
public class PaymentDetailsController extends BaseController {

	// getting all message from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.billing.payment.details");
	private static final String description = MessageResource.getText("mainpage.title.billing.payment.details.description");
	private static final String iconURL = "/icons/billing_and_invoice.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// binding FXML elements
	@FXML
	private TextField invoiceNumberTextField;
	@FXML
	private TextField totalBillTextField;
	@FXML
	private TextField totalPaidTextField;
	@FXML
	private TextField totalBalanceTextField;
	@FXML
	private TableView<UIPaymentDetails> paymentDetailsTableView;
	@FXML
	private TableColumn<UIPaymentDetails, Long> srNoColumn;
	@FXML
	private TableColumn<UIPaymentDetails, String> paymentDate;
	@FXML
	private TableColumn<UIPaymentDetails, String> totalBill;
	@FXML
	private TableColumn<UIPaymentDetails, String> paid;
	@FXML
	private TableColumn<UIPaymentDetails, String> balance;
	@FXML
	private TableColumn<UIPaymentDetails, String> paymentMethod;
	@FXML
	private TableColumn<UIPaymentDetails, String> billedBy;
	@FXML
	private TableColumn<UIPaymentDetails, String> remarksColumn;
	@FXML
	private CommonDetailController commonDetailController;

	// Variables
	private BillingInvoiceDto billingInvoiceDto;
	private UIPaymentService uIPaymentService = new UIPaymentService();
	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();
	private PrintTemplate<BillingInvoiceDto> printTemplate = new PrintTemplate<>();
	private double printableWidth;
	private double printableHeight;

	/* maintaining the session data for BillingAndInvoice And CompanyDetail */
	private SessionObject<String, Long> sessionObject = null;
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private List<PaymentDto> paymentsDtoList = null;
	private CompanyDto companyDto;

	@SuppressWarnings("unchecked")
	public void buildData() {
		resetFields();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		sessionObject = Session.getInstance().getSessionObject();
		Long invoiceNumber = null;
		if (null != sessionObjectForCompany || null != sessionObject) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
			invoiceNumber = sessionObject.getComponent(Constants.BILLING_INVOICE);
		}
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		if (null != invoiceNumber) {
			billingInvoiceDto = billingAndInvoiceService.findBillingAndInvoiceByInvoiceNumber(invoiceNumber);
			buildTableData(billingInvoiceDto.getInvoiceNumber());
		}
		if (null != billingInvoiceDto) {
			String currencyType = companyDto.getBillingCurrency();
			invoiceNumberTextField.setText(String.valueOf(billingInvoiceDto.getInvoiceNumber()));
			totalBillTextField.setText(currencyType + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBill()));
			totalPaidTextField.setText(currencyType + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalPaid()));
			totalBalanceTextField
					.setText(currencyType + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBalance()));
		}
		makeTextFieldNonEditable();
	}

	private void buildTableData(Long invoiceNumber) {

		paymentsDtoList = uIPaymentService.findAllPaymentWithInvoiceNumber(invoiceNumber);
		if (null != paymentsDtoList && paymentsDtoList.size() > 0) {
			String currencyType = companyDto.getBillingCurrency();
			ObservableList<UIPaymentDetails> observableList = FXCollections.observableArrayList();
			List<UIPaymentDetails> col = new ArrayList<UIPaymentDetails>();
			paymentsDtoList.forEach(new Consumer<PaymentDto>() {
				int count = 1;

				@Override
				public void accept(PaymentDto dto) {
					Long srNo = new Long(count);
					String totalBillAsString = Util.getValueUpto2Decimal(dto.getTotalBill());
					String totalPaidAsString = Util.getValueUpto2Decimal(dto.getPaidBill());
					String totalBalanceAsString = Util.getValueUpto2Decimal(dto.getBalance());
					UIPaymentDetails paymentDetails = new UIPaymentDetails(srNo, dto.getId(),
							Util.formatDate(IConstants.DATE_FORMAT, dto.getPaymentDate()),
							currencyType + totalBillAsString, currencyType + totalPaidAsString,
							currencyType + totalBalanceAsString, dto.getPaymentMode(), dto.getPaymentBilledBy(),
							dto.getRemarks());
					col.add(paymentDetails);
					count++;
				}

			});
			observableList.setAll(col);
			try {
				srNoColumn.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, Long>("srNo"));
				paymentDate.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("paymentDate"));
				totalBill.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("totalBill"));
				paid.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("paid"));
				balance.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("balance"));
				paymentMethod.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("paymentMethod"));
				billedBy.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("billedBy"));
				remarksColumn.setCellValueFactory(new PropertyValueFactory<UIPaymentDetails, String>("remarks"));
				// Adding Textarea in remarks column
				remarksColumn.setCellFactory(
						new Callback<TableColumn<UIPaymentDetails, String>, TableCell<UIPaymentDetails, String>>() {

							@Override
							public TableCell<UIPaymentDetails, String> call(
									TableColumn<UIPaymentDetails, String> param) {
								return new TextAreaCell<UIPaymentDetails>(paymentDetailsTableView, login.getRoleId());
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
			paymentDetailsTableView.setItems(observableList);
		}
	}

	/**
	 * Prints the action.
	 */
	@FXML
	private void printAction() {
		Printer printer = Printer.getDefaultPrinter();
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		// setting layout of page
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
				Printer.MarginType.DEFAULT);
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);
		printableWidth = pageLayout.getPrintableWidth();
		printableHeight = pageLayout.getPrintableHeight();
		// Here we are creating printable table,If table size is large then left
		// data write on next page.
		List<Node> nodes = createPrintableTable(paymentDetailsTableView.getColumns(),
				paymentDetailsTableView.getItems());

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
	 * Creates the print page. This method create BorderPane with data data
	 * inside page.(Header,Footer, Center Data etc)
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
		root.setPrefHeight(pageLayout.getPrintableHeight());

		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, description, pageLayout);
		root.setTop(headerHbox);

		// Setting the Center part of BorderPanePage with content(Common
		// Section, PaymentInfo, Table View) at
		// center
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		contentVBox.getChildren().add(patientGrid);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		Client woman = womanCode.getClient();
		HBox addressDetail = printTemplate.createAddressDetail(woman.getHomeAddress() + "\n Contact No.:"
				+ woman.getPhoneNumber() + "\n Age : " + Util.getAge(woman.getdOB()), pageLayout);
		contentVBox.getChildren().add(addressDetail);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		// VBox paymentInfoVBox = createParentVBoxForPaymentInfo();
		HBox invoiceNoAndDate = printTemplate.createParentHBoxForInvoiceNoAndDate(pageLayout, billingInvoiceDto);
		if (null != invoiceNoAndDate) {
			contentVBox.getChildren().add(invoiceNoAndDate);
			contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		}
		contentVBox.getChildren().add(table);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		HBox paymentAmountInfo = hBoxForPaymentAmountInfo();
		if (null != paymentAmountInfo) {
			contentVBox.getChildren().add(paymentAmountInfo);
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

	private List<Node> createPrintableTable(ObservableList<TableColumn<UIPaymentDetails, ?>> columns,
			Collection<UIPaymentDetails> items) {

		List<Node> nodes = null;
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = printableWidth / columnCount;
		if (null != items && items.size() > 0) {
			nodes = new ArrayList<Node>();
			for (UIPaymentDetails invoice : items) {
				// elementHeight would be the height of each cell
				final double elementHeight = 15;
				// adding table on multiple pages
				// 210 - height of patient section + height of embryo Info HBox
				if (elementHeight + totalHeight > printableHeight - 465) {
					tableVBox = new VBox();
					tableVBox.setPrefWidth(printableWidth);
					tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
					HBox tableHeader = createTableHeader(columns, labelWidth);
					// adding table columns in Table VBox
					tableVBox.getChildren().add(tableHeader);
					nodes.add(tableVBox);
					totalHeight = 0;
				}
				HBox row = createTableRow(invoice, labelWidth);
				totalHeight += elementHeight;
				if (tableVBox != null)
					tableVBox.getChildren().add(row); // adding table rows in
														// Table
														// VBox
			}
		}
		return nodes;
	}

	public HBox createTableHeader(ObservableList<TableColumn<UIPaymentDetails, ?>> columns, double labelWidth) {

		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(printableWidth);
		headerHBox.setPrefHeight(20);
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIPaymentDetails, ?> cols : columns) {
			// condition to avoid the last separator in table
			if (count < columns.size()) {
				if (cols.getText().indexOf("Billed By") >= 0) {
					headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("Recieved By", labelWidth));
				} else {
					headerHBox.getChildren().add(printTemplate.createTableHeaderLabel(cols.getText(), labelWidth));
					headerHBox.getChildren().add(printTemplate.createSeparator());
				}
			}
			count++;

		}
		return headerHBox;
	}

	public HBox createTableRow(UIPaymentDetails record, double labelWidth) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(printableWidth);
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getSrNo() + "", labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getPaymentDate(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getTotalBill(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getPaid(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getBalance(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getPaymentMethod(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getBilledBy(), labelWidth, false));
		return rowHBox;
	}

	private HBox hBoxForPaymentAmountInfo() {
		HBox hBoxForPaymentInfo = null;
		if (null != paymentsDtoList && paymentsDtoList.size() > 0) {
			hBoxForPaymentInfo = new HBox();
			hBoxForPaymentInfo.setPrefHeight(25);
			hBoxForPaymentInfo.setPrefWidth(printableWidth);
			hBoxForPaymentInfo.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
			// hBoxForPaymentInfo.getChildren().add(createSubParentHBoxForAmount());

			int numberOfElements = 3;
			hBoxForPaymentInfo.setPrefWidth(printableWidth);
			hBoxForPaymentInfo.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
			hBoxForPaymentInfo.getChildren().add(createChildHBoxForPaymentInfo(
					" " + MessageResource.getText("add.payment.total.bill") + ":",
					companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBill()),
					numberOfElements));
			hBoxForPaymentInfo.getChildren().add(createChildHBoxForPaymentInfo(
					" " + MessageResource.getText("add.payment.total.paid") + ":",
					companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalPaid()),
					numberOfElements));
			hBoxForPaymentInfo.getChildren()
					.add(createChildHBoxForPaymentInfo(" " + MessageResource.getText("add.payment.total.balance") + ":",
							companyDto.getBillingCurrency()
									+ Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBalance()),
							numberOfElements));

		}
		return hBoxForPaymentInfo;

	}

	/**
	 * Creates the child H box for payment info.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the h box
	 */
	private HBox createChildHBoxForPaymentInfo(String key, String value, int columns) {
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setStyle("-fx-border-width: 0 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		hBox.setPrefWidth(printableWidth / columns);
		hBox.getChildren().add(printTemplate.createStaticLabel(key));
		hBox.getChildren().add(printTemplate.createDynamicLabel(value));
		return hBox;
	}

	private void makeTextFieldNonEditable() {
		invoiceNumberTextField.setEditable(false);
		totalBillTextField.setEditable(false);
		totalPaidTextField.setEditable(false);
		totalBalanceTextField.setEditable(false);
	}

	private void resetFields() {
		paymentDetailsTableView.getItems().clear();
		invoiceNumberTextField.setText(IConstants.emptyString);
		totalBillTextField.setText(IConstants.emptyString);
		totalPaidTextField.setText(IConstants.emptyString);
		totalBalanceTextField.setText(IConstants.emptyString);
		// We are setting null because of change patient session
		billingInvoiceDto = null;
		paymentsDtoList = null;
	}
}
