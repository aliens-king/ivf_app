package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.NurseStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseStationDao extends GenericDao<NurseStation, Long>{
	
	@Query("FROM NurseStation ns WHERE ns.couple.id = :coupleId")
	public List<NurseStation> findNurseStationByCoupleId(@Param("coupleId") Long coupleId);
}
