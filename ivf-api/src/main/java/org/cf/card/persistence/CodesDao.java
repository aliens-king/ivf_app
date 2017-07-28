package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Dell on 10/7/2014.
 */
public interface CodesDao extends GenericDao<Codes, Long> {

    List<Codes> findByCodeLike(String code);

    List<Codes> findByTreatment(Treatment treatment);

    Codes findById(long id);

    List <Long> findCodesByCodeLike(String code);

    List<Codes> findByClient(Client id);

    List<Codes> findCodesByCode(String code);
    
    List<Codes> findByClientId(Long id);
  
    //Custome Query for fetch LatestCodeId by clientId
    @Query(value = "SELECT max(c) from Codes c where c.client.id=:clientId")
	Codes findLatestCodeByClientId(@Param("clientId") Long clientId);
    
    //Query for fetch findMaxCodeId
    @Query(value = "SELECT max(c.id) from Codes c")
    Long findMaxCodeId();
   
}
