package org.cf.card.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.cf.card.dto.EmbryoDto;
import org.cf.card.dto.EmbryologyRegistrantDto;
import org.cf.card.dto.RemarksDto;
import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.Embryo;
import org.cf.card.model.EmbryoCode;
import org.cf.card.model.Treatment;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.components.ComboBoxCell;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIDay;
import org.cf.card.ui.model.UIEmbryo;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UICodesService;
import org.cf.card.ui.service.UIDayProgressValueService;
import org.cf.card.ui.service.UIEmbryoRegistrantService;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.service.UITreatmentService;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FXMLUtils;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumCycleType.CycleType;
import org.cf.card.util.EnumDayTable;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumEmbryo;
import org.cf.card.util.EnumEmbryo.Injection;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.util.StringUtils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author insonix
 *
 */
public class EmbryologyController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.embryology");
	private static final String titleDescription = MessageResource
			.getText("mainpage.title.embryology.description.overview");
	private static final String iconURL = "/icons/embryology.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String OOCYTESMessage = MessageResource.getText("embryology.controller.info.message");
	private static final String OOCYTESMessageForCycleType = MessageResource
			.getText("embryology.controller.info.message.cycle.type");
	private static final String selectOOCYTES = MessageResource.getText("embryology.controller.validation.error");
	private static final String noChangesAfterDay9 = MessageResource.getText("embryology.controller.info.nochanges");
	private static final String oopsError = MessageResource.getText("embryology.controller.info.someerror");

	// creating object
	UIEmbryoService embryoService = new UIEmbryoService();
	UITreatmentService treatmentService = new UITreatmentService();
	UICodesService codesService = new UICodesService();
	UIRemarksService uiRemarksService = new UIRemarksService();
	UIDayProgressValueService uiDayProgressValueService = new UIDayProgressValueService();
	UIEmbryoRegistrantService uiEmbryoRegistrantService = new UIEmbryoRegistrantService();
	private PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element
	@FXML
	private AnchorPane embryologyPane;

	@FXML
	private TableView<UIEmbryo> dayTable;

	@FXML
	private TableColumn<UIEmbryo, String> index;

	@FXML
	private TableColumn<UIEmbryo, EnumEmbryo.Injection> injection;

	@FXML
	private TextField oocyte;

	@FXML
	private TextField incubator;

	@FXML
	private TextField startDate;

	@FXML
	private CheckBox researchSelectionCheckbox;

	@FXML
	private Button saveButton;

	@FXML
	private Button printFormButton;

	@FXML
	private Text validationError;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private TextField embRegistrantDay0;
	@FXML
	private TextField drRegistrantDay0;
	@FXML
	private TextField embRegistrantDay1;
	@FXML
	private TextField drRegistrantDay1;
	@FXML
	private TextField embRegistrantDay2;
	@FXML
	private TextField drRegistrantDay2;
	@FXML
	private TextField embRegistrantDay3;
	@FXML
	private TextField drRegistrantDay3;
	@FXML
	private TextField embRegistrantDay4;
	@FXML
	private TextField drRegistrantDay4;
	@FXML
	private TextField embRegistrantDay5;
	@FXML
	private TextField drRegistrantDay5;
	@FXML
	private TextField embRegistrantDay6;
	@FXML
	private TextField drRegistrantDay6;
	@FXML
	private TextField embRegistrantDay7;
	@FXML
	private TextField drRegistrantDay7;
	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private InvestigationCommonController investigationCommonController;

	@FXML
	private AnchorPane commonDetail;

	// creating instance variables(class level)
	/** The administrator warning label. */
	private Label administratorWarningLabel;
	private Treatment treatment;
	private boolean cycleCompleted = false;

	private int oocyteTypeCount = 0;
	private int eggs;
	private int cycleType;
	private RemarksDto remarksDto;
	private EmbryologyRegistrantDto registrantDto;
	// get reaserchCheckBox selelction(true or false)
	boolean isResearch;
	private int SCREEN_ID = Module.EMBRYOLOGY_OVERVIEW.getKey();

	public int getOocyteTypeCount() {
		return oocyteTypeCount;
	}

	public void setOocyteTypeCount(int oocyteTypeCount) {
		this.oocyteTypeCount = oocyteTypeCount;
	}

	@FXML
	private void initialize() {
		// treatmentId = 2l;// get dynamic from global variable
		commonDetailController.setBackgroundColor(false);
		try {
			dayTable.getSelectionModel().setCellSelectionEnabled(true);
			dayTable.setEditable(false);

			oocyte.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (Pattern.matches(".*\\D.*", newValue)) {
						oocyte.setText(oldValue);
					}

				}
			});

			// binding fxml column values
			index.setCellValueFactory(cellData -> cellData.getValue().indexProperty());

			// setting combobox for injection
			injection.setCellValueFactory(new PropertyValueFactory<UIEmbryo, EnumEmbryo.Injection>("injection"));
			injection.setCellFactory(
					new Callback<TableColumn<UIEmbryo, EnumEmbryo.Injection>, TableCell<UIEmbryo, EnumEmbryo.Injection>>() {
						@Override
						public TableCell<UIEmbryo, EnumEmbryo.Injection> call(
								TableColumn<UIEmbryo, EnumEmbryo.Injection> param) {
							return new ComboBoxCell<UIEmbryo, EnumEmbryo.Injection>(
									FXCollections.observableArrayList(Injection.values()));
						};
					});

			// adding days columns dynamically to existing table
			List<TableColumn<UIEmbryo, UIDay>> days = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				Callback<TableColumn<UIEmbryo, UIDay>, TableCell<UIEmbryo, UIDay>> cellFactoryCustom = new Callback<TableColumn<UIEmbryo, UIDay>, TableCell<UIEmbryo, UIDay>>() {

					@Override
					public TableCell<UIEmbryo, UIDay> call(TableColumn<UIEmbryo, UIDay> param) {
						DayCell cell = new DayCell();
						cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new DayEvent());
						return cell;
					}
				};
				TableColumn<UIEmbryo, UIDay> day = new TableColumn<>("Day " + i);
				final int s = new Integer(i);
				day.setCellValueFactory(cellData -> cellData.getValue().getUiDays().get(s));
				day.setCellFactory(cellFactoryCustom);
				day.setId("day-" + i);
				days.add(day);
			}
			// setting days column to table
			dayTable.getColumns().addAll(days);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		makeFieldsEditable();
	}

	public void makeFieldsNonEditable() {
		oocyte.setEditable(false);
		incubator.setEditable(false);
		remarksTextArea.setEditable(false);
	}

	public void makeFieldsEditable() {
		oocyte.setEditable(true);
		incubator.setEditable(true);
		remarksTextArea.setEditable(true);
	}

	public void buildTableView() {
		ObservableList<TableColumn<UIEmbryo, ?>> list = dayTable.getColumns();
		String dateNext = IConstants.emptyString;
		Date date = null;
		if (null != treatment) {
			date = treatment.getStartDate();

		}
		int counter = 1;
		int dayCount = 0;
		/** updating the table view column name */
		for (int i = 2; i < list.size(); i++) {
			TableColumn<UIEmbryo, ?> tableColumn = list.get(i);
			String name = tableColumn.getText();
			Date nextDate = Util.nextDate(date, counter);
			dateNext = Util.formatDate(IConstants.DATE_FORMAT, nextDate);
			if (name.equalsIgnoreCase("Day " + dayCount))
				tableColumn.setText(name + "\n" + dateNext);
			else {
				String names = name.split("\n")[0];
				tableColumn.setText(names + "\n" + dateNext);
			}

			counter++;
			dayCount++;
		}
	}

	public void buildData() {

		resetFields();
		remarksDto = uiRemarksService.getRemarksByCodeId(womanCode.getId(), SCREEN_ID);
		if (null != remarksDto)
			remarksTextArea.setText(remarksDto.getRemarksText());

		registrantDto = uiEmbryoRegistrantService.getEmbryoRegistrantByCodeAndScreenID(womanCode.getId(), SCREEN_ID);
		if (null != registrantDto) {
			embRegistrantDay0.setText(registrantDto.getEmbRegistrantDay0());
			drRegistrantDay0.setText(registrantDto.getDrRegistrantDay0());
			embRegistrantDay1.setText(registrantDto.getEmbRegistrantDay1());
			drRegistrantDay1.setText(registrantDto.getDrRegistrantDay1());
			embRegistrantDay2.setText(registrantDto.getEmbRegistrantDay2());
			drRegistrantDay2.setText(registrantDto.getDrRegistrantDay2());
			embRegistrantDay3.setText(registrantDto.getEmbRegistrantDay3());
			drRegistrantDay3.setText(registrantDto.getDrRegistrantDay3());
			embRegistrantDay4.setText(registrantDto.getEmbRegistrantDay4());
			drRegistrantDay4.setText(registrantDto.getDrRegistrantDay4());
			embRegistrantDay5.setText(registrantDto.getEmbRegistrantDay5());
			drRegistrantDay5.setText(registrantDto.getDrRegistrantDay5());
			embRegistrantDay6.setText(registrantDto.getEmbRegistrantDay6());
			drRegistrantDay6.setText(registrantDto.getDrRegistrantDay6());
			embRegistrantDay7.setText(registrantDto.getEmbRegistrantDay7());
			drRegistrantDay7.setText(registrantDto.getDrRegistrantDay7());
		}
		if (null != couple) {
			womanCode = codesService.getCodeById(womanCode.getId());
			treatment = womanCode.getTreatment();
			cycleType = treatment.getCycleType();
			// commonDetailController.setCouple(couple);
			commonDetailController.setWomanPersonalInfo(womanCode);
			commonDetailController.setPartnerPersonalInfo(manCode);
			investigationCommonController.setCouple(couple);
			investigationCommonController.setManCode(manCode);
			investigationCommonController.setWomanCode(womanCode);
			investigationCommonController.build();

			// set oocyte value from db ooctye text field on screen
			int oocyteCount = oocyteTypeCount > 0 ? oocyteTypeCount : treatment.getEggs();
			oocyte.setText(String.valueOf(oocyteCount));
			eggs = new Integer(treatment.getEggs());
			incubator.setText(treatment.getIncubator());
			isResearch = treatment.getResearch() == 1 ? true : false;
			researchSelectionCheckbox.setSelected(isResearch);

			ObservableList<UIEmbryo> uiEmbryos = FXCollections.observableArrayList();
			// Option destiny= null;
			List<EmbryoCode> aoEmbryos = embryoService.getEmbryosByCode(womanCode.getId());
			String treatmentDate = IConstants.emptyString;
			if (null != treatment) {
				Date date = treatment.getStartDate();
				Date nextDate = Util.nextDate(date, 1);
				treatmentDate = Util.formatDate(IConstants.DATE_FORMAT, nextDate);

				// make oocyte readonly if it is not day 0
				String dateNext = Util.formatDate(IConstants.DATE_FORMAT, nextDate);
				String curentDate = Util.formatDate(IConstants.DATE_FORMAT, new Date());

				Boolean flag = Util.CompareDates(curentDate, dateNext);
				oocyte.setEditable(flag);
				if (cycleType == CycleType.EMBRYO_THAW.getKey() || cycleType == CycleType.EGG_THAW.getKey())
					oocyte.setEditable(false);

				// oocyte.requestFocus();
				oocyte.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (!oocyte.isEditable() && (cycleType != CycleType.EMBRYO_THAW.getKey()
								|| cycleType != CycleType.EGG_THAW.getKey()) && event.getClickCount() > 1) {
							Notify notify = new Notify(AlertType.INFORMATION, OOCYTESMessage);
							notify.showAndWait();
						} else if (cycleType == CycleType.EMBRYO_THAW.getKey()
								|| cycleType == CycleType.EGG_THAW.getKey()) {
							Notify notify = new Notify(AlertType.INFORMATION, OOCYTESMessageForCycleType);
							notify.showAndWait();
						}
					}

				});
				startDate.setText(treatmentDate);
				if (treatment.getCycleType() != CycleType.EMBRYO_THAW.getKey()) {

					// if treatment started has been more that 9 days then it is
					// assumed to be completed
					Date day10 = Util.nextDate(date, 11);
					if (Util.compareDates(new Date(), day10) == 1) {
						cycleCompleted = true;
					} else {
						cycleCompleted = false;
					}

					/* getting day progress values by woman code id */

					// key = embryoCodeId, innrMap-> key= dayIndex
					Map<Long, Map<Integer, List<DayProgressValue>>> valuesMap = uiDayProgressValueService
							.findDayProgressValuesMapByCodeIdAndModuleId(womanCode.getId(),
									Module.EMBRYOLOGY_OVERVIEW.getKey(), cycleType);

					for (EmbryoCode embryoCode : aoEmbryos) {
						Option destiny = null;
						Date destinyDate = null;
						List<UIDay> uiDays = new ArrayList<>();
						Map<Integer, List<DayProgressValue>> innerMap = valuesMap.get(embryoCode.getId());
						for (int k = 0; k < 10; k++) {

							Date dayDate = Util.addDate(treatment.getStartDate(), k + 1);
							String value = IConstants.emptyString;
							if (innerMap != null && innerMap.containsKey(k)) {
								List<DayProgressValue> aoDayProgressValues = innerMap.get(k);
								for (DayProgressValue dayProgressValue : aoDayProgressValues) {
									int optionKey = dayProgressValue.getDayOptionId();
									Option option = Option.getOptionByKey(optionKey);
									value += option.getName() + ".";
									if (null == destiny && EnumDayTable.getDestiny().contains(option)) {
										destiny = Option.getOptionByKey(optionKey);
										destinyDate = Util.addDate(treatment.getStartDate(), k + 1);
									}
								}
							}

							// setting value for day cell of the table view
							UIDay uiDay = new UIDay(k, embryoCode.getIndex(),
									value.length() > 0 ? value.substring(0, value.length() - 1) : value,
									embryoCode.getId(), dayDate, EnumPermission.Module.EMBRYOLOGY_OVERVIEW.getKey());
							uiDays.add(uiDay);

						}

						// settng values of row ofo table view
						UIEmbryo uiEmbryo = new UIEmbryo(String.valueOf(embryoCode.getIndex()),
								Injection.getEnumByKey(embryoCode.getEmbryo().getInjection()), uiDays);
						Embryo embryo = embryoCode.getEmbryo();
						uiEmbryo.setEmbryoId(embryo.getId());
						uiEmbryo.setDestiny(destiny);
						uiEmbryo.setDestinyDate(destinyDate);
						uiEmbryos.add(uiEmbryo);

					}

				}

				dayTable.setItems(uiEmbryos);
			}
		}
	}

	@FXML
	private void editAction() {
		if (treatment != null) {
			Date date = treatment.getStartDate();
			Date nextDate = Util.nextDate(date, 1);
			String dateNext = Util.formatDate(IConstants.DATE_FORMAT, nextDate);
			String curentDate = Util.formatDate(IConstants.DATE_FORMAT, new Date());
			Boolean flag = Util.CompareDates(curentDate, dateNext);
			oocyte.setEditable(flag);
			oocyte.requestFocus();
		}

	}

	/**
	 * Save action for embryology overview. ooctyes, incubator, research
	 * checkbox, remarks and procedure column for oocytes in each table gets
	 * saved on click of save button
	 */
	@FXML
	private void saveAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.EMBRYOLOGY_OVERVIEW.getKey())) {
			if (!cycleCompleted) {
				boolean valid = true;
				if (oocyte.textProperty().isEmpty().getValue()) {
					oocyte.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
					validationError.setText(selectOOCYTES);
					valid = false;
				}
				/*
				 * if (incubator.textProperty().isEmpty().getValue()) {
				 * incubator.setStyle(Constants.VALIDATE_FIELDS_FAIL_STYLE);
				 * validationError.setText("Please mention incubator"); }
				 */

				if (valid) {
					/*
					 * womanCode.getTreatment().setEggs(Integer.parseInt(oocyte.
					 * getText()));
					 * womanCode.getTreatment().setIncubator(incubator.getText()
					 * ); int isResearch =
					 * researchSelectionCheckbox.isSelected() ? 1 : 0;
					 * womanCode.getTreatment().setResearch(isResearch); //Codes
					 * womanCodeObj = codesService.addCode(womanCode);
					 * 
					 * this.treatment = womanCode != null ?
					 * womanCode.getTreatment() : null;
					 */
					int isResearch = researchSelectionCheckbox.isSelected() ? 1 : 0;
					TreatmentDto treatmentDto = new TreatmentDto();
					treatmentDto.setTreatmentID(this.treatment.getId());
					;
					treatmentDto.setOocytes(Integer.parseInt(oocyte.getText()));
					treatmentDto.setIncubator(incubator.getText());
					treatmentDto.setResearch(isResearch);

					treatmentService.updateTreatment(treatmentDto);

					int oocytes = Integer.parseInt(oocyte.getText());
					if (oocytes != eggs) {
						embryoService.addEmbryos(Integer.parseInt(oocyte.getText()), womanCode.getId());
					} else {
						List<UIEmbryo> aoUiEmbryos = dayTable.getItems();
						List<EmbryoDto> aoEmbryoDto = new ArrayList<>();
						// for updating values of ICSI and Injection.
						for (UIEmbryo uiEmbryo : aoUiEmbryos) {
							EmbryoDto embryoDto = new EmbryoDto(uiEmbryo.getEmbryoId(), womanCode.getId(),
									uiEmbryo.getInjection().getKey(), Integer.parseInt(uiEmbryo.getIndex()));
							aoEmbryoDto.add(embryoDto);
						}
						embryoService.updateEmbryos(aoEmbryoDto);
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
				}
			} else {
				Notify notify = new Notify(AlertType.INFORMATION, noChangesAfterDay9);
				notify.showAndWait();
			}
		} else
			FileUtils.privillegeEditError();
	}

	/**
	 * Save registrant action.
	 */
	@FXML
	private void saveRegistrantAction() {
		if (null == registrantDto) {
			registrantDto = new EmbryologyRegistrantDto();
		}
		registrantDto.setCodeId(womanCode.getId());
		registrantDto.setScreenId(SCREEN_ID);
		registrantDto.setEmbRegistrantDay0(embRegistrantDay0.getText());
		registrantDto.setDrRegistrantDay0(drRegistrantDay0.getText());
		registrantDto.setEmbRegistrantDay1(embRegistrantDay1.getText());
		registrantDto.setDrRegistrantDay1(drRegistrantDay1.getText());
		registrantDto.setEmbRegistrantDay2(embRegistrantDay2.getText());
		registrantDto.setDrRegistrantDay2(drRegistrantDay2.getText());
		registrantDto.setEmbRegistrantDay3(embRegistrantDay3.getText());
		registrantDto.setDrRegistrantDay3(drRegistrantDay3.getText());
		registrantDto.setEmbRegistrantDay4(embRegistrantDay4.getText());
		registrantDto.setDrRegistrantDay4(drRegistrantDay4.getText());
		registrantDto.setEmbRegistrantDay5(embRegistrantDay5.getText());
		registrantDto.setDrRegistrantDay5(drRegistrantDay5.getText());
		registrantDto.setEmbRegistrantDay6(embRegistrantDay6.getText());
		registrantDto.setDrRegistrantDay6(drRegistrantDay6.getText());
		registrantDto.setEmbRegistrantDay7(embRegistrantDay7.getText());
		registrantDto.setDrRegistrantDay7(drRegistrantDay7.getText());
		registrantDto = uiEmbryoRegistrantService.save(registrantDto);
	}

	/**
	 * prints the page content
	 */
	@FXML
	private void printAction() {
		Printer printer = Printer.getDefaultPrinter();
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		// setting layout of page
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, MarginType.DEFAULT);
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);
		List<Node> nodes = createEmbryologyPrintTable(dayTable.getColumns(), dayTable.getItems(), pageLayout);
		if (remarksTextArea.getText() != null && !remarksTextArea.getText().isEmpty()) {
			List<Node> remarksNodeList = printTemplate.createRemarks(remarksTextArea.getText(), pageLayout);
			for (Node remarkNode : remarksNodeList) {
				nodes.add(remarkNode);
			}
		}

		int page = 1;
		if (nodes != null && nodes.size() > 0) {
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
	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, titleDescription, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		HBox embryoHBox = createEmbryoInfo(pageLayout);
		GridPane registrantGridPane = createEmbryoRegistrant(pageLayout);
		Label spaceBetweenElement1 = printTemplate.spaceBetweenElements(20);
		Label spaceBetweenElement2 = printTemplate.spaceBetweenElements(20);
		contentVBox.getChildren().addAll(patientGrid, embryoHBox, spaceBetweenElement1, registrantGridPane,
				spaceBetweenElement2, table);
		root.setCenter(contentVBox);

		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	/**
	 * Setting Embryology Info in HBox - (Middle Section)
	 *
	 * @param pageLayout
	 * @return HBox
	 */
	private HBox createEmbryoInfo(PageLayout pageLayout) {
		HBox hBox = new HBox();
		hBox.setPrefHeight(50);
		hBox.setPrefWidth(pageLayout.getPrintableWidth());
		hBox.setSpacing(30);
		hBox.setAlignment(Pos.CENTER);
		hBox.setStyle(Constants.PRINT_GREY_BORDER_STYLE);
		String ooctypeVal = null, incubatorVal = null, treatmentDate = null;
		if (null != treatment) {
			ooctypeVal = String.valueOf(treatment.getEggs());
			incubatorVal = treatment.getIncubator() != null ? treatment.getIncubator() : "0";
			Date date = treatment.getStartDate();
			Date nextDate = Util.nextDate(date, 1);
			treatmentDate = Util.formatDate(IConstants.DATE_FORMAT, nextDate);
		}
		hBox.getChildren().add(printTemplate.createStaticLabel("OOCTYPE: "));
		hBox.getChildren().add(printTemplate.createDynamicLabel((ooctypeVal + "")));
		hBox.getChildren().add(printTemplate.createStaticLabel("INCUBATOR: "));
		hBox.getChildren().add(printTemplate.createDynamicLabel(incubatorVal));
		hBox.getChildren().add(printTemplate.createStaticLabel("DATE: "));
		hBox.getChildren().add(printTemplate.createDynamicLabel(treatmentDate));
		hBox.getChildren().add(printTemplate.createStaticLabel("RESEARCH: "));
		CheckBox checkBox = new CheckBox();
		checkBox.setSelected(isResearch);
		hBox.getChildren().add(checkBox);
		return hBox;
	}

	/**
	 * Creating Table on multiple pages if table content is more
	 *
	 * @param pageLayout
	 * @return List<Node>
	 */
	private List<Node> createEmbryologyPrintTable(ObservableList<TableColumn<UIEmbryo, ?>> columns,
			Collection<UIEmbryo> items, PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = pageLayout.getPrintableWidth() / columnCount;
		for (UIEmbryo record : items) {
			HBox row = createTableRow(record, labelWidth, pageLayout);
			// elementHeight would be the height of each cell
			final double elementHeight = 15;
			// adding table on multiple pages
			// 210 - height of patient section + height of embryo Info HBox
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 320) {
				tableVBox = new VBox();
				tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
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
	private HBox createTableHeader(ObservableList<TableColumn<UIEmbryo, ?>> columns, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		for (TableColumn<UIEmbryo, ?> cols : columns) {
			// condition to avoid the last separator in table
			if (cols.getText().indexOf("Day 9") >= 0) {
				headerHBox.getChildren().add(printTemplate.createTableHeaderLabel(cols.getText(), labelWidth));
			} else {
				headerHBox.getChildren().add(printTemplate.createTableHeaderLabel(cols.getText(), labelWidth));
				headerHBox.getChildren().add(printTemplate.createSeparator());
			}
		}
		return headerHBox;
	}

	/**
	 * Setting Cell Data (Row) in HBox
	 */
	private HBox createTableRow(UIEmbryo record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(record.indexProperty().getValue() + "", labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(record.getInjection()), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		List<ObjectProperty<UIDay>> uiDays = record.getUiDays();
		if (uiDays != null) {
			for (ObjectProperty<UIDay> uiDay : uiDays) {
				// condition to avoid the last separator in table
				if (uiDay.getValue().getDayIndex() == 9) {
					rowHBox.getChildren().add(printTemplate.createTableRowLabel(
							uiDay.getValue().getValue() != null ? uiDay.getValue().getValue() : "", labelWidth, true));
				} else {
					rowHBox.getChildren().add(printTemplate.createTableRowLabel(
							uiDay.getValue().getValue() != null ? uiDay.getValue().getValue() : "", labelWidth, true));
					rowHBox.getChildren().add(printTemplate.createSeparator());
				}
			}
		}
		return rowHBox;
	}

	private GridPane createEmbryoRegistrant(PageLayout pageLayout) {
		GridPane parenPane = null;
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setResources(MessageResource.getResourceBundle());
			loader.setLocation(MainApp.class.getResource("/view/popups/EmbryologyPrintingRegistrant.fxml"));
			parenPane = (GridPane) loader.load();
			parenPane.setPrefWidth(pageLayout.getPrintableWidth());
			if (null != registrantDto) {

				// EMB registrant
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay0Label",
						registrantDto.getEmbRegistrantDay0());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay1Label",
						registrantDto.getEmbRegistrantDay1());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay2Label",
						registrantDto.getEmbRegistrantDay2());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay3Label",
						registrantDto.getEmbRegistrantDay3());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay4Label",
						registrantDto.getEmbRegistrantDay4());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay5Label",
						registrantDto.getEmbRegistrantDay5());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay6Label",
						registrantDto.getEmbRegistrantDay6());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#embRegistrantDay7Label",
						registrantDto.getEmbRegistrantDay7());

				// DR registrant
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay0Label",
						registrantDto.getDrRegistrantDay0());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay1Label",
						registrantDto.getDrRegistrantDay1());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay2Label",
						registrantDto.getDrRegistrantDay2());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay3Label",
						registrantDto.getDrRegistrantDay3());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay4Label",
						registrantDto.getDrRegistrantDay4());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay5Label",
						registrantDto.getDrRegistrantDay5());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay6Label",
						registrantDto.getDrRegistrantDay6());
				FXMLUtils.getElementByFxIDAndSetText(parenPane, "#drRegistrantDay7Label",
						registrantDto.getDrRegistrantDay7());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parenPane;
	}

	@FXML
	private void semenPreprationAction() {
		LoadPanels.loadModule(mainApp, login, Module.SEMEN_PREPRATION.getKey(), null);
	}

	@FXML
	private void cryopreservationEmbryologyAction() {

		LoadPanels.loadModule(mainApp, login, Module.CRYOPRESERVATION_E.getKey(), null);
	}

	@FXML
	private void embryoTransferAction() {
		LoadPanels.loadModule(mainApp, login, Module.EMBRYO_TRANSFER.getKey(), null);
	}

	/**
	 * The Class DayEvent. Even handler for db click in
	 */
	class DayEvent implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

			if (EnumPermission.canWrite(login.getRoleId(), Module.EMBRYOLOGY_OVERVIEW.getKey())) {
				if (event.getClickCount() > 1 && !cycleCompleted) {
					// TODO: Can write check here as well. Pop up can only be
					// opened
					// if user has write privillege

					DayCell cell = null;
					if (event.getTarget() instanceof DayCell) {
						cell = (DayCell) event.getTarget();
					} else if (event.getTarget() instanceof Text) {
						Text text = (Text) event.getTarget();
						cell = (DayCell) text.getParent();
					}

					if (cell != null) {
						UIEmbryo rowItem = (UIEmbryo) cell.getTableRow().getItem();

						int dateFlag = -1;
						boolean destinyFlag = false;
						UIDay item = cell.getItem();
						String cellDate = IConstants.emptyString;
						if (womanCode != null) {
							Treatment treatment = womanCode.getTreatment();
							Date date = treatment.getStartDate();
							Date selectedDate = Util.nextDate(date, item.getDayIndex() + 1);
							cellDate = Util.formatDate(IConstants.DATE_FORMAT, selectedDate);
							// String curentDate =
							// Util.formatDate(IConstants.DATE_FORMAT, new
							// Date());
							dateFlag = Util.compareDates(new Date(), selectedDate);

							if (rowItem.getDestiny() != null) {
								if (Util.compareDates(selectedDate, rowItem.getDestinyDate()) != 0)
									destinyFlag = true;
							}
						}
						if (dateFlag == 1 && !destinyFlag) {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(MainApp.class.getResource("/view/DayDialog.fxml"));
							try {
								// AnchorPane dayDialog = (AnchorPane)
								// loader.load();
								GridPane dayDialog = (GridPane) loader.load();
								/*
								 * if (item.getDayIndex() > 3) { double width =
								 * 111 * 7; dayDialog.setPrefWidth(width);
								 * dayDialog.setMaxWidth(width);
								 * dayDialog.setMinWidth(width); }
								 */

								if (item.getDayIndex() > 0 && item.getDayIndex() <= 3) {
									double width = 120 * 4;
									// anchorPane.setPrefWidth(width);
									// anchorPane.setMinWidth(width);
									// anchorPane.setMaxWidth(width);
									dayDialog.setPrefWidth(width);
									dayDialog.setMaxWidth(width);
									dayDialog.setMinWidth(width);
								} else if (item.getDayIndex() > 3) {
									double width = 111 * 7;
									dayDialog.setPrefWidth(width);
									dayDialog.setMaxWidth(width);
									dayDialog.setMinWidth(width);
								}

								Group group = new Group(dayDialog);
								// group.scaleXProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
								// /1100.0);
								// group.scaleYProperty().set(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
								// /1100.0);

								// group.setLayoutX(dayDialog.getPrefWidth() *
								// ((1 -
								// group.getScaleX()) / 2 + 0.02));
								// group.setLayoutY(dayDialog.getPrefHeight() *
								// ((1
								// - group.getScaleY()) / 2 + 0.01));
								Scene scene = new Scene(group);
								mainApp.getDayDialog().setScene(scene);
								scene.getStylesheets()
										.add(MainApp.class.getResource("/CSS/dayDialog.css").toExternalForm());
								// Rectangle2D primaryScreenBounds =
								// Screen.getPrimary().getVisualBounds();
								// mainApp.getDayDialog().setX(0);
								// mainApp.getDayDialog().setY(0);
								mainApp.getDayDialog().centerOnScreen();
								mainApp.getDayDialog().show();
								DayDialogController controller = loader.getController();
								controller.setMainApp(mainApp);
								controller.setLogin(login);
								controller.setUiDay(item);
								controller.setWomanCode(womanCode);
								controller.buildData();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							Object[] params = new Object[2];
							params[0] = item.getDayIndex();
							params[1] = cellDate;
							String message = MessageResource.getText("embryology.controller.notify.day.alert", params);
							if (rowItem.getDestiny() != null) {
								params = new Object[1];
								params[0] = rowItem.getDestiny().getName();
								message = MessageResource.getText("embryology.controller.message.destiny.done", params);
							}
							Notify notify = new Notify(AlertType.INFORMATION, message);
							notify.showAndWait();
						}
					} else {
						Notify notify = new Notify(AlertType.INFORMATION);
						notify.setContentText(oopsError);
						notify.showAndWait();
					}
				}
			} else {
				FileUtils.privillegeEditError();
			}

		}
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

	// Reset All TextField and TextArea
	private void resetFields() {
		remarksTextArea.setText(IConstants.emptyString);
		incubator.setText(IConstants.emptyString);
		startDate.setText(IConstants.emptyString);
		researchSelectionCheckbox.setText(IConstants.emptyString);
		validationError.setText(IConstants.emptyString);
		embRegistrantDay0.setText(IConstants.emptyString);
		drRegistrantDay0.setText(IConstants.emptyString);
		embRegistrantDay1.setText(IConstants.emptyString);
		drRegistrantDay1.setText(IConstants.emptyString);
		embRegistrantDay2.setText(IConstants.emptyString);
		drRegistrantDay2.setText(IConstants.emptyString);
		embRegistrantDay3.setText(IConstants.emptyString);
		drRegistrantDay3.setText(IConstants.emptyString);
		embRegistrantDay4.setText(IConstants.emptyString);
		drRegistrantDay4.setText(IConstants.emptyString);
		embRegistrantDay5.setText(IConstants.emptyString);
		drRegistrantDay5.setText(IConstants.emptyString);
		embRegistrantDay6.setText(IConstants.emptyString);
		drRegistrantDay6.setText(IConstants.emptyString);
		embRegistrantDay7.setText(IConstants.emptyString);
		drRegistrantDay7.setText(IConstants.emptyString);
	}

}

class DayCell extends TableCell<UIEmbryo, UIDay> {

	@Override
	protected void updateItem(UIDay item, boolean empty) {

		super.updateItem(item, empty);
		setText(empty ? null : getString());
		setGraphic(null);
	}

	private String getString() {
		return getItem() == null ? "" : getItem().getValue();
	}
}