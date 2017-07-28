/**
 *
 */
package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.service.AppointmentService;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AppointmentController.
 *
 * @author Nikhil Mahajan
 */
@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController {

	/** The appointment service. */
	@Autowired
	private AppointmentService appointmentService;

	/**
	 * Adds the appointment.
	 *
	 * @param appointmentDto
	 *            the appointment
	 * @return the appointment
	 */
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody AppointmentDto addAppointment(@RequestBody AppointmentDto appointmentDto) {
		return appointmentService.saveAppointment(appointmentDto);
	}

	/**
	 * Check total scheduled appointment on date and time.
	 *
	 * @param appointment
	 *            the appointment
	 * @return the long
	 */
	@RequestMapping(value = "/totalScheduledAppointmentsOnDateAndTime", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody Long checkTotalScheduledAppointmentOnDateAndTime(@RequestBody AppointmentDto appointmentDto) {
		return appointmentService.checkTotalScheduledAppointmentOnDateAndTime(
				Util.stringToDate(appointmentDto.getAppointmentDate(), IConstants.DATE_FORMAT),
				Util.stringToDate(appointmentDto.getAppointmentTime(), IConstants.TIME_FORMAT));
	}

	/**
	 * Delete booked appointment.
	 *
	 * @param id
	 *            the id
	 */
	@RequestMapping(method = DELETE, value = "{id}")
	public @ResponseBody void deleteBookedAppointment(@PathVariable("id") Long appointmentId) {
		appointmentService.deleteBookedAppointment(appointmentId);

	}

	/**
	 * This method is use for find all appointments on a date having many
	 * appointments on single time slot. Gets the appointments by date.
	 *
	 * @param appointmentDate
	 *            the appointment date
	 * @return the appointments by date
	 */
	@RequestMapping(value = "/appointmentsMapOnDate", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<String, List<AppointmentDto>> getAppointmentsByDate(
			@RequestParam(value = "appointmentDate") String appointmentDate) {
		return appointmentService.getAppointmentsByDate(Util.stringToDate(appointmentDate, IConstants.DATE_FORMAT));

	}

}
