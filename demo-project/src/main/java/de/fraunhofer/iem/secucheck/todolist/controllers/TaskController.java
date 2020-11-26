
package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.fraunhofer.iem.secucheck.todolist.model.Task;
import de.fraunhofer.iem.secucheck.todolist.model.TaskList;
import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.repository.TaskRepository;
import de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService;
import de.fraunhofer.iem.secucheck.todolist.service.UserService;
import de.fraunhofer.iem.secucheck.todolist.utils.SecurityUtils;

@Controller
public class TaskController {

	private final String EMPTY = "";
	private static SecurityUtils secUtils = new SecurityUtils();
	private static Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	private Cipher encryptCipher,
	   decryptCipher;
	
	@Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private DirectoryStorageService storageService;
    
    public TaskController()
    {
        try { init();} catch (Exception e) {	}
    }


    private void init() throws Exception 
    {
        try 
        {
        	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec("My Encryption Key98".getBytes());
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        }
        catch (Exception ex) 
        {
        	handleException(ex, EMPTY, false);
        }
    }

   
    
    @RequestMapping(value = "/update", params = "done", method = RequestMethod.POST)
    public String markDoneTask(@ModelAttribute TaskList requestItems, HttpServletResponse response) throws Exception 
    {
    	try
    	{
    		Task newTask = getModifiedTask(requestItems);
    		newTask.setComplete(!newTask.isComplete());
    		newTask.setName(secUtils.encrypt(newTask.getName(), this.encryptCipher));
            newTask.setDescription(secUtils.encrypt(newTask.getDescription(), this.encryptCipher));
            response.sendRedirect("https://shownumberofremaing.tasks?num="+requestItems.getTaskList().size());
    		taskRepository.save(newTask);
    		
    		return "redirect:/tasks";
    	}
    	catch(Exception ex)
    	{
    		return handleException(ex, EMPTY, true);
    	}   	
    }
    
    
    @RequestMapping(value = "/update", params = "edit", method = RequestMethod.POST)
    public String updateTask(@ModelAttribute TaskList requestItems, Model model) 
    		throws Exception 
    {
    	try
    	{
    		Task oldTask = getModifiedTask(requestItems);
    		model.addAttribute("oldTask", oldTask);
			return "task/edit_task";
    	}
    	catch(Exception ex)
    	{
    		return handleException(ex, EMPTY, true);
    	}
    }
        
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editTask(@ModelAttribute Task oldTask, 
    		@RequestParam(value="attachment") MultipartFile file,
    		RedirectAttributes redirectAttributes) throws Exception 
    {
    	boolean cleanupFile = false;
    	User user = null;
    	
        try 
    	{
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user = userService.findUserByEmail(auth.getName());
            
            oldTask = taskRepository.findById(oldTask.getId()).get();
            
            if (!file.isEmpty())
            {	
            	if (oldTask.getFileName() != null && !oldTask.getFileName().isEmpty())
            	{
            		try { storageService.deleteAll(user.getId() + File.separator +
            				oldTask.getId() + File.separator + oldTask.getFileName()); 
        			} catch (Exception e) { }           	
            	}
            	else 
            	{  
            		oldTask.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
            	}            	
            	
            	storageService.store(file, oldTask, user.getId() + File.separator + oldTask.getId());
            	
            	cleanupFile = true;
            	
            	int bytes = storageService.getFileSizeOnSystem(oldTask, 
            					user.getId() + File.separator + oldTask.getId());
            	
            	oldTask.setFileSizeOnSystem(bytes);
            }

            // Save once more with the update file info.
            taskRepository.save(oldTask);
            
            redirectAttributes.addFlashAttribute("message",
    				"You successfully uploaded " + file.getOriginalFilename() + "!");           
            
            return "redirect:/tasks";
    	}
    	catch (Exception ex)
    	{
    		    		
    		if (cleanupFile)
    		{
    			try { storageService.deleteAll(user.getId() + File.separator +
    					oldTask.getId() + File.separator + oldTask.getFileName()); 
    			} catch (Exception e) { }
    			
    		}
    		
    		return handleException(ex, EMPTY, true);
    	}       
    }
    
    
    @RequestMapping(value = "/update", params = "delete", method = RequestMethod.POST)
    public String deleteTask(@ModelAttribute TaskList requestItems, 
    		RedirectAttributes redirectAttributes) throws Exception 
    {
    	boolean cleanupTask = false;
    	Task oldTask = null;
    	
    	try
    	{
    		oldTask = getModifiedTask(requestItems);
    		cleanupTask = true;
    		
    		if (oldTask.getFileName() != null && !oldTask.getFileName().isEmpty())
    		{
    			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user = userService.findUserByEmail(auth.getName());
                
    			storageService.deleteAll(user.getId() + File.separator +
    					oldTask.getId() + File.separator + oldTask.getFileName());
    		}
    			
    		taskRepository.deleteById(oldTask.getId());
    		
    		redirectAttributes.addFlashAttribute("message",
    				"You successfully deleted the task!");           
    		
    		return "redirect:/tasks";
    	}
    	catch(Exception ex)
    	{
    		if (cleanupTask)
    		{
    			taskRepository.delete(oldTask);
    		}
    		
    		return handleException(ex, EMPTY, true);
    	}
    }
    
    @RequestMapping(value = "/update", params = "deleteall", method = RequestMethod.POST)
    public String deleteAllTasks(@ModelAttribute TaskList requestItems, 
    		RedirectAttributes redirectAttributes) throws Exception 
    {	
    	try
    	{
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            
            for (Task task : taskRepository.findAll())
            {
            	if (task.getUserId() != null && task.getUserId().equals(user.getEmail())) {
            		
            		if (task.getFileName() != null && !task.getFileName().isEmpty())
            		{
            			storageService.deleteAll(user.getId() + File.separator +
            					task.getId() + File.separator + task.getFileName());
            		}
            		
            		// Possible collection manipulation during iteration issue.
            		taskRepository.deleteById(task.getId());
            	}
            }
            	
    		redirectAttributes.addFlashAttribute("message",
    				"You successfully deleted all the tasks!");           
    		
    		return "redirect:/tasks";
    	}
    	catch(Exception ex)
    	{	
    		return handleException(ex, EMPTY, true);
    	}
    }
    
    
    @RequestMapping(value = "/update", params = "download", method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadTaskAttachment(@ModelAttribute TaskList requestItems)
    		throws Exception 
    {   
    	Task oldTask = null;
    	
    	try
    	{
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
        
    		oldTask = getModifiedTask(requestItems);
    		
            Resource file = storageService.loadAsResource(oldTask,
            		(user.getId()+ File.separator +oldTask.getId()));

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
    				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
    	}
    	catch(Exception ex)
    	{
    		handleException(ex, EMPTY, false);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
    				"attachment; filename=\"" + oldTask.getFileName() + "\"").body(null);
    	}
    }
        
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public void redirectToExternalUrl(HttpServletResponse response,
    		@RequestParam(value = "name", required = true) String page) 
    				throws Exception {
        try 
        {
            response.sendRedirect(page);
        } 
        catch (Exception ex)
        {
        	handleException(ex, EMPTY, false);
        	try { response.sendError(404, ex.getMessage());}
        	catch (Exception e) { e.printStackTrace(); }
        	throw ex;
        }
    }


    
    private Task getModifiedTask(TaskList taskList) throws Exception
    {
    	if (taskList == null) throw new Exception();
    	if (taskList.getTaskList() == null) throw new Exception();
    	Task postTask = taskList.getTaskList().stream().
    			filter(y -> y.getId() != null).findFirst().get();
    	Optional<Task> orgTask = taskRepository.findById(postTask.getId());
		if (!orgTask.isPresent()) throw new Exception();
		Task task = orgTask.get();
		task.setName(secUtils.decrypt(task.getName(), this.decryptCipher));
		task.setDescription(secUtils.decrypt(task.getDescription(), this.decryptCipher));
		return orgTask.get();
    }
    

        
    
    private String getStacktrace(Exception ex)
    {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	return sw.toString();
    }
    
    private String handleException(Exception ex, String stateString,
    		boolean throwException) throws Exception
    {
    	logger.error("Message: " + ex.getMessage() + ". Stack trace: " 
    			+ getStacktrace(ex) + ". Name: " + ex.toString() + ". State: " + stateString);
    	
    	if (throwException) throw ex;
    	
    	return null;
    }
}