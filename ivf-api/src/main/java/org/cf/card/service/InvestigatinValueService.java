package org.cf.card.service;

import org.cf.card.model.InvestigatinValue;

public interface InvestigatinValueService  extends BaseService<InvestigatinValue>{

	/**
	 * Delete by patient investigation id.
	 *
	 * @param id the patientInvestigationId
	 */
	void deleteByPatientInvestigationId(Long id);

}
