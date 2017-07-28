package org.cf.card.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class UICryoEmbryoEgg {

	private IntegerProperty total;
	private IntegerProperty available;
	private IntegerProperty used;

	private IntegerProperty srNo;
	private StringProperty button;
	public UICryoEmbryoEgg(int totalEgg, int available, int used) {
		super();
		this.total = new SimpleIntegerProperty(totalEgg);
		this.available = new SimpleIntegerProperty(available);
		this.used = new SimpleIntegerProperty(used);
	}
	
	public final IntegerProperty totalProperty(){
		return this.total;
	}
	public final IntegerProperty availableProperty(){
		return this.available;
	}
	public final IntegerProperty usedProperty(){
		return this.used;
	}

	
	public final java.lang.Integer getTotal() {
		return this.totalProperty().get();
	}

	public void setTotal(final java.lang.Integer total) {
		this.totalProperty().set(total);
	}

	public final java.lang.Integer getAvailable() {
		return this.availableProperty().get();
	}

	public void setAvailable(final java.lang.Integer available) {
		this.availableProperty().set(available);
	}

	public java.lang.Integer getUsed() {
		return this.usedProperty().get();
	}

	public void setUsed(final java.lang.Integer used) {
		this.usedProperty().set(used);
	}
	
	
}
