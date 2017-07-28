package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.dto.EmbryoCodeDayDto;
import org.cf.card.model.EmbryoCode;
import org.cf.card.model.EmbryoCodeDay;
import org.cf.card.persistence.EmbryoCodeDayDao;
import org.cf.card.service.EmbryoCodeDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("embryoCodeDayService")
public class EmbryoCodeDayServiceImpl extends BaseServiceImpl<EmbryoCodeDay>implements EmbryoCodeDayService {

	/** The remark dao. */
	protected EmbryoCodeDayDao embryoCodeDayDao;

	/**
	 * Sets the remark dao.
	 *
	 * @param remarkDao
	 *            the new remark dao
	 */
	@Autowired
	public final void setEmbryoCodeDayDao(EmbryoCodeDayDao embryoCodeDayDao) {
		this.embryoCodeDayDao = embryoCodeDayDao;
		setGenericDao(embryoCodeDayDao);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cf.card.service.EmbryoCodeDayService#saveRemark(org.cf.card.dto.
	 * EmbryoCodeDayDto)
	 */
	@Override
	public EmbryoCodeDayDto saveRemark(EmbryoCodeDayDto embryoCodeDayDto) {
		EmbryoCodeDay embryoCodeDay = createEmbryoCodeDay(embryoCodeDayDto);
		
		EmbryoCodeDay codeDay = embryoCodeDayDao.save(embryoCodeDay);
		EmbryoCodeDayDto codeDayDto = createEmbryoCodeDayDto(codeDay);
		return codeDayDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cf.card.service.EmbryoCodeDayService#
	 * getRemarksByEmbryoCodeIdAndDayIndex(java.lang.Long, int)
	 */
	@Override
	public EmbryoCodeDayDto getRemarksByEmbryoCodeIdAndDayIndex(Long embryoCodeId, int remarkDayIndex) {
		EmbryoCodeDayDto embryoCodeDayDto = null;
		EmbryoCodeDay embryoCodeDay = remarksByEmbryoCodeIdAndDayIndex(embryoCodeId, remarkDayIndex);
		if (embryoCodeDay != null) {
			embryoCodeDayDto = createEmbryoCodeDayDto(embryoCodeDay);
		}
		return embryoCodeDayDto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.EmbryoCodeDayService#remarksByEmbryoCodeIdAndDayIndex
	 * (java.lang.Long, int)
	 */
	@Override
	public EmbryoCodeDay remarksByEmbryoCodeIdAndDayIndex(Long embryoCodeId, int remarkDayIndex) {
		return embryoCodeDayDao.findRemarksByEmbryoCodeIdAndDayIndex(embryoCodeId, remarkDayIndex);
	}

	// convert EmbryoCodeDay to EmbryoCodeDayDto object
	public EmbryoCodeDayDto createEmbryoCodeDayDto(EmbryoCodeDay embryoCodeDay) {
		EmbryoCodeDayDto embryoCodeDayDto = null;
		if (embryoCodeDay != null) {
			embryoCodeDayDto = new EmbryoCodeDayDto();
			embryoCodeDayDto.setId(embryoCodeDay.getId());
			embryoCodeDayDto.setDayIndex(embryoCodeDay.getDayIndex());
			embryoCodeDayDto.setEmbryoCodeId(embryoCodeDay.getEmbryoCode().getId());
			embryoCodeDayDto.setRemark(embryoCodeDay.getRemark());
		}

		return embryoCodeDayDto;
	}

	// convert EmbryoCodeDayDto to EmbryoCodeDay object
	public EmbryoCodeDay createEmbryoCodeDay(EmbryoCodeDayDto embryoCodeDayDto) {
		EmbryoCodeDay embryoCodeDay = new EmbryoCodeDay();
		embryoCodeDay.setDayIndex(embryoCodeDayDto.getDayIndex());
		embryoCodeDay.setEmbryoCode(new EmbryoCode(embryoCodeDayDto.getEmbryoCodeId()));
		embryoCodeDay.setRemark(embryoCodeDayDto.getRemark());
		if (embryoCodeDayDto.getId() != null) {
			embryoCodeDay.setId(embryoCodeDayDto.getId());
		}
		return embryoCodeDay;
	}

}
