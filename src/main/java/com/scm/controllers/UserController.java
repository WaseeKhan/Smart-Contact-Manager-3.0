package com.scm.controllers;

import java.security.Principal;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helper.Helper;
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
        String username = Helper.getEmailOfLoggedInUser(authentication);

        // String name = principal.getName();
       User user = userService.getUserByEmail(username);

        logger.info("User LoggedIn Name: {}:", user.getName());
        logger.info("User LoggedIn Email: {}:", user.getEmail());

        model.addAttribute("loggedInUser", user);
        return "user/profile";
    }
    // user add contact page 

    // user view contact 

    // user edit contact 

    // user delete contact 

    // user search contact 


}
