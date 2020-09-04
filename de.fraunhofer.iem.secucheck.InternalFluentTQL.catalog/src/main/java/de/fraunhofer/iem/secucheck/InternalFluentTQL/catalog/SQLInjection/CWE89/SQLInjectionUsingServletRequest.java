package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

/**
 * This class contains a SQL Injection example in Servlet.
 *
 * @author Ranjith Krishnamurthy
 */
public class SQLInjectionUsingServletRequest extends HttpServlet {

    /**
     * This method contains SQL-Injection vulnerability.
     * This method reads user input from the servlet {@link javax.servlet.http.HttpServletRequest#getParameter(String) getParameter}
     * method and finally send this user input to a sink method
     * {@link java.sql.Statement#executeQuery(String) executeQuery} that leads to a security vulnerability. If the attacker performs the SQL-Injection attack on this
     * method, then this method returns employee information of all the employee.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) {

        // Method getParameter is a source that takes input from user.
        String employeeID = request.getParameter("employeeID");

        try {
            Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");
            Statement myStatement = myConnection.createStatement();

            // Method executeQuery is a sink that perform sensitive operation and leaks the
            // data.
            ResultSet queryResult = myStatement.executeQuery("SELECT * FROM EMPLOYEE where EID = '" + employeeID + "'");

            String resp = "";
            while (queryResult.next()) {
                resp += queryResult.getString(1) + " : " + queryResult.getString(2) + " : " + queryResult.getString(3)
                        + "\n";

            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method avoids the SQL-Injection attack shown in {@link #doGetWithSanitizer(HttpServletRequest, HttpServletResponse) doGetWithoutSanitizer} method by using the
     * OWASP ESAPI encoder. See
     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException  If fails to load JDBC driver for the database.
     * @throws SQLException If attacker performs SQL-Injection attack.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {

        // Method getParameter is a source that takes input from user.
        String userInput = request.getParameter("employeeID");

        // Sanitizer for SQL
        String employeeID = sanitizeForSQL(userInput);

        Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");
        Statement myStatement = myConnection.createStatement();

        // Method executeQuery is a sink that perform sensitive operation and leaks the
        // data.
        ResultSet queryResult = myStatement.executeQuery("SELECT * FROM EMPLOYEE where EID = '" + employeeID + "'");

        String resp = "";
        while (queryResult.next()) {
            resp += queryResult.getString(1) + " : " + queryResult.getString(2) + " : " + queryResult.getString(3)
                    + "\n";

        }

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(resp);
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input for the SQL. See
     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @param employeeID Employee ID.
     * @return sanitized employee ID.
     * @throws IOException If fails to suppress ESAPI library output.
     */
    public String sanitizeForSQL(String employeeID) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = ESAPI.encoder().encodeForSQL(new OracleCodec(), employeeID);

        SuppressLibraryOutput.Restore();

        return result;
    }
}
