package org.cf.card.ui.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.Client;
import org.cf.card.ui.components.RefundInvoiceHyperlinkCell;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.TextAreaCell;
import org.cf.card.ui.model.UIRefundAndInvoice;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIRefundAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

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
 *
 * @since : Jan 2, 2017
 */
public class RefundBillPerCoupleController extends BaseController {

	// getting all message from message.property file
	private static final String mainPageTitle = MessageResource
			.getText("mainpage.title.billing.refund.invoice.per.couple");
	private static final String titleDescription = MessageResource
			.getText("mainpage.title.billing.invoices.detail.description");
	private static final String iconURL = "/icons/refund_overall.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// Binding fxml elements
	@FXML
	private TextField totallRefundTextField;
	@FXML
	private TableView<UIRefundAndInvoice> overallRefundPerCoupleTableView;
	@FXML
	private TableColumn<UIRefundAndInvoice, Long> SrNoColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, Hyperlink> refundInvoiceNumberColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, Long> totalRefunedBillColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, String> billRefundedByColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, String> refundInvoiceDateCloumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, String> remarksColumn;
	@FXML
	private CommonDetailController commonDetailController;

	// Variables
	private UIRefundAndInvoiceService refundAndInvoiceService = new UIRefundAndInvoiceService();
	private PrintTemplate<RefundInvoiceDto> printTemplate = new PrintTemplate<>();
	private double printableWidth;
	private double printableHeight;
	private Float overallRefundBill = null;
	
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
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		buildTableDataForRefund();
		makeTextFieldNonEditable();
		
	}

	private void buildTableDataForRefund() {
		overallRefundPerCoupleTableView.setEditable(true);
		overallRefundPerCoupleTableView.getItems().clear();
		List<RefundInvoiceDto> refundInvoiceDtos = refundAndInvoiceService.findAllRefundByCoupleId(couple.getId());
		ObservableList<UIRefundAndInvoice> observableList = FXCollections.observableArrayList();
		List<UIRefundAndInvoice> column = new ArrayList<>();
		if (null != refundInvoiceDtos && refundInvoiceDtos.size() > 0) {
			overallRefundBill = new Float(0.0);
			String currencyType = companyDto.getBillingCurrency();
			refundInvoiceDtos.forEach(new Consumer<RefundInvoiceDto>() {
				int count = 1;

				@Override
				public void accept(RefundInvoiceDto t) {
					Float totalBill = t.getTotalRefundBill();
					overallRefundBill = overallRefundBill + totalBill;
					DecimalFormat dateFormat = new DecimalFormat("0.00");
					dateFormat.setMaximumFractionDigits(2);
					String refundTotalBillAsString = dateFormat.format(totalBill);
					Long SrNo = new Long(count);

					UIRefundAndInvoice refundInvoices = new UIRefundAndInvoice(SrNo, t.getId(),
							currencyType + refundTotalBillAsString, t.getRefundInvoiceNumber(),
							Util.formatDate(IConstants.DATE_FORMAT, t.getRefundCreateDate()), t.getCreatedBy(),
							t.getRemarks());
					column.add(refundInvoices);
					count++;
				}
			});
			observableList.setAll(column);
			try {
				SrNoColumn.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, Long>("srNo"));
				refundInvoiceNumberColumn.setCellValueFactory(
						new PropertyValueFactory<UIRefundAndInvoice, Hyperlink>("refundInvoiceHyperlink"));
				refundInvoiceNumberColumn.setCellFactory(hyperlinkCellFactory);
				totalRefunedBillColumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, Long>("totalRefundBill"));
				refundInvoiceDateCloumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, String>("refundInvoiceDate"));
				billRefundedByColumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, String>("billRefundedBy"));
				remarksColumn.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, String>("remarks"));
				// Adding Textarea in remarks column
				remarksColumn.setCellFactory(
						new Callback<TableColumn<UIRefundAndInvoice, String>, TableCell<UIRefundAndInvoice, String>>() {

							@Override
							public TableCell<UIRefundAndInvoice, String> call(
									TableColumn<UIRefundAndInvoice, String> param) {
								return new TextAreaCell<UIRefundAndInvoice>(overallRefundPerCoupleTableView,
										login.getRoleId());
							}
						});

			} catch (Exception e) {
				e.printStackTrace();

			}
			overallRefundPerCoupleTableView.setItems(observableList);
			String uiTotalBillRefund = Util.getValueUpto2Decimal(overallRefundBill);
			totallRefundTextField.setText(currencyType + uiTotalBillRefund);
		}

	}

	private Callback<TableColumn<UIRefundAndInvoice, Hyperlink>, TableCell<UIRefundAndInvoice, Hyperlink>> hyperlinkCellFactory = new Callback<TableColumn<UIRefundAndInvoice, Hyperlink>, TableCell<UIRefundAndInvoice, Hyperlink>>() {
		@Override
		public TableCell<UIRefundAndInvoice, Hyperlink> call(TableColumn<UIRefundAndInvoice, Hyperlink> p) {
			return new RefundInvoiceHyperlinkCell(mainApp, login);
		}
	};

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
		printableWidth = pageLayout.getPrintableWidth();
		printableHeight = pageLayout.getPrintableHeight();
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		// Here we are creating printable table,If table size is large then left
		// data write on next page.
		List<Node> nodes = createPrintableTable(overallRefundPerCoupleTableView.getColumns(),
				overallRefundPerCoupleTableView.getItems());

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
	public List<Node> createPrintableTable(ObservableList<TableColumn<UIRefundAndInvoice, ?>> columns,
			Collection<UIRefundAndInvoice> items) {
		List<Node> nodes = null;
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = printableWidth / columnCount;
		if (null != items && items.size() > 0) {
			nodes = new ArrayList<Node>();
			for (UIRefundAndInvoice invoice : items) {
				// elementHeight would be the height of each cell
				final double elementHeight = 15;
				// adding table on multiple pages
				// We are subtract 400 from totalPrintableHeight, here 400 =
				// (Page Header + patientSection + BillingAddress +
				// CompanyRefund Policy). So printableHeight - 400 = result
				// height will for table.
				if (elementHeight + totalHeight > printableHeight - 400) {
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
					tableVBox.getChildren().add(row);
			}
		}
		return nodes;
	}

	/**
	 * Setting Column Names(Header) in HBox
	 */
	public HBox createTableHeader(ObservableList<TableColumn<UIRefundAndInvoice, ?>> columns, double labelWidth) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(printableWidth);
		headerHBox.setPrefHeight(20);
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIRefundAndInvoice, ?> cols : columns) {
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
	public HBox createTableRow(UIRefundAndInvoice record, double labelWidth) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(printableWidth);
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getSrNo()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox,
				String.valueOf(record.getRefundInvoiceHyperlink().getText()), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getTotalRefundBill()),
				labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getRefundInvoiceDate()),
				labelWidth);
		// without separator
		printTemplate.addTableRowDataToHBox(rowHBox, String.valueOf(record.getBillRefundedBy()), labelWidth);
		return rowHBox;
	}

	private void resetFields() {
		overallRefundPerCoupleTableView.getItems().clear();
		totallRefundTextField.setText(IConstants.emptyString);
	}
	
	
	private void makeTextFieldNonEditable(){
		totallRefundTextField.setEditable(false);
	}

}
