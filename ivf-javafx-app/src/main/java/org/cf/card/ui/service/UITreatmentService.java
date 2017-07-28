package org.cf.card.ui.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.cf.card.ui.configuration.Configuration;
import org.cf.card.ui.model.UIPrintListPatient;
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

public class UITreatmentService {

    UICodesService uiCodesService = new UICodesService();
    private List<UIPrintListPatient> printListPatients = new ArrayList<>();

    private RestTemplate restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

    public List<UIPrintListPatient> getPrintListPatients() {
        return printListPatients;
    }

    public void refreshTreatments() {
        printListPatients.clear();
        List<Treatment> treatments = getTreatments();
        for(int i=0; i<treatments.size(); i++)
            if(treatments.get(i).getCodes().getClient().equals(treatments.get(i).getCodes().getClient().getCouple().getMan()))
                printListPatients.add(new UIPrintListPatient(treatments.get(i-1),treatments.get(i)));

    }

    public void refreshTreatmentsByDate(String date) {
        printListPatients.clear();
        List<Treatment> treatments = getTreatmentsByDate(date);
        for (int i = 0; i < treatments.size(); i++)
            if (treatments.get(i).getCodes().getClient().equals(treatments.get(i).getCodes().getClient().getCouple().getMan()))
                printListPatients.add(new UIPrintListPatient(treatments.get(i - 1), treatments.get(i)));

    }

    public String getPastDateString(int day) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return dateFormat.format(cal.getTime());
    }


    public List<Treatment> getTreatments() {

        ResponseEntity<List<Treatment>> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/treatments",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Treatment>>() {
                }, new ParameterizedTypeReference<List<Treatment>>() {
                }
        );
        return exchange.getBody();
    }

    public Codes saveTreatment(Date date, Client client,Date dateLast, int cycleType) {
        Treatment treatment = new Treatment();
        treatment.setName("");

        
            // (2) give the formatter a String that matches the SimpleDateFormat pattern
        	//java.sql.Date dt = newStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
            // (3) prints out "Tue Sep 22 00:00:00 EDT 2009"
            treatment.setStartDate(date);
            treatment.setEndDate(dateLast);
        Codes codes = new Codes();
        codes.setTreatment(treatment);
        treatment.setCodes(codes);
        treatment.setCycleType(cycleType);
        codes.setClient(client);
        Codes codes1 = uiCodesService.addCode(codes);
        //codes1.setCode(uiCodesService.formatValue(codes1.getId(), 7));
     //   uiCodesService.addCode(codes1);
        return codes1;

    }

    public void deleteCode(Long l) {


        restTemplate.exchange(Configuration.getServerUrl()+"/codes/{id}",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Codes>() {
                }, l
        );

    }


    public List<Treatment> getTreatmentsByDate(String date) {
        ResponseEntity<List<Treatment>> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/treatments?date={date}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Treatment>>() {
                }, date
        );
        return exchange.getBody();
    }

    public Treatment latestTreatment() {
        ResponseEntity<Treatment> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/treatments/latest",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Treatment>() {
                }
        );
        return exchange.getBody();
    }


    public Treatment update(Treatment t) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Treatment> entity = new HttpEntity<>(t, headers);

        ResponseEntity<Treatment> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/treatments/update",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Treatment>() {
                }, new ParameterizedTypeReference<Treatment>() {
                }
        );

        return exchange.getBody();
    }

/*    public Treatment updateTreatmentforSemen(TreatmentDto treatmentDto){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TreatmentDto> entity = new HttpEntity<>(treatmentDto, httpHeaders);
		HttpEntity<Treatment> exchange = restTemplate.exchange(Configuration.getServerUrl() + "/treatments/semens",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<Treatment>(){
				}
			);
		return exchange.getBody();
	}*/

    /* get latest treatment by passing parameter coupleId*/
    public TreatmentDto getTreatmentsByCoupleId(Long coupleId) {

    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> entity = new HttpEntity<>(coupleId, httpHeaders);

        ResponseEntity<TreatmentDto> exchange = restTemplate.exchange(Configuration.getServerUrl() +"/treatments/latestTreatment/{coupleId}",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<TreatmentDto>() {
                }, coupleId
        );
        return exchange.getBody();
    }
    
    public Treatment updateTreatment(TreatmentDto t) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TreatmentDto> entity = new HttpEntity<>(t, headers);

        ResponseEntity<Treatment> exchange = restTemplate.exchange(Configuration.getServerUrl()+"/treatments/updateTreatment",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Treatment>() {
                }, new ParameterizedTypeReference<Treatment>() {
                }
        );

        return exchange.getBody();
    }
}
