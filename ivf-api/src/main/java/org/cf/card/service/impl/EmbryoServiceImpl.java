/**
 *
 */
package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cf.card.dto.CycleDto;
import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.EmbryoCodeDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.EmbryoDto;
import org.cf.card.dto.UIDayDto;
import org.cf.card.model.Codes;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.Embryo;
import org.cf.card.model.EmbryoCode;
import org.cf.card.model.Remark;
import org.cf.card.persistence.DayProgressValueDao;
import org.cf.card.persistence.EmbryoCodeDao;
import org.cf.card.persistence.EmbryoDao;
import org.cf.card.service.DayProgressValueService;
import org.cf.card.service.EmbryoService;
import org.cf.card.util.EnumCycleType.CycleType;
import org.cf.card.util.EnumDayTable;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The Class EmbryoServiceImpl.
 *
 * @author Nikhil Mahajan
 */

// @Transactional
@Service("embryoService")
public class EmbryoServiceImpl extends BaseServiceImpl<Embryo> implements EmbryoService {

	/** The embryo dao. */
	private EmbryoDao embryoDao;

	@Autowired
	private EmbryoCodeDao embryoCodeDao;

	@Autowired
	private DayProgressValueService dayProgressValueService;

	@Autowired
	private DayProgressValueDao dayProgressValueDao;

	/*
	 * @Autowired private RemarkService remarkService;
	 */

	/**
	 * Sets the embryo dao.
	 *
	 * @param embryoDao
	 *            the new embryo dao
	 */
	@Autowired
	public final void setEmbryoDao(EmbryoDao embryoDao) {
		this.embryoDao = embryoDao;
		setGenericDao(embryoDao);
	}

	@Override
	public List<Embryo> findEmbryoByCodeId(Long codeId) {
		return embryoDao.findByEmbryoCodesCodeId(codeId);
	}

	@Override
	public List<EmbryoCode> findByCodeId(Long codeId) {
		return embryoCodeDao.findByCodeId(codeId);
	}

	@Override
	public EmbryoCode findEmbryoCodeByEmbryoCodeId(Long embryoCodeId) {
		return embryoCodeDao.findOne(embryoCodeId);
	}

	@Override
	public List<EmbryoCode> saveAll(int oocytes, long codeId) {

		List<EmbryoCode> aoEmbryoCode = new ArrayList<EmbryoCode>();

		for (int i = 1; i <= oocytes; i++) {
			EmbryoCode embryoCode = new EmbryoCode();
			embryoCode.setIndex(i);// the sequential number
			embryoCode.setCode(new Codes(codeId));// foreign key
			embryoCode.setEmbryo(new Embryo()); // foreign key
			aoEmbryoCode.add(embryoCode);
		}

		if (oocytes > 0) {

			// deleting old embryos
			// embryoDao.deleteEmbryoByCodeId(codeId);
			// saving new embryos
			aoEmbryoCode = embryoCodeDao.save(aoEmbryoCode);
		}

		return aoEmbryoCode;
	}

	@Override
	public List<Embryo> saveEmbryos(int oocytes, long codeId) {
		List<Embryo> embryos = new ArrayList<>();
		/** Adding embryo existing object */
		List<Embryo> aoEmbryo = new ArrayList<>();

		List<EmbryoCode> aoEmbryoCode = embryoCodeDao.findByCodeId(codeId);
		for (EmbryoCode embryoCode : aoEmbryoCode) {
			Embryo embryo = embryoCode.getEmbryo();
			aoEmbryo.add(embryo);
		}
		/**
		 * Delete the existing object if user is editing the current oocyte
		 * value on current date (day 0)
		 */
		if (aoEmbryo.size() > 0)
			embryoDao.delete(aoEmbryo);
		for (int i = 1; i <= oocytes; i++) {
			Embryo embryo = new Embryo();
			Set<EmbryoCode> embryoCodes = new HashSet<>(1);
			// create a new embryo code by given code id, add that to a set and
			// set it into the embryo
			EmbryoCode embryoCode = new EmbryoCode();
			embryoCode.setIndex(i);// the sequential number
			embryoCode.setCode(new Codes(codeId));// foreign key
			embryoCode.setEmbryo(embryo); // foreign key
			embryoCodes.add(embryoCode);
			embryo.setEmbryoCodes(embryoCodes);
			// add embryo to main lists to be saved to db
			embryos.add(embryo);
		}
		if (embryos.size() > 0) {
			embryos = embryoDao.save(embryos);
		}
		return embryos;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.EmbryoService#saveCryoEmbryo(org.cf.card.dto.
	 * EmbryoCodeDto)
	 */
	@Override
	public void saveCryoEmbryoEgg(EmbryoCodeDto embryoCodeDto) {
		if (null != embryoCodeDto) {
			if (null != embryoCodeDto.getAoEgg() && embryoCodeDto.getAoEgg().size() > 0)
				saveCryoEggs(embryoCodeDto);
			if (null != embryoCodeDto.getAoEmbryo() && embryoCodeDto.getAoEmbryo().size() > 0)
				saveCryoEmbryo(embryoCodeDto);
		}
	}

	/*private void useCryoEggEmbryo(EmbryoCodeDto embryoCodeDto, List<EmbryoCodeValueDto> aoUsedEggEmbryo)
			throws CloneNotSupportedException {
		// List<Long> aoEmbryoCodeId = new ArrayList<>();
		// Saving List to map with Key and Value
		Map<Long, EmbryoCodeValueDto> moEmbryoCodeValueDto = new HashMap<Long, EmbryoCodeValueDto>();
		// creating a list of cryo embryoCodeIds to be used
		for (EmbryoCodeValueDto embryoCodeValueDto : aoUsedEggEmbryo) {
			// aoEmbryoCodeId.add(embryoCodeValueDto.getEmbryoCodeId());
			moEmbryoCodeValueDto.put(embryoCodeValueDto.getEmbryoCodeId(), embryoCodeValueDto);
		}
		List<EmbryoCode> aoEmbryoCode = embryoCodeDao.findAllEmbryoCodebyId(moEmbryoCodeValueDto.keySet());
		List<EmbryoCode> aoNewEmbryoCode = new ArrayList<>();

		List<DayProgressValue> aoDayProgressValues = dayProgressValueService
				.findByEmbryoCodeIdsAndDestiny(moEmbryoCodeValueDto.keySet(), Option.CRYO.getKey());

		int count = 1;
		for (EmbryoCode embryoCode : aoEmbryoCode) {
			count++;
			// creating new embryoCode
			EmbryoCodeValueDto embryoCodeValueDto = moEmbryoCodeValueDto.get(embryoCode.getId());
			EmbryoCode embryoCodeNew = embryoCode.clone();
			embryoCodeNew.setId(null);
			embryoCodeNew.setCode(new Codes(embryoCodeDto.getWomanCodeId()));
			embryoCodeNew.setIndex(count);
			embryoCodeNew.setDestinyDate(null);
			embryoCodeNew.setRemark(embryoCodeValueDto.getRemarks());

			// creating new list for dayprogressvalue
			List<DayProgressValue> filteredDpvs = aoDayProgressValues.stream()
					.filter(dpv -> dpv.getEmbryoCode().getId().equals(embryoCode.getId())).collect(Collectors.toList());

			aoNewEmbryoCode.add(embryoCodeNew);

			//
			// Getting Date of Use for Old Cycle
			Date date = null;
			if (StringUtils.isEmpty(embryoCodeValueDto.getDateOfUse())) {
				date = Util.stringToDate(embryoCodeValueDto.getDateOfUse(), IConstants.DATE_FORMAT);
			}
			// setting date of of use to old embryoCode object
			embryoCode.setDateOfUse(date);
		}

		// TODO: check if date is use is setting inot database if not then use
		// separate list of old embryoCodes
		embryoCodeDao.save(aoEmbryoCode);
		embryoCodeDao.save(aoNewEmbryoCode);

	}
*/
	/**
	 * Save cryo eggs.
	 *
	 * @param aoCryoEggs
	 *            the ao cryo eggs
	 */
	private void saveCryoEggs(EmbryoCodeDto embryoCodeDto) {
		List<Embryo> newEmbryos = new ArrayList<>();
		List<EmbryoCode> aoEmbryoCode = null;
		List<EmbryoCode> aoEmbryoCodeNewCycleSaveList = new ArrayList<EmbryoCode>();
		List<EmbryoCode> aoEmbryoCodeOldCycleUpdateList = new ArrayList<EmbryoCode>();
		List<EmbryoCodeValueDto> aoCryoEggs = embryoCodeDto.getAoEgg();

		// Saving List to map with Key and Value
		Map<Long, EmbryoCodeValueDto> aoEggMap = new HashMap<Long, EmbryoCodeValueDto>();
		Iterator<EmbryoCodeValueDto> aoEggListItr = aoCryoEggs.listIterator();

		List<Long> aoEmbryoCodeId = new ArrayList<>();
		while (aoEggListItr.hasNext()) {
			EmbryoCodeValueDto embryoCodeValueDto = aoEggListItr.next();
			aoEggMap.put(embryoCodeValueDto.getEmbryoCodeId(), embryoCodeValueDto);
			aoEmbryoCodeId.add(embryoCodeValueDto.getEmbryoCodeId());
		}

		// getting all EmbryoCode from Database by send ebryoCodeId.
		aoEmbryoCode = embryoCodeDao.findAllEmbryoCodebyId(aoEmbryoCodeId);

		Iterator<EmbryoCode> aoEmbryoCodeItr = aoEmbryoCode.iterator();
		int count = 1;
		while (aoEmbryoCodeItr.hasNext()) {

			EmbryoCode embryoCodeNewCycle = aoEmbryoCodeItr.next();

			// Cloning the real object embryoCodeNewCycle to embryoCodeOldCycle
			EmbryoCode embryoCodeOldCycle = null;
			try {
				embryoCodeOldCycle = embryoCodeNewCycle.clone();
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
			EmbryoCodeValueDto embryoCodeValueDto = aoEggMap.get(embryoCodeNewCycle.getId());

			Embryo embryo = new Embryo();
			Set<EmbryoCode> embryoCodes = new HashSet<>(1);
			// changin primary key to null so that we get new inserts in
			// database
			embryoCodeNewCycle.setId(null);
			embryoCodeNewCycle.setIndex(count);

			embryoCodeNewCycle.setCode(new Codes(embryoCodeDto.getWomanCodeId()));
			embryoCodeNewCycle.setDestinyDate(null);
			embryoCodeNewCycle.setEmbryo(embryo); // foreign key
			embryoCodes.add(embryoCodeNewCycle);
			embryo.setEmbryoCodes(embryoCodes);
			// add embryo to main lists to be saved to db
			newEmbryos.add(embryo);

			// Getting Date of Use for for Old Cycle
			Date date = null;
			if (StringUtils.isEmpty(embryoCodeValueDto.getDestinyDate())) {
				try {
					String stringDate = embryoCodeValueDto.getDateOfUse();
					if (null != stringDate && !stringDate.isEmpty()) {
						date = Util.stringToDate(stringDate, IConstants.DATE_FORMAT);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Setting Date of Use for Old Cycle
			embryoCodeOldCycle.setDateOfUse(date);
			if (null != embryoCodeValueDto.getRemarks())
				embryoCodeOldCycle.setRemark(embryoCodeValueDto.getRemarks());

			aoEmbryoCodeNewCycleSaveList.add(embryoCodeNewCycle);
			aoEmbryoCodeOldCycleUpdateList.add(embryoCodeOldCycle);
			count++;
		}
		embryoCodeDao.save(aoEmbryoCodeOldCycleUpdateList);
		if (newEmbryos.size() > 0) {
			newEmbryos = embryoDao.save(newEmbryos);
		}
		// embryoCodeDao.save(aoEmbryoCodeNewCycleSaveList);
	}

/*	private void saveDayProgressValuesForCryoEggEmbryo() {

	}*/

	/**
	 * Save cryo embryo.
	 *
	 * @param aoCryoEmbryo
	 *            the ao cryo embryo
	 */
	private void saveCryoEmbryo(EmbryoCodeDto embryoCodeDto) {

		// List of new values which save in EmbryoCode Table as a new rows
		List<EmbryoCode> aoEmbryoCodeNewCycleSaveList = new ArrayList<EmbryoCode>();
		// List of old values which update in EmbryoCode Table as date_of_use
		// update
		List<EmbryoCode> aoEmbryoCodeOldCycleUpdateList = new ArrayList<EmbryoCode>();
		// getting list of selected embryos from CRYO-E through which we create
		// new cycle
		List<EmbryoCodeValueDto> aoCryoEmbryo = embryoCodeDto.getAoEmbryo();

		// List of All woman_code_id of same couple from Old Cycles
		List<Long> aoWomanCodeId = new ArrayList<Long>();

		// Saving List to map with Key and Value
		Map<Long, EmbryoCodeValueDto> aoEmbryoMap = new HashMap<Long, EmbryoCodeValueDto>();
		Iterator<EmbryoCodeValueDto> aoEmbryoListItr = aoCryoEmbryo.listIterator();
		List<Long> aoEmbryoCodeId = new ArrayList<>();
		while (aoEmbryoListItr.hasNext()) {
			EmbryoCodeValueDto embryoCodeValueDto = aoEmbryoListItr.next();
			aoEmbryoMap.put(embryoCodeValueDto.getEmbryoCodeId(), embryoCodeValueDto);
			aoEmbryoCodeId.add(embryoCodeValueDto.getEmbryoCodeId());
		}

		// getting all EmbryoCode from Database by send embryoCodeId.
		List<EmbryoCode> aoEmbryoCode = embryoCodeDao.findAllEmbryoCodebyId(aoEmbryoCodeId);
		Iterator<EmbryoCode> aoEmbryoCodeItr = aoEmbryoCode.iterator();
		EmbryoCode embryoCodeId = new EmbryoCode();
		while (aoEmbryoCodeItr.hasNext()) {
			EmbryoCode embryoCodeNewCycle = aoEmbryoCodeItr.next();

			// Cloning the real object from DB embryoCodeNewCycle to
			// embryoCodeOldCycle
			EmbryoCode embryoCodeOldCycle = null;
			try {
				embryoCodeOldCycle = embryoCodeNewCycle.clone();
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
			EmbryoCodeValueDto embryoCodeValueDto = aoEmbryoMap.get(embryoCodeNewCycle.getId());
			// channgind primary key to null so that we get new inserts in
			// database
			embryoCodeNewCycle.setId(null);
			// updating foreign key of code to set new cyle code
			embryoCodeNewCycle.setCode(new Codes(embryoCodeDto.getWomanCodeId()));
			embryoCodeNewCycle
					.setDateOfUse(Util.stringToDate(embryoCodeValueDto.getDateOfUse(), IConstants.DATE_FORMAT));
			Date date = null;
			if (StringUtils.isEmpty(embryoCodeValueDto.getDestinyDate())) {
				try {
					String stringDate = embryoCodeValueDto.getDateOfUse();
					if (null != stringDate && !stringDate.isEmpty()) {
						date = Util.stringToDate(stringDate, IConstants.DATE_FORMAT);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			embryoCodeOldCycle.setDateOfUse(date);
			aoWomanCodeId.add(embryoCodeOldCycle.getCode().getId());
			if (null != embryoCodeValueDto.getRemarks())
				embryoCodeOldCycle.setRemark(embryoCodeValueDto.getRemarks());
			embryoCodeId.setId(embryoCodeOldCycle.getCode().getId());
			aoEmbryoCodeNewCycleSaveList.add(embryoCodeNewCycle);
			aoEmbryoCodeOldCycleUpdateList.add(embryoCodeOldCycle);
		}
		// Updating existing row by value date_of_used
		embryoCodeDao.save(aoEmbryoCodeOldCycleUpdateList);

		// List Id's of existing embryoCode
		List<Long> aoEmbryoCodeIds = new ArrayList<Long>();
		for (EmbryoCode embryoCode : aoEmbryoCodeOldCycleUpdateList) {
			Long id = embryoCode.getId();
			aoEmbryoCodeIds.add(id);
		}
		List<Integer> aoCycleType = new ArrayList<Integer>();
		aoCycleType.add(CycleType.NORMAL.getKey());
		aoCycleType.add(CycleType.EGG_THAW.getKey());

		// Saving new entries in EmbryoCode table
		List<EmbryoCode> aoEmbryoCodesOfNewCycleFromDB = embryoCodeDao.save(aoEmbryoCodeNewCycleSaveList);

		// Passing Old WomanCodeId
		// Map<Long, Map<Integer, List<DayProgressValueDto>>>
		// aoDayprogressValuesMap=
		// dayProgressValueService.findDayProgressValueMapForEmbryoThaw(embryoCodeId.getId(),
		// cycleType);
		// converting List to Map
		/*
		 * Map<Long, List<DayProgressValue>> aoEmbryoCodesOfNewCycleMap= new
		 * HashMap<Long, List<DayProgressValue>>(); Iterator<EmbryoCode>
		 * embryoCodeListItr = aoEmbryoCodesOfNewCycleFromDB.listIterator();
		 * while (embryoCodeListItr.hasNext()) { EmbryoCode embryoCode =
		 * (EmbryoCode) embryoCodeListItr.next();
		 * aoEmbryoCodesOfNewCycleMap.put(embryoCode.getId(), new
		 * ArrayList<DayProgressValue>());
		 * 
		 * }
		 */

		// Passing old cycle's womanCodeId, embryoCodeId and cycleTypes
		Map<Long, Map<Integer, List<DayProgressValueDto>>> aoDayprogressValuesMapForEmbryoThaw = dayProgressValueService
				.findDayProgressValueMapForEmbryoThawFromOldCycle(aoWomanCodeId, aoCycleType, aoEmbryoCodeIds);

		List<DayProgressValue> aoDayProgressValue = new ArrayList<DayProgressValue>();
		int i = 0;
		for (Map.Entry<Long, Map<Integer, List<DayProgressValueDto>>> element : aoDayprogressValuesMapForEmbryoThaw
				.entrySet()) {
			Map<Integer, List<DayProgressValueDto>> innerValue = element.getValue();

			for (Map.Entry<Integer, List<DayProgressValueDto>> innerElement : innerValue.entrySet()) {
				EmbryoCode embryoCode = aoEmbryoCodesOfNewCycleFromDB.get(i);
				List<DayProgressValueDto> aodayprogressValueList = innerElement.getValue();
				Iterator<DayProgressValueDto> dayProgressValueListItr = aodayprogressValueList.listIterator();
				while (dayProgressValueListItr.hasNext()) {

					DayProgressValue dayProgressValue = new DayProgressValue();
					DayProgressValueDto dayProgressValueDto = (DayProgressValueDto) dayProgressValueListItr.next();

					// System.out.println("DayIndex
					// :"+dayProgressValueDto.getDayIndex()+",
					// EmbryoCodeIndex"+dayProgressValueDto.getEmbryoCodeIndex()
					// +", DayOptionId"+dayProgressValueDto.getDayOptionId()+",
					// EmbryoCodeId"+dayProgressValueDto.getEmbryoCodeId());

					dayProgressValue.setId(null);
					dayProgressValue.setDayColumnName(dayProgressValueDto.getDayColumnName());
					dayProgressValue.setDayDate(new Date());
					dayProgressValue.setDayIndex(dayProgressValueDto.getDayIndex());
					dayProgressValue.setDayOptionId(dayProgressValueDto.getDayOptionId());
					// TODO:
					dayProgressValue.setEmbryoCode(new EmbryoCode(embryoCode.getId()));
					dayProgressValue.setModuleId(dayProgressValueDto.getModuleId());
					aoDayProgressValue.add(dayProgressValue);

				}
			}
			++i;
		}
		dayProgressValueDao.save(aoDayProgressValue);

	}

	@Override
	public List<DayProgressValue> saveDayProgressValue(UIDayDto uiDayDto) {
		List<DayProgressValue> aoValue = new ArrayList<>();
		List<DayProgressValue> aoDaysProgress = dayProgressValueDao
				.findByEmbryoCodeIdAndDayIndex(uiDayDto.getEmbryoCodeId(), uiDayDto.getDayIndex());
		/*
		 * deleting the existing pop up values and saving the new selected
		 * values
		 */
		if (aoDaysProgress.size() > 0)
			dayProgressValueDao.delete(aoDaysProgress);
		Map<Integer, String> dayColumnKeyValue = uiDayDto.getDayColumnKeyValue();
		Date destinyDate = null;
		/* Mapping key and value of day table columns */
		for (Map.Entry<Integer, String> entry : dayColumnKeyValue.entrySet()) {
			DayProgressValue dayProgressValue = new DayProgressValue();
			dayProgressValue.setDayDate(uiDayDto.getDayDate());
			dayProgressValue.setDayIndex(uiDayDto.getDayIndex());
			dayProgressValue.setModuleId(uiDayDto.getModuleId());
			EmbryoCode embryoCode = new EmbryoCode(uiDayDto.getEmbryoCodeId());
			dayProgressValue.setEmbryoCode(embryoCode);
			Integer id = Integer.valueOf(entry.getValue());
			Option option = Option.getOptionByKey(id);
			if (EnumDayTable.getDestiny().contains(option)) {
				destinyDate = new Date();
			}
			dayProgressValue.setDayOptionId(id);
			dayProgressValue.setDayColumnName(entry.getKey() + "");
			aoValue.add(dayProgressValue);
		}
		if (aoValue.size() > 0)
			aoValue = dayProgressValueDao.save(aoValue);

		EmbryoCode embryoCode = embryoCodeDao.findOne(uiDayDto.getEmbryoCodeId());
		embryoCode.setDestinyDate(destinyDate);
		embryoCodeDao.save(embryoCode);
		return aoValue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.EmbryoService#findByEmbryoCodeIdAndDayIndex(java.lang
	 * .Long, int)
	 */
	@Override
	public List<DayProgressValue> findByEmbryoCodeIdAndDayIndex(Long embryoCodeid, int datIndex) {
		return dayProgressValueDao.findByEmbryoCodeIdAndDayIndex(embryoCodeid, datIndex);
	}

	public EmbryoCode addEmbryoTransfer(EmbryoCodeValueDto embryoCodeValueDto) {
		Remark remark = new Remark();
		remark.setRemark(embryoCodeValueDto.getRemarks());
		remark.setType(embryoCodeValueDto.getRemarkType());
		// Remark sRemark = remarkService.save(remark);

		// updating
		EmbryoCode saveEmbryoCode = null;
		List<EmbryoCode> embryoETList = embryoCodeValueDto.getEmbryoETList();
		if (embryoETList != null && embryoETList.size() > 0) {
			for (EmbryoCode embryoET : embryoETList) {
				EmbryoCode updateEmbryoCode = embryoCodeDao.findOne(embryoET.getId());
				updateEmbryoCode.setCatheter(embryoCodeValueDto.getCatheter());
				updateEmbryoCode.setTime(embryoCodeValueDto.getTime());
				updateEmbryoCode.setType(embryoCodeValueDto.getType());
				saveEmbryoCode = embryoCodeDao.save(updateEmbryoCode);
			}
		}
		return saveEmbryoCode;
	}

	@Override
	public List<EmbryoDto> updateEmbryos(List<EmbryoDto> aoEmbryoDto) {

		List<Embryo> aoEmbryo = new ArrayList<Embryo>();

		for (EmbryoDto embryoDto : aoEmbryoDto) {
			Embryo embryo = new Embryo(embryoDto.getEmbryoId(), embryoDto.getInjection());
			aoEmbryo.add(embryo);
		}
		embryoDao.save(aoEmbryo);

		return aoEmbryoDto;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.EmbryoService#findEmbryoCodeByClientId(java.lang.
	 * Long)
	 */
	@Override
	public List<EmbryoCode> findEmbryoCodeByClientId(Long clientId) {

		return embryoCodeDao.findEmbryoCodeByClientId(clientId);
	}

	/*
	 * @Override public Set<CycleDto> findEmbryoCyclesByWoman(Long
	 * womanClientId) {
	 *
	 * Set<CycleDto> soCycleDto = new HashSet<>(); // try{ soCycleDto =
	 * embryoDao.findWomanCycles(womanClientId);
	 *
	 * // }catch(InvalidDataAccessApiUsageException i){ //
	 * System.out.println(i.getMessage()); // }
	 *
	 * return soCycleDto; }
	 *
	 * @Override public Set<VoSemenCode> findSemenCyclesByMan(Long manClientId)
	 * {
	 *
	 * Set<VoSemenCode> soVoSemenCode = new HashSet<>(); // try{
	 *
	 * soVoSemenCode = embryoDao.findPartnerCycles(manClientId); //
	 * }catch(InvalidDataAccessApiUsageException i){ //
	 * System.out.println(i.getMessage()); // }
	 *
	 * return soVoSemenCode; }
	 */

	public Collection<CycleDto> findCoupleCycles(Long womanClientId, Long partnerClientId) {
		Map<String, CycleDto> cycleMap = new HashMap<>();

		Set<CycleDto> womanCycles = findEmbryoCyclesByWoman(womanClientId);
		for (CycleDto womanCycle : womanCycles) {
			String wCoupleStartDateKey = womanCycle.getCoupleId() + "->" + womanCycle.getStartDate();
			// CycleDto womanCycleDto = createWomanCyclesDto(womanCycle);

			// adding woman cyclesDto in map where key = coupleId & startDate
			cycleMap.put(wCoupleStartDateKey, womanCycle);
		}

		Set<CycleDto> partnerCycles = findSemenCyclesByMan(partnerClientId);
		for (CycleDto partnerCycle : partnerCycles) {
			String pCoupleStartDateKey = partnerCycle.getCoupleId() + "->" + partnerCycle.getStartDate();
			// if couple Id & start Date already exists for partner then get the
			// cycleDto to add partner cycle data
			CycleDto cycleDto = new CycleDto();
			if (cycleMap.containsKey(pCoupleStartDateKey)) {
				cycleDto = cycleMap.get(pCoupleStartDateKey);
			}

			// updating cycleDto with partner Cycle data
			CycleDto updatedCycleDto = updateCycleDto(cycleDto, partnerCycle);
			cycleMap.put(pCoupleStartDateKey, updatedCycleDto);
		}

		Collection<CycleDto> cycleDtoSet = cycleMap.values();
		return cycleDtoSet;
	}

	private Set<CycleDto> findEmbryoCyclesByWoman(Long womanClientId) {
		return embryoDao.findWomanCycles(womanClientId);
	}

	private Set<CycleDto> findSemenCyclesByMan(Long manClientId) {
		return embryoDao.findPartnerCycles(manClientId);
	}

/*	private CycleDto createWomanCyclesDto(CycleDto womanCycle) {
		CycleDto wCycleDto = new CycleDto();
		// setting woman data
		wCycleDto.setWomanCodeId(womanCycle.getWomanCodeId());
		wCycleDto.setCoupleId(womanCycle.getCoupleId());
		wCycleDto.setWomanCode(womanCycle.getWomanCode());
		wCycleDto.setStartDate(womanCycle.getStartDate());
		wCycleDto.setEggCollection(womanCycle.getEggCollection());
		wCycleDto.setEvolution(womanCycle.getEvolution());
		wCycleDto.setBiochemicalOption(womanCycle.getBiochemicalOption());
		wCycleDto.setEndDate(womanCycle.getEndDate());
		return wCycleDto;
	}*/

	private CycleDto updateCycleDto(CycleDto cycleDto, CycleDto partnerCycle) {
		// setting partner data
		// if (cycleDto == null) {
		// cycleDto = new CycleDto();
		// }

		String quality = new StringBuilder(String.valueOf(partnerCycle.getDensity())).append("/")
				.append(partnerCycle.getMotility()).append("/").append(partnerCycle.getMorphology()).toString();
		cycleDto.setPartnerCode(partnerCycle.getPartnerCode());
		cycleDto.setPartnerCodeId(partnerCycle.getPartnerCodeId());
		if (null != partnerCycle.getStartDate())
			cycleDto.setStartDate(partnerCycle.getStartDate());
		cycleDto.setQuality(quality);
		return cycleDto;
	}

	public List<Long> findCodeIdByCoupleIdAndStartDate(Long coupleId, String startDate) {
		Date sDate = Util.stringToDate(startDate, IConstants.DATE_FORMAT);
		return embryoDao.findCodeIdByStartDateAndCoupleId(coupleId, sDate);
	}

	/**
	 * Find day progress value by embryo code id and destiny.
	 *
	 * @param embryoCodeId
	 *            the embryo code id
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	@Override
	public List<DayProgressValue> findDayProgressValueByEmbryoCodeIdAndDestiny(List<Long> aoEmbryoCodeId, int destiny) {
		List<DayProgressValue> aoCryoDayProgressValueList = dayProgressValueDao
				.findByEmbryoCodeIdAndDestiny(aoEmbryoCodeId, destiny);

		return aoCryoDayProgressValueList;
	}

	/*private Map<Long, Map<Integer, List<DayProgressValueDto>>> getDayProgressValueDtoMapByModuleId(
			List<DayProgressValue> aoDayProgressValues) {

		Map<Long, Map<Integer, List<DayProgressValueDto>>> dayProgressValueMap = new HashMap<>();
		for (DayProgressValue dayProgressValue : aoDayProgressValues) {

			Map<Integer, List<DayProgressValueDto>> innerMap = new LinkedHashMap<>();
			Long embryoCodeId = dayProgressValue.getEmbryoCode().getId();
			if (dayProgressValueMap.containsKey(embryoCodeId)) {
				innerMap = dayProgressValueMap.get(embryoCodeId);
			}
			List<DayProgressValueDto> dpvList = new ArrayList<>();
			if (innerMap.containsKey(dayProgressValue.getModuleId())) {
				dpvList = innerMap.get(dayProgressValue.getModuleId());
			}

			dpvList.add(createDayProgressValueDto(dayProgressValue));
			innerMap.put(dayProgressValue.getModuleId(), dpvList);
			dayProgressValueMap.put(embryoCodeId, innerMap);
		}

		return dayProgressValueMap;

	}*/

	/*private DayProgressValueDto createDayProgressValueDto(DayProgressValue dayProgressValue) {
		EmbryoCode embryoCode = dayProgressValue.getEmbryoCode();
		DayProgressValueDto dto = new DayProgressValueDto();
		dto.setEmbryoCodeId(embryoCode.getId());
		dto.setCodeId(embryoCode.getCode().getId());
		dto.setEmbryoCodeIndex(embryoCode.getIndex());
		dto.setDayColumnName(dayProgressValue.getDayColumnName());
		dto.setDayDate(dayProgressValue.getDayDate());
		dto.setDayOptionId(dayProgressValue.getDayOptionId());
		dto.setDayIndex(dayProgressValue.getDayIndex());
		dto.setModuleId(dayProgressValue.getModuleId());

		return dto;

	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.EmbryoService#updateEmbryoCodeValueDto(org.cf.card.
	 * dto.EmbryoCodeValueDto)
	 */
	@Override
	public void updateEmbryoCodeValueDto(EmbryoCodeValueDto embryoCodeValueDto) {
		if (null != embryoCodeValueDto) {
			embryoCodeDao.updateEmbryoCodeValueDto(embryoCodeValueDto.getEmbryoCodeId(),
					embryoCodeValueDto.getRemarks());
		}

	}

}
