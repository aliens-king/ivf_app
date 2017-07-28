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
@Table(name = "nurse_station")
public class NurseStation {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "couple_id")
	private Couple couple;
	
	@Column(name = "nurse_notes", columnDefinition = "LONGTEXT")
	private String nurseNotes;

	@Column(name = "remarks", columnDefinition = "LONGTEXT")
	private String remarks;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	
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

	public String getNurseNotes() {
		return nurseNotes;
	}

	public void setNurseNotes(String nurseNotes) {
		this.nurseNotes = nurseNotes;
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