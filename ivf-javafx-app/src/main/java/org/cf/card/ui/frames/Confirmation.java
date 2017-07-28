package org.cf.card.ui.frames;

import org.cf.card.ui.MainApp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class Confirmation extends Alert{

	public Confirmation(AlertType alertType) {
		super(alertType);
		initialize();
	}
	
	public Confirmation(AlertType alertType, String contentText, ButtonType[] buttons) {
		super(alertType, contentText, buttons);
		initialize();
	}
	private void initialize(){
		DialogPane dialogPane = this.getDialogPane();
		dialogPane.getStylesheets().add(
				MainApp.class.getResource("/CSS/alertBox.css").toExternalForm());
		dialogPane.getStyleClass().add("alertBox");
		this.initStyle(StageStyle.UNDECORATED);
	}

}
