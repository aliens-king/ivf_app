package org.cf.card.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UIDayDto {

	private int dayIndex;
	private Long embryoCodeId;
	private Date dayDate;
	private List<Integer> dayOptionId;
	private int moduleId;

	private Map<Integer, String> dayColumnKeyValue;

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}

	public Long getEmbryoCodeId() {
		return embryoCodeId;
	}

	public void setEmbryoCodeId(Long embryoCodeId) {
		this.embryoCodeId = embryoCodeId;
	}

	public List<Integer> getDayOptionId() {
		return dayOptionId;
	}

	public void setDayOptionId(List<Integer> dayOptionId) {
		this.dayOptionId = dayOptionId;
	}

	public Map<Integer, String> getDayColumnKeyValue() {
		return dayColumnKeyValue;
	}

	public void setDayColumnKeyValue(Map<Integer, String> dayColumnKeyValue) {
		this.dayColumnKeyValue = dayColumnKeyValue;
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

}
