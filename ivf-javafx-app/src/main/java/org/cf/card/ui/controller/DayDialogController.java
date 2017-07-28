/**
 *
 */
package org.cf.card.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.cf.card.dto.EmbryoCodeDayDto;
import org.cf.card.dto.UIDayDto;
import org.cf.card.model.Codes;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIDay;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIDayProgressValueService;
import org.cf.card.ui.service.UIEmbryoCodeDayService;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.util.EnumDayTable;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumDayTable.Progress;
import org.cf.card.util.IConstants;
import org.springframework.util.StringUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Nikhil Mahajan
 *
 */
public class DayDialogController {

	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private GridPane gridPane;

	private UIDay uiDay;

	private MainApp mainApp;

	private User login;

	private Codes womanCode;

	@FXML
	private TextArea remarksTextArea = new TextArea();;

	Object[] params = null;

	@FXML
	private Button closeButton;

	@FXML
	private Button okButtion;

	private EmbryoCodeDayDto embryoCodeDayDto;

	public UIDay getUiDay() {
		return uiDay;
	}

	public void setUiDay(UIDay uiDay) {
		this.uiDay = uiDay;
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	public Codes getWomanCode() {
		return womanCode;
	}

	public void setWomanCode(Codes womanCode) {
		this.womanCode = womanCode;
	}

	class Delta {
		double x, y;
	}

	DayCell dayCell = null;
	Button ok = null;

	Map<Integer, String> dayColumnKeyValue = null;
	UIEmbryoService embryoService = new UIEmbryoService();
	UIEmbryoCodeDayService embryoCodeDayService = new UIEmbryoCodeDayService();
	UIDayProgressValueService dayProgressValueService = new UIDayProgressValueService();

	@FXML
	void initialize() {
		final Delta dragDelta = new Delta();
		gridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = mainApp.getDayDialog().getX() - mouseEvent.getScreenX();
				dragDelta.y = mainApp.getDayDialog().getY() - mouseEvent.getScreenY();
			}
		});
		gridPane.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				mainApp.getDayDialog().setX(mouseEvent.getScreenX() + dragDelta.x);
				mainApp.getDayDialog().setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});
	}

	public void buildData() {
		resetFields();
		// getting here remarks text by embryoCodeId(as row) and DayIndex(as
		// column)
		embryoCodeDayDto = embryoCodeDayService.getRemarksByEmbryoCodeIdAndDayIndex(uiDay.getEmbryoCodeId(),
				uiDay.getDayIndex());
		if (null != embryoCodeDayDto) {
			remarksTextArea.setText(embryoCodeDayDto.getRemark());
		}
		gridPane.getRowConstraints().clear();
		/* Getting day progress values to show the selected button */
		List<DayProgressValue> aoDayProgressValue = embryoService.getDayProgressValue(uiDay.getEmbryoCodeId(),
				uiDay.getDayIndex());

		Label dayTitleLabel = new Label();
		int displayDayIndex = uiDay.getDayIndex() == -1 ? 2 : uiDay.getDayIndex();
		params = new Object[1];
		params[0] = displayDayIndex;
		dayTitleLabel.setText(MessageResource.getText("common.label.day.display", params));// +
																							// displayDayIndex);

		dayColumnKeyValue = new LinkedHashMap<>();
		Map<Progress, Set<Option>> tableData = EnumDayTable.getRange(uiDay.getDayIndex());
		if(uiDay.getDayIndex()==-1){
			System.out.println("true");
			//EnumDayTable.
		}

		/* setting column minHeight, prefHeight, maxHeight */
		ColumnConstraints colC = new ColumnConstraints(10, 100, 150);
		// colC.setStyle("-fx-border: 2px solid; -fx-border-color: red;");

		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setGridLinesVisible(true);
		// gridPane.setStyle("-fx-border: 2px solid; -fx-border-color: red;");
		int col = 0;

		RowConstraints rowinfo0 = new RowConstraints(); // row zero
		rowinfo0.setPercentHeight(10);

		RowConstraints rowinfo = new RowConstraints();
		rowinfo.setPercentHeight(10);

		RowConstraints rowinfo1 = new RowConstraints();
		rowinfo1.setPercentHeight(60);

		RowConstraints rowinfo2 = new RowConstraints();
		rowinfo2.setPercentHeight(10);
		gridPane.getRowConstraints().add(rowinfo0);
		gridPane.getRowConstraints().add(rowinfo);// 2*50 percent
		gridPane.getRowConstraints().add(rowinfo1);
		gridPane.getRowConstraints().add(rowinfo2);
		HBox box = new HBox();
		// box.setStyle("-fx-border: 2px solid; -fx-border-color: green;");

		box.setAlignment(Pos.CENTER_RIGHT);
		box.setStyle("-fx-background-color:#06aad9;");
		box.setPrefHeight(35);
		box.setMaxHeight(46);
		closeButton = new Button();
		closeButton.setAlignment(Pos.CENTER_RIGHT);
		closeButton.setPrefHeight(50);
		closeButton.setPrefWidth(40);
		closeButton.setId("exit");
		closeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleCloseButton(null);

			}
		});
		int count11 = tableData.entrySet().size();
		dayTitleLabel.setPrefHeight(46);
		dayTitleLabel.setPrefWidth(colC.getMaxWidth() * count11);
		gridPane.add(dayTitleLabel, 0, 0, tableData.entrySet().size() - 1, 1);
		box.getChildren().add(closeButton);
		gridPane.add(box, tableData.entrySet().size() - 1, 0, 1, 1);

		// check if the destiny ET is already set for any other embryo
		// key : destiny_day, value : day on which destiny is done
		// key : embryoCodeId, value : embryoCodeId
		boolean isETDone = false;
		List<Map<String, Object>> dayProgressValueListMap = dayProgressValueService
				.findDayIndexByCodeIdAndDestiny(womanCode.getId(), Option.ET.getKey());
		if (!dayProgressValueListMap.isEmpty()) {
			for (Map<String, Object> map : dayProgressValueListMap) {
				if (map.containsKey("destiny_day")) {
					if (!map.get("destiny_day").equals(uiDay.getDayIndex())) {
						isETDone = true;
					}
				}
			}
		}

		for (Map.Entry<Progress, Set<Option>> entry : tableData.entrySet()) {
			gridPane.getColumnConstraints().add(colC);
			Progress key = entry.getKey();
			Set<Option> range = entry.getValue();
			Label heading = new Label(key.getName());

			heading.setPrefHeight(46);
			heading.setPrefWidth(colC.getMaxWidth());
			gridPane.add(heading, col, 1);
			Pane pane = null;
			if (key.getLayout().equals("flow")) {
				pane = new FlowPane();
				pane.setStyle("-fx-background-color:WHITE;-fx-alignment: TOP_CENTER;");
			} else {
				pane = new VBox();
				pane.setStyle("-fx-background-color:WHITE;-fx-alignment: TOP_CENTER;");
			}
			ToggleGroup tg = new ToggleGroup();
			tg.setUserData(col);

			for (Option option : range) {
				ToggleButton tb = new ToggleButton(option.getName());

				tb.setUserData(option.getKey());
				tb.setId(key.getKey() + "");
				tb.getStyleClass().add(".toggle-button:selected:focused");
				/* display selected button if already save in data base */
				for (DayProgressValue dayProgressValue : aoDayProgressValue) {

					if (dayProgressValue.getDayOptionId().equals(option.getKey())
							&& dayProgressValue.getDayColumnName().equals(col + "")) {
						tb.setSelected(true);
						// tb.setUserData(dayProgressValue.getDayOptionId());
						dayColumnKeyValue.put(col, dayProgressValue.getDayOptionId().toString());
						// index++;
					}
				}

				// If ET destiny is already selected for other embryo then ET
				// option will not be show on day dialog
				if (isETDone) {
					if (option.getKey() != Option.ET.getKey()) {
						tb.setToggleGroup(tg);
						pane.getChildren().add(tb);
					}
				} else {
					tb.setToggleGroup(tg);
					pane.getChildren().add(tb);
				}
			}

			tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if (tg.getSelectedToggle() != null) {
						int key = Integer.valueOf(tg.getUserData().toString());
						dayColumnKeyValue.put(key, tg.getSelectedToggle().getUserData().toString());
					} else {
						int key = Integer.valueOf(tg.getUserData().toString());
						dayColumnKeyValue.remove(key);
					}
				}
			});
			gridPane.add(pane, col, 2);
			col++;
		}
		Label heading1 = new Label(MessageResource.getText("common.label.remarks"));
		@SuppressWarnings("unused")
		int count = tableData.entrySet().size() - 1;
		// heading1.setPrefWidth(colC.getMaxWidth() * count);
		heading1.setPrefWidth(100);
		heading1.setPrefHeight(46);
		gridPane.add(heading1, 0, 3, tableData.entrySet().size() - 1, 1);

		// Reamarks TextField Area Dymanically Created here.
		remarksTextArea.setId("remarks");
		remarksTextArea.setWrapText(true);
		remarksTextArea.setMaxWidth(600);
		remarksTextArea.setPrefSize(200, 100);
		// gridPane.add(remarksTextArea, 1, 3);
		gridPane.add(remarksTextArea, 1, 3, 2, 1);
		// gridPane.add(child, columnIndex, rowIndex, colspan, rowspan);

		Button clear = new Button(MessageResource.getText("daydialog.button.clear"));
		clear.setPrefSize(colC.getPrefWidth(), 29);
		gridPane.add(clear, 0, 4);
		/*
		 * Label label = new Label("fucsfcuasfcuasyfcau");
		 * label.setPrefWidth(colC.getMaxWidth() * count);
		 * label.setPrefHeight(30); embryoCodeDayDto =
		 * embryoCodeDayService.getRemarksByEmbryoCodeIdAndDayIndex(
		 * embryoCodeId, displayDayIndex); if (null != embryoCodeDayDto)
		 * remarksTextArea.setText(embryoCodeDayDto.getRemark());
		 *
		 * label.setStyle("-fx-background-color: #3498db;");
		 */
		// gridPane.add(label, 1, 4, tableData.entrySet().size() - 2, 1);
		ok = new Button(MessageResource.getText("common.button.ok"));
		ok.setPrefSize(100, 29);
		GridPane.setMargin(ok, new Insets(0, 0, 0, 0));

		// ok.setPrefSize(colC.getPrefWidth(), 29);
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				okAction(event);

			}
		});
		// gridPane.add(heading1, 0, 3, tableData.entrySet().size() - 1, 1);

		gridPane.add(ok, 2, 4, 1, 1);
		// gridPane.add(ok, tableData.entrySet().size() - 1, 4);

	}

	/**
	 * Saving selected pop up data
	 */
	public void okAction(ActionEvent event) {

		UIDayDto uiDayDto = new UIDayDto();
		uiDayDto.setDayIndex(uiDay.getDayIndex());
		uiDayDto.setEmbryoCodeId(uiDay.getEmbryoCodeId());
		uiDayDto.setDayDate(uiDay.getDayDate());
		uiDayDto.setModuleId(uiDay.getModuleId());
		Map<Integer, String> treeMap = new TreeMap<Integer, String>(dayColumnKeyValue);
		uiDayDto.setDayColumnKeyValue(treeMap);
		if (dayColumnKeyValue.size() > 0) {
			embryoService.addDayProgressValues(uiDayDto);
			LoadPanels.loadModule(mainApp, login, uiDay.getModuleId(), event);
			/*if (mainApp.getPrimaryStage().getWidth() != Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
				MainPageController mController = LoadPanels.loadMainPageHalfScreen(mainApp, login);
				// mController.setLogin(login);
				mController.setMainApp(mainApp);
				mController.embryologyAction(null);
				mController.embryoThawingAction(null);
			} else {
				MainPageController mController = LoadPanels.loadMainPageFullScreen(mainApp, login);
				// mController.setLogin(login);
				mController.setMainApp(mainApp);
				mController.embryologyAction(null);
			}*/

			// save or update EmbryoCodeDayRemarks object in database
			if (!StringUtils.isEmpty(remarksTextArea.getText())) {
				if (embryoCodeDayDto == null) {
					embryoCodeDayDto = new EmbryoCodeDayDto();
				}
				embryoCodeDayDto.setEmbryoCodeId(uiDay.getEmbryoCodeId());
				embryoCodeDayDto.setDayIndex(uiDay.getDayIndex());
				embryoCodeDayDto.setRemark(remarksTextArea.getText());
				embryoCodeDayDto = embryoCodeDayService.saveRemark(embryoCodeDayDto);
			}
			Stage stage = (Stage) ok.getScene().getWindow();
			stage.close();
		} else {
			Notify notify = new Notify(AlertType.WARNING, MessageResource.getText("daydialog.warning.message"));
			notify.showAndWait();
		}
	}

	public void handleCloseButton(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}

	// Reset All TextField and TextArea
	private void resetFields() {
		remarksTextArea.setText(IConstants.emptyString);
	}
}
