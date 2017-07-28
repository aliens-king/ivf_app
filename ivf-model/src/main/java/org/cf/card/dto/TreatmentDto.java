/**
 *
 */
package org.cf.card.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nikhil Mahajan
 *
 */
public class TreatmentDto {

	private Long treatmentID;

	private int semenValue;

	private int oocytes;

	private long codeId;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date cryoDate;
	
	private String incubator;
	
	private int research;
	
	private int cycleType;
	
	

	public int getCycleType() {
		return cycleType;
	}

	public void setCycleType(int cycleType) {
		this.cycleType = cycleType;
	}

	private List<EmbryoCodeDto> aoEmbryoCodeDto = new ArrayList<>();

	
	public Long getTreatmentID() {
		return treatmentID;
	}

	public void setTreatmentID(Long treatmentID) {
		this.treatmentID = treatmentID;
	}

	public int getSemenValue() {
		return semenValue;
	}

	public void setSemenValue(int semenValue) {
		this.semenValue = semenValue;
	}

	public int getOocytes() {
		return oocytes;
	}

	public void setOocytes(int oocytes) {
		this.oocytes = oocytes;
	}

	public long getCodeId() {
		return codeId;
	}

	public void setCodeId(long codeId) {
		this.codeId = codeId;
	}

	public List<EmbryoCodeDto> getAoEmbryoCodeDto() {
		return aoEmbryoCodeDto;
	}

	public void setAoEmbryoCodeDto(List<EmbryoCodeDto> aoEmbryoCodeDto) {
		this.aoEmbryoCodeDto = aoEmbryoCodeDto;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setCryoDate(Date cryoDate)
	{
		this.cryoDate = cryoDate;
	}
	
	public Date getCryoDate()
	{
		return cryoDate;
	}

	public String getIncubator() {
		return incubator;
	}

	public void setIncubator(String incubator) {
		this.incubator = incubator;
	}

	public int getResearch() {
		return research;
	}

	public void setResearch(int research) {
		this.research = research;
	}
	
	

}
