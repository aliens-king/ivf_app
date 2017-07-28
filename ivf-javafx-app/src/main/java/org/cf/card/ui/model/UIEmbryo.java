/**
 *
 */
package org.cf.card.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumEmbryo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class UIEmbryo.
 *
 * @author Nikhil Mahajan
 */
public class UIEmbryo {

	/** The index. */
	private StringProperty index;

	/** The injection. */
	private ObjectProperty<EnumEmbryo.Injection> injection;

	/** The ui days. */
	private List<ObjectProperty<UIDay>> uiDays;

	private Long embryoId;

	private Option destiny;

	private Date destinyDate;

	private IntegerProperty srNo;
	private StringProperty grade;
	private StringProperty day;

	public UIEmbryo(int srNo, String grade){
		this.srNo = new SimpleIntegerProperty(srNo);
		this.grade = new SimpleStringProperty(grade);
	}

	public UIEmbryo(String index, EnumEmbryo.Injection injection, List<UIDay> uiDays) {

		this.index = new SimpleStringProperty(index);
		this.injection = new SimpleObjectProperty<EnumEmbryo.Injection>(injection);
		this.uiDays = new ArrayList<ObjectProperty<UIDay>>();
		List<ObjectProperty<UIDay>> days = new ArrayList<>();

		for (UIDay uiDay : uiDays) {
			days.add(new SimpleObjectProperty<UIDay>(uiDay));
		}

		this.uiDays = days;
	}

	/**
	 * Index property.
	 *
	 * @return the string property
	 */
	public final StringProperty indexProperty() {
		return this.index;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public final java.lang.String getIndex() {
		return this.indexProperty().get();
	}

	/**
	 * Sets the index.
	 *
	 * @param index
	 *            the new index
	 */
	public final void setIndex(final java.lang.String index) {
		this.indexProperty().set(index);
	}

	public List<ObjectProperty<UIDay>> getUiDays() {
		return uiDays;
	}

	public void setUiDays(List<ObjectProperty<UIDay>> uiDays) {
		this.uiDays = uiDays;
	}

	public final ObjectProperty<EnumEmbryo.Injection> injectionProperty() {
		return this.injection;
	}


	public final org.cf.card.util.EnumEmbryo.Injection getInjection() {
		return this.injectionProperty().get();
	}


	public final void setInjection(final org.cf.card.util.EnumEmbryo.Injection injection) {
		this.injectionProperty().set(injection);
	}

	public Long getEmbryoId() {
		return embryoId;
	}

	public void setEmbryoId(Long embryoId) {
		this.embryoId = embryoId;
	}

	public Option getDestiny() {
		return destiny;
	}

	public void setDestiny(Option destiny) {
		this.destiny = destiny;
	}

	public Date getDestinyDate() {
		return destinyDate;
	}

	public void setDestinyDate(Date destinyDate) {
		this.destinyDate = destinyDate;
	}

	public final IntegerProperty srNoProperty(){
		return this.srNo;
	}

	public final StringProperty gradeProperty(){
		return this.grade;
	}

	public final StringProperty dayProperty(){
		return this.day;
	}

	public final java.lang.Integer getSrNo() {
		return this.srNoProperty().get();
	}

	public void setSrNo(final java.lang.Integer srNo) {
		 this.srNoProperty().set(srNo);
	}

	public final java.lang.String getGrade() {
		return this.gradeProperty().get();
	}

	public void setGrade(final java.lang.String grade) {
		this.gradeProperty().set(grade);
	}

	public String getDay() {
		return this.dayProperty().get();
	}

	public void setDay(final java.lang.String day) {
		this.dayProperty().set(day);
	}
}
