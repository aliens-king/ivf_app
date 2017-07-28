/**
 * 
 */
package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.cf.card.dto.PregnancyOutcomesDto;
import org.cf.card.service.PregnancyOutcomesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author insonix
 *
 */

@RestController
@RequestMapping(value = "/pregnancyOutcomes")
public class PregnancyController {

	@Autowired
	private PregnancyOutcomesService pregnancyOutcomesService;

	/**
	 * Save pregnancy.
	 *
	 * @param pregnancyOutcomesDto the pregnancy outcomes dto
	 * @return the pregnancy outcomes dto
	 */
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	private @ResponseBody PregnancyOutcomesDto savePregnancy(@RequestBody PregnancyOutcomesDto pregnancyOutcomesDto) {
		return pregnancyOutcomesService.save(pregnancyOutcomesDto);
	}

	/**
	 * Gets the pregnancy outcomes by woman code id.
	 *
	 * @param womanCodeId the woman code id
	 * @return the pregnancy outcomes by woman code id
	 */
	@RequestMapping(value = "/pregnancyOutcomeByCodeId/{womanCodeId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody PregnancyOutcomesDto getPregnancyOutcomesByWomanCodeId(
			@PathVariable(value = "womanCodeId") Long womanCodeId) {
		return pregnancyOutcomesService.getPregnancyOutcome(womanCodeId);
	}

}
