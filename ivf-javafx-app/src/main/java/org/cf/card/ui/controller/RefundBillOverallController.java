package org.cf.card.ui.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.RefundInvoice;
import org.cf.card.ui.components.RefundInvoiceHyperlinkCell;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIRefundAndInvoice;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIRefundAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.ReadWriteExcelUtils;
import org.cf.card.util.EnumPermission.Module;
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
 */
public class RefundBillOverallController extends BaseController {

	private static final String mainPageTitle = MessageResource
			.getText("mainpage.title.billing.refund.invoice.per.couple");
	private static final String titleDescription = MessageResource.getText("mainpage.title.billing.refund.overall");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String iconURL = "/icons/unpaid_bill.png";

	// Binding fxml elements
	@FXML
	private DatePicker fromDate;
	@FXML
	private DatePicker toDate;
	@FXML
	private TextField totallRefundTextField;
	@FXML
	private TableView<UIRefundAndInvoice> refundBillOverallTableView;
	@FXML
	private TableColumn<UIRefundAndInvoice, Long> srNoColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, Hyperlink> refundInvoiceNumberColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, Long> totalRefundBillColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, String> billedByColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, String> refundDateColumn;
	@FXML
	private TableColumn<UIRefundAndInvoice, String> coupleDetailColumn;

	// Variable
	private UIRefundAndInvoiceService refundAndInvoiceService = new UIRefundAndInvoiceService();
	private Float overallRefundBill = null;
	private PrintTemplate<RefundInvoiceDto> printTemplate = new PrintTemplate<>();
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
		List<RefundInvoice> refundInvoices = refundAndInvoiceService.findOverallRefundBillOfInvoices();
		if (null != refundInvoices && refundInvoices.size() > 0) {
			String currencyType = companyDto.getBillingCurrency();
			overallRefundBill = new Float(0.0);
			ObservableList<UIRefundAndInvoice> observableList = FXCollections.observableArrayList();
			List<UIRefundAndInvoice> col = new ArrayList<UIRefundAndInvoice>();
			refundInvoices.forEach(new Consumer<RefundInvoice>() {
				int count = 1;

				@Override
				public void accept(RefundInvoice refundInvoice) {
					Float totalBill = refundInvoice.getTotalRefundBill();
					overallRefundBill = overallRefundBill + totalBill;
					String totalBillAsString = Util.getValueUpto2Decimal(totalBill);
					Long srNo = new Long(count);

					String firstName = refundInvoice.getCouple().getWoman().getFirstName();
					String lastName = refundInvoice.getCouple().getWoman().getSurname();
					String phoneNumber = refundInvoice.getCouple().getWoman().getPhoneNumber();
					String age = Util.formatDate(IConstants.DATE_FORMAT, refundInvoice.getCouple().getWoman().getdOB());
					String womanDetails = firstName + " " + lastName + "\nPhone : " + phoneNumber + "\nDOB : " + age;

					UIRefundAndInvoice refundInvoices = new UIRefundAndInvoice(srNo,
							refundInvoice.getRefundInvoiceNumber(), currencyType + totalBillAsString,
							Util.formatDate(IConstants.DATE_FORMAT, refundInvoice.getRefundCreateDate()),
							refundInvoice.getCreatedBy(), womanDetails);
					col.add(refundInvoices);
					count++;
				}

			});
			observableList.setAll(col);
			try {
				srNoColumn.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, Long>("srNo"));
				refundInvoiceNumberColumn.setCellValueFactory(
						new PropertyValueFactory<UIRefundAndInvoice, Hyperlink>("refundInvoiceHyperlink"));
				refundInvoiceNumberColumn.setCellFactory(hyperlinkCellFactory);
				totalRefundBillColumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, Long>("totalRefundBill"));
				refundDateColumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, String>("refundInvoiceDate"));
				billedByColumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, String>("billRefundedBy"));

				coupleDetailColumn
						.setCellValueFactory(new PropertyValueFactory<UIRefundAndInvoice, String>("womanDetails"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			refundBillOverallTableView.setItems(observableList);
			String uiTotalBillRefund = Util.getValueUpto2Decimal(overallRefundBill);
			totallRefundTextField.setText(currencyType + uiTotalBillRefund);
		}
	}

	private Callback<TableColumn<UIRefundAndInvoice, Hyperlink>, TableCell<UIRefundAndInvoice, Hyperlink>> hyperlinkCellFactory = new Callback<TableColumn<UIRefundAndInvoice, Hyperlink>, TableCell<UIRefundAndInvoice, Hyperlink>>() {
		@Override
		public TableCell<UIRefundAndInvoice, Hyperlink> call(TableColumn<UIRefundAndInvoice, Hyperlink> p) {
			return new RefundInvoiceHyperlinkCell(login, mainApp);
		}
	};

	@FXML
	private void generateExcelPdfFileAction() {

		LocalDate fromLocalDate = fromDate.getValue();
		LocalDate toLocalDate = toDate.getValue();
		if (null != fromLocalDate && null != toLocalDate) {
			Date refundFromDateObj = Util.getDateFromLocalDate(fromLocalDate);
			Date refundToDateObj = Util.getDateFromLocalDate(toLocalDate);
			List<RefundInvoiceDto> listOfRefundsInvoiceDtos = refundAndInvoiceService
					.findRefundInvoiceBetweenDates(refundFromDateObj, refundToDateObj);
			if (null != listOfRefundsInvoiceDtos && listOfRefundsInvoiceDtos.size() > 0) {
				ReadWriteExcelUtils.writeToExcelWithXSSFForRefundOverall(listOfRefundsInvoiceDtos, Module.REFUND_BILL_OVERALL.getKey());
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

		List<Node> nodes = createRefundBillOverallPrintTable(refundBillOverallTableView.getColumns(),
				refundBillOverallTableView.getItems(), pageLayout);

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

	private List<Node> createRefundBillOverallPrintTable(
			ObservableList<TableColumn<UIRefundAndInvoice, ?>> columnsOfTable,
			Collection<UIRefundAndInvoice> itemsofTable, PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableBox = null;
		double tableHeight = Double.POSITIVE_INFINITY;
		int coulmnCount = columnsOfTable.size();
		double labelWidth = pageLayout.getPrintableWidth() / coulmnCount;
		for (UIRefundAndInvoice record : itemsofTable) {
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

	private HBox createTableHeader(ObservableList<TableColumn<UIRefundAndInvoice, ?>> columnsOfTable, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		double columnsWidth = 70;
		double srNoWidth = 30;
		double coupleInfoWidth = 100;
		double rowHeight = 20;
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIRefundAndInvoice, ?> cols : columnsOfTable) {
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

	private HBox createTableRow(UIRefundAndInvoice record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		double columnsWidth = 70;
		double srNoWidth = 30;
		double coupleInfoWidth = 100;
		double rowHeight = 40;
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.setSnapToPixel(true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, String.valueOf(record.getSrNo()), srNoWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getRefundInvoiceHyperlink().getText(),
				columnsWidth, rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getTotalRefundBill(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getBillRefundedBy(), columnsWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, record.getRefundInvoiceDate(), columnsWidth,
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
		RefundInvoiceDto refundInvoiceDto = new RefundInvoiceDto();
		refundInvoiceDto.setTotalRefundBill(overallRefundBill);
		HBox invoiceTotalAmounts = printTemplate.createParentHBoxForTotalBillTotalPaidAndTotalBalance(pageLayout,
				refundInvoiceDto, companyDto.getBillingCurrency());
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
		refundBillOverallTableView.getItems().clear();
		fromDate.getEditor().clear();
		toDate.getEditor().clear();
		fromDate.setValue(null);
		toDate.setValue(null);
		totallRefundTextField.setText(IConstants.emptyString);
	}

	private void makeFieldsNonEditable() {
		totallRefundTextField.setEditable(false);
	}

}
