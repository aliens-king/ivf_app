/**
 *
 */
package org.cf.card.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nikhil Mahajan
 *
 */
public class CycleDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long womanCodeId = 0l;
	private String womanCode;

	/*
	 * private Long partnerCodeId; private String partnerCode;
	 */

	private Date startDate;
	private Long coupleId;

	private int eggCollection;
	private int biochemical;
	private String clinical;
	private long evolution;
	private Integer biochemicalOption;

	/*
	 * private Long womanClientId; private Long partnerClientId;
	 */
	private Date endDate;

	// Added to get the Semen Details
	/*
	 * private Long semenCodeId; private Date dateUsed; private Integer index;
	 * private Long codeId; private Long semenId; private String code;
	 */
	private Long partnerCodeId = 0l;
	private String partnerCode;
	private float morphology;
	private int motilityA;
	private int motilityB;
	private int motilityC;
	private int motilityD;
	private float density;
	private int motility;
	private String quality;
	private String remark;
	private Long remarkId;

	/**
	 * COnstrutor for embryoCyles Query
	 *
	 * Instantiates a new cycle dto.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param womanCode
	 *            the woman code
	 * @param partnerCodeId
	 *            the partner code id
	 * @param partnerCode
	 *            the partner code
	 * @param startDate
	 *            the start date
	 * @param eggCollection
	 *            the egg collection
	 * @param bioChemical
	 *            the bio chemical
	 * @param clinical
	 *            the clinical
	 * @param evolution
	 *            the evolution
	 */
	/*
	 * public CycleDto(Long womanCodeId, String womanCode, int eggCollection,
	 * Date startDate, String bioChemical, String clinical, long evolution) {
	 * super(); this.womanCodeId = womanCodeId; this.womanCode = womanCode;
	 * this.eggCollection = eggCollection; this.startDate = startDate;
	 * this.bioChemical = bioChemical; this.clinical = clinical; this.evolution
	 * = evolution; }
	 */

	/*
	 * public CycleDto(Long womanCodeId, String womanCode, int eggCollection,
	 * Date startDate, String clinical, long evolution) { super();
	 * this.womanCodeId = womanCodeId; this.womanCode = womanCode;
	 * this.eggCollection = eggCollection; this.startDate = startDate;
	 * this.clinical = clinical; this.evolution = evolution; }
	 */

	public CycleDto(Long womanCodeId, String womanCode, Long coupleId, int eggCollection, Date startDate, Date endDate,
			Integer biochemicalOption, long evolution, String remark, Long remarkId) {
		super();
		this.womanCodeId = womanCodeId;
		this.womanCode = womanCode;
		this.coupleId = coupleId;
		this.eggCollection = eggCollection;
		this.startDate = startDate;
		this.endDate = endDate;
		this.biochemicalOption = biochemicalOption;
		this.evolution = evolution;
		this.remark = remark;
		this.remarkId = remarkId!=null ? remarkId : 0l;
	}

	/*
	 * public CycleDto(Long semenCodeId, Date dateUsed, Integer index, Long
	 * codeId, Long semenId, String code) { this.semenCodeId = semenCodeId;
	 * this.dateUsed = dateUsed; this.index = index; this.codeId = codeId;
	 * this.semenId = semenId; this.code = code; }
	 */

	/**
	 * Used in query for Semen table on Cycles Screen.
	 *
	 * @param partnerCodeId
	 *            the partner code id
	 * @param partnerCode
	 *            the partner code
	 * @param density
	 *            the density
	 * @param morphology
	 *            the morphology
	 * @param motilityA
	 *            the motility a
	 * @param motilityB
	 *            the motility b
	 * @param motilityC
	 *            the motility c
	 * @param motilityD
	 *            the motility d
	 */
	public CycleDto(Long partnerCodeId, String partnerCode, Long coupleId, Date startDate, Float density,
			Float morphology, Integer motilityA, Integer motilityB, Integer motilityC, Integer motilityD) {
		this.partnerCodeId = partnerCodeId;
		this.partnerCode = partnerCode;
		this.coupleId = coupleId;
		this.startDate = startDate;
		this.density = density != null ? density : 0;
		this.morphology = morphology != null ? morphology : 0;
		this.motilityA = motilityA != null ? motilityA : 0;
		this.motilityB = motilityB != null ? motilityB : 0;
		this.motilityC = motilityC != null ? motilityC : 0;
		this.motilityD = motilityD != null ? motilityD : 0;
	}

	public CycleDto(Long semenCodeId, Date dateUsed, Integer index, Long codeId, Long semenId, String code,
			float morphology, int motilityA, int motilityB, int motilityC, int motilityD, float density) {
		/*
		 * this.semenCodeId = semenCodeId; this.dateUsed = dateUsed; this.index
		 * = index; this.codeId = codeId; this.semenId = semenId; this.code =
		 * code;
		 */
		this.morphology = morphology;
		this.motilityA = motilityA;
		this.motilityB = motilityB;
		this.motilityC = motilityC;
		this.motilityD = motilityD;
		this.density = density;
	}

	public CycleDto() {

	}

	public Long getWomanCodeId() {
		return womanCodeId;
	}

	public void setWomanCodeId(Long womanCodeId) {
		this.womanCodeId = womanCodeId;
	}

	public String getWomanCode() {
		return womanCode;
	}

	public void setWomanCode(String womanCode) {
		this.womanCode = womanCode;
	}

	public Long getPartnerCodeId() {
		return partnerCodeId;
	}

	public void setPartnerCodeId(Long partnerCodeId) {
		this.partnerCodeId = partnerCodeId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getEggCollection() {
		return eggCollection;
	}

	public void setEggCollection(int eggCollection) {
		this.eggCollection = eggCollection;
	}

	public int getBiochemical() {
		return biochemical;
	}

	public void setBiochemical(int biochemical) {
		this.biochemical = biochemical;
	}

	public String getClinical() {
		return clinical;
	}

	public void setClinical(String clinical) {
		this.clinical = clinical;
	}

	public long getEvolution() {
		return evolution;
	}

	public void setEvolution(long evolution) {
		this.evolution = evolution;
	}

	/*
	 * public Long getWomanClientId() { return womanClientId; }
	 *
	 * public void setWomanClientId(Long womanClientId) { this.womanClientId =
	 * womanClientId; }
	 *
	 * public Long getPartnerClientId() { return partnerClientId; }
	 *
	 * public void setPartnerClientId(Long partnerClientId) {
	 * this.partnerClientId = partnerClientId; }
	 */

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CycleDto [womanCodeId=");
		builder.append(womanCodeId);
		builder.append(", womanCode=");
		builder.append(womanCode);
		builder.append(", partnerCodeId=");
		builder.append(partnerCodeId);
		builder.append(", partnerCode=");
		builder.append(partnerCode);
		builder.append(", wstartDate=");
		builder.append(startDate);
		builder.append(", eggCollection=");
		builder.append(eggCollection);
		builder.append(", biochemicalOption=");
		builder.append(biochemicalOption);
		builder.append(", clinical=");
		builder.append(clinical);
		builder.append(", evolution=");
		builder.append(evolution);
		/*
		 * builder.append(", womanClientId="); builder.append(womanClientId);
		 * builder.append(", partnerClientId=");
		 * builder.append(partnerClientId);
		 */
		builder.append(", wendDate=");
		builder.append(endDate);
		builder.append("\n MAN DATA");
		builder.append(", partnerCodeId=");
		builder.append(partnerCodeId);
		builder.append(", partnerCode=");
		builder.append(partnerCode);
		builder.append(", pcoupleId=");
		builder.append(coupleId);
		builder.append(", pstartDate=");
		builder.append(startDate);
		builder.append(", morphology=");
		builder.append(morphology);
		builder.append(", motilityA=");
		builder.append(motilityA);
		builder.append(", quality=");
		builder.append(quality);
		builder.append(", remark=");
		builder.append(remark);
		builder.append("]");

		return builder.toString();
	}

	/*
	 * public Long getSemenCodeId() { return semenCodeId; }
	 *
	 * public void setSemenCodeId(Long semenCodeId) { this.semenCodeId =
	 * semenCodeId; }
	 *
	 * public Date getDateUsed() { return dateUsed; }
	 *
	 * public void setDateUsed(Date dateUsed) { this.dateUsed = dateUsed; }
	 *
	 * public Integer getIndex() { return index; }
	 *
	 * public void setIndex(Integer index) { this.index = index; }
	 *
	 * public Long getCodeId() { return codeId; }
	 *
	 * public void setCodeId(Long codeId) { this.codeId = codeId; }
	 *
	 * public Long getSemenId() { return semenId; }
	 *
	 * public void setSemenId(Long semenId) { this.semenId = semenId; }
	 *
	 * public String getCode() { return code; }
	 *
	 * public void setCode(String code) { this.code = code; }
	 */

	public float getMorphology() {
		return morphology;
	}

	public void setMorphology(float morphology) {
		this.morphology = morphology;
	}

	public int getMotilityA() {
		return motilityA;
	}

	public void setMotilityA(int motilityA) {
		this.motilityA = motilityA;
	}

	public int getMotilityB() {
		return motilityB;
	}

	public void setMotilityB(int motilityB) {
		this.motilityB = motilityB;
	}

	public int getMotilityC() {
		return motilityC;
	}

	public void setMotilityC(int motilityC) {
		this.motilityC = motilityC;
	}

	public int getMotilityD() {
		return motilityD;
	}

	public void setMotilityD(int motilityD) {
		this.motilityD = motilityD;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int getMotility() {
		this.motility = motilityA + motilityB + motilityC;
		return motility;
	}

	public String getQuality() {
		/*
		 * this.quality = new
		 * StringBuilder(String.valueOf(density)).append("/").append(getMotility
		 * ()).append("/") .append(morphology).toString();
		 */
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public Integer getBiochemicalOption() {
		return biochemicalOption;
	}

	public void setBiochemicalOption(Integer biochemicalOption) {
		this.biochemicalOption = biochemicalOption;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}

}
