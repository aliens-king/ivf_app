package org.cf.card.persistence;

import java.util.List;

import org.cf.card.dto.ProcedureDto;
import org.cf.card.model.RefundInvoiceProcedure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("refundInvoiceProcedureDao")
public interface RefundInvoiceProcedureDao extends GenericDao<RefundInvoiceProcedure, Long> {
	
	@Query("SELECT NEW org.cf.card.dto.ProcedureDto(p.id,p.procedureName,p.procedurePrice,p.procedureCreateDate) FROM RefundInvoiceProcedure AS rip INNER JOIN rip.refundInvoice AS ri"
			+ " INNER JOIN rip.procedure AS p"
			+ " WHERE ri.id=:id ")
	public List<ProcedureDto> getProceduresRelatedToRefundInvoice(@Param("id") Long id);

}
