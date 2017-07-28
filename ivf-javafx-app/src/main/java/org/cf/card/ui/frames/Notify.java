/**
 *
 */
package org.cf.card.ui.frames;

import org.cf.card.ui.MainApp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

/**
 * @author Nikhil Mahajan
 *
 */
public class Notify extends Alert {

	/**
	 * @param alertType
	 */
	public Notify(AlertType alertType) {
		super(alertType);
		initialize();
	}

	/**
	 * @param alertType
	 * @param contentText
	 * @param buttons
	 */
	public Notify(AlertType alertType, String contentText, ButtonType... buttons) {
		super(alertType, contentText, buttons);
		initialize();
	}

	private void initialize() {
		// Text text = new Text(contentText);
		// text.setWrappingWidth(100);

		DialogPane dialogPane = this.getDialogPane();
		dialogPane.getStylesheets().add(MainApp.class.getResource("/CSS/alertBox.css").toExternalForm());
		dialogPane.getStyleClass().add("alertBox");
		// dialogPane.setContent(text);
		this.initStyle(StageStyle.UNDECORATED);
	}

}
