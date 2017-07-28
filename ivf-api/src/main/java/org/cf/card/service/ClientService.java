/**
 *
 */
package org.cf.card.service;

import java.util.Date;
import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Couple;

/**
 * The Interface PermissionServiceIntf.
 *
 * @author Nikhil Mahajan
 */
public interface ClientService extends BaseService<Client> {
	
	/**
	 * Gets the all clients by couple id.
	 *
	 * @param coupleId the couple id
	 * @return the all clients by couple id
	 */
	public List<Client> findAllClientsByCoupleId(Long coupleId);

	/**
	 * Gets the client by surname.
	 *
	 * @param surname the surname
	 * @return the client by surname
	 */
	public List<Client> getClientBySurname(String surname);

	/**
	 * Gets the client by middle name.
	 *
	 * @param middleName the middle name
	 * @return the client by middle name
	 */
	public List<Client> getClientByMiddleName(String middleName);

	/**
	 * Gets the clients by first name.
	 *
	 * @param firstName the first name
	 * @return the clients by first name
	 */
	public List<Client> getClientsByFirstName(String firstName);

	/**
	 * Gets the clients by dob.
	 *
	 * @param dob the dob
	 * @return the clients by dob
	 */
	public List<Client> getClientsByDOB(Date dob);

	/**
	 * Gets the clients by gender.
	 *
	 * @param gender the gender
	 * @return the clients by gender
	 */
	public List<Client> getClientsByGender(String gender);

	/**
	 * Gets the clients by couple.
	 *
	 * @param couple the couple
	 * @return the clients by couple
	 */
	public List<Client> getClientsByCouple(Couple couple);

	/**
	 * Gets the client by surname like.
	 *
	 * @param surname the surname
	 * @return the client by surname like
	 */
	public List<Client> getClientBySurnameLike(String surname);

	/**
	 * Gets the clients by first name like.
	 *
	 * @param firstName the first name
	 * @return the clients by first name like
	 */
	public List<Client> getClientsByFirstNameLike(String firstName);

	/**
	 * Gets the clients by dob like.
	 *
	 * @param dob the dob
	 * @return the clients by dob like
	 */
	public List<Client> getClientsByDOBLike(Date dob);

	/**
	 * Gets the clients by gender like.
	 *
	 * @param gender the gender
	 * @return the clients by gender like
	 */
	public List<Client> getClientsByGenderLike(String gender);

}
