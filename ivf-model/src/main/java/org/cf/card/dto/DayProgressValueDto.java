/**
 *
 */
package org.cf.card.dto;

import java.util.Date;

/**
 * @author Nikhil Mahajan
 *
 */
public class DayProgressValueDto {

	private int embryoCodeIndex;

	private Long codeId;

	private Long embryoCodeId;

	private Date dayDate;

	private int dayIndex;

	private Integer dayOptionId;

	private String dayColumnName;

	private int moduleId;

	/**
	 *
	 */
	public DayProgressValueDto() {
	}
	
	public DayProgressValueDto(Long embryoCodeId,Date dayDate,int dayIndex,Integer dayOptionId) {
		this.embryoCodeId = embryoCodeId;
		this.dayDate = dayDate;
		this.dayIndex = dayIndex;
		this.dayOptionId = dayOptionId;
		
	}

	public int getEmbryoCodeIndex() {
		return embryoCodeIndex;
	}

	public void setEmbryoCodeIndex(int embryoCodeIndex) {
		this.embryoCodeIndex = embryoCodeIndex;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
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

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}

	public Integer getDayOptionId() {
		return dayOptionId;
	}

	public void setDayOptionId(Integer dayOptionId) {
		this.dayOptionId = dayOptionId;
	}

	public String getDayColumnName() {
		return dayColumnName;
	}

	public void setDayColumnName(String dayColumnName) {
		this.dayColumnName = dayColumnName;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	
	

}
