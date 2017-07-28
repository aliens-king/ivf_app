package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.ProcedureDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.Client;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIProcedure;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIRefundAndInvoiceService;
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
 * @since : Jan 3, 2017
 */
public class RefundAndInvoiceController extends BaseController {

	private static final Logger logger = Logger.getLogger(RefundAndInvoiceController.class);
	// getting all message from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.billing.refund.invoice");
	private static final String iconURL = "/icons/refund_overall.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// Binding FXML elements
	@FXML
	private TextField refundInvoiceNumberTextField;
	@FXML
	private TextField refundDateTextField;
	@FXML
	private TextField refundSubTotalTextField;
	@FXML
	private TextField refundVatTextField;
	@FXML
	private TextField refundTotalBillTextField;
	@FXML
	private TextArea remarksTextArea;
	@FXML
	private Label vatLabel;
	@FXML
	private CommonDetailController commonDetailController;
	@FXML
	private TableView<UIProcedure> proceduresTableView;
	@FXML
	private TableColumn<UIProcedure, Long> refundSno;
	@FXML
	private TableColumn<UIProcedure, String> refundProcedureName;
	@FXML
	private TableColumn<UIProcedure, String> refundProcedurePrice;

	// Instance level Variable
	private static final String REMARKS_SAVE_INFO = MessageResource.getText("refund.bill.remarks.save");
	private RefundInvoiceDto refundInvoiceDto;
	private ObservableList<UIProcedure> refundObservableList;
	private UIRefundAndInvoiceService refundAndInvoiceService = new UIRefundAndInvoiceService();
	private PrintTemplate<RefundInvoiceDto> printTemplate = new PrintTemplate<>();

	/* maintaining the session data for RefundAndInvoice And CompanyDetails */
	private SessionObject<String, Long> sessionObject = null;
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	@SuppressWarnings("unchecked")
	public void buildData() {
		resetField();
		sessionObject = Session.getInstance().getSessionObject();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		Long refundInvoiceNumber = null;
		if (null != sessionObjectForCompany || null != sessionObject) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
			refundInvoiceNumber = sessionObject.getComponent(Constants.REFUND_INVOICE);
		}
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		populatedRefundTable(refundInvoiceNumber);
		makeFieldsNonEditable();

	}

	@FXML
	private void editRemarksAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.REFUND_AND_INVOICE.getKey())) {
			logger.info("Inside editRemarksAction of RefundAndInvoice Screen.");
			makeFieldsEditable();
		} else
			FileUtils.privillegeEditError();

	}

	/**
	 * Save remarks action.
	 */
	@FXML
	private void saveRemarksAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.REFUND_AND_INVOICE.getKey())) {
			logger.info("Inside saveRemarksAction of RefundAndInvoice Screen.");
			if (null != refundInvoiceDto) {
				if (!StringUtils.isEmpty(remarksTextArea.getText())) {
					refundInvoiceDto.setRemarks(remarksTextArea.getText());
					refundAndInvoiceService.updateRefundInvoicRemarks(refundInvoiceDto);
				}
			} else {
				Notify alert = new Notify(AlertType.INFORMATION, REMARKS_SAVE_INFO);
				alert.showAndWait();
				resetField();
				makeFieldsNonEditable();
			}
		} else
			FileUtils.privillegeEditError();

	}

	@FXML
	private void refundAddProcedureAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.REFUND_AND_INVOICE.getKey())) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setResources(MessageResource.getResourceBundle());
				loader.setLocation(MainApp.class.getResource("/view/popups/AddProceduresPopup.fxml"));
				BorderPane addProcedurePopupPane = (BorderPane) loader.load();
				AddProceduresPopupController addProceduresPopupController = loader.getController();
				addProceduresPopupController.setMainApp(mainApp);
				addProceduresPopupController.setLogin(login);
				addProceduresPopupController.setCouple(couple);
				addProceduresPopupController.setModuleKey(Module.REFUND_AND_INVOICE.getKey());
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

	@FXML
	private void refundsAction() {
		LoadPanels.loadModule(mainApp, login, Module.REFUND_BILL_PER_COUPLE.getKey(), null);
	}

	public void populatedRefundTable(Long refundInvoiceNumber) {
		List<ProcedureDto> listOfProceduresRefund = null;
		if (null != refundInvoiceNumber) {
			refundInvoiceDto = refundAndInvoiceService.findRefundAndInvoiceByInvoiceNumber(refundInvoiceNumber);
		} else {
			refundInvoiceDto = refundAndInvoiceService.getLatestRefundAndInvoicesByCoupleId(couple.getId());
			if (null != refundInvoiceDto) {
				@SuppressWarnings("unchecked")
				SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.REFUND_INVOICE, refundInvoiceDto.getRefundInvoiceNumber());
			}
		}

		if (null != refundInvoiceDto && refundInvoiceDto.getProcedureDto().size() > 0) {
			String currencyType = companyDto.getBillingCurrency();
			String vatLabelValue = "(" + companyDto.getBillingVAT() + "%)";
			vatLabel.setText("VAT"+vatLabelValue);
			refundInvoiceNumberTextField.setText(String.valueOf(refundInvoiceDto.getRefundInvoiceNumber()));
			refundDateTextField
					.setText(Util.formatDate(IConstants.DATE_FORMAT, refundInvoiceDto.getRefundCreateDate()));
			refundSubTotalTextField.setText(currencyType + String.valueOf(refundInvoiceDto.getSubTotal()));
			refundVatTextField.setText(currencyType + Util
					.getValueUpto2Decimal(Util.vatAmount(refundInvoiceDto.getSubTotal(), refundInvoiceDto.getVat())));
			refundTotalBillTextField.setText(currencyType + String.valueOf(refundInvoiceDto.getTotalRefundBill()));
			remarksTextArea.setText(refundInvoiceDto.getRemarks());
			listOfProceduresRefund = refundInvoiceDto.getProcedureDto();
			refundObservableList = FXCollections.observableArrayList();
			List<UIProcedure> column = new ArrayList<>();
			listOfProceduresRefund.forEach(new Consumer<ProcedureDto>() {
				int count = 1;

				@Override
				public void accept(ProcedureDto t) {
					Long srNo = new Long(count);
					UIProcedure refundProcedure = new UIProcedure(srNo, t.getProcedureName(),
							companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(t.getProcedurePrice()));

					column.add(refundProcedure);
					count++;
				}
			});
			refundObservableList.setAll(column);
			try {
				refundSno.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
				refundProcedureName.setCellValueFactory(cellData -> cellData.getValue().getProcedureName());
				refundProcedurePrice.setCellValueFactory(cellData -> cellData.getValue().getProcedurePrice());
			} catch (Exception e) {
				e.printStackTrace();
			}
			proceduresTableView.setItems(refundObservableList);
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

	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, IConstants.emptyString, pageLayout);
		root.setTop(headerHbox);

		VBox unuseSpace1 = new VBox();
		unuseSpace1.setPrefHeight(10);
		VBox unuseSpace2 = new VBox();
		unuseSpace2.setPrefHeight(10);
		VBox unuseSpace3 = new VBox();
		unuseSpace3.setPrefHeight(10);
		VBox unuseSpace4 = new VBox();
		unuseSpace4.setPrefHeight(10);
		VBox unuseSpace5 = new VBox();
		unuseSpace5.setPrefHeight(10);
		VBox unuseSpace6 = new VBox();
		unuseSpace6.setPrefHeight(10);

		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		contentVBox.getChildren().add(patientGrid);
		contentVBox.getChildren().add(unuseSpace1);
		Client woman = womanCode.getClient();
		HBox addressDetail = printTemplate.createAddressDetail(woman.getHomeAddress() + "\n Contact No.:"
				+ woman.getPhoneNumber() + "\n Age : " + Util.getAge(woman.getdOB()), pageLayout);
		contentVBox.getChildren().add(addressDetail);
		contentVBox.getChildren().add(unuseSpace2);
		HBox invoiceNoAndDate = printTemplate.createParentHBoxForInvoiceNoAndDate(pageLayout, refundInvoiceDto);
		if (null != invoiceNoAndDate) {
			contentVBox.getChildren().add(invoiceNoAndDate);
			contentVBox.getChildren().add(unuseSpace3);
		}
		contentVBox.getChildren().add(table);
		contentVBox.getChildren().add(unuseSpace4);
		HBox invoiceVatSubTotalAndTotal = printTemplate.createParentHBoxForVatSubTotalAndTotal(pageLayout,
				refundInvoiceDto, companyDto.getBillingCurrency());
		if (null != invoiceVatSubTotalAndTotal) {
			contentVBox.getChildren().add(invoiceVatSubTotalAndTotal);
			contentVBox.getChildren().add(unuseSpace5);
		}

		VBox billingInvoiceRemarksVBox = printTemplate.createInvoiceRemarks(pageLayout, refundInvoiceDto);
		if (null != billingInvoiceRemarksVBox) {
			contentVBox.getChildren().add(billingInvoiceRemarksVBox);
			contentVBox.getChildren().add(unuseSpace6);
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

	public void makeFieldsNonEditable() {
		refundInvoiceNumberTextField.setEditable(false);
		refundDateTextField.setEditable(false);
		refundSubTotalTextField.setEditable(false);
		refundTotalBillTextField.setEditable(false);
		refundVatTextField.setEditable(false);
		remarksTextArea.setEditable(false);
	}

	public void makeFieldsEditable() {
		remarksTextArea.setEditable(true);
	}

	private void resetField() {
		proceduresTableView.getItems().clear();
		refundInvoiceNumberTextField.setText(IConstants.emptyString);
		refundDateTextField.setText(IConstants.emptyString);
		refundSubTotalTextField.setText(IConstants.emptyString);
		refundVatTextField.setText(IConstants.emptyString);
		refundTotalBillTextField.setText(IConstants.emptyString);
		remarksTextArea.setText(IConstants.emptyString);
		vatLabel.setText(IConstants.emptyString);
		refundInvoiceDto = null;
	}

}
