package org.cf.card.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UICryoActionSemen {

	private LongProperty id;
	private IntegerProperty srNo;
	private StringProperty date;
	
	
	
	public UICryoActionSemen(Long id, int srNo, String date) {
		this.id=  new SimpleLongProperty(id);
		this.srNo = new SimpleIntegerProperty(srNo);
		this.date = new SimpleStringProperty(date);
	}

	public final LongProperty idProperty(){
		return this.id;
	}
	public final java.lang.Long getId() {
		return this.idProperty().get();
	}

	public void setId(final java.lang.Long id) {
		 this.idProperty().set(id);
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
	
	public final StringProperty dateProperty() {
		return this.date;
	}
	
	public final java.lang.String getDate() {
		return this.dateProperty().get();
	}
	
	public final void setDate(final java.lang.String date) {
		this.dateProperty().set(date);
	}
	
	
	
	
	
}
