package org.cf.card.ui.controller;

import org.cf.card.ui.MainApp;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Created by Dell on 5/4/2015.
 */
public class LoginSuccessfulController {

	@FXML
	private Text welcomeText;

	@SuppressWarnings("unused")
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void initialize() {
		welcomeText.setText("");
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = new Text(welcomeText);
	}
}
