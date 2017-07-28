package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.cf.card.dto.EmbryoCodeDayDto;
import org.cf.card.service.EmbryoCodeDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/remarksDayDialog")
public class EmbryoCodeDayController {

	@Autowired
	private EmbryoCodeDayService embryoCodeDayService;

	// save or update EmbryoCodeDayRemarks object in database
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody EmbryoCodeDayDto addRemarks(@RequestBody EmbryoCodeDayDto embryoCodeDayDto) {
		return embryoCodeDayService.saveRemark(embryoCodeDayDto);
	}

	// getting here EmbryoCodeDayDto object by embryoCodeId and DayIndex
	@RequestMapping(value = "/remarksByEmbryoCodeIdAndDayIndex/{embryoCodeId}/{remarkDayIndex}", method = GET, produces = APPLICATION_JSON_VALUE)
	public EmbryoCodeDayDto getRemarksByEmbryoCodeIdAndDayIndex(@PathVariable(value = "embryoCodeId") Long embryoCodeId,
			@PathVariable(value = "remarkDayIndex") int remarkDayIndex) {
		return embryoCodeDayService.getRemarksByEmbryoCodeIdAndDayIndex(embryoCodeId, remarkDayIndex);
	}

}
