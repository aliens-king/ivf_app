package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.ui.components.InvoiceHyperlinkCell;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.TextAreaCell;
import org.cf.card.ui.model.UIBillingAndInvoice;
import org.cf.card.ui.print.templates.BillingInvoiceDetailAndUnpaidPerCouplePrinting;
import org.cf.card.ui.service.UIBillingAndInvoiceService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * @author Pramod Maurya
 *
 * @since : Jan 2, 2017
 */
public class UnpaidBillPerCoupleController extends BaseController {

	// getting all message from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.billing.unpaid");
	private static final String iconURL = "/icons/unpaid_bill.png";

	// binding fxml elements
	@FXML
	private TextField totalBill;
	@FXML
	private TextField totalPaid;
	@FXML
	private TextField totalBalance;
	@FXML
	private TableView<UIBillingAndInvoice> unpaidBillTableView;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> srNoColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, Hyperlink> invoiceHyperlink;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> totalBillColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> totalPaidColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, Long> totalBalanceColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, String> billedByColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, String> invoiceDateColumn;
	@FXML
	private TableColumn<UIBillingAndInvoice, String> remarksColumn;
	@FXML
	private CommonDetailController commonDetailController;

	// Variables
	private UIBillingAndInvoiceService billingAndInvoiceService = new UIBillingAndInvoiceService();
	private Float overallBill = null;
	private Float overallPaidBill = null;
	private Float overallBalance = null;

	/* maintaining the session data for and CompanyDetail */
	private SessionObject<String, CompanyDto> sessionObjectForCompany = null;
	private CompanyDto companyDto;

	@SuppressWarnings("unchecked")
	public void buildData() {
		resetFields();
		sessionObjectForCompany = Session.getInstance().getSessionObject();
		if (null != sessionObjectForCompany) {
			companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);
		}
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		buildTableData();
		makeTextFieldNonEditable();
	}

	private void buildTableData() {
		unpaidBillTableView.getItems().clear();
		List<BillingInvoiceDto> billingInvoiceDtos = billingAndInvoiceService
				.findAllUnpaidBillPerCoupleByCoupleId(couple.getId());
		ObservableList<UIBillingAndInvoice> observableList = FXCollections.observableArrayList();
		List<UIBillingAndInvoice> col = new ArrayList<>();
		if (null != billingInvoiceDtos && billingInvoiceDtos.size() > 0) {
			overallBill = new Float(0.0);
			overallPaidBill = new Float(0.0);
			overallBalance = new Float(0.0);
			String currencyType = companyDto.getBillingCurrency();
			billingInvoiceDtos.forEach(new Consumer<BillingInvoiceDto>() {
				int count = 1;

				@Override
				public void accept(BillingInvoiceDto dto) {
					Float totalBill = dto.getTotalBill();
					Float totalPaid = dto.getTotalPaid();
					Float totalBalance = dto.getTotalBalance();
					overallBill = overallBill + totalBill;
					overallPaidBill = overallPaidBill + totalPaid;
					overallBalance = overallBalance + totalBalance;
					String totalBillAsString = Util.getValueUpto2Decimal(dto.getTotalBill());
					String totalPaidAsString = Util.getValueUpto2Decimal(dto.getTotalPaid());
					String totalBalanceAsString = Util.getValueUpto2Decimal(dto.getTotalBalance());
					Long srNo = new Long(count);
					UIBillingAndInvoice invoives = new UIBillingAndInvoice(srNo, dto.getId(),
							currencyType + totalBillAsString, currencyType + totalPaidAsString,
							currencyType + totalBalanceAsString,
							Util.formatDate(IConstants.DATE_FORMAT, dto.getInvoiceCreateDate()), dto.getCreatedBy(),
							dto.getRemarks(), dto.getInvoiceNumber());
					col.add(invoives);
					count++;
				}

			});
			observableList.setAll(col);
			try {
				srNoColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("srNo"));
				invoiceHyperlink.setCellValueFactory(
						new PropertyValueFactory<UIBillingAndInvoice, Hyperlink>("invoiceHyperlink"));
				invoiceHyperlink.setCellFactory(hyperlinkCellFactory);
				totalBillColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("totalBill"));
				totalPaidColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("totalPaid"));
				totalBalanceColumn
						.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, Long>("totalBalance"));
				billedByColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, String>("billedBy"));
				invoiceDateColumn
						.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, String>("invoiceDate"));
				remarksColumn.setCellValueFactory(new PropertyValueFactory<UIBillingAndInvoice, String>("remarks"));
				// Adding Textarea in remarks column
				remarksColumn.setCellFactory(
						new Callback<TableColumn<UIBillingAndInvoice, String>, TableCell<UIBillingAndInvoice, String>>() {
							@Override
							public TableCell<UIBillingAndInvoice, String> call(
									TableColumn<UIBillingAndInvoice, String> param) {
								return new TextAreaCell<UIBillingAndInvoice>(unpaidBillTableView, login.getRoleId());
							}
						});

			} catch (Exception e) {
				e.printStackTrace();
			}
			unpaidBillTableView.setItems(observableList);
			String uiTotalBill = currencyType + Util.getValueUpto2Decimal(overallBill);
			String uiTotalPaid = currencyType + Util.getValueUpto2Decimal(overallPaidBill);
			String uiTotalBalance = currencyType + Util.getValueUpto2Decimal(overallBalance);
			totalBill.setText(uiTotalBill);
			totalPaid.setText(uiTotalPaid);
			totalBalance.setText(uiTotalBalance);
		}
	}

	private Callback<TableColumn<UIBillingAndInvoice, Hyperlink>, TableCell<UIBillingAndInvoice, Hyperlink>> hyperlinkCellFactory = new Callback<TableColumn<UIBillingAndInvoice, Hyperlink>, TableCell<UIBillingAndInvoice, Hyperlink>>() {
		@Override
		public TableCell<UIBillingAndInvoice, Hyperlink> call(TableColumn<UIBillingAndInvoice, Hyperlink> p) {
			return new InvoiceHyperlinkCell(mainApp, login);
		}
	};

	@FXML
	private void printAction() {

		BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();
		billingInvoiceDto.setTotalPaid(overallPaidBill);
		billingInvoiceDto.setTotalBalance(overallBalance);
		billingInvoiceDto.setTotalBill(overallBill);
		new BillingInvoiceDetailAndUnpaidPerCouplePrinting().printActionForInvoicesAndUnpaidPerCouple(
				unpaidBillTableView, womanCode, manCode, mainApp, mainPageTitle, iconURL, billingInvoiceDto);

	}

	private void resetFields() {
		unpaidBillTableView.getItems().clear();
		totalBill.setText(IConstants.emptyString);
		totalPaid.setText(IConstants.emptyString);
		totalBalance.setText(IConstants.emptyString);
	}
	
	private void makeTextFieldNonEditable() {
		totalBill.setEditable(false);
		totalPaid.setEditable(false);
		totalBalance.setEditable(false);
	}


}
