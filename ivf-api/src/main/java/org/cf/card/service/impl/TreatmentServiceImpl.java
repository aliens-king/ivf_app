package org.cf.card.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.dto.EmbryoCodeDto;
import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Treatment;
import org.cf.card.persistence.TreatmentDao;
import org.cf.card.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by Dell on 10/15/2014.
 */
@Service("treatmentService")
@Transactional
public class TreatmentServiceImpl extends BaseServiceImpl<Treatment>implements TreatmentService {

	private TreatmentDao treatmentDao;



	@Autowired
	public void setTreatmentDao(TreatmentDao treatmentDao) {
		this.treatmentDao = treatmentDao;
		setGenericDao(treatmentDao);
	}

	// public Treatment createNewTreatment(Treatment treatment) {
	// notNull(treatment, "login can't be null");
	// return treatmentDao.save(treatment);
	// }
	//
	// public List<Treatment> getTreatments() {
	// Iterable<Treatment> iterable = treatmentDao.findAll();
	// Iterator<Treatment> iterator = iterable.iterator();
	// List<Treatment> treatment = new ArrayList<Treatment>();
	//
	// while (iterator.hasNext()) {
	// treatment.add(iterator.next());
	// }
	//
	// return treatment;
	// }
	//
	// public Treatment getById(long id) {
	// return treatmentDao.findById(id);
	// }

	 @Override
	 public List<Treatment> getByName(String name) {
	 return treatmentDao.findByName(name);
	 }
	//
	@Override
	public List<Treatment> getByStartDate(Date startDate) {
		return treatmentDao.findByStartDate(startDate);
	}
	//
	 @Override
	 public List<Treatment> getByEndDate(Date endDate){
	 return treatmentDao.findByEndDate(endDate);
	 }

	 @Override
	 public Treatment getLatestTreatment(){
		 List<Treatment> treatments = treatmentDao.findAll(new Sort(Sort.Direction.DESC, "startDate"));
		 Treatment retVal = null;
		 if(treatments.size()>0){
			 retVal = treatments.iterator().next();
		 }
		 return retVal;
	 }
	//
	// @Override
	// public Treatment getByCodes(Codes code){
	// return treatmentDao.findByCodes(code);
	// }

	/*@Override
	public Treatment updateTreatment(TreatmentDto treatmentDto) {

		Treatment treatment=treatmentDao.findOne(treatmentDto.getTreatmentID());
	//	treatment.setSemens(treatmentDto.getSemenValue());
		if(treatment.getSemenCryoDate()==null){
			treatment.setSemenCryoDate(treatmentDto.getCryoDate());
		}
		return treatmentDao.save(treatment);
	}*/


	/* get latest treatment by passing parameter coupleId*/
	@Override
	public TreatmentDto findLatesTreatmentByCoupleId(Long coupleId) {
		TreatmentDto retVal = null;
		// Here you have to get the first element of treatment convert that to dto and return that dto to javafx app
		List<Treatment> aoTreatment = treatmentDao.findTreatmentByCoupleId(coupleId);
		if(aoTreatment!=null && aoTreatment.size()>0){
			Treatment treatment = aoTreatment.iterator().next();

			retVal = createTreamentDto(treatment);
		}
		return retVal;

	}

	private TreatmentDto createTreamentDto(Treatment treatment){
		List<EmbryoCodeDto> aoEmbryoCodeDto = new ArrayList<>();

		TreatmentDto treatmentDto = new TreatmentDto();
		treatmentDto.setTreatmentID(treatment.getId());
		treatmentDto.setStartDate(treatment.getStartDate());
		treatmentDto.setEndDate(treatment.getEndDate());
	//	treatmentDto.setSemenValue(treatment.getSemens());
		treatmentDto.setOocytes(treatment.getEggs());
		treatmentDto.setCycleType(treatment.getCycleType());
		/*if(null != treatment.getCodes()){
		    treatmentDto.setCodeId(treatment.getCodes().getId());
		    List<EmbryoCode> embryoCode =treatment.getCodes().getEmbryoCode();

		}*/
		treatmentDto.setAoEmbryoCodeDto(aoEmbryoCodeDto);
		return treatmentDto;
	}
	
	
	@Override
	public Treatment updateTreatmentEntity(TreatmentDto treatmentDto) {

		Treatment treatment=treatmentDao.findOne(treatmentDto.getTreatmentID());
		treatment.setEggs(treatmentDto.getOocytes());
		treatment.setResearch(treatmentDto.getResearch());
		treatment.setIncubator(treatmentDto.getIncubator());
		return treatmentDao.save(treatment);
	}
}
