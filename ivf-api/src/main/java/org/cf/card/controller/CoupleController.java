package org.cf.card.controller;

import org.cf.card.model.Couple;
import org.cf.card.service.CoupleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Dell on 11/10/2014.
 */
@RestController
@RequestMapping(value = "/couples")
public class CoupleController {

    @Autowired
    private CoupleService coupleService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Couple> getCouples() {

        List<Couple> couples = new ArrayList<>();
        couples.addAll(coupleService.findAll());
        return couples;
    }


    @RequestMapping(method = GET, value = "{id}")
    public
    @ResponseBody
    Couple getClientById(@PathVariable("id") long id) {
        return coupleService.findOne(id);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Couple addCouple(@RequestBody Couple couple) {

        return coupleService.save(couple);
    }

    @RequestMapping(method = PUT, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Couple updateCouple(@RequestBody Couple couple) {
        return coupleService.update(couple);
    }

    @RequestMapping(method = DELETE, value = "{id}")
    public @ResponseBody void deleteCouple(@PathVariable("id") Long id) {

        coupleService.deleteById(id);

    }
}
