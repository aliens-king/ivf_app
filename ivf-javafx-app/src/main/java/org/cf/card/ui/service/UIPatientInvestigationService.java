package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.dto.PatientInvestigationDto;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.model.User;
import org.cf.card.ui.configuration.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UIPatientInvestigationService extends UIBaseService {

	private static final String PATIENT_INVESTIGATION_URL = BASE_URL + "/patientInvestigation";

	public PatientInvestigationDto addWomanPatientInvestigation(PatientInvestigationDto uiPatientInvestigation) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatientInvestigationDto> entity = new HttpEntity<>(uiPatientInvestigation, headers);
		ResponseEntity<PatientInvestigationDto> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + "/patientInvestigation/addWomanPatientInvestigation", HttpMethod.POST,
				entity, new ParameterizedTypeReference<PatientInvestigationDto>() {
				});
		return exchange.getBody();

	}

	public PatientInvestigationDto addPartnerPatientInvestigation(PatientInvestigationDto uiPatientInvestigation) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatientInvestigationDto> entity = new HttpEntity<>(uiPatientInvestigation, headers);
		ResponseEntity<PatientInvestigationDto> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + "/patientInvestigation/addPartnerPatientInvestigation", HttpMethod.POST,
				entity, new ParameterizedTypeReference<PatientInvestigationDto>() {
				});
		return exchange.getBody();

	}

	public List<PatientInvestigation> updateWomanPatientInvestigation(PatientInvestigationDto uiPatientInvestigation) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatientInvestigationDto> entity = new HttpEntity<>(uiPatientInvestigation, headers);
		ResponseEntity<List<PatientInvestigation>> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + "/patientInvestigation/updateWomanPatientInvestigation", HttpMethod.POST,
				entity, new ParameterizedTypeReference<List<PatientInvestigation>>() {
				});
		return exchange.getBody();

	}

	public List<PatientInvestigation> updatePartnerPatientInvestigation(
			PatientInvestigationDto uiPatientInvestigation) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatientInvestigationDto> entity = new HttpEntity<>(uiPatientInvestigation, headers);
		ResponseEntity<List<PatientInvestigation>> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + "/patientInvestigation/updatePartnerPatientInvestigation",
				HttpMethod.POST, entity, new ParameterizedTypeReference<List<PatientInvestigation>>() {
				});
		return exchange.getBody();

	}

	public List<PatientInvestigation> patientInvestigationInfo(Long id) {
		ResponseEntity<List<PatientInvestigation>> exchange = restTemplate.exchange(
				PATIENT_INVESTIGATION_URL + "?id={id}", HttpMethod.POST, null,
				new ParameterizedTypeReference<List<PatientInvestigation>>() {
				}, id);
		return exchange.getBody();

	}

	/**
	 * Save or update patient investigation by sending PatientInvestigationDto
	 * is Request Body
	 *
	 * @param patientInvestigationDto
	 *            the patient investigation dto
	 * @return the patient investigation dto
	 */
	public PatientInvestigationDto saveOrUpdate(PatientInvestigationDto patientInvestigationDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PatientInvestigationDto> entity = new HttpEntity<>(patientInvestigationDto, headers);
		ResponseEntity<PatientInvestigationDto> exchange = restTemplate.exchange(
				PATIENT_INVESTIGATION_URL + "/saveOrUpdate", HttpMethod.POST, entity,
				new ParameterizedTypeReference<PatientInvestigationDto>() {
				});
		return exchange.getBody();

	}

	public List<User> getNurseByRoleId(int nurseId) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
				PATIENT_INVESTIGATION_URL + "/getNurseByNurseRoleId/{nurseId}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				}, nurseId);

		return responseEntity.getBody();
	}

}
