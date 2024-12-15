package com.raz.billingsystem.service.impl;

import com.raz.billingsystem.model.User;
import com.raz.billingsystem.repository.UserRepository;
import com.raz.billingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public void userSigIn(User user) {
        userRepo.save(user);
    }

    @Override
    public User userLogIn(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }


}
