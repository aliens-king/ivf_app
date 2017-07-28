/**
 * 
 */
package org.cf.card.ui.validation;

import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.util.Constants;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author insonix
 *
 */
public class FormValidator {

	public int validateCompanyField(TextField companyFullName, TextArea companyAddress, TextArea companyMotive,
			TextField companyBillingCurrency, TextField companyBillingVAT, TextArea companyBillingRefundPolicy,
			Text companyNameErrorLabel, Text companyAddressErrorLabel, Text companyMotiveErrorLabel,
			Text companyBillingCurrencyErrorText, Text companyBillingVATErrorText,
			Text companyBillingRefundPolicyErrorText) {

		int ok = 1;
		if (companyFullName.getText().equals("")) {
			companyFullName.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			companyNameErrorLabel.setText(MessageResource.getText("company.validate.companyname.error.label.text"));
			companyNameErrorLabel.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			companyFullName.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			companyNameErrorLabel.setText("");
		}
		if (companyAddress.getText().equals("")) {
			// companyAddress.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			companyAddressErrorLabel
					.setText(MessageResource.getText("company.validate.companyaddress.error.label.text"));
			companyAddressErrorLabel.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			companyAddress.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			companyAddressErrorLabel.setText("");
		}
		if (companyMotive.getText().equals("")) {
			// companyMotive.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			companyMotiveErrorLabel.setText(MessageResource.getText("company.validate.companymotive.error.label.text"));
			companyMotiveErrorLabel.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			companyMotive.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			companyMotiveErrorLabel.setText("");
		}
		if (companyBillingCurrency.getText().equals("")) {
			// companyMotive.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			companyBillingCurrencyErrorText
					.setText(MessageResource.getText("company.validate.companymotive.error.label.text"));
			companyBillingCurrencyErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			companyBillingCurrency.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			companyBillingCurrencyErrorText.setText("");
		}
		if (companyBillingVAT.getText().equals("")) {
			// companyMotive.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			companyBillingVATErrorText
					.setText(MessageResource.getText("company.validate.companymotive.error.label.text"));
			companyBillingVATErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			companyBillingVAT.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			companyBillingVATErrorText.setText("");
		}
		if (companyBillingRefundPolicy.getText().equals("")) {
			// companyMotive.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			companyBillingRefundPolicyErrorText
					.setText(MessageResource.getText("company.validate.companymotive.error.label.text"));
			companyBillingRefundPolicyErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			companyBillingRefundPolicy.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			companyBillingRefundPolicyErrorText.setText("");
		}
		return ok;
	}

	public int validateBillingSetupField(TextField procedureName, TextField procedurePrice, Text procedureNameErrorText,
			Text procedurePriceErrorText) {

		int ok = 1;
		if (procedureName.getText().equals("")) {
			procedureName.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			procedureNameErrorText.setText(MessageResource.getText("billing.setup.validate.procedureName"));
			procedureNameErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			procedureName.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			procedureNameErrorText.setText("");
		}
		if (procedurePrice.getText().equals("")) {
			procedurePrice.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			procedurePriceErrorText.setText(MessageResource.getText("billing.setup.validate.procedurePrice"));
			procedurePriceErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			procedurePrice.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			procedurePriceErrorText.setText("");
		}
		return ok;
	}

	public static int validateAddPaymentFields(TextField addPaymentTextField, TextField paymentModeTextField,
			Text addPaymentErrorText, Text paymentModeErrorText) {
		int ok = 1;
		if (addPaymentTextField.getText().equals("")) {
			addPaymentTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			addPaymentErrorText.setText(MessageResource.getText("payment.amount"));
			addPaymentErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			addPaymentTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			addPaymentErrorText.setText("");
		}
		if (paymentModeTextField.getText().equals("")) {
			paymentModeTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			paymentModeErrorText.setText(MessageResource.getText("payment.method"));
			paymentModeErrorText.setStyle("-fx-font-size: 13");
			ok = 0;
		} else {
			paymentModeTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			paymentModeErrorText.setText("");
		}
		return ok;
	}
	
	
	/**
	 * Validate add registrant.
	 *
	 * @param nameOfUser the name of user
	 * @param assistantUser the assistant user
	 * @return the int
	 */
	public static int validateAddRegistrant(TextField nameOfUser, TextField assistantUser) {
		int ok = 1;
		if (nameOfUser.getText().equals("")) {
			nameOfUser.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			ok = 0;
		} else {
			nameOfUser.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
		}
		if (assistantUser.getText().equals("")) {
			assistantUser.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			ok = 0;
		} else {
			assistantUser.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
		}
		return ok;
	}
	

}
