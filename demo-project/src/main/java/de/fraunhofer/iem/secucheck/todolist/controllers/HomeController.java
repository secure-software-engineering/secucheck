package de.fraunhofer.iem.secucheck.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.service.UserService;

@Controller
public class HomeController {
	
    @Autowired
    private UserService userService;
	
	@RequestMapping(value = {"/", "index", "home"})
    public ModelAndView index() {
		
    	ModelAndView modelAndView = new ModelAndView();		
        modelAndView.setViewName("index");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
    	if(!(auth instanceof AnonymousAuthenticationToken))
		{
    		User user = userService.findUserByEmail(auth.getName());
    		modelAndView.addObject("username", user.getName() + " " + user.getLastName() );
		}

		return modelAndView;
    }
}
