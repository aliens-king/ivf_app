package org.cf.card.ui.service;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.RefundInvoice;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 3, 2017
 */
public class UIRefundAndInvoiceService extends UIBaseService {

	private static final String REFUND_INVOICE_URL = BASE_URL + "/refundAndInvoice";

	public RefundInvoiceDto saveRefundInvoiceProcedures(RefundInvoiceDto refundInvoiceDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RefundInvoiceDto> entity = new HttpEntity<RefundInvoiceDto>(refundInvoiceDto, headers);
		ResponseEntity<RefundInvoiceDto> exchange = restTemplate.exchange(REFUND_INVOICE_URL + "/save", HttpMethod.POST,
				entity, new ParameterizedTypeReference<RefundInvoiceDto>() {
				});
		return exchange.getBody();
	}

	public void updateRefundInvoicRemarks(RefundInvoiceDto refundInvoiceDto) {
		String url = REFUND_INVOICE_URL + "/updateRefundInvoicRemarks";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RefundInvoiceDto> entity = new HttpEntity<>(refundInvoiceDto, headers);
		restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

	}

	public RefundInvoiceDto findRefundAndInvoiceByInvoiceNumber(Long invoiceNumber) {
		String url = REFUND_INVOICE_URL + "/findRefundAndInvoiceByInvoiceNumber/{invoiceNumber}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> entity = new HttpEntity<Long>(invoiceNumber, httpHeaders);
		ResponseEntity<RefundInvoiceDto> exchange = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<RefundInvoiceDto>() {
				}, invoiceNumber);
		return exchange.getBody();
	}

	public RefundInvoiceDto getLatestRefundAndInvoicesByCoupleId(Long coupleId) {
		String url = REFUND_INVOICE_URL + "/getLatestRefundAndInvoicesByCoupleId/{coupleId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<Long> entity = new HttpEntity<Long>(coupleId, httpHeaders);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<RefundInvoiceDto> exchange = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<RefundInvoiceDto>() {
				}, coupleId);
		return exchange.getBody();

	}

	public List<RefundInvoiceDto> findAllRefundByCoupleId(Long coupleId) {

		String url = REFUND_INVOICE_URL + "/findAllRefundByCoupleId/{coupleId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<RefundInvoiceDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RefundInvoiceDto>>() {
				}, coupleId);
		return exchange.getBody();
	}

	/**
	 * Find overall refund bill of invoices.
	 *
	 * @return the list
	 */
	public List<RefundInvoice> findOverallRefundBillOfInvoices() {
		String url = REFUND_INVOICE_URL + "/findOverallRefundBillOfInvoices";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<RefundInvoice>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RefundInvoice>>() {
				});
		return exchange.getBody();
	}

	/**
	 * Find refund invoice between dates.
	 *
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @return the list
	 */
	public List<RefundInvoiceDto> findRefundInvoiceBetweenDates(Date fromDate, Date toDate) {
		String url = REFUND_INVOICE_URL + "/refundList?fromDateOfRefund={fromDateOfRefund}&toDateOfRefund={toDateOfRefund}";
		ResponseEntity<List<RefundInvoiceDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RefundInvoiceDto>>() {
				}, Util.formatDate(IConstants.DATE_FORMAT, fromDate), Util.formatDate(IConstants.DATE_FORMAT, toDate));
		return exchange.getBody();

	}

}
