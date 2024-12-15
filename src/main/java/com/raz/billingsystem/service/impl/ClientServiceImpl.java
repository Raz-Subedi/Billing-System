package com.raz.billingsystem.service.impl;

import com.raz.billingsystem.model.Client;
import com.raz.billingsystem.repository.ClientRepository;
import com.raz.billingsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepo;
    @Override
    public void addClient(Client client) {
        clientRepo.save(client);

    }

    @Override
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    @Override
    public Client getClientById(int id) {
        return clientRepo.findById(id).get();
    }

    @Override
    public void updateClient(Client client) {
        clientRepo.save(client);
    }

    @Override
    public void deleteClientById(int id) {
        clientRepo.deleteById(id);
    }

    @Override
    public List<Client> searchClient(String fname, String lname) {
        return  clientRepo.findByFnameAndLname(fname,lname);
    }

    @Override
    public Client getClientByPhone(String phone) {
        return clientRepo.findByPhone(phone);
    }

}
