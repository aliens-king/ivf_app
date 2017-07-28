/**
 *
 */
package org.cf.card.service;

import java.util.List;

import org.cf.card.model.User;

/**
 * The Interface PermissionServiceIntf.
 *
 * @author Nikhil Mahajan
 */
public interface UserService extends BaseService<User> {

	/**
	 * Gets the by login code.
	 *
	 * @param code the code
	 * @return the by login code
	 */
	List<User> getByLoginCode(String code);



	/**
	 * return authenticated user
	 * @param password
	 * @return
	 */
	User findByPassword(String password);

}
