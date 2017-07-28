package org.cf.card;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cf.card.model.Codes;
import org.cf.card.model.Semen;
import org.cf.card.model.SemenCode;
import org.cf.card.model.SemenData;
import org.cf.card.persistence.SemenCodeDao;
import org.cf.card.persistence.SemenDao;
import org.cf.card.persistence.SemenDataDao;
import org.cf.card.service.SemenService;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.Enumeration;
import org.cf.card.vo.VoSemenCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class SemenTest extends BaseTest{

	@Autowired
	SemenService semenService;
	@Autowired
	SemenCodeDao semenCodeDao;
	@Autowired
	SemenDataDao semenDataDao;

	@Autowired
	SemenDao semenDao;

	//	@Test
	public void test() {
		System.out.println("---"+semenDao.findAll());
	}
//	@Test
	public void getSemenData(){

		List<SemenData> aoSemenData =  semenService.findBySemenId(1l);
		System.out.println("semen data "+aoSemenData);
	}

	public Semen constructSemen(){
		Semen semen = new Semen();
		//semen.setRemark(remark);
		semen.setUse(9);
		semen.setMediaAdded(9);
		semen.setViscosity(9);
		semen.setAgglutination(9);
		semen.setDebris(9);
		semen.setAggregation(9);
		semen.setTimeProduced("12:00");
		semen.setTimeProcessed("15:00");
		semen.setScreen(1);

		Set<SemenData> semenDataSet = new HashSet<>();

		SemenData semenData = createSemenData(Enumeration.SemenType.RAW.getKey());
		semenData.setSemen(semen);

		SemenData semenData1 = createSemenData(Enumeration.SemenType.PREPARED.getKey());
		semenData1.setSemen(semen);

		semenDataSet.add(semenData);
		semenDataSet.add(semenData1);

		Set<SemenCode> semenCodeSet = new HashSet<>();
		SemenCode semenCode = new SemenCode();
		semenCode.setDateUsed(null);
		semenCode.setIndex(1);
		semenCode.setCode(new Codes(1l));
		semenCode.setSemen(semen);
		semenCodeSet.add(semenCode);

		semen.setSemenCodes(semenCodeSet);
		semen.setSemenDatas(semenDataSet);

		return semen;
	}



	SemenData createSemenData(int key){
		SemenData semenData = new SemenData();
		semenData.setVolume(99);
		semenData.setDensity(99);
		semenData.setMotilityA(99);
		semenData.setMotilityB(99);
		semenData.setMotilityC(99);
		semenData.setMotilityD(99);
		semenData.setMorphology(99);
		semenData.setRoundCell(99);
		semenData.setType(key);

		return semenData;
	}

//	@Test
	public void deleteSemenByIdAndScreen(){

//		Semen semen = constructSemen();
//		semen = semenDao.save(semen);
//		System.out.println("---"+semen.getId());

//		semenDataDao.deleteSemenDataBySemenId(semenId);
//		semenDao.deleteSemenByIdAndScreen(4l, 1);
//		semenDao.delete(5l);
//		System.out.println("semen deleted ************** ");
	}

//	@Test
	public void updateSemen(){
		Semen semen = new Semen();
		semen.setId(5l);
		semen.setUse(99);
		semen.setMediaAdded(99);
		semen.setViscosity(99);
		semen.setAgglutination(99);
		semen.setDebris(99);
		semen.setAggregation(99);
		semen.setTimeProduced("01:00");
		semen.setTimeProcessed("03:00");
		semen.setScreen(1);

		Set<SemenData> semenDataSet = new HashSet<>();

		SemenData semenData = createSemenData(Enumeration.SemenType.RAW.getKey());
		semenData.setSemen(semen);

		SemenData semenData1 = createSemenData(Enumeration.SemenType.PREPARED.getKey());
		semenData1.setSemen(semen);

		semenDataSet.add(semenData);
		semenDataSet.add(semenData1);

		semen.setSemenDatas(semenDataSet);

		semenDataDao.deleteSemenDataBySemenId(semen.getId());

		semen = semenDao.save(semen);
		Assert.isTrue(semen.getSemenDatas().size() == 2);

	}

	@Test
	public void findSemenCodeAndSemenDataByClientId(){
		long clientId = 13l;
		List<VoSemenCode> aoVoSemenCode = semenCodeDao.findSemenCodeAndSemenDataByClientId(clientId, Enumeration.SemenType.RAW.getKey(),EnumPermission.Module.ANDROLOGY.getKey());

		for (VoSemenCode voSemenCode : aoVoSemenCode) {
			System.out.println("-----"+voSemenCode.getQuality());
		}
	}

//	@Test
	public void findSemenCodeByClientIdScreen(){
		long clientId = 13l;
		List<VoSemenCode> aoVoSemenCode = semenCodeDao.findSemenCodeByClientIdScreen(clientId, EnumPermission.Module.ANDROLOGY.getKey());
		for (VoSemenCode voSemenCode : aoVoSemenCode) {
			System.out.println("-----"+voSemenCode.getSemenCodeId() + " total semen  *** " +voSemenCode.getTotalSemens());
		}
	}

//	@Test
	public void findSemenCodeByCodeIdAndScreenId(){
		long codeId = 2l;
		List<SemenCode> aoVoSemenCode = semenCodeDao.findSemenCodeByCodeIdAndScreenId(codeId, EnumPermission.Module.ANDROLOGY.getKey());
		for (SemenCode voSemenCode : aoVoSemenCode) {
			System.out.println("-----"+voSemenCode.getSemen().getCryoVisibility() + " total semen  *** " +voSemenCode.getSemen().getSemens());
		}
	}



}
