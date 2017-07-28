package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.EmbryoCodeDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.dto.RemarksDto;
import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.EmbryoCode;
import org.cf.card.model.Investigation;
import org.cf.card.model.Treatment;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.CellButton;
import org.cf.card.ui.model.TextAreaCell;
import org.cf.card.ui.model.UICryoEgg;
import org.cf.card.ui.model.UICryoEmbryo;
import org.cf.card.ui.model.UICryoEmbryoEgg;
import org.cf.card.ui.model.UiCryoTableView;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UIDayProgressValueService;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.service.UITreatmentService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumCycleType.CycleType;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CryopreservationEmbryoController extends BaseController {

	// private UICoupleService uiCoupleService = new UICoupleService();
	private UITreatmentService treatmentService = new UITreatmentService();

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.cryopreservation");
	private static final String iconURL = "/icons/cryopreservation_embyro.png";
	private static final String titleDescription = MessageResource
			.getText("mainpage.title.cryopreservation.description.embryo");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String cycleNotCompleted = MessageResource
			.getText("patientfile.controller.warning.treatment.already.progrees");
	private static final String differntTypeNotValid = MessageResource
			.getText("differntTypeNotValid.cryoEmbryo.cycle.start");
	private static final String selectEggEmbryo = MessageResource.getText("select.egg.embryo");

	// creating object
	UIClientService uiClientService = new UIClientService();
	UIEmbryoService uiEmbryoService = new UIEmbryoService();

	UIRemarksService uiRemarksService = new UIRemarksService();
	UICoupleService coupleService = new UICoupleService();
	UIDayProgressValueService uiDayProgressValueService = new UIDayProgressValueService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();
	private PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element

	@FXML
	private GridPane embyoGridPane;

	/* Embryo Egg Table related to day 0 */
	@FXML
	private TableView<UICryoEmbryoEgg> embryoEggTable;

	@FXML
	private TableColumn<UICryoEmbryoEgg, Integer> totalEgg;

	@FXML
	private TableColumn<UICryoEmbryoEgg, Integer> availableEgg;

	@FXML
	private TableColumn<UICryoEmbryoEgg, Integer> usedEgg;

	/* Embryo Table all day except Day 0 */
	@FXML
	private TableView<UICryoEmbryoEgg> embryoTableView;

	@FXML
	private TableColumn<UICryoEmbryoEgg, Integer> totalEmbryo;

	@FXML
	private TableColumn<UICryoEmbryoEgg, String> availableEmbryo;

	@FXML
	private TableColumn<UICryoEmbryoEgg, String> usedEmbryo;

	/* Previous Egg/Embryo Cycle Table */
	@FXML
	private TableView<UiCryoTableView> embryoPreviousCodeTable;

	@FXML
	private TableColumn<UiCryoTableView, String> codeCycle;

	@FXML
	private TableColumn<UiCryoTableView, Integer> codeSrNo;

	@FXML
	private TableColumn<UiCryoTableView, String> codeQuality;

	@FXML
	private TableColumn<UiCryoTableView, String> codeDateOfUse;

	@FXML
	private TableColumn<UiCryoTableView, String> codeType;

	@FXML
	private TableColumn<UiCryoTableView, String> remarks;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private InvestigationCommonController investigationCommonController;

	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextField embRegistrant;
	@FXML
	private TextField witEmbRegistrant;

	private int cycleType;

	/*
	 * @FXML private Label cycleDate;
	 */

	// creating instance variables(class level)
	ObservableList<UiCryoTableView> aoUiCryoTableView = null;
	ObservableList<UICryoEmbryoEgg> aoEmbryoTableView = null;
	ObservableList<UICryoEmbryoEgg> aoEmbryoEggTable = null;

	ObservableList<UiCryoTableView> printItemsCurrentCycle = null;

	List<Investigation> groups = null;
	private static final String TYPE_EGG = "Egg";
	private static final String TYPE_EMBRYO = "Embryo";
	private RemarksDto remarksDto;
	private RegistrantDto registrantDto;
	private Long remarkId = 0L;
	private int SCREEN_ID = Module.CRYOPRESERVATION_E.getKey();

	String codeValue = "";

	/**
	 * Initialize method calls once on FXML load.
	 */
	@FXML
	public void initialize() {

		// Initializing columns of all tables on this screen
		initEmbryoEggTable();
		initEmbryoTable();

		/* initEggActionTable(); */
		initOldCyclesTable();
	}

	public void buildData() {
		resetFields();
		if (null != womanCode && null != manCode) {
			// commonDetailController.setCouple(couple);
			commonDetailController.setWomanPersonalInfo(womanCode);
			commonDetailController.setPartnerPersonalInfo(manCode);
			investigationCommonController.setCouple(couple);
			investigationCommonController.setManCode(manCode);
			investigationCommonController.setWomanCode(womanCode);
			investigationCommonController.build();
		}

		if (womanCode != null) {
			// cycleDate.setText(womanCode.getCode());
			// getting remarks for this page
			Long womanCodeId = womanCode.getId();
			remarksDto = uiRemarksService.getRemarksByCodeId(womanCodeId, SCREEN_ID);
			if (remarksDto != null) {
				remarkId = remarksDto.getId();
			}
			registrantDto = uiRegistrantService.getRegistrantByCodeAndScreenID(womanCodeId, SCREEN_ID);
			if (null != registrantDto) {
				embRegistrant.setText(registrantDto.getNameOfUser());
				witEmbRegistrant.setText(registrantDto.getAssistantUser());
			}

		}
		// setting data of all tables
		buildTablesData();

	}

	/**
	 * Initializes the embryo egg table for Day 0. Overview used and available
	 * eggs in CRYOEgg state
	 */
	private void initEmbryoEggTable() {
		embryoEggTable.setEditable(false);
		/* Egg Table view columns */
		totalEgg.setCellValueFactory(new PropertyValueFactory<UICryoEmbryoEgg, Integer>("total"));
		availableEgg.setCellValueFactory(new PropertyValueFactory<UICryoEmbryoEgg, Integer>("available"));
		usedEgg.setCellValueFactory(new PropertyValueFactory<UICryoEmbryoEgg, Integer>("used"));
	}

	/**
	 * Initializes the embryo action table. List of all embryos in CryoEmbryo
	 * state for current cycle
	 */
	private void initEmbryoTable() {

		embryoTableView.setEditable(false);
		totalEmbryo.setCellValueFactory(new PropertyValueFactory<UICryoEmbryoEgg, Integer>("total"));
		availableEmbryo.setCellValueFactory(new PropertyValueFactory<UICryoEmbryoEgg, String>("available"));
		usedEmbryo.setCellValueFactory(new PropertyValueFactory<UICryoEmbryoEgg, String>("used"));
	}

	/**
	 * Initializes the egg action table.
	 *
	 * List of all eggs in Cryo state for current cycle
	 */
	/*
	 * private void initEggActionTable() { Egg action table view columns //
	 * aoEmbryoEggActionTable = FXCollections.observableArrayList();
	 * codeDateOfUse.setCellValueFactory(new
	 * PropertyValueFactory<UiCryoTableView, String>("used"));
	 * codeDateOfUse.setCellFactory( new Callback<TableColumn<UiCryoTableView,
	 * String>, TableCell<UiCryoTableView, String>>() {
	 * 
	 * @Override public TableCell<UiCryoTableView, String>
	 * call(TableColumn<UiCryoTableView, String> param) { return new
	 * CellButton<UiCryoTableView>(embryoPreviousCodeTable); } }); }
	 */

	/**
	 * Initializes the old cycles table.
	 */
	private void initOldCyclesTable() {
		// embryoPreviousCodeTable.setEditable(false);
		codeCycle.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("cycle"));
		codeSrNo.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, Integer>("srNo"));
		codeQuality.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("quality"));
		codeDateOfUse.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("used"));
		codeDateOfUse.setCellFactory(
				new Callback<TableColumn<UiCryoTableView, String>, TableCell<UiCryoTableView, String>>() {
					@Override
					public TableCell<UiCryoTableView, String> call(TableColumn<UiCryoTableView, String> param) {
						return new CellButton<UiCryoTableView>(embryoPreviousCodeTable);
					}
				});
		codeType.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("type"));
		remarks.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("remarks"));
		// Adding Textarea in remarks column
		remarks.setCellFactory(
				new Callback<TableColumn<UiCryoTableView, String>, TableCell<UiCryoTableView, String>>() {

					@Override
					public TableCell<UiCryoTableView, String> call(TableColumn<UiCryoTableView, String> param) {
						return new TextAreaCell<UiCryoTableView>(embryoPreviousCodeTable, login.getRoleId());
						// return new
						// RemarksTextAreaCell<UiCryoTableView>(embryoPreviousCodeTable);
					}
				});

		embryoPreviousCodeTable.setItems(aoUiCryoTableView);
	}

	/**
	 * Builds the tables data rows.
	 */
	@SuppressWarnings("unused")
	private void buildTablesData() {

		TreatmentDto treatmentDto = null;
		int totalEgg = 0;
		int available = 0;
		int used = 0;

		int totalEmbryo = 0;
		int availableEmbryo = 0;
		int usedEmbryo = 0;

		treatmentDto = treatmentService.getTreatmentsByCoupleId(couple.getId());
		cycleType = treatmentDto.getCycleType();
		// total/available/used table
		aoEmbryoEggTable = FXCollections.observableArrayList();

		// showing list of cryo embryos
		aoEmbryoTableView = FXCollections.observableArrayList();

		// for table showing previous or old cycles
		aoUiCryoTableView = FXCollections.observableArrayList();

		// adding items of current cycle to sperate list so that they can be
		// added later to print list
		printItemsCurrentCycle = FXCollections.observableArrayList();

		Map<Long, Map<Integer, List<DayProgressValue>>> valuesMap = uiDayProgressValueService
				.findDayProgressValueMapByClientDestinyAndModule(womanCode.getClient().getId(), Option.CRYO.getKey(),
						Module.EMBRYOLOGY_OVERVIEW.getKey(), cycleType);

		for (Map.Entry<Long, Map<Integer, List<DayProgressValue>>> element : valuesMap.entrySet()) {
			Map<Integer, List<DayProgressValue>> value = element.getValue();
			EmbryoCode embryoCode = null;
			String quality = "";
			String type = "";
			String dateOfUse = "";
			boolean eggEmbryoUsed = false;
			// iterating inner map key is day index. As as only destiny day
			// should be fetched . So following loop should run only once
			for (Map.Entry<Integer, List<DayProgressValue>> innerMap : value.entrySet()) {
				type = innerMap.getKey() == 0 ? TYPE_EGG : TYPE_EMBRYO;
				// Creating quality
				List<DayProgressValue> aoDayProgressValue = innerMap.getValue();
				for (DayProgressValue dayProgressValue : aoDayProgressValue) {
					embryoCode = dayProgressValue.getEmbryoCode();
					int optionKey = dayProgressValue.getDayOptionId();
					Option option = Option.getOptionByKey(optionKey);
					if (!option.equals(Option.CRYO)) {
						quality += option.getName() + ".";
					}
				}

				// check if the CRYO egg/embryo has been used or not
				if (embryoCode.getDateOfUse() != null) {
					dateOfUse = Util.formatDate(IConstants.DATE_FORMAT, embryoCode.getDateOfUse());
					eggEmbryoUsed = true;
				} else {
					dateOfUse = "";
				}
			}
			// to be used later during printing
			// String remarks = null != remarksDto ? remarksDto.getRemarksText()
			// : "";
			UiCryoTableView uiCryoTableView = new UiCryoTableView(embryoCode.getId(), embryoCode.getCode().getCode(),
					String.valueOf(embryoCode.getIndex()), quality, dateOfUse, type, embryoCode.getRemark(),
					womanCode.getId(), remarkId, couple, null);
			printItemsCurrentCycle.add(uiCryoTableView);
			aoUiCryoTableView.add(uiCryoTableView);
			// generating data for the tables of current cycle/treatment
			if (type.equals(TYPE_EGG)) {
				totalEgg++;
				UICryoEgg cryoEmbryoEggAction = new UICryoEgg(embryoCode.getId(), embryoCode.getIndex(), dateOfUse);
				// aoEmbryoEggActionTable.add(cryoEmbryoEggAction);
				if (eggEmbryoUsed)
					used++;
			} else if (type.equals(TYPE_EMBRYO)) {
				totalEmbryo++;
				UICryoEmbryo cryoEmbryo = new UICryoEmbryo(embryoCode.getId(), embryoCode.getIndex(), quality,
						dateOfUse, eggEmbryoUsed);
				if (eggEmbryoUsed)
					usedEmbryo++;
				// aoEmbryoTableView.add(cryoEmbryo);
			}

			// }

		}
		// creating single row for embryoEggTable
		availableEmbryo = totalEmbryo - usedEmbryo;
		available = totalEgg - used;
		UICryoEmbryoEgg uiCryoEmbryoEgg = new UICryoEmbryoEgg(totalEgg, available, used);
		UICryoEmbryoEgg uiCryoEmbryo = new UICryoEmbryoEgg(totalEmbryo, availableEmbryo, usedEmbryo);
		aoEmbryoEggTable.add(uiCryoEmbryoEgg);

		// total available used table
		embryoEggTable.setItems(aoEmbryoEggTable);
		aoEmbryoTableView.add(uiCryoEmbryo);
		// Embryo Action table
		embryoTableView.setItems(aoEmbryoTableView);

		// setting items for table showing old cycles
		embryoPreviousCodeTable.setItems(aoUiCryoTableView);
		initOldCyclesTable();

	}

	/**
	 * Edits the action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	@FXML
	public void editAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.CRYOPRESERVATION_E.getKey())) {
		} else
			FileUtils.privillegeEditError();

	}

	/**
	 * Save action.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	@FXML
	public void saveAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.CRYOPRESERVATION_E.getKey())) {

			List<EmbryoCodeValueDto> data = new ArrayList<>();
			UiCryoTableView uiCryoTableView = null;
			int totalUsedEgg = 0, totalUsedEmbryo = 0, count = 0;
			EmbryoCodeDto embryoCodeDto = new EmbryoCodeDto();
			ObservableList<UiCryoTableView> aoEmbryoTableView = embryoPreviousCodeTable.getItems();

			// Used EGG/EMBRYO Date from Cryo-E
			String usedDate = null;
			for (UiCryoTableView uiCryoEmbryo : aoEmbryoTableView) {
				EmbryoCodeValueDto embryoCodeValueDto = new EmbryoCodeValueDto();
				embryoCodeValueDto.setEmbryoCodeId(uiCryoEmbryo.getId());
				embryoCodeValueDto.setDateOfUse(uiCryoEmbryo.getUsed());
				embryoCodeValueDto.setRemarks(uiCryoEmbryo.getRemarks());
				uiCryoTableView = uiCryoEmbryo.getUiCryoTableView();
				if (null != uiCryoTableView) {
					String type = uiCryoTableView.getType();
					if (TYPE_EGG == type) {
						totalUsedEgg = totalUsedEgg + 1;
					} else if (TYPE_EMBRYO == type) {
						totalUsedEmbryo = totalUsedEmbryo + 1;
					}
					count = count + 1;
					usedDate = uiCryoEmbryo.getUsed();
					data.add(embryoCodeValueDto);
				}

			}
			if (count > 0) {

				if (totalUsedEgg > 0 && totalUsedEmbryo > 0) {
					Notify notify = new Notify(AlertType.WARNING);
					notify.setContentText(differntTypeNotValid);
					notify.show();
				} else {
					if (totalUsedEgg > 0)
						embryoCodeDto.setAoEgg(data);
					else
						embryoCodeDto.setAoEmbryo(data);
					// updating current couple object
					couple = coupleService.getCoupleById(couple.getId());
					/*
					 * update table view data after saving the value of egg and
					 * embryo table
					 */

					// switching cycle page to Embryology Overview screen
					if (null != couple) {
						startNewCycleOfUsedEggEmbryoAfterSave(usedDate, couple, totalUsedEgg, totalUsedEmbryo,
								embryoCodeDto);
					}
					// for Purpose Refresh Used Button if Not Save Button
					// Clicked
				}
			} else {
				Notify notify = new Notify(AlertType.WARNING);
				notify.setContentText(selectEggEmbryo);
				notify.show();
			}
			buildData();
			initOldCyclesTable();

		} else {
			FileUtils.privillegeEditError();
		}

	}

	/**
	 * Save registrant.
	 */
	@FXML
	private void saveRegistrant() {
		registrantDto = FileUtils.saveOrUpdateRegistrant(registrantDto, womanCode.getId(), SCREEN_ID, embRegistrant,
				witEmbRegistrant);
	}

	/**
	 * Start new cycle of used egg embryo after save.
	 *
	 * @param useDateOfEgg_Embryo
	 *            the use date of egg_ embryo
	 * @param couple
	 *            the couple
	 * @param totalUsedEgg
	 *            the total used egg
	 * @param totalUsedEmbryo
	 *            the total used embryo
	 * @param embryoCodeDto
	 *            the embryo code dto
	 */
	@SuppressWarnings("unchecked")
	public void startNewCycleOfUsedEggEmbryoAfterSave(String useDateOfEgg_Embryo, Couple couple, int totalUsedEgg,
			int totalUsedEmbryo, EmbryoCodeDto embryoCodeDto) {

		Date selectedDateofEgg_Embryo = null;
		Date lastDatefromOfSelection = null;
		Date treatmentEndDate = null;
		Codes manCode = null;
		Codes womanCode = null;
		TreatmentDto treatmentDto = null;
		// Couple couple = null;
		if (useDateOfEgg_Embryo != null) {
			try {
				selectedDateofEgg_Embryo = Util.stringToDate(useDateOfEgg_Embryo, IConstants.DATE_FORMAT);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null != couple) {
				treatmentDto = treatmentService.getTreatmentsByCoupleId(couple.getId());
				if (treatmentDto != null)
					treatmentEndDate = treatmentDto.getEndDate();
			}
			// Adding one day Extra to EndDate of Cycle because of day 0.
			treatmentEndDate = Util.nextDate(treatmentEndDate, 1);

			if (treatmentEndDate != null && (selectedDateofEgg_Embryo.before(treatmentEndDate)
					|| selectedDateofEgg_Embryo.equals(treatmentEndDate))) {
				Notify notify = new Notify(AlertType.WARNING);
				notify.setContentText(cycleNotCompleted);
				notify.show();
			} else {

				// Getting the last Date object
				lastDatefromOfSelection = Util.nextDate(selectedDateofEgg_Embryo, 10);
				// Reducing One day because of Day -1
				Date treatmentStart = Util.nextDate(selectedDateofEgg_Embryo, -1);
				Date treatmentEnd = Util.nextDate(lastDatefromOfSelection, -1);

				if (totalUsedEgg > 0) {

					womanCode = treatmentService.saveTreatment(treatmentStart, couple.getWoman(), treatmentEnd,
							CycleType.EGG_THAW.getKey());
					Treatment treatmentObject = womanCode.getTreatment();
					TreatmentDto treatDto = new TreatmentDto();
					treatDto.setTreatmentID(treatmentObject.getId());
					treatDto.setCycleType(treatmentObject.getCycleType());
					treatDto.setOocytes(totalUsedEgg);
					treatDto.setEndDate(treatmentObject.getEndDate());
					treatDto.setStartDate(treatmentObject.getStartDate());
					treatDto.setCodeId(womanCode.getId());

					treatmentService.updateTreatment(treatDto);
					manCode = treatmentService.saveTreatment(treatmentStart, couple.getMan(), treatmentEnd,
							CycleType.EGG_THAW.getKey());
				} else {
					womanCode = treatmentService.saveTreatment(treatmentStart, couple.getWoman(), treatmentEnd,
							CycleType.EMBRYO_THAW.getKey());
					manCode = treatmentService.saveTreatment(treatmentStart, couple.getMan(), treatmentEnd,
							CycleType.EMBRYO_THAW.getKey());
				}

				couple = womanCode.getClient().getCouple();
				// updating couple and codes as per new treatment schedule
				SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple.getId());
				sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, womanCode.getId());
				sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, manCode.getId());
				embryoCodeDto.setWomanCodeId(womanCode.getId());
				// buildData();
				// Saving list of embryoCodeValue to embryoCode
				uiEmbryoService.saveCryoEmbryo(embryoCodeDto);
				if (totalUsedEgg > 0) {
					LoadPanels.loadModule(mainApp, login, Module.EMBRYOLOGY_OVERVIEW.getKey(), null);
				} else {
					LoadPanels.loadModule(mainApp, login, Module.EGG_THAWING.getKey(), null);
				}
			}
		} else {
			Notify notify = new Notify(AlertType.WARNING);
			notify.setContentText(MessageResource.getText("patientfile.warning.select.date"));
			notify.show();
		}
	}

	/**
	 * Embryo thawing action.
	 */
	public void embryoThawingAction() {

		LoadPanels.loadModule(mainApp, login, Module.EGG_THAWING.getKey(), null);
	}

	/**
	 * Prints the action.
	 */
	@FXML
	private void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		// Getting all center nodes
		List<Node> allCenterNodes = createCryoEmbryoPrintTable(pageLayout);
		int page = 1;
		if (allCenterNodes.size() > 0 && allCenterNodes != null) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				for (Node tableVBox : allCenterNodes) {
					BorderPane printPage = createPrintPage(tableVBox, page, pageLayout);
					printerJob.printPage(printPage);
					page++;
				}
				printerJob.endJob();
			}
		} else {
			Notify notify = new Notify(AlertType.INFORMATION, noDataAvailableMessage);
			notify.showAndWait();
		}
	}

	/**
	 * Creates the print page.
	 *
	 * @param table
	 *            the table
	 * @param page
	 *            the page
	 * @param pageLayout
	 *            the page layout
	 * @return the border pane
	 */
	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section,RegistrantInfo,Table View)at
		// center
		VBox contentVBox = new VBox();
		// This Section for Patient Details.
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		Label spaceBetweenElements1 = printTemplate.spaceBetweenElements(20);
		contentVBox.getChildren().addAll(patientGrid, spaceBetweenElements1);
		// This Section for Registrant Details.
		VBox registrantDetailVBox = printTemplate.createRegistrantCommonInfo(pageLayout, registrantDto,
				MessageResource.getText("common.emb.registrant"), MessageResource.getText("common.emb.wit.registrant"));
		Label spaceBetweenElements2 = printTemplate.spaceBetweenElements(20);
		contentVBox.getChildren().addAll(registrantDetailVBox, spaceBetweenElements2);
		contentVBox.getChildren().add(table);
		root.setCenter(contentVBox);
		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	private List<Node> createCryoEmbryoPrintTable(PageLayout pageLayout) {
		ObservableList<UiCryoTableView> items = embryoPreviousCodeTable.getItems();
		List<Node> nodes = new ArrayList<>();
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		double labelWidth = pageLayout.getPrintableWidth() / 5;
		codeValue = "";
		for (UiCryoTableView item : items) {
			HBox row = createPrintTableRow(item, labelWidth, pageLayout);
			// HBox row = createTableRow( labelWidth, pageLayout);
			// elementHeight would be the height of each cell
			final double elementHeight = 15;
			// adding table on multiple pages
			// 210 - height of patient section + height of embryo Info HBox
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 250) {
				tableVBox = new VBox();
				tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableVBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
				HBox tableHeader = createTableHeader(labelWidth, pageLayout);
				// adding table columns in Table VBox
				tableVBox.getChildren().add(tableHeader);
				nodes.add(tableVBox);
				totalHeight = 0;
			}
			if (tableVBox != null && row.getChildren().size() > 0) {
				tableVBox.getChildren().add(row); // adding table rows in Table
													// VBox
				totalHeight += elementHeight;
			}
		}
		return nodes;

	}

	/**
	 * Setting Column Names(Header) in HBox.
	 *
	 * @param labelWidth
	 *            the label width
	 * @param pageLayout
	 *            the page layout
	 * @return the h box
	 */
	private HBox createTableHeader(double labelWidth, PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		// headerHBox.setStyle("-fx-border-width: 0 0 1 0; " +
		// Constants.PRINT_GREY_BORDER_STYLE);
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("CYCLE", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("SR NO", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("QUALITY", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("DATE OF USE", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("TYPE", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("REMARKS", labelWidth));
		return headerHBox;
	}

	/** Setting Cell Data (Row) in HBox. */
	private HBox createPrintTableRow(UiCryoTableView item, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		if (codeValue.equals(item.getCycle())) {
			rowHBox.getChildren().add(printTemplate.createTableRowLabel("", labelWidth, false));
		} else {
			codeValue = item.getCycle();
			Label codeLabel = printTemplate.createTableRowLabel(codeValue, labelWidth, true);
			codeLabel.setAlignment(Pos.CENTER);
			rowHBox.getChildren().add(codeLabel);
			rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		}
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(item.getSrNo(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());

		rowHBox.getChildren().add(printTemplate.createTableRowLabel(item.getQuality(), labelWidth, true));

		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(item.getUsed(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(item.getType(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(item.getRemarks(), labelWidth, false));

		return rowHBox;
	}

	private void resetFields() {
		embRegistrant.setText(IConstants.emptyString);
		witEmbRegistrant.setText(IConstants.emptyString);
	}

}