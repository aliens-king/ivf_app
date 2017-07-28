package org.cf.card.model;

import org.cf.card.BaseTest;
import org.cf.card.controller.ClientController;
import org.cf.card.service.ClientService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.cf.card.model.matcher.ClientMatcher.clientEqual;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ClientTests extends BaseTest {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientController clientController;


    @Test
    public void addingClientSavesItCorrectly() {
        Client expected = constructClient();
        Client received = clientService.save(expected);
        assertThat(received, is(clientEqual(expected)));
        clientService.deleteById((received.getId()));
    }

    @Test
    public void deleteClientWorks() {
        Client created = constructClient();
        clientService.save(created);
        clientService.deleteById(created.getId());
        Client received = clientService.findOne(created.getId());
        assertThat(received, nullValue());
    }

    @Test
    public void findOne() {
        Client expected = clientController.addClient(constructClient());
        Client received = clientService.findOne(expected.getId());
        assertThat(received, is(clientEqual(expected)));
        clientService.deleteById(expected.getId());

    }

    @Test
    public void getClientsBySurname() {
        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientBySurname(expected.getSurname());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById(clientForDeletion.getId());

    }

    @Test
    public void getClientsByFirstName() {
        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientsByFirstName(expected.getFirstName());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));
    }

    @Test
    public void getClientsByDOB() {
        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientsByDOB(expected.getdOB());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));
    }

    @Test
    public void getClientsByGender() {

        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientsByGender(expected.getGender());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));
    }

    /*@Test
    public void getClientByIdLike() {
        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientByIdLike(expected.getId());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));
    }*/

    @Test
    public void getClientsBySurnameLike() {
        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientBySurnameLike(expected.getSurname());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById(clientForDeletion.getId());

    }

    @Test
    public void getClientsByFirstNameLike() {
        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientsByFirstNameLike(expected.getFirstName());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));
    }

//    @Test
//    public void getClientsByDOBLike() {
//        Client expected = constructClient();
//        Client clientForDeletion = clientService.save(expected);
//        List<Client> received = clientService.getClientsByDOBLike(expected.getdOB());
//        assertThat(received, hasItem(clientEqual(expected)));
//        clientService.deleteById((clientForDeletion.getId()));
//    }

    @Test
    public void getClientsByGenderLike() {

        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientsByGenderLike(expected.getGender());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));
    }


    @Test
    public void getClientsByCouple() {

        Client expected = constructClient();
        Client clientForDeletion = clientService.save(expected);
        List<Client> received = clientService.getClientsByCouple(expected.getCouple());
        assertThat(received, hasItem(clientEqual(expected)));
        clientService.deleteById((clientForDeletion.getId()));

    }
//
//    @Test
//    public void updateClient() {
//        Client expected = constructClient();
//        clientService.save(expected);
//        List<Client> received = clientService.getClientBySurname(expected.getSurname());
//        assertThat(received, hasItem(clientEqual(expected)));
//
//
//    }



    private Client constructClient() {

        Clipart clipart = new Clipart();
        //clipart.setSource("TestSource");

        Couple couple = new Couple();
        couple.setClipart(clipart);

        Client client = new Client();
        client.setSurname("Test");
        client.setGender("Male");

        Client client1 = new Client();

        client1.setSurname("TestClient");
        client1.setGender("Female");

        couple.setWoman(client1);
        couple.setMan(client);

        client.setCouple(couple);

        return client;
    }

}