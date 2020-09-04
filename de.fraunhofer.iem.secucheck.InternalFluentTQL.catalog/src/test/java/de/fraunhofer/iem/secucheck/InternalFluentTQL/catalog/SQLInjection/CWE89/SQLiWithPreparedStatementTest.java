package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89;

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

public class SQLiWithPreparedStatementTest {
    private static StringWriter MYSW = null;
    private static PrintWriter MYPW = null;
    private static String USERNAME = "user1";
    private static String MALICIOUSPASSWORD = "1\' OR '1' = '1";
    private static String PASSWORD = "gfs356cfr";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the database server required for testing.
     *
     * @throws ClassNotFoundException If the JDBCDriver class is not found.
     * @throws SQLException           If the processing of SQL fails.
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        DatabaseForJUnitTest.setUpDatabase();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * This method clears the database after the test case ran.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    @After
    public void clear() throws SQLException {
        DatabaseForJUnitTest.clearDatabase();
    }

    private void setHttpServlet(String user, String pass) throws IOException {
        when(request.getParameter("user")).thenReturn(user);
        when(request.getParameter("pass")).thenReturn(pass);

        MYSW = new StringWriter();
        MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is trying SQL Injection
     * on an application that does not uses sanitizer, therefore SQL Injection
     * happens.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws IOException, ServletException {
        setHttpServlet(USERNAME, MALICIOUSPASSWORD);

        SQLiWithPreparedStatement myServlet = new SQLiWithPreparedStatement();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to access the data without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user access the data from
     * database that does not uses sanitizer and behaves normally.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws IOException, ServletException {
        setHttpServlet(USERNAME, PASSWORD);

        SQLiWithPreparedStatement myServlet = new SQLiWithPreparedStatement();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to access the data without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker trying SQL injection on
     * application that uses sanitizer. In this case, it throws a
     * SQLSyntaxErrorException and avoids SQL Injection.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() throws IOException, ServletException {
        setHttpServlet(USERNAME, MALICIOUSPASSWORD);

        SQLiWithPreparedStatement myServlet = new SQLiWithPreparedStatement();
        String result = "";
        myServlet.doGetWithSanitizer(request, response);
        result = MYSW.getBuffer().toString().trim();

        System.out.println("\nResults when attacker tries to access the data with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user accessing the
     * database on application that uses sanitizer. In this case, it behaves
     * normally
     *
     * @throws SQLException     If the processing of SQL fails.
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws SQLException, IOException, ServletException {
        setHttpServlet(USERNAME, PASSWORD);

        SQLiWithPreparedStatement myServlet = new SQLiWithPreparedStatement();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to access the data with sanitizer:");
        System.out.println(result);
    }
}
