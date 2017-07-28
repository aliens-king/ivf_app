package org.cf.card.ui.controller;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.model.Codes;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.UIAppointmentService;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.util.EnumAppointment.AppointmentType;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppointmentPopupController {

	@FXML
	private DatePicker appoinmentDatePicker;
	@FXML
	private ComboBox<String> appointmentComboBoxTime;
	@FXML
	private ComboBox<AppointmentType> appointmentComboBoxType;
	@FXML
	private TextField doctorInitialTextField;
	@FXML
	private javafx.scene.control.Button closeButton;
	@FXML
	private TextField otherTypeTextField;
	@FXML
	private Label otherTypeLabel;

	private int appointmentTypeOtherKey = AppointmentType.Others.getKey();

	private boolean appointmentOtherTypeSelected;

	private Codes latestCodeId;

	public Codes getLatestCodeId() {
		return latestCodeId;
	}

	public void setLatestCodeId(Codes latestCodeId) {
		this.latestCodeId = latestCodeId;
	}

	private UIEmbryoService uiEmbryoService = new UIEmbryoService();
	private UIAppointmentService uiAppointmentService = new UIAppointmentService();

	@FXML
	public void initialize() {
		appointmentComboBoxType.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (null != newValue) {
						if (appointmentTypeOtherKey == newValue.getKey()) {
							appointmentOtherTypeSelected = true;
							appointmentOtherTypeShow();
						} else {
							appointmentOtherTypeSelected = false;
							appointmentOtherTypeHide();
						}
					}
				});
		buildData();
	}

	public void buildData() {
		resetFields();
		appointmentOtherTypeHide();
		ObservableList<String> dataTime = uiEmbryoService.appointmentTime();
		appointmentComboBoxTime.setItems(dataTime);
		appointmentComboBoxTime.getSelectionModel().select(0);

		// Get AppointmentType enum values
		ObservableList<AppointmentType> data = FXCollections.observableArrayList();
		data.addAll(AppointmentType.values());
		appointmentComboBoxType.setItems(data);
		appointmentComboBoxType.getSelectionModel().select(0);
	}

	/* Save Action of Appointment Popup page */
	@FXML
	public void saveAction(ActionEvent actionEvent) throws ParseException {

		// This condition is use for check Is date and doctor initials selected
		// for save or not.
		if (appoinmentDatePicker.getValue() == null || doctorInitialTextField.getText().isEmpty()) {
			Notify notify = new Notify(AlertType.INFORMATION,
					MessageResource.getText("appointmentpopup.error.message"));
			notify.showAndWait();
		} else {
			AppointmentDto appointmentDto = new AppointmentDto();
			Date appointmentSelectedDate = null;
			// Selected time from drop-down
			String time = appointmentComboBoxTime.getValue().toString();
			// Get here time to Date Object
			Date timeToDate = Util.stringToDate(time, IConstants.TIME_FORMAT);

			// Current Date
			Date currentDate = new Date();
			String currentDateAsString = Util.formatDate(IConstants.DATE_FORMAT, currentDate);
			Date currentDateWithFormat = Util.stringToDate(currentDateAsString, IConstants.DATE_FORMAT);

			if (null != latestCodeId) {
				// Selected Date
				appointmentSelectedDate = java.util.Date
						.from(appoinmentDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				String appointmentDateString = Util.formatDate(IConstants.DATE_FORMAT, appointmentSelectedDate);
				Date appointmentDateWithFormat = Util.stringToDate(appointmentDateString, IConstants.DATE_FORMAT);

				// This condition is use for check Is selected date is before
				// current date or not.
				if (appointmentDateWithFormat.before(currentDateWithFormat)) {
					Notify notify = new Notify(AlertType.INFORMATION,
							MessageResource.getText("appointmentpopup.error.message.previousdate"));
					notify.showAndWait();
				} else {

					if (appointmentOtherTypeSelected == true && otherTypeTextField.getText().isEmpty()) {
						Notify notify = new Notify(AlertType.INFORMATION);
						notify.setContentText(MessageResource.getText("appointment.type.other.desc"));
						notify.showAndWait();
					} else {
						appointmentDto.setCodeId(latestCodeId.getId());
						String appointmentDateAsString = Util.formatDate(IConstants.DATE_FORMAT,
								appointmentSelectedDate);
						appointmentDto.setAppointmentDate(appointmentDateAsString);
						appointmentDto.setAppointmentType(
								appointmentComboBoxType.getSelectionModel().getSelectedItem().getKey());
						String appointmentTimeAsString = Util.formatDate(IConstants.TIME_FORMAT, timeToDate);
						appointmentDto.setAppointmentTime(appointmentTimeAsString);
						appointmentDto.setDoctorInitials(doctorInitialTextField.getText());
						if (appointmentOtherTypeSelected)
							appointmentDto.setAppointmentTypeDesc(otherTypeTextField.getText());
						int totalScheduledAppointments = 0;
						// Checking scheduled appointments on single date and
						// time.
						totalScheduledAppointments = uiAppointmentService
								.checkTotalScheduledAppointmentOnDateAndTime(new AppointmentDto(appointmentTimeAsString,
										appointmentDateAsString));
						if (totalScheduledAppointments < 5) {
							uiAppointmentService.save(appointmentDto);
							Stage stage = (Stage) closeButton.getScene().getWindow();
							stage.close();
						} else {
							Notify notify = new Notify(AlertType.INFORMATION,
									MessageResource.getText("appointmentpopup.info.message"));
							notify.showAndWait();
							Stage stage = (Stage) closeButton.getScene().getWindow();
							stage.close();
						}
					}

				}
			} else {
				Notify notify = new Notify(AlertType.INFORMATION);
				notify.setContentText(MessageResource.getText("patientfile.controller.error.message"));
				notify.showAndWait();
			}
		}

	}

	@FXML
	public void clearAction(ActionEvent actionEvent) {
		appoinmentDatePicker.setValue(null);
		appointmentComboBoxTime.getSelectionModel().select(0);
		appointmentComboBoxType.getSelectionModel().select(0);
	}

	private void resetFields() {
		doctorInitialTextField.setText(IConstants.emptyString);
	}

	@FXML
	public void handleCloseButton(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	private void appointmentOtherTypeShow() {
		otherTypeLabel.setVisible(true);
		otherTypeTextField.setVisible(true);
	}

	private void appointmentOtherTypeHide() {
		otherTypeLabel.setVisible(false);
		otherTypeTextField.setVisible(false);
	}

}