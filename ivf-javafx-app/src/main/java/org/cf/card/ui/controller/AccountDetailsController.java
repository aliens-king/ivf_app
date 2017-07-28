package org.cf.card.ui.controller;

import org.cf.card.model.User;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.UILoginService;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * Created by Dell on 5/7/2015.
 */
public class AccountDetailsController extends BaseController {
	
	
	private static final String PASSWORD_EDIT_PRIVILEGES = MessageResource.getText("password.edit.privileges");

	@FXML
	private TextField surname;
	@FXML
	private TextField firstName;
	@FXML
	private TextField homeAddress;
	@FXML
	private TextField country;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private AnchorPane accountDetailsPane;

	private UILoginService loginService = new UILoginService();
	private Text mainPanelLoginName;

	public Text getMainPanelLoginName() {
		return mainPanelLoginName;
	}

	public void setMainPanelLoginName(Text mainPanelLoginName) {
		this.mainPanelLoginName = mainPanelLoginName;
	}

	public void initialize() {
		surname.setEditable(false);
		firstName.setEditable(false);
		email.setEditable(false);
		homeAddress.setEditable(false);
		country.setEditable(false);
		password.setEditable(false);
	}

	public void setLogin(User login) {
		this.login = login;
		surname.setText(login.getSurname());
		firstName.setText(login.getFirstName());
		email.setText(login.getEmail());
		homeAddress.setText(login.getAddress());
		country.setText(login.getCountry());
		password.setText(login.getLoginCode());
	}

	public void editAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.USER_DETAILS.getKey())) {
			surname.setEditable(true);
			firstName.setEditable(true);
			email.setEditable(true);
			homeAddress.setEditable(true);
			country.setEditable(true);
			password.setEditable(true);
			surname.requestFocus();
		} else {
			Notify notify = new Notify(AlertType.WARNING, PASSWORD_EDIT_PRIVILEGES);
			notify.showAndWait();
			surname.setEditable(false);
			firstName.setEditable(false);
			email.setEditable(false);
			homeAddress.setEditable(false);
			country.setEditable(false);
			password.setEditable(true);
			// FileUtils.privillegeEditError();
		}

	}

	public void saveButtonAction(ActionEvent actionEvent) {
			login.setSurname(surname.getText());
			login.setFirstName(firstName.getText());
			login.setEmail(email.getText());
			login.setAddress(homeAddress.getText());
			login.setCountry(country.getText());
			login.setLoginCode(password.getText());
			loginService.save(login);
			surname.setEditable(false);
			firstName.setEditable(false);
			email.setEditable(false);
			homeAddress.setEditable(false);
			country.setEditable(false);
			password.setEditable(false);
			
	}
}
