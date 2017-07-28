package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;


public class EnumSemen {

	public enum UseSemenType{
		SA(1, "SA"), SAPrep(2, "SA + Prep"), IUI(3, "IUI"), FSP(4, "TESA"), FSD(5, "TESE"), KSM(6, "PESA"), CRYO(7, "CRYO"), MESA(8, "MESA"), IVF(9, "IVF"), ICSI(10, "ICSI");

		private int key;
		private String value;
		

		private static Map<Integer,UseSemenType> map = new HashMap<>();

		UseSemenType(int key, String value){
			this.key = key;
			this.value = value;
		}

		public int getKey(){
			return key;
		}

		public String getStr(){
			return value;
		}

		@Override
		public String toString(){
			return value;
		}
		
		static {
			for (UseSemenType element: UseSemenType.values()){
				map.put(element.getKey(), element);
			}
		}

		public static UseSemenType getEnumByKey(int use) {

			if(map.containsKey(use)){
				return map.get(use);
			}

			return SA;
	    }


	}
}
