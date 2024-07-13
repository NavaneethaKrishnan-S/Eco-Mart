package com.ecomart.EcoMartBackEnd.controller;

import com.ecomart.EcoMartBackEnd.exception.UserNotFoundException;
import com.ecomart.EcoMartBackEnd.service.UserService;
import com.ecomart.EcoMartCommon.entity.Role;
import com.ecomart.EcoMartCommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listAll(Model model){

        List<User> listUsers = userService.listAllUser();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/create/users")
    public String createNewUser(Model model){

        //getting all roles from table to display in the new user form.
        List<Role> allRoles =  userService.listAllRoles();

        User newUser = new User();
        newUser.setEnabled(true); //Default - setting the enabled value as true

        model.addAttribute("user", newUser);
        model.addAttribute("listOfRoles", allRoles);
        model.addAttribute("formTitle", "Create New User");

        return "create_user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){

        System.out.println(user);
        userService.save(user);

        redirectAttributes.addFlashAttribute("message",
                "The user has been saved successfully!");
        return "redirect:/users"; //it will redirect to the particular url
    }

    @GetMapping("/edit/users/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                                RedirectAttributes redirectAttributes,
                                    Model model){
        try{
            User existingUser = userService.get(id);
            List<Role> allRoles =  userService.listAllRoles();

            model.addAttribute("user", existingUser);
            model.addAttribute("formTitle", "Edit User (ID: " + id + ")");
            model.addAttribute("listOfRoles", allRoles);

            return "create_user_form";
        } catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }
}
