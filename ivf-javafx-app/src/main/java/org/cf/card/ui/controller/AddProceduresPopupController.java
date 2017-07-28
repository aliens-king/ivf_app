package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.ProcedureDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.ProcedureBooleanCell;
import org.cf.card.ui.model.UIProcedure;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.service.UIBillingSetupService;
import org.cf.card.ui.service.UIRefundAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 30, 2016
 */
public class AddProceduresPopupController extends BaseController {

	// Binding fxml elements
	@FXML
	private Button closeButton;
	@FXML
	private TableView<UIProcedure> procedureTable;
	@FXML
	private TableColumn<UIProcedure, Long> sNo;
	@FXML
	private TableColumn<UIProcedure, String> procedureName;
	@FXML
	private TableColumn<UIProcedure, Boolean> selectBox;
	@FXML
	private TableColumn<UIProcedure, String> procedurePrice;

	// Instance variables
	private Map<Long, ProcedureDto> procedureDtoMap;
	private ObservableList<UIProcedure> observableList;
	private int moduleKey;
	private UIBillingSetupService billingSetupService = new UIBillingSetupService();
	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();
	private UIRefundAndInvoiceService refundAndInvoiceService = new UIRefundAndInvoiceService();

	/* maintaining the session data for and CompanyDetail */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	@SuppressWarnings("unchecked")
	public void buildData() {
		resetFields();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany)
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		populateDateInTable();
	}

	@FXML
	private void saveProcedureAction() {

		// companyDto = companyService.getCompanyDetails(1l);
		Float subTotal = new Float(0);
		List<ProcedureDto> procedureDtos = new ArrayList<ProcedureDto>();
		// String currencyType = companyDto.getBillingCurrency();
		if (null != observableList && observableList.size() > 0) {
			for (int i = 0; i < observableList.size(); i++) {
				if (observableList.get(i).getCheck()) {
					ProcedureDto procedureDto = procedureDtoMap.get(observableList.get(i).getId().get());
					String string = observableList.get(i).getProcedurePrice().get();
					string = string.replace(companyDto.getBillingCurrency(), "").trim();
					Float price = Float.parseFloat(string);
					subTotal = subTotal + price;
					procedureDtos.add(procedureDto);
				}
			}
		}

		if (null == companyDto) {
			Notify alert = new Notify(AlertType.WARNING, MessageResource.getText("billing.setup.create.vat"));
			alert.showAndWait();
		} else if (null== observableList || !(observableList.size() > 0)) {
			Notify alert = new Notify(AlertType.WARNING, MessageResource.getText("billing.setup.create.procedure"));
			alert.showAndWait();
		} else {

			if (Module.BILLING_AND_INVOICE.getKey() == moduleKey) {
				BillingInvoiceDto billingInvoiceDto = saveBillingInvoice(subTotal, procedureDtos);
				// We are setting Billing And Invoice Null(remove old billing
				// session) when we create new invoice
				@SuppressWarnings("unchecked")
				SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.BILLING_INVOICE, billingInvoiceDto.getInvoiceNumber());
				LoadPanels.loadModule(mainApp, login, Module.BILLING_AND_INVOICE.getKey(), null);
			} else if (Module.REFUND_AND_INVOICE.getKey() == moduleKey) {
				RefundInvoiceDto refundinvoicedto = saveRefundInvoice(subTotal, procedureDtos);
				// We are setting Refund And Invoice Null(remove old refund
				// session)
				// when we create new refundinvoice
				@SuppressWarnings("unchecked")
				SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.REFUND_INVOICE, refundinvoicedto.getRefundInvoiceNumber());
				LoadPanels.loadModule(mainApp, login, Module.REFUND_AND_INVOICE.getKey(), null);
			}
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
		}

	}

	/**
	 * Populate date in table.
	 */
	public void populateDateInTable() {
		List<ProcedureDto> dtos = billingSetupService.findAllProcedure();
		procedureDtoMap = new HashMap<>();
		if (null != dtos && dtos.size() > 0) {
			List<UIProcedure> col = new ArrayList<>();
			observableList = FXCollections.observableArrayList();
			dtos.forEach(new Consumer<ProcedureDto>() {
				@Override
				public void accept(ProcedureDto t) {
					procedureDtoMap.put(t.getId(), t);
					Float price = t.getProcedurePrice();
					String procedurPrice = Util.getValueUpto2Decimal(price);
					UIProcedure procedure = new UIProcedure(t.getId(), t.getProcedureName(),
							companyDto.getBillingCurrency() + procedurPrice, null);
					col.add(procedure);
				}

			});
			observableList.setAll(col);
			try {
				sNo.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
				procedureName.setCellValueFactory(cellData -> cellData.getValue().getProcedureName());
				procedurePrice.setCellValueFactory(cellData -> cellData.getValue().getProcedurePrice());
				selectBox.setCellValueFactory(cellData -> cellData.getValue().checkProperty());
				selectBox.setCellFactory(booleanCellFactory);

			} catch (Exception e) {
				e.printStackTrace();
			}
			procedureTable.setItems(observableList);
		}

	}

	/**
	 * Save billing invoice.
	 *
	 * @param subTotal
	 *            the sub total
	 * @param procedureDto
	 *            the procedure dto
	 */
	public BillingInvoiceDto saveBillingInvoice(Float subTotal, List<ProcedureDto> procedureDto) {

		int vatPercentage = companyDto.getBillingVAT();
		BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();
		billingInvoiceDto.setCreatedBy(login.getFirstName());
		billingInvoiceDto.setInvoiceCreateDate(new Date());
		billingInvoiceDto.setInvoiceNumber(Util.generateUID());
		billingInvoiceDto.setSubTotal(subTotal);
		Float totalBill = Util.calculateTotalBillWithVAT(subTotal, vatPercentage);
		billingInvoiceDto.setTotalBill(totalBill);
		billingInvoiceDto.setTotalBalance(totalBill);
		billingInvoiceDto.setCoupleId(couple.getId());
		billingInvoiceDto.setProcedureDto(procedureDto);
		billingInvoiceDto.setVat(vatPercentage);
		billingInvoiceDto = billingAndInvoiceService.saveBillingInvoiceWithProcedures(billingInvoiceDto);
		return billingInvoiceDto;

	}

	public RefundInvoiceDto saveRefundInvoice(Float subTotal, List<ProcedureDto> procedureDto) {
		int vatPercentage = companyDto.getBillingVAT();
		RefundInvoiceDto refundinvoicedto = new RefundInvoiceDto();
		refundinvoicedto.setCoupleId(couple.getId());
		refundinvoicedto.setCreatedBy(login.getFirstName());
		refundinvoicedto.setRefundInvoiceNumber(Util.generateUID());
		refundinvoicedto.setSubTotal(subTotal);
		Float totalBill = Util.calculateTotalBillWithVAT(subTotal, vatPercentage);
		refundinvoicedto.setRefundCreateDate(new Date());
		refundinvoicedto.setProcedureDto(procedureDto);
		refundinvoicedto.setTotalRefundBill(totalBill);
		refundinvoicedto.setVat(vatPercentage);
		refundinvoicedto = refundAndInvoiceService.saveRefundInvoiceProcedures(refundinvoicedto);
		return refundinvoicedto;
	}

	public long calculateTotalBill(Long subTotal, long vatAmount) {
		long totalamount = subTotal * vatAmount / 100;
		return totalamount;
	}

	/**
	 * Mouse clicked. This method is use for get selected check box event
	 * 
	 * @param e
	 *            the e
	 */
	public void mouseClicked(MouseEvent e) {
		if (procedureTable.getSelectionModel().isSelected(procedureTable.getSelectionModel().getSelectedIndex(),
				selectBox)) {
			observableList.get(procedureTable.getSelectionModel().getSelectedIndex())
					.setCheck(!procedureTable.getSelectionModel().getSelectedItem().getCheck());
		}
	}

	private Callback<TableColumn<UIProcedure, Boolean>, TableCell<UIProcedure, Boolean>> booleanCellFactory = new Callback<TableColumn<UIProcedure, Boolean>, TableCell<UIProcedure, Boolean>>() {
		@Override
		public TableCell<UIProcedure, Boolean> call(TableColumn<UIProcedure, Boolean> p) {
			return new ProcedureBooleanCell();
		}
	};

	@FXML
	public void handleCloseButton(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	public int getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(int moduleKey) {
		this.moduleKey = moduleKey;
	}

	private void resetFields() {
		procedureTable.getItems().clear();
		companyDto = null;
	}
}
