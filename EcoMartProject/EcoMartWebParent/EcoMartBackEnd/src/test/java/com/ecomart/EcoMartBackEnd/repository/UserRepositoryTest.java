package com.ecomart.EcoMartBackEnd.repository;

import com.ecomart.EcoMartCommon.entity.Role;
import com.ecomart.EcoMartCommon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

//    @Test
    public void testCreateUserWithSingleRole() {

        Role roleSalesPerson = entityManager.find(Role.class, 1);
        User user = new User("naveensarana8@gmail.com", "password", "Navaneetha", "krishnan");
        user.addRole(roleSalesPerson);

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

//    @Test
    public void testCreateUserWithMultipleRoles() {

        User user = new User("hari@gmail.com", "password", "Hariharan", "saravana");

        Role roleEditor = new Role(2);
        Role roleAdmin = new Role(5);

        user.addRole(roleAdmin);
        user.addRole(roleEditor);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {

        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(System.out::println);
    }

    @Test
    public void testGetUserById() {

        User user = userRepository.findById(1).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

//    @Test
    public void testUpdateUserDetails() {

        User user = userRepository.findById(1).get();
        user.setEnabled(true);
        user.setEmail("naveensaravana812@gmail.com");

        userRepository.save(user);
    }

//    @Test
    public void testUpdateUserRoles() {

        User user = userRepository.findById(2).get();
        Role  roleEditor = new Role(2);
        Role roleAssistant = new Role(3);

        user.getRoles().remove(roleEditor);
        user.addRole(roleAssistant);

        userRepository.save(user);
    }

//    @Test
    public void testDeleteUser(){

        userRepository.deleteById(10);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "naveensaravana812@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }
}
