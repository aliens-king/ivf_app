package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.ProcedureDto;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIProcedure;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIBillingSetupService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.ui.validation.FormValidator;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 28, 2016
 */
public class BillingSetupController extends BaseController {

	// getting all message from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.billing.setup");
	private static final String iconURL = "/icons/billing_setup.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// binding fxml element
	@FXML
	private TextField procedureNameTextField;
	@FXML
	private TextField procedurePriceTextField;
	@FXML
	private Text procedureNameErrorText;
	@FXML
	private Text procedurePriceErrorText;
	@FXML
	private TableView<UIProcedure> procedureTable;
	@FXML
	private TableColumn<UIProcedure, Long> id;
	@FXML
	private TableColumn<UIProcedure, String> procedureName;
	@FXML
	private TableColumn<UIProcedure, String> procedurePrice;
	@FXML
	private TableColumn<UIProcedure, String> procedureCreateDate;
	@FXML
	private TableColumn<UIProcedure, String> createdBy;

	// creating object
	UIBillingSetupService billingSetupService = new UIBillingSetupService();
	FormValidator validator = new FormValidator();
	private PrintTemplate<ProcedureDto> printTemplate = new PrintTemplate<>();
	/* maintaining the session data for and CompanyDetail */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	/**
	 * Builds the data.
	 */
	@SuppressWarnings("unchecked")
	public void buildData() {
		resetFields();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		procedurePriceTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^([+-]?\\d*\\.?\\d*)$")) {
					procedurePriceTextField.setText(newValue.replaceAll("[^([+-]?\\d*\\.?\\d]", ""));
				}
			}
		});
		populateDateIntoTable();
	}

	/**
	 * Populate date into table.
	 */
	public void populateDateIntoTable() {

		if (null != companyDto) {
			List<ProcedureDto> dtos = billingSetupService.findAllProcedure();
			ObservableList<UIProcedure> observableList = FXCollections.observableArrayList();
			List<UIProcedure> col = new ArrayList<>();
			dtos.forEach(new Consumer<ProcedureDto>() {
				@Override
				public void accept(ProcedureDto t) {
					Float price = t.getProcedurePrice();
					String procedurPrice = Util.getValueUpto2Decimal(price);
					UIProcedure procedure = new UIProcedure(t.getId(), t.getProcedureName(),
							companyDto.getBillingCurrency() + procedurPrice,
							Util.formatDate(IConstants.DATE_FORMAT, t.getProcedureCreateDate()), login.getFirstName());
					col.add(procedure);
				}
			});
			observableList.setAll(col);
			try {
				id.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
				procedureName.setCellValueFactory(cellData -> cellData.getValue().getProcedureName());
				procedurePrice.setCellValueFactory(cellData -> cellData.getValue().getProcedurePrice());
				procedureCreateDate.setCellValueFactory(cellData -> cellData.getValue().getProcedureCreateDate());
				createdBy.setCellValueFactory(cellData -> cellData.getValue().getCreatedBy());
			} catch (Exception e) {
				e.printStackTrace();
			}
			procedureTable.setItems(observableList);
		}
	}

	@FXML
	private void saveAction() {

		if (null != companyDto) {
			if (EnumPermission.canWrite(login.getRoleId(), Module.BILLING_SETUP.getKey())) {
				int ok = 1;
				ok = validator.validateBillingSetupField(procedureNameTextField, procedurePriceTextField,
						procedureNameErrorText, procedurePriceErrorText);
				if (ok == 1) {
					ProcedureDto procedureDto = new ProcedureDto();
					procedureDto.setProcedureCreateDate(new Date());
					procedureDto.setProcedureName(procedureNameTextField.getText());
					procedureDto.setProcedurePrice(Float.parseFloat(procedurePriceTextField.getText()));
					billingSetupService.saveProcedures(procedureDto);
					buildData();
				}
			} else
				FileUtils.privillegeEditError();
		} else {
			Notify notify = new Notify(AlertType.INFORMATION);
			notify.setContentText(MessageResource.getText("billing.setup.create.vat"));
			notify.showAndWait();
		}

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

		// Here we are creating printable table,If table size is large then left
		// data write on next page.
		List<Node> nodes = createPrintableTable(procedureTable.getColumns(), procedureTable.getItems(), pageLayout);

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

	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, IConstants.emptyString, pageLayout);
		root.setTop(headerHbox);

		// Setting the content at center of Border Pane
		VBox contentVBox = new VBox();
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

	public List<Node> createPrintableTable(ObservableList<TableColumn<UIProcedure, ?>> columns,
			Collection<UIProcedure> items, PageLayout pageLayout) {
		List<Node> nodes = null;
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = pageLayout.getPrintableWidth() / columnCount;
		if (null != items && items.size() > 0) {
			nodes = new ArrayList<Node>();
			for (UIProcedure invoice : items) {
				// elementHeight would be the height of each cell
				final double elementHeight = 15;
				// adding table on multiple pages
				// We are subtract 400 from totalPrintableHeight, here 400 =
				// (Page Header +
				// CompanyRefund Policy). So printableHeight - 400 = result
				// height will for table.
				if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 400) {
					tableVBox = new VBox();
					tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
					tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
					HBox tableHeader = createTableHeader(columns, labelWidth, pageLayout);
					// adding table columns in Table VBox
					tableVBox.getChildren().add(tableHeader);
					nodes.add(tableVBox);
					totalHeight = 0;
				}
				HBox row = createTableRow(invoice, labelWidth, pageLayout);
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
	public HBox createTableHeader(ObservableList<TableColumn<UIProcedure, ?>> columns, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setPrefHeight(20);
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIProcedure, ?> cols : columns) {
			// condition to avoid the last separator in table
			if (count == columns.size()) {
				printTemplate.addTableHeaderRowToHBox(headerHBox, cols.getText(), labelWidth);
			} else {
				printTemplate.addTableHeaderRowToHBoxWithSeparator(headerHBox, cols.getText(), labelWidth);
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
	 * @param pageLayout
	 *            the page layout
	 * @return the h box
	 */
	public HBox createTableRow(UIProcedure record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getId().getValue()),
				labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, record.getProcedureName().getValue(), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, record.getProcedurePrice().getValue(), labelWidth);
		printTemplate.addTableRowDataToHBoxWithSeparator(rowHBox, record.getProcedureCreateDate().getValue(),
				labelWidth);
		printTemplate.addTableRowDataToHBox(rowHBox, record.getCreatedBy().getValue(), labelWidth);
		return rowHBox;
	}

	private void resetFields() {
		procedureTable.getItems().clear();
		procedureNameTextField.setText(IConstants.emptyString);
		procedurePriceTextField.setText(IConstants.emptyString);

	}

}
