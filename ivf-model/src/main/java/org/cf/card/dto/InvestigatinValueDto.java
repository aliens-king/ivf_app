package org.cf.card.dto;

import java.io.Serializable;

public class InvestigatinValueDto implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long investigationId;

	private Long investigationValueId;

	private String value;

	public Long getInvestigationId() {
		return investigationId;
	}

	public Long getInvestigationValueId() {
		return investigationValueId;
	}

	public void setInvestigationValueId(Long investigationValueId) {
		this.investigationValueId = investigationValueId;
	}

	public void setInvestigationId(Long investigationId) {
		this.investigationId = investigationId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvestigatinValueDto [investigationId=");
		builder.append(investigationId);
		builder.append(", investigationValueId=");
		builder.append(investigationValueId);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}



}
