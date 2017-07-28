/**
 *
 */
package org.cf.card.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.model.Appointment;
import org.cf.card.model.Codes;
import org.cf.card.persistence.AppointmentDao;
import org.cf.card.service.AppointmentService;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class AppointmentServiceImpl.
 *
 * @author Nikhil Mahajan
 */

@Service("appointmentService")
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment> implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;

	private List<AppointmentDto> listOfAppointmentsOnSameTime = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.AppointmentService#saveAppointment(org.cf.card.dto.
	 * AppointmentDto)
	 */
	@Override
	public AppointmentDto saveAppointment(AppointmentDto appointmentDto) {
		if (null != appointmentDto) {
			Appointment appointment = createAppointmentObj(appointmentDto);
			Appointment appointmentFronDB = appointmentDao.save(appointment);
			appointmentDto = createAppointmentDto(appointmentFronDB);
		}
		return appointmentDto;

	}

	/**
	 * Check total scheduled appointment on date and time.
	 *
	 * @param appointmentDate
	 *            the appointment date
	 * @param appointmentTime
	 *            the appointment time
	 * @return the long
	 */
	@Override
	public Long checkTotalScheduledAppointmentOnDateAndTime(Date appointmentDate, Date appointmentTime) {
		return appointmentDao.findByAppointmentDateAndAppointmentTime(appointmentDate, appointmentTime);
	}

	@Override
	public void deleteBookedAppointment(Long appointmentId) {
		appointmentDao.delete(appointmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.AppointmentService#findAppointmentDtosByDate(java.
	 * util.Date)
	 */
	@Override
	public List<AppointmentDto> findAppointmentDtosByDate(Date appointmentDate) {
		List<AppointmentDto> listOfAppointments = appointmentDao.findByAppointmentDate(appointmentDate);
		return listOfAppointments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.AppointmentService#getAppointmentsByDate(java.util.
	 * Date)
	 */
	@Override
	public Map<String, List<AppointmentDto>> getAppointmentsByDate(Date dateOfAppointment) {
		List<AppointmentDto> listOfAppointments = findAppointmentDtosByDate(dateOfAppointment);
		Map<String, List<AppointmentDto>> mapOfAppointments = new LinkedHashMap<String, List<AppointmentDto>>();
		if (null != listOfAppointments && listOfAppointments.size() > 0)
			getAppointmentMap(listOfAppointments, mapOfAppointments);
		return mapOfAppointments;
	}

	/**
	 * Gets the appointment map.
	 *
	 * @param listOfAppointments
	 *            the list of appointments
	 * @param mapOfAppointments
	 *            the map of appointments
	 * @return the appointment map
	 */
	private Map<String, List<AppointmentDto>> getAppointmentMap(List<AppointmentDto> listOfAppointments,
			Map<String, List<AppointmentDto>> mapOfAppointments) {
		listOfAppointments.forEach(appointment -> {
			String timeKey = appointment.getAppointmentTime();
			if (!mapOfAppointments.containsKey(timeKey)) {
				listOfAppointmentsOnSameTime = new LinkedList<AppointmentDto>();
				mapOfAppointments.put(timeKey, listOfAppointmentsOnSameTime);
			} else {
				listOfAppointmentsOnSameTime = mapOfAppointments.get(timeKey);
			}
			listOfAppointmentsOnSameTime.add(appointment);

		});
		return mapOfAppointments;

	}

	/**
	 * Creates the appointment dto.
	 *
	 * @param appointment
	 *            the appointment
	 * @return the appointment dto
	 */
	private AppointmentDto createAppointmentDto(Appointment appointment) {
		AppointmentDto appointmentDto = null;
		if (null != appointment) {
			appointmentDto = new AppointmentDto();
			appointmentDto.setAppointmentId(appointment.getId());
			appointmentDto
					.setAppointmentDate(Util.formatDate(IConstants.DATE_FORMAT, appointment.getAppointmentDate()));
			appointmentDto
					.setAppointmentTime(Util.formatDate(IConstants.TIME_FORMAT, appointment.getAppointmentTime()));
			appointmentDto.setAppointmentType(appointment.getAppointmentType());
			appointmentDto.setAppointmentTypeDesc(appointment.getAppointmentTypeDesc());
			appointmentDto.setDoctorInitials(appointment.getDoctorInitials());
			appointmentDto.setCodeId(appointment.getPatientCode().getId());
			appointmentDto.setAppointmentStatus(appointment.getAppointmentStatus());
		}
		return appointmentDto;
	}

	/**
	 * Creates the appointment obj.
	 *
	 * @param appointmentDto
	 *            the appointment dto
	 * @return the appointment
	 */
	private Appointment createAppointmentObj(AppointmentDto appointmentDto) {
		Appointment appointment = new Appointment();
		if (null != appointmentDto.getAppointmentId()) {
			appointment.setId(appointmentDto.getAppointmentId());
		}
		appointment.setAppointmentDate(Util.stringToDate(appointmentDto.getAppointmentDate(), IConstants.DATE_FORMAT));
		appointment.setAppointmentTime(Util.stringToDate(appointmentDto.getAppointmentTime(), IConstants.TIME_FORMAT));
		appointment.setAppointmentType(appointmentDto.getAppointmentType());
		appointment.setAppointmentTypeDesc(appointmentDto.getAppointmentTypeDesc());
		appointment.setDoctorInitials(appointmentDto.getDoctorInitials());
		appointment.setPatientCode(new Codes(appointmentDto.getCodeId()));
		appointment.setAppointmentStatus(appointmentDto.getAppointmentStatus());
		return appointment;

	}

}
