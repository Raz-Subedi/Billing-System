package com.raz.billingsystem.service;

import com.raz.billingsystem.model.Client;

import java.util.List;

public interface ClientService {

    void addClient(Client client);

    List<Client> getAllClients();

    Client getClientById(int id);

    void updateClient(Client client);

    void deleteClientById(int id);

     List<Client> searchClient(String fname, String lname);

    Client getClientByPhone(String phone);


}
