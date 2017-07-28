/**
 * 
 */
package org.cf.card.service;

import org.cf.card.dto.EmbryologyRegistrantDto;

/**
 * @author Pramod Maurya
 * @since 14-Jul-2017
 */
public interface EmbryoRegistrantService {

	public EmbryologyRegistrantDto saveEmbryoRegistrant(EmbryologyRegistrantDto embryologyRegistrantDto);

	public EmbryologyRegistrantDto getEmbryoRegistrantByCodeAndScreenId(Long codeId, int screenId);

}
