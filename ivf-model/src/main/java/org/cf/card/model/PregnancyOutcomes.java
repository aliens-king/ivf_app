/**
 * 
 */
package org.cf.card.model;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author insonix
 *
 */
@Entity
@Table(name = "pregnancy_outcomes")
public class PregnancyOutcomes {

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "code_id")
	private Codes code;

	@Column(name = "biochemical_id")
	private int biochemical;

	@Column(name = "clinical_id")
	private int clinical;

	@Column(name = "livebirth_id")
	private int livebirth;

	@Column(name = "biochemical_option_id")
	private int biochemicalOption;

	@Column(name = "clinical_option_id")
	private int clinicalOption;

	@Column(name = "livebirth_option_id")
	private int livebirthOption;

	@Temporal(TemporalType.DATE)
	@Column(name = "biochemical_date")
	private Date biochemicalDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "clinical_date")
	private Date clinicalDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "livebirth_date")
	private Date livebirthDate;

	@Column(name = "remarks", columnDefinition = "LONGTEXT")
	private String remarks;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "nurse_id")
	private User nurse;

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
	 * @return the code
	 */
	public Codes getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Codes code) {
		this.code = code;
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
	public User getNurse() {
		return nurse;
	}

	/**
	 * @param nurse the nurse to set
	 */
	public void setNurse(User nurse) {
		this.nurse = nurse;
	}
	
	

}
