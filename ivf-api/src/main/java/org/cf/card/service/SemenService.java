package org.cf.card.service;

import java.util.List;
import java.util.Map;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.SemenCodeDto;
import org.cf.card.dto.SemenDto;
import org.cf.card.model.Semen;
import org.cf.card.model.SemenCode;
import org.cf.card.model.SemenData;
import org.cf.card.vo.VoSemenCode;

public interface SemenService extends BaseService<Semen> {

	Semen addSemen(SemenDto semenDto);

	List<SemenCode> findByCodeId(Long codeId);

	List<SemenData> findBySemenId(Long id);

	List<SemenCode> updateSemenCode(List<SemenCodeDto> aoSemenCodeDto);

	List<SemenCode> saveSemenCode(CryoSemenDto cryoSemenDto);

	List<SemenCode> saveOrUpdateSemenCode(CryoSemenDto cryoSemenDto);

	List<SemenCode> findSemenCodeByClientId(Long clientId);

	List<VoSemenCode> findSemenCodeAndSemenDataByClientId(Long clientId);

	List<SemenCode> findSemenCodeByCodeIdAndScreenId(Long manCodeId, int screen);
	
	List<SemenCode> findSemenCodeByClientIdScreenAndDate(Long clientId, int screen, String createdDate);
	
	Map<String, SemenDto> findSemenCodeMapByClientIdScreen(Long manCodeId, int screen);
}
