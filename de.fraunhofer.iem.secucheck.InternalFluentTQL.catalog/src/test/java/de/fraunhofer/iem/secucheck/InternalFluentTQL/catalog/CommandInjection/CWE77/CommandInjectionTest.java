package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.CommandInjection.CWE77;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CommandInjectionTest {
    private static StringWriter MYSW = null;
    private static PrintWriter MYPW = null;
    private static String MESSAGE = "Hello from normal user";
    private static String MALICIOUSMESSAGEFORWINDOWS = "IAmEvil & dir";
    private static String MALICIOUSMESSAGEFORUNIX = "IAmEvil; ls -ltr";

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
        MockitoAnnotations.initMocks(this);
    }

    private void setHttpServlet(String message) throws IOException {
        when(request.getParameter("message")).thenReturn(message);

        MYSW = new StringWriter();
        MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is running command
     * on an application that does not use sanitizer, therefore command injection attack happens.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void attackerRunningTheCMDWithoutSanitizer() throws IOException, ServletException {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            setHttpServlet(MALICIOUSMESSAGEFORWINDOWS);
        } else {
            setHttpServlet(MALICIOUSMESSAGEFORUNIX);
        }


        CommandInjection myServlet = new CommandInjection();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to run the command without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying to run the command
     * on an application that uses sanitizer, therefore it avoids command injection attack.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void attackerRunningTheCMDWithSanitizer() throws IOException, ServletException {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            setHttpServlet(MALICIOUSMESSAGEFORWINDOWS);
        } else {
            setHttpServlet(MALICIOUSMESSAGEFORUNIX);
        }


        CommandInjection myServlet = new CommandInjection();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to run the command with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is trying to run the command
     * on an application that does not use sanitizer, and behaves normally.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void normalUserRunningTheCMDWithoutSanitizer() throws IOException, ServletException {
        setHttpServlet(MESSAGE);

        CommandInjection myServlet = new CommandInjection();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to run the command without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is trying to run the command
     * on an application that uses sanitizer, and behaves normally.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void normalUserRunningTheCMDWithSanitizer() throws IOException, ServletException {
        setHttpServlet(MESSAGE);

        CommandInjection myServlet = new CommandInjection();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to run the command with sanitizer:");
        System.out.println(result);
    }
}
