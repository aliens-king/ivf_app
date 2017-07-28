package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.dto.DoctorNurseDto;
import org.cf.card.model.Couple;
import org.cf.card.model.DoctorOffice;
import org.cf.card.persistence.DoctorOfficeDao;
import org.cf.card.service.DoctorOfficeService;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("doctorOfficeService")
public class DoctorOfficeServiceImpl extends BaseServiceImpl<DoctorOffice> implements DoctorOfficeService{
	
	DoctorOfficeDao doctorOfficeDao;
	
	
	/**
	 * Sets the Doctor Office dao.
	 *
	 * @param doctorOfficeDao the new Doctor Office dao Report dao
	 */

	@Autowired
	public void setDoctorOfficeDao(DoctorOfficeDao doctorOfficeDao) {
		this.doctorOfficeDao = doctorOfficeDao;
		setGenericDao(doctorOfficeDao);
	}

	@Override
	public List<DoctorNurseDto> getDoctorOfficeByCoupleId(Long coupleId){
		List<DoctorNurseDto> doctorNurseDtoList = new ArrayList<>();
		List<DoctorOffice> doctorOfficeList = doctorOfficeByCoupleId(coupleId);
		for(DoctorOffice doctorOffice : doctorOfficeList){
			DoctorNurseDto doctorNurseDto = createDoctorOfficeDto(doctorOffice); // setting DB values in DTO
			doctorNurseDtoList.add(doctorNurseDto);
		}
		return doctorNurseDtoList;
	}

	private List<DoctorOffice> doctorOfficeByCoupleId(Long coupleId){
		return doctorOfficeDao.findDoctorOfficeByCoupleId(coupleId);
	}
	
	private DoctorNurseDto createDoctorOfficeDto(DoctorOffice doctorOffice){
		DoctorNurseDto doctorNurseDto = new DoctorNurseDto();
		if(doctorOffice != null){
			doctorNurseDto.setCoupleId(doctorOffice.getCouple().getId());
			doctorNurseDto.setId(doctorOffice.getId());
			doctorNurseDto.setLabPoints(doctorOffice.getLabPoints());
			doctorNurseDto.setMedicalHistory(doctorOffice.getMedicalHistory());
			doctorNurseDto.setRemarks(doctorOffice.getRemarks());
			doctorNurseDto.setCreatedDate(doctorOffice.getCreatedDate());
		}
		return doctorNurseDto;
	}
	
	@Override
	public DoctorNurseDto saveDoctorOffice(DoctorNurseDto doctorNurseDto) {
		//saving Doctor Office data in doctor_office table
		DoctorOffice doctorOffice = createDoctorOffice(doctorNurseDto);
		Date currentDate = new Date();
		String sCurrentDate = Util.formatDate(IConstants.DATE_FORMAT, currentDate);
		String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, doctorNurseDto.getCreatedDate());
		if(null !=doctorNurseDto.getId() && sCurrentDate.compareTo(sCreatedDate) == 0)
			doctorOffice.setId(doctorNurseDto.getId());
		DoctorOffice savedDO = doctorOfficeDao.save(doctorOffice);
		DoctorNurseDto savedDto = createDoctorOfficeDto(savedDO);
		return savedDto;
	}
	
	private DoctorOffice createDoctorOffice(DoctorNurseDto doctorNurseDto){
		DoctorOffice doctorOffice = new DoctorOffice();
		doctorOffice.setCouple(new Couple(doctorNurseDto.getCoupleId()));
		doctorOffice.setLabPoints(doctorNurseDto.getLabPoints());
		doctorOffice.setMedicalHistory(doctorNurseDto.getMedicalHistory());
		doctorOffice.setRemarks(doctorNurseDto.getRemarks());
		doctorOffice.setCreatedDate(doctorNurseDto.getCreatedDate());
		return doctorOffice;
	}

}
