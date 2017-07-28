/**
 * 
 */
package org.cf.card.service.impl;

import org.cf.card.dto.CompanyDto;
import org.cf.card.model.Company;
import org.cf.card.persistence.CompanyDetailsDao;
import org.cf.card.service.CompanyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class CompanyDetailsServiceImpl.
 *
 * @author Pramod Maurya
 */
@Service("companyDetailsService")
public class CompanyDetailsServiceImpl extends BaseServiceImpl<Company> implements CompanyDetailsService {

	private CompanyDetailsDao companyDetailsDao;

	@Autowired
	public final void setCompanyDao(CompanyDetailsDao companyDetailsDao) {
		this.companyDetailsDao = companyDetailsDao;
		setGenericDao(companyDetailsDao);
	}

	/*
	 * (non-Javadoc) param companyDto Save the Company Details in
	 * CompanyEntity(Database Table)
	 * 
	 * @see org.cf.card.service.CompanyDetailsService#save(org.cf.card.dto.
	 * CompanyDto)
	 */
	@Override
	public CompanyDto save(CompanyDto companyDto) {
		Company company = getCompany(companyDto);
		Company companyData = companyDetailsDao.save(company);
		CompanyDto companyDtoData = getCompanyDto(companyData);
		return companyDtoData;
	}

	/*
	 * (non-Javadoc) param id of superuser get details of Company from Database
	 * 
	 * @see
	 * org.cf.card.service.CompanyDetailsService#getCompanyDetails(java.lang.
	 * Long)
	 */
	@Override
	public CompanyDto getCompanyDetails(Long id) {
		CompanyDto companyDto = null;
		if (null != id) {
			Company company = companyDetailsDao.findOne(id);
			companyDto = getCompanyDto(company);
		}
		return companyDto;
	}

	/**
	 * Gets the company.
	 *
	 * @param companyDto
	 *            the company dto
	 * @return the company
	 */
	private Company getCompany(CompanyDto companyDto) {
		Company company = null;
		if (null != companyDto) {
			company = new Company();
			if (null != companyDto.getId()) {
				company.setId(companyDto.getId());
			}
			company.setCompanyFullName(companyDto.getCompanyFullName());
			company.setCompanyAddress(companyDto.getCompanyAddress());
			company.setCompanyMotive(companyDto.getCompanyMotive());
			company.setBillingCurrency(companyDto.getBillingCurrency());
			company.setBillingVAT(companyDto.getBillingVAT());
			company.setBillingRefundPolicy(companyDto.getBillingRefundPolicy());
		}
		return company;
	}

	/**
	 * Gets the company dto.
	 *
	 * @param company
	 *            the company
	 * @return the company dto
	 */
	private CompanyDto getCompanyDto(Company company) {
		CompanyDto companyDto = null;
		if (null != company) {
			companyDto = new CompanyDto();
			companyDto.setId(company.getId());
			companyDto.setCompanyFullName(company.getCompanyFullName());
			companyDto.setCompanyAddress(company.getCompanyAddress());
			companyDto.setCompanyMotive(company.getCompanyMotive());
			companyDto.setBillingCurrency(company.getBillingCurrency());
			companyDto.setBillingVAT(company.getBillingVAT());
			companyDto.setBillingRefundPolicy(company.getBillingRefundPolicy());
		}
		return companyDto;
	}

}
