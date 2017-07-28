/**
 *
 */
package org.cf.card.dto;

import java.io.Serializable;
import java.util.Date;

import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

/**
 * @author Nikhil Mahajan
 *
 */
public class AppointmentDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long appointmentId;
	private Long coupleId;
	private Long codeId;
	private String appointmentTime;
	private String appointmentDate;
	private int appointmentType;
	private String doctorInitials;
	private String appointmentTypeDesc;
	private String firstName;
	private String middleName;
	private String surname;
	private int appointmentStatus;

	public AppointmentDto() {
	}
	
	
	

	
	/**
	 * @param appointmentTime
	 * @param appointmentDate
	 */
	public AppointmentDto(String appointmentTime, String appointmentDate) {
		this.appointmentTime = appointmentTime;
		this.appointmentDate = appointmentDate;
	}





	/**
	 * Instantiates a new appointment dto.
	 *
	 * @param appointmentId the appointment id
	 * @param coupleId the couple id
	 * @param appointmentTime the appointment time
	 * @param appointmentDate the appointment date
	 * @param appointmentType the appointment type
	 * @param doctorInitials the doctor initials
	 * @param appointmentTypeDesc the appointment type desc
	 * @param firstName the first name
	 * @param middleName the middle name
	 * @param surname the surname
	 */
	public AppointmentDto(Long appointmentId, Long coupleId, Long codeId, Date appointmentTime,
			Date appointmentDate, int appointmentType, String doctorInitials, String appointmentTypeDesc,
			int appointmentStatus, String firstName, String middleName, String surname) {
		this.appointmentId = appointmentId;
		this.coupleId = coupleId;
		this.codeId = codeId;
		this.appointmentTime = Util.formatDate(IConstants.TIME_FORMAT, appointmentTime);
		this.appointmentDate = Util.formatDate(IConstants.DATE_FORMAT, appointmentDate);
		this.appointmentType = appointmentType;
		this.doctorInitials = doctorInitials;
		this.appointmentTypeDesc = appointmentTypeDesc;
		this.appointmentStatus = appointmentStatus;
		this.firstName = firstName;
		this.middleName = middleName;
		this.surname = surname;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	public Long getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public int getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(int appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the doctorInitials
	 */
	public String getDoctorInitials() {
		return doctorInitials;
	}

	/**
	 * @param doctorInitials
	 *            the doctorInitials to set
	 */
	public void setDoctorInitials(String doctorInitials) {
		this.doctorInitials = doctorInitials;
	}

	/**
	 * @return the appointmentTypeDesc
	 */
	public String getAppointmentTypeDesc() {
		return appointmentTypeDesc;
	}

	/**
	 * @param appointmentTypeDesc
	 *            the appointmentTypeDesc to set
	 */
	public void setAppointmentTypeDesc(String appointmentTypeDesc) {
		this.appointmentTypeDesc = appointmentTypeDesc;
	}

	/**
	 * @return the appointmentStatus
	 */
	public int getAppointmentStatus() {
		return appointmentStatus;
	}

	/**
	 * @param appointmentStatus the appointmentStatus to set
	 */
	public void setAppointmentStatus(int appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	/**
	 * @return the codeId
	 */
	public Long getCodeId() {
		return codeId;
	}

	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

}
