package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.RegistrantDto;
import org.cf.card.dto.RemarksDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.EmbryoCode;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UICryoEmbryo;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIDayProgressValueService;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumEmbryo.EmbryoType;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.springframework.util.StringUtils;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EmbryologyTranseferController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitleText = MessageResource.getText("mainpage.title.embryology");
	private static final String iconURL = "/icons/andrology.png";
	private static final String descriptionTitleText = MessageResource
			.getText("mainpage.title.embryology.description.embryo");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String embryologyTransferError = MessageResource.getText("embryology.transfer.error");

	// binding fxml element
	@FXML
	private CommonDetailController commonDetailController;
	@FXML
	private InvestigationCommonController investigationCommonController;

	@FXML
	private TextField catheterTextField;

	@FXML
	private ComboBox<EmbryoType> comboBoxType;

	@FXML
	private ComboBox<String> comboBoxTime;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private Button editBtn;

	@FXML
	private TextField embRegistrant;

	@FXML
	private TextField drRegistrant;

	@FXML
	private TableView<UICryoEmbryo> embryoTransferTableView;

	@FXML
	private TableColumn<UICryoEmbryo, Integer> srNo;

	@FXML
	private TableColumn<UICryoEmbryo, String> quality;

	// creating instance variables(class level)
	/** The administrator warning label. */
	private Label administratorWarningLabel;
	private ObservableList<UICryoEmbryo> embryoETListTableView = null;
	private List<EmbryoCode> embryoCodeETList = null;
	private UIEmbryoService uiEmbryoService = new UIEmbryoService();
	private RemarksDto remarksDto;
	private RegistrantDto registrantDto;
	private PrintTemplate<?> printTemplate = new PrintTemplate<>();
	private EmbryoCode embryoCode = null;
	private int SCREEN_ID = Module.EMBRYO_TRANSFER.getKey();

	// creating object
	UIRemarksService uiRemarksService = new UIRemarksService();
	UIDayProgressValueService uiDayProgressValueService = new UIDayProgressValueService();
	UIRegistrantService uiRegistrantService = new UIRegistrantService();

	@FXML
	public void initialize() {
		makeFieldsNonEditable();
	}

	@SuppressWarnings("unchecked")
	public void buildData() {
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

		registrantDto = uiRegistrantService.getRegistrantByCodeAndScreenID(womanCode.getId(), SCREEN_ID);
		if (null != registrantDto) {
			embRegistrant.setText(registrantDto.getNameOfUser());
			drRegistrant.setText(registrantDto.getAssistantUser());
		}

		embryoCodeETList = new ArrayList<EmbryoCode>();
		commonDetailController.setMainApp(mainApp);
		// commonDetailController.setCouple(couple);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		investigationCommonController.setCouple(couple);
		investigationCommonController.build();
		ObservableList<EmbryoType> data = FXCollections.observableArrayList();
		data.addAll(EmbryoType.values());
		comboBoxType.setItems(data);
		// here
		ObservableList<String> dataTime = uiEmbryoService.appointmentTime();
		comboBoxTime.setItems(dataTime); // timeTextField is the ComboBox
		// to here
		// checking if any embryo with destiny ET exists
		embryoETListTableView = FXCollections.observableArrayList();

		Map<Long, Map<Integer, List<DayProgressValue>>> valuesMap = uiDayProgressValueService
				.findDayProgressValuesMapByCodeIdAndDestiny(womanCode.getId(), Option.ET.getKey());

		for (Map.Entry<Long, Map<Integer, List<DayProgressValue>>> element : valuesMap.entrySet()) {
			Map<Integer, List<DayProgressValue>> value = element.getValue();
			/* EmbryoCode embryoCode = null; */
			String embryoQuality = "";
			// iterating inner map key is day index. As as only destiny day
			// should be fetched . SO following loop should run only once
			for (Map.Entry<Integer, List<DayProgressValue>> innerMap : value.entrySet()) {
				List<DayProgressValue> aoDayProgressValue = innerMap.getValue();
				for (DayProgressValue dayProgressValue : aoDayProgressValue) {
					embryoCode = dayProgressValue.getEmbryoCode();
					int optionKey = dayProgressValue.getDayOptionId();
					Option option = Option.getOptionByKey(optionKey);
					if (!option.equals(Option.ET)) {
						embryoQuality += option.getName() + ".";
					}

				}
			}

			embryoCodeETList.add(embryoCode);

			UICryoEmbryo uiCryoEmbryo = new UICryoEmbryo(embryoCode.getIndex(), embryoQuality);
			embryoETListTableView.add(uiCryoEmbryo);
			catheterTextField.setText(embryoCode.getCatheter());
			// timeTextField.setText(embryoCode.getTime());
			comboBoxTime.setValue(embryoCode.getTime());
			if (embryoCode.getType() != 0)
				comboBoxType.setValue(EmbryoType.getEnumByKey(embryoCode.getType()));

		}

		embryoTransferTableView.setEditable(false);
		srNo.setCellValueFactory(new PropertyValueFactory<UICryoEmbryo, Integer>("srNo"));
		quality.setCellValueFactory(new PropertyValueFactory<UICryoEmbryo, String>("quality"));
		embryoTransferTableView.setItems(embryoETListTableView);

		if (embryoCodeETList.size() <= 0) {
			Notify notify = new Notify(AlertType.ERROR, embryologyTransferError);
			notify.showAndWait();
		}

		makeFieldsNonEditable();

	}

	@FXML
	public void editAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.EMBRYO_TRANSFER.getKey())) {
			makeFieldsEditable();
		} else
			FileUtils.privillegeEditError();
	}

	@FXML
	public void saveEmbryoTransferAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.EMBRYO_TRANSFER.getKey())) {
			// saving embryo transfer data
			EmbryoCodeValueDto embryoCodeValueDto = new EmbryoCodeValueDto();
			if (embryoCodeETList != null && embryoCodeETList.size() > 0) {
				embryoCodeValueDto.setCatheter(catheterTextField.getText());
				embryoCodeValueDto.setTime((String) comboBoxTime.getSelectionModel().getSelectedItem());

				embryoCodeValueDto.setType(comboBoxType.getSelectionModel().getSelectedItem().getKey());
				embryoCodeValueDto.setRemarks(remarksTextArea.getText());
				embryoCodeValueDto.setRemarkType(Module.EMBRYO_TRANSFER.getKey());
				embryoCodeValueDto.setEmbryoETList(embryoCodeETList);
				uiEmbryoService.addEmbryoTransfer(embryoCodeValueDto);

			}
			if (!StringUtils.isEmpty(remarksTextArea.getText())) {

				if (remarksDto == null) {
					remarksDto = new RemarksDto();
				}
				remarksDto.setRemarksText(remarksTextArea.getText());
				remarksDto.setCodeId(womanCode.getId());
				remarksDto.setRemarksType(SCREEN_ID);
				remarksDto = uiRemarksService.save(remarksDto);
			}
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
				drRegistrant);
	}

	@FXML
	private void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);

		// Getting all center nodes
		List<Node> tableNodes = createTable(embryoTransferTableView.getColumns(), embryoTransferTableView.getItems(),
				pageLayout);
		List<Node> remarksNodeList = printTemplate.createRemarks(remarksTextArea.getText(), pageLayout);
		for (Node remarkNode : remarksNodeList) {
			tableNodes.add(remarkNode);
		}
		int page = 1;

		if (tableNodes != null && tableNodes.size() > 0) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				for (Node node : tableNodes) {
					BorderPane printPage = createPrintPage(node, page, pageLayout);
					printerJob.printPage(printPage);
					page++;
				}
				printerJob.endJob();
			}
		} else {
			Notify alert = new Notify(AlertType.WARNING, noDataAvailableMessage);
			alert.showAndWait();
		}

	}

	/**
	 * Creating Table on multiple pages if table content is more
	 *
	 * @param pageLayout
	 * @return List<Node>
	 */
	private List<Node> createTable(ObservableList<TableColumn<UICryoEmbryo, ?>> columns, Collection<UICryoEmbryo> items,
			PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();

		// VBox for Catcher,Type and Time
		VBox vBox = null;
		// list of Array for comboxBoxes text(label and Values)
		List<String> values = null;
		if (null != embryoCode) {
			values = new ArrayList<>();
			values.add("  CATHETER :");
			values.add(embryoCode.getCatheter());
			values.add("  TYPE     :");
			values.add(String.valueOf(EmbryoType.getEnumByKey(embryoCode.getType())));
			values.add("  TIME     :");
			values.add(embryoCode.getTime());
			vBox = printTemplate.createComboBoxNodes(pageLayout, values);
			vBox.setMinHeight(50);
			vBox.setStyle("-fx-border-width: 0 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);

		}
		// nodes.add(vBox);
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = pageLayout.getPrintableWidth() / columnCount;
		for (UICryoEmbryo record : items) {
			HBox row = createTableRow(record, labelWidth, pageLayout);
			// elementHeight would be the height of each cell
			final double elementHeight = 10;
			// adding table on multiple pages
			// 210 - height of patient section + height of embryo Info HBox
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 300) {
				tableVBox = new VBox();
				// tableVBox.getChildren().add(printTemplate.createStaticLabel(IConstants.emptyString));
				tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableVBox.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
				tableVBox.getChildren().add(vBox);
				tableVBox.getChildren().add(printTemplate.createStaticLabel(IConstants.emptyString));
				HBox tableHeader = createTableHeader(columns, labelWidth, pageLayout);
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
	 * Setting Column Names(Header) in HBox
	 */
	private HBox createTableHeader(ObservableList<TableColumn<UICryoEmbryo, ?>> columns, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		int count = 1;
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setStyle("-fx-border-width: 1 0 1 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		for (TableColumn<UICryoEmbryo, ?> cols : columns) {

			if (count != columns.size()) {
				headerHBox.getChildren().add(printTemplate.createTableHeaderLabel(" " + cols.getText(), labelWidth));
				headerHBox.getChildren().add(printTemplate.createSeparator());
				count++;
			} else {
				headerHBox.getChildren().add(printTemplate.createTableHeaderLabel(cols.getText(), labelWidth));
			}

		}
		return headerHBox;
	}

	/**
	 * Setting Cell Data (Row) in HBox
	 */
	private HBox createTableRow(UICryoEmbryo record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 0 0 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getSrNo().toString(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(record.getQuality(), labelWidth, false));
		return rowHBox;
	}

	// Final creating full page for printing
	private BorderPane createPrintPage(Node node, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());
		// Setting the Title header at top of Border Pane

		HBox headerHbox = printTemplate.createHeader(mainPageTitleText, iconURL, descriptionTitleText, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		Label spaceBetweenElements1 = printTemplate.spaceBetweenElements(20);
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		Label spaceBetweenElements2 = printTemplate.spaceBetweenElements(20);
		VBox registrantDetailVBox = printTemplate.createRegistrantCommonInfo(pageLayout, registrantDto,
				MessageResource.getText("common.emb.registrant"),
				MessageResource.getText("common.dr.registrant"));
		contentVBox.getChildren().addAll(patientGrid, spaceBetweenElements1, registrantDetailVBox, spaceBetweenElements2, node);
		root.setCenter(contentVBox);
		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	public void resetFields() {
		catheterTextField.setText(IConstants.emptyString);
		comboBoxTime.getSelectionModel().select(0);
		comboBoxType.getSelectionModel().select(0);
		remarksTextArea.setText(IConstants.emptyString);
		embRegistrant.setText(IConstants.emptyString);
		drRegistrant.setText(IConstants.emptyString);
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	public void makeFieldsEditable() {
		remarksTextArea.setEditable(true);
		catheterTextField.setEditable(true);
	}

	public void makeFieldsNonEditable() {
		remarksTextArea.setEditable(false);
		catheterTextField.setEditable(false);
	}

}
