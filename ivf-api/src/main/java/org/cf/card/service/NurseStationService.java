package org.cf.card.service;

import java.util.List;

import org.cf.card.dto.DoctorNurseDto;

public interface NurseStationService {

	/**
	 * getting the DoctorNurseDto by coupleId from Database
	 * @param coupleId the couple id
	 * @return the List<DoctorNurseDto> by couple id
	 */
	
	List<DoctorNurseDto> getNurseStationByCoupleId(Long coupleId);
	
	/**
	 * saving Nurse Station data to database with DoctorNurseDto object
	 * @param DoctorNurseDto the Doctor Nurse Dto 
	 * @return the DoctorNurseDto
	 */
	DoctorNurseDto saveNurseStation(DoctorNurseDto doctorNurseDto);
}
