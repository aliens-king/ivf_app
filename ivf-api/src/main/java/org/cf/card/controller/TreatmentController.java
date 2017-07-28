package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Treatment;
import org.cf.card.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dell on 11/26/2014.
 */
@RestController
@RequestMapping(value = "/treatments")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Treatment> getTreatments() {

        List<Treatment> treatments = new ArrayList<>();
        treatments.addAll(treatmentService.findAll());
        return treatments;
    }


    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Treatment addTreatment(@RequestBody Treatment treatment) {
        return treatmentService.saveOrUpdate(treatment);
    }

    @RequestMapping(value="/update",method = POST, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Treatment update(@RequestBody Treatment treatment) {
        return treatmentService.update(treatment);
    }


    @RequestMapping(method = PUT, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Treatment updateTreatment(@RequestBody Treatment treatment) {
        return treatmentService.update(treatment);
    }




    @RequestMapping(method = GET, value = "date")
    public
    List<Treatment> getTreatmentsByDate(@RequestParam(value = "date", required = true) Date date) {
        return treatmentService.getByStartDate(date);
    }

    @RequestMapping(value = "/latest",method = GET, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Treatment latestTreatment() {
        return treatmentService.getLatestTreatment();
    }

  /*  @RequestMapping(value="/semens",method = POST, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Treatment updateTreatmentSemens(@RequestBody TreatmentDto treatmentDto) {
        return treatmentService.updateTreatment(treatmentDto);
    }*/
    
    /* get latest treatment by passing parameter coupleId*/
    @RequestMapping(method = GET, value = "/latestTreatment/{coupleId}")
    public
    TreatmentDto getTreatmentsByCoupleId(@PathVariable("coupleId") Long coupleId) {
        return treatmentService.findLatesTreatmentByCoupleId(coupleId);
    }
    
  
    @RequestMapping(value="/updateTreatment",method = POST, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Treatment updateTreatment(@RequestBody TreatmentDto treatmentDto) {
        return treatmentService.updateTreatmentEntity(treatmentDto);
    }

}
