package org.cf.card.ui.service;

import java.io.IOException;
import java.util.Optional;

import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.configuration.MessageResource;
import org.springframework.web.client.HttpClientErrorException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by Dell on 8/1/2015.
 */
public class LoadProperties {

    public static void findApplicationPropertiesFile(String absoluteConfigDir) {
        boolean succeded = false;
        while (succeded == false) {
        	Object[] params = new Object[3];
            //String separator =
        	params[0] = absoluteConfigDir;

            params[1]=	System.getProperty("file.separator");

            params[2]=  System.getProperty("file.separator");

          String content = MessageResource.getText("loadproperties.text.content",params);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(MessageResource.getText("common.text.warning"));
            alert.setHeaderText(MessageResource.getText("loadproperties.text.header"));
            alert.setContentText(content);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    initializeProperties(absoluteConfigDir);
                    succeded = true;
                } catch (IOException | HttpClientErrorException e) {
                    succeded = false;
                }
            } else {
                System.exit(0);
            }
        }

    }

    public static void initializeProperties(String absoluteConfigDir) throws IOException, HttpClientErrorException {

        Configuration.init(absoluteConfigDir);
       // MessageResource.init(absoluteConfigDir);


    }

    public static void findServerUrl(String absoluteConfigDir) {
        boolean succeded = false;

        while (succeded == false) {
            TextInputDialog dialog = new TextInputDialog(Configuration.getServerUrl());

            dialog.setTitle(MessageResource.getText("loadproperties.find.serverurl.title"));
            dialog.setHeaderText(MessageResource.getText("loadproperties.find.serverurl.content.header.text"));
            dialog.setContentText(MessageResource.getText("loadproperties.find.serverurl.content.text"));
            dialog.setWidth(500);
            dialog.setHeight(200);
            Optional<String> result2 = dialog.showAndWait();
            if (result2.isPresent()) {
                Configuration.setServerUrl(result2.get());
            }
            if (dialog.getResult() == null)
                System.exit(0);

            Configuration.saveParamChanges(absoluteConfigDir);
            try {
                testServerUrl(absoluteConfigDir);
                succeded = true;
            } catch (IOException | HttpClientErrorException e) {
                succeded = false;
            }
        }
    }

    public static void testServerUrl(String absoluteConfigDir) throws IOException {
        Configuration.validate(absoluteConfigDir);
    }

    public static void testDesignsDirectory(String absoluteConfigDir) throws IOException {
//        BufferedImage img = null;
//        img = ImageIO.read(new File(Configuration.getClipartDir() + File.separator + "1.jpg"));
    	Configuration.validateDesignsDirectory(Configuration.getClipartDir() + "1.jpg");
    }

    public static void findDesignsDirectory(String absoluteConfigDir) {

        boolean succeded = false;
        while (succeded == false) {
            TextInputDialog dialog = new TextInputDialog(Configuration.getClipartDir());
            dialog.setTitle(MessageResource.getText("loadproperties.find.designdirectory.title"));
            dialog.setHeaderText(MessageResource.getText("loadproperties.find.designdirectory.header.text"));
            dialog.setContentText(MessageResource.getText("loadproperties.find.designdirectory.content.text"));
            dialog.setWidth(600);
            dialog.setHeight(200);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                Configuration.setClipartDir(result.get());
            }

            if (dialog.getResult() == null)
                System.exit(0);

            result.ifPresent(name -> Configuration.setClipartDir(result.get()));
            Configuration.saveParamChanges(absoluteConfigDir);
            try {
                testDesignsDirectory(absoluteConfigDir);
                succeded = true;
            } catch (IOException e) {
                e.printStackTrace();
                succeded = false;
            }
        }
    }

    public static boolean testLicense() {
        return LicenseService.checkKey(Configuration.getCompanyName(), Configuration.getLicenseKey().toLowerCase());
    }


    public static void findLicense(String absoluteConfigDir) {
        //LicensePane licensePane = new LicensePane();


        boolean validUser = false;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(MessageResource.getText("loadproperties.find.licence.error.title"));
        alert.setHeaderText(MessageResource.getText("loadproperties.find.licence.error.header.text"));
        alert.setContentText(MessageResource.getText("loadproperties.find.licence.error.content.text"));

        alert.showAndWait();

        do {
            // Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle(MessageResource.getText("loadproperties.find.licence.dialog.title"));
            dialog.setHeaderText(MessageResource.getText("loadproperties.find.licence.dialog.header.text"));
            dialog.setWidth(600);
            dialog.setHeight(300);


            ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
            final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            btOk.addEventFilter(ActionEvent.ACTION, event -> {
                System.exit(0);
            });

// Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField username = new TextField();
            username.setPromptText(MessageResource.getText("loadproperties.find.licence.username.prompt.text"));
            username.setPrefWidth(400);
            TextField password1 = new TextField();
            password1.setPromptText(MessageResource.getText("loadproperties.find.licence.password.prompt.text"));
            password1.setPrefWidth(400);
            grid.add(new Label(MessageResource.getText("loadproperties.find.licence.username.prompt.text")), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label(MessageResource.getText("loadproperties.find.licence.password.prompt.text")), 0, 1);
            grid.add(password1, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
            Platform.runLater(() -> username.requestFocus());


            Optional<Pair<String, String>> result = dialog.showAndWait();

            if (!LicenseService.checkKey(username.getText(), password1.getText().toLowerCase())) {
                alert.showAndWait();

            } else {
                validUser = true;
                Configuration.setCompanyName(username.getText());
                Configuration.setLicenseKey(password1.getText());
                Configuration.saveParamChanges(absoluteConfigDir);
            }


        } while (!validUser);

    }

}
