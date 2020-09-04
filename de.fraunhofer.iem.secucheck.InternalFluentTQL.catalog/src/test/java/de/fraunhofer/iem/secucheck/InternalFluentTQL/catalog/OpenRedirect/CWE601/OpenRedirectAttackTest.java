package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.OpenRedirect.CWE601;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class OpenRedirectAttackTest {
    private static StringWriter MYSW = null;
    private static final String MALICIOUSURL = "http://www.attacker.com/lookslikeTrustedLoginPage/thatTracksTheUserData";
    private static final String URL1 = "http://www.myApp.com/secureMyPage";
    private static final String URL2 = "trustedPage";


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

    private void setHttpServlet(String redirectLink) throws IOException {
        when(request.getParameter("redirectLink")).thenReturn(redirectLink);


        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker sent the malicious link through phishing mail using the
     * trusted server that does not use sanitizer, therefore open-redirect attack happens.
     *
     * @throws IOException
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws IOException {
        setHttpServlet(MALICIOUSURL);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        OpenRedirectAttack myServlet = new OpenRedirectAttack();
        myServlet.doGetWithoutSanitizer(request, resp);
        System.out.println("\nResults when attacker tries Open-Redirect attack without sanitizer:");
        System.out.println("Status Code = " + resp.getStatus() + ": " + resp.getContentAsString());
    }

    /**
     * This test case tests for the scenario where attacker sent the malicious link through phishing mail using the
     * trusted server that uses sanitizer, therefore open-redirect attack is avoided.
     *
     * @throws IOException
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() throws IOException {
        setHttpServlet(MALICIOUSURL);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        OpenRedirectAttack myServlet = new OpenRedirectAttack();
        myServlet.doGetWithSanitizer(request, resp);
        System.out.println("\nResults when attacker tries Open-Redirect attack with sanitizer:");
        System.out.println("Status Code = " + resp.getStatus() + ": " + resp.getContentAsString());
    }

    /**
     * This test case tests for the scenario where normal user sent the page redirection to the
     * trusted server that uses sanitizer and behaves normally.
     *
     * @throws IOException
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws IOException {
        setHttpServlet(URL2);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        OpenRedirectAttack myServlet = new OpenRedirectAttack();
        myServlet.doGetWithSanitizer(request, resp);
        System.out.println("\nResults when normal user tries to access the redirection server with sanitizer:");
        System.out.println("Status Code = " + resp.getStatus() + ": " + resp.getContentAsString());
    }

    /**
     * This test case tests for the scenario where normal user sent the page redirection to the
     * trusted server that does not use sanitizer and behaves normally.
     *
     * @throws IOException
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws IOException {
        setHttpServlet(URL1);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        OpenRedirectAttack myServlet = new OpenRedirectAttack();
        myServlet.doGetWithoutSanitizer(request, resp);
        System.out.println("\nResults when normal user tries to access the redirection server without sanitizer:");
        System.out.println("Status Code = " + resp.getStatus() + ": " + resp.getContentAsString());
    }
}
