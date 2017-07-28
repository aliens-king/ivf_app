package org.cf.card.dto;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.EmbryoCode;

public class EmbryoCodeValueDto {
	
	private Long embryoCodeId;

	private Long eggEmbryoCodeId;

	private String dateOfUse;

	private String destinyDate;

	private String catheter;

	private int type;

	private String time;

	private String remarks;

	private int remarkType;
	
	

	private List<EmbryoCode> embryoETList = new ArrayList<EmbryoCode>();

	public Long getEmbryoCodeId() {
		return embryoCodeId;
	}

	public void setEmbryoCodeId(Long embryoCodeId) {
		this.embryoCodeId = embryoCodeId;
	}

	public Long getEggEmbryoCodeId() {
		return eggEmbryoCodeId;
	}

	public void setEggEmbryoCodeId(Long eggEmbryoCodeId) {
		this.eggEmbryoCodeId = eggEmbryoCodeId;
	}

	public String getDateOfUse() {
		return dateOfUse;
	}

	public void setDateOfUse(String dateOfUse) {
		this.dateOfUse = dateOfUse;
	}

	public String getDestinyDate() {
		return destinyDate;
	}

	public void setDestinyDate(String destinyDate) {
		this.destinyDate = destinyDate;
	}

	public String getCatheter() {
		return catheter;
	}

	public void setCatheter(String catheter) {
		this.catheter = catheter;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getRemarkType() {
		return remarkType;
	}

	public void setRemarkType(int remarkType) {
		this.remarkType = remarkType;
	}

	public List<EmbryoCode> getEmbryoETList() {
		return embryoETList;
	}

	public void setEmbryoETList(List<EmbryoCode> embryoETList) {
		this.embryoETList = embryoETList;
	}
}
