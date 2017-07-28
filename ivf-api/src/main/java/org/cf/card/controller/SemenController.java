package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import org.cf.card.dto.CryoSemenDto;
import org.cf.card.dto.SemenDto;
import org.cf.card.model.Semen;
import org.cf.card.model.SemenCode;
import org.cf.card.model.SemenData;
import org.cf.card.service.SemenService;
import org.cf.card.vo.VoSemenCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/semen")
public class SemenController {

	@Autowired
	private SemenService semenService;

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody Semen addSemen(@RequestBody SemenDto semenDto){
		return semenService.addSemen(semenDto);
	}

	@RequestMapping(value="/semenCode/{id}",method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<SemenCode> findSemenCode(@PathVariable(value = "id") Long codeId){
		return semenService.findByCodeId(codeId);
	}

	@RequestMapping(value="/semenData/{id}",method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<SemenData> findSemenData(@PathVariable(value = "id") Long semenId){
		return semenService.findBySemenId(semenId);
	}

/*	@RequestMapping(value="/semenCode/update", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody List<SemenCode> saveSemenCode(@RequestBody List<SemenCodeDto> semenCodeDto){
		return semenService.updateSemenCode(semenCodeDto);
	}*/

	/**
	 * save semen code data
	 * @param cryoSemenDto
	 * @return
	 */
	@RequestMapping(value="/semenCode/save", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody List<SemenCode> saveSemenCode(@RequestBody CryoSemenDto cryoSemenDto){
		return semenService.saveSemenCode(cryoSemenDto);//semenService.updateSemenCode(semenCodeDto);
	}

	/**
	 * update Semen code data
	 * @param cryoSemenDto
	 * @return List<SemenCode>
	 */
	@RequestMapping(value="/semenCode/update", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody List<SemenCode> updateSemenCode(@RequestBody CryoSemenDto cryoSemenDto){
		return  semenService.saveOrUpdateSemenCode(cryoSemenDto);
	}

	/**
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value="/semenDataByClientId/{clientId}",method=GET,produces =APPLICATION_JSON_VALUE)
	public List<VoSemenCode> findSemenCodeAndSemenDataByClientId(@PathVariable(value = "clientId") Long clientId){
		return semenService.findSemenCodeAndSemenDataByClientId(clientId);
	}

	/**
	 * Find semenCode by code id and screen id.
	 *
	 * @param manCodeId the man code id
	 * @param screen the screen
	 * @return the list
	 */
	@RequestMapping(value="/list/findSemenCodeByCodeIdAndScreenId/{manCodeId}/{screen}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<SemenCode> findSemenCodeByCodeIdAndScreenId(@PathVariable(value = "manCodeId") Long manCodeId,
			@PathVariable(value = "screen") int screen){
		return semenService.findSemenCodeByCodeIdAndScreenId(manCodeId, screen);
	}
	
	@RequestMapping(value = "/findSemenCodeByClientIdScreenAndDate", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<SemenCode> findSemenCodeByClientIdScreenIdAndDate(@RequestParam(value = "clientId") Long clientId, 
			@RequestParam(value = "screen") int screen, @RequestParam(value = "createdDate")  String createdDate){
		return semenService.findSemenCodeByClientIdScreenAndDate(clientId, screen, createdDate);
	}

	@RequestMapping(value = "/map/findSemenCodeMapByClientIdScreen/{clientId}/{screen}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Map<String, SemenDto> findSemenCodeMapByClientIdScreen(@PathVariable(value = "clientId") Long clientId, 
			@PathVariable(value = "screen") int screen){
		return semenService.findSemenCodeMapByClientIdScreen(clientId, screen);
	}
	

//	@RequestMapping(value="/cryo/add",method = POST, consumes = APPLICATION_JSON_VALUE)
//    public
//    @ResponseBody
//    Treatment addCryoSemens(@RequestBody TreatmentDto treatmentDto) {
//
//        return treatmentService.updateTreatment(treatmentDto);
//    }

}
