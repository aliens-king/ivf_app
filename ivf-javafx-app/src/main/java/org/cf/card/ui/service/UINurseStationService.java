package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.dto.DoctorNurseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class UINurseStationService extends UIBaseService {
	
	private static final String NURSE_STATION_URL = BASE_URL + "/nurse_station";
	
	public List<DoctorNurseDto> getNurseStationByCoupleId(Long coupleId){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<DoctorNurseDto>> responseEntity = restTemplate.exchange(NURSE_STATION_URL + "/getNurseStationByCoupleId/{coupleId}", 
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<DoctorNurseDto>>() {
				}, coupleId) ;
		
		return responseEntity.getBody();
	}
	
	public DoctorNurseDto saveNurseStation(DoctorNurseDto doctorNurseDto){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DoctorNurseDto> httpEntity = new HttpEntity<DoctorNurseDto>(doctorNurseDto, httpHeaders);
		
	/*	restTemplate.exchange(NURSE_STATION_URL + "/saveNurseStation", 
				HttpMethod.POST, httpEntity, 
				new ParameterizedTypeReference<Void>() {
				}, doctorNurseDto);*/
		
		ResponseEntity<DoctorNurseDto> responseEntity = restTemplate.exchange(NURSE_STATION_URL + "/saveNurseStation", 
				HttpMethod.POST, httpEntity, 
				new ParameterizedTypeReference<DoctorNurseDto>() {
				}, doctorNurseDto);
		return responseEntity.getBody();
	}
}
