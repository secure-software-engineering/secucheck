
package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
    	
        logger.error("Exception during execution of the application", throwable);      
        
        String errorMessage = "Unknown error.";
        String errorStacktrace =  "Unknown stacktrace.";
        String errorToString = "Unknown type.";
        
        if (throwable != null)
        {
        	 errorMessage = throwable.getMessage();
             errorStacktrace =  getStacktrace(throwable);
             errorToString = throwable.toString();
        }
        
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorStack", errorStacktrace);
        model.addAttribute("errorToString", errorToString);
        return "error";
    }
    
    private String getStacktrace(Throwable ex)
    {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	return sw.toString();
    }
}