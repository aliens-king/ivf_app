package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.dto.SemenDataDto;
import org.cf.card.dto.SemenDto;
import org.cf.card.model.Codes;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.service.UISemenService;
import org.cf.card.ui.service.UITreatmentService;
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

public class AndrologySemenController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitleText = MessageResource.getText("mainpage.title.andrology");
	private static final String iconURL = "/icons/andrology.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String andrologysemenAB = MessageResource.getText("andrologysemen.textfield.aplusb");
	private static final String andrologysemenABC = MessageResource.getText("andrologysemen.textfield.aplusbplusc");
	private static final String andrologysemenError = MessageResource
			.getText("androlodysemen.controller.error.message");
	private static final String semenAnalysisHeader = MessageResource.getText("andrologysemen.label.semenanalysis");
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
	private static final String ANALYSISDATE = MessageResource.getText("andrologysemen.label.analysis.date");
	private static final String REMARKS_NOT_EMPTY = MessageResource.getText("andrologysemen.label.remarks.not.empty");

	private static final String previousDayEditError = MessageResource
			.getText("andrologysemen.controller.error.edit.previous.days");

	private static final int ANDROLOGY_SCREEN_KEY = Module.ANDROLOGY.getKey();

	// creating object
	UISemenService uiSemenService = new UISemenService();
	UICoupleService coupleService = new UICoupleService();
	UITreatmentService uiTreatmentService = new UITreatmentService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();
	private PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element
	@FXML
	private AnchorPane andrologySemenAnchorPane;
	@FXML
	private ComboBox<UseSemenType> useComboBox;

	@FXML
	private ComboBox<Integer> viscosityComboBox;

	@FXML
	private ComboBox<Integer> debrisComboBox;

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
	private TextField totalMotilityAB_RawSemenTextField;

	@FXML
	private TextField totalMotilityABC_RawSemenTextField;

	@FXML
	private TextField timeProcessedTextField;

	@FXML
	private TextField timeProducedTextField;

	@FXML
	private Label dateLabel;

	@FXML
	private Label countLabel;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private InvestigationCommonController investigationCommonController;

	@FXML
	private Label administratorWarningLabel;

	/** The total semen. */
	@FXML
	private TextField totalSemen;

	@FXML
	private Button saveToCryoButton;

	@FXML
	private TextField embRegistrant;
	@FXML
	private TextField drRegistrant;

	// creating instance variables(class level)
	private RegistrantDto registrantDto;
	private int SCREEN_ID = Module.ANDROLOGY.getKey();
	private SemenDto todaySemenDto;
	Map<String, SemenDto> semenMap = new TreeMap<>();

	@FXML
	private void initialize() {
		ObservableList<UseSemenType> data = FXCollections.observableArrayList(UseSemenType.values());
		useComboBox.setItems(data);
		// UISemenService.fillUseComboBox(useComboBox);
		uiSemenService.fillViscosityComboBox(viscosityComboBox);
		uiSemenService.fillDebrisComboBox(debrisComboBox);
		uiSemenService.fillAgglutinationComboBox(agglutinationComboBox);
		uiSemenService.fillAgglutinationComboBox(aggregationComboBox);

		restrictTextFieldToDecimal(volumeRawSemenTextField);
		restrictTextFields(motilityARawSemenTextField);
		restrictTextFields(motilityBRawSemenTextField);
		restrictTextFields(motilityCRawSemenTextField);
		restrictTextFields(motilityDRawSemenTextField);
		restrictTextFields(roundCellsRawSemenTextField);

		restrictTextFieldToDecimal(densityRawSemenTextField);
		restrictTextFieldToDecimal(mediaAddedTextField);
		restrictTextFieldToDecimal(morphologyRawSemenTextField);

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

		totalSemen.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (Pattern.matches(".*\\D.*", newValue)) {
					totalSemen.setText(oldValue);
				}
			}
		});

		makeFieldsNonEditable();
	}

	public void makeFieldsNonEditable() {
		motilityARawSemenTextField.setEditable(false);
		volumeRawSemenTextField.setEditable(false);
		densityRawSemenTextField.setEditable(false);
		motilityBRawSemenTextField.setEditable(false);
		motilityCRawSemenTextField.setEditable(false);
		motilityDRawSemenTextField.setEditable(false);
		morphologyRawSemenTextField.setEditable(false);
		roundCellsRawSemenTextField.setEditable(false);
		remarksTextArea.setEditable(false);
		timeProcessedTextField.setEditable(false);
		timeProducedTextField.setEditable(false);
		mediaAddedTextField.setEditable(false);
	}

	public void buildData() {
		reset();
		makeFieldsNonEditable();
		totalMotilityAB_RawSemenTextField.setText(andrologysemenAB);
		totalMotilityABC_RawSemenTextField.setText(andrologysemenABC);
		totalMotilityAB_RawSemenTextField.setEditable(false);
		totalMotilityABC_RawSemenTextField.setEditable(false);
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		// commonDetailController.setCouple(couple);
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
			Date todayDate = new Date();
			String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, todayDate);
			dateLabel.setText(sCreatedDate);
			semenMap = uiSemenService.findSemenCodeMapByClientIdScreen(manCode.getClient().getId(),
					ANDROLOGY_SCREEN_KEY);
			countLabel.setText(String.valueOf(semenMap.size() > 0 ? semenMap.size() : 1));
			if (null != semenMap && !semenMap.isEmpty()) {

				if (semenMap.containsKey(sCreatedDate)) {
					todaySemenDto = semenMap.get(sCreatedDate);
					// Enable TotalSemen TextField & saveToCryoButton to
					// enter/save data
					totalSemen.setEditable(true);
					saveToCryoButton.setDisable(false);
				} else {
					// If andrology today's data is blank then add data in map
					todaySemenDto = null;
					semenMap.put(sCreatedDate, todaySemenDto);
				}
				setAndrologyData(todaySemenDto);
			} else {
				todaySemenDto = null;
			}
		}
	}

	// used by buildData, previousAction , NextAction
	private void setAndrologyData(SemenDto semenDto) {
		if (null != semenDto) {
			useComboBox.setValue(UseSemenType.getEnumByKey(semenDto.getUse()));
			viscosityComboBox.setValue(semenDto.getViscosity());
			agglutinationComboBox.setValue(semenDto.getAgglutination());
			mediaAddedTextField.setText(String.valueOf(semenDto.getMediaAdded()));
			debrisComboBox.setValue(semenDto.getDebris());
			aggregationComboBox.setValue(semenDto.getAggregation());
			timeProcessedTextField.setText(semenDto.getTimeProcessed());
			timeProducedTextField.setText(semenDto.getTimeProduced());
			remarksTextArea.setText(semenDto.getRemarks());
			// semenId = semen.getId();
			// semenDataSet = semenCodeList.get(0).getSemen().getSemenDatas();
			// semenDataSet = semenDto.getSemenDataList();
			totalSemen.setText(String.valueOf(semenDto.getTotalSemens()));
			List<SemenDataDto> semenDataDtos = semenDto.getSemenDataList();
			for (SemenDataDto semenDataDto : semenDataDtos) {
				// if (semenDataDto.getType() ==
				// (Enumeration.SemenType.RAW.getKey())) {
				volumeRawSemenTextField.setText(String.valueOf(semenDataDto.getVolume()));
				densityRawSemenTextField.setText(String.valueOf(semenDataDto.getDensity()));
				motilityARawSemenTextField.setText(String.valueOf(semenDataDto.getMotilityA()));
				motilityBRawSemenTextField.setText(String.valueOf(semenDataDto.getMotilityB()));
				motilityCRawSemenTextField.setText(String.valueOf(semenDataDto.getMotilityC()));
				motilityDRawSemenTextField.setText(String.valueOf(semenDataDto.getMotilityD()));
				morphologyRawSemenTextField.setText(String.valueOf(semenDataDto.getMorphology()));
				roundCellsRawSemenTextField.setText(String.valueOf(semenDataDto.getRoundCell()));
				// }
			}
		}
	}

	@FXML
	private void previousAction() {
		String sCreatedDate = dateLabel.getText();
		List<String> indexes = new ArrayList<>(semenMap.keySet());
		int dateIndex = indexes.indexOf(sCreatedDate);
		if (dateIndex >= 0) {
			int nextIndex = dateIndex - 1;
			if (nextIndex > -1) {
				SemenDto semenDto = (SemenDto) semenMap.values().toArray()[nextIndex];
				if (null != semenDto) {
					setAndrologyData(semenDto);
					Date createdDate = semenDto.getCreatedDate();
					String cDate = Util.formatDate(IConstants.DATE_FORMAT, createdDate);
					dateLabel.setText(cDate);
					int count = Integer.valueOf(countLabel.getText()) - 1;
					countLabel.setText(String.valueOf(count));
				}
			}
		}
		/** No of straws Non edit able if date is less then current date. */
		String changeLabelDate = dateLabel.getText();
		Date todayDate = new Date();
		sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, todayDate);

		if (!changeLabelDate.equals(sCreatedDate)) {
			totalSemen.setEditable(false);
			saveToCryoButton.setDisable(true);
		}
	}

	@FXML
	private void nextAction() {
		String sCreatedDate = dateLabel.getText();
		List<String> indexes = new ArrayList<>(semenMap.keySet());
		int dateIndex = indexes.indexOf(sCreatedDate);
		if (dateIndex >= 0) {
			int nextIndex = dateIndex + 1;
			if (nextIndex < semenMap.size()) {
				SemenDto semenDto = (SemenDto) semenMap.values().toArray()[nextIndex];
				if (null != semenDto) {
					setAndrologyData(semenDto);
					Date createdDate = semenDto.getCreatedDate();
					String cDate = Util.formatDate(IConstants.DATE_FORMAT, createdDate);
					dateLabel.setText(cDate);
					int count = Integer.valueOf(countLabel.getText()) + 1;
					countLabel.setText(String.valueOf(count));
					/**
					 * No of straws edit able if date is equals to current date.
					 */
					Date todayDate = new Date();
					sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, todayDate);
					if (cDate.equals(sCreatedDate)) {
						totalSemen.setEditable(true);
						saveToCryoButton.setDisable(false);
					}
				} else if (nextIndex == semenMap.size() - 1 && semenDto == null) {
					buildData();
				}
			}
		}
	}

	@FXML
	private void saveAndrologyAction() {
		if (EnumPermission.canWrite(login.getRoleId(), ANDROLOGY_SCREEN_KEY)) {
			int index = 1, cryoVisibility = 1;
			boolean result = validateFields();
			if (result == true) {
				List<SemenDataDto> semenDataList = new ArrayList<SemenDataDto>();
				SemenDataDto rawSemenDataDto = createSemenDataDto();
				semenDataList.add(rawSemenDataDto);

				if (todaySemenDto == null) {
					todaySemenDto = new SemenDto();
				}
				todaySemenDto.setUse(useComboBox.getSelectionModel().getSelectedItem().getKey());
				todaySemenDto.setViscosity(viscosityComboBox.getSelectionModel().getSelectedIndex());
				todaySemenDto.setAgglutination(agglutinationComboBox.getSelectionModel().getSelectedIndex());
				float mediaAdded = mediaAddedTextField.getText() != null && !mediaAddedTextField.getText().isEmpty()
						? Float.parseFloat(mediaAddedTextField.getText()) : 0;
				todaySemenDto.setMediaAdded(mediaAdded);

				todaySemenDto.setDebris(debrisComboBox.getSelectionModel().getSelectedIndex());
				todaySemenDto.setAggregation(aggregationComboBox.getSelectionModel().getSelectedIndex());
				todaySemenDto.setScreen(Module.ANDROLOGY.getKey());
				todaySemenDto.setTimeProcessed(timeProcessedTextField.getText());
				todaySemenDto.setTimeProduced(timeProducedTextField.getText());

				// not getting cryo data (total semens and cryoVisibility) on
				// updating andrology data. So, saving cryo data on updating
				// andro data
				if (!StringUtils.isEmpty(totalSemen.getText()) && totalSemen.getText() != null) {
					int totalSemens = Integer.parseInt(totalSemen.getText());
					if (totalSemens > 0) {
						todaySemenDto.setTotalSemens(totalSemens);
						todaySemenDto.setCryoVisibility(cryoVisibility);
					}
				}

				/*
				 * if (semen != null) { semenDto.setSemenId(semen.getId());
				 * semenDto.setCreatedDate(semen.getCreatedDate()); }
				 */
				todaySemenDto.setSemenDataList(semenDataList);
				todaySemenDto.setCodeId(manCode.getId());
				todaySemenDto.setIndex(index);

				if (!StringUtils.isEmpty(remarksTextArea.getText())) {
					todaySemenDto.setRemarks(remarksTextArea.getText());
					uiSemenService.addSemen(todaySemenDto);
					// updating current couple object
					couple = coupleService.getCoupleById(couple.getId());
					makeFieldsNonEditable();
					buildData();
				} else {
					Notify alert = new Notify(AlertType.ERROR, REMARKS_NOT_EMPTY);
					alert.showAndWait();
				}

			} else {
				Notify alert = new Notify(AlertType.ERROR, andrologysemenError);
				alert.showAndWait();
			}
		} else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void saveToCryoAction() {
		if (EnumPermission.canWrite(login.getRoleId(), ANDROLOGY_SCREEN_KEY)) {
			if (null != todaySemenDto) {
				int cryoVisibility = 1;
				int semenValue = Integer.parseInt(totalSemen.getText());
				CryoSemenDto cryoSemenDto = new CryoSemenDto();
				cryoSemenDto.setTotalSemens(semenValue);
				cryoSemenDto.setSemenId(todaySemenDto.getSemenId());
				cryoSemenDto.setSemenCodeId(todaySemenDto.getSemenCodeId());
				cryoSemenDto.setCryoVisibility(cryoVisibility);
				cryoSemenDto.setCryoDate(new Date());
				cryoSemenDto.setCodeId(manCode.getId());
				// save Cryo data
				uiSemenService.saveSemenCode(cryoSemenDto);
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

	private SemenDataDto createSemenDataDto() {
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

	@FXML
	private void editAndrologyAction() {
		if (EnumPermission.canWrite(login.getRoleId(), ANDROLOGY_SCREEN_KEY)) {

			String labelDate = dateLabel.getText();
			Date todayDate = new Date();
			String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, todayDate);

			if (labelDate.equals(sCreatedDate)) {
				volumeRawSemenTextField.setEditable(true);
				densityRawSemenTextField.setEditable(true);
				motilityARawSemenTextField.setEditable(true);
				motilityBRawSemenTextField.setEditable(true);
				motilityCRawSemenTextField.setEditable(true);
				motilityDRawSemenTextField.setEditable(true);
				morphologyRawSemenTextField.setEditable(true);
				roundCellsRawSemenTextField.setEditable(true);
				remarksTextArea.setEditable(true);
				timeProcessedTextField.setEditable(true);
				timeProducedTextField.setEditable(true);
				mediaAddedTextField.setEditable(true);
			} else {
				Notify notify = new Notify(AlertType.ERROR, previousDayEditError);
				notify.showAndWait();
			}
		} else
			FileUtils.privillegeEditError();
	}

	/**
	 * Prints the page content.
	 */
	@FXML
	private void printAction() {
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		// Getting all center nodes
		VBox allCenterNodes = createAndrologySemenScreenDataInfo(pageLayout);
		int page = 1;
		if (null != allCenterNodes && allCenterNodes.getChildren().size() > 0) {
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

	// Creating here All Node with ConboBoxes, SEMEN ANALYSIS and RAW SEMEN,
	// TIME PROCESSED and DATE OF ANALYSIS and REMARKS
	private VBox createAndrologySemenScreenDataInfo(PageLayout pageLayout) {

		// This VBOX include all nodes
		VBox vBoxParent = new VBox();
		vBoxParent.setPrefWidth(pageLayout.getPrintableWidth());
		vBoxParent.setPrefHeight(pageLayout.getPrintableHeight() - 200);
		// top-right-bottom-left
		vBoxParent.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);

		// This Section for Use, Viscosity, Agglutination and others.
		List<String> values = new LinkedList<>();
		values.add("  Use 		   :");
		values.add(
				String.valueOf(UseSemenType.getEnumByKey(useComboBox.getSelectionModel().getSelectedItem().getKey())));
		values.add("  Viscosity    :");
		values.add(String.valueOf(viscosityComboBox.getSelectionModel().getSelectedIndex()));
		values.add("  Agglutination:");
		values.add(String.valueOf(agglutinationComboBox.getSelectionModel().getSelectedIndex()));
		values.add("  Media Added  :");
		values.add(String.valueOf(mediaAddedTextField.getText()));
		values.add("  Debris        :");
		values.add(String.valueOf(debrisComboBox.getSelectionModel().getSelectedIndex()));
		values.add("  Aggregation  :");
		values.add(String.valueOf(aggregationComboBox.getSelectionModel().getSelectedIndex()));
		VBox vBoxWithComboBox = printTemplate.createComboBoxNodes(pageLayout, values);
		vBoxWithComboBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		Label spaceBetweenElements1 = printTemplate.spaceBetweenElements(16);

		// This Section for SEMEN ANALYSIS and RAW SEMEN
		HBox hboxForSemenAndRaw = new HBox();
		hboxForSemenAndRaw.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		hboxForSemenAndRaw.setPrefWidth(pageLayout.getPrintableWidth());
		hboxForSemenAndRaw.setSpacing(140);
		hboxForSemenAndRaw.getChildren().add(printTemplate.createHeaderLabel(semenAnalysisHeader));
		hboxForSemenAndRaw.getChildren().add(printTemplate.createHeaderLabel(rawSemenHeader));
		// Grid for Semen Analysis and Raw Semen
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		gridPane.setPrefWidth(pageLayout.getPrintableWidth());
		ColumnConstraints constraintsOne = new ColumnConstraints();
		constraintsOne.setPercentWidth(1);
		ColumnConstraints constraintsTwo = new ColumnConstraints();
		constraintsTwo.setPercentWidth(50);
		ColumnConstraints constraintsThree = new ColumnConstraints();
		constraintsThree.setPercentWidth(0);
		ColumnConstraints constraintsFour = new ColumnConstraints();
		constraintsFour.setPercentWidth(49);
		gridPane.getColumnConstraints().addAll(constraintsOne, constraintsTwo, constraintsThree, constraintsFour);
		// Creating static label for SEMEN ANALYSIS and RAW SEMEN
		gridPane.add(printTemplate.createStaticLabel(volumeLabel), 1, 0);
		gridPane.add(printTemplate.createStaticLabel(densityLabel), 1, 1);
		gridPane.add(printTemplate.createStaticLabel(totalmolalityLabel), 1, 2);
		gridPane.add(printTemplate.createStaticLabel(motilityALabel), 1, 4);
		gridPane.add(printTemplate.createStaticLabel(motilityBLabel), 1, 5);
		gridPane.add(printTemplate.createStaticLabel(motilityCLabel), 1, 6);
		gridPane.add(printTemplate.createStaticLabel(motilityDLabel), 1, 7);
		gridPane.add(printTemplate.createStaticLabel(morphologyLabel), 1, 8);
		gridPane.add(printTemplate.createStaticLabel(roundcellsLabel), 1, 9);
		// Creating Dynamic label SEMEN ANALYSIS and RAW SEMEN
		gridPane.add(printTemplate.createDynamicLabel(volumeRawSemenTextField.getText()), 3, 0);
		gridPane.add(printTemplate.createDynamicLabel(densityRawSemenTextField.getText()), 3, 1);
		gridPane.add(printTemplate.createDynamicLabel(totalMotilityABC_RawSemenTextField.getText()), 3, 2);
		gridPane.add(printTemplate.createDynamicLabel(totalMotilityAB_RawSemenTextField.getText()), 3, 3);
		gridPane.add(printTemplate.createDynamicLabel(motilityARawSemenTextField.getText()), 3, 4);
		gridPane.add(printTemplate.createDynamicLabel(motilityBRawSemenTextField.getText()), 3, 5);
		gridPane.add(printTemplate.createDynamicLabel(motilityCRawSemenTextField.getText()), 3, 6);
		gridPane.add(printTemplate.createDynamicLabel(motilityDRawSemenTextField.getText()), 3, 7);
		gridPane.add(printTemplate.createDynamicLabel(morphologyRawSemenTextField.getText()), 3, 8);
		gridPane.add(printTemplate.createDynamicLabel(roundCellsRawSemenTextField.getText()), 3, 9);
		Label spaceBetweenElements2 = printTemplate.spaceBetweenElements(16);

		// This Section for TIME PROCESSED, TIME PRODUCED and ANALYSIS DATE.
		List<String> timeValues = new ArrayList<>();
		timeValues.add(" " + timeProcessedLabel);
		timeValues.add(timeProcessedTextField.getText());
		timeValues.add(timeProducedLabel);
		timeValues.add(timeProducedTextField.getText());
		timeValues.add(ANALYSISDATE);
		timeValues.add(dateLabel.getText());
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
		if(null!=remarksNode){
			vBoxParent.getChildren().addAll(vBoxWithComboBox, spaceBetweenElements1, hboxForSemenAndRaw, gridPane,
					spaceBetweenElements2, timeVBox, spaceBetweenElements3, registrantDetailVBox, spaceBetweenElements4,
					remarksNode);
		}else {
			vBoxParent = null;
		}
		return vBoxParent;
	}

	// Create Complete BorderPane for printing
	private BorderPane createPrintPage(int page, Node node, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());
		// Setting the Title header at top of Border Pane
		String mainPageTitle = mainPageTitleText;
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, "", pageLayout);
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
				&& timeProcessedTextField.getText().equals(IConstants.emptyString)
				&& timeProducedTextField.getText().equals(IConstants.emptyString))
			valid = false;
		return valid;
	}

	// Reset All TextField and TextArea
	private void reset() {
		useComboBox.getSelectionModel().select(0);
		viscosityComboBox.getSelectionModel().select(0);
		aggregationComboBox.getSelectionModel().select(0);
		agglutinationComboBox.getSelectionModel().select(0);
		debrisComboBox.getSelectionModel().select(0);
		mediaAddedTextField.setText(IConstants.emptyString);
		volumeRawSemenTextField.setText(IConstants.emptyString);
		densityRawSemenTextField.setText(IConstants.emptyString);
		totalMotilityAB_RawSemenTextField.setText(IConstants.emptyString);
		totalMotilityABC_RawSemenTextField.setText(IConstants.emptyString);
		motilityARawSemenTextField.setText(IConstants.emptyString);
		motilityBRawSemenTextField.setText(IConstants.emptyString);
		motilityCRawSemenTextField.setText(IConstants.emptyString);
		motilityDRawSemenTextField.setText(IConstants.emptyString);
		morphologyRawSemenTextField.setText(IConstants.emptyString);
		roundCellsRawSemenTextField.setText(IConstants.emptyString);
		timeProducedTextField.setText(IConstants.emptyString);
		timeProcessedTextField.setText(IConstants.emptyString);
		remarksTextArea.setText(IConstants.emptyString);
		dateLabel.setText(IConstants.emptyString);
		countLabel.setText(IConstants.emptyString);
		totalSemen.setText(IConstants.emptyString);
		totalSemen.setEditable(false);
		saveToCryoButton.setDisable(true);
		embRegistrant.setText(IConstants.emptyString);
		drRegistrant.setText(IConstants.emptyString);
	}

	@FXML
	private void cryoSemenAction() {
		LoadPanels.loadModule(mainApp, login, Module.CRYOPRESERVATION_A.getKey(), null);
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	public Codes getManCode() {
		return manCode;
	}

	public void setManCode(Codes manCode) {
		this.manCode = manCode;
	}
}
