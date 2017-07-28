package org.cf.card.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SemenDto {

	private String type;

	private int use;

	private int viscosity;

	private int debris;

	private int agglutination;

	private int aggregation;

	private float mediaAdded;

	private int screen;

	private String timeProduced;

	private String timeProcessed;

	private Long codeId;

	private int index;

	private Date createdDate;

	private String remarks;

	private int totalSemens;

	private Long semenId;

	private Long semenCodeId;

	private int cryoVisibility;


	private List<SemenDataDto> semenDataList = new ArrayList<SemenDataDto>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUse() {
		return use;
	}

	public void setUse(int use) {
		this.use = use;
	}

	public int getViscosity() {
		return viscosity;
	}

	public void setViscosity(int viscosity) {
		this.viscosity = viscosity;
	}

	public int getDebris() {
		return debris;
	}

	public void setDebris(int debris) {
		this.debris = debris;
	}

	public int getAgglutination() {
		return agglutination;
	}

	public void setAgglutination(int agglutination) {
		this.agglutination = agglutination;
	}

	public int getAggregation() {
		return aggregation;
	}

	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

	public float getMediaAdded() {
		return mediaAdded;
	}

	public void setMediaAdded(float mediaAdded) {
		this.mediaAdded = mediaAdded;
	}

	public int getScreen() {
		return screen;
	}

	public void setScreen(int screen) {
		this.screen = screen;
	}

	public String getTimeProduced() {
		return timeProduced;
	}

	public void setTimeProduced(String timeProduced) {
		this.timeProduced = timeProduced;
	}

	public String getTimeProcessed() {
		return timeProcessed;
	}

	public void setTimeProcessed(String timeProcessed) {
		this.timeProcessed = timeProcessed;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getSemenId() {
		return semenId;
	}

	public void setSemenId(Long semenId) {
		this.semenId = semenId;
	}

	public Long getSemenCodeId() {
		return semenCodeId;
	}

	public void setSemenCodeId(Long semenCodeId) {
		this.semenCodeId = semenCodeId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getTotalSemens() {
		return totalSemens;
	}

	public void setTotalSemens(int totalSemens) {
		this.totalSemens = totalSemens;
	}

	public int getCryoVisibility() {
		return cryoVisibility;
	}

	public void setCryoVisibility(int cryoVisibility) {
		this.cryoVisibility = cryoVisibility;
	}

	public List<SemenDataDto> getSemenDataList() {
		return semenDataList;
	}

	public void setSemenDataList(List<SemenDataDto> semenDataList) {
		this.semenDataList = semenDataList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SemenDto [type=");
		builder.append(type);
		builder.append(", use=");
		builder.append(use);
		builder.append(", viscosity=");
		builder.append(viscosity);
		builder.append(", debris=");
		builder.append(debris);
		builder.append(", agglutination=");
		builder.append(agglutination);
		builder.append(", aggregation=");
		builder.append(aggregation);
		builder.append(", mediaAdded=");
		builder.append(mediaAdded);
		builder.append(", screen=");
		builder.append(screen);
		builder.append(", timeProduced=");
		builder.append(timeProduced);
		builder.append(", timeProcessed=");
		builder.append(timeProcessed);
		builder.append(", codeId=");
		builder.append(codeId);
		builder.append(", index=");
		builder.append(index);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", totalSemens=");
		builder.append(totalSemens);
		builder.append(", semenId=");
		builder.append(semenId);
		builder.append(", semenCodeId=");
		builder.append(semenCodeId);
		builder.append(", cryoVisibility=");
		builder.append(cryoVisibility);
		builder.append(", semenDataList=");
		builder.append(semenDataList);
		builder.append("]");
		return builder.toString();
	}


}
