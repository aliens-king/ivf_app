package org.cf.card.dto;

import java.util.Date;

public class PaymentDto {

	private Long id;

	private Float totalBill;

	private Float paidBill;

	private Float balance;

	private String paymentMode;

	private String paymentBilledBy;

	private Date paymentDate;
	
	private String remarks;

	private Long billingInvoiceId;

	private Long coupleId;

	public PaymentDto() {
	}

	public PaymentDto(Long id, Float totalBill, Float paidBill, Float balance, String paymentMode,
			String paymentBilledBy, Date paymentDate, String remarks, Long billingInvoiceId, Long coupleId) {
		super();
		this.id = id;
		this.totalBill = totalBill;
		this.paidBill = paidBill;
		this.balance = balance;
		this.paymentMode = paymentMode;
		this.paymentBilledBy = paymentBilledBy;
		this.paymentDate = paymentDate;
		this.remarks = remarks;
		this.billingInvoiceId = billingInvoiceId;
		this.coupleId = coupleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(Float totalBill) {
		this.totalBill = totalBill;
	}

	public Float getPaidBill() {
		return paidBill;
	}

	public void setPaidBill(Float paidBill) {
		this.paidBill = paidBill;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getBillingInvoiceId() {
		return billingInvoiceId;
	}

	public void setBillingInvoiceId(Long billingInvoiceId) {
		this.billingInvoiceId = billingInvoiceId;
	}

	public Long getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	public String getPaymentBilledBy() {
		return paymentBilledBy;
	}

	public void setPaymentBilledBy(String paymentBilledBy) {
		this.paymentBilledBy = paymentBilledBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
