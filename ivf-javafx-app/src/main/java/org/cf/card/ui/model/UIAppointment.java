/**
 *
 */
package org.cf.card.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.dto.AppointmentDto;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class is equlivaen to one row on appointmentOverview Screen
 *
 * @author Nikhil Mahajan
 *
 */
public class UIAppointment {

	private StringProperty time;

	private List<ObjectProperty<AppointmentDto>> appointmentDetails;

	public UIAppointment() {
	}

	public UIAppointment(String time, List<AppointmentDto> appointmentDetails) {
		super();
		this.time = new SimpleStringProperty(time);
		this.appointmentDetails = new ArrayList<ObjectProperty<AppointmentDto>>();
		for (AppointmentDto uiAppointmentDetail : appointmentDetails) {
			this.appointmentDetails.add(new SimpleObjectProperty<AppointmentDto>(uiAppointmentDetail));
		}
	}

	public final StringProperty timeProperty() {
		return this.time;
	}

	public final java.lang.String getTime() {
		return this.timeProperty().get();
	}

	public final void setTime(final java.lang.String time) {
		this.timeProperty().set(time);
	}

	public List<ObjectProperty<AppointmentDto>> getAppointmentDetails() {
		return appointmentDetails;
	}

	public void setAppointmentDetails(List<ObjectProperty<AppointmentDto>> appointmentDetails) {
		this.appointmentDetails = appointmentDetails;
	}

}
