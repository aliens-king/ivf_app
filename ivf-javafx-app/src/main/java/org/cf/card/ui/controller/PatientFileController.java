package org.cf.card.ui.controller;

import static java.util.stream.Collectors.toList;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.Treatment;
import org.cf.card.model.User;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.components.DatePickerConverter;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.model.UIClient;
import org.cf.card.ui.model.UIPrintClient;
import org.cf.card.ui.model.UITreatment;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.ui.service.UITreatmentService;
import org.cf.card.ui.service.printing.PDFPrinter;
import org.cf.card.ui.service.printing.templates.FMDish;
import org.cf.card.ui.service.printing.templates.SpecialPot;
import org.cf.card.ui.service.printing.templates.Straw;
import org.cf.card.ui.service.printing.templates.braceletTemplate;
import org.cf.card.ui.service.printing.templates.fileTemplate;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;
import org.cf.card.ui.util.FileUtils;
import org.cf.card.ui.validation.PatientValidator;
import org.cf.card.util.EnumCycleType.CycleType;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Dell on 5/10/2015.
 */
public class PatientFileController {
	Logger log = Logger.getLogger(PatientFileController.class);
	PatientValidator patientValidator = new PatientValidator();

	// private int treatmentDays = 11;

	@FXML
	private javafx.scene.control.Button closeButton;
	@FXML
	private BorderPane patientFileBorderPane;
	@FXML
	private TextField womanSurnameTextField;
	@FXML
	private TextField womanFirstNameTextField;
	@FXML
	private TextField womanMiddleNameTextField;
	/*
	 * @FXML private TextField womanDateOfBirthTextField;
	 */
	@FXML
	private TextField womanAgeTextField;
	@FXML
	private TextField partnerSurnameTextField;
	@FXML
	private TextField partnerFirstNameTextField;
	@FXML
	private TextField partnerMiddleNameTextField;
	@FXML
	private TextField partnerAgeTextField;
	@FXML
	private ImageView clipartLabel;
	@FXML
	private TableView<UITreatment> table;
	@FXML
	private TableColumn<UITreatment, String> scheduleColumn;
	@FXML
	private TableColumn<UITreatment, String> nameColumn;
	@FXML
	private TableColumn<UITreatment, String> codesColumn;
	@FXML
	private TableColumn<UITreatment, String> endDateColumn;

	@FXML
	private DatePicker newStartDate;
	@FXML
	private CheckBox allIvfCheckBox;
	@FXML
	private CheckBox allMedicalCheckBox;
	@FXML
	private CheckBox allIvfFileCheckBox;
	@FXML
	private CheckBox icsiCheckBox;
	@FXML
	private CheckBox tubesCheckBox;
	@FXML
	private CheckBox braceletCheckBox;
	@FXML
	private CheckBox civfCheckBox;
	@FXML
	private CheckBox fmCheckBox;
	@FXML
	private CheckBox opuCheckbox;
	@FXML
	private CheckBox denutationCheckbox;
	@FXML
	private CheckBox cmCheckbox;
	@FXML
	private CheckBox biopsyCheckBox;
	@FXML
	private CheckBox strawSCheckBox;
	@FXML
	private CheckBox bmCheckBox;
	@FXML
	private CheckBox iuiCheckBox;
	@FXML
	private CheckBox strawECheckBox;
	@FXML
	private CheckBox semenPotCheckBox;
	@FXML
	private CheckBox vialsCheckBox;
	@FXML
	private CheckBox allMedicalFileCheckbox;
	@FXML
	private CheckBox potCheckbox;
	@FXML
	private CheckBox strawCheckBox;
	@FXML
	private CheckBox biopsyMedicalCheckBox;
	@FXML
	private CheckBox specialPotCheckBox;
	@FXML
	private Spinner<Integer> spinnerBox;
	@FXML
	private Text advertismentText;
	@FXML
	private Text loginText;
	@FXML
	private Label administratorWarningLabel;
	// Added new fields Home Address, Phone Number, Email
	@FXML
	private TextField womanHomeAddressTextField;
	@FXML
	private TextField womanPhoneNumberTextField;
	@FXML
	private TextField womanEmailTextField;
	@FXML
	private TextField partnerHomeAddressTextField;
	@FXML
	private TextField partnerPhoneNumberTextField;
	@FXML
	private TextField partnerEmailTextField;

	@FXML
	private Text manSurnameErrorLabel;
	@FXML
	private Text manMiddleNameErrorLabel;
	@FXML
	private Text manFirstNameErrorLabel;
	@FXML
	private Button womanClearButton;
	@FXML
	private Text womanSurnameErrorLabel;
	@FXML
	private Text womanMiddleNameErrorLabel;
	@FXML
	private Text womanFirstNameErrorLabel;
	@FXML
	private Text womanPhoneNumberErrorLabel;
	@FXML
	private Text womanEmailErrorLabel;
	@FXML
	private Text womanDOBErrorLabel;
	@FXML
	private Text manPhoneNumberErrorLabel;
	@FXML
	private Text manEmailErrorLabel;

	@FXML
	private DatePicker womanDOBDatePicker;
	@FXML
	private DatePicker partnerDOBDatePicker;
	@FXML
	private Text manDOBErrorLabel;

	private Date treatmentEndDate;

	private Codes manCode;
	private Codes womanCode;
	private Couple couple;
	private ObservableList<UITreatment> data;
	private UICoupleService uiCoupleService = new UICoupleService();
	private UITreatmentService treatmentService = new UITreatmentService();
	private User login;

	private MainApp mainApp;
	private UIClipartService clipartService = new UIClipartService();

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public Codes getManCode() {
		return manCode;
	}

	public void setManCode(Codes manCode) {
		this.manCode = manCode;
	}

	public Codes getWomanCode() {
		return womanCode;
	}

	public void setWomanCode(Codes womanCode) {
		this.womanCode = womanCode;
	}

	public void minimizeAction(ActionEvent actionEvent) {
		mainApp.getPatientFileStage().setIconified(true);
	}

	@FXML
	private CommonDetailController commonDetailController;

	class Delta {
		double x, y;
	}

	@FXML
	void initialize() {
		final Delta dragDelta = new Delta();
		patientFileBorderPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = mainApp.getPatientFileStage().getX() - mouseEvent.getScreenX();
				dragDelta.y = mainApp.getPatientFileStage().getY() - mouseEvent.getScreenY();
			}
		});
		patientFileBorderPane.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				mainApp.getPatientFileStage().setX(mouseEvent.getScreenX() + dragDelta.x);
				mainApp.getPatientFileStage().setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});

		partnerSurnameTextField.setEditable(false);
		partnerMiddleNameTextField.setEditable(false);
		partnerFirstNameTextField.setEditable(false);
		partnerDOBDatePicker.setEditable(false);
		partnerAgeTextField.setEditable(false);
		partnerHomeAddressTextField.setEditable(false);
		partnerPhoneNumberTextField.setEditable(false);
		partnerEmailTextField.setEditable(false);
		womanSurnameTextField.setEditable(false);
		womanMiddleNameTextField.setEditable(false);
		womanFirstNameTextField.setEditable(false);
		womanDOBDatePicker.setEditable(false);
		womanAgeTextField.setEditable(false);
		womanHomeAddressTextField.setEditable(false);
		womanPhoneNumberTextField.setEditable(false);
		womanEmailTextField.setEditable(false);

		spinnerBox.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, Integer.parseInt(String.valueOf(1))));
		spinnerBox.getEditor().setFont(new Font("Open Sans", 30));
		spinnerBox.getEditor().setAlignment(Pos.CENTER);

		EventHandler<KeyEvent> enterKeyEventHandler;

		enterKeyEventHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				// handle users "enter key event"

				try {
					// yes, using exception for control is a bad solution ;-)
					Integer.parseInt(spinnerBox.getEditor().textProperty().get());
				} catch (NumberFormatException e) {

					spinnerBox.getEditor().setText(spinnerBox.getEditor().getText().replaceAll("\\D+", ""));
				}

			}
		};
		spinnerBox.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, enterKeyEventHandler);
		spinnerBox.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, enterKeyEventHandler);
		spinnerBox.getEditor().addEventHandler(KeyEvent.KEY_TYPED, enterKeyEventHandler);
		spinnerBox.setEditable(true);
		scheduleColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		codesColumn.setCellValueFactory(cellData -> cellData.getValue().codesProperty());
		// endDateColumn.xFactory(cellData ->
		// cellData.getValue().endDateProperty());
		// EmbryologyAction(null);// initialize EMbryology Controller with a
		// Treatment

		// formating dates as per our format

		newStartDate.setConverter(new DatePickerConverter());
		womanDOBDatePicker.setConverter(new DatePickerConverter());
		partnerDOBDatePicker.setConverter(new DatePickerConverter());

	}

	public void buildData() {
		data = FXCollections.observableArrayList();
		List<Codes> coupleCodes = uiCoupleService.getCoupleById(couple.getId()).getMan().getCodes();
		coupleCodes.addAll(uiCoupleService.getCoupleById(couple.getId()).getWoman().getCodes());
		Iterable<Codes> iterable = coupleCodes;
		Iterator<Codes> iterator = iterable.iterator();
		List<Treatment> treatments = new ArrayList<>();

		while (iterator.hasNext()) {
			treatments.add(iterator.next().getTreatment());
		}
		java.util.List<UITreatment> clients = treatments.stream().map(UITreatment::new).collect(toList());

		try {
			data.addAll(clients);
			table.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
		loginText.setText(login.getSurname());

	}

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple){
		this.couple = couple;
		try {
			partnerSurnameTextField.setText(couple.getMan().getSurname());
			partnerMiddleNameTextField.setText(couple.getMan().getMiddleName());
			partnerFirstNameTextField.setText(couple.getMan().getFirstName());
			// partnerDateOfBirthTextField.setText(new SimpleDateFormat("EEE
			// dd/MM/yyyy").format(couple.getMan().getdOB()));
			Date mDate = couple.getMan().getdOB();
			LocalDate manDate = mDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			partnerDOBDatePicker.setValue(manDate);
			partnerHomeAddressTextField.setText(couple.getMan().getHomeAddress());
			partnerPhoneNumberTextField.setText(couple.getMan().getPhoneNumber());
			partnerEmailTextField.setText(couple.getMan().getEmail());
			UIClient partner = new UIClient(couple.getMan());
			partnerAgeTextField.setText(partner.getAge());
			/* Woman Personal Info */
			womanSurnameTextField.setText(couple.getWoman().getSurname());
			womanMiddleNameTextField.setText(couple.getWoman().getMiddleName());
			womanFirstNameTextField.setText(couple.getWoman().getFirstName());
			Date wDate = couple.getWoman().getdOB();
			LocalDate womanDate = wDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			womanDOBDatePicker.setValue(womanDate);
			// womanDateOfBirthTextField.setText(new SimpleDateFormat("EEE
			// dd/MM/yyyy").format(couple.getWoman().getdOB()));
			womanHomeAddressTextField.setText(couple.getWoman().getHomeAddress());
			womanPhoneNumberTextField.setText(couple.getWoman().getPhoneNumber());
			womanEmailTextField.setText(couple.getWoman().getEmail());
			UIClient woman = new UIClient(couple.getWoman());
			womanAgeTextField.setText(woman.getAge());
			/* Include common file calling controller */
			/*
			 * commonDetailController.setMainApp(mainApp);
			 * commonDetailController.setCouple(couple);
			 * commonDetailController.setBackgroundColor(false);
			 */
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		try {

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		try {
			javafx.scene.image.Image image;

			File file = new File(clipartService.getImage(couple.getId()));

			image = new javafx.scene.image.Image(file.toURI().toURL().toString());

			clipartLabel.setImage(image);
			/// clipartLabel.setGraphic(new
			/// javafx.scene.image.ImageView(image));
			buildData();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handleCloseButton(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}

	public void logOutAction(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(MessageResource.getResourceBundle());
		loader.setLocation(MainApp.class.getResource("/view/LoginPage.fxml"));
		try {
			BorderPane personOverview = (BorderPane) loader.load();

			Scene scene = new Scene(personOverview);
			mainApp.getPrimaryStage().setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("/CSS/Login.css").toExternalForm());
			mainApp.getPrimaryStage().show();
			LoginController controller = loader.getController();
			controller.setMainApp(mainApp);
		} catch (IOException e) {

		}
	}

	public void directoryAction(ActionEvent actionEvent) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/DirectoryPage.fxml"));
		try {
			loader.setResources(MessageResource.getResourceBundle());
			BorderPane personOverview = (BorderPane) loader.load();
			Scene scene = new Scene(personOverview);
			mainApp.getPrimaryStage().setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("/CSS/Directory.css").toExternalForm());
			mainApp.getPrimaryStage().show();
			DirectoryPageController controller = loader.getController();
			controller.setMainApp(mainApp);
			controller.setLogin(login);
		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void searchPatientsAction(ActionEvent actionEvent) {

	}

	public void addPatientsAction(ActionEvent actionEvent) {

	}

	public void patientsListAction(ActionEvent actionEvent) {

	}

	public void accountDetailsAction(ActionEvent actionEvent) {

	}

	public void addAccountsDetails(ActionEvent actionEvent) {

	}

	public void embryologyAction(ActionEvent actionEvent) {

		if (EnumPermission.canView(login.getRoleId(), Module.PATIENT_DETAILS.getKey())) {
			if (couple != null) {

				List<Codes> womanCodes = couple.getWoman().getCodes();
				if (womanCodes != null && womanCodes.size() > 0) {
					LoadPanels.loadModule(mainApp, login, Module.EMBRYOLOGY_OVERVIEW.getKey(), null);
					handleCloseButton(null);// close patient dialog
				} else {
					Notify notify = new Notify(AlertType.ERROR,
							MessageResource.getText("patientfile.error.schedule.treatment"));
					notify.showAndWait();
				}
			}
		} else {
			FileUtils.privillegeError();
		}

	}

	@FXML
	public void todayAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PATIENT_DETAILS.getKey()))
			newStartDate.setValue(LocalDate.now());
		else
			FileUtils.privillegeEditError();
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void scheduleAction(ActionEvent actionEvent) throws ParseException {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PATIENT_DETAILS.getKey())) {
			// Selected Date from DatePicker
			LocalDate date = newStartDate.getValue();
			Date selectedDate = null;
			Date lastDatefromOfSelection = null;
			TreatmentDto treatmentDto = null;

			if (date != null) {

				// This check is because IT Person can have write privileges in
				// Patient File page except schedule treatement
				if (login.getRoleId() != 2) {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IConstants.expectedPattern);
					String selectedDateAsString = date.format(formatter);
					selectedDate = Util.stringToDate(selectedDateAsString, IConstants.expectedPattern);

					/* get latest treatment by passing parameter coupleId */
					treatmentDto = treatmentService.getTreatmentsByCoupleId(couple.getId());
					if (treatmentDto != null) {
						// treatmentStartDate = treatmentDto.getStartDate();
						treatmentEndDate = treatmentDto.getEndDate();
					}

					if (treatmentEndDate != null
							&& (selectedDate.before(treatmentEndDate) || selectedDate.equals(treatmentEndDate))) {
						Notify notify = new Notify(AlertType.WARNING);
						notify.setContentText("Treatment already in progress. Can't schedule a new\ntreatment.");
						notify.show();
					} else {

						// Getting the last Date object
						lastDatefromOfSelection = Util.nextDate(selectedDate, 10);
						womanCode = treatmentService.saveTreatment(selectedDate, couple.getWoman(),
								lastDatefromOfSelection, CycleType.NORMAL.getKey());
						manCode = treatmentService.saveTreatment(selectedDate, couple.getMan(), lastDatefromOfSelection,
								CycleType.NORMAL.getKey());
						couple = womanCode.getClient().getCouple();
						// updating couple and codes as per new treatment
						// schedule
						SessionObject<String, Long> sessionObject = Session.getInstance().getSessionObject();
						sessionObject.setComponent(Constants.COUPLE_SEESION_KEY, couple.getId());
						sessionObject.setComponent(Constants.IS_CURRUNT_CYCLE, 1l);
						sessionObject.setComponent(Constants.WOMANCODE_SESSION_KEY, womanCode.getId());
						sessionObject.setComponent(Constants.MANCODE_SESSION_KEY, manCode.getId());
						buildData();
						advertismentText.setText("");
					}
				} else {
					FileUtils.privillegeEditError();
				}

			} else {
				Notify notify = new Notify(AlertType.WARNING);
				notify.setContentText(MessageResource.getText("patientfile.warning.select.date"));
				notify.show();
			}
		} else
			FileUtils.privillegeEditError();
	}

	public void editAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PATIENT_DETAILS.getKey())) {

			setEditable(true);
			partnerAgeTextField.setEditable(false);
			womanAgeTextField.setEditable(false);
			womanSurnameTextField.requestFocus();
			administratorWarningLabel.setText("");
		} else {
			// administratorWarningLabel.setText("You are not an
			// administrator!");
			FileUtils.privillegeEditError();
		}
	}

	public void saveAction(ActionEvent actionEvent) {
		if (EnumPermission.canWrite(login.getRoleId(), Module.PATIENT_DETAILS.getKey())) {
			int ok = 1;
			ok = patientValidator.validate(partnerSurnameTextField, partnerMiddleNameTextField,
					partnerFirstNameTextField, womanSurnameTextField, womanMiddleNameTextField, womanFirstNameTextField,
					womanHomeAddressTextField, womanPhoneNumberTextField, womanEmailTextField,
					partnerHomeAddressTextField, partnerPhoneNumberTextField, partnerEmailTextField, womanDOBDatePicker,
					partnerDOBDatePicker, womanSurnameErrorLabel, womanFirstNameErrorLabel, manSurnameErrorLabel,
					manFirstNameErrorLabel, womanPhoneNumberErrorLabel, womanEmailErrorLabel, manPhoneNumberErrorLabel,
					manEmailErrorLabel, womanDOBErrorLabel, manDOBErrorLabel);
			if (ok == 1) {
				couple.getWoman().setSurname(womanSurnameTextField.getText());
				couple.getWoman().setFirstName(womanFirstNameTextField.getText());
				couple.getWoman().setMiddleName(womanMiddleNameTextField.getText());
				couple.getWoman().setHomeAddress(womanHomeAddressTextField.getText());
				couple.getWoman().setPhoneNumber(womanPhoneNumberTextField.getText());
				couple.getWoman().setEmail(womanEmailTextField.getText());
				java.util.Date womanDOB = java.util.Date
						.from(womanDOBDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				couple.getWoman().setdOB(womanDOB);

				couple.getMan().setSurname(partnerSurnameTextField.getText());
				couple.getMan().setFirstName(partnerFirstNameTextField.getText());
				couple.getMan().setMiddleName(partnerMiddleNameTextField.getText());
				couple.getMan().setHomeAddress(partnerHomeAddressTextField.getText());
				couple.getMan().setPhoneNumber(partnerPhoneNumberTextField.getText());
				couple.getMan().setEmail(partnerEmailTextField.getText());
				java.util.Date manDOB = java.util.Date
						.from(partnerDOBDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				couple.getMan().setdOB(manDOB);

				couple = uiCoupleService.addCouple(couple);
				setEditable(false);
				womanAgeTextField.setText(Util.getAge(womanDOB));
				partnerAgeTextField.setText(Util.getAge(manDOB));
			}
		} else {
			// administratorWarningLabel.setText("You are not an
			// administrator!");
			FileUtils.privillegeEditError();
		}
	}

	public void allIvfAction(ActionEvent actionEvent) {

		allIvfFileCheckBox.setSelected(allIvfCheckBox.isSelected());
		braceletCheckBox.setSelected(allIvfCheckBox.isSelected());
		opuCheckbox.setSelected(allIvfCheckBox.isSelected());
		biopsyCheckBox.setSelected(allIvfCheckBox.isSelected());
		iuiCheckBox.setSelected(allIvfCheckBox.isSelected());
		icsiCheckBox.setSelected(allIvfCheckBox.isSelected());
		civfCheckBox.setSelected(allIvfCheckBox.isSelected());
		denutationCheckbox.setSelected(allIvfCheckBox.isSelected());
		strawECheckBox.setSelected(allIvfCheckBox.isSelected());
		strawSCheckBox.setSelected(allIvfCheckBox.isSelected());
		vialsCheckBox.setSelected(allIvfCheckBox.isSelected());
		tubesCheckBox.setSelected(allIvfCheckBox.isSelected());
		fmCheckBox.setSelected(allIvfCheckBox.isSelected());
		cmCheckbox.setSelected(allIvfCheckBox.isSelected());
		bmCheckBox.setSelected(allIvfCheckBox.isSelected());
		semenPotCheckBox.setSelected(allIvfCheckBox.isSelected());

	}

	private void setEditable(boolean editable){

		womanSurnameTextField.setEditable(editable);
		womanFirstNameTextField.setEditable(editable);
		womanMiddleNameTextField.setEditable(editable);
		womanDOBDatePicker.setEditable(editable);
		womanHomeAddressTextField.setEditable(editable);
		womanPhoneNumberTextField.setEditable(editable);
		womanEmailTextField.setEditable(editable);


		partnerSurnameTextField.setEditable(editable);
		partnerFirstNameTextField.setEditable(editable);
		partnerMiddleNameTextField.setEditable(editable);
		partnerDOBDatePicker.setEditable(editable);
		partnerHomeAddressTextField.setEditable(editable);
		partnerPhoneNumberTextField.setEditable(editable);
		partnerEmailTextField.setEditable(editable);
	}

	public void allMedicalAction(ActionEvent actionEvent) {

		allMedicalFileCheckbox.setSelected(allMedicalCheckBox.isSelected());
		potCheckbox.setSelected(allMedicalCheckBox.isSelected());
		strawCheckBox.setSelected(allMedicalCheckBox.isSelected());
		biopsyMedicalCheckBox.setSelected(allMedicalCheckBox.isSelected());
		specialPotCheckBox.setSelected(allMedicalCheckBox.isSelected());
	}

	public void printAction(ActionEvent actionEvent) {
		List<Component> printComponents = new ArrayList<>();
		UIPrintClient printClient = new UIPrintClient(couple.getWoman(), couple.getMan());
		Codes woman = new Codes();
		Codes partner = new Codes();

		woman = womanCode;
		partner = manCode;
		try {
			try {
				woman.equals(partner);
				printClient.setMainClientTreatmentCode(woman);
			} catch (NullPointerException e) {
				printClient.setMainClientTreatmentCode(
						couple.getWoman().getCodes().get(couple.getWoman().getCodes().size() - 1));
			}
			try {
				partner.equals(woman);
				printClient.setPartnerTreatmentCode(partner);
			} catch (NullPointerException e) {
				printClient
						.setPartnerTreatmentCode(couple.getMan().getCodes().get(couple.getMan().getCodes().size() - 1));
			}
			for (int i = 0; i < Integer.parseInt(spinnerBox.getEditor().getText()); i++) {
				if (allIvfFileCheckBox.isSelected()) {
					printComponents.add(new fileTemplate(printClient));
				}
				if (semenPotCheckBox.isSelected()) {
					printComponents.add(new fileTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
					printComponents.add(new braceletTemplate(printClient));
				}
				if (opuCheckbox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true)); // mirrored
				}
				if (biopsyCheckBox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (icsiCheckBox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (civfCheckBox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (denutationCheckbox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (cmCheckbox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (bmCheckBox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (fmCheckBox.isSelected()) {
					printComponents.add(new FMDish(printClient));
					printComponents.add(new FMDish(printClient, true));
				}
				if (iuiCheckBox.isSelected()) {
					printComponents.add(new braceletTemplate(printClient));
				}
				if (braceletCheckBox.isSelected()) {
					printComponents.add(new braceletTemplate(printClient));
				}
				if (tubesCheckBox.isSelected()) {
					printComponents.add(new braceletTemplate(printClient));
				}
				if (vialsCheckBox.isSelected()) {
					printComponents.add(new braceletTemplate(printClient));
				}
				if (strawSCheckBox.isSelected()) {
					printComponents.add(new Straw(printClient, "man"));
				}
				if (strawECheckBox.isSelected()) {
					printComponents.add(new Straw(printClient, "woman"));
				}
				if (allMedicalFileCheckbox.isSelected()) {
					printComponents.add(new fileTemplate(printClient));
				}
				if (potCheckbox.isSelected()) {
					printComponents.add(new fileTemplate(printClient));
				}
				if (strawCheckBox.isSelected()) {
					printComponents.add(new Straw(printClient, "woman"));
				}
				if (tubesCheckBox.isSelected()) {
					printComponents.add(new braceletTemplate(printClient));
				}
				if (specialPotCheckBox.isSelected()) {
					printComponents.add(new SpecialPot(printClient));
				}
			}

			new PDFPrinter(printComponents);
		} catch (ArrayIndexOutOfBoundsException r) {
			advertismentText.setText(MessageResource.getText("patientfile.error.schedule.treatment"));
		}
	}

}