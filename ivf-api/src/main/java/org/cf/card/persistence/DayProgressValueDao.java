package org.cf.card.persistence;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cf.card.model.DayProgressValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DayProgressValueDao extends GenericDao<DayProgressValue, Long> {

	List<DayProgressValue> findByEmbryoCodeIdAndDayIndex(Long embryoCodeid, int dayIndex);

	/**
	 * find day progress values by code id using inner join with embryo code
	 *
	 * @param womanCodeId
	 * @return
	 */
	@Query("Select dvp FROM DayProgressValue dvp inner join dvp.embryoCode ec  where ec.code.id=:womanCodeId and dvp.moduleId = :moduleId and ec.code.treatment.cycleType =:cycleType")
	List<DayProgressValue> findDayProgressValueByCodeIdAndModuleId(@Param("womanCodeId") Long womanCodeId,
			@Param("moduleId") int moduleId, @Param("cycleType") int cycleType);

	@Query("select dvp from DayProgressValue dvp inner join dvp.embryoCode ec inner join ec.code c inner join c.client cl where cl.id=:clientId and dvp.moduleId = :moduleId")
	List<DayProgressValue> findDayProgressValueByClientIdAndModuleId(@Param("clientId") Long clientId,
			@Param("moduleId") int moduleId);

	@Query("Select dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec  where ec.code.id=:womanCodeId and dpv.dayIndex = (select dpv1.dayIndex from DayProgressValue dpv1 where dpv1.dayOptionId = :destiny and dpv1.embryoCode.id = ec.id  )")
	List<DayProgressValue> findDayProgressValueByCodeIdAndDestiny(@Param("womanCodeId") Long womanCodeId,
			@Param("destiny") int destiny);

	@Query("Select dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec  where ec.code.client.id=:womanClientId and ec.code.treatment.cycleType in (:aocycleType) and dpv.moduleId = :module and dpv.dayIndex = (select dpv1.dayIndex from DayProgressValue dpv1 where dpv1.dayOptionId = :destiny and dpv1.embryoCode.id = ec.id and dpv1.moduleId = :module)")
	List<DayProgressValue> findDayProgressValueByClientIdAndDestinyAndModule(@Param("womanClientId") Long womanClientId,
			@Param("destiny") int destiny, @Param("module") int module,
			@Param("aocycleType") List<Integer> aocycleType);

	@Query("Select dpv FROM DayProgressValue dpv where dpv.dayDate= :currentDateObj and dpv.dayOptionId = :destiny")
	List<DayProgressValue> getDayProgressValueByDayDateAndDestinyTest(@Param("currentDateObj") Date currentDateObj,
			@Param("destiny") int destiny);

	@Query("Select dpv FROM DayProgressValue dpv where dpv.dayDate between :from and :to and dpv.dayOptionId = :destiny")
	List<DayProgressValue> getDayProgressValueByDayDateAndDestiny(@Param("from") Date from, @Param("to") Date to,
			@Param("destiny") int destiny);

	@Query("Select dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec where ec.code.id = :womanCodeId and ec.code.treatment.cycleType =:cycleType and ec.dateOfUse is not null and dpv.dayIndex = (select dpv1.dayIndex from DayProgressValue dpv1 where dpv1.dayOptionId =:destiny and dpv1.embryoCode.id = ec.id)")
	List<DayProgressValue> findDayProgressValueByCodeIdDestinyAndDateOfUseNotNull(
			@Param("womanCodeId") Long womanCodeId, @Param("destiny") int destiny, @Param("cycleType") int cycleType);

	/*
	 * @Query("Select dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec where ec.code.id = :womanCodeId and ec.code.treatment.cycleType =:cycleType and ec.dateOfUse is not null and ec.id IN (:aoEmbryoCodeId) and dpv.dayIndex = (select dpv1.dayIndex from DayProgressValue dpv1 where dpv1.dayOptionId =:destiny and dpv1.embryoCode.id = ec.id)"
	 * ) List<DayProgressValue>
	 * findDayProgressValueByWomanCodeIdDestinyEmbryoCodeIdCycleTypeAndDateOfUseNotNull
	 * (@Param("womanCodeId") Long womanCodeId, @Param("destiny") int
	 * destiny, @Param("cycleType") int cycleType, @Param("aoEmbryoCodeId")
	 * List<Long> aoEmbryoCodeId);
	 */

	/**
	 * Find day progress value by woman code id destiny embryo code id cycle
	 * type and date of use not null.
	 *
	 * @param aoWomanCodeId
	 *            the ao woman code id
	 * @param destiny
	 *            the destiny
	 * @param aoCycleType
	 *            the ao cycle type
	 * @param aoEmbryoCodeId
	 *            the ao embryo code id
	 * @return the list
	 */
	@Query("Select dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec where ec.code.id in (:aoWomanCodeId) and ec.code.treatment.cycleType in (:aoCycleType) and ec.dateOfUse is not null and ec.id in (:aoEmbryoCodeId) and dpv.dayIndex = (select dpv1.dayIndex from DayProgressValue dpv1 where dpv1.dayOptionId =:destiny and dpv1.embryoCode.id = ec.id)")
	List<DayProgressValue> findDayProgressValueByWomanCodeIdDestinyEmbryoCodeIdCycleTypeAndDateOfUseNotNull(
			@Param("aoWomanCodeId") List<Long> aoWomanCodeId, @Param("destiny") int destiny,
			@Param("aoCycleType") List<Integer> aoCycleType, @Param("aoEmbryoCodeId") List<Long> aoEmbryoCodeId);

	@Query("SELECT NEW map(dpv.dayIndex as destiny_day, ec.id as embryoCodeId) FROM DayProgressValue dpv inner join dpv.embryoCode ec where ec.code.id = :womanCodeId and dpv.dayOptionId = :destiny")
	List<Map<String, Object>> findDayIndexByCodeIdAndDestiny(@Param("womanCodeId") Long womanCodeId,
			@Param("destiny") int destiny);

	/*	*//**
			 * findDayProgressValueByCodeIdDestinyDateOfUseNotNullDayIndexAndModuleId
			 * 
			 * @param womanCodeId
			 * @param destiny
			 * @param dayIndex
			 * @param moduleId
			 * @return
			 *//*
			 * @Query("SELECT NEW org.cf.card.dto.DayProgressValueDto(dpv.embryoCode.id, dpv.dayDate,dpv.dayIndex,dpv.dayOptionId) "
			 * + "FROM DayProgressValue AS dpv " +
			 * "INNER JOIN dpv.embryoCode AS ec " +
			 * "WHERE ec.code.id = :womanCodeId " //+
			 * "AND dpv.dayOptionId = :destiny " +
			 * "AND ec.dateOfUse is NOT NULL " + "AND dpv.moduleId = :moduleId "
			 * + "AND dpv.dayIndex = :dayIndex ") List<DayProgressValueDto>
			 * findDayProgressValueByCodeIdDestinyDateOfUseNotNullDayIndexAndModuleId(
			 * 
			 * @Param("womanCodeId") Long womanCodeId, @Param("moduleId") int
			 * moduleId, @Param("dayIndex") int dayIndex);
			 */

	/**
	 * Find day progress value by code id and day index.
	 *
	 * @param womanCodeId
	 *            the woman code id
	 * @param destiny
	 *            the destiny
	 * @return the map
	 */
	@Query("SELECT dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec where ec.code.id = :womanCodeId and dpv.dayIndex = :dayIndex and ec.code.treatment.cycleType =:cycleType")
	List<DayProgressValue> findDayProgressValueByCodeIdAndDayIndex(@Param("womanCodeId") Long womanCodeId,
			@Param("dayIndex") int dayIndex, @Param("cycleType") int cycleType);

	// @Query("SELECT dpv FROM DayProgressValue dpv inner join dpv.embryoCode ec
	// where ec.id in (:aoEmbryoCodeId)")

	/**
	 * Find by embryo code id and destiny.
	 *
	 * @param aoEmbryoCodeId
	 *            the ao embryo code id
	 * @param destiny
	 *            the destiny
	 * @return the list
	 */
	@Query("SELECT dpv FROM DayProgressValue AS dpv " + "INNER JOIN dpv.embryoCode AS ec "
			+ "WHERE ec.id IN (:aoEmbryoCodeId) " + "AND dpv.dayIndex " + "IN (SELECT dpv1.dayIndex "
			+ "FROM DayProgressValue AS dpv1 " + "WHERE dpv1.dayOptionId =:destiny)")
	List<DayProgressValue> findByEmbryoCodeIdAndDestiny(@Param("aoEmbryoCodeId") Collection<Long> aoEmbryoCodeId,
			@Param("destiny") int destiny);

}
