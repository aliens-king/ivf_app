/**
 * 
 */
package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.dto.EmbryologyRegistrantDto;
import org.cf.card.model.Codes;
import org.cf.card.model.EmbryologyRegistrant;
import org.cf.card.persistence.EmbryoRegistrantDao;
import org.cf.card.service.EmbryoRegistrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
@Service("embryoRegistrantService")
@Transactional
public class EmbryoRegistrantServiceImpl extends BaseServiceImpl<EmbryologyRegistrant>
		implements EmbryoRegistrantService {

	@Autowired
	private EmbryoRegistrantDao embryoRegistrantDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.EmbryoRegistrantService#saveEmbryoRegistrant(org.cf.
	 * card.dto.EmbryologyRegistrantDto)
	 */
	@Override
	public EmbryologyRegistrantDto saveEmbryoRegistrant(EmbryologyRegistrantDto embryologyRegistrantDto) {
		EmbryologyRegistrant registrant = getEmbryologyRegistrantObj(embryologyRegistrantDto);
		if (null != registrant) {
			registrant = embryoRegistrantDao.save(registrant);
			embryologyRegistrantDto = getEmbryologyRegistrantDtoObj(registrant);
		}
		return embryologyRegistrantDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cf.card.service.EmbryoRegistrantService#
	 * getEmbryoRegistrantByCodeAndScreenId(java.lang.Long, int)
	 */
	@Override
	public EmbryologyRegistrantDto getEmbryoRegistrantByCodeAndScreenId(Long codeId, int screenId) {
		return embryoRegistrantDao.findByCodeAndScreenId(codeId, screenId);
	}

	/**
	 * Gets the embryology registrant obj.
	 *
	 * @param dto
	 *            the dto
	 * @return the embryology registrant obj
	 */
	private EmbryologyRegistrant getEmbryologyRegistrantObj(EmbryologyRegistrantDto dto) {

		EmbryologyRegistrant registrant = null;
		if (null != dto) {
			registrant = new EmbryologyRegistrant();
			if (null != dto.getId()) {
				registrant.setId(dto.getId());
			}
			registrant.setCode(new Codes(dto.getCodeId()));
			registrant.setScreenId(dto.getScreenId());
			registrant.setEmbRegistrantDay0(dto.getEmbRegistrantDay0());
			registrant.setDrRegistrantDay0(dto.getDrRegistrantDay0());
			registrant.setEmbRegistrantDay1(dto.getEmbRegistrantDay1());
			registrant.setDrRegistrantDay1(dto.getDrRegistrantDay1());
			registrant.setEmbRegistrantDay2(dto.getEmbRegistrantDay2());
			registrant.setDrRegistrantDay2(dto.getDrRegistrantDay2());
			registrant.setEmbRegistrantDay3(dto.getEmbRegistrantDay3());
			registrant.setDrRegistrantDay3(dto.getDrRegistrantDay3());
			registrant.setEmbRegistrantDay4(dto.getEmbRegistrantDay4());
			registrant.setDrRegistrantDay4(dto.getDrRegistrantDay4());
			registrant.setEmbRegistrantDay5(dto.getEmbRegistrantDay5());
			registrant.setDrRegistrantDay5(dto.getDrRegistrantDay5());
			registrant.setEmbRegistrantDay6(dto.getEmbRegistrantDay6());
			registrant.setDrRegistrantDay6(dto.getDrRegistrantDay6());
			registrant.setEmbRegistrantDay7(dto.getEmbRegistrantDay7());
			registrant.setDrRegistrantDay7(dto.getDrRegistrantDay7());

		}
		return registrant;

	}

	/**
	 * Gets the embryology registrant dto obj.
	 *
	 * @param embryologyRegistrant
	 *            the embryology registrant
	 * @return the embryology registrant dto obj
	 */
	private EmbryologyRegistrantDto getEmbryologyRegistrantDtoObj(EmbryologyRegistrant embryologyRegistrant) {

		EmbryologyRegistrantDto dto = null;
		if (null != embryologyRegistrant) {
			dto = new EmbryologyRegistrantDto();
			dto.setId(embryologyRegistrant.getId());
			dto.setCodeId(embryologyRegistrant.getCode().getId());
			dto.setScreenId(embryologyRegistrant.getScreenId());
			dto.setEmbRegistrantDay0(embryologyRegistrant.getEmbRegistrantDay0());
			dto.setDrRegistrantDay0(embryologyRegistrant.getDrRegistrantDay0());
			dto.setEmbRegistrantDay1(embryologyRegistrant.getEmbRegistrantDay1());
			dto.setDrRegistrantDay1(embryologyRegistrant.getDrRegistrantDay1());
			dto.setEmbRegistrantDay2(embryologyRegistrant.getEmbRegistrantDay2());
			dto.setDrRegistrantDay2(embryologyRegistrant.getDrRegistrantDay2());
			dto.setEmbRegistrantDay3(embryologyRegistrant.getEmbRegistrantDay3());
			dto.setDrRegistrantDay3(embryologyRegistrant.getDrRegistrantDay3());
			dto.setEmbRegistrantDay4(embryologyRegistrant.getEmbRegistrantDay4());
			dto.setDrRegistrantDay4(embryologyRegistrant.getDrRegistrantDay4());
			dto.setEmbRegistrantDay5(embryologyRegistrant.getEmbRegistrantDay5());
			dto.setDrRegistrantDay5(embryologyRegistrant.getDrRegistrantDay5());
			dto.setEmbRegistrantDay6(embryologyRegistrant.getEmbRegistrantDay6());
			dto.setDrRegistrantDay6(embryologyRegistrant.getDrRegistrantDay6());
			dto.setEmbRegistrantDay7(embryologyRegistrant.getEmbRegistrantDay7());
			dto.setDrRegistrantDay7(embryologyRegistrant.getDrRegistrantDay7());
		}
		return dto;
	}

}
