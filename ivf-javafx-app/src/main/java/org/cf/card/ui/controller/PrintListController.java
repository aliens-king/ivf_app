package org.cf.card.ui.controller;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIPrintClient;
import org.cf.card.ui.model.UIPrintListPatient;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UITreatmentService;
import org.cf.card.ui.service.printing.PDFPrinter;
import org.cf.card.ui.service.printing.templates.FMDish;
import org.cf.card.ui.service.printing.templates.SpecialPot;
import org.cf.card.ui.service.printing.templates.Straw;
import org.cf.card.ui.service.printing.templates.braceletTemplate;
import org.cf.card.ui.service.printing.templates.fileTemplate;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by Dell on 5/11/2015.
 */
public class PrintListController {

	@FXML
	private VBox vbox;
	@FXML
	private ScrollPane scrPane;
	@FXML
	private ToggleButton minusOne;
	@FXML
	private ToggleButton zero;
	@FXML
	private ToggleButton one;
	@FXML
	private ToggleButton two;
	@FXML
	private ToggleButton three;
	@FXML
	private ToggleButton four;
	@FXML
	private ToggleButton five;
	@FXML
	private ToggleButton six;
	@FXML
	private ToggleButton seven;
	@FXML
	private ToggleButton eight;
	@FXML
	private ToggleButton nine;
	@FXML
	private Text dateLabel;

	UITreatmentService treatmentService = new UITreatmentService();
	List<FXMLLoader> loaders = new ArrayList<>();
	private User login;
	private Label administratorWarningLabel;

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void setLogin(User login) {
		this.login = login;
		minusOneAction(null);
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	public void minusOneAction(ActionEvent actionEvent) {

		minusOne.setSelected(true);
		loaders.clear();
		vbox.getChildren().clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String pastDate = treatmentService.getPastDateString(0);
		treatmentService.refreshTreatmentsByDate(pastDate);
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {
				FXMLLoader loader = LoadPanels.getPatientListEntityLoader().get(j);
				PatientListEntityController controller = loader.getController();

				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(loader);
				j++;
				controller.unselectAll();
			}
		}

	}

	public void zeroAction(ActionEvent actionEvent) {
		zero.setSelected(true);
		loaders.clear();
		vbox.getChildren().clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-1);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void oneAction(ActionEvent actionEvent) {
		one.setSelected(true);
		loaders.clear();
		vbox.getChildren().clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-2);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void twoAction(ActionEvent actionEvent) {
		two.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-3);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void threeAction(ActionEvent actionEvent) {
		three.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-4);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void fourAction(ActionEvent actionEvent) {
		four.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-5);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void fiveAction(ActionEvent actionEvent) {
		five.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-6);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void sixAction(ActionEvent actionEvent) {
		six.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-7);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void sevenAction(ActionEvent actionEvent) {
		seven.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-8);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;
		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void eightAction(ActionEvent actionEvent) {
		eight.setSelected(true);
		vbox.getChildren().clear();
		loaders.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		String pastDate = treatmentService.getPastDateString(-9);
		dateLabel.setText(pastDate);
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
		int j = 0;

		for (int i = 0; i < printListPatients.size(); i++) {
			if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate())).equals(pastDate)) {

				PatientListEntityController controller = LoadPanels.getPatientListEntityLoader().get(j).getController();
				controller.setUiPrintListPatient(printListPatients.get(i));
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				vbox.getChildren().add(LoadPanels.patientListEntityes.get(j));
				LoadPanels.patientListEntityes.get(j).setMinWidth(11 * minusOne.getWidth() - 30);
				if (j % 2 == 0)
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 0)));
				else
					controller.setNumberText(hour.format(new Date(200, 02, 10, 8 + j / 2, 30)));

				loaders.add(LoadPanels.getPatientListEntityLoader().get(j));
				j++;
				controller.unselectAll();
			}
		}
	}

	public void nineAction(ActionEvent actionEvent) {
		dateLabel.setText("RECENT ARCHIVE");
		nine.setSelected(true);
		vbox.getChildren().clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		treatmentService.refreshTreatments();
		java.util.List<UIPrintListPatient> printListPatients = treatmentService.getPrintListPatients();
		java.util.List<UIPrintListPatient> patientsPerDay = new ArrayList<>();
		int k = 0;
		int g = 0;
		for (int j = 0; j < 100; j++) {
			patientsPerDay.clear();

			for (int i = 0; i < printListPatients.size(); i++) {
				if ((dateFormat.format(printListPatients.get(i).getWomanTreatment().getStartDate()))
						.equals(treatmentService.getPastDateString(-j - 9)))
					patientsPerDay.add(printListPatients.get(i));
			}
			if (patientsPerDay.size() != 0) {

				RecentArchiveHboxController controller = LoadPanels.getArchiveHBoxLoader().get(g).getController();
				controller.setLogin(login);
				controller.setMainApp(mainApp);
				controller.setAdministratorWarningLabel(administratorWarningLabel);
				controller.setDataRand(treatmentService.getPastDateString(-j - 9));
				k = controller.setPrintListPatients(patientsPerDay, k);
				vbox.getChildren().add(LoadPanels.getArchiveHBoxEntityes().get(g));
				k++;
				g++;

			}
		}

	}

	public void printSelectedAction(ActionEvent actionEvent) {

		if (!nine.isSelected()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			treatmentService.refreshTreatments();
			UIPrintListPatient printListPatient;
			UIPrintClient printClient = new UIPrintClient(null, null);
			List<Component> printComponents = new ArrayList<>();
			for (int i = 0; i < vbox.getChildren().size(); i++) {

				printListPatient = ((PatientListEntityController) loaders.get(i).getController())
						.getUiPrintListPatient();
				if ((dateFormat.format(printListPatient.getWomanTreatment().getStartDate()))
						.equals(dateLabel.getText())) {

					printClient.setMainClient(printListPatient.getWomanTreatment().getCodes().getClient());
					printClient.setPartner(
							printListPatient.getWomanTreatment().getCodes().getClient().getCouple().getMan());
					printClient.setMainClientTreatmentCode(printListPatient.getWomanTreatment().getCodes());
					printClient.setPartnerTreatmentCode(printListPatient.getPartnerTreatment().getCodes());
					if (printListPatient.isCheckbox()) {
						printComponents.add(new fileTemplate(printClient));
						printComponents.add(new fileTemplate(printClient));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient, true)); // mirrored
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new FMDish(printClient));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new Straw(printClient, "man"));
						printComponents.add(new Straw(printClient, "woman"));
						printComponents.add(new fileTemplate(printClient));
						printComponents.add(new fileTemplate(printClient));
						printComponents.add(new Straw(printClient, "woman"));
						printComponents.add(new braceletTemplate(printClient));
						printComponents.add(new SpecialPot(printClient));
					} else {

						if (printListPatient.isFile()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++)
								printComponents.add(new fileTemplate(printClient));
						}
						if (printListPatient.isBiopsy()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++) {
								printComponents.add(new FMDish(printClient));
								printComponents.add(new FMDish(printClient, true));
							}
						}
						if (printListPatient.isIui()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++)
								printComponents.add(new braceletTemplate(printClient));
						}
						if (printListPatient.isStraws()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++) {
								printComponents.add(new Straw(printClient, "man"));
							}
						}
						if (printListPatient.isStrawe()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++)
								printComponents.add(new Straw(printClient, "woman"));
						}
						if (printListPatient.isBm()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++) {
								printComponents.add(new FMDish(printClient));
								printComponents.add(new FMDish(printClient, true));
							}
						}
						if (printListPatient.isSemenPot()) {
							for (int j = 0; j < Integer
									.parseInt(printListPatient.getSpinner().getEditor().getText()); j++) {
								printComponents.add(new fileTemplate(printClient));
								printComponents.add(new braceletTemplate(printClient));
								printComponents.add(new braceletTemplate(printClient));
							}
						}
					}

				}
			}
			new PDFPrinter(printComponents);
		}

	}

	public void printPatientListAction(ActionEvent actionEvent) {
		if (nine.isSelected() == false) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			treatmentService.refreshTreatments();
			UIPrintListPatient printListPatient;
			UIPrintClient printClient = new UIPrintClient(null, null);
			List<Component> printComponents = new ArrayList<>();
			for (int i = 0; i < vbox.getChildren().size(); i++) {
				printListPatient = ((PatientListEntityController) loaders.get(i).getController())
						.getUiPrintListPatient();
				if ((dateFormat.format(printListPatient.getWomanTreatment().getStartDate()))
						.equals(dateLabel.getText())) {
					printClient.setMainClient(printListPatient.getWomanTreatment().getCodes().getClient());
					printClient.setPartner(
							printListPatient.getWomanTreatment().getCodes().getClient().getCouple().getMan());
					printClient.setMainClientTreatmentCode(printListPatient.getWomanTreatment().getCodes());
					printClient.setPartnerTreatmentCode(printListPatient.getPartnerTreatment().getCodes());

					printComponents.add(new fileTemplate(printClient));
					printComponents.add(new fileTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true)); // mirrored
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new Straw(printClient, "man"));
					printComponents.add(new Straw(printClient, "woman"));
					printComponents.add(new fileTemplate(printClient));
					printComponents.add(new fileTemplate(printClient));
					printComponents.add(new Straw(printClient, "woman"));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new SpecialPot(printClient));

				}
			}
			new PDFPrinter(printComponents);
		}
	}

	@SuppressWarnings("unused")
	public void deleteSelectedAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PATIENT_LIST.getKey())) {
			if (nine.isSelected() == false) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				treatmentService.refreshTreatments();
				UIPrintListPatient printListPatient;
				UIPrintClient printClient = new UIPrintClient(null, null);
				List<Component> printComponents = new ArrayList<>();
				int countDeleteTreatment = 0;
				for (int i = 0; i < vbox.getChildren().size(); i++) {
					printListPatient = ((PatientListEntityController) loaders.get(i).getController())
							.getUiPrintListPatient();
					if ((dateFormat.format(printListPatient.getWomanTreatment().getStartDate()))
							.equals(dateLabel.getText())) {
						printClient.setMainClient(printListPatient.getWomanTreatment().getCodes().getClient());
						printClient.setPartner(
								printListPatient.getWomanTreatment().getCodes().getClient().getCouple().getMan());
						printClient.setMainClientTreatmentCode(printListPatient.getWomanTreatment().getCodes());
						printClient.setPartnerTreatmentCode(printListPatient.getPartnerTreatment().getCodes());
						if (printListPatient.isCheckbox()) {
							if (minusOne.isSelected() || zero.isSelected()) {
								countDeleteTreatment++;
								treatmentService.deleteCode(printClient.getMainClientTreatmentCode().getId());
								treatmentService.deleteCode(printClient.getPartnerTreatmentCode().getId());
							}
						}
					}
				}
				if (countDeleteTreatment < 1) {
					Notify notify = new Notify(AlertType.INFORMATION);
					notify.setContentText(MessageResource.getText("printlist.deleteselected.message"));
					notify.showAndWait();
				}
			}
			if (minusOne.isSelected())
				minusOneAction(null);
			else if (zero.isSelected())
				zeroAction(null);
			else if (one.isSelected())
				oneAction(null);
			else if (two.isSelected())
				twoAction(null);
			else if (three.isSelected())
				threeAction(null);
			else if (four.isSelected())
				fourAction(null);
			else if (five.isSelected())
				fiveAction(null);
			else if (six.isSelected())
				sixAction(null);
			else if (seven.isSelected())
				sevenAction(null);
			else if (eight.isSelected())
				eightAction(null);
			else if (nine.isSelected())
				nineAction(null);
		} else
			FileUtils.privillegeEditError();
	}

	public void clickSelected() {
		if (minusOne.isSelected())
			minusOneAction(null);
		else if (zero.isSelected())
			zeroAction(null);
		else if (one.isSelected())
			oneAction(null);
		else if (two.isSelected())
			twoAction(null);
		else if (three.isSelected())
			threeAction(null);
		else if (four.isSelected())
			fourAction(null);
		else if (five.isSelected())
			fiveAction(null);
		else if (six.isSelected())
			sixAction(null);
		else if (seven.isSelected())
			sevenAction(null);
		else if (eight.isSelected())
			eightAction(null);
		else if (nine.isSelected())
			nineAction(null);
	}

	public double getButtonWidth() {
		return minusOne.getWidth();
	}
}
