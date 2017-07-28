package org.cf.card.ui.controller;

import java.io.IOException;
import java.util.Date;

import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UICodesService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.Util;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * Created by Dell on 5/3/2015.
 */
public class DirectoryPageController {

	private UICodesService codesService = new UICodesService();
	// private UIClientService clientService = new UIClientService();
	private UICoupleService coupleService = new UICoupleService();

	@FXML
	private Text loginText;
	@FXML
	private BorderPane directoryPane;
	@FXML
	private Label currentDate;

	private MainApp mainApp;
	private User login;
	private int recentModuleKey = 5;

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
		loginText.setText(login.getSurname());

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	class Delta {
		double x, y;
	}

	@FXML
	public void initialize() {
		// Call Util class formatDate method go get required format.
		currentDate.setText(Util.formatDate("dd:MM:yyyy", new Date()));
		final Delta dragDelta = new Delta();
		directoryPane.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = mainApp.getPrimaryStage().getX() - mouseEvent.getScreenX();
				dragDelta.y = mainApp.getPrimaryStage().getY() - mouseEvent.getScreenY();
			}
		});
		directoryPane.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				mainApp.getPrimaryStage().setX(mouseEvent.getScreenX() + dragDelta.x);
				mainApp.getPrimaryStage().setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});

	}

	public int getRecentModuleKey() {
		return recentModuleKey;
	}

	public void setRecentModuleKey(int recentModuleKey) {
		this.recentModuleKey = recentModuleKey;
	}

	public void recentPageAction(ActionEvent actionEvent) {
		LoadPanels.loadModule(mainApp, login, recentModuleKey, actionEvent);
	}

	public void stockManagementAction(javafx.event.ActionEvent actionEvent) {
		comingSoon();
	}

	public void mismatchAction(javafx.event.ActionEvent actionEvent) throws IOException {
		comingSoon();
	}

	public void searchViaScannningAction(javafx.event.ActionEvent actionEvent) throws IOException {
		comingSoon();
	}

	public void standardInvestigationActionTemporary(javafx.event.ActionEvent actionEvent) throws IOException {
		comingSoon();
	}

	private void comingSoon() {
		Notify notify = new Notify(AlertType.INFORMATION, MessageResource.getText("common.notify.info.message"));
		notify.showAndWait();
	}

	@FXML
	private void buttonAction(Event event) {
		Button button = (Button) event.getTarget();
		Module module = (Module) button.getUserData();
		if (treatmentExists(module.getKey())) {
			LoadPanels.loadModule(mainApp, login, module.getKey(), event);
		}

	}

	@SuppressWarnings("unchecked")
	private boolean treatmentExists(int moduleKey) {

		boolean retVal = false;
		if (!FileUtils.isOpenWithoutTreatmentStart(moduleKey)) {
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
			Long coupleId = sessionObject.getComponent(Constants.COUPLE_SEESION_KEY);
			Couple couple = coupleService.getCoupleById(coupleId);
			Long womanCodeId = sessionObject.getComponent(Constants.WOMANCODE_SESSION_KEY);
			Long manCodeId = sessionObject.getComponent(Constants.MANCODE_SESSION_KEY);

			if (couple != null) {

				Codes womanCode = codesService.getCodeById(womanCodeId);
				Codes manCode = codesService.getCodeById(manCodeId);
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
