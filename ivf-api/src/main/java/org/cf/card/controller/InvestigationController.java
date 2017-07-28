package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.cf.card.model.Investigation;
import org.cf.card.service.InvestigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/investigation")
public class InvestigationController {

	@Autowired
	InvestigationService investigationService;

	@RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
	public List<Investigation> getGroupColumns(@RequestParam("key") int key){
		List<Investigation> group = investigationService.findByGroup(key);
		return group;
	}

	@RequestMapping(value ="/all",method = GET)
	public @ResponseBody  List<Investigation> findAllInvestigation(){
		List<Investigation> group = investigationService.findAll();
		return group;
	}



}
