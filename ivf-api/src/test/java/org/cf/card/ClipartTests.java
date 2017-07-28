package org.cf.card;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.cf.card.service.ClipartService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.cf.card.model.matcher.ClipartMatcher.clipartEqual;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Dell on 10/6/2014.
 */

public class ClipartTests extends BaseTest {

    @Autowired
    private ClipartService clipartService;

//    private Clipart constructClipart() {
//        Clipart clipart = new Clipart();
//        clipart.setSource("TestSource");
//
//        Couple couple = new Couple();
//        couple.setClipart(clipart);
//
//        Client client = new Client();
//        client.setSurname("Test");
//        client.setGender("Male");
//
//        Client client1 = new Client();
//
//        client.setSurname("TestClient");
//        client.setGender("Female");
//
//        couple.setWoman(client1);
//        couple.setMan(client);
//
//        client.setCouple(couple);
//        clipart.setCouple(couple);
//
//
//        return clipart;
//    }
//
    @Test
         public void addingClipartTest() {
//        Clipart expected = constructClipart();
//        Clipart received = clipartService.createNewClipart(expected);
//        assertThat(received, is(expected));
//        clipartService.deleteClipart(received.getId());
    }
//
//    @Test
//    public void getClipartByIdTest() {
//
//        Clipart expected = constructClipart();
//        Clipart clipartForDeletion = clipartService.createNewClipart(expected);
//        Clipart received = clipartService.getClipartById(expected.getId());
//        assertThat(received, is(clipartEqual(expected)));
//        clipartService.deleteClipart(clipartForDeletion.getId());
//    }
//
//    @Test
//    public void getClipartBySourceTest() {
//        Clipart expected = constructClipart();
//        Clipart clipartForDeletion = clipartService.createNewClipart(expected);
//        List<Clipart> received = clipartService.getClipartBySource(expected.getSource());
//        assertThat(received, hasItem(clipartEqual(expected)));
//        clipartService.deleteClipart(clipartForDeletion.getId());
//    }
//
//    @Test
//    public void getClipartByCoupleTest() {
//        Clipart expected = constructClipart();
//        clipartService.createNewClipart(expected);
//        Clipart received = clipartService.getClipartByCouple(expected.getCouple());
//        assertThat(received, is(clipartEqual(expected)));
//        clipartService.deleteClipart(received.getId());
//    }
//
//    @Test
//    public void getClipartByClientAssociatedToClipartTest() {
//        Clipart expected = constructClipart();
//        clipartService.createNewClipart(expected);
//        Clipart received = clipartService.getClipartByCouple(expected.getCouple());
//        assertThat(received, is(clipartEqual(expected)));
//        clipartService.deleteClipart(received.getId());
//    }
//
//    @Test
//    public void deleteClipartTest() {
//        Clipart created = constructClipart();
//        clipartService.createNewClipart(created);
//        clipartService.deleteClipart(created.getId());
//        Clipart received = clipartService.getClipartById(created.getId());
//        assertThat(received, is(nullValue()));
//    }
//
//    @Test
//    public void updateClipartTest() {
//
//        Clipart expected = constructClipart();
//        Clipart received = clipartService.updateClipart(expected);
//        assertThat(received, is(clipartEqual(expected)));
//        clipartService.deleteClipart(received.getId());
//    }

}
