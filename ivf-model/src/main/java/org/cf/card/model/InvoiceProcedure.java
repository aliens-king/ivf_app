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
 * @since : Dec 28, 2016
 */
@Entity
@Table(name = "invoice_procedure", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "billing_invoice_id", "procedure_id" }) })
public class InvoiceProcedure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3763227684208715112L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "procedure_id")
	private Procedure procedure;

	@ManyToOne
	@JoinColumn(name = "billing_invoice_id")
	private BillingInvoice billingInvoice;

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

	public BillingInvoice getBillingInvoice() {
		return billingInvoice;
	}

	public void setBillingInvoice(BillingInvoice billingInvoice) {
		this.billingInvoice = billingInvoice;
	}

}
