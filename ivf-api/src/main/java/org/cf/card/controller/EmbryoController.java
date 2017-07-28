/**
 *
 */
package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;
import java.util.List;

import org.cf.card.dto.CycleDto;
import org.cf.card.dto.EmbryoCodeDto;
import org.cf.card.dto.EmbryoCodeValueDto;
import org.cf.card.dto.EmbryoDto;
import org.cf.card.dto.UIDayDto;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.Embryo;
import org.cf.card.model.EmbryoCode;
import org.cf.card.service.EmbryoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class EmbryoController.
 *
 * @author Nikhil Mahajan
 */
@RestController
@RequestMapping(value = "/embryo")
public class EmbryoController {

	/** The embryo dao. */
	@Autowired
	EmbryoService embryoService;

	/**
	 * Find all by various params
	 *
	 * @param codeId
	 *            the code id
	 * @param embryoCodeId
	 *            the embryo code id
	 * @return the list
	 */
	@RequestMapping(value = "/list", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmbryoCode> findAll(@RequestParam(value = "codeId", required = false) long codeId) {
		List<EmbryoCode> embryos = embryoService.findByCodeId(codeId);
		return embryos;
	}

	/**
	 * Find one : Find embryo code by emmbryoCodeId
	 *
	 * @param embryoCodeId
	 *            the embryo code id
	 * @return the list
	 */
	@RequestMapping(value = "/findByEmbryoCodeId/{embryoCodeId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public EmbryoCode findOne(@PathVariable(value = "embryoCodeId") Long embryoCodeId) {
		return embryoService.findEmbryoCodeByEmbryoCodeId(embryoCodeId);
	}

	@RequestMapping(value = "/addEmbryos", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<Embryo> addEmbryos(@RequestParam("oocytes") int oocytes,
			@RequestParam("codeId") long codeId) {

		// long codeId = (long)map.get("codeId");
		// int oocytes = (int)map.get("oocytes");
		return embryoService.saveEmbryos(oocytes, codeId);
	}

	@RequestMapping(value = "/update", method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody Embryo update(@RequestBody Embryo embryo) {
		return embryoService.update(embryo);
	}


	/**
	 * @param embryoCodeDto
	 * @return
	 */
	@RequestMapping(value = "/cryoEmbryo", method = POST, consumes = APPLICATION_JSON_VALUE)
	public void saveCryoEmbryo(@RequestBody EmbryoCodeDto embryoCodeDto) {
		 embryoService.saveCryoEmbryoEgg(embryoCodeDto);
	}

	/**
	 * @param uiDayDto
	 * @return
	 */
	@RequestMapping(value = "/dayProgressValue", method = POST, consumes = APPLICATION_JSON_VALUE)
	public List<DayProgressValue> saveDayProgressValue(@RequestBody UIDayDto uiDayDto) {
		return embryoService.saveDayProgressValue(uiDayDto);
	}

	/**
	 * @param embryoCodeid
	 * @param datIndex
	 * @return
	 */
	@RequestMapping(value = "/dayProgressValue/{embryoCodeid}/{datIndex}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<DayProgressValue> findByEmbryoCodeIdAndDayIndex(@PathVariable(value = "embryoCodeid") Long embryoCodeid,
			@PathVariable(value = "datIndex") int datIndex) {
		return embryoService.findByEmbryoCodeIdAndDayIndex(embryoCodeid, datIndex);
	}

	@RequestMapping(value = "/embryoTransfer", method = POST, consumes = APPLICATION_JSON_VALUE)
	public EmbryoCode addEmbryoTransfer(@RequestBody EmbryoCodeValueDto embryoCodeValueDto) {
		return embryoService.addEmbryoTransfer(embryoCodeValueDto);
	}

	/**
	 * Update embryos for injection.
	 * Used in EmbroyologyController
	 *
	 * @param aoEmbryoDto the ao embryo dto
	 * @return the list
	 */
	@RequestMapping(value = "/updateEmbryos", method = POST, consumes = APPLICATION_JSON_VALUE)
	public void updateEmbryos(@RequestBody List<EmbryoDto> aoEmbryoDto) {
		embryoService.updateEmbryos(aoEmbryoDto);
	}

	/**
	 * getting EmbryoCode by client id
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "/embryoCodeByClientId/{clientId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<EmbryoCode> findEmbryoCodeByClientId(@PathVariable(value = "clientId") Long clientId){
		return embryoService.findEmbryoCodeByClientId(clientId);
	}

	/**
	 * getting all cycles of woman & partner
	 * @param womanClientId
	 * @param manClientId
	 * @return Collection of cycleDto
	 */

	@RequestMapping(value="/set/findCoupleCycles/{womanClientId}/{manClientId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public Collection<CycleDto> findCoupleCycles(@PathVariable(value = "womanClientId") Long womanClientId, @PathVariable(value="manClientId") Long manClientId){
		return embryoService.findCoupleCycles(womanClientId, manClientId);
	}

	/**
	 * get the codes by couple & startdate
	 * @param coupleId
	 * @param startDate
	 * @return List of CodesId
	 */
	@RequestMapping(value="/findCodeIdByCoupleIdAndStartDate", method = GET, produces = APPLICATION_JSON_VALUE)
	public List<Long> findCodeIdByCoupleIdAndStartDate(@RequestParam(value = "coupleId") Long coupleId, @RequestParam(value="startDate") String startDate){
		return embryoService.findCodeIdByCoupleIdAndStartDate(coupleId, startDate);
	}

	/**
	 * * Update embryo code value for remarks only.
	 * @param embryoCodeValueDto the embryo code value dto
	 */
	@RequestMapping(value = "/updateEmbryoCodeValueDto", method = POST, consumes = APPLICATION_JSON_VALUE)
	public void updateEmbryoCodeValueDto(@RequestBody EmbryoCodeValueDto embryoCodeValueDto) {
		embryoService.updateEmbryoCodeValueDto(embryoCodeValueDto);
	}

}
