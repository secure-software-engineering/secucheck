package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XSS.CWE79;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

/**
 * This class contains a simple Stored XSS attack.
 *
 * @author Ranjith Krishnamurthy
 */
public class StoredXSS extends HttpServlet {

    private static final long serialVersionUID = 1L;

    //This method creates a response HTML page.
    private static String createHTML(String comment) {
        String HTML = "<html>\r\n" +
                "	<head>\r\n" +
                "		<meta charset=\"UTF-8\">\r\n" +
                "		<title>Last Comment</title>\r\n" +
                "	</head>\r\n" +
                "	<body>\r\n" +
                "		<pre>\r\n" +
                "			Last Comment : " + comment +
                "		</pre>\r\n" +
                "	</body>\r\n" +
                "</html>";

        return HTML;
    }

    /**
     * This method contains a Stored XSS vulnerability.
     * This method reads user comment from the servlet {@link javax.servlet.http.HttpServletRequest#getParameter(String) getParameter}
     * method and saves the comment in database.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException            If fails to handle the PrintWriter for response.
     * @throws ClassNotFoundException If JDBC driver is not found.
     * @throws SQLException           If fails to store comment.
     */
    public void saveMyComment(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ClassNotFoundException, SQLException {

        // Method getParameter is a source that takes input from user.
        String comment = request.getParameter("comment");

        //Stores the comment in database, comment might be malicious comment from the attacker.
        DatabaseForStoredXSS.saveComment(comment);

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("Comment Saved");
    }

    /**
     * This method respond to the user's HttpServletRequest by sending the last
     * comment saved in database. This does not use sanitizer therefore it has a
     * Stored XSS vulnerability.
     *
     * @param response HttpServletResponse sent by the user.
     * @throws IOException  If fails to load the JDBC driver file.
     * @throws SQLException If fail in SQL.
     */
    public void doGetWithoutSanitizer(HttpServletResponse response)
            throws IOException, SQLException {

        String comment = DatabaseForStoredXSS.getLastComment();

        String responseHTML = createHTML(comment);

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(responseHTML);
    }

    /**
     * This method respond to the user's HttpServletRequest by sending the last
     * comment saved in database. This uses sanitizer therefore it avoids a Stored
     * XSS vulnerability as shown in {@link #doGetWithoutSanitizer(HttpServletResponse)} () doGetWithoutSanitizer} method by using the
     * OWASP ESAPI encoder. See
     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @param response HttpServletResponse sent by the user.
     * @throws IOException  If fails to load the JDBC driver file.
     * @throws SQLException If fail in SQL.
     */
    public void doGetWithSanitizer(HttpServletResponse response)
            throws IOException, SQLException {

        String comment = DatabaseForStoredXSS.getLastComment();

        String responseHTML = createHTML(sanitizeForHTML(comment));

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(responseHTML);
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input for the HTML.
     *
     * @param comment User's comment.
     * @return sanitized User's comment.
     * @throws IOException If fails to suppress ESAPI library output.
     */
    public String sanitizeForHTML(String comment) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = ESAPI.encoder().encodeForHTML(comment);

        SuppressLibraryOutput.Restore();

        return result;
    }
}
