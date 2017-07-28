package org.cf.card.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientInvestigationDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -3020873694716371316L;

	private Long womanPatientInvestigationId;

	private Long partnerPatientInvestigationId;

	private Long womanClientId;

	private Long partnerClientId;

	private Long treatmentId;

	private Float bmi;

	private Float partnerBMI;

	/*private String medicalHistory;

	private String nurseNotes;

	private String biochemstry;

	private String scan;

	private String labPoints;*/

	private Long womanCodeId;

	private Long partnerCodeId;

	private Long nurseId;//user id

	private String scan;

	List<InvestigatinValueDto> investigatinValues = new ArrayList<>();

	List<InvestigatinValueDto> aoMicrobiology = new ArrayList<>();

	List<InvestigatinValueDto> aoHormonal = new ArrayList<>();


	List<InvestigatinValueDto> aoPartnerBlood = new ArrayList<>();

	List<InvestigatinValueDto> aoPartnerMicrobiology = new ArrayList<>();

	List<InvestigatinValueDto> aoPartnerHormonal = new ArrayList<>();

	public Long getWomanClientId() {
		return womanClientId;
	}

	public void setWomanClientId(Long womanClientId) {
		this.womanClientId = womanClientId;
	}

	public Long getPartnerClientId() {
		return partnerClientId;
	}

	public void setPartnerClientId(Long partnerClientId) {
		this.partnerClientId = partnerClientId;
	}

	public Long getTreatmentId() {
		return treatmentId;
	}

	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}

	public Float getBmi() {
		return bmi;
	}

	public void setBmi(Float bmi) {
		this.bmi = bmi;
	}

	public Float getPartnerBMI() {
		return partnerBMI;
	}

	public void setPartnerBMI(Float partnerBMI) {
		this.partnerBMI = partnerBMI;
	}



	/*public String getMedicalHistory() {
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

	public String getBiochemstry() {
		return biochemstry;
	}

	public void setBiochemstry(String biochemstry) {
		this.biochemstry = biochemstry;
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
	}*/

	public String getScan() {
		return scan;
	}

	public void setScan(String scan) {
		this.scan = scan;
	}

	public List<InvestigatinValueDto> getInvestigatinValues() {
		return investigatinValues;
	}

	public void setInvestigatinValues(List<InvestigatinValueDto> investigatinValues) {
		this.investigatinValues = investigatinValues;
	}
	public Long getWomanPatientInvestigationId() {
		return womanPatientInvestigationId;
	}

	public void setWomanPatientInvestigationId(Long womanPatientInvestigationId) {
		this.womanPatientInvestigationId = womanPatientInvestigationId;
	}

	public Long getPartnerPatientInvestigationId() {
		return partnerPatientInvestigationId;
	}

	public void setPartnerPatientInvestigationId(Long partnerPatientInvestigationId) {
		this.partnerPatientInvestigationId = partnerPatientInvestigationId;
	}

	public Long getWomanCodeId() {
		return womanCodeId;
	}

	public void setWomanCodeId(Long womanCodeId) {
		this.womanCodeId = womanCodeId;
	}

	public Long getPartnerCodeId() {
		return partnerCodeId;
	}

	public void setPartnerCodeId(Long partnerCodeId) {
		this.partnerCodeId = partnerCodeId;
	}
	public Long getNurseId() {
		return nurseId;
	}

	public void setNurseId(Long nurseId) {
		this.nurseId = nurseId;
	}

	public List<InvestigatinValueDto> getAoMicrobiology() {
		return aoMicrobiology;
	}

	public void setAoMicrobiology(List<InvestigatinValueDto> aoMicrobiology) {
		this.aoMicrobiology = aoMicrobiology;
	}

	public List<InvestigatinValueDto> getAoHormonal() {
		return aoHormonal;
	}

	public void setAoHormonal(List<InvestigatinValueDto> aoHormonal) {
		this.aoHormonal = aoHormonal;
	}

	public List<InvestigatinValueDto> getAoPartnerBlood() {
		return aoPartnerBlood;
	}

	public void setAoPartnerBlood(List<InvestigatinValueDto> aoPartnerBlood) {
		this.aoPartnerBlood = aoPartnerBlood;
	}

	public List<InvestigatinValueDto> getAoPartnerMicrobiology() {
		return aoPartnerMicrobiology;
	}

	public void setAoPartnerMicrobiology(List<InvestigatinValueDto> aoPartnerMicrobiology) {
		this.aoPartnerMicrobiology = aoPartnerMicrobiology;
	}

	public List<InvestigatinValueDto> getAoPartnerHormonal() {
		return aoPartnerHormonal;
	}

	public void setAoPartnerHormonal(List<InvestigatinValueDto> aoPartnerHormonal) {
		this.aoPartnerHormonal = aoPartnerHormonal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PatientInvestigationDto [womanPatientInvestigationId=");
		builder.append(womanPatientInvestigationId);
		builder.append(", partnerPatientInvestigationId=");
		builder.append(partnerPatientInvestigationId);
		builder.append(", womanClientId=");
		builder.append(womanClientId);
		builder.append(", partnerClientId=");
		builder.append(partnerClientId);
		builder.append(", treatmentId=");
		builder.append(treatmentId);
		builder.append(", bmi=");
		builder.append(bmi);
		builder.append(", partnerBMI=");
		builder.append(partnerBMI);
		builder.append(", womanCodeId=");
		builder.append(womanCodeId);
		builder.append(", partnerCodeId=");
		builder.append(partnerCodeId);
		builder.append(", nurseId=");
		builder.append(nurseId);
		builder.append(", scan=");
		builder.append(scan);
		builder.append(", investigatinValues=");
		builder.append(investigatinValues);
		builder.append(", aoMicrobiology=");
		builder.append(aoMicrobiology);
		builder.append(", aoHormonal=");
		builder.append(aoHormonal);
		builder.append(", aoPartnerBlood=");
		builder.append(aoPartnerBlood);
		builder.append(", aoPartnerMicrobiology=");
		builder.append(aoPartnerMicrobiology);
		builder.append(", aoPartnerHormonal=");
		builder.append(aoPartnerHormonal);
		builder.append("]");
		return builder.toString();
	}





}
