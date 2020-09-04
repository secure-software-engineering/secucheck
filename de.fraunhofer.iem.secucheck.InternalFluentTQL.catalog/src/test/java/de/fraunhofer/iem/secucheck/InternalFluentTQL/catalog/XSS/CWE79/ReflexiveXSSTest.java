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

public class ReflexiveXSSTest {
    private static StringWriter MYSW = null;
    private static final String MALICIOUSNAME = "<script>\r\n"
            + "				alert( \"Your cookie = \" + document.cookie )\r\n" + "			</script>";
    private static final String NAME = "Visitor1";
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the servlet to send the request
     *
     * @throws ClassNotFoundException If the JDBCDriver class is not found.
     * @throws SQLException
     */
    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        DatabaseForStoredXSSJUnit.setUpDatabase();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * This method clears the database after the test case ran.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    @After
    public void clear() throws SQLException {
        DatabaseForStoredXSSJUnit.clearDatabase();
    }

    private void setHttpServlet(String name) throws IOException {
        when(request.getParameter("name")).thenReturn(name);

        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is trying XSS on an
     * application that does not uses sanitizer, therefore XSS happens.
     *
     * @throws IOException If it fails to read the ESAPI properties file.
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws IOException {
        setHttpServlet(MALICIOUSNAME);
        ReflexiveXSS myServlet = new ReflexiveXSS();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to access the web page without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying XSS on an
     * application that uses sanitizer, therefore XSS does not happen.
     *
     * @throws IOException If it fails to read the ESAPI properties file.
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() throws IOException {
        setHttpServlet(MALICIOUSNAME);
        ReflexiveXSS myServlet = new ReflexiveXSS();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to access the web page with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is sending request to
     * website on an application that does uses sanitizer and behaves normally.
     *
     * @throws IOException      If it fails to read the ESAPI properties file.
     * @throws ServletException If there is a fail in Servlet.
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws IOException, ServletException {
        setHttpServlet(NAME);
        ReflexiveXSS myServlet = new ReflexiveXSS();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to access the web page with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is sending request to
     * website on an application that does not uses sanitizer and behaves normally.
     *
     * @throws IOException      If it fails to read the ESAPI properties file.
     * @throws ServletException If there is a fail in Servlet.
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws IOException, ServletException {
        setHttpServlet(NAME);
        ReflexiveXSS myServlet = new ReflexiveXSS();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to access the web page without sanitizer:");
        System.out.println(result);
    }

}
