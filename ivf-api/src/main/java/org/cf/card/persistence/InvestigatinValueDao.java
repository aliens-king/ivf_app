package org.cf.card.persistence;

import org.cf.card.model.InvestigatinValue;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestigatinValueDao extends GenericDao<InvestigatinValue, Long>{


	/**
	 * Delete by patient investigation id.
	 *
	 * @param id the patientInvestigationId
	 */
	@Modifying
	@Query("delete from InvestigatinValue iv where iv.patientInvestigation.id=:id")
	void deleteByPatientInvestigationId(@Param("id")Long id);
}
