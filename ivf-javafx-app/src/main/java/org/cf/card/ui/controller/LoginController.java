package org.cf.card.ui.controller;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cf.card.dto.CompanyDto;
import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.service.UIComapnyService;
import org.cf.card.ui.service.UILoginService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Created by Dell on 5/2/2015.
 */
public class LoginController {

	@FXML
	private BorderPane loginBorderPane;
	@FXML
	private Button enterButton;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private AnchorPane centerAnchorPane;
	@FXML
	private javafx.scene.text.Text welcomeText;

	private UILoginService loginService = new UILoginService();

	@FXML
	private Label companyNameLabel;
	@FXML
	private ImageView companyLogoImageView;

	UIComapnyService comapnyService = new UIComapnyService();
	UIClipartService clipartService = new UIClipartService();
	UIClientService clientService = new UIClientService();
	private CompanyDto companyDto = null;

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	class Delta {
		double x, y;
	}

	@FXML
	public void initialize() {
		buildData();
		final Delta dragDelta = new Delta();
		loginBorderPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = mainApp.getPrimaryStage().getX() - mouseEvent.getScreenX();
				dragDelta.y = mainApp.getPrimaryStage().getY() - mouseEvent.getScreenY();
			}
		});
		loginBorderPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				mainApp.getPrimaryStage().setX(mouseEvent.getScreenX() + dragDelta.x);
				mainApp.getPrimaryStage().setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});

	}

	@SuppressWarnings("unchecked")
	public void enterButtonAction(ActionEvent actionEvent) {
		companyLogoImageView.setVisible(false);
		companyNameLabel.setVisible(false);
		Object[] params = new Object[1];

		// if (loginService.checkLogin(passwordTextField)) {
		User user = loginService.authenticate(passwordTextField);
		if (user != null) {
			SessionObject<String, User> sessionObjects = Session.getInstance().getSessionObject();
			sessionObjects.setComponent(Constants.USER_SEESION_KEY, user);
			setSessionData();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/LoginSuccessful.fxml"));
			loader.setResources(MessageResource.getResourceBundle());
			AnchorPane loginSuccesgulAnchorPane = null;
			try {
				loginSuccesgulAnchorPane = (AnchorPane) loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			loginSuccesgulAnchorPane.setPrefWidth(centerAnchorPane.getPrefWidth());
			loginSuccesgulAnchorPane.setPrefHeight(centerAnchorPane.getPrefHeight());
			params[0] = user.getSurname();
			// ((javafx.scene.text.Text)
			// loginSuccesgulAnchorPane.getChildren().get(0)).setText("Welcome,
			// " + '\n' + loginService.login.getSurname());
			((javafx.scene.text.Text) loginSuccesgulAnchorPane.getChildren().get(0))
					.setText(MessageResource.getText("login.text.welcome.display", params));// +",
																							// "
																							// +
																							// '\n'
																							// +
																							// user.getSurname());
			centerAnchorPane.getChildren().add(loginSuccesgulAnchorPane);
			loginSuccesgulAnchorPane.toFront();

			Task<Void> sleeper = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					return null;
				}
			};
			sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize()
							.getWidth()) {
						LoadPanels.loadDirectoryPageHalfScreen(mainApp, user);
						loginBorderPane.setOpacity(0);
					} else {
						LoadPanels.loadDirectoryPageFullScreen(mainApp, user);
						loginBorderPane.setOpacity(0);
					}
				}
			});
			new Thread(sleeper).start();

		} else
			passwordTextField.setStyle("-fx-background-color: RED");
	}

	private void setSessionData(){
		@SuppressWarnings("unchecked")
		SessionObject<String,Long> sessionObject = Session.getInstance().getSessionObject();
		List<Client> clients = clientService.getClients();
		if (clients != null && clients.size() > 0) {
			Client client = clients.get(clients.size() - 1);
			Couple couple = client.getCouple();
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

	public void passwordTextField(ActionEvent actionEvent) {
		enterButtonAction(null);

	}

	public void buildData() {
		companyDto = comapnyService.getCompanyDetails(1l);
		Image image = null;

		// Image image = new Image();
		if (null != companyDto) {
			companyNameLabel.setText(companyDto.getCompanyFullName());
			try {
				companyLogoImageView.setFitWidth(200);
				companyLogoImageView.setFitHeight(200);
				companyLogoImageView.setPreserveRatio(true);
				String logoDestPath = comapnyService.getImage(1l);
				image = new Image(new File(logoDestPath).toURI().toString());
				companyLogoImageView.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}