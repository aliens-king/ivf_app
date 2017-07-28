/**
 * 
 */
package org.cf.card.dto;

import java.util.Date;

/**
 * @author insonix
 *
 */
public class PregnancyOutcomesDto {

	private Long id;

	private Long womanCodeId;

	private int biochemical;

	private int clinical;

	private int livebirth;

	private int biochemicalOption;

	private int clinicalOption;

	private int livebirthOption;

	private Date biochemicalDate;

	private Date clinicalDate;

	private Date livebirthDate;

	private String remarks;

	private Long nurse;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the womanCodeId
	 */
	public Long getWomanCodeId() {
		return womanCodeId;
	}

	/**
	 * @param womanCodeId
	 *            the womanCodeId to set
	 */
	public void setWomanCodeId(Long womanCodeId) {
		this.womanCodeId = womanCodeId;
	}

	/**
	 * @return the biochemical
	 */
	public int getBiochemical() {
		return biochemical;
	}

	/**
	 * @param biochemical
	 *            the biochemical to set
	 */
	public void setBiochemical(int biochemical) {
		this.biochemical = biochemical;
	}

	/**
	 * @return the clinical
	 */
	public int getClinical() {
		return clinical;
	}

	/**
	 * @param clinical
	 *            the clinical to set
	 */
	public void setClinical(int clinical) {
		this.clinical = clinical;
	}

	/**
	 * @return the livebirth
	 */
	public int getLivebirth() {
		return livebirth;
	}

	/**
	 * @param livebirth
	 *            the livebirth to set
	 */
	public void setLivebirth(int livebirth) {
		this.livebirth = livebirth;
	}

	/**
	 * @return the biochemicalOption
	 */
	public int getBiochemicalOption() {
		return biochemicalOption;
	}

	/**
	 * @param biochemicalOption
	 *            the biochemicalOption to set
	 */
	public void setBiochemicalOption(int biochemicalOption) {
		this.biochemicalOption = biochemicalOption;
	}

	/**
	 * @return the clinicalOption
	 */
	public int getClinicalOption() {
		return clinicalOption;
	}

	/**
	 * @param clinicalOption
	 *            the clinicalOption to set
	 */
	public void setClinicalOption(int clinicalOption) {
		this.clinicalOption = clinicalOption;
	}

	/**
	 * @return the livebirthOption
	 */
	public int getLivebirthOption() {
		return livebirthOption;
	}

	/**
	 * @param livebirthOption
	 *            the livebirthOption to set
	 */
	public void setLivebirthOption(int livebirthOption) {
		this.livebirthOption = livebirthOption;
	}

	/**
	 * @return the biochemicalDate
	 */
	public Date getBiochemicalDate() {
		return biochemicalDate;
	}

	/**
	 * @param biochemicalDate
	 *            the biochemicalDate to set
	 */
	public void setBiochemicalDate(Date biochemicalDate) {
		this.biochemicalDate = biochemicalDate;
	}

	/**
	 * @return the clinicalDate
	 */
	public Date getClinicalDate() {
		return clinicalDate;
	}

	/**
	 * @param clinicalDate
	 *            the clinicalDate to set
	 */
	public void setClinicalDate(Date clinicalDate) {
		this.clinicalDate = clinicalDate;
	}

	/**
	 * @return the livebirthDate
	 */
	public Date getLivebirthDate() {
		return livebirthDate;
	}

	/**
	 * @param livebirthDate
	 *            the livebirthDate to set
	 */
	public void setLivebirthDate(Date livebirthDate) {
		this.livebirthDate = livebirthDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the nurse
	 */
	public Long getNurse() {
		return nurse;
	}

	/**
	 * @param nurse
	 *            the nurse to set
	 */
	public void setNurse(Long nurse) {
		this.nurse = nurse;
	}

}
