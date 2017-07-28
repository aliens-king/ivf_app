/**
 * 
 */
package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.dto.PregnancyOutcomesDto;
import org.cf.card.model.Codes;
import org.cf.card.model.PregnancyOutcomes;
import org.cf.card.persistence.PregnancyOutcomesDao;
import org.cf.card.service.PregnancyOutcomesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author insonix
 *
 */
@Transactional
@Service("pregnancyOutcomesService")
public class PregnancyOutcomesServiceImpl extends BaseServiceImpl<PregnancyOutcomes>
		implements PregnancyOutcomesService {

	/** The remark dao. */
	protected PregnancyOutcomesDao pregnancyOutcomesDao;

	/**
	 * Sets the remark dao.
	 *
	 * @param remarkDao
	 *            the new remark dao
	 */
	@Autowired
	public final void setPregnancyOutcomesDao(PregnancyOutcomesDao pregnancyOutcomesDao) {
		this.pregnancyOutcomesDao = pregnancyOutcomesDao;
		setGenericDao(pregnancyOutcomesDao);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cf.card.service.PregnancyOutcomesService#save(org.cf.card.dto.
	 * PregnancyOutcomesDto)
	 */
	public PregnancyOutcomesDto save(PregnancyOutcomesDto outcomesDto) {
		PregnancyOutcomes pregnancyOutcomes = getPregnancyOutcomes(outcomesDto);
		PregnancyOutcomesDto dto = null;
		PregnancyOutcomes objectFromDB = null;
		if (null != pregnancyOutcomes)
			objectFromDB = pregnancyOutcomesDao.save(pregnancyOutcomes);
		if (null != objectFromDB)
			dto = getPregnancyDto(objectFromDB);
		return dto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.PregnancyOutcomesService#getPregnancyOutcome(java.
	 * lang.Long)
	 */
	public PregnancyOutcomesDto getPregnancyOutcome(Long womanCodeId) {
		PregnancyOutcomesDto pregnancyOutcomesDto = null;
		PregnancyOutcomes pregnancyOutcomes = pregnancyOutcomesDao.findPregnancyOutcomesByWomanCodeId(womanCodeId);
		if (null != pregnancyOutcomes) {
			pregnancyOutcomesDto = getPregnancyDto(pregnancyOutcomes);
		}
		return pregnancyOutcomesDto;
	}

	/**
	 * Gets the pregnancy dto object.
	 *
	 * @param pregnancyOutcomes
	 *            the pregnancy outcomes
	 * @return the pregnancy dto
	 */
	private PregnancyOutcomesDto getPregnancyDto(PregnancyOutcomes pregnancyOutcomes) {
		PregnancyOutcomesDto dto = null;
		if (null != pregnancyOutcomes) {
			dto = new PregnancyOutcomesDto();
			if (null != pregnancyOutcomes.getId())
				dto.setId(pregnancyOutcomes.getId());

			if (null != pregnancyOutcomes.getBiochemicalDate()) {

				dto.setBiochemical(pregnancyOutcomes.getBiochemical());
				dto.setBiochemicalOption(pregnancyOutcomes.getBiochemicalOption());
				dto.setBiochemicalDate(pregnancyOutcomes.getBiochemicalDate());
			}

			if (null != pregnancyOutcomes.getClinicalDate()) {
				dto.setClinical(pregnancyOutcomes.getClinical());
				dto.setClinicalOption(pregnancyOutcomes.getClinicalOption());
				dto.setClinicalDate(pregnancyOutcomes.getClinicalDate());
			}

			if (null != pregnancyOutcomes.getLivebirthDate()) {
				dto.setLivebirth(pregnancyOutcomes.getLivebirth());
				dto.setLivebirthOption(pregnancyOutcomes.getLivebirthOption());
				dto.setLivebirthDate(pregnancyOutcomes.getLivebirthDate());
			}
			if (null != pregnancyOutcomes.getRemarks())
				dto.setRemarks(pregnancyOutcomes.getRemarks());

			dto.setWomanCodeId(pregnancyOutcomes.getCode().getId());
		}
		return dto;
	}

	/**
	 * Gets the pregnancy outcomes object.
	 *
	 * @param pregnancyOutcomesDto
	 *            the pregnancy outcomes dto
	 * @return the pregnancy outcomes
	 */
	private PregnancyOutcomes getPregnancyOutcomes(PregnancyOutcomesDto pregnancyOutcomesDto) {

		PregnancyOutcomes pregnancyObject = null;

		if (null != pregnancyOutcomesDto) {
			pregnancyObject = new PregnancyOutcomes();
			if (null != pregnancyOutcomesDto.getId())
				pregnancyObject.setId(pregnancyOutcomesDto.getId());

			if (null != pregnancyOutcomesDto.getBiochemicalDate()) {
				pregnancyObject.setBiochemical(pregnancyOutcomesDto.getBiochemical());
				pregnancyObject.setBiochemicalOption(pregnancyOutcomesDto.getBiochemicalOption());
				pregnancyObject.setBiochemicalDate(pregnancyOutcomesDto.getBiochemicalDate());
			}
			if (null != pregnancyOutcomesDto.getClinicalDate()) {
				pregnancyObject.setClinical(pregnancyOutcomesDto.getClinical());
				pregnancyObject.setClinicalOption(pregnancyOutcomesDto.getClinicalOption());
				pregnancyObject.setClinicalDate(pregnancyOutcomesDto.getClinicalDate());
			}

			if (null != pregnancyOutcomesDto.getLivebirthDate()) {
				pregnancyObject.setLivebirth(pregnancyOutcomesDto.getLivebirth());
				pregnancyObject.setLivebirthOption(pregnancyOutcomesDto.getLivebirthOption());
				pregnancyObject.setLivebirthDate(pregnancyOutcomesDto.getLivebirthDate());
			}
			if (null != pregnancyOutcomesDto.getRemarks())
				pregnancyObject.setRemarks(pregnancyOutcomesDto.getRemarks());

			pregnancyObject.setCode(new Codes(pregnancyOutcomesDto.getWomanCodeId()));

		}
		return pregnancyObject;
	}
}
