package org.cf.card.model;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "embryo_code_day")
public class EmbryoCodeDay implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "embryoCode_id")
	private EmbryoCode embryoCode;

	@Column(name = "remark", columnDefinition = "LONGTEXT")
	private String remark;

	@Column(name = "day_index", length = 2)
	private int dayIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmbryoCode getEmbryoCode() {
		return embryoCode;
	}

	public void setEmbryoCode(EmbryoCode embryoCode) {
		this.embryoCode = embryoCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}

}
