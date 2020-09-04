package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LDAPInjection.CWE90;

import com.unboundid.ldap.sdk.LDAPException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.zapodot.junit.ldap.EmbeddedLdapRule;
import org.zapodot.junit.ldap.EmbeddedLdapRuleBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class LdapInjectionTest {
    private final String DOMAIN_DSN = "dc=test,dc=com";
    private final String userLdif = "user.ldif";

    @Rule
    public EmbeddedLdapRule embeddedLdapRule = EmbeddedLdapRuleBuilder.newInstance().usingDomainDsn(DOMAIN_DSN)
            .importingLdifs(userLdif).build();

    private static StringWriter MYSW = null;
    private static final String userSN = "senior manager1";
    private static final String maliciousUserSN = "S*";
    private static final String pass = "password1";
    private static final String maliciousPass = "*";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    /**
     * This method set up the LDAP server required for testing.
     */
    @Before
    public void setUp() {
        try {
            LDAPServerUtils.ldapConnection = embeddedLdapRule.ldapConnection();
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        MockitoAnnotations.initMocks(this);
    }

    private void setHttpServlet(String userSN, String userPassword) throws IOException, LDAPException {

        when(request.getParameter("userSN")).thenReturn(userSN);
        when(request.getParameter("userPassword")).thenReturn(userPassword);

        MYSW = new StringWriter();
        PrintWriter MYPW = new PrintWriter(MYSW);

        when(response.getWriter()).thenReturn(MYPW);
    }

    /**
     * This test case tests for the scenario where attacker is trying LDAP-Injection
     * on an application that does not use sanitizer, therefore LDAP-Injection attack happens.
     *
     * @throws IOException
     */
    @Test
    public void attackerRunningTheCMDWithoutSanitizer() throws IOException, LDAPException {
        setHttpServlet(maliciousUserSN, maliciousPass);

        LdapInjection myServlet = new LdapInjection();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker accesses the LDAP server without sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where attacker is trying LDAP-Injection
     * on an application that uses sanitizer, therefore LDAP-Injection attack is avoided.
     *
     * @throws IOException
     */
    @Test
    public void attackerRunningTheCMDWithSanitizer() throws IOException, LDAPException {
        setHttpServlet(maliciousUserSN, maliciousPass);

        LdapInjection myServlet = new LdapInjection();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when attacker accesses the LDAP server with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is trying LDAP server
     * on an application that uses sanitizer and behaves normally.
     *
     * @throws IOException
     */
    @Test
    public void normalUserRunningTheCMDWithSanitizer() throws IOException, LDAPException {
        setHttpServlet(userSN, pass);

        LdapInjection myServlet = new LdapInjection();
        myServlet.doGetWithSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user accesses the LDAP server with sanitizer:");
        System.out.println(result);
    }

    /**
     * This test case tests for the scenario where normal user is trying LDAP server
     * on an application that does not use sanitizer and behaves normally.
     *
     * @throws IOException
     */
    @Test
    public void normalUserRunningTheCMDWithoutSanitizer() throws IOException, LDAPException {
        setHttpServlet(userSN, pass);

        LdapInjection myServlet = new LdapInjection();
        myServlet.doGetWithoutSanitizer(request, response);
        String result = MYSW.getBuffer().toString().trim();
        System.out.println("\nResults when normal user accesses the LDAP server without sanitizer:");
        System.out.println(result);
    }
}
