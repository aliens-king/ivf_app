/**
 * 
 */
package org.cf.card.service;

import org.cf.card.dto.RegistrantDto;

/**
 * @author Pramod Maurya
 * @since 10-Jul-2017
 */
public interface RegistrantService {
	
	public RegistrantDto saveRegistrant(RegistrantDto registrantDto);
	
	public RegistrantDto getRegistrantByCodeAndScreenId(Long codeId, int screenId);

}
