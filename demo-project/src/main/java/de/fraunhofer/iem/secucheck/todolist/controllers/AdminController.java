package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.model.UserList;
import de.fraunhofer.iem.secucheck.todolist.repository.UserRepository;
import de.fraunhofer.iem.secucheck.todolist.service.UserService;

@Secured("ROLE_ADMIN")
@Controller
public class AdminController {
	
    @Autowired
    private UserService userService;

    public AdminController() { }

    @RequestMapping("/users")
    public String listUsers(Model model) {
        ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();        
        model.addAttribute("users", new UserList(users));
        return "admin/users";        
    }
    
    // TODO: Add capability of editing roles.
    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUsers(@ModelAttribute UserList userList) {
    	for (User user : userList.getUserList()) {
            User toUpdate = userService.findUserByEmail(user.getEmail());
    		toUpdate.setActive(user.getActive());
    		toUpdate.setName(user.getName());
    		toUpdate.setLastName(user.getLastName());
    		userService.saveUserPlain(toUpdate);
        }
    	return "redirect:/users"; 
    }
}