package org.cf.card.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cf.card.dto.DayProgressValueDto;
import org.cf.card.dto.RemarksDto;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIDay;
import org.cf.card.ui.model.UIEmbryoThaw;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIDayProgressValueService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumCycleType.CycleType;
import org.cf.card.util.EnumDayTable.Option;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.springframework.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class EmbryologyThawingController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.embryology");
	private static final String titleDescription = MessageResource.getText("mainpage.title.embryology.description.egg");
	private static final String iconURL = "/icons/embryology.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String oppsError = MessageResource.getText("embryology.thawing.info.someerror");

	// creating object
	UIDayProgressValueService uiDayProgressValueService = new UIDayProgressValueService();
	UIRemarksService uiRemarksService = new UIRemarksService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element
	@FXML
	private TextArea remarksTextArea;

	@FXML
	private Button embryoTransferButton;

	@FXML
	private CommonDetailController commonDetailController;
	@FXML
	private InvestigationCommonController investigationCommonController;

	@FXML
	private TableView<UIEmbryoThaw> embryoThawTableView;

	@FXML
	private TableColumn<UIEmbryoThaw, Integer> srNo;

	@FXML
	private TableColumn<UIEmbryoThaw, UIDay> day1;

	@FXML
	private TableColumn<UIEmbryoThaw, UIDay> day2;

	// creating instance variables(class level)
	private RemarksDto remarksDto;
	private int remarksType = Module.EGG_THAWING.getKey();

	@FXML
	public void initialize() {
		embryoThawTableView.getSelectionModel().setCellSelectionEnabled(true);
		embryoThawTableView.setEditable(false);

		day1.setCellFactory(new Callback<TableColumn<UIEmbryoThaw, UIDay>, TableCell<UIEmbryoThaw, UIDay>>() {

			@Override
			public TableCell<UIEmbryoThaw, UIDay> call(TableColumn<UIEmbryoThaw, UIDay> param) {
				DayCell dayCell = new DayCell();
				return dayCell;
			}
		});

		day2.setCellFactory(new Callback<TableColumn<UIEmbryoThaw, UIDay>, TableCell<UIEmbryoThaw, UIDay>>() {

			@Override
			public TableCell<UIEmbryoThaw, UIDay> call(TableColumn<UIEmbryoThaw, UIDay> param) {
				DayCell dayCell = new DayCell();
				dayCell.addEventHandler(MouseEvent.MOUSE_CLICKED, new DayEvent());
				return dayCell;
			}
		});

	}

	public void buildData() {
		resetFields();
		makeFieldNonEditable();
		remarksDto = uiRemarksService.getRemarksByCodeId(womanCode.getId(), remarksType);
		if (null != remarksDto)
			remarksTextArea.setText(remarksDto.getRemarksText());
		embryoTransferButton.setStyle("-fx-background-color: #B73630;");
		commonDetailController.setMainApp(mainApp);
		// commonDetailController.setCouple(couple);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		investigationCommonController.setCouple(couple);
		investigationCommonController.setManCode(manCode);
		investigationCommonController.setWomanCode(womanCode);
		investigationCommonController.build();

		ObservableList<UIEmbryoThaw> uiThawList = FXCollections.observableArrayList();
		/*
		 * Map<Long, Map<Integer, List<DayProgressValue>>> dayProgressValueMap =
		 * uiDayProgressValueService
		 * .findDayProgressValueMapByCodeIdDestinyAndDateOfUseNotNull(womanCode.
		 * getId(), Option.CRYO.getKey());
		 */

		Map<Long, Map<Integer, List<DayProgressValueDto>>> dayProgressValueMap = uiDayProgressValueService
				.findDayProgressValueMapForEmbryoThaw(womanCode.getId(), CycleType.EMBRYO_THAW.getKey());

		for (Map.Entry<Long, Map<Integer, List<DayProgressValueDto>>> element : dayProgressValueMap.entrySet()) {
			Map<Integer, List<DayProgressValueDto>> innerValue = element.getValue();

			// day 1 is from Embryology Overview screen
			UIDay day1 = getUIDay(innerValue.get(Module.EMBRYOLOGY_OVERVIEW.getKey()),
					Module.EMBRYOLOGY_OVERVIEW.getKey());

			Long embryoCodeId = day1.getEmbryoCodeId();
			int embryoIndex = day1.getOocyte();

			// day2 is from Egg Thawing screen if the values is saved for that
			// pop up.
			UIDay day2 = getUIDay(innerValue.get(Module.EGG_THAWING.getKey()), Module.EGG_THAWING.getKey());
			// as day2 can have empty dayProgressValue list so we have to set
			// the embryoCodeId and oocyte as that will be same for both days
			day2.setEmbryoCodeId(embryoCodeId);
			day2.setOocyte(embryoIndex);

			UIEmbryoThaw uiEmbryo = new UIEmbryoThaw(embryoCodeId, embryoIndex, day1, day2);
			uiThawList.add(uiEmbryo);
		}
		srNo.setCellValueFactory(new PropertyValueFactory<UIEmbryoThaw, Integer>("srNo"));
		day1.setCellValueFactory(new PropertyValueFactory<UIEmbryoThaw, UIDay>("day1"));
		day2.setCellValueFactory(new PropertyValueFactory<UIEmbryoThaw, UIDay>("day2"));
		embryoThawTableView.setItems(uiThawList);
	}

	class DayEvent implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (EnumPermission.canWrite(login.getRoleId(), Module.EGG_THAWING.getKey())) {
				if (event.getClickCount() > 1) {

					DayCell cell = null;
					if (event.getTarget() instanceof DayCell) {
						cell = (DayCell) event.getTarget();
					} else if (event.getTarget() instanceof Text) {
						Text text = (Text) event.getTarget();
						cell = (DayCell) text.getParent();
					}

					if (cell != null) {
						UIDay item = cell.getItem();
						if (womanCode != null) {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(MainApp.class.getResource("/view/DayDialog.fxml"));
							try {

								GridPane dayDialog = (GridPane) loader.load();
								double width = 111 * 7;
								dayDialog.setPrefWidth(width);
								dayDialog.setMaxWidth(width);
								dayDialog.setMinWidth(width);
								Group group = new Group(dayDialog);
								Scene scene = new Scene(group);
								mainApp.getDayDialog().setScene(scene);
								scene.getStylesheets()
										.add(MainApp.class.getResource("/CSS/dayDialog.css").toExternalForm());
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

						}
					} else {
						Notify notify = new Notify(AlertType.INFORMATION);
						notify.setContentText(oppsError);
						notify.showAndWait();
					}

				}
			} else {
				FileUtils.privillegeEditError();
			}

		}
	}

	@FXML
	public void saveAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.EGG_THAWING.getKey())) {
			if (!StringUtils.isEmpty(remarksTextArea.getText())) {

				if (remarksDto == null) {
					remarksDto = new RemarksDto();
				}
				remarksDto.setRemarksText(remarksTextArea.getText());
				remarksDto.setCodeId(womanCode.getId());
				remarksDto.setRemarksType(remarksType);
				remarksDto = uiRemarksService.save(remarksDto);
			}
			buildData();
		} else
			FileUtils.privillegeEditError();

	}

	@FXML
	public void editAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.EGG_THAWING.getKey())) {
			remarksTextArea.setEditable(true);
		} else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void embryoTransferAction() {
		LoadPanels.loadModule(mainApp, login, Module.EMBRYO_TRANSFER.getKey(), null);
	}

	class DayCell extends TableCell<UIEmbryoThaw, UIDay> {

		@Override
		protected void updateItem(UIDay item, boolean empty) {

			super.updateItem(item, empty);
			if (!empty) {
				setText(empty ? null : getString());
				setGraphic(null);
			}

		}

		private String getString() {
			return getItem() == null ? "" : getItem().getValue();
		}
	}

	/**
	 * Gets the UI day from given day progress value list.
	 *
	 * @param dayProgressValueList
	 *            the day progress value list
	 * @param moduleId
	 *            the module id
	 * @return the UI day
	 */
	private UIDay getUIDay(List<DayProgressValueDto> dayProgressValueList, int moduleId) {

		String embryoGrade = "";
		Long embryoCodeId = -1l;
		Date dayDate = new Date();
		int dayIndex = -1;
		int embryoIndex = -1;
		if (dayProgressValueList != null) {
			for (DayProgressValueDto dayProgressValue : dayProgressValueList) {
				embryoCodeId = dayProgressValue.getEmbryoCodeId();
				int optionKey = dayProgressValue.getDayOptionId();
				Option option = Option.getOptionByKey(optionKey);
				if (!option.equals(Option.CRYO)) {
					embryoGrade += option.getName() + ".";
				}
				embryoIndex = dayProgressValue.getEmbryoCodeIndex();
				dayDate = dayProgressValue.getDayDate();
			}
		}
		UIDay uiDay = new UIDay(dayIndex, embryoIndex, embryoGrade, embryoCodeId, dayDate, moduleId);
		return uiDay;
	}

	// print Embryo Thawing Nodes page method
	@FXML
	public void printAction() {
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);

		List<Node> nodes = createThawingPrintTable(embryoThawTableView.getColumns(), embryoThawTableView.getItems(),
				pageLayout);
		List<Node> remarksNodeList = printTemplate.createRemarks(remarksTextArea.getText(), pageLayout);
		for (Node remarkNode : remarksNodeList) {
			nodes.add(remarkNode);
		}
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
		// Setting the Page Content(Common Section, Table View) at center
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		VBox spaceVBox = new VBox();
		spaceVBox.setPrefHeight(20);
		contentVBox.getChildren().addAll(patientGrid, spaceVBox, table);
		root.setCenter(contentVBox);

		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	/**
	 * Creating Table on multiple pages if table content is more
	 *
	 * @param pageLayout
	 * @return List<Node>
	 */
	private List<Node> createThawingPrintTable(ObservableList<TableColumn<UIEmbryoThaw, ?>> columns,
			Collection<UIEmbryoThaw> items, PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = pageLayout.getPrintableWidth() / columnCount;
		for (UIEmbryoThaw record : items) {
			HBox row = createTableRow(record, labelWidth, pageLayout);
			// elementHeight would be the height of each cell
			final double elementHeight = 15;
			// adding table on multiple pages
			// 180 - height of Header + patient section + space VBox + Footer
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 180) {
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
	private HBox createTableHeader(ObservableList<TableColumn<UIEmbryoThaw, ?>> columns, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setStyle("-fx-border-width: 1 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("SR NO", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("DAY 1", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("DAY 2", labelWidth));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("EMBRYOLOGIST", labelWidth));
		return headerHBox;
	}

	/**
	 * Setting Cell Data (Row) in HBox
	 */
	private HBox createTableRow(UIEmbryoThaw uiEmbryoThaw, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 1 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(uiEmbryoThaw.getSrNo()), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(uiEmbryoThaw.getDay1().getValue(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(uiEmbryoThaw.getDay2().getValue(), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(IConstants.emptyString, labelWidth, false));
		return rowHBox;
	}

	// Reset All TextField and TextArea
	private void resetFields() {
		remarksTextArea.setText(IConstants.emptyString);
	}
	
	private void makeFieldNonEditable(){
		remarksTextArea.setEditable(false);
	}

}
