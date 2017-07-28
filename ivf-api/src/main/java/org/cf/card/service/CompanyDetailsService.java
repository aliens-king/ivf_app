/**
 * 
 */
package org.cf.card.service;

import org.cf.card.dto.CompanyDto;
import org.cf.card.model.Company;

/**
 * The Interface CompanyDetailsService.
 *
 * @author Pramod Maurya
 */
public interface CompanyDetailsService extends BaseService<Company>{
	
	
	/**
	 * Save.
	 *
	 * @param companyDto the company dto
	 * @return the company dto
	 */
	public CompanyDto save(CompanyDto companyDto);
	
	/**
	 * Gets the company details.
	 *
	 * @param id the id
	 * @return the company details
	 */
	public CompanyDto getCompanyDetails(Long id);
	
}
