package org.cf.card.ui.service;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dell on 3/29/2015.
 */
public class LicenseService {

	public static boolean checkKey(String companyName, String key) {

		String fullNameString = companyName;
		String serialNumberEncoded = "";
		try {
			serialNumberEncoded = calculateSecurityHash(fullNameString, "MD2")
					+ calculateSecurityHash(fullNameString, "MD5") + calculateSecurityHash(fullNameString, "SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String serialNumberCalc = "" + serialNumberEncoded.charAt(32) + serialNumberEncoded.charAt(76)
				+ serialNumberEncoded.charAt(100) + serialNumberEncoded.charAt(50) + "-" + serialNumberEncoded.charAt(2)
				+ serialNumberEncoded.charAt(91) + serialNumberEncoded.charAt(73) + serialNumberEncoded.charAt(72)
				+ serialNumberEncoded.charAt(98) + "-" + serialNumberEncoded.charAt(47) + serialNumberEncoded.charAt(65)
				+ serialNumberEncoded.charAt(18) + serialNumberEncoded.charAt(85) + "-" + serialNumberEncoded.charAt(27)
				+ serialNumberEncoded.charAt(53) + serialNumberEncoded.charAt(102) + serialNumberEncoded.charAt(15)
				+ serialNumberEncoded.charAt(99);

		String m = key.charAt(22) + "" + key.charAt(23);
		int month = 0;
		switch (m) {
		case "fb":
			month = 2;
			break;
		case "mr":
			month = 3;
			break;
		case "ar":
			month = 4;
			break;
		case "mi":
			month = 5;
			break;
		case "jn":
			month = 6;
			break;
		case "j;":
			month = 7;
			break;
		case "ag":
			month = 8;
			break;
		case "sp":
			month = 9;
			break;
		case "ot":
			month = 10;
			break;
		case "nv":
			month = 11;
			break;
		case "dc":
			month = 12;
			break;
		}

		String day = "";
		String year = "" + 20;

		if (key.charAt(26) != 'x') {
			day = day + key.charAt(24) + key.charAt(26);
		} else
			day = day + key.charAt(24);

		year = year + key.charAt(28) + key.charAt(30);
		Date today = new Date();

		if (key.substring(0, 21).equals(serialNumberCalc)) {

			Date licenseKeyExpiryDate;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, Integer.parseInt(day));
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			licenseKeyExpiryDate = cal.getTime();
			System.out.println(licenseKeyExpiryDate);
			System.out.println(today);
			if (today.before(licenseKeyExpiryDate))
				return true;
		}
		return false;

	}

	private static String calculateSecurityHash(String stringInput, String algorithmName)
			throws java.security.NoSuchAlgorithmException {
		String hexMessageEncode = "";
		byte[] buffer = stringInput.getBytes();
		java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(algorithmName);
		messageDigest.update(buffer);
		byte[] messageDigestBytes = messageDigest.digest();
		for (int index = 0; index < messageDigestBytes.length; index++) {
			int countEncode = messageDigestBytes[index] & 0xff;
			if (Integer.toHexString(countEncode).length() == 1)
				hexMessageEncode = hexMessageEncode + "0";
			hexMessageEncode = hexMessageEncode + Integer.toHexString(countEncode);
		}
		return hexMessageEncode;
	}
}
