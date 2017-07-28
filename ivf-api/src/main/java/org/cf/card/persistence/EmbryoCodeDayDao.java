package org.cf.card.persistence;

import org.cf.card.model.EmbryoCodeDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmbryoCodeDayDao extends GenericDao<EmbryoCodeDay, Long> {

	@Query("FROM EmbryoCodeDay r WHERE r.embryoCode.id = :embryoCodeId and r.dayIndex=:remarkDayIndex")
	public EmbryoCodeDay findRemarksByEmbryoCodeIdAndDayIndex(@Param("embryoCodeId") Long embryoCodeId,
			@Param("remarkDayIndex") int remarkDayIndex);

}
