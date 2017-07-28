/**
 *
 */
package org.cf.card.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Nikhil Mahajan
 *
 */
public class EnumDayTable {

	public enum Progress {

		PB(1, "PB", "flow"), MATURITY(2, "MATURITY", "flow"), PN(3, "PN"), ZN(4, "ZN"), CELL(5, "CELL", "flow"), FR(6,
				"FR"), EVEN(7, "EVEN"), NU(8, "NU"), ICM(9, "ICM"), TCM(10, "TCM"), DESTINY(11, "DESTINY");

		private String name;

		private int key;

		private String layout;

		Progress(int key, String name, String layout) {
			this.key = key;
			this.name = name;
			this.layout = layout;
		}

		Progress(int key, String name) {
			this.key = key;
			this.name = name;
			this.layout = "vbox";

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getLayout() {
			return layout;
		}

		public void setLayout(String layout) {
			this.layout = layout;
		}

	}

	public enum Option {

		_0(0,"0"),_1(1, "1"), _2(2, "2"), _3(3, "3"), _4(4, "4"), _5(5, "5"), _6(6, "6"), _7(7, "7"), _8(8, "8"), _9(9,
				"9"), _10(10, "10"), _11(11, "11"), _12(12, "12"), C1(13, "C1"), C2(14, "C2"), BL1(15, "BL1"), BL2(16,
						"BL2"), BL3(17, "BL3"), BL4(18, "BL4"), BL5(19, "BL5"), BL6(20, "BL6"), BL7(21, "BL7"), DEG(22,
								"DEG"), A(23, "A"), B(24, "B"), C(25, "C"), D(26, "D"), MI(27, "MI"), MII(28,
										"MII"), GV(29, "GV"), MEGA(30, "MEGA"), DE(31, "DE"), FR(32, "FR"), _2FR(33,
												"2FR"), GRE_2FR(34, ">2FR"), GRE_8(35, ">8"), CRYO(36, "CRYO"), DON(37,
														"DON"), RES(38, "RES"), ET(39, "ET"), DISC(40, "DISC"), NEC(41,"NEC"),
															LOST(42,"LOST");

		private String name;

		private int key;

		private static Map<Integer,Option> map = new HashMap<>();

		Option(int key, String name) {
			this.key = key;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		static {
			for (Option element: Option.values()){
				map.put(element.getKey(), element);
			}
		}

		public static Option getOptionByKey(int key) {

			if(map.containsKey(key)){
				return map.get(key);
			}

			return null;
	    }

	}

	public static Map<Progress, Set<Option>> getRange(int day) {
		Map<Progress, Set<Option>> retVal = null;
		if (day == 0) {
			retVal = getDay0();
		} else if (day == 1) {
			retVal = getDay1();
		} else if (day == 2) {
			retVal =getDay2();
		}else if (day == 3) {
			retVal =getDay3();
		}else if (day == 4) {
			retVal=getDay4();
		}else if (day == -1) {
			retVal=getDayMinus1();
		}else {
			retVal = getDayX();
		}

		return retVal;
	}

	private static Map<Progress, Set<Option>> getDay0() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();
		// Setting values for PB
		Set<Option> pbValues = new LinkedHashSet<>();
		pbValues.add(Option._1);
		pbValues.add(Option._2);
		pbValues.add(Option.FR);
		pbValues.add(Option._2FR);
		pbValues.add(Option.GRE_2FR);
		retVal.put(Progress.PB, pbValues);

		// Setting values for PB
		Set<Option> maturityValues = new LinkedHashSet<>();
		maturityValues.add(Option.MI);
		maturityValues.add(Option.MII);
		maturityValues.add(Option.GV);
		maturityValues.add(Option.MEGA);
		maturityValues.add(Option.DE);
		retVal.put(Progress.MATURITY, maturityValues);

		// Setting values for PB

		retVal.put(Progress.DESTINY, getDestiny());

		return retVal;
	}

	private static Map<Progress, Set<Option>> getDay1() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();
		// Setting values for PB
		Set<Option> pbValues = new LinkedHashSet<>();
		pbValues=getOneToFour();
		pbValues.add(Option.FR);
		pbValues.add(Option._2FR);
		pbValues.add(Option.GRE_2FR);
		retVal.put(Progress.PB, pbValues);

		// Setting values for PN
		retVal.put(Progress.PN, getOneToFourAndNECAndLOST());

		// Setting values for ZN
		retVal.put(Progress.ZN, getOneToFour());

		retVal.put(Progress.DESTINY, getDestiny());

		return retVal;
	}

	private static Map<Progress, Set<Option>> getDay2() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();

		// Setting values for PN
		retVal.put(Progress.PN, getOneToFourAndNECAndLOST());

		// Setting values for CELL
		Set<Option> cellValues = getTwoToEight();
		cellValues.add(Option.GRE_8);
		cellValues.add(Option.DEG);
		retVal.put(Progress.CELL, cellValues);

		// Setting values for FR
		retVal.put(Progress.FR, getZeroToFour());
		// Setting values for EVEN
		retVal.put(Progress.EVEN, getZeroToFour());
		// Setting values for NU
		retVal.put(Progress.NU, getOneToFour());
		// Setting values for DESTINY
		retVal.put(Progress.DESTINY, getDestiny());
		return retVal;
	}
	private static Map<Progress, Set<Option>> getDay3() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();

		// Setting values for PN
		retVal.put(Progress.PN, getOneToFourAndNECAndLOST());

		// Setting values for CELL
		Set<Option> cellValues =  getTwoToEight();
		cellValues.add(Option._9);
		cellValues.add(Option._10);
		cellValues.add(Option._11);
		cellValues.add(Option._12);
		cellValues.add(Option.C1);
		cellValues.add(Option.C2);
		cellValues.add(Option.DEG);
		retVal.put(Progress.CELL, cellValues);

		// Setting values for FR
		retVal.put(Progress.FR, getZeroToFour());
		// Setting values for EVEN
		retVal.put(Progress.EVEN, getZeroToFour());
		// Setting values for NU
		retVal.put(Progress.NU, getOneToFour());
		// Setting values for DESTINY
		retVal.put(Progress.DESTINY, getDestiny());
		return retVal;
	}
	private static Map<Progress, Set<Option>> getDay4() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();

		// Setting values for PN
		retVal.put(Progress.PN, getOneToFourAndNECAndLOST());

		// Setting values for CELL
		Set<Option> cellValues = getTwoToEight();
		//retVal.put(Progress.CELL, getTwoToEight());
		cellValues.add(Option._9);
		cellValues.add(Option._10);
		cellValues.add(Option._11);
		cellValues.add(Option._12);
		cellValues.add(Option.C1);
		cellValues.add(Option.C2);
		cellValues.add(Option.BL1);
		cellValues.add(Option.BL2);
		cellValues.add(Option.BL3);
		cellValues.add(Option.DEG);
		retVal.put(Progress.CELL, cellValues);

		// Setting values for FR
		retVal.put(Progress.FR, getZeroToFour());
		// Setting values for EVEN
		retVal.put(Progress.EVEN, getZeroToFour());
		// Setting values for NU
		retVal.put(Progress.NU, getOneToFour());
		// Setting values for ICM
		retVal.put(Progress.ICM, getABCD());
		// Setting values for TC
		retVal.put(Progress.TCM, getABCD());
		// Setting values for DESTINY
		retVal.put(Progress.DESTINY, getDestiny());
		return retVal;
	}


	private static Map<Progress, Set<Option>> getDayX() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();

		// Setting values for PN
		retVal.put(Progress.PN, getOneToFourAndNECAndLOST());

		// Setting values for CELL
		Set<Option> cellValues = new LinkedHashSet<>();
		cellValues.add(Option._1);
		cellValues.add(Option._2);
		cellValues.add(Option._3);
		cellValues.add(Option._4);
		cellValues.add(Option._5);
		cellValues.add(Option._6);
		cellValues.add(Option._7);
		cellValues.add(Option._8);
		cellValues.add(Option._9);
		cellValues.add(Option._10);
		cellValues.add(Option._11);
		cellValues.add(Option._12);
		cellValues.add(Option.C1);
		cellValues.add(Option.C2);
		cellValues.add(Option.BL1);
		cellValues.add(Option.BL2);
		cellValues.add(Option.BL3);
		cellValues.add(Option.BL4);
		cellValues.add(Option.BL5);
		cellValues.add(Option.BL6);
		cellValues.add(Option.BL7);
		cellValues.add(Option.DEG);
		retVal.put(Progress.CELL, cellValues);

		// Setting values for FR
		retVal.put(Progress.FR, getZeroToFour());

		// Setting values for FR
		retVal.put(Progress.EVEN, getZeroToFour());

		// Setting values for FR
		retVal.put(Progress.NU, getOneToFour());

		// Setting values for FR
		retVal.put(Progress.ICM, getABCD());

		// Setting values for FR
		retVal.put(Progress.TCM, getABCD());
		
		// Setting values for DESTINY
		retVal.put(Progress.DESTINY, getDestiny());

		return retVal;
	}
	
	private static Map<Progress, Set<Option>> getDayMinus1() {
		Map<Progress, Set<Option>> retVal = new LinkedHashMap<>();

		// Setting values for PN
		retVal.put(Progress.PN, getOneToFourAndNECAndLOST());

		// Setting values for CELL
		Set<Option> cellValues = new LinkedHashSet<>();
		cellValues.add(Option._1);
		cellValues.add(Option._2);
		cellValues.add(Option._3);
		cellValues.add(Option._4);
		cellValues.add(Option._5);
		cellValues.add(Option._6);
		cellValues.add(Option._7);
		cellValues.add(Option._8);
		cellValues.add(Option._9);
		cellValues.add(Option._10);
		cellValues.add(Option._11);
		cellValues.add(Option._12);
		cellValues.add(Option.C1);
		cellValues.add(Option.C2);
		cellValues.add(Option.BL1);
		cellValues.add(Option.BL2);
		cellValues.add(Option.BL3);
		cellValues.add(Option.BL4);
		cellValues.add(Option.BL5);
		cellValues.add(Option.BL6);
		cellValues.add(Option.BL7);
		cellValues.add(Option.DEG);
		retVal.put(Progress.CELL, cellValues);

		// Setting values for FR
		retVal.put(Progress.FR, getZeroToFour());

		// Setting values for FR
		retVal.put(Progress.EVEN, getZeroToFour());

		// Setting values for FR
		retVal.put(Progress.NU, getOneToFour());

		// Setting values for FR
		retVal.put(Progress.ICM, getABCD());

		// Setting values for FR
		retVal.put(Progress.TCM, getABCD());
		
		// Setting values for DESTINY
		retVal.put(Progress.DESTINY, getDestinyForMinusOneDay());

		return retVal;
	}
	

	public static Set<Option> getDestinyForMinusOneDay() {
		Set<Option> destiny = new LinkedHashSet<>();
//		destiny.add(Option.NONE);
		//destiny.add(Option.CRYO);
		destiny.add(Option.DON);
		destiny.add(Option.RES);
		destiny.add(Option.ET);
		destiny.add(Option.DISC);
		return destiny;
	}

	public static Set<Option> getDestiny() {
		Set<Option> destiny = new LinkedHashSet<>();
//		destiny.add(Option.NONE);
		destiny.add(Option.CRYO);
		destiny.add(Option.DON);
		destiny.add(Option.RES);
		destiny.add(Option.ET);
		destiny.add(Option.DISC);
		return destiny;
	}

	private static Set<Option> getZeroToFour() {
		Set<Option> retVal = new LinkedHashSet<>();
		retVal.add(Option._0);
		retVal.add(Option._1);
		retVal.add(Option._2);
		retVal.add(Option._3);
		retVal.add(Option._4);
		return retVal;
	}
	private static Set<Option> getOneToFour() {
		Set<Option> retVal = new LinkedHashSet<>();
		retVal.add(Option._1);
		retVal.add(Option._2);
		retVal.add(Option._3);
		retVal.add(Option._4);
		return retVal;
	}
	
	private static Set<Option> getOneToFourAndNECAndLOST() {
		Set<Option> retVal = new LinkedHashSet<>();
		retVal.add(Option._1);
		retVal.add(Option._2);
		retVal.add(Option._3);
		retVal.add(Option._4);
		retVal.add(Option.NEC);
		retVal.add(Option.LOST);	
		return retVal;
	}

	private static Set<Option> getABCD() {
		Set<Option> retVal = new LinkedHashSet<>();
		retVal.add(Option.A);
		retVal.add(Option.B);
		retVal.add(Option.C);
		retVal.add(Option.D);
		return retVal;
	}

	private static Set<Option> getTwoToEight() {
		Set<Option> retVal = new LinkedHashSet<>();
		retVal.add(Option._2);
		retVal.add(Option._3);
		retVal.add(Option._4);
		retVal.add(Option._5);
		retVal.add(Option._5);
		retVal.add(Option._6);
		retVal.add(Option._7);
		retVal.add(Option._8);
		return retVal;
	}
}
