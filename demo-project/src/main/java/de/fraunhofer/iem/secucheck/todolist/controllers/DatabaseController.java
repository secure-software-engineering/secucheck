package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.fraunhofer.iem.secucheck.todolist.model.Task;
import de.fraunhofer.iem.secucheck.todolist.model.TaskList;
import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.repository.TaskRepository;
import de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService;
import de.fraunhofer.iem.secucheck.todolist.service.UserService;
import de.fraunhofer.iem.secucheck.todolist.utils.*;

/*
 * This controller executes operations with the database. 
 */
@Controller
public class DatabaseController {

	private static SecurityUtils secUtils = new SecurityUtils();
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
    
    public DatabaseController()
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
						y.setName(secUtils.decrypt(y.getName(), decryptCipher));
	    				y.setDescription(secUtils.decrypt(y.getDescription(), decryptCipher));
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
    
    
    @RequestMapping(value= {"/urgent-tasks"}, method=RequestMethod.GET)
    public String showUrgentTasks(Model model, 
    		@RequestParam(value = "shortname", required = false) String shortname)
    				throws Exception
    {
        try
        {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Stream<Task> stream;
            
            if (shortname != null && !shortname.isEmpty()) {
            	stream = jdbcTemplate.query(secUtils.sanitizeForSQL(
            			getSearchQuery(shortname, user.getEmail())), 
            			getTaskRowMapper()).stream();
            } else {
            	// no urgent tasks found in the database
            	return "task/show_urgent-tasks";
            }
            
            ArrayList<Task> taskList = new ArrayList<Task>();
            stream.forEach(y-> {
    			if (y != null) {
    				try {
						y.setName(secUtils.decrypt(y.getName(), decryptCipher));
	    				y.setDescription(secUtils.decrypt(y.getDescription(), decryptCipher));
					} catch (Exception ex) {
						try { handleException(ex, EMPTY, true);}
						catch (Exception e) { }
					}
    				taskList.add(y);
    			}
    		});;
            
            model.addAttribute("shortname", shortname);
            model.addAttribute("username", user.getName());
            model.addAttribute("fullname", getFullName(shortname));
            model.addAttribute("location", getFullName(shortname));
            model.addAttribute("item", new Task());
            model.addAttribute("items", new TaskList(taskList));
            
            return "task/show_urgent-tasks";
        }
        catch (Exception ex)
        {
        	return handleException(ex, EMPTY, true);
        }
    }   
    
    
    public String getFullName(String shortname) {

        try {
            Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:USERS", "admin", "pass");
            Statement myStatement = myConnection.createStatement();

            ResultSet queryResult = myStatement.executeQuery("SELECT * FROM USERS where EID = " + shortname);
            return queryResult.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return "";
    }
    
    public String getLocation(String userID) {

        try {
            Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:LOCATIONS", "admin", "pass");
            Statement myStatement = myConnection.createStatement();

            ResultSet queryResult = myStatement.executeQuery("SELECT * FROM LOCATIONS where EID = " + userID);
            return queryResult.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return "";
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

    /*
     * Prepares an SQL query to get all tasks for given user and search pattern. 
     */
    private String getSearchQuery(String searchPattern, String userId)
    {
    	return "SELECT * FROM todolist.task WHERE user_id = '" + userId 
    			+ "' AND ( name LIKE '%" + searchPattern + "%' OR " +
    			" description LIKE '%" + searchPattern + "%');";
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
    
    private String handleException(Exception ex, String stateString,
    		boolean throwException) throws Exception
    {
    	logger.error("Message: " + ex.getMessage() + ". Stack trace: " 
    			+ secUtils.getStacktrace(ex) + ". Name: " + ex.toString() + ". State: " + stateString);
    	
    	if (throwException) throw ex;
    	
    	return null;
    }
    
}