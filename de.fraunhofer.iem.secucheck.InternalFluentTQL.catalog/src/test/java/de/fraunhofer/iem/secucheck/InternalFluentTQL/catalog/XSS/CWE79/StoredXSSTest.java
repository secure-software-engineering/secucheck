package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XSS.CWE79;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StoredXSSTest {
    private static StringWriter MYSW = null;
    private static PrintWriter MYPW = null;
    private static String MALICIOUSCOMMENT = "<script>alert( \"Your cookie = \" + document.cookie )</script>";
    private static String COMMENT = "Hi, comment from user ABC";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the servlet to send the request
     *
     * @throws ClassNotFoundException If the JDBCDriver class is not found.
     * @throws SQLException           If fails in SQL.
     */
    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        DatabaseForStoredXSSJUnit.setUpDatabase();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * This method clears the database after the test case run.
     *
     * @throws SQLException If fails in SQL.
     */
    @After
    public void clear() throws SQLException {
        DatabaseForStoredXSSJUnit.clearDatabase();
    }

    /**
     * This method set up the HttpServletRequest with the comment to send the
     * request to server.
     *
     * @param comment Comment.
     * @throws IOException If fails in the HttpServletResponse.
     */
    private void setHttpServlet(String comment) throws IOException {
        when(request.getParameter("comment")).thenReturn(comment);

        MYSW = new StringWriter();
        MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This method sends the comment to server.
     *
     * @param comment Comment.
     * @throws IOException            If fails in HttpServletResponse.
     * @throws ClassNotFoundException If JDBC driver class is not found
     * @throws SQLException           If fails in SQL
     */
    private void performSendComment(String comment)
            throws IOException, ClassNotFoundException, SQLException {
        setHttpServlet(comment);
        StoredXSS myServlet = new StoredXSS();
        myServlet.saveMyComment(request, response);
    }

    /**
     * This method reads the last comment from the server that does not have
     * sanitizer.
     *
     * @return Last Comment.
     * @throws IOException  If fails in HttpServletResponse.
     * @throws SQLException If fails in SQL
     */
    private void performReadLastComment() throws IOException, SQLException {
        setHttpServlet("");
        StoredXSS myServlet = new StoredXSS();
        myServlet.doGetWithoutSanitizer(response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println(result);
    }

    /**
     * This method reads the last comment from the server that uses sanitizer.
     *
     * @return Last Comment.
     * @throws IOException  If fails in HttpServletResponse.
     * @throws SQLException If fails in SQL
     */
    private void performReadLastCommentSafely() throws IOException, SQLException {
        setHttpServlet("");
        StoredXSS myServlet = new StoredXSS();
        myServlet.doGetWithSanitizer(response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println(result);
    }

    /**
     * This test case for the scenario where the application contains Stored XSS
     * vulnerability.
     *
     * @throws IOException            If it fails to read the ESAPI properties file.
     * @throws SQLException           If fails in SQL.
     * @throws ClassNotFoundException If JDBC driver class not found.
     */
    @Test
    public void appWithoutSanitizer() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("**************Scenario in the Application without sanitizer for HTML******************");
        // User ABC adds the comment.
        System.out.println("User ABC adds his below comment: \n" + COMMENT);
        performSendComment(COMMENT);

        // User 1 reads the last comment.
        System.out.println("\nUser 1 reads the last comment: ");
        performReadLastComment();

        // User 2 reads the last comment.
        System.out.println("\nUser 2 reads the last comment: ");
        performReadLastComment();

        // Attacker adds the malicious comment.
        System.out.println("\nAttacker adds his below malicious comment: \n" + MALICIOUSCOMMENT);
        performSendComment(MALICIOUSCOMMENT);

        // User 1 reads the last comment.
        System.out.println("\nUser 3 reads the last comment: ");
        performReadLastComment();

        // User 2 reads the last comment.
        System.out.println("\nUser 4 reads the last comment: ");
        performReadLastComment();
        System.out.println("***********************************************************************************");
    }

    /**
     * This test case for the scenario where the application does not contain Stored
     * XSS vulnerability.
     *
     * @throws IOException            If it fails to read the ESAPI properties file.
     * @throws ServletException       If there is a fail in Servlet.
     * @throws SQLException           If fails in SQL.
     * @throws ClassNotFoundException If JDBC driver class not found.
     */
    @Test
    public void appWithSanitizer() throws IOException, ServletException, SQLException, ClassNotFoundException {
        System.out.println("**************Scenario in the Application with sanitizer for HTML******************");
        // User ABC adds the comment.
        System.out.println("User ABC adds his below comment: \n" + COMMENT);
        performSendComment(COMMENT);

        // User 1 reads the last comment.
        System.out.println("\nUser 1 reads the last comment: ");
        performReadLastCommentSafely();

        // User 2 reads the last comment.
        System.out.println("\nUser 2 reads the last comment: ");
        performReadLastCommentSafely();

        // Attacker adds the malicious comment.
        System.out.println("\nAttacker adds his below malicious comment: \n" + MALICIOUSCOMMENT);
        performSendComment(MALICIOUSCOMMENT);

        // User 1 reads the last comment.
        System.out.println("\nUser 3 reads the last comment: ");
        performReadLastCommentSafely();

        // User 2 reads the last comment.
        System.out.println("\nUser 4 reads the last comment: ");
        performReadLastCommentSafely();
        System.out.println("***********************************************************************************");
    }
}
