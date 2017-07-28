package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.Client;
import org.cf.card.model.Couple;
import org.cf.card.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:bulzanstefan@gmail.com">Stefan Bulzan</a>
 */
@RestController
@RequestMapping(value = "/clients")
public class ClientController {
	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/{coupleId}", method = GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody List<Client> findClientsByCoupleId(@PathVariable(value = "coupleId") Long coupleId) {
		return clientService.getClientsByCouple(new Couple(coupleId));
	}

	@RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
	public List<Client> getClients(@RequestParam(value = "surname", required = false) String surname,
			@RequestParam(value = "gender", required = false) String gender) {

		List<Client> clients = new ArrayList<>();

		clients.addAll(clientService.findAll());
		if (surname != null)
			clients = clientService.getClientBySurname(surname);

		if (gender != null)
			clients = clientService.getClientsByGender(gender);

		return clients;
	}

	@RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody Client addClient(@RequestBody Client client) {
		return clientService.save(client);
	}

	@RequestMapping(method = PUT, consumes = APPLICATION_JSON_VALUE)
	public @ResponseBody Client updateClient(@RequestBody Client client) {
		return clientService.update(client);
	}

	@RequestMapping(method = DELETE)
	public void deleteAll() {
		clientService.deleteAll();
	}

	@RequestMapping(method = DELETE, value = "{id}")
	public @ResponseBody void deleteClient(@PathVariable("id") long id) {

		clientService.deleteById(id);
	}

	@RequestMapping(method = GET, value = "{id}")
	public @ResponseBody Client getClientById(@PathVariable("id") long id) {
		return clientService.findOne(id);
	}

}
