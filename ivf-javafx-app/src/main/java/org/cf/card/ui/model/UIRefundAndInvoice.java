package org.cf.card.ui.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Hyperlink;

public class UIRefundAndInvoice {

	private LongProperty refundInvoiceId;
	private LongProperty srNo;
	private Hyperlink refundInvoiceHyperlink;
	private LongProperty refundInvoiceNumber;
	private StringProperty totalRefundBill;
	private StringProperty refundInvoiceDate;
	private StringProperty billRefundedBy;
	private StringProperty womanDetails;
	private StringProperty remarks;
	

	
	
	/**
	 * This Constructor is for RefundBillPerCouple Screen.
	 * Instantiates a new UI refund and invoice.
	 * We are passing refundInvoiceId @param for save remarks purpose issue.
	 *
	 * @param srNo the sr no
	 * @param refundInvoiceNumber the refund invoice number
	 * @param totalRefundBill the total refund bill
	 * @param refundInvoiceDate the refund invoice date
	 * @param billRefundedBy the bill refunded by
	 */
	public UIRefundAndInvoice(Long srNo, Long refundInvoiceId , String totalRefundBill, Long refundInvoiceNumber, String refundInvoiceDate,
			String billRefundedBy, String remarks) {
		this.srNo = new SimpleLongProperty(srNo);
		this.refundInvoiceId = new SimpleLongProperty(refundInvoiceId);
		/*this.refundInvoiceNumber = new SimpleLongProperty(refundInvoiceNumber);*/
		this.refundInvoiceHyperlink = new  Hyperlink(String.valueOf(refundInvoiceNumber));
		this.totalRefundBill = new SimpleStringProperty(totalRefundBill);
		this.refundInvoiceDate = new SimpleStringProperty(refundInvoiceDate);
		this.billRefundedBy = new SimpleStringProperty(billRefundedBy);
		this.remarks = new SimpleStringProperty(remarks);
	}

	/**
	 * This Constructor is for RefundBillOverall Screen.
	 * Instantiates a new UI refund and invoice.
	 *
	 * @param srNo the sr no
	 * @param refundInvoiceNumber the refund invoice number
	 * @param totalRefundBill the total refund bill
	 * @param refundInvoiceDate the refund invoice date
	 * @param billRefundedBy the bill refunded by
	 * @param womanDetails the woman details
	 */
	public UIRefundAndInvoice(Long srNo, Long refundInvoiceNumber, String totalRefundBill, String refundInvoiceDate,
			String billRefundedBy, String womanDetails) {
		this.srNo = new SimpleLongProperty(srNo);
		this.refundInvoiceHyperlink = new  Hyperlink(String.valueOf(refundInvoiceNumber));
		this.refundInvoiceNumber = new SimpleLongProperty(refundInvoiceNumber);
		this.totalRefundBill = new SimpleStringProperty(totalRefundBill);
		this.refundInvoiceDate = new SimpleStringProperty(refundInvoiceDate);
		this.billRefundedBy = new SimpleStringProperty(billRefundedBy);
		this.womanDetails = new SimpleStringProperty(womanDetails);
	}

	public Hyperlink getRefundInvoiceHyperlink() {
		return refundInvoiceHyperlink;
	}

	public void setRefundInvoiceHyperlink(Hyperlink refundInvoiceHyperlink) {
		this.refundInvoiceHyperlink = refundInvoiceHyperlink;
	}

	public final LongProperty refundInvoiceNumberProperty() {
		return this.refundInvoiceNumber;
	}

	public final long getRefundInvoiceNumber() {
		return this.refundInvoiceNumberProperty().get();
	}

	public final void setRefundInvoiceNumber(final long refundInvoiceNumber) {
		this.refundInvoiceNumberProperty().set(refundInvoiceNumber);
	}

	public final StringProperty totalRefundBillProperty() {
		return this.totalRefundBill;
	}

	public final String getTotalRefundBill() {
		return this.totalRefundBillProperty().get();
	}

	public void setTotalRefundBill(String totalRefundBill) {
		this.totalRefundBillProperty().set(totalRefundBill);
		;
	}

	public final StringProperty refundInvoiceDateProperty() {
		return this.refundInvoiceDate;
	}

	public final String getRefundInvoiceDate() {
		return this.refundInvoiceDateProperty().get();
	}

	public final void setRefundInvoiceDate(String refundInvoiceDate) {
		this.refundInvoiceDateProperty().set(refundInvoiceDate);
		;
	}

	public final StringProperty billRefundedByProperty() {
		return this.billRefundedBy;
	}

	public final String getBillRefundedBy() {
		return this.billRefundedByProperty().get();
	}

	public final void setBillRefundedBy(String billRefundedBy) {
		this.billRefundedByProperty().set(billRefundedBy);
	}

	public final StringProperty womanDetailsProperty() {
		return this.womanDetails;
	}

	public final String getWomanDetails() {
		return this.womanDetailsProperty().get();
	}

	public final void setWomanDetails(String womanDetails) {
		this.womanDetailsProperty().set(womanDetails);
	}

	public final LongProperty srNoProperty() {
		return this.srNo;
	}

	public final long getSrNo() {
		return this.srNoProperty().get();
	}

	public final void setSrNo(final long srNo) {
		this.srNoProperty().set(srNo);
	}

	public final StringProperty remarksProperty() {
		return this.remarks;
	}
	

	public final String getRemarks() {
		return this.remarksProperty().get();
	}
	

	public final void setRemarks(final String remarks) {
		this.remarksProperty().set(remarks);
	}

	public final LongProperty refundInvoiceIdProperty() {
		return this.refundInvoiceId;
	}
	

	public final long getRefundInvoiceId() {
		return this.refundInvoiceIdProperty().get();
	}
	

	public final void setRefundInvoiceId(final long refundInvoiceId) {
		this.refundInvoiceIdProperty().set(refundInvoiceId);
	}
	
	
	

}
