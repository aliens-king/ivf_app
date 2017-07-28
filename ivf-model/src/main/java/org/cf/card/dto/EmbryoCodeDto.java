/**
 * 
 */
package org.cf.card.dto;

import java.util.List;

/**
 * @author Surinder
 *
 */
/**
 * @author insonix
 *
 */
public class EmbryoCodeDto {

	private List<EmbryoCodeValueDto> aoEgg;
	
	private List<EmbryoCodeValueDto> aoEmbryo;

	private long womanCodeId;
	
	public long getWomanCodeId() {
		return womanCodeId;
	}

	public void setWomanCodeId(long womanCodeId) {
		this.womanCodeId = womanCodeId;
	}

	public List<EmbryoCodeValueDto> getAoEgg() {
		return aoEgg;
	}

	public void setAoEgg(List<EmbryoCodeValueDto> aoEgg) {
		this.aoEgg = aoEgg;
	}

	public List<EmbryoCodeValueDto> getAoEmbryo() {
		return aoEmbryo;
	}

	public void setAoEmbryo(List<EmbryoCodeValueDto> aoEmbryo) {
		this.aoEmbryo = aoEmbryo;
	}
 	
	
	
}
