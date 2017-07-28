package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.model.Investigation;
import org.cf.card.ui.configuration.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UIInvestigationService {

	private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

	 public List<Investigation> getInvestigationGroupByKey(int key) {

	        ResponseEntity<List<Investigation>> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/investigation?key={key}",
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<Investigation>>() {
	                }, key
	        );
	        return exchange.getBody();

	    }
	 public List<Investigation> findAllInvestigation() {

	        ResponseEntity<List<Investigation>> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/investigation/all",
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<Investigation>>() {
	                }
	        );
	        return exchange.getBody();

	    }
}
