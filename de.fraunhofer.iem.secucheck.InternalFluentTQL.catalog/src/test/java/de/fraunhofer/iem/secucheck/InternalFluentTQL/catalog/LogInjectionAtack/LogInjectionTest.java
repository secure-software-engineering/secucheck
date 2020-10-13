package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LogInjectionAtack;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LogInjectionAttack.CWE117.LogInjection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.owasp.esapi.errors.EncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class LogInjectionTest {
    private static final String maliciousUser = "abc is invalid\n" +
            "INFO: Successfully logged in by the user: admin\n" +
            "INFO: admin successfully transferred money to user1\n" +
            "INFO: Successfully logged out by the user: admin\n" +
            "INFO: Username abc";
    private static final String user = "admin";
    private static final String pass = "myAdminPassword";
    private static final String junKPass = "asdfas";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the servlet server for testing.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void setHttpServlet(String user, String pass) throws IOException {

        when(request.getParameter("user")).thenReturn(user);
        when(request.getParameter("pass")).thenReturn(pass);

        StringWriter MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is trying Log-Injection
     * on an application that does not use sanitizer, therefore Log-Injection
     * happens.
     *
     * @throws IOException If fails in response writer
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws IOException {
        System.out.println("\nResults when attacker tries Log-Injection without sanitizer:");
        setHttpServlet(maliciousUser, junKPass);

        LogInjection myServlet = new LogInjection();
        myServlet.doGetWithoutSanitizer(request, response);
    }

    /**
     * This test case tests for the scenario where attacker is trying Log-Injection
     * on an application that uses sanitizer, therefore Log-Injection
     * is avoided.
     *
     * @throws IOException       If fails in response writer
     * @throws EncodingException If fails to encode
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() throws IOException, EncodingException {
        System.out.println("\nResults when attacker tries Log-Injection with sanitizer:");
        setHttpServlet(maliciousUser, junKPass);

        LogInjection myServlet = new LogInjection();
        myServlet.doGetWithSanitizer(request, response);
    }

    /**
     * This test case tests for the scenario where attacker is trying Log-Injection
     * on an application that uses sanitizer, therefore Log-Injection
     * is avoided.
     *
     * @throws IOException       If fails in response writer
     * @throws EncodingException If fails to encode
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws IOException, EncodingException {
        System.out.println("\nResults when attacker tries Log-Injection with sanitizer:");
        setHttpServlet(user, pass);

        LogInjection myServlet = new LogInjection();
        myServlet.doGetWithSanitizer(request, response);
    }

    /**
     * This test case tests for the scenario where attacker is trying Log-Injection
     * on an application that uses sanitizer and behave normally.
     *
     * @throws IOException If fails in response writer
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws IOException {
        System.out.println("\nResults when attacker tries Log-Injection with sanitizer:");
        setHttpServlet(user, pass);

        LogInjection myServlet = new LogInjection();
        myServlet.doGetWithoutSanitizer(request, response);
    }


}
