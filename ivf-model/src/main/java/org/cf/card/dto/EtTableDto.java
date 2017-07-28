package org.cf.card.dto;

public class EtTableDto {

	private String patientName;
	private Long coupleId;
	private int dayIndex;
	private String firstName;
	private String middleName;
	private String surName;
	
	private String etTime;
	private String etDate;
	
	

	public String getEtTime() {
		return etTime;
	}

	public String getEtDate() {
		return etDate;
	}

	public void setEtTime(String etTime) {
		this.etTime = etTime;
	}

	public void setEtDate(String etDate) {
		this.etDate = etDate;
	}

	public EtTableDto(){

	}

	public EtTableDto(String patientName, Long coupleId, int dayIndex,String fName,String surName) {
		super();
		this.patientName = patientName;
		this.coupleId = coupleId;
		this.dayIndex = dayIndex;
		this.firstName= fName;
		this.surName= surName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Long getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
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

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}
	
	
	
}
