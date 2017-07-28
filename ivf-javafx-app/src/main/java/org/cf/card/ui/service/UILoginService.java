package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.model.User;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.util.Constants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javafx.scene.control.TextField;

/**
 * Created by Dell on 5/3/2015.
 */
public class UILoginService {

	private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());
	public User login;

	public List<User> getLogins() {

		ResponseEntity<List<User>> exchange = restTemplate.exchange(Constants.LOCAL_HOST_API_LOGIN, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<User>>() {
				}, new ParameterizedTypeReference<List<User>>() {
				});
		return exchange.getBody();
	}

	public boolean checkLogin(TextField jTextField1) {
		List<User> logins = getLogins();
		for (int i = 0; i < logins.size(); i++)
			if (logins.get(i).getLoginCode().equals(jTextField1.getText())) {
				login = logins.get(i);
				return true;

			}

		return false;
	}

	public User authenticate(TextField jTextField1) {
		String password = jTextField1.getText();
		User user = null;
		if (!password.isEmpty()) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String url = Constants.LOCAL_HOST_API_LOGIN + "/authenticate/{password}";
			ResponseEntity<User> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<User>() {
					}, password);
			user = exchange.getBody();
			setLogin(user);
			return user;
		} else {
			return null;
		}
	}

	public User save(User login) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(login, headers);

		ResponseEntity<User> exchange = restTemplate.exchange(Constants.LOCAL_HOST_API_LOGIN, HttpMethod.POST, entity,
				new ParameterizedTypeReference<User>() {
				}, new ParameterizedTypeReference<User>() {
				});

		return exchange.getBody();
	}

	public void delete(Long l) {
		restTemplate.exchange(Configuration.getServerUrl() + "/login/{id}", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<User>() {
				}, l);
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}
	
}
