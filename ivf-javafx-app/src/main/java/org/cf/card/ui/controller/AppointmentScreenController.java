package org.cf.card.ui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIAppointment;
import org.cf.card.ui.model.UIClient;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIAppointmentService;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;

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

public class AppointmentScreenController {

	@FXML
	private TableView<UIAppointment> appointmentTable;

	@FXML
	private Button nextAppointmentButton;

	@FXML
	private Label timeLabel;

	@FXML
	private Label dateLabel;

	@FXML
	private ImageView appointmentImageView;

	@FXML
	private TableColumn<UIAppointment, String> appointmentTime;

	@FXML
	private TableView<UIClient> patientsTable;

	@FXML
	private AnchorPane appointmentScreen;

	// Reference to the main application.
	private MainApp mainApp;
	private int appointmentColumns = 5;
	private int rowCount = 24;
	private DetailCell detailCell;
	private User login;
	private Map<String, List<AppointmentDto>> mapOfAppointments;
	private Map<Long, Integer> moAppointmentsForNextAction;
	private Map<Integer, AppointmentDto> moAppointmentDto;
	private int totalAppointments;
	private Integer selectedSequence;
	private Date currentDate;
	private UIAppointmentService uiAppointmentService = new UIAppointmentService();

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		appointmentTable.getSelectionModel().setCellSelectionEnabled(true);
		appointmentTable.setEditable(false);
		buildColumns(false);
	}

	/**
	 * Builds the data.
	 */
	public void buildData() {
		currentDate = new Date();
		buildColumns(true);
		buildRows();
		buildButtonRow();
	}

	/**
	 * Builds the columns for appointmentTable dynamically.
	 */
	private void buildColumns(boolean removeOldColumns) {
		appointmentTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		List<TableColumn<UIAppointment, AppointmentDto>> columns = new ArrayList<>();
		if (removeOldColumns)
			appointmentTable.getColumns().remove(1, appointmentTable.getColumns().size());
		TableColumn<UIAppointment, AppointmentDto> appointmentHeadColumn = new TableColumn<UIAppointment, AppointmentDto>(
				"Appointments");
		for (int i = 0; i < appointmentColumns; i++) {
			int coulumNumber = i + 1;
			TableColumn<UIAppointment, AppointmentDto> appointmentsColumns = new TableColumn<>(
					String.valueOf(coulumNumber));
			final int s = new Integer(i);
			appointmentsColumns.setCellValueFactory(cellData -> cellData.getValue().getAppointmentDetails().get(s));
			appointmentsColumns.setMinWidth(95);
			appointmentsColumns.setPrefWidth(95);
			Callback<TableColumn<UIAppointment, AppointmentDto>, TableCell<UIAppointment, AppointmentDto>> detailCellFactory = new Callback<TableColumn<UIAppointment, AppointmentDto>, TableCell<UIAppointment, AppointmentDto>>() {
				@Override
				public TableCell<UIAppointment, AppointmentDto> call(TableColumn<UIAppointment, AppointmentDto> param) {
					detailCell = new DetailCell();
					return detailCell;
				}
			};
			appointmentsColumns.setCellFactory(detailCellFactory);
			appointmentsColumns.setSortable(false);
			appointmentHeadColumn.getColumns().add(appointmentsColumns);

		}
		columns.add(appointmentHeadColumn);
		appointmentTable.getColumns().addAll(columns);
	}

	/**
	 * Builds the data rows from statically from 8:00 AM to 8:00 PM.
	 */
	private void buildRows() {

		totalAppointments = 0;
		moAppointmentsForNextAction = new LinkedHashMap<Long, Integer>();
		moAppointmentDto = new LinkedHashMap<Integer, AppointmentDto>();
		LocalTime startTime = LocalTime.of(8, 0);
		ObservableList<UIAppointment> uiAppointments = FXCollections.observableArrayList();
		mapOfAppointments = uiAppointmentService.getAppointmentsByDate(currentDate);
		for (int i = 0; i < rowCount; i++) {
			// getting time dynamically from 8:00 AM to 8:PM
			LocalTime nextTime = startTime.plusMinutes(30 * i);
			String formattedTime = nextTime.format(DateTimeFormatter.ofPattern(IConstants.TIME_FORMAT));
			List<AppointmentDto> uiDetails = new ArrayList<AppointmentDto>();
			List<AppointmentDto> uiDetailsWithValue = mapOfAppointments.get(formattedTime);
			// Below we are going to bind appointments on single time slot with
			// tableView
			int originalSize = 0;
			if (uiDetailsWithValue != null) {
				originalSize = uiDetailsWithValue.size();
				// If there appointment list exist in a time slot then save that
				// list in uiDetails list object.
				uiDetails = uiDetailsWithValue;
				for (int j = 1; j <= uiDetails.size(); j++) {
					// moAppointmentsForNextAction.put(totalAppointments+j,
					// uiDetails.get(j));
					totalAppointments++;
					moAppointmentsForNextAction.put(uiDetails.get(j - 1).getAppointmentId(), totalAppointments);
					moAppointmentDto.put(totalAppointments, uiDetails.get(j - 1));
				}

			}
			// This method is very important for binding data with table view.
			// We have 5 column to bind if only 2 objects are for bind then
			// other 3 will null and throw exception so we add new object for
			// binding other columns.
			for (int j = 0; j < appointmentColumns - originalSize; j++) {
				uiDetails.add(new AppointmentDto());
			}
			UIAppointment uiAppointment = new UIAppointment(formattedTime, uiDetails);
			uiAppointments.add(uiAppointment);
		}
		appointmentTable.setItems(uiAppointments);
	}

	/**
	 * This method is use for load AppointmentOverview screen. Builds the button
	 * row.
	 */
	@FXML
	private void buildButtonRow() {
		Button appointmentScreenButtonAction = new Button(MessageResource.getText("tv.appointment.button"));
		appointmentScreenButtonAction.setLayoutX(750);
		appointmentScreenButtonAction.setLayoutY(760);
		appointmentScreenButtonAction.setPrefWidth(250);
		appointmentScreenButtonAction.setPrefHeight(45);
		appointmentScreen.getChildren().add(appointmentScreenButtonAction);
		appointmentScreenButtonAction.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				appointmentOverviewAction();
			}
		});
	}

	/**
	 * Appointment overview action.
	 */
	@FXML
	private void appointmentOverviewAction() {
		LoadPanels.loadModule(mainApp, login, Module.APPOINTMENT_OVERVIEW.getKey(), null);
	}

	/**
	 * Next appointment button action.
	 */
	@FXML
	private void nextAppointmentButtonAction() {
		if (moAppointmentDto.size() > 0) {
			AppointmentDto appointmentDto = moAppointmentDto.get(selectedSequence + 1);
			if (null != appointmentDto) {
				FileUtils.playMedia("/sound/next_please.m4a");
				detailCell.buildNode(appointmentDto);
			}
		}
	}

	class DetailCell extends TableCell<UIAppointment, AppointmentDto> {

		private GridPane gridPane;
		private static final int cellHeight = 120;
		UIClipartService clipartService = new UIClipartService();

		@Override
		protected void updateItem(AppointmentDto item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null && item.getAppointmentId() != null) {
				buildNode(item);
				setGraphic(gridPane);
			} else {
				Pane pane = new Pane();
				pane.setPrefHeight(cellHeight);
				pane.setMaxHeight(cellHeight);
				setText("");
				try {
					setGraphic(pane);
				} catch (Exception e) {
				}
			}
		}

		private void buildNode(AppointmentDto item) {
			gridPane = new GridPane();
			// getting image of coupleID
			File file = new File(clipartService.getImage(item.getCoupleId()));
			ImageView imageView = new ImageView();
			Image image = null;
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
			if (null != item) {
				Integer sequence = moAppointmentsForNextAction.get(item.getAppointmentId());
				selectedSequence = sequence;
				nextAppointmentButton
						.setText(MessageResource.getText("tv.button") + "     \"" + String.valueOf(sequence) + "\"");
				appointmentImageView.setImage(image);
				final SequentialTransition transitionForward = FileUtils.createTransition(appointmentImageView, image);
				transitionForward.play();
				timeLabel.setText(item.getAppointmentTime());
				dateLabel.setText(item.getAppointmentDate());

			}
			imageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new LoadPatientDetails(image, item, imageView));
			GridPane.setMargin(imageButton, new Insets(0, 20, 0, 0));
			this.gridPane.add(imageButton, 0, 0);
			this.gridPane.setAlignment(Pos.CENTER);
			this.gridPane.setMinHeight(cellHeight);
			this.gridPane.setMaxHeight(cellHeight);
		}
	}

	class LoadPatientDetails implements EventHandler<MouseEvent> {
		Image image;
		AppointmentDto item;
		int count;
		ImageView imageView;

		public LoadPatientDetails(Image image, AppointmentDto item, ImageView imageView) {
			this.image = image;
			this.item = item;
			this.imageView = imageView;
		}

		@Override
		public void handle(MouseEvent event) {
			if (null != item) {
				FileUtils.playMedia("/sound/next_please.m4a");
				timeLabel.setText(item.getAppointmentTime());
				dateLabel.setText(item.getAppointmentDate());
				Integer sequence = moAppointmentsForNextAction.get(item.getAppointmentId());
				selectedSequence = sequence;
				nextAppointmentButton
						.setText(MessageResource.getText("tv.button") + "     \"" + String.valueOf(sequence) + "\"");
				appointmentImageView.setImage(image);
				final SequentialTransition transitionForward = FileUtils.createTransition(appointmentImageView, image);
				transitionForward.play();
			}
		}

	}

	/**
	 * Reset build node.
	 */
	/*
	 * private void resetBuildNode() { appointmentImageView.setImage(null);
	 * timeLabel.setText(MessageResource.getText("tv.time"));
	 * dateLabel.setText(MessageResource.getText("tv.date"));
	 * nextAppointmentButton.setText(MessageResource.getText("tv.button")); }
	 */

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

}