package org.cf.card.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Dell on 10/14/2014.
 */
@Entity
@Table(name = "treatment")
public class Treatment implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = -8216849029287005038L;



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    //@Length(min = 0, max = 45)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Codes codes;
/*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_investigation_id")
    private PatientInvestigation patientInvestigation;*/

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", length = 19)
    private Date endDate;

    @Column(name = "name", length = 45)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", length = 19)
    private Date startDate;

    @Column(name = "eggs", nullable = false, columnDefinition = "int default 0")
    private int eggs;

    @Column(name = "incubator", length = 45)
    private String incubator;
    
    @Column(name = "research", nullable = false, columnDefinition = "int default 0")
    private int research;
    
    @Column(name ="cycle_type" , nullable= false)
    private int cycleType;
    
    
    public int getCycleType() {
		return cycleType;
	}

	public void setCycleType(int cycleType) {
		this.cycleType = cycleType;
	}

	public int getResearch() {
		return research;
	}

	public void setResearch(int research) {
		this.research = research;
	}

	public Treatment(long id) {
		this.id = id;
	}

	public Treatment() {
		super();
	}

	public long getIdTreatment() {
        return id;
    }

    public void setIdTreatment(long idTreatment) {
        this.id = idTreatment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Codes getCodes() {
        return codes;
    }

    public void setCodes(Codes codes) {
        this.codes = codes;
    }
    public  int getEggs() {
        return eggs;
    }

    public  void setEggs(int eggs) {
        this.eggs = eggs;
    }

   public  String getIncubator() {
        return incubator;
    }

    public  void setIncubator(String incubator) {
        this.incubator = incubator;
    }
    
	@Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Treatment treatment = (Treatment) o;

        if (id != treatment.id)
            return false;
        if (codes != null ? !codes.equals(treatment.codes)
                : treatment.codes != null)
            return false;
        if (endDate != null ? !endDate.equals(treatment.endDate)
                : treatment.endDate != null)
            return false;
        if (name != null ? !name.equals(treatment.name)
                : treatment.name != null)
            return false;
        if (startDate != null ? !startDate.equals(treatment.startDate)
                : treatment.startDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Treatment{" + "id=" + id + ", name='" + name + '\''
                + ", startDate='" + startDate + '\'' + ", endDate='" + endDate
                + '\'' + '}';
    }
}

