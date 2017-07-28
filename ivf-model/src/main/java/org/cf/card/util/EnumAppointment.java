package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

public class EnumAppointment {

	public enum AppointmentType {

		Consultation(1, "Consultation"), Standard_Investigation(2, "Standard Investigation"), Egg_Collection(3,
				"Egg Collection"), Frozen_Thawed_Embryo_Transfer(4, "Frozen Thawed Embryo Transfer"), Registration(5,
						"Registration"), Biochemical_Pregnancy_Test(6,
								"Biochemical Pregnancy Test"), Clinical_Pregnancy_Test_Scan(7,
										"Clinical Pregnancy Test/Scan"), Cyst_Draining(8, "Cyst Draining"), HCG_Scan(9,
												"HCG Scan"), Egg_Donation(10, "Egg Donation"), Surrogacy(11,
														"Surrogacy"), Semen_Donation(12,
																"Semen Donation"), Woman_Health(13,
																		"Woman Health"), Results_Discussion(14,
																				"Results Discussion"), Others(15,
																						"Others");

		private int key;
		private String val;

		private static Map<Integer, AppointmentType> map = new HashMap<>();

		AppointmentType(int key, String val) {
			this.key = key;
			this.val = val;
		}

		public int getKey() {
			return key;
		}

		public String getVal() {
			return val;
		}

		static {
			for (AppointmentType element : AppointmentType.values()) {
				map.put(element.getKey(), element);
			}
		}

		public static AppointmentType getEnumByKey(int key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			return Others;
		}

	}

	public enum AppointmentStatus {
		ARRIVE(1, "A"), CANCEL(2, "C"), SEEN(3, "S");

		private int key;
		private String value;

		private static Map<Integer, AppointmentStatus> map = new HashMap<>();

		AppointmentStatus(int key, String value) {
			this.key = key;
			this.value =value;

		}

		/**
		 * @return the key
		 */
		public int getKey() {
			return key;
		}

		/**
		 * @return the val
		 */
		public String getVal() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}

		static {
			for (AppointmentStatus element : AppointmentStatus.values()) {
				map.put(element.getKey(), element);
			}
		}

		public static AppointmentStatus getEnumByKey(int key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			return null;
		}

	}

}
