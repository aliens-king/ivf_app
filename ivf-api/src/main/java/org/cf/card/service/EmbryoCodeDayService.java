package org.cf.card.service;

import org.cf.card.dto.EmbryoCodeDayDto;
import org.cf.card.model.EmbryoCodeDay;

public interface EmbryoCodeDayService extends BaseService<EmbryoCodeDay> {

	/**
	 * save or update EmbryoCodeDayRemarks object in database
	 * 
	 * @param embryoCodeDayDto
	 *            the embryo code day dto
	 * @return the embryo code day dto
	 */
	public EmbryoCodeDayDto saveRemark(EmbryoCodeDayDto embryoCodeDayDto);

	/**
	 * getting here EmbryoCodeDayDto object by embryoCodeId and DayIndex
	 * 
	 * @param embryoCodeId
	 *            the embryo code id
	 * @param remarkDayIndex
	 *            the remark day index
	 * @return the remarks by embryo code id and day index
	 */
	public EmbryoCodeDayDto getRemarksByEmbryoCodeIdAndDayIndex(Long embryoCodeId, int remarkDayIndex);

	/**
	 * getting here EmbryoCodeDayDto object by embryoCodeId and DayIndex
	 * 
	 * @param embryoCodeId
	 *            the embryo code id
	 * @param remarkDayIndex
	 *            the remark day index
	 * @return the remarks by embryo code id and day index
	 */
	public EmbryoCodeDay remarksByEmbryoCodeIdAndDayIndex(Long embryoCodeId, int remarkDayIndex);

}
