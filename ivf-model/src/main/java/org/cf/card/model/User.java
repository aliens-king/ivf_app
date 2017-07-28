package org.cf.card.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Dell on 10/10/2014.
 */

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String loginCode;

	@Column
	private String surname;

	@Column
	private String firstName;

	@Column
	private String email;

	@Column
	private String address;

	@Column
	private String country;

	@Column(name = "role_id")
	private int roleId;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nurse")
	List<PatientInvestigation> patientInvestigation = new ArrayList<>();

	/**
	 * Constructor using primary key
	 *
	 * @param id
	 */
	public User(long id) {
		this.id = id;
	}

	/**
	 * Default Constructor
	 */
	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<PatientInvestigation> getPatientInvestigation() {
		return patientInvestigation;
	}

	public void setPatientInvestigation(List<PatientInvestigation> patientInvestigation) {
		this.patientInvestigation = patientInvestigation;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User login = (User) o;

		if (id != login.id)
			return false;
		if (address != null ? !address.equals(login.address) : login.address != null)
			return false;
		if (country != null ? !country.equals(login.country) : login.country != null)
			return false;
		if (email != null ? !email.equals(login.email) : login.email != null)
			return false;
		if (firstName != null ? !firstName.equals(login.firstName) : login.firstName != null)
			return false;
		if (loginCode != null ? !loginCode.equals(login.loginCode) : login.loginCode != null)
			return false;
		if (surname != null ? !surname.equals(login.surname) : login.surname != null)
			return false;
		if (roleId != login.roleId)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (loginCode != null ? loginCode.hashCode() : 0);
		result = 31 * result + (surname != null ? surname.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (roleId ^ (roleId >>> 32));
		return result;
	}

}
