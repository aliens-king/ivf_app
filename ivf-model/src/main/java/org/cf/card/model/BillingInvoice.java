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
 * @since : Jan 4, 2017
 */
@Entity
@Table(name = "billing_invoice")
public class BillingInvoice implements Serializable{
	
	
	private static final long serialVersionUID = 1537155294580307318L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "invoice_number", nullable = false, columnDefinition = "int default 0")
	private Long invoiceNumber;
	
	@Column(name = "total_bill", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float totalBill;
	
	@Column(name = "total_paid", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float totalPaid;
	
	@Column(name = "sub_total", nullable = false, columnDefinition = "Float(10,2) default '00.00'")
	private Float subTotal;
	
	@Column(name = "total_balance", nullable = false,  columnDefinition = "Float(10,2) default '00.00'")
	private Float totalBalance;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_create_date")
	private Date invoiceCreateDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "invoice_vat")
	private int vat;
	
	@JsonIgnore
	@OneToMany(mappedBy="procedure", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InvoiceProcedure> invoiceProcedures;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="couple_id")
	private Couple couple;
	
	
	public BillingInvoice() {
	}

	public BillingInvoice(Long id) {
		this.id = id;
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

	public List<InvoiceProcedure> getInvoiceProcedures() {
		return invoiceProcedures;
	}

	public void setInvoiceProcedures(List<InvoiceProcedure> invoiceProcedures) {
		this.invoiceProcedures = invoiceProcedures;
	}

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
