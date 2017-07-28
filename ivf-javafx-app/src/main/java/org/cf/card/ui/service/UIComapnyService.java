package org.cf.card.ui.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.cf.card.dto.CompanyDto;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.util.Constants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author Pramod Maurya
 *
 */
public class UIComapnyService extends UIBaseService {

	private static final String COMPANY_URL = BASE_URL + "/company";

	/**
	 * Save company details.
	 *
	 * @param companyDto
	 *            the company dto
	 * @return the company dto
	 */
	public CompanyDto saveCompanyDetails(CompanyDto companyDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CompanyDto> entity = new HttpEntity<CompanyDto>(companyDto, headers);

		ResponseEntity<CompanyDto> exchange = restTemplate.exchange(COMPANY_URL + "/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<CompanyDto>() {
				});
		return exchange.getBody();

	}

	/**
	 * Gets the company details.
	 *
	 * @param id the id
	 * @return the company details
	 */
	public CompanyDto getCompanyDetails(Long id) {

		ResponseEntity<CompanyDto> exchange = restTemplate.exchange(COMPANY_URL + "/getCompanyDetails/{id}",
				HttpMethod.GET, null, new ParameterizedTypeReference<CompanyDto>() {
				}, id);
		return exchange.getBody();

	}

	/**
	 * Save companylogo.
	 *
	 * @param file
	 *            the file
	 * @return the string
	 */
	public String saveCompanylogo(File file, String logoDestPathsss) {

		String url = COMPANY_URL + "/saveCompanyLogo";
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		try {
			map.add("file", new FileSystemResource(file));
			map.add("logoDestPathsss", logoDestPathsss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return exchange.getBody();
	}

	
	/**
	 * Gets the image.
	 *
	 * @param id
	 *            the id
	 * @return the image
	 */
	// in this method there is no use of id
	public String getImage(long id) {

		// path for temporary store Company Logo.
		File f = new File(Constants.RESOURCE_FILEPATH_TEMP_LOGO);
		InputStream in = null;
		if (f.exists())
			f.delete();
		if (!f.exists() || f.isDirectory()) {
			ResponseEntity<byte[]> exchange = restTemplate.exchange(
					Configuration.getServerUrl() + "/clipart/companyLogo?logoName={logoName}", HttpMethod.GET, null,
					new ParameterizedTypeReference<byte[]>() {
					}, Configuration.getClipartDir() + Constants.RESOURCE_FILEPATH_PERM_LOGO);

			if (null != exchange.getBody()) {

				in = new ByteArrayInputStream(exchange.getBody());
				if (!f.exists())
					f.mkdirs();
				// System.out.println("folder created!!");
				try {
					BufferedImage bImageFromConvert = ImageIO.read(in);
					ImageIO.write(bImageFromConvert, "png", f.getAbsoluteFile());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		return f.getAbsolutePath();
	}

}