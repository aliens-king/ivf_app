/**
 * 
 */
package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.dto.RegistrantDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Registrant;
import org.cf.card.persistence.RegistrantDao;
import org.cf.card.service.RegistrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pramod Maurya
 * @since 10-Jul-2017
 */
@Service("registrantService")
@Transactional
public class RegistrantServiceImpl extends BaseServiceImpl<Registrant> implements RegistrantService {

	/** The Registrant dao. */
	@Autowired
	private RegistrantDao registrantDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.RegistrantService#saveRegistrant(org.cf.card.dto.
	 * RegistrantDto)
	 */
	@Override
	public RegistrantDto saveRegistrant(RegistrantDto registrantDto) {
		Registrant registrant = getRegistrantObj(registrantDto);
		if (null != registrant) {
			registrant = registrantDao.save(registrant);
			registrantDto = getRegistrantDtoObj(registrant);
		}
		return registrantDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.RegistrantService#getRegistrantByCodeAndScreenId(java
	 * .lang.Long, int)
	 */
	@Override
	public RegistrantDto getRegistrantByCodeAndScreenId(Long codeId, int screenId) {
		return registrantDao.findByCodeAndScreenId(codeId, screenId);
	}

	/**
	 * Gets the registrant obj.
	 *
	 * @param registrantDto
	 *            the registrant dto
	 * @return the registrant obj
	 */
	private Registrant getRegistrantObj(RegistrantDto registrantDto) {
		Registrant registrant = null;
		if (null != registrantDto) {
			registrant = new Registrant();
			if (null != registrantDto.getId()) {
				registrant.setId(registrantDto.getId());
			}
			registrant.setCode(new Codes(registrantDto.getCodeId()));
			registrant.setNameOfUser(registrantDto.getNameOfUser());
			registrant.setAssistantUser(registrantDto.getAssistantUser());
			registrant.setScreenId(registrantDto.getScreenId());
		}
		return registrant;
	}

	/**
	 * Gets the registrant dto obj.
	 *
	 * @param registrant
	 *            the registrant
	 * @return the registrant dto obj
	 */
	private RegistrantDto getRegistrantDtoObj(Registrant registrant) {
		RegistrantDto registrantDto = null;
		if (null != registrant) {
			registrantDto = new RegistrantDto();
			registrantDto.setId(registrant.getId());
			registrantDto.setCodeId(registrant.getCode().getId());
			registrantDto.setNameOfUser(registrant.getNameOfUser());
			registrantDto.setAssistantUser(registrant.getAssistantUser());
			registrantDto.setScreenId(registrant.getScreenId());
		}
		return registrantDto;

	}

}
