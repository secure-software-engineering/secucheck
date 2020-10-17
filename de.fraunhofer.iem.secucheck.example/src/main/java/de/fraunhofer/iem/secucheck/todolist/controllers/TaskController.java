
package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Stream;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

@Controller
public class TaskController {

	private final String EMPTY = "";
	
	private static Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	private Cipher encryptCipher,
				   decryptCipher;
	
	@Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DirectoryStorageService storageService;
    
    public TaskController()
    {
        try { init();} catch (Exception e) {	}
    }

    @RequestMapping(value= {"/tasks"}, method=RequestMethod.GET)
    public String showTasks(Model model, 
    		@RequestParam(value = "search", required = false) String pattern)
    				throws Exception
    {
        try
        {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Stream<Task> stream;
            
            if (pattern != null && !pattern.isEmpty()) {
            	stream = jdbcTemplate.query(
            			getSearchQuery(pattern, user.getEmail()), 
            			getTaskRowMapper()).stream();
            } else {
            	stream = ((ArrayList<Task>) taskRepository.findAll())
                		.stream().filter(y-> y != null 
                			&& y.getUserId() != null 
                			&& y.getUserId().equals(user.getEmail()));
            }
            
            ArrayList<Task> taskList = new ArrayList<Task>();
            stream.forEach(y-> {
    			if (y != null) {
    				try {
						y.setName(decrypt(y.getName()));
	    				y.setDescription(decrypt(y.getDescription()));
					} catch (Exception ex) {
						try { handleException(ex, EMPTY, true);}
						catch (Exception e) { }
					}
    				taskList.add(y);
    			}
    		});;
            
            model.addAttribute("search", pattern);
            model.addAttribute("username", user.getName());
            model.addAttribute("item", new Task());
            model.addAttribute("items", new TaskList(taskList));
            
            return "task/show_tasks";
        }
        catch (Exception ex)
        {
        	return handleException(ex, EMPTY, true);
        }
    }   

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
            newTask.setName(encrypt(newTask.getName()));
            newTask.setDescription(encrypt(newTask.getDescription()));
            taskRepository.save(newTask);
            
            cleanupTask = true;
            
            if (!file.isEmpty())
            {	
            	if (newTask.getFileName() == null || newTask.getFileName().isEmpty())
            	{
            		newTask.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
            	}
            	
            	storageService.store(file, newTask.getFileName(), 
            			user.getId() + File.separator + newTask.getId());
            	
            	cleanupFile = true;
            	
            	int bytes = storageService.getFileSizeOnSystem(newTask.getFileName(), 
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
    
    @RequestMapping(value = "/update", params = "done", method = RequestMethod.POST)
    public String markDoneTask(@ModelAttribute TaskList requestItems) throws Exception 
    {
    	try
    	{
    		Task newTask = getModifiedTask(requestItems);
    		newTask.setComplete(!newTask.isComplete());
    		newTask.setName(encrypt(newTask.getName()));
            newTask.setDescription(encrypt(newTask.getDescription()));
            
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
            	
            	storageService.store(file, correctFileName(oldTask.getFileName()), 
            			user.getId() + File.separator + oldTask.getId());
            	
            	cleanupFile = true;
            	
            	int bytes = storageService.getFileSizeOnSystem(oldTask.getFileName(), 
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
    		
            Resource file = storageService.loadAsResource(oldTask.getFileName(),
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
    
    private String decrypt(String encrypted) throws Exception 
    {
    	if (!encrypted.isEmpty()) 
    	{
    		// Decode base64 to get bytes    
            byte[] dec = Base64.getDecoder().decode(encrypted);
            byte[] utf8 = decryptCipher.doFinal(dec);
            encrypted = new String(utf8, "UTF8");
    	}

    	return encrypted;
    }

    private String encrypt(String string) throws Exception 
    {
        // Encode the string into bytes using utf-8
        if (!string.isEmpty())
        {
            byte[] utf8 = string.getBytes("UTF8");
            byte[] enc = encryptCipher.doFinal(utf8);
            string = Base64.getEncoder().encodeToString(enc);   
        }
        
        return string;
    }
    
    private String correctFileName(String name)
    {
    	return name.replace("./", "");
    }
        
    private String getSearchQuery(String searchPattern, String userId)
    {
    	return "SELECT * FROM todolist.task WHERE user_id = '" + userId 
    			+ "' AND ( name LIKE '%" + searchPattern + "%' OR " +
    			" description LIKE '%" + searchPattern + "%');";
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
		task.setName(decrypt(task.getName()));
		task.setDescription(decrypt(task.getDescription()));
		return orgTask.get();
    }
    
    private RowMapper<Task> getTaskRowMapper()
    {
    	return new RowMapper<Task>() {
			@Override
			public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
				Task task = new Task();
				task.setId(rs.getLong(1));
				task.setComplete(rs.getBoolean(2));
				task.setDeadline(rs.getDate(3));
				task.setDescription(rs.getString(4));
				task.setFileName(rs.getString(5));
				task.setFileSizeOnSystem(rs.getInt(6));
				task.setName(rs.getString(7));
				task.setUserId(rs.getString(8));
				return task;
			}
		};
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