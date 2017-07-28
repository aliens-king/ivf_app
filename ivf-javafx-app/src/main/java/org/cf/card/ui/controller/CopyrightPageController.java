package org.cf.card.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.cf.card.dto.CompanyDto;
import org.cf.card.ui.MainApp;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.service.LoadPanels;
import org.cf.card.ui.service.UIClipartService;
import org.cf.card.ui.service.UIComapnyService;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.cf.card.ui.util.Constants;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Created by Dell on 5/3/2015.
 */
public class CopyrightPageController {

	@FXML
	private BorderPane borderPane;
	@FXML
	private Label companyNameLabel;
	@FXML
	private ImageView companyLogoImageView;

	UIComapnyService comapnyService = new UIComapnyService();
	UIClipartService clipartService = new UIClipartService();
	private CompanyDto companyDto = null;
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

		buildData();

		ResourceBundle bundle = MessageResource.getResourceBundle();
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() {

				LoadPanels.getTableLoader().setLocation(MainApp.class.getResource("/view/TablePane.fxml"));
				LoadPanels.getAddPatientsLoader().setLocation(MainApp.class.getResource("/view/AddPatients.fxml"));
				LoadPanels.getPatientListLoader().setLocation(MainApp.class.getResource("/view/PrintListPage.fxml"));
				LoadPanels.getDetailsLoader().setLocation(MainApp.class.getResource("/view/AccountDetails.fxml"));
				LoadPanels.getAddAccountsLoader().setLocation(MainApp.class.getResource("/view/AddAccounts.fxml"));
				LoadPanels.getLoginLoader().setLocation(MainApp.class.getResource("/view/LoginPage.fxml"));
				LoadPanels.getDirectoryLoader().setLocation(MainApp.class.getResource("/view/DirectoryPage.fxml"));
				LoadPanels.getEmbryologyPageLoader().setLocation(MainApp.class.getResource("/view/Embryology.fxml"));
				LoadPanels.getStandardInvestigationLoader()
						.setLocation(MainApp.class.getResource("/view/StandardInvestigation.fxml"));
				LoadPanels.getCryopreservationEmbryoLoader()
						.setLocation(MainApp.class.getResource("/view/CryopreservationEmbryo.fxml"));
				LoadPanels.getCryopreservationSemenLoader()
						.setLocation(MainApp.class.getResource("/view/CryopreservationSemen.fxml"));
				LoadPanels.getEmbryologySemenPreparationLoader()
						.setLocation(MainApp.class.getResource("/view/SemenPreparation.fxml"));
				LoadPanels.getAndrologySemenLoader()
						.setLocation(MainApp.class.getResource("/view/AndrologySemen.fxml"));
				LoadPanels.getEmbrylogyTransferLoader()
						.setLocation(MainApp.class.getResource("/view/EmbryologyTransfer.fxml"));
				LoadPanels.getEmbryologyThawingLoader()
						.setLocation(MainApp.class.getResource("/view/EmbryologyThawing.fxml"));
				LoadPanels.getNurseStationLoader().setLocation(MainApp.class.getResource("/view/NurseStation.fxml"));
				LoadPanels.getDoctorOfficeLoader().setLocation(MainApp.class.getResource("/view/DoctorOffice.fxml"));
				LoadPanels.getEtTableLoader().setLocation(MainApp.class.getResource("/view/EtTableOverview.fxml"));
				LoadPanels.getAppointmentsLoader()
						.setLocation(MainApp.class.getResource("/view/AppointmentOverview.fxml"));
				LoadPanels.getCyclesLoader().setLocation(MainApp.class.getResource("/view/Cycles.fxml"));
				LoadPanels.getAppointmentsScreenLoader()
						.setLocation(MainApp.class.getResource("/view/AppointmentScreen.fxml"));
				LoadPanels.getCompanyDetailsLoader()
						.setLocation(MainApp.class.getResource("/view/CompanyDetails.fxml"));
				LoadPanels.getArchiveDetailsLoader().setLocation(MainApp.class.getResource("/view/ArchiveScreen.fxml"));
				LoadPanels.getPregnancyOutcomesLoader()
						.setLocation(MainApp.class.getResource("/view/PregnancyOutcomes.fxml"));
				LoadPanels.getEtScreenLoader().setLocation(MainApp.class.getResource("/view/EtScreen.fxml"));
				
				//Billing Module 
				LoadPanels.getBillingSetupLoader().setLocation(MainApp.class.getResource("/view/BillingSetup.fxml"));
				LoadPanels.getBillingAndInvoiceLoader().setLocation(MainApp.class.getResource("/view/BillingAndInvoice.fxml"));
				LoadPanels.getPaymentDetailsLoader().setLocation(MainApp.class.getResource("/view/PaymentDetails.fxml"));
				LoadPanels.getInvoicesDetailLoader().setLocation(MainApp.class.getResource("/view/InvoicesDetail.fxml"));
				LoadPanels.getRefundAndInvoiceLoader().setLocation(MainApp.class.getResource("/view/RefundAndInvoice.fxml"));
				LoadPanels.getRefundBillPerCoupleLoader().setLocation(MainApp.class.getResource("/view/RefundBillPerCouple.fxml"));
				LoadPanels.getRefundBillOverallLoader().setLocation(MainApp.class.getResource("/view/RefundBillOverall.fxml"));
				LoadPanels.getUnpaidBillPerCoupleLoader().setLocation(MainApp.class.getResource("/view/UnpaidBillPerCouple.fxml"));
				LoadPanels.getUnpaidBillOverallLoader().setLocation(MainApp.class.getResource("/view/UnpaidBillOverall.fxml"));
				
				
				
				
				// LoadPanels.getCopyrightLoader().setLocation(MainApp.class.getResource("/view/CopyrightPage.fxml"));
				// LoadPanels.getPregnancyOutcomesLoader().setLocation(MainApp.class.getResource("/view/PregnancyOutcomes.fxml"));

				/* Add all the page before the main page */
				LoadPanels.getMainPageLoader().setLocation(MainApp.class.getResource("/view/MainPage.fxml"));
				LoadPanels.getMainPageLoader().setResources(bundle);
				LoadPanels.getAndrologySemenLoader().setResources(bundle);
				LoadPanels.getTableLoader().setResources(bundle);
				LoadPanels.getAddPatientsLoader().setResources(bundle);
				LoadPanels.getPatientListLoader().setResources(bundle);
				LoadPanels.getDetailsLoader().setResources(bundle);
				LoadPanels.getAddAccountsLoader().setResources(bundle);
				LoadPanels.getLoginLoader().setResources(bundle);
				LoadPanels.getDirectoryLoader().setResources(bundle);
				LoadPanels.getEmbryologyPageLoader().setResources(bundle);
				LoadPanels.getStandardInvestigationLoader().setResources(bundle);
				LoadPanels.getCryopreservationEmbryoLoader().setResources(bundle);
				LoadPanels.getCryopreservationSemenLoader().setResources(bundle);
				LoadPanels.getEmbryologySemenPreparationLoader().setResources(bundle);
				LoadPanels.getEmbrylogyTransferLoader().setResources(bundle);
				LoadPanels.getEmbryologyThawingLoader().setResources(bundle);
				LoadPanels.getNurseStationLoader().setResources(bundle);
				LoadPanels.getDoctorOfficeLoader().setResources(bundle);
				LoadPanels.getEtTableLoader().setResources(bundle);
				LoadPanels.getAppointmentsLoader().setResources(bundle);
				LoadPanels.getCyclesLoader().setResources(bundle);
				LoadPanels.getCopyrightLoader().setResources(bundle);
				LoadPanels.getSuccessfullPageLoader().setResources(bundle);
				LoadPanels.getAppointmentsScreenLoader().setResources(bundle);
				LoadPanels.getCompanyDetailsLoader().setResources(bundle);
				LoadPanels.getArchiveDetailsLoader().setResources(bundle);
				LoadPanels.getPregnancyOutcomesLoader().setResources(bundle);
				LoadPanels.getEtScreenLoader().setResources(bundle);
				
				//Billing Module 
				LoadPanels.getBillingSetupLoader().setResources(bundle);
				LoadPanels.getBillingAndInvoiceLoader().setResources(bundle);
				LoadPanels.getPaymentDetailsLoader().setResources(bundle);
				LoadPanels.getInvoicesDetailLoader().setResources(bundle);
				LoadPanels.getRefundAndInvoiceLoader().setResources(bundle);
				LoadPanels.getRefundBillPerCoupleLoader().setResources(bundle);
				LoadPanels.getRefundBillOverallLoader().setResources(bundle);
				LoadPanels.getUnpaidBillPerCoupleLoader().setResources(bundle);
				LoadPanels.getUnpaidBillOverallLoader().setResources(bundle);

				// LoadPanels.getPatientListEntityes().setResources(bundle);

				for (int i = 0; i < 70; i++) {
					LoadPanels.getPatientListEntityLoader().add(new FXMLLoader());
				}
				for (int i = 0; i < 70; i++) {
					LoadPanels.getPatientListEntityLoader().get(i)
							.setLocation(MainApp.class.getResource("/view/PrintListEntity.fxml"));
					LoadPanels.getPatientListEntityLoader().get(i).setResources(MessageResource.getResourceBundle());
				}
				for (int i = 0; i < 100; i++) {
					LoadPanels.getArchiveEntityLoader().add(new FXMLLoader());
				}
				for (int i = 0; i < 100; i++) {
					LoadPanels.getArchiveEntityLoader().get(i)
							.setLocation(MainApp.class.getResource("/view/RecentArchiveEntity.fxml"));
					LoadPanels.getArchiveEntityLoader().get(i).setResources(MessageResource.getResourceBundle());
				}

				for (int i = 0; i < 50; i++) {
					LoadPanels.getArchiveHBoxLoader().add(new FXMLLoader());
				}
				for (int i = 0; i < 50; i++) {
					LoadPanels.getArchiveHBoxLoader().get(i)
							.setLocation(MainApp.class.getResource("/view/RecentArchiveHbox.fxml"));
					LoadPanels.getArchiveHBoxLoader().get(i).setResources(MessageResource.getResourceBundle());
				}
				/*
				 * for (int i = 0; i < 70; i++) {
				 * LoadPanels.getEmbryologyEntityLoader().add(new FXMLLoader());
				 * }
				 */
				try {
					LoadPanels.tablePane = LoadPanels.getTableLoader().load();
					LoadPanels.addPatientsPane = LoadPanels.getAddPatientsLoader().load();
					LoadPanels.patientsListPane = LoadPanels.getPatientListLoader().load();
					LoadPanels.detailsPane = LoadPanels.getDetailsLoader().load();
					LoadPanels.addAccountsPane = LoadPanels.getAddAccountsLoader().load();
					LoadPanels.loginPage = LoadPanels.getLoginLoader().load();
					LoadPanels.directoryPage = LoadPanels.getDirectoryLoader().load();
					LoadPanels.embryologyPane = LoadPanels.getEmbryologyPageLoader().load();
					LoadPanels.standardInvestigationPane = LoadPanels.getStandardInvestigationLoader().load();
					LoadPanels.cryopreservationEmbryoPane = LoadPanels.getCryopreservationEmbryoLoader().load();
					LoadPanels.cryopreservationSemenPane = LoadPanels.getCryopreservationSemenLoader().load();
					LoadPanels.embryologySemenPreparationPane = LoadPanels.getEmbryologySemenPreparationLoader().load();
					LoadPanels.andryologySemenPane = LoadPanels.getAndrologySemenLoader().load();
					LoadPanels.embrylogyTransferPane = LoadPanels.getEmbrylogyTransferLoader().load();
					LoadPanels.nurseStationPane = LoadPanels.getNurseStationLoader().load();
					LoadPanels.embryologyThawingPane = LoadPanels.getEmbryologyThawingLoader().load();
					LoadPanels.doctorOfficePane = LoadPanels.getDoctorOfficeLoader().load();
					LoadPanels.etTablePane = LoadPanels.getEtTableLoader().load();
					LoadPanels.appointmentsPane = LoadPanels.getAppointmentsLoader().load();
					LoadPanels.cyclesPane = LoadPanels.getCyclesLoader().load();
					LoadPanels.appointmentsScreenPane = LoadPanels.getAppointmentsScreenLoader().load();
					LoadPanels.companyDetailsPane = LoadPanels.getCompanyDetailsLoader().load();
					LoadPanels.archivePane = LoadPanels.getArchiveDetailsLoader().load();
					LoadPanels.pregnancyOutcomesPane = LoadPanels.getPregnancyOutcomesLoader().load();
					LoadPanels.etScreenPane = LoadPanels.getEtScreenLoader().load();
					
					//Billing Module 
					LoadPanels.billingSetupPane = LoadPanels.getBillingSetupLoader().load();
					LoadPanels.billingAndInvoicePane = LoadPanels.getBillingAndInvoiceLoader().load();
					LoadPanels.paymentDetailsPane = LoadPanels.getPaymentDetailsLoader().load();
					LoadPanels.InvoicesDetailpane = LoadPanels.getInvoicesDetailLoader().load();
					LoadPanels.refundAndInvoicePane = LoadPanels.getRefundAndInvoiceLoader().load();
					LoadPanels.refundBillPerCouplePane = LoadPanels.getRefundBillPerCoupleLoader().load();
					LoadPanels.refundBillOverallPane = LoadPanels.getRefundBillOverallLoader().load();
					LoadPanels.unpaidBillPerCouplepane = LoadPanels.getUnpaidBillPerCoupleLoader().load();
					LoadPanels.unpaidBillOverallpane = LoadPanels.getUnpaidBillOverallLoader().load();
					
					
					// LoadPanels.copyRightPane =
					// LoadPanels.getCopyrightLoader().load();
					/* Add all the page before the main page */
					LoadPanels.mainPage = LoadPanels.getMainPageLoader().load();
					for (int i = 0; i < 70; i++)
						LoadPanels.patientListEntityes.add(new AnchorPane());

					for (int i = 0; i < 70; i++) {
						LoadPanels.patientListEntityes.set(i, LoadPanels.getPatientListEntityLoader().get(i).load());
					}
					for (int i = 0; i < 100; i++)
						LoadPanels.archiveEntityes.add(new AnchorPane());

					for (int i = 0; i < 100; i++)
						LoadPanels.archiveEntityes.set(i, LoadPanels.getArchiveEntityLoader().get(i).load());

					for (int i = 0; i < 50; i++)
						LoadPanels.archiveHBoxEntityes.add(new AnchorPane());

					for (int i = 0; i < 50; i++)
						LoadPanels.archiveHBoxEntityes.set(i, LoadPanels.getArchiveHBoxLoader().get(i).load());
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {

				LoadPanels.loadLoginPanel(mainApp);
			}
		});
		new Thread(sleeper).start();

	}

	public void buildData() {
		companyDto = comapnyService.getCompanyDetails(1l);
		Image image = null;
		
		if (null != companyDto) {
			companyNameLabel.setText(companyDto.getCompanyFullName());
			try {
				companyLogoImageView.setFitWidth(200);
				companyLogoImageView.setFitHeight(200);
				companyLogoImageView.setPreserveRatio(true);
				String logoDestPath = comapnyService.getImage(1l);
				image = new Image(new File(logoDestPath).toURI().toString());
				companyLogoImageView.setImage(image);
			/*	@SuppressWarnings("unchecked")
				SessionObject<String, String> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.CURRENCY_TYPE_SYMBOL, companyDto.getBillingCurrency());*/
				// We are setting CompanyToSession
				@SuppressWarnings("unchecked")
				SessionObject<String, CompanyDto> sessionObject = Session.getInstance().getSessionObject();
				sessionObject.setComponent(Constants.COMPANY_DETAILS, companyDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}