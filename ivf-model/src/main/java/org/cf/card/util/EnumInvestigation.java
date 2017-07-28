package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

public class EnumInvestigation {

	public enum HormonalAssay {
		AMH("AMH", "AMH"), FSH("FSH", "FSH"), LH("LH", "LH"), ESTRADIOL("ESTRADIOL", "ESTRADIOL");

		private static Map<String, String> map = new HashMap<String, String>();
		private String key;
		private String value;

		HormonalAssay(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getVal() {
			return value;
		}

		static {
			for (HormonalAssay element : HormonalAssay.values()) {
				map.put(element.getKey(), element.value);
			}
		}

		public static String getEnumByKey(String key) {
			if (map.containsKey(key)) {
				return map.get(key);
			} else {
				return "";
			}
		}

	}

	public enum BloodWork {
		HBV("HBV", "HBV"), CMV("CMV", "CMV"), SYPHILIS("SYPHILIS", "SYPHILIS"), BGROUP("B.Group", "BGROUP"), HBC("HBC",
				"HBC"), HIVIANDII("HIV I & II", "HIVIANDII");

		private static Map<String, String> map = new HashMap<String, String>();
		private String key;
		private String value;

		BloodWork(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getVal() {
			return value;
		}

		static {
			for (BloodWork element : BloodWork.values()) {
				map.put(element.getKey(), element.value);
			}
		}

		public static String getEnumByKey(String key) {
			if (map.containsKey(key)) {
				return map.get(key);
			} else {
				return "none";
			}
		}

	}

	public enum MicroBiology {
		UREAFORMYCOPLASMA("Urea for Mycoplasma", "UreaforMycoplasma"), URINEFORCHLAMYDIA("Urine for Chlamydia",
				"UrineforChlamydia");
		private static Map<String, String> map = new HashMap<String, String>();
		private String key;
		private String value;

		MicroBiology(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getVal() {
			return value;
		}

		static {
			for (MicroBiology element : MicroBiology.values()) {
				map.put(element.getKey(), element.value);
			}
		}

		public static String getEnumByKey(String key) {
			if (map.containsKey(key)) {
				return map.get(key);
			} else {
				return "none";
			}
		}

	}
}
