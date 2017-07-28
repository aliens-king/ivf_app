package org.cf.card.model.matcher;

import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Created by Dell on 10/18/2014.
 */
public class ClipartMatcher extends CFMatcher<Clipart> {

    protected ClipartMatcher(Clipart expected) {
        this(expected, true);
    }

    protected ClipartMatcher(Clipart expected, boolean received) {
        super(expected, received);
    }

    @Override
    public boolean matches(Object item) {
        if (!checkIdentity(item)) {
            return false;
        }

        Clipart received = (Clipart) item;
//        if (!received.getSource().equals(expected.getSource()))
//            return false;
        Matcher<Couple> coupleMatcher = CoupleMatcher.coupleSimpleEqual(expected.getCouple());
        if (recursive && !coupleMatcher.matches(received.getCouple()))
            return false;

        return true;
    }

    @Factory
    public static Matcher<Clipart> clipartEqual(Clipart expected){
        return new ClipartMatcher(expected);
    }
    @Factory
    public static Matcher<Clipart> clipartSimpleEqual(Clipart expected){
        return new ClipartMatcher(expected,false);
    }
}
