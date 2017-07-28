package org.cf.card.persistence;

import org.cf.card.model.User;

/**
 * Created by Dell on 10/10/2014.
 */
public interface UserDao extends GenericDao<User, Long> {

	User findById(long idLogin);

	User findByLoginCode(String loginCode);

}
