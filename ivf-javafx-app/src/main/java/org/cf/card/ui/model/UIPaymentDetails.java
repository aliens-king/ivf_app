package org.cf.card.ui.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIPaymentDetails {

	private LongProperty srNo;
	private LongProperty paymentId;
	private StringProperty paymentDate;
	private StringProperty totalBill;
	private StringProperty paid;
	private StringProperty balance;
	private StringProperty paymentMethod;
	private StringProperty billedBy;
	private StringProperty remarks;

	public UIPaymentDetails(Long srNo, Long paymentId, String paymentDate, String totalBill, String paid, String balance, String paymentMethod,
			String billedBy, String remarks) {
		this.srNo = new SimpleLongProperty(srNo);
		this.paymentId = new SimpleLongProperty(paymentId);
		this.paymentDate = new SimpleStringProperty(paymentDate);
		this.totalBill = new SimpleStringProperty(totalBill);
		this.paid = new SimpleStringProperty(paid);
		this.balance = new SimpleStringProperty(balance);
		this.paymentMethod = new SimpleStringProperty(paymentMethod);
		this.billedBy = new SimpleStringProperty(billedBy);
		this.remarks = new SimpleStringProperty(remarks);
	}

	
	public final StringProperty paymentDateProperty() {
		return this.paymentDate;
	}

	public final String getPaymentDate() {
		return this.paymentDateProperty().get();
	}

	public final void setPaymentDate(final String paymentDate) {
		this.paymentDateProperty().set(paymentDate);
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

	public final StringProperty paidProperty() {
		return this.paid;
	}

	public final String getPaid() {
		return this.paidProperty().get();
	}

	public final void setPaid(final String paid) {
		this.paidProperty().set(paid);
	}

	public final StringProperty balanceProperty() {
		return this.balance;
	}

	public final String getBalance() {
		return this.balanceProperty().get();
	}

	public final void setBalance(final String balance) {
		this.balanceProperty().set(balance);
	}

	public final StringProperty paymentMethodProperty() {
		return this.paymentMethod;
	}

	public final String getPaymentMethod() {
		return this.paymentMethodProperty().get();
	}

	public final void setPaymentMethod(final String paymentMethod) {
		this.paymentMethodProperty().set(paymentMethod);
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


	public final LongProperty paymentIdProperty() {
		return this.paymentId;
	}
	


	public final long getPaymentId() {
		return this.paymentIdProperty().get();
	}
	


	public final void setPaymentId(final long paymentId) {
		this.paymentIdProperty().set(paymentId);
	}
	
	
	
	
	

}
