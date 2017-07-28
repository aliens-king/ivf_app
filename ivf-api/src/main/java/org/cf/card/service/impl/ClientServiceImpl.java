/*
 * Copyright (c) 2013 Manning Publications Co.
 *
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
 */
package org.cf.card.service.impl;

import java.util.Date;
import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Couple;
import org.cf.card.persistence.ClientDao;
import org.cf.card.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
@Service
@Transactional
public class ClientServiceImpl extends BaseServiceImpl<Client>implements ClientService {

	private ClientDao clientDao;

	@Autowired
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
		setGenericDao(clientDao);
	}

	// public Client createClient(Client client) {
	// notNull(client, "client can't be null");
	// return clientDao.save(client);
	// }
	//
	// public List<Client> getClients() {
	// Iterable<Client> iterable = clientDao.findAll();
	// Iterator<Client> iterator = iterable.iterator();
	// List<Client> clients = new ArrayList<>();
	// while (iterator.hasNext()) {
	// clients.add(iterator.next());
	// }
	// return clients;
	// }

	@Override
	public List<Client> getClientBySurname(String surname) {
		return clientDao.findBySurname(surname);
	}

	@Override
	public List<Client> getClientByMiddleName(String middleName) {
		return clientDao.findBySurname(middleName);
	}

	// public Client getClientById(Long id) {
	// return clientDao.findById(id);
	// }
	@Override
	public List<Client> getClientsByFirstName(String firstName) {
		return clientDao.findByFirstName(firstName);
	}

	@Override
	public List<Client> getClientsByDOB(Date dob) {
		return clientDao.findByDOB(dob);
	}

	@Override
	public List<Client> getClientsByGender(String gender) {
		return clientDao.findByGender(gender);
	}

	@Override
	public List<Client> getClientsByCouple(Couple couple) {
		return clientDao.findByCouple(couple);
	}

	@Override
	public List<Client> getClientBySurnameLike(String surname) {
		return clientDao.findBySurnameLike(surname);
	}

	@Override
	public List<Client> getClientsByFirstNameLike(String firstName) {
		return clientDao.findByFirstName(firstName);
	}

	@Override
	public List<Client> getClientsByDOBLike(Date dob) {
		return clientDao.findByDOBLike(dob);
	}

	@Override
	public List<Client> getClientsByGenderLike(String gender) {
		return clientDao.findByGenderLike(gender);
	}

	@Override
	public List<Client> findAllClientsByCoupleId(Long coupleId) {
		// TODO Auto-generated method stub
		return null;
	}

	// public Client updateClient(Client client) {
	// notNull(client, "client can't be null");
	// return clientDao.save(client);
	// }
	//
	// public void deleteClient(long id) {
	// notNull(id, "id can't be null");
	// clientDao.delete(id);
	// }

	/**
	 * deletes all clients. ONLY for development.
	 */
	// public void deleteAll() {
	// clientDao.deleteAll();
	// }
}
