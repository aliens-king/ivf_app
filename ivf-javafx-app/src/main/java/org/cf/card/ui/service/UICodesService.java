package org.cf.card.ui.service;

import java.util.List;

import org.cf.card.model.Codes;
import org.cf.card.ui.configuration.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Dell on 5/11/2015.
 */
public class UICodesService {

    private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

    public List<Codes> getCodes() {

        ResponseEntity<List<Codes>> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/codes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Codes>>() {
                }, new ParameterizedTypeReference<List<Codes>>() {
                }
        );
        return exchange.getBody();
    }

    public Codes addCode(Codes c1) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Codes> entity = new HttpEntity<Codes>(c1, headers);

        ResponseEntity<Codes> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/codes",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Codes>() {
                }, new ParameterizedTypeReference<Codes>() {
                }
        );
        return exchange.getBody();
    }

    public String formatValue(long i, int nrOfCharacters) {
        String result = new String();
        for (int j = 1; j <= nrOfCharacters; j++) {
            if ('A' + (i % 36) <= 'Z')
                result = (char) ('A' + (i % 36)) + result;
            else
                result = (char) ('A' + (i % 36) - 43) + result;
            i /= 36;
        }
        return result;
    }

    public Codes recentCode() {
        ResponseEntity<Codes> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/codes/recent",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Codes>() {
                }
        );
        return exchange.getBody();
    }
    
    public Codes getCodeById(Long id){
    	ResponseEntity<Codes> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/codes/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Codes>() {
                },id
        );
        return exchange.getBody();
    }
    
    
    public Codes getLatestCodeByClientId(Long clientId){
    	ResponseEntity<Codes> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/codes/findLatestCodeByClientId/{clientId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Codes>() {
                },clientId
        );
        return exchange.getBody();
    }
}
