package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }


    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home Page Handler");
        // sending data to thymleaf view
        model.addAttribute("appName", "Smart Contact Manager 2.0");
        model.addAttribute("developedBy", "Waseem Khan");
        model.addAttribute("githubRepo", "https://github.com/WaseeKhan");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("isLogin", true);
        System.out.println("Inside about Handler");
        return "about";
    }

    @RequestMapping("/services")
    public String services(){
        System.out.println("Inside Services Handler");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact(){
        System.out.println("Inside Services Handler");
        return "contact";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("========Inside login Handler==========");
        return new String("login");
    }

    // @PostMapping("/authenticate")
    // public String PostLogin(){
    //     System.out.println("========Inside PostLogin Handler==========");
    //     return new String("login");
    // }
    @RequestMapping("/register")
    public String register(Model model){
        UserForm userForm = new UserForm();
        // userForm.setName("Waseem");
        // userForm.setAbout("WOWOWOWOW");
        // userForm.setEmail("wasee@dev.io");
        // userForm.setPassword("123");
        // userForm.setPhoneNumber("987654");
        model.addAttribute("userForm", userForm);

        return "register";
    }

    // processing  register request 
    //add @valid for validation
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult, HttpSession session){
        System.out.println("==============Processing registration===========");
        //fetch form data
        System.out.println(userForm);
        //validate  -- todo
        if (bindingResult.hasErrors()) {
            return "register"; 
        }
        //save to db
                // converting userForm to user with builder 
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .about(userForm.getAbout())
        // .password(userForm.getPassword())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("@{'/images/logo.svg'}")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setAbout(userForm.getAbout());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("@{'/images/logo.svg'}");
        User savedUser = userService.saveUser(user);
        System.out.println(savedUser);
        //message- registration done
        Message message =  Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);
        //redirect to login
        return "redirect:/register";

    }

}
