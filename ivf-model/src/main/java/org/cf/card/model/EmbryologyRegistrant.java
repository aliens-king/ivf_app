/**
 * 
 */
package org.cf.card.model;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
@Entity
@Table(name = "embryo_registrant")
public class EmbryologyRegistrant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5587290682127140548L;

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "emb_registrant_day0")
	private String embRegistrantDay0;
	@Column(name = "dr_registrant_day0")
	private String drRegistrantDay0;

	@Column(name = "emb_registrant_day1")
	private String embRegistrantDay1;
	@Column(name = "dr_registrant_day1")
	private String drRegistrantDay1;

	@Column(name = "emb_registrant_day2")
	private String embRegistrantDay2;
	@Column(name = "dr_registrant_day2")
	private String drRegistrantDay2;

	@Column(name = "emb_registrant_day3")
	private String embRegistrantDay3;
	@Column(name = "dr_registrant_day3")
	private String drRegistrantDay3;

	@Column(name = "emb_registrant_day4")
	private String embRegistrantDay4;
	@Column(name = "dr_registrant_day4")
	private String drRegistrantDay4;

	@Column(name = "emb_registrant_day5")
	private String embRegistrantDay5;
	@Column(name = "dr_registrant_day5")
	private String drRegistrantDay5;

	@Column(name = "emb_registrant_day6")
	private String embRegistrantDay6;
	@Column(name = "dr_registrant_day6")
	private String drRegistrantDay6;

	@Column(name = "emb_registrant_day7")
	private String embRegistrantDay7;
	@Column(name = "dr_registrant_day7")
	private String drRegistrantDay7;

	@Column(name = "screen_id")
	private int screenId;

	@ManyToOne
	@JoinColumn(name = "code_id")
	private Codes code;

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
	 * @return the embRegistrantDay0
	 */
	public String getEmbRegistrantDay0() {
		return embRegistrantDay0;
	}

	/**
	 * @param embRegistrantDay0
	 *            the embRegistrantDay0 to set
	 */
	public void setEmbRegistrantDay0(String embRegistrantDay0) {
		this.embRegistrantDay0 = embRegistrantDay0;
	}

	/**
	 * @return the drRegistrantDay0
	 */
	public String getDrRegistrantDay0() {
		return drRegistrantDay0;
	}

	/**
	 * @param drRegistrantDay0
	 *            the drRegistrantDay0 to set
	 */
	public void setDrRegistrantDay0(String drRegistrantDay0) {
		this.drRegistrantDay0 = drRegistrantDay0;
	}

	/**
	 * @return the embRegistrantDay1
	 */
	public String getEmbRegistrantDay1() {
		return embRegistrantDay1;
	}

	/**
	 * @param embRegistrantDay1
	 *            the embRegistrantDay1 to set
	 */
	public void setEmbRegistrantDay1(String embRegistrantDay1) {
		this.embRegistrantDay1 = embRegistrantDay1;
	}

	/**
	 * @return the drRegistrantDay1
	 */
	public String getDrRegistrantDay1() {
		return drRegistrantDay1;
	}

	/**
	 * @param drRegistrantDay1
	 *            the drRegistrantDay1 to set
	 */
	public void setDrRegistrantDay1(String drRegistrantDay1) {
		this.drRegistrantDay1 = drRegistrantDay1;
	}

	/**
	 * @return the embRegistrantDay2
	 */
	public String getEmbRegistrantDay2() {
		return embRegistrantDay2;
	}

	/**
	 * @param embRegistrantDay2
	 *            the embRegistrantDay2 to set
	 */
	public void setEmbRegistrantDay2(String embRegistrantDay2) {
		this.embRegistrantDay2 = embRegistrantDay2;
	}

	/**
	 * @return the drRegistrantDay2
	 */
	public String getDrRegistrantDay2() {
		return drRegistrantDay2;
	}

	/**
	 * @param drRegistrantDay2
	 *            the drRegistrantDay2 to set
	 */
	public void setDrRegistrantDay2(String drRegistrantDay2) {
		this.drRegistrantDay2 = drRegistrantDay2;
	}

	/**
	 * @return the embRegistrantDay3
	 */
	public String getEmbRegistrantDay3() {
		return embRegistrantDay3;
	}

	/**
	 * @param embRegistrantDay3
	 *            the embRegistrantDay3 to set
	 */
	public void setEmbRegistrantDay3(String embRegistrantDay3) {
		this.embRegistrantDay3 = embRegistrantDay3;
	}

	/**
	 * @return the drRegistrantDay3
	 */
	public String getDrRegistrantDay3() {
		return drRegistrantDay3;
	}

	/**
	 * @param drRegistrantDay3
	 *            the drRegistrantDay3 to set
	 */
	public void setDrRegistrantDay3(String drRegistrantDay3) {
		this.drRegistrantDay3 = drRegistrantDay3;
	}

	/**
	 * @return the embRegistrantDay4
	 */
	public String getEmbRegistrantDay4() {
		return embRegistrantDay4;
	}

	/**
	 * @param embRegistrantDay4
	 *            the embRegistrantDay4 to set
	 */
	public void setEmbRegistrantDay4(String embRegistrantDay4) {
		this.embRegistrantDay4 = embRegistrantDay4;
	}

	/**
	 * @return the drRegistrantDay4
	 */
	public String getDrRegistrantDay4() {
		return drRegistrantDay4;
	}

	/**
	 * @param drRegistrantDay4
	 *            the drRegistrantDay4 to set
	 */
	public void setDrRegistrantDay4(String drRegistrantDay4) {
		this.drRegistrantDay4 = drRegistrantDay4;
	}

	/**
	 * @return the embRegistrantDay5
	 */
	public String getEmbRegistrantDay5() {
		return embRegistrantDay5;
	}

	/**
	 * @param embRegistrantDay5
	 *            the embRegistrantDay5 to set
	 */
	public void setEmbRegistrantDay5(String embRegistrantDay5) {
		this.embRegistrantDay5 = embRegistrantDay5;
	}

	/**
	 * @return the drRegistrantDay5
	 */
	public String getDrRegistrantDay5() {
		return drRegistrantDay5;
	}

	/**
	 * @param drRegistrantDay5
	 *            the drRegistrantDay5 to set
	 */
	public void setDrRegistrantDay5(String drRegistrantDay5) {
		this.drRegistrantDay5 = drRegistrantDay5;
	}

	/**
	 * @return the embRegistrantDay6
	 */
	public String getEmbRegistrantDay6() {
		return embRegistrantDay6;
	}

	/**
	 * @param embRegistrantDay6
	 *            the embRegistrantDay6 to set
	 */
	public void setEmbRegistrantDay6(String embRegistrantDay6) {
		this.embRegistrantDay6 = embRegistrantDay6;
	}

	/**
	 * @return the drRegistrantDay6
	 */
	public String getDrRegistrantDay6() {
		return drRegistrantDay6;
	}

	/**
	 * @param drRegistrantDay6
	 *            the drRegistrantDay6 to set
	 */
	public void setDrRegistrantDay6(String drRegistrantDay6) {
		this.drRegistrantDay6 = drRegistrantDay6;
	}

	/**
	 * @return the embRegistrantDay7
	 */
	public String getEmbRegistrantDay7() {
		return embRegistrantDay7;
	}

	/**
	 * @param embRegistrantDay7
	 *            the embRegistrantDay7 to set
	 */
	public void setEmbRegistrantDay7(String embRegistrantDay7) {
		this.embRegistrantDay7 = embRegistrantDay7;
	}

	/**
	 * @return the drRegistrantDay7
	 */
	public String getDrRegistrantDay7() {
		return drRegistrantDay7;
	}

	/**
	 * @param drRegistrantDay7
	 *            the drRegistrantDay7 to set
	 */
	public void setDrRegistrantDay7(String drRegistrantDay7) {
		this.drRegistrantDay7 = drRegistrantDay7;
	}

	/**
	 * @return the screenId
	 */
	public int getScreenId() {
		return screenId;
	}

	/**
	 * @param screenId
	 *            the screenId to set
	 */
	public void setScreenId(int screenId) {
		this.screenId = screenId;
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

}
