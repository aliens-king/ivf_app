/**
 *
 */
package org.cf.card.persistence;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.cf.card.dto.CycleDto;
import org.cf.card.model.Embryo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Nikhil Mahajan
 *
 */
@Repository
public interface EmbryoDao extends GenericDao<Embryo, Long> {

	public List<Embryo> findByEmbryoCodesCodeId(Long treatmentId);

	
	@Query("SELECT NEW org.cf.card.dto.CycleDto(c.id as womanCodeId, c.code as womanCode, co.id as coupleId, "
			+ "t.eggs as eggCollection, t.startDate as startDate, t.endDate as endDate, "
			+ "po.biochemicalOption as biochemicalOption, "
			+ " COALESCE(( SELECT count(ec1.id) as evolution from EmbryoCode ec1, DayProgressValue dpvs  WHERE dpvs.embryoCode.id = ec1.id and dpvs.dayOptionId IN (36,37,38,39,40) and dpvs.dayIndex = 0 and ec1.code.id = c.id group by ec1.code.id ), 0) as evolution"
			+ " ,r.remark as remark, r.id as remarkId "
			+ ")  from Codes c "
			+ "LEFT JOIN c.pregnancyOutcomes po "
			+ "LEFT JOIN c.remarks r with r.type = 30"
		//	+ "LEFT JOIN c.embryoCode ec "
			+ "INNER JOIN c.treatment t "
			+ "INNER JOIN c.client cl LEFT JOIN cl.couple co "
			+ "WHERE c.client.id = :womanClientId group by c.id, co.id, t.id, po.id, r.id")
	public Set<CycleDto> findWomanCycles(@Param("womanClientId") Long womanClientId);

	@Query("SELECT count(ec1.id) from EmbryoCode ec1,DayProgressValue dpvs  WHERE dpvs.embryoCode.id = ec1.id and dpvs.dayOptionId IN (36,37,38,39,40) and dpvs.dayIndex = 0 and ec1.code.client.id = 4 group by ec1.code.id ")
	public List<Long> subQueryTest();



	@Query("SELECT new org.cf.card.dto.CycleDto(c.id as partnerCodeId, c.code as partnerCode, c.client.couple.id as coupleId, c.treatment.startDate as startDate, "
			+ "sd.density as density, sd.morphology as morphology, sd.motilityA, sd.motilityB, sd.motilityC, sd.motilityD) from Codes c "
			+ "LEFT JOIN c.semenCode sc "
			+ "INNER JOIN sc.semen s WITH s.screen = 17 "
  			+ "INNER JOIN s.semenDatas sd WITH sd.type=1 "
  			+ "WHERE c.client.id = :manId group by c.id, sd.id"	)
	public Set<CycleDto> findPartnerCycles(@Param("manId") Long manId);
	

	@Query("SELECT c.id FROM Codes c "
			+ "INNER JOIN c.treatment t "
			+ "INNER JOIN c.client cl "
			+ "WHERE cl.couple.id = :coupleId and t.startDate = :startDate")
	public List<Long> findCodeIdByStartDateAndCoupleId(@Param("coupleId") Long coupleId, @Param("startDate") Date startDate);


}
