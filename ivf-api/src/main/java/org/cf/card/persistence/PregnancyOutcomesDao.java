/**
 * 
 */
package org.cf.card.persistence;

import org.cf.card.model.PregnancyOutcomes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author insonix
 *
 */
@Repository
public interface PregnancyOutcomesDao extends GenericDao<PregnancyOutcomes, Long> {

	@Query("FROM PregnancyOutcomes pr WHERE pr.code.id = :womanCodeId")
	public PregnancyOutcomes findPregnancyOutcomesByWomanCodeId(@Param("womanCodeId") Long womanCodeId);

}
