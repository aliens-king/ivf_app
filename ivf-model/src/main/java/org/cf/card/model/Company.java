package org.cf.card.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "company_full_name", columnDefinition = "varchar(255)")
	private String companyFullName;
	
	@Column(name = "company_address", columnDefinition = "LONGTEXT")
	private String companyAddress;
	
	@Column(name = "comapny_logo")
	private Blob companyLogo;
	
	@Column(name = "company_motive", columnDefinition = "LONGTEXT")
	private String companyMotive;
	
	@Column(name = "lockdown_status", columnDefinition = "boolean default false")
	private boolean lockdownPin;
	
	@Column(name = "billing_currency")
	private String billingCurrency;
	
	@Column(name = "billing_vat")
	private int billingVAT;
	
	@Column(name = "billing_refund_policy")
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

	public Blob getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(Blob companyLogo) {
		this.companyLogo = companyLogo;
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

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyFullName=" + companyFullName + ", companyAddress=" + companyAddress
				+ ", companyLogo=" + companyLogo + ", companyMotive=" + companyMotive + ", billingCurrency="
				+ billingCurrency + ", billingVAT=" + billingVAT + ", billingRefundPolicy=" + billingRefundPolicy + "]";
	}

}
