/**
 * 
 */
package org.cf.card.service;

import org.cf.card.dto.PregnancyOutcomesDto;
import org.cf.card.model.PregnancyOutcomes;

/**
 * @author insonix
 *
 */
public interface PregnancyOutcomesService extends BaseService<PregnancyOutcomes> {

	/**
	 * Save.
	 * 
	 * @param outcomesDto
	 *            the outcomes dto
	 * @return the pregnancy outcomes dto
	 */
	public PregnancyOutcomesDto save(PregnancyOutcomesDto outcomesDto);

	/**
	 * Gets the pregnancy outcome.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @return the pregnancy outcome
	 */
	public PregnancyOutcomesDto getPregnancyOutcome(Long womanCodeId);
}
