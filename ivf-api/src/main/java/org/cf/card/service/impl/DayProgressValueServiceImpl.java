/**
 *
 */
package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.EtTableDto;
import org.cf.card.model.Codes;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.EmbryoCode;
import org.cf.card.persistence.DayProgressValueDao;
import org.cf.card.service.DayProgressValueService;
import org.cf.card.util.EnumDayTable;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class DayProgressValueServiceImpl.
 *
 * @author Nikhil Mahajan
 */
@Service("dayProgressValueService")
public class DayProgressValueServiceImpl extends BaseServiceImpl<DayProgressValue>implements DayProgressValueService {

	/** The day progress value dao. */
	private DayProgressValueDao dayProgressValueDao;

	/**
	 * Sets the day progress value dao.
	 *
	 * @param dayProgressValueDao
	 *            the new day progress value dao
	 */
	@Autowired
	public void setDayProgressValueDao(DayProgressValueDao dayProgressValueDao) {
		this.dayProgressValueDao = dayProgressValueDao;
		setGenericDao(dayProgressValueDao);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.DayProgressValueService#findDayProgressValueByCodeId(
	 * java.lang.Long)
	 */
	@Override
	public List<DayProgressValue> findDayProgressValueByCodeIdAndModuleId(Long womanCodeId, int moduleId,
			int cycleType) {
		return dayProgressValueDao.findDayProgressValueByCodeIdAndModuleId(womanCodeId, moduleId, cycleType);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.DayProgressValueService#
	 * findDayProgressValueByCodeIdAndDestiny(java.lang.Long, int)
	 */
	@Override
	public List<DayProgressValue> findDayProgressValueByCodeIdAndDestiny(Long womanCodeId, int destiny) {
		return dayProgressValueDao.findDayProgressValueByCodeIdAndDestiny(womanCodeId, destiny);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.DayProgressValueService#findDayProgressValueByDestiny
	 * (int)
	 */
	@Override
	public List<DayProgressValue> findDayProgressValueByClientIdDestinyAndModule(Long womanClientId, int destiny,
			int module, int cycleType) {
		List<Integer> aoCyclyeType = new ArrayList<Integer>();
		aoCyclyeType.add(1);
		aoCyclyeType.add(2);
		// aoCyclyeType.add(3);

		return dayProgressValueDao.findDayProgressValueByClientIdAndDestinyAndModule(womanClientId, destiny, module,
				aoCyclyeType);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.DayProgressValueService#
	 * findDayProgressValuesByCodeIdAndDestiny(java.lang.Long, int)
	 */
	@Override
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdAndDestiny(Long womanCodeId,
			int destiny) {

		List<DayProgressValue> aoDayProgressValues = this.findDayProgressValueByCodeIdAndDestiny(womanCodeId, destiny);
		Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap = getDayProgressValueMap(
				aoDayProgressValues);
		return dayProgressValueMap;
	}

	@Override
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByClientIdAndDestinyAndModule(
			Long womanClientId, int destiny, int module, int cycleType) {

		List<DayProgressValue> aoDayProgressValues = this.findDayProgressValueByClientIdDestinyAndModule(womanClientId,
				destiny, module, cycleType);
		Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap = getDayProgressValueMap(
				aoDayProgressValues);
		return dayProgressValueMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cf.card.service.DayProgressValueService#findDayProgressValuesByCodeId
	 * (java.lang.Long)
	 */
	@Override
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdAndModuleId(Long womanCodeId,
			int moduleId, int cycleType) {

		List<DayProgressValue> aoDayProgressValues = this.findDayProgressValueByCodeIdAndModuleId(womanCodeId, moduleId,
				cycleType);
		Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap = getDayProgressValueMap(
				aoDayProgressValues);
		return dayProgressValueMap;
	}

	/**
	 * Gets the day progress value map.
	 *
	 * @param aoDayProgressValues
	 *            the ao day progress values
	 * @return the day progress value map as Map<Long, Map<Integer,
	 *         DayProgressValue>> where
	 *
	 *         key = embryoCodeId value = Map<dayIndex,DayProgressValue>
	 */
	private Map<Long, Map<Integer, List<DayProgressValue>>> getDayProgressValueMap(
			List<DayProgressValue> aoDayProgressValues) {

		Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap = new LinkedHashMap<Long, Map<Integer, List<DayProgressValue>>>();
		for (DayProgressValue dayProgressValue : aoDayProgressValues) {
			Map<Integer, List<DayProgressValue>> innerMap = new LinkedHashMap<>();
			Long embryoCodeId = dayProgressValue.getEmbryoCode().getId();
			if (dayProgressValueMap.containsKey(embryoCodeId)) {
				innerMap = dayProgressValueMap.get(embryoCodeId);
			}
			List<DayProgressValue> dpvList = new ArrayList<>();
			if (innerMap.containsKey(dayProgressValue.getDayIndex())) {
				dpvList = innerMap.get(dayProgressValue.getDayIndex());
			}

			dpvList.add(dayProgressValue);
			innerMap.put(dayProgressValue.getDayIndex(), dpvList);
			dayProgressValueMap.put(embryoCodeId, innerMap);
		}

		return dayProgressValueMap;

	}

	/**
	 * Gets the day progress value map by screen id.
	 *
	 * @param aoDayProgressValues
	 *            the ao day progress values the day progress value map as
	 *            Map<Long, Map<Integer, DayProgressValue>> where
	 *
	 *            key = embryoCodeId value = Map<ModuleId,DayProgressValue>
	 *
	 *
	 */
	private Map<Long, Map<Integer, List<DayProgressValue>>> getDayProgressValueMapByModuleId(
			List<DayProgressValue> aoDayProgressValues) {

		Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap = new HashMap<Long, Map<Integer, List<DayProgressValue>>>();
		for (DayProgressValue dayProgressValue : aoDayProgressValues) {
			Map<Integer, List<DayProgressValue>> innerMap = new LinkedHashMap<>();
			Long embryoCodeId = dayProgressValue.getEmbryoCode().getId();
			if (dayProgressValueMap.containsKey(embryoCodeId)) {
				innerMap = dayProgressValueMap.get(embryoCodeId);
			}
			List<DayProgressValue> dpvList = new ArrayList<>();
			if (innerMap.containsKey(dayProgressValue.getModuleId())) {
				dpvList = innerMap.get(dayProgressValue.getModuleId());
			}

			dpvList.add(dayProgressValue);
			innerMap.put(dayProgressValue.getModuleId(), dpvList);
			dayProgressValueMap.put(embryoCodeId, innerMap);
		}

		return dayProgressValueMap;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.DayProgressValueService#
	 * getDayProgressValueByDayDateAndDestiny(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public List<DayProgressValue> getDayProgressValueByDayDateAndDestiny(String from, String to, int destiny) {
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = Util.stringToDate(from, IConstants.DATE_FORMAT);
			toDate = Util.stringToDate(to, IConstants.DATE_FORMAT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dayProgressValueDao.getDayProgressValueByDayDateAndDestiny(fromDate, toDate, destiny);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.DayProgressValueService#
	 * getDayProgressValueByDayDateAndDestinyMap(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public Map<String, Map<String, EtTableDto>> getDayProgressValueByDayDateAndDestinyMap(String from, String to,
			int destiny) {
		List<DayProgressValue> dayProgressValueList = this.getDayProgressValueByDayDateAndDestiny(from, to, destiny);
		Map<String, Map<String, EtTableDto>> dayProgressValueMap = dayProgressValueMap(dayProgressValueList);
		return dayProgressValueMap;
	}

	/**
	 * Day progress value map.
	 *
	 * @param dayProgressValList
	 *            the day progress value list
	 * @return the map
	 */
	private Map<String, Map<String, EtTableDto>> dayProgressValueMap(List<DayProgressValue> dayProgressValList) {
		Map<String, Map<String, EtTableDto>> dayProgressValMap = new TreeMap<>();
		for (DayProgressValue dayProgressVal : dayProgressValList) {
			Map<String, EtTableDto> innerMap = new TreeMap<>();
			String dateKey = Util.formatDate(IConstants.DATE_FORMAT, dayProgressVal.getDayDate());
			if (dayProgressValMap.containsKey(dateKey)) {
				innerMap = dayProgressValMap.get(dayProgressVal.getDayDate());
			}

			String date = Util.formatDate(IConstants.DATE_FORMAT, dayProgressVal.getDayDate());
			innerMap.put(date, this.createEtTableDto(dayProgressVal));

			String timeKey = dayProgressVal.getEmbryoCode().getTime();
			if (timeKey != null)
				dayProgressValMap.put(timeKey, innerMap);
		}
		return dayProgressValMap;
	}

	/**
	 * Creates the etTableDto.
	 *
	 * @param dayProgressVal
	 *            the dayProgressValue
	 * @return the etTableDto
	 */
	private EtTableDto createEtTableDto(DayProgressValue dayProgressVal) {
		EtTableDto etTableDto = new EtTableDto();
		Codes code = dayProgressVal.getEmbryoCode().getCode();
		if (code != null) {
			etTableDto.setPatientName(code.getClient().getFullName());
			etTableDto.setFirstName(code.getClient().getFirstName());
			etTableDto.setMiddleName(code.getClient().getMiddleName());
			etTableDto.setSurName(code.getClient().getSurname());
			etTableDto.setCoupleId(code.getClient().getCouple().getId());
			List<EmbryoCode> eCodes = code.getEmbryoCode();
			for (EmbryoCode embryoCode : eCodes) {
				etTableDto.setEtDate(Util.formatDate(IConstants.DATE_FORMAT, embryoCode.getDestinyDate()));
				etTableDto.setEtTime(embryoCode.getTime());
				break;
			}
		}
		etTableDto.setDayIndex(dayProgressVal.getDayIndex());
		return etTableDto;
	}

	@Override
	public List<DayProgressValue> findDayProgressValueByClientIdAndModuleId(Long clientId, int moduleId) {
		return dayProgressValueDao.findDayProgressValueByClientIdAndModuleId(clientId, moduleId);
	}

	@Override
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(
			Long womanCodeId, int destiny, int cycleType) {
		List<DayProgressValue> dayProgressValueList = dayProgressValueDao
				.findDayProgressValueByCodeIdDestinyAndDateOfUseNotNull(womanCodeId, destiny, cycleType);
		Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap = this
				.getDayProgressValueMapByModuleId(dayProgressValueList);
		return dayProgressValueMap;
	}

	/**
	 * Creates the List of Map.
	 *
	 * @param womanCodeId
	 *            the womanCodeId
	 * @param destiny
	 *            the destiny
	 * @return the List
	 */
	@Override
	public List<Map<String, Object>> findDayIndexByCodeIdAndDestiny(Long womanCodeId, int destiny) {
		List<Map<String, Object>> dpvListMap = dayProgressValueDao.findDayIndexByCodeIdAndDestiny(womanCodeId, destiny);
		return dpvListMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.cf.card.service.DayProgressValueService#
	 * findDayProgressValueByCodeIdAndDayIndex(java.lang.Long, int)
	 */
	@Override
	public List<DayProgressValue> findDayProgressValueByCodeIdAndDayIndex(Long womanCodeId, int dayIndex,
			int cycleType) {
		List<DayProgressValue> dayProgressValues = dayProgressValueDao
				.findDayProgressValueByCodeIdAndDayIndex(womanCodeId, dayIndex, cycleType);
		return dayProgressValues;
	}

	@Override
	public Map<Long, Map<Integer, List<DayProgressValueDto>>> findDayProgressValueMapForEmbryoThaw(Long womanCodeId,
			int cycleType) {
		List<DayProgressValue> dayProgressValueList = dayProgressValueDao
				.findDayProgressValueByCodeIdDestinyAndDateOfUseNotNull(womanCodeId, EnumDayTable.Option.CRYO.getKey(),
						cycleType);
		// -1 dayIndex is for day Day2 in embryoThawing.
		dayProgressValueList.addAll(this.findDayProgressValueByCodeIdAndDayIndex(womanCodeId, -1, cycleType));
		Map<Long, Map<Integer, List<DayProgressValueDto>>> dayProgressValueMapByModuleId = this
				.getDayProgressValueDtoMapByModuleId(dayProgressValueList);
		return dayProgressValueMapByModuleId;
	}

	/*
	 * @Override public List<DayProgressValueDto>
	 * findDayProgressValueMapForCryoEggsDayZero( Long womanCodeId, int
	 * moduleId) { List<DayProgressValueDto> dayProgressValueList =
	 * dayProgressValueDao.
	 * findDayProgressValueByCodeIdDestinyDateOfUseNotNullDayIndexAndModuleId(
	 * womanCodeId, moduleId, 0); return dayProgressValueList;
	 *
	 * }
	 */

	private Map<Long, Map<Integer, List<DayProgressValueDto>>> getDayProgressValueDtoMapByModuleId(
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

	}

	private DayProgressValueDto createDayProgressValueDto(DayProgressValue dayProgressValue) {
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

	}

	@Override
	public List<DayProgressValueDto> findDayProgressValueMapForCryoEggsDayZero(Long womanCodeId, int moduleId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cf.card.service.DayProgressValueService#findByEmbryoCodeIdsAndDestiny(java.util.Collection, int)
	 */
	@Override
	public List<DayProgressValue> findByEmbryoCodeIdsAndDestiny(Collection<Long> aoEmbryoCodeId, int destiny) {
		return dayProgressValueDao.findByEmbryoCodeIdAndDestiny(aoEmbryoCodeId, destiny);
	}
	

	/* (non-Javadoc)
	 * @see org.cf.card.service.DayProgressValueService#findDayProgressValueMapForEmbryoThawFromOldCycle(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public Map<Long, Map<Integer, List<DayProgressValueDto>>> findDayProgressValueMapForEmbryoThawFromOldCycle(List<Long> aoWomanCodeId, List<Integer> aoCycleType, List<Long> aoEmbryoCodeId) {
		List<DayProgressValue> dayProgressValueList = dayProgressValueDao.findDayProgressValueByWomanCodeIdDestinyEmbryoCodeIdCycleTypeAndDateOfUseNotNull(aoWomanCodeId, EnumDayTable.Option.CRYO.getKey(), aoCycleType, aoEmbryoCodeId);
		Map<Long, Map<Integer, List<DayProgressValueDto>>> dayProgressValueMapByModuleId = this.getDayProgressValueDtoMapByModuleId(dayProgressValueList);
		return dayProgressValueMapByModuleId;
	}

}
