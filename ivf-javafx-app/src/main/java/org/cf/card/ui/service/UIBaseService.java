/**
 *
 */
package org.cf.card.ui.service;

import org.cf.card.ui.configuration.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nikhil Mahajan
 *
 */
public abstract class UIBaseService {

	protected RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

	protected static final String BASE_URL = Configuration.getServerUrl();
}
