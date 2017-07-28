package org.cf.card.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.model.SemenData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemenDataDao extends GenericDao<SemenData, Long> {

	List<SemenData> findBySemenId(Long id);

	@Modifying
	@Transactional
	@Query("delete from SemenData sd where sd.semen.id = :semenId")
	void deleteSemenDataBySemenId(@Param("semenId") Long semenId);

	/*@Query("select sd from SemenData sd inner join sd.semen s inner join s.semenCodes sc inner join sc.code c where c.client.id =:clientId and sd._type=1 ")
	List<SemenData> findSemenDataByClientId(@Param("clientId") Long clientId);*/
}
