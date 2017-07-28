package org.cf.card.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.SemenCodeDto;
import org.cf.card.dto.SemenDataDto;
import org.cf.card.dto.SemenDto;
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
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.cf.card.vo.VoSemenCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Transactional
@Transactional
@Service("semenService")
public class SemenServiceImpl extends BaseServiceImpl<Semen>implements SemenService {

	protected SemenDao semenDao;

	@Autowired
	private SemenCodeDao semenCodeDao;

	@Autowired
	private SemenDataDao semenDataDao;

	@Autowired
	public final void setSemenDao(SemenDao semenDao) {
		this.semenDao = semenDao;
		setGenericDao(semenDao);
	}

	@Override
	public Semen addSemen(SemenDto semenDto) {
		// Long semenId = semenDto.getSemenId();
		// Long remarkId = semenDto.getRemarkId();
		Semen semen = saveSemen(semenDto);
		// //check if semen already exists
		// if(semenId != null && semenId != 0){
		// //delete existing semen
		// semenDao.delete(semenId);
		//
		// if(remarkId != null && remarkId != 0){
		// remarkDao.delete(remarkId);
		// }
		// }

		return semen;
	}

//	private Semen saveSemen(SemenDto semenDto) {
//
//		Long semenId = semenDto.getSemenId();
//		int screen = semenDto.getScreen();
//		// deleting old semen entries from database
//		if(semenId!=null && semenId !=0l){
//			semenCodeDao.deleteSemenCodeBySemenId(semenId);
//			semenDataDao.deleteSemenDataBySemenId(semenId);
//			semenDao.deleteSemenByIdAndScreen(semenId, screen);
//		}
//
//		// create new semen object
//		Semen semen = createSemen(semenDto);
//
//		// creating semen code object
//		Set<SemenCode> semenCodeSet = new HashSet<>();
//
//		SemenCode semenCode = createSemenCode(semenDto);
//		semen.setCreatedDate(new Date());
//		semenCode.setSemen(semen);
//		semenCodeSet.add(semenCode);
//		semen.setSemenCodes(semenCodeSet);
//
//		// creating semen Data object. In case of Semen Prep list size wil be 2 and in case of Andro it will be 1
//		List<SemenDataDto> semenDataList = semenDto.getSemenDataList();
//		Set<SemenData> semenDataSet = new HashSet<>();
//		for (SemenDataDto semenDataDto : semenDataList) {
//			SemenData semenData = createSemenData(semenDataDto);
//			semenData.setSemen(semen);
//			semenDataSet.add(semenData);
//			// semen.getSemenDatas().add(semenData);
//		}
//		semen.setSemenDatas(semenDataSet);
//
//		Semen retVal = semenDao.save(semen);
//		return retVal;
//	}


	private Semen saveSemen(SemenDto semenDto) {
		Long semenId = semenDto.getSemenId();

		Semen semen = createSemen(semenDto);

		if (semenDto.getScreen() == EnumPermission.Module.ANDROLOGY.getKey() && semenId != null && semenId != 0
				&& semen.getCreatedDate() != null) {
			// saving andrology data per date
			// if we are updating then bind semen id and delete semenDatas and
			// old remarks
			semen.setId(semenDto.getSemenId());
			semenDataDao.deleteSemenDataBySemenId(semenId);
		} else if (semenDto.getScreen() == EnumPermission.Module.SEMEN_PREPRATION.getKey() && semenId != null
				&& semenId != 0) {
			// semen prep data wouldn't be per date
			// if we are updating then bind semen id and delete semenDatas and
			// old remarks
			semen.setId(semenDto.getSemenId());
			semenDataDao.deleteSemenDataBySemenId(semenId);
		} else {
			// create semenCode only when we are inserting first time. Not when
			// we are updating
			Set<SemenCode> semenCodeSet = new HashSet<>();

			SemenCode semenCode = createSemenCode(semenDto);
			semen.setCreatedDate(new Date());
			semenCode.setSemen(semen);
			semenCodeSet.add(semenCode);
			semen.setSemenCodes(semenCodeSet);
		}

		// creating semen Data object
		List<SemenDataDto> semenDataList = semenDto.getSemenDataList();
		Set<SemenData> semenDataSet = new HashSet<>();
		for (SemenDataDto semenDataDto : semenDataList) {
			SemenData semenData = createSemenData(semenDataDto);
			semenData.setSemen(semen);
			semenDataSet.add(semenData);
			// semen.getSemenDatas().add(semenData);
		}
		semen.setSemenDatas(semenDataSet);

		Semen retVal = semenDao.save(semen);
		return retVal;
	}

	private Semen createSemen(SemenDto semenDto) {
		Semen semen = new Semen();
		semen.setUse(semenDto.getUse());
		semen.setMediaAdded(semenDto.getMediaAdded());
		semen.setViscosity(semenDto.getViscosity());
		semen.setAgglutination(semenDto.getAgglutination());
		semen.setDebris(semenDto.getDebris());
		semen.setAggregation(semenDto.getAggregation());
		semen.setTimeProduced(semenDto.getTimeProduced());
		semen.setTimeProcessed(semenDto.getTimeProcessed());
		semen.setScreen(semenDto.getScreen());
		semen.setCreatedDate(semenDto.getCreatedDate());
		semen.setRemark(semenDto.getRemarks());
		int totalSemens = semenDto.getTotalSemens();
		if (totalSemens > 0) {
			semen.setCryoVisibility(semenDto.getCryoVisibility());
			semen.setSemens(totalSemens);
		}
		return semen;
	}

	private SemenData createSemenData(SemenDataDto semenDataDto) {
		SemenData semenData = new SemenData();
		semenData.setVolume(semenDataDto.getVolume());
		semenData.setDensity(semenDataDto.getDensity());
		semenData.setMotilityA(semenDataDto.getMotilityA());
		semenData.setMotilityB(semenDataDto.getMotilityB());
		semenData.setMotilityC(semenDataDto.getMotilityC());
		semenData.setMotilityD(semenDataDto.getMotilityD());
		semenData.setMorphology(semenDataDto.getMorphology());
		semenData.setRoundCell(semenDataDto.getRoundCell());
		semenData.setType(semenDataDto.getType());
		return semenData;
	}

	private SemenCode createSemenCode(SemenDto semenDto) {
		// creating semen code object
		SemenCode semenCode = new SemenCode();
		Codes code = new Codes(semenDto.getCodeId());
		semenCode.setDateUsed(null);
		semenCode.setIndex(semenDto.getIndex());
		semenCode.setCode(code);

		return semenCode;
	}

	@Override
	public List<SemenCode> findByCodeId(Long codeId) {
		return semenCodeDao.findByCodeId(codeId);
	}

	@Override
	public List<SemenData> findBySemenId(Long id) {
		return semenDataDao.findBySemenId(id);
	}

	@Override
	public List<SemenCode> updateSemenCode(List<SemenCodeDto> aoSemenCodeDto) {
		List<SemenCode> aoSemenCode = new ArrayList<>();
		for (SemenCodeDto semenCodeDto : aoSemenCodeDto) {
			SemenCode semenCode = semenCodeDao.findOne(semenCodeDto.getSemenCodeId());

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = dateFormat.parse(semenCodeDto.getDateOfUsed());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			semenCode.setDateUsed(date);
			semenCodeDao.save(semenCode);
			aoSemenCode.add(semenCode);
		}

		return aoSemenCode;
	}

	public List<SemenCode> saveAll(int semens, long codeId) {

		List<SemenCode> aoSemenCode = new ArrayList<SemenCode>();

		for (int i = 1; i <= semens; i++) {
			SemenCode semenCode = new SemenCode();
			semenCode.setIndex(i);// the sequential number
			semenCode.setCode(new Codes(codeId));// foreign key
			semenCode.setSemen(new Semen()); // foreign key
			aoSemenCode.add(semenCode);
		}

		if (semens > 0) {
			// deleting old semens
			// embryoDao.deleteEmbryoByCodeId(codeId);
			// saving new semens
			aoSemenCode = semenCodeDao.save(aoSemenCode);
		}

		return aoSemenCode;
	}
	/*
	 * @Override public List<SemenCode> saveOrUpdateSemenCode(CryoSemenDto
	 * cryoSemenDto) { List<SemenCode> aoSemenCode = new ArrayList<>(); if(null
	 * != cryoSemenDto){ List<SemenCodeDto> aoSemenCodeDto =
	 * cryoSemenDto.getAoSemenCodeDto(); for (SemenCodeDto semenCodeDto :
	 * aoSemenCodeDto) { SemenCode semenCode =
	 * semenCodeDao.findOne(semenCodeDto.getSemenCodeId());
	 * aoSemenCode.add(semenCode); } Semen semen =
	 * semenDao.findOne(cryoSemenDto.getSemenId()); if(null != semen){ for (int
	 * i = 0; i < cryoSemenDto.getNoOfStraw(); i++) { SemenCode semenCode = new
	 * SemenCode();
	 *
	 * } }
	 *
	 *
	 * }
	 *
	 * return null; }
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.SemenService#saveSemenCode(org.cf.card.dto.
	 * CryoSemenDto)
	 */
	@Override
	public List<SemenCode> saveSemenCode(CryoSemenDto cryoSemenDto) {
		List<SemenCode> aoSemenCodes = new ArrayList<>();
		//System.out.println("--CRYO SEMEN CODE DTO--"+cryoSemenDto);
		if (null != cryoSemenDto) {

			// deleting old semen code
			semenCodeDao.deleteSemenCodeBySemenId(cryoSemenDto.getSemenId());
			semenCodeDao.flush();

			Codes manCode = new Codes(cryoSemenDto.getCodeId());
			Semen semen = semenDao.findOne(cryoSemenDto.getSemenId());
//			Semen semen = new Semen(cryoSemenDto.getSemenId());

			if (semen != null) {
				semen.setCryoVisibility(cryoSemenDto.getCryoVisibility());
				semen.setSemenCryoDate(cryoSemenDto.getCryoDate());
				semen.setSemens(cryoSemenDto.getTotalSemens());
				semenDao.save(semen);
			}
			for (int i = 1; i <= cryoSemenDto.getTotalSemens(); i++) {
				SemenCode newSemenCode = new SemenCode();
				newSemenCode.setCode(manCode);
				newSemenCode.setSemen(semen);
				newSemenCode.setIndex(i);
				SemenCode semenCodes = semenCodeDao.save(newSemenCode);
				aoSemenCodes.add(semenCodes);
			}
		}
		return aoSemenCodes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.SemenService#saveOrUpdateSemenCode(org.cf.card.dto.
	 * CryoSemenDto)
	 */
	@Override
	public List<SemenCode> saveOrUpdateSemenCode(CryoSemenDto cryoSemenDto) {
		List<SemenCode> aoSemenCode = new ArrayList<>();
		if (null != cryoSemenDto) {
			List<SemenCodeDto> aoSemenCodeDto = cryoSemenDto.getAoSemenCodeDto();
			for (SemenCodeDto semenCodeDto : aoSemenCodeDto) {
				SemenCode semenCode = semenCodeDao.findOne(semenCodeDto.getSemenCodeId());
				Date date = null;
				if (null != semenCodeDto.getDateOfUsed() && semenCodeDto.getDateOfUsed().length() > 0) {
					date = Util.stringToDate(semenCodeDto.getDateOfUsed(), IConstants.DATE_FORMAT);
				}
				String remarks = semenCodeDto.getRemarks();
				if (null != remarks && !remarks.isEmpty()) {
					semenCode.setCryoARemarks(remarks);
				}
				semenCode.setDateUsed(date);
				semenCodeDao.save(semenCode);
				aoSemenCode.add(semenCode);

			}
		}

		return aoSemenCode;
	}

	/*
	 * @Override public List<SemenData> findSemenDataByClientId(Long clientId) {
	 * return semenDataDao.findSemenDataByClientId(clientId);
	 *
	 * }
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.SemenService#findSemenCodeByClientId(java.lang.Long)
	 */
	@Override
	public List<SemenCode> findSemenCodeByClientId(Long clientId) {
		return semenCodeDao.findSemenCodeByClientId(clientId, Enumeration.SemenType.RAW.getKey());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.SemenService#findSemenCodeAndSemenDataByClientId(java
	 * .lang.Long)
	 */
	@Override
	public List<VoSemenCode> findSemenCodeAndSemenDataByClientId(Long clientId) {
		return semenCodeDao.findSemenCodeAndSemenDataByClientId(clientId, Enumeration.SemenType.RAW.getKey(),
				EnumPermission.Module.ANDROLOGY.getKey());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.SemenService#findSemenCodeByCodeIdAndScreenId(java.
	 * lang.Long, int)
	 */
	@Override
	public List<SemenCode> findSemenCodeByCodeIdAndScreenId(Long manCodeId, int screen) {
		return semenCodeDao.findSemenCodeByCodeIdAndScreenId(manCodeId, screen);
	}

	@Override
	public List<SemenCode> findSemenCodeByClientIdScreenAndDate(Long clientId, int screen, String createdDate) {
		Date date = Util.stringToDate(createdDate, IConstants.DATE_FORMAT);
		return semenCodeDao.findSemenCodeByClientIdScreenAndDate(clientId, screen, date);
	}

	@Override
	public Map<String, SemenDto> findSemenCodeMapByClientIdScreen(Long clientId, int screen) {
		List<VoSemenCode> voSemenCodes = this.findSemenCodeByClientIdScreen(clientId, screen);
		return getSemenMap(voSemenCodes);
	}

	private List<VoSemenCode> findSemenCodeByClientIdScreen(Long clientId, int screen) {
		return semenCodeDao.findSemenCodeByClientIdScreen(clientId, screen);
	}

	private Map<String, SemenDto> getSemenMap(List<VoSemenCode> voSemenCodes) {
		Map<String, SemenDto> semenMap = new LinkedHashMap<>();
		for (VoSemenCode voSemenCode : voSemenCodes) {
			Date createdDate = voSemenCode.getCreatedDate();
			String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, createdDate);

			SemenDto semenDto = createSemenDto(voSemenCode);
			// adding date & andrology data in semenDto
			semenMap.put(sCreatedDate, semenDto);
		}
		return semenMap;
	}

	private SemenDto createSemenDto(VoSemenCode voSemenCode) {
		SemenDto semenDto = new SemenDto();
		semenDto.setSemenId(voSemenCode.getSemenId());
		semenDto.setUse(voSemenCode.getUse());
		semenDto.setMediaAdded(voSemenCode.getMediaAdded());
		semenDto.setAgglutination(voSemenCode.getAgglutination());
		semenDto.setAggregation(voSemenCode.getAggregation());
		semenDto.setDebris(voSemenCode.getDebris());
		semenDto.setViscosity(voSemenCode.getViscosity());
		semenDto.setRemarks(voSemenCode.getRemarks());
		semenDto.setTimeProcessed(voSemenCode.getTimeProcessed());
		semenDto.setTimeProduced(voSemenCode.getTimeProduced());
		semenDto.setCreatedDate(voSemenCode.getCreatedDate());
		semenDto.setSemenCodeId(voSemenCode.getSemenCodeId());
		semenDto.setTotalSemens(voSemenCode.getTotalSemens());
		// semenDto.setCryoVisibility(voSemenCode.getCryoVisiblity());
		List<SemenDataDto> semenDataDtoList = new ArrayList<>();
		SemenDataDto semenDataDto = createSemenDataDto(voSemenCode);
		semenDataDtoList.add(semenDataDto);
		semenDto.setSemenDataList(semenDataDtoList);
		return semenDto;
	}

	private SemenDataDto createSemenDataDto(VoSemenCode voSemenCode) {
		SemenDataDto rawSemenDataDto = new SemenDataDto();
		rawSemenDataDto.setVolume(voSemenCode.getVolume());
		rawSemenDataDto.setDensity(voSemenCode.getDensity());
		rawSemenDataDto.setMotilityA(voSemenCode.getMotilityA());
		rawSemenDataDto.setMotilityB(voSemenCode.getMotilityB());
		rawSemenDataDto.setMotilityC(voSemenCode.getMotilityC());
		rawSemenDataDto.setMotilityD(voSemenCode.getMotilityD());
		rawSemenDataDto.setMorphology(voSemenCode.getMorphology());
		rawSemenDataDto.setRoundCell(voSemenCode.getRoundCell());
		return rawSemenDataDto;
	}

}
