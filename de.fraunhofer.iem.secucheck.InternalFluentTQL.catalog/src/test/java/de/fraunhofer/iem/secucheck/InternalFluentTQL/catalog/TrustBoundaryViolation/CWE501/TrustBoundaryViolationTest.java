package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.TrustBoundaryViolation.CWE501;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import static org.mockito.Mockito.when;

public class TrustBoundaryViolationTest {
    private static StringWriter MYSW = null;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the database server required for testing.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    private void setHttpServlet(String user, String pass) throws IOException {

        when(request.getParameter("user")).thenReturn(user);
        when(request.getParameter("pass")).thenReturn(pass);

        HttpSession mySession = new HttpSession() {
            private String user = null;

            public long getCreationTime() {
                return 0;
            }

            public String getId() {
                return "testing-id";
            }

            public long getLastAccessedTime() {
                return 0;
            }

            public ServletContext getServletContext() {
                return null;
            }

            public void setMaxInactiveInterval(int i) {

            }

            public int getMaxInactiveInterval() {
                return 0;
            }

            public HttpSessionContext getSessionContext() {
                return null;
            }

            public Object getAttribute(String s) {
                if ("user".equals(s))
                    return user;
                return null;
            }

            public Object getValue(String s) {
                return null;
            }

            public Enumeration<String> getAttributeNames() {
                return null;
            }

            public String[] getValueNames() {
                return new String[0];
            }

            public void setAttribute(String s, Object o) {
                if ("user".equals(s))
                    user = (String) o;
            }

            public void putValue(String s, Object o) {

            }

            public void removeAttribute(String s) {

            }

            public void removeValue(String s) {

            }

            public void invalidate() {

            }

            public boolean isNew() {
                return false;
            }
        };

        when(request.getSession()).thenReturn(mySession);

        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is trying Trust boundary violation
     * on an application that does not use sanitizer, therefore Trust boundary violation
     * happens.
     *
     * @throws IOException
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws IOException {
        setHttpServlet("dummyUser", "dummyPass");

        TrustBoundaryViolation myServlet = new TrustBoundaryViolation();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********" +
                "Results when attacker tries Trust boundary violation without sanitizer:**********");
        System.out.println("Requested the service that sets the user attribute in the session object");

        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.requestAfterAuthentication(request, response);

        result = MYSW.getBuffer().toString().trim();
        System.out.println("\nTries to request some other trusted service from the application:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying Trust boundary violation
     * on an application that uses sanitizer, therefore Trust boundary violation
     * is avoided.
     *
     * @throws IOException
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() throws IOException {
        setHttpServlet("dummyUser", "dummyPass");

        TrustBoundaryViolation myServlet = new TrustBoundaryViolation();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********" +
                "Results when attacker tries Trust boundary violation with sanitizer:**********");
        System.out.println("Requested the service that sets the user attribute in the session object");

        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.requestAfterAuthentication(request, response);

        result = MYSW.getBuffer().toString().trim();
        System.out.println("\nTries to request some other trusted service from the application:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is accessing the server
     * on an application that uses sanitizer and behaves normally. If the legitimate user provides the invalid password or
     * username still trust boundary violation can happen.
     *
     * @throws IOException
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws IOException {
        setHttpServlet("admin", "mypass");

        TrustBoundaryViolation myServlet = new TrustBoundaryViolation();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********" +
                "Results when normal user accessing the server without sanitizer:**********");
        System.out.println("Requested the service that sets the user attribute in the session object");

        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.requestAfterAuthentication(request, response);

        result = MYSW.getBuffer().toString().trim();
        System.out.println("\nTries to request some other trusted service from the application:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is accessing the server
     * on an application that uses sanitizer and behaves normally. If the legitimate user provides the invalid password or
     * username still trust boundary violation will be avoided.
     *
     * @throws IOException
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws IOException {
        setHttpServlet("admin", "mypass");

        TrustBoundaryViolation myServlet = new TrustBoundaryViolation();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********" +
                "Results when normal user accessing the server with sanitizer:**********");
        System.out.println("Requested the service that sets the user attribute in the session object");

        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.requestAfterAuthentication(request, response);

        result = MYSW.getBuffer().toString().trim();
        System.out.println("\nTries to request some other trusted service from the application:");
        System.out.println(result);
    }
}
