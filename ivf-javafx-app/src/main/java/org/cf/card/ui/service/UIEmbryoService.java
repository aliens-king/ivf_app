/**
 *
 */
package org.cf.card.ui.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import org.cf.card.dto.CycleDto;
import org.cf.card.dto.EmbryoCodeDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.EmbryoDto;
import org.cf.card.dto.UIDayDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.Embryo;
import org.cf.card.model.EmbryoCode;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.util.EnumEmbryo;
import org.cf.card.util.EnumEmbryo.Injection;
import org.cf.card.util.IConstants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * The Class UIEmbryoService.
 *
 * @author Nikhil Mahajan
 */
public class UIEmbryoService extends UIBaseService {

	/** The Constant EMBRYO_URL. */
	private static final String EMBRYO_URL = BASE_URL + "/embryo";

	/**
	 * Gets the embryos by code.
	 *
	 * @param codeId
	 *            the code id
	 * @param moduleId
	 * @return the embryos by code
	 */
	public List<EmbryoCode> getEmbryosByCode(Long codeId) {

		String url = EMBRYO_URL + "/list?codeId={codeId}";
		ResponseEntity<List<EmbryoCode>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EmbryoCode>>() {
				}, codeId);
		return exchange.getBody();
	}

	/**
	 * Gets the embryo.
	 *
	 * @param embryoCodeId
	 *            the embryo code id
	 * @return the embryo
	 */
	public EmbryoCode getEmbryo(Long embryoCodeId) {

		ResponseEntity<EmbryoCode> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + EMBRYO_URL + "/findByEmbryoCodeId/{embryoCodeId}", HttpMethod.GET, null,
				new ParameterizedTypeReference<EmbryoCode>() {
				}, embryoCodeId);
		return exchange.getBody();
	}

	/**
	 * Save.
	 *
	 * @param embryo
	 *            the embryo
	 * @return the embryo code
	 */
	public EmbryoCode save(Embryo embryo) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Embryo> entity = new HttpEntity<>(embryo, headers);

		ResponseEntity<EmbryoCode> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/save",
				HttpMethod.POST, entity, new ParameterizedTypeReference<EmbryoCode>() {
				});

		return exchange.getBody();
	}

	/**
	 * Update.
	 *
	 * @param embryo
	 *            the embryo
	 * @return the embryo code
	 */
	public EmbryoCode update(Embryo embryo) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Embryo> entity = new HttpEntity<>(embryo, headers);

		ResponseEntity<EmbryoCode> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/update",
				HttpMethod.POST, entity, new ParameterizedTypeReference<EmbryoCode>() {
				});

		return exchange.getBody();
	}

	/**
	 * Adds the embryos.
	 *
	 * @param oocytes the oocytes
	 * @param codeId the code id
	 * @return the list
	 */
	public List<Embryo> addEmbryos(int oocytes, long codeId) {

		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("oocctes", oocytes);
		// map.put("codeId", codeId);
		//
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_JSON);
		// HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map,
		// headers);

		// ResponseEntity<List<EmbryoCode>> exchange =
		// restTemplate.postForObject(EMBRYO_URL + "/addEmbryos", map, new
		// ParameterizedTypeReference<List<EmbryoCode>>() {});
		// ResponseEntity<List<EmbryoCode>> exchange =
		// restTemplate.exchange(EMBRYO_URL + "/addEmbryos",
		// HttpMethod.POST,
		// entity,
		// new ParameterizedTypeReference<List<EmbryoCode>>() {
		// }
		// );

		// need to create db again for test

		ResponseEntity<List<Embryo>> exchange = restTemplate.exchange(
				EMBRYO_URL + "/addEmbryos?codeId={codeId}&oocytes={oocytes}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Embryo>>() {
				}, codeId, oocytes);

		return exchange.getBody();
	}


	/**
	 *  save the embryo code object.
	 *
	 * @param embryoCodeDto the embryo code dto
	 * @return list of embryo code
	 */
	public void saveCryoEmbryo(EmbryoCodeDto embryoCodeDto) {
		String url = EMBRYO_URL + "/cryoEmbryo";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EmbryoCodeDto> entity = new HttpEntity<>(embryoCodeDto, headers);
		restTemplate.exchange(url, HttpMethod.POST, entity,	new ParameterizedTypeReference< Void>() {});

		//return exchange.getBody();
	}

	/**
	 * Adds the day progress values.
	 *
	 * @param uiDayDto the ui day dto
	 * @return list of day progress value
	 */
	public List<DayProgressValue> addDayProgressValues(UIDayDto uiDayDto) {
		String url = EMBRYO_URL + "/dayProgressValue";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UIDayDto> entity = new HttpEntity<>(uiDayDto, headers);
		ResponseEntity<List<DayProgressValue>> exchange = restTemplate.exchange(url, HttpMethod.POST, entity,
				new ParameterizedTypeReference<List<DayProgressValue>>() {
				});
		return exchange.getBody();
	}

	/**
	 * Gets the day progress value.
	 *
	 * @param embryoCodeId the embryo code id
	 * @param dayIndex the day index
	 * @return list of day progress value
	 */
	public List<DayProgressValue> getDayProgressValue(Long embryoCodeId, int dayIndex) {
		String url = EMBRYO_URL + "/dayProgressValue/{embryoCodeId}/{dayIndex}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<DayProgressValue>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DayProgressValue>>() {
				}, embryoCodeId, dayIndex);
		return exchange.getBody();
	}



	/**
	 * Adds the embryo transfer.
	 *
	 * @param embryoCodeValueDto the embryo code value dto
	 * @return the embryo code
	 */
	public EmbryoCode addEmbryoTransfer(EmbryoCodeValueDto embryoCodeValueDto){
		String url = EMBRYO_URL + "/embryoTransfer";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EmbryoCodeValueDto> entity = new HttpEntity<>(embryoCodeValueDto, headers);
		ResponseEntity<EmbryoCode> exchange = restTemplate.exchange(url, HttpMethod.POST, entity,
				new ParameterizedTypeReference<EmbryoCode>() {
			}
		);
		return exchange.getBody();
	}

	public ComboBox<Injection> fillInjectionComboBox(Injection selected){
		ComboBox<Injection> jComboBox = new ComboBox<>();
		//	jComboBox.getItems().add("n/a");
		for (EnumEmbryo.Injection element : EnumEmbryo.Injection.values()) {
			jComboBox.getItems().add(element);
		}
		jComboBox.setValue(selected);
		return jComboBox;

	}

	public void updateEmbryos(List<EmbryoDto> aoEmbryoDto) {
		String url = EMBRYO_URL + "/updateEmbryos";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<EmbryoDto>> entity = new HttpEntity<>(aoEmbryoDto, headers);
		 restTemplate.exchange(url, HttpMethod.POST, entity,Void.class);
		//return exchange.getBody();

	}

 public  ObservableList<String>   appointmentTime()
 {
	 ObservableList<String> dataTime = FXCollections.observableArrayList();
		// Time Slot 8 am to 8 pm
		LocalTime startTime = LocalTime.of(8, 0);
		//ObservableList<UIAppointment> uiAppointments = FXCollections.observableArrayList();
		for (int i = 0; i < 24; i++) {

			// getting time dynamically from 8:00 AM to *:PM
			LocalTime nextTime = startTime.plusMinutes(30 * i);
			String formattedTime = nextTime.format(DateTimeFormatter.ofPattern(IConstants.TIME_FORMAT));

		dataTime.add(formattedTime);
		}

		return dataTime;
 }



	/**
	 * Find day progress values map by code id.
	 *
	 * @param womanCodeId the woman code id
	 * @return the map
	 */
	/*public Map<Integer,List<DayProgressValue>> findDayProgressValuesMapByCodeId(Long womanCodeId) {
		String url = EMBRYO_URL + "/dayProgressValue/{womanCodeId}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Integer,List<DayProgressValue>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<Integer,List<DayProgressValue>>>() {
				}, womanCodeId);
		return exchange.getBody();
	}*/





	/**
	 * getting old treatment details for all generated codes of woman
	 * @param clientId
	 * @return
	 */
	/*public Map<String, List<DayProgressValue>> getDayProgressValuesByClientId(Long clientId) {
		String url = EMBRYO_URL + "/dayProgressValueByClientId/{clientId}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<String, List<DayProgressValue>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<String, List<DayProgressValue>>>() {
				}, clientId);
		return exchange.getBody();
	}*/




	public List<EmbryoCode>  findEmbryoCodeByClientId(Long clientId) {
		String url = EMBRYO_URL + "/embryoCodeByClientId/{clientId}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<EmbryoCode>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EmbryoCode>>() {
				}, clientId);
		return exchange.getBody();
	}

	/*public Map<Long, List<DayProgressValue>> findDayProgressValuesByClientsId(Long clientId) {
		String url = EMBRYO_URL + "/dayProgressValueByClientsId/{clientId}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Map<Long, List<DayProgressValue>>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<Long, List<DayProgressValue>>>() {
				}, clientId);
		return exchange.getBody();
	}*/

	/*public Set<CycleDto> findEmbryoCyclesByWoman(Long womanClientId){
		String url = EMBRYO_URL + "/set/findEmbryoCyclesByWoman/{womanClientId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Set<CycleDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Set<CycleDto>>() {
		}, womanClientId);
		return exchange.getBody();
	}*/

	/*public Set<VoSemenCode> findSemenCyclesByMan(Long manClientId){
		String url = EMBRYO_URL + "/set/findSemenCyclesByMan/{womanClientId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Set<VoSemenCode>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Set<VoSemenCode>>() {
		}, manClientId);
		return exchange.getBody();
	}*/
	
	/*public Set<CycleDto> findSemenCyclesByMan(Long manClientId){
		String url = EMBRYO_URL + "/set/findSemenCyclesByMan/{manClientId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Set<CycleDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Set<CycleDto>>() {
		}, manClientId);
		return exchange.getBody();
	}*/
	
	public Collection<CycleDto> findCoupleCycles(Long womanClientId, Long manClientId){
		String url = EMBRYO_URL + "/set/findCoupleCycles/{womanClientId}/{manClientId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Collection<CycleDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Collection<CycleDto>>() {
		}, womanClientId, manClientId);
		return exchange.getBody();
	}
	
	public List<Long> findCodeIdByCoupleIdAndStartDate(Long coupleId, String startDate){
		String url = EMBRYO_URL + "/findCodeIdByCoupleIdAndStartDate?coupleId={coupleId}&startDate={startDate}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<Long>> exchange = restTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Long>>() {
		}, coupleId, startDate);
		
		return exchange.getBody();
	}
	
	
	public void updateEmbryoCodesRemarks(EmbryoCodeValueDto embryoCodeValueDto) {
		String url = EMBRYO_URL + "/updateEmbryoCodeValueDto";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EmbryoCodeValueDto> entity = new HttpEntity<>(embryoCodeValueDto, headers);
		 restTemplate.exchange(url, HttpMethod.POST, entity,Void.class);
		//return exchange.getBody();

	}
	
	

}
