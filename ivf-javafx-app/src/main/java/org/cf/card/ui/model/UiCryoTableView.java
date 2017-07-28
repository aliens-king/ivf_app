package org.cf.card.ui.model;

import org.cf.card.model.Couple;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UiCryoTableView {
	
	private LongProperty id;
	private StringProperty cycle;
	private StringProperty srNo;
	private StringProperty quality;
	private StringProperty used;
	//private BooleanProperty action;
	private LongProperty womanCodeId;
	private LongProperty remarkId;
	private StringProperty remarks;
	private StringProperty type;
	private ObjectProperty<UiCryoTableView> uiCryoTableView;
	private ObjectProperty<Couple> couple;

	public UiCryoTableView(Long id, String cycle, String srNo, String quality, String used, String type, String remarks, Long womanCodeId, Long remarkId, Couple couple, UiCryoTableView uiCryoTableView) {
		this.id = new SimpleLongProperty(id);
		this.cycle = new SimpleStringProperty(cycle);
		this.womanCodeId = new SimpleLongProperty(womanCodeId);
		this.remarkId = new SimpleLongProperty(remarkId);
		this.srNo = new SimpleStringProperty(srNo);
		this.couple = new SimpleObjectProperty<Couple>(couple);
		this.quality = new SimpleStringProperty(quality);
		this.used = new SimpleStringProperty(used);
		this.remarks = new SimpleStringProperty(remarks);
		this.type = new SimpleStringProperty(type);
		this.uiCryoTableView = new SimpleObjectProperty<UiCryoTableView>(uiCryoTableView);
	}
	
	public final LongProperty idProperty() {
		return this.id;
	}
	

	public final long getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final long id) {
		this.idProperty().set(id);
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
	

	public final StringProperty srNoProperty() {
		return this.srNo;
	}
	

	public final java.lang.String getSrNo() {
		return this.srNoProperty().get();
	}
	

	public final void setSrNo(final java.lang.String srNo) {
		this.srNoProperty().set(srNo);
	}
	

	public final StringProperty qualityProperty() {
		return this.quality;
	}
	

	public final java.lang.String getQuality() {
		return this.qualityProperty().get();
	}
	

	public final void setQuality(final java.lang.String quality) {
		this.qualityProperty().set(quality);
	}
	

	public final StringProperty usedProperty() {
		return this.used;
	}
	

	public final java.lang.String getUsed() {
		return this.usedProperty().get();
	}
	

	public final void setUsed(final java.lang.String used) {
		this.usedProperty().set(used);
	}
	

	public final StringProperty typeProperty() {
		return this.type;
	}
	

	public final java.lang.String getType() {
		return this.typeProperty().get();
	}
	

	public final void setType(final java.lang.String type) {
		this.typeProperty().set(type);
	}




	public final ObjectProperty<Couple> coupleProperty() {
		return this.couple;
	}
	




	public final org.cf.card.model.Couple getCouple() {
		return this.coupleProperty().get();
	}
	




	public final void setCouple(final org.cf.card.model.Couple couple) {
		this.coupleProperty().set(couple);
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




	public final LongProperty womanCodeIdProperty() {
		return this.womanCodeId;
	}
	




	public final long getWomanCodeId() {
		return this.womanCodeIdProperty().get();
	}
	




	public final void setWomanCodeId(final long womanCodeId) {
		this.womanCodeIdProperty().set(womanCodeId);
	}
	




	public final LongProperty remarkIdProperty() {
		return this.remarkId;
	}
	




	public final long getRemarkId() {
		return this.remarkIdProperty().get();
	}
	




	public final void setRemarkId(final long remarkId) {
		this.remarkIdProperty().set(remarkId);
	}





	public final ObjectProperty<UiCryoTableView> uiCryoTableViewProperty() {
		return this.uiCryoTableView;
	}
	





	public final org.cf.card.ui.model.UiCryoTableView getUiCryoTableView() {
		return this.uiCryoTableViewProperty().get();
	}
	





	public final void setUiCryoTableView(final org.cf.card.ui.model.UiCryoTableView uiCryoTableView) {
		this.uiCryoTableViewProperty().set(uiCryoTableView);
	}
	
	
	
	
	

	
}