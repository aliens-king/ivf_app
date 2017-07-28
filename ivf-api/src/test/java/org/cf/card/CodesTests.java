package org.cf.card;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.cf.card.service.AppointmentService;
import org.cf.card.service.CodesService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.cf.card.model.matcher.CodesMatcher.codesEqual;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Dell on 10/8/2014.
 */

public class CodesTests extends BaseTest{

    @Autowired
    private CodesService codesService;


    private Codes constructCode() {
        Treatment treatment = new Treatment();
        treatment.setName("TestName");
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        date.setNanos(0);
        treatment.setStartDate(date);
        treatment.setEndDate(date);

        Client client = new Client();
        client.setSurname("TestSurname");
        client.setFirstName("Test Other Names");
        client.setGender("Male");


        Codes code = new Codes();
        code.setCode("XYZ");
        code.setTreatment(treatment);
        code.setClient(client);
        return code;

    }

    @Test
    public void addCodeTest() {
        Codes expected = constructCode();
        Codes received = codesService.createNewCode(expected);
        assertThat(received, is(codesEqual(expected)));
        codesService.deleteById(received.getId());
    }

    @Test
    public void getCodeByIdTest() {
        Codes expected = constructCode();
        codesService.createNewCode(expected);
        Codes received = codesService.findOne(expected.getId());
        assertThat(received, is(codesEqual(expected)));
        codesService.deleteById(received.getId());

    }

    @Test
    public void getCodeByCodeTest() {
        Codes expected = constructCode();
        Codes codeForDeletion = codesService.createNewCode(expected);
        List<Codes> received = codesService.getCodesByCode(expected.getCode());
        assertThat(received, hasItem(codesEqual(expected)));
        codesService.deleteById(codeForDeletion.getId());
    }

   @Test
    public void deleteCodeTest() {
        Codes created = constructCode();
        codesService.createNewCode(created);
        codesService.deleteById(created.getId());
        Codes received = codesService.findOne(created.getId());
        assertThat(received, is(nullValue()));
    }

    @Test
    public void updateCodeTest() {
        Codes expected = constructCode();
        Codes received = codesService.update(expected);
        assertThat(received, is(codesEqual(expected)));
        codesService.deleteById(received.getId());
    }

    @Test
    public void getCodeByIdClientTest() {
        Codes expected = constructCode();
        Codes codeForDeletion = codesService.createNewCode(expected);
        List<Codes> received = codesService.getCodesByClient(expected.getClient());
        assertThat(received, hasItem(codesEqual(expected)));
        codesService.deleteById(codeForDeletion.getId());
    }

   @Test
    public void getCodeByTreatmentTest() {

        Codes expected = constructCode();
        Codes codeForDeletion = codesService.createNewCode(expected);
        List<Codes> received = codesService.getCodesByTreatment(expected.getTreatment());
        assertThat(received, hasItem(codesEqual(expected)));
        codesService.deleteById(codeForDeletion.getId());
    }

    @Test
    public void findCodesByClientId(){
    	List<Codes> aoCodes = codesService.findCodesByClientId(2l);
    	for (Codes codes : aoCodes) {
    		System.out.println(codes.getCode());
		}
    }

   /* @Test
    public void findLatestCodeByClientId(){
    	Codes codes = appointmentService.findLatestCodeByClientId(1l);
    	System.out.println(codes.getCode());

    }*/
    @Test
    public void findLatestCodeByClientId(){
    	Codes latestCodeId = codesService.findLatestCodeByClientId(1l);
    	System.out.println(latestCodeId);

    }

//    @Test
//    public void getRecentCode() {
//
//    	Codes code = codesService.findRecent();
//    	System.out.println("----cods"+code);
//    	assertThat(code, is(codesEqual(null)));
//
//    }


//    @Test
//    public void updateTest() {
//
//    	Codes code = codesService.findOne(5l);
//		code.getTreatment().setEggs(4);
//		code.getTreatment().setIncubator("A2");
//		codesService.update(code);
//	    codesService.deleteById(code.getId());
//    }
}
