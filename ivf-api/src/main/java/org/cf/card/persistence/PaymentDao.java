package org.cf.card.persistence;

import java.util.List;

import org.cf.card.dto.PaymentDto;
import org.cf.card.model.Payment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 6, 2017
 */
@Repository("paymentDao")
public interface PaymentDao extends GenericDao<Payment, Long> {

	/*
	@Query("SELECT NEW org.cf.card.dto.BillingInvoiceDto"
			+ "(bi.id,bi.invoiceNumber,bi.totalBill,bi.totalPaid,bi.subTotal,bi.totalBalance,bi.invoiceCreateDate,bi.createdBy,bi.couple.id)"
			+ "FROM BillingInvoice AS bi INNER JOIN bi.couple c "
			+ "WHERE c.id=:coupleId ")*/
	@Query("SELECT NEW org.cf.card.dto.PaymentDto "
			+ "(p.id, p.totalBill, p.paidBill, p.balance, p.paymentMode, p.paymentBilledBy, p.paymentDate, p.remarks, p.billingInvoice.id, p.couple.id) "
			+ "FROM Payment AS p " 
			+ "INNER JOIN p.billingInvoice bi "
			+ "INNER JOIN p.couple c "
			+ "WHERE bi.invoiceNumber=:invoiceNumber")
	public List<PaymentDto> findAllPaymentsWithInvoiceId(@Param("invoiceNumber") Long invoiceNumber);
	
	@Modifying
	@Transactional
	@Query("UPDATE Payment p set remarks =:remarks where id= :paymentId")
	public void updatePaymentRemarks(@Param("paymentId") Long paymentId,
			@Param("remarks") String remarks);

}
