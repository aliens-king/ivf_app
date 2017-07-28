package org.cf.card.ui.validation;

import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.util.Constants;
import org.springframework.util.StringUtils;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PatientValidator {

	public int validate(TextField partnerSurnameTextField, TextField partnerMiddleNameTextField,
			TextField partnerFirstNameTextField, TextField womanSurnameTextField, TextField womanMiddleNameTextField,
			TextField womanFirstNameTextField, TextField womanHomeAddressTextField, TextField womanPhoneNumberTextField,
			TextField womanEmailTextField, TextField partnerHomeAddressTextField, TextField partnerPhoneNumberTextField,
			TextField partnerEmailTextField, DatePicker womanDOBDatePicker, DatePicker partnerDOBDatePicker,
			Text womanSurnameErrorLabel, Text womanFirstNameErrorLabel, Text manSurnameErrorLabel,
			Text manFirstNameErrorLabel, Text womanPhoneNumberErrorLabel, Text womanEmailErrorLabel,
			Text manPhoneNumberErrorLabel, Text manEmailErrorLabel, Text womanDOBErrorLabel, Text manDOBErrorLabel) {
		int ok = 1;
		if (womanSurnameTextField.getText().equals("")) {
			womanSurnameTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			womanSurnameErrorLabel
					.setText(MessageResource.getText("patient.validator.common.surname.error.label.text"));
			ok = 0;
		} else {
			womanSurnameTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			womanSurnameErrorLabel.setText("");
		}
		if (womanFirstNameTextField.getText().equals("")) {
			womanFirstNameTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			womanFirstNameErrorLabel
					.setText(MessageResource.getText("patient.validator.common.firstname.error.label.text"));
			ok = 0;
		} else {
			womanFirstNameTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			womanFirstNameErrorLabel.setText("");
		}

		if (partnerSurnameTextField.getText().equals("")) {
			partnerSurnameTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			manSurnameErrorLabel.setText(MessageResource.getText("patient.validator.common.surname.error.label.text"));
			ok = 0;
		} else {
			partnerSurnameTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			manSurnameErrorLabel.setText("");
		}
		if (partnerFirstNameTextField.getText().equals("")) {
			partnerFirstNameTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			manFirstNameErrorLabel
					.setText(MessageResource.getText("patient.validator.common.firstname.error.label.text"));
			ok = 0;
		} else {
			partnerFirstNameTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			manFirstNameErrorLabel.setText("");
		}

		// if (womanPhoneNumberTextField.getText().equals("")) {
		// womanPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
		// womanPhoneNumberErrorLabel.setText("Phone Number required.");
		// ok = 0;
		// } // validate if phone number is numeric
		// else
		if (StringUtils.isEmpty(womanPhoneNumberTextField.getText())) {
			womanPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			womanPhoneNumberErrorLabel
					.setText(MessageResource.getText("patient.validator.common.phonenumber.required"));
			ok = 0;
		} else {
			if (!womanPhoneNumberTextField.getText().matches("\\d{10,14}")) {
				womanPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
				womanPhoneNumberErrorLabel
						.setText(MessageResource.getText("patient.validator.common.phonenumber.message"));
				ok = 0;
			} else {
				womanPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
				womanPhoneNumberErrorLabel.setText("");
			}
		}
		if (StringUtils.isEmpty(partnerPhoneNumberTextField.getText())) {
			partnerPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			manPhoneNumberErrorLabel.setText(MessageResource.getText("patient.validator.common.phonenumber.required"));
			ok = 0;
		} else {
			if (!partnerPhoneNumberTextField.getText().matches("\\d{10,14}")) {
				partnerPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
				manPhoneNumberErrorLabel
						.setText(MessageResource.getText("patient.validator.common.phonenumber.message"));
				ok = 0;
			} else {
				partnerPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
				manPhoneNumberErrorLabel.setText("");
			}
		}
		if (womanEmailTextField.getText().equals("")) {
			womanEmailTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			womanEmailErrorLabel.setText(MessageResource.getText("patient.validator.common.email.required"));
			ok = 0;
		} // validate if email is valid
		else if (!womanEmailTextField.getText()
				.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			womanEmailTextField.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			womanEmailErrorLabel.setText(MessageResource.getText("patient.validator.common.email.valid"));
			ok = 0;
		} else {
			womanEmailTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			womanEmailErrorLabel.setText("");
		}
		// if (partnerPhoneNumberTextField.getText().equals("")) {
		// partnerPhoneNumberTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
		// manPhoneNumberErrorLabel.setText("Phone Number required.");
		// ok = 0;
		// } else

		if (partnerEmailTextField.getText().equals("")) {
			partnerEmailTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			manEmailErrorLabel.setText(MessageResource.getText("patient.validator.common.email.required"));
			ok = 0;
		} else if (!partnerEmailTextField.getText()
				.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			partnerEmailTextField.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			manEmailErrorLabel.setText(MessageResource.getText("patient.validator.common.email.valid"));
			ok = 0;
		} else {
			partnerEmailTextField.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			manEmailErrorLabel.setText("");
		}

		if (womanDOBDatePicker.getValue() == null) {
			womanDOBDatePicker.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			womanDOBErrorLabel.setText(MessageResource.getText("patient.validator.common.dob.required"));
			ok = 0;
		} else {
			womanDOBDatePicker.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			womanDOBErrorLabel.setText("");
		}

		if (partnerDOBDatePicker.getValue() == null) {
			partnerDOBDatePicker.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
			manDOBErrorLabel.setText(MessageResource.getText("patient.validator.common.dob.required"));
			ok = 0;
		} else {
			partnerDOBDatePicker.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
			manDOBErrorLabel.setText("");
		}

		return ok;

	}

}
