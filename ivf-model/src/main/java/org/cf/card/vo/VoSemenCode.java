package org.cf.card.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Surinder
 *
 */
public class VoSemenCode implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long semenCodeId;
	private Date dateUsed;
	private Date cryoDate;
	private Integer index;
	private Long codeId;
	private Long semenId;
	private String code;
	private float morphology;
	private int motilityA;
	private int motilityB;
	private int motilityC;
	private int motilityD;
	private float density;
	private int motility;
	private String quality;
	private String remarks;
	private Date createdDate;
	private int use;
	private float mediaAdded;
	private int debris;
	private int viscosity;
	private int agglutination;
	private int aggregation;
	private String timeProcessed; 
	private String timeProduced;
	private float volume;
	private int roundCell;
	private int cryoVisiblity;
	private int totalSemens;
	
	

	public VoSemenCode() {
		super();
	}

	public VoSemenCode(Long semenCodeId, Date dateUsed, Integer index, Long codeId, Long semenId, String code, String remarks) {
		this.semenCodeId = semenCodeId;
		this.dateUsed = dateUsed;
		this.index = index;
		this.codeId = codeId;
		this.semenId = semenId;
		this.code = code;
		this.remarks = remarks;
	}

	public VoSemenCode(Long semenCodeId, Date dateUsed, Integer index, Long codeId, Long semenId, String code, Date createdDate, Date cryoDate,
			float morphology, int motilityA, int motilityB, int motilityC, int motilityD, float density, String remarks) {
		this.semenCodeId = semenCodeId;
		this.dateUsed = dateUsed;
		this.index = index;
		this.codeId = codeId;
		this.semenId = semenId;
		this.code = code;
		this.createdDate = createdDate;
		this.cryoDate = cryoDate;
		this.morphology = morphology;
		this.motilityA = motilityA;
		this.motilityB = motilityB;
		this.motilityC = motilityC;
		this.motilityD = motilityD;
		this.density = density;
		this.remarks = remarks;
	}



	/**
	 * Used in query for Semen table on Cycles Screen.
	 *
	 * @param partnerCodeId the partner code id
	 * @param partnerCode the partner code
	 * @param density the density
	 * @param morphology the morphology
	 * @param motilityA the motility a
	 * @param motilityB the motility b
	 * @param motilityC the motility c
	 * @param motilityD the motility d
	 */
	public VoSemenCode(Long partnerCodeId, String partnerCode,float density, float morphology, int motilityA, int motilityB, int motilityC,
			int motilityD) {
		this.codeId = partnerCodeId;
		this.code = partnerCode;
		this.density = density;
		this.morphology = morphology;
		this.motilityA = motilityA;
		this.motilityB = motilityB;
		this.motilityC = motilityC;
		this.motilityD = motilityD;

	}
	
	/**
	 * Used in query for Andrology Screen.
	 *
	 */
	
	public VoSemenCode(Long semenCodeId, Long semenId, Date createdDate, int totalSemens, int use, float mediaAdded, int debris, int viscosity, int agglutination, int aggregation, int  cryoVisiblity, String timeProcessed, String timeProduced, 
			String remarks, float volume, float density, int motilityA, int motilityB, int motilityC, int motilityD, float morphology, int roundCell){
		this.semenCodeId = semenCodeId;
		this.semenId = semenId;
		this.createdDate = createdDate;
		this.totalSemens = totalSemens;
		this.use = use;
		this.mediaAdded = mediaAdded;
		this.debris = debris;
		this.viscosity = viscosity;
		this.agglutination = agglutination;
		this.aggregation = aggregation;
		this.cryoVisiblity = cryoVisiblity;
		this.timeProcessed = timeProcessed;
		this.timeProduced = timeProduced;
		this.remarks = remarks;
		this.volume = volume;
		this.density = density;
		this.motilityA = motilityA;
		this.motilityB = motilityB;
		this.motilityC = motilityC;
		this.motilityD = motilityD;
		this.morphology = morphology;
		this.roundCell = roundCell;
	}
	


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getSemenCodeId() {
		return semenCodeId;
	}

	public void setSemenCodeId(Long semenCodeId) {
		this.semenCodeId = semenCodeId;
	}

	public Date getDateUsed() {
		return dateUsed;
	}

	public void setDateUsed(Date dateUsed) {
		this.dateUsed = dateUsed;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getSemenId() {
		return semenId;
	}

	public void setSemenId(Long semenId) {
		this.semenId = semenId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
		this.quality = new StringBuilder(String.valueOf(density)).append("/").append(getMotility()).append("/")
				.append(morphology).toString();
		return quality;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getUse() {
		return use;
	}

	public void setUse(int use) {
		this.use = use;
	}

	public float getMediaAdded() {
		return mediaAdded;
	}

	public void setMediaAdded(float mediaAdded) {
		this.mediaAdded = mediaAdded;
	}

	public int getDebris() {
		return debris;
	}

	public void setDebris(int debris) {
		this.debris = debris;
	}

	public int getViscosity() {
		return viscosity;
	}

	public void setViscosity(int viscosity) {
		this.viscosity = viscosity;
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

	public String getTimeProcessed() {
		return timeProcessed;
	}

	public void setTimeProcessed(String timeProcessed) {
		this.timeProcessed = timeProcessed;
	}

	public String getTimeProduced() {
		return timeProduced;
	}

	public void setTimeProduced(String timeProduced) {
		this.timeProduced = timeProduced;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public int getRoundCell() {
		return roundCell;
	}

	public void setRoundCell(int roundCell) {
		this.roundCell = roundCell;
	}

	public void setMotility(int motility) {
		this.motility = motility;
	}

	public int getCryoVisiblity() {
		return cryoVisiblity;
	}

	public void setCryoVisiblity(int cryoVisiblity) {
		this.cryoVisiblity = cryoVisiblity;
	}

	public int getTotalSemens() {
		return totalSemens;
	}

	public void setTotalSemens(int totalSemens) {
		this.totalSemens = totalSemens;
	}

	public Date getCryoDate() {
		return cryoDate;
	}

	public void setCryoDate(Date cryoDate) {
		this.cryoDate = cryoDate;
	}
	
}
