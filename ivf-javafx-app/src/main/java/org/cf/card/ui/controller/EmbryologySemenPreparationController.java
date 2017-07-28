package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.cf.card.dto.RegistrantDto;
import org.cf.card.dto.SemenDataDto;
import org.cf.card.dto.SemenDto;
import org.cf.card.model.Semen;
import org.cf.card.model.SemenCode;
import org.cf.card.model.SemenData;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.service.UISemenService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.EnumSemen.UseSemenType;
import org.cf.card.util.Enumeration;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.util.StringUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EmbryologySemenPreparationController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitleText = MessageResource.getText("mainpage.title.embryology");
	private static final String titlrdescriptions = MessageResource
			.getText("mainpage.title.embryology.description.semen");
	private static final String iconURL = "/icons/embryology.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	private static final String andrologysemenAB = MessageResource.getText("andrologysemen.textfield.aplusb");
	private static final String andrologysemenABC = MessageResource.getText("andrologysemen.textfield.aplusbplusc");
	private static final String semenPreaparationHeader = MessageResource.getText("embryology.button.semenprepration");
	private static final String prepareSemenHeader = MessageResource
			.getText("embryology.semenprepration.label.preparedsemen");
	private static final String rawSemenHeader = MessageResource.getText("andrologysemen.label.rawsemen");
	private static final String volumeLabel = MessageResource.getText("embryology.semenprepration.label.volume");
	private static final String densityLabel = MessageResource.getText("andrologysemen.text.density");
	private static final String totalmolalityLabel = MessageResource.getText("andrologysemen.text.totalmolality");
	private static final String motilityALabel = MessageResource.getText("andrologysemen.text.a");
	private static final String motilityBLabel = MessageResource.getText("andrologysemen.text.b");
	private static final String motilityCLabel = MessageResource.getText("andrologysemen.text.c");
	private static final String motilityDLabel = MessageResource.getText("andrologysemen.text.d");
	private static final String morphologyLabel = MessageResource.getText("andrologysemen.text.morphology");
	private static final String roundcellsLabel = MessageResource.getText("andrologysemen.text.roundcells");
	private static final String timeProcessedLabel = MessageResource.getText("andrologysemen.label.timeprocessed");
	private static final String timeProducedLabel = MessageResource.getText("andrologysemen.label.timeproduced");
	private static final String REMARKS_NOT_EMPTY = MessageResource.getText("andrologysemen.label.remarks.not.empty");
	private static final String ANALYSISDATE = MessageResource.getText("andrologysemen.label.analysis.date");
	private static final String errorMessage = MessageResource.getText("embryology.semen.controller.error.message");

	// binding fxml element
	@FXML
	private AnchorPane embryologySemenPrepAnchorPane;

	@FXML
	private ComboBox<UseSemenType> useComboBox;

	@FXML
	private ComboBox<Integer> viscosityComboBox;

	@FXML
	private ComboBox<Integer> debrisComboBox;

	@FXML
	private Button editBtn;

	@FXML
	private ComboBox<Integer> agglutinationComboBox;

	@FXML
	private ComboBox<Integer> aggregationComboBox;

	@FXML
	private TextField mediaAddedTextField;

	@FXML
	private TextField volumeRawSemenTextField;

	@FXML
	private TextField morphologyRawSemenTextField;

	@FXML
	private TextField roundCellsRawSemenTextField;

	@FXML
	private TextField densityRawSemenTextField;

	@FXML
	private TextField motilityARawSemenTextField;

	@FXML
	private TextField motilityBRawSemenTextField;

	@FXML
	private TextField motilityCRawSemenTextField;

	@FXML
	private TextField motilityDRawSemenTextField;

	@FXML
	private TextField motilityAPreparedSemenTextField;

	@FXML
	private TextField motilityBPreparedSemenTextField;

	@FXML
	private TextField motilityCPreparedSemenTextField;

	@FXML
	private TextField motilityDPreparedSemenTextField;

	@FXML
	private TextField densityPreparedSemenTextField;

	@FXML
	private TextField volumePreparedSemenTextField;

	@FXML
	private TextField morphologyPreparedSemenTextField;

	@FXML
	private TextField roundCellsPreparedSemenTextField;

	@FXML
	private TextField totalMotilityAB_RawSemenTextField;

	@FXML
	private TextField totalMotilityABC_RawSemenTextField;

	@FXML
	private TextField totalMotilityAB_PreparedSemenTextField;

	@FXML
	private TextField totalMotilityABC_PreparedSemenTextField;

	@FXML
	private TextField timeProcessedTextField;

	@FXML
	private TextField timeProducedTextField;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private Label administratorWarningLabel;

	@FXML
	private TextField embRegistrant;

	@FXML
	private TextField drRegistrant;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private InvestigationCommonController investigationCommonController;

	// creating instance variables(class level)
	@SuppressWarnings("unused")
	private Long semenId = null;
	private PrintTemplate<?> printTemplate = new PrintTemplate<>();
	private Semen semen = null;
	private Date dateOfAnalysis = null;
	private RegistrantDto registrantDto;
	private int SCREEN_ID = Module.SEMEN_PREPRATION.getKey();

	// creating object
	UISemenService UISemenService = new UISemenService();
	UICoupleService coupleService = new UICoupleService();
	UISemenService uiSemenService = new UISemenService();
	UIRemarksService uiRemarksService = new UIRemarksService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	@FXML
	private void initialize() {

		restrictTextFieldToDecimal(volumeRawSemenTextField);
		restrictTextFields(motilityARawSemenTextField);
		restrictTextFields(motilityBRawSemenTextField);
		restrictTextFields(motilityCRawSemenTextField);
		restrictTextFields(motilityDRawSemenTextField);
		restrictTextFields(roundCellsRawSemenTextField);

		restrictTextFieldToDecimal(volumePreparedSemenTextField);
		restrictTextFields(motilityAPreparedSemenTextField);
		restrictTextFields(motilityBPreparedSemenTextField);
		restrictTextFields(motilityCPreparedSemenTextField);
		restrictTextFields(motilityDPreparedSemenTextField);
		restrictTextFields(roundCellsPreparedSemenTextField);

		restrictTextFieldToDecimal(mediaAddedTextField);
		restrictTextFieldToDecimal(densityRawSemenTextField);
		restrictTextFieldToDecimal(morphologyRawSemenTextField);
		restrictTextFieldToDecimal(densityPreparedSemenTextField);
		restrictTextFieldToDecimal(morphologyPreparedSemenTextField);

		ChangeListener<String> rawSemenTextListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int rMotilityA = motilityARawSemenTextField.getText() != null
						&& !motilityARawSemenTextField.getText().isEmpty()
								? Integer.parseInt(motilityARawSemenTextField.getText()) : 0;
				int rMotilityB = motilityBRawSemenTextField.getText() != null
						&& !motilityBRawSemenTextField.getText().isEmpty()
								? Integer.parseInt(motilityBRawSemenTextField.getText()) : 0;
				int rMotilityC = motilityCRawSemenTextField.getText() != null
						&& !motilityCRawSemenTextField.getText().isEmpty()
								? Integer.parseInt(motilityCRawSemenTextField.getText()) : 0;
				int rtotalMotilityAB = rMotilityA + rMotilityB;
				totalMotilityAB_RawSemenTextField.setText(andrologysemenAB + rtotalMotilityAB);
				int rTotalMotilityABC = rMotilityA + rMotilityB + rMotilityC;
				totalMotilityABC_RawSemenTextField.setText(andrologysemenABC + rTotalMotilityABC);
			}
		};

		this.motilityARawSemenTextField.textProperty().addListener(rawSemenTextListener);
		this.motilityBRawSemenTextField.textProperty().addListener(rawSemenTextListener);
		this.motilityCRawSemenTextField.textProperty().addListener(rawSemenTextListener);

		ChangeListener<String> preparedSemenTextListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int pMotilityA = motilityAPreparedSemenTextField.getText() != null
						&& !motilityAPreparedSemenTextField.getText().isEmpty()
								? Integer.parseInt(motilityAPreparedSemenTextField.getText()) : 0;
				int pMotilityB = motilityBPreparedSemenTextField.getText() != null
						&& !motilityBPreparedSemenTextField.getText().isEmpty()
								? Integer.parseInt(motilityBPreparedSemenTextField.getText()) : 0;
				int pMotilityC = motilityCPreparedSemenTextField.getText() != null
						&& !motilityCPreparedSemenTextField.getText().isEmpty()
								? Integer.parseInt(motilityCPreparedSemenTextField.getText()) : 0;
				int pTotalMotilityAB = pMotilityA + pMotilityB;
				totalMotilityAB_PreparedSemenTextField.setText(andrologysemenAB + " " + pTotalMotilityAB);
				int ptotalMotilityABC = pMotilityA + pMotilityB + pMotilityC;
				totalMotilityABC_PreparedSemenTextField.setText(andrologysemenABC + " " + ptotalMotilityABC);
			}
		};

		this.motilityAPreparedSemenTextField.textProperty().addListener(preparedSemenTextListener);
		this.motilityBPreparedSemenTextField.textProperty().addListener(preparedSemenTextListener);
		this.motilityCPreparedSemenTextField.textProperty().addListener(preparedSemenTextListener);

		// UISemenService.fillUseComboBox(useComboBox);
		ObservableList<UseSemenType> useData = FXCollections.observableArrayList(UseSemenType.values());
		useComboBox.setItems(useData);
		UISemenService.fillViscosityComboBox(viscosityComboBox);
		UISemenService.fillViscosityComboBox(debrisComboBox);
		UISemenService.fillAgglutinationComboBox(agglutinationComboBox);
		UISemenService.fillAgglutinationComboBox(aggregationComboBox);
		makeFieldsNonEditable();

	}

	class Delta {
		double x, y;
	}

	public void makeFieldsNonEditable() {
		volumeRawSemenTextField.setEditable(false);
		densityRawSemenTextField.setEditable(false);
		motilityARawSemenTextField.setEditable(false);
		motilityBRawSemenTextField.setEditable(false);
		motilityCRawSemenTextField.setEditable(false);
		motilityDRawSemenTextField.setEditable(false);
		morphologyRawSemenTextField.setEditable(false);
		roundCellsRawSemenTextField.setEditable(false);

		volumePreparedSemenTextField.setEditable(false);
		densityPreparedSemenTextField.setEditable(false);
		motilityAPreparedSemenTextField.setEditable(false);
		motilityBPreparedSemenTextField.setEditable(false);
		motilityCPreparedSemenTextField.setEditable(false);
		motilityDPreparedSemenTextField.setEditable(false);
		morphologyPreparedSemenTextField.setEditable(false);
		roundCellsPreparedSemenTextField.setEditable(false);

		timeProcessedTextField.setEditable(false);
		timeProducedTextField.setEditable(false);
		remarksTextArea.setEditable(false);
		mediaAddedTextField.setEditable(false);
	}

	public void buildData() {
		resetFields();

		/*
		 * remarksDto = uiRemarksService.getRemarksByCodeId(womanCode.getId(),
		 * remarksType); if (null != remarksDto)
		 * remarksTextArea.setText(remarksDto.getRemarksText());
		 */

		@SuppressWarnings("unchecked")
		SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
		long sessionStatus = sessionObject.getComponent(Constants.IS_CURRUNT_CYCLE);
		if (sessionStatus != 1l) {
			editBtn.setDisable(true);
		} else {
			editBtn.setDisable(false);
		}

		makeFieldsNonEditable();
		totalMotilityAB_RawSemenTextField.setText(andrologysemenAB);
		totalMotilityABC_RawSemenTextField.setText(andrologysemenABC);
		totalMotilityAB_PreparedSemenTextField.setText(andrologysemenAB);
		totalMotilityABC_PreparedSemenTextField.setText(andrologysemenABC);
		totalMotilityAB_RawSemenTextField.setEditable(false);
		totalMotilityABC_RawSemenTextField.setEditable(false);
		totalMotilityAB_PreparedSemenTextField.setEditable(false);
		totalMotilityABC_PreparedSemenTextField.setEditable(false);
		useComboBox.getSelectionModel().select(0);
		viscosityComboBox.getSelectionModel().select(0);
		debrisComboBox.getSelectionModel().select(0);
		agglutinationComboBox.getSelectionModel().select(0);
		aggregationComboBox.getSelectionModel().select(0);
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setBackgroundColor(true);
		// commonDetailController.setCouple(couple);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		investigationCommonController.setCouple(couple);
		investigationCommonController.setManCode(manCode);
		investigationCommonController.setWomanCode(womanCode);
		investigationCommonController.build();
		if (manCode != null) {

			registrantDto = uiRegistrantService.getRegistrantByCodeAndScreenID(womanCode.getId(), SCREEN_ID);
			if (null != registrantDto) {
				embRegistrant.setText(registrantDto.getNameOfUser());
				drRegistrant.setText(registrantDto.getAssistantUser());
			}
			// fetching existing values from DB

			// List<SemenCode> semenCodeList =
			// UISemenService.getSemenCode(manCode.getId());
			List<SemenCode> semenCodeList = UISemenService.findSemenCodeByCodeIdAndScreenId(manCode.getId(),
					Module.SEMEN_PREPRATION.getKey());
			if (semenCodeList != null && semenCodeList.size() > 0) {
				SemenCode semenCode = semenCodeList.iterator().next();
				semen = semenCode.getSemen();
				dateOfAnalysis = semen.getCreatedDate();
				// Semen semen = semenCodeList.get(0).getSemen();
				if (semen != null) {
					useComboBox.setValue(UseSemenType.getEnumByKey(semen.getUse()));
					viscosityComboBox.setValue(semen.getViscosity());
					agglutinationComboBox.setValue(semen.getAgglutination());
					mediaAddedTextField.setText(String.valueOf(semen.getMediaAdded()));
					debrisComboBox.setValue(semen.getDebris());
					aggregationComboBox.setValue(semen.getAggregation());
					timeProcessedTextField.setText(semen.getTimeProcessed());
					timeProducedTextField.setText(semen.getTimeProduced());
					remarksTextArea.setText(semen.getRemark());
					semenId = semen.getId();
					Set<SemenData> semenDataSet = semen.getSemenDatas();

					for (SemenData semenData : semenDataSet) {

						if (semenData.getType() == (Enumeration.SemenType.RAW.getKey())) {
							volumeRawSemenTextField.setText(String.valueOf(semenData.getVolume()));
							densityRawSemenTextField.setText(String.valueOf((int) semenData.getDensity()));
							motilityARawSemenTextField.setText(String.valueOf(semenData.getMotilityA()));
							motilityBRawSemenTextField.setText(String.valueOf(semenData.getMotilityB()));
							motilityCRawSemenTextField.setText(String.valueOf(semenData.getMotilityC()));
							motilityDRawSemenTextField.setText(String.valueOf(semenData.getMotilityD()));
							morphologyRawSemenTextField.setText(String.valueOf(semenData.getMorphology()));
							roundCellsRawSemenTextField.setText(String.valueOf(semenData.getRoundCell()));
						}

						else if (semenData.getType() == (Enumeration.SemenType.PREPARED.getKey())) {
							volumePreparedSemenTextField.setText(String.valueOf(semenData.getVolume()));
							densityPreparedSemenTextField.setText(String.valueOf((int) semenData.getDensity()));
							motilityAPreparedSemenTextField.setText(String.valueOf(semenData.getMotilityA()));
							motilityBPreparedSemenTextField.setText(String.valueOf(semenData.getMotilityB()));
							motilityCPreparedSemenTextField.setText(String.valueOf(semenData.getMotilityC()));
							motilityDPreparedSemenTextField.setText(String.valueOf(semenData.getMotilityD()));
							morphologyPreparedSemenTextField.setText(String.valueOf(semenData.getMorphology()));
							roundCellsPreparedSemenTextField.setText(String.valueOf(semenData.getRoundCell()));
						}
					}
				}
			} else {
				semen = null;
			}
		}
	}

	@FXML
	public void editAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.SEMEN_PREPRATION.getKey()))
			makeFieldsEditable();
		else
			FileUtils.privillegeEditError();

	}

	@FXML
	public void saveAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.SEMEN_PREPRATION.getKey())) {

			int index = 1;
			boolean valid = validateFields();

			if (valid == true) {

				List<SemenDataDto> semenDataList = new ArrayList<SemenDataDto>();
				semenDataList.add(rawSemenDataDto());
				semenDataList.add(preparedSemenDataDto());

				SemenDto semenDto = new SemenDto();
				semenDto.setUse(useComboBox.getSelectionModel().getSelectedItem().getKey());
				semenDto.setViscosity(viscosityComboBox.getSelectionModel().getSelectedIndex());
				semenDto.setAgglutination(agglutinationComboBox.getSelectionModel().getSelectedIndex());
				float mediaAdded = mediaAddedTextField.getText() != null && !mediaAddedTextField.getText().isEmpty()
						? Float.parseFloat(mediaAddedTextField.getText()) : 0;
				semenDto.setMediaAdded(mediaAdded);
				semenDto.setDebris(debrisComboBox.getSelectionModel().getSelectedIndex());
				semenDto.setAggregation(aggregationComboBox.getSelectionModel().getSelectedIndex());
				semenDto.setScreen(Module.SEMEN_PREPRATION.getKey());
				semenDto.setTimeProcessed(timeProcessedTextField.getText());
				semenDto.setTimeProduced(timeProducedTextField.getText());

				if (semen != null) {
					semenDto.setSemenId(semen.getId());
					semenDto.setCreatedDate(semen.getCreatedDate());
				}
				
				semenDto.setSemenDataList(semenDataList);
				semenDto.setCodeId(manCode.getId());
				semenDto.setIndex(index);

				if (!StringUtils.isEmpty(remarksTextArea.getText())) {
					semenDto.setRemarks(remarksTextArea.getText());
					Semen savedSemen = uiSemenService.addSemen(semenDto);
					semenId = savedSemen.getId();
					// updating current couple object
					couple = coupleService.getCoupleById(couple.getId());
					buildData();
					makeFieldsNonEditable();
				} else {
					Notify alert = new Notify(AlertType.ERROR, REMARKS_NOT_EMPTY);
					alert.showAndWait();
				}

			} else {
				Notify alert = new Notify(AlertType.ERROR, errorMessage);
				alert.showAndWait();
			}
		} else
			FileUtils.privillegeEditError();
	}

	/**
	 * Save registrant.
	 */
	@FXML
	private void saveRegistrant() {
		registrantDto = FileUtils.saveOrUpdateRegistrant(registrantDto, womanCode.getId(), SCREEN_ID, embRegistrant,
				drRegistrant);
	}

	/**
	 * Prints the page action.
	 */
	@FXML
	private void printPageAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		// Getting all center nodes
		VBox allCenterNodes = createSemenAnalysisAndRawSemen(pageLayout);
		int page = 1;
		if (allCenterNodes != null && allCenterNodes.getChildren().size() > 0) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				BorderPane printPage = createPrintPage(page, allCenterNodes, pageLayout);
				printerJob.printPage(printPage);
			}
			printerJob.endJob();
		} else {
			Notify alert = new Notify(AlertType.WARNING, noDataAvailableMessage);
			alert.showAndWait();
		}

	}

	// Creating here All Node with ConboBoxes, SEMEN PREPARATION,RAW SEMEN,
	// PREPARED SEMEN and REMARKS
	private VBox createSemenAnalysisAndRawSemen(PageLayout pageLayout) {

		// This VBOX include all nodes
		VBox VBoxParent = new VBox();
		VBoxParent.setPrefWidth(pageLayout.getPrintableWidth());
		VBoxParent.setPrefHeight(pageLayout.getPrintableHeight() - 200);
		// top-right-bottom-left
		VBoxParent.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);

		// This Section for Use, Viscosity, Agglutination and others.
		List<String> values = new LinkedList<>();
		if(null!=semen){
			values.add("  Use 		   :");
			values.add(String.valueOf(UseSemenType.getEnumByKey(semen.getUse())));
			values.add("  Viscosity    :");
			values.add(String.valueOf(semen.getViscosity()));
			values.add("  Agglutination:");
			values.add(String.valueOf(semen.getAgglutination()));
			values.add("  Media Added  :");
			values.add(String.valueOf(semen.getMediaAdded()));
			values.add("  Debris        :");
			values.add(String.valueOf(semen.getDebris()));
			values.add("  Aggregation  :");
			values.add(String.valueOf(semen.getAggregation()));
		}
		VBox vBoxWithComboBox = printTemplate.createComboBoxNodes(pageLayout, values);
		Label spaceBetweenElements1 = printTemplate.spaceBetweenElements(16);

		// HBox for Headers(SEMEN PREPARATION,RAW SEMEN and PREPARED SEMEN )
		HBox hboxForSemenRawAndPrepared = new HBox();
		hboxForSemenRawAndPrepared.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		hboxForSemenRawAndPrepared.setPrefWidth(pageLayout.getPrintableWidth());
		hboxForSemenRawAndPrepared.setSpacing(75);
		hboxForSemenRawAndPrepared.getChildren().add(printTemplate.createHeaderLabel(semenPreaparationHeader));
		hboxForSemenRawAndPrepared.getChildren().add(printTemplate.createHeaderLabel(rawSemenHeader));
		hboxForSemenRawAndPrepared.getChildren().add(printTemplate.createHeaderLabel(prepareSemenHeader));

		// Grid for SEMEN PREPARATION,RAW SEMEN and PREPARED SEMEN
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		gridPane.setPrefWidth(pageLayout.getPrintableWidth());
		ColumnConstraints constraintsOne = new ColumnConstraints();
		constraintsOne.setPercentWidth(1);
		ColumnConstraints constraintsTwo = new ColumnConstraints();
		constraintsTwo.setPercentWidth(39);
		ColumnConstraints constraintsThree = new ColumnConstraints();
		constraintsThree.setPercentWidth(30);
		ColumnConstraints constraintsFour = new ColumnConstraints();
		constraintsFour.setPercentWidth(30);
		gridPane.getColumnConstraints().addAll(constraintsOne, constraintsTwo, constraintsThree, constraintsFour);

		// Creating static label for SEMEN PREPARATION
		gridPane.add(printTemplate.createStaticLabel(volumeLabel), 1, 0);
		gridPane.add(printTemplate.createStaticLabel(densityLabel), 1, 1);
		gridPane.add(printTemplate.createStaticLabel(totalmolalityLabel), 1, 2);
		gridPane.add(printTemplate.createStaticLabel(motilityALabel), 1, 4);
		gridPane.add(printTemplate.createStaticLabel(motilityBLabel), 1, 5);
		gridPane.add(printTemplate.createStaticLabel(motilityCLabel), 1, 6);
		gridPane.add(printTemplate.createStaticLabel(motilityDLabel), 1, 7);
		gridPane.add(printTemplate.createStaticLabel(morphologyLabel), 1, 8);
		gridPane.add(printTemplate.createStaticLabel(roundcellsLabel), 1, 9);

		// Creating Dynamic label for RAW SEMEN
		gridPane.add(printTemplate.createDynamicLabel(volumeRawSemenTextField.getText()), 2, 0);
		gridPane.add(printTemplate.createDynamicLabel(densityRawSemenTextField.getText()), 2, 1);
		gridPane.add(printTemplate.createDynamicLabel(totalMotilityABC_RawSemenTextField.getText()), 2, 2);
		gridPane.add(printTemplate.createDynamicLabel(totalMotilityAB_RawSemenTextField.getText()), 2, 3);
		gridPane.add(printTemplate.createDynamicLabel(motilityARawSemenTextField.getText()), 2, 4);
		gridPane.add(printTemplate.createDynamicLabel(motilityBRawSemenTextField.getText()), 2, 5);
		gridPane.add(printTemplate.createDynamicLabel(motilityCRawSemenTextField.getText()), 2, 6);
		gridPane.add(printTemplate.createDynamicLabel(motilityDRawSemenTextField.getText()), 2, 7);
		gridPane.add(printTemplate.createDynamicLabel(morphologyRawSemenTextField.getText()), 2, 8);
		gridPane.add(printTemplate.createDynamicLabel(roundCellsRawSemenTextField.getText()), 2, 9);

		// Creating Dynamic label for PREPARED SEMEN
		gridPane.add(printTemplate.createDynamicLabel(volumePreparedSemenTextField.getText()), 3, 0);
		gridPane.add(printTemplate.createDynamicLabel(densityPreparedSemenTextField.getText()), 3, 1);
		gridPane.add(printTemplate.createDynamicLabel(totalMotilityABC_PreparedSemenTextField.getText()), 3, 2);
		gridPane.add(printTemplate.createDynamicLabel(totalMotilityAB_PreparedSemenTextField.getText()), 3, 3);
		gridPane.add(printTemplate.createDynamicLabel(motilityAPreparedSemenTextField.getText()), 3, 4);
		gridPane.add(printTemplate.createDynamicLabel(motilityBPreparedSemenTextField.getText()), 3, 5);
		gridPane.add(printTemplate.createDynamicLabel(motilityCPreparedSemenTextField.getText()), 3, 6);
		gridPane.add(printTemplate.createDynamicLabel(motilityDPreparedSemenTextField.getText()), 3, 7);
		gridPane.add(printTemplate.createDynamicLabel(morphologyPreparedSemenTextField.getText()), 3, 8);
		gridPane.add(printTemplate.createDynamicLabel(roundCellsPreparedSemenTextField.getText()), 3, 9);
		Label spaceBetweenElements2 = printTemplate.spaceBetweenElements(16);

		// This Section for TIME PROCESSED, TIME PRODUCED and ANALYSIS DATE.
		List<String> timeValues = new ArrayList<>();
		timeValues.add(" " + timeProcessedLabel);
		timeValues.add(timeProcessedTextField.getText());
		timeValues.add(timeProducedLabel);
		timeValues.add(timeProducedTextField.getText());
		timeValues.add(ANALYSISDATE);
		timeValues.add(Util.formatDate(IConstants.DATE_FORMAT, dateOfAnalysis));
		VBox timeVBox = printTemplate.createComboBoxNodes(pageLayout, timeValues);
		timeVBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		timeVBox.setMinHeight(50);
		Label spaceBetweenElements3 = printTemplate.spaceBetweenElements(16);

		// This Section for Registrant Details.
		VBox registrantDetailVBox = printTemplate.createRegistrantCommonInfo(pageLayout, registrantDto,
				MessageResource.getText("common.emb.registrant"), MessageResource.getText("common.dr.registrant"));
		Label spaceBetweenElements4 = printTemplate.spaceBetweenElements(16);

		// This Section for REMARKS.
		Node remarksNode = null;
		if (remarksTextArea.getText() != null && !remarksTextArea.getText().isEmpty()) {
			List<Node> remarksNodeList = printTemplate.createRemarks(remarksTextArea.getText(), pageLayout);
			for (Node node : remarksNodeList) {
				remarksNode = node;
			}
			remarksNode.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		}
		if (null != remarksNode) {
			VBoxParent.getChildren().addAll(vBoxWithComboBox, spaceBetweenElements1, hboxForSemenRawAndPrepared,
					gridPane, spaceBetweenElements2, timeVBox, spaceBetweenElements3, registrantDetailVBox,
					spaceBetweenElements4, remarksNode);
		} else {
			VBoxParent = null;
		}
		return VBoxParent;

	}

	// Final creating full page for printing
	private BorderPane createPrintPage(int page, Node node, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());

		HBox headerHbox = printTemplate.createHeader(mainPageTitleText, iconURL, titlrdescriptions, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		VBox unuseSpace = new VBox();
		unuseSpace.setPrefHeight(20);
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		contentVBox.getChildren().addAll(patientGrid, unuseSpace, node);
		root.setCenter(contentVBox);
		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	/**
	 * Creating Raw semen data dto from textfields.
	 *
	 * @return the semen data dto
	 */
	SemenDataDto rawSemenDataDto() {

		SemenDataDto rawSemenDataDto = new SemenDataDto();
		float rawVolume = volumeRawSemenTextField.getText() != null && !volumeRawSemenTextField.getText().isEmpty()
				? Float.parseFloat(volumeRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setVolume(rawVolume);
		float rDensity = densityRawSemenTextField.getText() != null && !densityRawSemenTextField.getText().isEmpty()
				? Float.parseFloat(densityRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setDensity(rDensity);
		int rMotilityA = motilityARawSemenTextField.getText() != null && !motilityARawSemenTextField.getText().isEmpty()
				? Integer.parseInt(motilityARawSemenTextField.getText()) : 0;
		rawSemenDataDto.setMotilityA(rMotilityA);
		int rMotilityB = motilityBRawSemenTextField.getText() != null && !motilityBRawSemenTextField.getText().isEmpty()
				? Integer.parseInt(motilityBRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setMotilityB(rMotilityB);
		int rMotilityC = motilityCRawSemenTextField.getText() != null && !motilityCRawSemenTextField.getText().isEmpty()
				? Integer.parseInt(motilityCRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setMotilityC(rMotilityC);
		int rMotilityD = motilityDRawSemenTextField.getText() != null && !motilityDRawSemenTextField.getText().isEmpty()
				? Integer.parseInt(motilityDRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setMotilityD(rMotilityD);
		float rMorphology = morphologyRawSemenTextField.getText() != null
				&& !morphologyRawSemenTextField.getText().isEmpty()
						? Float.parseFloat(morphologyRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setMorphology(rMorphology);
		int rRoundCells = roundCellsRawSemenTextField.getText() != null
				&& !roundCellsRawSemenTextField.getText().isEmpty()
						? Integer.parseInt(roundCellsRawSemenTextField.getText()) : 0;
		rawSemenDataDto.setRoundCell(rRoundCells);
		rawSemenDataDto.setType(Enumeration.SemenType.RAW.getKey());

		return rawSemenDataDto;

	}

	/**
	 * Creating Prepared semen data dto from UI textfields.
	 *
	 * @return the semen data dto
	 */
	SemenDataDto preparedSemenDataDto() {

		SemenDataDto preparedSemenDataDto = new SemenDataDto();
		float pVolume = volumePreparedSemenTextField.getText() != null
				&& !volumePreparedSemenTextField.getText().isEmpty()
						? Float.parseFloat(volumePreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setVolume(pVolume);
		float pDensity = densityPreparedSemenTextField.getText() != null
				&& !densityPreparedSemenTextField.getText().isEmpty()
						? Float.parseFloat(densityPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setDensity(pDensity);
		int pMotilityA = motilityAPreparedSemenTextField.getText() != null
				&& !motilityAPreparedSemenTextField.getText().isEmpty()
						? Integer.parseInt(motilityAPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setMotilityA(pMotilityA);
		int pMotilityB = motilityBPreparedSemenTextField.getText() != null
				&& !motilityBPreparedSemenTextField.getText().isEmpty()
						? Integer.parseInt(motilityBPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setMotilityB(pMotilityB);
		int pMotilityC = motilityCPreparedSemenTextField.getText() != null
				&& !motilityCPreparedSemenTextField.getText().isEmpty()
						? Integer.parseInt(motilityCPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setMotilityC(pMotilityC);
		int pMotilityD = motilityDPreparedSemenTextField.getText() != null
				&& !motilityDPreparedSemenTextField.getText().isEmpty()
						? Integer.parseInt(motilityDPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setMotilityD(pMotilityD);
		float pMorphology = morphologyPreparedSemenTextField.getText() != null
				&& !morphologyPreparedSemenTextField.getText().isEmpty()
						? Float.parseFloat(morphologyPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setMorphology(pMorphology);
		int pRoundCells = roundCellsPreparedSemenTextField.getText() != null
				&& !roundCellsPreparedSemenTextField.getText().isEmpty()
						? Integer.parseInt(roundCellsPreparedSemenTextField.getText()) : 0;
		preparedSemenDataDto.setRoundCell(pRoundCells);
		preparedSemenDataDto.setType(Enumeration.SemenType.PREPARED.getKey());

		return preparedSemenDataDto;

	}

	public void makeFieldsEditable() {
		volumeRawSemenTextField.setEditable(true);
		densityRawSemenTextField.setEditable(true);
		motilityARawSemenTextField.setEditable(true);
		motilityBRawSemenTextField.setEditable(true);
		motilityCRawSemenTextField.setEditable(true);
		motilityDRawSemenTextField.setEditable(true);
		morphologyRawSemenTextField.setEditable(true);
		roundCellsRawSemenTextField.setEditable(true);

		volumePreparedSemenTextField.setEditable(true);
		densityPreparedSemenTextField.setEditable(true);
		motilityAPreparedSemenTextField.setEditable(true);
		motilityBPreparedSemenTextField.setEditable(true);
		motilityCPreparedSemenTextField.setEditable(true);
		motilityDPreparedSemenTextField.setEditable(true);
		morphologyPreparedSemenTextField.setEditable(true);
		roundCellsPreparedSemenTextField.setEditable(true);

		timeProcessedTextField.setEditable(true);
		timeProducedTextField.setEditable(true);
		remarksTextArea.setEditable(true);
		mediaAddedTextField.setEditable(true);

	}

	public void restrictTextFields(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (Pattern.matches(".*\\D.*", newValue)) {
					textField.setText(oldValue);
				}
			}
		});
	}

	public void restrictTextFieldToDecimal(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!Pattern.matches("-?((\\d*)|(\\d+\\.\\d*))", newValue)) {
					textField.setText(oldValue);
				}
			}
		});
	}

	public boolean validateFields() {
		boolean valid = true;

		if (useComboBox.getSelectionModel().getSelectedIndex() == 0
				&& viscosityComboBox.getSelectionModel().getSelectedIndex() == 0
				&& agglutinationComboBox.getSelectionModel().getSelectedIndex() == 0
				&& debrisComboBox.getSelectionModel().getSelectedIndex() == 0
				&& aggregationComboBox.getSelectionModel().getSelectedIndex() == 0
				&& mediaAddedTextField.getText().equals(IConstants.emptyString)
				&& volumeRawSemenTextField.getText().equals(IConstants.emptyString)
				&& densityRawSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityARawSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityBRawSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityCRawSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityDRawSemenTextField.getText().equals(IConstants.emptyString)
				&& morphologyRawSemenTextField.getText().equals(IConstants.emptyString)
				&& roundCellsRawSemenTextField.getText().equals(IConstants.emptyString)
				&& volumePreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& densityPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityAPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityBPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityCPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& motilityDPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& morphologyPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& roundCellsPreparedSemenTextField.getText().equals(IConstants.emptyString)
				&& timeProcessedTextField.getText().equals(IConstants.emptyString)
				&& timeProducedTextField.getText().equals(IConstants.emptyString))
			valid = false;

		return valid;
	}

	private void resetFields() {
		useComboBox.getSelectionModel().select(0);
		viscosityComboBox.getSelectionModel().select(0);
		aggregationComboBox.getSelectionModel().select(0);
		agglutinationComboBox.getSelectionModel().select(0);
		debrisComboBox.getSelectionModel().select(0);
		mediaAddedTextField.setText(IConstants.emptyString);
		volumeRawSemenTextField.setText(IConstants.emptyString);
		volumePreparedSemenTextField.setText(IConstants.emptyString);
		densityRawSemenTextField.setText(IConstants.emptyString);
		densityPreparedSemenTextField.setText(IConstants.emptyString);
		totalMotilityAB_RawSemenTextField.setText(IConstants.emptyString);
		totalMotilityAB_PreparedSemenTextField.setText(IConstants.emptyString);
		totalMotilityABC_RawSemenTextField.setText(IConstants.emptyString);
		totalMotilityABC_PreparedSemenTextField.setText(IConstants.emptyString);
		motilityARawSemenTextField.setText(IConstants.emptyString);
		motilityAPreparedSemenTextField.setText(IConstants.emptyString);
		motilityBRawSemenTextField.setText(IConstants.emptyString);
		motilityBPreparedSemenTextField.setText(IConstants.emptyString);
		motilityCRawSemenTextField.setText(IConstants.emptyString);
		motilityCPreparedSemenTextField.setText(IConstants.emptyString);
		motilityDRawSemenTextField.setText(IConstants.emptyString);
		motilityDPreparedSemenTextField.setText(IConstants.emptyString);
		morphologyRawSemenTextField.setText(IConstants.emptyString);
		morphologyPreparedSemenTextField.setText(IConstants.emptyString);
		roundCellsRawSemenTextField.setText(IConstants.emptyString);
		roundCellsPreparedSemenTextField.setText(IConstants.emptyString);
		timeProducedTextField.setText(IConstants.emptyString);
		timeProcessedTextField.setText(IConstants.emptyString);
		remarksTextArea.setText(IConstants.emptyString);
		embRegistrant.setText(IConstants.emptyString);
		drRegistrant.setText(IConstants.emptyString);
	}
}
