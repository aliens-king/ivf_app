package org.cf.card;

import java.util.List;
import java.util.Set;

import org.cf.card.dto.CycleDto;
import org.cf.card.model.EmbryoCode;
import org.cf.card.persistence.EmbryoCodeDao;
import org.cf.card.persistence.EmbryoDao;
import org.cf.card.util.EnumDayTable.Option;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class EmbryoCodeTest extends BaseTest {


	@Autowired
	private EmbryoCodeDao embryoCodeDao;

	@Autowired
	EmbryoDao embryoDao;



	//@Test
	public void count() {
		try {
			Integer count = embryoCodeDao.countEmbryoCodeByCodeId(1l);
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
	public void findByCodeIdAndDestiny(){
		List<EmbryoCode> aoEmbryoCode = embryoCodeDao.findThawedEggAndEmbryo(1l, Option.CRYO.getKey());
		System.out.println("  aoEmbryoCode   "+aoEmbryoCode.size());
		Assert.notEmpty(aoEmbryoCode, "collection can't be null");
		Assert.isTrue(aoEmbryoCode.size()==4, "Size shold be 4");
	}
	@Test
	public void findAllCyclesByCouple(){

		try{

			Set<CycleDto>  aoCycles = embryoDao.findWomanCycles(7l);

			for (CycleDto cycleDto : aoCycles) {

				System.out.println("cycle Dto = "+cycleDto);
			}

			System.out.println("  aoEmbryoCode   "+aoCycles.size());



			Assert.notEmpty(aoCycles, "collection can't be null");
			Assert.isTrue(aoCycles.size()==1, "Size shold be 1");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
