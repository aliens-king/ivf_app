/**
 *
 */
package org.cf.card.persistence;

import java.util.Collection;
import java.util.List;

import org.cf.card.model.EmbryoCode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nikhil Mahajan
 *
 */
@Repository
public interface EmbryoCodeDao extends GenericDao<EmbryoCode, Long> {

	List<EmbryoCode> findByCodeId(Long codeId);

	@Query("select count(e) from EmbryoCode e where e.code.id = :id")
	public Integer countEmbryoCodeByCodeId(@Param("id") long id);
	@Query("Select ec from EmbryoCode ec inner join ec.code c inner join c.client cl where cl.id =:clientId ")
	List<EmbryoCode> findEmbryoCodeByClientId(@Param("clientId") Long clientId);

	@Query("select ec from EmbryoCode ec inner join ec.dayProgressValues dpvs where ec.code.id = :codeId and dpvs.dayOptionId = :destiny and ec.dateOfUse is not null")
	List<EmbryoCode> findThawedEggAndEmbryo(@Param("codeId") Long codeId,@Param("destiny") int destiny);

	//finding all EmbryoCode by ID
	@Query("select ec from EmbryoCode ec where ec.id in (:aoEmbryoCodeId)")
	List<EmbryoCode> findAllEmbryoCodebyId(@Param("aoEmbryoCodeId")Collection<Long> aoEmbryoCodeId);

	/**
	 * Update embryo code value for remarks only.
	 *
	 * @param embryoCodeId the embryo code id
	 * @param remarks the remarks
	 */
	@Modifying
	@Transactional
	@Query("UPDATE EmbryoCode ec set remark =:remarks where id= :embryoCodeId")
	public void updateEmbryoCodeValueDto(@Param("embryoCodeId") Long embryoCodeId, @Param("remarks") String remarks);
}
