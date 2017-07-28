package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.dto.PaymentDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 6, 2017
 */
public class UIPaymentService extends UIBaseService {

	private static final String PAYMENT_URL = BASE_URL + "/payment";

	public PaymentDto saveInvoicePayment(PaymentDto paymentDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaymentDto> entity = new HttpEntity<PaymentDto>(paymentDto, headers);
		ResponseEntity<PaymentDto> exchange = restTemplate.exchange(PAYMENT_URL + "/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<PaymentDto>() {
				});
		return exchange.getBody();
	}

	/** We are updating remarks column of payment table.
	 * Update payment remarks.
	 *
	 * @param paymentDto the payment dto
	 */
	public void updatePaymentRemarks(PaymentDto paymentDto) {
		String url = PAYMENT_URL + "/updatePaymentRemarks";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaymentDto> entity = new HttpEntity<>(paymentDto, headers);
		restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

	}

	/**
	 * Find all payment with invoice number.
	 *
	 * @param invoiceNumber
	 *            the invoice number
	 * @return the list
	 */
	public List<PaymentDto> findAllPaymentWithInvoiceNumber(Long invoiceNumber) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaymentDto> entity = new HttpEntity<PaymentDto>(headers);
		ResponseEntity<List<PaymentDto>> exchange = restTemplate.exchange(
				PAYMENT_URL + "/findAllPaymentWithInvoiceNumber?invoiceNumber={invoiceNumber}", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<PaymentDto>>() {
				}, invoiceNumber);
		return exchange.getBody();
	}

}
