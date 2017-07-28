package org.cf.card.ui.model;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;

/**
 * Created by Dell on 12/24/2014.
 */
public class UIPrintClient {

    private Client mainClient;
    private Client partner;
    private int AllIVFCheck = 0;
    private int AllLabCheck = 0;
    private int fileIVF = 0;
    private int semenPot = 0;
    private int bracelet = 0;
    private int tubesIVF = 0;
    private int vials = 0;
    private int iui = 0;
    private int opu = 0;
    private int denutation = 0;
    private int cIVF = 0;
    private int iCSI = 0;
    private int fM = 0;
    private int cM = 0;
    private int bM = 0;
    private int biopsy = 0;
    private int strawS = 0;
    private int strawE = 0;
    private int fileLAB = 0;
    private int pot = 0;
    private int straw = 0;
    private int tubesLAB = 0;
    private int specialPot = 0;
    private Codes mainClientTreatmentCode;
    private Codes partnerTreatmentCode;

    public UIPrintClient(Client mainClient, Client partner) {
        this.mainClient = mainClient;
        this.partner = partner;
    }

    public UIPrintClient(){

    }

    public void setMainClient(Client mainClient) {
        this.mainClient = mainClient;
    }

    public void setPartner(Client partner) {
        this.partner = partner;
    }

    public Client getMainClient() {
        return mainClient;
    }

    public Client getPartner() {
        return partner;
    }

    public int getAllIVFCheck() {
        return AllIVFCheck;
    }

    public void setAllIVFCheck(int allIVFCheck) {
        AllIVFCheck = allIVFCheck;
        fileIVF = allIVFCheck;
        semenPot = allIVFCheck;
        bracelet = allIVFCheck;
        tubesIVF = allIVFCheck;
        vials = allIVFCheck;
        iui = allIVFCheck;
        opu = allIVFCheck;
        denutation = allIVFCheck;
        cIVF =  allIVFCheck;
        iCSI = allIVFCheck;
        fM = allIVFCheck;
        cM = allIVFCheck;
        bM = allIVFCheck;
        biopsy = allIVFCheck;
        strawS = allIVFCheck;
        strawE = allIVFCheck;

    }

    public int getAllLabCheck() {
        return AllLabCheck;
    }

    public void setAllLabCheck(int allLabCheck) {
        AllLabCheck = allLabCheck;
        fileLAB = allLabCheck;
        pot = allLabCheck;
        straw = allLabCheck;
        tubesLAB = allLabCheck;
        specialPot = allLabCheck;
    }

    public int getFileIVF() {
        return fileIVF;
    }

    public void setFileIVF(int fileIVF) {
        this.fileIVF = fileIVF;
    }

    public int getSemenPot() {
        return semenPot;
    }

    public void setSemenPot(int semenPot) {
        this.semenPot = semenPot;
    }

    public int getBracelet() {
        return bracelet;
    }

    public void setBracelet(int bracelet) {
        this.bracelet = bracelet;
    }

    public int getTubesIVF() {
        return tubesIVF;
    }

    public void setTubesIVF(int tubesIVF) {
        this.tubesIVF = tubesIVF;
    }

    public int getVials() {
        return vials;
    }

    public void setVials(int vials) {
        this.vials = vials;
    }

    public int getIui() {
        return iui;
    }

    public void setIui(int iui) {
        this.iui = iui;
    }

    public int getOpu() {
        return opu;
    }

    public void setOpu(int opu) {
        this.opu = opu;
    }

    public int getDenutation() {
        return denutation;
    }

    public void setDenutation(int denutation) {
        this.denutation = denutation;
    }

    public int getcIVF() {
        return cIVF;
    }

    public void setcIVF(int cIVF) {
        this.cIVF = cIVF;
    }

    public int getiCSI() {
        return iCSI;
    }

    public void setiCSI(int iCSI) {
        this.iCSI = iCSI;
    }

    public int getfM() {
        return fM;
    }

    public void setfM(int fM) {
        this.fM = fM;
    }

    public int getcM() {
        return cM;
    }

    public void setcM(int cM) {
        this.cM = cM;
    }

    public int getbM() {
        return bM;
    }

    public void setbM(int bM) {
        this.bM = bM;
    }

    public int getBiopsy() {
        return biopsy;
    }

    public void setBiopsy(int biopsy) {
        this.biopsy = biopsy;
    }

    public int getStrawS() {
        return strawS;
    }

    public void setStrawS(int strawS) {
        this.strawS = strawS;
    }

    public int getStrawE() {
        return strawE;
    }

    public void setStrawE(int strawE) {
        this.strawE = strawE;
    }

    public int getFileLAB() {
        return fileLAB;
    }

    public void setFileLAB(int fileLAB) {
        this.fileLAB = fileLAB;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getStraw() {
        return straw;
    }

    public void setStraw(int straw) {
        this.straw = straw;
    }

    public int getTubesLAB() {
        return tubesLAB;
    }

    public void setTubesLAB(int tubesLAB) {
        this.tubesLAB = tubesLAB;
    }

    public int getSpecialPot() {
        return specialPot;
    }

    public void setSpecialPot(int specialPot) {
        this.specialPot = specialPot;
    }

    public Codes getMainClientTreatmentCode() {
        return mainClientTreatmentCode;
    }

    public void setMainClientTreatmentCode(Codes mainClientTreatmentCode) {
        this.mainClientTreatmentCode = mainClientTreatmentCode;
    }

    public Codes getPartnerTreatmentCode() {
        return partnerTreatmentCode;
    }

    public void setPartnerTreatmentCode(Codes partnerTreatmentCode) {
        this.partnerTreatmentCode = partnerTreatmentCode;
    }
}
