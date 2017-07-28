/**
 *
 */
package org.cf.card.ui.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.model.Couple;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Nikhil Mahajan
 *
 */
public class UIAppointmentService extends UIBaseService {

	private static final String APPOINTMENT_URL = BASE_URL + "/appointment";

	/**
	 * Save appointment object for scheduling appointment on particular Date and
	 * Time.
	 *
	 * @param appointment
	 *            the appointment
	 * @return the appointment
	 */
	public AppointmentDto save(AppointmentDto appointment) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AppointmentDto> entity = new HttpEntity<AppointmentDto>(appointment, headers);

		ResponseEntity<AppointmentDto> exchange = restTemplate.exchange(APPOINTMENT_URL + "/save", HttpMethod.POST,
				entity, new ParameterizedTypeReference<AppointmentDto>() {
				});
		return exchange.getBody();
	}

	/**
	 * Check total scheduled appointment on date and time.
	 *
	 * @param appointmentDto
	 *            the appointment dto
	 * @return the int
	 */
	public int checkTotalScheduledAppointmentOnDateAndTime(AppointmentDto appointmentDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AppointmentDto> entity = new HttpEntity<AppointmentDto>(appointmentDto, headers);
		String url = APPOINTMENT_URL + "/totalScheduledAppointmentsOnDateAndTime";
		int totalScheduledAppointments = 0;
		ResponseEntity<Long> exchange = restTemplate.exchange(url, HttpMethod.POST, entity,
				new ParameterizedTypeReference<Long>() {
				});
		if (null != exchange) {
			return totalScheduledAppointments = exchange.getBody().intValue();
		}
		return totalScheduledAppointments;
	}

	/**
	 * Delete booked appointment.
	 *
	 * @param appointmentId
	 *            the appointment id
	 */
	public void deleteBookedAppointment(Long appointmentId) {
		restTemplate.exchange(Configuration.getServerUrl() + "/appointment/{id}", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Couple>() {
				}, appointmentId);
	}

	/**
	 * Gets the appointments by date.
	 *
	 * @param appointmentDate
	 *            the appointment date
	 * @return the appointments by date
	 */
	public Map<String, List<AppointmentDto>> getAppointmentsByDate(Date appointmentDate) {
		String url = APPOINTMENT_URL + "/appointmentsMapOnDate?appointmentDate={appointmentDate}";
		ResponseEntity<Map<String, List<AppointmentDto>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<String, List<AppointmentDto>>>() {
				}, Util.formatDate(IConstants.DATE_FORMAT, appointmentDate));
		return exchange.getBody();

	}

}
