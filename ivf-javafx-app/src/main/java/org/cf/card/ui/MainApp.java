package org.cf.card.ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.LoadProperties;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private static final Logger log = Logger.getLogger(MainApp.class);

    private Stage primaryStage;
    private Stage patientFileStage;
    private Stage dayDialog;
    private Stage appointmentPopup;
    private Stage addProcedurePopup;
    private Stage addPaymentPopup;
    private BorderPane rootLayout;
    static String configDir;
    private double height;
    private double width;

    public double getHeight() {
	return height;
    }

    public double getWidth() {
	return width;
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
	try {
	    log.info("Initilaizng ivf-app application");
	    // initialize Resource Bundle
	    MessageResource.init(Locale.UK);

	    this.primaryStage = primaryStage;
	    this.primaryStage.setTitle(MessageResource.getText("application.title"));
	    this.patientFileStage = new Stage();
	    patientFileStage.initStyle(StageStyle.TRANSPARENT);
	    patientFileStage.initModality(Modality.WINDOW_MODAL);
	    patientFileStage.initOwner(this.primaryStage);

	    this.dayDialog = new Stage();
	    dayDialog.initStyle(StageStyle.TRANSPARENT);
	    dayDialog.initModality(Modality.WINDOW_MODAL);
	    dayDialog.initOwner(this.primaryStage);

	    this.appointmentPopup = new Stage();
	    appointmentPopup.initStyle(StageStyle.TRANSPARENT);
	    appointmentPopup.initModality(Modality.WINDOW_MODAL);
	    appointmentPopup.initOwner(this.primaryStage);
	    
	    this.addProcedurePopup = new Stage();
	    addProcedurePopup.initStyle(StageStyle.TRANSPARENT);
	    addProcedurePopup.initModality(Modality.WINDOW_MODAL);
	    addProcedurePopup.initOwner(this.primaryStage);
	    
	    this.addPaymentPopup = new Stage();
	    addPaymentPopup.initStyle(StageStyle.TRANSPARENT);
	    addPaymentPopup.initModality(Modality.WINDOW_MODAL);
	    addPaymentPopup.initOwner(this.primaryStage);
	    
	    String absoluteConfigDir = new File(configDir).getAbsolutePath();

	    try {
		log.info("Loading application.properties");
		LoadProperties.initializeProperties(absoluteConfigDir);
	    } catch (IOException | HttpClientErrorException | ResourceAccessException e) {
		log.error("props file not found", e);
		LoadProperties.findApplicationPropertiesFile(absoluteConfigDir);
	    }

	    try {
		log.info("Testing app server url");
		LoadProperties.testServerUrl(absoluteConfigDir);
	    } catch (IOException | HttpClientErrorException | ResourceAccessException e) {
		log.error("Server URL not found", e);
		LoadProperties.findServerUrl(absoluteConfigDir);
	    }

	    try {
		log.info("Testing design directory");
		LoadProperties.testDesignsDirectory(absoluteConfigDir);

	    } catch (IOException | HttpClientErrorException | ResourceAccessException e) {
		// e.printStackTrace();
		log.error("Design directory", e);
		LoadProperties.findDesignsDirectory(absoluteConfigDir);
	    }

	    log.info("Testing license");
	    if (LoadProperties.testLicense()) {
		// start the program
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
			try {
			    log.info("Launch Copyright screen");
			    initCopyright();
			} catch (InterruptedException e1) {
			    e1.printStackTrace();
			}
		    }
		});
	    } else {

		LoadProperties.findLicense(absoluteConfigDir);
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
			try {
			    initCopyright();
			} catch (InterruptedException e1) {
			    e1.printStackTrace();
			}
		    }
		});
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    // System.out.println(e);
	    log.error("Exception occurred" + e.getMessage(), e);
	}
	// setting Open Sans fonts
	fontStyle();
    }

    public void initCopyright() throws InterruptedException {
	LoadPanels.loadCopyrightPanel(rootLayout, primaryStage, this);
	height = this.getPrimaryStage().getHeight();
	width = this.getPrimaryStage().getWidth();
	patientFileStage.getIcons().add(new Image("/images/ivf(2).png"));
    }

    public static void fontStyle() {
	try {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    // File regularFont = new
	    // File(Configuration.class.getResource("/CSS/fonts/OpenSans-Regular.ttf").getFile());
	    // File semiboldFont = new File(
	    // Configuration.class.getResource("/CSS/fonts/OpenSans-Semibold.ttf").getFile());
	    // File boldFont = new
	    // File(Configuration.class.getResource("/CSS/fonts/OpenSans-Bold.ttf").getFile());
	    // File italicFont = new
	    // File(Configuration.class.getResource("/CSS/fonts/OpenSans-Italic.ttf").getFile());

	    InputStream regularFont = Configuration.class.getResourceAsStream("/CSS/fonts/OpenSans-Regular.ttf");
	    InputStream semiboldFont = Configuration.class.getResourceAsStream("/CSS/fonts/OpenSans-Semibold.ttf");
	    InputStream boldFont = Configuration.class.getResourceAsStream("/CSS/fonts/OpenSans-Bold.ttf");
	    InputStream italicFont = Configuration.class.getResourceAsStream("/CSS/fonts/OpenSans-Italic.ttf");

	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, regularFont));
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, semiboldFont));
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, boldFont));
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, italicFont));

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public Stage getPrimaryStage() {
	return primaryStage;
    }

    public static void main(String[] args) {
	configDir = args.length > 0 ? args[0] : "";
	launch(args);
    }

    public Stage getPatientFileStage() {
	return patientFileStage;
    }

    public Stage getDayDialog() {
	return dayDialog;
    }

    public Stage getAppointmentPopup() {
	return appointmentPopup;
    }

	public Stage getAddProcedurePopup() {
		return addProcedurePopup;
	}

	public Stage getAddPaymentPopup() {
		return addPaymentPopup;
	}
	
    
}