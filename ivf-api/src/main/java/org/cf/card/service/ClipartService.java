/**
 *
 */
package org.cf.card.service;

import java.util.List;

import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;

/**
 * The Interface ClipartService.
 *
 * @author Nikhil Mahajan
 */
public interface ClipartService extends BaseService<Clipart> {

	/**
	 * Gets the clipart by source.
	 *
	 * @param source the source
	 * @return the clipart by source
	 */
	public List<Clipart> getClipartBySource(String source);

	/**
	 * Gets the clipart by couple.
	 *
	 * @param couple the couple
	 * @return the clipart by couple
	 */
	public Clipart getClipartByCouple(Couple couple);

	/**
	 * Gets the clipart image.
	 *
	 * @param path the path
	 * @return the clipart image
	 */
	public byte[] getClipartImage(String path);
}
