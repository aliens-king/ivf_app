package org.cf.card.persistence;

import org.cf.card.model.Remark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RemarkDao extends GenericDao<Remark, Long>{
	
	@Query("FROM Remark r WHERE r.code.id = :codeId and r.type=:remarksType")
	public Remark findRemarkByCodeId(@Param("codeId") Long codeId, @Param("remarksType") int remarksType);
	
}
