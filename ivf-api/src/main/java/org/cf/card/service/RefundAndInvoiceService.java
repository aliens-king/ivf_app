package org.cf.card.service;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.RefundInvoice;
import org.springframework.data.repository.query.Param;

public interface RefundAndInvoiceService extends BaseService<RefundInvoice> {

	public RefundInvoiceDto saveRefundAndInvoiceProcedure(RefundInvoiceDto refundInvoiceDto);

	public RefundInvoiceDto getLatestRefundAndInvoicesByCoupleId(Long coupleId);

	public RefundInvoiceDto findRefundAndInvoiceByInvoiceNumber(Long refundInvoiceNumber);

	public List<RefundInvoiceDto> findAllUnpaidRefundPerCoupleByCoupleId(@Param("coupleId") Long coupleId);

	public List<RefundInvoice> findOverallRefundBillOfInvoices();

	public List<RefundInvoiceDto> findRefundInvoiceBetweenDates(Date fromDate, Date toDate);

	public void updateRefundInvoicRemarks(RefundInvoiceDto refundInvoiceDto);

}
