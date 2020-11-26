package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.io.IOException;
import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() 
    {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authentication/login");
        return modelAndView;
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView registration() 
    {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("authentication/signup");
        return modelAndView;
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, 
    		HttpServletRequest request, HttpServletResponse response) {
            	
    	ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
    	        
        if (userExists == null) 
        {
            userService.saveUserDefault(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("authentication/signup");
        } else {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
        }
        
        modelAndView.setViewName("authentication/signup");
        return modelAndView;
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView registrationWithCode(HttpServletRequest request, HttpServletResponse response) 
    {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("authentication/signup");
        String code = request.getParameter("code");
        try {
			response.sendRedirect(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return modelAndView;
    }
    
    private User cleanUser(User user)
    {
    	user.setName(user.getName().replace("<", ""));
    	user.setName(user.getName().replace(">", ""));
    	return user;
    }
}