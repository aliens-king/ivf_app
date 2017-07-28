package org.cf.card.ui.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nurse{
	
	private LongProperty id;
	private StringProperty name;
	
	public Nurse(Long id, String name) {
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
	}
	public StringProperty nameProperty(){
		return name;
	}
	public LongProperty idProperty(){
		return id;
	}
	
	public Long getId() {
		return id.get();
	}

	public void setId(Long id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	
	/*private  StringProperty firstName;
	private  StringProperty lastName;
	public Nurse(String firstName, String lastName) {
	this.firstName = new SimpleStringProperty(firstName);
	this.lastName = new SimpleStringProperty(lastName);
	}
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}
	
	@Override
	public String toString() {
		return getFirstName() + " " + getLastName();
	}*/
	
	
	
	
	
}
