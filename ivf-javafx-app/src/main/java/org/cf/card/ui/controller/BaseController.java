package org.cf.card.ui.controller;

import org.cf.card.model.BillingInvoice;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;

/**
 * @author Surinder
 *
 */
public class BaseController {

	/**
	 * Couple object
	 */
	Couple couple;

	/**
	 * Codes object
	 */
	Codes womanCode;

	/**
	 * Codes object
	 */
	Codes manCode;

	/**
	 * MainApp object
	 */
	MainApp mainApp;

	/**
	 * User Object
	 */
	User login;

	/**
	 * BillingInvoice Object
	 */
	BillingInvoice billingInvoice;

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

	public Codes getWomanCode() {
		return womanCode;
	}

	public void setWomanCode(Codes womanCode) {
		this.womanCode = womanCode;
	}

	public Codes getManCode() {
		return manCode;
	}

	public void setManCode(Codes manCode) {
		this.manCode = manCode;
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	public BillingInvoice getBillingInvoice() {
		return billingInvoice;
	}

	public void setBillingInvoice(BillingInvoice billingInvoice) {
		this.billingInvoice = billingInvoice;
	}
	
	
}