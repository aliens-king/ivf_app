package org.cf.card;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.SemenCodeDto;
import org.cf.card.model.SemenCode;
import org.cf.card.persistence.SemenCodeDao;
import org.cf.card.service.SemenService;
import org.cf.card.vo.VoSemenCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SemenCodeTest extends BaseTest {

	@Autowired
	SemenService semenService;

	@Autowired
	SemenCodeDao semenCodeDao;

	// @Test
	public void test() {
		List<SemenCode> ao = semenService.saveSemenCode(init());
		for (SemenCode semenCode : ao) {
			System.out.println(semenCode.getId());
		}
	}

	public CryoSemenDto init() {

		List<SemenCodeDto> aoSemenCodeDto = new ArrayList<>();
		for (Integer i = 50; i < 51; i++) {
			SemenCodeDto semenCodeDto = new SemenCodeDto();
			semenCodeDto.setSemenCodeId(i.longValue());
			semenCodeDto.setDateOfUsed("");
			aoSemenCodeDto.add(semenCodeDto);
		}

		CryoSemenDto cryoSemenDto = new CryoSemenDto();
		cryoSemenDto.setTotalSemens(0);
		cryoSemenDto.setAoSemenCodeDto(aoSemenCodeDto);
		cryoSemenDto.setSemenId(2l);
		cryoSemenDto.setCodeId(116l);
		return cryoSemenDto;
	}

//	@Test
	public void save() {
		List<SemenCode> aoSemenCode = semenService.findByCodeId(120l);
		if (!aoSemenCode.isEmpty()) {
			List<SemenCodeDto> aoSemenCodeDto = new ArrayList<>();
			int semenValue = 2;
			for (SemenCode semenCode : aoSemenCode) {
				System.out.println("i " + semenCode.getId());
				SemenCodeDto semenCodeDto = new SemenCodeDto();
				semenCodeDto.setSemenCodeId(semenCode.getId());
				semenCodeDto.setDateOfUsed("");
				aoSemenCodeDto.add(semenCodeDto);
			}
			CryoSemenDto cryoSemenDto = new CryoSemenDto();
			cryoSemenDto.setTotalSemens(semenValue);
			cryoSemenDto.setAoSemenCodeDto(aoSemenCodeDto);
			cryoSemenDto.setSemenId(3l);
			cryoSemenDto.setCodeId(120l);

			if (semenValue != aoSemenCode.size()) {
				semenService.saveSemenCode(cryoSemenDto);
			} else {
				semenService.saveOrUpdateSemenCode(cryoSemenDto);
			}
		}
	}

	@Test
	public void findSemenCodeByCodeIdAndScreenId() {
		List<SemenCode> semenCodeList = semenCodeDao.findSemenCodeByCodeIdAndScreenId(10l, 16);
		System.out.println("semenCodeList **************** 111 22 " +semenCodeList.get(0).getSemen().getId());
	}

	@Test
	public void findSemenCodeByClientIdScreenAndDate(){
	
		List<SemenCode> semenCodeList = semenCodeDao.findSemenCodeByClientIdScreenAndDate(1l, 16, new Date());
		System.out.println("semenCodeList ****************" +semenCodeList);
	}
	
//	@Test
	public void findSemenCodeByClientIdScreen(){
		List<VoSemenCode> semenCodeList = semenCodeDao.findSemenCodeByClientIdScreen(7l, 16);
		for(VoSemenCode vo : semenCodeList){
			System.out.println("date ** " + vo.getCreatedDate());
		}
	}

	
}
