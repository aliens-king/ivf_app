package org.cf.card.ui.controller;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.cf.card.model.Couple;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIPrintListPatient;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * Created by Dell on 5/11/2015.
 */
public class PatientListEntityController extends BaseController {

	@FXML
	private Label womanSurname;
	@FXML
	private Label womanFirstName;
	@FXML
	private Label partnerSurname;
	@FXML
	private Label partnerFirstName;
	@FXML
	private Text numberText;
	@FXML
	private Label codeLabel;
	/*
	 * @FXML private Label secondCharacter;
	 * 
	 * @FXML private Label thirdCharacter;
	 */
	@FXML
	private Button button;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private CheckBox allCheckBox;
	@FXML
	private CheckBox fileCheckBox;
	@FXML
	private CheckBox biopsyCheckBox;
	@FXML
	private CheckBox iuiCheckBox;
	@FXML
	private CheckBox strawsCheckBox;
	@FXML
	private CheckBox straweCheckBox;
	@FXML
	private CheckBox bmCheckBox;
	@FXML
	private CheckBox semenPotCheckBox;
	@FXML
	private CheckBox mainCheckBox;
	@SuppressWarnings("rawtypes")
	@FXML
	private Spinner spinner;

	private UIPrintListPatient uiPrintListPatient;
	private UIClipartService clipartService = new UIClipartService();

	public UIPrintListPatient getUiPrintListPatient() {
		return uiPrintListPatient;
	}

	public PatientListEntityController getController() {
		return this;
	}

	@SuppressWarnings("unchecked")
	public void setUiPrintListPatient(UIPrintListPatient uiPrintListPatient) {
		this.uiPrintListPatient = uiPrintListPatient;
		womanSurname.setText(uiPrintListPatient.getWomanTreatment().getCodes().getClient().getSurname()
				.substring(0, Math
						.min(uiPrintListPatient.getWomanTreatment().getCodes().getClient().getSurname().length(), 15))
				+ ",");
		womanFirstName.setText((uiPrintListPatient.getWomanTreatment().getCodes().getClient().getFirstName() + " "
				+ uiPrintListPatient.getWomanTreatment().getCodes().getClient().getMiddleName()).substring(
						0,
						Math.min((uiPrintListPatient.getWomanTreatment().getCodes().getClient().getFirstName() + " "
								+ uiPrintListPatient.getWomanTreatment().getCodes().getClient().getMiddleName())
										.length(),
								20)));
		partnerSurname.setText(uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getSurname()
				.substring(0, Math
						.min(uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getSurname().length(), 15))
				+ ",");
		partnerFirstName
				.setText(
						(uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getFirstName().substring(0,
								Math.min(uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getFirstName()
										.length(), 20))
								+ " " + uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getMiddleName())
										.substring(0, Math.min(uiPrintListPatient.getPartnerTreatment().getCodes()
												.getClient().getMiddleName().length(), 20)));
		// firstCharacter.setText(String.valueOf(uiPrintListPatient.getWomanTreatment().getCodes().getCode().charAt(0)));

		codeLabel.setText(String.valueOf(uiPrintListPatient.getWomanTreatment().getCodes().getCode()));
		// secondCharacter.setText(String.valueOf(uiPrintListPatient.getWomanTreatment().getCodes().getCode().charAt(1)));
		// thirdCharacter.setText(String.valueOf(uiPrintListPatient.getWomanTreatment().getCodes().getCode().charAt(2)));

		javafx.scene.image.Image image;

		File file = new File(clipartService
				.getImage(uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getCouple().getId()));

		try {
			image = new javafx.scene.image.Image(file.toURI().toURL().toString(), 98, 40, false, false);
			button.setGraphic(new javafx.scene.image.ImageView(image));
			button.setPadding(new Insets(1, 1, 1, 1));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		spinner.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, Integer.parseInt(String.valueOf(0))));
		spinner.getEditor().setFont(new javafx.scene.text.Font("Open Sans", 18));
		spinner.getEditor().setAlignment(Pos.CENTER);
		spinner.setEditable(true);
		EventHandler<KeyEvent> enterKeyEventHandler;

		enterKeyEventHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				// handle users "enter key event"

				try {
					// yes, using exception for control is a bad solution ;-)
					Integer.parseInt(spinner.getEditor().textProperty().get());
				} catch (NumberFormatException e) {

					spinner.getEditor().setText(spinner.getEditor().getText().replaceAll("\\D+", ""));
				}

			}
		};
		spinner.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, enterKeyEventHandler);
		spinner.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, enterKeyEventHandler);
		spinner.getEditor().addEventHandler(KeyEvent.KEY_TYPED, enterKeyEventHandler);
		uiPrintListPatient.setSpinner(spinner);
	}

	public void setNumberText(String numberText) {
		this.numberText.setText(numberText);
	}

	public void checkBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setCheckbox(!uiPrintListPatient.isCheckbox());
		if (mainCheckBox.isSelected()) {
			allCheckBox.setSelected(false);
			uiPrintListPatient.setFile(false);
			fileCheckBox.setSelected(false);
			uiPrintListPatient.setBiopsy(false);
			biopsyCheckBox.setSelected(false);
			uiPrintListPatient.setIui(false);
			iuiCheckBox.setSelected(false);
			uiPrintListPatient.setStraws(false);
			strawsCheckBox.setSelected(false);
			uiPrintListPatient.setStrawe(false);
			straweCheckBox.setSelected(false);
			uiPrintListPatient.setBm(false);
			bmCheckBox.setSelected(false);
			uiPrintListPatient.setSemenPot(false);
			semenPotCheckBox.setSelected(false);
		}
	}

	@SuppressWarnings("unused")
	public void buttonAction(ActionEvent actionEvent) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/PatientFile.fxml"));
		try {
			loader.setResources(MessageResource.getResourceBundle());
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

			/* maintaining the session data for couple object */
			@SuppressWarnings("unchecked")
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
			Couple couple = uiPrintListPatient.getWomanTreatment().getCodes().getClient().getCouple();
			sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple.getId());
			sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY,
					uiPrintListPatient.getWomanTreatment().getCodes().getId());
			sessionObject.setComponent(Constants.MANCODE_SESSION_KEY,
					uiPrintListPatient.getPartnerTreatment().getCodes().getId());
			// List<Codes> aoWomanCode = couple.getWoman().getCodes();
			// if(!aoWomanCode.isEmpty()){
			// Codes womanCode = aoWomanCode.get(aoWomanCode.size()-1);
			// sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY,
			// womanCode!=null ? womanCode.getId():0l);
			// }else{
			// sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, 0l);
			// }
			// List<Codes> aoManCode = couple.getMan().getCodes();
			// if(!aoManCode.isEmpty()){
			// Codes manCode = aoManCode.get(aoManCode.size()-1);
			// sessionObject.setComponent(Constants.MANCODE_SESSION_KEY,manCode
			// !=null ? manCode.getId():0l);
			// }else{
			// sessionObject.setComponent(Constants.MANCODE_SESSION_KEY,0l);
			// }

			controller.setCouple(uiPrintListPatient.getWomanTreatment().getCodes().getClient().getCouple());
			controller.setManCode(uiPrintListPatient.getPartnerTreatment().getCodes());
			controller.setWomanCode(uiPrintListPatient.getWomanTreatment().getCodes());
		} catch (IOException e) {

		}

	}

	public void allAction(ActionEvent actionEvent) {
		mainCheckBox.setSelected(false);

		fileCheckBox.setSelected(allCheckBox.isSelected());
		uiPrintListPatient.setFile(allCheckBox.isSelected());
		biopsyCheckBox.setSelected(allCheckBox.isSelected());
		uiPrintListPatient.setIui(allCheckBox.isSelected());
		iuiCheckBox.setSelected(allCheckBox.isSelected());
		uiPrintListPatient.setStraws(allCheckBox.isSelected());
		strawsCheckBox.setSelected(allCheckBox.isSelected());
		uiPrintListPatient.setStrawe(allCheckBox.isSelected());
		straweCheckBox.setSelected(allCheckBox.isSelected());
		uiPrintListPatient.setBm(allCheckBox.isSelected());
		bmCheckBox.setSelected(allCheckBox.isSelected());
		uiPrintListPatient.setSemenPot(allCheckBox.isSelected());
		semenPotCheckBox.setSelected(allCheckBox.isSelected());

	}

	public void fileCheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setFile(!uiPrintListPatient.isFile());
		mainCheckBox.setSelected(false);
	}

	public void strawECheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setStrawe(!uiPrintListPatient.isStrawe());
		mainCheckBox.setSelected(false);
	}

	public void biopsyCheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setBiopsy(!uiPrintListPatient.isBiopsy());
		mainCheckBox.setSelected(false);
	}

	public void strawsCheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setStraws(!uiPrintListPatient.isStraws());
		mainCheckBox.setSelected(false);
	}

	public void iuiCheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setIui(!uiPrintListPatient.isIui());
		mainCheckBox.setSelected(false);
	}

	public void bmCheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setBm(!uiPrintListPatient.isBm());
		mainCheckBox.setSelected(false);
	}

	public void semenPotCheckBoxAction(ActionEvent actionEvent) {
		uiPrintListPatient.setSemenPot(!uiPrintListPatient.isSemenPot());
		mainCheckBox.setSelected(false);
	}

	public void unselectAll() {
		mainCheckBox.setSelected(false);
		allCheckBox.setSelected(false);
		fileCheckBox.setSelected(false);
		uiPrintListPatient.setFile(false);
		biopsyCheckBox.setSelected(false);
		uiPrintListPatient.setIui(false);
		iuiCheckBox.setSelected(false);
		uiPrintListPatient.setStraws(false);
		strawsCheckBox.setSelected(false);
		uiPrintListPatient.setStrawe(false);
		straweCheckBox.setSelected(false);
		uiPrintListPatient.setBm(false);
		bmCheckBox.setSelected(false);
		uiPrintListPatient.setSemenPot(false);
		semenPotCheckBox.setSelected(false);
	}
}
