package org.cf.card.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.CycleDto;
import org.cf.card.model.Client;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.TextAreaCell;
import org.cf.card.ui.model.UICycles;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIEmbryoService;
import org.cf.card.ui.service.UIRemarksService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.EnumPregnancyOutcomes;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CyclesController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.embryology");
	private static final String titleDescription = MessageResource
			.getText("mainpage.title.embryology.description.cycles");
	private static final String iconURL = "/icons/cycles.png";
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
//	private static final int SCREEN_ID = Module.CYCLES.getKey();

	// creating object
	UIEmbryoService uiEmbryoService = new UIEmbryoService();
	UIRemarksService uiRemarksService = new UIRemarksService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element
	@FXML
	public CommonDetailController commonDetailController;

	@FXML
	public InvestigationCommonController investigationCommonController;

	@FXML
	TableView<UICycles> cyclesTableView;

	@FXML
	TableColumn<UICycles, Integer> srNo;

	@FXML
	TableColumn<UICycles, String> womanCodeCol;

	@FXML
	TableColumn<UICycles, String> date;

	@FXML
	TableColumn<UICycles, String> eggs;

	@FXML
	TableColumn<UICycles, String> pOutcome;

	@FXML
	TableColumn<UICycles, Long> evaluation;

	@FXML
	TableColumn<UICycles, String> cyclesButtonColumn;

	@FXML
	TableColumn<UICycles, String> partnerCodeCol;

	@FXML
	TableColumn<UICycles, String> semenQuality;

	@FXML
	TableColumn<UICycles, String> remarks;

	@FXML
	TableColumn<UICycles, String> saveButtonColumn;

	// label Width sizes for printing
	int big = 40;
	int bigger = 80;
	int biggest = 150;

	public void initialize() {
		/*
		 * cyclesTableView.setEditable(true); remarks.setEditable(true);
		 */
	}

	public void buildData() {
		commonDetailController.setMainApp(mainApp);
		// commonDetailController.setCouple(couple);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		investigationCommonController.setCouple(couple);
		investigationCommonController.setManCode(manCode);
		investigationCommonController.setWomanCode(womanCode);
		investigationCommonController.build();

		Client man = couple.getMan();
		Client woman = couple != null ? couple.getWoman() : null;

		Long manClientId = man != null ? man.getId() : null;
		Long womanClientId = woman != null ? woman.getId() : null;

		ObservableList<UICycles> uiCyclesList = FXCollections.observableArrayList();
		if (null != womanClientId && null != manClientId) {
			Collection<CycleDto> cycleDtoSet = uiEmbryoService.findCoupleCycles(womanClientId, manClientId);
			int counter = 1;
			for (CycleDto cycleDto : cycleDtoSet) {
				// CycleDto cycleDto = entry.getValue();
//				String remarks = null;
//				Long remarkId = null;
//				Long womanCodeId = cycleDto.getWomanCodeId();
//				if (null != womanCodeId) {
//					RemarksDto remarksDto = uiRemarksService.getRemarksByCodeId(womanCodeId, SCREEN_ID);
//					if (null != remarksDto) {
//						remarks = remarksDto.getRemarksText();
//						remarkId = remarksDto.getId();
//					}
//				}
				UICycles uiCycles = null;

				String date = Util.formatDate(IConstants.DATE_FORMAT, cycleDto.getStartDate());
				String endDate = Util.formatDate(IConstants.DATE_FORMAT, cycleDto.getEndDate());
				String bioChemical = IConstants.emptyString;
				if(cycleDto.getBiochemicalOption()!=null  && cycleDto.getBiochemicalOption()!=0){
					bioChemical = EnumPregnancyOutcomes.BiochemicalType.getEnumByKey(cycleDto.getBiochemicalOption()).getValue();
				}
				uiCycles = new UICycles(counter, cycleDto.getWomanCodeId(), cycleDto.getWomanCode(),
						cycleDto.getPartnerCodeId(), cycleDto.getPartnerCode(), date, endDate,
						cycleDto.getEggCollection(), cycleDto.getQuality(), bioChemical,
						cycleDto.getEvolution(), cycleDto.getRemark(),cycleDto.getRemarkId());
				uiCyclesList.add(uiCycles);
				counter++;
			}
		}

		srNo.setCellValueFactory(new PropertyValueFactory<UICycles, Integer>("srNo"));
		womanCodeCol.setCellValueFactory(new PropertyValueFactory<UICycles, String>("womanCode"));
		partnerCodeCol.setCellValueFactory(new PropertyValueFactory<UICycles, String>("partnerCode"));
		date.setCellValueFactory(new PropertyValueFactory<UICycles, String>("date"));
		eggs.setCellValueFactory(new PropertyValueFactory<UICycles, String>("eggCollection"));
		semenQuality.setCellValueFactory(new PropertyValueFactory<UICycles, String>("quality"));
		pOutcome.setCellValueFactory(new PropertyValueFactory<UICycles, String>("bioChemical"));
		evaluation.setCellValueFactory(new PropertyValueFactory<UICycles, Long>("evaluation"));
		// cyclesButtonColumn.setCellValueFactory(new
		// PropertyValueFactory<UICycles, String>("cycles"));
		cyclesButtonColumn.setCellFactory(buttonCellFactory);
		remarks.setCellValueFactory(new PropertyValueFactory<UICycles, String>("remarks"));
		// Adding Textarea in remarks column
		remarks.setCellFactory(new Callback<TableColumn<UICycles, String>, TableCell<UICycles, String>>() {

			@Override
			public TableCell<UICycles, String> call(TableColumn<UICycles, String> param) {
				return new TextAreaCell<UICycles>(cyclesTableView, login.getRoleId());
			}
		});

		cyclesTableView.setItems(uiCyclesList);
	}

	private Callback<TableColumn<UICycles, String>, TableCell<UICycles, String>> buttonCellFactory = new Callback<TableColumn<UICycles, String>, TableCell<UICycles, String>>() {

		@Override
		public TableCell<UICycles, String> call(TableColumn<UICycles, String> param) {
			ButtonCellCycles buttonCellCycles = new ButtonCellCycles();
			// TODO Auto-generated method stub
			return buttonCellCycles;
		}
	};

	class ButtonCellCycles extends TableCell<UICycles, String> {
		Button cyclesButton = new Button(MessageResource.getText("cycles.button.cycleDetails"));

		public ButtonCellCycles() {
			cyclesButton.setOnAction(new SwitchCycle());
		}

		// Display button if row is not empty
		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (!empty) {
				setGraphic(cyclesButton);
			} else {
				setGraphic(null);
			}
		}
	}

	class SwitchCycle implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (event.getTarget() instanceof Button) {
				Button button = (Button) event.getTarget();
				ButtonCellCycles cell = (ButtonCellCycles) button.getParent();
				UICycles uiCycles = (UICycles) cell.getTableRow().getItem();
				if (null != uiCycles) {
					Long partnerCodeId = uiCycles.getPartnerCodeId();
					Long womanCodeId = uiCycles.getWomanCodeId();
					Long coupleId = couple.getId();
					String startDate = uiCycles.getDate();
					String endDate = uiCycles.getEndDate();

					@SuppressWarnings("unchecked")
					SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();

					if (womanCodeId == 0) {
						// get womanCodes from partnerCode
						List<Long> codes = uiEmbryoService.findCodeIdByCoupleIdAndStartDate(coupleId, startDate);
						for (Long code : codes) {
							if (code != partnerCodeId) {
								womanCodeId = code;
							}
						}
					}
					long status = isCurruntCycle(startDate, endDate);
					// setting Woman Code ID in session
					if (null != womanCodeId) {
						sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, womanCodeId);
					}

					if (partnerCodeId == 0) {
						// get partnerCode from womanCode
						List<Long> codes = uiEmbryoService.findCodeIdByCoupleIdAndStartDate(coupleId, startDate);
						for (Long code : codes) {
							if (code != womanCodeId) {
								partnerCodeId = code;
							}
						}
					}

					// setting Man Code Id in session
					if (null != partnerCodeId) {
						sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, partnerCodeId);
					}

					// setting couple in session
					sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple.getId());

					// setting session status
					sessionObject.setComponent(Constants.IS_CURRUNT_CYCLE, status);

					// switching cycle page to Embryology Overview screen
					LoadPanels.loadModule(mainApp, login, Module.EMBRYOLOGY_OVERVIEW.getKey(), null);

					long sessionValue = sessionObject.getComponent(Constants.IS_CURRUNT_CYCLE);
					System.out.println(sessionValue);
				}
			}
		}


		public long isCurruntCycle(String startDate, String endDate) {
			Date dateStart = null;
			Date dateEnd = null;
			Date systemDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
			try {
				dateStart = dateFormat.parse(startDate);
				dateEnd = dateFormat.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (systemDate.compareTo(dateStart) >= 0 && systemDate.compareTo(dateEnd) <= 0) {
				return 1;
			}
			return 0;
		}
	}

	// print Cycles Nodes page method
	@FXML
	public void printAction() {
		Printer printer = Printer.getDefaultPrinter();
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
				Printer.MarginType.DEFAULT);
		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		List<Node> nodes = createCyclesPrintTable(cyclesTableView.getColumns(), cyclesTableView.getItems(), pageLayout);
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
		contentVBox.setPrefWidth(pageLayout.getPrintableWidth());
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
	private List<Node> createCyclesPrintTable(ObservableList<TableColumn<UICycles, ?>> columns,
			Collection<UICycles> items, PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		// int columnCount = columns.size() - 1;
		// double labelWidth = pageLayout.getPrintableWidth() / columnCount;
		for (UICycles record : items) {
			// elementHeight would be the height of each cell
			final double elementHeight = 20;
			HBox row = createTableRow(record, pageLayout);
			row.setPrefHeight(elementHeight);

			// adding table on multiple pages
			// 180 - height of patient section + space VBox + Header + Footer
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 180) {
				tableVBox = new VBox();
				tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
				HBox tableHeader = createTableHeader(columns, pageLayout);
				// adding table columns in Table VBox
				tableVBox.getChildren().add(tableHeader);
				nodes.add(tableVBox);
				totalHeight = 0;
			}
			totalHeight += elementHeight;
			if (tableVBox != null) {
				tableVBox.getChildren().add(row); // adding table rows in Table
													// VBox
			}

		}
		return nodes;
	}

	/**
	 * Setting Column Names(Header) in HBox
	 */
	private HBox createTableHeader(ObservableList<TableColumn<UICycles, ?>> columns, PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setStyle("-fx-border-width: 1 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);

		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("SR NO.", big));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("WOMAN CODE", bigger));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("PARTNER CODE", bigger));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("ST. DATE", bigger));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("EGGS", big));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("SEMEN QUALITY", bigger));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("PREGNANCY OUTCOME(BIOCHEMICAL)", biggest));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("EVOLUTION", bigger));
		headerHBox.getChildren().add(printTemplate.createSeparator());
		headerHBox.getChildren().add(printTemplate.createTableHeaderLabel("REMARKS", biggest));

		return headerHBox;
	}

	/**
	 * Setting Cell Data (Row) in HBox
	 */
	private HBox createTableRow(UICycles uiCycles, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 1 1 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(String.valueOf(uiCycles.getSrNo()), big, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(uiCycles.getWomanCode(), bigger, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(uiCycles.getPartnerCode(), bigger, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(uiCycles.getDate(), bigger, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(uiCycles.getEggCollection()), big, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(uiCycles.getQuality(), bigger, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(uiCycles.getBioChemical(), biggest, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(uiCycles.getEvaluation()), bigger, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate.createTableRowLabel(uiCycles.getRemarks(), biggest, false));
		return rowHBox;
	}

}
