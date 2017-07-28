package org.cf.card.dto;

import java.util.Date;
import java.util.List;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 3, 2017
 */
public class RefundInvoiceDto {

	private Long id;

	private Long refundInvoiceNumber;

	private Float totalRefundBill;

	private Float subTotal;

	private Date refundCreateDate;

	private String createdBy;
	
	private String remarks;

	private List<ProcedureDto> procedureDto;

	private int vat;

	private Long coupleId;

	public RefundInvoiceDto() {

	}

	public RefundInvoiceDto(Long id, Long refundInvoiceNumber, Float totalRefundBill, Float subTotal,
			Date refundCreateDate, String createdBy, int vat, String remarks, Long coupleId) {
		this.id = id;
		this.refundInvoiceNumber = refundInvoiceNumber;
		this.totalRefundBill = totalRefundBill;
		this.subTotal = subTotal;
		this.refundCreateDate = refundCreateDate;
		this.createdBy = createdBy;
		// this.procedureDto = procedureDto;
		this.vat = vat;
		this.remarks = remarks;
		this.coupleId = coupleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRefundInvoiceNumber() {
		return refundInvoiceNumber;
	}

	public void setRefundInvoiceNumber(Long refundInvoiceNumber) {
		this.refundInvoiceNumber = refundInvoiceNumber;
	}

	public Float getTotalRefundBill() {
		return totalRefundBill;
	}

	public void setTotalRefundBill(Float totalRefundBill) {
		this.totalRefundBill = totalRefundBill;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public Date getRefundCreateDate() {
		return refundCreateDate;
	}

	public void setRefundCreateDate(Date refundCreateDate) {
		this.refundCreateDate = refundCreateDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public Long getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ProcedureDto> getProcedureDto() {
		return procedureDto;
	}

	public void setProcedureDto(List<ProcedureDto> procedureDto) {
		this.procedureDto = procedureDto;
	}

}
