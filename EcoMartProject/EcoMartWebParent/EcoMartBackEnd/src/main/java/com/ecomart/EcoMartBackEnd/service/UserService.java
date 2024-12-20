package com.ecomart.EcoMartBackEnd.service;

import com.ecomart.EcoMartBackEnd.exception.UserNotFoundException;
import com.ecomart.EcoMartBackEnd.repository.RoleRepository;
import com.ecomart.EcoMartBackEnd.repository.UserRepository;
import com.ecomart.EcoMartCommon.entity.Role;
import com.ecomart.EcoMartCommon.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
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

        boolean isUpdatingUser = (user.getId() != null);

        if(isUpdatingUser){
            User existingUser = userRepository.findById(user.getId()).get();

            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            } else{
                encodePassword(user);
            }
        } else{
            encodePassword(user);
        }

        userRepository.save(user);
    }

    private void encodePassword(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        // If no user exists with the email, it's unique
        if (userByEmail == null) return true;

        // If creating a new user, email is not unique
        if (id == null) return false;

        // If editing a user, check if the email belongs to the same user
        return userByEmail.getId().equals(id);
    }

    public User getById(Integer id) throws UserNotFoundException {
        try{
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new UserNotFoundException("Couldn't find any user with ID " + id);
        }
    }


    public void delete(Integer id) throws UserNotFoundException{
        Long countById = userRepository.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Couldn't find any user with ID " + id);
        }

        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled){
        userRepository.updateEnabledStatus(id, enabled);
    }
}
