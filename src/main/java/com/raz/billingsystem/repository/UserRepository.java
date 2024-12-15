package com.raz.billingsystem.repository;

import com.raz.billingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmailAndPassword(String email,String Password);

}
