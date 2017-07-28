package org.cf.card.model.matcher;

import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Created by Dell on 10/18/2014.
 */
public class TreatmentMatcher extends CFMatcher<Treatment> {

    protected TreatmentMatcher(Treatment expected) {
        this(expected, true);
    }

    protected TreatmentMatcher(Treatment expected, boolean recursive) {
        super(expected, recursive);
    }

    @Override
    public boolean matches(Object item) {
        if (!checkIdentity(item)) {
            return false;
        }

        Treatment received = (Treatment) item;

        if (!received.getName().equals(expected.getName()))
            return false;

        if (!received.getStartDate().equals(expected.getStartDate()))
            return false;
        if (!received.getEndDate().equals(expected.getEndDate()))
            return false;

        Matcher<Codes> codesMatcher = CodesMatcher.codesSimpleEqual(expected.getCodes());
        if (recursive && !codesMatcher.matches(received.getCodes()))
            return false;

        return true;
    }

    @Factory
    public static Matcher<Treatment> treatmentSimpleEqual(Treatment expected) {
        return new TreatmentMatcher(expected, false);
    }

    @Factory
    public static Matcher<Treatment> treatmentEqual(Treatment expected) {
        return new TreatmentMatcher(expected);
    }
}
