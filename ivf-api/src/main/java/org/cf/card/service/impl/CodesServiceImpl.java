package org.cf.card.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.cf.card.model.Client;
import org.cf.card.model.Codes;
import org.cf.card.model.Treatment;
import org.cf.card.persistence.CodesDao;
import org.cf.card.service.ClientService;
import org.cf.card.service.CodesService;
import org.cf.card.utility.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by Dell on 10/7/2014.
 */

@Service("codesService")
@Transactional
public class CodesServiceImpl extends BaseServiceImpl<Codes> implements CodesService {

	private CodesDao codesDao;
	
	@Autowired
	private CodeGenerator codeGenerator;

	@Autowired
	public void setCodesDao(CodesDao codesDao) {
		this.codesDao = codesDao;
		setGenericDao(codesDao);
	}

	@Autowired
	private ClientService clientService;

	@Override
	public Codes createNewCode(Codes code) {
		notNull(code, "Code can't be null");
		Long id = code.getClient().getId();
		// Client client = clientService.getClientById(id);
		Client client = clientService.findOne(id);
		code.setClient(client);
		List<Codes> codes = client.getCodes();
		codes.add(code);
		code.getClient().setCodes(codes);
		String codeValue = codeGenerator.getNext();
		if(null!=codeValue && !codeValue.isEmpty())
			code.setCode(codeValue);
		return codesDao.save(code);
	}

	@PostConstruct
	public void generateCode() {
		Long codeId = codesDao.findMaxCodeId();
		if (null != codeId) {
			Codes code = codesDao.findById(codeId);
			if(null!=code){
			codeGenerator.setLast(code.getCode());
			}
		}
	}

	// public List<Codes> getCodes() {
	// Iterable<Codes> iterable = codesDao.findAll();
	// Iterator<Codes> iterator = iterable.iterator();
	// List<Codes> codes = new ArrayList<>();
	//
	// while (iterator.hasNext()) {
	// codes.add(iterator.next());
	// }
	//
	// return codes;
	// }

	@Override
	public List<Codes> getCodesByTreatment(Treatment treatment) {
		return codesDao.findByTreatment(treatment);
	}

	// public Codes getCodesById(long id) {
	// return codesDao.findById(id);
	// }

	@Override
	public List<Codes> getCodesByClient(Client client) {
		return codesDao.findByClient(client);
	}

	@Override
	public List<Codes> getCodesByCode(String code) {
		return codesDao.findCodesByCode(code);
	}

	@Override
	public Codes findRecent() {
		List<Codes> codes = codesDao.findAll(new Sort(Sort.Direction.DESC, "treatment.startDate"));
		Codes code = null;
		if (codes.size() > 0) {
			code = codes.iterator().next();
		}
		return code;
	}

	@Override
	public List<Codes> findCodesByClientId(Long clientId) {
		return codesDao.findByClientId(clientId);
	}

	/* find latestCodeId by client id */
	@Override
	public Codes findLatestCodeByClientId(Long clientId) {
		return codesDao.findLatestCodeByClientId(clientId);
	}

	// public Codes updateCode(Codes code) {
	//
	// notNull(code, "Update code can't be null");
	// return codesDao.save(code);
	// }
	//
	// public void deleteCode(long id) {
	// notNull(id, "Delete code can't be null");
	// codesDao.delete(id);
	// }

}
