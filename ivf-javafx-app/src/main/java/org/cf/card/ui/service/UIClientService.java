package org.cf.card.ui.service;

import static java.util.stream.Collectors.toList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * @author <a href="mailto:sbulzan@sdl.com">Stefan Bulzan</a>
 * @since WS 11.0
 */
@Component
@Cacheable
public class UIClientService {

	private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());
	private String root = System.getProperty("user.dir");
	UICoupleService uiCoupleService = new UICoupleService();

	/**
	 * Gets the clients by couple id.
	 *
	 * @param coupleId
	 *            the couple id
	 * @return the clients by couple id
	 */
	public List<Client> getClientsByCoupleId(Long coupleId) {
		String url = Configuration.getServerUrl() + "/clients/{coupleId}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<Client>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Client>>() {
				}, coupleId);
		return exchange.getBody();

	}

	public List<Client> getClients() {

		ResponseEntity<List<Client>> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clients",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Client>>() {
				}, new ParameterizedTypeReference<List<Client>>() {
				});
		return exchange.getBody();
	}

	public Client addClient(Client c1) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(c1, headers);

		ResponseEntity<Client> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clients",
				HttpMethod.POST, entity, new ParameterizedTypeReference<Client>() {
				}, new ParameterizedTypeReference<Client>() {
				});
		return exchange.getBody();

	}

	public Client updateClient(Client c1) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(c1, headers);

		ResponseEntity<Client> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clients",
				HttpMethod.PUT, entity, new ParameterizedTypeReference<Client>() {
				}, new ParameterizedTypeReference<Client>() {
				});
		return exchange.getBody();

	}

	public void deleteAll() {
		restTemplate.exchange(Configuration.getServerUrl() + "/clients", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Client>() {
				}, new ParameterizedTypeReference<Client>() {
				});
	}

	public Client getClientById(long id) {

		ResponseEntity<Client> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/clients/{id}",
				HttpMethod.GET, null, new ParameterizedTypeReference<Client>() {
				}, id);
		return exchange.getBody();
	}

	public void deleteClient(Client client) {

		restTemplate.exchange(Configuration.getServerUrl() + "/clients/{id}", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Client>() {
				}, client.getId());
	}

	public List<Client> getClientsByGender(String gender) {

		ResponseEntity<List<Client>> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + "/clients?gender={gender}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Client>>() {
				}, gender);
		return exchange.getBody();

	}

	public List<Client> getClientsBySurname(String surname) {

		ResponseEntity<List<Client>> exchange = restTemplate.exchange(
				Configuration.getServerUrl() + "/clients?surname={surname}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Client>>() {
				}, surname);
		return exchange.getBody();

	}

	/**
	 * Fill year values in combo box.
	 *
	 * @param jComboBox1
	 *            the j combo box1
	 */
	/*
	 * public void fillYearComboBox(ComboBox jComboBox1) {
	 * jComboBox1.getItems().clear(); jComboBox1.getItems().add("n/a");
	 * 
	 * for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= 1900; i--)
	 * jComboBox1.getItems().add(i); }
	 * 
	 *//**
		 * Fill age data in combo box.
		 *
		 * @param jComboBox1
		 *            the j combo box1
		 */
	/*
	 * public void fillAgeComboBox(JComboBox jComboBox1) {
	 * jComboBox1.removeAllItems(); jComboBox1.addItem("Age"); for (int i = 0; i
	 * < 121; i++) jComboBox1.addItem(i);
	 * 
	 * }
	 * 
	 *//**
		 * Fill days by month combo box.
		 *
		 * @param jComboBox2
		 *            the j combo box2
		 * @param jComboBox3
		 *            the j combo box3
		 */
	/*
	 * public void fillDaysByMonthComboBox(ComboBox jComboBox2, ComboBox
	 * jComboBox3) { jComboBox3.getItems().clear(); String s = (String)
	 * jComboBox2.getValue();
	 * 
	 * if (s != null && s.equals("January")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("February")) { for (int i = 1; i <= 29; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("March")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("April")) { for (int i = 1; i <= 30; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("May")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("June")) { for (int i = 1; i <= 30; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("July")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("August")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("September")) { for (int i = 1; i <= 30; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("October")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("November")) { for (int i = 1; i <= 30; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("December")) { for (int i = 1; i <= 31; i++)
	 * jComboBox3.getItems().add(i); }
	 * 
	 * if (s != null && s.equals("Month")) { jComboBox3.getItems().add("Day"); }
	 * }
	 * 
	 * 
	 *//**
		 * Fill month values in combo box.
		 *
		 * @param jComboBox
		 *            the j combo box
		 */
	/*
	 * public void fillMonthComboBox(ComboBox jComboBox){
	 * jComboBox.getItems().clear(); jComboBox.getItems().add("Month");
	 * jComboBox.getItems().add("January");
	 * jComboBox.getItems().add("February"); jComboBox.getItems().add("March");
	 * jComboBox.getItems().add("April"); jComboBox.getItems().add("May");
	 * jComboBox.getItems().add("June"); jComboBox.getItems().add("July");
	 * jComboBox.getItems().add("August");
	 * jComboBox.getItems().add("September");
	 * jComboBox.getItems().add("October");
	 * jComboBox.getItems().add("November");
	 * jComboBox.getItems().add("December"); }
	 * 
	 *//**
		 * Fill day values in combo box.
		 *
		 * @param jComboBox
		 *            the j combo box
		 *//*
		 * public void fillDayComboBox(ComboBox jComboBox){
		 * jComboBox.getItems().clear(); jComboBox.getItems().add("Day"); for
		 * (int i = 1; i <= 30; i++) jComboBox.getItems().add(i); }
		 */

	/**
	 * Fill gender in combo box.
	 *
	 * @param jComboBox
	 *            the j combo box
	 */
	public void fillGenderComboBox(ComboBox jComboBox) {
		jComboBox.getItems().clear();
		jComboBox.getItems().add(MessageResource.getText("uiclient.service.combo.na"));
		jComboBox.getItems().add(MessageResource.getText("uiclient.service.combo.male"));
		jComboBox.getItems().add(MessageResource.getText("uiclient.service.combo.female"));
		jComboBox.getItems().add(MessageResource.getText("uiclient.service.combo.male.donor"));
		jComboBox.getItems().add(MessageResource.getText("uiclient.service.combo.female.donor"));
	}

	/**
	 * Save couple data to DB.
	 *
	 * @param manSurnameTextField
	 *            the man surname text field
	 * @param manMiddleNameTextField
	 *            the man middle name text field
	 * @param manFirstNameTextField
	 *            the man first name text field
	 * @param manYearComboBox
	 *            the man year combo box
	 * @param manMonthComboBox
	 *            the man month combo box
	 * @param manDayComboBox
	 *            the man day combo box
	 * @param womanSurnameTextField
	 *            the woman surname text field
	 * @param womanMiddleNameTextField
	 *            the woman middle name text field
	 * @param womanFirstNameTextField
	 *            the woman first name text field
	 * @param womanYearComboBox
	 *            the woman year combo box
	 * @param womanMonthComboBox
	 *            the woman month combo box
	 * @param womanDaysComboBox
	 *            the woman days combo box
	 * @param partnerGender
	 *            the partner gender
	 * @param womanHomeAddressTextField
	 *            the woman home address text field
	 * @param womanPhoneNumberTextField
	 *            the woman phone number text field
	 * @param womanEmailTextField
	 *            the woman email text field
	 * @param partnerHomeAddressTextField
	 *            the partner home address text field
	 * @param partnerPhoneNumberTextField
	 *            the partner phone number text field
	 * @param partnerEmailTextField
	 *            the partner email text field
	 * @return the couple
	 * @throws ParseException
	 *             the parse exception
	 */
	public Couple saveAsCouple(TextField manSurnameTextField, TextField manMiddleNameTextField,
			TextField manFirstNameTextField, TextField womanSurnameTextField, TextField womanMiddleNameTextField,
			TextField womanFirstNameTextField, ComboBox partnerGender, TextField womanHomeAddressTextField,
			TextField womanPhoneNumberTextField, TextField womanEmailTextField, TextField partnerHomeAddressTextField,
			TextField partnerPhoneNumberTextField, TextField partnerEmailTextField, DatePicker womanDOBDatePicker,
			DatePicker manDOBDatePicker) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Client c1 = new Client();
		c1.setSurname(manSurnameTextField.getText());
		c1.setMiddleName(manMiddleNameTextField.getText());
		c1.setFirstName(manFirstNameTextField.getText());
		// c1.setdOB(sdf.parse(manDayComboBox.getValue() + "/" +
		// manMonthComboBox.getSelectionModel().getSelectedIndex() + "/" +
		// manYearComboBox.getValue()));

		java.util.Date manDOB = java.util.Date
				.from(manDOBDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		// java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		c1.setdOB(manDOB);
		c1.setGender(partnerGender.getValue().toString());
		c1.setHomeAddress(partnerHomeAddressTextField.getText());
		c1.setPhoneNumber(partnerPhoneNumberTextField.getText());
		c1.setEmail(partnerEmailTextField.getText());

		Client c2 = new Client();
		c2.setSurname(womanSurnameTextField.getText());
		c2.setMiddleName(womanMiddleNameTextField.getText());
		c2.setFirstName(womanFirstNameTextField.getText());
		// c2.setdOB(sdf.parse(womanDaysComboBox.getValue() + "/" +
		// womanMonthComboBox.getSelectionModel().getSelectedIndex() + "/" +
		// womanYearComboBox.getValue()));
		java.util.Date womanDOB = java.util.Date
				.from(womanDOBDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		c2.setdOB(womanDOB);
		c2.setGender(MessageResource.getText("uiclient.service.combo.female"));
		c2.setHomeAddress(womanHomeAddressTextField.getText());
		c2.setPhoneNumber(womanPhoneNumberTextField.getText());
		c2.setEmail(womanEmailTextField.getText());

		Couple couple = new Couple();
		couple.setMan(c1);
		couple.setWoman(c2);
		couple.getMan().setCouple(couple);
		couple.getWoman().setCouple(couple);

		couple = uiCoupleService.addCouple(couple);

		Clipart clipart = new Clipart();

		clipart.setSource(couple.getId() + ".jpg");
		couple.setClipart(clipart);
		clipart.setCouple(couple);

		return uiCoupleService.addCouple(couple);

	}

	public org.cf.card.model.Codes findCode(String text) {
		List<Client> dbClients = getClients();
		List<UIClient> clients = dbClients.stream().map(UIClient::new).collect(toList());
		for (int i = 0; i < clients.size(); i++) {
			for (int j = 0; j < clients.get(i).getClient().getCodes().size(); j++) {
				if (clients.get(i).getClient().getCodes().get(j).getCode().equals(text))
					return clients.get(i).getClient().getCodes().get(j);
			}
		}

		return null;
	}

	/**
	 * Check if same couple with same data exists and return a value based on
	 * that.
	 *
	 * @param manSurnameTextField
	 *            the man surname text field
	 * @param manMiddleNameTextField
	 *            the man middle name text field
	 * @param manFirstNameTextField
	 *            the man first name text field
	 * @param manYearComboBox
	 *            the man year combo box
	 * @param manMonthComboBox
	 *            the man month combo box
	 * @param manDayComboBox
	 *            the man day combo box
	 * @param womanSurnameTextField
	 *            the woman surname text field
	 * @param womanMiddleNameTextField
	 *            the woman middle name text field
	 * @param womanFirstNameTextField
	 *            the woman first name text field
	 * @param womanYearComboBox
	 *            the woman year combo box
	 * @param womanMonthComboBox
	 *            the woman month combo box
	 * @param womanDaysComboBox
	 *            the woman days combo box
	 * @param partnerGender
	 *            the partner gender
	 * @param womanHomeAddressTextField
	 *            the woman home address text field
	 * @param womanPhoneNumberTextField
	 *            the woman phone number text field
	 * @param womanEmailTextField
	 *            the woman email text field
	 * @param partnerHomeAddressTextField
	 *            the partner home address text field
	 * @param partnerPhoneNumberTextField
	 *            the partner phone number text field
	 * @param partnerEmailTextField
	 *            the partner email text field
	 * @return the int
	 */
	public int checkCouple(TextField manSurnameTextField, TextField manMiddleNameTextField,
			TextField manFirstNameTextField, TextField womanSurnameTextField, TextField womanMiddleNameTextField,
			TextField womanFirstNameTextField, ComboBox<String> partnerGender, TextField womanHomeAddressTextField,
			TextField womanPhoneNumberTextField, TextField womanEmailTextField, TextField partnerHomeAddressTextField,
			TextField partnerPhoneNumberTextField, TextField partnerEmailTextField, DatePicker womanDOBDatePicker,
			DatePicker manDOBDatePicker) {
		List<Client> clients = getClients();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Client c1 = new Client();
		c1.setSurname(manSurnameTextField.getText());
		c1.setMiddleName(manMiddleNameTextField.getText());
		c1.setFirstName(manFirstNameTextField.getText());
		java.util.Date manDOB =  null;
		if (manDOBDatePicker.getValue() != null) {
			manDOB = java.util.Date
					.from(manDOBDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		c1.setdOB(manDOB);

		if (partnerGender.getValue() != null)
			c1.setGender(partnerGender.getValue().toString());
		c1.setHomeAddress(partnerHomeAddressTextField.getText());
		c1.setPhoneNumber(partnerPhoneNumberTextField.getText());
		c1.setEmail(partnerEmailTextField.getText());

		Client c2 = new Client();
		c2.setSurname(womanSurnameTextField.getText());
		c2.setMiddleName(womanMiddleNameTextField.getText());
		c2.setFirstName(womanFirstNameTextField.getText());
		c2.setHomeAddress(womanHomeAddressTextField.getText());
		c2.setPhoneNumber(womanPhoneNumberTextField.getText());
		c2.setEmail(womanEmailTextField.getText());
		java.util.Date womanDOB = null;
		if (womanDOBDatePicker.getValue() != null) {
			womanDOB = java.util.Date
					.from(womanDOBDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		c2.setdOB(womanDOB);
		c2.setGender(MessageResource.getText("uiclient.service.combo.female"));
		
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getGender().equals(MessageResource.getText("uiclient.service.combo.male"))
					|| clients.get(i).getGender().equals(MessageResource.getText("uiclient.service.combo.female.donor"))
					|| clients.get(i).getGender().equals(MessageResource.getText("uiclient.service.combo.male.donor"))) {
				if (clients.get(i).getSurname().equals(c1.getSurname())
						&& clients.get(i).getFirstName().equals(c1.getFirstName())
						&& clients.get(i).getdOB().equals(c1.getdOB())
						&& clients.get(i).getHomeAddress().equals(c1.getHomeAddress())
						&& clients.get(i).getPhoneNumber().equals(c1.getPhoneNumber())
						&& clients.get(i).getEmail().equals(c1.getEmail())) {
					if (clients.get(i).getCouple().getWoman().getSurname().equals(c2.getSurname())
							&& clients.get(i).getCouple().getWoman().getFirstName().equals(c2.getFirstName())
							&& clients.get(i).getCouple().getWoman().getdOB().equals(c2.getdOB())
							&& clients.get(i).getCouple().getWoman().getHomeAddress().equals(c2.getHomeAddress())
							&& clients.get(i).getCouple().getWoman().getPhoneNumber().equals(c2.getPhoneNumber())
							&& clients.get(i).getCouple().getWoman().getHomeAddress().equals(c2.getHomeAddress())
							&& clients.get(i).getCouple().getWoman().getEmail().equals(c2.getEmail())) {
						return 0;
					}
				}
			}
		}
		return 1;
	}
}
