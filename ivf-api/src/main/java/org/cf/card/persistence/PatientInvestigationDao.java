package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.PatientInvestigation;
import org.cf.card.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInvestigationDao extends GenericDao<PatientInvestigation, Long> {

	@Query("FROM User u WHERE u.roleId= :nurseId")
	public List<User> findAlNurse(@Param("nurseId") int nurseId);

}
