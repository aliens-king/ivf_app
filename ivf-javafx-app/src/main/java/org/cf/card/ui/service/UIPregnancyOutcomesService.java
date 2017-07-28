/**
 * 
 */
package org.cf.card.ui.service;

import org.cf.card.dto.PregnancyOutcomesDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author insonix
 *
 */
public class UIPregnancyOutcomesService extends UIBaseService {

	private static final String PREGNANCY_OUTCOMES_URL = BASE_URL + "/pregnancyOutcomes";

	/**
	 * Save pregnancy outcome.
	 *
	 * @param outcomesDto
	 *            the outcomes dto
	 * @return the pregnancy outcomes dto
	 */
	public PregnancyOutcomesDto savePregnancyOutcome(PregnancyOutcomesDto outcomesDto) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PregnancyOutcomesDto> entity = new HttpEntity<>(outcomesDto, httpHeaders);

		ResponseEntity<PregnancyOutcomesDto> exchange = restTemplate.exchange(PREGNANCY_OUTCOMES_URL + "/save",
				HttpMethod.POST, entity, new ParameterizedTypeReference<PregnancyOutcomesDto>() {
				}, outcomesDto);
		return exchange.getBody();

	}

	/**
	 * Gets the pregnancy outcomes by code id.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @return the pregnancy outcomes by code id
	 */
	public PregnancyOutcomesDto getPregnancyOutcomesByWomanCodeId(Long womanCodeId) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<PregnancyOutcomesDto> exchange = restTemplate.exchange(
				PREGNANCY_OUTCOMES_URL + "/pregnancyOutcomeByCodeId/{womanCodeId}", HttpMethod.GET, null,
				new ParameterizedTypeReference<PregnancyOutcomesDto>() {
				}, womanCodeId);

		return exchange.getBody();

	}

}
