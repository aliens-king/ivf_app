package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.DoctorNurseDto;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.print.templates.PrintTemplate;
import org.cf.card.ui.service.UIDoctorOfficeService;
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

public class DoctorOfficeController extends BaseController {

	// getting all data(text) from message.property file
	private static final String mainPageTitle = MessageResource.getText("mainpage.title.doctoroffice");
	private static final String noDataAvailableMessage = MessageResource.getText("print.error.message");
	private static final String iconURL = "/icons/doctor_office.png";
	private static final String DOError = MessageResource.getText("common.empty.fields.error.message");

	// creating object
	UIDoctorOfficeService uiDoctorOfficeService = new UIDoctorOfficeService();
	PrintTemplate<?> printTemplate = new PrintTemplate<>();

	// binding FXML element
	@FXML
	private TextArea labPointsTextArea;

	@FXML
	private TextArea medicalHistoryTextArea;

	@FXML
	private TextArea remarksTextArea;

	@FXML
	private TextArea editableLabPointsTextArea;

	@FXML
	private TextArea editableMedicalHistoryTextArea;

	@FXML
	private TextArea editableRemarksTextArea;

	@FXML
	private CommonDetailController commonDetailController;

	@FXML
	private InvestigationCommonController investigationCommonController;

	private Date currentDate = null;
	private String currentDateAsString = null;
	// creating instance variables(class level)
	/** The administrator warning label. */
	private Label administratorWarningLabel;
	private DoctorNurseDto todayDoctorNurseDto = null;

	@FXML
	private void initialize() {
		labPointsTextArea.setWrapText(true);
		medicalHistoryTextArea.setWrapText(true);
		labPointsTextArea.setWrapText(true);
		remarksTextArea.setWrapText(true);
		editableMedicalHistoryTextArea.setWrapText(true);
		editableLabPointsTextArea.setWrapText(true);
		editableRemarksTextArea.setWrapText(true);
		makeFieldsNonEditable();
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

		if (null != couple) {

			// fetching values from DB to display to the user
			List<DoctorNurseDto> doctorNurseDtoList = uiDoctorOfficeService.getDoctorOfficeByCoupleId(couple.getId());
			if (null != doctorNurseDtoList && !doctorNurseDtoList.isEmpty()) {
				StringBuilder medicalHistoryStb = new StringBuilder();
				StringBuilder labPointsStb = new StringBuilder();
				StringBuilder remarksStb = new StringBuilder();
				currentDate = new Date();
				for (DoctorNurseDto doctorNurseDto : doctorNurseDtoList) {
					currentDateAsString = Util.formatDate(IConstants.DATE_FORMAT, currentDate);
					String sCreatedDate = Util.formatDate(IConstants.DATE_FORMAT, doctorNurseDto.getCreatedDate());
					if (currentDateAsString.compareTo(sCreatedDate) == 0) {
						todayDoctorNurseDto = doctorNurseDto;
						editableMedicalHistoryTextArea.setText(createTextAreaValue(doctorNurseDto.getMedicalHistory()));
						editableLabPointsTextArea.setText(createTextAreaValue(doctorNurseDto.getLabPoints()));
						editableRemarksTextArea.setText(createTextAreaValue(doctorNurseDto.getRemarks()));
					} else {
						medicalHistoryStb.append(
								sCreatedDate + "\n" + createTextAreaValue(doctorNurseDto.getMedicalHistory()) + "\n");
						labPointsStb.append(
								sCreatedDate + "\n" + createTextAreaValue(doctorNurseDto.getLabPoints()) + "\n");
						remarksStb
								.append(sCreatedDate + "\n" + createTextAreaValue(doctorNurseDto.getRemarks()) + "\n");
						todayDoctorNurseDto = null;
					}
				}
				medicalHistoryTextArea.setText(medicalHistoryStb.toString());
				labPointsTextArea.setText(labPointsStb.toString());
				remarksTextArea.setText(remarksStb.toString());
			} else {
				todayDoctorNurseDto = null;
			}
		}
		makeFieldsNonEditable();
	}

	private String createTextAreaValue(String textValue) {
		return textValue != null ? textValue : IConstants.emptyString;
	}

	private void reset() {
		labPointsTextArea.setText(IConstants.emptyString);
		medicalHistoryTextArea.setText(IConstants.emptyString);
		remarksTextArea.setText(IConstants.emptyString);
		editableLabPointsTextArea.setText(IConstants.emptyString);
		editableMedicalHistoryTextArea.setText(IConstants.emptyString);
		editableRemarksTextArea.setText(IConstants.emptyString);
	}

	public void makeFieldsNonEditable() {
		labPointsTextArea.setEditable(false);
		medicalHistoryTextArea.setEditable(false);
		remarksTextArea.setEditable(false);
		editableMedicalHistoryTextArea.setEditable(false);
		editableLabPointsTextArea.setEditable(false);
		editableRemarksTextArea.setEditable(false);
	}

	public void makeFieldsEditable() {
		editableMedicalHistoryTextArea.setEditable(true);
		editableLabPointsTextArea.setEditable(true);
		editableRemarksTextArea.setEditable(true);
	}

	@FXML
	private void editDoctorOfficeAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.DOCTORS_OFFICE.getKey()))
			makeFieldsEditable();
		else
			FileUtils.privillegeEditError();
	}

	@FXML
	private void saveDoctorOfficeAction() {
		if (EnumPermission.canWrite(login.getRoleId(), Module.DOCTORS_OFFICE.getKey())) {

			// Now, saving DO data in doctor_office table instead of
			// patient_investigation table
			boolean result = validateFields();
			if (result == true) {
				if (null != couple) {
					if (todayDoctorNurseDto == null)
						todayDoctorNurseDto = new DoctorNurseDto();
					currentDate = new Date();
					todayDoctorNurseDto.setCoupleId(couple.getId());

					String editableMedicalHistory = editableMedicalHistoryTextArea.getText();
					if (!StringUtils.isEmpty(editableMedicalHistory))
						todayDoctorNurseDto.setMedicalHistory(editableMedicalHistory);

					String editableLabPoints = editableLabPointsTextArea.getText();
					if (!StringUtils.isEmpty(editableLabPoints))
						todayDoctorNurseDto.setLabPoints(editableLabPoints);

					String editableRemarks = editableRemarksTextArea.getText();
					if (!StringUtils.isEmpty(editableRemarks))
						todayDoctorNurseDto.setRemarks(editableRemarks);

					todayDoctorNurseDto.setCreatedDate(currentDate);
					todayDoctorNurseDto = uiDoctorOfficeService.saveDoctorOffice(todayDoctorNurseDto);
				}
				// updating current couple object
				// couple = coupleService.getCoupleById(couple.getId());
				buildData();
				makeFieldsNonEditable();
			} else {
				Notify alert = new Notify(AlertType.ERROR, DOError);
				alert.showAndWait();
			}
		} else
			FileUtils.privillegeEditError();
	}

	private boolean validateFields() {
		boolean valid = true;
		if (editableMedicalHistoryTextArea.getText().equals(IConstants.emptyString)
				&& editableLabPointsTextArea.getText().equals(IConstants.emptyString)
				&& editableRemarksTextArea.getText().equals(IConstants.emptyString))
			valid = false;
		return valid;
	}

	// print Doctor Office page
	@FXML
	private void printAction() {

		PrinterJob printerJob = PrinterJob.createPrinterJob();
		PageLayout pageLayout = printTemplate.printPageLayout(printerJob);

		// create list for text(RemarksHeader + RemarksText)
		List<String> labelAndTextList = new ArrayList<>();
		// VBox for allCenterNodes
		List<Node> allCenterNodes = null;

		// MEDICAL HISTORY notes for current date and previous dates
		String currenDateMedcalHistory = editableMedicalHistoryTextArea.getText();
		String medicalHistory = medicalHistoryTextArea.getText();
		if (!StringUtils.isEmpty(medicalHistory)) {
			labelAndTextList.add("MEDICAL HISTORY");
			labelAndTextList.add(medicalHistory + (!StringUtils.isEmpty(currenDateMedcalHistory)
					? currentDateAsString + "\n" + currenDateMedcalHistory : IConstants.emptyString));
		} else if (!StringUtils.isEmpty(currenDateMedcalHistory)) {
			labelAndTextList.add("MEDICAL HISTORY");
			labelAndTextList.add(currentDateAsString + "\n" + currenDateMedcalHistory);
		}

		// LAB POINTS notes for current date and previous dates
		String currentDateLabPoints = editableLabPointsTextArea.getText();
		String labPoints = labPointsTextArea.getText();
		if (!StringUtils.isEmpty(labPoints)) {
			labelAndTextList.add("POINT FOR LAB");
			labelAndTextList.add(labPoints + (!StringUtils.isEmpty(currentDateLabPoints)
					? currentDateAsString + "\n" + currentDateLabPoints : IConstants.emptyString));
		} else if(!StringUtils.isEmpty(currentDateLabPoints)){
			labelAndTextList.add("POINT FOR LAB");
			labelAndTextList.add(currentDateAsString + "\n" + currentDateLabPoints);
		}

		// REMARKS notes for current date and previous dates
		String currentDateRemarks = editableRemarksTextArea.getText();
		String remarks = remarksTextArea.getText();
		if (!StringUtils.isEmpty(remarksTextArea.getText())) {
			labelAndTextList.add("REMARKS");
			labelAndTextList.add(remarks + (!StringUtils.isEmpty(currentDateRemarks)
					? currentDateAsString + "\n" + currentDateRemarks : IConstants.emptyString));
		} else if(!StringUtils.isEmpty(currentDateRemarks)) {
			labelAndTextList.add("REMARKS");
			labelAndTextList.add(currentDateAsString + "\n" + currentDateRemarks);

		}
		allCenterNodes = printTemplate.createHeaderAndTextArea(labelAndTextList, pageLayout,
				printTemplate.remarksHeight);

		// }
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
		// Setting the Page Content(Common Section, Remarks data) at center
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
