package org.cf.card.util;

public class Enumeration {

	public enum Group {
		BLOOD(1, "Blood Work"), MICROBIOLOGY(2, "Microbiology"), HORMONAL(3, "Hormonal Assay");
		private String strValue;

		private int key;

		Group(int key, String group) {
			this.key = key;
			this.strValue = group;
		}

		public int getKey() {
			return key;
		}

		public String getStrValue() {
			return strValue;
		}
	}

	public enum SemenType {
		RAW(1, "RAW"), PREPARED(2, "Prepared");

		private String str;
		private int key;

		SemenType(int key, String str) {
			this.key = key;
			this.str = str;
		}

		public int getKey() {
			return key;
		}

		public String getStr() {
			return str;
		}
	}

	public enum ScreenType {
		E(1, "Embryology"), A(2, "Andrology");

		private String str;
		private int key;

		ScreenType(int key, String str) {
			this.key = key;
			this.str = str;
		}

		public int getKey() {
			return key;
		}

		public String getStr() {
			return str;
		}
	}

	public enum RemarkType {
		ESP(1, "Embrology-Semen Preparation"), A(2, "Andrology"), NS(3, "Nurse Station"), DO(4, "Doctor Office");

		private String val;
		private int key;

		RemarkType(int key, String val) {
			this.key = key;
			this.val = val;
		}

		public int getKey() {
			return key;
		}

		public String getVal() {
			return val;
		}
	}

	public enum Gender {

		MALE(1, "Male"), FEMALE(2, "Female");

		private Integer key;
		private String name;

		private Gender(Integer key, String name) {
			this.key = key;
			this.name = name;
		}

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
