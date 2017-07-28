package org.cf.card.ui.service;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.model.BillingInvoice;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class UIBillingAndInvoiceService extends UIBaseService {

	private static final String BILLING_INVOICE_URL = BASE_URL + "/billingAndInvoice";

	/**
	 * Save procedures.
	 *
	 * @param procedureDto
	 *            the procedure dto
	 * @return the procedure dto
	 */
	public BillingInvoiceDto saveBillingInvoiceWithProcedures(BillingInvoiceDto billingInvoice) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BillingInvoiceDto> entity = new HttpEntity<BillingInvoiceDto>(billingInvoice, headers);
		ResponseEntity<BillingInvoiceDto> exchange = restTemplate.exchange(BILLING_INVOICE_URL + "/save",
				HttpMethod.POST, entity, new ParameterizedTypeReference<BillingInvoiceDto>() {
				});
		return exchange.getBody();
	}

	public void updateBillingInvoicRemarks(BillingInvoiceDto billingInvoiceDto) {
		String url = BILLING_INVOICE_URL + "/updateBillingInvoicRemarks";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BillingInvoiceDto> entity = new HttpEntity<>(billingInvoiceDto, headers);
		restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

	}

	public BillingInvoiceDto getLatestBillingInvoiceObjectByCoupleId(Long coupleId) {

		String url = BILLING_INVOICE_URL + "/getLatestBillingInvoiceByCoupleId/{coupleId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> entity = new HttpEntity<Long>(coupleId, httpHeaders);
		ResponseEntity<BillingInvoiceDto> exchange = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<BillingInvoiceDto>() {
				}, coupleId);
		return exchange.getBody();

	}

	/**
	 * Gets the all billing and invoies with couple id.
	 *
	 * @param coupleId
	 *            the couple id
	 * @return the all billing and invoies with couple id
	 */
	public List<BillingInvoiceDto> getAllBillingAndInvoiesWithCoupleId(Long coupleId) {
		String url = BILLING_INVOICE_URL + "/getAllBillingAndInvoiesWithCoupleId?coupleId={coupleId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<BillingInvoiceDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BillingInvoiceDto>>() {
				}, coupleId);

		return exchange.getBody();

	}

	/**
	 * Find billing and invoice by invoice number.
	 *
	 * @param invoiceNumber
	 *            the invoice number
	 * @return the billing invoice dto
	 */
	public BillingInvoiceDto findBillingAndInvoiceByInvoiceNumber(Long invoiceNumber) {

		String url = BILLING_INVOICE_URL + "/findBillingAndInvoiceByInvoiceNumber/{invoiceNumber}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> entity = new HttpEntity<Long>(invoiceNumber, httpHeaders);
		ResponseEntity<BillingInvoiceDto> exchange = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<BillingInvoiceDto>() {
				}, invoiceNumber);
		return exchange.getBody();

	}

	/**
	 * Find billing and invoice by id.
	 *
	 * @param billingInvoiceId
	 *            the billing invoice id
	 * @return the billing invoice dto
	 */
	public BillingInvoiceDto findBillingAndInvoiceById(Long billingInvoiceId) {

		String url = BILLING_INVOICE_URL + "/findBillingAndInvoiceById/{billingInvoiceId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> entity = new HttpEntity<Long>(billingInvoiceId, httpHeaders);
		ResponseEntity<BillingInvoiceDto> exchange = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<BillingInvoiceDto>() {
				}, billingInvoiceId);
		return exchange.getBody();

	}

	/**
	 * Find all unpaid bill per couple by couple id.
	 *
	 * @param coupleId
	 *            the couple id
	 * @return the list
	 */
	public List<BillingInvoiceDto> findAllUnpaidBillPerCoupleByCoupleId(Long coupleId) {

		String url = BILLING_INVOICE_URL + "/findAllUnpaidBillPerCoupleByCoupleId/{coupleId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<BillingInvoiceDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BillingInvoiceDto>>() {
				}, coupleId);
		return exchange.getBody();
	}

	/*
	 * public List<BillingInvoiceDto> findOverallUnpaidBillOfInvoices() {
	 * 
	 * String url = BILLING_INVOICE_URL + "/findOverallUnpaidBillOfInvoices";
	 * HttpHeaders httpHeaders = new HttpHeaders();
	 * httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	 * ResponseEntity<List<BillingInvoiceDto>> exchange =
	 * restTemplate.exchange(url, HttpMethod.GET, null, new
	 * ParameterizedTypeReference<List<BillingInvoiceDto>>() { }); return
	 * exchange.getBody(); }
	 */
	public List<BillingInvoice> findOverallUnpaidBillOfInvoices() {

		String url = BILLING_INVOICE_URL + "/findOverallUnpaidBillOfInvoices";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<BillingInvoice>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BillingInvoice>>() {
				});
		return exchange.getBody();
	}

	public List<BillingInvoiceDto> findAllUnpaidBillBetweenDates(Date fromDate, Date toDate) {

		String url = BILLING_INVOICE_URL + "/list?fromDate={fromDate}&toDate={toDate}";
		ResponseEntity<List<BillingInvoiceDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BillingInvoiceDto>>() {
				}, Util.formatDate(IConstants.DATE_FORMAT, fromDate), Util.formatDate(IConstants.DATE_FORMAT, toDate));
		return exchange.getBody();

	}

}
