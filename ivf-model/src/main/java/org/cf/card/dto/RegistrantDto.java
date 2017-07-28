/**
 * 
 */
package org.cf.card.dto;

/**
 * @author Pramod Maurya
 * @since 10-Jul-2017
 */
public class RegistrantDto {

	private Long id;

	private String nameOfUser;

	private String assistantUser;

	private int screenId;

	private Long codeId;
	
	/**
	 * 
	 */
	public RegistrantDto() {
		
	}

	/**
	 * @param nameOfUser
	 * @param assistantUser
	 */
	public RegistrantDto(Long registrantId, String nameOfUser, String assistantUser) {
		this.id = registrantId;
		this.nameOfUser = nameOfUser;
		this.assistantUser = assistantUser;
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
	 * @return the nameOfUser
	 */
	public String getNameOfUser() {
		return nameOfUser;
	}

	/**
	 * @param nameOfUser
	 *            the nameOfUser to set
	 */
	public void setNameOfUser(String nameOfUser) {
		this.nameOfUser = nameOfUser;
	}

	/**
	 * @return the assistantUser
	 */
	public String getAssistantUser() {
		return assistantUser;
	}

	/**
	 * @param assistantUser
	 *            the assistantUser to set
	 */
	public void setAssistantUser(String assistantUser) {
		this.assistantUser = assistantUser;
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
