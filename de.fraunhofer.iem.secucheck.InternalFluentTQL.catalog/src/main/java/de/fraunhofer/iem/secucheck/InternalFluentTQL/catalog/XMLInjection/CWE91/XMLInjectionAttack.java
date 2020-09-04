package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XMLInjection.CWE91;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * This class contains the example of XML-Injection.
 * This example has been taken from
 * <a href="https://wiki.sei.cmu.edu/confluence/display/java/IDS16-J.+Prevent+XML+Injection" target="_blank">here</a>
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://wiki.sei.cmu.edu/confluence/display/java/IDS16-J.+Prevent+XML+Injection" target="_blank">XML-Injection example</a>
 */
public class XMLInjectionAttack extends HttpServlet {
    private final String xmlFile = "src/main/java/de/fraunhofer/iem/secucheck/InternalFluentTQL/catalog/XMLInjection/CWE91/myOrder.xml";

    /**
     * This method returns the order detail from the xml file.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws ParserConfigurationException If fails to create a new DocumentBuilder.
     * @throws IOException                  If fails to parse the xml.
     * @throws SAXException                 If fails to parse the xml.
     */
    public void reviewYourOrder(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory myDBFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder myDocBuilder = myDBFac.newDocumentBuilder();
        Document myDoc = myDocBuilder.parse(new File(xmlFile));
        Element xmlRootElement = myDoc.getDocumentElement();

        String resp = "Order Details: \n" +
                "Product: " +
                xmlRootElement.getElementsByTagName("name").item(0).getTextContent()
                        .replaceAll("\\s+", "") + "\n" +
                "Product ID: " +
                xmlRootElement.getElementsByTagName("productid").item(0).getTextContent()
                        .replaceAll("\\s+", "") + "\n" +
                "Quantity: " +
                xmlRootElement.getElementsByTagName("quantity").item(0).getTextContent()
                        .replaceAll("\\s+", "") + "\n" +
                "Email: " +
                xmlRootElement.getElementsByTagName("email").item(0).getTextContent()
                        .replaceAll("\\s+", "") + "\n" +
                "Price: " +
                xmlRootElement.getElementsByTagName("price").item(0).getTextContent()
                        .replaceAll("\\s+", "") + " Dollar\n";

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(resp);
    }

    /**
     * This method orders the new Smart Watch and stores the information in XML file that will be used later while
     * order processing. This method does not use sanitizer therefore this method is vulnerable to XML-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws ParserConfigurationException If fails to create a new DocumentBuilder.
     * @throws IOException                  If fails to parse the xml.
     * @throws SAXException                 If fails to parse the xml.
     * @throws TransformerException         If fails to get the Transformer object.
     */
    public void orderSmartWatchWithoutSafe(HttpServletRequest request, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        String quantity = request.getParameter("quantity");
        String email = request.getParameter("email");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<item>\n" +
                "   <name>\n" +
                "       Smart Watch\n" +
                "   </name>\n" +
                "   <productid>\n" +
                "       SW123\n" +
                "   </productid>\n" +
                "   <quantity>\n" +
                "       " + quantity + "\n" +
                "   </quantity>\n" +
                "   <email>\n" +
                "       " + email + "\n" +
                "   </email>\n" +
                "   <price>\n" +
                "       15000\n" +
                "   </price>\n" +
                "</item>";


        DocumentBuilderFactory myDBFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder myDocBuilder = myDBFac.newDocumentBuilder();
        Document xmlDoc = myDocBuilder.parse(new InputSource(new StringReader(xmlString)));

        // Write the parsed document to an xml file
        TransformerFactory myTFactory = TransformerFactory.newInstance();
        Transformer myTransformer = myTFactory.newTransformer();
        DOMSource source = new DOMSource(xmlDoc);

        StreamResult dest = new StreamResult(new File(xmlFile));
        myTransformer.transform(source, dest);

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("Order Placed.");
    }

    /**
     * This method orders the new Smart Watch and stores the information in XML file that will be used later while
     * order processing safely. This method uses sanitizer therefore this method avoids XML-Injection.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws ParserConfigurationException If fails to create a new DocumentBuilder.
     * @throws IOException                  If fails to parse the xml.
     * @throws SAXException                 If fails to parse the xml.
     * @throws TransformerException         If fails to get the Transformer object.
     */
    public void orderSmartWatchWithSafe(HttpServletRequest request, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        String quantity = request.getParameter("quantity");
        String email = request.getParameter("email");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<item>\n" +
                "   <name>\n" +
                "       Smart Watch\n" +
                "   </name>\n" +
                "   <productid>\n" +
                "       SW123\n" +
                "   </productid>\n" +
                "   <quantity>\n" +
                "       " + sanitizeForXML(quantity) + "\n" +
                "   </quantity>\n" +
                "   <email>\n" +
                "       " + sanitizeForXML(email) + "\n" +
                "   </email>\n" +
                "   <price>\n" +
                "       15000\n" +
                "   </price>\n" +
                "</item>";


        DocumentBuilderFactory myDBFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder myDocBuilder = myDBFac.newDocumentBuilder();
        Document xmlDoc = myDocBuilder.parse(new InputSource(new StringReader(xmlString)));

        // Write the parsed document to an xml file
        TransformerFactory myTFactory = TransformerFactory.newInstance();
        Transformer myTransformer = myTFactory.newTransformer();
        DOMSource source = new DOMSource(xmlDoc);

        StreamResult dest = new StreamResult(new File(xmlFile));
        myTransformer.transform(source, dest);

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("Order Placed.");
    }

    /**
     * This method is used to sanitize the user input for the XML.
     *
     * @param message A value to be stored in XML file.
     * @return Sanitized value.
     * @throws IOException If fails to suppress the library output.
     */
    public String sanitizeForXML(String message) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = "";
        result = ESAPI.encoder().encodeForXML(message);


        SuppressLibraryOutput.Restore();

        return result;
    }
}
