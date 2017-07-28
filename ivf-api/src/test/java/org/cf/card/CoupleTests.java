package org.cf.card;

import static org.cf.card.model.matcher.CoupleMatcher.coupleEqual;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.cf.card.service.CoupleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dell on 10/10/2014.
 */
public class CoupleTests extends BaseTest{

    @Autowired
    private CoupleService coupleService;

    private Couple constructCouple() {

        Client man = new Client();
        Client woman = new Client();
        Clipart clipart = new Clipart();
        Couple couple = new Couple();
        couple.setMan(man);
        couple.setWoman(woman);
        couple.setClipart(clipart);

        return couple;

    }


//    @Test
    public void addCoupleTest() {
        Couple expected = constructCouple();
        Couple received = coupleService.save(expected);
        assertThat(received, is(coupleEqual(expected)));
        coupleService.deleteById(received.getId());
    }

//   @Test
    public void getCoupleByClipartTest() {
        Couple expected = constructCouple();
        coupleService.save(expected);
        Couple received = coupleService.getCoupleByClipart(expected.getClipart());
        assertThat(received, is(coupleEqual(expected)));
        coupleService.deleteById(received.getId());
    }

//    @Test
    public void getCoupleByManTest() {
        Couple expected = constructCouple();
        coupleService.save(expected);
        Couple received = coupleService.getCoupleByMan(expected.getMan());
        assertThat(received, is(coupleEqual(expected)));
        coupleService.deleteById(received.getId());
    }

//    @Test
    public void getCoupleByWomanTest() {
        Couple expected = constructCouple();
        coupleService.save(expected);
        Couple received = coupleService.getCoupleByWoman(expected.getWoman());
        assertThat(received, is(coupleEqual(expected)));
        coupleService.deleteById(received.getId());
    }
//    @Test
    public void updateCoupleTest(){
        Couple expected = constructCouple();
        Couple received = coupleService.update(expected);
        assertThat(received, is(coupleEqual(expected)));
        coupleService.deleteById(received.getId());

    }
    //@Test
    public void deleteCoupleTest(){
        Couple created = constructCouple();
        coupleService.save(created);
        coupleService.deleteById(created.getId());
        Couple received = coupleService.findOne(created.getId());
        assertThat(received, is(nullValue()));
    }
     
}
