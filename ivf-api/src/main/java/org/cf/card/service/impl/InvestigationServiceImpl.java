package org.cf.card.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.model.Investigation;
import org.cf.card.persistence.InvestigationDao;
import org.cf.card.service.InvestigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Transactional
@Service("investigationService")
public class InvestigationServiceImpl extends BaseServiceImpl<Investigation> implements InvestigationService{

	private InvestigationDao investigationDao;


	@Autowired
	public void setInvestigationDao(InvestigationDao investigationDao) {
		this.investigationDao = investigationDao;
		setGenericDao(investigationDao);
	}
	@Override
	public List<Investigation> findByGroup(int groupKey) {
		return investigationDao.findByGroup(groupKey);
	}

//	@Override
//	public List<Investigation> findAll() {
//		return investigationDao.findAll();
//	}
}
