/**
 *
 */
package org.cf.card;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cf.card.dto.CycleDto;
import org.cf.card.model.Codes;
import org.cf.card.model.Embryo;
import org.cf.card.model.Treatment;
import org.cf.card.persistence.EmbryoDao;
import org.cf.card.service.EmbryoService;
import org.cf.card.service.TreatmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author Nikhil Mahajan
 *
 */
public class EmbryoTests extends BaseTest {

	@Autowired
	private TreatmentService treatmentService;

	@Autowired
	private EmbryoService embryoService;

	@Autowired
	private EmbryoDao embryoDao;

	public Treatment construct() {
		Treatment treatment = new Treatment();
		treatment.setName("Test");
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		date.setNanos(0);
		treatment.setStartDate(date);
		treatment.setEndDate(date);
		treatment.setEggs(5);
		treatment.setIncubator("A4");
		Codes codes = new Codes();
		codes.setTreatment(treatment);
		codes.setCode("aaa");
		treatment.setCodes(codes);
		return treatment;
	}

	// @Test
	public void saveEmbryos() {

		Treatment treatment = null;
		List<Embryo> embryos = new ArrayList<Embryo>();
		try {
			treatment = treatmentService.save(construct());
			embryos = embryoService.saveEmbryos(treatment.getEggs(), treatment.getCodes().getId());
			assertThat(treatment.getEggs(), is(embryos.size()));
			System.out.println("---" + embryos.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			for (Embryo embryo : embryos) {
				embryoService.delete(embryo);
			}
			treatmentService.delete(treatment);
		}

		// deleting data

	}

//	@Test
	public void womanCycles() {
		try {

			Set<CycleDto> womanCycles = embryoDao.findWomanCycles(4l);
			Assert.notEmpty(womanCycles);
			Assert.isTrue((womanCycles.size() == 3), "Size is " + womanCycles.size());
			 for (CycleDto cycleDto : womanCycles) {
			 System.out.println("cycleDto ----" + cycleDto);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void manCycles() {
		try {

			Set<CycleDto> manCycles = embryoDao.findPartnerCycles(7l);
			for (CycleDto cycleDto : manCycles) {
				 System.out.println("cycleDto ----" + cycleDto);
				 }
			Assert.notEmpty(manCycles);
			Assert.isTrue((manCycles.size() == 1), "Size is " + manCycles.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	 @Test
	public void cycles() {
		Collection<CycleDto> cycles = embryoService.findCoupleCycles(8l, 7l);
		for (CycleDto cycleDto : cycles) {
			System.out.println("cycleDto ----" + cycleDto);
		}
		try {
//			Set<CycleDto> womanCycles = embryoDao.findWomanCycles(4l);
//			for (CycleDto cycleDto : womanCycles) {
//				System.out.println("cycleDto ----" + cycleDto);
//			}
//
//			// Set<CycleDto> partnerCycles = embryoDao.findPartnerCycles(5l);
//			Set<CycleDto> partnerCycles = embryoDao.findPartnerCycles(3l);
//			// System.out.println("-Size-" + partnerCycles.size());
//			/*
//			 * for (CycleDto mancycleDto : partnerCycles) { System.out.println(
//			 * "mancycleDto ----" + mancycleDto); }
//			 */
//
//			Map<String, CycleDto> cycleMap = new HashMap<>();
//			CycleDto cycleDto = null;
//			for (CycleDto womanCycle : womanCycles) {
//				String coupleStartDateKey = womanCycle.getCoupleId() + "-" + womanCycle.getStartDate();
//				CycleDto womanCycleDto = createWomanCyclesDto(womanCycle);
//				cycleMap.put(coupleStartDateKey, womanCycleDto);
//			}
//
//			for (CycleDto partnerCycle : partnerCycles) {
//				String pCoupleStartDateKey = partnerCycle.getCoupleId() + "-" + partnerCycle.getStartDate();
//
//				if (cycleMap.containsKey(pCoupleStartDateKey)) {
//					cycleDto = cycleMap.get(pCoupleStartDateKey);
//				}
//
//				// updating cycleDto with partner Cycle data
//				CycleDto updatedCycleDto = updateCycleDto(cycleDto, partnerCycle);
//				cycleMap.put(pCoupleStartDateKey, updatedCycleDto);
//			}

		} catch (Exception e) {
			System.out.println("=============> " + e.getMessage());
			e.printStackTrace();
		}
	}

	public CycleDto createWomanCyclesDto(CycleDto womanCycle) {
		CycleDto wCycleDto = new CycleDto();
		// setting woman data
		wCycleDto.setWomanCodeId(womanCycle.getWomanCodeId());
		wCycleDto.setCoupleId(womanCycle.getCoupleId());
		wCycleDto.setWomanCode(womanCycle.getWomanCode());
		wCycleDto.setStartDate(womanCycle.getStartDate());
		wCycleDto.setEggCollection(womanCycle.getEggCollection());
		wCycleDto.setEvolution(womanCycle.getEvolution());
		wCycleDto.setClinical(womanCycle.getClinical());
		return wCycleDto;
	}

	public CycleDto updateCycleDto(CycleDto cycleDto, CycleDto partnerCycle) {
		if (cycleDto == null) {
			cycleDto = new CycleDto();
		}
		// setting partner data
		cycleDto.setPartnerCode(partnerCycle.getPartnerCode());
		cycleDto.setPartnerCodeId(partnerCycle.getPartnerCodeId());
		// cycleDto.setQuality(partnerCycle.getQuality());
		return cycleDto;
	}

	// @Test
	public void findCodeIdByStartDateAndCoupleId() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String from = "17-02-2016 10:20:56";
		Date date = null;
		try {
			date = sdf.parse(from);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Long> codeList = embryoDao.findCodeIdByStartDateAndCoupleId(3l, date);
		System.out.println("codeList " + codeList);

	}
}
