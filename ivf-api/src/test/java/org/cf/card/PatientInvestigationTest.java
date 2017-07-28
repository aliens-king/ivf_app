package org.cf.card;

import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.Investigation;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.model.Treatment;
import org.cf.card.service.ClientService;
import org.cf.card.service.InvestigationService;
import org.cf.card.service.PatientInvestigationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PatientInvestigationTest extends BaseTest {

	@Autowired
	PatientInvestigationService patientInvestigationService;

	@Autowired
	ClientService clientService;

	@Autowired
	InvestigationService investigationService;

	Client client = null;
	Treatment treatment = null;
	List<Client> clients = null;
	Couple couple = null;
	List<Investigation> groups = null;

	@Before
	public void getData() {
		clients = clientService.getClientBySurname("johan");
		for (Client client1 : clients) {
			couple = client1.getCouple();
			treatment = client1.getCodes().get(0).getTreatment();
			groups = investigationService.findByGroup(1);
		}
	}

	Investigation ret = null;

	// @Test
	/*public void addPatientInvestigation() {
		try {
			PatientInvestigation pInvestigation = new PatientInvestigation();
			pInvestigation.setBiochemstry("test");
			pInvestigation.setNurseNotes("notes");
			pInvestigation.setScan("scan");
			pInvestigation.setMedicalHistory("medical history");
			pInvestigation.setBmi(0.5f);

			Investigation investigation = investigationService.findByGroup(1).get(0);

			InvestigatinValue investigatinValue = new InvestigatinValue();
			investigatinValue.setValue(investigation.getName());
			investigatinValue.setInvestigation(investigation);
			investigatinValue.setPatientInvestigation(pInvestigation);
			Set<InvestigatinValue> set = new HashSet<>();
			set.add(investigatinValue);
			investigation.setInvestigatinValues(set);

			// pInvestigation.setInvestigatinValues(set);

			patientInvestigationService.saveOrUpdate(pInvestigation);
			// ret = investigationService.saveOrUpdate(investigation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	// @Test
	public void getInvestionByGroup() {
		if (ret != null)
			investigationService.delete(ret);
	}

	PatientInvestigation pi = null;

	/*@Test
	public void addUIPatient() {
		try {
			InvestigatinValueDto uiInvestigatinValue = new InvestigatinValueDto();
			uiInvestigatinValue.setInvestigationId(7l);
			uiInvestigatinValue.setValue("HBC");
			List<InvestigatinValueDto> list = new ArrayList<>();
			list.add(uiInvestigatinValue);
			PatientInvestigationDto uiPatientInvestigation = new PatientInvestigationDto();
			uiPatientInvestigation.setWomanCodeId(2l);
			uiPatientInvestigation.setBiochemstry("test");
			uiPatientInvestigation.setNurseNotes("notes");
			uiPatientInvestigation.setScan("scan");
			uiPatientInvestigation.setMedicalHistory("medical history");
			uiPatientInvestigation.setBmi(1.6f);
			uiPatientInvestigation.setInvestigatinValues(list);
			patientInvestigationService.addWomanPatientInvestigation(uiPatientInvestigation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	// @Test
	public void deletes() {
		// if(pi!=null)
		// patientInvestigationService.delete(pi);
	}

	@Test
	public void getTest() {
		Client man = clientService.findOne(1l);

		// Client woman = couple.getWoman();

		List<Codes> manCode = man.getCodes();
		System.out.println(manCode);

	}

	public void saveTest(){

	}
}
