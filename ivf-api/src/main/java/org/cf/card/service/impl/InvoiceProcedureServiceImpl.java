package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.model.InvoiceProcedure;
import org.cf.card.persistence.InvoiceProcedureDao;
import org.cf.card.service.InvoiceProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class InvoiceProcedureServiceImpl.
 *
 * @author Ankit Sood Jan 3, 2017
 */
@Transactional
@Service("invoiceProcedureService")
public class InvoiceProcedureServiceImpl extends BaseServiceImpl<InvoiceProcedure> implements InvoiceProcedureService {

	@Autowired
	private InvoiceProcedureDao invoiceProcedureDao;

}
