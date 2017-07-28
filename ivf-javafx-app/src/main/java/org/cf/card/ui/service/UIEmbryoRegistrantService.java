/**
 * 
 */
package org.cf.card.ui.service;

import org.cf.card.dto.EmbryologyRegistrantDto;
import org.cf.card.dto.RegistrantDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
public class UIEmbryoRegistrantService extends UIBaseService{
	
	private static final String EMBRYO_REGISTRANT_URL = BASE_URL + "/embryoRegistrant";
	

	
	/**
	 * Save.
	 *
	 * @param embryologyRegistrantDto the embryology registrant dto
	 * @return the embryology registrant dto
	 */
	public EmbryologyRegistrantDto save(EmbryologyRegistrantDto embryologyRegistrantDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EmbryologyRegistrantDto> entity = new HttpEntity<EmbryologyRegistrantDto>(embryologyRegistrantDto, headers);
		ResponseEntity<EmbryologyRegistrantDto> exchange = restTemplate.exchange(EMBRYO_REGISTRANT_URL + "/save", HttpMethod.POST,
				entity, new ParameterizedTypeReference<EmbryologyRegistrantDto>() {
				}, embryologyRegistrantDto);
		return exchange.getBody();
	}

	
	// getting the EmbryoRegistrantDto by codeID and screenId from Database
	public EmbryologyRegistrantDto getEmbryoRegistrantByCodeAndScreenID(Long codeId, int screenId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<EmbryologyRegistrantDto> exchange = restTemplate.exchange(
				EMBRYO_REGISTRANT_URL + "/embryoRegistrantByCodeAndScreenId/{codeId}/{screenId}", HttpMethod.GET, null,
				new ParameterizedTypeReference<EmbryologyRegistrantDto>() {
				}, codeId, screenId);
		return exchange.getBody();

	}
}
