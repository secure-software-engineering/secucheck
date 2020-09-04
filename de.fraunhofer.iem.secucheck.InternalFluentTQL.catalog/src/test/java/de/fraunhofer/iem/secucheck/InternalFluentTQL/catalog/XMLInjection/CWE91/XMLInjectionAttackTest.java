package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XMLInjection.CWE91;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class XMLInjectionAttackTest {
    private static StringWriter MYSW = null;
    private static final String Quantity = "1";
    private static final String Email = "myname@gmail.com";
    private static final String MaliciousEmail = "iamevil@gmail.com</email><price>100</price><email>iamevil@gmail.com";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void setHttpServlet(String email, String quantity) throws IOException {
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("quantity")).thenReturn(quantity);

        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is trying XML Injection
     * on an application that does not use sanitizer, therefore XML Injection
     * happens.
     *
     * @throws IOException
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        setHttpServlet(MaliciousEmail, Quantity);

        XMLInjectionAttack myServlet = new XMLInjectionAttack();
        myServlet.orderSmartWatchWithoutSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********Results when attacker tries XML Injection on application without sanitizer:**********");
        System.out.println("Requests to place the order: ");
        System.out.println(result + "\n");
        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.reviewYourOrder(request, response);
        result = MYSW.getBuffer().toString().trim();
        System.out.println("Requests the Order detail: ");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying XML Injection
     * on an application that uses sanitizer, therefore XML Injection
     * is avoided.
     *
     * @throws IOException
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        setHttpServlet(MaliciousEmail, Quantity);

        XMLInjectionAttack myServlet = new XMLInjectionAttack();
        myServlet.orderSmartWatchWithSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********Results when attacker tries XML Injection on application with sanitizer:**********");
        System.out.println("Requests to place the order: ");
        System.out.println(result + "\n");
        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.reviewYourOrder(request, response);
        result = MYSW.getBuffer().toString().trim();
        System.out.println("Requests the Order detail: ");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is ordering the smart watch
     * on an application that uses sanitizer and behaves normally.
     *
     * @throws IOException
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        setHttpServlet(Email, Quantity);

        XMLInjectionAttack myServlet = new XMLInjectionAttack();
        myServlet.orderSmartWatchWithSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********Results when normal user accessing the application with sanitizer:**********");
        System.out.println("Requests to place the order: ");
        System.out.println(result + "\n");
        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.reviewYourOrder(request, response);
        result = MYSW.getBuffer().toString().trim();
        System.out.println("Requests the Order detail: ");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is ordering the smart watch
     * on an application that does not use sanitizer and behaves normally.
     *
     * @throws IOException
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        setHttpServlet(Email, Quantity);

        XMLInjectionAttack myServlet = new XMLInjectionAttack();
        myServlet.orderSmartWatchWithoutSafe(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\n**********Results when normal user accessing the application without sanitizer:**********");
        System.out.println("Requests to place the order: ");
        System.out.println(result + "\n");
        MYSW.getBuffer().delete(0, MYSW.getBuffer().length());
        myServlet.reviewYourOrder(request, response);
        result = MYSW.getBuffer().toString().trim();
        System.out.println("Requests the Order detail: ");
        System.out.println(result);
    }
}
