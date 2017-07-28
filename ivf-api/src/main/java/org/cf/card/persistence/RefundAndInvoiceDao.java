package org.cf.card.persistence;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.RefundInvoice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("refundAndInvoiceDao")
public interface RefundAndInvoiceDao extends GenericDao<RefundInvoice, Long> {

	@Query("select max(ri) from RefundInvoice " + "ri WHERE ri.couple.id=:coupleId")
	public RefundInvoice getLatestRefundInvoiceByCoupleId(@Param("coupleId") Long coupleId);

	public RefundInvoice findByRefundInvoiceNumber(@Param("refundInvoiceNumber") Long refundInvoiceNumber);

	@Query("SELECT NEW org.cf.card.dto.RefundInvoiceDto "
			+ "(ri.id, ri.refundInvoiceNumber,ri.totalRefundBill,ri.subTotal,ri.refundCreateDate,"
			+ "ri.createdBy,ri.vat, ri.remarks, ri.couple.id) " + "FROM RefundInvoice AS ri INNER JOIN ri.couple c "
			+ "WHERE c.id=:coupleId")
	// +"AND ri.totalRefundBill>0.0")
	public List<RefundInvoiceDto> findAllRefundByCoupleId(@Param("coupleId") Long coupleId);

	@Modifying
	@Transactional
	@Query("UPDATE RefundInvoice ri set remarks =:remarks where id= :refundInvoiceId")
	public void updateRefundInvoiceRemarks(@Param("refundInvoiceId") Long refundInvoiceId,
			@Param("remarks") String remarks);

	/**
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT NEW org.cf.card.dto.RefundInvoiceDto"
			+ "(ri.id, ri.refundInvoiceNumber, ri.totalRefundBill, ri.subTotal, ri.refundCreateDate, "
			+ "ri.createdBy, ri.vat, ri.remarks, ri.couple.id) "
			+ "FROM RefundInvoice AS ri "
			+ "WHERE ri.refundCreateDate BETWEEN :fromDate AND :toDate")
	public List<RefundInvoiceDto> findAllRefundsBetweenDates(@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

}
