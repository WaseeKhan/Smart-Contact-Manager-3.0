package com.scm.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    // user dashboard 
    @GetMapping("/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }
    
    // user profile 
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication){
        return "user/profile";
    }

    // user add contact page 

    // user view contact 

    // user edit contact 

    // user delete contact 

    // user search contact 

}
