/**
 *
 */
package org.cf.card.service;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;

/**
 * The Interface CoupleService.
 *
 * @author Nikhil Mahajan
 */
public interface CoupleService extends BaseService<Couple> {

	/**
	 * Gets the couple by man.
	 *
	 * @param man the man
	 * @return the couple by man
	 */
	public Couple getCoupleByMan(Client man);

	/**
	 * Gets the couple by woman.
	 *
	 * @param woman the woman
	 * @return the couple by woman
	 */
	public Couple getCoupleByWoman(Client woman);

	/**
	 * Gets the couple by clipart.
	 *
	 * @param clipart the clipart
	 * @return the couple by clipart
	 */
	public Couple getCoupleByClipart(Clipart clipart);

}
