package org.cf.card.ui.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.cf.card.model.Treatment;

import java.text.SimpleDateFormat;

/**
 * Created by Dell on 5/11/2015.
 */
public class UITreatment {

    private LongProperty id;
    private StringProperty name;
    private StringProperty startDate;
    private StringProperty codes;

    public UITreatment(Treatment treatment){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.id = new SimpleLongProperty(treatment.getId());
        this.name = new SimpleStringProperty(treatment.getCodes().getClient().getFirstName());
        this.startDate = new SimpleStringProperty(new SimpleDateFormat("EEE dd/MM/yyyy").format(treatment.getStartDate()));
        this.codes = new SimpleStringProperty(treatment.getCodes().getCode());
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public StringProperty codesProperty() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes.set(codes);
    }
}
