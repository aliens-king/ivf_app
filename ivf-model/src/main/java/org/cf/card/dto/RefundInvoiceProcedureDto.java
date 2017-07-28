package org.cf.card.dto;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 4, 2017
 */
public class RefundInvoiceProcedureDto {

	private Long id;

	private Long procedureId;

	private Long refundInvoiceId;

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

	public Long getRefundInvoiceId() {
		return refundInvoiceId;
	}

	public void setRefundInvoiceId(Long refundInvoiceId) {
		this.refundInvoiceId = refundInvoiceId;
	}
	
	

}
