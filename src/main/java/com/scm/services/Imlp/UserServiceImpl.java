package com.scm.services.Imlp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.scm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.UserRepository;
import com.scm.services.UserService;

@Service
public class UserServiceImpl  implements UserService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        //generating user Id
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        //encrypting password before save
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set user role 
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
       return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
     User user2 = userRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
     user2.setName(user.getName());
     user2.setContact(user.getContact());
     user2.setAbout(user.getAbout());
     user2.setPassword(user.getPassword());
     user2.setEmail(user.getEmail()
     user2.setPhoneNumber(user.getPhoneNumber());
     user2.setProfilePic(user.getProfilePic());
     user2.setEnabled(user.isEnabled());
     user2.setEmailVerified(user.isEmailVerified());
     user2.setPhoneVerified(user.isPhoneVerified());
     user2.setProvider(user.getProvider());
     user2.setProviderUserId(user.getProviderUserId());

     User save = userRepository.save(user2);
     return Optional.ofNullable(save);
     
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        userRepository.delete(user2);
       
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepository.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {

       User user = userRepository.findByEmail(email).orElse(null);
       return user != null ? true : false;
        
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
