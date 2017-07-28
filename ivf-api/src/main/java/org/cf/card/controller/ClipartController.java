package org.cf.card.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.cf.card.service.ClipartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dell on 1/17/2015.
 */
@RequestMapping(value = "/clipart")
@RestController
public class ClipartController {

	@Autowired
	ClipartService clipartService;

	/**
	 * Gets the image by id.
	 *
	 * @param id
	 *            the id
	 * @return the image by id
	 */
	@RequestMapping(method = GET, produces = "id/jpg")
	public @ResponseBody byte[] getImageById(@RequestParam(value = "id", required = true) String id) {
		return clipartService.getClipartImage(id);
	}

	/**
	 * Gets the image by name.
	 *
	 * @param logoName
	 *            the logo name
	 * @return the image by name
	 */
	@RequestMapping(value = "/companyLogo", method = GET, produces = "logoName/png")
	public @ResponseBody byte[] getImageByName(@RequestParam(value = "logoName", required = true) String logoName) {
		return clipartService.getClipartImage(logoName);
	}

}