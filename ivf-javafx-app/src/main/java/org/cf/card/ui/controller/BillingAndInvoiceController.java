package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.ProcedureDto;
import org.cf.card.model.Client;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIProcedure;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Pramod Maurya
 * @since : Dec 30, 2016
 */
public class BillingAndInvoiceController extends BaseController {

	private static final Logger logger = Logger.getLogger(BillingAndInvoiceController.class);
	// getting all message from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.billing.invoice");
	private static final String iconURL = "/icons/billing_and_invoice.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// binding fxml elements
	@FXML
	private TextField invoiceNoTextField;
	@FXML
	private TextField invoiceCreateDate;
	@FXML
	private TextField subTotalTextField;
	@FXML
	private TextField vatTextField;
	@FXML
	private TextField totalBillTextField;
	@FXML
	private TextArea remarksTextArea;
	@FXML
	private Label vatLabel;
	@FXML
	private CommonDetailController commonDetailController;
	@FXML
	private TableView<UIProcedure> proceduresTableView;
	@FXML
	private TableColumn<UIProcedure, Long> sno;
	@FXML
	private TableColumn<UIProcedure, String> procedureName;
	@FXML
	private TableColumn<UIProcedure, String> procedurePrice;

	// Instance level Variable
	private static final String REMARKS_SAVE_INFO = MessageResource.getText("refund.bill.remarks.save");
	private BillingInvoiceDto billingInvoiceDto;
	private ObservableList<UIProcedure> observableList;
	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();
	private PrintTemplate<BillingInvoiceDto> printTemplate = new PrintTemplate<>();

	/* maintaining the session data for BillingAndInvoice and CompanyDetail */
	private SessionObject<String, Long> sessionObjectForBillingInvoice = null;
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	/**
	 * Builds the data.
	 */
	@SuppressWarnings("unchecked")
	public void buildData() {
		resetField();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		sessionObjectForBillingInvoice = Session.getInstance().getSessionObject();
		Long invoiceNumber = null;
		if (null != sessionObjectForCompany || null != sessionObjectForBillingInvoice) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
			invoiceNumber = sessionObjectForBillingInvoice.getComponent(Constants.BILLING_INVOICE);
		}
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		populateTable(invoiceNumber);
		makeFieldsNonEditable();
	}

	/**
	 * Adds the procedure action. This method popups a AddProcedure Screen and
	 * Polulate all procedures for selection.
	 */
	@FXML
	private void addProcedureAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.BILLING_AND_INVOICE.getKey())) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setResources(MessageResource.getResourceBundle());
				loader.setLocation(MainApp.class.getResource("/view/popups/AddProceduresPopup.fxml"));
				BorderPane addProcedurePopupPane = (BorderPane) loader.load();
				AddProceduresPopupController addProceduresPopupController = loader.getController();
				addProceduresPopupController.setMainApp(mainApp);
				addProceduresPopupController.setLogin(login);
				addProceduresPopupController.setCouple(couple);
				addProceduresPopupController.setModuleKey(Module.BILLING_AND_INVOICE.getKey());
				addProceduresPopupController.buildData();
				Group group = new Group(addProcedurePopupPane);
				Scene scene = new Scene(group);
				mainApp.getAddProcedurePopup().setScene(scene);
				mainApp.getAddProcedurePopup().centerOnScreen();
				mainApp.getAddProcedurePopup().show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			FileUtils.privillegeEditError();

	}

	/**
	 * Populate table. This method load latest BillingInvoive if any and
	 * populate to TableView of screen.
	 */
	public void populateTable(Long invoiceNumber) {
		List<ProcedureDto> listOfProcedures = null;
		if (null != invoiceNumber) {
			billingInvoiceDto = billingAndInvoiceService.findBillingAndInvoiceByInvoiceNumber(invoiceNumber);
		} else {
			billingInvoiceDto = billingAndInvoiceService.getLatestBillingInvoiceObjectByCoupleId(couple.getId());
			if (null != billingInvoiceDto) {
				@SuppressWarnings("unchecked")
				SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.BILLING_INVOICE, billingInvoiceDto.getInvoiceNumber());
			}
		}

		if (null != billingInvoiceDto && billingInvoiceDto.getProcedureDto().size() > 0) {
			vatLabel.setText(IConstants.emptyString);
			String currencyType = companyDto.getBillingCurrency();
			String vatLabelValue = "(" + billingInvoiceDto.getVat() + "%)";
			vatLabel.setText("VAT"+vatLabelValue);
			invoiceNoTextField.setText(String.valueOf(billingInvoiceDto.getInvoiceNumber()));
			invoiceCreateDate
					.setText(Util.formatDate(IConstants.DATE_FORMAT, billingInvoiceDto.getInvoiceCreateDate()));
			subTotalTextField.setText(currencyType + Util.getValueUpto2Decimal(billingInvoiceDto.getSubTotal()));
			totalBillTextField.setText(currencyType + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBill()));
			vatTextField.setText(currencyType + Util
					.getValueUpto2Decimal(Util.vatAmount(billingInvoiceDto.getSubTotal(), billingInvoiceDto.getVat())));
			remarksTextArea.setText(billingInvoiceDto.getRemarks());
			listOfProcedures = billingInvoiceDto.getProcedureDto();
			observableList = FXCollections.observableArrayList();
			List<UIProcedure> col = new ArrayList<>();
			listOfProcedures.forEach(new Consumer<ProcedureDto>() {
				int count = 1;

				@Override
				public void accept(ProcedureDto t) {
					Long srNo = new Long(count);
					UIProcedure procedure = new UIProcedure(srNo, t.getProcedureName(),
							currencyType + Util.getValueUpto2Decimal(t.getProcedurePrice()), null);
					col.add(procedure);
					count++;
				}

			});
			observableList.setAll(col);
			try {
				sno.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
				procedureName.setCellValueFactory(cellData -> cellData.getValue().getProcedureName());
				procedurePrice.setCellValueFactory(cellData -> cellData.getValue().getProcedurePrice());
			} catch (Exception e) {
				e.printStackTrace();
			}
			proceduresTableView.setItems(observableList);
		}else {
			
		}
	}

	/**
	 * Adds the payment action. This method popups a AddPayment Screen and User
	 * set payment from this popup.
	 */
	@FXML
	private void addPaymentAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.BILLING_AND_INVOICE.getKey())) {
			if (null != billingInvoiceDto) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setResources(MessageResource.getResourceBundle());
					loader.setLocation(MainApp.class.getResource("/view/popups/AddPaymentPopup.fxml"));
					BorderPane addPaymentPopup = (BorderPane) loader.load();
					AddPaymentPopupController addPaymentPopupController = loader.getController();
					// We are rest field before set BillingInvoiceDto, So old
					// billingInvoice will remove before add new.
					addPaymentPopupController.resetFields();
					addPaymentPopupController.setMainApp(mainApp);
					addPaymentPopupController.setLogin(login);
					addPaymentPopupController.setCouple(couple);
					addPaymentPopupController.setBillingInvoiceDto(billingInvoiceDto);
					addPaymentPopupController.buildData();
					Group group = new Group(addPaymentPopup);
					Scene scene = new Scene(group);
					mainApp.getAddPaymentPopup().setScene(scene);
					mainApp.getAddPaymentPopup().centerOnScreen();
					mainApp.getAddPaymentPopup().show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("add.payment.alert.create.invoice"));
				alert.showAndWait();
			}
		} else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void paymentDetailsAction() {
		LoadPanels.loadModule(mainApp, login, Module.PAYMENT_DETAILS.getKey(), null);
	}

	@FXML
	private void refundAndInvoiveAction() {
		LoadPanels.loadModule(mainApp, login, Module.REFUND_AND_INVOICE.getKey(), null);
	}

	@FXML
	private void unpaidBillAction() {
		LoadPanels.loadModule(mainApp, login, Module.UNPAID_BILL_PER_COUPLE.getKey(), null);

	}

	@FXML
	private void invoicesAction() {
		LoadPanels.loadModule(mainApp, login, Module.INVOICES_DETAIL.getKey(), null);
	}

	@FXML
	private void editRemarksAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.BILLING_AND_INVOICE.getKey())) {
			logger.info("Inside editRemarksAction of BillingAndInvoice Screen.");
			makeFieldsEditable();
		} else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void saveRemarksAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.BILLING_AND_INVOICE.getKey())) {
			logger.info("Inside saveRemarksAction of BillingAndInvoice Screen.");
			if (null != billingInvoiceDto) {
				if (!StringUtils.isEmpty(remarksTextArea.getText())) {
					billingInvoiceDto.setRemarks(remarksTextArea.getText());
					billingAndInvoiceService.updateBillingInvoicRemarks(billingInvoiceDto);
				}
			} else {
				Notify alert = new Notify(AlertType.INFORMATION, REMARKS_SAVE_INFO);
				alert.showAndWait();
				remarksTextArea.setText(IConstants.emptyString);
			}
		} else
			FileUtils.privillegeEditError();

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

		// Here we are creating printable table,If table size is large then left
		// data write on next page.
		List<Node> nodes = printTemplate.createPrintableTable(proceduresTableView.getColumns(),
				proceduresTableView.getItems(), pageLayout);

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
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, IConstants.emptyString, pageLayout);
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
		HBox invoiceNoAndDate = printTemplate.createParentHBoxForInvoiceNoAndDate(pageLayout, billingInvoiceDto);
		if (null != invoiceNoAndDate) {
			contentVBox.getChildren().add(invoiceNoAndDate);
			contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		}
		contentVBox.getChildren().add(table);
		contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		HBox invoiceVatSubTotalAndTotal = printTemplate.createParentHBoxForVatSubTotalAndTotal(pageLayout,
				billingInvoiceDto, companyDto.getBillingCurrency());
		if (null != invoiceVatSubTotalAndTotal) {
			contentVBox.getChildren().add(invoiceVatSubTotalAndTotal);
			contentVBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		}

		VBox billingInvoiceRemarksVBox = printTemplate.createInvoiceRemarks(pageLayout, billingInvoiceDto);
		if (null != billingInvoiceRemarksVBox) {
			contentVBox.getChildren().add(billingInvoiceRemarksVBox);
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
	 * Reset field.
	 */
	private void resetField() {
		proceduresTableView.getItems().clear();
		invoiceNoTextField.setText(IConstants.emptyString);
		invoiceCreateDate.setText(IConstants.emptyString);
		subTotalTextField.setText(IConstants.emptyString);
		vatTextField.setText(IConstants.emptyString);
		totalBillTextField.setText(IConstants.emptyString);
		remarksTextArea.setText(IConstants.emptyString);
		billingInvoiceDto = null;
	}

	/**
	 * Make fields editable.
	 */
	public void makeFieldsEditable() {
		remarksTextArea.setEditable(true);
	}

	/**
	 * Make fields non editable.
	 */
	public void makeFieldsNonEditable() {
		invoiceNoTextField.setEditable(false);
		invoiceCreateDate.setEditable(false);
		subTotalTextField.setEditable(false);
		totalBillTextField.setEditable(false);
		vatTextField.setEditable(false);
		remarksTextArea.setEditable(false);
	}

}
