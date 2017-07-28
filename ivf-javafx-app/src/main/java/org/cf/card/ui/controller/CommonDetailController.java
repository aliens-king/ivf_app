package org.cf.card.ui.controller;

import java.io.File;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.service.UIClipartService;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CommonDetailController {

	private UIClipartService clipartService = new UIClipartService();

	@FXML
	private AnchorPane anchorPane;

	private Boolean backgroundColor;

	@FXML
	private TextField womanSurnameTextField;
	@FXML
	private TextField womanFirstNameTextField;
	@FXML
	private TextField womanMiddleNameTextField;
	@FXML
	private TextField womanHomeAddressTextField;
	@FXML
	private TextField womanEmailTextField;
	@FXML
	private TextField womanPhoneNumberTextField;
	@FXML
	private DatePicker womanDOBDatePicker;

	/*
	 * @FXML private TextField womanDateOfBirthTextField;
	 *
	 * @FXML private TextField womanAgeTextField;
	 */
	@FXML
	private TextField partnerSurnameTextField;
	@FXML
	private TextField partnerFirstNameTextField;
	@FXML
	private TextField partnerMiddleNameTextField;
	@FXML
	private TextField partnerHomeAddressTextField;
	@FXML
	private TextField partnerEmailTextField;
	@FXML
	private TextField partnerPhoneNumberTextField;
	@FXML
	private DatePicker partnerDOBDatePicker;

	@FXML
	private TextField womanCodeTextfield;

	@FXML
	private TextField partnerCodeTextfield;

	/*
	 * @FXML private TextField partnerDateOfBirthTextField;
	 *
	 * @FXML private TextField partnerAgeTextField;
	 */
	@FXML
	private Label imageLabel;

	@FXML
	private ImageView imageView;

	private Couple couple;
	@SuppressWarnings("unused")
	private MainApp mainApp;

	private Client client;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public Couple getCouple() {
		return couple;
	}

	/*
	 * public void setCouple(Couple couple) { this.couple = couple; try {
	 * Partner personal information
	 * partnerSurnameTextField.setText(couple.getMan().getSurname());
	 * partnerFirstNameTextField.setText(couple.getMan().getFirstName());
	 * partnerMiddleNameTextField.setText(couple.getMan().getMiddleName());
	 * partnerHomeAddressTextField.setText(couple.getMan().getHomeAddress());
	 * partnerEmailTextField.setText(couple.getMan().getEmail());
	 * partnerPhoneNumberTextField.setText(couple.getMan().getPhoneNumber());
	 * Date mDate = couple.getMan().getdOB(); LocalDate manDate =
	 * mDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	 * partnerDOBDatePicker.setValue(manDate); woman personal information
	 * womanSurnameTextField.setText(couple.getWoman().getSurname());
	 * womanFirstNameTextField.setText(couple.getWoman().getFirstName());
	 * womanMiddleNameTextField.setText(couple.getWoman().getMiddleName());
	 * womanHomeAddressTextField.setText(couple.getWoman().getHomeAddress());
	 * womanEmailTextField.setText(couple.getWoman().getEmail());
	 * womanPhoneNumberTextField.setText(couple.getWoman().getPhoneNumber());
	 * Date wDate = couple.getWoman().getdOB(); LocalDate womanDate =
	 * wDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	 * womanDOBDatePicker.setValue(womanDate);
	 * 
	 * if(couple.getWoman().getCodes().size()>0){ String womanCode =
	 * couple.getWoman().getCodes().get(couple.getWoman().getCodes().size()-1).
	 * getCode(); womanCodeTextfield.setText(womanCode); }
	 * if(couple.getMan().getCodes().size()>0){ String manCode =
	 * couple.getMan().getCodes().get(couple.getMan().getCodes().size()-1).
	 * getCode(); partnerCodeTextfield.setText(manCode); }
	 * 
	 * 
	 * 
	 * } catch (NullPointerException e) { e.printStackTrace(); }
	 * 
	 * try { File file = new File(clipartService.getImage(couple.getId()));
	 * Image image = new Image(file.toURI().toURL().toString());
	 * imageView.setImage(image); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	public void setWomanPersonalInfo(Codes womanCode) {
		Client woman = womanCode != null ? womanCode.getClient() : null;
		if (null != woman) {
			womanFirstNameTextField.setText(woman.getFirstName());
			womanMiddleNameTextField.setText(woman.getMiddleName());
			womanSurnameTextField.setText(woman.getSurname());
			womanCodeTextfield.setText(womanCode.getCode());

			try {
				File file = new File(clipartService.getImage(woman.getCouple().getId()));
				Image image = new Image(file.toURI().toURL().toString());
				imageView.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setPartnerPersonalInfo(Codes partnerCode) {
		Client partner = partnerCode != null ? partnerCode.getClient() : null;
		if (null != partner) {
			partnerFirstNameTextField.setText(partner.getFirstName());
			partnerMiddleNameTextField.setText(partner.getMiddleName());
			partnerSurnameTextField.setText(partner.getSurname());
			partnerCodeTextfield.setText(partnerCode.getCode());
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Boolean getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Boolean backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Couple save() {
		couple.getWoman().setSurname(womanSurnameTextField.getText());
		couple.getWoman().setFirstName(womanFirstNameTextField.getText());
		couple.getWoman().setMiddleName(womanMiddleNameTextField.getText());
		/*
		 * couple.getWoman().setHomeAddress(womanHomeAddressTextField.getText())
		 * ;
		 * couple.getWoman().setPhoneNumber(womanPhoneNumberTextField.getText())
		 * ; couple.getWoman().setEmail(womanEmailTextField.getText());
		 * java.util.Date womanDOB =
		 * java.util.Date.from(womanDOBDatePicker.getValue().atStartOfDay(ZoneId
		 * .systemDefault()).toInstant()); couple.getWoman().setdOB(womanDOB);
		 */

		couple.getMan().setSurname(partnerSurnameTextField.getText());
		couple.getMan().setFirstName(partnerFirstNameTextField.getText());
		couple.getMan().setMiddleName(partnerMiddleNameTextField.getText());
		/*
		 * couple.getMan().setHomeAddress(partnerHomeAddressTextField.getText())
		 * ;
		 * couple.getMan().setPhoneNumber(partnerPhoneNumberTextField.getText())
		 * ; couple.getMan().setEmail(partnerEmailTextField.getText());
		 * java.util.Date manDOB =
		 * java.util.Date.from(partnerDOBDatePicker.getValue().atStartOfDay(
		 * ZoneId.systemDefault()).toInstant()); couple.getMan().setdOB(manDOB);
		 */
		return couple;
	}

	@FXML
	void initialize() {
		/* Set non editable field */
		partnerSurnameTextField.setEditable(false);
		partnerMiddleNameTextField.setEditable(false);
		partnerFirstNameTextField.setEditable(false);
		/*
		 * partnerHomeAddressTextField.setEditable(false);
		 * partnerEmailTextField.setEditable(false);
		 * partnerPhoneNumberTextField.setEditable(false);
		 * partnerDOBDatePicker.setEditable(false);
		 */

		womanSurnameTextField.setEditable(false);
		womanMiddleNameTextField.setEditable(false);
		womanFirstNameTextField.setEditable(false);
		/*
		 * womanHomeAddressTextField.setEditable(false);
		 * womanEmailTextField.setEditable(false);
		 * womanPhoneNumberTextField.setEditable(false);
		 * womanDOBDatePicker.setEditable(false);
		 */

	}

}
