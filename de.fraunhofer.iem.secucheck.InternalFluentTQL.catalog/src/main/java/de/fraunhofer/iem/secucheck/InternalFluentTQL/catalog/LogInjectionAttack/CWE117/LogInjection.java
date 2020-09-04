package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LogInjectionAttack.CWE117;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class contains the example for Log-Injection.
 * This example has been taken from the
 * <a href="https://owasp.org/www-community/attacks/Log_Injection" target="_blank">OWASP Log-Injection example</a>.
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://owasp.org/www-community/attacks/Log_Injection" target="_blank">OWASP Log-Injection example</a>
 */
public class LogInjection extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogInjection.class.getName());
    //This is just for testing purpose to simulate the authentication.
    private static final String testUser = "admin";
    private static final String testPass = "myAdminPassword";

    /**
     * This method authenticate the username and password. This method log everything and does not use sanitizer,
     * therefore it contains Log-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) {

        // Method getParameter is a source that takes input from user.
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        if (testUser.equals(user)) {
            if (testPass.equals(pass)) {
                LOGGER.info("Successfully logged in by the user: " + user);
                //Do the remaining part after successful login.
            } else {
                LOGGER.info("Invalid password for the user: " + user);
                //Return the error message invalid password.
            }
        } else {
            LOGGER.info("Username " + user + " is invalid");
            //Return the error message invalid username.
        }
    }

    /**
     * This method authenticate the username and password. This method log everything and uses sanitizer,
     * therefore it avoids Log-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException       If fails to load ESAPI properties file.
     * @throws EncodingException If fails to encode the string to avoid Log-Injection.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) throws IOException, EncodingException {

        // Method getParameter is a source that takes input from user.
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        if (testUser.equals(user)) {
            if (testPass.equals(pass)) {
                LOGGER.info("Successfully logged in by the user: " + sanitizeForLog(user));
                //Do the remaining part after successful login.
            } else {
                LOGGER.info("Invalid password for the user: " + sanitizeForLog(user));
                //Return the error message invalid password.
            }
        } else {
            LOGGER.info("Username " + sanitizeForLog(user) + " is invalid");
            //Return the error message invalid username.
        }
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input for the URL. We have used encodeForURL, because
     * it encodes next line carriage return that avoids the Log-Injection. Best solution for Log-Injection is
     * input validation. See
     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @param user Username
     * @return Sanitized username.
     * @throws IOException       If fails to load ESAPI properties file.
     * @throws EncodingException If fails to encode the string to avoid Log-Injection.
     */
    public String sanitizeForLog(String user) throws IOException, EncodingException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = ESAPI.encoder().encodeForURL(user);

        SuppressLibraryOutput.Restore();

        return result;
    }
}
