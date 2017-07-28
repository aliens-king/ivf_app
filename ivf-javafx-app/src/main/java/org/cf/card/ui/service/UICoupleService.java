package org.cf.card.ui.service;

import org.cf.card.model.Couple;
import org.cf.card.ui.configuration.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Dell on 11/10/2014.
 */
@Component
public class UICoupleService {

    private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

    public List<Couple> getCouples() {

        ResponseEntity<List<Couple>> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/couples",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Couple>>() {
                }, new ParameterizedTypeReference<List<Couple>>() {
                }
        );
        return exchange.getBody();
    }

    public Couple getCoupleById(long id) {

        ResponseEntity<Couple> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/couples/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Couple>() {
                }, id
        );
        return exchange.getBody();
    }

    public Couple addCouple(Couple c1) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(c1, headers);

        ResponseEntity<Couple> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/couples",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Couple>() {
                }, new ParameterizedTypeReference<Couple>() {
                }
        );

        return exchange.getBody();
    }

    public void updateCouple(Couple c1) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(c1, headers);

        restTemplate.exchange(Configuration.getServerUrl()+"/couples",
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<Couple>() {
                }, c1.getId()
        );
    }

    public void deleteCouple(Long l) {


        restTemplate.exchange(Configuration.getServerUrl()+"/couples/{id}",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Couple>() {
                }, l
        );

    }
}
