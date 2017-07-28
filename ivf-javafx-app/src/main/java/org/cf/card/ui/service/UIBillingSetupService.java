package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.dto.ProcedureDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Pramod Maurya
 * @since : Dec 28, 2016
 */
public class UIBillingSetupService extends UIBaseService {

	private static final String BILLING_SETUP_URL = BASE_URL + "/billingSetup";

	/**
	 * Save procedures.
	 *
	 * @param procedureDto the procedure dto
	 * @return the procedure dto
	 */
	public ProcedureDto saveProcedures(ProcedureDto procedureDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProcedureDto> entity = new HttpEntity<ProcedureDto>(procedureDto, headers);

		ResponseEntity<ProcedureDto> exchange = restTemplate.exchange(BILLING_SETUP_URL + "/save", HttpMethod.POST,
				entity, new ParameterizedTypeReference<ProcedureDto>() {
				});
		return exchange.getBody();
	}

	/**
	 * Find all procedure.
	 *
	 * @return the list
	 */
	public List<ProcedureDto> findAllProcedure() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProcedureDto> entity = new HttpEntity<ProcedureDto>(headers);
		ResponseEntity<List<ProcedureDto>> exchange = restTemplate.exchange(BILLING_SETUP_URL + "/findAll",
				HttpMethod.POST, entity, new ParameterizedTypeReference<List<ProcedureDto>>() {
				});
		return exchange.getBody();
	}

}
