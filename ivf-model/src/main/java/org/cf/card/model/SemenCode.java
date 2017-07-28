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
 * @author insonix
 *
 */
@Entity
@Table(name = "semen_code")
public class SemenCode implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1082745657777952864L;

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "treatment_id") private Treatment treatment;
	 */

	// @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "code_id")
	private Codes code;

	// @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "semen_id")
	private Semen semen;

	@Column(name = "cryoARemarks", columnDefinition = "LONGTEXT", nullable = true)
	private String cryoARemarks;

	@Column(name = "_index")
	private Integer index;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_used", length = 19)
	private Date dateUsed;

	public SemenCode() {}

	public SemenCode(Long id, Codes code, Semen semen, Integer index, Date dateUsed) {
		this.id = id;
		this.code = code;
		this.semen = semen;
		this.index = index;
		this.dateUsed = dateUsed;
	}
	
	public String getCryoARemarks() {
		return cryoARemarks;
	}

	public void setCryoARemarks(String cryoARemarks) {
		this.cryoARemarks = cryoARemarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Codes getCode() {
		return code;
	}

	public void setCode(Codes code) {
		this.code = code;
	}

	public Semen getSemen() {
		return semen;
	}

	public void setSemen(Semen semen) {
		this.semen = semen;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Date getDateUsed() {
		return dateUsed;
	}

	public void setDateUsed(Date dateUsed) {
		this.dateUsed = dateUsed;
	}

}
