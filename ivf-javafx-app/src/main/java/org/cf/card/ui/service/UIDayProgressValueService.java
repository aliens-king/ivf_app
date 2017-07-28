/**
 *
 */
package org.cf.card.ui.service;

import java.util.List;
import java.util.Map;

import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.EtTableDto;
import org.cf.card.model.DayProgressValue;
import org.hibernate.validator.internal.xml.ParameterType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * The Class UIDayProgressService.
 *
 * @author Nikhil Mahajan
 */
public class UIDayProgressValueService extends UIBaseService {

	/**  The Constant EMBRYO_URL: /dayProgress. */
	private static final String DAY_PROGRESS_URL = BASE_URL + "/dayProgressValue";

	/**
	 * Gets the day progress value list.
	 *
	 * @param womanCodeId the woman code id
	 * @return the list
	 */
	public List<DayProgressValue> findDayProgressValuesByCodeIdAndModuleId(Long womanCodeId,int moduleId, int cycleType) {
		String url = DAY_PROGRESS_URL + "/listByCodeAndModule/{womanCodeId}/{moduleId}/{cycleType}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<DayProgressValue>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DayProgressValue>>() {
				}, womanCodeId,moduleId, cycleType);
		return exchange.getBody();
	}

	/**
	 * Gets the day progress value list.
	 *
	 * @param womanCodeId the woman code id
	 * @param destiny the destiny
	 * @return the list
	 */
	public List<DayProgressValue> findDayProgressValuesByCodeIdAndDestiny(Long womanCodeId, int destiny) {
		String url = DAY_PROGRESS_URL + "/listByCodeAndDestiny/{womanCodeId}/{destiny}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<DayProgressValue>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DayProgressValue>>() {
				}, womanCodeId, destiny);
		return exchange.getBody();
	}

	/**
	 * Find day progress values map by code id.
	 *
	 * @param womanCodeId            the woman code id
	 * @param destiny the destiny
	 * @return the map
	 */
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValuesMapByCodeIdAndDestiny(Long womanCodeId,
			int destiny) {
		String url = DAY_PROGRESS_URL + "/mapByCodeAndDestiny/{womanCodeId}/{destiny}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Long, Map<Integer, List<DayProgressValue>>>> exchange = restTemplate.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<Map<Long, Map<Integer, List<DayProgressValue>>>>() {
				}, womanCodeId, destiny);
		return exchange.getBody();
	}

	/**
	 * Find day progress values map by code id and destiny.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @return the map
	 */
	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValuesMapByCodeIdAndModuleId(Long womanCodeId, int moduleId, int cycleType) {
		String url = DAY_PROGRESS_URL + "/mapByCodeAndModule/{womanCodeId}/{moduleId}/{cycleType}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Long, Map<Integer, List<DayProgressValue>>>> exchange = restTemplate.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<Map<Long, Map<Integer, List<DayProgressValue>>>>() {
				}, womanCodeId,moduleId, cycleType);
		return exchange.getBody();
	}


	/**
	 * Gets the day progress value Map by day date range and destiny.
	 *
	 * @param from
	 * @param to
	 * @param destiny the destiny
	 * @return the map
	 */
	public Map<String, Map<String, EtTableDto>> getDayProgressValueByDayDateAndDestinyMap(String from, String to, int destiny) {
		String url = DAY_PROGRESS_URL + "/map/getDayProgressValueByDayDateAndDestinyMap?from={from}&to={to}&destiny={destiny}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<String, Map<String, EtTableDto>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<String, Map<String, EtTableDto>>>() {
				}, from, to, destiny);
		return exchange.getBody();
	}

	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(Long womanCodeId, int destiny, int cycleType){
		String url = DAY_PROGRESS_URL + "/map/findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull/{womanCodeId}/{destiny}/{cycleType}";
		HttpHeaders headers = new  HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Long, Map<Integer, List<DayProgressValue>>>> exchange = restTemplate.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<Map<Long, Map<Integer, List<DayProgressValue>>>>() {
				}, womanCodeId, destiny, cycleType);
		return exchange.getBody();
	}

	public List<DayProgressValue>  findDayProgressValuesByClientIdAndModuleId(Long clientId,int moduleId) {
		String url = DAY_PROGRESS_URL + "/listByClientAndModule/{clientId}/{moduleId}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<DayProgressValue> > exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DayProgressValue> >() {
				}, clientId,moduleId);
		return exchange.getBody();
	}

	public List<Map<String, Object>> findDayIndexByCodeIdAndDestiny(Long womanCodeId, int destiny){
		String url = DAY_PROGRESS_URL + "/listMap/findDayIndexByCodeIdAndDestiny{womanCodeId}/{destiny}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<Map<String, Object>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Map<String, Object>>>() {
				}, womanCodeId, destiny);
		return exchange.getBody();
	}


	public Map<Long, Map<Integer, List<DayProgressValue>>> findDayProgressValueMapByClientDestinyAndModule(Long clientId,  int destiny, int moduleId, int cycleType){
		String url = DAY_PROGRESS_URL + "/mapByClientDestinyAndModule/{clientId}/{destiny}/{moduleId}/{cycleType}";
		HttpHeaders headers = new  HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Long, Map<Integer, List<DayProgressValue>>>> exchange = restTemplate.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<Map<Long, Map<Integer, List<DayProgressValue>>>>() {
				}, clientId, destiny,moduleId, cycleType);
		return exchange.getBody();
	}


	public Map<Long, Map<Integer, List<DayProgressValueDto>>> findDayProgressValueMapForEmbryoThaw(Long womanCodeId, int cycleType) {
		String url = DAY_PROGRESS_URL  + "/map/findDayProgressValueByCodeIdAndDayIndex/{womanCodeId}/{cycleType}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Long, Map<Integer, List<DayProgressValueDto>>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<Long, Map<Integer, List<DayProgressValueDto>>>>(){
				}, womanCodeId, cycleType);
		return exchange.getBody();

	}

}
