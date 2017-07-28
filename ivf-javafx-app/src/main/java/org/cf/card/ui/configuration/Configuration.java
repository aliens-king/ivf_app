package org.cf.card.ui.configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Dell on 2/3/2015.
 */
public class Configuration {

	private static final Logger log = Logger.getLogger(Configuration.class);

	private static final String jarPath = new File(Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getPath();

	private static Properties properties;
	private static RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

	private Configuration() {

	}

	public static void init(String configDir) throws IOException {

		properties = new Properties();
        String propertiesPath=URLDecoder.decode(jarPath+"/config/application.properties","UTF-8");
        log.info("loading properties file from path -> "+propertiesPath);
		properties.load(new FileReader(new File(propertiesPath)));
	}

	public static void validate(String configDir) throws IOException, HttpClientErrorException {

		restTemplate.exchange(Configuration.getServerUrl() + "/ping", HttpMethod.GET, null,
				new ParameterizedTypeReference<HttpStatus>() {
				}, new ParameterizedTypeReference<HttpStatus>() {
				});

		File testFile = new File(configDir + "/testConfig.txt");
		if (!testFile.delete()) {
			testFile.deleteOnExit();
		}
	}

	public static void validateDesignsDirectory(String designsDir) throws IOException {

		ResponseEntity<byte[]> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clipart?id={id}",
				HttpMethod.GET, null, new ParameterizedTypeReference<byte[]>() {
				}, designsDir);

		if (exchange.hasBody()) {
			InputStream in = new ByteArrayInputStream(exchange.getBody());
			ImageIO.read(in);
		} else {
			throw new IOException("design directory not set");
		}

	}

	private static String getValue(String key) {
		return properties.getProperty(key);
	}

	public static String getServerUrl() {
		return getValue("server.url");
	}

	public static void setServerUrl(String url) {
		properties.setProperty("server.url", url);
	}

	public static void saveParamChanges(String configDir) {
		try {
			Properties props = new Properties();
			props.setProperty("server.url", getValue("server.url"));
			props.setProperty("clipart.dir", "" + getValue("clipart.dir"));
			props.setProperty("license.key", "" + getValue("license.key"));
			props.setProperty("company.name", "" + getValue("company.name"));

//			File f = new File(Configuration.class.getResource("/config/application.properties").getFile());
			File f= new File(URLDecoder.decode(jarPath+"/config/application.properties","UTF-8"));
			log.info("Storing new licesne key "+f.getAbsolutePath());
			OutputStream out = new FileOutputStream(f);

			props.store(out, "Storing Licesne key");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed to store license", e);
		}
	}

	public static String getClipartDir() {
		return getValue("clipart.dir");
	}

	public static void setClipartDir(String path) {
		properties.setProperty("clipart.dir", path);
	}

	public static String getLicenseKey() {
		return getValue("license.key");
	}

	public static void setLicenseKey(String licenseKey) {
		properties.setProperty("license.key", licenseKey);
	}

	public static String getCompanyName() {
		return getValue("company.name");
	}

	public static void setCompanyName(String name) {
		properties.setProperty("company.name", name);
	}

}
