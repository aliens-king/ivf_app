package org.cf.card.persistence;

import org.cf.card.model.Procedure;
import org.springframework.stereotype.Repository;

@Repository("billingSetupDao")
public interface BillingSetupDao extends GenericDao<Procedure, Long> {

}
