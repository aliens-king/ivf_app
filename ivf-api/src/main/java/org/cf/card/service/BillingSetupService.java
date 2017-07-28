package org.cf.card.service;

import java.util.List;

import org.cf.card.dto.ProcedureDto;
import org.cf.card.model.Procedure;

public interface BillingSetupService extends BaseService<Procedure>{
	
	public ProcedureDto save(ProcedureDto procedureDto);
	
	public List<ProcedureDto> findAllProcedure();

}
