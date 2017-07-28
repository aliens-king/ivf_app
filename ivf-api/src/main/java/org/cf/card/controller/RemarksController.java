package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.cf.card.dto.RemarksDto;
import org.cf.card.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/remarks")
public class RemarksController {

	@Autowired
	RemarkService remarkService;

	// saving Remarks text to database with RemarksDto object
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody RemarksDto addRemarks(@RequestBody RemarksDto remarksDto) {
		return remarkService.saveRemark(remarksDto);
	}

	// getting the remarksObject by codeID and remarksType from Database
	@RequestMapping(value = "/remarksByCodeId/{codeId}/{remarksType}", method = GET, produces = APPLICATION_JSON_VALUE)
	public RemarksDto getRemarksByCodeId(@PathVariable(value = "codeId") Long codeId,
			@PathVariable(value = "remarksType") int remarksType) {
		return remarkService.getRemarksByCodeId(codeId, remarksType);
	}

}
