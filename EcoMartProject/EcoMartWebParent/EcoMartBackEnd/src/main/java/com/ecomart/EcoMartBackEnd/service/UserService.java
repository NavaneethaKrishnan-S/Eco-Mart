package com.ecomart.EcoMartBackEnd.service;

import com.ecomart.EcoMartBackEnd.exception.UserNotFoundException;
import com.ecomart.EcoMartBackEnd.repository.RoleRepository;
import com.ecomart.EcoMartBackEnd.repository.UserRepository;
import com.ecomart.EcoMartCommon.entity.Role;
import com.ecomart.EcoMartCommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAllUser(){
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listAllRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public void save(User user) {

        encodePassword(user);
        userRepository.save(user);
    }

    private void encodePassword(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(String email) {
        /*
        * If there is user with this email, it will give the user.
        * Then userByEmail will be Not null
        * Not null == null ==> false
        * If there is no user with this email, it will give the null.
        * Then userByEmail will be null
        * null == null ==> true
        * */
        User userByEmail = userRepository.getUserByEmail(email);
        return userByEmail == null;
    }

    public User get(Integer id) throws UserNotFoundException {
        try{
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new UserNotFoundException("Couldn't find any user with ID " + id);
        }

    }
}
