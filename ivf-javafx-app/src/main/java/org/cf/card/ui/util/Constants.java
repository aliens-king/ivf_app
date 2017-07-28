package org.cf.card.ui.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import org.cf.card.ui.configuration.Configuration;

/**
 * Created by Dell on 28.01.2015.
 *
 */
public abstract class Constants {

	// UILoginService
	public static final String LOCAL_HOST_API_LOGIN = Configuration.getServerUrl() + "/login";

	public static final String RESOURCE_FILEPATH_TEMP_CLIPARTS = System.getProperty("user.home") + File.separator
			+ "AppData" + File.separator + "Local" + "MVID" + File.separator;
	public static final String RESOURCE_ARCHIVE_TEMP_CLIPARTS = System.getProperty("user.home") + File.separator
			+ "AppData" + File.separator + "Local" + "MVID" + File.separator + "archive" + File.separator;
	public static final String RESOURCE_FILEPATH_TEMP_LOGO = System.getProperty("user.home") + File.separator
			+ "AppData" + File.separator + "Local" + "MVID" + File.separator + "Logo" + File.separator + "logo.png";
	public static final String RESOURCE_FILEPATH_PERM_LOGO = "company/logo/logo.png";
	public static final String RESOURCE_ARCHIVE_FILE_UPLOAD = "archive/";
	// window coordinates
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int FRAME_WIDTH = (int) (SCREEN_SIZE.getWidth() * 0.85);
	public static final int FRAME_HEIGHT = (int) (SCREEN_SIZE.getHeight() * 0.85);
	public static final int PATIENT_FILE_FRAME_WIDTH = (int) (SCREEN_SIZE.getWidth() * 0.75);
	public static final int PATIENT_FILE_FRAME_HEIGHT = (int) (SCREEN_SIZE.getHeight() * 0.9);
	public static final int TABLE_WIDTH = (int) (FRAME_WIDTH * 0.735);
	public static final int TABLE_HEIGHT = (int) (FRAME_HEIGHT * 0.7);
	public static final String COMPANY_LOGO = "/resources/images/company/logo.png";
	/*public static final String CURRENCY_TYPE = "$";*/
	public static final String EXECL_PDF_FILE_NAME_UNPAID = "UnapaidOverall_";
	public static final String EXECL_PDF_FILE_NAME_REFUND = "RefundOverall_";

	public static final float SCALE_WIDTH_FULL = 0.85f;
	public static final float SCALE_HEIGHT_FULL = 0.735f;

	

	// AddClientsComponent
	public static final String DESIGN_SOURCE_FILEPATH = org.cf.card.ui.configuration.Configuration.getClipartDir();

	// Validation
	public static final String VALIDATE_FIELDS_PASS_STYLE = "-fx-background-color: #ffffff;";
	public static final String VALIDATE_FIELDS_FAIL_STYLE = "-fx-background-color: #B73630; -fx-text-fill: #ffffff;";

	/* Session object key name */
	public static final String USER_SEESION_KEY = "user";
	public static final String COUPLE_SEESION_KEY = "couple";
	public static final String MANCODE_SESSION_KEY = "manCode";
	public static final String WOMANCODE_SESSION_KEY = "womanCode";
	public static final String IS_CURRUNT_CYCLE = "isCurruntCycle";
	public static final String BILLING_INVOICE = "billingAndInvoice";
	public static final String REFUND_INVOICE = "refundAndInvoice";
//	public static final String CURRENCY_TYPE_SYMBOL = "currencyType";
	public static final String COMPANY_DETAILS = "companyDetails";

	// Print Template
	public static final String PRINT_GREY_BORDER_STYLE = "-fx-border-color: #cccccc; -fx-background-color: #ffffff;";
	public static final String PRINT_STYLE = "-fx-background-color: #FFFFFF; -fx-color:#000000; ";
	public static final String PRINT_FONT_FAMILY = "Open Sans Bold";
	public static final String PRINT_FONT_FAMILY_ITALIC = "Open Sans Italic";
	public static final String FOOTER_TEXT = "MVID0000";
	public static final String FOOTER_URL = "www.ivf-app.com";

	public static enum Extensions {

		PNG(".png"), JPG(".jpg"), TXT(".txt"), PDF(".pdf"), XLSX(".xlsx");

		private String strValue;

		private Extensions(String strValue) {
			this.strValue = strValue;
		}

		public String getStrValue() {
			return strValue;
		}

	}

}