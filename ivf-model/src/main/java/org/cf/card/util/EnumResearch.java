package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

public class EnumResearch {

	public enum Research {
		YES(1, "Research Yes"), NO(2, "Research No");

		private static Map<Integer, Research> map = new HashMap<>();

		private int key;
		private String value;

		private Research(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		static {

			for (Research research : Research.values()) {
				map.put(research.getKey(), research);
			}
		}

		public static Research getResearchByKey(int key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			return null;
		}

	}
}
