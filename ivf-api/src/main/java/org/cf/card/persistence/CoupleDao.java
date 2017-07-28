package org.cf.card.persistence;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;

/**
 * Created by Dell on 10/8/2014.
 */
public interface CoupleDao extends GenericDao<Couple, Long> {

    Couple findByMan(Client man);

    Couple findByWoman(Client woman);

    Couple findById(long id);

    Couple findByClipart(Clipart idClipartId);
}
