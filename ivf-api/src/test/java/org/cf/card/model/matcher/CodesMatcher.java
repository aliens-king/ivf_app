package org.cf.card.model.matcher;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Created by Dell on 10/17/2014.
 */
public class CodesMatcher extends CFMatcher<Codes> {

    protected CodesMatcher(Codes expected, boolean recursive) {
        super(expected, recursive);
    }

    protected CodesMatcher(Codes expected) {
        this(expected, true);
    }

    public boolean matches(Object item) {
        if (!checkIdentity(item)) {
            return false;
        }

        Codes received = (Codes) item;
        if (!received.getCode().equals(expected.getCode())) {
            return false;
        }
        Matcher<Client> clientMatcher = ClientMatcher.clientSimpleEqual(expected.getClient());
        if (recursive && !clientMatcher.matches(received.getClient()))
            return false;

        Matcher<Treatment> treatmentMatcher = TreatmentMatcher.treatmentSimpleEqual(expected.getTreatment());
        if(recursive && !treatmentMatcher.matches(received.getTreatment()))
            return false;


        return true;
    }

    @Factory
    public static Matcher<Codes> codesSimpleEqual(Codes expected) {
        return new CodesMatcher(expected, false);
    }

    @Factory
    public static Matcher<Codes> codesEqual(Codes expected) {
        return new CodesMatcher(expected);
    }

}
