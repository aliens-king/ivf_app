package org.cf.card.ui.controller;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.model.UIPrintListPatient;
import org.cf.card.ui.service.UIClipartService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * Created by Dell on 5/12/2015.
 */
public class RecentArchiveEntityController {

	@FXML
	private Label womanSurname;
	@FXML
	private Button button;

	private MainApp mainApp;
	private User login;
	private Label administratorWarningLabel;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	private UIPrintListPatient uiPrintListPatient;
	private UIClipartService clipartService = new UIClipartService();

	public UIPrintListPatient getUiPrintListPatient() {
		return uiPrintListPatient;
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	public void setUiPrintListPatient(UIPrintListPatient uiPrintListPatient) {
		this.uiPrintListPatient = uiPrintListPatient;
		womanSurname.setText(uiPrintListPatient.getWomanTreatment().getCodes().getClient().getSurname());

		javafx.scene.image.Image image;

		File file = new File(clipartService
				.getImage(uiPrintListPatient.getPartnerTreatment().getCodes().getClient().getCouple().getId()));

		try {
			image = new javafx.scene.image.Image(file.toURI().toURL().toString(), 98, 41, false, false);
			button.setGraphic(new javafx.scene.image.ImageView(image));
			button.setPadding(new Insets(4, 4, 4, 4));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	public void buttonAction(ActionEvent actionEvent) {
		FXMLLoader loader = new FXMLLoader();
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
			controller.setCouple(uiPrintListPatient.getWomanTreatment().getCodes().getClient().getCouple());
			controller.setManCode(uiPrintListPatient.getPartnerTreatment().getCodes());
			controller.setWomanCode(uiPrintListPatient.getWomanTreatment().getCodes());
		} catch (IOException e) {

		}
	}
}
