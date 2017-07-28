package org.cf.card.model.matcher;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.cf.card.model.matcher.ClientMatcher.clientSimpleEqual;
import static org.cf.card.model.matcher.ClipartMatcher.clipartSimpleEqual;

/**
 * @author <a href="mailto:sbulzan@sdl.com">Stefan Bulzan</a>
 * @since WS 11.0
 */

public class CoupleMatcher extends CFMatcher<Couple> {
    protected CoupleMatcher(Couple expected) {
        this(expected, true);
    }

    protected CoupleMatcher(Couple expected, boolean recursive) {
        super(expected, recursive);
    }

    @Override
    public boolean matches(Object item) {
        if (!checkIdentity(item)) {
            return false;
        }
        Couple received = (Couple) item;
        Matcher<Client> clientMatcher = clientSimpleEqual(expected.getMan());
        if (recursive && !clientMatcher.matches(received.getMan())) {
            return false;
        }
        Matcher<Client> clientMatcher1 = clientSimpleEqual(expected.getWoman());
        if(recursive && !clientMatcher1.matches(received.getWoman())){
            return false;
        }

        Matcher<Clipart> clipartMatcher = clipartSimpleEqual(expected.getClipart());
        if(recursive && !clipartMatcher.matches(received.getClipart()))
            return false;
        return true;
    }

    @Factory
    public static Matcher<Couple> coupleEqual(Couple expected) {
        return new CoupleMatcher(expected);
    }

    @Factory
    public static Matcher<Couple> coupleSimpleEqual(Couple expected) {
        return new CoupleMatcher(expected, false);
    }
}
