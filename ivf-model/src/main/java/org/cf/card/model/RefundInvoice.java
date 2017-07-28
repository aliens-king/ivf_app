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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 3, 2017
 */
@Entity
@Table(name="refund_invoice")
public class RefundInvoice implements Serializable{
	
	private static final long serialVersionUID = 796211363158140048L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "refund_invoice_number", nullable = false, columnDefinition = "int default 0")
	private Long refundInvoiceNumber;
	
	@Column(name = "total_refund_bill", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float totalRefundBill;
	
	@Column(name = "sub_total", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float subTotal;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "refund_create_date")
	private Date refundCreateDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "refund_invoice_vat")
	private int vat;
	
	@JsonIgnore
	@OneToMany(mappedBy="procedure", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefundInvoiceProcedure> refundInvoiceProcedure;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="couple_id")
	private Couple couple;
	
	
	public RefundInvoice() {
	}

	public RefundInvoice(Long id) {
		this.id = id;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public List<RefundInvoiceProcedure> getRefundInvoiceProcedure() {
		return refundInvoiceProcedure;
	}

	public void setRefundInvoiceProcedure(List<RefundInvoiceProcedure> refundInvoiceProcedure) {
		this.refundInvoiceProcedure = refundInvoiceProcedure;
	}

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	
}
