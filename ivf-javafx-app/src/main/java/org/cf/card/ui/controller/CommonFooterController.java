package org.cf.card.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CommonFooterController {

	@FXML
	private AnchorPane anchorPane;

	// @FXML
	// private Label currentDateLabel;

	private Boolean backgroundColor;

	@FXML
	private void initialize() {
		/*
		 * if(backgroundColor){ System.out.println(
		 * "backgroundColor **************************** "
		 * +getBackgroundColor());
		 * anchorPane.setStyle("-fx-background-color:gray"); }
		 */
		// DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Date date = new Date();
		// String todayDate = dateFormat.format(date);
		// currentDateLabel.setText("TODAY'S DATE: "+todayDate);
	}

	public Boolean getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Boolean backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
