package org.cf.card.service;

import java.util.List;

import org.cf.card.model.Investigation;

public interface InvestigationService extends BaseService<Investigation>{

	public List<Investigation> findByGroup(int groupKey);

}
