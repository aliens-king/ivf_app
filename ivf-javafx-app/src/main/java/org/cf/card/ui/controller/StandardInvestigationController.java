package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.cf.card.dto.InvestigatinValueDto;
import org.cf.card.dto.PatientInvestigationDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.dto.RemarksDto;
import org.cf.card.model.InvestigatinValue;
import org.cf.card.model.Investigation;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.ui.components.EditingCell;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.Nurse;
import org.cf.card.ui.model.UIInvestigation;
import org.cf.card.ui.model.UISTInvestigation;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIClientService;
import org.cf.card.ui.service.UICodesService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UIInvestigationService;
import org.cf.card.ui.service.UIPatientInvestigationService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumInvestigation;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.springframework.util.StringUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class StandardInvestigationController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.standard_investigation");
	private static final String titleDescription = IConstants.emptyString;
	private static final String iconURL = "/icons/standard_investigation.png";

	// creating object
	UIInvestigationService uiInvestigationService = new UIInvestigationService();
	UIPatientInvestigationService patientInvestigationService = new UIPatientInvestigationService();
	UIRemarksService uiRemarksService = new UIRemarksService();
	UIClientService uiClientService = new UIClientService();
	UICoupleService coupleService = new UICoupleService();
	UICodesService codesService = new UICodesService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding FXML elements
	@FXML
	private TableView<UISTInvestigation> womanBloodWorkTable;

	@FXML
	private TableView<UISTInvestigation> womanMicrobiologyTable;

	@FXML
	private TableView<UISTInvestigation> womanHormonalTable;

	@FXML
	private TableView<UISTInvestigation> manBloodWorkTable;

	@FXML
	private TableView<UISTInvestigation> manMicrobiologyTable;

	@FXML
	private Button editBtn;

	@FXML
	private TableView<UISTInvestigation> manHormonalTable;

	@FXML
	private TextField woamnBMITextField;

	@FXML
	private TextField partnerBMITextField;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private TextArea pScanTextArea;
	@FXML
	private ComboBox<Nurse> nurseComboBox;
	@FXML
	private TextField nurseRegistrant;
	@FXML
	private TextField asstNurseRegistrant;	
	@FXML
	private CommonDetailController commonDetailController;
	@FXML
	private PatientInvestigation womanPatientInvestigation;
	@FXML
	private PatientInvestigation partnerPatientInvestigation;

	// creating instance variables(class level)
	/** The administrator warning label. */
	private Label administratorWarningLabel;
	public static final int BLOODWORK = 1;
	public static final int MICROBIOLOGY = 2;
	public static final int HORMONAL = 3;
	private RemarksDto remarksDto;
	private RegistrantDto registrantDto;
	List<Investigation> groups = null;
	/** Partner table view data */
	ObservableList<UISTInvestigation> aoPartnerBloodList = null;
	ObservableList<UISTInvestigation> aoPartnerMicrobiologyList = null;
	ObservableList<UISTInvestigation> aoPartnerHormonalList = null;

	ObservableList<UISTInvestigation> aoBloodList = null;
	ObservableList<UISTInvestigation> aoMicrobiologyList = null;
	ObservableList<UISTInvestigation> aoHormonalList = null;

	private int SCREEN_ID = Module.STANDARD_INVESTIGATION.getKey();

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

	@FXML
	public void initialize() {
		remarksTextArea.setCache(false);
		// remarksTextArea.setStyle("-fx-font-smoothing-type: lcd;");
		groups = uiInvestigationService.findAllInvestigation();
		int count = 0;

		/** woman tableview's column list */
		List<TableColumn<UISTInvestigation, String>> bloodColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> microbiologyColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> hormonalColumnName = new ArrayList<>();

		/** partner tableview's column list */
		List<TableColumn<UISTInvestigation, String>> partnerBloodColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> partnerMicrobiologyColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> partnerHormonalColumnName = new ArrayList<>();

		Callback<TableColumn<UISTInvestigation, String>, TableCell<UISTInvestigation, String>> editCellFactory = new Callback<TableColumn<UISTInvestigation, String>, TableCell<UISTInvestigation, String>>() {
			public TableCell<UISTInvestigation, String> call(TableColumn<UISTInvestigation, String> p) {
				return new EditingCell<UISTInvestigation, String>();
			}
		};

		for (Investigation investigation : groups) {
			if (investigation.getGroup() == BLOODWORK) {
				TableColumn<UISTInvestigation, String> column = new TableColumn<>(MessageResource
						.getText("BLOODWORK." + (EnumInvestigation.BloodWork.getEnumByKey(investigation.getName()))));
				final int s = new Integer(count);
				column.setCellValueFactory(cellData -> cellData.getValue().getInvestigation().get(s).valueProperty());
				column.setId(investigation.getName());
				column.setEditable(true);
				// column.setCellFactory(TextFieldTableCell.forTableColumn());
				column.setCellFactory(editCellFactory);
				if (count == 2 || count == 3 || count == 5) {
					column.setMinWidth(10.0);
					column.setPrefWidth(70);
					column.setMaxWidth(200.0);
				} else {
					column.setMinWidth(10.0);
					column.setPrefWidth(60);
					column.setMaxWidth(200.0);
				}
				// count++;
				bloodColumnName.add(column);

				// partner blood tableview
				TableColumn<UISTInvestigation, String> partnerBloodColumn = new TableColumn<>(MessageResource
						.getText("BLOODWORK." + (EnumInvestigation.BloodWork.getEnumByKey(investigation.getName()))));
				partnerBloodColumn.setCellValueFactory(
						cellData -> cellData.getValue().getPartnerInvestigation().get(s).valueProperty());
				partnerBloodColumn.setId(investigation.getName());
				partnerBloodColumn.setEditable(true);
				// partnerBloodColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				partnerBloodColumn.setCellFactory(editCellFactory);
				if (count == 2 || count == 3 || count == 5) {
					partnerBloodColumn.setMinWidth(10.0);
					partnerBloodColumn.setPrefWidth(70);
					partnerBloodColumn.setMaxWidth(200.0);
				} else {
					partnerBloodColumn.setMinWidth(10.0);
					partnerBloodColumn.setPrefWidth(60);
					partnerBloodColumn.setMaxWidth(200.0);
				}
				count++;
				partnerBloodColumnName.add(partnerBloodColumn);
				if (count == 6)
					count = 0;
			} else if (investigation.getGroup() == MICROBIOLOGY) {
				TableColumn<UISTInvestigation, String> column = new TableColumn<>(MessageResource.getText(
						"MICROBIOLOGY." + (EnumInvestigation.MicroBiology.getEnumByKey(investigation.getName()))));
				final int s = new Integer(count);
				column.setCellValueFactory(cellData -> cellData.getValue().getInvestigation().get(s).valueProperty());
				column.setId(investigation.getName());
				column.setEditable(true);
				// column.setCellFactory(TextFieldTableCell.forTableColumn());
				column.setCellFactory(editCellFactory);
				column.setMinWidth(190);
				column.setPrefWidth(190);
				// count++;
				microbiologyColumnName.add(column);

				// partner Microbiology tableview
				TableColumn<UISTInvestigation, String> partnerMicroColumn = new TableColumn<>(MessageResource.getText(
						"MICROBIOLOGY." + (EnumInvestigation.MicroBiology.getEnumByKey(investigation.getName()))));
				partnerMicroColumn.setCellValueFactory(
						cellData -> cellData.getValue().getPartnerInvestigation().get(s).valueProperty());
				partnerMicroColumn.setId(investigation.getName());
				partnerMicroColumn.setEditable(true);
				// partnerMicroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				partnerMicroColumn.setCellFactory(editCellFactory);
				partnerMicroColumn.setMinWidth(190);
				partnerMicroColumn.setPrefWidth(190);
				count++;
				partnerMicrobiologyColumnName.add(partnerMicroColumn);
				if (count == 2)
					count = 0;
			} else {
				TableColumn<UISTInvestigation, String> column = new TableColumn<>(MessageResource.getText(
						"HORMONAL." + (EnumInvestigation.HormonalAssay.getEnumByKey(investigation.getName()))));
				final int s = new Integer(count);
				column.setCellValueFactory(cellData -> cellData.getValue().getInvestigation().get(s).valueProperty());
				column.setId(investigation.getName());
				column.setEditable(true);
				// column.setCellFactory(TextFieldTableCell.forTableColumn());
				column.setCellFactory(editCellFactory);
				if (count == 3) {
					column.setMinWidth(110);
					column.setPrefWidth(110);
				} else {
					column.setMinWidth(90);
					column.setPrefWidth(90);
				}
				// count++;
				hormonalColumnName.add(column);

				// partner Microbiology tableview
				TableColumn<UISTInvestigation, String> partnerHromonalColumn = new TableColumn<>(
						MessageResource.getText(
								"HORMONAL." + (EnumInvestigation.HormonalAssay.getEnumByKey(investigation.getName()))));
				partnerHromonalColumn.setCellValueFactory(
						cellData -> cellData.getValue().getPartnerInvestigation().get(s).valueProperty());
				partnerHromonalColumn.setId(investigation.getName());
				partnerHromonalColumn.setEditable(true);
				// partnerHromonalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				partnerHromonalColumn.setCellFactory(editCellFactory);
				if (count == 3) {
					partnerHromonalColumn.setMinWidth(100);
					partnerHromonalColumn.setPrefWidth(100);
				} else {
					partnerHromonalColumn.setMinWidth(90);
					partnerHromonalColumn.setPrefWidth(90);
				}
				count++;
				partnerHormonalColumnName.add(partnerHromonalColumn);
				if (count == 4)
					count = 0;
			}

		}
		womanBloodWorkTable.getColumns().addAll(bloodColumnName);
		womanMicrobiologyTable.getColumns().addAll(microbiologyColumnName);
		womanHormonalTable.getColumns().addAll(hormonalColumnName);

		manBloodWorkTable.getColumns().addAll(partnerBloodColumnName);
		manMicrobiologyTable.getColumns().addAll(partnerMicrobiologyColumnName);
		manHormonalTable.getColumns().addAll(partnerHormonalColumnName);
		restrictTextFieldToDecimal(woamnBMITextField);
		restrictTextFieldToDecimal(partnerBMITextField);
		makeFieldsNonEditable();

	}

	@SuppressWarnings("unchecked")
	public void build() {
		resetFields();

		SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
		long sessionStatus = sessionObject.getComponent(Constants.IS_CURRUNT_CYCLE);
		if (sessionStatus != 1l) {
			editBtn.setDisable(true);
		} else {
			editBtn.setDisable(false);
		}

		remarksDto = uiRemarksService.getRemarksByCodeId(womanCode.getId(), SCREEN_ID);
		if (null != remarksDto)
			remarksTextArea.setText(remarksDto.getRemarksText());

		aoBloodList = FXCollections.observableArrayList();
		aoMicrobiologyList = FXCollections.observableArrayList();
		aoHormonalList = FXCollections.observableArrayList();
		aoPartnerBloodList = FXCollections.observableArrayList();
		aoPartnerMicrobiologyList = FXCollections.observableArrayList();
		aoPartnerHormonalList = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoBlood = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoMicrobiology = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoHormonal = FXCollections.observableArrayList();

		/** Partner table view data */
		ObservableList<UIInvestigation> aoPartnerBlood = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoPartnerMicrobiology = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoPartnerHormonal = FXCollections.observableArrayList();
		commonDetailController.setMainApp(mainApp);
		commonDetailController.setBackgroundColor(false);
		// List<Client> client = uiClientService.getClients();
		// if(client.size()>0 && client!=null){
		partnerBMITextField.setText("0.0");
		woamnBMITextField.setText("0.0");
		if (null != womanCode && null != manCode) {
			
			registrantDto = uiRegistrantService.getRegistrantByCodeAndScreenID(womanCode.getId(), SCREEN_ID);
			if (null != registrantDto) {
				nurseRegistrant.setText(registrantDto.getNameOfUser());
				asstNurseRegistrant.setText(registrantDto.getAssistantUser());
			}			
			// Client latestClient = client.get(client.size()-1);
			// commonDetailController.setCouple(couple);
			// Client partnerClient = couple.getMan();
			// Client womanClient = couple.getWoman();
			// List<Codes> aoPartnerCode = partnerClient.getCodes();
			// List<Codes> aoWomanCodeList = womanClient.getCodes();
			// Codes partnerCode = aoPartnerCode.size() > 0 ?
			// aoPartnerCode.get(0) : null;
			// Codes womanCode = aoWomanCodeList.size() > 0 ?
			// aoWomanCodeList.get(0) : null;
			commonDetailController.setWomanPersonalInfo(womanCode);
			commonDetailController.setPartnerPersonalInfo(manCode);
			partnerPatientInvestigation = manCode != null ? manCode.getPatientInvestigation() : null;
			womanPatientInvestigation = womanCode != null ? womanCode.getPatientInvestigation() : null;
			if (partnerPatientInvestigation != null) {
				List<InvestigatinValue> aoPartnerInvestigtaionValue = partnerPatientInvestigation
						.getInvestigatinValues();
				for (InvestigatinValue investigatinValue : aoPartnerInvestigtaionValue) {
					if (investigatinValue.getInvestigation().getGroup() == BLOODWORK) {
						UIInvestigation uiInvestigation1 = new UIInvestigation(investigatinValue.getValue(),
								investigatinValue.getInvestigation().getId().intValue());
						aoPartnerBlood.add(uiInvestigation1);
					} else if (investigatinValue.getInvestigation().getGroup() == MICROBIOLOGY) {
						UIInvestigation uiInvestigation1 = new UIInvestigation(investigatinValue.getValue(),
								investigatinValue.getInvestigation().getId().intValue());
						aoPartnerMicrobiology.add(uiInvestigation1);
					} else {
						UIInvestigation uiInvestigation1 = new UIInvestigation(investigatinValue.getValue(),
								investigatinValue.getInvestigation().getId().intValue());
						aoPartnerHormonal.add(uiInvestigation1);
					}
				}
				partnerBMITextField.setText("" + partnerPatientInvestigation.getBmi());
			}
			if (womanPatientInvestigation != null) {
				List<InvestigatinValue> aoWomanInvestigationValue = womanPatientInvestigation.getInvestigatinValues();
				for (InvestigatinValue investigatinValue : aoWomanInvestigationValue) {
					if (investigatinValue.getInvestigation().getGroup() == BLOODWORK) {
						UIInvestigation uiInvestigation = new UIInvestigation(investigatinValue.getValue(),
								investigatinValue.getInvestigation().getId().intValue());
						aoBlood.add(uiInvestigation);
					} else if (investigatinValue.getInvestigation().getGroup() == MICROBIOLOGY) {
						UIInvestigation uiInvestigation = new UIInvestigation(investigatinValue.getValue(),
								investigatinValue.getInvestigation().getId().intValue());
						aoMicrobiology.add(uiInvestigation);
					} else {
						UIInvestigation uiInvestigation = new UIInvestigation(investigatinValue.getValue(),
								investigatinValue.getInvestigation().getId().intValue());
						aoHormonal.add(uiInvestigation);
					}
				}
				woamnBMITextField.setText("" + womanPatientInvestigation.getBmi());
				pScanTextArea.setText(womanPatientInvestigation.getScan());
			}
		}

		if (womanPatientInvestigation == null && partnerPatientInvestigation == null) {
			womanBloodWorkTable.setEditable(true);
			womanMicrobiologyTable.setEditable(true);
			womanHormonalTable.setEditable(true);
			manBloodWorkTable.setEditable(true);
			manMicrobiologyTable.setEditable(true);
			manHormonalTable.setEditable(true);
		} else {
			womanBloodWorkTable.setEditable(false);
			womanMicrobiologyTable.setEditable(false);
			womanHormonalTable.setEditable(false);
			manBloodWorkTable.setEditable(false);
			manMicrobiologyTable.setEditable(false);
			manHormonalTable.setEditable(false);
		}
		/*
		 * int womanBlood = aoBlood.size(); int womanMicro =
		 * aoMicrobiology.size(); int womanHor = aoHormonal.size();
		 *
		 * int manBlood = aoPartnerBlood.size(); int manMicro =
		 * aoPartnerMicrobiology.size(); int manHor = aoPartnerHormonal.size();
		 *
		 * for (int i = womanBlood; i <6-womanBlood; i++) { UIInvestigation
		 * uiInvestigation = new UIInvestigation("", i);
		 * aoBlood.add(uiInvestigation); } for (int i = womanMicro; i
		 * <2-womanMicro; i++) { UIInvestigation uiInvestigation = new
		 * UIInvestigation("", i); aoMicrobiology.add(uiInvestigation); } for
		 * (int i = womanHor; i <4-womanHor; i++) { UIInvestigation
		 * uiInvestigation = new UIInvestigation("", i);
		 * aoHormonal.add(uiInvestigation); }
		 */
		
		if(womanPatientInvestigation == null && partnerPatientInvestigation == null) {
			for (Investigation investigation : groups) {
				if (investigation.getGroup() == BLOODWORK) {
					UIInvestigation uiInvestigation = new UIInvestigation("", investigation.getId().intValue());
					aoBlood.add(uiInvestigation);
					UIInvestigation uiInvestigation1 = new UIInvestigation("", investigation.getId().intValue());
					aoPartnerBlood.add(uiInvestigation1);
	
				} else if (investigation.getGroup() == MICROBIOLOGY) {
					UIInvestigation uiInvestigation = new UIInvestigation("", investigation.getId().intValue());
					aoMicrobiology.add(uiInvestigation);
					UIInvestigation uiInvestigation1 = new UIInvestigation("", investigation.getId().intValue());
					aoPartnerMicrobiology.add(uiInvestigation1);
	
				} else {
					UIInvestigation uiInvestigation = new UIInvestigation("", investigation.getId().intValue());
					aoHormonal.add(uiInvestigation);
					UIInvestigation uiInvestigation1 = new UIInvestigation("", investigation.getId().intValue());
					aoPartnerHormonal.add(uiInvestigation1);
				}
			}
		}

		UISTInvestigation uiSTBloodInvestigation = new UISTInvestigation(aoBlood, null);
		aoBloodList.add(uiSTBloodInvestigation);
		UISTInvestigation uiSTMicrobiologyInvestigation = new UISTInvestigation(aoMicrobiology, null);
		aoMicrobiologyList.add(uiSTMicrobiologyInvestigation);
		UISTInvestigation uiSTHormonalInvestigation = new UISTInvestigation(aoHormonal, null);
		aoHormonalList.add(uiSTHormonalInvestigation);

		UISTInvestigation uiSTPartnerBloodInvestigation = new UISTInvestigation(null, aoPartnerBlood);
		aoPartnerBloodList.add(uiSTPartnerBloodInvestigation);
		UISTInvestigation uiSTPartnerMicrobiologyInvestigation = new UISTInvestigation(null, aoPartnerMicrobiology);
		aoPartnerMicrobiologyList.add(uiSTPartnerMicrobiologyInvestigation);
		UISTInvestigation uiSTPartnerHormonalInvestigation = new UISTInvestigation(null, aoPartnerHormonal);
		aoPartnerHormonalList.add(uiSTPartnerHormonalInvestigation);

		/*
		 * UISTInvestigation uiSTBloodInvestigation = new
		 * UISTInvestigation(aoBlood,); aoBloodList.add(uiSTBloodInvestigation);
		 * UISTInvestigation uiSTMicrobiologyInvestigation = new
		 * UISTInvestigation(aoBlood);
		 * aoMicrobiologyList.add(uiSTMicrobiologyInvestigation);
		 * UISTInvestigation uiSTHormonalInvestigation = new
		 * UISTInvestigation(aoBlood);
		 * aoHormonalList.add(uiSTHormonalInvestigation);
		 */

		womanBloodWorkTable.setItems(aoBloodList);
		womanMicrobiologyTable.setItems(aoMicrobiologyList);
		womanHormonalTable.setItems(aoHormonalList);

		manBloodWorkTable.setItems(aoPartnerBloodList);
		manMicrobiologyTable.setItems(aoPartnerMicrobiologyList);
		manHormonalTable.setItems(aoPartnerHormonalList);

		ObservableList<Nurse> data = FXCollections.observableArrayList();
		if (login != null) {
			Nurse nurse = new Nurse(login.getId(), login.getSurname());
			data.add(nurse);
			nurseComboBox.setItems(data);
			// Define rendering of the list of values in ComboBox drop down.
			nurseComboBox.setCellFactory((comboBox) -> {
				return new ListCell<Nurse>() {
					@Override
					protected void updateItem(Nurse item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
						} else {
							setText(item.getName());
						}
					}
				};
			});
			// Define rendering of selected value shown in ComboBox.
			nurseComboBox.setConverter(new StringConverter<Nurse>() {
				@Override
				public String toString(Nurse item) {
					if (item == null) {
						return null;
					} else {
						return item.getName();
					}
				}

				@Override
				public Nurse fromString(String itemString) {
					return null; // No conversion fromString needed.
				}
			});
			nurseComboBox.getSelectionModel().select(0);
		}
		makeFieldsNonEditable();
	}

	/**
	 * Invokes on click of Save button. Save values from all fields and table we
	 * have
	 *
	 * @param actionEvent
	 */
	public void saveAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.STANDARD_INVESTIGATION.getKey())) {

			PatientInvestigationDto dto = new PatientInvestigationDto();

			if (womanPatientInvestigation != null) {
				dto.setWomanPatientInvestigationId(womanPatientInvestigation.getId());
			}
			if (partnerPatientInvestigation != null) {
				dto.setPartnerPatientInvestigationId(partnerPatientInvestigation.getId());
			}

			// setting codes for each
			dto.setWomanCodeId(womanCode.getId());
			dto.setPartnerCodeId(manCode.getId());

			// setting BMIS of each
			dto.setBmi(!StringUtils.isEmpty(woamnBMITextField.getText()) ? Float.valueOf(woamnBMITextField.getText())
					: 0f);
			dto.setPartnerBMI(!StringUtils.isEmpty(partnerBMITextField.getText())
					? Float.valueOf(partnerBMITextField.getText()) : 0f);

			// setting Investigation list for Woman
			dto.setInvestigatinValues(aoWomanDto(womanBloodWorkTable.getItems()));
			dto.setAoMicrobiology(aoWomanDto(womanMicrobiologyTable.getItems()));
			dto.setAoHormonal(aoWomanDto(womanHormonalTable.getItems()));

			// setting Investigation list for Partner/Man
			dto.setAoPartnerBlood(aoPartnerDto(manBloodWorkTable.getItems()));
			dto.setAoPartnerMicrobiology(aoPartnerDto(manMicrobiologyTable.getItems()));
			dto.setAoPartnerHormonal(aoPartnerDto(manHormonalTable.getItems()));

			// setting other values
			dto.setNurseId(nurseComboBox.getSelectionModel().getSelectedItem().getId());
			dto.setScan(pScanTextArea.getText());

			// saving to db
			patientInvestigationService.saveOrUpdate(dto);
			manCode = codesService.getCodeById(manCode.getId());
			womanCode = codesService.getCodeById(womanCode.getId());

			if (!StringUtils.isEmpty(remarksTextArea.getText())) {

				if (remarksDto == null) {
					remarksDto = new RemarksDto();
				}
				remarksDto.setRemarksText(remarksTextArea.getText());
				remarksDto.setCodeId(womanCode.getId());
				remarksDto.setRemarksType(SCREEN_ID);
				remarksDto = uiRemarksService.save(remarksDto);
			}
			build();
		} else {
			FileUtils.privillegeEditError();
		}

	}
	
	
	/**
	 * Save registrant.
	 */
	@FXML
	private void saveRegistrant() {
		registrantDto = FileUtils.saveOrUpdateRegistrant(registrantDto, womanCode.getId(), SCREEN_ID, nurseRegistrant, asstNurseRegistrant);
	}

	/**
	 * Create Investigation DTO list for Woman's tables of Standard
	 * Investigation
	 *
	 * @param aoObservableList
	 *            from Woman tables only
	 * @return List<InvestigatinValueDto>
	 */
	public List<InvestigatinValueDto> aoWomanDto(ObservableList<UISTInvestigation> aoObservableList) {
		List<InvestigatinValueDto> aoDto = new ArrayList<>();
		for (UISTInvestigation uiStInvestigation : aoObservableList) {
			for (UIInvestigation uiInvestigation : uiStInvestigation.getInvestigation()) {
				aoDto.add(createInvestigationValueDto(uiInvestigation));
			}
		}
		return aoDto;
	}

	/**
	 * Create Investigation DTO list for Partner's tables of Standard
	 * Investigation
	 *
	 * @param aoObservableList
	 *            : List from Partner Tables only
	 * @return List<InvestigatinValueDto>
	 */
	public List<InvestigatinValueDto> aoPartnerDto(ObservableList<UISTInvestigation> aoObservableList) {
		List<InvestigatinValueDto> aoDto = new ArrayList<>();
		for (UISTInvestigation uiStInvestigation : aoObservableList) {
			for (UIInvestigation uiInvestigation : uiStInvestigation.getPartnerInvestigation()) {
				aoDto.add(createInvestigationValueDto(uiInvestigation));
			}
		}
		return aoDto;
	}

	/**
	 * Create InvestigatinValueDto from UIInvestigation object
	 * 
	 * @param investigation
	 * @return InvestigatinValueDto
	 */
	public InvestigatinValueDto createInvestigationValueDto(UIInvestigation investigation) {
		InvestigatinValueDto dto = new InvestigatinValueDto();
		dto.setInvestigationId(Long.valueOf(investigation.getDayIndex()));
		dto.setValue(investigation.getValue());
		return dto;
	}

	/*
	 * @FXML public void saveAction1(ActionEvent actionEvent) { if
	 * (EnumPermission.canWrite(login.getRoleId(),
	 * Module.STANDARD_INVESTIGATION.getKey())) { // This Portion is Commented
	 * As now validation for not to enter // String value is done in
	 * restrictTextFieldToDecimal method // if //
	 * (woamnBMITextField.getText().trim().matches("[-]?[0-9]*\\.?[0-9]+") // &&
	 * // partnerBMITextField.getText().trim().matches("[-]?[0-9]*\\.?[0-9]+"))
	 * // {
	 * 
	 * List<InvestigatinValueDto> aoBlood = new ArrayList<>();
	 * List<InvestigatinValueDto> aoMicrobiology = new ArrayList<>();
	 * List<InvestigatinValueDto> aoHormonal = new ArrayList<>();
	 *//** Partner table view data */

	/*
	 * List<InvestigatinValueDto> aoPartnerBlood = new ArrayList<>();
	 * List<InvestigatinValueDto> aoPartnerMicrobiology = new ArrayList<>();
	 * List<InvestigatinValueDto> aoPartnerHormonal = new ArrayList<>();
	 * ObservableList<UISTInvestigation> listBlood =
	 * womanBloodWorkTable.getItems();
	 * 
	 * ObservableList<UISTInvestigation> listMicro =
	 * womanMicrobiologyTable.getItems();
	 * 
	 * ObservableList<UISTInvestigation> listHormonal =
	 * womanHormonalTable.getItems();
	 * 
	 * ObservableList<UISTInvestigation> listPartnerBlood =
	 * manBloodWorkTable.getItems();
	 * 
	 * ObservableList<UISTInvestigation> listPartnerMicro =
	 * manMicrobiologyTable.getItems();
	 * 
	 * ObservableList<UISTInvestigation> listPartnerHormonal =
	 * manHormonalTable.getItems();
	 * 
	 * Nurse nurse = nurseComboBox.getSelectionModel().getSelectedItem();
	 * 
	 * if(womanPatientInvestigation == null ){ // Save Woman Patient
	 * Investigation data PatientInvestigationDto uiPatientInvestigation = new
	 * PatientInvestigationDto();
	 * uiPatientInvestigation.setWomanCodeId(womanCode != null ?
	 * womanCode.getId() : 0l);
	 * uiPatientInvestigation.setBmi(!StringUtils.isEmpty(woamnBMITextField.
	 * getText()) ? Float.valueOf(woamnBMITextField.getText()) : 0f);
	 * uiPatientInvestigation.setNurseId(nurse.getId());
	 * if(!StringUtils.isEmpty(pScanTextArea.getText()))
	 * uiPatientInvestigation.setScan(pScanTextArea.getText());
	 * 
	 * for (UISTInvestigation uistInvestigation : listBlood) { for
	 * (UIInvestigation invest : uistInvestigation.getInvestigation()) {
	 * InvestigatinValueDto uiInvestigatinValue = new InvestigatinValueDto();
	 * uiInvestigatinValue.setInvestigationId(Long.valueOf(invest.getDayIndex())
	 * ); uiInvestigatinValue.setValue(invest.getValue());
	 * aoBlood.add(uiInvestigatinValue); } } for (UISTInvestigation
	 * uistInvestigation : listMicro) { for (UIInvestigation invest :
	 * uistInvestigation.getInvestigation()) { InvestigatinValueDto
	 * uiInvestigatinValue = new InvestigatinValueDto();
	 * uiInvestigatinValue.setInvestigationId(Long.valueOf(invest.getDayIndex())
	 * ); uiInvestigatinValue.setValue(invest.getValue());
	 * aoMicrobiology.add(uiInvestigatinValue); } } for (UISTInvestigation
	 * uistInvestigation : listHormonal) { for (UIInvestigation invest :
	 * uistInvestigation.getInvestigation()) { InvestigatinValueDto
	 * uiInvestigatinValue = new InvestigatinValueDto();
	 * uiInvestigatinValue.setInvestigationId(Long.valueOf(invest.getDayIndex())
	 * ); uiInvestigatinValue.setValue(invest.getValue());
	 * aoHormonal.add(uiInvestigatinValue); } }
	 * uiPatientInvestigation.setInvestigatinValues(aoBlood);
	 * uiPatientInvestigation.setAoMicrobiology(aoMicrobiology);
	 * uiPatientInvestigation.setAoHormonal(aoHormonal);
	 * 
	 * PatientInvestigationDto pInvestigation = patientInvestigationService
	 * .addWomanPatientInvestigation(uiPatientInvestigation); // womanCode =
	 * codesService.getCodeById(pInvestigation.getWomanCodeId()); // manCode =
	 * codesService.getCodeById(pInvestigation.getPartnerCodeId()); }
	 * 
	 * if(partnerPatientInvestigation == null){ // Save Partner Patient
	 * Investigation data PatientInvestigationDto uiPatientInvestigation = new
	 * PatientInvestigationDto();
	 * uiPatientInvestigation.setPartnerCodeId(manCode != null ? manCode.getId()
	 * : 0l); uiPatientInvestigation.setPartnerBMI(!StringUtils.isEmpty(
	 * partnerBMITextField.getText()) ?
	 * Float.valueOf(partnerBMITextField.getText()) : 0f);
	 * uiPatientInvestigation.setNurseId(nurse.getId());
	 *//** getting partner's investigation values *//*
													 * for (UISTInvestigation
													 * uistInvestigation :
													 * listPartnerBlood) { for
													 * (UIInvestigation invest :
													 * uistInvestigation.
													 * getPartnerInvestigation()
													 * ) { InvestigatinValueDto
													 * uiInvestigatinValue = new
													 * InvestigatinValueDto();
													 * uiInvestigatinValue.
													 * setInvestigationId(Long.
													 * valueOf(invest.
													 * getDayIndex()));
													 * uiInvestigatinValue.
													 * setValue(invest.getValue(
													 * )); aoPartnerBlood.add(
													 * uiInvestigatinValue); } }
													 * for (UISTInvestigation
													 * uistInvestigation :
													 * listPartnerMicro) { for
													 * (UIInvestigation invest :
													 * uistInvestigation.
													 * getPartnerInvestigation()
													 * ) { InvestigatinValueDto
													 * uiInvestigatinValue = new
													 * InvestigatinValueDto();
													 * uiInvestigatinValue.
													 * setInvestigationId(Long.
													 * valueOf(invest.
													 * getDayIndex()));
													 * uiInvestigatinValue.
													 * setValue(invest.getValue(
													 * ));
													 * aoPartnerMicrobiology.add
													 * (uiInvestigatinValue); }
													 * } for (UISTInvestigation
													 * uistInvestigation :
													 * listPartnerHormonal) {
													 * for (UIInvestigation
													 * invest :
													 * uistInvestigation.
													 * getPartnerInvestigation()
													 * ) { InvestigatinValueDto
													 * uiInvestigatinValue = new
													 * InvestigatinValueDto();
													 * uiInvestigatinValue.
													 * setInvestigationId(Long.
													 * valueOf(invest.
													 * getDayIndex()));
													 * uiInvestigatinValue.
													 * setValue(invest.getValue(
													 * ));
													 * aoPartnerHormonal.add(
													 * uiInvestigatinValue); } }
													 * uiPatientInvestigation.
													 * setAoPartnerBlood(
													 * aoPartnerBlood);
													 * uiPatientInvestigation.
													 * setAoPartnerMicrobiology(
													 * aoPartnerMicrobiology);
													 * uiPatientInvestigation.
													 * setAoPartnerHormonal(
													 * aoPartnerHormonal);
													 * 
													 * PatientInvestigationDto
													 * pInvestigation =
													 * patientInvestigationService
													 * .addPartnerPatientInvestigation
													 * (uiPatientInvestigation);
													 * // womanCode =
													 * codesService.getCodeById(
													 * pInvestigation.
													 * getWomanCodeId());
													 * //manCode =
													 * codesService.getCodeById(
													 * pInvestigation.
													 * getPartnerCodeId()); }
													 * 
													 * if(
													 * womanPatientInvestigation
													 * != null){ //update woman
													 * Patient Investigation
													 * data
													 * PatientInvestigationDto
													 * uiPatientInvestigation =
													 * new
													 * PatientInvestigationDto()
													 * ; uiPatientInvestigation.
													 * setWomanPatientInvestigationId
													 * (
													 * womanPatientInvestigation
													 * .getId()); String
													 * womanBMI =
													 * woamnBMITextField.getText
													 * ().length() > 0 ?
													 * woamnBMITextField.getText
													 * () : "0";
													 * uiPatientInvestigation.
													 * setBmi(Float.parseFloat(
													 * womanBMI.trim().isEmpty()
													 * ? "0" : womanBMI));
													 * uiPatientInvestigation.
													 * setNurseId(nurse.getId())
													 * ; uiPatientInvestigation.
													 * setScan(pScanTextArea.
													 * getText());
													 * List<InvestigatinValue>
													 * aoWomanIvestigationValue
													 * =
													 * womanPatientInvestigation
													 * .getInvestigatinValues();
													 * int count = 0; int
													 * microCount = 0; int
													 * hormCount = 0; for
													 * (InvestigatinValue
													 * investigatinValue :
													 * aoWomanIvestigationValue)
													 * { if (investigatinValue.
													 * getInvestigation().
													 * getGroup() == BLOODWORK)
													 * { // woman's Blood Work
													 * table view data
													 * UIInvestigation
													 * womanInvest =
													 * listBlood.get(0).
													 * getInvestigation().get(
													 * count);
													 * InvestigatinValueDto
													 * womanUIInvestigatinValueBlood
													 * = new
													 * InvestigatinValueDto();
													 * womanUIInvestigatinValueBlood
													 * .setInvestigationValueId(
													 * investigatinValue.getId()
													 * );
													 * womanUIInvestigatinValueBlood
													 * .setInvestigationId(Long.
													 * valueOf(womanInvest.
													 * getDayIndex()));
													 * womanUIInvestigatinValueBlood
													 * .setValue(womanInvest.
													 * getValue()); aoBlood.add(
													 * womanUIInvestigatinValueBlood
													 * ); count++; } else if
													 * (investigatinValue.
													 * getInvestigation().
													 * getGroup() ==
													 * MICROBIOLOGY) { //
													 * woman's Micro biology
													 * table view data
													 * UIInvestigation
													 * womanInvest =
													 * listMicro.get(0).
													 * getInvestigation().get(
													 * microCount);
													 * InvestigatinValueDto
													 * womanUIInvestigatinValueMicro
													 * = new
													 * InvestigatinValueDto();
													 * womanUIInvestigatinValueMicro
													 * .setInvestigationValueId(
													 * investigatinValue.getId()
													 * );
													 * womanUIInvestigatinValueMicro
													 * .setInvestigationId(Long.
													 * valueOf(womanInvest.
													 * getDayIndex()));
													 * womanUIInvestigatinValueMicro
													 * .setValue(womanInvest.
													 * getValue());
													 * aoMicrobiology.add(
													 * womanUIInvestigatinValueMicro
													 * ); microCount++; } else {
													 * // woman's Hormonal Assay
													 * table view data
													 * UIInvestigation
													 * womanInvest =
													 * listHormonal.get(0).
													 * getInvestigation().get(
													 * hormCount);
													 * InvestigatinValueDto
													 * womanUIInvestigatinValueHormonal
													 * = new
													 * InvestigatinValueDto();
													 * womanUIInvestigatinValueHormonal
													 * .setInvestigationValueId(
													 * investigatinValue.getId()
													 * );
													 * womanUIInvestigatinValueHormonal
													 * .setInvestigationId(Long.
													 * valueOf(womanInvest.
													 * getDayIndex()));
													 * womanUIInvestigatinValueHormonal
													 * .setValue(womanInvest.
													 * getValue());
													 * aoHormonal.add(
													 * womanUIInvestigatinValueHormonal
													 * ); hormCount++; } }
													 * uiPatientInvestigation.
													 * setInvestigatinValues(
													 * aoBlood);
													 * uiPatientInvestigation.
													 * setAoMicrobiology(
													 * aoMicrobiology);
													 * uiPatientInvestigation.
													 * setAoHormonal(aoHormonal)
													 * ;
													 * 
													 * patientInvestigationService
													 * .
													 * updateWomanPatientInvestigation
													 * (uiPatientInvestigation);
													 * 
													 * // updating current
													 * couple object couple =
													 * coupleService.
													 * getCoupleById(couple.
													 * getId()); //womanCode =
													 * codesService.getCodeById(
													 * womanCode.getId()); //
													 * manCode =
													 * codesService.getCodeById(
													 * manCode.getId()); }
													 * 
													 * if(
													 * partnerPatientInvestigation
													 * != null){
													 * PatientInvestigationDto
													 * uiPatientInvestigation =
													 * new
													 * PatientInvestigationDto()
													 * ; uiPatientInvestigation.
													 * setPartnerPatientInvestigationId
													 * (
													 * partnerPatientInvestigation
													 * .getId()); String manBMI
													 * = partnerBMITextField.
													 * getText().length() > 0 ?
													 * partnerBMITextField.
													 * getText() : "0";
													 * uiPatientInvestigation.
													 * setPartnerBMI(Float.
													 * parseFloat(manBMI.trim().
													 * isEmpty() ? "0" :
													 * manBMI));
													 * List<InvestigatinValue>
													 * aoPartnerIvestigationValue
													 * =
													 * partnerPatientInvestigation
													 * .getInvestigatinValues();
													 * int count = 0; int
													 * microCount = 0; int
													 * hormCount = 0; for
													 * (InvestigatinValue
													 * investigatinValue :
													 * aoPartnerIvestigationValue)
													 * { if (investigatinValue.
													 * getInvestigation().
													 * getGroup() == BLOODWORK)
													 * { // partner's Blood Work
													 * table view data
													 * UIInvestigation invest =
													 * listPartnerBlood.get(0).
													 * getPartnerInvestigation()
													 * .get(count);
													 * InvestigatinValueDto
													 * uiInvestigatinValue = new
													 * InvestigatinValueDto();
													 * uiInvestigatinValue.
													 * setInvestigationValueId(
													 * investigatinValue.getId()
													 * ); uiInvestigatinValue.
													 * setInvestigationId(Long.
													 * valueOf(invest.
													 * getDayIndex()));
													 * uiInvestigatinValue.
													 * setValue(invest.getValue(
													 * )); aoPartnerBlood.add(
													 * uiInvestigatinValue);
													 * count++; } else if
													 * (investigatinValue.
													 * getInvestigation().
													 * getGroup() ==
													 * MICROBIOLOGY) { //
													 * partner's table
													 * Microbiology view data
													 * UIInvestigation invest =
													 * listPartnerMicro.get(0).
													 * getPartnerInvestigation()
													 * .get(microCount);
													 * InvestigatinValueDto
													 * uiInvestigatinValue = new
													 * InvestigatinValueDto();
													 * uiInvestigatinValue.
													 * setInvestigationValueId(
													 * investigatinValue.getId()
													 * ); uiInvestigatinValue.
													 * setInvestigationId(Long.
													 * valueOf(invest.
													 * getDayIndex()));
													 * uiInvestigatinValue.
													 * setValue(invest.getValue(
													 * ));
													 * aoPartnerMicrobiology.add
													 * (uiInvestigatinValue);
													 * microCount++; } else { //
													 * partner's Hormonal Assay
													 * table view data
													 * UIInvestigation invest =
													 * listPartnerHormonal.get(0
													 * ).getPartnerInvestigation
													 * ().get(hormCount);
													 * InvestigatinValueDto
													 * uiInvestigatinValue = new
													 * InvestigatinValueDto();
													 * uiInvestigatinValue.
													 * setInvestigationValueId(
													 * investigatinValue.getId()
													 * ); uiInvestigatinValue.
													 * setInvestigationId(Long.
													 * valueOf(invest.
													 * getDayIndex()));
													 * uiInvestigatinValue.
													 * setValue(invest.getValue(
													 * ));
													 * aoPartnerHormonal.add(
													 * uiInvestigatinValue);
													 * hormCount++; } }
													 * 
													 * uiPatientInvestigation.
													 * setAoPartnerBlood(
													 * aoPartnerBlood);
													 * uiPatientInvestigation.
													 * setAoPartnerMicrobiology(
													 * aoPartnerMicrobiology);
													 * uiPatientInvestigation.
													 * setAoPartnerHormonal(
													 * aoPartnerHormonal);
													 * patientInvestigationService
													 * .
													 * updatePartnerPatientInvestigation
													 * (uiPatientInvestigation);
													 * 
													 * // updating current
													 * couple object couple =
													 * coupleService.
													 * getCoupleById(couple.
													 * getId()); //womanCode =
													 * codesService.getCodeById(
													 * womanCode.getId());
													 * //manCode =
													 * codesService.getCodeById(
													 * manCode.getId()); }
													 * 
													 * if (!StringUtils.isEmpty(
													 * remarksTextArea.getText()
													 * )) {
													 * 
													 * if (remarksDto == null) {
													 * remarksDto = new
													 * RemarksDto(); }
													 * remarksDto.setRemarksText
													 * (remarksTextArea.getText(
													 * )); remarksDto.setCodeId(
													 * womanCode.getId());
													 * remarksDto.setRemarksType
													 * (remarksType); remarksDto
													 * = uiRemarksService.save(
													 * remarksDto); } build();
													 * 
													 * Alert alert = new
													 * Alert(AlertType.
													 * INFORMATION);
													 * alert.setContentText(
													 * "inprogress..");
													 * alert.showAndWait();
													 * 
													 * womanBloodWorkTable.
													 * setEditable(false);
													 * womanMicrobiologyTable.
													 * setEditable(false);
													 * womanHormonalTable.
													 * setEditable(false);
													 * manBloodWorkTable.
													 * setEditable(false);
													 * manMicrobiologyTable.
													 * setEditable(false);
													 * manHormonalTable.
													 * setEditable(false);
													 * 
													 * } else { Notify notify =
													 * new Notify(AlertType.
													 * INFORMATION);
													 * notify.setContentText(
													 * MessageResource.getText(
													 * "standardinvestigation.controller.info.message"
													 * )); notify.showAndWait();
													 *
													 * }
													 * 
													 * } else { FileUtils.
													 * privillegeEditError(); }
													 * }
													 */

	@FXML
	public void editAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.STANDARD_INVESTIGATION.getKey())) {
			makeFieldsEditable();
			editInvestigationTables(true);
		} else {
			FileUtils.privillegeEditError();
		}
	}

	private void editInvestigationTables(boolean flag) {
		womanBloodWorkTable.setEditable(flag);
		womanMicrobiologyTable.setEditable(flag);
		womanHormonalTable.setEditable(flag);
		manBloodWorkTable.setEditable(flag);
		manMicrobiologyTable.setEditable(flag);
		manHormonalTable.setEditable(flag);
	}

	@FXML
	public void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		List<Node> nodes = createNodes(pageLayout);
		int page = 1;
		if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
			for (Node node : nodes) {
				BorderPane printPage = createPrintPage(node, page, pageLayout);
				printerJob.printPage(printPage);
				page++;
			}
			printerJob.endJob();
		}
	}

	// Creating ST nodes (ST all Nodes on one page & remarks section on other
	// page)
	private List<Node> createNodes(PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox STVBox = createStandardInvestigationPageContent(pageLayout);
		List<Node> remarkNodeList = printTemplate.createRemarks(remarksTextArea.getText(), pageLayout);
		nodes.add(STVBox);
		for (Node remarkNode : remarkNodeList) {
			nodes.add(remarkNode);
		}
		return nodes;
	}

	/**
	 * Setting page content in BorderPane for printing
	 *
	 * @param table
	 *            the Table view
	 * @param page
	 *            no of page to be printed
	 * @param pageLayout
	 *            pageLayout
	 * @return BorderPane
	 */
	private BorderPane createPrintPage(Node node, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		contentVBox.setPrefWidth(pageLayout.getPrintableWidth());
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		Label spaceBetweenElements1 = printTemplate.spaceBetweenElements(20);
		// This Section for Registrant Details.
		VBox registrantDetailVBox = printTemplate.createRegistrantCommonInfo(pageLayout, registrantDto,
				MessageResource.getText("common.emb.nurse.registrant"), MessageResource.getText("common.emb.asst.nurse.registrant"));
		Label spaceBetweenElements2 = printTemplate.spaceBetweenElements(20);
		contentVBox.getChildren().addAll(patientGrid, spaceBetweenElements1,registrantDetailVBox, spaceBetweenElements2, node);
		root.setCenter(contentVBox);

		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	// Setting ST Content(Woman & Partner Investigation Section + Nurse Section)
	// in VBox
	public VBox createStandardInvestigationPageContent(PageLayout pageLayout) {
		VBox STVBox = new VBox();
		HBox womanHbox = createInvetigationHeaderHBox("Woman Investigation Info", pageLayout);
		GridPane womanInvestigationGrid = createWomanInvestigationSection(pageLayout);
		VBox unuseSpace = new VBox();
		unuseSpace.setPrefHeight(20);
		HBox partnerHbox = createInvetigationHeaderHBox("Partner Investigation Info", pageLayout);
		GridPane partnerInvestigationGrid = createPartnerInvestigationTable(pageLayout);
		VBox space = new VBox();
		space.setPrefHeight(20);
		HBox nurseHBox = createNurseHBox(pageLayout);
		STVBox.getChildren().addAll(womanHbox, womanInvestigationGrid, unuseSpace, partnerHbox,
				partnerInvestigationGrid, space, nurseHBox);
		return STVBox;
	}

	// Creating Woman Investigation Section
	private GridPane createWomanInvestigationSection(PageLayout pageLayout) {
		GridPane womanInvestigationGrid = new GridPane();
		womanInvestigationGrid.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		womanInvestigationGrid.setPrefWidth(pageLayout.getPrintableWidth());

		// Woman Blood Work
		Label bloodLabel = printTemplate.createStaticLabel(" Blood Work");
		ObservableList<TableColumn<UISTInvestigation, ?>> bloodWorkColumns = womanBloodWorkTable.getColumns();
		int bloodColCount = bloodWorkColumns.size();
		double bloodLabelWidth = pageLayout.getPrintableWidth() / bloodColCount;
		HBox bloodHeaderHbox = createInvestigationTableHeader(bloodWorkColumns, pageLayout, bloodLabelWidth);
		ObservableList<UIInvestigation> uiBloodInvestigationList = womanBloodWorkTable.getItems().get(0)
				.getInvestigation();
		HBox bloodRowHbox = createInvestigationTableRow(uiBloodInvestigationList, pageLayout, bloodLabelWidth,
				bloodColCount);

		// Woman Microbiology
		Label microbiolgyLabel = printTemplate.createStaticLabel(" MicroBiology");
		// Creating MicroBiology Columns
		ObservableList<TableColumn<UISTInvestigation, ?>> microBiologyColumns = womanMicrobiologyTable.getColumns();
		int microbiologyColCount = microBiologyColumns.size();
		double microbiologyLabelWidth = pageLayout.getPrintableWidth() / microbiologyColCount;
		HBox microbiologyHeaderHbox = createInvestigationTableHeader(microBiologyColumns, pageLayout,
				microbiologyLabelWidth);
		ObservableList<UIInvestigation> uiMicrobiologyInvestigationList = womanMicrobiologyTable.getItems().get(0)
				.getInvestigation();
		HBox microbiologyRowHbox = createInvestigationTableRow(uiMicrobiologyInvestigationList, pageLayout,
				microbiologyLabelWidth, microbiologyColCount);

		// Woman Hormonal Assay
		Label hormonalLabel = printTemplate.createStaticLabel(" Hormonal Assay");
		// Creating Hormonal Assay Columns & rows
		ObservableList<TableColumn<UISTInvestigation, ?>> hormonalColumns = womanHormonalTable.getColumns();
		int hormonalColCount = hormonalColumns.size();
		double hormonalLabelWidth = pageLayout.getPrintableWidth() / hormonalColCount;
		HBox hormonalHeaderHbox = createInvestigationTableHeader(hormonalColumns, pageLayout, hormonalLabelWidth);
		ObservableList<UIInvestigation> uiHormonalInvestigationList = womanHormonalTable.getItems().get(0)
				.getInvestigation();
		HBox hormonalRowHbox = createInvestigationTableRow(uiHormonalInvestigationList, pageLayout, hormonalLabelWidth,
				hormonalColCount);

		// Woman BMI
		HBox womanBMIHBox = new HBox();
		womanBMIHBox.getChildren().add(printTemplate.createStaticLabel(" BMI"));
		womanBMIHBox.setStyle("-fx-border-width: 0 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		Label womanBMIValueLabel = printTemplate
				.createDynamicLabel(null != woamnBMITextField.getText() ? woamnBMITextField.getText() : "");

		womanInvestigationGrid.add(bloodLabel, 0, 0);
		GridPane.setRowSpan(bloodLabel, 2);

		womanInvestigationGrid.add(bloodHeaderHbox, 1, 0);
		womanInvestigationGrid.add(bloodRowHbox, 1, 1);

		womanInvestigationGrid.add(microbiolgyLabel, 0, 2);
		GridPane.setRowSpan(microbiolgyLabel, 2);
		womanInvestigationGrid.add(microbiologyHeaderHbox, 1, 2);
		womanInvestigationGrid.add(microbiologyRowHbox, 1, 3);

		womanInvestigationGrid.add(hormonalLabel, 0, 4);
		GridPane.setRowSpan(hormonalLabel, 2);
		womanInvestigationGrid.add(hormonalHeaderHbox, 1, 4);
		womanInvestigationGrid.add(hormonalRowHbox, 1, 5);

		womanInvestigationGrid.add(womanBMIHBox, 0, 6);
		womanInvestigationGrid.add(womanBMIValueLabel, 1, 6);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(20);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(80);
		womanInvestigationGrid.getColumnConstraints().addAll(col1, col2);
		return womanInvestigationGrid;
	}

	// Creating Partner Investigation Section
	public GridPane createPartnerInvestigationTable(PageLayout pageLayout) {
		// Partner Investigation Table
		GridPane partnerInvestigationGrid = new GridPane();
		partnerInvestigationGrid.setPrefWidth(pageLayout.getPrintableWidth());
		partnerInvestigationGrid.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);

		// Partner Blood Work
		Label bloodLabel = printTemplate.createStaticLabel(" Blood Work");
		bloodLabel.setAlignment(Pos.CENTER);
		ObservableList<TableColumn<UISTInvestigation, ?>> manBloodWorkColumns = manBloodWorkTable.getColumns();
		int manBloodColCount = manBloodWorkColumns.size();
		double manBloodLabelWidth = pageLayout.getPrintableWidth() / manBloodColCount;
		HBox partnerBloodHeaderHbox = createInvestigationTableHeader(manBloodWorkColumns, pageLayout,
				manBloodLabelWidth);
		ObservableList<UIInvestigation> uiBloodPInvestigationList = manBloodWorkTable.getItems().get(0)
				.getPartnerInvestigation();
		HBox partnerBloodRowHbox = createInvestigationTableRow(uiBloodPInvestigationList, pageLayout,
				manBloodLabelWidth, manBloodColCount);

		// Partner Microbiology
		Label microbiolgyLabel = printTemplate.createStaticLabel(" MicroBiology");
		microbiolgyLabel.setAlignment(Pos.CENTER);
		ObservableList<TableColumn<UISTInvestigation, ?>> manMicrobiologyWorkColumns = manMicrobiologyTable
				.getColumns();
		int manMicrobiologyColCount = manMicrobiologyWorkColumns.size();
		double manMicrobiologyLabelWidth = (pageLayout.getPrintableWidth() / manMicrobiologyColCount) - 5;
		HBox partnerMicrobiologyHeaderHbox = createInvestigationTableHeader(manMicrobiologyWorkColumns, pageLayout,
				manMicrobiologyLabelWidth);
		ObservableList<UIInvestigation> uiMicrobiologyPInvestigationList = manMicrobiologyTable.getItems().get(0)
				.getPartnerInvestigation();
		HBox partnerMicrobiologyRowHbox = createInvestigationTableRow(uiMicrobiologyPInvestigationList, pageLayout,
				manMicrobiologyLabelWidth, manMicrobiologyColCount);

		// Partner Hormonal Assay
		Label hormonalLabel = printTemplate.createStaticLabel(" Hormonal Assay");
		hormonalLabel.setAlignment(Pos.CENTER);
		ObservableList<TableColumn<UISTInvestigation, ?>> manHormonalWorkColumns = manHormonalTable.getColumns();
		int manHormonalColCount = manHormonalWorkColumns.size();
		double manHormonalLabelWidth = pageLayout.getPrintableWidth() / manHormonalColCount;
		HBox partnerHormonalHeaderHbox = createInvestigationTableHeader(manHormonalTable.getColumns(), pageLayout,
				manHormonalLabelWidth);
		ObservableList<UIInvestigation> uiHormonalPInvestigationList = manHormonalTable.getItems().get(0)
				.getPartnerInvestigation();
		HBox partnerHormonalRowHbox = createInvestigationTableRow(uiHormonalPInvestigationList, pageLayout,
				manHormonalLabelWidth, manHormonalColCount);

		// partner BMI
		HBox partnerBMIHBox = new HBox();
		partnerBMIHBox.getChildren().add(printTemplate.createStaticLabel(" BMI"));
		partnerBMIHBox.setStyle("-fx-border-width: 0 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		Label partnerBMIValueLabel = printTemplate
				.createDynamicLabel(null != partnerBMITextField.getText() ? partnerBMITextField.getText() : "");

		partnerInvestigationGrid.add(bloodLabel, 0, 0);
		GridPane.setRowSpan(bloodLabel, 2);

		Separator verticalSeparator = printTemplate.createSeparator();
		partnerInvestigationGrid.add(verticalSeparator, 1, 0);
		GridPane.setRowSpan(verticalSeparator, 9);
		partnerInvestigationGrid.add(partnerBloodHeaderHbox, 1, 0);
		partnerInvestigationGrid.add(partnerBloodRowHbox, 1, 1);

		partnerInvestigationGrid.add(microbiolgyLabel, 0, 2);
		GridPane.setRowSpan(microbiolgyLabel, 2);
		partnerInvestigationGrid.add(partnerMicrobiologyHeaderHbox, 1, 2);
		partnerInvestigationGrid.add(partnerMicrobiologyRowHbox, 1, 3);

		partnerInvestigationGrid.add(hormonalLabel, 0, 4);
		GridPane.setRowSpan(hormonalLabel, 2);
		partnerInvestigationGrid.add(partnerHormonalHeaderHbox, 1, 4);
		partnerInvestigationGrid.add(partnerHormonalRowHbox, 1, 5);

		partnerInvestigationGrid.add(partnerBMIHBox, 0, 6);
		partnerInvestigationGrid.add(partnerBMIValueLabel, 1, 6);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(20);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(80);
		partnerInvestigationGrid.getColumnConstraints().addAll(col1, col2);
		return partnerInvestigationGrid;
	}

	// Creating Woman & Partner Investigation Section Headers
	public HBox createInvestigationTableHeader(ObservableList<TableColumn<UISTInvestigation, ?>> tableColumns,
			PageLayout pageLayout, double labelWidth) {
		HBox headerHbox = new HBox();
		headerHbox.setStyle("-fx-border-width: 0 0 0 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		// Creating investigation Columns
		for (int i = 0; i < tableColumns.size(); i++) {
			// condition to avoid the last separator
			if (i != tableColumns.size() - 1) {
				headerHbox.getChildren()
						.add(printTemplate.createTableHeaderLabel(tableColumns.get(i).getText(), labelWidth));
				headerHbox.getChildren().add(printTemplate.createSeparator());
			} else {
				headerHbox.getChildren()
						.add(printTemplate.createTableHeaderLabel(tableColumns.get(i).getText(), labelWidth));
			}
		}
		return headerHbox;
	}

	// Creating Woman & Partner Investigation Section Rows
	public HBox createInvestigationTableRow(ObservableList<UIInvestigation> uiInvestigationList, PageLayout pageLayout,
			double labelWidth, int colCount) {
		HBox rowHbox = new HBox();
		rowHbox.setStyle("-fx-border-width: 1 0 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		if (null != uiInvestigationList) {
			/*
			 * int count = 1; for (UIInvestigation invest : uiInvestigationList)
			 * { // condition to avoid the last separator if (count ==
			 * uiInvestigationList.size() / 2 && null != invest.getValue() &&
			 * !StringUtils.isEmpty(invest.getValue())) {
			 * rowHbox.getChildren().add(printTemplate.createTableRowLabel(
			 * invest.getValue(), labelWidth, false)); } else if (null !=
			 * invest.getValue() && !StringUtils.isEmpty(invest.getValue())) {
			 * rowHbox.getChildren().add(printTemplate.createTableRowLabel(
			 * invest.getValue(), labelWidth, false));
			 * rowHbox.getChildren().add(printTemplate.createSeparator());
			 * count++; } }
			 */

			for (int i = 0; i < colCount; i++) {
				String val = uiInvestigationList.get(i).getValue();
				if (i != colCount - 1) {
					rowHbox.getChildren().add(printTemplate.createTableRowLabel(val, labelWidth, false));
					rowHbox.getChildren().add(printTemplate.createSeparator());
				} else {
					rowHbox.getChildren().add(printTemplate.createTableRowLabel(val, labelWidth, false));
				}
			}
		} else {
			rowHbox.getChildren().add(printTemplate.createTableRowLabel("", labelWidth, false));
			rowHbox.getChildren().add(printTemplate.createSeparator());
		}
		return rowHbox;
	}

	// Creating Woman & Partner Investigation Section Main Title Headers
	private HBox createInvetigationHeaderHBox(String text, PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefHeight(40);
		headerHBox.setAlignment(Pos.CENTER);
		headerHBox.getChildren().add(printTemplate.createHeaderLabel(text));
		return headerHBox;
	}

	// Creating Nurse section for ST
	public HBox createNurseHBox(PageLayout pageLayout) {
		HBox hBox = new HBox();
		hBox.setPrefHeight(50);
		hBox.setPrefWidth(pageLayout.getPrintableWidth());
		hBox.setSpacing(30);
		hBox.setAlignment(Pos.CENTER_LEFT);
		Nurse nurse = nurseComboBox.getSelectionModel().getSelectedItem();
		hBox.getChildren().add(printTemplate.createStaticLabel("Nurse : "));
		hBox.getChildren().add(printTemplate.createDynamicLabel(nurse.getName()));
		return hBox;
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	public class Addcell extends TableCell<UIInvestigation, String> {

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			setText(empty ? null : getString());
			setGraphic(null);
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}

	public void makeFieldsEditable() {
		pScanTextArea.setEditable(true);
		remarksTextArea.setEditable(true);
		woamnBMITextField.setEditable(true);
		partnerBMITextField.setEditable(true);
	}

	public void makeFieldsNonEditable() {
		pScanTextArea.setEditable(false);
		remarksTextArea.setEditable(false);
		woamnBMITextField.setEditable(false);
		partnerBMITextField.setEditable(false);
	}

	// Reset All TextField and TextArea
	private void resetFields() {
		woamnBMITextField.setText(IConstants.emptyString);
		partnerBMITextField.setText(IConstants.emptyString);
		nurseComboBox.getSelectionModel().select(0);
		remarksTextArea.setText(IConstants.emptyString);
		pScanTextArea.setText(IConstants.emptyString);
		nurseRegistrant.setText(IConstants.emptyString);
		asstNurseRegistrant.setText(IConstants.emptyString);
	}
}
