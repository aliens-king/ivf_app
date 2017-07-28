package org.cf.card.dto;

import java.util.Date;

public class DoctorNurseDto {
	
	private Long id;
	
	private Long coupleId;
	
	private String medicalHistory;

	private String nurseNotes;

	private String scan;

	private String labPoints;
	
	private String remarks;
	
	private Date createdDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getNurseNotes() {
		return nurseNotes;
	}

	public void setNurseNotes(String nurseNotes) {
		this.nurseNotes = nurseNotes;
	}

	public String getScan() {
		return scan;
	}

	public void setScan(String scan) {
		this.scan = scan;
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
