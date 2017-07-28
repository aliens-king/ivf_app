package org.cf.card.service;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.model.BillingInvoice;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 30, 2016
 */
public interface BillingAndInvoiceService extends BaseService<BillingInvoice> {

	public BillingInvoiceDto saveBillingInvoice(BillingInvoiceDto billingInvoice);

	public BillingInvoiceDto getLatestBillingInvoiceByCoupleId(Long coupleId);

	public List<BillingInvoiceDto> getAllBillingAndInvoiesWithCoupleId(Long coupleId);

	public BillingInvoiceDto findBillingAndInvoiceByInvoiceNumber(Long invoiceNumber);

	public BillingInvoiceDto findBillingAndInvoiceById(Long billingInvoiceId);

	public List<BillingInvoiceDto> findAllUnpaidBillPerCoupleByCoupleId(Long coupleId);

	public List<BillingInvoice> findOverallUnpaidBillOfInvoices();

	public List<BillingInvoiceDto> findAllUnpaidBillBetweenDates(Date fromDate, Date toDate);

	public void updateBillingInvoicRemarks(BillingInvoiceDto billingInvoiceDto);

}
