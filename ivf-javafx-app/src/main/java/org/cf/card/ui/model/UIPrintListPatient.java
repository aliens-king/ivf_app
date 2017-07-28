package org.cf.card.ui.model;

import javafx.scene.control.Spinner;
import org.cf.card.model.Treatment;

/**
 * Created by Dell on 1/4/2015.
 */
public class UIPrintListPatient {
    private Treatment womanTreatment;
    private Treatment partnerTreatment;
    private boolean file;
    private boolean biopsy;
    private boolean iui;
    private boolean straws;
    private boolean strawe;
    private boolean bm;
    private boolean semenPot;
    private boolean checkbox;
    private Spinner spinner;


    public UIPrintListPatient(Treatment womanTreatment, Treatment partnerTreatment) {

        this.womanTreatment = womanTreatment;
        this.partnerTreatment = partnerTreatment;
    }

    public Treatment getWomanTreatment() {
        return womanTreatment;
    }

    public void setWomanTreatment(Treatment womanTreatment) {
        this.womanTreatment = womanTreatment;
    }

    public Treatment getPartnerTreatment() {
        return partnerTreatment;
    }

    public void setPartnerTreatment(Treatment partnerTreatment) {
        this.partnerTreatment = partnerTreatment;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }

    public boolean isBiopsy() {
        return biopsy;
    }

    public void setBiopsy(boolean biopsy) {
        this.biopsy = biopsy;
    }

    public boolean isIui() {
        return iui;
    }

    public void setIui(boolean iui) {
        this.iui = iui;
    }

    public boolean isStraws() {
        return straws;
    }

    public void setStraws(boolean straws) {
        this.straws = straws;
    }

    public boolean isStrawe() {
        return strawe;
    }

    public void setStrawe(boolean strawe) {
        this.strawe = strawe;
    }

    public boolean isBm() {
        return bm;
    }

    public void setBm(boolean bm) {
        this.bm = bm;
    }

    public boolean isSemenPot() {
        return semenPot;
    }

    public void setSemenPot(boolean semenPot) {
        this.semenPot = semenPot;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    @Override
    public String toString() {
        return "UIPrintListPatient{" +
                "womanTreatment=" + womanTreatment +
                ", partnerTreatment=" + partnerTreatment +
                ", file=" + file +
                ", biopsy=" + biopsy +
                ", iui=" + iui +
                ", straws=" + straws +
                ", strawe=" + strawe +
                ", bm=" + bm +
                ", semenPot=" + semenPot +
                ", checkbox=" + checkbox +
                ", spinner=" + spinner +
                '}';
    }
}
