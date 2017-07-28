package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

public class EnumEmbryo {

	public enum EmbryoType{
		EASY(1, "EASY"), DIFF(2, "DIFFICULT"), BLOOD(3, "BLOOD");

		private int key;
		private String value;

		private static Map<Integer,EmbryoType> map = new HashMap<>();

		EmbryoType(int key, String value){
			this.key = key;
			this.value= value;
		}

		public int getKey(){
			return key;
		}

		public String getValue(){
			return value;
		}

		@Override
		public String toString(){
			return value;
		}

		static {
			for (EmbryoType element: EmbryoType.values()){
				map.put(element.getKey(), element);
			}
		}

		public static EmbryoType getEnumByKey(int embryoType) {

			if(map.containsKey(embryoType)){
				return map.get(embryoType);
			}

			return EASY;
	    }



	}

	public enum Injection{
		ICSI(1, "ICSI"), IVF(2, "IVF"), IMSI(3, "IMSI");

		private int key;
		private String value;

		private static Map<Integer,Injection> map = new HashMap<>();

		Injection(int key, String value){
			this.key = key;
			this.value= value;
		}

		public int getKey(){
			return key;
		}

		public String getValue(){
			return value;
		}

		@Override
		public String toString(){
			return value;
		}

		static {
			for (Injection element: Injection.values()){
				map.put(element.getKey(), element);
			}
		}

		public static Injection getEnumByKey(int injection) {

			if(map.containsKey(injection)){
				return map.get(injection);
			}

			return ICSI;
	    }
	}
}
