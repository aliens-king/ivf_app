package org.cf.card.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created by Dell on 10/8/2014.
 */

@Entity
@Table(name = "couple")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Couple implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Client man;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Client woman;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Clipart clipart;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couple", cascade = CascadeType.ALL)
    private List<Archive> archives = new ArrayList<Archive>(0);

    public Couple(){
    	
    }
    
    public Couple(long id) {
		this.id = id;
	}

	public long getId() {
        return id;
    }

    public Client getMan() {
        return man;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMan(Client man) {
        this.man = man;
    }

    public Client getWoman() {
        return woman;
    }

    public void setWoman(Client woman) {
        this.woman = woman;
    }

    public Clipart getClipart() {
        return clipart;
    }

    public void setClipart(Clipart clipart) {
        this.clipart = clipart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Couple couple = (Couple) o;

        if (id != couple.id) return false;
        if (clipart != null ? !clipart.equals(couple.clipart) : couple.clipart != null) return false;
        if (man != null ? !man.equals(couple.man) : couple.man != null) return false;
        if (woman != null ? !woman.equals(couple.woman) : couple.woman != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (man != null ? man.hashCode() : 0);
        result = 31 * result + (woman != null ? woman.hashCode() : 0);
        result = 31 * result + (clipart != null ? clipart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Couple{" +
                "id=" + id +
                ", man=" + man +
                ", woman=" + woman +
                ", clipart=" + clipart +
                '}';
    }
}
