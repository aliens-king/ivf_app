/**
 *
 */
package org.cf.card.model;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Nikhil Mahajan
 *
 */
@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public Appointment() {
	}

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "patient_code_id")
	private Codes patientCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "appointment_date")
	private Date appointmentDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "appointment_time")
	private Date appointmentTime;

	@Column(name = "appointment_type", columnDefinition = "int default 0")
	private int appointmentType;
	
	@Column(name = "doctor_initials")
	private String doctorInitials;
	
	@Column(name = "appointment_type_desc")
	private String appointmentTypeDesc;
	
	@Column(name = "appointment_status")
	private int appointmentStatus;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Codes getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(Codes patientCode) {
		this.patientCode = patientCode;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public int getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(int appointmentType) {
		this.appointmentType = appointmentType;
	}

	/**
	 * @return the doctorInitials
	 */
	public String getDoctorInitials() {
		return doctorInitials;
	}

	/**
	 * @param doctorInitials the doctorInitials to set
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
	 * @param appointmentTypeDesc the appointmentTypeDesc to set
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
	
	
	

}
