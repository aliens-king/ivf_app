package org.mvid.test;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainTest extends Application{
	static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/*try {
			FXMLLoader loader = new FXMLLoader(MainTest.class.getResource("/view/LatestTableView.fxml"));
			ScrollPane page = (ScrollPane) loader.load();
			TableViewController controller = loader.getController();
			controller.buidData();
			Scene scene = new Scene(page);
			
			//scene.getStylesheets().add(GridPaneTest.class.getResource("dayDialog.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			this.primaryStage =primaryStage;
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		this.primaryStage =primaryStage;
		call();
		
		
		
	}
	
	public static void call(){
		try {
			FXMLLoader loader = new FXMLLoader(MainTest.class.getResource("GridFinal.fxml"));
			GridPane page = (GridPane) loader.load();
			CryoEmbroController controller = loader.getController();
			controller.buildData();
			Scene scene = new Scene(page);
			
			//scene.getStylesheets().add(GridPaneTest.class.getResource("dayDialog.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
