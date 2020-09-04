package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.PathTraversalAttack.CWE22;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class PathTraversalTest {
    private static StringWriter MYSW = null;
    private static final String MALICIOUSFILENAME = "../validation.properties";
    private static final String FILENAME = "user1File.txt";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /*
     * This method set up the mock for servlet application for testing.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /*
     * This method sets up the servlet request and response for servlet testing.
     */
    private void setHttpServlet(String fileName) throws IOException {
        when(request.getParameter("fileName")).thenReturn(fileName);

        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /*
     * This test case tests for the scenario where attacker is trying Path traversal
     * on an application that does not use sanitizer, Path traversal
     * happens.
     */
    @Test
    public void attackerAccessingTheFileWithoutSanitizer() throws IOException {
        setHttpServlet(MALICIOUSFILENAME);

        PathTraversal myServlet = new PathTraversal();
        myServlet.doGetWithoutSanitizer(request, response);

        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to access the file without sanitizer: " +
                "with the given file name as " + MALICIOUSFILENAME);
        System.out.println(result);
    }

    /*
     * This test case tests for the scenario where attacker is trying Path traversal
     * on an application that uses sanitizer, therefore, Path traversal
     * is avoided here.
     */
    @Test
    public void attackerAccessingTheFileWithSanitizer() throws IOException {
        setHttpServlet(MALICIOUSFILENAME);

        PathTraversal myServlet = new PathTraversal();
        myServlet.doGetWithSanitizer(request, response);

        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to access the file with sanitizer: " +
                "with the given file name as " + MALICIOUSFILENAME);
        System.out.println(result);
    }

    /*
     * This test case tests for the scenario where normal user is trying to access a file
     * on an application that does not use sanitizer, and behaves normally.
     */
    @Test
    public void normalUserAccessingTheFileWithoutSanitizer() throws IOException {
        setHttpServlet(FILENAME);

        PathTraversal myServlet = new PathTraversal();
        myServlet.doGetWithoutSanitizer(request, response);

        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to access the file without sanitizer: " +
                "with the given file name as " + FILENAME);
        System.out.println(result);
    }

    /*
     * This test case tests for the scenario where normal user is trying to access a file
     * on an application that uses sanitizer, and behaves normally.
     */
    @Test
    public void normalUserAccessingTheFileWithSanitizer() throws IOException {
        setHttpServlet(FILENAME);

        PathTraversal myServlet = new PathTraversal();
        myServlet.doGetWithSanitizer(request, response);

        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to access the file with sanitizer: " +
                "with the given file name as " + FILENAME);
        System.out.println(result);
    }
}
