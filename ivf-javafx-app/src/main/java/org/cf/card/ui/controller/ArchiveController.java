package org.cf.card.ui.controller;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.print.PrintService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.cf.card.dto.ArchiveDto;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Confirmation;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.BooleanCheckCell;
import org.cf.card.ui.model.UIArchive;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIArchiveService;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.Constants.Extensions;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

/**
 * @author Ankit
 *
 */
public class ArchiveController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.archive.details");
	private static final String iconURL = "/icons/archieve.png";
	private static final String deleteArchiveFileButtonText = MessageResource.getText("delete.button");
	private static final String deleteArchiveWarningText = MessageResource.getText("delete.warning.message");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");

	// creating object
	UIArchiveService archiveService = new UIArchiveService();
	UIClipartService clipartService = new UIClipartService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding fxml element
	@FXML
	private TableView<UIArchive> archiveTable;

	@FXML
	private TableColumn<UIArchive, String> date;

	@FXML
	private TableColumn<UIArchive, ArchiveDto> fileName;

	@FXML
	private Button selectedPrint;

	@FXML
	private Label fileUploadedTypeLable;

	@FXML
	private TableColumn<UIArchive, Boolean> selectedColumn;

	@FXML
	private TableColumn<UIArchive, Integer> deleteFile;

	@FXML
	private TableColumn<UIArchive, String> extention;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private AnchorPane commonDetail;

	// creating instance variables(class level)
	private ObservableList<UIArchive> uiCyclesList;

	// callback function for checkbox which created at runtime
	private Callback<TableColumn<UIArchive, Boolean>, TableCell<UIArchive, Boolean>> booleanCellFactory = new Callback<TableColumn<UIArchive, Boolean>, TableCell<UIArchive, Boolean>>() {
		@Override
		public TableCell<UIArchive, Boolean> call(TableColumn<UIArchive, Boolean> p) {
			return new BooleanCheckCell();
		}
	};

	// callback function for file open while click on cell
	Callback<TableColumn<UIArchive, ArchiveDto>, TableCell<UIArchive, ArchiveDto>> detailCellFactory = new Callback<TableColumn<UIArchive, ArchiveDto>, TableCell<UIArchive, ArchiveDto>>() {
		@Override
		public TableCell<UIArchive, ArchiveDto> call(TableColumn<UIArchive, ArchiveDto> param) {
			DetailCell detailCell = new DetailCell();
			return detailCell;
		}
	};

	// callback function for delete button which created at runtime
	private Callback<TableColumn<UIArchive, Integer>, TableCell<UIArchive, Integer>> buttonCellFactory = new Callback<TableColumn<UIArchive, Integer>, TableCell<UIArchive, Integer>>() {
		@Override
		public TableCell<UIArchive, Integer> call(TableColumn<UIArchive, Integer> param) {
			return new ButtonCellShedule();
		}
	};

	@FXML
	public void initialize() {
		// archiveTable.getSelectionModel().setCellSelectionEnabled(true);
		archiveTable.setEditable(false);
		buildData();
	}

	public void buildData() {

		if (null != couple) {
			commonDetailController.setMainApp(mainApp);
			commonDetailController.setWomanPersonalInfo(womanCode);
			commonDetailController.setPartnerPersonalInfo(manCode);
			fileUploadedTypeLable
					.setFont(Font.font(Constants.PRINT_FONT_FAMILY_ITALIC, FontWeight.NORMAL, FontPosture.ITALIC, 18));
			fileUploadedTypeLable.setStyle("-fx-text-fill: #FFFF99;");
			// fileUploadedTypeLable.setFont(Font.font(Constants.PRINT_FONT_FAMILY,
			// FontWeight.BOLD, fontSize));

			// Long coupleId = couple.getId();
			List<ArchiveDto> archives = new ArrayList<>();
			uiCyclesList = FXCollections.observableArrayList();

			archives = archiveService.archiveList(couple.getId());
			for (ArchiveDto archiveDto : archives) {
				UIArchive archive = new UIArchive(archiveDto);
				uiCyclesList.add(archive);

			}

			selectedColumn.setCellValueFactory(cellData -> cellData.getValue().checkProperty());
			selectedColumn.setCellFactory(booleanCellFactory);
			deleteFile.setCellFactory(buttonCellFactory);
			// embryo Cycles
			date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			fileName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());

			fileName.setCellFactory(detailCellFactory);
			extention.setCellValueFactory(cellData -> cellData.getValue().extentionProperty());
			archiveTable.setItems(uiCyclesList);
		}

	}

	/*
	 * Mark all rows of Table
	 */
	public void markAllAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.ARCHIVE.getKey())) {
			for (int i = 0; i < uiCyclesList.size(); i++) {
				uiCyclesList.get(i).setCheck(true);
			}
		} else {
			FileUtils.privillegeEditError();
		}
	}

	/*
	 * Unmark all rows of Table
	 */
	public void unmarkAllAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.ARCHIVE.getKey())) {
			for (int i = 0; i < uiCyclesList.size(); i++) {
				uiCyclesList.get(i).setCheck(false);
			}
		} else {
			FileUtils.privillegeEditError();
		}
	}

	/**
	 * Prints the selected Items as Data output.
	 * 
	 * @param actionEvent
	 *            the action event
	 */
	@FXML
	private void printSelectedAction() {
		System.out.println("I AM ON CLICK BUTTON from Print Selected Method::::::::  ");
		if (null != uiCyclesList && uiCyclesList.size() > 0) {
			for (int z = 0; z < uiCyclesList.size(); z++) {
				UIArchive uiArchive = null;
				String filePath = null;
				ArchiveDto archiveDto = null;
				if (uiCyclesList.get(z).checkProperty().getValue()) {

					uiArchive = uiCyclesList.get(z);
					archiveDto = uiArchive.getFileName();
					filePath = archiveService.getFileFromServer(archiveDto);
					System.out.println(filePath);
					File file = new File(filePath);
					PrinterJob printerJob = PrinterJob.createPrinterJob();
					PageLayout pageLayout = printTemplate.printPageLayout(printerJob);

					if (null != file) {
						// print if file type is Images(.png, .jpg)
						if (Extensions.PNG.getStrValue().equals(archiveDto.getExtention())
								|| Extensions.JPG.getStrValue().equals(archiveDto.getExtention())) {

							if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {

								AnchorPane root = new AnchorPane();
								root.setPrefHeight(pageLayout.getPrintableHeight());
								root.setPrefWidth(pageLayout.getPrintableWidth());
								GridPane gridPane = new GridPane();
								gridPane.setAlignment(Pos.CENTER);
								gridPane.setMinHeight(50);
								gridPane.setMaxHeight(50);
								gridPane.setPrefHeight(root.getHeight());
								gridPane.setPrefWidth(root.getWidth());

								gridPane.setMaxHeight(600);
								gridPane.setMaxWidth(600);

								AnchorPane.setTopAnchor(gridPane, 0.0);
								AnchorPane.setBottomAnchor(gridPane, 0.0);
								AnchorPane.setLeftAnchor(gridPane, 0.0);
								AnchorPane.setRightAnchor(gridPane, 0.0);

								ImageView imageView = new ImageView();
								imageView.setFitHeight(300);
								imageView.setFitWidth(500);
								GridPane.setValignment(imageView, VPos.CENTER);
								GridPane.setHalignment(imageView, HPos.CENTER);
								gridPane.add(imageView, 0, 0);
								root.getChildren().add(gridPane);

								imageView.setImage(new Image(file.toURI().toString()));
								printerJob.printPage(root);
								printerJob.endJob();

							}
						}
						// print if file type is Text(.txt)
						else if (Extensions.TXT.getStrValue().equals(archiveDto.getExtention())) {

							if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {

								AnchorPane root = new AnchorPane();

								root.setPrefHeight(pageLayout.getPrintableHeight());
								root.setPrefWidth(pageLayout.getPrintableWidth());
								root.setStyle("-fx-border-width: 1 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
								GridPane gridPane = new GridPane();
								gridPane.setPrefHeight(root.getHeight());
								gridPane.setPrefWidth(root.getWidth());
								AnchorPane.setTopAnchor(gridPane, 0.0);
								AnchorPane.setBottomAnchor(gridPane, 0.0);
								AnchorPane.setLeftAnchor(gridPane, 0.0);
								AnchorPane.setRightAnchor(gridPane, 0.0);

								ColumnConstraints columnConstraints = new ColumnConstraints();
								columnConstraints.setPercentWidth(100);
								gridPane.getColumnConstraints().add(columnConstraints);

								Label label = new Label();
								label.setStyle("-fx-font-size: 9px;");
								label.setWrapText(true);
								String reatTextFromFile = null;
								try {
									reatTextFromFile = Util.readFile(file.toString());
								} catch (IOException e) {
									e.printStackTrace();
								}
								label.setText("    " + reatTextFromFile);

								GridPane.setValignment(label, VPos.CENTER);
								GridPane.setHalignment(label, HPos.CENTER);
								gridPane.getChildren().add(label);
								root.getChildren().add(gridPane);

								printerJob.printPage(root);
								printerJob.endJob();

							}

						}
						// print if file type is PDF(.pdf)
						else {
							try {
								java.awt.print.PrinterJob printJob = java.awt.print.PrinterJob.getPrinterJob();
								PrintService printService = printJob.getPrintService();
								printJob.setPrintService(printService);
								PDDocument doc = PDDocument.load(file);
								System.out.println(doc.getNumberOfPages());
								doc.print();

							} catch (Exception e) {
								System.err.println("Printed Aborted.");
							}

						}
					}
				}

			}
		} else {
			Notify alert = new Notify(AlertType.WARNING, noDataAvailableMessage);
			alert.showAndWait();
		}

	}

	/**
	 * Prints the summary action. This is print Screen.
	 */
	@FXML
	private void printSummaryAction() {
		buildData();
		System.out.println("I am Print Summary Action");
		Printer printer = Printer.getDefaultPrinter();
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		// setting layout of page
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, MarginType.DEFAULT);

		JobSettings jobSettings = printerJob.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		List<Node> nodes = createMyArchivePrintTable(archiveTable.getColumns(), archiveTable.getItems(), pageLayout);

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
	 * Creates the print page with BorderPane.
	 *
	 */
	private BorderPane createPrintPage(Node table, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, IConstants.emptyString, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section, Embryo Info, Table View) at
		// center
		VBox contentVBox = new VBox();
		GridPane patientGrid = printTemplate.createPatientSection(womanCode, manCode, pageLayout);
		contentVBox.getChildren().addAll(patientGrid, table);
		root.setCenter(contentVBox);

		// Setting the Footer at bottom
		GridPane footerGrid = printTemplate.createFooter(page, pageLayout);
		root.setBottom(footerGrid);
		return root;
	}

	/**
	 * Creates the my archive print table with columns and rows.
	 */
	private List<Node> createMyArchivePrintTable(ObservableList<TableColumn<UIArchive, ?>> columns,
			Collection<UIArchive> items, PageLayout pageLayout) {
		List<Node> nodes = new ArrayList<>();
		VBox tableVBox = null;
		double totalHeight = Double.POSITIVE_INFINITY;
		int columnCount = columns.size();
		double labelWidth = pageLayout.getPrintableWidth() - 5 / columnCount;
		for (UIArchive record : items) {
			HBox row = createTableRow(record, labelWidth, pageLayout);
			row.setPrefHeight(17);
			// elementHeight would be the height of each cell
			final double elementHeight = 30;
			// adding table on multiple pages
			// 210 - height of patient section + height of embryo Info HBox
			if (elementHeight + totalHeight > pageLayout.getPrintableHeight() - 50) {
				tableVBox = new VBox();
				tableVBox.setPrefWidth(pageLayout.getPrintableWidth());
				tableVBox.setStyle("-fx-border-width: 0 1 1 1; " + Constants.PRINT_GREY_BORDER_STYLE);
				HBox tableHeader = createTableHeader(columns, labelWidth + 5, pageLayout);
				tableHeader.setPrefHeight(17);
				// tableHeader.setSpacing(10);
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
	 * Creates the table row.
	 */
	private HBox createTableRow(UIArchive record, double labelWidth, PageLayout pageLayout) {
		HBox rowHBox = new HBox();
		rowHBox.setPrefWidth(pageLayout.getPrintableWidth());
		rowHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		rowHBox.getChildren().add(createTableRowCheckBox(record.isCheck() + "", labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(record.getDate()), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren().add(printTemplate
				.createTableRowLabel(String.valueOf(record.getFileName().getOrignalFileName()), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(record.getExtention()), labelWidth, false));
		rowHBox.getChildren().add(printTemplate.createSeparator());
		rowHBox.getChildren()
				.add(printTemplate.createTableRowLabel(String.valueOf(record.getDeleteFile()), labelWidth, false));
		return rowHBox;

	}

	private HBox createTableHeader(ObservableList<TableColumn<UIArchive, ?>> columns, double labelWidth,
			PageLayout pageLayout) {
		HBox headerHBox = new HBox();
		headerHBox.setPrefWidth(pageLayout.getPrintableWidth());
		headerHBox.setStyle("-fx-border-width: 1 0 0 0; " + Constants.PRINT_GREY_BORDER_STYLE);
		for (TableColumn<UIArchive, ?> cols : columns) {
			headerHBox.getChildren().add(printTemplate.createTableHeaderLabel(cols.getText(), labelWidth));
			if (cols.getText().equals("Delete"))
				System.out.println();
			else {
				headerHBox.getChildren().add(printTemplate.createSeparator());
			}

		}
		return headerHBox;
	}

	public CheckBox createTableRowCheckBox(String value, double labelWidth, boolean boldText) {
		CheckBox checkBox = new CheckBox();
		checkBox.setPrefWidth(labelWidth);
		if (value.equals(true))
			checkBox.setSelected(true);
		// double fontSize = 8;
		// condition to set the Grade in bold
		/*
		 * if (boldText) {
		 * rowLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY,
		 * FontWeight.BOLD, fontSize)); } else {
		 * rowLabel.setFont(Font.font(Constants.PRINT_FONT_FAMILY,
		 * FontWeight.NORMAL, fontSize)); } rowLabel.setPrefWidth(labelWidth);
		 * rowLabel.setStyle(Constants.PRINT_STYLE);
		 */
		return checkBox;
	}

	public Separator createSeparator() {
		Separator separator = new Separator(Orientation.VERTICAL);
		return separator;
	}

	/**
	 * Adds the new file by Browse from PC.
	 *
	 * @param actionEvent
	 *            the action event
	 */
	@FXML
	private void addNewFile() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.ARCHIVE.getKey())) {
			FileChooser fileChooser = new FileChooser();
			String uploadFilePath = null;
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("Image Files", "*.png", "*.jpg"), new ExtensionFilter("All Files", "*.pdf"));
			File selectedFile = fileChooser.showOpenDialog(mainApp.getPatientFileStage());
			uploadFilePath = Configuration.getClipartDir() + Constants.RESOURCE_ARCHIVE_FILE_UPLOAD;
			if (null != selectedFile)
				archiveService.saveArchiveFile(selectedFile, uploadFilePath, couple.getId());
			buildData();
		} else {
			// administratorWarningLabel.setText("You are not an
			// administrator!");
			FileUtils.privillegeEditError();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (archiveTable.getSelectionModel().isSelected(archiveTable.getSelectionModel().getSelectedIndex(),
				selectedColumn) && e.getX() <= selectedColumn.getWidth()) {
			uiCyclesList.get(archiveTable.getSelectionModel().getSelectedIndex())
					.setCheck(!archiveTable.getSelectionModel().getSelectedItem().isCheck());
		}
	}

	/**
	 * inner Class DetailCell. This is CallBack Class.
	 */
	class DetailCell extends TableCell<UIArchive, ArchiveDto> {
		UIClipartService clipartService = new UIClipartService();

		@Override
		protected void updateItem(ArchiveDto item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null && item.getId() != null) {
				GridPane gridPane = buildNode(item);
				setGraphic(gridPane);
			} else {
				setGraphic(null);
			}
		}

		private GridPane buildNode(ArchiveDto item) {
			GridPane gridPane = new GridPane();
			Button imageButton = new Button();
			imageButton.setBorder(null);
			imageButton.setText(item.getOrignalFileName());
			imageButton.setPadding(new Insets(0, 0, 0, 0));
			imageButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-pref-width: 1000");
			imageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new LoadArchiveDetails(item));

			GridPane.setMargin(imageButton, new Insets(0, 0, 0, 0));
			gridPane.add(imageButton, 0, 0);
			gridPane.setAlignment(Pos.CENTER);
			return gridPane;
		}
	}

	/**
	 * The Class OpenFile. This method is for Open file in default Viewer of PC.
	 */
	class OpenFile implements Runnable {
		File file;

		public OpenFile(File file) {
			this.file = file;
		}

		@Override
		public void run() {
			if (file.exists()) {
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class LoadArchiveDetails implements EventHandler<MouseEvent> {
		ArchiveDto item;

		public LoadArchiveDetails(ArchiveDto item) {
			this.item = item;
		}

		@Override
		public void handle(MouseEvent event) {
			if (null != item) {
				String filePath = archiveService.getFileFromServer(item);
				File file = new File(filePath);
				OpenFile openFile = new OpenFile(file);
				Thread thread = new Thread(openFile);
				thread.start();
			}
		}
	}

	// create inner class for custom buttons inside column(Delete)
	class ButtonCellShedule extends TableCell<UIArchive, Integer> {

		// create new Button object
		Button cellButton = new Button(deleteArchiveFileButtonText);

		ButtonCellShedule() {
			cellButton.setMinHeight(30);
			cellButton.setAlignment(Pos.CENTER);
			cellButton.setStyle("-fx-background-color: #B73630;");
			// Action when the button is pressed
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("Print from Delete button");
					if (login.getRoleId() == 1) {

						ButtonCellShedule buttonCellShedule = null;
						UIArchive archive = null;
						Confirmation confirmation = new Confirmation(AlertType.CONFIRMATION);

						ButtonType buttonTypeYes = new ButtonType(MessageResource.getText("common.button.yes"));
						ButtonType buttonTypeCancel = new ButtonType(MessageResource.getText("common.button.cancel"));
						confirmation.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
						confirmation.setContentText(deleteArchiveWarningText);
						Optional<ButtonType> result = confirmation.showAndWait();
						if (result.get() == buttonTypeYes) {
							if (event.getTarget() instanceof Button) {
								Button button = (Button) event.getTarget();
								buttonCellShedule = (ButtonCellShedule) button.getParent();
								archive = (UIArchive) buttonCellShedule.getTableRow().getItem();
								Long fileId = archive.getId();
								System.out.println(fileId);
								archiveService.deleteUploadedFile(fileId);
								buildData();
							}
						}
					} else
						FileUtils.privillegeEditError();

				}
			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Integer item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}

	}

	class BooleanCell extends TableCell<UIArchive, Boolean> {

		private CheckBox checkBox = new CheckBox();

		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty || null != item)
				setGraphic(checkBox);
			else {
				setGraphic(null);
			}
		}

	}

	/**
	 * Choose printer. This printee class from Java API not of JavaFX 8 This is
	 * for print PDF and Images Directly
	 * 
	 * @return the prints the service
	 */
	public static PrintService choosePrinter() {
		java.awt.print.PrinterJob printJob = java.awt.print.PrinterJob.getPrinterJob();
		if (printJob.printDialog()) {
			return printJob.getPrintService();
		} else {
			return null;
		}
	}

	/**
	 * Prints the pdf. Print the Pdf and Images
	 */
	public static void printPDF(String fileName, PrintService printer) throws IOException, PrinterException {
		java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
		job.setPrintService(printer);
		PDDocument doc = PDDocument.load(fileName);
		System.out.println(doc.getNumberOfPages());
		// doc.silentPrint();
		doc.print();
		job.cancel();

	}
}
