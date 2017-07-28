package org.cf.card.persistence;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.model.BillingInvoice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pramod Maurya
 *
 * @since : Dec 30, 2016
 */
@Repository("billingAndInvoiceDao")
public interface BillingAndInvoiceDao extends GenericDao<BillingInvoice, Long> {

	/*
	 * @Query("select max(bi) from BillingInvoice AS bi " +
	 * "INNER JOIN bi.couple c" + "WHERE c.id=:coupleId")
	 */
	@Query("select max(bi) from BillingInvoice bi " + "WHERE bi.couple.id=:coupleId")
	public BillingInvoice getLatestBillingInvoiceByCoupleId(@Param("coupleId") Long coupleId);

	@Query("SELECT NEW org.cf.card.dto.BillingInvoiceDto"
			+ "(bi.id,bi.invoiceNumber,bi.totalBill,bi.totalPaid,bi.subTotal,bi.totalBalance,bi.invoiceCreateDate,bi.createdBy, bi.remarks, bi.couple.id)"
			+ "FROM BillingInvoice AS bi INNER JOIN bi.couple c " + "WHERE c.id=:coupleId ")
	public List<BillingInvoiceDto> getAllBillingAndInvoiesWithCoupleId(@Param("coupleId") Long coupleId);

	public BillingInvoice findByInvoiceNumber(@Param("invoiceNumber") Long invoiceNumber);

	public BillingInvoice findById(@Param("invoiceNumber") Long invoiceNumber);

	@Query("SELECT NEW org.cf.card.dto.BillingInvoiceDto"
			+ "(bi.id, bi.invoiceNumber, bi.totalBill, bi.totalPaid, bi.subTotal,"
			+ " bi.totalBalance, bi.invoiceCreateDate, bi.createdBy, bi.remarks, bi.couple.id)"
			+ "FROM BillingInvoice AS bi INNER JOIN bi.couple c " + "WHERE c.id=:coupleId " + "AND bi.totalBalance>0.0")
	public List<BillingInvoiceDto> findAllUnpaidBillPerCoupleByCoupleId(@Param("coupleId") Long coupleId);

	/*
	 * @Query("SELECT NEW org.cf.card.dto.BillingInvoiceDto " +
	 * "(bi.id, bi.invoiceNumber, bi.totalBill, bi.totalPaid, bi.subTotal, " +
	 * "bi.totalBalance, bi.invoiceCreateDate, bi.createdBy, bi.couple.id) " +
	 * "FROM BillingInvoice AS bi INNER JOIN bi.couple c " +
	 * "WHERE bi.totalBalance>0.0") public List<BillingInvoiceDto>
	 * findOverallUnpaidBillOfInvoices();
	 */

	@Query("Select bi FROM BillingInvoice bi where bi.totalBalance>0.0")
	public List<BillingInvoice> findOverallUnpaidBillOfInvoices();

	/**
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT NEW org.cf.card.dto.BillingInvoiceDto"
			+ "(bi.id, bi.invoiceNumber, bi.totalBill, bi.totalPaid, bi.subTotal,"
			+ " bi.totalBalance, bi.invoiceCreateDate, bi.createdBy, bi.remarks, bi.couple.id)"
			+ "FROM BillingInvoice AS bi "
			+ "WHERE bi.totalBalance>0.0 "
			+ "AND bi.invoiceCreateDate BETWEEN :fromDate AND :toDate")
	public List<BillingInvoiceDto> findAllUnpaidBillBetweenDates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	

	@Modifying
	@Transactional
	@Query("UPDATE BillingInvoice bi set remarks =:remarks where id= :billingInvoiceId")
	public void updateBillingInvoiceRemarks(@Param("billingInvoiceId") Long billingInvoiceId,
			@Param("remarks") String remarks);

}
