package org.cf.card.ui.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.cf.card.dto.ArchiveDto;
import org.cf.card.model.Archive;
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
 * @author Ankit
 *
 */
public class UIArchiveService extends UIBaseService {

	private static final String COMPANY_URL = BASE_URL + "/archive";

	/**
	 * Save file.
	 *
	 * @param file
	 *            the file
	 * @return the string
	 */
	public String saveArchiveFile(File file, String filePath, long coupleId) {
		String url = COMPANY_URL + "/uploadArchive";
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		try {
			map.add("file", new FileSystemResource(file));
			map.add("filePath", filePath);
			map.add("coupleID", coupleId);
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

	public String getFileFromServer(ArchiveDto archiveDto) {
		File f = new File(Constants.RESOURCE_ARCHIVE_TEMP_CLIPARTS + archiveDto.getCoupleId());
		File file = null;
		String localUrl = Configuration.getClipartDir() + "archive/";
		String serverUrl = Configuration.getServerUrl() + "/archive/archiveFile?id=" + archiveDto.getId() + "&path="
				+ localUrl;
		if (!f.exists() || f.isDirectory()) {
			ResponseEntity<byte[]> exchange = restTemplate.exchange(serverUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<byte[]>() {
					}, Configuration.getClipartDir() + "archive/" + archiveDto.getOrignalFileName());
			if (!f.exists())
				f.mkdirs();
			try {
				String filePath = f.getAbsolutePath() + "/" + archiveDto.getOrignalFileName();
				file = new File(filePath);
				if (!file.exists()) {
					FileUtils.writeByteArrayToFile(file, exchange.getBody());
				}
			} catch (IOException e) {
			}
		}
		return file.getAbsolutePath();
	}

	public List<ArchiveDto> archiveList(long coupleId) {
		String url = COMPANY_URL + "/map?couple={coupleId}";
		ResponseEntity<List<ArchiveDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ArchiveDto>>() {
				}, coupleId);
		return exchange.getBody();
	}

	/**
	 * Delete uploaded file.
	 *
	 * @param fileId
	 *            the file id
	 */
	public void deleteUploadedFile(Long fileId) {

		restTemplate.exchange(COMPANY_URL + "/delete/{fileId}", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Archive>() {
				}, fileId);

	}
}
