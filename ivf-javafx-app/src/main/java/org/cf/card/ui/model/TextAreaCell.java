package org.cf.card.ui.model;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.PaymentDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.dto.RemarksDto;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.ui.service.UIPaymentService;
import org.cf.card.ui.service.UIRefundAndInvoiceService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TextAreaCell<T> extends TableCell<T, String> {

	// Table View row object
	T obj;

	// UICycles
	private static int SCREEN_ID = Module.CYCLES.getKey();
	private UIRemarksService uiRemarksService = new UIRemarksService();
	private UIEmbryoService embryoService = new UIEmbryoService();
	private UIRefundAndInvoiceService refundAndInvoiceService = new UIRefundAndInvoiceService();
	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();
	private UIPaymentService paymentService = new UIPaymentService();
	private TextArea textArea;
	private int loginUserRoleId;

	public TextAreaCell(TableView<T> tableView, int loginUserRoleId) {
		this.loginUserRoleId = loginUserRoleId;
		if (null != tableView && tableView.getItems().size() > 0)
			obj = tableView.getItems().get(0);
	}

	public TextAreaCell() {
	}

	@Override
	public void startEdit() {
		super.startEdit();
		if (textArea == null) {
			createTextArea();
		}
		setText(null);
		setGraphic(textArea);
		textArea.selectAll();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText((String) getItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textArea != null) {
					textArea.setText(getString());
				}
				setText(null);
				setGraphic(textArea);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	private void createTextArea() {

		textArea = new TextArea(getString());
		textArea.setPrefWidth(10);
		textArea.setPrefHeight(10);
		textArea.setWrapText(true);
		textArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void handle(KeyEvent t) {
				if (obj instanceof UICycles) {
					if (EnumPermission.canWrite(loginUserRoleId, Module.CYCLES.getKey())) {
						if (t.getCode() == KeyCode.ENTER) {
							TextArea textArea = (TextArea) t.getTarget();
							TextAreaCell cell = (TextAreaCell) textArea.getParent();
							UICycles uiCycles = (UICycles) cell.getTableRow().getItem();
							String remarks = textArea.getText();
							commitEdit(remarks);
							RemarksDto remarksDto = new RemarksDto();
							Long remarkId = uiCycles.getRemarkId();
							remarksDto.setCodeId(uiCycles.getWomanCodeId());
							remarksDto.setRemarksText(remarks);
							remarksDto.setId(remarkId);
							remarksDto.setRemarksType(SCREEN_ID);
							RemarksDto savedremarksDto = uiRemarksService.save(remarksDto);
							// to get latest saved remark Id
							uiCycles.setRemarkId(savedremarksDto.getId());
						} else if (t.getCode() == KeyCode.ESCAPE) {
							cancelEdit();
						}
					} else
						FileUtils.privillegeEditError();
				} else if (obj instanceof UiCryoTableView) {
					if (EnumPermission.canWrite(loginUserRoleId, Module.CYCLES.getKey())) {
						if (t.getCode() == KeyCode.ENTER) {
							TextArea textArea = (TextArea) t.getTarget();
							TextAreaCell cell = (TextAreaCell) textArea.getParent();
							UiCryoTableView uiCryoTableView = (UiCryoTableView) cell.getTableRow().getItem();
							String remarks = textArea.getText();
							commitEdit(remarks);
							uiCryoTableView.setRemarks(remarks);

							EmbryoCodeValueDto codeValueDto = new EmbryoCodeValueDto();
							codeValueDto.setEmbryoCodeId(uiCryoTableView.getId());
							codeValueDto.setRemarks(remarks);
							// Enter Action Event for save remarks in CRYO-E
							// previous data table
							embryoService.updateEmbryoCodesRemarks(codeValueDto);

						} else if (t.getCode() == KeyCode.ESCAPE) {
							cancelEdit();
						}
					} else
						FileUtils.privillegeEditError();
				} else if (obj instanceof UICryoSemen) {
					if (EnumPermission.canWrite(loginUserRoleId, Module.CRYOPRESERVATION_A.getKey())) {
						if (t.getCode() == KeyCode.ENTER) {
							TextArea textArea = (TextArea) t.getTarget();
							TextAreaCell cell = (TextAreaCell) textArea.getParent();
							UICryoSemen uiCryoSemen = (UICryoSemen) cell.getTableRow().getItem();
							String remarks = textArea.getText();
							commitEdit(remarks);
							uiCryoSemen.setRemarks(remarks);
						} else if (t.getCode() == KeyCode.ESCAPE) {
							cancelEdit();
						}
					} else
						FileUtils.privillegeEditError();
				} else if (obj instanceof UIRefundAndInvoice) {
					if (EnumPermission.canWrite(loginUserRoleId, Module.REFUND_AND_INVOICE.getKey())) {
						if (t.getCode() == KeyCode.ENTER) {
							TextArea textArea = (TextArea) t.getTarget();
							TextAreaCell cell = (TextAreaCell) textArea.getParent();
							UIRefundAndInvoice uiRefundAndInvoice = (UIRefundAndInvoice) cell.getTableRow().getItem();
							String remarks = textArea.getText();
							commitEdit(remarks);
							uiRefundAndInvoice.setRemarks(remarks);
							RefundInvoiceDto refundInvoiceDto = new RefundInvoiceDto();
							refundInvoiceDto.setId(uiRefundAndInvoice.getRefundInvoiceId());
							refundInvoiceDto.setRemarks(remarks);
							// Enter Action Event for save remarks in
							// RefundInvoiceTable
							// previous data table
							refundAndInvoiceService.updateRefundInvoicRemarks(refundInvoiceDto);
						} else if (t.getCode() == KeyCode.ESCAPE) {
							cancelEdit();
						}
					} else
						FileUtils.privillegeEditError();
				} else if (obj instanceof UIBillingAndInvoice) {
					if (EnumPermission.canWrite(loginUserRoleId, Module.BILLING_AND_INVOICE.getKey())) {
						if (t.getCode() == KeyCode.ENTER) {
							TextArea textArea = (TextArea) t.getTarget();
							TextAreaCell cell = (TextAreaCell) textArea.getParent();
							UIBillingAndInvoice uiBillingAndInvoice = (UIBillingAndInvoice) cell.getTableRow()
									.getItem();
							String remarks = textArea.getText();
							commitEdit(remarks);
							uiBillingAndInvoice.setRemarks(remarks);
							BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();
							billingInvoiceDto.setId(uiBillingAndInvoice.getBillingInvoiceId());
							billingInvoiceDto.setRemarks(remarks);
							// Enter Action Event for save remarks in
							// RefundInvoiceTable
							// previous data table
							billingAndInvoiceService.updateBillingInvoicRemarks(billingInvoiceDto);
						} else if (t.getCode() == KeyCode.ESCAPE) {
							cancelEdit();
						}
					} else
						FileUtils.privillegeEditError();
				} else if (obj instanceof UIPaymentDetails) {
					if (EnumPermission.canWrite(loginUserRoleId, Module.BILLING_AND_INVOICE.getKey())) {
						if (t.getCode() == KeyCode.ENTER) {
							TextArea textArea = (TextArea) t.getTarget();
							TextAreaCell cell = (TextAreaCell) textArea.getParent();
							UIPaymentDetails uiPaymentDetails = (UIPaymentDetails) cell.getTableRow().getItem();
							String remarks = textArea.getText();
							commitEdit(remarks);
							uiPaymentDetails.setRemarks(remarks);
							PaymentDto paymentDto = new PaymentDto();
							paymentDto.setId(uiPaymentDetails.getPaymentId());
							paymentDto.setRemarks(remarks);
							// Enter Action Event for save remarks in
							// Payment
							// previous data table
							paymentService.updatePaymentRemarks(paymentDto);

						} else if (t.getCode() == KeyCode.ESCAPE) {
							cancelEdit();
						}
					} else
						FileUtils.privillegeEditError();

				}

				else {
					System.out.println("Please write code regards new obj");
				}

			}
		});

	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}
