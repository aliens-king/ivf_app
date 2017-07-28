package org.cf.card.ui.service;

import java.util.List;
import java.util.Map;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.SemenDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Semen;
import org.cf.card.model.SemenCode;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.vo.VoSemenCode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javafx.scene.control.ComboBox;

public class UISemenService extends UIBaseService{

	/** The Constant SEMEN_URL. */
	private static final String SEMEN_URL = BASE_URL + "/semen";
	
	public Semen addSemen(SemenDto semenDto){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SemenDto> entity = new HttpEntity<>(semenDto, httpHeaders);
		HttpEntity<Semen> exchange = restTemplate.exchange(SEMEN_URL,
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<Semen>(){
				}
			);
		return exchange.getBody();
	}

	public List<SemenCode> getSemenCode(Long codeId){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String url = SEMEN_URL +  "/semenCode/{codeId}";
		HttpEntity<List<SemenCode>> exchange = restTemplate.exchange(url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<SemenCode>>(){
				},codeId);
		return exchange.getBody();
	}

	/**
	 * save semen code if no of straw are not equals to no of semen code
	 * @param cryoSemenDto
	 * @return
	 */
	public List<SemenCode> saveSemenCode(CryoSemenDto cryoSemenDto){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CryoSemenDto> entity = new HttpEntity<>(cryoSemenDto, httpHeaders);
		String url = SEMEN_URL + "/semenCode/save";
		HttpEntity<List<SemenCode>> exchange = restTemplate.exchange(url,
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<List<SemenCode>>(){
				}
			);
		return exchange.getBody();
	}

	/**
	 *  update semen code if no of straw are equals to no of semen code
	 * @param cryoSemenDto
	 * @return
	 */
	public List<SemenCode> updateSemenCode(CryoSemenDto cryoSemenDto){

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CryoSemenDto> entity = new HttpEntity<>(cryoSemenDto, httpHeaders);

		String url = SEMEN_URL + "/semenCode/update/";
		HttpEntity<List<SemenCode>> exchange = restTemplate.exchange(url,
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<List<SemenCode>>(){
				}
			);
		return exchange.getBody();

	}


	/**
	 * find codes by client id
	 * @param clientId
	 * @return
	 */
	public List<Codes> findCodeByClientId(Long clientId){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String url = Configuration.getServerUrl() + "/codes/findByClientId/{clientId}";
		HttpEntity<List<Codes>> exchange = restTemplate.exchange(url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Codes>>(){
				},clientId);
		return exchange.getBody();
	}

	/**
	 * getting the semen code data by client id
	 * @param clientId
	 * @return
	 */
	public List<VoSemenCode> findSemenCodeAndSemenDataByClientId(Long clientId){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String url = SEMEN_URL + "/semenDataByClientId/{clientId}";
		HttpEntity<List<VoSemenCode>> exchange = restTemplate.exchange(url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<VoSemenCode>>(){
				},clientId);
		return exchange.getBody();
	}

	/**
	 * Find semenCode by code id and screen id.
	 *
	 * @param manCodeId the man code id
	 * @param screen the screen
	 * @return the list
	 */
	public List<SemenCode> findSemenCodeByCodeIdAndScreenId(Long manCodeId, int screen){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String url = SEMEN_URL + "/list/findSemenCodeByCodeIdAndScreenId/{manCodeId}/{screen}";
		HttpEntity<List<SemenCode>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SemenCode>>() {
				}, manCodeId, screen);
		return exchange.getBody();
	}
	
	
	public List<SemenCode> findSemenCodeByClientIdScreenAndDate(Long clientId, int screen, String createdDate){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String url = SEMEN_URL + "/findSemenCodeByClientIdScreenAndDate?clientId={clientId}&screen={screen}&createdDate={createdDate}";
		HttpEntity<List<SemenCode>> exchange = restTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<SemenCode>>() {
				}, clientId, screen, createdDate);
		return exchange.getBody();
	}
	
	public Map<String, SemenDto> findSemenCodeMapByClientIdScreen(Long clientId, int screen){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String url = SEMEN_URL + "/map/findSemenCodeMapByClientIdScreen/{clientId}/{screen}";
		HttpEntity<Map<String, SemenDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<Map<String, SemenDto>>() {
				}, clientId, screen);
		return exchange.getBody();
	}
	

	public void fillViscosityComboBox(ComboBox<Integer> jComboBox){
		jComboBox.getItems().clear();
		for(int i = 0; i<=4 ;i++)
			jComboBox.getItems().add(new Integer(i));
	}

	public void fillDebrisComboBox(ComboBox<Integer> jComboBox){
		jComboBox.getItems().clear();
		for(int i = 0; i<=4 ;i++)
			jComboBox.getItems().add(new Integer(i));
	}

	public void fillAgglutinationComboBox(ComboBox<Integer> jComboBox){
		//	jComboBox.getItems().add("n/a");
		for(int i = 0; i<=4 ;i++)
			jComboBox.getItems().add(new Integer(i));
	}

	public void fillMorphologyComboBox(ComboBox<String> jComboBox){
		//	jComboBox.getItems().add("n/a");
			jComboBox.getItems().add("Good");
			jComboBox.getItems().add("Marginal");
			jComboBox.getItems().add("Poor");
	}
}
