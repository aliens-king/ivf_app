package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.Investigation;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestigationDao extends GenericDao<Investigation, Long>{

	public List<Investigation> findByGroup(int groupKey);

}
