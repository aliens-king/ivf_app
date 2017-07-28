package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.DoctorNurseDto;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UINurseStationService;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;
import org.springframework.util.StringUtils;

import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NurseStationController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.nursestation");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String iconURL = "/icons/nurse_station.png";
	private static final String NSError = MessageResource.getText("common.empty.fields.error.message");

	// creating object
	UINurseStationService uiNurseStationService = new UINurseStationService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding FXML element
	@FXML
	private TextArea nurseNotesTextArea;

	@FXML
	private TextArea labRemarksTextArea;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private InvestigationCommonController investigationCommonController;

	@FXML
	private Label administratorWarningLabel;

	@FXML
	private TextArea editableNurseNotesTextArea;

	@FXML
	private TextArea editableLabRemarksTextArea;

	private Date currentDate = null;
	private String currentDateAsString = null;

	// creating instance variables(class level)
	private DoctorNurseDto todayDoctorNurseDto = null;

	@FXML
	private void initialize() {
		makeFieldsNonEditable();
		nurseNotesTextArea.setWrapText(true);
		labRemarksTextArea.setWrapText(true);
		editableNurseNotesTextArea.setWrapText(true);
		editableLabRemarksTextArea.setWrapText(true);
	}

	public void makeFieldsNonEditable() {
		nurseNotesTextArea.setEditable(false);
		labRemarksTextArea.setEditable(false);
		editableNurseNotesTextArea.setEditable(false);
		editableLabRemarksTextArea.setEditable(false);
	}

	public void makeFieldsEditable() {
		editableNurseNotesTextArea.setEditable(true);
		editableLabRemarksTextArea.setEditable(true);
	}

	public void buildData() {
		reset();
		commonDetailController.setMainApp(mainApp);
		// commonDetailController.setCouple(couple);
		commonDetailController.setWomanPersonalInfo(womanCode);
		commonDetailController.setPartnerPersonalInfo(manCode);
		investigationCommonController.setCouple(couple);
		investigationCommonController.setManCode(manCode);
		investigationCommonController.setWomanCode(womanCode);
		investigationCommonController.build();

		// fetching values from DB
		if (null != couple) {

			List<DoctorNurseDto> doctorNurseDtoList = uiNurseStationService.getNurseStationByCoupleId(couple.getId());
			if (null != doctorNurseDtoList && !doctorNurseDtoList.isEmpty()) {
				StringBuilder nurseNotesStb = new StringBuilder();
				StringBuilder labRemarksStb = new StringBuilder();
				currentDate = new Date();
				for (DoctorNurseDto doctorNurseDto : doctorNurseDtoList) {
					currentDateAsString = Util.formatDate(IConstants.DATE_FORMAT, currentDate);
					String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, doctorNurseDto.getCreatedDate());
					if (currentDateAsString.compareTo(sCreatedDate) == 0) {
						todayDoctorNurseDto = doctorNurseDto;
						editableNurseNotesTextArea.setText(createTextAreaValue(doctorNurseDto.getNurseNotes()));
						editableLabRemarksTextArea.setText(createTextAreaValue(doctorNurseDto.getRemarks()));
					} else {
						nurseNotesStb.append(
								sCreatedDate + "\n" + createTextAreaValue(doctorNurseDto.getNurseNotes()) + "\n");
						labRemarksStb
								.append(sCreatedDate + "\n" + createTextAreaValue(doctorNurseDto.getRemarks()) + "\n");
						todayDoctorNurseDto = null;
					}
				}
				nurseNotesTextArea.setText(nurseNotesStb.toString());
				labRemarksTextArea.setText(labRemarksStb.toString());
			} else {
				todayDoctorNurseDto = null;
			}
		}
		makeFieldsNonEditable();
	}

	private String createTextAreaValue(String textValue) {
		return textValue != null ? textValue : IConstants.emptyString;
	}

	@FXML
	private void editNurseStationAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.NURSES_STATION.getKey()))
			makeFieldsEditable();
		else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void saveNurseStationAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.NURSES_STATION.getKey())) {
			boolean result = validateFields();
			if (result == true) {
				if (null != couple) {
					if (todayDoctorNurseDto == null)
						todayDoctorNurseDto = new DoctorNurseDto();
					currentDate = new Date();
					// Now, saving NS data in nurse_station table instead of
					// Patient_investigation table

					todayDoctorNurseDto.setCoupleId(couple.getId());
					// if(!StringUtils.isEmpty(editableNurseNotesTextArea.getText())
					// && editableNurseNotesTextArea.getText() != null) {
					String editableNurseNotes = editableNurseNotesTextArea.getText();
					if (!StringUtils.isEmpty(editableNurseNotes))
						todayDoctorNurseDto.setNurseNotes(editableNurseNotes);

					String editableLabRemarks = editableLabRemarksTextArea.getText();
					if (!StringUtils.isEmpty(editableLabRemarks))
						todayDoctorNurseDto.setRemarks(editableLabRemarks);

					todayDoctorNurseDto.setCreatedDate(currentDate);
					todayDoctorNurseDto = uiNurseStationService.saveNurseStation(todayDoctorNurseDto);
				}
				// updating current couple object
				// couple = coupleService.getCoupleById(couple.getId());
				buildData();
				makeFieldsNonEditable();
			} else {
				Notify alert = new Notify(AlertType.ERROR, NSError);
				alert.showAndWait();
			}
		} else {
			FileUtils.privillegeEditError();
		}
	}

	private boolean validateFields() {
		boolean valid = true;
		if (editableNurseNotesTextArea.getText().equals(IConstants.emptyString)
				&& editableLabRemarksTextArea.getText().equals(IConstants.emptyString))
			valid = false;
		return valid;

	}

	// reset the TextAreas
	private void reset() {
		nurseNotesTextArea.setText(IConstants.emptyString);
		labRemarksTextArea.setText(IConstants.emptyString);
		editableNurseNotesTextArea.setText(IConstants.emptyString);
		editableLabRemarksTextArea.setText(IConstants.emptyString);
	}

	// print Nurse Station page
	@FXML
	private void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);
		// create list for HearderText and TextArea Text
		List<String> labelAndTextList = new ArrayList<>();
		;
		// VBox for allCenterNodes
		List<Node> allCenterNodes = null;

		// NURSE notes for current date and previous dates
		String currentDateNurseNotes = editableNurseNotesTextArea.getText();
		String nurseNotes = nurseNotesTextArea.getText();
		if (!StringUtils.isEmpty(nurseNotes)) {
			labelAndTextList.add("NURSES NOTES");
			labelAndTextList.add(nurseNotes + (!StringUtils.isEmpty(currentDateNurseNotes)
					? currentDateAsString + "\n" + currentDateNurseNotes : IConstants.emptyString));
		} else if (!StringUtils.isEmpty(currentDateNurseNotes)) {
			labelAndTextList.add("NURSES NOTES");
			labelAndTextList.add(currentDateAsString + "\n" + currentDateNurseNotes);
		}

		// LAB REMARKS notes for current date and previous dates
		String currentDateLabRemarks = editableLabRemarksTextArea.getText();
		String labRemarks = labRemarksTextArea.getText();
		if (!StringUtils.isEmpty(labRemarks)) {
			labelAndTextList.add("REMARKS");
			labelAndTextList.add(labRemarks + (!StringUtils.isEmpty(currentDateLabRemarks)
					? currentDateAsString + "\n" + currentDateLabRemarks : IConstants.emptyString));
		} else if (!StringUtils.isEmpty(currentDateLabRemarks)) {
			labelAndTextList.add("REMARKS");
			labelAndTextList.add(currentDateAsString + "\n" + currentDateLabRemarks);

		}
		allCenterNodes = printTemplate.createHeaderAndTextArea(labelAndTextList, pageLayout,
				printTemplate.remarksHeight);
		int page = 1;
		if (null != allCenterNodes && allCenterNodes.size() > 0) {
			if (printerJob.showPrintDialog(mainApp.getPrimaryStage())) {
				for (Node node : allCenterNodes) {
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

	// Complete BorderPage for printing
	private BorderPane createPrintPage(Node node, int page, PageLayout pageLayout) {
		BorderPane root = new BorderPane();
		root.setPrefHeight(pageLayout.getPrintableHeight());
		root.setPrefWidth(pageLayout.getPrintableWidth());

		// Setting the Title header at top of Border Pane
		HBox headerHbox = printTemplate.createHeader(mainPageTitle, iconURL, IConstants.emptyString, pageLayout);
		root.setTop(headerHbox);
		// Setting the Page Content(Common Section,Remarks header and text) at
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

	public Label getAdministratorWarningLabel() {
		return administratorWarningLabel;
	}

	public void setAdministratorWarningLabel(Label administratorWarningLabel) {
		this.administratorWarningLabel = administratorWarningLabel;
	}

}
