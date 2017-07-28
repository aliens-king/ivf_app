package org.cf.card.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIInvestigation {

	private IntegerProperty dayIndex;
	private StringProperty value;

	/**
	 * @param value
	 */
	public UIInvestigation(String value,int dayIndex) {
		this.dayIndex = new SimpleIntegerProperty(dayIndex);
		this.value = new SimpleStringProperty(value);
	}

	public final StringProperty valueProperty() {
		return this.value;
	}
	
	public StringProperty getValueProperty(String investigationId){
		System.out.println("dayIndex : "+dayIndex.getValue() +" investigationId : "+investigationId);
	 if(investigationId.equals(String.valueOf(dayIndex.getValue()))){
		
		 return value;
	 }
	 return new SimpleStringProperty("empty");

	}
	
	public final java.lang.String getValue() {
		return this.valueProperty().get();
	}
	public final void setValue(final java.lang.String value) {
		this.valueProperty().set(value);
	}

	public final IntegerProperty dayIndexProperty() {
		return this.dayIndex;
	}
	public final int getDayIndex() {
		return this.dayIndexProperty().get();
	}

	public final void setDayIndex(final int dayIndex) {
		this.dayIndexProperty().set(dayIndex);
	}



}
