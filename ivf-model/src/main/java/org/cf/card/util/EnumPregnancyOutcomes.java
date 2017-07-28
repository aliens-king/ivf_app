/**
 * 
 */
package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author insonix
 *
 */
public class EnumPregnancyOutcomes {

	/**
	 * The Enum PregnancyOutcomesOption.
	 */
	public enum PregnancyOutcomesOption {
		BIOCHEMICAL(1, "BIOCHEMICAL"), CLINICAL(2, "CLINICAL"), LIVE_BIRTH(3, "LIVE BIRTH");

		private int key;
		private String value;

		private static Map<Integer, BiochemicalType> map = new HashMap<>();

		public int getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}

		static {
			for (BiochemicalType element : BiochemicalType.values()) {
				map.put(element.getKey(), element);
			}

		}

		PregnancyOutcomesOption(int key, String value) {
			this.key = key;
			this.value = value;
		}

	}

	/**
	 * The Enum BiochemicalType.
	 */
	public enum BiochemicalType {
		MINUS_HCG(1, "-hCG"), PLUS_HCG(2, "+hCG"), FALSE_PLUSE(3, "False+");

		private int key;
		private String value;

		private static Map<Integer, BiochemicalType> map = new HashMap<>();

		public int getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}

		private BiochemicalType(int key, String value) {
			this.key = key;
			this.value = value;
		}

		static {
			for (BiochemicalType element : BiochemicalType.values()) {
				map.put(element.getKey(), element);
			}

		}

		public static BiochemicalType getEnumByKey(int biochemicalType) {

			if (map.containsKey(biochemicalType)) {
				return map.get(biochemicalType);
			} else {
				return null;
			}
		}

	}

	/**
	 * The Enum ClinicalType.
	 */
	public enum ClinicalType {
		no_sac(1, "no sac"), _1_sac(2, "1 sac"), _2_sac(3, "2 sac"), _3_sac(4, "3 sac"), _4_sac(5,
				"4 sac"), GRE_4_sac(6, "> 4 sac");

		private int key;
		private String value;

		private static Map<Integer, ClinicalType> map = new HashMap<>();

		public int getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}

		ClinicalType(int key, String value) {

			this.key = key;
			this.value = value;

		}

		static {
			for (ClinicalType element : ClinicalType.values()) {
				map.put(element.getKey(), element);
			}

		}

		public static ClinicalType getEnumByKey(int clinicalType) {

			if (map.containsKey(clinicalType)) {
				return map.get(clinicalType);
			} else {
				return null;
			}
		}

	}

	/**
	 * The Enum LiveBirthType.
	 */
	public enum LiveBirthType {

		Still_Born(1, "Still Born"), Singleton(2, "Singleton"), Twin(3, "Twin"), Triplet(4, "Triplet"), Mono_Twin(5,
				"Mono-Twin"), Quadruplet(6, "Quadruplet");

		private int key;
		private String value;

		private static Map<Integer, LiveBirthType> map = new HashMap<>();

		LiveBirthType(int key, String value) {
			this.key = key;
			this.value = value;

		}

		public int getKey() {
			return key;
		}

		public String getVal() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}

		static {
			for (LiveBirthType element : LiveBirthType.values()) {
				map.put(element.getKey(), element);
			}

		}

		public static LiveBirthType getEnumByKey(int liveBirthType) {

			if (map.containsKey(liveBirthType)) {
				return map.get(liveBirthType);
			} else {
				return null;
			}
		}
	}

}
