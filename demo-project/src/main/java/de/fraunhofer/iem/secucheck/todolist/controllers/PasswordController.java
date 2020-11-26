package de.fraunhofer.iem.secucheck.todolist.controllers;

import java.io.File;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.dtm.ref.DTMNodeList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Secured("ROLE_ADMIN")
@Controller
public class PasswordController {

    File file = new File("users.xml");


	    public PasswordController() { }

	    @RequestMapping("/users/password")
	    public String updatePassword(HttpServletRequest request, HttpServletResponse response) {
	    	
	    	String uname = request.getParameter("uname");
	    	String escaped = request.getParameterValues("email")[0];
	    	escaped = escape(uname);
	    	
	        String query1 = "/users/user[userName='" + uname +
	                "']/name/text()";

	        uname = escaped;
	        try {
	            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	            domFactory.setNamespaceAware(true);
	            DocumentBuilder builder = domFactory.newDocumentBuilder();
	            Document doc = (Document) builder.parse(file.getAbsolutePath());
	            XPathFactory factory = XPathFactory.newInstance();
	            XPath xpath = factory.newXPath();
	            XPathExpression expr1 = xpath.compile(query1);
	            DTMNodeList name = (DTMNodeList) expr1.evaluate(doc, XPathConstants.NODESET);

	            String resp = "";	
	            
	            for (int i = 0; i < name.getLength(); i++) {
	                resp += name.item(i).getNodeValue() + " : " + generateNewRandom() + "\n";
	            }

	            response.setContentType("text/html;charset=UTF-8");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().append(resp);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return "admin/users/password";        
	    }

		private String escape(String uname) {
			// TODO implement sanitizer
			return "";
		}

		private String generateNewRandom() {
			int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 10;
		    Random random = new Random();
		 
		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();
			return generatedString;
		}
	    
}
