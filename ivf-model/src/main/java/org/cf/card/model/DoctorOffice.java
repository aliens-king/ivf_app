package org.cf.card.model;

import static javax.persistence.GenerationType.AUTO;

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


@Entity
@Table(name = "doctor_office")
public class DoctorOffice {

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "couple_id")
	private Couple couple;

	@Column(name = "medical_history", columnDefinition = "LONGTEXT")
	private String medicalHistory;

	@Column(name = "lab_points", columnDefinition = "LONGTEXT")
	private String labPoints;
	
	@Column(name = "remarks", columnDefinition = "LONGTEXT")
	private String remarks;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	
	/*@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;*/
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getLabPoints() {
		return labPoints;
	}

	public void setLabPoints(String labPoints) {
		this.labPoints = labPoints;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
