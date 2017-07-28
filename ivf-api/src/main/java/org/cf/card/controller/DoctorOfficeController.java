package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.DoctorNurseDto;
import org.cf.card.service.DoctorOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "doctor_office")
public class DoctorOfficeController {
	
	@Autowired
	DoctorOfficeService doctorOfficeService;
	
	@RequestMapping(value = "/saveDoctorOffice", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody DoctorNurseDto saveDoctorOffice(@RequestBody DoctorNurseDto doctorNurseDto){
		return doctorOfficeService.saveDoctorOffice(doctorNurseDto);
	}
	
	
	@RequestMapping(value = "/getDoctorOfficeByCoupleId/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<DoctorNurseDto> getDoctorOfficeByCoupleId(@PathVariable(value= "coupleId") Long coupleId){
		return doctorOfficeService.getDoctorOfficeByCoupleId(coupleId);
	}
}
