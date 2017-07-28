package org.cf.card.dto;

import java.util.Date;
import java.util.List;

/**
 * @author insonix
 *
 */
public class CryoSemenDto {

	private Long semenId;

	private Long codeId;

	private String remarks;

	private Long semenCodeId;

	private int totalSemens;

	private Date cryoDate;

	private int cryoVisibility;

	private List<SemenCodeDto> aoSemenCodeDto;

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

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getSemenCodeId() {
		return semenCodeId;
	}

	public void setSemenCodeId(Long semenCodeId) {
		this.semenCodeId = semenCodeId;
	}

	public Date getCryoDate() {
		return cryoDate;
	}

	public void setCryoDate(Date cryoDate) {
		this.cryoDate = cryoDate;
	}

	public int getCryoVisibility() {
		return cryoVisibility;
	}

	public void setCryoVisibility(int cryoVisibility) {
		this.cryoVisibility = cryoVisibility;
	}

	public List<SemenCodeDto> getAoSemenCodeDto() {
		return aoSemenCodeDto;
	}

	public int getTotalSemens() {
		return totalSemens;
	}

	public void setTotalSemens(int totalSemens) {
		this.totalSemens = totalSemens;
	}

	public void setAoSemenCodeDto(List<SemenCodeDto> aoSemenCodeDto) {
		this.aoSemenCodeDto = aoSemenCodeDto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CryoSemenDto [semenId=");
		builder.append(semenId);
		builder.append(", codeId=");
		builder.append(codeId);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", semenCodeId=");
		builder.append(semenCodeId);
		builder.append(", totalSemens=");
		builder.append(totalSemens);
		builder.append(", cryoDate=");
		builder.append(cryoDate);
		builder.append(", cryoVisibility=");
		builder.append(cryoVisibility);
		builder.append(", aoSemenCodeDto=");
		builder.append(aoSemenCodeDto);
		builder.append("]");
		return builder.toString();
	}



}
