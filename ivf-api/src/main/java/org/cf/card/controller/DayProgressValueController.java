/**
 *
 */
package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Map;

import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.EtTableDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.service.DayProgressValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// TODO: Auto-generated Javadoc
/**
 * The Class DayProgressValueController.
 *
 * @author Nikhil Mahajan
 */
@RestController
@RequestMapping(value = "/dayProgressValue")
public class DayProgressValueController {

	/** The dayprogress value service. */
	@Autowired
	DayProgressValueService dayprogressValueService;

	/**
	 * Find day progress value map by code id and destiny.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the map
	 */
	@RequestMapping(value = "/mapByCodeAndDestiny/{womanCodeId}/{destiny}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdAndDestiny(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "destiny") int destiny) {
		return dayprogressValueService.findDayProgressValueMapByCodeIdAndDestiny(womanCodeId, destiny);
	}

	/* Fetch DayValueProgress by passing parameters clientId and destiny */
	@RequestMapping(value = "/mapByClientDestinyAndModule/{womanClientId}/{destiny}/{moduleId}/{cycleType}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueByClientIdAndDestinyAndModule(
			@PathVariable(value = "womanClientId") Long womanClientId, @PathVariable(value = "destiny") int destiny,
			@PathVariable(value = "moduleId") int moduleId, @PathVariable(value = "cycleType") int cycleType) {
		return dayprogressValueService.findDayProgressValueMapByClientIdAndDestinyAndModule(womanClientId, destiny,
				moduleId, cycleType);
	}

	/**
	 * Find day progress value map by code id.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param moduleId
	 *            the module id
	 * @return the map
	 */
	@RequestMapping(value = "/mapByCodeAndModule/{womanCodeId}/{moduleId}/{cycleType}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeId(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "moduleId") int moduleId,
			@PathVariable(value = "cycleType") int cycleType) {
		return dayprogressValueService.findDayProgressValueMapByCodeIdAndModuleId(womanCodeId, moduleId, cycleType);
	}

	/**
	 * Find day progress value by code id and destiny.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	@RequestMapping(value = "/listByCodeAndDestiny/{womanCodeId}/{destiny}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<DayProgressValue> findDayProgressValueByCodeIdAndDestiny(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "destiny") int destiny) {
		return dayprogressValueService.findDayProgressValueByCodeIdAndDestiny(womanCodeId, destiny);
	}

	/**
	 * Find day progress value by code id.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param moduleId
	 *            the module id
	 * @return the list
	 */
	@RequestMapping(value = "/listByCodeAndModule/{womanCodeId}/{moduleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<DayProgressValue> findDayProgressValueByCodeIdAndModuleId(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "moduleId") int moduleId,
			@PathVariable(value = "cycleType") int cycleType) {
		return dayprogressValueService.findDayProgressValueByCodeIdAndModuleId(womanCodeId, moduleId, cycleType);
	}

	/**
	 * Find day progress value by client id and module id.
	 *
	 * @param clientId
	 *            the client id
	 * @param moduleId
	 *            the module id
	 * @return the list
	 */
	@RequestMapping(value = "/listByClientAndModule/{clientId}/{moduleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<DayProgressValue> findDayProgressValueByClientIdAndModuleId(
			@PathVariable(value = "clientId") Long clientId, @PathVariable(value = "moduleId") int moduleId) {
		return dayprogressValueService.findDayProgressValueByClientIdAndModuleId(clientId, moduleId);
	}

	/**
	 * Gets the day progress value Map by day date range and destiny.
	 *
	 * @param from
	 *            date
	 * @param to
	 *            date
	 * @param destiny
	 *            the destiny
	 * @return map
	 */
	@RequestMapping(value = "/map/getDayProgressValueByDayDateAndDestinyMap", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Map<String, EtTableDto>> getDayProgressValueByDayDateAndDestinyMap(
			@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("destiny") int destiny) {
		return dayprogressValueService.getDayProgressValueByDayDateAndDestinyMap(from, to, destiny);
	}

	/**
	 * Find day progress value map by code id destiny and date of use not null.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the map
	 */
	@RequestMapping(value = "/map/findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull/{womanCodeId}/{destiny}/{cycleType}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "destiny") int destiny,
			@PathVariable(value = "cycleType") int cycleType) {
		return dayprogressValueService.findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(womanCodeId, destiny,
				cycleType);
	}

	/**
	 * Find day index by woman code id and destiny.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	@RequestMapping(value = "/listMap/findDayIndexByCodeIdAndDestiny{womanCodeId}/{destiny}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> findDayIndexByCodeIdAndDestiny(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "destiny") int destiny) {
		return dayprogressValueService.findDayIndexByCodeIdAndDestiny(womanCodeId, destiny);

	}

	/*
	 * @RequestMapping(value=
	 * "/map/findDayProgressValueByCodeIdAndDayIndex/{womanCodeId}/{dayIndex}",
	 * method = GET, produces = APPLICATION_JSON_VALUE) public Map<Long,
	 * Map<Integer, List<DayProgressValue>>>
	 * findDayProgressValueByCodeIdAndDayIndex(@PathVariable(value=
	 * "womanCodeId") Long womanCodeId,
	 * 
	 * @PathVariable(value="dayIndex") int dayIndex) { return
	 * dayprogressValueService.findDayProgressValueByCodeIdAndDayIndex(
	 * womanCodeId, dayIndex); }
	 */

	@RequestMapping(value = "/map/findDayProgressValueByCodeIdAndDayIndex/{womanCodeId}/{cycleType}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<Long, Map<Integer, List<DayProgressValueDto>>> findDayProgressValueMapForEmbryoThaw(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "cycleType") int cycleType) {
		Map<Long, Map<Integer, List<DayProgressValueDto>>> dayProgressValueMapByModuleId = dayprogressValueService
				.findDayProgressValueMapForEmbryoThaw(womanCodeId, cycleType);
		return dayProgressValueMapByModuleId;
	}

	/**
	 * Find day progress value by code id destiny date of use not null day index
	 * and module id.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	@RequestMapping(value = "/map/findDayProgressValueByCodeIdDateOfUseNotNullModuleIdAndDayIndex/{womanCodeId}/{moduleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<DayProgressValueDto> findDayProgressValueByCodeIdDestinyDateOfUseNotNullDayIndexAndModuleId(
			@PathVariable(value = "womanCodeId") Long womanCodeId, @PathVariable(value = "moduleId") int moduleId) {
		List<DayProgressValueDto> dayProgressValueMapByModuleId = dayprogressValueService
				.findDayProgressValueMapForCryoEggsDayZero(womanCodeId, moduleId);
		return dayProgressValueMapByModuleId;
	}

}
