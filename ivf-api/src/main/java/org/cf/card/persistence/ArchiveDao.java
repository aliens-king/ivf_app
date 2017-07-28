package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.Archive;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Ankit
 *
 */
public interface ArchiveDao extends GenericDao<Archive, Long> {
	
	//public Archive findById(long id);
	
	//@Query("Select a FROM archive a where a.couple.id: coupleId")
	@Query(value = "Select a FROM Archive a where a.couple.id =:coupleId")
	public List<Archive> findByCoupleId(@Param("coupleId")long coupleId);
	
	
}
