package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XPATHInjection.CWE643;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.PathTraversalAttack.CWE22.PathTraversal;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

/**
 * This class contains the example of XPath-Injection.
 * This example has been taken from the Fortify Application Security.
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://vulncat.fortify.com/en/detail?id=desc.dataflow.cfml.xpath_injection#Java%2FJSPFortify" target="_blank">Application Security: XPath-Injection</a>
 */
public class XPathInjection extends HttpServlet {
    ClassLoader classLoader = PathTraversal.class.getClassLoader();
    File file = new File(classLoader.getResource("accounts.xml")
            .getFile());

    /**
     * This method prints the user's account number after authenticating the username and password. This
     * method does not use sanitizer, therefore, it has a XPath-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void printPasswordWithoutSafe(HttpServletRequest request, HttpServletResponse response) {
        String uname = request.getParameter("uname");
        String pass = request.getParameter("pass");

        String query1 = "/employees/employee[userName='" + uname +
                "' and password='" +
                pass +
                "']/name/text()";

        String query2 = "/employees/employee[userName='" + uname +
                "' and password='" +
                pass +
                "']/acct/text()";

        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(file.getAbsolutePath());
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr1 = xpath.compile(query1);
            DTMNodeList name = (DTMNodeList) expr1.evaluate(doc, XPathConstants.NODESET);
            XPathExpression expr2 = xpath.compile(query2);
            DTMNodeList acct = (DTMNodeList) expr2.evaluate(doc, XPathConstants.NODESET);

            String resp = "";

            if (name.getLength() > 0)
                resp = "Authenticated!!!\n";
            else
                resp = "Invalid username or password!!!\n";

            for (int i = 0; i < name.getLength(); i++) {
                resp += name.item(i).getNodeValue() + " : " + acct.item(i).getNodeValue() + "\n";
            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method prints the user's account number after authenticating the username and password. This
     * method uses sanitizer, therefore, it avoids a XPath-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to load properties file for ESAPI.
     */
    public void printPasswordWithSafe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uname = sanitizeForXPath(request.getParameter("uname"));
        String pass = sanitizeForXPath(request.getParameter("pass"));

        String query1 = "/employees/employee[userName='" + uname +
                "' and password='" +
                pass +
                "']/name/text()";

        String query2 = "/employees/employee[userName='" + uname +
                "' and password='" +
                pass +
                "']/acct/text()";

        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(file.getAbsolutePath());
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr1 = xpath.compile(query1);
            DTMNodeList name = (DTMNodeList) expr1.evaluate(doc, XPathConstants.NODESET);
            XPathExpression expr2 = xpath.compile(query2);
            DTMNodeList acct = (DTMNodeList) expr2.evaluate(doc, XPathConstants.NODESET);

            String resp = "";

            if (name.getLength() > 0)
                resp = "Authenticated!!!\n";
            else
                resp = "Invalid username or password!!!\n";

            for (int i = 0; i < name.getLength(); i++) {
                resp += name.item(i).getNodeValue() + " : " + acct.item(i).getNodeValue() + "\n";
            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input to avoid XPath-injection.
     *
     * @param message String values to sanitize for XPath-Injection.
     * @return sanitized String value.
     * @throws IOException If fails to load ESAPI properties file.
     * @see <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html#encodeForXPath(java.lang.String)" target="_blank">encodeForXPath</a>
     */
    public String sanitizeForXPath(String message) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = "";
        result = ESAPI.encoder().encodeForXPath(message);


        SuppressLibraryOutput.Restore();

        return result;
    }
}
