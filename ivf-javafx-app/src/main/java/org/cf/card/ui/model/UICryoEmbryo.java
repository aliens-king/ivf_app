package org.cf.card.ui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UICryoEmbryo {

	private LongProperty id;
	private IntegerProperty srNo;
	private StringProperty quality;
	private StringProperty date;
	private BooleanProperty action;

	public UICryoEmbryo(Long id,int srNo, String quality, String date, boolean action) {
		this.id=  new SimpleLongProperty(id);
		this.srNo = new SimpleIntegerProperty(srNo);
		this.quality = new SimpleStringProperty(quality);
		this.date = new SimpleStringProperty(date);
		this.action = new SimpleBooleanProperty(action);
	}
	public UICryoEmbryo(Long id,int srNo, boolean action, String date) {
		this.id=  new SimpleLongProperty(id);
		this.srNo = new SimpleIntegerProperty(srNo);
		this.action = new SimpleBooleanProperty(action);
		this.date = new SimpleStringProperty(date);
	}

	public UICryoEmbryo(int srNo, String quality){
		this.srNo = new SimpleIntegerProperty(srNo);
		this.quality = new SimpleStringProperty(quality);
	}

	public final LongProperty idProperty(){
		return this.id;
	}

	public final IntegerProperty srNoProperty(){
		return this.srNo;
	}
	public final StringProperty qualityProperty(){
		return this.quality;
	}
	public final StringProperty dateProperty(){
		return this.date;
	}
	public final BooleanProperty actionProperty(){
		return this.action;
	}

	public final java.lang.Long getId() {
		return this.idProperty().get();
	}

	public void setId(final java.lang.Long id) {
		 this.idProperty().set(id);
	}
	public final java.lang.Integer getSrNo() {
		return this.srNoProperty().get();
	}

	public void setSrNo(final java.lang.Integer srNo) {
		 this.srNoProperty().set(srNo);
	}

	public final java.lang.String getQuality() {
		return this.qualityProperty().get();
	}

	public void setQuality(final java.lang.String quality) {
		this.qualityProperty().set(quality);
	}

	public java.lang.String getDate() {
		return this.dateProperty().get();
	}

	public void setDate(final java.lang.String date) {
		this.dateProperty().set(date);;
	}
	public java.lang.Boolean getAction() {
		return this.actionProperty().getValue();
	}
	public void setAction(java.lang.Boolean action) {
		this.actionProperty().set(action);
	}
	
	

	
	
}
