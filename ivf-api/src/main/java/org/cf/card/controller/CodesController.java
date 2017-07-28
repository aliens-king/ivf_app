package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.Codes;
import org.cf.card.service.CodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dell on 11/26/2014.
 */
@RestController
@RequestMapping(value = "/codes")
public class CodesController {

    @Autowired
    private CodesService codesService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Codes> getCodes() {

        List<Codes> treatments = new ArrayList<>();
        treatments.addAll(codesService.findAll());
        return treatments;
    }


    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Codes addCodes(@RequestBody Codes codes) {
        return codesService.createNewCode(codes);
    }

    @RequestMapping(method = PUT, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Codes updateCodes(@RequestBody Codes codes) {
        return codesService.update(codes);
    }

    @RequestMapping(method = DELETE, value = "{id}")
    public @ResponseBody void deleteCouple(@PathVariable("id") long id) {

        codesService.deleteById(id);
    }

    @RequestMapping(value = "/recent",method = GET, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Codes findRecent() {
        Codes code = codesService.findRecent();
        return code;
    }

    /**
     * find codes by id
     * @param id
     * @return
     */
    @RequestMapping(value ="/{id}" ,method= GET, produces=APPLICATION_JSON_VALUE)
    public @ResponseBody Codes findCodeById(@PathVariable("id") long id){
    	return codesService.findOne(id);
    }
    /**
     * find codes by client id
     * @param clientId
     * @return
     */
    @RequestMapping(value ="/findByClientId/{clientId}" ,method= GET, produces=APPLICATION_JSON_VALUE)
    public @ResponseBody List<Codes> findCodeByClientId(@PathVariable("clientId") long clientId){
    	return codesService.findCodesByClientId(clientId);
    } 
    
    
   
    /**
     * Find latest code by client id.
     *
     * @param clientId the client id
     * @return the long
     */
    @RequestMapping(value ="/findLatestCodeByClientId/{clientId}" ,method= GET, produces=APPLICATION_JSON_VALUE)
    public Codes findLatestCodeByClientId(@PathVariable("clientId") long clientId){
    	return codesService.findLatestCodeByClientId(clientId);
    } 
    
    
}
