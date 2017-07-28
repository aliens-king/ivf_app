package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;

import org.cf.card.dto.CompanyDto;
import org.cf.card.service.CompanyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class CompanyController.
 *
 * @author Pramod Maurya
 * @param <MultipartFormDataInput>
 */

@RestController
@RequestMapping(value = "/company")
public class CompanyController<MultipartFormDataInput> {

	public static final String RESOURCE_FILEPATH_TEMP_LOGO = System.getProperty("user.home") + File.separator
			+ "AppData" + File.separator + "Local" + "MVID" + File.separator + "Logo" + File.separator;

	@Autowired
	private CompanyDetailsService companyDetailsService;

	/**
	 * Save company details.
	 *
	 * @param companyDto
	 *            the company dto
	 * @return the company dto
	 */
	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public CompanyDto saveCompanyDetails(@RequestBody CompanyDto companyDto) {
		return companyDetailsService.save(companyDto);
	}

	/**
	 * Gets the company details.
	 *
	 * @param id
	 *            the id
	 * @return the company details
	 */
	@RequestMapping(value = "/getCompanyDetails/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
	public CompanyDto getCompanyDetails(@PathVariable(value = "id") Long id) {
		// String rootPath=System.getProperty("catalina.home");

		return companyDetailsService.getCompanyDetails(id);
	}

	@RequestMapping(method = POST, value = "/saveCompanyLogo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Consumes("multipart/form-data")
	public String saveCompanylogo(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam(value = "logoDestPathsss") String logoDestPathsss) throws IllegalStateException, IOException {

		File destination = new File(logoDestPathsss);
		if (!destination.exists()) {
			destination.mkdirs();
		}
		multipartFile.transferTo(destination);
		return destination.getPath();
	}

}