package org.cf.card.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Dell on 2/4/2015.
 */
@RestController
@RequestMapping(value = "/ping")
public class PingController {

    @RequestMapping(method = GET)
    public
    @ResponseBody
    HttpStatus status() {
        return HttpStatus.OK;
    }

}
