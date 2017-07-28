/**
 *
 */
package org.cf.card.dto;

import java.io.Serializable;

/**
 * @author Nikhil Mahajan
 *
 */
public class EmbryoDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long embryoId;
	private long codeId;
	private int injection;
	private Integer index;



	public EmbryoDto() {
	}

	public EmbryoDto(Long embryoId, long codeId, int injection, Integer index) {
		this.embryoId = embryoId;
		this.codeId = codeId;
		this.injection = injection;
		this.index = index;
	}

	public Long getEmbryoId() {
		return embryoId;
	}

	public void setEmbryoId(Long embryoId) {
		this.embryoId = embryoId;
	}

	public long getCodeId() {
		return codeId;
	}

	public void setCodeId(long codeId) {
		this.codeId = codeId;
	}

	public int getInjection() {
		return injection;
	}

	public void setInjection(int injection) {
		this.injection = injection;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
