package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.dto.DoctorNurseDto;
import org.cf.card.model.Couple;
import org.cf.card.model.NurseStation;
import org.cf.card.persistence.NurseStationDao;
import org.cf.card.service.NurseStationService;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("nurseStationService")
public class NurseStationServiceImpl extends BaseServiceImpl<NurseStation> implements NurseStationService {
	
	/**
	 * Sets the Nurse Station dao.
	 *
	 * @param nurseStationDao the new Nurse Station dao Report dao
	 */

	private NurseStationDao nurseStationDao;
	
	@Autowired
	public void setNurseStationDao(NurseStationDao nurseStationDao) {
		this.nurseStationDao = nurseStationDao;
		setGenericDao(nurseStationDao);
	}

	/**
	 * getting the DoctorNurseDto by coupleId from Database
	 * @param coupleId the couple id
	 * @return the DoctorNurseDto by couple id
	 */
	
	public List<DoctorNurseDto> getNurseStationByCoupleId(Long coupleId){
		List<DoctorNurseDto> doctorNurseDtoList = new ArrayList<>();
		List<NurseStation> nurseStationList = nurseStationDao.findNurseStationByCoupleId(coupleId);
		for (NurseStation nurseStation : nurseStationList) {
			DoctorNurseDto doctorNurseDto = createNurseStationDto(nurseStation);
			doctorNurseDtoList.add(doctorNurseDto);
		}
		return doctorNurseDtoList;
	}
	
	
	private DoctorNurseDto createNurseStationDto(NurseStation nurseStation){
		//setting DB values in Dto
		DoctorNurseDto doctorNurseDto = new DoctorNurseDto();
		doctorNurseDto.setNurseNotes(nurseStation.getNurseNotes());
		doctorNurseDto.setRemarks(nurseStation.getRemarks());
		doctorNurseDto.setCoupleId(nurseStation.getCouple().getId());
		doctorNurseDto.setId(nurseStation.getId());
		doctorNurseDto.setCreatedDate(nurseStation.getCreatedDate());
		return doctorNurseDto;
	}
	
	/**
	 * saving Nurse Station data to database with DoctorNurseDto object
	 * @param DoctorNurseDto the Doctor Nurse Dto 
	 * @return the DoctorNurseDto
	 */
	public DoctorNurseDto saveNurseStation(DoctorNurseDto doctorNurseDto){
		
		NurseStation nurseStation = createNurseStation(doctorNurseDto);
		Date currentDate = new Date();
		String sCurrentDate = Util.formatDate(IConstants.DATE_FORMAT, currentDate);
		String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, doctorNurseDto.getCreatedDate());
		if(null != doctorNurseDto.getId() && sCurrentDate.compareTo(sCreatedDate) == 0)
			nurseStation.setId(doctorNurseDto.getId());
		
//		nurseStationDao.save(nurseStation);
		NurseStation savedNS = nurseStationDao.save(nurseStation);
		return createNurseStationDto(savedNS);
	}
	
	private NurseStation createNurseStation(DoctorNurseDto doctorNurseDto){
		NurseStation nurseStation = new NurseStation();
		nurseStation.setCouple(new Couple(doctorNurseDto.getCoupleId()));
		nurseStation.setNurseNotes(doctorNurseDto.getNurseNotes());
		nurseStation.setRemarks(doctorNurseDto.getRemarks());
		nurseStation.setCreatedDate(doctorNurseDto.getCreatedDate());
		return nurseStation;
	}

}
