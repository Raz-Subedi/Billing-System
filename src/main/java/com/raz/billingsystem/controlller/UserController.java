package com.raz.billingsystem.controlller;

import com.raz.billingsystem.model.User;
import com.raz.billingsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({"/","/login"})
    public String getLogin(){

        return "LoginForm";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute User usr, Model model, HttpSession session){

       User user = userService.userLogIn(usr.getEmail(),usr.getPassword());

       if(user!=null){
           session.setAttribute("validUser",user);


           model.addAttribute("uname",user.getFname());
           return "SearchClientForm";
       }
       model.addAttribute("error","Invalid Email or Password!!!");
        return "LoginForm";
    }

    @GetMapping("/signup")
    public String getSignIn(){

        return "SignupForm";
    }

    @PostMapping("/signup")
    public String postSignIn(@ModelAttribute User usr){
        userService.userSigIn(usr);

        return "LoginForm";
    }

    @GetMapping("/logout")
    public String postLogout(){

        return "LoginForm";
    }
}
