package org.cf.card.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UICryoSemen {
	
	private StringProperty createdDate;
	
	private LongProperty semenCodeId;

	private StringProperty cycle;
	
	private IntegerProperty srNo;
	
	private StringProperty dateOfCryo;
	
	private StringProperty dateOfUse;
	
	private StringProperty dmm;
	
	private StringProperty remarks;


	public UICryoSemen(String cycle, Integer srNo, String dateOfCryo,
			String dateOfUse, String createdDate,String dmm, String remarks, Long semenId) {
		super();
		this.cycle = new SimpleStringProperty(cycle);
		this.srNo = new SimpleIntegerProperty(srNo);
		this.dateOfCryo = new SimpleStringProperty(dateOfCryo);
		this.dateOfUse = new SimpleStringProperty(dateOfUse);
		this.createdDate = new SimpleStringProperty(createdDate);
		this.dmm = new SimpleStringProperty(dmm);
		this.remarks = new SimpleStringProperty(remarks);
		this.semenCodeId = new SimpleLongProperty(semenId);
}
	
	public final StringProperty cycleProperty() {
		return this.cycle;
	}
	
	public final java.lang.String getCycle() {
		return this.cycleProperty().get();
	}
	

	public final void setCycle(final java.lang.String cycle) {
		this.cycleProperty().set(cycle);
	}
	

	public final IntegerProperty srNoProperty() {
		return this.srNo;
	}
	

	public final int getSrNo() {
		return this.srNoProperty().get();
	}
	

	public final void setSrNo(final int srNo) {
		this.srNoProperty().set(srNo);
	}
	

	public final StringProperty dateOfCryoProperty() {
		return this.dateOfCryo;
	}
	

	public final java.lang.String getDateOfCryo() {
		return this.dateOfCryoProperty().get();
	}
	

	public final void setDateOfCryo(final java.lang.String dateOfCryo) {
		this.dateOfCryoProperty().set(dateOfCryo);
	}
	

	public final StringProperty dateOfUseProperty() {
		return this.dateOfUse;
	}
	

	public final java.lang.String getDateOfUse() {
		return this.dateOfUseProperty().get();
	}
	

	public final void setDateOfUse(final java.lang.String dateOfUse) {
		this.dateOfUseProperty().set(dateOfUse);
	}

	

	public final StringProperty dmmProperty() {
		return this.dmm;
	}
	

	public final java.lang.String getDmm() {
		return this.dmmProperty().get();
	}
	

	public final void setDmm(final java.lang.String dmm) {
		this.dmmProperty().set(dmm);
	}



	public final LongProperty semenIdProperty() {
		return this.semenCodeId;
	}
	
	public final void setSemenId(final long semenId) {
		this.semenIdProperty().set(semenId);
	}

	public final LongProperty semenCodeIdProperty() {
		return this.semenCodeId;
	}
	

	public final long getSemenCodeId() {
		return this.semenCodeIdProperty().get();
	}
	

	public final void setSemenCodeId(final long semenCodeId) {
		this.semenCodeIdProperty().set(semenCodeId);
	}

	public final StringProperty remarksProperty() {
		return this.remarks;
	}
	

	public final java.lang.String getRemarks() {
		return this.remarksProperty().get();
	}
	

	public final void setRemarks(final java.lang.String remarks) {
		this.remarksProperty().set(remarks);
	}

	public final StringProperty createdDateProperty() {
		return this.createdDate;
	}
	

	public final java.lang.String getCreatedDate() {
		return this.createdDateProperty().get();
	}
	

	public final void setCreatedDate(final java.lang.String createdDate) {
		this.createdDateProperty().set(createdDate);
	}
		
}
