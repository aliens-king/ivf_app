/**
 * 
 */
package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.cf.card.dto.RegistrantDto;
import org.cf.card.service.RegistrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pramod Maurya
 * @since 10-Jul-2017
 */
@RestController
@RequestMapping(value = "/registrant")
public class RegistrantController {

	@Autowired
	private RegistrantService registrantService;

	// saving Registrant object to database
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody RegistrantDto addRegistrant(@RequestBody RegistrantDto registrantDto) {
		return registrantService.saveRegistrant(registrantDto);
	}

	// getting the remarksObject by codeID and remarksType from Database
	@RequestMapping(value = "/registrantByCodeAndScreenId/{codeId}/{screenId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public RegistrantDto getregistrantByCodeAndScreenId(@PathVariable(value = "codeId") Long codeId,
			@PathVariable(value = "screenId") int screenId) {
		return registrantService.getRegistrantByCodeAndScreenId(codeId, screenId);
	}

}
