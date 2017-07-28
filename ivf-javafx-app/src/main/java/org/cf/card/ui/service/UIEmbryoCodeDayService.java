package org.cf.card.ui.service;

import org.cf.card.dto.EmbryoCodeDayDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class UIEmbryoCodeDayService extends UIBaseService {

	private static final String REMARKS_DAY_DIALOG_URL = BASE_URL + "/remarksDayDialog";

	// save or update EmbryoCodeDayRemarks object in database
	public EmbryoCodeDayDto saveRemark(EmbryoCodeDayDto embryoCodeDayDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EmbryoCodeDayDto> entity = new HttpEntity<>(embryoCodeDayDto, headers);

		ResponseEntity<EmbryoCodeDayDto> exchange = restTemplate.exchange(REMARKS_DAY_DIALOG_URL + "/save",
				HttpMethod.POST, entity, new ParameterizedTypeReference<EmbryoCodeDayDto>() {
				}, embryoCodeDayDto);
		return exchange.getBody();
	}

	// getting here EmbryoCodeDayDto object by embryoCodeId and DayIndex
	public EmbryoCodeDayDto getRemarksByEmbryoCodeIdAndDayIndex(Long embryoCodeId, int remarkDayIndex) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<EmbryoCodeDayDto> exchange = restTemplate.exchange(
				REMARKS_DAY_DIALOG_URL + "/remarksByEmbryoCodeIdAndDayIndex/{embryoCodeId}/{remarkDayIndex}",
				HttpMethod.GET, null, new ParameterizedTypeReference<EmbryoCodeDayDto>() {
				}, embryoCodeId, remarkDayIndex);

		return exchange.getBody();

	}
}
