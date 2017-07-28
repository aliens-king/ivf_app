/**
 *
 */
package org.cf.card.ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.cf.card.util.IConstants;

import javafx.util.StringConverter;

/**
 * @author Nikhil Mahajan
 *
 */
public class DatePickerConverter extends StringConverter<LocalDate>{

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(IConstants.DATE_FORMAT);

    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }

}
