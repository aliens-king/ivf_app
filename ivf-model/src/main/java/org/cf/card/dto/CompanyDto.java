/**
 * 
 */
package org.cf.card.dto;

/**
 * @author insonix
 *
 */
public class CompanyDto {

	private Long id;

	private String companyFullName;

	private String companyAddress;

	private String companyMotive;

	private boolean lockdownPin;

	private String billingCurrency;

	private int billingVAT;

	private String billingRefundPolicy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyFullName() {
		return companyFullName;
	}

	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyMotive() {
		return companyMotive;
	}

	public void setCompanyMotive(String companyMotive) {
		this.companyMotive = companyMotive;
	}

	public boolean isLockdownPin() {
		return lockdownPin;
	}

	public void setLockdownPin(boolean lockdownPin) {
		this.lockdownPin = lockdownPin;
	}

	public String getBillingCurrency() {
		return billingCurrency;
	}

	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	public int getBillingVAT() {
		return billingVAT;
	}

	public void setBillingVAT(int billingVAT) {
		this.billingVAT = billingVAT;
	}

	public String getBillingRefundPolicy() {
		return billingRefundPolicy;
	}

	public void setBillingRefundPolicy(String billingRefundPolicy) {
		this.billingRefundPolicy = billingRefundPolicy;
	}

}
