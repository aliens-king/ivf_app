package org.cf.card.persistence;

import java.util.Date;
import java.util.List;

import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Dell on 10/15/2014.
 */
@Repository
public interface TreatmentDao extends GenericDao<Treatment, Long> {


    Treatment findById(long id);

    List<Treatment> findByName(String name);

    List<Treatment> findByStartDate(Date startDate);

    List<Treatment> findByEndDate(Date endDate);

    /* get latest treatment by passing parameter coupleId*/
    @Query("FROM Treatment t WHERE t.codes.client.couple.id = :coupleId order by t.startDate desc")
    //@Query("FROM Treatment t WHERE t.id IN (SELECT c.treatment.id FROM Codes c WHERE c.client.id IN (SELECT c.id FROM Client c WHERE c.couple.id =:coupleId)) order by t.startDate desc")
    List<Treatment> findTreatmentByCoupleId(@Param("coupleId") Long coupleId);

    Treatment findByCodes(Codes codes);

}
