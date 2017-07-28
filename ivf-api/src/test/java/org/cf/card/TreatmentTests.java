package org.cf.card;

import static org.cf.card.model.matcher.TreatmentMatcher.treatmentEqual;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.cf.card.service.TreatmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dell on 10/16/2014.
 */
public class TreatmentTests extends BaseTest {
    @Autowired
    private TreatmentService treatmentService;

    private Treatment constructTreatment() {
        Treatment treatment = new Treatment();
        treatment.setName("Test");
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        date.setNanos(0);
        treatment.setStartDate(date);
        treatment.setEndDate(date);
        Codes codes = new Codes();
        codes.setTreatment(treatment);
        codes.setCode("aaa");
        treatment.setCodes(codes);
        return treatment;
    }

//    @Test
    public void addTreatmentTest() {
        Treatment expected = constructTreatment();
        Treatment received = treatmentService.save(expected);
        assertThat(received, is(treatmentEqual(expected)));
        treatmentService.deleteById(received.getIdTreatment());
    }

//    @Test
    public void getById() {
        Treatment expected = constructTreatment();
        treatmentService.save(expected);
        Treatment received = treatmentService.findOne(expected.getIdTreatment());
        assertThat(received, is(treatmentEqual(expected)));
        treatmentService.deleteById(received.getIdTreatment());
    }

//    @Test
    public void getByName() {
        Treatment expected = constructTreatment();
        treatmentService.save(expected);
        List<Treatment> received = treatmentService.getByName(expected.getName());
        assertThat(received, hasItem(treatmentEqual(expected)));
        treatmentService.deleteById(expected.getIdTreatment());
    }

//    @Test
    public void getByStartDate() {
        Treatment expected = constructTreatment();
        treatmentService.save(expected);
        List<Treatment> received = treatmentService.getByStartDate(expected.getStartDate());
        assertThat(received, hasItem(treatmentEqual(expected)));
        treatmentService.deleteById(expected.getIdTreatment());
    }

//    @Test
    public void getByEndDate() {
        Treatment expected = constructTreatment();
        treatmentService.save(expected);
        List<Treatment> received = treatmentService.getByEndDate(expected.getEndDate());
        assertThat(received, hasItem(treatmentEqual(expected)));
        treatmentService.deleteById(expected.getIdTreatment());
    }

//    @Test
    public void deleteTreatmentTest() {
        Treatment created = constructTreatment();
        treatmentService.save(created);
        treatmentService.deleteById(created.getIdTreatment());
        Treatment received = treatmentService.findOne(created.getIdTreatment());
        assertThat(received, is(nullValue()));
    }

    @Test
    public void latestTreatmentByCoupleId() {


    	TreatmentDto treatmentDto=treatmentService.findLatesTreatmentByCoupleId(1l);
    	System.out.println(treatmentDto.getStartDate());
    	System.out.println(treatmentDto.getEndDate());
    }
}