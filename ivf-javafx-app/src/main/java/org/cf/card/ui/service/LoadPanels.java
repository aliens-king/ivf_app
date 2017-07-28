package org.cf.card.ui.service;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.controller.CopyrightPageController;
import org.cf.card.ui.controller.DirectoryPageController;
import org.cf.card.ui.controller.LoginController;
import org.cf.card.ui.controller.MainPageController;
import org.cf.card.ui.controller.PatientFileController;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Dell on 8/1/2015.
 */
public class LoadPanels {

	private static FXMLLoader copyrightLoader = new FXMLLoader();
	private static FXMLLoader loginLoader = new FXMLLoader();
	private static FXMLLoader directoryLoader = new FXMLLoader();
	private static FXMLLoader tableLoader = new FXMLLoader();
	private static FXMLLoader addPatientsLoader = new FXMLLoader();
	private static FXMLLoader patientListLoader = new FXMLLoader();
	private static FXMLLoader detailsLoader = new FXMLLoader();
	private static FXMLLoader addAccountsLoader = new FXMLLoader();
	private static FXMLLoader mainPageLoader = new FXMLLoader();
	private static FXMLLoader embryologyPageLoader = new FXMLLoader();
	private static FXMLLoader standardInvestigationLoader = new FXMLLoader();
	private static FXMLLoader cryopreservationEmbryoLoader = new FXMLLoader();
	private static FXMLLoader cryopreservationSemenLoader = new FXMLLoader();
	private static FXMLLoader embryologySemenPreparationLoader = new FXMLLoader();
	private static FXMLLoader andrologySemenLoader = new FXMLLoader();
	private static FXMLLoader embrylogyTransferLoader = new FXMLLoader();
	private static FXMLLoader embryologyThawingLoader = new FXMLLoader();
	private static FXMLLoader nurseStationLoader = new FXMLLoader();
	private static FXMLLoader doctorOfficeLoader = new FXMLLoader();
	private static FXMLLoader etTableLoader = new FXMLLoader();
	private static FXMLLoader appointmentsLoader = new FXMLLoader();
	private static FXMLLoader cyclesLoader = new FXMLLoader();
	private static FXMLLoader successfullPageLoader = new FXMLLoader();
	private static FXMLLoader appointmentsScreenLoader = new FXMLLoader();
	private static FXMLLoader companyDetailsLoader = new FXMLLoader();
	private static FXMLLoader archiveDetailsLoader = new FXMLLoader();
	private static FXMLLoader pregnancyOutcomesLoader = new FXMLLoader();
	private static FXMLLoader etScreenLoader = new FXMLLoader();
	
	//Billing Modules Loader
	private static FXMLLoader billingSetupLoader = new FXMLLoader();
	private static FXMLLoader billingAndInvoiceLoader = new FXMLLoader();
	private static FXMLLoader paymentDetailsLoader = new FXMLLoader();
	private static FXMLLoader InvoicesDetailLoader = new FXMLLoader();
	private static FXMLLoader refundAndInvoiceLoader = new FXMLLoader();
	private static FXMLLoader refundBillPerCoupleLoader = new FXMLLoader();
	private static FXMLLoader refundBillOverallLoader = new FXMLLoader();
	private static FXMLLoader unpaidBillPerCoupleLoader = new FXMLLoader();
	private static FXMLLoader unpaidBillOverallLoader = new FXMLLoader();

	
	private static List<FXMLLoader> patientListEntityLoader = new ArrayList<>();
	private static List<FXMLLoader> archiveEntityLoader = new ArrayList<>();
	private static List<FXMLLoader> archiveHBoxLoader = new ArrayList<>();
	private static List<FXMLLoader> embryologyEntityLoader = new ArrayList<>();
	public static AnchorPane tablePane = new AnchorPane();
	public static AnchorPane addPatientsPane = new AnchorPane();
	public static AnchorPane patientsListPane = new AnchorPane();
	public static AnchorPane detailsPane = new AnchorPane();
	public static AnchorPane addAccountsPane = new AnchorPane();
	public static BorderPane mainPage = new BorderPane();
	public static BorderPane loginPage = new BorderPane();
	public static BorderPane directoryPage = new BorderPane();
	public static AnchorPane embryologyPane = new AnchorPane();
	public static AnchorPane standardInvestigationPane = new AnchorPane();
	public static AnchorPane cryopreservationEmbryoPane = new AnchorPane();
	public static AnchorPane cryopreservationSemenPane = new AnchorPane();
	public static AnchorPane embryologySemenPreparationPane = new AnchorPane();
	public static AnchorPane andryologySemenPane = new AnchorPane();
	public static AnchorPane embrylogyTransferPane = new AnchorPane();
	public static AnchorPane embryologyThawingPane = new AnchorPane();
	public static AnchorPane nurseStationPane = new AnchorPane();
	public static AnchorPane doctorOfficePane = new AnchorPane();
	public static AnchorPane etTablePane = new AnchorPane();
	public static AnchorPane appointmentsPane = new AnchorPane();
	public static AnchorPane appointmentsScreenPane = new AnchorPane();
	public static AnchorPane cyclesPane = new AnchorPane();
	public static AnchorPane companyDetailsPane = new AnchorPane();
	public static AnchorPane archivePane = new AnchorPane();
	public static AnchorPane pregnancyOutcomesPane = new AnchorPane();
	public static AnchorPane etScreenPane = new AnchorPane();
	
	//Billing Modules Screen
	public static AnchorPane billingSetupPane = new AnchorPane();
	public static AnchorPane billingAndInvoicePane = new AnchorPane();
	public static AnchorPane paymentDetailsPane  = new AnchorPane();
	public static AnchorPane InvoicesDetailpane = new AnchorPane();
	public static AnchorPane refundAndInvoicePane = new AnchorPane();
	public static AnchorPane refundBillPerCouplePane = new AnchorPane();
	public static AnchorPane refundBillOverallPane  = new AnchorPane();
	public static AnchorPane unpaidBillPerCouplepane = new AnchorPane();
	public static AnchorPane unpaidBillOverallpane = new AnchorPane();
	

	public static ArrayList<AnchorPane> patientListEntityes = new ArrayList<>();
	public static ArrayList<AnchorPane> archiveEntityes = new ArrayList<>();
	public static ArrayList<AnchorPane> archiveHBoxEntityes = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static void patientFileDialog(Couple couple, MainApp mainApp, User login) {
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
			mainApp.getPatientFileStage().centerOnScreen();
			// Rectangle2D primaryScreenBounds =
			// Screen.getPrimary().getVisualBounds();
			// mainApp.getPatientFileStage().setX(0);
			// mainApp.getPatientFileStage().setY(0);

			mainApp.getPatientFileStage().show();
			PatientFileController controller = loader.getController();
			/* maintaining the session data for couple object */
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();

			sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple.getId());
			List<Codes> aoWomanCode = couple.getWoman().getCodes();
			if (!aoWomanCode.isEmpty()) {
				Codes womanCode = aoWomanCode.get(aoWomanCode.size() - 1);
				sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, womanCode != null ? womanCode.getId() : 0l);
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
			controller.setMainApp(mainApp);
			controller.setLogin(login);
			controller.setCouple(couple);
			MainPageController mainPageController = LoadPanels.getMainPageLoader().getController();
			mainPageController.setCouple(couple);
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	public static void loadCopyrightPanel(BorderPane rootLayout, Stage primaryStage, MainApp mainApp) {
		try {
			copyrightLoader.setResources(MessageResource.getResourceBundle());
			copyrightLoader.setLocation(MainApp.class.getResource("/view/CopyrightPage.fxml"));
			rootLayout = copyrightLoader.load();
			CopyrightPageController controller = copyrightLoader.getController();
			controller.setMainApp(mainApp);
			// Group group = new Group(rootLayout);
			// group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
			// / 1080.0);
			// group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
			// / 1080.0);
			// group.setLayoutX(-rootLayout.getPrefWidth() * ((1 -
			// group.getScaleX()) / 2));
			// group.setLayoutY(-rootLayout.getPrefHeight() * ((1 -
			// group.getScaleY()) / 2));
			// Scene scene = new Scene(group);

			Scene scene = getSceneForHalfScreen(rootLayout);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new javafx.scene.image.Image("/icons/logotaskbar.png"));
			scene.getStylesheets().add(MainApp.class.getResource("/CSS/Copyright.css").toExternalForm());
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void loadLoginPanel(MainApp mainApp) {

		// Group group = new Group(loginPage);
		//
		// group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
		// / 1080.0);
		// group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
		// / 1080.0);
		// group.setLayoutX(-loginPage.getPrefWidth() * ((1 - group.getScaleX())
		// / 2));
		// group.setLayoutY(-loginPage.getPrefHeight() * ((1 -
		// group.getScaleY()) / 2));
		// Scene scene = new Scene(group);
		Scene scene = getSceneForHalfScreen(loginPage);

		mainApp.getPrimaryStage().setScene(scene);
		scene.getStylesheets().add(MainApp.class.getResource("/CSS/Login.css").toExternalForm());
		mainApp.getPrimaryStage().show();
		LoginController controller = loginLoader.getController();
		controller.setMainApp(mainApp);

	}

	public static void loadModule(MainApp mainApp, User login, int moduleKey, Event event) {

		if (EnumPermission.canView(login.getRoleId(), moduleKey)) {
			MainPageController controller = null;
			if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
				controller = LoadPanels.loadMainPageHalfScreen(mainApp, login);
			} else {
				controller = LoadPanels.loadMainPageFullScreen(mainApp, login);
			}
			viewModule(moduleKey, controller, event);
		} else {
			if (event.getTarget() instanceof ToggleButton) {
				ToggleButton button = (ToggleButton) event.getTarget();
				button.setSelected(false);
				FileUtils.privillegeError();
			} else {
				FileUtils.privillegeError();
			}
		}
	}

	public static DirectoryPageController loadDirectoryPageFullScreen(MainApp mainApp, User login) {

		// StackPane group = new StackPane(directoryPage);
		// Scene scene = new Scene(group);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		Scene scene = getSceneForFullScreen(directoryPage);
		mainApp.getPrimaryStage().setScene(scene);
		scene.getStylesheets().add(MainApp.class.getResource("/CSS/Directory.css").toExternalForm());

		mainApp.getPrimaryStage().setX(bounds.getMinX());
		mainApp.getPrimaryStage().setY(bounds.getMinY());
		mainApp.getPrimaryStage().setWidth(bounds.getWidth());
		mainApp.getPrimaryStage().setHeight(bounds.getHeight());
		mainApp.getPrimaryStage().show();
		DirectoryPageController controller = directoryLoader.getController();
		controller.setMainApp(mainApp);
		controller.setLogin(login);

		return controller;
	}

	public static DirectoryPageController loadDirectoryPageHalfScreen(MainApp mainApp, User login) {

		// Group group = new Group(directoryPage);

		Scene scene = getSceneForHalfScreen(directoryPage);

		mainApp.getPrimaryStage().setScene(scene);
		scene.getStylesheets().add(MainApp.class.getResource("/CSS/Directory.css").toExternalForm());
		mainApp.getPrimaryStage().show();
		DirectoryPageController controller = directoryLoader.getController();
		controller.setMainApp(mainApp);
		controller.setLogin(login);

		return controller;
	}

	public static MainPageController loadMainPageFullScreen(MainApp mainApp, User login) {

		// StackPane group = new StackPane(mainPage);
		// Scene scene = new Scene(group);
		Scene scene = getSceneForFullScreen(mainPage);
		mainApp.getPrimaryStage().setScene(scene);
		scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css").toExternalForm());
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		mainApp.getPrimaryStage().setX(bounds.getMinX());
		mainApp.getPrimaryStage().setY(bounds.getMinY());
		mainApp.getPrimaryStage().setWidth(bounds.getWidth());
		mainApp.getPrimaryStage().setHeight(bounds.getHeight());
		mainApp.getPrimaryStage().show();
		MainPageController controller = mainPageLoader.getController();
		controller.setMainApp(mainApp);
		controller.setLogin(login);
		return controller;

	}

	public static MainPageController loadMainPageHalfScreen(MainApp mainApp, User login) {

		// Group group = new Group(mainPage);
		// group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
		// / 1080.0);
		// group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
		// / 1080.0);
		// group.setLayoutX(-mainPage.getPrefWidth() * ((1 - group.getScaleX())
		// / 2));
		// group.setLayoutY(-mainPage.getPrefHeight() * ((1 - group.getScaleY())
		// / 2));
		Scene scene = getSceneForHalfScreen(mainPage);

		mainApp.getPrimaryStage().setScene(scene);
		scene.getStylesheets().add(MainApp.class.getResource("/CSS/MainPage.css").toExternalForm());
		mainApp.getPrimaryStage().show();
		MainPageController controller = mainPageLoader.getController();
		controller.setMainApp(mainApp);
		controller.setLogin(login);
		return controller;
	}

	public static Scene getSceneForHalfScreen(Pane pane) {

		Group group = new Group(pane);
		group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
		group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080.0);
		group.setLayoutX(-pane.getPrefWidth() * ((1 - group.getScaleX()) / 2));
		group.setLayoutY(-pane.getPrefHeight() * ((1 - group.getScaleY()) / 2));
		Scene scene = new Scene(group);

		return scene;
	}

	public static Scene getSceneForFullScreen(Pane pane) {

		Group group = new Group(pane);
		group.scaleXProperty().set(Constants.SCREEN_SIZE.getWidth() / pane.getPrefWidth());
		group.scaleYProperty().set((Constants.SCREEN_SIZE.getHeight() / pane.getPrefHeight()) - 0.037);
		group.setLayoutX(-pane.getPrefWidth() * ((1 - group.getScaleX()) / 2));
		group.setLayoutY(-pane.getPrefHeight() * ((1 - group.getScaleY()) / 2));
		Scene scene = new Scene(group);

		return scene;
	}

	private static void viewModule(int moduleKey, MainPageController controller, Event event) {
		if (Module.DIRECTORY.getKey() == moduleKey) {
			controller.directoryAction(null);
		} else if (Module.REGISTER_PATIENT.getKey() == moduleKey) {
			controller.addPatientsAction(null);
		} else if (Module.PATIENT_LIST.getKey() == moduleKey) {
			controller.patientsListAction((ActionEvent) event);
		} else if (Module.SEARCH_PATIENTS.getKey() == moduleKey) {
			controller.searchPatientsAction(null);
		} else if (Module.PRINT.getKey() == moduleKey) {
			controller.patientsListAction(null);
		} else if (Module.SEARCH_BY_NAME_CODE.getKey() == moduleKey) {
			controller.searchPatientsAction(null);
		} else if (Module.APPOINTMENT_OVERVIEW.getKey() == moduleKey) {
			controller.appointmentOverviewAction(null);
		} else if (Module.REGISTER_USER.getKey() == moduleKey) {
			controller.addAccountsDetails(null);
		} else if (Module.USER_DETAILS.getKey() == moduleKey) {
			controller.accountDetailsAction(null);
		} else if (Module.EMBRYOLOGY_OVERVIEW.getKey() == moduleKey) {
			controller.embryologyAction(null);
		} else if (Module.EMBRYO_TRANSFER.getKey() == moduleKey) {
			controller.embryoTransferAction(null);
		} else if (Module.EGG_THAWING.getKey() == moduleKey) {
			controller.embryoThawingAction(null);
		} else if (Module.ANDROLOGY.getKey() == moduleKey) {
			controller.andrologySemenAction(null);
		} else if (Module.CRYOPRESERVATION_E.getKey() == moduleKey) {
			controller.cryopreservationEmbryAction(null);
		} else if (Module.CRYOPRESERVATION_A.getKey() == moduleKey) {
			controller.cryopreservationSemenAction(null);
		} else if (Module.ET_TABLE.getKey() == moduleKey) {
			controller.etTableAction(null);
		} else if (Module.DOCTORS_OFFICE.getKey() == moduleKey) {
			controller.doctorsOfficeAction(null);
		} else if (Module.NURSES_STATION.getKey() == moduleKey) {
			controller.nursesStationAction(null);
		} else if (Module.STANDARD_INVESTIGATION.getKey() == moduleKey) {
			controller.standardInvestigationAction(null);

		} else if (Module.CYCLES.getKey() == moduleKey) {
			controller.cyclesAction(null);
		} else if (Module.SEMEN_PREPRATION.getKey() == moduleKey) {
			controller.embryoSemenPreparationAction(null);
		} else if (Module.APPOINTMENT_SCREEN.getKey() == moduleKey) {
			controller.appointmentScreenAction(null);
		} else if (Module.COMPANY_DETAILS.getKey() == moduleKey) {
			controller.companyDetailsAction(null);
		} else if (Module.ARCHIVE.getKey() == moduleKey) {
			controller.archiveAction(null);
		} else if (Module.PREGNANCY_OUTCOMES.getKey() == moduleKey) {
			controller.pregnancyOutcomesAction(null);
		} else if (Module.RECENT_ARCHIVE.getKey() == moduleKey) {
			controller.patientsListAction((ActionEvent) event);
		} else if (Module.ET_SCREEN.getKey() == moduleKey) {
			controller.etScreenAction(null);
		}else if (Module.BILLING_SETUP.getKey() == moduleKey) {
			controller.billingSetupAction(null);
		} else if (Module.BILLING_AND_INVOICE.getKey() == moduleKey) {
			controller.billingAndInvoiceAction(null);
		} else if (Module.PAYMENT_DETAILS.getKey() == moduleKey) {
			controller.paymentDetailsAction(null);
		} else if (Module.INVOICES_DETAIL.getKey() == moduleKey) {
			controller.invoiceDetailAction(null);
		} else if (Module.REFUND_AND_INVOICE.getKey() == moduleKey) {
			controller.refundAndInvoiceAction(null);
		} else if (Module.REFUND_BILL_PER_COUPLE.getKey() == moduleKey) {
			controller.refundBillPerCoupleAction(null);
		} else if (Module.REFUND_BILL_OVERALL.getKey() == moduleKey) {
			controller.refundBillOverallAction(null);
		} else if (Module.UNPAID_BILL_PER_COUPLE.getKey() == moduleKey) {
			controller.unpaidBillPerCoupleAction(null);
		} else if (Module.UNPAID_BILL_OVERALL.getKey() == moduleKey) {
			controller.unpaidBillOverallAction(null);
		} else {
			// open search screen by default
			controller.searchPatientsAction(null);
		}

		controller.setScreen(moduleKey);
		// open search screen in case a new treatment to be started
//		controller.searchPatientsAction(null);
//		controller.setScreen(Module.SEARCH_PATIENTS.getKey());
//		if(Module.RECENT_PAGE.getKey() != moduleKey)
//			controller.setScreen(moduleKey);
//		else {
//			controller.setScreenForRecentPage();
//		}

	}

	public static FXMLLoader getCopyrightLoader() {
		return copyrightLoader;
	}

	public static FXMLLoader getSuccessfullPageLoader() {
		return successfullPageLoader;
	}

	public static void setCopyrightLoader(FXMLLoader copyrightLoader) {
		LoadPanels.copyrightLoader = copyrightLoader;
	}

	public static FXMLLoader getLoginLoader() {
		return loginLoader;
	}

	public static void setLoginLoader(FXMLLoader loginLoader) {
		LoadPanels.loginLoader = loginLoader;
	}

	public static FXMLLoader getDirectoryLoader() {
		return directoryLoader;
	}

	public static void setDirectoryLoader(FXMLLoader directoryLoader) {
		LoadPanels.directoryLoader = directoryLoader;
	}

	public static FXMLLoader getTableLoader() {
		return tableLoader;
	}

	public static void setTableLoader(FXMLLoader tableLoader) {
		LoadPanels.tableLoader = tableLoader;
	}

	public static FXMLLoader getAddPatientsLoader() {
		return addPatientsLoader;
	}

	public static void setAddPatientsLoader(FXMLLoader addPatientsLoader) {
		LoadPanels.addPatientsLoader = addPatientsLoader;
	}

	public static FXMLLoader getPatientListLoader() {
		return patientListLoader;
	}

	public static void setPatientListLoader(FXMLLoader patientListLoader) {
		LoadPanels.patientListLoader = patientListLoader;
	}

	public static List<FXMLLoader> getPatientListEntityLoader() {
		return patientListEntityLoader;
	}

	public static void setPatientListEntityLoader(ArrayList<FXMLLoader> patientListEntityLoader) {
		LoadPanels.patientListEntityLoader = patientListEntityLoader;
	}

	public static ArrayList<AnchorPane> getPatientListEntityes() {
		return patientListEntityes;
	}

	public static void setPatientListEntityes(ArrayList<AnchorPane> patientListEntityes) {
		LoadPanels.patientListEntityes = patientListEntityes;
	}

	public static FXMLLoader getDetailsLoader() {
		return detailsLoader;
	}

	public static void setDetailsLoader(FXMLLoader detailsLoader) {
		LoadPanels.detailsLoader = detailsLoader;
	}

	public static FXMLLoader getAddAccountsLoader() {
		return addAccountsLoader;
	}

	public static void setAddAccountsLoader(FXMLLoader addAccountsLoader) {
		LoadPanels.addAccountsLoader = addAccountsLoader;
	}

	public static FXMLLoader getMainPageLoader() {
		return mainPageLoader;
	}

	public static void setMainPageLoader(FXMLLoader mainPageLoader) {
		LoadPanels.mainPageLoader = mainPageLoader;
	}

	public static AnchorPane getTablePane() {
		return tablePane;
	}

	public static void setTablePane(AnchorPane tablePane) {
		LoadPanels.tablePane = tablePane;
	}

	public static AnchorPane getAddPatientsPane() {
		return addPatientsPane;
	}

	public static void setAddPatientsPane(AnchorPane addPatientsPane) {
		LoadPanels.addPatientsPane = addPatientsPane;
	}

	public static AnchorPane getPatientsListPane() {
		return patientsListPane;
	}

	public static void setPatientsListPane(AnchorPane patientsListPane) {
		LoadPanels.patientsListPane = patientsListPane;
	}

	public static AnchorPane getDetailsPane() {
		return detailsPane;
	}

	public static void setDetailsPane(AnchorPane detailsPane) {
		LoadPanels.detailsPane = detailsPane;
	}

	public static AnchorPane getAddAccountsPane() {
		return addAccountsPane;
	}

	public static void setAddAccountsPane(AnchorPane addAccountsPane) {
		LoadPanels.addAccountsPane = addAccountsPane;
	}

	public static void setPatientListEntityLoader(List<FXMLLoader> patientListEntityLoader) {
		LoadPanels.patientListEntityLoader = patientListEntityLoader;
	}

	public static ArrayList<AnchorPane> getArchiveEntityes() {
		return archiveEntityes;
	}

	public static void setArchiveEntityes(ArrayList<AnchorPane> archiveEntityes) {
		LoadPanels.archiveEntityes = archiveEntityes;
	}

	public static List<FXMLLoader> getArchiveEntityLoader() {
		return archiveEntityLoader;
	}

	public static void setArchiveEntityLoader(List<FXMLLoader> archiveEntityLoader) {
		LoadPanels.archiveEntityLoader = archiveEntityLoader;
	}

	public static List<FXMLLoader> getArchiveHBoxLoader() {
		return archiveHBoxLoader;
	}

	public static void setArchiveHBoxLoader(List<FXMLLoader> archiveHBoxLoader) {
		LoadPanels.archiveHBoxLoader = archiveHBoxLoader;
	}

	public static ArrayList<AnchorPane> getArchiveHBoxEntityes() {
		return archiveHBoxEntityes;
	}

	public static void setArchiveHBoxEntityes(ArrayList<AnchorPane> archiveHBoxEntityes) {
		LoadPanels.archiveHBoxEntityes = archiveHBoxEntityes;
	}

	public static FXMLLoader getEmbryologyPageLoader() {
		return embryologyPageLoader;
	}

	public static void setEmbryologyPageLoader(FXMLLoader embryologyPageLoader) {
		LoadPanels.embryologyPageLoader = embryologyPageLoader;
	}

	public static AnchorPane getEmbryologyPane() {
		return embryologyPane;
	}

	public static void setEmbryologyPane(AnchorPane embryologyPane) {
		LoadPanels.embryologyPane = embryologyPane;
	}

	public static List<FXMLLoader> getEmbryologyEntityLoader() {
		return embryologyEntityLoader;
	}

	public static void setEmbryologyEntityLoader(List<FXMLLoader> embryologyEntityLoader) {
		LoadPanels.embryologyEntityLoader = embryologyEntityLoader;
	}

	public static FXMLLoader getStandardInvestigationLoader() {
		return standardInvestigationLoader;
	}

	public static void setStandardInvestigationLoader(FXMLLoader standardInvestigationLoader) {
		LoadPanels.standardInvestigationLoader = standardInvestigationLoader;
	}

	public static AnchorPane getStandardInvestigationPane() {
		return standardInvestigationPane;
	}

	public static void setStandardInvestigationPane(AnchorPane standardInvestigationPane) {
		LoadPanels.standardInvestigationPane = standardInvestigationPane;
	}

	public static FXMLLoader getCryopreservationEmbryoLoader() {
		return cryopreservationEmbryoLoader;
	}

	public static void setCryopreservationEmbryoLoader(FXMLLoader cryopreservationEmbryoLoader) {
		LoadPanels.cryopreservationEmbryoLoader = cryopreservationEmbryoLoader;
	}

	public static FXMLLoader getCryopreservationSemenLoader() {
		return cryopreservationSemenLoader;
	}

	public static void setCryopreservationSemenLoader(FXMLLoader cryopreservationSemenLoader) {
		LoadPanels.cryopreservationSemenLoader = cryopreservationSemenLoader;
	}

	public static AnchorPane getCryopreservationEmbryoPane() {
		return cryopreservationEmbryoPane;
	}

	public static void setCryopreservationEmbryoPane(AnchorPane cryopreservationEmbryoPane) {
		LoadPanels.cryopreservationEmbryoPane = cryopreservationEmbryoPane;
	}

	public static AnchorPane getCryopreservationSemenPane() {
		return cryopreservationSemenPane;
	}

	public static void setCryopreservationSemenPane(AnchorPane cryopreservationSemenPane) {
		LoadPanels.cryopreservationSemenPane = cryopreservationSemenPane;
	}

	public static FXMLLoader getEmbryologySemenPreparationLoader() {
		return embryologySemenPreparationLoader;
	}

	public static void setEmbryologySemenPreparationLoader(FXMLLoader embryologySemenPreparationLoader) {
		LoadPanels.embryologySemenPreparationLoader = embryologySemenPreparationLoader;
	}

	public static AnchorPane getEmbryologySemenPreparationPane() {
		return embryologySemenPreparationPane;
	}

	public static void setEmbryologySemenPreparationPane(AnchorPane embryologySemenPreparationPane) {
		LoadPanels.embryologySemenPreparationPane = embryologySemenPreparationPane;
	}

	public static FXMLLoader getAndrologySemenLoader() {
		return andrologySemenLoader;
	}

	public static void setAndrologySemenLoader(FXMLLoader andrologySemenLoader) {
		LoadPanels.andrologySemenLoader = andrologySemenLoader;
	}

	public static AnchorPane getAndryologySemenPane() {
		return andryologySemenPane;
	}

	public static void setAndryologySemenPane(AnchorPane andryologySemenPane) {
		LoadPanels.andryologySemenPane = andryologySemenPane;
	}

	public static FXMLLoader getEmbrylogyTransferLoader() {
		return embrylogyTransferLoader;
	}

	public static void setEmbrylogyTransferLoader(FXMLLoader embrylogyTransferLoader) {
		LoadPanels.embrylogyTransferLoader = embrylogyTransferLoader;
	}

	public static AnchorPane getEmbrylogyTransferPane() {
		return embrylogyTransferPane;
	}

	public static void setEmbrylogyTransferPane(AnchorPane embrylogyTransferPane) {
		LoadPanels.embrylogyTransferPane = embrylogyTransferPane;
	}

	public static FXMLLoader getNurseStationLoader() {
		return nurseStationLoader;
	}

	public static void setNurseStationLoader(FXMLLoader nurseStationLoader) {
		LoadPanels.nurseStationLoader = nurseStationLoader;
	}

	public static AnchorPane getNurseStationPane() {
		return nurseStationPane;
	}

	public static void setNurseStationPane(AnchorPane nurseStationPane) {
		LoadPanels.nurseStationPane = nurseStationPane;
	}

	public static FXMLLoader getEmbryologyThawingLoader() {
		return embryologyThawingLoader;
	}

	public static void setEmbryologyThawingLoader(FXMLLoader embryologyThawingLoader) {
		LoadPanels.embryologyThawingLoader = embryologyThawingLoader;
	}

	public static AnchorPane getEmbryologyThawingPane() {
		return embryologyThawingPane;
	}

	public static void setEmbryologyThawingPane(AnchorPane embryologyThawingPane) {
		LoadPanels.embryologyThawingPane = embryologyThawingPane;
	}

	public static FXMLLoader getDoctorOfficeLoader() {
		return doctorOfficeLoader;
	}

	public static void setDoctorOfficeLoader(FXMLLoader doctorOfficeLoader) {
		LoadPanels.doctorOfficeLoader = doctorOfficeLoader;
	}

	public static AnchorPane getDoctorOfficePane() {
		return doctorOfficePane;
	}

	public static void setDoctorOfficePane(AnchorPane doctorOfficePane) {
		LoadPanels.doctorOfficePane = doctorOfficePane;
	}

	public static FXMLLoader getEtTableLoader() {
		return etTableLoader;
	}

	public static void setEtTableLoader(FXMLLoader etTableLoader) {
		LoadPanels.etTableLoader = etTableLoader;
	}

	public static AnchorPane getEtTablePane() {
		return etTablePane;
	}

	public static void setEtTablePane(AnchorPane etTablePane) {
		LoadPanels.etTablePane = etTablePane;
	}

	public static FXMLLoader getAppointmentsLoader() {
		return appointmentsLoader;
	}

	public static void setAppointmentsLoader(FXMLLoader appointmentsLoader) {
		LoadPanels.appointmentsLoader = appointmentsLoader;
	}

	public static AnchorPane getAppointmentsPane() {
		return appointmentsPane;
	}

	public static void setAppointmentsPane(AnchorPane appointmentsPane) {
		LoadPanels.appointmentsPane = appointmentsPane;
	}

	public static FXMLLoader getAppointmentsScreenLoader() {
		return appointmentsScreenLoader;
	}

	public static void setAppointmentsScreenLoader(FXMLLoader appointmentsScreenLoader) {
		LoadPanels.appointmentsScreenLoader = appointmentsScreenLoader;
	}

	public static AnchorPane getAppointmentsScreenPane() {
		return appointmentsScreenPane;
	}

	public static void setAppointmentsScreenPane(AnchorPane appointmentsScreenPane) {
		LoadPanels.appointmentsScreenPane = appointmentsScreenPane;
	}

	public static FXMLLoader getCyclesLoader() {
		return cyclesLoader;
	}

	public static void setCyclesLoader(FXMLLoader cyclesLoader) {
		LoadPanels.cyclesLoader = cyclesLoader;
	}

	public static AnchorPane getCyclesPane() {
		return cyclesPane;
	}

	public static void setCyclesPane(AnchorPane cyclesPane) {
		LoadPanels.cyclesPane = cyclesPane;
	}

	public static FXMLLoader getCompanyDetailsLoader() {
		return companyDetailsLoader;
	}

	public static void setCompanyDetailsLoader(FXMLLoader companyDetailsLoader) {
		LoadPanels.companyDetailsLoader = companyDetailsLoader;
	}

	public static AnchorPane getCompanyDetailsPane() {
		return companyDetailsPane;
	}

	public static void setCompanyDetailsPane(AnchorPane companyDetailsPane) {
		LoadPanels.companyDetailsPane = companyDetailsPane;
	}

	public static AnchorPane getArchivePane() {
		return archivePane;
	}

	public static void setArchivePane(AnchorPane archivePane) {
		LoadPanels.archivePane = archivePane;
	}

	public static FXMLLoader getArchiveDetailsLoader() {
		return archiveDetailsLoader;
	}

	public static void setArchiveDetailsLoader(FXMLLoader archiveDetailsLoader) {
		LoadPanels.archiveDetailsLoader = archiveDetailsLoader;
	}

	/**
	 * @return the pregnancyOutcomesLoader
	 */
	public static FXMLLoader getPregnancyOutcomesLoader() {
		return pregnancyOutcomesLoader;
	}

	/**
	 * @param pregnancyOutcomesLoader
	 *            the pregnancyOutcomesLoader to set
	 */
	public static void setPregnancyOutcomesLoader(FXMLLoader pregnancyOutcomesLoader) {
		LoadPanels.pregnancyOutcomesLoader = pregnancyOutcomesLoader;
	}

	/**
	 * @return the pregnancyOutcomesPane
	 */
	public static AnchorPane getPregnancyOutcomesPane() {
		return pregnancyOutcomesPane;
	}

	/**
	 * @param pregnancyOutcomesPane
	 *            the pregnancyOutcomesPane to set
	 */
	public static void setPregnancyOutcomesPane(AnchorPane pregnancyOutcomesPane) {
		LoadPanels.pregnancyOutcomesPane = pregnancyOutcomesPane;
	}

	public static FXMLLoader getEtScreenLoader() {
		return etScreenLoader;
	}

	public static void setEtScreenLoader(FXMLLoader etScreenLoader) {
		LoadPanels.etScreenLoader = etScreenLoader;
	}

	public static AnchorPane getEtScreenPane() {
		return etScreenPane;
	}

	public static void setEtScreenPane(AnchorPane etScreenPane) {
		LoadPanels.etScreenPane = etScreenPane;
	}

	//Billing Modules getter setter
	
	public static FXMLLoader getBillingSetupLoader() {
		return billingSetupLoader;
	}

	public static void setBillingSetupLoader(FXMLLoader billingSetupLoader) {
		LoadPanels.billingSetupLoader = billingSetupLoader;
	}

	public static AnchorPane getBillingSetupPane() {
		return billingSetupPane;
	}

	public static void setBillingSetupPane(AnchorPane billingSetupPane) {
		LoadPanels.billingSetupPane = billingSetupPane;
	}

	public static FXMLLoader getBillingAndInvoiceLoader() {
		return billingAndInvoiceLoader;
	}

	public static void setBillingAndInvoiceLoader(FXMLLoader billingAndInvoiceLoader) {
		LoadPanels.billingAndInvoiceLoader = billingAndInvoiceLoader;
	}

	public static AnchorPane getBillingAndInvoicePane() {
		return billingAndInvoicePane;
	}

	public static void setBillingAndInvoicePane(AnchorPane billingAndInvoicePane) {
		LoadPanels.billingAndInvoicePane = billingAndInvoicePane;
	}
	
	public static FXMLLoader getPaymentDetailsLoader() {
		return paymentDetailsLoader;
	}

	public static void setPaymentDetailsLoader(FXMLLoader paymentDetailsLoader) {
		LoadPanels.paymentDetailsLoader = paymentDetailsLoader;
	}

	public static FXMLLoader getInvoicesDetailLoader() {
		return InvoicesDetailLoader;
	}

	public static void setInvoicesDetailLoader(FXMLLoader invoicesDetailLoader) {
		InvoicesDetailLoader = invoicesDetailLoader;
	}

	public static AnchorPane getPaymentDetailsPane() {
		return paymentDetailsPane;
	}

	public static void setPaymentDetailsPane(AnchorPane paymentDetailsPane) {
		LoadPanels.paymentDetailsPane = paymentDetailsPane;
	}

	public static AnchorPane getInvoicesDetailpane() {
		return InvoicesDetailpane;
	}

	public static void setInvoicesDetailpane(AnchorPane invoicesDetailpane) {
		InvoicesDetailpane = invoicesDetailpane;
	}

	public static FXMLLoader getRefundAndInvoiceLoader() {
		return refundAndInvoiceLoader;
	}

	public static void setRefundAndInvoiceLoader(FXMLLoader refundAndInvoiceLoader) {
		LoadPanels.refundAndInvoiceLoader = refundAndInvoiceLoader;
	}

	public static FXMLLoader getRefundBillPerCoupleLoader() {
		return refundBillPerCoupleLoader;
	}

	public static void setRefundBillPerCoupleLoader(FXMLLoader refundBillPerCoupleLoader) {
		LoadPanels.refundBillPerCoupleLoader = refundBillPerCoupleLoader;
	}

	public static FXMLLoader getRefundBillOverallLoader() {
		return refundBillOverallLoader;
	}

	public static void setRefundBillOverallLoader(FXMLLoader refundBillOverallLoader) {
		LoadPanels.refundBillOverallLoader = refundBillOverallLoader;
	}

	public static FXMLLoader getUnpaidBillPerCoupleLoader() {
		return unpaidBillPerCoupleLoader;
	}

	public static void setUnpaidBillPerCoupleLoader(FXMLLoader unpaidBillPerCoupleLoader) {
		LoadPanels.unpaidBillPerCoupleLoader = unpaidBillPerCoupleLoader;
	}

	public static FXMLLoader getUnpaidBillOverallLoader() {
		return unpaidBillOverallLoader;
	}

	public static void setUnpaidBillOverallLoader(FXMLLoader unpaidBillOverallLoader) {
		LoadPanels.unpaidBillOverallLoader = unpaidBillOverallLoader;
	}

	public static AnchorPane getRefundAndInvoicePane() {
		return refundAndInvoicePane;
	}

	public static void setRefundAndInvoicePane(AnchorPane refundAndInvoicePane) {
		LoadPanels.refundAndInvoicePane = refundAndInvoicePane;
	}

	public static AnchorPane getRefundBillPerCouplePane() {
		return refundBillPerCouplePane;
	}

	public static void setRefundBillPerCouplePane(AnchorPane refundBillPerCouplePane) {
		LoadPanels.refundBillPerCouplePane = refundBillPerCouplePane;
	}

	public static AnchorPane getRefundBillOverallPane() {
		return refundBillOverallPane;
	}

	public static void setRefundBillOverallPane(AnchorPane refundBillOverallPane) {
		LoadPanels.refundBillOverallPane = refundBillOverallPane;
	}

	public static AnchorPane getUnpaidBillPerCouplepane() {
		return unpaidBillPerCouplepane;
	}

	public static void setUnpaidBillPerCouplepane(AnchorPane unpaidBillPerCouplepane) {
		LoadPanels.unpaidBillPerCouplepane = unpaidBillPerCouplepane;
	}

	public static AnchorPane getUnpaidBillOverallpane() {
		return unpaidBillOverallpane;
	}

	public static void setUnpaidBillOverallpane(AnchorPane unpaidBillOverallpane) {
		LoadPanels.unpaidBillOverallpane = unpaidBillOverallpane;
	}

}
