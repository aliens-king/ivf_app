package org.cf.card.service;

import org.cf.card.dto.RemarksDto;
import org.cf.card.model.Remark;

public interface RemarkService extends BaseService<Remark>{
	
	
	/**
	 * saving Remarks text to database with RemarksDto object
	 * @param remarksDto the remarks dto
	 * @return the remark
	 */
	public RemarksDto saveRemark(RemarksDto remarksDto);
	
	
	/**
	 * getting the remarksObject by codeID and remarksType from Database
	 * @param codeId the code id
	 * @param type the type
	 * @return the remarks by code id
	 */
	public RemarksDto getRemarksByCodeId(Long codeId,int type);
	
	
	/**
	 * getting the remarksObject by codeID and remarksType from Database
	 * @param codeId the code id
	 * @param type the type
	 * @return the remark
	 */
	public Remark remarksByCodeId(Long codeId,int type);
	
}
