package org.cf.card.dto;

import java.util.Date;
import java.util.List;

/**
 * The Class BillingInvoiceDto.
 *
 * @author Ankit Sood Jan 3, 2017
 */
public class BillingInvoiceDto {

	private Long id;

	private Long invoiceNumber;

	private Float totalBill;

	private Float totalPaid;

	private Float subTotal;

	private Float totalBalance;
	
	private int vat;

	private Date invoiceCreateDate;

	private String createdBy;
	
	private String remarks;

	private List<ProcedureDto> procedureDto;

	private Long coupleId;

	public BillingInvoiceDto() {
	}

	

	/**
	 * This constructor is using for get all billingInvoice and all unpaid invoice by coupleID
	 *
	 * @param id the id
	 * @param invoiceNumber the invoice number
	 * @param totalBill the total bill
	 * @param totalPaid the total paid
	 * @param subTotal the sub total
	 * @param totalBalance the total balance
	 * @param invoiceCreateDate the invoice create date
	 * @param createdBy the created by
	 * @param remarks the remarks
	 * @param coupleId the couple id
	 */
	public BillingInvoiceDto(Long id, Long invoiceNumber, Float totalBill, Float totalPaid, Float subTotal,
			Float totalBalance, Date invoiceCreateDate, String createdBy, String remarks, Long coupleId) {
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.totalBill = totalBill;
		this.totalPaid = totalPaid;
		this.subTotal = subTotal;
		this.totalBalance = totalBalance;
		this.invoiceCreateDate = invoiceCreateDate;
		this.createdBy = createdBy;
		this.remarks = remarks;
		this.coupleId = coupleId;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getInvoiceNumber() {
		return invoiceNumber;
	}



	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}



	public Float getTotalBill() {
		return totalBill;
	}



	public void setTotalBill(Float totalBill) {
		this.totalBill = totalBill;
	}



	public Float getTotalPaid() {
		return totalPaid;
	}



	public void setTotalPaid(Float totalPaid) {
		this.totalPaid = totalPaid;
	}



	public Float getSubTotal() {
		return subTotal;
	}



	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}



	public Float getTotalBalance() {
		return totalBalance;
	}



	public void setTotalBalance(Float totalBalance) {
		this.totalBalance = totalBalance;
	}



	public int getVat() {
		return vat;
	}



	public void setVat(int vat) {
		this.vat = vat;
	}



	public Date getInvoiceCreateDate() {
		return invoiceCreateDate;
	}



	public void setInvoiceCreateDate(Date invoiceCreateDate) {
		this.invoiceCreateDate = invoiceCreateDate;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public List<ProcedureDto> getProcedureDto() {
		return procedureDto;
	}



	public void setProcedureDto(List<ProcedureDto> procedureDto) {
		this.procedureDto = procedureDto;
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

}
