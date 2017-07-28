package org.cf.card.ui.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.cf.card.model.Client;
import org.cf.card.util.Util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Dell on 10/24/2014.
 */
public class UIClient implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private LongProperty id;
    private StringProperty surname;
    private StringProperty middleName;
    private StringProperty firstName;
    private StringProperty dOB;
    private StringProperty gender;
    private StringProperty couple;
    private BooleanProperty check;
    private StringProperty age;
    private Client client;
    private StringProperty homeAddress;
    private StringProperty phoneNumber;
    private StringProperty email;
    private IntegerProperty appointment;

    public UIClient(Client client) {

	this.client = client;
	this.id = new SimpleLongProperty(client.getId());
	this.surname = new SimpleStringProperty(client.getSurname());
	this.middleName = new SimpleStringProperty(client.getMiddleName());
	this.firstName = new SimpleStringProperty(client.getFirstName());
	this.dOB = new SimpleStringProperty(new SimpleDateFormat("EEE dd/MM/yyyy").format(client.getdOB()));
	this.gender = new SimpleStringProperty(client.getGender());
	if (client.equals(client.getCouple().getWoman()))

	    this.couple = new SimpleStringProperty(client.getCouple().getMan().getSurname());

	else

	    this.couple = new SimpleStringProperty(client.getCouple().getWoman().getSurname());

	// try {
	age = new SimpleStringProperty(Util.getAge(client.getdOB()));
	// } catch (NullPointerException e) {
	// age = new SimpleStringProperty("");
	// }

	this.check = new SimpleBooleanProperty(false);
	this.homeAddress = new SimpleStringProperty(client.getHomeAddress());
	this.phoneNumber = new SimpleStringProperty(client.getPhoneNumber());
	this.email = new SimpleStringProperty(client.getEmail());
	Integer id = client.getId() != null ? client.getId().intValue() : null;
	this.appointment = new SimpleIntegerProperty(id);

    }

    public Client getClient() {
	return client;
    }

    public void setClient(Client client) {
	this.client = client;
    }

    public long getId() {
	return id.get();
    }

    public LongProperty idProperty() {
	return id;
    }

    public void setId(long id) {
	this.id.set(id);
    }

    public String SurnameProperty() {
	return surname.get();
    }

    public StringProperty surnameProperty() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname.set(surname);
    }

    public String getMiddleName() {
	return middleName.get();
    }

    public StringProperty middleNameProperty() {
	return middleName;
    }

    public void setMiddleName(String middleName) {
	this.middleName.set(middleName);
    }

    public String getFirstName() {
	return firstName.get();
    }

    public StringProperty firstNameProperty() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName.set(firstName);
    }

    public String getdOB() {
	return dOB.get();
    }

    public StringProperty dOBProperty() {
	return dOB;
    }

    public void setdOB(String dOB) {
	this.dOB.set(dOB);
    }

    public String getGender() {
	return gender.get();
    }

    public StringProperty genderProperty() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender.set(gender);
    }

    public String getCouple() {
	return couple.get();
    }

    public StringProperty coupleProperty() {
	return couple;
    }

    public void setCouple(String couple) {
	this.couple.set(couple);
    }

    public void setCheck(boolean check) {
	this.check.set(check);
    }

    public boolean getCheck() {
	return check.get();
    }

    public BooleanProperty checkProperty() {
	return check;
    }

    public StringProperty ageProperty() {
	return age;
    }

    public void setAge(String age) {
	this.age.set(age);
    }

    public String getAge() {
	return age.getValue();
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("UIClient [id=");
	builder.append(id);
	builder.append(", surname=");
	builder.append(surname);
	builder.append(", middleName=");
	builder.append(middleName);
	builder.append(", firstName=");
	builder.append(firstName);
	builder.append(", dOB=");
	builder.append(dOB);
	builder.append(", gender=");
	builder.append(gender);
	builder.append(", couple=");
	builder.append(couple);
	builder.append(", check=");
	builder.append(check);
	builder.append(", age=");
	builder.append(age);
	builder.append(", client=");
	builder.append(client);
	builder.append(", homeAddress=");
	builder.append(homeAddress);
	builder.append(", phoneNumber=");
	builder.append(phoneNumber);
	builder.append(", email=");
	builder.append(email);
	builder.append("]");
	return builder.toString();
    }

    public final IntegerProperty appointmentProperty() {
	return this.appointment;
    }

    public final int getAppointment() {
	return this.appointmentProperty().get();
    }

    public final void setAppointment(final int appointment) {
	this.appointmentProperty().set(appointment);
    }

}
