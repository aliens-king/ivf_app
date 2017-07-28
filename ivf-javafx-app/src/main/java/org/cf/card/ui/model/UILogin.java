package org.cf.card.ui.model;

import javafx.beans.property.*;
import org.cf.card.model.Client;
import org.cf.card.model.User;
import org.cf.card.util.EnumPermission.Role;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dell on 5/7/2015.
 */
public class UILogin {
    private LongProperty id;
    private StringProperty surname;
    private StringProperty firstName;
    private StringProperty homeAdress;
    private StringProperty country;
    private StringProperty email;
    private StringProperty password;
    private StringProperty type;

    public UILogin(User login) {

        this.id = new SimpleLongProperty(login.getId());
        this.surname = new SimpleStringProperty(login.getSurname());
        this.firstName = new SimpleStringProperty(login.getFirstName());
        this.homeAdress = new SimpleStringProperty(login.getAddress());
        this.country = new SimpleStringProperty(login.getCountry());
        this.email = new SimpleStringProperty(login.getEmail());
        this.password = new SimpleStringProperty(login.getLoginCode());
        this.type = new SimpleStringProperty(Role.getRoleByKey(login.getRoleId()).getValue());
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


    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
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

    public String getHomeAdress() {
        return homeAdress.get();
    }

    public StringProperty homeAdressProperty() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress.set(homeAdress);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
