/**
 * 
 */
package org.cf.card.ui.service;

import org.cf.card.dto.RegistrantDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Pramod Maurya
 * @since 10-Jul-2017
 */
public class UIRegistrantService extends UIBaseService {
	

	private static final String REGISTRANT_URL = BASE_URL + "/registrant";

	/**
	 * Save.
	 *
	 * @param registrantDto the registrant dto
	 * @return the registrant dto
	 */
	public RegistrantDto save(RegistrantDto registrantDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RegistrantDto> entity = new HttpEntity<>(registrantDto, headers);
		ResponseEntity<RegistrantDto> exchange = restTemplate.exchange(REGISTRANT_URL + "/save", HttpMethod.POST,
				entity, new ParameterizedTypeReference<RegistrantDto>() {
				}, registrantDto);
		return exchange.getBody();
	}

	
	// getting the RegistrantDto by codeID and screenId from Database
	public RegistrantDto getRegistrantByCodeAndScreenID(Long codeId, int screenId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<RegistrantDto> exchange = restTemplate.exchange(
				REGISTRANT_URL + "/registrantByCodeAndScreenId/{codeId}/{screenId}", HttpMethod.GET, null,
				new ParameterizedTypeReference<RegistrantDto>() {
				}, codeId, screenId);
		return exchange.getBody();

	}
}
