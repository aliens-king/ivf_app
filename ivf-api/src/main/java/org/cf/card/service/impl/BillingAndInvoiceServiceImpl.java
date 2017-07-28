package org.cf.card.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.ProcedureDto;
import org.cf.card.model.BillingInvoice;
import org.cf.card.model.Couple;
import org.cf.card.model.InvoiceProcedure;
import org.cf.card.model.Procedure;
import org.cf.card.persistence.BillingAndInvoiceDao;
import org.cf.card.persistence.InvoiceProcedureDao;
import org.cf.card.service.BillingAndInvoiceService;
import org.cf.card.service.CoupleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 30, 2016
 */
@Service("billingAndInvoiceService")
@Transactional
public class BillingAndInvoiceServiceImpl extends BaseServiceImpl<BillingInvoice> implements BillingAndInvoiceService {

	private BillingAndInvoiceDao billingAndInvoiceDao;

	@Autowired
	private InvoiceProcedureDao invoiceProcedureDao;

	@Autowired
	private CoupleService coupleService;

	@Autowired
	public void setBillingAndInvoiceDao(BillingAndInvoiceDao billingAndInvoiceDao) {
		this.billingAndInvoiceDao = billingAndInvoiceDao;
		setGenericDao(billingAndInvoiceDao);
	}

	@Override
	public BillingInvoiceDto saveBillingInvoice(BillingInvoiceDto billingInvoiceDto) {

		List<ProcedureDto> listOfProcedureDto = null;
		BillingInvoice billingInvoice = getBillingInvoice(billingInvoiceDto);
		BillingInvoice invoice = billingAndInvoiceDao.save(billingInvoice);
		if (billingInvoiceDto.getProcedureDto().size() > 0) {

			listOfProcedureDto = billingInvoiceDto.getProcedureDto();
			listOfProcedureDto.forEach(new Consumer<ProcedureDto>() {
				@Override
				public void accept(ProcedureDto procedureDto) {
					Procedure procedure = getProcedureObj(procedureDto);
					InvoiceProcedure invoiceProcedure = new InvoiceProcedure();
					invoiceProcedure.setBillingInvoice(new BillingInvoice(invoice.getId()));
					invoiceProcedure.setProcedure(new Procedure(procedure.getId()));
					invoiceProcedureDao.save(invoiceProcedure);
				}
			});
		}
		return getBillingInvoiceDto(invoice);
	}

	private Procedure getProcedureObj(ProcedureDto procedureDto) {
		Procedure procedure = null;
		if (null != procedureDto) {
			procedure = new Procedure();
			if (null != procedureDto.getId())
				procedure.setId(procedureDto.getId());
			procedure.setProcedureCreateDate(procedureDto.getProcedureCreateDate());
			procedure.setProcedurePrice(procedureDto.getProcedurePrice());
			procedure.setProcedureName(procedureDto.getProcedureName());
		}
		return procedure;

	}

	/**
	 * Gets the billing invoice.
	 *
	 * @param billingInvoiceDto
	 *            the billing invoice dto
	 * @return the billing invoice
	 */
	public BillingInvoice getBillingInvoice(BillingInvoiceDto billingInvoiceDto) {
		BillingInvoice billingInvoice = null;
		if (null != billingInvoiceDto) {
			billingInvoice = new BillingInvoice();
			if (null != billingInvoiceDto.getCoupleId()) {
				Couple couple = coupleService.findOne(billingInvoiceDto.getCoupleId());
				if (null != couple)
					billingInvoice.setCouple(couple);
			}
			billingInvoice.setCreatedBy(billingInvoiceDto.getCreatedBy());
			if (null != billingInvoiceDto.getId()) {
				billingInvoice.setId(billingInvoiceDto.getId());
				// billingAndInvoiceDao.findOne(billingInvoiceDto.getId());
			}
			billingInvoice.setInvoiceCreateDate(billingInvoiceDto.getInvoiceCreateDate());
			billingInvoice.setInvoiceNumber(billingInvoiceDto.getInvoiceNumber());
			billingInvoice.setSubTotal(billingInvoiceDto.getSubTotal());
			if (null == billingInvoiceDto.getTotalBalance()) {
				billingInvoiceDto.setTotalBalance(new Float(0.00));
			}
			if (null == billingInvoiceDto.getTotalPaid()) {
				billingInvoiceDto.setTotalPaid(new Float(0.00));
			}
			billingInvoice.setTotalBalance(billingInvoiceDto.getTotalBalance());
			billingInvoice.setTotalBill(billingInvoiceDto.getTotalBill());
			billingInvoice.setTotalPaid(billingInvoiceDto.getTotalPaid());
			billingInvoice.setVat(billingInvoiceDto.getVat());
		}
		return billingInvoice;
	}

	/**
	 * Gets the billing invoice dto.
	 *
	 * @param billingInvoice
	 *            the billing invoice
	 * @return the billing invoice dto
	 */
	public BillingInvoiceDto getBillingInvoiceDto(BillingInvoice billingInvoice) {
		BillingInvoiceDto billingInvoiceDto = null;

		if (null != billingInvoice) {
			billingInvoiceDto = new BillingInvoiceDto();
			billingInvoiceDto.setId(billingInvoice.getId());
			billingInvoiceDto.setCoupleId(billingInvoice.getCouple().getId());
			billingInvoiceDto.setCreatedBy(billingInvoice.getCreatedBy());
			billingInvoiceDto.setInvoiceCreateDate(billingInvoice.getInvoiceCreateDate());
			billingInvoiceDto.setInvoiceNumber(billingInvoice.getInvoiceNumber());
			billingInvoiceDto.setSubTotal(billingInvoice.getSubTotal());
			billingInvoiceDto.setTotalBalance(billingInvoice.getTotalBalance());
			billingInvoiceDto.setTotalBill(billingInvoice.getTotalBill());
			billingInvoiceDto.setTotalPaid(billingInvoice.getTotalPaid());
			billingInvoiceDto.setVat(billingInvoice.getVat());
			billingInvoiceDto.setRemarks(billingInvoice.getRemarks());
		}
		return billingInvoiceDto;

	}

	@Override
	public BillingInvoiceDto getLatestBillingInvoiceByCoupleId(Long coupleId) {
		List<ProcedureDto> procedureDto = null;
		BillingInvoiceDto billingInvoiceDto = null;
		BillingInvoice billingInvoice = billingAndInvoiceDao.getLatestBillingInvoiceByCoupleId(coupleId);
		if (null != billingInvoice) {
			procedureDto = getProceduresRelatedToBillingInvoice(billingInvoice.getId());
			billingInvoiceDto = getBillingInvoiceDto(billingInvoice);
			billingInvoiceDto.setProcedureDto(procedureDto);
		}
		return billingInvoiceDto;
	}

	public List<ProcedureDto> getProceduresRelatedToBillingInvoice(Long invoiceId) {
		return invoiceProcedureDao.getProceduresRelatedToBillingInvoice(invoiceId);
	}

	@Override
	public List<BillingInvoiceDto> getAllBillingAndInvoiesWithCoupleId(Long coupleId) {
		return billingAndInvoiceDao.getAllBillingAndInvoiesWithCoupleId(coupleId);
	}

	@Override
	public BillingInvoiceDto findBillingAndInvoiceByInvoiceNumber(Long invoiceNumber) {
		BillingInvoice billingInvoice = billingAndInvoiceDao.findByInvoiceNumber(invoiceNumber);
		BillingInvoiceDto billingInvoiceDto = null;
		List<ProcedureDto> procedureDto = null;
		if (null != billingInvoice) {
			procedureDto = getProceduresRelatedToBillingInvoice(billingInvoice.getId());
			billingInvoiceDto = getBillingInvoiceDto(billingInvoice);
			billingInvoiceDto.setProcedureDto(procedureDto);
		}
		return billingInvoiceDto;
	}

	@Override
	public BillingInvoiceDto findBillingAndInvoiceById(Long billingInvoiceId) {
		BillingInvoice billingInvoice = billingAndInvoiceDao.findById(billingInvoiceId);
		BillingInvoiceDto billingInvoiceDto = null;
		List<ProcedureDto> procedureDto = null;
		if (null != billingInvoice) {
			procedureDto = getProceduresRelatedToBillingInvoice(billingInvoice.getId());
			billingInvoiceDto = getBillingInvoiceDto(billingInvoice);
			billingInvoiceDto.setProcedureDto(procedureDto);
		}
		return billingInvoiceDto;
	}

	@Override
	public List<BillingInvoiceDto> findAllUnpaidBillPerCoupleByCoupleId(Long coupleId) {
		return billingAndInvoiceDao.findAllUnpaidBillPerCoupleByCoupleId(coupleId);
	}

	@Override
	public List<BillingInvoice> findOverallUnpaidBillOfInvoices() {
		return billingAndInvoiceDao.findOverallUnpaidBillOfInvoices();
	}

	@Override
	public List<BillingInvoiceDto> findAllUnpaidBillBetweenDates(Date fromDate, Date toDate) {

		List<BillingInvoiceDto> listOfBillingInvoiceDtos = billingAndInvoiceDao.findAllUnpaidBillBetweenDates(fromDate,
				toDate);
		return listOfBillingInvoiceDtos;
	}

	@Override
	public void updateBillingInvoicRemarks(BillingInvoiceDto billingInvoiceDto) {
		if (null != billingInvoiceDto) {
			billingAndInvoiceDao.updateBillingInvoiceRemarks(billingInvoiceDto.getId(), billingInvoiceDto.getRemarks());
		}

	}

}
