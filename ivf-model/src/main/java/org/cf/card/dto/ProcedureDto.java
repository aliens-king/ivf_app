package org.cf.card.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 4, 2017
 */
public class ProcedureDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4179198518935335119L;

	private Long id;

	private String procedureName;

	private Float procedurePrice;

	private Date procedureCreateDate;

	public ProcedureDto() {
	}

	public ProcedureDto(Long id, String procedureName, Float procedurePrice, Date procedureCreateDate) {
		this.id = id;
		this.procedureName = procedureName;
		this.procedurePrice = procedurePrice;
		this.procedureCreateDate = procedureCreateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public Float getProcedurePrice() {
		return procedurePrice;
	}

	public void setProcedurePrice(Float procedurePrice) {
		this.procedurePrice = procedurePrice;
	}

	public Date getProcedureCreateDate() {
		return procedureCreateDate;
	}

	public void setProcedureCreateDate(Date procedureCreateDate) {
		this.procedureCreateDate = procedureCreateDate;
	}

}
