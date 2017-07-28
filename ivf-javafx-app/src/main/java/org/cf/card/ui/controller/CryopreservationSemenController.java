package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.dto.SemenCodeDto;
import org.cf.card.model.SemenCode;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.CellButton;
import org.cf.card.ui.model.TextAreaCell;
import org.cf.card.ui.model.UIAndrologySemenData;
import org.cf.card.ui.model.UICryoActionSemen;
import org.cf.card.ui.model.UICryoSemen;
import org.cf.card.ui.model.UICryoTotalSemen;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UICodesService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.service.UISemenService;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.cf.card.vo.VoSemenCode;

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

// TODO: Auto-generated Javadoc
/**
 * The Class CryopreservationSemenController.
 *
 * @author insonix
 */
public class CryopreservationSemenController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.cryopreservation");
	private static final String titleDescription = MessageResource
			.getText("mainpage.title.cryopreservation.description.andro");
	private static final String iconURL = "/icons/cryopreservation_embyro.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// creating object
//	UICoupleService coupleService = new UICoupleService();
//	UIClientService uiClientService = new UIClientService();
//	UITreatmentService uiTreatmentService = new UITreatmentService();
//	UIRemarksService uiRemarksService = new UIRemarksService();
	UICodesService uiCodesService = new UICodesService();
	UISemenService uiSemenService = new UISemenService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element

	@FXML
	TableColumn<UICryoSemen, String> cryoDmm;

	@FXML
	TableColumn<UICryoSemen, String> remarks;

	@FXML
	TableColumn<UICryoSemen, String> date;

	// Semen table View
	@FXML
	TableView<UICryoTotalSemen> semenTable;

	@FXML
	TableColumn<UICryoTotalSemen, Integer> total;

	@FXML
	private TableColumn<UICryoTotalSemen, Integer> available;

	@FXML
	private TableColumn<UICryoTotalSemen, Integer> used;

	/** The semen action table. */
	// semen action Date View

	@FXML
	private TableColumn<UICryoActionSemen, Boolean> actionSemen;

	/** The andrology table view. */
	/* Andrology table view */
	@FXML
	private TableView<UIAndrologySemenData> andrologyTableView;

	@FXML
	private TableColumn<UIAndrologySemenData, Integer> density;

	@FXML
	private TableColumn<UIAndrologySemenData, Float> motility;

	@FXML
	private TableColumn<UIAndrologySemenData, Integer> morphology;

	/** The common detail controller. */
	@FXML
	private CommonDetailController commonDetailController;

	/** The investigation common controller. */
	@FXML
	private InvestigationCommonController investigationCommonController;
	
	@FXML
	private TextField embRegistrant;
	@FXML
	private TextField witEmbRegistrant;

	/** The semen cryo old table view. */
	/* Old treatment detail tableview */
	@FXML
	private TableView<UICryoSemen> semenCryoOldTableView;

	@FXML
	private TableColumn<UICryoSemen, String> codeCycle;

	@FXML
	private TableColumn<UICryoSemen, Integer> codeSrNo;

	@FXML
	private TableColumn<UICryoSemen, String> codeDateOfCryo;

	@FXML
	private TableColumn<UICryoSemen, String> codeDateOfUse;

	// creating instance variables(class level)
	ObservableList<UICryoTotalSemen> aoSemenViewTable = null;
	ObservableList<UICryoActionSemen> aoSemenActionTable = null;
	ObservableList<UIAndrologySemenData> aoAndrologyTableView = null;
	ObservableList<UICryoSemen> aoSemenCryoOldTableView = null;
	List<VoSemenCode> aoVoSemenCode = null;
	private int SCREEN_ID = Module.CRYOPRESERVATION_A.getKey();
	private RegistrantDto registrantDto;
	private List<SemenCode> aoSemenCode;
	private Long semenId = 0l;
	String codeVal = "", quality = "", createdDate = "", cryoDate = "";

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
//		makeFieldsNonEditable();
	}

	/**
	 * Builds the data.
	 */
	public void buildData() {
		resetFields();
		aoSemenViewTable = FXCollections.observableArrayList();
		aoSemenActionTable = FXCollections.observableArrayList();
		aoAndrologyTableView = FXCollections.observableArrayList();
		/* Semen strain/vials table view */
		total.setCellValueFactory(new PropertyValueFactory<UICryoTotalSemen, Integer>("total"));
		available.setCellValueFactory(new PropertyValueFactory<UICryoTotalSemen, Integer>("available"));
		used.setCellValueFactory(new PropertyValueFactory<UICryoTotalSemen, Integer>("used"));

		/* Quality table view */
		andrologyTableView.setEditable(true);
		density.setCellValueFactory(new PropertyValueFactory<UIAndrologySemenData, Integer>("total"));
		motility.setCellValueFactory(new PropertyValueFactory<UIAndrologySemenData, Float>("available"));
		morphology.setCellValueFactory(new PropertyValueFactory<UIAndrologySemenData, Integer>("used"));

		if (couple != null) {
			registrantDto = uiRegistrantService.getRegistrantByCodeAndScreenID(womanCode.getId(), SCREEN_ID);
			if (null != registrantDto) {
				embRegistrant.setText(registrantDto.getNameOfUser());
				witEmbRegistrant.setText(registrantDto.getAssistantUser());
			}

			// Couple couple = manCode!=null?manCode.getClient().getCouple():null;
			commonDetailController.setMainApp(mainApp);
			// commonDetailController.setCouple(couple);
			commonDetailController.setWomanPersonalInfo(womanCode);
			commonDetailController.setPartnerPersonalInfo(manCode);
			/* investigation page calling */
			investigationCommonController.setCouple(couple);
			investigationCommonController.setManCode(manCode);
			investigationCommonController.setWomanCode(womanCode);
			investigationCommonController.build();
			aoSemenCode = uiSemenService.findSemenCodeByCodeIdAndScreenId(manCode.getId(), Module.ANDROLOGY.getKey());
			// cycleCode.setText(manCode.getCode());
			int availableSemens = 0;
			int usedSemens = 0;
			int totalSemens = 0;
			int cryoVisibility = 1;
			UIAndrologySemenData uiAndrologySemenData = null;
			for (SemenCode semenCode : aoSemenCode) {
				semenId = semenCode.getSemen().getId();
	//			String dateOfUses = "";
				if (null != semenCode.getDateUsed()) {
					usedSemens = usedSemens + 1;
					/*Date date = semenCode.getDateUsed();
					dateOfUses = Util.formatDate(IConstants.DATE_FORMAT, date);*/
				}
				
				if(semenCode.getSemen().getCryoVisibility() == cryoVisibility ){
					totalSemens = totalSemens + 1;
				}
				/*UICryoActionSemen uiCryoActionSemen = new UICryoActionSemen(semenCode.getId(), semenCode.getIndex(),
						dateOfUses);
				aoSemenActionTable.add(uiCryoActionSemen);*/
				/*Set<SemenData> aoSemenData = semenCode.getSemen().getSemenDatas();
				for (SemenData semenData : aoSemenData) {
					 Only getting the data of RAW semen type 
					if (semenData.getType() == Enumeration.SemenType.RAW.getKey()) {
						int motilityTotal = semenData.getMotilityA() + semenData.getMotilityB() + semenData.getMotilityC();
						// uiAndrologySemenData = new
						// UIAndrologySemenData(semenData.getDensity() + "",
						// motilityTotal,
						// semenData.getMorphology());
					}
				}*/
			}
//			int totalSemens = aoSemenCode.size();
			availableSemens = totalSemens - usedSemens;
	//		totalSemen.setText(totalSemens + "");
			UICryoTotalSemen ui = new UICryoTotalSemen(totalSemens, availableSemens, usedSemens);
			aoSemenViewTable.add(ui);
			semenTable.setEditable(true);
			semenTable.setItems(aoSemenViewTable);
			// andrologyTableView.setItems(aoAndrologyTableView);

			/* displaying the old treatment details */
			codeCycle.setCellValueFactory(new PropertyValueFactory<UICryoSemen, String>("cycle"));
			codeSrNo.setCellValueFactory(new PropertyValueFactory<UICryoSemen, Integer>("srNo"));
			codeDateOfCryo.setCellValueFactory(new PropertyValueFactory<UICryoSemen, String>("dateOfCryo"));

			date.setCellValueFactory(new PropertyValueFactory<UICryoSemen, String>("createdDate"));
			cryoDmm.setCellValueFactory(new PropertyValueFactory<UICryoSemen, String>("dmm"));
			codeDateOfUse.setCellValueFactory(new PropertyValueFactory<UICryoSemen, String>("dateOfUse"));

			remarks.setCellValueFactory(new PropertyValueFactory<UICryoSemen, String>("remarks"));
			// Adding Textarea in remarks column
			remarks.setCellFactory(new Callback<TableColumn<UICryoSemen, String>, TableCell<UICryoSemen, String>>() {
				@Override
				public TableCell<UICryoSemen, String> call(TableColumn<UICryoSemen, String> param) {
					return new TextAreaCell<UICryoSemen>(semenCryoOldTableView, login.getRoleId());
				}
			});

			codeDateOfUse
					.setCellFactory(new Callback<TableColumn<UICryoSemen, String>, TableCell<UICryoSemen, String>>() {
						@Override
						public TableCell<UICryoSemen, String> call(TableColumn<UICryoSemen, String> param) {
							return new CellButton<UICryoSemen>(semenCryoOldTableView);
						}
					});

			aoSemenCryoOldTableView = FXCollections.observableArrayList();

			// List<Codes> aoCodes =
			// uiSemenService.findCodeByClientId(couple.getMan().getId());
			/*
			 * List<Codes> aoManCodes = couple.getMan().getCodes(); for (int j =
			 * aoManCodes.size()-1; j >=0; j--) { Codes codes =
			 * aoManCodes.get(j); String code = codes.getCode();
			 * if(!code.equals(manCode.getCode())){ List<SemenCode>
			 * aoSemenCodeList = codes.getSemenCode(); for (SemenCode semenCode2
			 * : aoSemenCodeList) { String dateOfUses = "";
			 * if(null!=semenCode2.getDateUsed()){ usedSemens = usedSemens+1;
			 * Date date = semenCode2.getDateUsed(); dateOfUses =
			 * Util.formatDate(IConstants.DATE_FORMAT, date); } Set<SemenData>
			 * aoSemenData = semenCode2.getSemen().getSemenDatas(); for
			 * (SemenData semenData : aoSemenData) { if(semenData.getType() ==
			 * Enumeration.SemenType.RAW.getKey()){ motilityTotal =
			 * semenData.getMotilityA()+semenData.getMotilityB()+semenData.
			 * getMotilityC()+semenData.getMotilityD(); //UICryoSemen
			 * uiCryoSemen = new UICryoSemen(code, semenCode2.getIndex(), "",
			 * dateOfUses); //aoSemenCryoOldTableView.add(uiCryoSemen); } } } }
			 * }
			 */

			aoVoSemenCode = uiSemenService.findSemenCodeAndSemenDataByClientId(manCode.getClient().getId());
			
			
			Collections.reverse(aoVoSemenCode);
			String code = "";
			int availableSemensCode = 0;
			int usedSemensCode = 0;
			int totalSemensCode = 0;
			for (VoSemenCode voSemenCode : aoVoSemenCode) {
				// if (!voSemenCode.getCode().equals(manCode.getCode())) {
				String dateOfUses = null;
				if (null != voSemenCode.getDateUsed()) {
					usedSemensCode = usedSemensCode + 1;
				}
				
				Date cryoDate = voSemenCode.getCryoDate();
				String scryoDate = Util.formatDate(IConstants.DATE_FORMAT, cryoDate);
				
				Date date = voSemenCode.getDateUsed();
				dateOfUses = Util.formatDate(IConstants.DATE_FORMAT, date);
				String dmm = voSemenCode.getQuality();
				
				Date createdDate = voSemenCode.getCreatedDate();
				String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, createdDate);
				/*
				 * if (code.equals(voSemenCode.getCode())) { UICryoSemen
				 * uiCryoSemen = new UICryoSemen("", voSemenCode.getIndex(), "",
				 * dateOfUses, "", dmm);
				 * aoSemenCryoOldTableView.add(uiCryoSemen); } else {
				 */
				code = voSemenCode.getCode();
				UICryoSemen uiCryoSemen = new UICryoSemen(code, voSemenCode.getIndex(), scryoDate, dateOfUses, sCreatedDate, dmm,
						voSemenCode.getRemarks(), voSemenCode.getSemenCodeId());
				aoSemenCryoOldTableView.add(uiCryoSemen);
				// }
				// }
			}
			totalSemensCode = aoVoSemenCode.size();
			availableSemensCode = totalSemensCode - usedSemensCode;
			uiAndrologySemenData = new UIAndrologySemenData(totalSemensCode, availableSemensCode, usedSemensCode);
			aoAndrologyTableView.add(uiAndrologySemenData);
			andrologyTableView.setItems(aoAndrologyTableView);
			semenCryoOldTableView.setItems(aoSemenCryoOldTableView);
		}
//		makeFieldsNonEditable();
	}

	/**
	 * Save button.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	/*
	 * TODO if the user will enter the no of straw more then one then new
	 * entries will go to semenCode table. otherwise one entry will be update as
	 * action table value.
	 */
	@FXML
	public void saveButton(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.CRYOPRESERVATION_A.getKey())) {
			//Moved totalSemen textfield to Andrology screen & in semen table 
			/*int semenValue = Integer.parseInt(totalSemen.getText());
			TreatmentDto treatmentDto = new TreatmentDto();
			long treatmentId = manCode.getTreatment().getIdTreatment();
			treatmentDto.setTreatmentID(treatmentId);
			treatmentDto.setSemenValue(semenValue);
			treatmentDto.setCryoDate(new Date());
			 save or update no of Semen 
			uiTreatmentService.updateTreatmentforSemen(treatmentDto);*/
			/* getting the data of action view table */
			List<SemenCodeDto> aoSemenCodeDto = new ArrayList<>();
			for (UICryoSemen uiCryo : aoSemenCryoOldTableView) {
				SemenCodeDto semenCodeDto = new SemenCodeDto();
				semenCodeDto.setSemenCodeId(uiCryo.getSemenCodeId());
				semenCodeDto.setDateOfUsed(uiCryo.getDateOfUse());
				semenCodeDto.setRemarks(uiCryo.getRemarks());
				aoSemenCodeDto.add(semenCodeDto);
			}
			CryoSemenDto cryoSemenDto = new CryoSemenDto();
	//		cryoSemenDto.setNoOfStraw(semenValue);
			cryoSemenDto.setAoSemenCodeDto(aoSemenCodeDto);
			cryoSemenDto.setSemenId(semenId);
	//		ObservableList<UICryoSemen> aoEmbryoTableView = semenCryoOldTableView.getItems();
			cryoSemenDto.setCodeId(manCode.getId());
			/* no of straw are equal then update otherwise save */
			/*if (semenValue != aoSemenCode.size()) {
				uiSemenService.saveSemenCode(cryoSemenDto);
			} else {*/
				uiSemenService.updateSemenCode(cryoSemenDto);
	//		}
			// couple = coupleService.getCoupleById(couple.getId());
			manCode = uiCodesService.getCodeById(manCode.getId());

			/* reloading the data after save or update */
			buildData();
		} else
			FileUtils.privillegeEditError();
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
	 * Prints the action.
	 */
	@FXML
	private void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		// Getting all center nodes
		List<Node> nodes = createCryoSemenTable(semenCryoOldTableView.getItems(), pageLayout);
		int page = 1;
		if (nodes.size() > 0 && nodes != null) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				for (Node tableVBox : nodes) {
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
		root.setPrefWidth(pageLayout.getPrintableWidth());
		root.setPrefHeight(pageLayout.getPrintableHeight());

		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section,RegistrantInfo,CryoSemenTable View)at
		// center
		VBox contentVBox = new VBox();
		contentVBox.setPrefWidth(pageLayout.getPrintableWidth());
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

	/**
	 * Creates the cryo semen table.
	 *
	 * @param aoVoSemenCode
	 *            the ao vo semen code
	 * @param pageLayout
	 *            the page layout
	 * @return the list
	 */
	private List<Node> createCryoSemenTable(ObservableList<UICryoSemen> items, PageLayout pageLayout) {
		// Collections.reverse(aoVoSemenCode);
		List<Node> nodes = new ArrayList<>();
		VBox tableVBox = null;
		double labelWidth = pageLayout.getPrintableWidth() / 5;
		double totalHeight = Double.POSITIVE_INFINITY;
		// to get code, quality values each time on printing
		codeVal = "";
		quality = "";
		createdDate = ""; 
		cryoDate = "";
		for (UICryoSemen record : items) {
			HBox row = createTableRow(record, labelWidth, pageLayout);
			// elementHeight would be the height of each cell
			final double elementHeight = 15;
			// adding table on multiple pages
			// 170 - height of Header + patient section + Footer
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 260) {
				tableVBox = new VBox();
				tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableVBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
				HBox tableHeader = createTableHeader(labelWidth, pageLayout);
				// adding table columns in Table VBox
				tableVBox.getChildren().add(tableHeader);
				nodes.add(tableVBox);
				totalHeight = 0;
			}
			totalHeight += elementHeight;
			if (tableVBox != null)
				tableVBox.getChildren().add(row); // adding table rows in Table
													// VBox
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
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("DATE", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("CYCLE", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("SR NO", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("D/M/M", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("DATE OF CRYO", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("DATE OF USE", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("REMARKS", labelWidth));
		
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
	 * @return the hbox
	 */
	private HBox createTableRow(UICryoSemen record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		
		if (createdDate.equals(record.getCreatedDate())) {
			rowHBox.getChildren().add(printTemplate.createTableRowLabel(IConstants.emptyString, labelWidth, false));
		} else {
			createdDate = record.getCreatedDate();
			Label createdDateLabel = printTemplate.createTableRowLabel(createdDate, labelWidth, true);
			createdDateLabel.setAlignment(Pos.CENTER);
			rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			rowHBox.getChildren().add(createdDateLabel);
		}
		rowHBox.getChildren().add(printTemplate.createSeparator());
		
		if (codeVal.equals(record.getCycle())) {
			rowHBox.getChildren().add(printTemplate.createTableRowLabel(IConstants.emptyString, labelWidth, false));
		} else {
			codeVal = record.getCycle();
			Label codeLabel = printTemplate.createTableRowLabel(codeVal, labelWidth, false);
			codeLabel.setAlignment(Pos.CENTER);
			rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
			rowHBox.getChildren().add(codeLabel);
		}
		rowHBox.getChildren().add(printTemplate.createSeparator());
		
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getSrNo() + "", labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());

		if (quality.equals(record.getDmm())) {
			rowHBox.getChildren().add(printTemplate.createTableRowLabel(IConstants.emptyString, labelWidth, false));
		} else {
			quality = record.getDmm();
			Label qualityLabel = printTemplate.createTableRowLabel(quality, labelWidth, true);
			qualityLabel.setAlignment(Pos.CENTER);
			rowHBox.getChildren().add(qualityLabel);
		}
		rowHBox.getChildren().add(printTemplate.createSeparator());
		
		if (cryoDate.equals(record.getDateOfCryo())) {
			rowHBox.getChildren().add(printTemplate.createTableRowLabel(IConstants.emptyString, labelWidth, false));
		} else {
			cryoDate = record.getDateOfCryo();
			Label cryoDateLabel = printTemplate.createTableRowLabel(cryoDate, labelWidth, false);
			cryoDateLabel.setAlignment(Pos.CENTER);
			rowHBox.getChildren().add(cryoDateLabel);
		}
		rowHBox.getChildren().add(printTemplate.createSeparator());
		
		rowHBox.getChildren().add(printTemplate
				.createTableRowLabel(record.getDateOfUse(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getRemarks(), labelWidth, false));
		
		return rowHBox;
	}

	private void resetFields() {
		embRegistrant.setText(IConstants.emptyString);
		witEmbRegistrant.setText(IConstants.emptyString);
	}
}
