package org.cf.card.dto;

public class RemarksDto {

	private Long id;
	private int remarksType;
	private Long codeId;
	private String remarksText;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRemarksType() {
		return remarksType;
	}

	public void setRemarksType(int remarksType) {
		this.remarksType = remarksType;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getRemarksText() {
		return remarksText;
	}

	public void setRemarksText(String remarksText) {
		this.remarksText = remarksText;
	}

}
