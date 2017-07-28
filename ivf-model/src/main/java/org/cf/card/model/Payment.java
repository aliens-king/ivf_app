package org.cf.card.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 30, 2016
 */
@Entity
@Table(name="payment")
public class Payment implements Serializable{
	
	private static final long serialVersionUID = 103277196536630276L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "total_bill", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float totalBill;
	
	@Column(name = "paid_bill", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float paidBill;
	
	@Column(name = "balance", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float balance;
	
	@Column(name = "payment_mode")
	private String paymentMode;
	
	@Column(name = "payment_billed_by")
	private String paymentBilledBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "payment_date")
	private Date paymentDate;
	
	@Column(name="remarks")
	private String remarks;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="billing_invoice_id")
	private BillingInvoice billingInvoice;
	
	@ManyToOne
	@JoinColumn(name="couple_id")
	private Couple couple;

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

	public String getPaymentBilledBy() {
		return paymentBilledBy;
	}

	public void setPaymentBilledBy(String paymentBilledBy) {
		this.paymentBilledBy = paymentBilledBy;
	}

	public BillingInvoice getBillingInvoice() {
		return billingInvoice;
	}

	public void setBillingInvoice(BillingInvoice billingInvoice) {
		this.billingInvoice = billingInvoice;
	}

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
