package org.cf.card;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.cf.card.model.Appointment;
import org.cf.card.model.Codes;
import org.cf.card.persistence.AppointmentDao;
import org.cf.card.service.AppointmentService;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

public class AppointmentTest extends BaseTest {

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	AppointmentDao appointmentDao;

	public void addData() {
		List<Appointment> aoAppointments = new ArrayList<Appointment>();
		LocalTime startTime = LocalTime.of(8, 0);
		@SuppressWarnings("unused")
		DateFormat dateFormat = new SimpleDateFormat(IConstants.DATE_FORMAT);

		Calendar cal = Calendar.getInstance();
		// cal.set(2015, 10, 1);
		for (int i = 0; i < 24; i++) {

			LocalTime nextTime = startTime.plusMinutes(30 * i);
			Appointment appointment = new Appointment();
			appointment.setAppointmentType(1);

			cal.set(2015, 10, i + 1);
			Date appointmentDate = cal.getTime();
			appointment.setAppointmentDate(appointmentDate);
			appointment.setAppointmentTime(Util.LocalTimeToUtilTime(nextTime));
			appointment.setPatientCode(new Codes(21l));
			aoAppointments.add(appointment);
		}

		appointmentService.saveAll(aoAppointments);
	}

	// @Test
	public void deleteAll() {
		appointmentDao.deleteAll();
	}

	/*
	 * //@Test public void findByAppointmentOnCurrentDate(){ List<Appointment>
	 * listOfAppointments= appointmentDao.findByAppointmentDate(new Date()); for
	 * (Appointment appointment : listOfAppointments) {
	 * 
	 * System.out.println("Time: "+appointment.getAppointmentTime()+",  Date:"
	 * +appointment.getAppointmentDate()); } }
	 */
}
