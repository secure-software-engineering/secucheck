package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LDAPInjection.CWE90;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * This class contains the LDAP-Injection.
 * This example has been taken from the
 * <a href="https://wiki.sei.cmu.edu/confluence/spaces/flyingpdf/pdfpageexport.action?pageId=88487534" target="_blank">example.</a>
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://wiki.sei.cmu.edu/confluence/spaces/flyingpdf/pdfpageexport.action?pageId=88487534" target="_blank">LDAP-Injection example.</a>
 */
public class LdapInjection extends HttpServlet {
    private final String DOMAIN_DSN = "dc=test,dc=com";

    /**
     * This method returns the information stored in the LDIF from the LDAP server. This method does not use sanitizer, therefore
     * LDAP-Injection happens.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @author Ranjith Krishnamurthy
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) {

        String userSN = request.getParameter("userSN");
        String userPassword = request.getParameter("userPassword");

        String filter = "(&(sn=" + userSN + ")(userPassword=" + userPassword + "))";

        try {
            final SearchResult searchResult = LDAPServerUtils.ldapConnection
                    .search(DOMAIN_DSN, SearchScope.SUB, filter);

            List<SearchResultEntry> searchEntries = searchResult.getSearchEntries();

            String resp = "";

            if (searchEntries.size() > 0)
                resp = "Authentication succesfull!!!\n";
            else
                resp = "Invalid username or password!!!\n";

            for (int i = 0; i < searchEntries.size(); i++) {
                resp += searchEntries.get(i).getAttribute("cn").getValue();
                resp += " : ";
                resp += searchEntries.get(i).getAttribute("mail").getValue();
            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);

        } catch (LDAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the information stored in the LDIF from the LDAP server. This method uses sanitizer, therefore
     * it avoids LDAP-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) {

        String userSN = sanitizeForLDAP(request.getParameter("userSN"));
        String userPassword = sanitizeForLDAP(request.getParameter("userPassword"));

        String filter = "(&(sn=" + userSN + ")(userPassword=" + userPassword + "))";

        try {
            final SearchResult searchResult = LDAPServerUtils.ldapConnection
                    .search(DOMAIN_DSN, SearchScope.SUB, filter);

            List<SearchResultEntry> searchEntries = searchResult.getSearchEntries();

            String resp = "";

            if (searchEntries.size() > 0)
                resp = "Authentication succesfull!!!\n";
            else
                resp = "Invalid username or password!!!\n";


            for (int i = 0; i < searchEntries.size(); i++) {
                resp += searchEntries.get(i).getAttribute("cn").getValue();
                resp += " : ";
                resp += searchEntries.get(i).getAttribute("mail").getValue();
            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);

        } catch (LDAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input to avoid LDAP-injection.
     *
     * @param message Message to sanitize for LDAP.
     * @return sanitized message.
     * @see <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html#encodeForLDAP(java.lang.String)" target="_blank">encodeForLDAP</a>
     */
    public String sanitizeForLDAP(String message) {

        try {
            SuppressLibraryOutput.SupressOutput();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = "";

        result = ESAPI.encoder().encodeForLDAP(message);

        try {
            SuppressLibraryOutput.Restore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
