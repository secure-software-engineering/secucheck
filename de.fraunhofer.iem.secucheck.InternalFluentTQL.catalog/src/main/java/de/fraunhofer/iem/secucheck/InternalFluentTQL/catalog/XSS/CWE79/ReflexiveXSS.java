package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XSS.CWE79;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * This class contains a simple Reflexive XSS attack.
 *
 * @author Ranjith Krishnamurthy
 */
public class ReflexiveXSS extends HttpServlet {

    private static final long serialVersionUID = 1L;

    //This method creates a response HTML page.
    private static String createHTML(String name) {
        String HTML = "<html>\r\n" +
                "	<head>\r\n" +
                "		<meta charset=\"UTF-8\">\r\n" +
                "		<title>Hello World</title>\r\n" +
                "	</head>\r\n" +
                "	<body>\r\n" +
                "		<script>\r\n" +
                "			var c = \"secret=this is secret cookie;\" \r\n" +
                "			document.cookie = c;\r\n" +
                "		</script>\r\n" + "		<pre>\r\n" +
                "			Hello " + name + "!! Welcome to myWebsite\r\n." +
                "		</pre>\r\n" +
                "	</body>\r\n" +
                "</html>";

        return HTML;
    }

    /**
     * This method contains a Reflexive XSS vulnerability.
     * This method reads user input from the servlet {@link javax.servlet.http.HttpServletRequest#getParameter(String) getParameter}
     * method and finally send this user input to a sink method
     * {@link java.io.PrintWriter#append append} that leads to a security vulnerability. If the attacker performs the Reflexive XSS
     * attack on this
     * method, then this method can potentially execute any attacker javascript on victim's browser.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to handle the PrintWriter for response.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Method getParameter is a source that takes input from user.
        String name = request.getParameter("name");

        String responseHTML = createHTML(name);

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //Method append is a sink, responseHTML will be displayed on client's browser. If there is a script then it will be
        //executed on client's browser.
        response.getWriter().append(responseHTML);
    }

    /**
     * This method avoids the Reflexive XSS attack shown in {@link #doGetWithoutSanitizer(HttpServletRequest, HttpServletResponse)} () doGetWithoutSanitizer} method by using the
     * OWASP ESAPI encoder. See
     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to handle the PrintWriter for response.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Method getParameter is a source that takes input from user.
        String name = request.getParameter("name");

        //Encodes all the HTML related data.
        String responseHTML = createHTML(sanitizeForHTML(name));

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //Since all the HTML related data are encoded, any scripts embedded by the attacker
        //will not be executed in client's browser.
        response.getWriter().append(responseHTML);
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input for the HTML.
     *
     * @param employeeID Employee ID.
     * @return sanitized employee ID.
     * @throws IOException If fails to suppress ESAPI library output.
     */
    public String sanitizeForHTML(String employeeID) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = ESAPI.encoder().encodeForHTML(employeeID);

        SuppressLibraryOutput.Restore();

        return result;
    }
}
