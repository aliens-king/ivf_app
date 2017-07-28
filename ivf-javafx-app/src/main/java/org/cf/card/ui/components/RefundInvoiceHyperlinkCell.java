package org.cf.card.ui.components;

import java.util.List;

import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.controller.MainPageController;
import org.cf.card.ui.controller.RefundAndInvoiceController;
import org.cf.card.ui.model.UIRefundAndInvoice;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UIRefundAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.EnumPermission.Module;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;

/**
 * The Class RefundInvoiceHyperlinkCell.
 * We are creating here two constructor one for RefundInvoice Number session for per couple
 * And other for RefundInvoice Number session for overall couple 
 */
/**
 * @author Pramod Maurya
 * @since : Mar 2, 2017
 */
public class RefundInvoiceHyperlinkCell extends TableCell<UIRefundAndInvoice, Hyperlink> {

	private final Hyperlink invoiceLink;

	private RefundInvoiceDto refundInvoiceDto;

	private Long refundInvoiceNumber;

	private UIRefundAndInvoiceService uiRefundAndInvoiceService = new UIRefundAndInvoiceService();

	private UICoupleService coupleService = new UICoupleService();

	/**
	 * This Constructor is for refundInvoices Per couple Screen. Instantiates a
	 * new invoice hyperlink cell.
	 *
	 * @param mainApp
	 *            the main app
	 * @param login
	 *            the login
	 */
	public RefundInvoiceHyperlinkCell(MainApp mainApp, User login) {
		invoiceLink = new Hyperlink();
		invoiceLink.setOnAction(evt -> {
			refundInvoiceDto = uiRefundAndInvoiceService.findRefundAndInvoiceByInvoiceNumber(refundInvoiceNumber);
			@SuppressWarnings("unchecked")
			SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
			sessionObject.setComponent(Constants.REFUND_INVOICE, refundInvoiceDto.getRefundInvoiceNumber());
			LoadPanels.loadModule(mainApp, login, Module.REFUND_AND_INVOICE.getKey(), null);
		});
	}

	/**
	 * This Constructor is for Refund Overall Screen. Instantiates a new invoice
	 * hyperlink cell.
	 *
	 * @param mainApp
	 *            the main app
	 * @param login
	 *            the login
	 */
	public RefundInvoiceHyperlinkCell(User login, MainApp mainApp) {
		invoiceLink = new Hyperlink();
		invoiceLink.setOnAction(evt -> {
			refundInvoiceDto = uiRefundAndInvoiceService.findRefundAndInvoiceByInvoiceNumber(refundInvoiceNumber);
			Couple couple = coupleService.getCoupleById(refundInvoiceDto.getCoupleId());
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
			sessionObject.setComponent(Constants.REFUND_INVOICE, refundInvoiceDto.getRefundInvoiceNumber());
			//We are destroying BillingInvoice Session so If any session related to BillingInvoice for old couple left, then that will destroy.
			sessionObject.setComponent(Constants.BILLING_INVOICE, null);
			RefundAndInvoiceController refundAndInvoiceController = LoadPanels.getRefundAndInvoiceLoader()
					.getController();
			refundAndInvoiceController.setMainApp(mainApp);
			refundAndInvoiceController.setLogin(login);
			refundAndInvoiceController.setCouple(couple);
			MainPageController mainPageController = LoadPanels.getMainPageLoader().getController();
			mainPageController.setCouple(couple);
			LoadPanels.loadModule(mainApp, login, Module.REFUND_AND_INVOICE.getKey(), null);

		});
	}

	@Override
	protected void updateItem(Hyperlink item, boolean empty) {
		super.updateItem(item, empty);
		if (null != item && !empty) {
			setRefundInvoiceNumber(Long.valueOf(item.getText()));
			invoiceLink.setText(item.getText());
			setGraphic(invoiceLink);
		} else {
			setGraphic(null);
		}
	}

	public void setRefundInvoiceNumber(Long refundInvoiceNumber) {
		this.refundInvoiceNumber = refundInvoiceNumber;
	}

}
