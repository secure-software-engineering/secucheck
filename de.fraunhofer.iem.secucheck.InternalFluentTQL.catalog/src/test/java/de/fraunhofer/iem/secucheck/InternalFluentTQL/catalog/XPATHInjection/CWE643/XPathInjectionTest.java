package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XPATHInjection.CWE643;

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

public class XPathInjectionTest {
    private static StringWriter MYSW = null;
    private static final String USERNAME = "user1";
    private static final String MALICIOUSPASS = "1' or '1' = '1";
    private static final String PASSWORD = "mySecret1";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the servlet required for testing.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void setHttpServlet(String uname, String pass) {
        when(request.getParameter("uname")).thenReturn(uname);
        when(request.getParameter("pass")).thenReturn(pass);

        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        try {
            when(response.getWriter()).thenReturn(MYPW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests for the scenario where attacker is trying XPath-Injection on
     * application that does not use sanitizer, therefore it retrieves all the sensitive information.
     */
    @Test
    public void attackerQueryingXPathWithoutSanitizer() {
        setHttpServlet(USERNAME, MALICIOUSPASS);


        XPathInjection myServlet = new XPathInjection();
        myServlet.printPasswordWithoutSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to run the command without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying XPath-Injection on
     * application that uses sanitizer, therefore it avoids XPath-Injection.
     *
     * @throws IOException If fails to load properties file for ESAPI.
     */
    @Test
    public void attackerQueryingXPathWithSanitizer() throws IOException {
        setHttpServlet(USERNAME, MALICIOUSPASS);


        XPathInjection myServlet = new XPathInjection();
        myServlet.printPasswordWithSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker tries to run the command with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying XPath-Injection on
     * application that uses sanitizer and behaves normally.
     *
     * @throws IOException If fails to load properties file for ESAPI.
     */
    @Test
    public void normalUserQueryingXPathWithSanitizer() throws IOException {
        setHttpServlet(USERNAME, PASSWORD);


        XPathInjection myServlet = new XPathInjection();
        myServlet.printPasswordWithSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user tries to run the command with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying XPath-Injection on
     * application that does not use sanitizer and behaves normally.
     *
     * @throws IOException If fails to load properties file for ESAPI.
     */
    @Test
    public void normalUserQueryingXPathWithoutSanitizer() throws IOException {
        setHttpServlet(USERNAME, PASSWORD);


        XPathInjection myServlet = new XPathInjection();
        myServlet.printPasswordWithoutSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal tries to run the command without sanitizer:");
        System.out.println(result);
    }
}
