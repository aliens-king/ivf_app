/**
 *
 */
package org.cf.card.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.EtTableDto;
import org.cf.card.model.DayProgressValue;

/**
 * The Interface DayProgressValueService.
 *
 * @author Nikhil Mahajan
 */
public interface DayProgressValueService extends BaseService<DayProgressValue> {

	/**
	 * Find day progress values by code id and destiny.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the Map<Long, Map<Integer, List<DayProgressValue>>> : key =
	 *         embryoCodeId value = Map<dayIndex,List<DayProgressValue>>
	 */
	Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdAndDestiny(Long womanCodeId,
			int destiny);

	/**
	 * Find day progress values by code id.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param moduleId
	 *            the module id
	 * @return the Map<Long, Map<Integer, List<DayProgressValue>>> : key =
	 *         embryoCodeId value = Map<dayIndex,List<DayProgressValue>>
	 */
	Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdAndModuleId(Long womanCodeId,
			int moduleId, int cycleType);

	/**
	 * Find day progress value by code id.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param moduleId
	 *            the module id
	 * @return the list
	 */
	List<DayProgressValue> findDayProgressValueByCodeIdAndModuleId(Long womanCodeId, int moduleId, int cycleType);

	/**
	 * Find day progress value by code id and destiny.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	List<DayProgressValue> findDayProgressValueByCodeIdAndDestiny(Long womanCodeId, int destiny);

	/**
	 * Find day progress value by destiny.
	 *
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	List<DayProgressValue> findDayProgressValueByClientIdDestinyAndModule(Long womanClientId, int destiny, int module,
			int cycle);

	/**
	 * Gets the day progress value list by day date range and destiny.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param destiny
	 *            the destiny
	 * @return list
	 */
	List<DayProgressValue> getDayProgressValueByDayDateAndDestiny(String from, String to, int destiny);

	/**
	 * Gets the day progress value Map by day date range and destiny.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param destiny
	 *            the destiny
	 * @return map
	 */
	Map<String, Map<String, EtTableDto>> getDayProgressValueByDayDateAndDestinyMap(String from, String to, int destiny);

	/**
	 * Find day progress value map by code id destiny and date of use not null.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the map
	 */
	Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(
			Long womanCodeId, int destiny, int cycleType);

	/**
	 * Find day progress value by client id and module id.
	 *
	 * @param clientId
	 *            the client id
	 * @param moduleId
	 *            the module id
	 * @return the list
	 */
	List<DayProgressValue> findDayProgressValueByClientIdAndModuleId(Long clientId, int moduleId);

	List<Map<String, Object>> findDayIndexByCodeIdAndDestiny(Long womanCodeId, int destiny);

	Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByClientIdAndDestinyAndModule(
			Long womanClientId, int destiny, int module, int cycleType);

	/**
	 * Find day progress value by code id and day index.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param dayIndex
	 *            the day index
	 * @return the map
	 */
	List<DayProgressValue> findDayProgressValueByCodeIdAndDayIndex(Long womanCodeId, int dayIndex, int cycleType);

	/**
	 * Find day progress value map for embryo thaw.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @return the map
	 */
	Map<Long, Map<Integer, List<DayProgressValueDto>>> findDayProgressValueMapForEmbryoThaw(Long womanCodeId,
			int cycleType);

	/**
	 * Find day progress value map for cryo eggs day zero.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param moduleId
	 *            the module id
	 * @return the map
	 */
	List<DayProgressValueDto> findDayProgressValueMapForCryoEggsDayZero(Long womanCodeId, int moduleId);

	/**
	 * Find DayProgressValue list by list of code ids and destiny
	 *
	 * @param aoEmbryoCodeId
	 * @param destiny
	 * @return List<DayProgressValue>
	 */
	List<DayProgressValue> findByEmbryoCodeIdsAndDestiny(Collection<Long> aoEmbryoCodeId, int destiny);

	/**
	 * Find day progress value map for embryo thaw from old cycle.
	 *
	 * @param aoWomanCodeId
	 *            the ao woman code id
	 * @param aoCycleType
	 *            the ao cycle type
	 * @param aoEmbryoCodeId
	 *            the ao embryo code id
	 * @return the map
	 */
	Map<Long, Map<Integer, List<DayProgressValueDto>>> findDayProgressValueMapForEmbryoThawFromOldCycle(
			List<Long> aoWomanCodeId, List<Integer> aoCycleType, List<Long> aoEmbryoCodeId);

}
