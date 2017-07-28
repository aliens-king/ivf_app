package org.cf.card.persistence;

import javax.transaction.Transactional;

import org.cf.card.model.Semen;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemenDao extends GenericDao<Semen, Long> {



	@Transactional
	@Modifying
	@Query("delete from Semen s where s.id = :id and s.screen = :screen")
	public void deleteSemenByIdAndScreen(@Param ("id") Long id, @Param("screen") int screen);

}