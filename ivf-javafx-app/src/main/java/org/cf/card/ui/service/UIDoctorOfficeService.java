package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.dto.DoctorNurseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class UIDoctorOfficeService extends UIBaseService {
	
private static final String DOCTOR_OFFICE_URL = BASE_URL + "/doctor_office";
	

	public List<DoctorNurseDto> getDoctorOfficeByCoupleId(Long coupleId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<DoctorNurseDto>> entity = restTemplate.exchange(DOCTOR_OFFICE_URL + "/getDoctorOfficeByCoupleId/{coupleId}", 
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<DoctorNurseDto>>() {
			}, coupleId);
		return  entity.getBody();
	}
	
	public DoctorNurseDto saveDoctorOffice(DoctorNurseDto doctorNurseDto) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DoctorNurseDto> httpEntity = new HttpEntity<DoctorNurseDto>(doctorNurseDto, httpHeaders);
		ResponseEntity<DoctorNurseDto> entity = restTemplate.exchange(DOCTOR_OFFICE_URL + "/saveDoctorOffice", 
				HttpMethod.POST, httpEntity, 
				new ParameterizedTypeReference<DoctorNurseDto>() {
		}, doctorNurseDto);
		return entity.getBody();
	}

}
