package de.fraunhofer.iem.secucheck.todolist.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import de.fraunhofer.iem.secucheck.todolist.controllers.TaskController;

public class SecurityUtils {

	private static Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	private final String EMPTY = "";
	
	
	
	public String decrypt(String encrypted, Cipher decryptCipher) throws Exception 
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

	 public String encrypt(String string, Cipher encryptCipher) throws Exception 
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
	    
	    public String getStacktrace(Exception ex)
	    {
	    	StringWriter sw = new StringWriter();
	    	PrintWriter pw = new PrintWriter(sw);
	    	ex.printStackTrace(pw);
	    	return sw.toString();
	    }
	    
	    /**
	     * This method uses OWASP ESAPI to sanitize the user input for the SQL. See
	     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
	     * OWASP HTML-Sanitizer</a>
	     *
	     * @param employeeID Employee ID.
	     * @return sanitized employee ID.
	     * @throws IOException If fails to suppress ESAPI library output.
	     */
	    public String sanitizeForSQL(String employeeID) throws IOException {


	    	ClassLoader classLoader = getClass().getClassLoader();
	        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

	        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
	        ob.setResourceDirectory(file.getAbsolutePath());

	        String result = ESAPI.encoder().encodeForSQL(new OracleCodec(), employeeID);
	        
	        return result;
	    }
	
}
