package org.cf.card.service;

import java.util.List;

import org.cf.card.dto.DoctorNurseDto;

public interface DoctorOfficeService {
	
	/**
	 * saving Doctor Office data to database with DoctorNurseDto object
	 * @param DoctorNurseDto the Doctor Nurse Dto 
	 * @return the DoctorNurseDto
	 */
	DoctorNurseDto saveDoctorOffice(DoctorNurseDto doctorNurseDto);
	
	/**
	 * getting the DoctorNurseDto by coupleId from Database
	 * @param coupleId the couple id
	 * @return the DoctorNurseDto by couple id
	 */
	
	List<DoctorNurseDto> getDoctorOfficeByCoupleId(Long coupleId);
	
/*	
	/**
	 * getting the DoctorOffice by coupleID from Database
	 * @param coupleId the couple id
	 * @return the DoctorOffice
	 *//*
	DoctorOffice doctorOfficeByCoupleId(Long coupleId);*/

}
