package org.cf.card.service;

import java.util.List;

import org.cf.card.dto.PaymentDto;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 6, 2017
 */
public interface PaymentService {

	public PaymentDto saveInvoicePayment(PaymentDto paymentDto);

	public void updatePaymentRemarks(PaymentDto paymentDto);

	public List<PaymentDto> findAllPaymentWithInvoiceNumber(Long invoiceNumber);

}
