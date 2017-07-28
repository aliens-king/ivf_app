package org.cf.card.dto;

/**
 * The Class InvoiceProcedureDto.
 *
 * @author Ankit Sood Jan 3, 2017
 */
public class InvoiceProcedureDto {

	private Long id;

	private Long procedureId;

	private Long billingInvoiceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(Long procedureId) {
		this.procedureId = procedureId;
	}

	public Long getBillingInvoiceId() {
		return billingInvoiceId;
	}

	public void setBillingInvoiceId(Long billingInvoiceId) {
		this.billingInvoiceId = billingInvoiceId;
	}

}
