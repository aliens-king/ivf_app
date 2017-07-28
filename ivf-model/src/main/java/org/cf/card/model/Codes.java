package org.cf.card.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created by Dell on 10/7/2014.
 */
@Entity
@Table(name = "codes")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID")
public class Codes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	// @Length(min = 3, max = 10)
	@Column
	private String code;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "treatment_id")
	private Treatment treatment;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumn(name = "patient_investigation_id")
	private PatientInvestigation patientInvestigation;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "code")
	private List<EmbryoCode> embryoCode;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "code")
	private List<SemenCode> semenCode;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "code")
	private List<PregnancyOutcomes> pregnancyOutcomes;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "code")
	private List<Remark> remarks;


	public Codes() {

	}

	public Codes(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public PatientInvestigation getPatientInvestigation() {
		return patientInvestigation;
	}

	public void setPatientInvestigation(PatientInvestigation patientInvestigation) {
		this.patientInvestigation = patientInvestigation;
	}

	public List<EmbryoCode> getEmbryoCode() {
		return embryoCode;
	}

	public void setEmbryoCode(List<EmbryoCode> embryoCode) {
		this.embryoCode = embryoCode;
	}

	public List<SemenCode> getSemenCode() {
		return semenCode;
	}

	public void setSemenCode(List<SemenCode> semenCode) {
		this.semenCode = semenCode;
	}

	public List<PregnancyOutcomes> getPregnancyOutcomes() {
		return pregnancyOutcomes;
	}

	public void setPregnancyOutcomes(List<PregnancyOutcomes> pregnancyOutcomes) {
		this.pregnancyOutcomes = pregnancyOutcomes;
	}

	public List<Remark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<Remark> remarks) {
		this.remarks = remarks;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Codes codes = (Codes) o;

		if (id != codes.id)
			return false;
		if (client != null ? !client.equals(codes.client) : codes.client != null)
			return false;
		if (code != null ? !code.equals(codes.code) : codes.code != null)
			return false;
		if (treatment != null ? !treatment.equals(codes.treatment) : codes.treatment != null)
			return false;
		if (patientInvestigation != null ? !patientInvestigation.equals(codes.patientInvestigation)
				: codes.patientInvestigation != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (client != null ? client.hashCode() : 0);
		result = 31 * result + (treatment != null ? treatment.hashCode() : 0);
		result = 31 * result + (patientInvestigation != null ? patientInvestigation.hashCode() : 0);
		return result;
	}

	/*
	 * @Override public String toString() { StringBuilder builder = new
	 * StringBuilder(); builder.append("Codes [id="); builder.append(id);
	 * builder.append(", code="); builder.append(code); // builder.append(
	 * ", client="); // builder.append(client); // builder.append(", treatment="
	 * ); // builder.append(treatment); builder.append("]"); return
	 * builder.toString(); }
	 */

}
