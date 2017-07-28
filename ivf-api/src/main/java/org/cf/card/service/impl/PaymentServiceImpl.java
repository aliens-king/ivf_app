package org.cf.card.service.impl;

import java.util.List;

import org.cf.card.dto.PaymentDto;
import org.cf.card.model.BillingInvoice;
import org.cf.card.model.Couple;
import org.cf.card.model.Payment;
import org.cf.card.persistence.PaymentDao;
import org.cf.card.service.BillingAndInvoiceService;
import org.cf.card.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl extends BaseServiceImpl<Payment> implements PaymentService {

	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private BillingAndInvoiceService billingAndInvoiceService;

	@Override
	public PaymentDto saveInvoicePayment(PaymentDto paymentDto) {
		Payment payment = getPaymentObj(paymentDto);
		Payment paymentUpdatedObj = paymentDao.save(payment);
		BillingInvoice invoice = getBillinfInvoiceObj(paymentUpdatedObj);
		billingAndInvoiceService.save(invoice);
		return getPaymentDtoObj(paymentUpdatedObj);
	}

	@Override
	public void updatePaymentRemarks(PaymentDto paymentDto) {
		if (null != paymentDto) {
			paymentDao.updatePaymentRemarks(paymentDto.getId(), paymentDto.getRemarks());
		}
	}

	@Override
	public List<PaymentDto> findAllPaymentWithInvoiceNumber(Long invoiceNumber) {
		return paymentDao.findAllPaymentsWithInvoiceId(invoiceNumber);
	}

	private BillingInvoice getBillinfInvoiceObj(Payment payment) {
		BillingInvoice billingInvoice = null;
		if (null != payment) {
			Long billingInvoiceId = payment.getBillingInvoice().getId();
			if (null != billingInvoiceId) {
				billingInvoice = billingAndInvoiceService.findOne(billingInvoiceId);
				billingInvoice.setTotalBalance(payment.getBalance());
				Float totalPaid = billingInvoice.getTotalPaid() + payment.getPaidBill();
				billingInvoice.setTotalPaid(totalPaid);
			}
		}
		return billingInvoice;

	}

	/**
	 * Gets the payment obj.
	 *
	 * @param paymentDto
	 *            the payment dto
	 * @return the payment obj
	 */
	private Payment getPaymentObj(PaymentDto paymentDto) {
		Payment payment = null;
		if (null != paymentDto) {
			payment = new Payment();
			if (null != paymentDto.getId()) {
				payment.setId(paymentDto.getId());
			}
			payment.setId(paymentDto.getId());
			payment.setTotalBill(paymentDto.getTotalBill());
			payment.setPaidBill(paymentDto.getPaidBill());
			payment.setBalance(paymentDto.getBalance());
			payment.setPaymentDate(paymentDto.getPaymentDate());
			payment.setPaymentBilledBy(paymentDto.getPaymentBilledBy());
			payment.setPaymentMode(paymentDto.getPaymentMode());
			payment.setBillingInvoice(new BillingInvoice(paymentDto.getBillingInvoiceId()));
			payment.setCouple(new Couple(paymentDto.getCoupleId()));
		}
		return payment;

	}

	/**
	 * Gets the payment dto obj.
	 *
	 * @param payment
	 *            the payment
	 * @return the payment dto obj
	 */
	private PaymentDto getPaymentDtoObj(Payment payment) {
		PaymentDto paymentDto = null;
		if (null != payment) {
			paymentDto = new PaymentDto();
			paymentDto.setId(payment.getId());
			paymentDto.setTotalBill(payment.getTotalBill());
			paymentDto.setPaidBill(payment.getPaidBill());
			paymentDto.setBalance(payment.getBalance());
			paymentDto.setPaymentDate(payment.getPaymentDate());
			paymentDto.setPaymentBilledBy(payment.getPaymentBilledBy());
			paymentDto.setPaymentMode(payment.getPaymentMode());
			paymentDto.setBillingInvoiceId(payment.getBillingInvoice().getId());
			paymentDto.setCoupleId(payment.getCouple().getId());
		}
		return paymentDto;
	}

}
