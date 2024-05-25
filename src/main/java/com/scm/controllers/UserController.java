package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // user dashboard 

    @GetMapping("/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }
    
    // user profile 
    @GetMapping("/profile")
    public String userProfile(){
        return "user/profile";
    }
    // user add contact page 

    // user view contact 

    // user edit contact 

    // user delete contact 

    // user search contact 


}
