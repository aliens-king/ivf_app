package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.ProcedureDto;
import org.cf.card.model.Procedure;
import org.cf.card.persistence.BillingSetupDao;
import org.cf.card.service.BillingSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("billingSetupService")
public class BillingSetupServiceImpl extends BaseServiceImpl<Procedure> implements BillingSetupService {

	private BillingSetupDao billingSetupDao;

	@Autowired
	public void setBillingSetupDao(BillingSetupDao billingSetupDao) {
		this.billingSetupDao = billingSetupDao;
		setGenericDao(billingSetupDao);
	}

	@Override
	public ProcedureDto save(ProcedureDto procedureDto) {
		Procedure procedure = getProcedureObj(procedureDto);
		procedure = billingSetupDao.save(procedure);
		return getProcedureDtoObj(procedure);
	}

	/**
	 * Gets the procedure obj.
	 *
	 * @param procedureDto
	 *            the procedure dto
	 * @return the procedure obj
	 */
	private Procedure getProcedureObj(ProcedureDto procedureDto) {
		Procedure procedure = null;
		if (null != procedureDto) {
			procedure = new Procedure();
			if (null != procedureDto.getId()) {
				procedure.setId(procedureDto.getId());
			}
			procedure.setProcedureCreateDate(procedureDto.getProcedureCreateDate());
			procedure.setProcedureName(procedureDto.getProcedureName());
			procedure.setProcedurePrice(procedureDto.getProcedurePrice());
			return procedure;
		}
		return null;

	}

	/**
	 * Gets the procedure dto obj.
	 *
	 * @param procedure
	 *            the procedure
	 * @return the procedure dto obj
	 */
	private ProcedureDto getProcedureDtoObj(Procedure procedure) {
		ProcedureDto procedureDto = null;
		if (null != procedure) {
			procedureDto = new ProcedureDto();
			procedureDto.setId(procedure.getId());
			procedureDto.setProcedureCreateDate(procedure.getProcedureCreateDate());
			procedureDto.setProcedureName(procedure.getProcedureName());
			procedureDto.setProcedurePrice(procedure.getProcedurePrice());
			return procedureDto;
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cf.card.service.BillingSetupService#findAllProcedure(java.util.List)
	 */
	@Override
	public List<ProcedureDto> findAllProcedure() {
		List<Procedure> list = billingSetupDao.findAll();
		List<ProcedureDto> procedureDtos = new ArrayList<>();
		list.forEach(new Consumer<Procedure>() {
			@Override
			public void accept(Procedure t) {
				procedureDtos.add(getProcedureDtoObj(t));
			}
		});
		return procedureDtos;
	}

}
