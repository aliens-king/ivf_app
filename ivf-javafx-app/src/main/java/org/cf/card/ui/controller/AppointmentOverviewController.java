package org.cf.card.ui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIAppointment;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIAppointmentService;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumAppointment.AppointmentStatus;
import org.cf.card.util.EnumAppointment.AppointmentType;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class AppointmentOverviewController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.appointment");
	private static final String titleDescription = MessageResource.getText("mainpage.title.appointment.description");
	private static final String iconURL = "/icons/appointment.png";

	// binding fxml element
	@FXML
	private AnchorPane appointmentOverview;
	@FXML
	private TableView<UIAppointment> appointmentTable;
	@FXML
	private TableColumn<UIAppointment, String> appointmentTime;
	@FXML
	private Button previousButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button appointmentScreenButtonAction;
	@FXML
	private DatePicker appointmentDatePicker;
	@FXML
	private Label appointmentShowDate;

	// Variables
	private MainApp mainApp;
	private User login;
	private int nextDate = 1;
	private int previousDate = -1;
	private int appointmentColumns = 5;
	private int rowCount = 24;
	private Date currentDate = new Date();
	private int appointmentSrNo;
	private PrintTemplate<AppointmentDto> printTemplate = new PrintTemplate<>();
	/* maintaining the session data for and CompanyDetail */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	@SuppressWarnings("unused")
	private CompanyDto companyDto;

	private UICoupleService coupleService = new UICoupleService();
	private UIAppointmentService uiAppointmentService = new UIAppointmentService();

	@FXML
	public void initialize() {
		appointmentTable.getSelectionModel().setCellSelectionEnabled(true);
		appointmentTable.setEditable(false);
		buildColumns(false);
		buildAppointmentScreenButton();
	}

	@SuppressWarnings("unchecked")
	public void buildData() {
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		// firstDate = new Date();
		// We are recreate current date while load screen again.
		currentDate = new Date();
		buildColumns(true);
		buildRows();
		buildAppointmentScreenButton();
	}

	/**
	 * Builds the columns for appointmentTable dynamically. Table show single
	 * date data having times from 08:00 AM to 07:30PM and each time can have 5
	 * appointments.
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
			TableColumn<UIAppointment, AppointmentDto> appointmentColumn = new TableColumn<>(
					String.valueOf(coulumNumber));
			final int s = new Integer(i);
			appointmentColumn.setCellValueFactory(cellData -> cellData.getValue().getAppointmentDetails().get(s));
			Callback<TableColumn<UIAppointment, AppointmentDto>, TableCell<UIAppointment, AppointmentDto>> detailCellFactory = new Callback<TableColumn<UIAppointment, AppointmentDto>, TableCell<UIAppointment, AppointmentDto>>() {
				@Override
				public TableCell<UIAppointment, AppointmentDto> call(TableColumn<UIAppointment, AppointmentDto> param) {
					return new DetailCell();
				}
			};
			appointmentColumn.setCellFactory(detailCellFactory);
			appointmentColumn.setSortable(false);
			appointmentHeadColumn.getColumns().add(appointmentColumn);
		}
		columns.add(appointmentHeadColumn);
		appointmentTable.getColumns().addAll(columns);
	}

	/**
	 * Builds the data rows from statically from 8:00 AM to 7:30 PM.
	 */
	private void buildRows() {

		appointmentShowDate.setText("DATE : " + Util.formatDate(IConstants.DATE_FORMAT, currentDate));
		appointmentShowDate.setStyle("-fx-font-size: 18px;");
		LocalTime startTime = LocalTime.of(8, 0);
		ObservableList<UIAppointment> uiAppointments = FXCollections.observableArrayList();
		Map<String, List<AppointmentDto>> mapOfAppointments = uiAppointmentService.getAppointmentsByDate(currentDate);
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
	 * Builds the appointment screen button.
	 */
	private void buildAppointmentScreenButton() {
		appointmentScreenButtonAction = new Button("Appointment Screen");
		appointmentScreenButtonAction.setLayoutX(642);
		appointmentScreenButtonAction.setLayoutY(762);
		appointmentOverview.getChildren().add(appointmentScreenButtonAction);
		appointmentScreenButtonAction.setOnAction(this::appointmentScreenAction);
	}

	/**
	 * Appointment screen action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	@FXML
	private void appointmentScreenAction(final ActionEvent actionEvent) {
		LoadPanels.loadModule(mainApp, login, Module.APPOINTMENT_SCREEN.getKey(), null);
	}

	@FXML
	private void nextDateAction() {
		nextButton.setDisable(true);
		previousButton.setDisable(true);
		this.currentDate = Util.nextDate(currentDate, nextDate);
		paginate();
		previousButton.setDisable(false);
		nextButton.setDisable(false);

	}

	@FXML
	private void previousDateAction() {
		previousButton.setDisable(true);
		nextButton.setDisable(true);
		this.currentDate = Util.nextDate(currentDate, previousDate);
		paginate();
		nextButton.setDisable(false);
		previousButton.setDisable(false);

	}

	private void paginate() {
		buildColumns(true);
		buildRows();
	}

	class DetailCell extends TableCell<UIAppointment, AppointmentDto> {

		private static final int cellHeight = 120;
		UIClipartService clipartService = new UIClipartService();
		int count = 0;

		@Override
		protected void updateItem(AppointmentDto item, boolean empty) {

			super.updateItem(item, empty);
			if (item != null && item.getAppointmentId() != null) {
				AnchorPane pane = buildNode(item);
				setGraphic(pane);
			} else {
				Pane pane = new Pane();
				pane.setPrefHeight(cellHeight);
				pane.setMaxHeight(cellHeight);
				setText("");
				try {
					setGraphic(pane);
				} catch (Exception e) {
				}
				@SuppressWarnings("unused")
				int x = ++count;
			}
		}

		private AnchorPane buildNode(AppointmentDto item) {
			FXMLLoader loader = new FXMLLoader();
			AnchorPane parenPane = null;
			try {
				loader.setResources(MessageResource.getResourceBundle());
				loader.setLocation(MainApp.class.getResource("/view/popups/AppointmentDetails.fxml"));
				parenPane = (AnchorPane) loader.load();
				parenPane.setStyle("-fx-border-color: #DCDCDC;");
				GridPane childGridPane = (GridPane) parenPane.lookup("#appointmentDetailGridPane");

				final ToggleGroup appointmentStatusGroup = new ToggleGroup();
				RadioButton arrivedRadioButton = (RadioButton) childGridPane.lookup("#arrivedRadioButton");
				arrivedRadioButton.setToggleGroup(appointmentStatusGroup);
				RadioButton cancelRadioButton = (RadioButton) childGridPane.lookup("#cancelRadioButton");
				cancelRadioButton.setToggleGroup(appointmentStatusGroup);
				RadioButton seenRadioButton = (RadioButton) childGridPane.lookup("#seenRadioButton");
				seenRadioButton.setToggleGroup(appointmentStatusGroup);
				appointmentStatusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					@Override
					public void changed(ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newvalue) {
						RadioButton chk = (RadioButton) newvalue.getToggleGroup().getSelectedToggle();
						String selectedValue = chk.getText();
						if (AppointmentStatus.ARRIVE.getVal().equals(selectedValue)) {
							item.setAppointmentStatus(AppointmentStatus.ARRIVE.getKey());
						} else if (AppointmentStatus.CANCEL.getVal().equals(selectedValue)) {
							item.setAppointmentStatus(AppointmentStatus.CANCEL.getKey());
						} else {
							item.setAppointmentStatus(AppointmentStatus.SEEN.getKey());
						}
						uiAppointmentService.save(item);
					}
				});
				ImageView coupleImageView = (ImageView) childGridPane.lookup("#coupleImage");
				coupleImageView.setStyle("-fx-border-color: #2980b9;");
				Label drInitialsLabel = (Label) childGridPane.lookup("#drInitials");
				drInitialsLabel.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 12));
				Label deleteAppointmentLabel = (Label) childGridPane.lookup("#deleteAppointment");
				Label surnameLabel = (Label) childGridPane.lookup("#surname");
				surnameLabel.setFont(Font.font("Open Sans Bold", FontWeight.BOLD, 13));
				Label firstAndMiddleNameLabel = (Label) childGridPane.lookup("#firstAndMiddleName");
				firstAndMiddleNameLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 13));
				Label appointmentTypeLabel = (Label) childGridPane.lookup("#appointmentType");
				appointmentTypeLabel.setFont(Font.font("Open Sans Semibold", FontWeight.SEMI_BOLD, 13));

				int appointmentStatus = item.getAppointmentStatus();
				if (appointmentStatus > 0) {
					if (appointmentStatus == AppointmentStatus.ARRIVE.getKey()) {
						arrivedRadioButton.setSelected(true);
					} else if (appointmentStatus == AppointmentStatus.CANCEL.getKey()) {
						cancelRadioButton.setSelected(true);
					} else {
						seenRadioButton.setSelected(true);
					}
				}
				StringBuilder patientOtherName = new StringBuilder();
				patientOtherName.append(item.getMiddleName().toLowerCase());
				patientOtherName.append(" " + item.getFirstName().toLowerCase());
				surnameLabel.setText(item.getSurname().toUpperCase().toString());
				firstAndMiddleNameLabel.setText(patientOtherName.toString());
				deleteAppointmentLabel.setStyle("-fx-text-fill:red");
				deleteAppointmentLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new DeleteAppointment());
				if (AppointmentType.Others.getKey() == item.getAppointmentType()) {
					appointmentTypeLabel.setText(item.getAppointmentTypeDesc());
				} else {
					appointmentTypeLabel.setText(AppointmentType.getEnumByKey(item.getAppointmentType()).getVal());
				}
				coupleImageView.setImage(getCoupleImage(item));
				coupleImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new PatientDetails());
				drInitialsLabel.setText("Dr. " + item.getDoctorInitials());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return parenPane;
		}
	}

	class DeleteAppointment implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

			if (EnumPermission.canWrite(login.getRoleId(), Module.SCHEDULE_APPOINTMENT.getKey())) {

				if (event.getClickCount() >= 1) {
					if (event.getTarget() instanceof Text) {
						DetailCell cell = null;
						Text cross = (Text) event.getTarget();
						cell = (DetailCell) cross.getParent().getParent().getParent().getParent();
						AppointmentDto detail = cell.getItem();
						if (detail != null) {
							Long appointmentId = detail.getAppointmentId();
							Notify notify = new Notify(AlertType.CONFIRMATION);
							ButtonType buttonTypeYes = new ButtonType(MessageResource.getText("common.button.yes"));
							ButtonType buttonTypeCancel = new ButtonType(
									MessageResource.getText("common.button.cancel"));
							notify.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
							notify.setContentText(MessageResource.getText("appointment.info.message.want.to.delete"));
							Optional<ButtonType> result = notify.showAndWait();
							if (result.get() == buttonTypeYes) {
								uiAppointmentService.deleteBookedAppointment(appointmentId);
								Notify notifydelete = new Notify(AlertType.INFORMATION);
								notifydelete.setContentText(
										MessageResource.getText("appointment.info.message.appointment.cancel"));
								notifydelete.showAndWait();
								buildData();
							}
						}
					}
				}
			} else
				FileUtils.privillegeEditError();
		}

	}

	class PatientDetails implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

			// Button clipart = (Button) event.getTarget();
			ImageView clipart = (ImageView) event.getTarget();
			// get button from event
			// get parent of component till we get the cell object so as to
			// fetch clicked couple id
			DetailCell cell = (DetailCell) clipart.getParent().getParent().getParent();
			AppointmentDto detail = cell.getItem();
			if (detail != null) {
				Long coupleId = detail.getCoupleId();
				Couple couple = coupleService.getCoupleById(coupleId);
				LoadPanels.patientFileDialog(couple, mainApp, login);
			}
		}
	}

	private Image getCoupleImage(AppointmentDto item) {
		UIClipartService clipartService = new UIClipartService();
		File file = new File(clipartService.getImage(item.getCoupleId()));
		Image image = null;
		try {
			image = new Image(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Prints the appointment action.
	 */
	@FXML
	private void printAppointmentAction() {

		LocalDate appointmentDate = appointmentDatePicker.getValue();
		if (null != appointmentDate) {
			Date selectedDate = Util.getDateFromLocalDate(appointmentDate);
			Map<String, List<AppointmentDto>> mapOfAppointmentsPrint = uiAppointmentService
					.getAppointmentsByDate(selectedDate);
			if (null != mapOfAppointmentsPrint && mapOfAppointmentsPrint.size() > 0) {
				Printer printer = Printer.getDefaultPrinter();
				PrinterJob printerJob = PrinterJob.createPrinterJob();
				PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
						Printer.MarginType.DEFAULT);
				JobSettings jobSettings = printerJob.getJobSettings();
				jobSettings.setPageLayout(pageLayout);
				List<Node> appointmentTable = createAppointmentPrintTable(mapOfAppointmentsPrint, pageLayout);
				int page = 1;
				if (appointmentTable.size() > 0 && appointmentTable != null) {
					if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
						for (Node tableVBox : appointmentTable) {
							BorderPane printPage = createPrintPage(tableVBox, page, pageLayout);
							printerJob.printPage(printPage);
							page++;
						}
						printerJob.endJob();
					}
				}

			} else {
				Notify notifydelete = new Notify(AlertType.INFORMATION);
				notifydelete.setContentText(MessageResource.getText("appointment.info.no.reult.found"));
				notifydelete.showAndWait();

			}
		} else {
			Notify notifydelete = new Notify(AlertType.INFORMATION);
			notifydelete.setContentText(MessageResource.getText("patientfile.warning.select.date"));
			notifydelete.showAndWait();
		}

	}

	/**
	 * Creates the appointment print table.
	 *
	 * @param listOfAppointments
	 *            the list of appointments
	 * @param pageLayout
	 *            the page layout
	 * @return the v box
	 */
	private List<Node> createAppointmentPrintTable(Map<String, List<AppointmentDto>> mapOfAppointmentsPrint,
			PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		double rowHeight = 30;
		double srNoWidth = 27;
		double timeAndTagWidth = 45;
		double patientNameProcedureAndRemarksWidth = 90;
		double doctorNameWidth = 60;
		LocalTime startTime = LocalTime.of(8, 0);
		appointmentSrNo = 0;
		for (int i = 0; i < rowCount; i++) {
			// getting time dynamically from 8:00 AM to 8:PM
			LocalTime nextTime = startTime.plusMinutes(30 * i);
			String formattedTime = nextTime.format(DateTimeFormatter.ofPattern(IConstants.TIME_FORMAT));
			List<AppointmentDto> appointmentsList = mapOfAppointmentsPrint.get(formattedTime);
			if (appointmentsList != null && appointmentsList.size() > 0) {
				for (AppointmentDto appointmentDto : appointmentsList) {
					HBox row = createTableRow(appointmentDto, rowHeight, srNoWidth, timeAndTagWidth,
							patientNameProcedureAndRemarksWidth, doctorNameWidth, pageLayout, ++appointmentSrNo);
					if (rowHeight + totalHeight > pageLayout.getPrintableHeight() - 80) {
						tableBox = new VBox();
						tableBox.setPrefWidth(pageLayout.getPrintableWidth());
						tableBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
						HBox tableHeader = createTableHeader(srNoWidth, timeAndTagWidth,
								patientNameProcedureAndRemarksWidth, doctorNameWidth, pageLayout);
						tableBox.getChildren().add(tableHeader);
						nodes.add(tableBox);
						totalHeight = 0;
					}
					if (tableBox != null && row.getChildren().size() > 0) {
						tableBox.getChildren().add(row);
						totalHeight += rowHeight;
					}
				}
			}
		}
		return nodes;
	}

	/**
	 * Creates the table header.
	 *
	 * @param columnsOfTable
	 *            the columns of table
	 * @param timeAndTagWidth
	 *            the time and tag width
	 * @param patientNameProcedureAndRemarksWidth
	 *            the name and procedure width
	 * @param pageLayout
	 *            the page layout
	 * @return the h box
	 */
	private HBox createTableHeader(double srNoWidth, double timeAndTagWidth, double patientNameProcedureAndRemarksWidth,
			double doctorNameWidth, PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		double rowHeight = 20;
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox,
				MessageResource.getText("common.label.sr.no"), srNoWidth, rowHeight, true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox, MessageResource.getText("tv.time"),
				timeAndTagWidth, rowHeight, true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox,
				MessageResource.getText("patient.patientname"), patientNameProcedureAndRemarksWidth, rowHeight, true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox, "MVID-TAG", timeAndTagWidth, rowHeight,
				true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox,
				MessageResource.getText("embryology.tablecolumn.procedure"), patientNameProcedureAndRemarksWidth,
				rowHeight, true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(headerHBox,
				MessageResource.getText("enumpermission.role.doctor"), doctorNameWidth, rowHeight, true);
		printTemplate.addTableRowToHBoxOverallScreen(headerHBox, "Written Remarks ",
				patientNameProcedureAndRemarksWidth, rowHeight, true);
		return headerHBox;

	}

	/**
	 * Creates the table row.
	 *
	 * @param appointment
	 *            the appointment
	 * @param timeAndTagWidth
	 *            the time and tag width
	 * @param nameAndProcedureWidth
	 *            the name and procedure width
	 * @param pageLayout
	 *            the page layout
	 * @return the h box
	 */
	private HBox createTableRow(AppointmentDto appointment, double rowHeight, double srNoWidth, double timeAndTagWidth,
			double nameAndProcedureWidth, double doctorNameWidth, PageLayout pageLayout, int srNO) {
		HBox rowHBox = new HBox();
		double imageWidth = 20;
		double imageHeight = 20;

		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.setSnapToPixel(true);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, " " + String.valueOf(srNO), srNoWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, appointment.getAppointmentTime(),
				timeAndTagWidth, rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox,
				appointment.getFirstName() + " " + appointment.getSurname(), nameAndProcedureWidth, rowHeight, false);
		printTemplate.addImageViewToPrintingTableCell(rowHBox, appointment.getCoupleId(), timeAndTagWidth, imageWidth,
				imageHeight);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox,
				AppointmentType.getEnumByKey(appointment.getAppointmentType()).getVal(), nameAndProcedureWidth,
				rowHeight, false);
		printTemplate.addTableRowToHBoxWithSeparatorOverallScreen(rowHBox, appointment.getDoctorInitials(),
				doctorNameWidth, rowHeight, false);
		printTemplate.addTableRowToHBoxOverallScreen(rowHBox, IConstants.emptyString, nameAndProcedureWidth, rowHeight,
				false);
		return rowHBox;
	}

	// Complete BorderPage for printing
	private BorderPane createPrintPage(Node appointmentTable, int page, PageLayout pageLayout) {

		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());

		// Setting the content at top of Border Pane
		HBox headingHBox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headingHBox);

		// Setting the content at center of Border Pane
		VBox contentVerticalBox = new VBox();
		contentVerticalBox.getChildren().addAll(appointmentTable);
		contentVerticalBox.getChildren().add(printTemplate.createSpaceBetweenElements(10));
		root.setCenter(contentVerticalBox);

		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;

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

}