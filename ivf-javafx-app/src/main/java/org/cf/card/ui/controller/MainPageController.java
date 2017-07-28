package org.cf.card.ui.controller;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.service.UICodesService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPageController {

	@FXML
	private Label mainPageTitle;
	@FXML
	private ImageView mainPageIcon;
	@FXML
	private Label titleDescription;
	@FXML
	private Button closeButton;
	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private ToggleGroup group;

	@FXML
	private ToggleButton searchPatients;
	@FXML
	private ToggleButton addPatients;
	@FXML
	private ToggleButton printPatients;
	@FXML
	private ToggleButton patientsList;
	@FXML
	private ToggleButton accountDetails;
	@FXML
	private ToggleButton addAccounts;
	@FXML
	private ToggleButton embryology;
	@FXML
	private ToggleButton standardInvestigation;
	@FXML
	private ToggleButton cryopreservationEmbry;
	@FXML
	private ToggleButton cryopreservationSemen;
	@FXML
	private ToggleButton andrologyToggleButton;
	@FXML
	private ToggleButton appointmentToggleButton;
	@FXML
	private ToggleButton doctorOfficeToggleButton;
	@FXML
	private ToggleButton nurseStationToggleButton;
	@FXML
	private ToggleButton etTableToggleButton;
	@FXML
	private ToggleButton cyclesToggleButton;
	@FXML
	private ToggleButton companyDetailsToggleButton;
	@FXML
	private ToggleButton archiveToggleButton;
	@FXML
	private ToggleButton pregnancyToggleButton;
	@FXML
	private ToggleButton billingAndInvoiceToggleButton;
	@FXML
	private ToggleButton unpaidBillToggleButton;
	@FXML
	private StackPane centerPane;
	@FXML
	private StackPane mainStackPane;
	@FXML
	private Button logOutButton;
	@FXML
	private Button directoryButton;
	@FXML
	private Text loginText;
	@FXML
	private TableColumn<Client, String> genderColumn;
	@FXML
	private TableColumn<Client, String> coupleColumn;
	@FXML
	private Label administratorWarningLabel;
	@FXML
	private Button maximize;
	@FXML
	private ToggleButton testAction;

	PrintListController printListController;

	@SuppressWarnings("unused")
	private java.util.List<AnchorPane> anchorPanes = new ArrayList<>();

	private Map<Integer, Pane> moScenes = new HashMap<>();

	// private UITreatmentService treatmentService = new UITreatmentService();
	private UICodesService codesService = new UICodesService();
	private UIClientService clientService = new UIClientService();
	private UICoupleService coupleService = new UICoupleService();
	// Reference to the main application.
	private MainApp mainApp;
	private User login;
	private Codes manCode;
	private Codes womanCode;
	private Couple couple;

	public Codes getManCode() {
		return manCode;
	}

	public void setManCode(Codes manCode) {
		this.manCode = manCode;
	}

	public Codes getWomanCode() {
		return womanCode;
	}

	public void setWomanCode(Codes womanCode) {
		this.womanCode = womanCode;
	}

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
			maximize.setStyle("-fx-background-image: url(icons/maximize.png);");
		} else {
			maximize.setStyle("-fx-background-image: url(icons/normalize.png);");

		}
	}

	public User getLogin() {
		return login;
	}

	/* maintaining the session data for couple object */
	SessionObject<String, Long> sessionObject = null;

	@SuppressWarnings("unchecked")
	public void setLogin(User login) {
		sessionObject = Session.getInstance().getSessionObject();
		/*
		 * Codes code = codesService.recentCode(); if(code!=null){
		 * setCouple(code.getClient().getCouple());
		 * setWomanCode(couple.getWoman().getCodes().get(couple.getWoman().
		 * getCodes().size() - 1)); }
		 *
		 * List<Client> clients = clientService.getClients();
		 * if(getCouple()==null){ if(clients!=null && clients.size() >0){ Client
		 * client = clients.get(clients.size()-1);
		 * setCouple(client.getCouple()); } }
		 */

		List<Client> clients = clientService.getClients();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		if (null == coupleId) {
			if (clients != null && clients.size() > 0) {
				Client client = clients.get(clients.size() - 1);
				Couple couple = client != null ? client.getCouple() : null;
				setCouple(couple);
				sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple != null ? couple.getId() : 0l);
				sessionObject.setComponent(Constants.IS_CURRUNT_CYCLE, 1l);
				List<Codes> aoWomanCode = couple.getWoman().getCodes();
				if (!aoWomanCode.isEmpty()) {
					Codes womanCode = aoWomanCode.get(aoWomanCode.size() - 1);
					sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY,
							womanCode != null ? womanCode.getId() : 0l);
				} else {
					sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, 0l);
				}
				List<Codes> aoManCode = couple.getMan().getCodes();
				if (!aoManCode.isEmpty()) {
					Codes manCode = aoManCode.get(aoManCode.size() - 1);
					sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, manCode != null ? manCode.getId() : 0l);
				} else {
					sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, 0l);
				}
			}
		}
		this.login = login;
		loginText.setText(login.getSurname());
		/*
		 * TablePaneController controller =
		 * LoadPanels.getTableLoader().getController();
		 * controller.setMainApp(mainApp); controller.setLogin(login);
		 * controller.setAdministratorWarningLabel(administratorWarningLabel);
		 * AddPatientsController controller1 =
		 * LoadPanels.getAddPatientsLoader().getController();
		 * controller1.setMainApp(mainApp); controller1.setLogin(login);
		 * controller1.setAdministratorWarningLabel(administratorWarningLabel);
		 * controller4 = LoadPanels.getPatientListLoader().getController();
		 * controller4.setMainApp(mainApp); controller4.setLogin(login);
		 * controller4.setAdministratorWarningLabel(administratorWarningLabel);
		 * AccountDetailsController controller2 =
		 * LoadPanels.getDetailsLoader().getController();
		 * controller2.setMainApp(mainApp); controller2.setLogin(login);
		 * controller2.setMainPanelLoginName(loginText); AddAccounts controller3
		 * = LoadPanels.getAddAccountsLoader().getController();
		 * controller3.setMainApp(mainApp); controller3.setLogin(login);
		 *
		 * EmbryologyController controller5 =
		 * LoadPanels.getEmbryologyPageLoader().getController();
		 * controller5.setMainApp(mainApp); controller5.setLogin(login);
		 * controller5.setAdministratorWarningLabel(administratorWarningLabel);
		 * //controller5.setWomanCode(womanCode);
		 *
		 * StandardInvestigationController stController =
		 * LoadPanels.getStandardInvestigationLoader().getController();
		 * stController.setMainApp(mainApp); stController.setLogin(login);
		 * stController.setAdministratorWarningLabel(administratorWarningLabel);
		 *
		 * CryopreservationEmbryoController cryoEmbryocontroller =
		 * LoadPanels.getCryopreservationEmbryoLoader().getController();
		 * cryoEmbryocontroller.setMainApp(mainApp);
		 * cryoEmbryocontroller.setLogin(login);
		 * cryoEmbryocontroller.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * CryopreservationSemenController cryoSemencontroller =
		 * LoadPanels.getCryopreservationSemenLoader().getController();
		 * cryoSemencontroller.setMainApp(mainApp);
		 * cryoSemencontroller.setLogin(login);
		 * cryoSemencontroller.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * EmbryologySemenPreparationController embryologySemenPrepController =
		 * LoadPanels.getEmbryologySemenPreparationLoader().getController();
		 * embryologySemenPrepController.setMainApp(mainApp);
		 * embryologySemenPrepController.setLogin(login);
		 * embryologySemenPrepController.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * AndrologySemenController andrologySemenController =
		 * LoadPanels.getAndrologySemenLoader().getController();
		 * andrologySemenController.setMainApp(mainApp);
		 * andrologySemenController.setLogin(login);
		 * andrologySemenController.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * NurseStationController nurseStationController =
		 * LoadPanels.getNurseStationLoader().getController();
		 * nurseStationController.setMainApp(mainApp);
		 * nurseStationController.setLogin(login);
		 * nurseStationController.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * EmbryologyTranseferController embryologyTranseferController =
		 * LoadPanels.getEmbrylogyTransferLoader().getController();
		 * embryologyTranseferController.setMainApp(mainApp);
		 * embryologyTranseferController.setLogin(login);
		 * embryologyTranseferController.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * EmbryologyThawingController embryologyThawingController =
		 * LoadPanels.getEmbryologyThawingLoader().getController();
		 * embryologyThawingController.setMainApp(mainApp);
		 * embryologyThawingController.setLogin(login);
		 * embryologyThawingController.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 *
		 * DoctorOfficeController doctorOfficeController =
		 * LoadPanels.getDoctorOfficeLoader().getController();
		 * doctorOfficeController.setMainApp(mainApp);
		 * doctorOfficeController.setLogin(login);
		 * doctorOfficeController.setAdministratorWarningLabel(
		 * administratorWarningLabel);
		 */

	}

	class Delta {
		double x, y;
	}

	/*
	 * public void setMainPageTitle(MainPageController mainPageTitle){
	 * this.mainPageTitleName=mainPageTitle.getMainPageTitleName(); }
	 */

	@FXML
	private void initialize() throws IOException, InterruptedException {
		final Delta dragDelta = new Delta();
		mainBorderPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = mainApp.getPrimaryStage().getX() - mouseEvent.getScreenX();
				dragDelta.y = mainApp.getPrimaryStage().getY() - mouseEvent.getScreenY();
			}
		});
		mainBorderPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				mainApp.getPrimaryStage().setX(mouseEvent.getScreenX() + dragDelta.x);
				mainApp.getPrimaryStage().setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});

		moScenes.put(Module.SEARCH_PATIENTS.getKey(), LoadPanels.tablePane);
		moScenes.put(Module.SEARCH_BY_NAME_CODE.getKey(), LoadPanels.tablePane);
		moScenes.put(Module.REGISTER_PATIENT.getKey(), LoadPanels.addPatientsPane);
		moScenes.put(Module.PATIENT_LIST.getKey(), LoadPanels.patientsListPane);
		moScenes.put(Module.USER_DETAILS.getKey(), LoadPanels.detailsPane);
		moScenes.put(Module.REGISTER_USER.getKey(), LoadPanels.addAccountsPane);
		moScenes.put(Module.EMBRYOLOGY_OVERVIEW.getKey(), LoadPanels.embryologyPane);
		moScenes.put(Module.STANDARD_INVESTIGATION.getKey(), LoadPanels.standardInvestigationPane);
		moScenes.put(Module.CRYOPRESERVATION_E.getKey(), LoadPanels.cryopreservationEmbryoPane);
		moScenes.put(Module.SEMEN_PREPRATION.getKey(), LoadPanels.embryologySemenPreparationPane);
		moScenes.put(Module.CRYOPRESERVATION_A.getKey(), LoadPanels.cryopreservationSemenPane);
		moScenes.put(Module.ANDROLOGY.getKey(), LoadPanels.andryologySemenPane);
		moScenes.put(Module.NURSES_STATION.getKey(), LoadPanels.nurseStationPane);
		moScenes.put(Module.EMBRYO_TRANSFER.getKey(), LoadPanels.embrylogyTransferPane);
		moScenes.put(Module.EGG_THAWING.getKey(), LoadPanels.embryologyThawingPane);
		moScenes.put(Module.DOCTORS_OFFICE.getKey(), LoadPanels.doctorOfficePane);
		moScenes.put(Module.ET_TABLE.getKey(), LoadPanels.etTablePane);
		moScenes.put(Module.APPOINTMENT_OVERVIEW.getKey(), LoadPanels.appointmentsPane);
		moScenes.put(Module.CYCLES.getKey(), LoadPanels.cyclesPane);
		moScenes.put(Module.APPOINTMENT_SCREEN.getKey(), LoadPanels.appointmentsScreenPane);
		moScenes.put(Module.COMPANY_DETAILS.getKey(), LoadPanels.companyDetailsPane);
		moScenes.put(Module.ARCHIVE.getKey(), LoadPanels.archivePane);
		moScenes.put(Module.PREGNANCY_OUTCOMES.getKey(), LoadPanels.pregnancyOutcomesPane);
		moScenes.put(Module.RECENT_ARCHIVE.getKey(), LoadPanels.patientsListPane);
		moScenes.put(Module.ET_SCREEN.getKey(), LoadPanels.etScreenPane);

		// Billing Module
		moScenes.put(Module.BILLING_SETUP.getKey(), LoadPanels.billingSetupPane);
		moScenes.put(Module.BILLING_AND_INVOICE.getKey(), LoadPanels.billingAndInvoicePane);
		moScenes.put(Module.PAYMENT_DETAILS.getKey(), LoadPanels.paymentDetailsPane);
		moScenes.put(Module.INVOICES_DETAIL.getKey(), LoadPanels.InvoicesDetailpane);
		moScenes.put(Module.REFUND_AND_INVOICE.getKey(), LoadPanels.refundAndInvoicePane);
		moScenes.put(Module.REFUND_BILL_PER_COUPLE.getKey(), LoadPanels.refundBillPerCouplePane);
		moScenes.put(Module.REFUND_BILL_OVERALL.getKey(), LoadPanels.refundBillOverallPane);
		moScenes.put(Module.UNPAID_BILL_PER_COUPLE.getKey(), LoadPanels.unpaidBillPerCouplepane);
		moScenes.put(Module.UNPAID_BILL_OVERALL.getKey(), LoadPanels.unpaidBillOverallpane);

		// moScenes.put(Module.RECENT_PAGE.getKey(), LoadPanels.);

		// centerPane.getChildren().add(0, LoadPanels.tablePane);
		// anchorPanes.add(LoadPanels.tablePane);
		// LoadPanels.tablePane.setVisible(false);
		//
		// centerPane.getChildren().add(1, LoadPanels.addPatientsPane);
		// anchorPanes.add(LoadPanels.addPatientsPane);
		// LoadPanels.addPatientsPane.setVisible(false);
		//
		// centerPane.getChildren().add(2, LoadPanels.patientsListPane);
		// anchorPanes.add(LoadPanels.patientsListPane);
		// LoadPanels.patientsListPane.setVisible(false);
		//
		// centerPane.getChildren().add(3, LoadPanels.detailsPane);
		// anchorPanes.add(LoadPanels.detailsPane);
		// LoadPanels.detailsPane.setVisible(false);
		//
		// centerPane.getChildren().add(4, LoadPanels.addAccountsPane);
		// anchorPanes.add(LoadPanels.addAccountsPane);
		// LoadPanels.addAccountsPane.setVisible(false);
		//
		// centerPane.getChildren().add(5,LoadPanels.embryologyPane);
		// anchorPanes.add(LoadPanels.embryologyPane);
		// LoadPanels.embryologyPane.setVisible(false);
		//
		// centerPane.getChildren().add(6,LoadPanels.standardInvestigationPane);
		// anchorPanes.add(LoadPanels.standardInvestigationPane);
		// LoadPanels.standardInvestigationPane.setVisible(false);
		//
		// centerPane.getChildren().add(7,
		// LoadPanels.cryopreservationEmbryoPane);
		// anchorPanes.add(LoadPanels.cryopreservationEmbryoPane);
		// LoadPanels.cryopreservationEmbryoPane.setVisible(false);
		//
		// centerPane.getChildren().add(8,
		// LoadPanels.embryologySemenPreparationPane);
		// anchorPanes.add(LoadPanels.embryologySemenPreparationPane);
		// LoadPanels.embryologySemenPreparationPane.setVisible(false);
		//
		// centerPane.getChildren().add(9,
		// LoadPanels.cryopreservationSemenPane);
		// anchorPanes.add(LoadPanels.cryopreservationSemenPane);
		// LoadPanels.cryopreservationSemenPane.setVisible(false);
		//
		// centerPane.getChildren().add(10, LoadPanels.andryologySemenPane);
		// anchorPanes.add(LoadPanels.andryologySemenPane);
		// LoadPanels.andryologySemenPane.setVisible(false);
		//
		// centerPane.getChildren().add(11, LoadPanels.nurseStationPane);
		// anchorPanes.add(LoadPanels.nurseStationPane);
		// LoadPanels.nurseStationPane.setVisible(false);
		//
		// centerPane.getChildren().add(12, LoadPanels.embrylogyTransferPane);
		// anchorPanes.add(LoadPanels.embrylogyTransferPane);
		// LoadPanels.embrylogyTransferPane.setVisible(false);
		//
		// centerPane.getChildren().add(13, LoadPanels.embryologyThawingPane);
		// anchorPanes.add(LoadPanels.embryologyThawingPane);
		// LoadPanels.embryologyThawingPane.setVisible(false);
		//
		// centerPane.getChildren().add(14, LoadPanels.doctorOfficePane);
		// anchorPanes.add(LoadPanels.doctorOfficePane);
		// LoadPanels.doctorOfficePane.setVisible(false);
		//
		// centerPane.getChildren().add(15, LoadPanels.etTablePane);
		// anchorPanes.add(LoadPanels.etTablePane);
		// LoadPanels.etTablePane.setVisible(false);
		//
		// centerPane.getChildren().add(16, LoadPanels.appointmentsPane);
		// anchorPanes.add(LoadPanels.appointmentsPane);
		// LoadPanels.appointmentsPane.setVisible(false);
		//
		// centerPane.getChildren().add(17, LoadPanels.cyclesPane);
		// anchorPanes.add(LoadPanels.cyclesPane);
		// LoadPanels.cyclesPane.setVisible(false);
	}

	public void handleCloseButton(ActionEvent actionEvent) throws IOException {
		FXMLLoader loader10 = new FXMLLoader();
		loader10.setLocation(MainApp.class.getResource("/view/ExitWindow.fxml"));
		loader10.setResources(MessageResource.getResourceBundle());
		try {

			mainApp.getPrimaryStage().hide();

			mainApp.getPatientFileStage().close();
			BorderPane personOverview1 = (BorderPane) loader10.load();
			Group group = new Group(personOverview1);

			group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
			group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
			group.setLayoutX(-personOverview1.getPrefWidth() * ((1 - group.getScaleX()) / 2));
			group.setLayoutY(-personOverview1.getPrefHeight() * ((1 - group.getScaleY()) / 2));
			Scene scene = new Scene(group);

			scene.getStylesheets().add(MainApp.class.getResource("/CSS/Exit.css").toExternalForm());
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().sizeToScene();

			mainApp.getPrimaryStage().show();
			mainApp.getPrimaryStage().centerOnScreen();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				mainApp.getPatientFileStage().close();
				System.exit(0);
			}
		});
		new Thread(sleeper).start();

	}

	public void searchPatientsAction(ActionEvent actionEvent) {
		searchPatients.setSelected(true);
		TablePaneController tablePaneController = LoadPanels.getTableLoader().getController();
		tablePaneController.setMainApp(mainApp);
		tablePaneController.setLogin(login);
		tablePaneController.setAdministratorWarningLabel(administratorWarningLabel);
		tablePaneController.buildData();
		// anchorPanes.get(0).setVisible(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.searchpatient"));
		titleDescription.setText("");
		mainPageIcon.setImage(new Image("/icons/search_patient.png"));

	}

	public void addPatientsAction(ActionEvent actionEvent) {
		addPatients.setSelected(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.addpatient"));
		titleDescription.setText("");
		mainPageIcon.setImage(new Image("/icons/add_patients.png"));
		AddPatientsController controller = LoadPanels.getAddPatientsLoader().getController();
		controller.setLogin(login);
		controller.setMainApp(mainApp);

	}

	public void patientsListAction(ActionEvent actionEvent) {
		patientsList.setSelected(true);
		// if From Directory Page Recent Archive Clicked then need to load Day 9
		String recentArchive = null;
		if (null != actionEvent) {
			Object obj = actionEvent.getSource();
			Button button = null;
			// String recentArchive = null;
			if (null != obj && obj instanceof Button) {
				button = (Button) obj;
				recentArchive = button.getId();
			}

		}
		mainPageTitle.setText(MessageResource.getText("mainpage.title.patientlist"));
		titleDescription.setText("");
		mainPageIcon.setImage(new Image("/icons/print_patients.png"));
		printListController = LoadPanels.getPatientListLoader().getController();
		printListController.setMainApp(mainApp);
		printListController.setLogin(login);
		printListController.setAdministratorWarningLabel(administratorWarningLabel);

		if (null != recentArchive && recentArchive.equals("recentArchive")) {
			printListController.nineAction(null);
		} else {
			printListController.minusOneAction(null);
		}

	}

	public void accountDetailsAction(ActionEvent actionEvent) {

		accountDetails.setSelected(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.accountsdetail"));
		mainPageIcon.setImage(new Image("/icons/account_details.png"));
		titleDescription.setText("");
		AccountDetailsController controller = LoadPanels.getDetailsLoader().getController();
		controller.setMainApp(mainApp);
		controller.setLogin(login);
	}

	public void addAccountsDetails(ActionEvent actionEvent) {

		addAccounts.setSelected(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.register_new_user"));
		mainPageIcon.setImage(new Image("/icons/new_accounts.png"));
		titleDescription.setText("");
		AddAccounts controller = LoadPanels.getAddAccountsLoader().getController();
		controller.setMainApp(mainApp);
		controller.setLogin(login);
	}

	@SuppressWarnings("unchecked")
	public void embryologyAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {

			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			// setting titles at top
			mainPageTitle.setText(MessageResource.getText("mainpage.title.embryology"));
			mainPageIcon.setImage(new Image("/icons/embryology.png"));
			titleDescription.setText(MessageResource.getText("mainpage.title.embryology.description.overview"));

			if (null != womanCode && null != manCode) {
				embryology.setSelected(true);
				EmbryologyController embryologyController = LoadPanels.getEmbryologyPageLoader().getController();
				embryologyController.setTreatment(womanCode.getTreatment());
				embryologyController.setWomanCode(womanCode);
				embryologyController.setManCode(manCode);
				embryologyController.setCouple(couple);
				embryologyController.setMainApp(mainApp);
				embryologyController.setLogin(login);
				embryologyController.buildTableView();
				embryologyController.buildData();

			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void standardInvestigationAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				standardInvestigation.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.standard_investigation"));
				titleDescription.setText("");
				mainPageIcon.setImage(new Image("/icons/standard_investigation.png"));
				StandardInvestigationController stController = LoadPanels.getStandardInvestigationLoader()
						.getController();
				stController.setCouple(couple);
				stController.setMainApp(mainApp);
				stController.setWomanCode(womanCode);
				stController.setManCode(manCode);
				stController.setLogin(login);
				stController.build();
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void cryopreservationEmbryAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				cryopreservationEmbry.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.cryopreservation"));
				titleDescription.setText(MessageResource.getText("mainpage.title.cryopreservation.description.embryo"));
				mainPageIcon.setImage(new Image("/icons/cryopreservation_embyro.png"));

				CryopreservationEmbryoController controller = LoadPanels.getCryopreservationEmbryoLoader()
						.getController();
				controller.setCouple(couple);
				controller.setLogin(login);
				controller.setManCode(manCode);
				controller.setWomanCode(womanCode);
				controller.setMainApp(mainApp);
				controller.buildData();
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void cryopreservationSemenAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				cryopreservationSemen.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.cryopreservation"));
				titleDescription.setText(MessageResource.getText("mainpage.title.cryopreservation.description.andro"));
				mainPageIcon.setImage(new Image("/icons/cryopreservation_embyro.png"));
				CryopreservationSemenController semenController = LoadPanels.getCryopreservationSemenLoader()
						.getController();
				semenController.setMainApp(mainApp);
				semenController.setLogin(login);
				semenController.setManCode(manCode);
				semenController.setWomanCode(womanCode);
				semenController.setCouple(couple);
				semenController.buildData();
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void embryoSemenPreparationAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				embryology.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.embryology"));
				titleDescription.setText(MessageResource.getText("mainpage.title.embryology.description.semen"));
				// mainPageIcon.setImage(new
				// Image("/icons/embryology_menu.jpg"));
				EmbryologySemenPreparationController controller = LoadPanels.getEmbryologySemenPreparationLoader()
						.getController();
				controller.setMainApp(mainApp);
				controller.setLogin(login);
				controller.setManCode(manCode);
				controller.setWomanCode(womanCode);
				controller.setCouple(couple);
				controller.buildData();

			} else {
				Notify alert = new Notify(AlertType.ERROR,
						MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void andrologySemenAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		// couple can be null when there are no patients in the system
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				String gender = manCode.getClient().getGender();
				String maleGender = MessageResource.getText("uiclient.service.combo.male");
				if (!gender.equals(maleGender)) {
					Notify alert = new Notify(AlertType.ERROR,
							MessageResource.getText("mainpage.controller.error.partner.not.male", gender));
					alert.showAndWait();
				}
				andrologyToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.andrology"));
				titleDescription.setText("");
				mainPageIcon.setImage(new Image("/icons/andrology.png"));
				AndrologySemenController andrologyController = LoadPanels.getAndrologySemenLoader().getController();
				andrologyController.setMainApp(mainApp);
				andrologyController.setLogin(login);
				andrologyController.setManCode(manCode);
				andrologyController.setWomanCode(womanCode);
				andrologyController.setCouple(couple);
				andrologyController.buildData();
				/*
				 * else { Notify alert = new Notify(AlertType.ERROR,
				 * MessageResource.getText(
				 * "mainpage.controller.error.partner.not.male"));
				 * alert.showAndWait(); }
				 */

			} else {
				Notify alert = new Notify(AlertType.ERROR,
						MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void nursesStationAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				nurseStationToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.nursestation"));
				titleDescription.setText("");
				mainPageIcon.setImage(new Image("/icons/nurse_station.png"));
				NurseStationController nurseStationController = LoadPanels.getNurseStationLoader().getController();
				nurseStationController.setWomanCode(womanCode);
				nurseStationController.setManCode(manCode);
				nurseStationController.setLogin(login);
				nurseStationController.setCouple(couple);
				nurseStationController.setMainApp(mainApp);
				nurseStationController.buildData();
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void embryoTransferAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				embryology.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.embryology"));
				titleDescription.setText(MessageResource.getText("mainpage.title.embryology.description.embryo"));
				mainPageIcon.setImage(new Image("/icons/embryology.png"));
				EmbryologyTranseferController transeferController = LoadPanels.getEmbrylogyTransferLoader()
						.getController();
				transeferController.setWomanCode(womanCode);
				transeferController.setManCode(manCode);
				transeferController.setCouple(couple);
				transeferController.setLogin(login);
				transeferController.setMainApp(mainApp);
				transeferController.buildData();
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void embryoThawingAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				cryopreservationEmbry.setSelected(true);
				EmbryologyThawingController controller = LoadPanels.getEmbryologyThawingLoader().getController();
				controller.setMainApp(mainApp);
				controller.setLogin(login);
				controller.setCouple(couple);
				controller.setWomanCode(womanCode);
				controller.setManCode(manCode);
				controller.buildData();
				mainPageTitle.setText(MessageResource.getText("mainpage.title.embryology"));
				titleDescription.setText(MessageResource.getText("mainpage.title.embryology.description.egg"));
				mainPageIcon.setImage(new Image("/icons/embryology.png"));
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void doctorsOfficeAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				doctorOfficeToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.doctoroffice"));
				titleDescription.setText("");
				mainPageIcon.setImage(new Image("/icons/doctor_office.png"));
				DoctorOfficeController doctorOfficeController = LoadPanels.getDoctorOfficeLoader().getController();
				doctorOfficeController.setLogin(login);
				doctorOfficeController.setMainApp(mainApp);
				doctorOfficeController.setCouple(couple);
				doctorOfficeController.setWomanCode(womanCode);
				doctorOfficeController.setManCode(manCode);
				doctorOfficeController.buildData();
			} else {
				Notify alert = new Notify(AlertType.ERROR);
				alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
				alert.showAndWait();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void etTableAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				etTableToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.et_table"));
				titleDescription.setText(MessageResource.getText("mainpage.title.appointment.description"));
				mainPageIcon.setImage(new Image("/icons/et.png"));
				EtTableController etController = LoadPanels.getEtTableLoader().getController();
				etController.buildData();
				etController.setMainApp(mainApp);
				etController.setLogin(login);
			}
		}

	}

	public void appointmentOverviewAction(ActionEvent actionEvent) {

		appointmentToggleButton.setSelected(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.appointment"));
		titleDescription.setText(MessageResource.getText("mainpage.title.appointment.description"));
		mainPageIcon.setImage(new Image("/icons/appointment.png"));
		AppointmentOverviewController overviewController = LoadPanels.getAppointmentsLoader().getController();
		overviewController.buildData();
		overviewController.setMainApp(mainApp);
		overviewController.setLogin(login);

	}

	public void appointmentScreenAction(ActionEvent actionEvent) {

		mainPageTitle.setText(MessageResource.getText("mainpage.title.appointment"));
		titleDescription.setText(MessageResource.getText("mainpage.title.appointment.screen"));
		mainPageIcon.setImage(new Image("/icons/appointment.png"));
		AppointmentScreenController appointmentScreenController = LoadPanels.getAppointmentsScreenLoader()
				.getController();
		appointmentScreenController.buildData();
		appointmentScreenController.setMainApp(mainApp);
		appointmentScreenController.setLogin(login);

	}

	@SuppressWarnings("unchecked")
	public void cyclesAction(ActionEvent actionEvent) {
		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCodeId && null != manCodeId) {
				cyclesToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.embryology"));
				titleDescription.setText(MessageResource.getText("mainpage.title.embryology.description.cycles"));
				mainPageIcon.setImage(new Image("/icons/cycles.png"));
				CyclesController cyclesController = LoadPanels.getCyclesLoader().getController();
				cyclesController.setMainApp(mainApp);
				cyclesController.setLogin(login);
				cyclesController.setCouple(couple);
				cyclesController.setManCode(manCode);
				cyclesController.setWomanCode(womanCode);
				cyclesController.buildData();
			}
		}
	}

	@FXML
	public void companyDetailsAction(ActionEvent actionEvent) {
		companyDetailsToggleButton.setSelected(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.company.details"));
		mainPageIcon.setImage(new Image("/icons/company.png"));
		titleDescription.setText(IConstants.emptyString);
		CompanyDetailsController companyDetailsController = LoadPanels.getCompanyDetailsLoader().getController();
		companyDetailsController.setMainApp(mainApp);
		companyDetailsController.setLogin(login);
		companyDetailsController.buildData();

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void archiveAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				archiveToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.archive.details"));
				mainPageIcon.setImage(new Image("/icons/archieve.png"));
				titleDescription.setText(IConstants.emptyString);
				ArchiveController archiveController = LoadPanels.getArchiveDetailsLoader().getController();

				archiveController.setMainApp(mainApp);
				archiveController.setLogin(login);
				archiveController.setCouple(couple);
				archiveController.setManCode(manCode);
				archiveController.setWomanCode(womanCode);
				archiveController.buildData();
				archiveToggleButton.setSelected(true);
			}
		}
	}

	/**
	 * Pregnancy outcomes action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	public void pregnancyOutcomesAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				pregnancyToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.pregnancy.details"));
				mainPageIcon.setImage(new Image("/icons/pregnancy.png"));
				titleDescription.setText(MessageResource.getText("mainpage.title.pregnancy.description"));
				PregnancyOutcomesController pregnancyController = LoadPanels.getPregnancyOutcomesLoader()
						.getController();
				pregnancyController.setTreatment(womanCode.getTreatment());
				pregnancyController.setMainApp(mainApp);
				pregnancyController.setLogin(login);
				pregnancyController.setCouple(couple);
				pregnancyController.setManCode(manCode);
				pregnancyController.setWomanCode(womanCode);
				pregnancyController.buildData();
			}
		}
	}

	public void etScreenAction(ActionEvent actionEvent) {
		mainPageTitle.setText(MessageResource.getText("mainpage.title.et_table"));
		titleDescription.setText(MessageResource.getText("mainpage.title.et_table.screen"));
		mainPageIcon.setImage(new Image("/icons/et.png"));

		EtScreenController etScreenController = LoadPanels.getEtScreenLoader().getController();
		etScreenController.buildData();
		etScreenController.setMainApp(mainApp);
		etScreenController.setLogin(login);
	}

	public void billingSetupAction(ActionEvent actionEvent) {
		mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.setup"));
		titleDescription.setText(IConstants.emptyString);
		mainPageIcon.setImage(new Image("/icons/billing_setup.png"));

		BillingSetupController billingSetupController = LoadPanels.getBillingSetupLoader().getController();
		billingSetupController.setLogin(login);
		billingSetupController.buildData();
		billingSetupController.setMainApp(mainApp);
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void billingAndInvoiceAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				billingAndInvoiceToggleButton.setSelected(true);

				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.invoice"));
				titleDescription.setText(IConstants.emptyString);
				mainPageIcon.setImage(new Image("/icons/billing_and_invoice.png"));

				BillingAndInvoiceController billingInvoiceController = LoadPanels.getBillingAndInvoiceLoader()
						.getController();
				billingInvoiceController.setMainApp(mainApp);
				billingInvoiceController.setLogin(login);
				billingInvoiceController.setCouple(couple);
				billingInvoiceController.setManCode(manCode);
				billingInvoiceController.setWomanCode(womanCode);
				billingInvoiceController.buildData();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void paymentDetailsAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {

				billingAndInvoiceToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.payment.details"));
				titleDescription.setText(MessageResource.getText("mainpage.title.billing.payment.details.description"));
				mainPageIcon.setImage(new Image("/icons/billing_and_invoice.png"));

				PaymentDetailsController paymentDetailsController = LoadPanels.getPaymentDetailsLoader()
						.getController();
				paymentDetailsController.setMainApp(mainApp);
				paymentDetailsController.setLogin(login);
				paymentDetailsController.setCouple(couple);
				paymentDetailsController.setManCode(manCode);
				paymentDetailsController.setWomanCode(womanCode);
				paymentDetailsController.buildData();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void invoiceDetailAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				billingAndInvoiceToggleButton.setSelected(true);

				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.invoices.detail"));
				titleDescription.setText(MessageResource.getText("mainpage.title.billing.invoices.detail.description"));
				mainPageIcon.setImage(new Image("/icons/billing_and_invoice.png"));

				InvoicesDetailController invoicesDetailController = LoadPanels.getInvoicesDetailLoader()
						.getController();
				invoicesDetailController.setMainApp(mainApp);
				invoicesDetailController.setLogin(login);
				invoicesDetailController.setCouple(couple);
				invoicesDetailController.setManCode(manCode);
				invoicesDetailController.setWomanCode(womanCode);
				invoicesDetailController.buildData();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void refundAndInvoiceAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				billingAndInvoiceToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.refund.invoice"));
				titleDescription.setText(IConstants.emptyString);
				mainPageIcon.setImage(new Image("/icons/refund_overall.png"));

				RefundAndInvoiceController refundAndInvoiceController = LoadPanels.getRefundAndInvoiceLoader()
						.getController();
				refundAndInvoiceController.setMainApp(mainApp);
				refundAndInvoiceController.setLogin(login);
				refundAndInvoiceController.setCouple(couple);
				refundAndInvoiceController.setManCode(manCode);
				refundAndInvoiceController.setWomanCode(womanCode);
				refundAndInvoiceController.buildData();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void refundBillPerCoupleAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				billingAndInvoiceToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.refund.invoice.per.couple"));
				titleDescription.setText(MessageResource.getText("mainpage.title.billing.invoices.detail.description"));
				mainPageIcon.setImage(new Image("/icons/refund_overall.png"));

				RefundBillPerCoupleController refundBillPerCoupleController = LoadPanels.getRefundBillPerCoupleLoader()
						.getController();
				refundBillPerCoupleController.setMainApp(mainApp);
				refundBillPerCoupleController.setLogin(login);
				refundBillPerCoupleController.setCouple(couple);
				refundBillPerCoupleController.setManCode(manCode);
				refundBillPerCoupleController.setWomanCode(womanCode);
				refundBillPerCoupleController.buildData();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void refundBillOverallAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				billingAndInvoiceToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.refund.invoice.per.couple"));
				titleDescription.setText(MessageResource.getText("mainpage.title.billing.refund.overall"));
				mainPageIcon.setImage(new Image("/icons/refund_overall.png"));

				RefundBillOverallController refundBillOverallController = LoadPanels.getRefundBillOverallLoader()
						.getController();
				refundBillOverallController.setMainApp(mainApp);
				refundBillOverallController.setLogin(login);
				refundBillOverallController.setCouple(couple);
				refundBillOverallController.setManCode(manCode);
				refundBillOverallController.setWomanCode(womanCode);
				refundBillOverallController.buildData();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void unpaidBillPerCoupleAction(ActionEvent actionEvent) {

		sessionObject = Session.getInstance().getSessionObject();
		Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
		couple = coupleService.getCoupleById(coupleId);
		Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
		Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);
		if (couple != null) {
			womanCode = codesService.getCodeById(womanCodeId);
			manCode = codesService.getCodeById(manCodeId);
			if (null != womanCode && null != manCode) {
				unpaidBillToggleButton.setSelected(true);
				mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.unpaid"));
				titleDescription.setText(MessageResource.getText("mainpage.title.billing.invoices.detail.description"));
				mainPageIcon.setImage(new Image("/icons/unpaid_bill.png"));

				UnpaidBillPerCoupleController unpaidBillPerCoupleController = LoadPanels.getUnpaidBillPerCoupleLoader()
						.getController();
				unpaidBillPerCoupleController.setMainApp(mainApp);
				unpaidBillPerCoupleController.setLogin(login);
				unpaidBillPerCoupleController.setCouple(couple);
				unpaidBillPerCoupleController.setManCode(manCode);
				unpaidBillPerCoupleController.setWomanCode(womanCode);
				unpaidBillPerCoupleController.buildData();
			}
		}

	}

	@FXML
	public void unpaidBillOverallAction(ActionEvent actionEvent) {

		unpaidBillToggleButton.setSelected(true);
		mainPageTitle.setText(MessageResource.getText("mainpage.title.billing.unpaid"));
		titleDescription.setText(MessageResource.getText("mainpage.title.billing.refund.overall"));
		mainPageIcon.setImage(new Image("/icons/unpaid_bill.png"));
		UnpaidBillOverallController unpaidBillOverallController = LoadPanels.getUnpaidBillOverallLoader()
				.getController();
		unpaidBillOverallController.setMainApp(mainApp);
		unpaidBillOverallController.setLogin(login);
		unpaidBillOverallController.buildData();

	}

	public void logOutAction(ActionEvent actionEvent) {
		if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/LoginPage.fxml"));
			try {
				loader.setResources(MessageResource.getResourceBundle());
				BorderPane personOverview = (BorderPane) loader.load();
				Group group = new Group(personOverview);

				group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
				group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
				group.setLayoutX(-personOverview.getPrefWidth() * ((1 - group.getScaleX()) / 2));
				group.setLayoutY(-personOverview.getPrefHeight() * ((1 - group.getScaleY()) / 2));
				Scene scene = new Scene(group);
				mainApp.getPrimaryStage().setScene(scene);
				scene.getStylesheets().add(MainApp.class.getResource("/CSS/Login.css").toExternalForm());
				mainApp.getPrimaryStage().show();
				LoginController controller = loader.getController();
				controller.setMainApp(mainApp);
			} catch (IOException e) {

			}
		} else {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/LoginPage.fxml"));
			try {
				loader.setResources(MessageResource.getResourceBundle());
				BorderPane personOverview = (BorderPane) loader.load();
				// personOverview.set
				@SuppressWarnings("unused")
				StackPane group = new StackPane(personOverview);
				Scene scene = LoadPanels.getSceneForFullScreen(personOverview);
				mainApp.getPrimaryStage().setScene(scene);
				scene.getStylesheets().add(MainApp.class.getResource("/CSS/Login.css").toExternalForm());
				Screen screen = Screen.getPrimary();
				Rectangle2D bounds = screen.getVisualBounds();

				mainApp.getPrimaryStage().setX(bounds.getMinX());
				mainApp.getPrimaryStage().setY(bounds.getMinY());
				mainApp.getPrimaryStage().setWidth(bounds.getWidth());
				mainApp.getPrimaryStage().setHeight(bounds.getHeight());
				mainApp.getPrimaryStage().show();
				LoginController controller = loader.getController();
				controller.setMainApp(mainApp);

				/*
				 * mainApp.getPrimaryStage().hide();
				 *
				 * Scene scene =
				 * LoadPanels.getSceneForHalfScreen(mainBorderPane);
				 * scene.getStylesheets().add(MainApp.class.getResource(
				 * "/CSS/MainPage.css").toExternalForm());
				 * mainApp.getPrimaryStage().setScene(scene);
				 * mainApp.getPrimaryStage().sizeToScene();
				 *
				 * maximize.setStyle(
				 * "-fx-background-image: url(icons/maximize.png);");
				 * mainApp.getPrimaryStage().show();
				 * mainApp.getPrimaryStage().centerOnScreen();
				 */

			} catch (IOException e) {

			}
		}
	}

	public void directoryAction(ActionEvent actionEvent) {

		DirectoryPageController controller = null;
		if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
			controller = LoadPanels.loadDirectoryPageHalfScreen(mainApp, login);
		} else {
			controller = LoadPanels.loadDirectoryPageFullScreen(mainApp, login);
		}
		Module module = (Module) group.getSelectedToggle().getUserData();
		controller.setRecentModuleKey(module.getKey());

	}

	int i = 0;

	public void maximizeAction(ActionEvent actionEvent) {
		if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getBounds();

			Stage primaryStage = mainApp.getPrimaryStage();
			primaryStage.hide();

			Scene scene = LoadPanels.getSceneForFullScreen(mainBorderPane);

			// Scene scene = new
			// Scene(group,bounds.getWidth(),bounds.getHeight());
			scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css").toExternalForm());
			mainApp.getPrimaryStage().setScene(scene);

			primaryStage.setX(bounds.getMinX());
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth());
			primaryStage.setHeight(bounds.getHeight());
			maximize.setStyle("-fx-background-image: url(icons/normalize.png);");
			primaryStage.show();
		} else {
			mainApp.getPrimaryStage().hide();

			Scene scene = LoadPanels.getSceneForHalfScreen(mainBorderPane);
			scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css").toExternalForm());
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().sizeToScene();

			maximize.setStyle("-fx-background-image: url(icons/maximize.png);");
			mainApp.getPrimaryStage().show();
			mainApp.getPrimaryStage().centerOnScreen();
			i++;
			if (patientsList.isSelected()) {
				patientsListAction(null);
			}

		}
	}

	/*
	 * public void maximizeAction(ActionEvent actionEvent) {
	 *
	 * Region contentRootRegion = (Region) mainBorderPane;
	 *
	 * Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	 * // Set a default "standard" or "100%" resolution
	 *
	 * double origW = 960; double origH = 540;
	 * if(mainApp.getPrimaryStage().getWidth() !=primaryScreenBounds.getWidth()
	 * ){
	 *
	 * // If the Region containing the GUI does not already have a preferred //
	 * width and height, set it. // But, if it does, we can use that setting as
	 * the "standard" // resolution. if (contentRootRegion.getPrefWidth() ==
	 * Region.USE_COMPUTED_SIZE) contentRootRegion.setPrefWidth(origW); else{
	 * origW = contentRootRegion.getPrefWidth(); //origW =
	 * primaryScreenBounds.getWidth(); } if (contentRootRegion.getPrefHeight()
	 * == Region.USE_COMPUTED_SIZE) contentRootRegion.setPrefHeight(origH);
	 * else{ origH = contentRootRegion.getPrefHeight(); //origH =
	 * primaryScreenBounds.getHeight(); } // Wrap the resizable content in a
	 * non-resizable container (Group) Group group = new
	 * Group(contentRootRegion); // Place the Group in a StackPane, which will
	 * keep it centered StackPane rootPane = new StackPane();
	 * rootPane.getChildren().add(group);
	 *
	 *
	 * // Create the scene initally at the "100%" size Scene scene = new
	 * Scene(rootPane, origW, origH); // Bind the scene's width and height to
	 * the scaling parameters on the // group
	 * scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css")
	 * .toExternalForm());
	 * group.scaleXProperty().bind(scene.widthProperty().divide(origW));
	 * group.scaleYProperty().bind(scene.heightProperty().divide(origH)); // Set
	 * the scene to the window (stage) and show it
	 * mainApp.getPrimaryStage().setScene(scene);
	 * mainApp.getPrimaryStage().show(); }else{ origW = 1146.0; origH = 786.0;
	 *
	 * origH = contentRootRegion.getPrefHeight(); Group group = new
	 * Group(contentRootRegion); // Place the Group in a StackPane, which will
	 * keep it centered StackPane rootPane = new StackPane();
	 * rootPane.getChildren().add(group);
	 *
	 *
	 * // Create the scene initally at the "100%" size Scene scene = new
	 * Scene(rootPane, origW, origH); // Bind the scene's width and height to
	 * the scaling parameters on the // group
	 * scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css")
	 * .toExternalForm());
	 * group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().
	 * getHeight() / 1080.0);
	 * group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().
	 * getHeight() / 1000.0); group.setLayoutX(-mainBorderPane.getPrefWidth() *
	 * ((1 - group.getScaleX()) / 2));
	 * group.setLayoutY(-mainBorderPane.getPrefHeight() * ((1 -
	 * group.getScaleY()) / 2));
	 *
	 * //group.scaleXProperty().bind(scene.widthProperty().divide(origW));
	 * //group.scaleYProperty().bind(scene.heightProperty().divide(origH)); //
	 * Set the scene to the window (stage) and show it
	 * mainApp.getPrimaryStage().setScene(scene);
	 * //mainApp.getPrimaryStage().sizeToScene();
	 * mainApp.getPrimaryStage().centerOnScreen();
	 * mainApp.getPrimaryStage().show(); }
	 */

	/*
	 * if (mainApp.getPrimaryStage().getWidth() !=
	 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
	 * mainApp.getPrimaryStage().hide(); StackPane group = new
	 * StackPane(mainBorderPane);
	 *
	 * Scene scene = new Scene(group);
	 * scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css")
	 * .toExternalForm()); mainApp.getPrimaryStage().setScene(scene); Screen
	 * screen = Screen.getPrimary(); Rectangle2D bounds =
	 * screen.getVisualBounds();
	 *
	 * mainApp.getPrimaryStage().setX(bounds.getMinX());
	 * mainApp.getPrimaryStage().setY(bounds.getMinY());
	 * mainApp.getPrimaryStage().setWidth(bounds.getWidth());
	 * mainApp.getPrimaryStage().setHeight(bounds.getHeight());
	 * maximize.setStyle("-fx-background-image: url(icons/normalize.png);");
	 * mainApp.getPrimaryStage().show(); i++; if(patientsList.isSelected()) {
	 * patientsListAction(null); } for(int i=0;
	 * i<LoadPanels.getPatientListEntityes().size(); i++){
	 * LoadPanels.getPatientListEntityes().get(i).setMinWidth(java.awt.Toolkit.
	 * getDefaultToolkit().getScreenSize().getWidth()*0.91-10);
	 * LoadPanels.getPatientListEntityes().get(i).setMaxWidth(java.awt.Toolkit.
	 * getDefaultToolkit().getScreenSize().getWidth()*0.91-10); } } else {
	 *
	 * mainApp.getPrimaryStage().hide();
	 *
	 * Group group = new Group(mainBorderPane);
	 * group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().
	 * getHeight() / 1080.0);
	 * group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().
	 * getHeight() / 1080.0); group.setLayoutX(-mainBorderPane.getPrefWidth() *
	 * ((1 - group.getScaleX()) / 2));
	 * group.setLayoutY(-mainBorderPane.getPrefHeight() * ((1 -
	 * group.getScaleY()) / 2));
	 *
	 * Scene scene = new Scene(group);
	 * scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css")
	 * .toExternalForm()); mainApp.getPrimaryStage().setScene(scene);
	 * mainApp.getPrimaryStage().sizeToScene();
	 *
	 *
	 * maximize.setStyle("-fx-background-image: url(icons/maximize.png);");
	 * mainApp.getPrimaryStage().show();
	 * mainApp.getPrimaryStage().centerOnScreen(); i++;
	 * if(patientsList.isSelected()) { patientsListAction(null); }
	 *
	 * }
	 *
	 * }
	 */

	public void minimizeAction(ActionEvent actionEvent) {
		mainApp.getPrimaryStage().setIconified(true);
	}

	public void buttonAction(Event event) {
		// long started = System.currentTimeMillis();
		ToggleButton button = (ToggleButton) event.getTarget();
		Module module = (Module) button.getUserData();
		if (treatmentExists(module.getKey())) {
			LoadPanels.loadModule(mainApp, login, module.getKey(), event);
		} else {
			button.setSelected(false);
		}

		// long total = System.currentTimeMillis() - started;
		//// System.out.println("Time spent: " + total);
	}

	@FXML
	private void standardInvestigationActionTemporary(javafx.event.ActionEvent actionEvent) throws IOException {
		comingSoon();
		ToggleButton button = (ToggleButton) actionEvent.getTarget();
		button.setSelected(false);
	}

	private void comingSoon() {
		Notify notify = new Notify(AlertType.INFORMATION, MessageResource.getText("common.notify.info.message"));
		notify.showAndWait();
	}

	public void setScreen(Integer key) {

		final DoubleProperty opacity = centerPane.opacityProperty();

		// if(!centerPane.getChildren().isEmpty()){
		// // remove old scene
		// centerPane.getChildren().remove(0);
		// }
		// // add new scene
		// centerPane.getChildren().add(0,moScenes.get(key));

		if (!centerPane.getChildren().isEmpty()) {
			/*
			 * Timeline fadeOut = new Timeline(new KeyFrame(Duration.ZERO, new
			 * KeyValue(opacity, 1.0)), new KeyFrame(new Duration(1000), new
			 * KeyValue(opacity, 0.0)), new KeyFrame(new Duration(1000), new
			 * EventHandler<ActionEvent>() {
			 *
			 * @Override public void handle(ActionEvent event) {
			 *
			 * // remove old scene centerPane.getChildren().remove(0); // add
			 * new scene centerPane.getChildren().add(0, moScenes.get(key)); //
			 * add animation Timeline fadeIn = new Timeline(new
			 * KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), new
			 * KeyFrame(new Duration(1800), new KeyValue(opacity, 1.0)));
			 * fadeIn.play(); }
			 *
			 * })); fadeOut.play();
			 */

			Timeline fadeOut = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
					new KeyFrame(new Duration(1000), new KeyValue(opacity, 0.0)),

					new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							// remove old scene
							centerPane.getChildren().remove(0);

							// add new scene
							centerPane.getChildren().add(0, moScenes.get(key));

							// add animation
							Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
									new KeyFrame(new Duration(1800), new KeyValue(opacity, 1.0)));
							fadeIn.play();

						}

					})

			);
			fadeOut.play();

		} else {
			centerPane.setOpacity(0.0);
			centerPane.getChildren().add(0, moScenes.get(key));
			Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
					new KeyFrame(new Duration(2000), new KeyValue(opacity, 1.0)));
			fadeIn.play();
		}

	}

	public void setScreenForRecentPage() {

		final DoubleProperty opacity = centerPane.opacityProperty();

		if (!centerPane.getChildren().isEmpty()) {

			centerPane.setOpacity(0.0);
			// centerPane.getChildren().add(0, moScenes.get(key));
			Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
					new KeyFrame(new Duration(2000), new KeyValue(opacity, 1.0)));
			fadeIn.play();
		}

	}

	// public void standardInvestigationAction(javafx.event.ActionEvent
	// actionEvent) throws IOException {
	// comingSoon();
	// }

	// private void comingSoon() {
	// Notify notify = new Notify(AlertType.INFORMATION,
	// MessageResource.getText("common.notify.info.message"));
	// notify.showAndWait();
	// }

	@SuppressWarnings("unchecked")
	private boolean treatmentExists(int moduleKey) {

		boolean retVal = false;
		if (!FileUtils.isOpenWithoutTreatmentStart(moduleKey)) {
			sessionObject = Session.getInstance().getSessionObject();
			Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
			couple = coupleService.getCoupleById(coupleId);
			Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
			Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);

			if (couple != null) {

				womanCode = codesService.getCodeById(womanCodeId);
				manCode = codesService.getCodeById(manCodeId);
				if (null != womanCode && null != manCode) {
					retVal = true;
				} else {
					retVal = false;
					Notify alert = new Notify(AlertType.ERROR);
					alert.setContentText(MessageResource.getText("mainpage.controller.common.error.message"));
					alert.showAndWait();
				}
			}
		} else {
			retVal = true;
		}

		return retVal;
	}
}