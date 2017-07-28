/**
 * 
 */
package org.cf.card.persistence;

import org.cf.card.dto.RegistrantDto;
import org.cf.card.model.Registrant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Pramod Maurya
 * @since 10-Jul-2017
 */
@Repository("registrantDao")
public interface RegistrantDao extends GenericDao<Registrant, Long> {

	@Query("SELECT NEW org.cf.card.dto.RegistrantDto(r.id, r.nameOfUser, r.assistantUser) FROM Registrant AS r "
			+ "INNER JOIN r.code c " 
			+ "WHERE c.id=:codeId " 
			+ "AND r.screenId=:screenId")
	public RegistrantDto findByCodeAndScreenId(@Param("codeId") Long codeId, @Param("screenId") int screenId);
}
