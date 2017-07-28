package org.cf.card.ui.controller;

import java.io.File;

import org.cf.card.dto.CompanyDto;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIComapnyService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.ui.validation.FormValidator;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class CompanyDetailsController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.company.details");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String iconURL = "/icons/company.png";

	@FXML
	private TextField companyFullName;
	@FXML
	private TextArea companyAddress;
	@FXML
	private TextArea companyMotive;
	@FXML
	private TextField logoTextField;
	@FXML
	private TextField companyBillingCurrency;
	@FXML
	private TextField companyBillingVAT;
	@FXML
	private TextArea companyBillingRefundPolicy;
	@FXML
	private Text companyNameErrorLabel;
	@FXML
	private Text companyAddressErrorLabel;
	@FXML
	private Text companyMotiveErrorLabel;
	@FXML
	private Text companyBillingCurrencyErrorText;
	@FXML
	private Text companyBillingVATErrorText;
	@FXML
	private Text companyBillingRefundPolicyErrorText;
	@FXML
	private Button browselogoButton;
	@FXML

	private ImageView selectedLogo;
	UIComapnyService comapnyService = new UIComapnyService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();
	FormValidator validator = new FormValidator();
	private CompanyDto companyDto = null;
	private File file = null;

	@FXML
	public void initialize() {
		companyBillingVAT.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					companyBillingVAT.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		makeFieldNonEditable();

	}

	public void buildData() {

		makeFieldNonEditable();
		selectedLogo.setImage(null);
		companyDto = comapnyService.getCompanyDetails(login.getId());
		// logoTextField.setText(MessageResource.getText("company.controller.company.browse.logoTextField"));
		logoTextField.setStyle("-fx-font-size: 15");
		if (null != companyDto) {
			companyFullName.setText(companyDto.getCompanyFullName());
			companyAddress.setText(companyDto.getCompanyAddress());
			companyMotive.setText(companyDto.getCompanyMotive());
			companyBillingCurrency.setText(companyDto.getBillingCurrency());
			companyBillingVAT.setText(String.valueOf(companyDto.getBillingVAT()));
			companyBillingRefundPolicy.setText(companyDto.getBillingRefundPolicy());
			if (null != file)
				logoTextField.setText(file.getName());
			else
				logoTextField.setText(MessageResource.getText("company.controller.company.browse.logoTextField"));

		} else {
			companyFullName.clear();
			companyAddress.clear();
			companyMotive.clear();
		}

	}

	@FXML
	private void selectCompanyLogo() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.COMPANY_DETAILS.getKey())) {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(
					// new FileChooser.ExtensionFilter("All Images", "*."),
					new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
			fileChooser.setTitle("Open Resource File");
			file = fileChooser.showOpenDialog(mainApp.getPatientFileStage());

			if (null != file) {
				logoTextField.setText(file.getName());
				selectedLogo.setFitWidth(200);
				selectedLogo.setFitHeight(200);
				selectedLogo.setPreserveRatio(true);
				selectedLogo.setImage(new Image(file.getAbsoluteFile().toURI().toString()));

			}
		} else {
			FileUtils.privillegeEditError();
		}

	}

	@FXML
	private void saveAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.COMPANY_DETAILS.getKey())) {
			int ok = 1;
			String logoDestPathsss = null;
			if (null != companyDto) {
				companyDto.setId(companyDto.getId());
			} else {
				companyDto = new CompanyDto();
			}
			ok = validator.validateCompanyField(companyFullName, companyAddress, companyMotive, companyBillingCurrency,
					companyBillingVAT, companyBillingRefundPolicy, companyNameErrorLabel, companyAddressErrorLabel,
					companyMotiveErrorLabel, companyBillingCurrencyErrorText, companyBillingVATErrorText,
					companyBillingRefundPolicyErrorText);
			if (ok == 1) {
				try {

					companyDto.setCompanyFullName(companyFullName.getText());
					companyDto.setCompanyAddress(companyAddress.getText());
					companyDto.setCompanyMotive(companyMotive.getText());
					companyDto.setBillingCurrency(companyBillingCurrency.getText());
					companyDto.setBillingVAT(Integer.parseInt(companyBillingVAT.getText()));
					companyDto.setBillingRefundPolicy(companyBillingRefundPolicy.getText());
					CompanyDto companyDtoUpdated = comapnyService.saveCompanyDetails(companyDto);
					if(null!=companyDtoUpdated){
						// We are setting CompanyToSession
						@SuppressWarnings("unchecked")
						SessionObject<String, CompanyDto> sessionObject = Session.getInstance().getSessionObject();
						sessionObject.setComponent(Constants.COMPANY_DETAILS, companyDtoUpdated);
					}
					
					if (null != file) {
						logoDestPathsss = Configuration.getClipartDir() + Constants.RESOURCE_FILEPATH_PERM_LOGO;
						comapnyService.saveCompanylogo(file, logoDestPathsss);
					}
					buildData();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

		} else {
			FileUtils.privillegeEditError();
		}

	}

	@FXML
	private void editAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.COMPANY_DETAILS.getKey())) {
			makeFieldEditable();
			if (null != companyDto) {
				companyDto.setCompanyFullName(companyFullName.getText());
				companyDto.setCompanyAddress(companyAddress.getText());
				companyDto.setCompanyMotive(companyMotive.getText());
			}

		} else {
			FileUtils.privillegeEditError();
		}

	}

	@FXML
	private void billingSetupScreenAction() {
		LoadPanels.loadModule(mainApp, login, Module.BILLING_SETUP.getKey(), null);

	}

	/**
	 * Prints the action. Printing Details of Company.
	 */
	@FXML
	private void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		int page = 1;
		// Getting Header
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, IConstants.emptyString, pageLayout);
		// Getting Center
		GridPane centerGrid = createCompanyPage(pageLayout);
		// Getting Footer
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);

		BorderPane root = new BorderPane();
		// root.setStyle("-fx-border-width: 1 1 1 1; " +
		// Constants.PRINT_GREY_BORDER_STYLE);
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());
		root.setTop(headerHbox);
		root.setCenter(centerGrid);
		root.setBottom(footerGrid);

		// GridPane companyGrid = createCompanyPage(pageLayout);
		if (null != companyDto) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				printerJob.printPage(root);
			}
			printerJob.endJob();
		} else {
			Notify alert = new Notify(AlertType.WARNING, noDataAvailableMessage);
			alert.showAndWait();
		}

	}

	/**
	 * Creates the company page.
	 *
	 * @param pageLayout
	 *            the page layout
	 * @return the grid pane
	 */
	private GridPane createCompanyPage(PageLayout pageLayout) {

		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		gridPane.setPrefWidth(pageLayout.getPrintableWidth());
		gridPane.setPrefHeight(680);
		gridPane.setMaxHeight(680);

		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(20);
		ColumnConstraints columnTwo = new ColumnConstraints();
		columnTwo.setPercentWidth(5);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(75);
		gridPane.getColumnConstraints().addAll(columnOne, columnTwo, columnThree);

		RowConstraints rowOne = new RowConstraints();
		rowOne.setPercentHeight(15);
		RowConstraints rowTwo = new RowConstraints();
		rowTwo.setPercentHeight(10);
		RowConstraints rowThree = new RowConstraints();
		rowThree.setPercentHeight(10);
		RowConstraints rowFour = new RowConstraints();
		rowFour.setPercentHeight(65);

		gridPane.getRowConstraints().addAll(rowOne, rowTwo, rowThree, rowFour);

		if (null != companyDto) {

			Label companyHeader = new Label("  Company Logo :");
			companyHeader.setPrefHeight(20);
			companyHeader.setPrefWidth(100);
			companyHeader.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, 10));

			ImageView imageView = new ImageView();
			imageView.setFitHeight(100);
			imageView.setFitWidth(100);
			String logoDestPath = comapnyService.getImage(1l);
			Image image = new Image(new File(logoDestPath).toURI().toString());
			imageView.setImage(image);

			Label companyNameTitle = new Label("  Company Name :");
			companyNameTitle.setPrefHeight(20);
			companyNameTitle.setPrefWidth(100);
			companyNameTitle.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, 10));
			Label companyName = new Label(companyDto.getCompanyFullName());
			companyName.setWrapText(true);
			companyName.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.NORMAL, 9));

			Label companyAddressTitle = new Label("  Company Address :");
			companyAddressTitle.setPrefHeight(20);
			companyAddressTitle.setPrefWidth(100);
			companyAddressTitle.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, 10));
			Label companyAddress = new Label(companyDto.getCompanyAddress());
			companyAddress.setWrapText(true);
			companyAddress.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.NORMAL, 9));

			Label companyMotiveTitle = new Label("  Company Moto :");
			companyMotiveTitle.setPrefHeight(20);
			companyMotiveTitle.setPrefWidth(100);
			companyMotiveTitle.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.BOLD, 10));
			Label companyMoto = new Label(companyDto.getCompanyMotive());
			companyMoto.setWrapText(true);
			companyMoto.setFont(Font.font(Constants.PRINT_FONT_FAMILY, FontWeight.NORMAL, 9));

			gridPane.add(companyHeader, 0, 0, 1, 1);
			GridPane.setValignment(companyHeader, VPos.TOP);
			gridPane.add(imageView, 2, 0, 1, 1);
			GridPane.setValignment(imageView, VPos.CENTER);
			GridPane.setHalignment(imageView, HPos.CENTER);

			gridPane.add(companyNameTitle, 0, 1, 1, 1);
			GridPane.setValignment(companyNameTitle, VPos.TOP);
			gridPane.add(companyName, 2, 1, 1, 1);
			GridPane.setValignment(companyName, VPos.TOP);

			gridPane.add(companyAddressTitle, 0, 2, 1, 1);
			GridPane.setValignment(companyAddressTitle, VPos.TOP);
			gridPane.add(companyAddress, 2, 2, 1, 1);
			GridPane.setValignment(companyAddress, VPos.TOP);

			gridPane.add(companyMotiveTitle, 0, 3, 1, 1);
			GridPane.setValignment(companyMotiveTitle, VPos.TOP);
			gridPane.add(companyMoto, 2, 3, 1, 1);
			GridPane.setValignment(companyMoto, VPos.TOP);

		}
		return gridPane;
	}

	private void makeFieldEditable() {
		companyFullName.setEditable(true);
		companyAddress.setEditable(true);
		companyMotive.setEditable(true);
		companyBillingCurrency.setEditable(true);
		companyBillingVAT.setEditable(true);
		companyBillingRefundPolicy.setEditable(true);

	}

	private void makeFieldNonEditable() {
		companyFullName.setEditable(false);
		companyAddress.setEditable(false);
		companyMotive.setEditable(false);
		companyBillingCurrency.setEditable(false);
		companyBillingVAT.setEditable(false);
		companyBillingRefundPolicy.setEditable(false);
	}

}