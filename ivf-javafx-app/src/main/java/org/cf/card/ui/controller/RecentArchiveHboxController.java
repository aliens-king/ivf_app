package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.model.UIPrintListPatient;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UITreatmentService;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by Dell on 5/12/2015.
 */
public class RecentArchiveHboxController {

	@FXML
	private Text date;
	@FXML
	private HBox hbox;

	UITreatmentService treatmentService = new UITreatmentService();
//	private String dataRand;
	private MainApp mainApp;
	private User login;
	@SuppressWarnings("unused")
	private List<UIPrintListPatient> printListPatients = new ArrayList<>();
	private javafx.scene.control.Label administratorWarningLabel;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public javafx.scene.control.Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(javafx.scene.control.Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	public int setPrintListPatients(List<UIPrintListPatient> printListPatients, int k) {
		this.printListPatients = printListPatients;
		hbox.getChildren().clear();
		for (int i = 0; i < printListPatients.size(); i++) {
			RecentArchiveEntityController controller = LoadPanels.getArchiveEntityLoader().get(k).getController();
			controller.setUiPrintListPatient(printListPatients.get(i));
			controller.setLogin(login);
			controller.setMainApp(mainApp);
			hbox.getChildren().add(LoadPanels.getArchiveEntityes().get(k));
			k++;
		}
		return k;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	public void setDataRand(String dataRand) {
		date.setText(dataRand);
	}

	@FXML
	public void initialize() {

	}

}
