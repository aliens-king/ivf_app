package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.DoctorNurseDto;
import org.cf.card.service.NurseStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "nurse_station")
public class NurseStationController {
	
	@Autowired
	NurseStationService nurseStationService;
	
	@RequestMapping(value = "/getNurseStationByCoupleId/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<DoctorNurseDto> getNurseStationByCoupleId(@PathVariable(value = "coupleId") Long coupleId){
		return nurseStationService.getNurseStationByCoupleId(coupleId);
	}
	
	@RequestMapping(value = "/saveNurseStation", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody DoctorNurseDto saveNurseStation(@RequestBody DoctorNurseDto doctorNurseDto){
		return nurseStationService.saveNurseStation(doctorNurseDto);
//		nurseStationService.saveNurseStation(doctorNurseDto);
	}
}
