package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.PaymentDto;
import org.cf.card.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 6, 2017
 */
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public PaymentDto saveInvoicePayment(@RequestBody PaymentDto paymentDto) {
		return paymentService.saveInvoicePayment(paymentDto);
	}
	
	
	@RequestMapping(value = "/updatePaymentRemarks", method = POST, consumes = APPLICATION_JSON_VALUE)
	public void updateBillingInvoicRemarks(@RequestBody PaymentDto paymentDto) {
		paymentService.updatePaymentRemarks(paymentDto);
	}

	@RequestMapping(value = "/findAllPaymentWithInvoiceNumber", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<PaymentDto> findAllPaymentWithInvoiceNumber(@RequestParam(value = "invoiceNumber") Long invoiceNumber) {
		return paymentService.findAllPaymentWithInvoiceNumber(invoiceNumber);
	}

}
