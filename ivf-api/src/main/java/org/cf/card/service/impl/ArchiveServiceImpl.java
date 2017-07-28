package org.cf.card.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.dto.ArchiveDto;
import org.cf.card.model.Archive;
import org.cf.card.model.Couple;
import org.cf.card.persistence.ArchiveDao;
import org.cf.card.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ankit
 *
 */
@Service("archiveService")
@Transactional
public class ArchiveServiceImpl extends BaseServiceImpl<Archive> implements ArchiveService {

	private ArchiveDao archiveDao;

	@Autowired
	public void setArchiveDao(ArchiveDao archiveDao) {
		this.archiveDao = archiveDao;
		setGenericDao(archiveDao);
	}

	public String getFileExtention(String orignalFileName) {

		int i = orignalFileName.lastIndexOf('.');
		String extension = "";
		if (i >= 0) {
			extension = orignalFileName.substring(i + 1);
		}
		return "." + extension;
	}

	@Override
	public void saveArchive(MultipartFile multiPart, Couple couple, String UfileName) {
		Archive archive = new Archive();
		archive.setCouple(couple);
		String fileName = multiPart.getOriginalFilename();
		archive.setOrignalFileName(multiPart.getOriginalFilename());
		String ext = getFileExtention(fileName);
		if (null != ext) {
			archive.setExtention(ext);
		}
		archive.setUniqueFileName(UfileName);
		archive.setUploadedDate(new Date());
		archiveDao.save(archive);
	}

	@Override
	public List<ArchiveDto> findByCoupleId(long coupleId) {
		List<ArchiveDto> listOfArchive = null;
		List<Archive> archives = archiveDao.findByCoupleId(coupleId);
		if (null != archives) {
			listOfArchive = new ArrayList<>();
			for (Archive archive : archives) {
				listOfArchive.add(createArchiveDto(archive));
			}
		}
		return listOfArchive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cf.card.service.ArchiveService#getArchiveFile(org.cf.card.dto.
	 * ArchiveDto, java.lang.String)
	 */
	@Override
	public byte[] getArchiveFile(ArchiveDto archive, String path) {
		if (null != archive) {
			if (null != archive.getCoupleId()) {
				path = path + archive.getCoupleId() + "/" + archive.getUniqueFileName();
			}
		}
		byte[] bFile = null;
		try {
			File file = new File(path);

			if (file.exists()) {

				FileInputStream fileInputStream = null;

				// File file = new File(MyDir + "D111306260426.TXT");
				bFile = new byte[(int) file.length()];
				try {
					// convert file into array of bytes
					fileInputStream = new FileInputStream(file);
					fileInputStream.read(bFile);
					fileInputStream.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

				return bFile;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}

	/*
	 * @Override public List<ArchiveDao> findByCoupleId(Couple couple) { // TODO
	 * Auto-generated method stub List<Archive> archives =
	 * archiveDao.findByCouple(couple); System.out.println(
	 * "<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  "+archives.size()); return
	 * null; }
	 */

	private ArchiveDto createArchiveDto(Archive archive) {
		ArchiveDto archiveDto = null;
		if (null != archive) {
			archiveDto = new ArchiveDto();
			archiveDto.setId(archive.getId());
			archiveDto.setOrignalFileName(archive.getOrignalFileName());
			archiveDto.setUploadedDate(archive.getUploadedDate());
			archiveDto.setExtention(archive.getExtention());
			archiveDto.setUniqueFileName(archive.getUniqueFileName());
			if (null != archive.getCouple()) {
				archiveDto.setCoupleId(archive.getCouple().getId());
			}
		}
		return archiveDto;
	}

	@Override
	public ArchiveDto findById(long id) {
		Archive archive = archiveDao.findOne(id);
		ArchiveDto archiveDto = createArchiveDto(archive);
		return archiveDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cf.card.service.ArchiveService#listOfFiles(java.lang.Long)
	 */
	@Override
	public void deleteUploadedFile(Long fileId) {
		archiveDao.delete(fileId);
	}

	/*
	 * public static byte[] FileToArrayOfBytes() { }
	 */

}
