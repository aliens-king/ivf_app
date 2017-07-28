package org.cf.card.ui.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.cf.card.model.Clipart;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.util.Constants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Dell on 1/17/2015.
 */
@Component
public class UIClipartService {

	private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

	public Clipart getClipartById(long id) {
		ResponseEntity<Clipart> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clipart/{id}",
				HttpMethod.GET, null, new ParameterizedTypeReference<Clipart>() {
				}, id);
		return exchange.getBody();
	}

	public String getImage(long id) {
		// File f = new File(Constants.RESOURCE_FILEPATH_TEMP_CLIPARTS + id +
		// ".jpg");
		File f = new File(Constants.RESOURCE_FILEPATH_TEMP_CLIPARTS + id + ".jpg");
		// File f = new File(UIClipartService.class.getClassLoader()
		// .getResource(Constants.RESOURCE_FILEPATH_TEMP_CLIPARTS + id +
		// ".jpg").getFile());

		if (!f.exists() || f.isDirectory()) {
			ResponseEntity<byte[]> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clipart?id={id}",
					HttpMethod.GET, null, new ParameterizedTypeReference<byte[]>() {
					}, Configuration.getClipartDir() + id + ".jpg");

			InputStream in = new ByteArrayInputStream(exchange.getBody());

			if (!f.exists())
				f.mkdirs();
			// System.out.println("folder created!!");
			try {
				BufferedImage bImageFromConvert = ImageIO.read(in);
				ImageIO.write(bImageFromConvert, "jpg", f.getAbsoluteFile());
			} catch (IOException e) {

			}

		}
		return f.getAbsolutePath();
	}

}
