package org.session.test;

import java.io.IOException;

import org.cf.card.ui.controller.DayDialogController;
import org.cf.card.ui.model.UIDay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Main class to start the application.
 *
 * @author Marco Jakob
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Event Handling");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/view/DayDialog.fxml"));
		int dayIndex = 0;
		try {
			// AnchorPane dayDialog = (AnchorPane) loader.load();
			GridPane dayDialog = (GridPane) loader.load();
			/*
			 * if(dayIndex == 1 || dayIndex == 2 || dayIndex == 3){ double width
			 * = 120 * 4; //anchorPane.setPrefWidth(width);
			 * //anchorPane.setMinWidth(width); //anchorPane.setMaxWidth(width);
			 * dayDialog.setPrefWidth(width); dayDialog.setMaxWidth(width);
			 * dayDialog.setMinWidth(width); } else{ double width = 111 * 7;
			 * //anchorPane.setPrefWidth(width);
			 * //anchorPane.setMinWidth(width); //anchorPane.setMaxWidth(width);
			 * dayDialog.setPrefWidth(width); dayDialog.setMaxWidth(width);
			 * dayDialog.setMinWidth(width); }
			 */
			if (dayIndex > 0 && dayIndex <= 3) {
				double width = 120 * 4;
				// anchorPane.setPrefWidth(width);
				// anchorPane.setMinWidth(width);
				// anchorPane.setMaxWidth(width);
				dayDialog.setPrefWidth(width);
				dayDialog.setMaxWidth(width);
				dayDialog.setMinWidth(width);
			} else if (dayIndex > 3) {
				double width = 111 * 7;
				dayDialog.setPrefWidth(width);
				dayDialog.setMaxWidth(width);
				dayDialog.setMinWidth(width);
			}

			Group group = new Group(dayDialog);
			// group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
			// /1100.0);
			// group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
			// /1100.0);

			// group.setLayoutX(dayDialog.getPrefWidth() * ((1 -
			// group.getScaleX()) / 2 + 0.02));
			// group.setLayoutY(dayDialog.getPrefHeight() * ((1 -
			// group.getScaleY()) / 2 + 0.01));
			Scene scene = new Scene(group);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(Main.class.getResource("/CSS/dayDialog.css").toExternalForm());
			// Rectangle2D primaryScreenBounds =
			// Screen.getPrimary().getVisualBounds();
			// primaryStage.setX(0);
			// primaryStage.setY(0);

			primaryStage.show();
			UIDay day = new UIDay(dayIndex, 1, "", 1l, null, 13);
			DayDialogController controller = loader.getController();
			// controller.setMainApp(mainApp);
			// controller.setLogin(login);
			controller.setUiDay(day);
			controller.buildData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
