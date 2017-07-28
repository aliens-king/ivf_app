package org.cf.card.ui.controller;

import java.text.ParseException;

import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.ui.validation.PatientValidator;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by Dell on 4/29/2015.
 */
public class AddPatientsController extends BaseController {

	/** The client service. */
	UIClientService clientService = new UIClientService();

	PatientValidator patientValidator = new PatientValidator();

	/** The woman surname text field. */
	@FXML
	private TextField womanSurnameTextField;

	/** The woman first name text field. */
	@FXML
	private TextField womanFirstNameTextField;

	/** The woman middle name text field. */
	@FXML
	private TextField womanMiddleNameTextField;

	/** The partner surname text field. */
	@FXML
	private TextField partnerSurnameTextField;

	/** The partner first name text field. */
	@FXML
	private TextField partnerFirstNameTextField;

	/** The partner middle name text field. */
	@FXML
	private TextField partnerMiddleNameTextField;

	/** The partner gender combo box. */
	@FXML
	private ComboBox<String> partnerGenderComboBox;

	/** The woman surname error label. */
	@FXML
	private Text womanSurnameErrorLabel;

	/** The woman middle name error label. */
	@FXML
	private Text womanMiddleNameErrorLabel;

	/** The woman first name error label. */
	@FXML
	private Text womanFirstNameErrorLabel;

	/** The man surname error label. */
	@FXML
	private Text manSurnameErrorLabel;

	/** The man middle name error label. */
	@FXML
	private Text manMiddleNameErrorLabel;

	/** The man first name error label. */
	@FXML
	private Text manFirstNameErrorLabel;

	/** The man gender error label. */
	@FXML
	private Text manGenderErrorLabel;

	/** The woman clear button. */
	@FXML
	private Button womanClearButton;

	/** The partner clear button. */
	@FXML
	private Button partnerClearButton;

	/** The save button. */
	@FXML
	private Button saveButton;

	/** The clear button. */
	@FXML
	private Button clearButton;

	/** The couple existent. */
	@FXML
	private Text coupleExistent;

	/** The woman home address. */
	@FXML
	private Text womanHomeAddress;

	/** The woman phone number. */
	@FXML
	private Text womanPhoneNumber;

	/** The woman email. */
	@FXML
	private Text womanEmail;

	/** The partner home address. */
	@FXML
	private Text partnerHomeAddress;

	/** The partner phone number. */
	@FXML
	private Text partnerPhoneNumber;

	/** The partner email. */
	@FXML
	private Text partnerEmail;

	// Added new fields Home Address, Phone Number, Email

	/** The woman home address text field. */
	@FXML
	private TextField womanHomeAddressTextField;

	/** The woman phone number text field. */
	@FXML
	private TextField womanPhoneNumberTextField;

	/** The woman email text field. */
	@FXML
	private TextField womanEmailTextField;

	/** The partner home address text field. */
	@FXML
	private TextField partnerHomeAddressTextField;

	/** The partner phone number text field. */
	@FXML
	private TextField partnerPhoneNumberTextField;

	/** The partner email text field. */
	@FXML
	private TextField partnerEmailTextField;

	@FXML
	private DatePicker womanDOBDatePicker;

	@FXML
	private DatePicker partnerDOBDatePicker;

	/** The woman phone number error label. */
	@FXML
	private Text womanPhoneNumberErrorLabel;

	/** The woman email error label. */
	@FXML
	private Text womanEmailErrorLabel;

	/** The man phone number error label. */
	@FXML
	private Text manPhoneNumberErrorLabel;

	/** The man email error label. */
	@FXML
	private Text manEmailErrorLabel;

	@FXML
	private Text womanDOBErrorLabel;

	@FXML
	private Text manDOBErrorLabel;

	/** The administrator warning label. */
	private Label administratorWarningLabel;

	/**
	 * Gets the administrator warning label.
	 *
	 * @return the administrator warning label
	 */
	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	/**
	 * Sets the administrator warning label.
	 *
	 * @param administratorWarningLabel
	 *            the new administrator warning label
	 */
	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {

		clientService.fillGenderComboBox(partnerGenderComboBox);
		partnerGenderComboBox.getSelectionModel().select(0);

	}

	/**
	 * Save action will saves patients data.
	 *
	 * @param actionEvent
	 *            the action event
	 * @throws ParseException
	 *             the parse exception
	 */
	public void saveAction(ActionEvent actionEvent) throws ParseException {
		if (EnumPermission.canWrite(login.getRoleId(), Module.REGISTER_PATIENT.getKey())) {
			int ok = 1;
			if (partnerGenderComboBox.getSelectionModel().getSelectedIndex() == 0) {
				partnerGenderComboBox.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
				manGenderErrorLabel.setText(MessageResource.getText("addpatient.label.man.gender.error"));
				ok = 0;
			} else {
				partnerGenderComboBox.setStyle(Constants.VALIDATE_FIELDS_PASS_STYLE);
				manGenderErrorLabel.setText("");
			}
			ok = patientValidator.validate(partnerSurnameTextField, partnerMiddleNameTextField,
					partnerFirstNameTextField, womanSurnameTextField, womanMiddleNameTextField, womanFirstNameTextField,
					womanHomeAddressTextField, womanPhoneNumberTextField, womanEmailTextField,
					partnerHomeAddressTextField, partnerPhoneNumberTextField, partnerEmailTextField, womanDOBDatePicker,
					partnerDOBDatePicker, womanSurnameErrorLabel, womanFirstNameErrorLabel, manSurnameErrorLabel,
					manFirstNameErrorLabel, womanPhoneNumberErrorLabel, womanEmailErrorLabel, manPhoneNumberErrorLabel,
					manEmailErrorLabel, womanDOBErrorLabel, manDOBErrorLabel);

			if (ok == 1) {
				try {
					if (clientService.checkCouple(partnerSurnameTextField, partnerMiddleNameTextField,
							partnerFirstNameTextField, womanSurnameTextField, womanMiddleNameTextField,
							womanFirstNameTextField, partnerGenderComboBox, womanHomeAddressTextField,
							womanPhoneNumberTextField, womanEmailTextField, partnerHomeAddressTextField,
							partnerPhoneNumberTextField, partnerEmailTextField, womanDOBDatePicker,
							partnerDOBDatePicker) == 0) {
						coupleExistent.setStyle("-fx-font-size: 17px;");
						coupleExistent.setText(MessageResource.getText("addpatient.label.couple.existent"));
						ok = 0;
					} else {
						coupleExistent.setText("");
					}
					if (ok == 1) {
						couple = clientService.saveAsCouple(partnerSurnameTextField, partnerMiddleNameTextField,
								partnerFirstNameTextField, womanSurnameTextField, womanMiddleNameTextField,
								womanFirstNameTextField, partnerGenderComboBox, womanHomeAddressTextField,
								womanPhoneNumberTextField, womanEmailTextField, partnerHomeAddressTextField,
								partnerPhoneNumberTextField, partnerEmailTextField, womanDOBDatePicker,
								partnerDOBDatePicker);
						LoadPanels.patientFileDialog(couple, mainApp, login);
						womanSurnameErrorLabel.setText("");
						womanFirstNameErrorLabel.setText("");
						womanDOBErrorLabel.setText("");
						manSurnameErrorLabel.setText("");
						manFirstNameErrorLabel.setText("");
						manGenderErrorLabel.setText("");
						womanPhoneNumberErrorLabel.setText("");
						womanEmailErrorLabel.setText("");
						manPhoneNumberErrorLabel.setText("");
						manEmailErrorLabel.setText("");
						manDOBErrorLabel.setText("");
						womanSurnameTextField.setText("");
						womanFirstNameTextField.setText("");
						womanMiddleNameTextField.setText("");
						womanHomeAddressTextField.setText("");
						womanPhoneNumberTextField.setText("");
						womanEmailTextField.setText("");
						womanDOBDatePicker.setValue(null);

						partnerSurnameTextField.setText("");
						partnerFirstNameTextField.setText("");
						partnerMiddleNameTextField.setText("");
						partnerGenderComboBox.getSelectionModel().select(-1);
						partnerHomeAddressTextField.setText("");
						partnerPhoneNumberTextField.setText("");
						partnerEmailTextField.setText("");
						partnerDOBDatePicker.setValue(null);
						// administratorWarningLabel.setText("");

					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else
			// administratorWarningLabel.setText("You are not an
			// administrator!");
			FileUtils.privillegeEditError();
	}

	/**
	 * Clear action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	public void clearAction(ActionEvent actionEvent) {
		womanSurnameErrorLabel.setText("");
		womanFirstNameErrorLabel.setText("");
		womanDOBErrorLabel.setText("");
		manSurnameErrorLabel.setText("");
		manFirstNameErrorLabel.setText("");
		manGenderErrorLabel.setText("");
		manDOBErrorLabel.setText("");
		womanSurnameTextField.setText("");
		womanFirstNameTextField.setText("");
		womanMiddleNameTextField.setText("");
		womanDOBDatePicker.setValue(null);

		partnerSurnameTextField.setText("");
		partnerFirstNameTextField.setText("");
		partnerMiddleNameTextField.setText("");
		partnerGenderComboBox.getSelectionModel().select(-1);

		womanHomeAddressTextField.setText("");
		womanPhoneNumberTextField.setText("");
		womanEmailTextField.setText("");
		partnerHomeAddressTextField.setText("");
		partnerPhoneNumberTextField.setText("");
		partnerEmailTextField.setText("");
		womanPhoneNumberErrorLabel.setText("");
		womanEmailErrorLabel.setText("");
		manPhoneNumberErrorLabel.setText("");
		manEmailErrorLabel.setText("");
		partnerDOBDatePicker.setValue(null);
	}

	/**
	 * Woman clear button action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	public void womanClearButtonAction(ActionEvent actionEvent) {
		womanSurnameErrorLabel.setText("");
		womanFirstNameErrorLabel.setText("");
		womanDOBErrorLabel.setText("");
		womanSurnameTextField.setText("");
		womanFirstNameTextField.setText("");
		womanMiddleNameTextField.setText("");
		womanHomeAddressTextField.setText("");
		womanPhoneNumberTextField.setText("");
		womanEmailTextField.setText("");
		womanPhoneNumberErrorLabel.setText("");
		womanEmailErrorLabel.setText("");
		womanDOBDatePicker.setValue(null);

	}

	/**
	 * Partner clear button action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	public void partnerClearButtonAction(ActionEvent actionEvent) {
		manSurnameErrorLabel.setText("");
		manFirstNameErrorLabel.setText("");
		manGenderErrorLabel.setText("");
		manDOBErrorLabel.setText("");
		partnerSurnameTextField.setText("");
		partnerFirstNameTextField.setText("");
		partnerMiddleNameTextField.setText("");
		partnerGenderComboBox.getSelectionModel().select(-1);
		partnerHomeAddressTextField.setText("");
		partnerPhoneNumberTextField.setText("");
		partnerEmailTextField.setText("");
		manPhoneNumberErrorLabel.setText("");
		manEmailErrorLabel.setText("");
	}
}
