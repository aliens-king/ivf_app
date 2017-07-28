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
 * @since 10-Jul-2017
 */
@Entity
@Table(name="registrant")
public class Registrant implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8091365238879370906L;

	@Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
	private Long id;
	
    @Column(name = "username")
	private String nameOfUser;
	
    @Column(name = "assistant_username")
	private String assistantUser;
	
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
	 * @param id the id to set
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
	 * @param nameOfUser the nameOfUser to set
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
	 * @param assistantUser the assistantUser to set
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
	 * @param screenId the screenId to set
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
	 * @param code the code to set
	 */
	public void setCode(Codes code) {
		this.code = code;
	}
	
	

}
