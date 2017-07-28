package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;

import org.cf.card.dto.ArchiveDto;
import org.cf.card.model.Couple;
import org.cf.card.service.ArchiveService;
import org.cf.card.service.CoupleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class ArchiveController.
 *
 * @author Ankit
 * @param <MultipartFormDataInput>
 */

@RestController
@RequestMapping(value = "/archive")
public class ArchiveController<MultipartFormDataInput> {
	@Autowired
	private ArchiveService archiveService;

	@Autowired
	private CoupleService coupleService;

	@RequestMapping(method = POST, value = "/uploadArchive")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Consumes("multipart/form-data")
	public String saveAndUploadArchive(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam(value = "filePath") String archiveDestPath, @RequestParam("coupleID") long coupleID)
					throws IllegalStateException, IOException {
		File destination = new File(archiveDestPath + coupleID);
		if (!destination.exists()) {
			destination.mkdirs();
		}
		try {
			String uniqueName = UUID.randomUUID().toString();
			// String filePath =
			// destination.getPath()+"/"+multipartFile.getOriginalFilename();
			String filePath = destination.getPath() + "/" + uniqueName;
			Files.write(Paths.get(filePath), multipartFile.getBytes(), StandardOpenOption.CREATE);
			Couple couple = coupleService.findOne(coupleID);
			archiveService.saveArchive(multipartFile, couple, uniqueName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination.getPath();
	}

	@RequestMapping(value = "/map", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<ArchiveDto> mapByArchive(@RequestParam(value = "couple") long coupleId) {
		return archiveService.findByCoupleId(coupleId);
	}

	/**
	 * Gets file archive by id.
	 *
	 * @param id
	 *            the id
	 * @return the image by id
	 */
	@RequestMapping(value = "/archiveFile", method = GET)
	public @ResponseBody byte[] getArchiveById(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "path", required = true) String localUrl) {
		ArchiveDto archive = archiveService.findById(id);
		return archiveService.getArchiveFile(archive, localUrl);
	}

	@RequestMapping(value = "/delete/{fileId}", method = DELETE, produces = APPLICATION_JSON_VALUE)
	public void deleteUploadedFile(@PathVariable("fileId") Long fileId) {
		archiveService.deleteById(fileId);

	}
}
