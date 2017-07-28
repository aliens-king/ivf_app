package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.cf.card.dto.ProcedureDto;
import org.cf.card.service.BillingSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/billingSetup")
public class BillingSetupController {

    @Autowired
    private BillingSetupService billingSetupService;

    @RequestMapping(value = "/save", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ProcedureDto saveCompanyDetails(@RequestBody ProcedureDto procedureDto) {
	return billingSetupService.save(procedureDto);
    }

    @RequestMapping(value = "/findAll", method = POST, consumes = APPLICATION_JSON_VALUE)
    public List<ProcedureDto> findAllProcedure() {
	return billingSetupService.findAllProcedure();
    }

}
