package org.cf.card.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.ProcedureDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.Couple;
import org.cf.card.model.Procedure;
import org.cf.card.model.RefundInvoice;
import org.cf.card.model.RefundInvoiceProcedure;
import org.cf.card.persistence.RefundAndInvoiceDao;
import org.cf.card.persistence.RefundInvoiceProcedureDao;
import org.cf.card.service.CoupleService;
import org.cf.card.service.RefundAndInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("refundAndInvoiceService")
public class RefundAndInvoiceServiceImpl extends BaseServiceImpl<RefundInvoice> implements RefundAndInvoiceService {

	@Autowired
	private RefundAndInvoiceDao refundAndInvoiceDao;

	@Autowired
	private RefundInvoiceProcedureDao refundInvoiceProcedureDao;

	@Autowired
	private CoupleService coupleService;

	@Override
	public RefundInvoiceDto saveRefundAndInvoiceProcedure(RefundInvoiceDto refundInvoiceDto) {

		List<ProcedureDto> listOfProcedureDto = null;
		RefundInvoice refundInvoice = getRefundInvoiceObj(refundInvoiceDto);
		RefundInvoice invoice = refundAndInvoiceDao.save(refundInvoice);
		if (refundInvoiceDto.getProcedureDto().size() > 0) {

			listOfProcedureDto = refundInvoiceDto.getProcedureDto();
			listOfProcedureDto.forEach(new Consumer<ProcedureDto>() {
				@Override
				public void accept(ProcedureDto procedureDto) {
					Procedure procedure = getProcedureObj(procedureDto);
					RefundInvoiceProcedure invoiceProcedure = new RefundInvoiceProcedure();
					invoiceProcedure.setRefundInvoice(new RefundInvoice(invoice.getId()));
					invoiceProcedure.setProcedure(new Procedure(procedure.getId()));
					refundInvoiceProcedureDao.save(invoiceProcedure);
				}
			});
		}
		return getRefundInvoiceDtoObj(invoice);
	}

	/**
	 * Gets the refund invoice obj.
	 *
	 * @param refundInvoiceDto
	 *            the refund invoice dto
	 * @return the refund invoice obj
	 */
	private RefundInvoice getRefundInvoiceObj(RefundInvoiceDto refundInvoiceDto) {
		RefundInvoice refundInvoice = null;
		if (null != refundInvoiceDto) {
			refundInvoice = new RefundInvoice();
			if (null != refundInvoiceDto.getCoupleId()) {
				Couple couple = coupleService.findOne(refundInvoiceDto.getCoupleId());
				if (null != couple)
					refundInvoice.setCouple(couple);
			}
			refundInvoice.setCreatedBy(refundInvoiceDto.getCreatedBy());
			if (null != refundInvoiceDto.getId()) {
				refundInvoice.setId(refundInvoiceDto.getId());
			}
			refundInvoice.setRefundCreateDate(refundInvoiceDto.getRefundCreateDate());
			refundInvoice.setRefundInvoiceNumber(refundInvoiceDto.getRefundInvoiceNumber());
			refundInvoice.setSubTotal(refundInvoiceDto.getSubTotal());
			if (null == refundInvoiceDto.getTotalRefundBill()) {
				refundInvoiceDto.setTotalRefundBill(new Float(0.00));
			}
			refundInvoice.setTotalRefundBill(refundInvoiceDto.getTotalRefundBill());
			refundInvoice.setSubTotal(refundInvoiceDto.getSubTotal());
			refundInvoice.setVat(refundInvoiceDto.getVat());
		}
		return refundInvoice;
	}

	/**
	 * Gets the refund invoice dto obj.
	 *
	 * @param refundInvoice
	 *            the refund invoice
	 * @return the refund invoice dto obj
	 */
	private RefundInvoiceDto getRefundInvoiceDtoObj(RefundInvoice refundInvoice) {
		RefundInvoiceDto refundInvoiceDto = null;
		if (null != refundInvoice) {
			refundInvoiceDto = new RefundInvoiceDto();
			refundInvoiceDto.setId(refundInvoice.getId());
			refundInvoiceDto.setCreatedBy(refundInvoice.getCreatedBy());
			refundInvoiceDto.setId(refundInvoice.getId());
			refundInvoiceDto.setRefundCreateDate(refundInvoice.getRefundCreateDate());
			refundInvoiceDto.setRefundInvoiceNumber(refundInvoice.getRefundInvoiceNumber());
			refundInvoiceDto.setSubTotal(refundInvoice.getSubTotal());
			refundInvoiceDto.setTotalRefundBill(refundInvoice.getTotalRefundBill());
			refundInvoiceDto.setVat(refundInvoice.getVat());
			refundInvoiceDto.setRemarks(refundInvoice.getRemarks());
			refundInvoiceDto.setCoupleId(refundInvoice.getCouple().getId());
		}
		return refundInvoiceDto;

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

	@Override
	public RefundInvoiceDto getLatestRefundAndInvoicesByCoupleId(Long coupleId) {

		RefundInvoice refundInvoice = refundAndInvoiceDao.getLatestRefundInvoiceByCoupleId(coupleId);
		List<ProcedureDto> procedureDto = null;
		RefundInvoiceDto refundInvoiceDto = null;
		if (null != refundInvoice) {
			procedureDto = getProceduresRelatedToRefundInvoice(refundInvoice.getId());
			refundInvoiceDto = getRefundInvoiceDtoObj(refundInvoice);
			refundInvoiceDto.setProcedureDto(procedureDto);
		}
		return refundInvoiceDto;
	}

	@Override
	public List<RefundInvoiceDto> findAllUnpaidRefundPerCoupleByCoupleId(Long coupleId) {
		return refundAndInvoiceDao.findAllRefundByCoupleId(coupleId);
	}

	public List<ProcedureDto> getProceduresRelatedToRefundInvoice(Long invoiceId) {
		return refundInvoiceProcedureDao.getProceduresRelatedToRefundInvoice(invoiceId);
	}

	@Override
	public RefundInvoiceDto findRefundAndInvoiceByInvoiceNumber(Long refundInvoiceNumber) {
		RefundInvoice refundInvoice = refundAndInvoiceDao.findByRefundInvoiceNumber(refundInvoiceNumber);
		RefundInvoiceDto refundInvoiceDto = null;
		List<ProcedureDto> procedureDto = null;
		if (refundInvoice != null) {
			procedureDto = getProceduresRelatedToRefundInvoice(refundInvoice.getId());
			refundInvoiceDto = getRefundInvoiceDtoObj(refundInvoice);
			refundInvoiceDto.setProcedureDto(procedureDto);
		}
		return refundInvoiceDto;
	}

	@Override
	public List<RefundInvoice> findOverallRefundBillOfInvoices() {
		return refundAndInvoiceDao.findAll();
	}

	@Override
	public void updateRefundInvoicRemarks(RefundInvoiceDto refundInvoiceDto) {
		if (null != refundInvoiceDto) {
			refundAndInvoiceDao.updateRefundInvoiceRemarks(refundInvoiceDto.getId(), refundInvoiceDto.getRemarks());
		}

	}

	@Override
	public List<RefundInvoiceDto> findRefundInvoiceBetweenDates(Date fromDate, Date toDate) {
		List<RefundInvoiceDto> listOfRefundInvoiceDtos = refundAndInvoiceDao.findAllRefundsBetweenDates(fromDate,
				toDate);
		return listOfRefundInvoiceDtos;
	}

}
