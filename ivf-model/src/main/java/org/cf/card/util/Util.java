package org.cf.card.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.Node;

public class Util {

	public static Date getDateFromLocalDate(LocalDate localDate) {
		Instant fromInstant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		return Date.from(fromInstant);
	}

	
	/**
	 * String to date.
	 * This method is use for convert date or time string value to Date object.
	 *
	 * @param inputDateOrTime the input date or time
	 * @param format the format
	 * @return the date
	 */
	public static Date stringToDate(String inputDateOrTime, String format) {
		
		if (null != inputDateOrTime) {
			try {
			String dateOrTimeString = inputDateOrTime;
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date convertedDateOrTimeString = dateFormat.parse(dateOrTimeString);
			return convertedDateOrTimeString;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	
	/** This method is use for convert Date object to string value.
	 * Format date.
	 *
	 * @param pattern
	 *            the pattern
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String formatDate(String pattern, Date date) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		String dateString = "";
		if (date != null) {
			dateString = dateFormat.format(date);
		}
		return dateString;
	}

	@SuppressWarnings("deprecation")
	public static boolean dateMonthYearComparision(Date endDate) {

		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, endDate.getMonth());
		cal.set(Calendar.DATE, endDate.getDate());
		cal.set(Calendar.YEAR, endDate.getYear() + 1900);
		endDate = cal.getTime();
		System.out.println(endDate);
		System.out.println(currentDate);
		if (currentDate.after(endDate))
			return true;
		return false;
	}

	

	/**
	 * get next date form current date
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date nextDate(Date date, int day) {
		if (null != date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, day);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	/**
	 * get next date form current date
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date previousDate(Date date) {
		if (null != date) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			System.out.println("Yesterday's date = " + cal.getTime());

			/*
			 * Calendar calendar = Calendar.getInstance();
			 * calendar.setTime(date); calendar.add(Calendar.DAY_OF_YEAR, day);
			 */
			// return calendar.getTime();
			return cal.getTime();
		} else {
			return null;
		}
	}

	public static Date addDate(Date date, int days) {
		if (null != date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	/**
	 * Compare date with current date
	 *
	 * @param date
	 * @return
	 */
	public static Boolean currentDateComparison(Date date) {
		Date currentDate = new Date();
		if (null != date) {
			if (currentDate.compareTo(date) == 0)
				return true;
			else
				return false;
		} else {
			return null;
		}
	}

	/**
	 * @param currentDate
	 * @param nextDate
	 * @return
	 */
	public static Boolean CompareDates(String currentDate, String nextDate) {
		if (null != currentDate && null != nextDate) {
			if (currentDate.compareTo(nextDate) == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return null;
		}
	}

	public static long generateUID() {
		long l = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		return l;
	}

	/**
	 * Compare dates.
	 *
	 * @param date1
	 *            the date1
	 * @param date2
	 *            the date2
	 * @return 0 if dates are equal 1 date1 > date2 2 if date1 < date2
	 */
	public static int compareDates(Date date1, Date date2) {

		int retVal = -1;
		if (date1.equals(date2)) {
			retVal = 0;
		}

		if (date1.after(date2)) {
			retVal = 1;
		}

		if (date1.before(date2)) {
			retVal = 2;
		}
		return retVal;

	}

	/**
	 * Converts util time to java 8 local time
	 *
	 * @param time
	 *            the time
	 * @return the local time
	 */
	public static LocalTime UtilTimeToLocalTime(Date time) {
		Instant instant = Instant.ofEpochMilli(time.getTime());
		LocalTime retVal = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
		return retVal;
	}

	/**
	 * Converts util date to java 8 local date
	 *
	 * @param date
	 *            the date
	 * @return the local date
	 */
	public static LocalDate UtilDateToLocalDate(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDate retVal = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return retVal;
	}

	public static Date LocalTimeToUtilTime(LocalTime lt) {
		Instant instant = lt.atDate(LocalDate.of(2015, 11, 10)).atZone(ZoneId.systemDefault()).toInstant();
		Date retVal = Date.from(instant);
		return retVal;
	}

	public static Date LocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Read file and save text to String object.
	 *
	 * @param fileName
	 *            the file name
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static String getAge(Date birthDate) {
		// String retVal="";
		int years = 0;
		int months = 0;
		@SuppressWarnings("unused")
		int days = 0;
		// create calendar object for birth day
		Calendar birthDay = Calendar.getInstance();
		birthDay.setTimeInMillis(birthDate.getTime());
		// create calendar object for current day
		long currentTime = System.currentTimeMillis();
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(currentTime);
		// Get difference between years
		years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		int currMonth = now.get(Calendar.MONTH) + 1;
		int birthMonth = birthDay.get(Calendar.MONTH) + 1;
		// Get difference between months
		months = currMonth - birthMonth;
		// if month difference is in negative then reduce years by one and
		// calculate the number of months.
		if (months < 0) {
			years--;
			months = 12 - birthMonth + currMonth;
			if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
				months--;
		} else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
			years--;
			months = 11;
		}
		// Calculate the days
		if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
			days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
		else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
			int today = now.get(Calendar.DAY_OF_MONTH);
			now.add(Calendar.MONTH, -1);
			days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
		} else {
			days = 0;
			if (months == 12) {
				years++;
				months = 0;
			}
		}

		return years + "y - " + months + "m";
	}

	/**
	 * Calculate total bill. This method will calculate total bill of invoice
	 * after add vat mpount in subtotal.
	 * 
	 * @param subTotal
	 *            the sub total
	 * @param vatPercentage
	 *            the vat percentage
	 * @return the long
	 */
	public static Float calculateTotalBillWithVAT(Float subTotal, int vatPercentage) {
		Float vatAmount = vatAmount(subTotal, vatPercentage);
		Float totalAmount = vatAmount + subTotal;
		return totalAmount;
	}

	/**
	 * Vat amount.
	 *
	 * @param subTotal
	 *            the sub total
	 * @param vatPercentage
	 *            the vat percentage
	 * @return the float
	 */
	public static Float vatAmount(Float subTotal, int vatPercentage) {
		Float vatAmount = subTotal * vatPercentage / 100;
		return vatAmount;

	}

	/**
	 * We are passing float value and convert it upto 2 decimal places in form
	 * of String value. Gets the string value upto 2 decimal.
	 *
	 * @param value
	 *            the value
	 * @return the string value upto 2 decimal
	 */
	public static String getValueUpto2Decimal(Float value) {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		return df.format(value);
	}
	

}
