package org.cf.card.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UILogin;
import org.cf.card.ui.service.UILoginService;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission.Role;

import static java.util.stream.Collectors.toList;

/**
 * Created by Dell on 5/7/2015.
 */
public class AddAccounts extends BaseController {
	@FXML
	private javafx.scene.control.Button deleteSelectedButton;
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
	private ComboBox<Role> typeComboBox;
	@FXML
	private TableColumn<UILogin, String> surnameColumn;
	@FXML
	private TableColumn<UILogin, String> firstNameColumn;
	@FXML
	private TableColumn<UILogin, String> homeAddressColumn;
	@FXML
	private TableColumn<UILogin, String> countryColumn;
	@FXML
	private TableColumn<UILogin, String> typeColumn;
	@FXML
	private TableColumn<UILogin, String> emailColumn;
	@FXML
	private javafx.scene.control.TableView<UILogin> loginsTable;
	@FXML
	private Label attentionLabel;
	@FXML
	private Label passwordAttentionLabel;

	private ObservableList<UILogin> data;

	private ObservableList<Role> roles;

	private UILoginService loginService = new UILoginService();

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;

	}

	@FXML
	void initialize() {
		
		buildData();
		surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		homeAddressColumn.setCellValueFactory(cellData -> cellData.getValue().homeAdressProperty());
		countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

	}

	public void buildData() {
		data = FXCollections.observableArrayList();
		java.util.List<User> dbLogins = loginService.getLogins();
		java.util.List<UILogin> logins = dbLogins.stream().map(UILogin::new).collect(toList());
		try {
			roles = FXCollections.observableArrayList();
			// allRoles contains all the values for roles including Super User
			Role allRoles[] = Role.values();

			// rolesToDisplay has only those role values that we have to display
			// in the DropDown
			Role rolesToDisplay[] = new Role[allRoles.length - 1];
			for (int i = 0; i < allRoles.length - 1; i++) {

				rolesToDisplay[i] = allRoles[i + 1];
			}
			roles.addAll(rolesToDisplay);
			typeComboBox.setItems(roles);
			typeComboBox.getSelectionModel().select(0);
			// logins has all the values of user in database .

			// Here ----- User Data to Display in Table.
			// we donot have to display the Super User Data in table.

			// List of users to display
			java.util.List<UILogin> usersToDisplay = new java.util.ArrayList<UILogin>();
			for (UILogin login : logins) {
				if (!(login.getSurname().equals("INSONIX")))
					usersToDisplay.add(login);
			}
			data.addAll(usersToDisplay);
			loginsTable.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	public void saveButtonAction(ActionEvent actionEvent) {
		int ok = 1;
		if (surname.getText().equals("")) {
			surname.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			ok = 0;
		} else
			surname.setStyle("-fx-background-color: #ffffff");
		if (firstName.getText().equals("")) {
			firstName.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			ok = 0;
		} else
			firstName.setStyle("-fx-background-color: #ffffff");
		if (email.getText().equals("")) {
			email.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			ok = 0;
		} else
			email.setStyle("-fx-background-color: #ffffff");
		if (homeAddress.getText().equals("")) {
			homeAddress.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			ok = 0;
		} else
			homeAddress.setStyle("-fx-background-color: #ffffff");
		if (country.getText().equals("")) {
			country.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			ok = 0;
		} else
			country.setStyle("-fx-background-color: #ffffff");
		;
		if (password.getText().equals("")) {
			password.setStyle("-fx-background-color: #B73630; -fx-text-fill: #ffffff;");
			ok = 0;
		} else
			password.setStyle("-fx-background-color: #ffffff");

		for (int i = 0; i < data.size(); i++)
			if (password.getText().equals(data.get(i).getPassword())) {
				passwordAttentionLabel.setText(MessageResource.getText("addaccounts.label.password.taken"));
				ok = 0;
			}

		if (ok == 1) {
			User login = new User();
			login.setSurname(surname.getText());
			login.setFirstName(firstName.getText());
			login.setEmail(email.getText());
			login.setAddress(homeAddress.getText());
			login.setCountry(country.getText());
			login.setLoginCode(password.getText());
			// login.setType(typeComboBox.getSelectionModel().getSelectedItem().toString());
			login.setRoleId(typeComboBox.getSelectionModel().getSelectedItem().getKey());

			loginService.save(login);
			surname.setText("");
			firstName.setText("");
			email.setText("");
			homeAddress.setText("");
			country.setText("");
			password.setText("");
			passwordAttentionLabel.setText("");
			buildData();

		}
	}

	public void deleteSelectedAction(ActionEvent actionEvent) {
		 if(login.getRoleId()== 1) {
			 if (loginsTable.getSelectionModel().getSelectedItem().getId() != login.getId()) {
					loginService.delete((long) loginsTable.getSelectionModel().getSelectedItem().getId());
					attentionLabel.setText("");
					buildData();
				} else
					attentionLabel.setText(MessageResource.getText("addaccounts.label.message"));
		 }else {
				FileUtils.privillegeEditError();
			}
		
	}
}
