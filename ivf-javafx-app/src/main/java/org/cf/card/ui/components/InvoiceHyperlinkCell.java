package org.cf.card.ui.components;

import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.controller.BillingAndInvoiceController;
import org.cf.card.ui.controller.MainPageController;
import org.cf.card.ui.model.UIBillingAndInvoice;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.EnumPermission.Module;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;

/**
 * The Class InvoiceHyperlinkCell.
 * We are creating here two constructor one for BillingInvoice Number session for per couple
 * And other for BillingInvoice Number session for overall couple 
 */
/**
 * @author Pramod Maurya
 * @since : Mar 2, 2017
 */
public class InvoiceHyperlinkCell extends TableCell<UIBillingAndInvoice, Hyperlink> {

	private final Hyperlink invoiceLink;

	private BillingInvoiceDto billingInvoiceDto;
	
	private Long billingInvoiceNumber;

	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();
	
	private UICoupleService coupleService = new UICoupleService();
	

	/**
	 * This Constructor is for Invoices Per couple Screen.
	 * Instantiates a new invoice hyperlink cell.
	 *
	 * @param mainApp the main app
	 * @param login the login
	 */
	public InvoiceHyperlinkCell(MainApp mainApp, User login) {
		invoiceLink = new Hyperlink();
		invoiceLink.setOnAction(evt -> {
			billingInvoiceDto = billingAndInvoiceService.findBillingAndInvoiceByInvoiceNumber(billingInvoiceNumber);
			@SuppressWarnings("unchecked")
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
			sessionObject.setComponent(Constants.BILLING_INVOICE, billingInvoiceDto.getInvoiceNumber());
			LoadPanels.loadModule(mainApp, login, Module.BILLING_AND_INVOICE.getKey(), null);
		});
	}
	
	/**
	 * This Constructor is for Unpaid Overall Screen.
	 * Instantiates a new invoice hyperlink cell.
	 *
	 * @param mainApp the main app
	 * @param login the login
	 * @param uselessParam we are 
	 */
	public InvoiceHyperlinkCell(User login, MainApp mainApp) {
		invoiceLink = new Hyperlink();
		invoiceLink.setOnAction(evt -> {
			billingInvoiceDto = billingAndInvoiceService.findBillingAndInvoiceByInvoiceNumber(billingInvoiceNumber);
			Couple couple = coupleService.getCoupleById(billingInvoiceDto.getCoupleId());
			@SuppressWarnings("unchecked")
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
			sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple.getId());
			List<Codes> aoWomanCode = couple.getWoman().getCodes();
			if (!aoWomanCode.isEmpty()) {
				Codes womanCode = aoWomanCode.get(aoWomanCode.size() - 1);
				sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, womanCode != null ? womanCode.getId() : 0l);
			} else {
				sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, 0l);
			}
			List<Codes> aoManCode = couple.getMan().getCodes();
			if (!aoManCode.isEmpty()) {
				Codes manCode = aoManCode.get(aoManCode.size() - 1);
				sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, manCode != null ? manCode.getId() : 0l);
			} else {
				sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, 0l);
			}
			sessionObject.setComponent(Constants.BILLING_INVOICE, billingInvoiceDto.getInvoiceNumber());
			//We are destroying RefundInvoice Session so If any session related to refundInvoie for old couple left, then that will destroy.
			sessionObject.setComponent(Constants.REFUND_INVOICE, null);
			
			BillingAndInvoiceController billingAndInvoiceController = LoadPanels.getBillingAndInvoiceLoader().getController();
			billingAndInvoiceController.setMainApp(mainApp);
			billingAndInvoiceController.setLogin(login);
			billingAndInvoiceController.setCouple(couple);
			MainPageController mainPageController = LoadPanels.getMainPageLoader().getController();
			mainPageController.setCouple(couple);
			LoadPanels.loadModule(mainApp, login, Module.BILLING_AND_INVOICE.getKey(), null);
			
		});
	}

	@Override
	protected void updateItem(Hyperlink item, boolean empty) {
		super.updateItem(item, empty);
		if (null!=item && !empty) {
			setBillingInvoiceNumber(Long.valueOf(item.getText()));
			invoiceLink.setText(item.getText());
			setGraphic(invoiceLink);	
		} else {
			setGraphic(null);
		}
	}

	public void setBillingInvoiceNumber(Long billingInvoiceNumber) {
		this.billingInvoiceNumber = billingInvoiceNumber;
	}
	
	
}