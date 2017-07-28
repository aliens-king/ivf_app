/**
 * 
 */
package org.cf.card.dto;

/**
 * The Class EmbryologyRegistrantDto.
 *
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
public class EmbryologyRegistrantDto {

	private Long id;

	private String embRegistrantDay0;
	private String drRegistrantDay0;

	private String embRegistrantDay1;
	private String drRegistrantDay1;

	private String embRegistrantDay2;
	private String drRegistrantDay2;

	private String embRegistrantDay3;
	private String drRegistrantDay3;

	private String embRegistrantDay4;
	private String drRegistrantDay4;

	private String embRegistrantDay5;
	private String drRegistrantDay5;

	private String embRegistrantDay6;
	private String drRegistrantDay6;

	private String embRegistrantDay7;
	private String drRegistrantDay7;

	private int screenId;

	private Long codeId;

	/**
	 * 
	 */
	public EmbryologyRegistrantDto() {

	}

	/**
	 * Instantiates a new embryology registrant dto.
	 *
	 * @param embRegistrantDay0
	 *            the emb registrant day 0
	 * @param drRegistrantDay0
	 *            the dr registrant day 0
	 * @param embRegistrantDay1
	 *            the emb registrant day 1
	 * @param drRegistrantDay1
	 *            the dr registrant day 1
	 * @param embRegistrantDay2
	 *            the emb registrant day 2
	 * @param drRegistrantDay2
	 *            the dr registrant day 2
	 * @param embRegistrantDay3
	 *            the emb registrant day 3
	 * @param drRegistrantDay3
	 *            the dr registrant day 3
	 * @param embRegistrantDay4
	 *            the emb registrant day 4
	 * @param drRegistrantDay4
	 *            the dr registrant day 4
	 * @param embRegistrantDay5
	 *            the emb registrant day 5
	 * @param drRegistrantDay5
	 *            the dr registrant day 5
	 * @param embRegistrantDay6
	 *            the emb registrant day 6
	 * @param drRegistrantDay6
	 *            the dr registrant day 6
	 * @param embRegistrantDay7
	 *            the emb registrant day 7
	 * @param drRegistrantDay7
	 *            the dr registrant day 7
	 */
	public EmbryologyRegistrantDto(Long id, String embRegistrantDay0, String drRegistrantDay0, String embRegistrantDay1,
			String drRegistrantDay1, String embRegistrantDay2, String drRegistrantDay2, String embRegistrantDay3,
			String drRegistrantDay3, String embRegistrantDay4, String drRegistrantDay4, String embRegistrantDay5,
			String drRegistrantDay5, String embRegistrantDay6, String drRegistrantDay6, String embRegistrantDay7,
			String drRegistrantDay7) {
		this.id = id;
		this.embRegistrantDay0 = embRegistrantDay0;
		this.drRegistrantDay0 = drRegistrantDay0;
		this.embRegistrantDay1 = embRegistrantDay1;
		this.drRegistrantDay1 = drRegistrantDay1;
		this.embRegistrantDay2 = embRegistrantDay2;
		this.drRegistrantDay2 = drRegistrantDay2;
		this.embRegistrantDay3 = embRegistrantDay3;
		this.drRegistrantDay3 = drRegistrantDay3;
		this.embRegistrantDay4 = embRegistrantDay4;
		this.drRegistrantDay4 = drRegistrantDay4;
		this.embRegistrantDay5 = embRegistrantDay5;
		this.drRegistrantDay5 = drRegistrantDay5;
		this.embRegistrantDay6 = embRegistrantDay6;
		this.drRegistrantDay6 = drRegistrantDay6;
		this.embRegistrantDay7 = embRegistrantDay7;
		this.drRegistrantDay7 = drRegistrantDay7;
	}

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
	 * @return the codeId
	 */
	public Long getCodeId() {
		return codeId;
	}

	/**
	 * @param codeId
	 *            the codeId to set
	 */
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

}
