package org.cf.card.ui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.EtTableDto;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIEtTable;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UIDayProgressValueService;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

public class EtTableController {

	@FXML
	private AnchorPane etTableOverview;

	@FXML
	private TableView<UIEtTable> etTable;

	@FXML
	private TableColumn<UIEtTable, String> etTimeColumn;

	private Date firstDate = new Date();

	private int dateColumns = 7;

	private int rowCount = 24;

	private UIDayProgressValueService uiDayProgressValueService = new UIDayProgressValueService();

	UICoupleService coupleService = new UICoupleService();

	// Reference to the main application.
	private MainApp mainApp;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

	@FXML
	private Button etScreenButton;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private User login;

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	@FXML
	public void initialize() {

		etTable.getSelectionModel().setCellSelectionEnabled(true);
		etTable.setEditable(false);
		buildColumns(false);
	}

	// Builds the date columns dynamically
	public void buildColumns(boolean removeOldColumns) {
		etTimeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		List<TableColumn<UIEtTable, EtTableDto>> dateColumnList = new ArrayList<>();

		if (removeOldColumns)
			etTable.getColumns().remove(1, etTable.getColumns().size());

		String dateNext = "";
		// ObservableList<TableColumn<UIEmbryo, ?>> etTableList =
		// etTableView.getColumns();
		for (int i = 0; i < dateColumns; i++) {
			Date nextDate = Util.nextDate(firstDate, i);
			dateNext = Util.formatDate(IConstants.DATE_FORMAT, nextDate);

			TableColumn<UIEtTable, EtTableDto> dateColumn = new TableColumn<>(dateNext);
			final int s = new Integer(i);
			dateColumn.setCellValueFactory(cellData -> cellData.getValue().getEtDetails().get(s));

			Callback<TableColumn<UIEtTable, EtTableDto>, TableCell<UIEtTable, EtTableDto>> dataCellFactory = new Callback<TableColumn<UIEtTable, EtTableDto>, TableCell<UIEtTable, EtTableDto>>() {

				@Override
				public TableCell<UIEtTable, EtTableDto> call(TableColumn<UIEtTable, EtTableDto> param) {
					DataCell dataCell = new DataCell();
					return dataCell;
				}
			};
			dateColumn.setId(dateNext);
			dateColumn.setCellFactory(dataCellFactory);
			dateColumn.setSortable(false);
			// dateColumn.setId("Date "+i);
			// dateColumn.setEditable(false);
			/*
			 * dateColumn.setMinWidth(150); dateColumn.setPrefWidth(160);
			 */
			dateColumnList.add(dateColumn);
		}
		// adding dynamic dates to ET Table
		etTable.getColumns().addAll(dateColumnList);
	}

	// Builds the data rows for time 8:00 A.M to 8:00 P.M

	public void buildData() {
		LocalTime startTime = LocalTime.of(8, 0);
		ObservableList<UIEtTable> uiEtTableList = FXCollections.observableArrayList();
		etTimeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		// getting time dynamically from 8:00 AM to 8:PM
		String from = Util.formatDate(IConstants.DATE_FORMAT, firstDate);
		Date lastDate = Util.nextDate(firstDate, dateColumns);
		String to = Util.formatDate(IConstants.DATE_FORMAT, lastDate);
		// Gets the day progress value Map by day date range and destiny
		Map<String, Map<String, EtTableDto>> dayProgressValMap = uiDayProgressValueService
				.getDayProgressValueByDayDateAndDestinyMap(from, to, Option.ET.getKey());
		if (dayProgressValMap != null) {
			for (int i = 0; i <= rowCount; i++) {
				LocalTime nextTime = startTime.plusMinutes(30 * i);
				String formattedTime = nextTime.format(DateTimeFormatter.ofPattern(IConstants.TIME_FORMAT));
				Map<String, EtTableDto> innerMap = dayProgressValMap.get(formattedTime);
				List<EtTableDto> EtTableDtoList = new ArrayList<>();
				for (int j = 0; j < dateColumns; j++) {
					Date nextDate = Util.nextDate(firstDate, j);
					String dateKey = Util.formatDate(IConstants.DATE_FORMAT, nextDate);
					EtTableDto etDto = null;
					if (innerMap != null && innerMap.containsKey(dateKey)) {
						etDto = innerMap.get(dateKey);
					}
					EtTableDtoList.add(etDto);
				}
				UIEtTable uiet = new UIEtTable(formattedTime, EtTableDtoList);
				uiEtTableList.add(uiet);
			}
		}
		etTable.setItems(uiEtTableList);

		buildEtScreenButton();
	}

	private void buildEtScreenButton() {
		etScreenButton = new Button(MessageResource.getText("ettable.screen.button"));
		etScreenButton.setLayoutX(642);
		etScreenButton.setLayoutY(762);
		etTableOverview.getChildren().add(etScreenButton);
		etScreenButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				etScreenAction();
			}
		});
	}

	private void etScreenAction() {
		LoadPanels.loadModule(mainApp, login, Module.ET_SCREEN.getKey(), null);
	}

	@FXML
	private void nextPageAction() {
		nextButton.setDisable(true);
		previousButton.setDisable(true);
		this.firstDate = Util.nextDate(firstDate, dateColumns);
		paginate();
		previousButton.setDisable(false);
		nextButton.setDisable(false);
	}

	@FXML
	private void previousPageAction() {
		previousButton.setDisable(true);
		nextButton.setDisable(true);
		this.firstDate = Util.nextDate(firstDate, -dateColumns);
		paginate();
		previousButton.setDisable(false);
		nextButton.setDisable(false);
	}

	private void paginate() {
		buildColumns(true);
		buildData();
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	class DataCell extends TableCell<UIEtTable, EtTableDto> {
		// private GridPane grid;
		private static final int cellHeight = 125;
		UIClipartService clipartService = new UIClipartService();

		@Override
		protected void updateItem(EtTableDto item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				GridPane grid = buildGrid(item);
				setGraphic(grid);
			} else {
				Pane pane = new Pane();
				pane.setPrefHeight(cellHeight);
				pane.setMaxHeight(cellHeight);
				setText("");
				setGraphic(pane);
			}
		}

		private GridPane buildGrid(EtTableDto item) {
			GridPane grid = new GridPane();
			// grid.setStyle("-fx-border: 2px solid; -fx-border-color: red;");
			ColumnConstraints column1 = new ColumnConstraints(110);
			ColumnConstraints column2 = new ColumnConstraints(50, 100, 100);
			grid.getColumnConstraints().addAll(column1, column2);

			StringBuilder patientOtherName = new StringBuilder();
			patientOtherName.append(" " + item.getMiddleName().toLowerCase());
			patientOtherName.append(" " + item.getFirstName().toLowerCase());

			Label surnameLabel = new Label();
			surnameLabel.setText(item.getSurName().toUpperCase().toString());
			surnameLabel.setWrapText(true);
			surnameLabel.setFont(Font.font("Open Sans Bold", FontWeight.BOLD, 15));

			Label patientName = new Label(patientOtherName.toString());
			patientName.setWrapText(true);
			patientName.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));

			File file = new File(clipartService.getImage(item.getCoupleId()));
			ImageView imageView = new ImageView();

			Image image;
			try {
				image = new Image(file.toURI().toURL().toString());
				imageView.setFitHeight(70);
				imageView.setFitWidth(70);
				imageView.setImage(image);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			Button imageButton = new Button();
			imageButton.setGraphic(imageView);
			imageButton.setPadding(new Insets(0, 0, 0, 0));
			imageButton.setStyle("-fx-border-color: #2980b9;");

			imageButton.addEventHandler(ActionEvent.ACTION, new PatientDetails());
			Object[] params = new Object[1];
			params[0] = item.getDayIndex();
			Label day= null;
			
			if (item.getDayIndex() == -1) {
				params[0] = item.getDayIndex();
				day = new Label(MessageResource.getText("etTable.label.from.thawing"));
			} else {
				params[0] = item.getDayIndex();
				day = new Label(MessageResource.getText("etTable.label.message", params));
			}
			//Label day = new Label(MessageResource.getText("etTable.label.message", params));// +"
																							// "
																							// +
																							// item.getDayIndex()
																							// +
																							// "\n"
																							// +
																							// "
																							// "+MessageResource.getText("common.text.et"));
			day.setWrapText(true);
			day.setFont(Font.font("Open Sans", FontWeight.SEMI_BOLD, 16));
			day.setAlignment(Pos.TOP_RIGHT);
			day.setStyle("-fx-border-color: #2980b9;");

			GridPane.setMargin(imageButton, new Insets(0, 0, 0, 0));
			GridPane.setValignment(imageButton, VPos.TOP);
			grid.add(imageButton, 0, 0);
			GridPane.setValignment(surnameLabel, VPos.BOTTOM);
			GridPane.setHalignment(surnameLabel, HPos.CENTER);
			grid.add(surnameLabel, 0, 1, 2, 1);
			GridPane.setValignment(patientName, VPos.TOP);
			GridPane.setHalignment(patientName, HPos.CENTER);
			GridPane.setMargin(patientName, new Insets(-7, 0, 0, 0));
			grid.add(patientName, 0, 2, 2, 1);
			GridPane.setValignment(day, VPos.BOTTOM);
			GridPane.setMargin(day, new Insets(0, 0, 0, 0));
			grid.add(day, 1, 0, 1, 1);
			grid.setHgap(10);
			grid.setVgap(6);
			grid.setAlignment(Pos.CENTER);
			grid.setPrefHeight(cellHeight);
			grid.setMinHeight(cellHeight);
			grid.setMaxHeight(cellHeight);

			return grid;
		}
	}

	class PatientDetails implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button button = (Button) event.getTarget(); // get button from event
			// get parent of component till we get the cell object so as to
			// fecth clicked couple id
			DataCell cell = (DataCell) button.getParent().getParent();

			EtTableDto detail = cell.getItem();

			if (detail != null) {
				Long coupleId = detail.getCoupleId();
				Couple couple = coupleService.getCoupleById(coupleId);
				LoadPanels.patientFileDialog(couple, mainApp, login);
			}
		}
	}

}
