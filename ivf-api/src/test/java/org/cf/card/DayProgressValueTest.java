package org.cf.card;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.EmbryoCode;
import org.cf.card.persistence.DayProgressValueDao;
import org.cf.card.service.DayProgressValueService;
import org.cf.card.service.EmbryoService;
import org.cf.card.util.EnumCycleType.CycleType;
import org.cf.card.util.EnumDayTable;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class DayProgressValueTest extends BaseTest {

	@Autowired
	EmbryoService embryoService;


	@Autowired
	DayProgressValueService dayProgressValueService;

	@Autowired
	DayProgressValueDao dayProgressValueDao;

	// @Test
	// public void init() {
	// System.out.println(embryoService);
	// }
	//
	// @Test
	// public void test() {
	// Map<Integer, List<DayProgressValue>> map =
	// embryoService.findDayProgressValuesByCodeId(3l);
	// System.out.println(map);
	//
	// }
	//
//	 @Test
	 public void findDayProgressValuesByCodeIdAndDestiny() {
	 Map<Long, Map<Integer, List<DayProgressValue>>> map = dayProgressValueService.findDayProgressValueMapByCodeIdAndDestiny(41l, 36);

	 for (Map.Entry<Long, Map<Integer, List<DayProgressValue>>> element :
	 map.entrySet()) {

	 System.out.println("EMbryoCodeId" + element.getKey());
	 Map<Integer, List<DayProgressValue>> value = element.getValue();
	 for (Map.Entry<Integer, List<DayProgressValue>> dayProgressValue : value.entrySet()) {
		 System.out.println("\t index" + dayProgressValue.getKey());
		 System.out.println("\t value" + dayProgressValue.getValue());

	 }

	 }
	 }

//	@Test
//	public void findDayProgressValuesByCodeId() {
//		Map<Long, Map<Integer, DayProgressValue>> map = dayProgressValueService.findDayProgressValueMapByCodeId(21l);
//
//		for (Map.Entry<Long, Map<Integer, DayProgressValue>> element : map.entrySet()) {
//
//			System.out.println("EMbryoCodeId" + element.getKey());
//			Map<Integer, DayProgressValue> value = element.getValue();
//			for (Map.Entry<Integer, DayProgressValue> dayProgressValue : value.entrySet()) {
//				System.out.println("\t index" + dayProgressValue.getKey());
//			}
//
//		}
//	}


	 //@Test
	 public void delete(){

		 List<DayProgressValue> aoDaysProgress = dayProgressValueDao.findByEmbryoCodeIdAndDayIndex(16l, 0);
			/*
			 * deleting the existing pop up values and saving the new selected
			 * values
			 */
			if (aoDaysProgress.size() > 0)
				dayProgressValueDao.delete(aoDaysProgress);
	 }
	// @Test
	public void init() {
		System.out.println(embryoService);
	}
	//@Test
	/*public void getByCoupleId() {
		List<DayProgressValue> ao = dayProgressValueService.findDayProgressValueByCodeIdAndModuleId(1l,13);
		System.out.println(ao);
		
		 * for (DayProgressValue dayProgressValue : ao) { String value ="";
		 * EmbryoCode embryoCode = dayProgressValue.getEmbryoCode(); for (int k
		 * = 0; k < 10; k++) { if (dayProgressValue.getDayIndex() == k) { value
		 * += EnumDayTable.Option.values()[ao.get(k).getDayOptionId() -
		 * 1].getName() +" "; }
		 *
		 * }
		 *
		 *
		 *
		 *
		 * System.out.println("embryoCode "+embryoCode + " value :"+value); }
		 

		for (int k = 0; k < 10; k++) {
			// DayProgressValue dayProgressValue = ao.get(k);
			String value = "";
			EmbryoCode embryoCode = null;
			for (DayProgressValue dayProgressValue : ao) {
				if (dayProgressValue.getDayIndex() == k) {
					value += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName() + " ";
					embryoCode = dayProgressValue.getEmbryoCode();
				}
			}
			if (null != embryoCode)
				System.out.println("day :" + k + " value :" + value + " code id: " + embryoCode.getCode().getCode());
		}

		Map<Integer, List<DayProgressValue>> aoMap = embryoService.findDayProgressValuesByCodeId(3l);

		for (Map.Entry<Integer, List<DayProgressValue>> entry : aoMap.entrySet()) {
			List<DayProgressValue> aoDay = entry.getValue();
			Integer code = entry.getKey();
			for (int k = 0; k < 10; k++) {
				String value = "";
				String type = "";
				for (DayProgressValue dayProgressValue : aoDay) {
					if (dayProgressValue.getEmbryoCode().getIndex() == entry.getKey() && dayProgressValue.getDayIndex() == k && dayProgressValue.getDayIndex()==0) {
						value += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName() + " ";
						type = "egg";
					}else if(dayProgressValue.getEmbryoCode().getIndex() == entry.getKey() && dayProgressValue.getDayIndex() == k && dayProgressValue.getDayIndex()>=0){
						value += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName() + " ";
						type = "embryo";
					}
				}
				if(value !=""){
					System.out.println("day :" + k + " value :" + value + " code id: " + code +" type :"+type);
					code = null;
				}//else if(value !="" && k!=0)
					//System.out.println("day :" + k + " value :" + value + " code id: ");
			}
		}

	}*/
	//@Test
	public void embryoCode(){
		List<EmbryoCode> ao = embryoService.findEmbryoCodeByClientId(2l);
		Collections.reverse(ao);
		List<DayProgressValue> aoDay =dayProgressValueService.findDayProgressValueByClientIdAndModuleId(2l, 13);
		//Collections.reverse(aoDay);
		int count =1;
		for (EmbryoCode embryoCode : ao) {
			String code = embryoCode.getCode().getCode();
			for (int k = 0; k < 10; k++) {
				String value = "";
				String type = "";
				for (DayProgressValue dayProgressValue : aoDay) {

					if (dayProgressValue.getDayIndex() == k && dayProgressValue.getEmbryoCode().getId() == embryoCode.getId() && dayProgressValue.getDayIndex()==0) {
						value += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName() + ".";
						type = "Egg";

					}else if(dayProgressValue.getDayIndex() == k && dayProgressValue.getEmbryoCode().getId() == embryoCode.getId() && dayProgressValue.getDayIndex()>=0){
						value += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName() + ".";
						type = "Embryo";

					}
				}
				String dateOfUses = "";
				String dateOfembryo = "";
				if(null != embryoCode){
					Date date = embryoCode.getDateOfUse();
					dateOfUses = Util.formatDate(IConstants.DATE_FORMAT, date);

					Date dateOfEmb = embryoCode.getDestinyDate();
					dateOfembryo = Util.formatDate(IConstants.DATE_FORMAT, dateOfEmb);
				}
				if(!value.isEmpty() && type.equals("Egg")){
					System.out.println(count+" count "+"day :" + k + " value :" + value + " code id: " + code +" type :"+type +" dateOfUses: "+dateOfUses);
					code = "";
				}else if(!value.isEmpty() && type.equals("Embryo")){
					System.out.println(count+" count "+"day :" + k + " value :" + value + " code id: " + code +" type :"+type +" dateOfembryo: "+dateOfembryo);
					code = "";
				}
				count++;
			}
		}
	}


	@Test
	public void findDayProgressValueByDayDateAndDestiny(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		//String from = "21-11-2015 10:20:56";
		String to = "01-12-2015 10:20:56";
		Date fromDate = null;
		
		String from = Util.formatDate(IConstants.DATE_FORMAT, new Date());
		
		Date toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate =  sdf.parse(to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		Date toDate =  Util.stringToDate(to, IConstants.DATE_FORMAT);
		List<DayProgressValue> dayProgressValuesList = dayProgressValueDao.getDayProgressValueByDayDateAndDestinyTest(new Date(), 39);

	//	List<DayProgressValue> dayProgressValuesList = dayProgressValueDao.findDayProgressValueByCodeIdAndDestiny(3l, 39);
		System.out.println("day List ***************************** \n " +dayProgressValuesList);
	}

//	@Test
	/*public void findDayProgressValueByCodeIdDestinyAndDateOfUseNotNull(){
		Map<Long, Map<Integer, List<DayProgressValue>>>  dayProgressValueMap	 = dayProgressValueService.findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(1l, 36);

		System.out.println("--Outer size-"+dayProgressValueMap.size());
		Assert.notEmpty(dayProgressValueMap," Map cannot b null");
		Assert.isTrue(dayProgressValueMap.size()==4," Map should be 4");

		for(Map.Entry<Long, Map<Integer, List<DayProgressValue>>> element : dayProgressValueMap.entrySet()){
			Map<Integer, List<DayProgressValue>> innerValue = element.getValue();

			System.out.println("--innerValue keys--"+innerValue.keySet());

			List<DayProgressValue> EMBRYOLOGY_OVERVIEW_LIST = innerValue.get(Module.EMBRYOLOGY_OVERVIEW.getKey());

			Assert.notEmpty(EMBRYOLOGY_OVERVIEW_LIST," EMBRYOLOGY_OVERVIEW List cannot b null");
			Assert.isTrue(EMBRYOLOGY_OVERVIEW_LIST.size()>1," EMBRYOLOGY_OVERVIEW_LIST size should > 1");

			List<DayProgressValue> EGG_THAWING_LIST = innerValue.get(Module.EGG_THAWING.getKey());
			//System.out.println("--Inner size--"+EGG_THAWING_LIST.size());
			Assert.isNull(EGG_THAWING_LIST," EGG_THAWING_LIST List cannot b null");
			//Assert.isTrue(EMBRYOLOGY_OVERVIEW_LIST.size()>1," EGG_THAWING_LIST size should > 1");


		}



	//	System.out.println("dayProgressValuesList test ************************************************** "+dayProgressValuesList );
	}*/

//	@Test
	public void findDayProgressValueByClientIdAndModuleId(){
		List<DayProgressValue> arr = dayProgressValueService.findDayProgressValueByClientIdAndModuleId(1l,13);
		System.out.println("--arr--"+arr.size());
		Assert.isTrue(arr.size()>0);
	}

//	@Test
	public void findDayIndexByCodeIdAndDestiny(){
		List<Map<String,Object>>  dayProgressValueList = dayProgressValueDao.findDayIndexByCodeIdAndDestiny(5l, 39);
//		System.out.println("dayProgressValue ----- "+dayProgressValueList);
	}


	//@Test
	public void findDayProgressValueMapForEmbryoThaw(){
		//List<Map<String,Object>>  dayProgressValueList = dayProgressValueService.findDayProgressValueMapForEmbryoThaw(5l);
//		System.out.println("dayProgressValue ----- "+dayProgressValueLis

		List<Long> newCycleEmbryoCode= new ArrayList<Long>();
		newCycleEmbryoCode.add(1l);
		newCycleEmbryoCode.add(7l);
		newCycleEmbryoCode.add(8l);
		
		// Saving List to map with Key and Value
		Map<Long, String> aoEggMap = new HashMap<Long, String>();
		Iterator<Long> aoEggListItr = newCycleEmbryoCode.listIterator();
		
		List<Long> aoEmbryoCodeId = new ArrayList<>();
		while (aoEggListItr.hasNext()) {
			Long embryoCodeValueDto = aoEggListItr.next();
			aoEggMap.put(embryoCodeValueDto, "ABC");
			aoEmbryoCodeId.add(embryoCodeValueDto);
		}
		Map<Long, Map<Integer, List<DayProgressValueDto>>> aoDayprogressValuesMap = dayProgressValueService
				.findDayProgressValueMapForEmbryoThaw(3l, CycleType.EMBRYO_THAW.getKey());
		for (Map.Entry<Long, Map<Integer, List<DayProgressValueDto>>> element : aoDayprogressValuesMap.entrySet()) {
			for (Long embryoCode : newCycleEmbryoCode) {
				System.out.println("=----------"+embryoCode);
				Map<Integer, List<DayProgressValueDto>> innerValue = element.getValue();
					for (Map.Entry<Integer, List<DayProgressValueDto>> innerElement : innerValue.entrySet()) {
						
						System.out.println("KEY: "+ innerElement.getKey());
						List<DayProgressValueDto> aodayprogressValueList =innerElement.getValue();
						Iterator<DayProgressValueDto> dayProgressValueListItr= aodayprogressValueList.listIterator();
						while (dayProgressValueListItr.hasNext()) {
							String embryoCodeValueDto = aoEggMap.get(1l);
							System.out.println(embryoCodeValueDto);
							DayProgressValue dayProgressValue= new DayProgressValue();
							DayProgressValueDto dayProgressValueDto = (DayProgressValueDto) dayProgressValueListItr.next();
							System.out.println("DayIndex :"+dayProgressValueDto.getDayIndex()+", EmbryoCodeIndex"+dayProgressValueDto.getEmbryoCodeIndex()
							+", DayOptionId"+dayProgressValueDto.getDayOptionId()+", EmbryoCodeId"+dayProgressValueDto.getEmbryoCodeId());
							
							
							dayProgressValue.setId(null);
							dayProgressValue.setDayColumnName(dayProgressValueDto.getDayColumnName());
							dayProgressValue.setDayDate(dayProgressValueDto.getDayDate());
							dayProgressValue.setDayIndex(dayProgressValueDto.getDayIndex());
							dayProgressValue.setDayOptionId(dayProgressValueDto.getDayOptionId());
							//TODO: 
							//dayProgressValue.setEmbryoCode(new EmbryoCode(embryoCode));
							dayProgressValue.setModuleId(dayProgressValueDto.getModuleId());
							
							
						}
						System.out.println("\n\n");
					}
				System.out.println("End of Inner loop");
			//	}
			}}
	}
		
		//@Test
		public void findDayProgressValueMapForEmbryoEgg(){
			//List<Map<String,Object>>  dayProgressValueList = dayProgressValueService.findDayProgressValueMapForEmbryoThaw(5l);
//			System.out.println("dayProgressValue ----- "+dayProgressValueLis
			List<Long> embryoCodeId = new ArrayList<>();
			embryoCodeId.add(1l);
			embryoCodeId.add(3l);
			embryoCodeId.add(4l);
			embryoCodeId.add(6l);
			
			
			
			

			List<DayProgressValue> aoDayProgressvalueList=embryoService.findDayProgressValueByEmbryoCodeIdAndDestiny(embryoCodeId, Option.CRYO.getKey());
			for (DayProgressValue dayProgressValue : aoDayProgressvalueList) {
			
				System.out.println("dayIndex: "+dayProgressValue.getDayIndex()+
									"   DayOptionId:" + dayProgressValue.getDayOptionId()+
									"   ID:"+dayProgressValue.getId()+
									"   EmbryoCodeId:"+dayProgressValue.getEmbryoCode().getId());
				

				
			}

		}
		
	
	
	
	
	

}
