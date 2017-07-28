package org.cf.card.ui.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UICycles {
	
	private IntegerProperty srNo;
	private LongProperty womanCodeId;
	private LongProperty remarkId;
	private StringProperty womanCode;
	private LongProperty partnerCodeId;
	private StringProperty partnerCode;
	private StringProperty date;
	private IntegerProperty eggCollection;
	private StringProperty quality;
//	private StringProperty semenPrep;
	private StringProperty clinical;
	private StringProperty bioChemical;
	private LongProperty evaluation;
//	private StringProperty cycles;
	private StringProperty remarks;
	private StringProperty endDate;



	public UICycles(int srNo, Long womanCodeId, String womanCode, Long partnerCodeId, String partnerCode, String date,String endDate,  int eggCollection, String quality, 
			String bioChemical, long evaluation, String remarks, long remarkId){
		this.srNo = new SimpleIntegerProperty(srNo);
		this.womanCodeId = new SimpleLongProperty(womanCodeId);
		this.womanCode = new SimpleStringProperty(womanCode);
		this.partnerCodeId = new SimpleLongProperty(partnerCodeId);
		this.partnerCode = new SimpleStringProperty(partnerCode);
		this.date = new SimpleStringProperty(date);
		this.endDate = new SimpleStringProperty(endDate);
		this.eggCollection = new SimpleIntegerProperty(eggCollection);
		this.quality = new SimpleStringProperty(quality);
		this.bioChemical = new SimpleStringProperty(bioChemical);
		this.evaluation = new SimpleLongProperty(evaluation);
		this.remarks = new SimpleStringProperty(remarks);
		this.remarkId =  new SimpleLongProperty(remarkId);
	}
	
	
	
	
	public final IntegerProperty srNoProperty(){
		return this.srNo;
	}

	public final int getSrNo(){
		return this.srNoProperty().get();
	}

	public void setSrNo(Integer srNo){
		this.srNoProperty().set(srNo);
	}
	
		
		/*public final ObjectProperty<Codes> womanCodeObjProperty(){
			return this.womanCodeObj;
		}
		
		public final Object getWomanCodeObj(){
			return this.womanCodeObjProperty().get();
		}
		
		public void setWomanCodeObj(Codes womanCodeObj){
			this.womanCodeObjProperty().set(womanCodeObj);
		}	
		*/

	
	public final LongProperty womanCodeIdProperty(){
		return this.womanCodeId;
	}
	
	public final Long getWomanCodeId(){
		return this.womanCodeIdProperty().get();
	}
	
	public void setWomanCodeId(Long womanCodeId){
		this.womanCodeIdProperty().set(womanCodeId);
	}

	public final StringProperty dateProperty(){
		return this.date;
	}

	public final String getDate(){
		return this.dateProperty().get();
	}

	public void setDate(String date){
		this.dateProperty().set(date);
	}

	public final IntegerProperty eggCollectionProperty(){
		return this.eggCollection;
	}

	public final int getEggCollection(){
		return this.eggCollectionProperty().get();
	}

	public void setEggCollection(Integer eggCollection){
		this.eggCollectionProperty().set(eggCollection);
	}

	public final StringProperty clinicalProperty(){
		return this.clinical;
	}

	public final String getclinical(){
		return this.clinicalProperty().get();
	}

	public void setClinical(String clinical){
		this.clinicalProperty().set(clinical);
	}

	public final LongProperty evaluationProperty(){
		return this.evaluation;
	}

	public final Long getEvaluation(){
		return this.evaluationProperty().get();
	}

	public void setEvaluation(Long evaluation){
		this.evaluationProperty().set(evaluation);
	}
	
	/*public final StringProperty cyclesProperty(){
		return this.cycles;
	}
	
	public final String getCycles(){
		return this.cyclesProperty().get();
	}
	
	public void setCycles(String cycles){
		this.cyclesProperty().set(cycles);
	}*/


	public final StringProperty womanCodeProperty() {
		return this.womanCode;
	}
	
	public final java.lang.String getWomanCode() {
		return this.womanCodeProperty().get();
	}
	
	public final void setWomanCode(final java.lang.String womanCode) {
		this.womanCodeProperty().set(womanCode);
	}
	
	public final StringProperty partnerCodeProperty() {
		return this.partnerCode;
	}
	
	public final java.lang.String getPartnerCode() {
		return this.partnerCodeProperty().get();
	}
	
	public final void setPartnerCode(final java.lang.String partnerCode) {
		this.partnerCodeProperty().set(partnerCode);
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
	
	public final LongProperty partnerCodeIdProperty() {
		return this.partnerCodeId;
	}

	public final Long getPartnerCodeId() {
		return this.partnerCodeIdProperty().get();
	}

	public final void setPartnerCodeId(Long partnerCodeId) {
		this.partnerCodeIdProperty().set(partnerCodeId);
	}

	public final StringProperty remarksProperty() {
		return this.remarks;
	}
	
	public final String getRemarks() {
		return this.remarksProperty().get();
	}
	
	public final void setRemarks(String remarks) {
		this.remarksProperty().set(remarks);
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

	public final StringProperty endDateProperty() {
		return this.endDate;
	}
	
	public final java.lang.String getEndDate() {
		return this.endDateProperty().get();
	}
	
	public final void setEndDate(final java.lang.String endDate) {
		this.endDateProperty().set(endDate);
	}


	public final StringProperty bioChemicalProperty() {
		return this.bioChemical;
	}
	
	public final java.lang.String getBioChemical() {
		return this.bioChemicalProperty().get();
	}
	
	public final void setBioChemical(final java.lang.String bioChemical) {
		this.bioChemicalProperty().set(bioChemical);
	}
	
}
