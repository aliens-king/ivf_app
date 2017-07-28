/**
 * 
 */
package org.cf.card.persistence;

import org.cf.card.dto.EmbryologyRegistrantDto;
import org.cf.card.model.EmbryologyRegistrant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
@Repository("embryoRegistrantDao")
public interface EmbryoRegistrantDao extends GenericDao<EmbryologyRegistrant, Long> {

	@Query("SELECT NEW org.cf.card.dto.EmbryologyRegistrantDto(er.id, " + "er.embRegistrantDay0, er.drRegistrantDay0, "
			+ "er.embRegistrantDay1, er.drRegistrantDay1, er.embRegistrantDay2, er.drRegistrantDay2, "
			+ "er.embRegistrantDay3, er.drRegistrantDay3, er.embRegistrantDay4, er.drRegistrantDay4, "
			+ "er.embRegistrantDay5, er.drRegistrantDay5, er.embRegistrantDay6, er.drRegistrantDay6, "
			+ "er.embRegistrantDay7, er.drRegistrantDay7) FROM EmbryologyRegistrant AS er "
			+ "INNER JOIN er.code c WHERE c.id=:codeId AND er.screenId=:screenId")
	public EmbryologyRegistrantDto findByCodeAndScreenId(@Param("codeId") Long codeId, @Param("screenId") int screenId);

}
