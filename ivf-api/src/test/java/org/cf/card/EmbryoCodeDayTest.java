package org.cf.card;

import org.cf.card.dto.EmbryoCodeDayDto;
import org.cf.card.service.EmbryoCodeDayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmbryoCodeDayTest extends BaseTest{

	@Autowired
	EmbryoCodeDayService embryoCodeDayService;
	
	@Test
	public void getResult(){
		EmbryoCodeDayDto embryoCodeDayDto=new EmbryoCodeDayDto();
		embryoCodeDayDto = embryoCodeDayService.getRemarksByEmbryoCodeIdAndDayIndex(2l, 0);
		if (null != embryoCodeDayDto)
		{
			
			System.out.println(embryoCodeDayDto.getId()+"------"+embryoCodeDayDto.getEmbryoCodeId()+"----"
			+embryoCodeDayDto.getDayIndex()+"----"+embryoCodeDayDto.getRemark());
		}

	}
	
}
