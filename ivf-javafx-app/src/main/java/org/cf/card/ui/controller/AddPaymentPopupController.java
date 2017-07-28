package org.cf.card.ui.controller;

import java.util.Date;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.PaymentDto;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIPaymentService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.validation.FormValidator;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Pramod Maurya
 * @since : Dec 30, 2016
 */
public class AddPaymentPopupController extends BaseController {

	// Binding FXML elements
	@FXML
	private TextField invoiceNumberTextField;
	@FXML
	private TextField totalBillTextField;
	@FXML
	private TextField totalPaidTextField;
	@FXML
	private TextField totalBalanceTextField;
	@FXML
	private TextField addPaymentTextField;
	@FXML
	private TextField paymentModeTextField;
	@FXML
	private Text addPaymentErrorText;
	@FXML
	private Text paymentModeErrorText;
	@FXML
	private Button closeButton;
	@FXML
	private Button saveButton;
	@FXML
	private Label paymentCompleteLabel;

	// Instance variable declaration
	private BillingInvoiceDto billingInvoiceDto;
	private UIPaymentService uIPaymentService = new UIPaymentService();

	/* maintaining the session data for CompanyDetails */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	@FXML
	void initialize() {
		// This regex is only for Enter Integer or Float value
		addPaymentTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^([+-]?\\d*\\.?\\d*)$")) {
					addPaymentTextField.setText(newValue.replaceAll("[^([+-]?\\d*\\.?\\d]", ""));
				}
			}
		});

	}

	/**
	 * Builds the data.
	 */
	@SuppressWarnings("unchecked")
	public void buildData() {
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		if (null != billingInvoiceDto) {
			Float totalBill = billingInvoiceDto.getTotalBill();
			Float totalPaid = billingInvoiceDto.getTotalPaid();
			invoiceNumberTextField.setText(String.valueOf(billingInvoiceDto.getInvoiceNumber()));
			totalBillTextField.setText(
					companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBill()));
			totalPaidTextField.setText(
					companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalPaid()));
			totalBalanceTextField.setText(
					companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBalance()));
			makeTextFieldNonEditable();
			if (totalBill.equals(totalPaid)) {
				makeTextFieldNonEditableAfterTotalBillPaid();
				paymentCompleteLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16;");
				paymentCompleteLabel.setText(MessageResource.getText("payment.completed"));
				saveButton.setDisable(true);
			}
		}
	}

	/**
	 * Save payment action.
	 */
	@FXML
	private void savePaymentAction() {
		int ok = 1;
		ok = FormValidator.validateAddPaymentFields(addPaymentTextField, paymentModeTextField, addPaymentErrorText,
				paymentModeErrorText);
		if (ok == 1) {
			if (billingInvoiceDto.getTotalBalance() >= Float.parseFloat(addPaymentTextField.getText())) {
				PaymentDto paymentDto = new PaymentDto();
				Float totalBill = billingInvoiceDto.getTotalBill();
				Float totalPaid = billingInvoiceDto.getTotalPaid();
				Float addPayment = Float.parseFloat(addPaymentTextField.getText());
				Float totalBalance = totalBill - (totalPaid + addPayment);
				paymentDto.setTotalBill(totalBill);
				paymentDto.setPaidBill(Float.parseFloat(addPaymentTextField.getText()));
				paymentDto.setBalance(totalBalance);
				paymentDto.setPaymentDate(new Date());
				paymentDto.setPaymentMode(paymentModeTextField.getText());
				paymentDto.setPaymentBilledBy(login.getFirstName());
				paymentDto.setBillingInvoiceId(billingInvoiceDto.getId());
				paymentDto.setCoupleId(couple.getId());
				uIPaymentService.saveInvoicePayment(paymentDto);
				buildData();
				Stage stage = (Stage) closeButton.getScene().getWindow();
				stage.close();
				LoadPanels.loadModule(mainApp, login, Module.BILLING_AND_INVOICE.getKey(), null);
			} else {
				Notify notify = new Notify(AlertType.INFORMATION);
				notify.setContentText(MessageResource.getText("payment.extra.alert"));
				notify.showAndWait();
			}

		}

	}

	/**
	 * Make text field non editable.
	 */
	private void makeTextFieldNonEditable() {
		invoiceNumberTextField.setEditable(false);
		totalBillTextField.setEditable(false);
		totalPaidTextField.setEditable(false);
		totalBalanceTextField.setEditable(false);

	}

	/**
	 * Make text field non editable after total bill paid.
	 */
	private void makeTextFieldNonEditableAfterTotalBillPaid() {
		addPaymentTextField.setEditable(false);
		paymentModeTextField.setEditable(false);

	}

	public void resetFields() {
		invoiceNumberTextField.setText(IConstants.emptyString);
		totalBillTextField.setText(IConstants.emptyString);
		totalPaidTextField.setText(IConstants.emptyString);
		totalBalanceTextField.setText(IConstants.emptyString);
		addPaymentTextField.setText(IConstants.emptyString);
		paymentModeTextField.setText(IConstants.emptyString);
		addPaymentErrorText.setText(IConstants.emptyString);
		paymentModeErrorText.setText(IConstants.emptyString);
		paymentCompleteLabel.setText(IConstants.emptyString);
		billingInvoiceDto = null;

	}

	public BillingInvoiceDto getBillingInvoiceDto() {
		return billingInvoiceDto;
	}

	public void setBillingInvoiceDto(BillingInvoiceDto billingInvoiceDto) {
		this.billingInvoiceDto = billingInvoiceDto;
	}

	@FXML
	public void handleCloseButton(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

}
