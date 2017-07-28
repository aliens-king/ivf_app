package org.cf.card.persistence;

import java.util.Date;
import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Couple;
import org.springframework.stereotype.Repository;

/**
 * Created by Dell on 9/19/2014.
 */
@Repository
public interface ClientDao extends GenericDao<Client, Long> {

    List<Client> findBySurnameLike(String surname);

    List<Client> findByFirstName(String firstName);

    List<Client> findByDOBLike(Date dob);

    List<Client> findByGenderLike(String gender);

    List<Client> findByIdLike(Long id);

    List<Client> findBySurname(String surname);

    List<Client> findByMiddleName(String middleName);

    List<Client> findByFirstNameLike(String firstName);

    List<Client> findByDOB(Date dob);

    List<Client> findByGender(String gender);

    Client findById(Long id);

    List<Client> findByCouple(Couple couple);


}
