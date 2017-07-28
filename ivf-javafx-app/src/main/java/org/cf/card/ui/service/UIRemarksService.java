package org.cf.card.ui.service;

import org.cf.card.dto.RemarksDto;
import org.cf.card.model.Remark;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class UIRemarksService extends UIBaseService {

	private static final String REMARKS_URL = BASE_URL + "/remarks";

	// saving Remarks text to database with RemarksDto object
	public RemarksDto save(RemarksDto remarksDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RemarksDto> entity = new HttpEntity<>(remarksDto, headers);

		ResponseEntity<RemarksDto> exchange = restTemplate.exchange(REMARKS_URL + "/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<RemarksDto>() {
				}, remarksDto);
		return exchange.getBody();
	}

	// getting the remarksObject by codeID and remarksType from Database
	public RemarksDto getRemarksByCodeId(Long codeId, int remarksType) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<RemarksDto> exchange = restTemplate.exchange(
				REMARKS_URL + "/remarksByCodeId/{codeId}/{remarksType}", HttpMethod.GET, null,
				new ParameterizedTypeReference<RemarksDto>() {
				}, codeId, remarksType

		);

		return exchange.getBody();

	}
}
