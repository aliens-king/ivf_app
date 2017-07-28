package org.cf.card.service;

import java.util.List;

import org.cf.card.dto.ArchiveDto;
import org.cf.card.model.Archive;
import org.cf.card.model.Couple;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ankit
 *
 */
public interface ArchiveService extends BaseService<Archive>{
	
	public void saveArchive(MultipartFile multiPart, Couple couple, String fileName);
	
	public List<ArchiveDto> findByCoupleId(long coupleId);

	public byte[] getArchiveFile(ArchiveDto archive, String path);

	public ArchiveDto findById(long id);
	
	public void deleteUploadedFile(Long fileId);

}
