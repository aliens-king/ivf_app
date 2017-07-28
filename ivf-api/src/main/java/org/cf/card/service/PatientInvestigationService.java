package org.cf.card.service;

import java.util.List;

import org.cf.card.dto.PatientInvestigationDto;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.model.User;

public interface PatientInvestigationService  extends BaseService<PatientInvestigation>{

	public PatientInvestigationDto addWomanPatientInvestigation(PatientInvestigationDto patientInvestigationDto);

	public PatientInvestigationDto addPartnerPatientInvestigation(PatientInvestigationDto patientInvestigationDto);

	public List<PatientInvestigation> updateWomanPatientInvestigation(PatientInvestigationDto patientInvestigationDto);

	public List<PatientInvestigation> updatePartnerPatientInvestigation(PatientInvestigationDto patientInvestigationDto);
	
	public List<User> getNurseByRoleId(int nurseId);

	/**
	 * Save or update patient investigation for Standrd Investigation screen.
	 *
	 * @param dto the dto
	 * @return the patient investigation dto
	 */
	PatientInvestigationDto saveOrUpdate(PatientInvestigationDto dto);

}
