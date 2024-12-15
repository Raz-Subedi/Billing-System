package com.raz.billingsystem.repository;

import com.raz.billingsystem.model.Client;
import com.raz.billingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Integer> {

   List<Client> findByFnameAndLname(String fname, String lname);
    Client findByPhone(String phone);
}
