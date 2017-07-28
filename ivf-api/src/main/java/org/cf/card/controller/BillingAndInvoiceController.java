package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.model.BillingInvoice;
import org.cf.card.service.BillingAndInvoiceService;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pramod Maurya
 *
 *
 *
 * @since : Dec 30, 2016
 */
@RestController
@RequestMapping(value = "/billingAndInvoice")
public class BillingAndInvoiceController {

	@Autowired
	private BillingAndInvoiceService billingAndInvoiceService;

	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public BillingInvoiceDto saveInvoice(@RequestBody BillingInvoiceDto billingInvoice) {
		return billingAndInvoiceService.saveBillingInvoice(billingInvoice);
	}

	@RequestMapping(value = "/updateBillingInvoicRemarks", method = POST, consumes = APPLICATION_JSON_VALUE)
	public void updateBillingInvoicRemarks(@RequestBody BillingInvoiceDto billingInvoiceDto) {
		billingAndInvoiceService.updateBillingInvoicRemarks(billingInvoiceDto);
	}

	@RequestMapping(value = "/getLatestBillingInvoiceByCoupleId/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public BillingInvoiceDto getLatestBillingInvoiceByCoupleId(@PathVariable(value = "coupleId") Long coupleId) {
		return billingAndInvoiceService.getLatestBillingInvoiceByCoupleId(coupleId);
	}

	@RequestMapping(value = "/getAllBillingAndInvoiesWithCoupleId", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<BillingInvoiceDto> getAllBillingAndInvoiesWithCoupleId(
			@RequestParam(value = "coupleId") Long coupleId) {
		return billingAndInvoiceService.getAllBillingAndInvoiesWithCoupleId(coupleId);
	}

	@RequestMapping(value = "/findBillingAndInvoiceByInvoiceNumber/{invoiceNumber}", method = GET, produces = APPLICATION_JSON_VALUE)
	public BillingInvoiceDto findBillingAndInvoiceByInvoiceNumber(
			@PathVariable(value = "invoiceNumber") Long invoiceNumber) {
		return billingAndInvoiceService.findBillingAndInvoiceByInvoiceNumber(invoiceNumber);
	}

	@RequestMapping(value = "/findBillingAndInvoiceById/{billingInvoiceId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public BillingInvoiceDto findBillingAndInvoiceById(
			@PathVariable(value = "billingInvoiceId") Long billingInvoiceId) {
		return billingAndInvoiceService.findBillingAndInvoiceByInvoiceNumber(billingInvoiceId);
	}

	@RequestMapping(value = "/findAllUnpaidBillPerCoupleByCoupleId/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<BillingInvoiceDto> findAllUnpaidBillPerCoupleByCoupleId(
			@PathVariable(value = "coupleId") Long coupleId) {
		return billingAndInvoiceService.findAllUnpaidBillPerCoupleByCoupleId(coupleId);
	}

	@RequestMapping(value = "/findOverallUnpaidBillOfInvoices", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<BillingInvoice> findOverallUnpaidBillOfInvoices() {
		return billingAndInvoiceService.findOverallUnpaidBillOfInvoices();
	}

	@RequestMapping(value = "/list", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<BillingInvoiceDto> listByDateRange(@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate) {
		List<BillingInvoiceDto> listOfBillingInvoices = billingAndInvoiceService.findAllUnpaidBillBetweenDates(
				Util.stringToDate(fromDate, IConstants.DATE_FORMAT), Util.stringToDate(toDate, IConstants.DATE_FORMAT));
		return listOfBillingInvoices;
	}

}
