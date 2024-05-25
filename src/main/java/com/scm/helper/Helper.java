package com.scm.helper;



import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
        // AuthenticationPrincipal principal = (AuthenticationPrincipal)authentication.getPrincipal();
        
        if(authentication instanceof OAuth2AuthenticationToken){

            var aAuth2AuthenticationToken= (OAuth2AuthenticationToken)authentication;
            var clientId =  aAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username ="";
            // Logic to get username for self sign in 
        if(clientId.equalsIgnoreCase("google")){
            System.out.println("Getting Email From Google");
            username = oauth2User.getAttribute("email").toString();


            
             // Logic to get username for google sign in 
        }else if(clientId.equalsIgnoreCase("github")){
            System.out.println("Getting Email From GitHub");
            username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString()+"@dev.io";
        }

        
       

        // Logic to get username for github sign in 

       
    return username;
    }else{
        System.out.println("Getting Email from Local Database");
        return authentication.getName();
    }
    }
}
