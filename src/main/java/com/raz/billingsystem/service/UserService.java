package com.raz.billingsystem.service;

import com.raz.billingsystem.model.User;

public interface UserService{

    public void  userSigIn(User user);

    public User  userLogIn(String email,String password);

}
