package org.cf.card.dto;

public class EmbryoCodeDayDto {
	
	private Long id;
	private Long embryoCodeId;
	private int dayIndex;
	private String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmbryoCodeId() {
		return embryoCodeId;
	}
	public void setEmbryoCodeId(Long embryoCodeId) {
		this.embryoCodeId = embryoCodeId;
	}
	public int getDayIndex() {
		return dayIndex;
	}
	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
