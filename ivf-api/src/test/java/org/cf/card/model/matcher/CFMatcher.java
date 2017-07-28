package org.cf.card.model.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author <a href="mailto:sbulzan@sdl.com">Stefan Bulzan</a>
 * @since WS 11.0
 */

public abstract class CFMatcher<T> extends BaseMatcher<T> {
    protected final T expected;
    protected final StringBuilder errors = new StringBuilder();
    protected final boolean recursive;

    protected CFMatcher(T expected, boolean recursive) {
        this.expected = expected;
        this.recursive = recursive;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(errors.toString());
    }

    protected boolean checkIdentity(Object item) {
        if (item == expected) {
            return true;
        }
        if (item == null || expected == null) {
            addError("One is null; the other is not. Expected: ", expected, ". Received: ", item);
            return false;
        }
        return true;
    }

    protected void addError(Object... errorMsgs) {
        for (Object errorObject : errorMsgs) {
            errors.append(String.valueOf(errorObject));
        }
    }
}
