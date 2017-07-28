package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.PatientInvestigationDto;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.model.User;
import org.cf.card.service.PatientInvestigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patientInvestigation")
public class PatientInvestigationController {

	@Autowired
	PatientInvestigationService patientInvestigationService;

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, value = "/addWomanPatientInvestigation")
	public @ResponseBody PatientInvestigationDto addWomanPatientInvestigation(
			@RequestBody PatientInvestigationDto uiPatientInvestigation) {
		return patientInvestigationService.addWomanPatientInvestigation(uiPatientInvestigation);
	}

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, value = "/addPartnerPatientInvestigation")
	public @ResponseBody PatientInvestigationDto addPartnerPatientInvestigation(
			@RequestBody PatientInvestigationDto uiPatientInvestigation) {
		return patientInvestigationService.addPartnerPatientInvestigation(uiPatientInvestigation);
	}

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, value = "/updateWomanPatientInvestigation")
	public @ResponseBody List<PatientInvestigation> updateWomanPatientInvestigation(
			@RequestBody PatientInvestigationDto uiPatientInvestigation) {
		return patientInvestigationService.updateWomanPatientInvestigation(uiPatientInvestigation);
	}

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, value = "/updatePartnerPatientInvestigation")
	public @ResponseBody List<PatientInvestigation> updatePartnerPatientInvestigation(
			@RequestBody PatientInvestigationDto uiPatientInvestigation) {
		return patientInvestigationService.updatePartnerPatientInvestigation(uiPatientInvestigation);
	}

	/**
	 * Save or update the patinet investigation in database
	 *
	 * @param patientInvestigationDto
	 *            the patient investigation dto
	 * @return the patient investigation dto
	 */
	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, value = "/saveOrUpdate")
	public @ResponseBody PatientInvestigationDto saveOrUpdate(
			@RequestBody PatientInvestigationDto patientInvestigationDto) {
		return patientInvestigationService.saveOrUpdate(patientInvestigationDto);
	}

	@RequestMapping(value = "/getNurseByNurseRoleId/{nurseId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<User> getNurseByRoleId(@PathVariable(value = "nurseId") int nurseId) {
		return patientInvestigationService.getNurseByRoleId(nurseId);
	}

}
