/**
 *
 */
package org.cf.card.ui.controller;

import java.util.Date;

import org.cf.card.dto.PregnancyOutcomesDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.model.Treatment;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIPregnancyOutcomesService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.EnumPregnancyOutcomes;
import org.cf.card.util.EnumPregnancyOutcomes.BiochemicalType;
import org.cf.card.util.EnumPregnancyOutcomes.ClinicalType;
import org.cf.card.util.EnumPregnancyOutcomes.LiveBirthType;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author insonix
 *
 */
public class PregnancyOutcomesController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.pregnancy.details");
	private static final String titleDescription = MessageResource.getText("mainpage.title.pregnancy.description");
	private static final String iconURL = "/icons/pregnancy.png";
	private static final String remarksHeader = MessageResource.getText("common.label.remarks");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	private static final String cycleNotCompleted = MessageResource.getText("pregnancy.outcomes.alert.cycle.complete");
	private static final String emptyItem = MessageResource.getText("prenency.not.selected.item");

	private static final String biochemicalUpdateAlert = MessageResource
			.getText("pregnancy.outcomes.alert.cycle.update.biochemical");
	private static final String clinicalcalUpdateAlert = MessageResource
			.getText("pregnancy.outcomes.alert.cycle.update.clinical");
	private static final String clinicalcalUpdateAlertIfBiochemicalNotDone = MessageResource
			.getText("pregnancy.outcomes.alert.cycle.update.clinical.not.biochemical");

	private static final String livebirthUpdateAlert = MessageResource
			.getText("pregnancy.outcomes.alert.cycle.update.livebirth");

	private static final String livebirthUpdateAlertIfClinicalNotDone = MessageResource
			.getText("pregnancy.outcomes.alert.cycle.update.clinical.not.clinical");

	// creating object
	private Date currentDate = null;
	UIPregnancyOutcomesService uIPregnancyOutcomesService = new UIPregnancyOutcomesService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();
	private PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element
	@FXML
	private TextField cycleDateTextField;

	@FXML
	private TextField biochemicalDateLabel;

	@FXML
	private TextField clinicalDateLabel;

	@FXML
	private TextField liveBirthDateLabel;

	@FXML
	private ComboBox<BiochemicalType> biochemicalComboBox;
	@FXML
	private ComboBox<ClinicalType> clinicalComboBox;

	@FXML
	private ComboBox<LiveBirthType> liveBirthComboBox;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private TextField nurseRegistrant;

	@FXML
	private TextField asstNurseRegistrant;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private InvestigationCommonController investigationCommonController;

	// creating instance variables(class level)
	private Treatment treatment;
	private RegistrantDto registrantDto;
	private int SCREEN_ID = Module.PREGNANCY_OUTCOMES.getKey();
	private PregnancyOutcomesDto pregnancyOutcomesDto;

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public void buildData() {

		resetFields();
		currentDate = new Date();
		remarksTextArea.setStyle("-fx-font-size: 14px;");
		// Adding constant Values in Biochemical Combobox
		ObservableList<BiochemicalType> biochemicalList = FXCollections.observableArrayList();
		biochemicalList.addAll(BiochemicalType.values());
		biochemicalComboBox.setItems(biochemicalList);
		// biochemicalComboBox.getSelectionModel().selectFirst();
		// Adding constant Values in Clinical Combobox
		ObservableList<ClinicalType> clinicalList = FXCollections.observableArrayList();
		clinicalList.addAll(ClinicalType.values());
		clinicalComboBox.setItems(clinicalList);
		// clinicalComboBox.getSelectionModel().selectFirst();
		// Adding constant Values in Live Birth Combobox
		ObservableList<LiveBirthType> liveBirth = FXCollections.observableArrayList();
		liveBirth.addAll(LiveBirthType.values());
		liveBirthComboBox.setItems(liveBirth);
		// liveBirthComboBox.getSelectionModel().selectFirst();

		if (null != couple) {

			registrantDto = uiRegistrantService.getRegistrantByCodeAndScreenID(womanCode.getId(), SCREEN_ID);
			if (null != registrantDto) {
				nurseRegistrant.setText(registrantDto.getNameOfUser());
				asstNurseRegistrant.setText(registrantDto.getAssistantUser());
			}
			commonDetailController.setMainApp(mainApp);
			commonDetailController.setWomanPersonalInfo(womanCode);
			commonDetailController.setPartnerPersonalInfo(manCode);
			investigationCommonController.setCouple(couple);
			investigationCommonController.setManCode(manCode);
			investigationCommonController.setWomanCode(womanCode);
			investigationCommonController.build();
			if (null != treatment)
				cycleDateTextField.setText(Util.formatDate(IConstants.DATE_FORMAT, treatment.getStartDate()));

			pregnancyOutcomesDto = uIPregnancyOutcomesService.getPregnancyOutcomesByWomanCodeId(womanCode.getId());
			if (null != pregnancyOutcomesDto) {
				if (null != pregnancyOutcomesDto.getBiochemicalDate()) {
					biochemicalComboBox
							.setValue(BiochemicalType.getEnumByKey(pregnancyOutcomesDto.getBiochemicalOption()));
					biochemicalDateLabel.setText(
							Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getBiochemicalDate()));
				}
				if (null != pregnancyOutcomesDto.getClinicalDate()) {
					clinicalComboBox.setValue(ClinicalType.getEnumByKey(pregnancyOutcomesDto.getClinicalOption()));
					clinicalDateLabel
							.setText(Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getClinicalDate()));
				}
				if (null != pregnancyOutcomesDto.getLivebirthDate()) {
					liveBirthComboBox.setValue(LiveBirthType.getEnumByKey(pregnancyOutcomesDto.getLivebirthOption()));
					liveBirthDateLabel
							.setText(Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getLivebirthDate()));
				}
				if (null != pregnancyOutcomesDto.getRemarks())
					remarksTextArea.setText(pregnancyOutcomesDto.getRemarks());

			}

			makeFieldsNonEditable();

		}
	}

	/**
	 * Save biochemical action.
	 */
	@FXML
	private void saveBiochemicalAction() {

		if (EnumPermission.canWrite(login.getRoleId(), Module.PREGNANCY_OUTCOMES.getKey())) {
			// check that the Cycle Completed of not
			if (currentDate.after(treatment.getEndDate())) {

				if (null != biochemicalComboBox.getSelectionModel().getSelectedItem()) {
					if (null != pregnancyOutcomesDto && null != pregnancyOutcomesDto.getBiochemicalDate()) {

						// Edit Biochemical on Same date of Input
						if (Util.formatDate(IConstants.DATE_FORMAT, currentDate).equals(
								Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getBiochemicalDate()))) {
							pregnancyOutcomesDto
									.setBiochemical(EnumPregnancyOutcomes.PregnancyOutcomesOption.BIOCHEMICAL.getKey());
							pregnancyOutcomesDto.setBiochemicalOption(
									biochemicalComboBox.getSelectionModel().getSelectedItem().getKey());
							pregnancyOutcomesDto.setBiochemicalDate(currentDate);
							uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);
						} else {
							Notify alert = new Notify(AlertType.WARNING, biochemicalUpdateAlert);
							alert.showAndWait();
						}

					} else {
						if (null == pregnancyOutcomesDto)
							pregnancyOutcomesDto = new PregnancyOutcomesDto();
						pregnancyOutcomesDto
								.setBiochemical(EnumPregnancyOutcomes.PregnancyOutcomesOption.BIOCHEMICAL.getKey());
						pregnancyOutcomesDto.setBiochemicalOption(
								biochemicalComboBox.getSelectionModel().getSelectedItem().getKey());
						pregnancyOutcomesDto.setBiochemicalDate(currentDate);
						pregnancyOutcomesDto.setWomanCodeId(womanCode.getId());
						uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);
					}
				} else {
					Notify alert = new Notify(AlertType.WARNING, emptyItem);
					alert.showAndWait();
				}

			} else {
				Notify alert = new Notify(AlertType.WARNING, cycleNotCompleted);
				alert.showAndWait();
			}
			buildData();
		} else
			FileUtils.privillegeEditError();

	}

	/**
	 * Save clinical action.
	 */
	@FXML
	private void saveClinicalAction() {

		if (EnumPermission.canWrite(login.getRoleId(), Module.PREGNANCY_OUTCOMES.getKey())) {
			// check that the Cycle Completed of not
			if (currentDate.after(treatment.getEndDate())) {
				if (null != clinicalComboBox.getSelectionModel().getSelectedItem()) {
					if (null != pregnancyOutcomesDto && null != pregnancyOutcomesDto.getBiochemicalDate()) {

						if (null != pregnancyOutcomesDto.getClinicalDate()) {

							if (Util.formatDate(IConstants.DATE_FORMAT, currentDate).equals(
									Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getClinicalDate()))) {

								pregnancyOutcomesDto
										.setClinical(EnumPregnancyOutcomes.PregnancyOutcomesOption.CLINICAL.getKey());
								pregnancyOutcomesDto.setClinicalOption(
										clinicalComboBox.getSelectionModel().getSelectedItem().getKey());
								pregnancyOutcomesDto.setClinicalDate(currentDate);
								uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);

							} else {
								Notify alert = new Notify(AlertType.WARNING, clinicalcalUpdateAlert);
								alert.showAndWait();
							}

						} else {
							// pregnancyOutcomesDto = new
							// PregnancyOutcomesDto();
							pregnancyOutcomesDto
									.setClinical(EnumPregnancyOutcomes.PregnancyOutcomesOption.CLINICAL.getKey());
							pregnancyOutcomesDto
									.setClinicalOption(clinicalComboBox.getSelectionModel().getSelectedItem().getKey());
							pregnancyOutcomesDto.setClinicalDate(currentDate);
							pregnancyOutcomesDto.setWomanCodeId(womanCode.getId());
							uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);
						}
					} else {
						Notify alert = new Notify(AlertType.WARNING, clinicalcalUpdateAlertIfBiochemicalNotDone);
						alert.showAndWait();
					}
				} else {
					Notify alert = new Notify(AlertType.WARNING, emptyItem);
					alert.showAndWait();
				}
			} else {
				Notify alert = new Notify(AlertType.WARNING, cycleNotCompleted);
				alert.showAndWait();
			}
			buildData();
		} else
			FileUtils.privillegeEditError();
	}

	/**
	 * Save live birth action.
	 */
	@FXML
	private void saveLiveBirthAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PREGNANCY_OUTCOMES.getKey())) {
			// check that the Cycle Completed of not
			if (currentDate.after(treatment.getEndDate())) {
				if (null != liveBirthComboBox.getSelectionModel().getSelectedItem()) {
					if (null != pregnancyOutcomesDto.getClinicalDate()) {
						if (null != pregnancyOutcomesDto && null != pregnancyOutcomesDto.getLivebirthDate()) {
							if (Util.formatDate(IConstants.DATE_FORMAT, currentDate).equals(
									Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getLivebirthDate()))) {

								pregnancyOutcomesDto.setLivebirth(
										EnumPregnancyOutcomes.PregnancyOutcomesOption.LIVE_BIRTH.getKey());
								pregnancyOutcomesDto.setLivebirthOption(
										liveBirthComboBox.getSelectionModel().getSelectedItem().getKey());
								pregnancyOutcomesDto.setLivebirthDate(currentDate);
								uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);
							} else {
								Notify alert = new Notify(AlertType.WARNING, livebirthUpdateAlert);
								alert.showAndWait();
							}

						} else {
							// pregnancyOutcomesDto = new
							// PregnancyOutcomesDto();
							pregnancyOutcomesDto
									.setLivebirth(EnumPregnancyOutcomes.PregnancyOutcomesOption.LIVE_BIRTH.getKey());
							pregnancyOutcomesDto.setLivebirthOption(
									liveBirthComboBox.getSelectionModel().getSelectedItem().getKey());
							pregnancyOutcomesDto.setLivebirthDate(currentDate);
							pregnancyOutcomesDto.setWomanCodeId(womanCode.getId());
							uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);
						}
					} else {
						Notify alert = new Notify(AlertType.WARNING, livebirthUpdateAlertIfClinicalNotDone);
						alert.showAndWait();
					}
				} else {
					Notify alert = new Notify(AlertType.WARNING, emptyItem);
					alert.showAndWait();
				}
			} else {
				Notify alert = new Notify(AlertType.WARNING, cycleNotCompleted);
				alert.showAndWait();
			}
			buildData();
		} else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void saveAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PREGNANCY_OUTCOMES.getKey())) {

			if (null == pregnancyOutcomesDto) {
				pregnancyOutcomesDto = new PregnancyOutcomesDto();
				pregnancyOutcomesDto.setWomanCodeId(womanCode.getId());
			}
			pregnancyOutcomesDto.setRemarks(remarksTextArea.getText());
			uIPregnancyOutcomesService.savePregnancyOutcome(pregnancyOutcomesDto);
			/*
			 * else { Notify alert = new Notify(AlertType.WARNING,
			 * remarksAlert); alert.showAndWait(); }
			 */
			buildData();
		} else
			FileUtils.privillegeEditError();
	}

	/**
	 * Save registrant.
	 */
	@FXML
	private void saveRegistrant() {
		registrantDto = FileUtils.saveOrUpdateRegistrant(registrantDto, womanCode.getId(), SCREEN_ID, nurseRegistrant,
				asstNurseRegistrant);
	}

	@FXML
	private void editRemarksAction() {
		remarksTextArea.setEditable(true);
	}

	@FXML
	private void printFormAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);

		VBox pregnancyScreenVbox = new VBox();
		pregnancyScreenVbox.setSpacing(20);
		pregnancyScreenVbox.setPrefHeight(pageLayout.getPrintableHeight() - 225);
		pregnancyScreenVbox.setPrefWidth(pageLayout.getPrintableWidth());

		VBox pregnancyInfoVBox = createPregnancyOutcomeInfo();
		VBox registrantDetailVBox = printTemplate.createRegistrantCommonInfo(pageLayout, registrantDto,
				MessageResource.getText("common.emb.nurse.registrant"),
				MessageResource.getText("common.emb.asst.nurse.registrant"));
		VBox pregnancyRemarksVBox = createPregnancyOutcomeRemarks(pageLayout);

		if (null != pregnancyInfoVBox)
			pregnancyScreenVbox.getChildren().add(pregnancyInfoVBox);

		if (null!=registrantDto && null != registrantDetailVBox)
			pregnancyScreenVbox.getChildren().add(registrantDetailVBox);
		if (null != pregnancyRemarksVBox)
			pregnancyScreenVbox.getChildren().add(pregnancyRemarksVBox);
		int page = 1;
		if (null != pregnancyScreenVbox && pregnancyScreenVbox.getChildren().size() > 0) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				BorderPane printPage = createPrintPage(page, pregnancyScreenVbox, pageLayout);
				printerJob.printPage(printPage);
			}
			printerJob.endJob();
		} else {
			Notify notify = new Notify(AlertType.INFORMATION, noDataAvailableMessage);
			notify.showAndWait();
		}

	}

	private BorderPane createPrintPage(int page, VBox pregnancyScreenVbox, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headerHbox);
		Label spaceBetweenElements1 = printTemplate.spaceBetweenElements(20);
		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		contentVBox.getChildren().addAll(patientGrid, spaceBetweenElements1, pregnancyScreenVbox);
		root.setCenter(contentVBox);

		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	private VBox createPregnancyOutcomeInfo() {

		VBox vBox = null;
		HBox pregnancyHeaderHBox = new HBox();
		pregnancyHeaderHBox.setSpacing(100);
		pregnancyHeaderHBox.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		pregnancyHeaderHBox.getChildren().add(printTemplate.createStaticLabel(" PREGNANCY OUTCOME"));
		Label valueLabel = printTemplate.createStaticLabel("VALUE");
		valueLabel.setPrefWidth(110);
		valueLabel.setMinWidth(110);
		valueLabel.setMaxWidth(110);
		pregnancyHeaderHBox.getChildren().add(valueLabel);
		pregnancyHeaderHBox.getChildren().add(printTemplate.createStaticLabel("DATE"));

		if (null != pregnancyOutcomesDto) {
			vBox = new VBox();
			vBox.setSpacing(10);
			vBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
			HBox cycleStartDate = new HBox();
			cycleStartDate.setStyle("-fx-border-width: 0 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			cycleStartDate.getChildren().add(printTemplate.createStaticLabel(" Cycle start date : "));
			cycleStartDate.getChildren().add(printTemplate
					.createDynamicLabel(Util.formatDate(IConstants.DATE_FORMAT, treatment.getStartDate())));
			VBox unuseSpace = new VBox();
			HBox biochemicalHBox = new HBox();
			// biochemicalHBox.setStyle("-fx-border-width: 0 0 1 0; " +
			// Constants.PRINT_GREY_BORDER_STYLE);
			biochemicalHBox.setSpacing(145);
			biochemicalHBox.getChildren().add(printTemplate.createStaticLabel(" BIOCHEMICAL"));
			Label biochemicalTypeLabel = printTemplate.createDynamicLabel(
					BiochemicalType.getEnumByKey(pregnancyOutcomesDto.getBiochemicalOption()) != null
							? BiochemicalType.getEnumByKey(pregnancyOutcomesDto.getBiochemicalOption()).toString()
							: IConstants.emptyString);
			biochemicalTypeLabel.setPrefWidth(65);
			biochemicalTypeLabel.setMinWidth(65);
			biochemicalTypeLabel.setMaxWidth(65);
			biochemicalHBox.getChildren().add(biochemicalTypeLabel);
			// biochemicalHBox.getChildren().add(printTemplate.createDynamicLabel(
			// BiochemicalType.getEnumByKey(pregnancyOutcomesDto.getBiochemicalOption()).toString()));
			biochemicalHBox.getChildren().add(printTemplate.createDynamicLabel(
					Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getBiochemicalDate())));

			HBox clinicalHBox = new HBox();
			// clinicalHBox.setStyle("-fx-border-width: 0 0 1 0; " +
			// Constants.PRINT_GREY_BORDER_STYLE);
			clinicalHBox.setSpacing(145);
			clinicalHBox.getChildren().add(printTemplate.createStaticLabel(" CLINICAL         "));
			Label clinialTypeLabel = printTemplate
					.createDynamicLabel(ClinicalType.getEnumByKey(pregnancyOutcomesDto.getClinicalOption()) != null
							? ClinicalType.getEnumByKey(pregnancyOutcomesDto.getClinicalOption()).toString()
							: IConstants.emptyString);
			clinialTypeLabel.setPrefWidth(65);
			clinialTypeLabel.setMinWidth(65);
			clinialTypeLabel.setMaxWidth(65);
			clinicalHBox.getChildren().add(clinialTypeLabel);
			// clinicalHBox.getChildren().add(printTemplate.createDynamicLabel(
			// ClinicalType.getEnumByKey(pregnancyOutcomesDto.getClinicalOption()).toString()));
			clinicalHBox.getChildren().add(printTemplate.createDynamicLabel(
					Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getClinicalDate())));

			HBox livebirthHBox = new HBox();
			// livebirthHBox.setStyle("-fx-border-width: 0 0 0 0; " +
			// Constants.PRINT_GREY_BORDER_STYLE);
			livebirthHBox.setSpacing(145);
			livebirthHBox.getChildren().add(printTemplate.createStaticLabel(" LIVE BIRTH      "));
			Label livebirthTypeLabel = printTemplate
					.createDynamicLabel(LiveBirthType.getEnumByKey(pregnancyOutcomesDto.getLivebirthOption()) != null
							? LiveBirthType.getEnumByKey(pregnancyOutcomesDto.getLivebirthOption()).toString()
							: IConstants.emptyString);
			livebirthTypeLabel.setPrefWidth(65);
			livebirthTypeLabel.setMinWidth(65);
			livebirthTypeLabel.setMaxWidth(65);
			livebirthHBox.getChildren().add(livebirthTypeLabel);
			// livebirthHBox.getChildren().add(printTemplate.createDynamicLabel(
			// LiveBirthType.getEnumByKey(pregnancyOutcomesDto.getLivebirthOption()).toString()));
			livebirthHBox.getChildren().add(printTemplate.createDynamicLabel(
					Util.formatDate(IConstants.DATE_FORMAT, pregnancyOutcomesDto.getLivebirthDate())));
			vBox.getChildren().addAll(cycleStartDate, unuseSpace, pregnancyHeaderHBox, biochemicalHBox, clinicalHBox,
					livebirthHBox);

		}

		return vBox;
	}

	private VBox createPregnancyOutcomeRemarks(PageLayout pageLayout) {
		VBox vBox = null;
		if (null != pregnancyOutcomesDto && null != pregnancyOutcomesDto.getRemarks()) {
			vBox = new VBox();
			vBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
			vBox.getChildren().add(printTemplate.createHeaderLabel(" " + remarksHeader));
			vBox.setPrefHeight(280);
			// vBox.setMaxHeight(200);
			HBox remarksHBox = printTemplate.createTextArea(pregnancyOutcomesDto.getRemarks(), pageLayout);
			remarksHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);

			vBox.getChildren().add(remarksHBox);
		}
		return vBox;
	}

	public void makeFieldsNonEditable() {
		biochemicalDateLabel.setEditable(false);
		clinicalDateLabel.setEditable(false);
		liveBirthDateLabel.setEditable(false);
		remarksTextArea.setEditable(false);
	}

	private void resetFields() {
		biochemicalComboBox.setItems(null);
		clinicalComboBox.setItems(null);
		liveBirthComboBox.setItems(null);
		remarksTextArea.setText(IConstants.emptyString);
		biochemicalDateLabel.setText(IConstants.emptyString);
		clinicalDateLabel.setText(IConstants.emptyString);
		liveBirthDateLabel.setText(IConstants.emptyString);
		nurseRegistrant.setText(IConstants.emptyString);
		asstNurseRegistrant.setText(IConstants.emptyString);
	}

}
