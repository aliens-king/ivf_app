package org.cf.card.model;


import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

// Generated Sep 10, 2015 2:25:00 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Semen generated by hbm2java
 */
@Entity
@Table(name = "semen")
public class Semen implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "_use", length = 10)
    private int use;

    @Column(name = "viscosity", columnDefinition  ="int default 0")
    private int viscosity;

    @Column(name = "debris", columnDefinition  ="int default 0")
    private int debris;

    @Column(name = "agglutination", columnDefinition  ="int default 0")
    private int agglutination;

    @Column(name = "aggregation", columnDefinition  ="int default 0")
    private int aggregation;

    @Column(name = "media_added", columnDefinition  ="float default 0.0")
    private float mediaAdded;

    @Column(name = "screen")
    private int screen;

    @Column(name = "remark", columnDefinition="LONGTEXT", nullable=true)
    private String remark;

    @Column(name="time_produced")
    private String timeProduced;

    @Column(name = "time_processed")
    private String timeProcessed;

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "cryoVisibility", nullable = false, columnDefinition = "int default 0")
    private int cryoVisibility;

    @Temporal(TemporalType.DATE)
    @Column(name = "semen_cryo_date", length = 19)
    private Date semenCryoDate;

    @Column(name = "semens", nullable = false, columnDefinition = "int default 0")
    private int semens;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "semen", cascade = {CascadeType.ALL})
    private Set<SemenCode> semenCodes = new HashSet<SemenCode>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "semen", cascade = {CascadeType.ALL})
    private Set<SemenData> semenDatas = new HashSet<SemenData>(0);


    public Semen() {
    }



    public Semen(Long id) {
		this.id = id;
	}



	public Semen(int use, int viscosity, int debris,
    		int agglutination, float mediaAdded, int screen,
            Set<SemenCode> semenCodes, Set<SemenData> semenDatas) {
        this.use = use;
        this.viscosity = viscosity;
        this.debris = debris;
        this.agglutination = agglutination;
        this.mediaAdded = mediaAdded;
        this.screen = screen;
        this.semenCodes = semenCodes;
        this.semenDatas = semenDatas;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUse() {
        return this.use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public int getViscosity() {
        return this.viscosity;
    }

    public void setViscosity(int viscosity) {
        this.viscosity = viscosity;
    }

    public int getDebris() {
        return this.debris;
    }

    public void setDebris(int debris) {
        this.debris = debris;
    }

    public int getAgglutination() {
        return this.agglutination;
    }

    public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}



	public void setAgglutination(int agglutination) {
        this.agglutination = agglutination;
    }

    public int getAggregation() {
		return aggregation;
	}

	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

	public float getMediaAdded() {
        return this.mediaAdded;
    }

    public void setMediaAdded(float mediaAdded) {
        this.mediaAdded = mediaAdded;
    }

    public int getScreen() {
        return this.screen;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }

    public String getTimeProduced() {
		return timeProduced;
	}

	public void setTimeProduced(String timeProduced) {
		this.timeProduced = timeProduced;
	}

	public String getTimeProcessed() {
		return timeProcessed;
	}

	public void setTimeProcessed(String timeProcessed) {
		this.timeProcessed = timeProcessed;
	}

	/*public Set<SemenTreatment> getSemenTreatments() {
        return this.semenTreatments;
    }

    public void setSemenTreatments(Set<SemenTreatment> semenTreatments) {
        this.semenTreatments = semenTreatments;
    }*/

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCryoVisibility() {
		return cryoVisibility;
	}

	public void setCryoVisibility(int cryoVisibility) {
		this.cryoVisibility = cryoVisibility;
	}

	public Set<SemenCode> getSemenCodes() {
		return semenCodes;
	}

	public Set<SemenData> getSemenDatas() {
        return this.semenDatas;
    }
	public void setSemenCodes(Set<SemenCode> semenCodes) {
		this.semenCodes = semenCodes;
	}

	public void setSemenDatas(Set<SemenData> semenDatas) {
        this.semenDatas = semenDatas;
    }

	public Date getSemenCryoDate() {
		return semenCryoDate;
	}

	public void setSemenCryoDate(Date semenCryoDate) {
		this.semenCryoDate = semenCryoDate;
	}

	public int getSemens() {
		return semens;
	}

	public void setSemens(int semens) {
		this.semens = semens;
	}

}
