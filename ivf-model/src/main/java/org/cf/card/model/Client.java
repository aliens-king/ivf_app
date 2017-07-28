package org.cf.card.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 9/19/2014.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Length(min = 0, max = 45)
    @Column
    private String surname;
    @Length(min = 0, max = 45)
    @Column(name = "middle_name")
    private String middleName;
    @Length(min = 0, max = 45)
    @Column(name = "first_name")
    private String firstName;
    @Column
    private Date dOB;
    @Length(min = 0, max = 60)
    @Column
    private String gender;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn
    private Couple couple;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn
    private List<Codes> codes = new ArrayList<Codes>(0);
    @Length(min = 0, max = 150)
    @Column(name = "home_address")
    private String homeAddress;
    @Length(min = 0, max = 14)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Transient
    private String fullName;

    public Client() {
        this.surname = "";
        this.middleName = "";
        this.firstName = "";
        this.dOB = new Date();
        this.gender = "";
        this.homeAddress = "";
        this.phoneNumber = "";
        this.email = "";
    }

    public Client(Long clientId) {
		this.id = clientId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getdOB() {
        return dOB;
    }

    public void setdOB(Date dOB) {
        this.dOB = dOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Codes> getCodes() {
        return codes;
    }

    public void setCodes(List<Codes> codes) {
        this.codes = codes;
    }

    public Couple getCouple() {
        return couple;
    }

    public void setCouple(Couple couple) {
        this.couple = couple;
    }

    public final String getHomeAddress() {
        return homeAddress;
    }

    public final void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public final String getPhoneNumber() {
        return phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }


    public String getFullName(){
    	this.fullName = getFirstName().concat(" ").concat(getSurname());
    	return this.fullName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * Overriding equals method
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Client client = (Client) o;

        if (codes != null ? !codes.equals(client.codes) : client.codes != null)
            return false;
        // if (couple != null ? !couple.equals(client.couple) : client.couple !=
        // null) return false;
        if (dOB != null ? !dOB.equals(client.dOB) : client.dOB != null)
            return false;
        if (gender != null ? !gender.equals(client.gender)
                : client.gender != null)
            return false;
        if (id != null ? !id.equals(client.id) : client.id != null)
            return false;
        if (middleName != null ? !middleName.equals(client.middleName)
                : client.middleName != null)
            return false;
        if (firstName != null ? !firstName.equals(client.firstName)
                : client.firstName != null)
            return false;
        if (surname != null ? !surname.equals(client.surname)
                : client.surname != null)
            return false;
        if (homeAddress != null ? !homeAddress.equals(client.homeAddress)
                : client.homeAddress != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(client.phoneNumber)
                : client.phoneNumber != null)
            return false;
        if (email != null ? !email.equals(client.email)
                : client.email != null)
            return false;

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     * Overriding hashCode method
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (dOB != null ? dOB.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (homeAddress != null ? homeAddress.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     * Overridinh hashCode method
     */
    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", surname='" + surname + '\''
                + ", middleName='" + middleName + '\'' + ", firstName='"
                + firstName + '\'' + ", dOB=" + dOB + ", gender='" + gender
                + '\'' + ", codes=" + codes +  ", homeAddress='"+homeAddress
                + '\'' + ",phoneNumber='"+phoneNumber
                + '\'' + ",email=" +email+ '}';
    }
}
