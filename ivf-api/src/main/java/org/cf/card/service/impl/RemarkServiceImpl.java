package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.dto.RemarksDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Remark;
import org.cf.card.persistence.RemarkDao;
import org.cf.card.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class RemarkServiceImpl.
 */
@Transactional
@Service("remarkService")
public class RemarkServiceImpl extends BaseServiceImpl<Remark> implements RemarkService {
	
	/** The remark dao. */
	protected RemarkDao remarkDao;

	/**
	 * Sets the remark dao.
	 *
	 * @param remarkDao the new remark dao
	 */
	@Autowired
	public final void setRemarkDao(RemarkDao remarkDao){
		this.remarkDao = remarkDao;
		setGenericDao(remarkDao);

	}


	/* (non-Javadoc)
	 * @see org.cf.card.service.RemarkService#saveRemark(org.cf.card.dto.RemarksDto)
	 */
	@Override
	public RemarksDto saveRemark(RemarksDto remarksDto) {
		Remark remark=createRemark(remarksDto);
		if(remarksDto.getId()!=null && remarksDto.getId() != 0){
			remark.setId(remarksDto.getId());
		}
		Remark remarkObjectFromDB=remarkDao.save(remark);
		RemarksDto dto=createRemarksDto(remarkObjectFromDB);
		return dto;
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cf.card.service.RemarkService#getRemarksByCodeId(java.lang.Long, int)
	 */
	@Override
	public RemarksDto getRemarksByCodeId(Long codeId,int remarksType) {
		RemarksDto remarksDto=null;
		Remark remark= remarksByCodeId(codeId,remarksType);
		if(remark!=null){
			remarksDto = createRemarksDto(remark);
		}
		return remarksDto;
		
	}
	

	/* (non-Javadoc)
	 * @see org.cf.card.service.RemarkService#remarksByCodeId(java.lang.Long, int)
	 */
	@Override
	public Remark remarksByCodeId(Long codeId,int remarksType) {
		return remarkDao.findRemarkByCodeId(codeId, remarksType);	
	}
	
	public RemarksDto createRemarksDto(Remark remark){
		RemarksDto remarksDto=new RemarksDto();
		if(remark!=null){
			remarksDto.setId(remark.getId());
			remarksDto.setRemarksText(remark.getRemark());
			remarksDto.setRemarksType(remark.getType());
			remarksDto.setCodeId(remark.getCode().getId());
		}
		return remarksDto;
	}
	
	public Remark createRemark(RemarksDto remarksDto){
		Remark remark=new Remark();
		remark.setRemark(remarksDto.getRemarksText());
		remark.setType(remarksDto.getRemarksType());
		remark.setCode(new Codes(remarksDto.getCodeId()));
		return remark;
	}
}
