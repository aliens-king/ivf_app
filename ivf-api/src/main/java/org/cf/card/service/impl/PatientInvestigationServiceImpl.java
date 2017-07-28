package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.dto.InvestigatinValueDto;
import org.cf.card.dto.PatientInvestigationDto;
import org.cf.card.model.Codes;
import org.cf.card.model.InvestigatinValue;
import org.cf.card.model.Investigation;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.model.User;
import org.cf.card.persistence.PatientInvestigationDao;
import org.cf.card.service.CodesService;
import org.cf.card.service.InvestigatinValueService;
import org.cf.card.service.PatientInvestigationService;
import org.cf.card.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("patientInvestigationService")
public class PatientInvestigationServiceImpl extends BaseServiceImpl<PatientInvestigation>
		implements PatientInvestigationService {

	protected PatientInvestigationDao patientInvestigationDao;

	@Autowired
	public void setPatientInvestigationDao(PatientInvestigationDao patientInvestigationDao) {
		this.patientInvestigationDao = patientInvestigationDao;
		setGenericDao(patientInvestigationDao);
	}

	@Autowired
	private CodesService codeService;

	@Autowired
	private InvestigatinValueService investigatinValueService;

	@Autowired
	private UserService userService;

	@Override
	public PatientInvestigationDto saveOrUpdate(PatientInvestigationDto dto) {

		// Saving woman data
		PatientInvestigation womanInvestigation = saveWomanData(dto);
		dto.setWomanPatientInvestigationId(womanInvestigation.getId());

		// Saving Partner data
		PatientInvestigation partnerInvestigation = savePartnerData(dto);
		dto.setPartnerPatientInvestigationId(partnerInvestigation.getId());

		return dto;
	}

	/**
	 * Save woman data.
	 *
	 * @param dto
	 *            the dto
	 * @return the patient investigation
	 */
	private PatientInvestigation saveWomanData(PatientInvestigationDto dto) {

		// Creating woman's investigation Object
		PatientInvestigation womanInvestigation = new PatientInvestigation();
		if (dto.getWomanPatientInvestigationId() != null) {
			// deleting old investigation values
			investigatinValueService.deleteByPatientInvestigationId(dto.getWomanPatientInvestigationId());
			womanInvestigation = this.findOne(dto.getWomanPatientInvestigationId());
		}
		Codes womanCode = codeService.findOne(dto.getWomanCodeId());
		womanInvestigation.setCode(womanCode);
		//womanInvestigation.setCode(new Codes(dto.getWomanCodeId()));
		
		womanInvestigation.setNurse(new User(dto.getNurseId()));
		womanInvestigation.setScan(dto.getScan());
		womanInvestigation.setBmi(dto.getBmi());
		// setting investigation value for woman
		List<InvestigatinValue> aoWomanInvestigatinValues = new ArrayList<>(12);
		aoWomanInvestigatinValues.addAll(investigatinValueEntityList(dto.getInvestigatinValues(), womanInvestigation));
		aoWomanInvestigatinValues.addAll(investigatinValueEntityList(dto.getAoMicrobiology(), womanInvestigation));
		aoWomanInvestigatinValues.addAll(investigatinValueEntityList(dto.getAoHormonal(), womanInvestigation));
		womanInvestigation.setInvestigatinValues(aoWomanInvestigatinValues);
		womanCode.setPatientInvestigation(womanInvestigation);
		// saving or updating to database
		womanInvestigation = this.save(womanInvestigation);
		// TODO: Might need to update Code with new Patient Investigation Id
		return womanInvestigation;
	}

	/**
	 * Save partner data.
	 *
	 * @param dto
	 *            the dto
	 * @return the patient investigation
	 */
	private PatientInvestigation savePartnerData(PatientInvestigationDto dto) {

		// Creating Partner object
		PatientInvestigation partnerInvestigation = new PatientInvestigation();
		if (dto.getPartnerPatientInvestigationId() != null) {
			// deleting old investigation values
			investigatinValueService.deleteByPatientInvestigationId(dto.getPartnerPatientInvestigationId());
			partnerInvestigation = this.findOne(dto.getPartnerPatientInvestigationId());
		}
		Codes manCode = codeService.findOne(dto.getPartnerCodeId());
		partnerInvestigation.setCode(manCode);
		partnerInvestigation.setNurse(new User(dto.getNurseId()));
		partnerInvestigation.setScan(dto.getScan());
		partnerInvestigation.setBmi(dto.getPartnerBMI());
		// setting investigation value for partner
		List<InvestigatinValue> aoPartnerInvestigatinValues = new ArrayList<>(12);
		aoPartnerInvestigatinValues.addAll(investigatinValueEntityList(dto.getAoPartnerBlood(), partnerInvestigation));
		aoPartnerInvestigatinValues
				.addAll(investigatinValueEntityList(dto.getAoPartnerMicrobiology(), partnerInvestigation));
		aoPartnerInvestigatinValues
				.addAll(investigatinValueEntityList(dto.getAoPartnerHormonal(), partnerInvestigation));
		partnerInvestigation.setInvestigatinValues(aoPartnerInvestigatinValues);

		manCode.setPatientInvestigation(partnerInvestigation);

		// saving or updating to database
		partnerInvestigation = this.save(partnerInvestigation);

		return partnerInvestigation;
	}

	/**
	 * Converts a DTO list to a JPA ENTITY list i.e List
	 * <InvestigatinValueDto> => List<InvestigatinValue>
	 *
	 * @param aoInvestigationValueDto
	 *            the List<InvestigatinValueDto>
	 * @param patientInvestigation
	 *            the patient investigation the foreign to be linked with
	 * @return the List<InvestigatinValue>
	 */
	private List<InvestigatinValue> investigatinValueEntityList(List<InvestigatinValueDto> aoInvestigationValueDto,
			PatientInvestigation patientInvestigation) {
		List<InvestigatinValue> aoInvestigatinValues = new ArrayList<>();
		for (InvestigatinValueDto investigatinValueDto : aoInvestigationValueDto) {
			aoInvestigatinValues.add(investigatinValueEntity(investigatinValueDto, patientInvestigation));
		}
		return aoInvestigatinValues;

	}

	/**
	 * Converts a DTO a JPA ENTITY list i.e InvestigatinValueDto =>
	 * InvestigatinValue
	 *
	 * @param dto
	 *            the InvestigatinValueDto
	 * @param patientInvestigation
	 *            the patient investigation the foreign key
	 * @return the entity InvestigatinValue
	 */
	private InvestigatinValue investigatinValueEntity(InvestigatinValueDto dto,
			PatientInvestigation patientInvestigation) {
		InvestigatinValue entity = new InvestigatinValue();
		entity.setValue(dto.getValue());
		entity.setInvestigation(new Investigation(dto.getInvestigationId()));
		entity.setPatientInvestigation(patientInvestigation);
		return entity;
	}

	@Override
	public PatientInvestigationDto addWomanPatientInvestigation(PatientInvestigationDto patientInvestigationDto) {

		User nurse = userService.findOne(patientInvestigationDto.getNurseId());
		// woman patient investigation object
		Codes code = codeService.findOne(patientInvestigationDto.getWomanCodeId());

		PatientInvestigation pInvestigation = new PatientInvestigation();
		pInvestigation.setCode(code);
		pInvestigation.setBmi(patientInvestigationDto.getBmi());
		pInvestigation.setNurse(nurse);
		pInvestigation.setScan(patientInvestigationDto.getScan());
		List<InvestigatinValueDto> valuesList = patientInvestigationDto.getInvestigatinValues();
		for (InvestigatinValueDto uiInvestigatinValue : valuesList) {
			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(pInvestigation);
			pInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		List<InvestigatinValueDto> aoMicrobiology = patientInvestigationDto.getAoMicrobiology();
		for (InvestigatinValueDto uiInvestigatinValue : aoMicrobiology) {
			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(pInvestigation);
			pInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		List<InvestigatinValueDto> aoHormonal = patientInvestigationDto.getAoHormonal();
		for (InvestigatinValueDto uiInvestigatinValue : aoHormonal) {
			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(pInvestigation);
			pInvestigation.getInvestigatinValues().add(investigatinValue);
		}

		PatientInvestigation patientInvestigation = patientInvestigationDao.save(pInvestigation);
		if (patientInvestigation != null) {
			code.setPatientInvestigation(patientInvestigation);
			codeService.saveOrUpdate(code);
		}
		patientInvestigationDto.setWomanCodeId(patientInvestigation.getCode().getId());
		patientInvestigationDto.setWomanPatientInvestigationId(patientInvestigation.getId());
		return patientInvestigationDto;
	}

	@Override
	public PatientInvestigationDto addPartnerPatientInvestigation(PatientInvestigationDto patientInvestigationDto) {
		User nurse = userService.findOne(patientInvestigationDto.getNurseId());
		Codes partnerCode = codeService.findOne(patientInvestigationDto.getPartnerCodeId());
		PatientInvestigation patnerPInvestigation = new PatientInvestigation();
		patnerPInvestigation.setCode(partnerCode);
		patnerPInvestigation.setBmi(patientInvestigationDto.getPartnerBMI());
		patnerPInvestigation.setNurse(nurse);
		List<InvestigatinValueDto> aoPartnerBlood = patientInvestigationDto.getAoPartnerBlood();
		for (InvestigatinValueDto uiInvestigatinValue : aoPartnerBlood) {
			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(patnerPInvestigation);
			patnerPInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		List<InvestigatinValueDto> aoPartnerMicrobiology = patientInvestigationDto.getAoPartnerMicrobiology();
		for (InvestigatinValueDto uiInvestigatinValue : aoPartnerMicrobiology) {
			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(patnerPInvestigation);
			patnerPInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		List<InvestigatinValueDto> aoPartnerHormonal = patientInvestigationDto.getAoPartnerHormonal();
		for (InvestigatinValueDto uiInvestigatinValue : aoPartnerHormonal) {
			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(patnerPInvestigation);
			patnerPInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		PatientInvestigation partnerPatientInvestigation = patientInvestigationDao.save(patnerPInvestigation);
		if (partnerPatientInvestigation != null) {
			partnerCode.setPatientInvestigation(partnerPatientInvestigation);
			codeService.saveOrUpdate(partnerCode);
		}

		patientInvestigationDto.setPartnerCodeId(partnerCode.getId());
		patientInvestigationDto.setPartnerPatientInvestigationId(partnerPatientInvestigation.getId());
		return patientInvestigationDto;
	}

	@Override
	public List<PatientInvestigation> updateWomanPatientInvestigation(PatientInvestigationDto patientInvestigationDto) {
		List<PatientInvestigation> aoPatientInvestigation = new ArrayList<>();
		// User nurse =
		// userService.findOne(patientInvestigationDto.getNurseId());
		PatientInvestigation womanPatientInvestigation = patientInvestigationDao
				.findOne(patientInvestigationDto.getWomanPatientInvestigationId());

		// update woman patient investigation saving data
		womanPatientInvestigation.setBmi(patientInvestigationDto.getBmi());
		womanPatientInvestigation.setScan(patientInvestigationDto.getScan());
		// womanPatientInvestigation.setNurse(nurse);
		List<InvestigatinValueDto> valuesList = patientInvestigationDto.getInvestigatinValues();
		for (InvestigatinValueDto uiInvestigatinValue : valuesList) {
			InvestigatinValue investigatinValue = investigatinValueService
					.findOne(uiInvestigatinValue.getInvestigationValueId());
			// investigatinValue.setId(uiInvestigatinValue.getInvestigationValueId());
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(womanPatientInvestigation);
			womanPatientInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		List<InvestigatinValueDto> aoMicrobiology = patientInvestigationDto.getAoMicrobiology();
		for (InvestigatinValueDto uiInvestigatinValue : aoMicrobiology) {
			InvestigatinValue investigatinValue = investigatinValueService
					.findOne(uiInvestigatinValue.getInvestigationValueId());
			// investigatinValue.setId(uiInvestigatinValue.getInvestigationValueId());
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(womanPatientInvestigation);
			womanPatientInvestigation.getInvestigatinValues().add(investigatinValue);
		}
		List<InvestigatinValueDto> aoHormonal = patientInvestigationDto.getAoHormonal();
		for (InvestigatinValueDto uiInvestigatinValue : aoHormonal) {
			InvestigatinValue investigatinValue = investigatinValueService
					.findOne(uiInvestigatinValue.getInvestigationValueId());
			// investigatinValue.setId(uiInvestigatinValue.getInvestigationValueId());
			investigatinValue.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(womanPatientInvestigation);
			womanPatientInvestigation.getInvestigatinValues().add(investigatinValue);
		}

		PatientInvestigation woman = patientInvestigationDao.save(womanPatientInvestigation);
		aoPatientInvestigation.add(woman);
		return aoPatientInvestigation;

	}

	@Override
	public List<PatientInvestigation> updatePartnerPatientInvestigation(
			PatientInvestigationDto patientInvestigationDto) {
		List<PatientInvestigation> aoPatientInvestigation = new ArrayList<>();
		// update partner patient investigation saving data
		PatientInvestigation partnerPatientInvestigation = patientInvestigationDao
				.findOne(patientInvestigationDto.getPartnerPatientInvestigationId());
		partnerPatientInvestigation.setBmi(patientInvestigationDto.getPartnerBMI());
		// partnerPatientInvestigation.setNurse(nurse);
		List<InvestigatinValueDto> aoPartnerBlood = patientInvestigationDto.getAoPartnerBlood();
		for (InvestigatinValueDto uiInvestigatinValue : aoPartnerBlood) {
			InvestigatinValue investigatinValue1 = investigatinValueService
					.findOne(uiInvestigatinValue.getInvestigationValueId());
			// investigatinValue1.setId(uiInvestigatinValue.getInvestigationValueId());
			investigatinValue1.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue1.setInvestigation(investigation);
			investigatinValue1.setPatientInvestigation(partnerPatientInvestigation);
			partnerPatientInvestigation.getInvestigatinValues().add(investigatinValue1);
		}
		List<InvestigatinValueDto> aoPartnerMicrobiology = patientInvestigationDto.getAoPartnerMicrobiology();
		for (InvestigatinValueDto uiInvestigatinValue : aoPartnerMicrobiology) {
			InvestigatinValue investigatinValue1 = investigatinValueService
					.findOne(uiInvestigatinValue.getInvestigationValueId());
			// investigatinValue1.setId(uiInvestigatinValue.getInvestigationValueId());
			investigatinValue1.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue1.setInvestigation(investigation);
			investigatinValue1.setPatientInvestigation(partnerPatientInvestigation);
			partnerPatientInvestigation.getInvestigatinValues().add(investigatinValue1);
		}
		List<InvestigatinValueDto> aoPartnerHormonal = patientInvestigationDto.getAoPartnerHormonal();
		for (InvestigatinValueDto uiInvestigatinValue : aoPartnerHormonal) {
			InvestigatinValue investigatinValue1 = investigatinValueService
					.findOne(uiInvestigatinValue.getInvestigationValueId());
			// investigatinValue1.setId(uiInvestigatinValue.getInvestigationValueId());
			investigatinValue1.setValue(uiInvestigatinValue.getValue());
			Investigation investigation = new Investigation();
			investigation.setId(uiInvestigatinValue.getInvestigationId());
			investigatinValue1.setInvestigation(investigation);
			investigatinValue1.setPatientInvestigation(partnerPatientInvestigation);
			partnerPatientInvestigation.getInvestigatinValues().add(investigatinValue1);
		}

		PatientInvestigation partner = patientInvestigationDao.save(partnerPatientInvestigation);
		aoPatientInvestigation.add(partner);
		return aoPatientInvestigation;
	}

	/* (non-Javadoc)
	 * @see org.cf.card.service.PatientInvestigationService#getNurseByRoleId(java.lang.Long)
	 */
	@Override
	public List<User> getNurseByRoleId(int nurseId) {
		return patientInvestigationDao.findAlNurse(nurseId);
	}
}
