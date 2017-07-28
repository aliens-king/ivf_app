package org.cf.card.dto;

import java.util.Date;

public class ArchiveDto {

	private Long id;

	private Date uploadedDate;

	private String orignalFileName;

	private String uniqueFileName;

	private String extention;

	private Long coupleId;

	public Long getId() {
		return id;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public String getOrignalFileName() {
		return orignalFileName;
	}

	public String getUniqueFileName() {
		return uniqueFileName;
	}

	public String getExtention() {
		return extention;
	}

	public Long getCoupleId() {
		return coupleId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public void setOrignalFileName(String orignalFileName) {
		this.orignalFileName = orignalFileName;
	}

	public void setUniqueFileName(String uniqueFileName) {
		this.uniqueFileName = uniqueFileName;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public void setCoupleId(Long coupleId) {
		this.coupleId = coupleId;
	}

	@Override
	public String toString() {
		return "ArchiveDto [id=" + id + ", uploadedDate=" + uploadedDate + ", orignalFileName=" + orignalFileName
				+ ", uniqueFileName=" + uniqueFileName + ", extention=" + extention + ", coupleId=" + coupleId + "]";
	}

}
