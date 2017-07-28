/**
 *
 */
package org.cf.card.persistence;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.AppointmentDto;
import org.cf.card.model.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Nikhil Mahajan
 *
 */
@Repository
public interface AppointmentDao extends GenericDao<Appointment, Long> {

	/**
	 * Find by appointment date.
	 *
	 * @param appointmentDate
	 *            the appointment date
	 * @return the appointment
	 */
	@Query("SELECT NEW org.cf.card.dto.AppointmentDto(ap.id, cp.id, co.id, ap.appointmentTime, ap.appointmentDate, "
			+ "ap.appointmentType, ap.doctorInitials, ap.appointmentTypeDesc, ap.appointmentStatus, c.firstName, c.middleName, c.surname) "
			+ "FROM Appointment ap "
			+ "INNER JOIN ap.patientCode co "
			+ "INNER JOIN co.client c "
			+ "INNER JOIN c.couple cp "
			+ "WHERE ap.appointmentDate =:appointmentDate order by ap.appointmentTime, ap.id ASC")
	public List<AppointmentDto> findByAppointmentDate(@Param(value="appointmentDate") Date appointmentDate);
	
	

	/**
	 * Find by appointment date and appointment time.
	 *
	 * @param appointmentDate the appointment date
	 * @param appointmentTime the appointment time
	 * @return the long
	 */
	@Query("SELECT COUNT(ap) FROM Appointment ap WHERE ap.appointmentDate =:appointmentDate AND ap.appointmentTime =:appointmentTime")
	public Long findByAppointmentDateAndAppointmentTime(@Param(value = "appointmentDate") Date appointmentDate, @Param(value = "appointmentTime") Date appointmentTime);

}
