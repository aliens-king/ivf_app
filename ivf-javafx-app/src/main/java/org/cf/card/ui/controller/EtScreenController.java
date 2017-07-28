package org.cf.card.ui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class EtScreenController {

	@FXML
	private TableView<UIEtTable> etTableView;

	@FXML
	private TableColumn<UIEtTable, String> timeColumn;

	private Map<String, Map<String, EtTableDto>> dayProgressValMap;

	@FXML
	private Label timeLabel;

	@FXML
	private Label dateLabel;

	private String curruntSelectedTime;
	private Date firstDate = new Date();

	private int dateColumns = 2;

	@FXML
	private ImageView etImageView;

	@FXML
	private Button nextAppointmentButton;

	@FXML
	private AnchorPane etScreenAnchorPane;

	@FXML
	private Button etTableButton;

	private DataCell dataCell;

	private int rowCount = 24;

	private UIDayProgressValueService uiDayProgressValueService = new UIDayProgressValueService();

	UICoupleService coupleService = new UICoupleService();

	// Reference to the main application.
	private MainApp mainApp;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

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

		etTableView.getSelectionModel().setCellSelectionEnabled(true);
		etTableView.setEditable(false);
		buildColumns(false);
	}

	// Builds the date columns dynamically
	public void buildColumns(boolean removeOldColumns) {
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		List<TableColumn<UIEtTable, EtTableDto>> dateColumnList = new ArrayList<>();
		if (removeOldColumns)
			etTableView.getColumns().remove(1, etTableView.getColumns().size());
		String dateNext = "";
		Date nextDate = firstDate;
		dateLabel.setStyle("-fx-font-size: 18pt;");
		timeLabel.setStyle("-fx-font-size: 18pt;");
		dateNext = Util.formatDate(IConstants.DATE_FORMAT, nextDate);
		TableColumn<UIEtTable, EtTableDto> dateColumn = new TableColumn<>(dateNext);

		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getEtDetails().get(0));
		dateColumn.setPrefWidth(100);
		dateColumn.setPrefWidth(200);
		Callback<TableColumn<UIEtTable, EtTableDto>, TableCell<UIEtTable, EtTableDto>> dataCellFactory = new Callback<TableColumn<UIEtTable, EtTableDto>, TableCell<UIEtTable, EtTableDto>>() {
			@Override
			public TableCell<UIEtTable, EtTableDto> call(TableColumn<UIEtTable, EtTableDto> param) {
				dataCell = new DataCell();
				return dataCell;
			}
		};
		dateColumn.setId(dateNext);
		dateColumn.setCellFactory(dataCellFactory);
		dateColumn.setSortable(false);
		dateColumnList.add(dateColumn);
		// adding dynamic dates to ET Table
		etTableView.getColumns().addAll(dateColumnList);
	}

	@SuppressWarnings("rawtypes")
	@FXML
	private void nextETAction() {
		Iterator it = dayProgressValMap.entrySet().iterator();
		Map.Entry entry = null;
		String key = null;
		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			key = (String) entry.getKey();
			if (null != curruntSelectedTime && curruntSelectedTime.equals(key)) {
				if (it.hasNext()) {
					FileUtils.playMedia("/sound/Next please.m4a");
					entry = (Map.Entry) it.next();
					key = (String) entry.getKey();
					curruntSelectedTime = key;
					Map<String, EtTableDto> appointmentMap = dayProgressValMap.get(key);
					for (String m : appointmentMap.keySet()) {
						EtTableDto etTableDto = appointmentMap.get(m);
						dataCell.buildGrid(etTableDto);
					}
					break;
				}
			}
		}
	}

	// Builds the data rows for time 8:00 A.M to 8:00 P.M

	public void buildData() {
		LocalTime startTime = LocalTime.of(8, 0);
		ObservableList<UIEtTable> uiEtTableList = FXCollections.observableArrayList();
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		// getting time dynamically from 8:00 AM to 8:PM
		String from = Util.formatDate(IConstants.DATE_FORMAT, firstDate);
		Date lastDate = Util.nextDate(firstDate, dateColumns);
		String to = Util.formatDate(IConstants.DATE_FORMAT, lastDate);
		// Gets the day progress value Map by day date range and destiny
		dayProgressValMap = uiDayProgressValueService.getDayProgressValueByDayDateAndDestinyMap(from, to,
				Option.ET.getKey());
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
		etTableView.setItems(uiEtTableList);
		buildEtTableButton();
	}

	@FXML
	private void nextPage() {
		nextButton.setDisable(true);
		previousButton.setDisable(true);
		this.firstDate = Util.nextDate(firstDate, dateColumns);
		paginate();
		previousButton.setDisable(false);
		nextButton.setDisable(false);
	}

	@FXML
	private void previousPage() {
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

	private void buildEtTableButton() {
		etTableButton = new Button(MessageResource.getText("et_table.button"));
		etTableButton.setLayoutX(820);
		etTableButton.setLayoutY(780);
		etTableButton.setPrefWidth(250);
		etTableButton.setPrefHeight(45);
		etScreenAnchorPane.getChildren().add(etTableButton);
		etTableButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				EtTableAction();
			}
		});
	}

	private void EtTableAction() {
		LoadPanels.loadModule(mainApp, login, Module.ET_TABLE.getKey(), null);
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

		@SuppressWarnings("rawtypes")
		private int valueIndex() {
			Iterator it = dayProgressValMap.entrySet().iterator();
			Map.Entry entry = null;
			String key = null;
			int count = 0;
			while (it.hasNext()) {
				entry = (Map.Entry) it.next();
				key = (String) entry.getKey();
				count = count + 1;
				if (null != curruntSelectedTime && curruntSelectedTime.equals(key)) {
					break;
				}
			}
			return count;
		}

		private GridPane buildGrid(EtTableDto item) {
			int count = 0;
			GridPane grid = new GridPane();
			// grid.setStyle("-fx-border: 2px solid; -fx-border-color: red;");
			/*
			 * ColumnConstraints column1 = new ColumnConstraints(110);
			 * ColumnConstraints column2 = new ColumnConstraints(50, 100, 100);
			 * grid.getColumnConstraints().addAll(column1, column2);
			 * 
			 * StringBuilder patientOtherName = new StringBuilder();
			 * patientOtherName.append(" " +
			 * item.getMiddleName().toLowerCase()); patientOtherName.append(" "
			 * + item.getFirstName().toLowerCase());
			 * 
			 * Label surnameLabel = new Label();
			 * surnameLabel.setText(item.getSurName().toUpperCase().toString());
			 * surnameLabel.setWrapText(true);
			 * surnameLabel.setFont(Font.font("Open Sans Bold", FontWeight.BOLD,
			 * 15));
			 * 
			 * Label patientName = new Label(patientOtherName.toString());
			 * patientName.setWrapText(true);
			 * patientName.setFont(Font.font("Open Sans", FontWeight.NORMAL,
			 * 15));
			 */

			File file = new File(clipartService.getImage(item.getCoupleId()));
			ImageView imageView = new ImageView();

			Image image = null;
			try {
				image = new Image(file.toURI().toURL().toString());
				imageView.setFitHeight(100);
				imageView.setFitWidth(120);
				imageView.setImage(image);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			Button imageButton = new Button();
			imageButton.setGraphic(imageView);
			imageButton.setPadding(new Insets(0, 0, 0, 0));
			imageButton.setStyle("-fx-border-color: #2980b9;");
			if (null != item) {
				count = valueIndex();
				nextAppointmentButton
						.setText(MessageResource.getText("tv.button") + "     \"" + String.valueOf(count) + "\"");
				etImageView.setImage(image);
				final SequentialTransition transitionForward = FileUtils.createTransition(etImageView, image);
				transitionForward.play();
				dateLabel.setText(item.getEtDate());
				timeLabel.setText(item.getEtTime());
			}
			imageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new LoadEtDetails(image, item));

			/*
			 * Object[] params = new Object[1]; Label day = null; if
			 * (item.getDayIndex() == -1) { params[0] = item.getDayIndex(); day
			 * = new
			 * Label(MessageResource.getText("etTable.label.from.thawing")); }
			 * else { params[0] = item.getDayIndex(); day = new
			 * Label(MessageResource.getText("etTable.label.message", params));
			 * }
			 * 
			 * // Label day = new //
			 * Label(MessageResource.getText("etTable.label.message", //
			 * params));// +" // " // + // item.getDayIndex() // + // "\n" // +
			 * // " // "+MessageResource.getText("common.text.et"));
			 * day.setWrapText(true); day.setFont(Font.font("Open Sans",
			 * FontWeight.SEMI_BOLD, 16)); day.setAlignment(Pos.TOP_RIGHT);
			 * day.setStyle("-fx-border-color: #2980b9;");
			 */

			GridPane.setMargin(imageButton, new Insets(0, 20, 0, 0));
			// GridPane.setValignment(imageButton, VPos.TOP);
			grid.add(imageButton, 0, 0);
			/*
			 * GridPane.setValignment(surnameLabel, VPos.BOTTOM);
			 * GridPane.setHalignment(surnameLabel, HPos.CENTER);
			 * grid.add(surnameLabel, 0, 1, 2, 1);
			 * GridPane.setValignment(patientName, VPos.TOP);
			 * GridPane.setHalignment(patientName, HPos.CENTER);
			 * GridPane.setMargin(patientName, new Insets(-7, 0, 0, 0));
			 * grid.add(patientName, 0, 2, 2, 1); GridPane.setValignment(day,
			 * VPos.BOTTOM); GridPane.setMargin(day, new Insets(0, 0, 0, 0));
			 * grid.add(day, 1, 0, 1, 1); grid.setHgap(10); grid.setVgap(6);
			 */
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

	class LoadEtDetails implements EventHandler<MouseEvent> {
		Image image;
		EtTableDto item;
		int count;

		public LoadEtDetails(Image image, EtTableDto item) {
			this.image = image;
			this.item = item;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void handle(MouseEvent event) {
			if (null != item) {
				FileUtils.playMedia("/sound/Next please.m4a");
				curruntSelectedTime = item.getEtTime();
				// String timeAndDate = item.getEtTime() + " / " +
				// item.getEtDate();
				dateLabel.setText(item.getEtDate());
				timeLabel.setText(item.getEtTime());
				// dateAndTime.setText(timeAndDate);
				count = dataCell.valueIndex();
				nextAppointmentButton
						.setText(MessageResource.getText("tv.button") + "     \"" + String.valueOf(count) + "\"");
				etImageView.setImage(image);
				final SequentialTransition transitionForward = FileUtils.createTransition(etImageView, image);
				transitionForward.play();
			}
		}

	}
}
