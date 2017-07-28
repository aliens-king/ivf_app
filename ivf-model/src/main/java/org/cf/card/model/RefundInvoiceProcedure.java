package org.cf.card.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 3, 2017
 */
@Entity
@Table(name = "refund_invoice_procedure", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "refund_invoice_id", "procedure_id" }) })
public class RefundInvoiceProcedure implements Serializable{
	
	private static final long serialVersionUID = 8598989888236885105L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "procedure_id")
	private Procedure procedure;

	@ManyToOne
	@JoinColumn(name = "refund_invoice_id")
	private RefundInvoice refundInvoice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public RefundInvoice getRefundInvoice() {
		return refundInvoice;
	}

	public void setRefundInvoice(RefundInvoice refundInvoice) {
		this.refundInvoice = refundInvoice;
	}

	
}
