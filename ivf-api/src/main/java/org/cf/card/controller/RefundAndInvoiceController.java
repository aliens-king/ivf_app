package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.RefundInvoice;
import org.cf.card.service.RefundAndInvoiceService;
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
 * @since : Jan 3, 2017
 */
@RestController
@RequestMapping(value = "/refundAndInvoice")
public class RefundAndInvoiceController {

	@Autowired
	private RefundAndInvoiceService refundAndInvoiceService;

	@RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	private RefundInvoiceDto saveRefundAndInvoiceProcedure(@RequestBody RefundInvoiceDto refundAndInvoiceDto) {
		return refundAndInvoiceService.saveRefundAndInvoiceProcedure(refundAndInvoiceDto);

	}

	/**
	 * * Update refundInvoice for remarks only.
	 * 
	 * @param embryoCodeValueDto
	 *            the embryo code value dto
	 */
	@RequestMapping(value = "/updateRefundInvoicRemarks", method = POST, consumes = APPLICATION_JSON_VALUE)
	public void updateRefundInvoicRemarks(@RequestBody RefundInvoiceDto refundInvoiceDto) {
		refundAndInvoiceService.updateRefundInvoicRemarks(refundInvoiceDto);
	}

	@RequestMapping(value = "/getLatestRefundAndInvoicesByCoupleId/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public RefundInvoiceDto getLatestRefundAndInvoicesByCoupleId(@PathVariable(value = "coupleId") Long coupleId) {
		return refundAndInvoiceService.getLatestRefundAndInvoicesByCoupleId(coupleId);
	}

	@RequestMapping(value = "/findAllRefundByCoupleId/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<RefundInvoiceDto> findAllUnpaidRefundPerCoupleByCoupleId(
			@PathVariable(value = "coupleId") Long coupleId) {
		return refundAndInvoiceService.findAllUnpaidRefundPerCoupleByCoupleId(coupleId);
	}

	@RequestMapping(value = "/findRefundAndInvoiceByInvoiceNumber/{refundInvoiceNumber}", method = GET, produces = APPLICATION_JSON_VALUE)
	public RefundInvoiceDto findRefundAndInvoiceByInvoiceNumber(
			@PathVariable("refundInvoiceNumber") Long refundInvoiceNumber) {
		return refundAndInvoiceService.findRefundAndInvoiceByInvoiceNumber(refundInvoiceNumber);

	}

	@RequestMapping(value = "/findOverallRefundBillOfInvoices", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<RefundInvoice> findOverallRefundBillOfInvoices() {
		return refundAndInvoiceService.findOverallRefundBillOfInvoices();
	}

	@RequestMapping(value = "/refundList", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<RefundInvoiceDto> listByDateRange(@RequestParam(value = "fromDateOfRefund") String fromDateOfRefund,
			@RequestParam(value = "toDateOfRefund") String toDateOfRefund) {
		List<RefundInvoiceDto> listOfrefundInvoices = refundAndInvoiceService.findRefundInvoiceBetweenDates(
				Util.stringToDate(fromDateOfRefund, IConstants.DATE_FORMAT), Util.stringToDate(toDateOfRefund, IConstants.DATE_FORMAT));
		return listOfrefundInvoices;
	}

}
