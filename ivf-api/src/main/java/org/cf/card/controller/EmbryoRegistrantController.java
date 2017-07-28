/**
 * 
 */
package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.cf.card.dto.EmbryologyRegistrantDto;
import org.cf.card.service.EmbryoRegistrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
@RestController
@RequestMapping(value = "/embryoRegistrant")
public class EmbryoRegistrantController {

	@Autowired
	private EmbryoRegistrantService embryoRegistrantService;

	// saving Registrant object to database
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody EmbryologyRegistrantDto addEmbryoRegistrant(
			@RequestBody EmbryologyRegistrantDto registrantDto) {
		return embryoRegistrantService.saveEmbryoRegistrant(registrantDto);
	}

	// getting the remarksObject by codeID and remarksType from Database
	@RequestMapping(value = "/embryoRegistrantByCodeAndScreenId/{codeId}/{screenId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public EmbryologyRegistrantDto getEmbryoRegistrantByCodeAndScreenId(@PathVariable(value = "codeId") Long codeId,
			@PathVariable(value = "screenId") int screenId) {
		return embryoRegistrantService.getEmbryoRegistrantByCodeAndScreenId(codeId, screenId);
	}

}
