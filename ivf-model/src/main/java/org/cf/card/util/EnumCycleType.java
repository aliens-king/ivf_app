package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

public class EnumCycleType {
	
	public enum CycleType{
		
		NORMAL(1, "Normal"), EGG_THAW(2, "Egg Thawing"), EMBRYO_THAW(3, "Embryo Thawing");
		
		private int key;
		private String value;
		
		private static Map<Integer, CycleType> map = new HashMap<>();
		
		private CycleType(int key, String value) {
			this.key = key;
			this.value = value;
		}


		public int getKey() {
			return key;
		}


		public String getValue() {
			return value;
		}
		
		
		static {
			for (CycleType element : CycleType.values()) {
				map.put(element.getKey(), element);
			}

		}

		public static CycleType getEnumByKey(int cycleType) {

			if (map.containsKey(cycleType)) {
				return map.get(cycleType);
			}

			return NORMAL;
		}
	}

}
