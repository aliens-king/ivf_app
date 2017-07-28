package org.cf.card.persistence;

import java.util.List;

import org.cf.card.dto.ProcedureDto;
import org.cf.card.model.InvoiceProcedure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The Interface InvoiceProcedureDto.
 *
 * @author Ankit Sood Jan 3, 2017
 */
@Repository("invoiceProcedureDao")
public interface InvoiceProcedureDao extends GenericDao<InvoiceProcedure, Long> {

	
	@Query("SELECT NEW org.cf.card.dto.ProcedureDto(p.id,p.procedureName,p.procedurePrice,p.procedureCreateDate) FROM InvoiceProcedure AS ip INNER JOIN ip.billingInvoice AS bi"
			+ " INNER JOIN ip.procedure AS p"
			+ " WHERE bi.id=:id ")
	public List<ProcedureDto> getProceduresRelatedToBillingInvoice(@Param("id") Long id);
}
