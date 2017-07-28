package org.cf.card.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ankit
 *
 */
@Entity
@Table(name = "archive")
public class Archive implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4890775779740786427L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Date uploadedDate;

	@Column
	private String orignalFileName;
	
	@Column
	private String uniqueFileName;
	
	@Column
	private String extention;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "couple_id")
	private Couple couple;

	public Long getId() {
		return id;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}


	public String getOrignalFileName() {
		return orignalFileName;
	}

	public void setOrignalFileName(String orignalFileName) {
		this.orignalFileName = orignalFileName;
	}

	public Couple getCouple() {
		return couple;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}


	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	public String getUniqueFileName() {
		return uniqueFileName;
	}

	public void setUniqueFileName(String uniqueFileName) {
		this.uniqueFileName = uniqueFileName;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	
}
