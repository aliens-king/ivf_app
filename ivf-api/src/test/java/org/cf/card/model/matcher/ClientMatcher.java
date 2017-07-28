package org.cf.card.model.matcher;

import org.cf.card.model.Client;
import org.cf.card.model.Couple;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.cf.card.model.matcher.CoupleMatcher.coupleSimpleEqual;

/**
 * @author <a href="mailto:sbulzan@sdl.com">Stefan Bulzan</a>
 * @since WS 11.0
 */

public class ClientMatcher extends CFMatcher<Client> {
    protected ClientMatcher(Client expected) {
        this(expected, true);
    }

    protected ClientMatcher(Client expected, boolean recursive) {
        super(expected, recursive);
    }

    @Override
    public boolean matches(Object item) {
        if (!checkIdentity(item)) {
            return false;
        }
        Client received = (Client) item;
        if (!received.getSurname().equals(expected.getSurname())) {
            addError("The surname are not equal. Expected: " + expected.getSurname() + " ;Received: " + received.getSurname());
            return false;
        }


//        if (!received.getCodes().equals(expected.getCodes())) {
//            return false;
//        }
        Matcher<Couple> coupleMatcher = coupleSimpleEqual(expected.getCouple());
        if (recursive && !coupleMatcher.matches(received.getCouple())) {
            return false;
        }
//        if (!received.getdOB().equals(expected.getdOB())) {
//            return false;
//        }
        if (!received.getGender().equals(expected.getGender())) {
            return false;
        }
        if (!received.getFirstName().equals(expected.getFirstName())) {
            return false;
        }
        return true;
    }


    @Factory
    public static Matcher<Client> clientEqual(Client expected) {
        return new ClientMatcher(expected);
    }

    @Factory
    public static Matcher<Client> clientSimpleEqual(Client expected) {
        return new ClientMatcher(expected, false);
    }
}