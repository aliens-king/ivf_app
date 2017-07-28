
package org.cf.card.util;

import java.util.HashMap;
import java.util.Map;

public class EnumPermission {

	public enum Role {
		SUPER_USER(1, "SuperUser"), IT_PERSON(2, "IT Person"), ADMINISTRATOR(3, "Administrator"), EMBRYO_SENIOR(4, "Embryology Senior"), 
		EMBRYO_JUNIOR(5, "Embryology Junior"), EMBRYO_TRAINEE(6, "Embryology Trainee"), ANDROLOGY(7, "Andrology"), NURSE(8, "Nurse"),
		DOCTOR(9, "Doctor"), LAB_MANAGER(10, "Lab Manager"), SENIOR_ACCOUNT_MANAGER(11, "Senior Account Manager"),
		JUNIOR_ACCOUNT_MANAGER(12, "Junior Account Manager"),TRAINEE_ACCOUNT_MANAGER(13,"Trainee Account Manager"),
		BILLING_OFFICER(14, "Billing Officer"),CLIENT_LIASON(15, "Client Liason") ;
		

		private static Map<Integer, Role> map = new HashMap<>();

		private String value;
		private int key;

		private Role(int key, String value) {
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
			for (Role element : Role.values()) {
				map.put(element.getKey(), element);
			}
		}

		public static Role getRoleByKey(int key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			return null;
		}

	}

	public enum Access {
		NONE(1, "None"), READ(2, "Read"), WRITE(3, "Write");

		private String value;
		private int key;
		private static Map<Integer, Access> map = new HashMap<>();

		private Access(int key, String value) {
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
			for (Access element : Access.values()) {
				map.put(element.getKey(), element);
			}
		}

		public static Access getRoleByKey(int key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			return null;
		}

	}

	public enum Module {
		
		DIRECTORY(1, "Directory"), REGISTER_PATIENT(2, "Register Patient"), PATIENT_LIST(3, "Patient List"),PATIENT_DETAILS(4, "Patient Details"), 
		SEARCH_PATIENTS(5, "Search Patients"), PRINT(6, "Print"), SEARCH_BY_NAME_CODE(7, "Search By Name Code"), APPOINTMENT_OVERVIEW(8, "Appointment Overview"), 
		SCHEDULE_APPOINTMENT(9, "Schedule Appointment"), REGISTER_USER(10, "Register User"), USER_DETAILS(11, "User Details"),
		USER_PRIVILLEGES(12,"User Privilleges"), EMBRYOLOGY_OVERVIEW(13, "Embryology Overview"), EMBRYO_TRANSFER(14,"Egg Evaluation"),
		EGG_THAWING(15,"Egg Thawing"), ANDROLOGY(16,"Andrology"), SEMEN_PREPRATION(17,"Semen Prepration"), ET_TABLE(18,"ET Table"),
		CRYOPRESERVATION_E(19,"CryoPreservation-E"), CRYOPRESERVATION_A(20,"CryoPreservation-A"), DOCTORS_OFFICE(21,"Doctors Office"), 
		NURSES_STATION(22,"Nurses Station"), MISMATCH(23,"Mismatch"), STANDARD_INVESTIGATION(24,"Standard Investigation"), STOCK_MAINTAINENCE(25,"Stock Maintainence"),
		BILLING_AND_INVOICE(26,"Billing and Invoice"), BILLING_SETUP(27,"Billing Setup"), RECENT_ARCHIVE(28,"Recent Archive"), RECENT_PAGE(	29,"Recent Page"), 
		CYCLES(30,"Cycles"), DAY_DIALOG(31,"Day Dialog"), APPOINTMENT_SCREEN(32,"Appointment Screen"), COMPANY_DETAILS(33,"Company Details"), 
		ARCHIVE(34,"My Archive"), PREGNANCY_OUTCOMES(35,"Pregnancy Outcomes"), ET_SCREEN(36,"Et Screen"), DELETE_APPOINTMENT(37,"Delete Appointment"),
		PAYMENT_DETAILS(38,"Payment Details"),INVOICES_DETAIL(39,"Invoices Detail"),REFUND_BILL_PER_COUPLE(40,"Refund Bill Per Couple"),REFUND_BILL_OVERALL(41,"Refund Bill Overall"),
		UNPAID_BILL_PER_COUPLE(42,"Unpaid Bill Per Couple"),UNPAID_BILL_OVERALL(43,"Unpaid Bill Overall"),REFUND_AND_INVOICE(44,"Refund and Invoice"),;

		private String value;
		private int key;
		private static Map<Integer, Module> map = new HashMap<>();

		Module(int key, String value) {
			this.key = key;
			this.value = value;
		}

		Module() {

		}

		public int getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		static {
			for (Module element : Module.values()) {
				map.put(element.getKey(), element);
			}
		}

		public static Module getModuleByKey(int key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			return null;
		}

	}

	/**
	 * Gets the access of a module by logged in user role.
	 *
	 * @param roleKey
	 *            the role key
	 * @param moduleKey
	 *            the module key
	 * @return the access
	 */

	private static Access getAccess(int roleKey, int moduleKey) {

		Module module = Module.getModuleByKey(moduleKey);
		Access access = Access.NONE;

		if (roleKey == Role.ADMINISTRATOR.key) {
			access = adminMap.get(module);
		} else if (roleKey == Role.SUPER_USER.key) {
			access = superUserMap.get(module);
		} else if (roleKey == Role.IT_PERSON.key) {
			access = itPersonMap.get(module);
		} else if (roleKey == Role.EMBRYO_SENIOR.key) {
			access = embryoSMap.get(module);
		} else if (roleKey == Role.EMBRYO_JUNIOR.key) {
			access = embryoJMap.get(module);
		} else if (roleKey == Role.EMBRYO_TRAINEE.key) {
			access = embryoTMap.get(module);
		} else if (roleKey == Role.ANDROLOGY.key) {
			access = androMap.get(module);
		} else if (roleKey == Role.NURSE.key) {
			access = nurseMap.get(module);
		} else if (roleKey == Role.DOCTOR.key) {
			access = doctorsMap.get(module);
		} else if (roleKey == Role.LAB_MANAGER.key) {
			access = labMgrMap.get(module);
		} else if (roleKey == Role.SENIOR_ACCOUNT_MANAGER.key) {
			access = seniorAccountManagerMap.get(module);
		}else if (roleKey == Role.JUNIOR_ACCOUNT_MANAGER.key) {
			access = juniorAccountManagerMap.get(module);
		}else if (roleKey == Role.TRAINEE_ACCOUNT_MANAGER.key) {
			access = traineeAccountManagerMap.get(module);
		}else if (roleKey == Role.BILLING_OFFICER.key) {
			access = billingOfficerMap.get(module);
		}else if (roleKey == Role.CLIENT_LIASON.key) {
			access = clientLiasonMap.get(module);
		}
		return access;
	}

	// declaring access maps for each role
	private static Map<Module, Access> adminMap = new HashMap<>();
	private static Map<Module, Access> superUserMap = new HashMap<>();
	private static Map<Module, Access> itPersonMap = new HashMap<>();
	private static Map<Module, Access> embryoSMap = new HashMap<>();
	private static Map<Module, Access> embryoJMap = new HashMap<>();
	private static Map<Module, Access> embryoTMap = new HashMap<>();
	private static Map<Module, Access> androMap = new HashMap<>();
	private static Map<Module, Access> nurseMap = new HashMap<>();
	private static Map<Module, Access> doctorsMap = new HashMap<>();
	private static Map<Module, Access> labMgrMap = new HashMap<>();
	
	private static Map<Module, Access> seniorAccountManagerMap = new HashMap<>();
	private static Map<Module, Access> juniorAccountManagerMap = new HashMap<>();
	private static Map<Module, Access> traineeAccountManagerMap = new HashMap<>();
	private static Map<Module, Access> billingOfficerMap = new HashMap<>();
	private static Map<Module, Access> clientLiasonMap = new HashMap<>();
	
	// TODO: declare maps for other roles here

	// Adding value to access map for Super User
	static {
		superUserMap.put(Module.DIRECTORY, Access.WRITE);
		superUserMap.put(Module.REGISTER_PATIENT, Access.WRITE);
		superUserMap.put(Module.PATIENT_LIST, Access.WRITE);
		superUserMap.put(Module.PATIENT_DETAILS, Access.WRITE);
		superUserMap.put(Module.SEARCH_PATIENTS, Access.WRITE);
		superUserMap.put(Module.PRINT, Access.WRITE);
		superUserMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		superUserMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		superUserMap.put(Module.SCHEDULE_APPOINTMENT, Access.WRITE);
		superUserMap.put(Module.REGISTER_USER, Access.WRITE);
		superUserMap.put(Module.USER_DETAILS, Access.WRITE);
		superUserMap.put(Module.USER_PRIVILLEGES, Access.WRITE);
		superUserMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.WRITE);
		superUserMap.put(Module.EMBRYO_TRANSFER, Access.WRITE);
		superUserMap.put(Module.EGG_THAWING, Access.WRITE);
		superUserMap.put(Module.ANDROLOGY, Access.WRITE);
		superUserMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		superUserMap.put(Module.ET_TABLE, Access.WRITE);
		superUserMap.put(Module.CRYOPRESERVATION_E, Access.WRITE);
		superUserMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		superUserMap.put(Module.DOCTORS_OFFICE, Access.WRITE);
		superUserMap.put(Module.NURSES_STATION, Access.WRITE);
		superUserMap.put(Module.MISMATCH, Access.WRITE);
		superUserMap.put(Module.STANDARD_INVESTIGATION, Access.WRITE);
		superUserMap.put(Module.STOCK_MAINTAINENCE, Access.WRITE);
		superUserMap.put(Module.BILLING_AND_INVOICE, Access.WRITE);
		superUserMap.put(Module.BILLING_SETUP, Access.WRITE);
		superUserMap.put(Module.RECENT_ARCHIVE, Access.WRITE);
		superUserMap.put(Module.RECENT_PAGE, Access.WRITE);

		superUserMap.put(Module.PREGNANCY_OUTCOMES, Access.WRITE);
		superUserMap.put(Module.ARCHIVE, Access.WRITE);
		superUserMap.put(Module.CYCLES, Access.WRITE);
		superUserMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		superUserMap.put(Module.DAY_DIALOG, Access.WRITE);
		superUserMap.put(Module.COMPANY_DETAILS, Access.WRITE);
		superUserMap.put(Module.ET_SCREEN, Access.WRITE);
		superUserMap.put(Module.DELETE_APPOINTMENT, Access.WRITE);
		
		superUserMap.put(Module.PAYMENT_DETAILS, Access.WRITE);
		superUserMap.put(Module.INVOICES_DETAIL, Access.WRITE);
		superUserMap.put(Module.REFUND_AND_INVOICE, Access.WRITE);
		superUserMap.put(Module.REFUND_BILL_PER_COUPLE, Access.WRITE);
		superUserMap.put(Module.REFUND_BILL_OVERALL, Access.WRITE);
		superUserMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.WRITE);
		superUserMap.put(Module.UNPAID_BILL_OVERALL, Access.WRITE);
		

	}

	// Adding value to access map for Admin
	static {
		adminMap.put(Module.DIRECTORY, Access.READ);
		adminMap.put(Module.REGISTER_PATIENT, Access.NONE);
		adminMap.put(Module.PATIENT_LIST, Access.READ);
		adminMap.put(Module.PATIENT_DETAILS, Access.READ);
		adminMap.put(Module.SEARCH_PATIENTS, Access.READ);
		adminMap.put(Module.PRINT, Access.WRITE);
		adminMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		adminMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		adminMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		adminMap.put(Module.REGISTER_USER, Access.NONE);
		adminMap.put(Module.USER_DETAILS, Access.READ);
		adminMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		adminMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.READ);
		adminMap.put(Module.EMBRYO_TRANSFER, Access.READ);
		adminMap.put(Module.EGG_THAWING, Access.READ);
		adminMap.put(Module.ANDROLOGY, Access.READ);
		adminMap.put(Module.SEMEN_PREPRATION, Access.READ);
		adminMap.put(Module.CRYOPRESERVATION_E, Access.READ);
		adminMap.put(Module.CRYOPRESERVATION_A, Access.READ);
		adminMap.put(Module.ET_TABLE, Access.READ);
		adminMap.put(Module.DOCTORS_OFFICE, Access.READ);
		adminMap.put(Module.NURSES_STATION, Access.READ);
		adminMap.put(Module.MISMATCH, Access.READ);
		adminMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		adminMap.put(Module.STOCK_MAINTAINENCE, Access.WRITE);
		adminMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		adminMap.put(Module.BILLING_SETUP, Access.NONE);
		adminMap.put(Module.RECENT_ARCHIVE, Access.READ);
		adminMap.put(Module.RECENT_PAGE, Access.READ);

		adminMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		adminMap.put(Module.ARCHIVE, Access.READ);
		adminMap.put(Module.CYCLES, Access.NONE);
		adminMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		adminMap.put(Module.DAY_DIALOG, Access.READ);
		adminMap.put(Module.COMPANY_DETAILS, Access.READ);
		adminMap.put(Module.ET_SCREEN, Access.READ);
		adminMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		adminMap.put(Module.PAYMENT_DETAILS, Access.READ);
		adminMap.put(Module.INVOICES_DETAIL, Access.READ);
		adminMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		adminMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		adminMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		adminMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		adminMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}

	// TODO: create static block to add values for other roles
	// adding values to access map for IT Person
	static {
		itPersonMap.put(Module.DIRECTORY, Access.READ);
		itPersonMap.put(Module.REGISTER_PATIENT, Access.WRITE);
		itPersonMap.put(Module.PATIENT_LIST, Access.READ);
		itPersonMap.put(Module.PATIENT_DETAILS, Access.WRITE);
		itPersonMap.put(Module.SEARCH_PATIENTS, Access.READ);
		itPersonMap.put(Module.PRINT, Access.WRITE);
		itPersonMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		itPersonMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		itPersonMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		itPersonMap.put(Module.REGISTER_USER, Access.WRITE);
		itPersonMap.put(Module.USER_DETAILS, Access.WRITE);
		itPersonMap.put(Module.USER_PRIVILLEGES, Access.WRITE);
		itPersonMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.WRITE);
		itPersonMap.put(Module.EMBRYO_TRANSFER, Access.WRITE);
		itPersonMap.put(Module.EGG_THAWING, Access.WRITE);
		itPersonMap.put(Module.ANDROLOGY, Access.WRITE);
		itPersonMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		itPersonMap.put(Module.CRYOPRESERVATION_E, Access.WRITE);
		itPersonMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		itPersonMap.put(Module.ET_TABLE, Access.WRITE);
		itPersonMap.put(Module.DOCTORS_OFFICE, Access.READ);
		itPersonMap.put(Module.NURSES_STATION, Access.READ);
		itPersonMap.put(Module.MISMATCH, Access.WRITE);
		itPersonMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		itPersonMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		itPersonMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		itPersonMap.put(Module.BILLING_SETUP, Access.NONE);
		itPersonMap.put(Module.RECENT_ARCHIVE, Access.WRITE);
		itPersonMap.put(Module.RECENT_PAGE, Access.WRITE);

		itPersonMap.put(Module.PREGNANCY_OUTCOMES, Access.WRITE);
		itPersonMap.put(Module.ARCHIVE, Access.WRITE);
		itPersonMap.put(Module.CYCLES, Access.READ);
		itPersonMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		itPersonMap.put(Module.DAY_DIALOG, Access.WRITE);
		itPersonMap.put(Module.COMPANY_DETAILS, Access.READ);
		itPersonMap.put(Module.ET_SCREEN, Access.WRITE);
		itPersonMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		itPersonMap.put(Module.PAYMENT_DETAILS, Access.READ);
		itPersonMap.put(Module.INVOICES_DETAIL, Access.READ);
		itPersonMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		itPersonMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		itPersonMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		itPersonMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		itPersonMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		embryoSMap.put(Module.DIRECTORY, Access.READ);
		embryoSMap.put(Module.REGISTER_PATIENT, Access.NONE);
		embryoSMap.put(Module.PATIENT_LIST, Access.READ);
		embryoSMap.put(Module.PATIENT_DETAILS, Access.READ);
		embryoSMap.put(Module.SEARCH_PATIENTS, Access.READ);
		embryoSMap.put(Module.PRINT, Access.WRITE);
		embryoSMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		embryoSMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		embryoSMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		embryoSMap.put(Module.REGISTER_USER, Access.NONE);
		embryoSMap.put(Module.USER_DETAILS, Access.READ);
		embryoSMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		embryoSMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.WRITE);
		embryoSMap.put(Module.EMBRYO_TRANSFER, Access.WRITE);
		embryoSMap.put(Module.EGG_THAWING, Access.WRITE);
		embryoSMap.put(Module.ANDROLOGY, Access.WRITE);
		embryoSMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		embryoSMap.put(Module.CRYOPRESERVATION_E, Access.WRITE);
		embryoSMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		embryoSMap.put(Module.ET_TABLE, Access.WRITE);
		embryoSMap.put(Module.DOCTORS_OFFICE, Access.READ);
		embryoSMap.put(Module.NURSES_STATION, Access.READ);
		embryoSMap.put(Module.MISMATCH, Access.READ);
		embryoSMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		embryoSMap.put(Module.STOCK_MAINTAINENCE, Access.WRITE);
		embryoSMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		embryoSMap.put(Module.BILLING_SETUP, Access.NONE);
		embryoSMap.put(Module.RECENT_ARCHIVE, Access.READ);
		embryoSMap.put(Module.RECENT_PAGE, Access.READ);

		embryoSMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		embryoSMap.put(Module.ARCHIVE, Access.WRITE);
		embryoSMap.put(Module.CYCLES, Access.WRITE);
		embryoSMap.put(Module.APPOINTMENT_SCREEN, Access.READ);
		embryoSMap.put(Module.DAY_DIALOG, Access.WRITE);
		embryoSMap.put(Module.COMPANY_DETAILS, Access.READ);
		embryoSMap.put(Module.ET_SCREEN, Access.WRITE);
		embryoSMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		embryoSMap.put(Module.PAYMENT_DETAILS, Access.READ);
		embryoSMap.put(Module.INVOICES_DETAIL, Access.READ);
		embryoSMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		embryoSMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		embryoSMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		embryoSMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		embryoSMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		embryoJMap.put(Module.DIRECTORY, Access.READ);
		embryoJMap.put(Module.REGISTER_PATIENT, Access.NONE);
		embryoJMap.put(Module.PATIENT_LIST, Access.READ);
		embryoJMap.put(Module.PATIENT_DETAILS, Access.READ);
		embryoJMap.put(Module.SEARCH_PATIENTS, Access.READ);
		embryoJMap.put(Module.PRINT, Access.WRITE);
		embryoJMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		embryoJMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		embryoJMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		embryoJMap.put(Module.REGISTER_USER, Access.NONE);
		embryoJMap.put(Module.USER_DETAILS, Access.READ);
		embryoJMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		embryoJMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.WRITE);
		embryoJMap.put(Module.EMBRYO_TRANSFER, Access.WRITE);
		embryoJMap.put(Module.EGG_THAWING, Access.WRITE);
		embryoJMap.put(Module.ANDROLOGY, Access.WRITE);
		embryoJMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		embryoJMap.put(Module.CRYOPRESERVATION_E, Access.WRITE);
		embryoJMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		embryoJMap.put(Module.ET_TABLE, Access.WRITE);
		embryoJMap.put(Module.DOCTORS_OFFICE, Access.READ);
		embryoJMap.put(Module.NURSES_STATION, Access.READ);
		embryoJMap.put(Module.MISMATCH, Access.READ);
		embryoJMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		embryoJMap.put(Module.STOCK_MAINTAINENCE, Access.WRITE);
		embryoJMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		embryoJMap.put(Module.BILLING_SETUP, Access.NONE);
		embryoJMap.put(Module.RECENT_ARCHIVE, Access.READ);
		embryoJMap.put(Module.RECENT_PAGE, Access.READ);

		embryoJMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		embryoJMap.put(Module.ARCHIVE, Access.WRITE);
		embryoJMap.put(Module.CYCLES, Access.READ);
		embryoJMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		embryoJMap.put(Module.DAY_DIALOG, Access.WRITE);
		embryoJMap.put(Module.COMPANY_DETAILS, Access.READ);
		embryoJMap.put(Module.ET_SCREEN, Access.WRITE);
		embryoJMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		embryoJMap.put(Module.PAYMENT_DETAILS, Access.READ);
		embryoJMap.put(Module.INVOICES_DETAIL, Access.READ);
		embryoJMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		embryoJMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		embryoJMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		embryoJMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		embryoJMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		embryoTMap.put(Module.DIRECTORY, Access.READ);
		embryoTMap.put(Module.REGISTER_PATIENT, Access.NONE);
		embryoTMap.put(Module.PATIENT_LIST, Access.READ);
		embryoTMap.put(Module.PATIENT_DETAILS, Access.READ);
		embryoTMap.put(Module.SEARCH_PATIENTS, Access.READ);
		embryoTMap.put(Module.PRINT, Access.WRITE);
		embryoTMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		embryoTMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		embryoTMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		embryoTMap.put(Module.REGISTER_USER, Access.NONE);
		embryoTMap.put(Module.USER_DETAILS, Access.READ);
		embryoTMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		embryoTMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.WRITE);
		embryoTMap.put(Module.EMBRYO_TRANSFER, Access.WRITE);
		embryoTMap.put(Module.EGG_THAWING, Access.WRITE);
		embryoTMap.put(Module.ANDROLOGY, Access.WRITE);
		embryoTMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		embryoTMap.put(Module.CRYOPRESERVATION_E, Access.READ);
		embryoTMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		embryoTMap.put(Module.ET_TABLE, Access.READ);
		embryoTMap.put(Module.DOCTORS_OFFICE, Access.READ);
		embryoTMap.put(Module.NURSES_STATION, Access.READ);
		embryoTMap.put(Module.MISMATCH, Access.READ);
		embryoTMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		embryoTMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		embryoTMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		embryoTMap.put(Module.BILLING_SETUP, Access.NONE);
		embryoTMap.put(Module.RECENT_ARCHIVE, Access.READ);
		embryoTMap.put(Module.RECENT_PAGE, Access.READ);

		embryoTMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		embryoTMap.put(Module.ARCHIVE, Access.WRITE);
		embryoTMap.put(Module.CYCLES, Access.READ);
		embryoTMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		embryoTMap.put(Module.DAY_DIALOG, Access.WRITE);
		embryoTMap.put(Module.COMPANY_DETAILS, Access.READ);
		embryoTMap.put(Module.ET_SCREEN, Access.READ);
		embryoTMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		embryoTMap.put(Module.PAYMENT_DETAILS, Access.READ);
		embryoTMap.put(Module.INVOICES_DETAIL, Access.READ);
		embryoTMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		embryoTMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		embryoTMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		embryoTMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		embryoTMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		androMap.put(Module.DIRECTORY, Access.READ);
		androMap.put(Module.REGISTER_PATIENT, Access.NONE);
		androMap.put(Module.PATIENT_LIST, Access.READ);
		androMap.put(Module.PATIENT_DETAILS, Access.READ);
		androMap.put(Module.SEARCH_PATIENTS, Access.READ);
		androMap.put(Module.PRINT, Access.WRITE);
		androMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		androMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		androMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		androMap.put(Module.REGISTER_USER, Access.NONE);
		androMap.put(Module.USER_DETAILS, Access.READ);
		androMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		androMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.READ);
		androMap.put(Module.EMBRYO_TRANSFER, Access.READ);
		androMap.put(Module.EGG_THAWING, Access.READ);
		androMap.put(Module.ANDROLOGY, Access.WRITE);
		androMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		androMap.put(Module.CRYOPRESERVATION_E, Access.READ);
		androMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		androMap.put(Module.ET_TABLE, Access.READ);
		androMap.put(Module.DOCTORS_OFFICE, Access.READ);
		androMap.put(Module.NURSES_STATION, Access.READ);
		androMap.put(Module.MISMATCH, Access.READ);
		androMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		androMap.put(Module.STOCK_MAINTAINENCE, Access.WRITE);
		androMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		androMap.put(Module.BILLING_SETUP, Access.NONE);
		androMap.put(Module.RECENT_ARCHIVE, Access.READ);
		androMap.put(Module.RECENT_PAGE, Access.READ);

		androMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		androMap.put(Module.ARCHIVE, Access.WRITE);
		androMap.put(Module.CYCLES, Access.READ);
		androMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		androMap.put(Module.DAY_DIALOG, Access.WRITE);
		androMap.put(Module.COMPANY_DETAILS, Access.READ);
		androMap.put(Module.ET_SCREEN, Access.READ);
		androMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		androMap.put(Module.PAYMENT_DETAILS, Access.READ);
		androMap.put(Module.INVOICES_DETAIL, Access.READ);
		androMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		androMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		androMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		androMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		androMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		nurseMap.put(Module.DIRECTORY, Access.READ);
		nurseMap.put(Module.REGISTER_PATIENT, Access.WRITE);
		nurseMap.put(Module.PATIENT_LIST, Access.WRITE);
		nurseMap.put(Module.PATIENT_DETAILS, Access.WRITE);
		nurseMap.put(Module.SEARCH_PATIENTS, Access.READ);
		nurseMap.put(Module.PRINT, Access.WRITE);
		nurseMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		nurseMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		nurseMap.put(Module.SCHEDULE_APPOINTMENT, Access.WRITE);
		nurseMap.put(Module.REGISTER_USER, Access.NONE);
		nurseMap.put(Module.USER_DETAILS, Access.READ);
		nurseMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		nurseMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.READ);
		nurseMap.put(Module.EMBRYO_TRANSFER, Access.READ);
		nurseMap.put(Module.EGG_THAWING, Access.READ);
		nurseMap.put(Module.ANDROLOGY, Access.READ);
		nurseMap.put(Module.SEMEN_PREPRATION, Access.READ);
		nurseMap.put(Module.CRYOPRESERVATION_E, Access.READ);
		nurseMap.put(Module.CRYOPRESERVATION_A, Access.READ);
		nurseMap.put(Module.ET_TABLE, Access.READ);
		nurseMap.put(Module.DOCTORS_OFFICE, Access.READ);
		nurseMap.put(Module.NURSES_STATION, Access.WRITE);
		nurseMap.put(Module.MISMATCH, Access.READ);
		nurseMap.put(Module.STANDARD_INVESTIGATION, Access.WRITE);
		nurseMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		nurseMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		nurseMap.put(Module.BILLING_SETUP, Access.NONE);
		nurseMap.put(Module.RECENT_ARCHIVE, Access.READ);
		nurseMap.put(Module.RECENT_PAGE, Access.READ);

		nurseMap.put(Module.PREGNANCY_OUTCOMES, Access.WRITE);
		nurseMap.put(Module.ARCHIVE, Access.WRITE);
		nurseMap.put(Module.CYCLES, Access.READ);
		nurseMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		nurseMap.put(Module.DAY_DIALOG, Access.WRITE);
		nurseMap.put(Module.COMPANY_DETAILS, Access.READ);
		nurseMap.put(Module.ET_SCREEN, Access.READ);
		nurseMap.put(Module.DELETE_APPOINTMENT, Access.WRITE);
		
		nurseMap.put(Module.PAYMENT_DETAILS, Access.READ);
		nurseMap.put(Module.INVOICES_DETAIL, Access.READ);
		nurseMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		nurseMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		nurseMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		nurseMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		nurseMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		doctorsMap.put(Module.DIRECTORY, Access.READ);
		doctorsMap.put(Module.REGISTER_PATIENT, Access.NONE);
		doctorsMap.put(Module.PATIENT_LIST, Access.READ);
		doctorsMap.put(Module.PATIENT_DETAILS, Access.READ);
		doctorsMap.put(Module.SEARCH_PATIENTS, Access.READ);
		doctorsMap.put(Module.PRINT, Access.WRITE);
		doctorsMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		doctorsMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		doctorsMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		doctorsMap.put(Module.REGISTER_USER, Access.NONE);
		doctorsMap.put(Module.USER_DETAILS, Access.READ);
		doctorsMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		doctorsMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.READ);
		doctorsMap.put(Module.EMBRYO_TRANSFER, Access.READ);
		doctorsMap.put(Module.EGG_THAWING, Access.READ);
		doctorsMap.put(Module.ANDROLOGY, Access.READ);
		doctorsMap.put(Module.SEMEN_PREPRATION, Access.READ);
		doctorsMap.put(Module.CRYOPRESERVATION_E, Access.READ);
		doctorsMap.put(Module.CRYOPRESERVATION_A, Access.READ);
		doctorsMap.put(Module.ET_TABLE, Access.READ);
		doctorsMap.put(Module.DOCTORS_OFFICE, Access.WRITE);
		doctorsMap.put(Module.NURSES_STATION, Access.READ);
		doctorsMap.put(Module.MISMATCH, Access.READ);
		doctorsMap.put(Module.STANDARD_INVESTIGATION, Access.WRITE);
		doctorsMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		doctorsMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		doctorsMap.put(Module.BILLING_SETUP, Access.NONE);
		doctorsMap.put(Module.RECENT_ARCHIVE, Access.READ);
		doctorsMap.put(Module.RECENT_PAGE, Access.READ);

		doctorsMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		doctorsMap.put(Module.ARCHIVE, Access.WRITE);
		doctorsMap.put(Module.CYCLES, Access.READ);
		doctorsMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		doctorsMap.put(Module.DAY_DIALOG, Access.WRITE);
		doctorsMap.put(Module.COMPANY_DETAILS, Access.READ);
		doctorsMap.put(Module.ET_SCREEN, Access.READ);
		doctorsMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		doctorsMap.put(Module.PAYMENT_DETAILS, Access.READ);
		doctorsMap.put(Module.INVOICES_DETAIL, Access.READ);
		doctorsMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		doctorsMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		doctorsMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		doctorsMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		doctorsMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}

	static {
		labMgrMap.put(Module.DIRECTORY, Access.READ);
		labMgrMap.put(Module.REGISTER_PATIENT, Access.NONE);
		labMgrMap.put(Module.PATIENT_LIST, Access.READ);
		labMgrMap.put(Module.PATIENT_DETAILS, Access.READ);
		labMgrMap.put(Module.SEARCH_PATIENTS, Access.READ);
		labMgrMap.put(Module.PRINT, Access.WRITE);
		labMgrMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		labMgrMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		labMgrMap.put(Module.SCHEDULE_APPOINTMENT, Access.READ);
		labMgrMap.put(Module.REGISTER_USER, Access.NONE);
		labMgrMap.put(Module.USER_DETAILS, Access.READ);
		labMgrMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		labMgrMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.WRITE);
		labMgrMap.put(Module.EMBRYO_TRANSFER, Access.WRITE);
		labMgrMap.put(Module.EGG_THAWING, Access.WRITE);
		labMgrMap.put(Module.ANDROLOGY, Access.WRITE);
		labMgrMap.put(Module.SEMEN_PREPRATION, Access.WRITE);
		labMgrMap.put(Module.CRYOPRESERVATION_E, Access.WRITE);
		labMgrMap.put(Module.CRYOPRESERVATION_A, Access.WRITE);
		labMgrMap.put(Module.ET_TABLE, Access.WRITE);
		labMgrMap.put(Module.DOCTORS_OFFICE, Access.READ);
		labMgrMap.put(Module.NURSES_STATION, Access.READ);
		labMgrMap.put(Module.MISMATCH, Access.READ);
		labMgrMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		labMgrMap.put(Module.STOCK_MAINTAINENCE, Access.WRITE);
		labMgrMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		labMgrMap.put(Module.BILLING_SETUP, Access.NONE);
		labMgrMap.put(Module.RECENT_ARCHIVE, Access.READ);
		labMgrMap.put(Module.RECENT_PAGE, Access.READ);

		labMgrMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		labMgrMap.put(Module.ARCHIVE, Access.WRITE);
		labMgrMap.put(Module.CYCLES, Access.WRITE);
		labMgrMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		labMgrMap.put(Module.DAY_DIALOG, Access.WRITE);
		labMgrMap.put(Module.COMPANY_DETAILS, Access.READ);
		labMgrMap.put(Module.ET_SCREEN, Access.WRITE);
		labMgrMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		labMgrMap.put(Module.PAYMENT_DETAILS, Access.READ);
		labMgrMap.put(Module.INVOICES_DETAIL, Access.READ);
		labMgrMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		labMgrMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		labMgrMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		labMgrMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		labMgrMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);

	}

	static {
		seniorAccountManagerMap.put(Module.DIRECTORY, Access.READ);
		seniorAccountManagerMap.put(Module.REGISTER_PATIENT, Access.NONE);
		seniorAccountManagerMap.put(Module.PATIENT_LIST, Access.READ);
		seniorAccountManagerMap.put(Module.PATIENT_DETAILS, Access.NONE);
		seniorAccountManagerMap.put(Module.SEARCH_PATIENTS, Access.READ);
		seniorAccountManagerMap.put(Module.PRINT, Access.WRITE);
		seniorAccountManagerMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		seniorAccountManagerMap.put(Module.APPOINTMENT_OVERVIEW, Access.READ);
		seniorAccountManagerMap.put(Module.SCHEDULE_APPOINTMENT, Access.NONE);
		seniorAccountManagerMap.put(Module.REGISTER_USER, Access.NONE);
		seniorAccountManagerMap.put(Module.USER_DETAILS, Access.READ);
		seniorAccountManagerMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		seniorAccountManagerMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.NONE);
		seniorAccountManagerMap.put(Module.EMBRYO_TRANSFER, Access.NONE);
		seniorAccountManagerMap.put(Module.EGG_THAWING, Access.NONE);
		seniorAccountManagerMap.put(Module.ANDROLOGY, Access.NONE);
		seniorAccountManagerMap.put(Module.SEMEN_PREPRATION, Access.NONE);
		seniorAccountManagerMap.put(Module.CRYOPRESERVATION_E, Access.NONE);
		seniorAccountManagerMap.put(Module.CRYOPRESERVATION_A, Access.NONE);
		seniorAccountManagerMap.put(Module.ET_TABLE, Access.NONE);
		seniorAccountManagerMap.put(Module.DOCTORS_OFFICE, Access.NONE);
		seniorAccountManagerMap.put(Module.NURSES_STATION, Access.NONE);
		seniorAccountManagerMap.put(Module.MISMATCH, Access.NONE);
		seniorAccountManagerMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		seniorAccountManagerMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		seniorAccountManagerMap.put(Module.BILLING_AND_INVOICE, Access.WRITE);
		seniorAccountManagerMap.put(Module.BILLING_SETUP, Access.WRITE);
		seniorAccountManagerMap.put(Module.RECENT_ARCHIVE, Access.READ);
		seniorAccountManagerMap.put(Module.RECENT_PAGE, Access.READ);

		seniorAccountManagerMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		seniorAccountManagerMap.put(Module.ARCHIVE, Access.WRITE);
		seniorAccountManagerMap.put(Module.CYCLES, Access.READ);
		seniorAccountManagerMap.put(Module.APPOINTMENT_SCREEN, Access.READ);
		seniorAccountManagerMap.put(Module.DAY_DIALOG, Access.WRITE);
		seniorAccountManagerMap.put(Module.COMPANY_DETAILS, Access.READ);
		seniorAccountManagerMap.put(Module.ET_SCREEN, Access.NONE);
		seniorAccountManagerMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		seniorAccountManagerMap.put(Module.PAYMENT_DETAILS, Access.WRITE);
		seniorAccountManagerMap.put(Module.INVOICES_DETAIL, Access.WRITE);
		seniorAccountManagerMap.put(Module.REFUND_AND_INVOICE, Access.WRITE);
		seniorAccountManagerMap.put(Module.REFUND_BILL_PER_COUPLE, Access.WRITE);
		seniorAccountManagerMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		seniorAccountManagerMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.WRITE);
		seniorAccountManagerMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}
	
	static {
		juniorAccountManagerMap.put(Module.DIRECTORY, Access.READ);
		juniorAccountManagerMap.put(Module.REGISTER_PATIENT, Access.NONE);
		juniorAccountManagerMap.put(Module.PATIENT_LIST, Access.READ);
		juniorAccountManagerMap.put(Module.PATIENT_DETAILS, Access.NONE);
		juniorAccountManagerMap.put(Module.SEARCH_PATIENTS, Access.READ);
		juniorAccountManagerMap.put(Module.PRINT, Access.WRITE);
		juniorAccountManagerMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		juniorAccountManagerMap.put(Module.APPOINTMENT_OVERVIEW, Access.NONE);
		juniorAccountManagerMap.put(Module.SCHEDULE_APPOINTMENT, Access.NONE);
		juniorAccountManagerMap.put(Module.REGISTER_USER, Access.NONE);
		juniorAccountManagerMap.put(Module.USER_DETAILS, Access.READ);
		juniorAccountManagerMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		juniorAccountManagerMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.NONE);
		juniorAccountManagerMap.put(Module.EMBRYO_TRANSFER, Access.NONE);
		juniorAccountManagerMap.put(Module.EGG_THAWING, Access.NONE);
		juniorAccountManagerMap.put(Module.ANDROLOGY, Access.NONE);
		juniorAccountManagerMap.put(Module.SEMEN_PREPRATION, Access.NONE);
		juniorAccountManagerMap.put(Module.CRYOPRESERVATION_E, Access.NONE);
		juniorAccountManagerMap.put(Module.CRYOPRESERVATION_A, Access.NONE);
		juniorAccountManagerMap.put(Module.ET_TABLE, Access.NONE);
		juniorAccountManagerMap.put(Module.DOCTORS_OFFICE, Access.NONE);
		juniorAccountManagerMap.put(Module.NURSES_STATION, Access.NONE);
		juniorAccountManagerMap.put(Module.MISMATCH, Access.NONE);
		juniorAccountManagerMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		juniorAccountManagerMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		juniorAccountManagerMap.put(Module.BILLING_AND_INVOICE, Access.WRITE);
		juniorAccountManagerMap.put(Module.BILLING_SETUP, Access.READ);
		juniorAccountManagerMap.put(Module.RECENT_ARCHIVE, Access.READ);
		juniorAccountManagerMap.put(Module.RECENT_PAGE, Access.READ);

		juniorAccountManagerMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		juniorAccountManagerMap.put(Module.ARCHIVE, Access.WRITE);
		juniorAccountManagerMap.put(Module.CYCLES, Access.READ);
		juniorAccountManagerMap.put(Module.APPOINTMENT_SCREEN, Access.READ);
		juniorAccountManagerMap.put(Module.DAY_DIALOG, Access.WRITE);
		juniorAccountManagerMap.put(Module.COMPANY_DETAILS, Access.READ);
		juniorAccountManagerMap.put(Module.ET_SCREEN, Access.NONE);
		juniorAccountManagerMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		juniorAccountManagerMap.put(Module.PAYMENT_DETAILS, Access.WRITE);
		juniorAccountManagerMap.put(Module.INVOICES_DETAIL, Access.WRITE);
		juniorAccountManagerMap.put(Module.REFUND_AND_INVOICE, Access.WRITE);
		juniorAccountManagerMap.put(Module.REFUND_BILL_PER_COUPLE, Access.WRITE);
		juniorAccountManagerMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		juniorAccountManagerMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.WRITE);
		juniorAccountManagerMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}
	
	static {
		traineeAccountManagerMap.put(Module.DIRECTORY, Access.READ);
		traineeAccountManagerMap.put(Module.REGISTER_PATIENT, Access.NONE);
		traineeAccountManagerMap.put(Module.PATIENT_LIST, Access.READ);
		traineeAccountManagerMap.put(Module.PATIENT_DETAILS, Access.NONE);
		traineeAccountManagerMap.put(Module.SEARCH_PATIENTS, Access.READ);
		traineeAccountManagerMap.put(Module.PRINT, Access.WRITE);
		traineeAccountManagerMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		traineeAccountManagerMap.put(Module.APPOINTMENT_OVERVIEW, Access.NONE);
		traineeAccountManagerMap.put(Module.SCHEDULE_APPOINTMENT, Access.NONE);
		traineeAccountManagerMap.put(Module.REGISTER_USER, Access.NONE);
		traineeAccountManagerMap.put(Module.USER_DETAILS, Access.READ);
		traineeAccountManagerMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		traineeAccountManagerMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.NONE);
		traineeAccountManagerMap.put(Module.EMBRYO_TRANSFER, Access.NONE);
		traineeAccountManagerMap.put(Module.EGG_THAWING, Access.NONE);
		traineeAccountManagerMap.put(Module.ANDROLOGY, Access.NONE);
		traineeAccountManagerMap.put(Module.SEMEN_PREPRATION, Access.NONE);
		traineeAccountManagerMap.put(Module.CRYOPRESERVATION_E, Access.NONE);
		traineeAccountManagerMap.put(Module.CRYOPRESERVATION_A, Access.NONE);
		traineeAccountManagerMap.put(Module.ET_TABLE, Access.NONE);
		traineeAccountManagerMap.put(Module.DOCTORS_OFFICE, Access.NONE);
		traineeAccountManagerMap.put(Module.NURSES_STATION, Access.NONE);
		traineeAccountManagerMap.put(Module.MISMATCH, Access.NONE);
		traineeAccountManagerMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		traineeAccountManagerMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		traineeAccountManagerMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		traineeAccountManagerMap.put(Module.BILLING_SETUP, Access.READ);
		traineeAccountManagerMap.put(Module.RECENT_ARCHIVE, Access.READ);
		traineeAccountManagerMap.put(Module.RECENT_PAGE, Access.READ);

		traineeAccountManagerMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		traineeAccountManagerMap.put(Module.ARCHIVE, Access.WRITE);
		traineeAccountManagerMap.put(Module.CYCLES, Access.READ);
		traineeAccountManagerMap.put(Module.APPOINTMENT_SCREEN, Access.READ);
		traineeAccountManagerMap.put(Module.DAY_DIALOG, Access.WRITE);
		traineeAccountManagerMap.put(Module.COMPANY_DETAILS, Access.READ);
		traineeAccountManagerMap.put(Module.ET_SCREEN, Access.NONE);
		traineeAccountManagerMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		traineeAccountManagerMap.put(Module.PAYMENT_DETAILS, Access.READ);
		traineeAccountManagerMap.put(Module.INVOICES_DETAIL, Access.READ);
		traineeAccountManagerMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		traineeAccountManagerMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		traineeAccountManagerMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		traineeAccountManagerMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		traineeAccountManagerMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}
	
	static {
		billingOfficerMap.put(Module.DIRECTORY, Access.READ);
		billingOfficerMap.put(Module.REGISTER_PATIENT, Access.NONE);
		billingOfficerMap.put(Module.PATIENT_LIST, Access.READ);
		billingOfficerMap.put(Module.PATIENT_DETAILS, Access.NONE);
		billingOfficerMap.put(Module.SEARCH_PATIENTS, Access.READ);
		billingOfficerMap.put(Module.PRINT, Access.WRITE);
		billingOfficerMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		billingOfficerMap.put(Module.APPOINTMENT_OVERVIEW, Access.NONE);
		billingOfficerMap.put(Module.SCHEDULE_APPOINTMENT, Access.NONE);
		billingOfficerMap.put(Module.REGISTER_USER, Access.NONE);
		billingOfficerMap.put(Module.USER_DETAILS, Access.READ);
		billingOfficerMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		billingOfficerMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.NONE);
		billingOfficerMap.put(Module.EMBRYO_TRANSFER, Access.NONE);
		billingOfficerMap.put(Module.EGG_THAWING, Access.NONE);
		billingOfficerMap.put(Module.ANDROLOGY, Access.NONE);
		billingOfficerMap.put(Module.SEMEN_PREPRATION, Access.NONE);
		billingOfficerMap.put(Module.CRYOPRESERVATION_E, Access.NONE);
		billingOfficerMap.put(Module.CRYOPRESERVATION_A, Access.NONE);
		billingOfficerMap.put(Module.ET_TABLE, Access.NONE);
		billingOfficerMap.put(Module.DOCTORS_OFFICE, Access.NONE);
		billingOfficerMap.put(Module.NURSES_STATION, Access.NONE);
		billingOfficerMap.put(Module.MISMATCH, Access.NONE);
		billingOfficerMap.put(Module.STANDARD_INVESTIGATION, Access.READ);
		billingOfficerMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		billingOfficerMap.put(Module.BILLING_AND_INVOICE, Access.WRITE);
		billingOfficerMap.put(Module.BILLING_SETUP, Access.READ);
		billingOfficerMap.put(Module.RECENT_ARCHIVE, Access.READ);
		billingOfficerMap.put(Module.RECENT_PAGE, Access.READ);

		billingOfficerMap.put(Module.PREGNANCY_OUTCOMES, Access.READ);
		billingOfficerMap.put(Module.ARCHIVE, Access.WRITE);
		billingOfficerMap.put(Module.CYCLES, Access.READ);
		billingOfficerMap.put(Module.APPOINTMENT_SCREEN, Access.READ);
		billingOfficerMap.put(Module.DAY_DIALOG, Access.WRITE);
		billingOfficerMap.put(Module.COMPANY_DETAILS, Access.READ);
		billingOfficerMap.put(Module.ET_SCREEN, Access.NONE);
		billingOfficerMap.put(Module.DELETE_APPOINTMENT, Access.READ);
		
		billingOfficerMap.put(Module.PAYMENT_DETAILS, Access.READ);
		billingOfficerMap.put(Module.INVOICES_DETAIL, Access.READ);
		billingOfficerMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		billingOfficerMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		billingOfficerMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		billingOfficerMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		billingOfficerMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}
	
	
	static {
		clientLiasonMap.put(Module.DIRECTORY, Access.READ);
		clientLiasonMap.put(Module.REGISTER_PATIENT, Access.NONE);
		clientLiasonMap.put(Module.PATIENT_LIST, Access.READ);
		clientLiasonMap.put(Module.PATIENT_DETAILS, Access.READ);
		clientLiasonMap.put(Module.SEARCH_PATIENTS, Access.READ);
		clientLiasonMap.put(Module.PRINT, Access.WRITE);
		clientLiasonMap.put(Module.SEARCH_BY_NAME_CODE, Access.WRITE);
		clientLiasonMap.put(Module.APPOINTMENT_OVERVIEW, Access.WRITE);
		clientLiasonMap.put(Module.SCHEDULE_APPOINTMENT, Access.WRITE);
		clientLiasonMap.put(Module.REGISTER_USER, Access.NONE);
		clientLiasonMap.put(Module.USER_DETAILS, Access.READ);
		clientLiasonMap.put(Module.USER_PRIVILLEGES, Access.NONE);
		clientLiasonMap.put(Module.EMBRYOLOGY_OVERVIEW, Access.NONE);
		clientLiasonMap.put(Module.EMBRYO_TRANSFER, Access.NONE);
		clientLiasonMap.put(Module.EGG_THAWING, Access.NONE);
		clientLiasonMap.put(Module.ANDROLOGY, Access.NONE);
		clientLiasonMap.put(Module.SEMEN_PREPRATION, Access.NONE);
		clientLiasonMap.put(Module.CRYOPRESERVATION_E, Access.NONE);
		clientLiasonMap.put(Module.CRYOPRESERVATION_A, Access.NONE);
		clientLiasonMap.put(Module.ET_TABLE, Access.NONE);
		clientLiasonMap.put(Module.DOCTORS_OFFICE, Access.READ);
		clientLiasonMap.put(Module.NURSES_STATION, Access.WRITE);
		clientLiasonMap.put(Module.MISMATCH, Access.READ);
		clientLiasonMap.put(Module.STANDARD_INVESTIGATION, Access.WRITE);
		clientLiasonMap.put(Module.STOCK_MAINTAINENCE, Access.READ);
		clientLiasonMap.put(Module.BILLING_AND_INVOICE, Access.READ);
		clientLiasonMap.put(Module.BILLING_SETUP, Access.NONE);
		clientLiasonMap.put(Module.RECENT_ARCHIVE, Access.READ);
		clientLiasonMap.put(Module.RECENT_PAGE, Access.READ);

		clientLiasonMap.put(Module.PREGNANCY_OUTCOMES, Access.WRITE);
		clientLiasonMap.put(Module.ARCHIVE, Access.WRITE);
		clientLiasonMap.put(Module.CYCLES, Access.READ);
		clientLiasonMap.put(Module.APPOINTMENT_SCREEN, Access.WRITE);
		clientLiasonMap.put(Module.DAY_DIALOG, Access.WRITE);
		clientLiasonMap.put(Module.COMPANY_DETAILS, Access.READ);
		clientLiasonMap.put(Module.ET_SCREEN, Access.NONE);
		clientLiasonMap.put(Module.DELETE_APPOINTMENT, Access.WRITE);
		
		clientLiasonMap.put(Module.PAYMENT_DETAILS, Access.READ);
		clientLiasonMap.put(Module.INVOICES_DETAIL, Access.READ);
		clientLiasonMap.put(Module.REFUND_AND_INVOICE, Access.READ);
		clientLiasonMap.put(Module.REFUND_BILL_PER_COUPLE, Access.READ);
		clientLiasonMap.put(Module.REFUND_BILL_OVERALL, Access.READ);
		clientLiasonMap.put(Module.UNPAID_BILL_PER_COUPLE, Access.READ);
		clientLiasonMap.put(Module.UNPAID_BILL_OVERALL, Access.READ);


	}

	public static boolean canWrite(int role, int moduleId) {
		boolean flag = false;
		Access access = getAccess(role, moduleId);
		if (access.equals(Access.WRITE)) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	public static boolean canView(int role, int moduleId) {
		boolean flag = false;
		Access access = getAccess(role, moduleId);
		if (!(access.equals(Access.NONE))) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	public enum Data {
		One, Two, Three
	}
}
