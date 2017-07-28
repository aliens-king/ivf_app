package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.DoctorOffice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorOfficeDao extends GenericDao<DoctorOffice, Long>{
	
	
	@Query("FROM DoctorOffice do WHERE do.couple.id = :coupleId")
	public List<DoctorOffice>  findDoctorOfficeByCoupleId(@Param("coupleId") Long coupleId);

}
