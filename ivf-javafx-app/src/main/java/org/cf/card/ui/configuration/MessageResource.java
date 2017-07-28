package org.cf.card.ui.configuration;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class MessageResource {

	private static final Logger log = Logger.getLogger(MessageResource.class);
	private static ResourceBundle resourceBundle;

	public static void init(Locale locale) throws IOException {
		log.info("Initilaizng MessageResource Bundle");
		resourceBundle = new PropertyResourceBundle(
				MessageResource.class.getResourceAsStream(("/bundle/message.properties")));

	}

	public static String getText(String key) {
		return resourceBundle.getString(key);
	}

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static String getText(String key, Object... args) {
		return MessageFormat.format(getText(key), args);
		// return resourceBundle.getString(key);
	}
}
