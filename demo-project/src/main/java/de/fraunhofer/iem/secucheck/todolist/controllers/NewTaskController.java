package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.io.File;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.fraunhofer.iem.secucheck.todolist.model.Task;
import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.repository.TaskRepository;
import de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService;
import de.fraunhofer.iem.secucheck.todolist.service.UserService;
import de.fraunhofer.iem.secucheck.todolist.utils.SecurityUtils;


@Controller
public class NewTaskController {

	private final String EMPTY = "";
	private static SecurityUtils secUtils = new SecurityUtils();
	
	private Cipher encryptCipher;
	
	@Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    
    @Autowired
    private DirectoryStorageService storageService;
	
	
	 @RequestMapping(value = "/add", method = RequestMethod.GET)
	    public String addTask(Model model) throws Exception
	    {
	    	try
	    	{
	    		model.addAttribute("newtask", new Task());
	            return "task/add_task";
	    	}
	    	catch(Exception ex)
	    	{
	    		return handleException(ex, EMPTY, true);
	    	}
	    }
	    
	    @RequestMapping(value = "/add", method = RequestMethod.POST)
	    public String saveTask(@ModelAttribute Task newTask, 
	    		@RequestParam(value="attachment") MultipartFile file,
	    		RedirectAttributes redirectAttributes) throws Exception 
	    {

	    	boolean cleanupFile = false,
	    			cleanupTask = false;
	    	
	    	User user = null;
	    	
	        try 
	    	{
	        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	            user = userService.findUserByEmail(auth.getName());
	            newTask.setUserId(user.getEmail());
	            newTask.setComplete(false);
	            newTask.setName(secUtils.encrypt(newTask.getName(), this.encryptCipher));
	            newTask.setDescription(secUtils.encrypt(newTask.getDescription(), this.encryptCipher));
	            taskRepository.save(newTask);
	            
	            cleanupTask = true;
	            
	            if (!file.isEmpty())
	            {	
	            	if (newTask.getFileName() == null || newTask.getFileName().isEmpty())
	            	{
	            		newTask.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
	            	}
	            	
	            	storageService.store(file, newTask, 
	            			user.getId() + File.separator + newTask.getId());
	            	
	            	cleanupFile = true;
	            	
	            	int bytes = storageService.getFileSizeOnSystem(newTask, 
	            					user.getId() + File.separator + newTask.getId());
	            	
	            	newTask.setFileSizeOnSystem(bytes);
	            }
	            else if (newTask.getFileName() != null)
	            {
	            	newTask.setFileName(null);
	            }

	            // Save once more with the update file info.
	            taskRepository.save(newTask);
	            
	            redirectAttributes.addFlashAttribute("message",
	    				"You successfully uploaded " + file.getOriginalFilename() + "!");           
	            
	            return "redirect:/tasks";
	    	}
	    	catch (Exception ex)
	    	{
	    		if (cleanupTask) { taskRepository.delete(newTask); }    		
	    		if (cleanupFile)
	    		{
	    			try { storageService.deleteAll(user.getId() + File.separator +
	    					newTask.getId() + File.separator + newTask.getFileName()); 
	    			} catch (Exception e) { }
	    		}
	    		
	    		return handleException(ex, user.toString() + ":" + newTask.toString(), true);
	    	}        
	    }
	    
	    private String correctFileName(String name)
	    {
	    	return name.replace("./", "");
	    }
	    
	    private String handleException(Exception ex, String stateString,
	    		boolean throwException) throws Exception
	    {
	    	
	    	if (throwException) throw ex;
	    	
	    	return null;
	    }
}
