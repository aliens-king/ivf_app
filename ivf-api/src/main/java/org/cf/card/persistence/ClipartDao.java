package org.cf.card.persistence;

import java.util.List;

import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;

/**
 * Created by Dell on 10/7/2014.
 */
public interface ClipartDao extends GenericDao<Clipart, Long> {

	List<Clipart> findBySource(String source);

	Clipart findById(Long id);

	Clipart findByCouple(Couple couple);
}
