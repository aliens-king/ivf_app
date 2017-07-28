package org.cf.card.ui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class UIProcedure.
 *
 * @author Ankit Sood Dec 29, 2016
 */
public class UIProcedure {

	private LongProperty id;
	private StringProperty procedureName;
	private StringProperty procedurePrice;
	private StringProperty procedureCreateDate;
	private BooleanProperty check;
	private StringProperty createdBy;
	private IntegerProperty selectBox;

	/**
	 * This Constructor is use for BillingSetup UI table
	 *
	 * @param id
	 *            the id
	 * @param procedureName
	 *            the procedure name
	 * @param procedurePrice
	 *            the procedure price
	 * @param procedureCreateDate
	 *            the procedure create date
	 * @param createdBy
	 *            the created by
	 */
	public UIProcedure(Long id, String procedureName, String procedurePrice, String procedureCreateDate,
			String createdBy) {
		this.check = new SimpleBooleanProperty(false);
		this.id = new SimpleLongProperty(id);
		this.procedureName = new SimpleStringProperty(procedureName);
		this.procedurePrice = new SimpleStringProperty(procedurePrice);
		this.procedureCreateDate = new SimpleStringProperty(procedureCreateDate);
		this.createdBy = new SimpleStringProperty(createdBy);
	}

	/**
	 * This Constructor is use for AddProcedurePopup UI table
	 *
	 * @param id
	 *            the id
	 * @param procedureName
	 *            the procedure name
	 * @param procedurePrice
	 *            the procedure price
	 * @param selectBox
	 *            the select box
	 */
	public UIProcedure(Long id, String procedureName, String procedurePrice, Integer selectBox) {
		this.id = new SimpleLongProperty(id);
		this.check = new SimpleBooleanProperty(false);
		this.procedureName = new SimpleStringProperty(procedureName);
		this.procedurePrice = new SimpleStringProperty(procedurePrice);
		Integer checkId = id != null ? id.intValue() : null;
		this.selectBox = new SimpleIntegerProperty(checkId);
	}

	/**
	 * This Constructor is use for BillingAndInvoice UI and RefundAndInvoice table
	 * Instantiates a new UI procedure.
	 *
	 * @param id
	 *            the id
	 * @param procedureName
	 *            the procedure name
	 * @param procedurePrice
	 *            the procedure price
	 */
	public UIProcedure(Long id, String procedureName, String procedurePrice) {
		this.id = new SimpleLongProperty(id);
		this.check = new SimpleBooleanProperty(false);
		this.procedureName = new SimpleStringProperty(procedureName);
		this.procedurePrice = new SimpleStringProperty(procedurePrice);
		Integer checkId = id != null ? id.intValue() : null;
		this.selectBox = new SimpleIntegerProperty(checkId);
	}

	public LongProperty getId() {
		return id;
	}

	public boolean getCheck() {
		return check.get();
	}

	public BooleanProperty checkProperty() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check.set(check);
	}

	public void setId(LongProperty id) {
		this.id = id;
	}

	public IntegerProperty getSelectBox() {
		return selectBox;
	}

	public void setSelectBox(IntegerProperty selectBox) {
		this.selectBox = selectBox;
	}

	public StringProperty getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(StringProperty procedureName) {
		this.procedureName = procedureName;
	}

	public StringProperty getProcedurePrice() {
		return procedurePrice;
	}

	public void setProcedurePrice(StringProperty procedurePrice) {
		this.procedurePrice = procedurePrice;
	}

	public StringProperty getProcedureCreateDate() {
		return procedureCreateDate;
	}

	public void setProcedureCreateDate(StringProperty procedureCreateDate) {
		this.procedureCreateDate = procedureCreateDate;
	}

	public StringProperty getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(StringProperty createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "UIProcedure [id=" + id + ", procedureName=" + procedureName + ", procedurePrice=" + procedurePrice
				+ ", procedureCreateDate=" + procedureCreateDate + ", createdBy=" + createdBy + "]";
	}

}
