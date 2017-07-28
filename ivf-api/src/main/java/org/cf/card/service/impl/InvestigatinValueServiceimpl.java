package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.model.InvestigatinValue;
import org.cf.card.persistence.InvestigatinValueDao;
import org.cf.card.service.InvestigatinValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("investigatinValueService")
public class InvestigatinValueServiceimpl extends BaseServiceImpl<InvestigatinValue> implements InvestigatinValueService{

	private InvestigatinValueDao investigatinValueDao;

	@Autowired
	public void setInvestigatinValueDao(InvestigatinValueDao investigatinValueDao) {
		this.investigatinValueDao = investigatinValueDao;
		setGenericDao(investigatinValueDao);
	}

	/* (non-Javadoc)
	 * @see org.cf.card.service.InvestigatinValueService#deleteByPatientInvestigationId(java.lang.Long)
	 */
	@Override
	public void deleteByPatientInvestigationId(Long id){
		investigatinValueDao.deleteByPatientInvestigationId(id);
	}
}
