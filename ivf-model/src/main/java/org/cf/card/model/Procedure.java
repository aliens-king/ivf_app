package org.cf.card.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 28, 2016
 */
@Entity
@Table(name = "procedures")
public class Procedure implements Serializable {
	
	
	private static final long serialVersionUID = -7021786781938866197L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "procedure_name")
	private String procedureName;
	
	@Column(name = "procedure_price",nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float procedurePrice;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "procedure_create_date")
	private Date procedureCreateDate;
	
	@OneToMany(mappedBy="procedure",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InvoiceProcedure> invoiceProcedures;
	
	@OneToMany(mappedBy="procedure",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefundInvoiceProcedure> refundInvoiceProcedure;
	
	
	
	public Procedure() {
	}

	public Procedure(Long id) {
		this.id = id;
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

	public List<InvoiceProcedure> getInvoiceProcedures() {
		return invoiceProcedures;
	}

	public void setInvoiceProcedures(List<InvoiceProcedure> invoiceProcedures) {
		this.invoiceProcedures = invoiceProcedures;
	}

	public List<RefundInvoiceProcedure> getRefundInvoiceProcedure() {
		return refundInvoiceProcedure;
	}

	public void setRefundInvoiceProcedure(List<RefundInvoiceProcedure> refundInvoiceProcedure) {
		this.refundInvoiceProcedure = refundInvoiceProcedure;
	}
	
}
