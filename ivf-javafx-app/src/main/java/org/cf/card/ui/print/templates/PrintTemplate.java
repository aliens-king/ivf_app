package org.cf.card.ui.print.templates;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cf.card.dto.BillingInvoiceDto;
import org.cf.card.dto.CompanyDto;
import org.cf.card.dto.RefundInvoiceDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIProcedure;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.util.StringUtils;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PrintTemplate<T> {

	private double fontSize = 9;
	private PageLayout pageLayout = null;
	public int remarksHeight;

	UIClipartService clipartService = new UIClipartService();
	private static final String remarksHeader = MessageResource.getText("common.label.remarks");

	@SuppressWarnings("unchecked")
	private SessionObject<String, CompanyDto> sessionObjectForCompany = Session.getInstance().getSessionObject();;
	private CompanyDto companyDto = sessionObjectForCompany.getComponent(Constants.COMPANY_DETAILS);

	/**
	 * Creating Main page Title Header (Top Section)
	 * 
	 * @param pageLayout
	 * @return HBox
	 */
	public HBox createHeader(String mainTitle, String iconURL, String description, PageLayout pageLayout) {
		HBox headerHbox = new HBox();
		headerHbox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHbox.setAlignment(Pos.CENTER);
		// MainPage Title
		Label mainPageTitle = new Label(mainTitle + " ");
		// Icon
		ImageView mainPageIcon = new ImageView(new Image(iconURL));
		mainPageIcon.setFitHeight(20);
		mainPageIcon.setFitWidth(20);
		// Description
		Label titleDescription = new Label("  " + description);
		titleDescription.setAlignment(Pos.TOP_CENTER);

		headerHbox.getChildren().addAll(mainPageTitle, mainPageIcon, titleDescription);
		HBox.setMargin(mainPageIcon, new Insets(0, 0, 3, 0));
		return headerHbox;
	}

	/**
	 * Setting Patient Info in Grid Pane(Middle Section)
	 * 
	 * @param pageLayout
	 * @return GridPane
	 */
	public GridPane createPatientSection(Codes womanCode, Codes manCode, PageLayout pageLayout) {

		GridPane patientGrid = new GridPane();
		patientGrid.setAlignment(Pos.TOP_CENTER);
		patientGrid.setPrefHeight(135);
		patientGrid.setPadding(new Insets(10, 0, 0, 0));
		patientGrid.setVgap(10);
		patientGrid.setPrefWidth(pageLayout.getPrintableWidth());
		patientGrid.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		if (pageLayout.getPageOrientation() == PageOrientation.LANDSCAPE) {
			patientGrid.setHgap(20);
		} else {
			patientGrid.setHgap(-20);
		}
		// patientGrid.setGridLinesVisible(true);
		// Woman Personal Info
		patientGrid.add(createHeaderLabel(" Woman Personal Info"), 0, 0);
		patientGrid.add(createStaticLabel("  Surname: "), 0, 1);
		patientGrid.add(createStaticLabel("  First Name: "), 0, 2);
		patientGrid.add(createStaticLabel("  Middle Name: "), 0, 3);
		patientGrid.add(createStaticLabel("  Code: "), 0, 4);
		Label label = new Label();
		label.setPrefHeight(17);
		label.setMaxHeight(17);
		patientGrid.add(label, 0, 5);
		Client woman = womanCode != null ? womanCode.getClient() : null;
		if (woman != null) {
			patientGrid.add(createDynamicLabel(woman.getSurname()), 1, 1);
			patientGrid.add(createDynamicLabel(woman.getFirstName()), 1, 2);
			patientGrid.add(createDynamicLabel(woman.getMiddleName()), 1, 3);
			patientGrid.add(createDynamicLabel(womanCode.getCode()), 1, 4);
		}

		// Partner Personal Info
		patientGrid.add(createHeaderLabel("Partner Personal Info"), 2, 0);
		patientGrid.add(createStaticLabel(" Surname: "), 2, 1);
		patientGrid.add(createStaticLabel(" First Name: "), 2, 2);
		patientGrid.add(createStaticLabel(" Middle Name: "), 2, 3);
		patientGrid.add(createStaticLabel(" Code: "), 2, 4);

		Client partner = manCode != null ? manCode.getClient() : null;
		ImageView imageView = null;
		// Image image = null;
		double width = 95;
		double height = 95;
		imageView = getImageView(partner.getCouple().getId(), width, height);
		if (partner != null) {
			patientGrid.add(createDynamicLabel(partner.getSurname()), 3, 1);
			patientGrid.add(createDynamicLabel(partner.getFirstName()), 3, 2);
			patientGrid.add(createDynamicLabel(partner.getMiddleName()), 3, 3);
			patientGrid.add(createDynamicLabel(manCode.getCode()), 3, 4);
		}

		patientGrid.add(imageView, 4, 1, 1, 4);
		GridPane.setMargin(imageView, new Insets(0, 0, 0, 0));
		GridPane.setValignment(imageView, VPos.TOP);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(20);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(20);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(20);
		ColumnConstraints col4 = new ColumnConstraints();
		col4.setPercentWidth(20);
		ColumnConstraints col5 = new ColumnConstraints();
		col5.setPercentWidth(20);
		patientGrid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
		return patientGrid;
	}

	/**
	 * Creating Headers for Common Section
	 */
	public Label createHeaderLabel(String text) {
		Label headerLabel = new Label(text);
		fontSize = 12;
		headerLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, fontSize));
		headerLabel.setStyle(Constants.PRINT_STYLE);
		GridPane.setHalignment(headerLabel, HPos.LEFT);
		GridPane.setColumnSpan(headerLabel, 2);
		return headerLabel;
	}

	/**
	 * Creating Static Label for Common Section, Embryo Info
	 */
	public Label createStaticLabel(String text) {
		Label staticLabel = new Label(text);
		fontSize = 10;
		staticLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, fontSize));
		staticLabel.setStyle(Constants.PRINT_STYLE);
		GridPane.setHalignment(staticLabel, HPos.LEFT);
		return staticLabel;
	}

	/**
	 * Dynamic Labels for Common Section, Embryo Info
	 */
	public Label createDynamicLabel(String text) {
		Label dynamicLabel = new Label(text);
		fontSize = 10;
		dynamicLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.NORMAL, fontSize));
		dynamicLabel.setStyle(Constants.PRINT_STYLE);
		return dynamicLabel;
	}

	/**
	 * Creating Labels for Table Header
	 */
	public Label createTableHeaderLabel(String text, double labelWidth) {
		Label tableHeader = new Label(text);
		double fontSize = 8;
		tableHeader.setPrefWidth(labelWidth);
		tableHeader.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, fontSize));
		tableHeader.setStyle(Constants.PRINT_STYLE);
		return tableHeader;
	}

	/**
	 * This method is use for add table header row in HBox with separator. Adds
	 * the table row to H box with separator.
	 *
	 * @param tableHeaderRowHBox
	 *            the table row H box
	 * @param value
	 *            the value
	 * @param labelWidth
	 *            the label width
	 */
	public void addTableHeaderRowToHBoxWithSeparator(HBox tableRowHBox, String value, double labelWidth) {
		tableRowHBox.getChildren().add(createTableHeaderLabel(value, labelWidth));
		tableRowHBox.getChildren().add(createSeparator());
	}

	/**
	 * This method is use for add table row in HBox without separator. Adds the
	 * table row to H box.
	 *
	 * @param tableRowHBox
	 *            the table row H box
	 * @param value
	 *            the value
	 * @param labelWidth
	 *            the label width
	 */
	public void addTableHeaderRowToHBox(HBox tableRowHBox, String value, double labelWidth) {
		tableRowHBox.getChildren().add(createTableHeaderLabel(value, labelWidth));
	}

	/**
	 * This method is use for add table row data in HBox with separator. Adds
	 * the table row data to H box with separator.
	 *
	 * @param tableRowHBox
	 *            the table row H box
	 * @param value
	 *            the value
	 * @param labelWidth
	 *            the label width
	 */
	public void addTableRowDataToHBoxWithSeparator(HBox tableRowHBox, String value, double labelWidth) {
		tableRowHBox.getChildren().add(createTableRowLabel(value, labelWidth, false));
		tableRowHBox.getChildren().add(createSeparator());
	}

	/**
	 * This method is use for add table row data in HBox without separator. Adds
	 * the table row data to H box.
	 *
	 * @param tableRowHBox
	 *            the table row H box
	 * @param value
	 *            the value
	 * @param labelWidth
	 *            the label width
	 */
	public void addTableRowDataToHBox(HBox tableRowHBox, String value, double labelWidth) {
		tableRowHBox.getChildren().add(createTableRowLabel(value, labelWidth, false));
	}

	/**
	 * Creating Label for Table Rows
	 */
	public Label createTableRowLabel(String text, double labelWidth, boolean boldText) {
		Label rowLabel = new Label(text);
		double fontSize = 8;
		// condition to set the Grade in bold
		if (boldText) {
			rowLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, fontSize));
		} else {
			rowLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.NORMAL, fontSize));
		}
		rowLabel.setPrefWidth(labelWidth);
		rowLabel.setStyle(Constants.PRINT_STYLE);
		return rowLabel;
	}

	public Separator createSeparator() {
		Separator separator = new Separator(Orientation.VERTICAL);
		return separator;
	}

	/**
	 * Creating Footer of page - (Bottom)
	 * 
	 * @param page
	 *            no of page to be printed
	 * @param pageLayout
	 * @return GridPane
	 */
	public GridPane createFooter(int page, PageLayout pageLayout) {
		GridPane footerGrid = new GridPane();
		footerGrid.setPrefWidth(pageLayout.getPrintableWidth());
		footerGrid.setPrefHeight(10);
		Label leftLabel = createDynamicLabel(Constants.FOOTER_TEXT + page);
		GridPane.setHalignment(leftLabel, HPos.LEFT);
		footerGrid.add(leftLabel, 0, 0);
		Label rightLabel = createDynamicLabel(Constants.FOOTER_URL);
		GridPane.setHalignment(rightLabel, HPos.RIGHT);
		footerGrid.add(rightLabel, 1, 0);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(50);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(50);
		footerGrid.getColumnConstraints().addAll(col1, col2);
		return footerGrid;
	}

	// Creating VBox with header and textArea
	public List<Node> createHeaderAndTextArea(List<String> headerAndTextAreaText, PageLayout pageLayout, int height) {

		List<Node> nodes = new ArrayList<>();

		for (int i = 0; i < headerAndTextAreaText.size(); i++) {
			VBox allNodes = new VBox();
			allNodes.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
			allNodes.setPrefWidth(pageLayout.getPrintableWidth());
			allNodes.setPrefHeight(height);

			/*
			 * HBox hBox=new HBox(); hBox.setPrefHeight(15);
			 * allNodes.getChildren().add(hBox);
			 */
			allNodes.getChildren().add(createTextAreaHeader(headerAndTextAreaText.get(i), pageLayout));
			i = i + 1;
			allNodes.getChildren().add(createTextArea(headerAndTextAreaText.get(i), pageLayout));

			if (i == headerAndTextAreaText.size() - 1) {
				HBox hBox2 = new HBox();
				hBox2.setPrefHeight(15);
				allNodes.getChildren().add(hBox2);
			}

			nodes.add(allNodes);

		}
		return nodes;
	}

	// Creating Header for Long TextArea pages
	public HBox createTextAreaHeader(String labelText, PageLayout pageLayout) {

		HBox hBox = new HBox();
		Label label = new Label();
		label.setText("  " + labelText);
		label.setPrefHeight(25);
		label.setPrefWidth(pageLayout.getPrintableWidth());
		label.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, 12));
		label.setAlignment(Pos.CENTER_LEFT);
		label.setStyle("-fx-border-width: 0 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		hBox.getChildren().add(label);
		hBox.setAlignment(Pos.CENTER);
		return hBox;
	}

	// Creating TextArea for Long TextArea pages
	public HBox createTextArea(String text, PageLayout pageLayout) {

		HBox hBox = new HBox();
		Label labelText = new Label();
		labelText.setText("\n" + text);
		labelText.setPrefHeight(460);
		labelText.setPrefWidth(pageLayout.getPrintableWidth() - 20);
		labelText.autosize();
		labelText.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.LIGHT, 9));
		labelText.setAlignment(Pos.TOP_LEFT);
		// labelText.setStyle("-fx-border-width: 1 1 1 1; "+
		// Constants.PRINT_GREY_BORDER_STYLE);
		labelText.setWrapText(true);
		hBox.getChildren().add(labelText);
		hBox.setAlignment(Pos.CENTER);
		hBox.autosize();

		return hBox;
	}

	// Creating All comboxBoxes with text(label and Values)
	public VBox createComboBoxNodes(PageLayout pageLayout, List<String> values) {

		VBox vBox = new VBox();
		vBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		vBox.setMaxHeight(70);
		GridPane gridPane = new GridPane();
		gridPane.setPrefWidth(pageLayout.getPrintableWidth());
		for (int i = 0; i < values.size(); i++) {
			if (i < 6) {
				Label label = createStaticLabel(values.get(i));
				label.setPrefWidth(112);
				gridPane.add(label, i, 0);
				i = i + 1;
				Label labelText = createDynamicLabel(values.get(i));
				labelText.setPrefWidth(50);
				gridPane.add(labelText, i, 0);
				if (i == 5) {
					Label label2 = new Label();
					label2.prefHeight(5);
					gridPane.add(label2, 0, 1);
				}
			} else {
				Label label = createStaticLabel(values.get(i));
				label.setPrefWidth(112);
				gridPane.add(label, i - 6, 2);
				i = i + 1;
				Label labelText = createDynamicLabel(values.get(i));
				labelText.setPrefWidth(50);
				gridPane.add(labelText, i - 6, 2);
				if (i == 5) {
					Label label2 = new Label();
					label2.prefHeight(5);
					gridPane.add(label2, 0, 1);
				}
			}
		}
		gridPane.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(createStaticLabel(IConstants.emptyString), gridPane,
				createStaticLabel(IConstants.emptyString));
		return vBox;
	}

	/**
	 * Creating Remarks section for pages - (Bottom)
	 * 
	 * @param remarksText
	 *            remarks text
	 * @param pageLayout
	 * @return List<Node>
	 */
	public List<Node> createRemarks(String remarksText, PageLayout pageLayout) {
		// List for Remarks label and text
		List<String> remarkslabelAndTextList = null;
		List<Node> remarkNodeList = new ArrayList<>();
		// 225 = Height of Header + patient section + free spacing
		int mainVBoxHeight = (int) (pageLayout.getPrintableHeight() - 225);
		if (remarksText != null && !StringUtils.isEmpty(remarksText)) {
			remarkslabelAndTextList = new ArrayList<>();
			remarkslabelAndTextList.add("REMARKS");
			remarkslabelAndTextList.add(remarksText);
			remarkNodeList = createHeaderAndTextArea(remarkslabelAndTextList, pageLayout, mainVBoxHeight);
		}
		return remarkNodeList;
	}

	public PageLayout printPageLayout(PrinterJob printerJob) {

		Printer printer = Printer.getDefaultPrinter();
		pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
		remarksHeight = (int) (pageLayout.getPrintableHeight() - 225);
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		return pageLayout;

	}

	/**
	 ***************************** Below All methods are used in Billing
	 * modules.************************************************
	 * 
	 */

	/**
	 * This method is for add Address Details of Patient in case Billing module.
	 * Creates the address detail.
	 *
	 * @param address
	 *            the address
	 * @return the h box
	 */
	public HBox createAddressDetail(String address, PageLayout pageLayout) {
		HBox hBoxCreateAddress = new HBox();
		hBoxCreateAddress.setPrefWidth(pageLayout.getPrintableWidth());
		hBoxCreateAddress.setPrefHeight(80);
		hBoxCreateAddress.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		VBox vBoxCreateAddress = new VBox();
		Label addressHeader = createStaticLabel(" Billing Address: ");
		addressHeader.setPrefWidth(pageLayout.getPrintableWidth());
		addressHeader.setStyle("-fx-background-color: #A9A9A9");// darkgray
		Label addressValue = createDynamicLabel("  " + address);
		vBoxCreateAddress.getChildren().addAll(addressHeader, addressValue);
		hBoxCreateAddress.getChildren().add(vBoxCreateAddress);
		return hBoxCreateAddress;
	}

	/**
	 * Creates the company info and refund policy footer.
	 *
	 * @param companyInfo
	 *            the company info
	 * @param refundPolicy
	 *            the refund policy
	 * @return the h box
	 */
	public HBox createCompanyInfoAndRefundPolicyFooter(String companyInfo, String refundPolicy, PageLayout pageLayout) {

		HBox hBoxCompanyInfoAndPolicy = new HBox();
		hBoxCompanyInfoAndPolicy.setSpacing(2);
		hBoxCompanyInfoAndPolicy.setPrefHeight(110);
		// hBoxCompanyInfoAndPolicy.setStyle("-fx-border-width: 1 1 1 1; " +
		// Constants.PRINT_GREY_BORDER_STYLE);
		double printableWidth = pageLayout.getPrintableWidth() - 2;
		hBoxCompanyInfoAndPolicy.setPrefWidth(printableWidth);
		VBox vBoxCompanyInfo = new VBox();
		vBoxCompanyInfo.setPrefWidth(printableWidth / 2);
		vBoxCompanyInfo.setStyle("-fx-border-width: 1 1 1 1;" + Constants.PRINT_GREY_BORDER_STYLE);
		Label companyInfoHeader = createStaticLabel(" Company Info:");
		companyInfoHeader.setPrefWidth(printableWidth / 2);
		companyInfoHeader.setStyle("-fx-background-color: #A9A9A9");
		Label companyInfoValue = createDynamicLabel(" " + companyInfo);
		companyInfoValue.setWrapText(true);
		vBoxCompanyInfo.getChildren().addAll(companyInfoHeader, companyInfoValue);
		VBox vBoxRefundPolicy = new VBox();
		vBoxRefundPolicy.setPrefWidth(printableWidth / 2);
		vBoxRefundPolicy.setStyle("-fx-border-width: 1 1 1 1;" + Constants.PRINT_GREY_BORDER_STYLE);
		Label refundPolicyHeader = createStaticLabel(" Refund Policy");
		refundPolicyHeader.setPrefWidth(printableWidth / 2);
		refundPolicyHeader.setStyle("-fx-background-color: #A9A9A9");
		Label refundPolicyValue = createDynamicLabel(" " + refundPolicy);
		refundPolicyValue.setWrapText(true);
		vBoxRefundPolicy.getChildren().addAll(refundPolicyHeader, refundPolicyValue);
		hBoxCompanyInfoAndPolicy.getChildren().addAll(vBoxCompanyInfo, vBoxRefundPolicy);
		return hBoxCompanyInfoAndPolicy;

	}

	/**
	 * Creates the H box for invoice no and date.
	 *
	 * @param pageLayout
	 *            the page layout
	 * @param obj
	 *            the obj
	 * @return the h box
	 */
	public HBox createParentHBoxForInvoiceNoAndDate(PageLayout pageLayout, T obj) {
		HBox hboxInvoiceInfo = null;
		if (null != obj) {
			hboxInvoiceInfo = new HBox();
			hboxInvoiceInfo.setPrefHeight(25);
			hboxInvoiceInfo.setPrefWidth(pageLayout.getPrintableWidth() - 2);
			hboxInvoiceInfo.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			if (obj instanceof BillingInvoiceDto) {
				BillingInvoiceDto billingInvoiceDto = (BillingInvoiceDto) obj;
				hboxInvoiceInfo.getChildren().add(createChildHBoxForInvoiceNoAndDate(" INVOICE NUMBER",
						String.valueOf(billingInvoiceDto.getInvoiceNumber()), pageLayout));
				hboxInvoiceInfo.getChildren().add(createChildHBoxForInvoiceNoAndDate(" INVOICE DATE",
						Util.formatDate(IConstants.DATE_FORMAT, billingInvoiceDto.getInvoiceCreateDate()), pageLayout));
				hboxInvoiceInfo.getChildren().add(
						createChildHBoxForInvoiceNoAndDate(" BILLED BY", billingInvoiceDto.getCreatedBy(), pageLayout));
			} else if (obj instanceof RefundInvoiceDto) {
				RefundInvoiceDto refundInvoiceDto = (RefundInvoiceDto) obj;
				hboxInvoiceInfo.getChildren().add(createChildHBoxForInvoiceNoAndDate(" REFUND INVOICE",
						String.valueOf(refundInvoiceDto.getRefundInvoiceNumber()), pageLayout));
				hboxInvoiceInfo.getChildren().add(createChildHBoxForInvoiceNoAndDate(" REFUND DATE",
						Util.formatDate(IConstants.DATE_FORMAT, refundInvoiceDto.getRefundCreateDate()), pageLayout));
				hboxInvoiceInfo.getChildren().add(
						createChildHBoxForInvoiceNoAndDate(" BILLED BY", refundInvoiceDto.getCreatedBy(), pageLayout));

			}
		}
		return hboxInvoiceInfo;
	}

	/**
	 * This method create VBox for BillingAndInvoice info, which include Header
	 * and their corresponding Value. Creates the V box for invoice info.
	 *
	 * @param staticHeaderName
	 *            the static header name
	 * @param value
	 *            the value
	 * @return the v box
	 */
	private HBox createChildHBoxForInvoiceNoAndDate(String staticHeaderName, String value, PageLayout pageLayout) {
		HBox invoiceInfoHBox = new HBox();
		invoiceInfoHBox.setPrefHeight(30);
		invoiceInfoHBox.setPrefWidth((pageLayout.getPrintableWidth() - 2) / 2);
		invoiceInfoHBox.setSpacing(10);
		invoiceInfoHBox.setStyle("-fx-border-width: 0 1 0 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		Label header = createStaticLabel(staticHeaderName + " :");
		Label outputValue = createDynamicLabel(value);
		invoiceInfoHBox.getChildren().addAll(header, outputValue);
		invoiceInfoHBox.setAlignment(Pos.CENTER_LEFT);
		return invoiceInfoHBox;
	}

	/**
	 * Creates the parent H box for vat sub total and total.
	 *
	 * @param pageLayout
	 *            the page layout
	 * @param obj
	 *            the obj
	 * @param currencyType
	 *            the currency type
	 * @param vatAmount
	 *            the vat amount
	 * @return the h box
	 */
	public HBox createParentHBoxForVatSubTotalAndTotal(PageLayout pageLayout, T obj, String currencyType) {
		HBox hboxVatSubTotalAndTotal = null;
		if (null != obj) {
			hboxVatSubTotalAndTotal = new HBox();
			hboxVatSubTotalAndTotal.setPrefHeight(30);
			hboxVatSubTotalAndTotal.setPrefWidth(pageLayout.getPrintableWidth());
			hboxVatSubTotalAndTotal.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			if (obj instanceof BillingInvoiceDto) {
				BillingInvoiceDto billingInvoiceDto = (BillingInvoiceDto) obj;
				int vatValue = billingInvoiceDto.getVat();
				hboxVatSubTotalAndTotal.getChildren().add(createChildHBoxForVatSubTotalAndTotal(
						" VAT(" + vatValue + "%) :",
						companyDto.getBillingCurrency()
								+ Util.getValueUpto2Decimal(Util.vatAmount(billingInvoiceDto.getSubTotal(), vatValue)),
						pageLayout));
				hboxVatSubTotalAndTotal.getChildren().add(createChildHBoxForVatSubTotalAndTotal(" SUB-TOTAL :",
						companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getSubTotal()),
						pageLayout));
				hboxVatSubTotalAndTotal.getChildren().add(createChildHBoxForVatSubTotalAndTotal(" TOTAL :",
						companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(billingInvoiceDto.getTotalBill()),
						pageLayout));
			} else if (obj instanceof RefundInvoiceDto) {
				RefundInvoiceDto refundInvoiceDto = (RefundInvoiceDto) obj;
				int vatValue = refundInvoiceDto.getVat();
				hboxVatSubTotalAndTotal.getChildren()
						.add(createChildHBoxForVatSubTotalAndTotal(" VAT(" + vatValue + "%) :",
								companyDto.getBillingCurrency() + Util
										.getValueUpto2Decimal(Util.vatAmount(refundInvoiceDto.getSubTotal(), vatValue)),
								pageLayout));
				hboxVatSubTotalAndTotal.getChildren().add(createChildHBoxForVatSubTotalAndTotal(" SUB-TOTAL :",
						companyDto.getBillingCurrency() + Util.getValueUpto2Decimal(refundInvoiceDto.getSubTotal()),
						pageLayout));
				hboxVatSubTotalAndTotal.getChildren()
						.add(createChildHBoxForVatSubTotalAndTotal(" TOTAL :",
								companyDto.getBillingCurrency()
										+ Util.getValueUpto2Decimal(refundInvoiceDto.getTotalRefundBill()),
								pageLayout));
			}
		}
		return hboxVatSubTotalAndTotal;
	}

	/**
	 * Creates the child H box for vat sub total and total.
	 *
	 * @param staticHeaderName
	 *            the static header name
	 * @param value
	 *            the value
	 * @param pageLayout
	 *            the page layout
	 * @return the h box
	 */
	public HBox createChildHBoxForVatSubTotalAndTotal(String staticHeaderName, String value, PageLayout pageLayout) {
		HBox invoiceInfoHBox = new HBox();
		invoiceInfoHBox.setPrefHeight(30);
		invoiceInfoHBox.setPrefWidth((pageLayout.getPrintableWidth() - 2) / 3);
		invoiceInfoHBox.setSpacing(10);
		invoiceInfoHBox.setStyle("-fx-border-width: 0 1 0 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		Label header = createStaticLabel(staticHeaderName + " ");
		Label outputValue = createDynamicLabel(value);
		invoiceInfoHBox.getChildren().addAll(header, outputValue);
		invoiceInfoHBox.setAlignment(Pos.CENTER_LEFT);
		return invoiceInfoHBox;
	}

	public VBox createInvoiceRemarks(PageLayout pageLayout, T obj) {
		VBox vBox = null;
		if (null != obj) {
			vBox = new VBox();
			vBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
			Label remarksHeaderlabel = createHeaderLabel(" " + remarksHeader);
			remarksHeaderlabel.setPrefWidth(pageLayout.getPrintableWidth() - 2);
			remarksHeaderlabel.setStyle("-fx-background-color: #A9A9A9");
			vBox.getChildren().add(remarksHeaderlabel);
			vBox.setPrefHeight(100);
			HBox remarksHBox = null;
			if (obj instanceof BillingInvoiceDto) {
				BillingInvoiceDto billingInvoiceDto = (BillingInvoiceDto) obj;
				String remarks = billingInvoiceDto.getRemarks();
				if (null != remarks && !remarks.isEmpty()) {
					remarksHBox = createTextArea(remarks, pageLayout);
				} else {
					return null;
				}
			} else if (obj instanceof RefundInvoiceDto) {
				RefundInvoiceDto refundInvoiceDto = (RefundInvoiceDto) obj;
				String remarks = refundInvoiceDto.getRemarks();
				if (null != remarks && !remarks.isEmpty()) {
					remarksHBox = createTextArea(remarks, pageLayout);
				} else {
					return null;
				}
			}
			remarksHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			vBox.getChildren().add(remarksHBox);
		}
		return vBox;
	}

	/**
	 * Creates the printable table.
	 *
	 * @param columns
	 *            the columns
	 * @param items
	 *            the items
	 * @param pageLayout
	 *            the page layout
	 * @return the list
	 */
	public List<Node> createPrintableTable(ObservableList<TableColumn<UIProcedure, ?>> columns,
			Collection<UIProcedure> items, PageLayout pageLayout) {
		List<Node> nodes = null;
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = pageLayout.getPrintableWidth() / columnCount;
		if (null != items && items.size() > 0) {
			nodes = new ArrayList<Node>();
			for (UIProcedure invoice : items) {
				// elementHeight would be the height of each cell
				final double elementHeight = 15;
				// adding table on multiple pages
				// We are subtract 550 from totalPrintableHeight, here 550 =
				// (Page Header + patientSection + BillingAddress +
				// CompanyRefund Policy). So printableHeight - 550 = result
				// height will for table.
				if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 550) {
					tableVBox = new VBox();
					tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
					tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
					HBox tableHeader = createTableHeader(columns, labelWidth, pageLayout);
					// adding table columns in Table VBox
					tableVBox.getChildren().add(tableHeader);
					nodes.add(tableVBox);
					totalHeight = 0;
				}
				HBox row = createTableRow(invoice, labelWidth, pageLayout);
				totalHeight += elementHeight;
				if (tableVBox != null)
					tableVBox.getChildren().add(row);
			}
		}
		return nodes;
	}

	/**
	 * Setting Column Names(Header) in HBox
	 */
	public HBox createTableHeader(ObservableList<TableColumn<UIProcedure, ?>> columns, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setPrefHeight(20);
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		int count = 1;
		for (TableColumn<UIProcedure, ?> cols : columns) {
			// condition to avoid the last separator in table
			if (count == columns.size()) {
				addTableHeaderRowToHBox(headerHBox, cols.getText(), labelWidth);
			} else {
				addTableHeaderRowToHBoxWithSeparator(headerHBox, cols.getText(), labelWidth);
			}
			count++;
		}
		return headerHBox;
	}

	/**
	 * Creates the table row.
	 *
	 * @param record
	 *            the record
	 * @param labelWidth
	 *            the label width
	 * @param pageLayout
	 *            the page layout
	 * @return the h box
	 */
	public HBox createTableRow(UIProcedure record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getId().getValue()), labelWidth);
		addTableRowDataToHBoxWithSeparator(rowHBox, String.valueOf(record.getProcedureName().getValue()), labelWidth);
		addTableRowDataToHBox(rowHBox, String.valueOf(record.getProcedurePrice().getValue()), labelWidth);
		return rowHBox;
	}

	/**
	 * Creates the space between elements when we printing pages.
	 *
	 * @param height
	 *            the height
	 * @return the v box
	 */
	public VBox createSpaceBetweenElements(double height) {
		VBox vBox = new VBox();
		vBox.setPrefHeight(height);
		return vBox;

	}

	/**
	 * Creates the parent H box for total bill total paid and total balance.
	 *
	 * @param pageLayout
	 *            the page layout
	 * @param billingInvoiceDto
	 *            the billing invoice dto
	 * @param currencyType
	 *            the currency type
	 * @return the h box
	 */
	public HBox createParentHBoxForTotalBillTotalPaidAndTotalBalance(PageLayout pageLayout, T obj,
			String currencyType) {
		HBox hboxTotalBillAndBalance = null;
		if (null != obj) {
			hboxTotalBillAndBalance = new HBox();
			hboxTotalBillAndBalance.setPrefHeight(30);
			hboxTotalBillAndBalance.setPrefWidth(pageLayout.getPrintableWidth());
			hboxTotalBillAndBalance.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			if (obj instanceof BillingInvoiceDto) {
				BillingInvoiceDto billingInvoiceDto = (BillingInvoiceDto) obj;
				hboxTotalBillAndBalance.getChildren().add(createChildHBoxForVatSubTotalAndTotal(" Total Bill :",
						companyDto.getBillingCurrency()
								+ Util.getValueUpto2Decimal(((BillingInvoiceDto) billingInvoiceDto).getTotalBill()),
						pageLayout));
				hboxTotalBillAndBalance.getChildren().add(createChildHBoxForVatSubTotalAndTotal(" Total Paid :",
						companyDto.getBillingCurrency()
								+ Util.getValueUpto2Decimal(((BillingInvoiceDto) billingInvoiceDto).getTotalPaid()),
						pageLayout));
				hboxTotalBillAndBalance.getChildren()
						.add(createChildHBoxForVatSubTotalAndTotal(" Total Balance :", companyDto.getBillingCurrency()
								+ Util.getValueUpto2Decimal(((BillingInvoiceDto) billingInvoiceDto).getTotalBalance()),
								pageLayout));
			}
			if (obj instanceof RefundInvoiceDto) {
				RefundInvoiceDto refundInvoiceDto = (RefundInvoiceDto) obj;
				hboxTotalBillAndBalance.getChildren().add(createChildHBoxForVatSubTotalAndTotal(" Total Refund Bill :",
						companyDto.getBillingCurrency()
								+ Util.getValueUpto2Decimal(((RefundInvoiceDto) refundInvoiceDto).getTotalRefundBill()),
						pageLayout));
			}
		}
		return hboxTotalBillAndBalance;
	}

	/**
	 * **************************** Below All methods are used for UnpaidOverall
	 * and Refund Overall**********************************************.
	 */

	/**
	 * @param text
	 *            the text
	 * @param labelWidth
	 *            the label width
	 * @param labelHeight
	 *            the label height
	 * @param boldText
	 *            the bold text
	 * @return the label
	 */
	public Label createTableRowLabelForUnpaid(String text, double labelWidth, double labelHeight, boolean boldText) {
		Label rowLabel = new Label(text);
		double fontSize = 8;
		if (boldText) {
			rowLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, fontSize));
		} else {
			rowLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.NORMAL, fontSize));
		}
		rowLabel.setWrapText(true);
		rowLabel.setMinWidth(labelWidth);
		rowLabel.setMaxWidth(labelWidth);
		rowLabel.setPrefWidth(labelWidth);
		rowLabel.setMinHeight(labelHeight);
		rowLabel.setMaxHeight(labelHeight);
		rowLabel.setPrefHeight(labelHeight);
		rowLabel.setWrapText(true);
		rowLabel.setStyle(Constants.PRINT_STYLE);
		return rowLabel;
	}

	/**
	 * This method is use for add table row in HBox with separator. Adds the
	 * table row to H box with separator.
	 *
	 * @param tableHeaderRowHBox
	 *            the table row H box
	 * @param value
	 *            the value
	 * @param labelWidth
	 *            the label width
	 */
	public void addTableRowToHBoxWithSeparatorOverallScreen(HBox tableRowHBox, String value, double labelWidth,
			double labelHeight, boolean isBold) {
		tableRowHBox.getChildren().add(createTableRowLabelForUnpaid(value, labelWidth, labelHeight, isBold));
		tableRowHBox.getChildren().add(createSeparator());
	}

	/**
	 * This method is use for add table row in HBox without separator. Adds the
	 * table row to H box.
	 *
	 * @param tableRowHBox
	 *            the table row H box
	 * @param value
	 *            the value
	 * @param labelWidth
	 *            the label width
	 */
	public void addTableRowToHBoxOverallScreen(HBox tableRowHBox, String value, double labelWidth, double labelHeight,
			boolean isBold) {
		tableRowHBox.getChildren().add(createTableRowLabelForUnpaid(value, labelWidth, labelHeight, isBold));
	}

	/**
	 * Adds the image view to printing table cell.
	 *
	 * @param tableRowHBox
	 *            the table row H box
	 * @param coupleId
	 *            the couple id
	 * @param columnWidth
	 *            the column width
	 */
	public void addImageViewToPrintingTableCell(HBox tableRowHBox, Long coupleId, double columnWidth, double imageWidth,
			double imageHeight) {
		HBox hBox = new HBox();
		hBox.setPrefWidth(columnWidth);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(getImageView(coupleId, imageWidth, imageHeight));
		tableRowHBox.getChildren().add(hBox);
		tableRowHBox.getChildren().add(createSeparator());
	}

	/**
	 * Image file path.
	 *
	 * @param coupleId
	 *            the couple id
	 * @return the file
	 */
	private ImageView getImageView(Long coupleId, double width, double height) {

		ImageView imageView = new ImageView();
		Image image = null;
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		try {
			File file = new File(clipartService.getImage(coupleId));
			image = new Image(file.toURI().toURL().toString());
			imageView.setImage(image);
			imageView.setEffect(new DropShadow(10, Color.BLACK));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageView;
	}

	public VBox createRegistrantCommonInfo(PageLayout pageLayout, RegistrantDto registrantDto,
			String userRegistrantLabelName, String asstUserRegistrantlabelName) {
		VBox vBox = new VBox();
		vBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		vBox.setSpacing(4);
		vBox.setPrefHeight(70);
		HBox registrantHeaderHBox = new HBox();
		HBox nurseHBox = new HBox();
		HBox asstNurseHBox = new HBox();
		nurseHBox.setSpacing(30);
		asstNurseHBox.setSpacing(30);
		registrantHeaderHBox.getChildren().add(createStaticLabel(" REGISTRANT INFO "));
		registrantHeaderHBox.setStyle("-fx-border-width: 0 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		Label nurseNameLabel = createStaticLabel(" " + userRegistrantLabelName);
		nurseNameLabel.setMinWidth(50);
		Label nurseNameValue = createDynamicLabel(registrantDto != null ? registrantDto.getNameOfUser() : IConstants.emptyString);
		nurseHBox.getChildren().addAll(nurseNameLabel, nurseNameValue);
		Label asstNurseNameLabel = createStaticLabel(" " + asstUserRegistrantlabelName);
		asstNurseNameLabel.setMinWidth(50);
		Label asstNurseNameValue = createDynamicLabel(registrantDto != null ? registrantDto.getAssistantUser(): IConstants.emptyString);
		asstNurseHBox.getChildren().addAll(asstNurseNameLabel, asstNurseNameValue);
		vBox.getChildren().addAll(registrantHeaderHBox, nurseHBox, asstNurseHBox);
		return vBox;
	}

	public Label spaceBetweenElements(double height) {
		Label extraSpace = createStaticLabel(IConstants.emptyString);
		extraSpace.setPrefHeight(20);
		return extraSpace;
	}

}
