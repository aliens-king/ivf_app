/**
 *
 */
package org.cf.card.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.model.Appointment;

/**
 * The Interface AppointmentService.
 *
 * @author Nikhil Mahajan
 */
public interface AppointmentService extends BaseService<Appointment> {

	/**
	 * Save appointment.
	 *
	 * @param appointmentDto
	 *            the appointment dto
	 * @return the appointment dto
	 */
	public AppointmentDto saveAppointment(AppointmentDto appointmentDto);

	
	/**
	 * 
	 * @param appointmentDate
	 * @param appointmentTime
	 * 
	 */
	public Long checkTotalScheduledAppointmentOnDateAndTime(Date appointmentDate, Date appointmentTime);
	
	
	
	/**
	 * Delete booked appointment.
	 *
	 * @param appointmentId the appointment id
	 */
	public void deleteBookedAppointment(Long appointmentId);
	
	
	/**
	 * Gets the appointments by date.
	 *
	 * @param dateOfAppointment
	 *            the date of appointment
	 * @return the appointments by date
	 */
	public Map<String, List<AppointmentDto>> getAppointmentsByDate(Date dateOfAppointment);

	
	/**
	 * Find appointment dtos by date.
	 *
	 * @param appointmentDate
	 *            the appointment date
	 * @return the list
	 */
	public List<AppointmentDto> findAppointmentDtosByDate(Date appointmentDate);

}
