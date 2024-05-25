package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.Imlp.SecurityCustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
// @EnableWebSecurity
public class SecurityConfig {

    
    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;

    @Autowired
    private SecurityCustomUserDetailsService securityCustomUserDetailsService;

    // user create & login using in moemory service 
    // @Bean
    // public UserDetailsService userDetailsService(){

    //     UserDetails user = User
    //     .withDefaultPasswordEncoder()
    //     .username("admin").password("admin").roles("ADMIN", "USER").build();

    //     UserDetails user2 = User
    //     .withUsername("Waseem")
    //     .password("waseem")
    //     .roles("NORMAL")
    //     .build();


    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user, user2);
    //     return inMemoryUserDetailsManager;

    // }

    // config of authentication provider 

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //object of UserDetailsService
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailsService);

        // object of PasswordEncoder 
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //securing routes using security fider chain

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        
        // configuration 
        httpSecurity.authorizeHttpRequests(autorize-> {
            // autorize.requestMatchers("/home", "/register", "/services", "/contact").permitAll(); 

            // we want to protect few url only 
            //so any url started from /user will be protected and rest will be open for all
            autorize.requestMatchers("/user/**").authenticated();
            // open for all 
            autorize.anyRequest().permitAll();

        });

        // will get security default page for login 
        // in - case if we required to create own login page 
        // then we need to config here 

        // httpSecurity.formLogin(Customizer.withDefaults());

        // own login form customizer here 
        httpSecurity.formLogin(formLogin->{

            formLogin.loginPage("/login");

            
            // url where login will process ie: /login like do-register 
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/dashboard");
            // formLogin.successForwardUrl("/user/dashboard");
            // formLogin.failureForwardUrl("/login?error=true");
            // //we are using email as username
            formLogin.usernameParameter("email"); 
            formLogin.passwordParameter("password");
      
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // OAuth2 configuration 
        // httpSecurity.oauth2Login(Customizer.withDefaults());
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oAuthenticationSuccessHandler);

            
            
                
            });
       

        return httpSecurity.build();

    }

}
