/**
 *
 */
package org.cf.card.service;

import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;

/**
 * The Interface CodesService.
 *
 * @author Nikhil Mahajan
 */
public interface CodesService extends BaseService<Codes> {

	/**
	 * Creates the new code.
	 *
	 * @param code the code
	 * @return the codes
	 */
	Codes createNewCode(Codes code);

	/**
	 * Gets the codes by treatment.
	 *
	 * @param treatment the treatment
	 * @return the codes by treatment
	 */
	List<Codes> getCodesByTreatment(Treatment treatment) ;

	/**
	 * Gets the codes by client.
	 *
	 * @param client the client
	 * @return the codes by client
	 */
	List<Codes> getCodesByClient(Client client) ;

	/**
	 * Gets the codes by code.
	 *
	 * @param code the code
	 * @return the codes by code
	 */
	List<Codes> getCodesByCode(String code) ;

	Codes findRecent();
	
	List<Codes> findCodesByClientId(Long clientId);
	
	
	
	/**
	 * Find latest code by client id.
	 *
	 * @param clientId the client id
	 * @return the long
	 */
	Codes findLatestCodeByClientId(Long clientId);
	

}
