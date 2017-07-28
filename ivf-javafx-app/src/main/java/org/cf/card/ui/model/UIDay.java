/**
 *
 */
package org.cf.card.ui.model;

import java.util.Date;

/**
 * The Class UIDay.
 *
 * @author Nikhil Mahajan
 */
public class UIDay {

	private int dayIndex;
	private int oocyte;
	private String value;
	private Long embryoCodeId;
	private Date dayDate;
	private int moduleId;

	public UIDay(int dayIndex, int oocyte, String value, Long embryoCodeId, Date dayDate,int moduleId) {
		super();
		this.dayIndex = dayIndex;
		this.oocyte = oocyte;
		this.value = value;
		this.embryoCodeId = embryoCodeId;
		this.dayDate = dayDate;
		this.moduleId = moduleId;
	}

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}

	public int getOocyte() {
		return oocyte;
	}

	public void setOocyte(int oocyte) {
		this.oocyte = oocyte;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getEmbryoCodeId() {
		return embryoCodeId;
	}

	public void setEmbryoCodeId(Long embryoCodeId) {
		this.embryoCodeId = embryoCodeId;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UIDay [dayIndex=");
		builder.append(dayIndex);
		builder.append(", oocyte=");
		builder.append(oocyte);
		builder.append(", value=");
		builder.append(value);
		builder.append(", embryoCodeId=");
		builder.append(embryoCodeId);
		builder.append("]");
		return builder.toString();
	}

	// private IntegerProperty dayIndex;
	//
	// private StringProperty value;
	//
	// /**
	// * Instantiates a new UI day.
	// *
	// * @param dayIndex
	// * the day index
	// * @param value
	// * the value
	// */
	// public UIDay(String value, int dayIndex) {
	//
	// this.dayIndex = new SimpleIntegerProperty(dayIndex);
	// this.value = new SimpleStringProperty(value);
	// }
	//
	// /**
	// * Value property.
	// *
	// * @return the string property
	// */
	// public final StringProperty valueProperty() {
	// return this.value;
	// }
	//
	// /**
	// * Gets the value.
	// *
	// * @return the value
	// */
	// public final java.lang.String getValue() {
	// return this.valueProperty().get();
	// }
	//
	// /**
	// * Sets the value.
	// *
	// * @param value
	// * the new value
	// */
	// public final void setValue(final java.lang.String value) {
	// this.valueProperty().set(value);
	// }
	//
	// public final IntegerProperty dayIndexProperty() {
	// return this.dayIndex;
	// }
	//
	// public final int getDayIndex() {
	// return this.dayIndexProperty().get();
	// }
	//
	// public final void setDayIndex(final int dayIndex) {
	// this.dayIndexProperty().set(dayIndex);
	// }
	//
	// @Override
	// public String toString() {
	// StringBuilder builder = new StringBuilder();
	// builder.append("UIDay [dayIndex=");
	// builder.append(dayIndex.getValue());
	// builder.append(", value=");
	// builder.append(value.getValue());
	// builder.append("]");
	// return builder.toString();
	// }
	//

}
