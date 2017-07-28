package org.cf.card.ui.model;

import org.cf.card.util.EnumEmbryo;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Hyperlink;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 6, 2017
 */
public class UIBillingAndInvoice {

	private LongProperty srNo;
	private LongProperty billingInvoiceId;
	private Hyperlink invoiceHyperlink;
	private LongProperty invoiceNumber;
	private StringProperty totalBill;
	private StringProperty totalPaid;
	private StringProperty totalBalance;
	private StringProperty invoiceDate;
	private StringProperty billedBy;
	private StringProperty womanDetails;
	private StringProperty remarks;

	/**
	 * This Constructor is for UnpaidBillPerCouple and InvoicesDetails Screen.
	 *
	 * Instantiates a new UI billing and invoice.
	 * 
	 * @param srNo
	 *            the sr no
	 * @param invoiceNumber
	 *            the invoice number
	 * @param totalBill
	 *            the total bill
	 * @param totalPaid
	 *            the total paid
	 * @param totalBalance
	 *            the total balance
	 * @param invoiceDate
	 *            the invoice date
	 * @param billedBy
	 *            the billed by
	 */
	public UIBillingAndInvoice(Long srNo, Long billingInvoiceId, String totalBill, String totalPaid, String totalBalance,
			String invoiceDate, String billedBy, String remarks, Long invoiceNumber) {
		this.srNo = new SimpleLongProperty(srNo);
		this.billingInvoiceId = new SimpleLongProperty(billingInvoiceId);
		this.invoiceHyperlink = new Hyperlink(String.valueOf(invoiceNumber));
		this.totalBill = new SimpleStringProperty(totalBill);
		this.totalPaid = new SimpleStringProperty(totalPaid);
		this.totalBalance = new SimpleStringProperty(totalBalance);
		this.invoiceDate = new SimpleStringProperty(invoiceDate);
		this.billedBy = new SimpleStringProperty(billedBy);
		this.remarks = new SimpleStringProperty(remarks);
	}

	/**
	 * This Constructor is for UnpaidBillOverall Screen.
	 * 
	 * Instantiates a new UI billing and invoice.
	 * 
	 * @param srNo
	 *            the sr no
	 * @param invoiceNumber
	 *            the invoice number
	 * @param totalBill
	 *            the total bill
	 * @param totalPaid
	 *            the total paid
	 * @param totalBalance
	 *            the total balance
	 * @param invoiceDate
	 *            the invoice date
	 * @param billedBy
	 *            the billed by
	 * @param coupleId
	 *            the couple id
	 */
	public UIBillingAndInvoice(Long srNo, Long invoiceNumber, String totalBill, String totalPaid, String totalBalance,
			 String billedBy, String invoiceDate, String womanDetails) {
		this.srNo = new SimpleLongProperty(srNo);
		this.invoiceHyperlink = new Hyperlink(String.valueOf(invoiceNumber));
		this.totalBill = new SimpleStringProperty(totalBill);
		this.totalPaid = new SimpleStringProperty(totalPaid);
		this.totalBalance = new SimpleStringProperty(totalBalance);
		this.billedBy = new SimpleStringProperty(billedBy);
		this.invoiceDate = new SimpleStringProperty(invoiceDate);
		this.womanDetails = new SimpleStringProperty(womanDetails);
	}

	public final LongProperty invoiceNumberProperty() {
		return this.invoiceNumber;
	}

	public final long getInvoiceNumber() {
		return this.invoiceNumberProperty().get();
	}

	public final void setInvoiceNumber(final long invoiceNumber) {
		this.invoiceNumberProperty().set(invoiceNumber);
	}

	public final StringProperty totalBillProperty() {
		return this.totalBill;
	}

	public final String getTotalBill() {
		return this.totalBillProperty().get();
	}

	public final void setTotalBill(final String totalBill) {
		this.totalBillProperty().set(totalBill);
	}

	public final StringProperty totalPaidProperty() {
		return this.totalPaid;
	}

	public final String getTotalPaid() {
		return this.totalPaidProperty().get();
	}

	public final void setTotalPaid(final String totalPaid) {
		this.totalPaidProperty().set(totalPaid);
	}

	public final StringProperty totalBalanceProperty() {
		return this.totalBalance;
	}

	public final String getTotalBalance() {
		return this.totalBalanceProperty().get();
	}

	public final void setTotalBalance(final String totalBalance) {
		this.totalBalanceProperty().set(totalBalance);
	}

	public final StringProperty invoiceDateProperty() {
		return this.invoiceDate;
	}

	public final String getInvoiceDate() {
		return this.invoiceDateProperty().get();
	}

	public final void setInvoiceDate(final String invoiceDate) {
		this.invoiceDateProperty().set(invoiceDate);
	}

	public final StringProperty billedByProperty() {
		return this.billedBy;
	}

	public final String getBilledBy() {
		return this.billedByProperty().get();
	}

	public final void setBilledBy(final String billedBy) {
		this.billedByProperty().set(billedBy);
	}

	public Hyperlink getInvoiceHyperlink() {
		return invoiceHyperlink;
	}

	public void setInvoiceHyperlink(Hyperlink invoiceHyperlink) {
		this.invoiceHyperlink = invoiceHyperlink;
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

	public final StringProperty womanDetailsProperty() {
		return this.womanDetails;
	}

	public final String getWomanDetails() {
		return this.womanDetailsProperty().get();
	}

	public final void setWomanDetails(final String womanDetails) {
		this.womanDetailsProperty().set(womanDetails);
	}

	public final LongProperty billingInvoiceIdProperty() {
		return this.billingInvoiceId;
	}

	public final long getBillingInvoiceId() {
		return this.billingInvoiceIdProperty().get();
	}

	public final void setBillingInvoiceId(final long billingInvoiceId) {
		this.billingInvoiceIdProperty().set(billingInvoiceId);
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
	
}
