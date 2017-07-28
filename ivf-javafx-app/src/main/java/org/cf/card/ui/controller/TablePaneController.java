package org.cf.card.ui.controller;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIClient;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.service.UICodesService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.util.Callback;

public class TablePaneController {

	@FXML
	private TableView<UIClient> patientsTable;
	@FXML
	private TableColumn<UIClient, Boolean> markedColumn;
	@FXML
	private TableColumn<UIClient, String> surnameColumn;
	@FXML
	private TableColumn<UIClient, String> middleNameColumn;
	@FXML
	private TableColumn<UIClient, String> otherNamesColumn;
	@FXML
	private TableColumn<UIClient, String> dateOfBirthColumn;
	@FXML
	private TableColumn<UIClient, String> ageColumn;
	@FXML
	private TableColumn<UIClient, String> genderColumn;
	@FXML
	private TableColumn<UIClient, String> coupleColumn;
	@FXML
	private TableColumn<UIClient, Number> appointmentColumn;
	@FXML
	private javafx.scene.control.TextField searchPatientsByName;
	@FXML
	private javafx.scene.control.TextField searchPatientsByCode;

	@FXML
	private TextField totalCoupleCount;

	private ObservableList<UIClient> data;
	private User login;
	private Label administratorWarningLabel;

	UIClientService clientService = new UIClientService();
	UICoupleService coupleService = new UICoupleService();
	UICodesService codesService = new UICodesService();

	// Reference to the main application.
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	private Callback<TableColumn<UIClient, Boolean>, TableCell<UIClient, Boolean>> booleanCellFactory = new Callback<TableColumn<UIClient, Boolean>, TableCell<UIClient, Boolean>>() {
		@Override
		public TableCell<UIClient, Boolean> call(TableColumn<UIClient, Boolean> p) {
			return new org.cf.card.ui.model.BooleanCell();
		}
	};

	private Callback<TableColumn<UIClient, Number>, TableCell<UIClient, Number>> buttonCellFactory = new Callback<TableColumn<UIClient, Number>, TableCell<UIClient, Number>>() {
		@Override
		public TableCell<UIClient, Number> call(TableColumn<UIClient, Number> param) {
			ButtonCellShedule buttonCellShedule = new ButtonCellShedule();
			return buttonCellShedule;
		}
	};

	// create inner class for custom buttons inside column(Appointment)
	class ButtonCellShedule extends TableCell<UIClient, Number> {
		// create new Button object
		Button cellButton = new Button(MessageResource.getText("tablepane.button.schedule"));

		public ButtonCellShedule() {
			cellButton.setOnAction(new ScheduleAppointment());
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Number item, boolean empty) {
			super.updateItem(item, empty);
			/*
			 * if(item !=null){ if(item.intValue() >0) setGraphic(cellButton);
			 * else setGraphic(null); }
			 */
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}

	}

	@FXML
	void initialize() {
		buildData();
		surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
		middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
		otherNamesColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
		dateOfBirthColumn.setCellValueFactory(cellData -> cellData.getValue().dOBProperty());
		genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
		coupleColumn.setCellValueFactory(cellData -> cellData.getValue().coupleProperty());
		appointmentColumn.setCellValueFactory(cellData -> cellData.getValue().appointmentProperty());
		appointmentColumn.setCellFactory(buttonCellFactory);
		markedColumn.setCellValueFactory(cellData -> cellData.getValue().checkProperty());
		markedColumn.setCellFactory(booleanCellFactory);
	}

	public void buildData() {
		data = FXCollections.observableArrayList();
		java.util.List<Client> dbClients = clientService.getClients();
		List<UIClient> clients = new ArrayList<>();
		for (int i = 0; i < dbClients.size(); i++) {
			clients.add(new UIClient(dbClients.get(i)));
		}
		try {
			data.addAll(clients);
			patientsTable.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
		if (dbClients.size() > 0) {
			totalCoupleCount.setText("TOTAL  = " + dbClients.size() / 2 + " couples");
			// totalCoupleCount.setStyle("-fx-font-size: 14px;");
			totalCoupleCount.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, 16));
		}

	}

	@SuppressWarnings("unused")
	public void tableKeyPressed(KeyEvent key) {
		if (key.getCode() == KeyCode.ENTER) {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(MessageResource.getResourceBundle());
			loader.setLocation(MainApp.class.getResource("/view/PatientFile.fxml"));
			try {

				BorderPane personOverview = (BorderPane) loader.load();
				Group group = new Group(personOverview);

				group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
				group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
				group.setLayoutX(-personOverview.getPrefWidth() * ((1 - group.getScaleX()) / 2 + 0.02));
				group.setLayoutY(-personOverview.getPrefHeight() * ((1 - group.getScaleY()) / 2 + 0.01));
				Scene scene = new Scene(group);
				mainApp.getPatientFileStage().setScene(scene);
				scene.getStylesheets().add(MainApp.class.getResource("/CSS/PatientFile.css").toExternalForm());
				Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
				mainApp.getPatientFileStage().setX(0);
				mainApp.getPatientFileStage().setY(0);

				mainApp.getPatientFileStage().show();
				PatientFileController controller = loader.getController();
				controller.setMainApp(mainApp);
				controller.setLogin(login);
				controller.setCouple(patientsTable.getSelectionModel().getSelectedItem().getClient().getCouple());
			} catch (IOException e) {

			}

		}
	}

	public void deleteSelectedAction(ActionEvent actionEvent) {
		if (login.getRoleId() == 1) {
			Client partner = new Client();
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getCheck()) {
					coupleService.deleteCouple(data.get(i).getClient().getCouple().getId());
					String male = MessageResource.getText("uiclient.service.combo.male");
					String semenDonor = MessageResource.getText("uiclient.service.combo.male.donor");
					String eggDonor = MessageResource.getText("uiclient.service.combo.female.donor");

					if (data.get(i).getClient().getGender().equals(male)
							|| data.get(i).getClient().getGender().equals(semenDonor)
							|| data.get(i).getClient().getGender().equals(eggDonor))
						partner = data.get(i).getClient().getCouple().getWoman();
					else
						partner = data.get(i).getClient().getCouple().getMan();
					data.remove(i);
					for (int j = 0; j < data.size(); j++) {
						if (data.get(j).getClient().equals(partner))
							data.remove(j);
					}

				}
			}
			administratorWarningLabel.setText("");
		} else {
			// administratorWarningLabel.setText("You are not an
			// administrator!");
			FileUtils.privillegeEditError();
		}
	}

	public void markAllAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.SEARCH_BY_NAME_CODE.getKey())) {
			for (int i = 0; i < data.size(); i++) {
				data.get(i).setCheck(true);
			}
		} else {
			FileUtils.privillegeEditError();
		}
	}

	public void unmarkAllAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.SEARCH_BY_NAME_CODE.getKey())) {
			for (int i = 0; i < data.size(); i++) {
				data.get(i).setCheck(false);
			}
		} else {
			FileUtils.privillegeEditError();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (patientsTable.getSelectionModel().isSelected(patientsTable.getSelectionModel().getSelectedIndex(),
				markedColumn) && e.getX() <= markedColumn.getWidth()) {
			data.get(patientsTable.getSelectionModel().getSelectedIndex())
					.setCheck(!patientsTable.getSelectionModel().getSelectedItem().getCheck());
		} else if (e.getClickCount() == 2 && patientsTable.getSelectionModel().getSelectedIndex() != -1) {
			Couple couple = patientsTable.getSelectionModel().getSelectedItem().getClient().getCouple();
			LoadPanels.patientFileDialog(couple, mainApp, login);
			// We are setting BillingInvoice and RefundInvoice Null when we
			// change the patient Session
			@SuppressWarnings("unchecked")
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
			sessionObject.setComponent(Constants.BILLING_INVOICE, null);
			sessionObject.setComponent(Constants.REFUND_INVOICE, null);
		}
	}

	public void searchPatientsByNameAction(ActionEvent actionEvent) {
		buildData();
		String s = searchPatientsByName.getText();
		CharSequence charSequence = s.toLowerCase();
		for (int i = 0; i < data.size(); i++) {
			if (!data.get(i).toString().toLowerCase().contains(charSequence)) {
				data.remove(i);
				i--;
			}
		}
	}

	public void typeAction(KeyEvent key) {
		buildData();
		String s = searchPatientsByName.getText();
		CharSequence charSequence = s.toLowerCase();
		for (int i = 0; i < data.size(); i++) {
			if (!data.get(i).toString().toLowerCase().contains(charSequence)) {
				data.remove(i);
				i--;
			}
		}
	}

	@SuppressWarnings("unused")
	public void searchPatientsByCodeAction(ActionEvent actionEvent) {

		Codes code = clientService.findCode(searchPatientsByCode.getText());
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(MessageResource.getResourceBundle());
		loader.setLocation(MainApp.class.getResource("/view/PatientFile.fxml"));
		try {

			BorderPane personOverview = (BorderPane) loader.load();
			Group group = new Group(personOverview);
			group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
			group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
			group.setLayoutX(-personOverview.getPrefWidth() * ((1 - group.getScaleX()) / 2 + 0.02));
			group.setLayoutY(-personOverview.getPrefHeight() * ((1 - group.getScaleY()) / 2 + 0.01));
			Scene scene = new Scene(group);
			mainApp.getPatientFileStage().setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("/CSS/PatientFile.css").toExternalForm());
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			mainApp.getPatientFileStage().setX(0);
			mainApp.getPatientFileStage().setY(0);

			mainApp.getPatientFileStage().show();
			PatientFileController controller = loader.getController();
			controller.setMainApp(mainApp);
			controller.setLogin(login);
			controller.setCouple(code.getClient().getCouple());
			searchPatientsByCode.setStyle("-fx-background-color: #ffffff");
			administratorWarningLabel.setText("");
		} catch (IOException e) {

		} catch (java.lang.NullPointerException e) {
			searchPatientsByCode.setStyle("-fx-background-color: #B73630;-fx-text-fill: #ffffff;");
			mainApp.getPatientFileStage().close();
		}

	}

	class ScheduleAppointment implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (EnumPermission.canWrite(login.getRoleId(), Module.SCHEDULE_APPOINTMENT.getKey())) {
				try {

					FXMLLoader loader = new FXMLLoader();
					loader.setResources(MessageResource.getResourceBundle());
					loader.setLocation(MainApp.class.getResource("/view/AppointmentPopup.fxml"));
					BorderPane appointmentPopup = (BorderPane) loader.load();

					Group group = new Group(appointmentPopup);
					Scene scene = new Scene(group);
					mainApp.getAppointmentPopup().setScene(scene);
					mainApp.getAppointmentPopup().centerOnScreen();
					mainApp.getAppointmentPopup().show();
					// List<Client> clients=clientService.getClients();
					ButtonCellShedule cell = null;
					UIClient obj = null;

					if (event.getTarget() instanceof Button) {
						Button button = (Button) event.getTarget();
						cell = (ButtonCellShedule) button.getParent();
						obj = (UIClient) cell.getTableRow().getItem();
						Codes latestCodeId = codesService.getLatestCodeByClientId(obj.getId());
						AppointmentPopupController appointmentPopupController = loader.getController();
						appointmentPopupController.setLatestCodeId(latestCodeId);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				FileUtils.privillegeEditError();
		}
	}
}
