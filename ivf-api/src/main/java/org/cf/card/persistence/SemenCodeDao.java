package org.cf.card.persistence;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.model.SemenCode;
import org.cf.card.vo.VoSemenCode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemenCodeDao extends GenericDao<SemenCode, Long> {

	List<SemenCode> findByCodeId(Long codeId);

/*	@Modifying
	@Query("delete from SemenCode sc where sc.id=:id")
	void deleteById(@Param("id") Long id);*/


	@Modifying
	@Transactional
	@Query("delete from SemenCode sc where sc.semen.id = :semenId")
	void deleteSemenCodeBySemenId(@Param("semenId") Long semenId);

	@Query("Select sc from SemenCode sc inner join sc.semen s inner join s.semenDatas sd inner join sc.code c where c.client.id =:clientId and sd.type=:type")
	List<SemenCode> findSemenCodeByClientId(@Param("clientId") Long clientId, @Param("type") int type);


	//@Query("Select sc.id as semenCodeId, sc.date_used as dateOfUse,sc._index as index, sc.code_id as codeId ,sd.morphology as morphology, sd.motilityA as motilityA, sd.motilityB as motilityB ,sd.motilityC as motilityC,sd.motilityD as motilityD,sd.density  as density ,s.id as semenId ,c.code as code from SemenCode sc inner join sc.semen s inner join s.semenDatas sd inner join sc.code c where c.client.id =19 and sd.type =1")
	@Query("Select new org.cf.card.vo.VoSemenCode(sc.id as semenCodeId, sc.dateUsed as dateUsed, sc.index as index, sc.code.id as codeId , s.id as semenId, c.code as code, s.createdDate as createdDate, s.semenCryoDate, sd.morphology as morphology, sd.motilityA as motilityA, sd.motilityB as motilityB, sd.motilityC as motilityC, sd.motilityD as motilityD, sd.density  as density, sc.cryoARemarks as remarks) from SemenCode sc inner join sc.semen s inner join s.semenDatas sd inner join sc.code c where c.client.id =:clientId and sd.type =:type and s.screen=:screen and s.cryoVisibility = 1")
	List<VoSemenCode> findSemenCodeAndSemenDataByClientId(@Param("clientId") Long clientId, @Param("type") int type, @Param("screen") int screen);

	@Query("Select sc from SemenCode sc inner join sc.semen s where sc.code.id = :manCodeId and s.screen = :screen ")
	List<SemenCode> findSemenCodeByCodeIdAndScreenId(@Param("manCodeId")Long manCodeId, @Param("screen") int screen);

	@Query("SELECT sc FROM SemenCode sc "
			+ "inner join sc.semen s "
			+ "inner join sc.code c "
			+ "WHERE c.client.id = :clientId and s.screen = :screen and s.createdDate = :date")
	List<SemenCode> findSemenCodeByClientIdScreenAndDate(@Param("clientId") Long clientId, @Param("screen") int screen, @Param("date") Date date);

	@Query("SELECT new org.cf.card.vo.VoSemenCode(sc.id, s.id, s.createdDate, s.semens, s.use, s.mediaAdded, s.debris, s.viscosity, s.agglutination, s.aggregation, s.cryoVisibility, s.timeProcessed, s.timeProduced, s.remark, "
			+ "sd.volume, sd.density, sd.motilityA, sd.motilityB, sd.motilityC, sd.motilityD, sd.morphology, sd.roundCell) "
			+ "FROM SemenCode sc inner join sc.semen s inner join s.semenDatas sd "
			+ "WHERE sc.code.client.id = :clientId and s.screen = :screen "
			+ "GROUP BY s.id, sc.id, sd.id ")
//			+ "order by s.createdDate desc")
	List<VoSemenCode> findSemenCodeByClientIdScreen(@Param("clientId") Long clientId, @Param("screen") int screen);

}
