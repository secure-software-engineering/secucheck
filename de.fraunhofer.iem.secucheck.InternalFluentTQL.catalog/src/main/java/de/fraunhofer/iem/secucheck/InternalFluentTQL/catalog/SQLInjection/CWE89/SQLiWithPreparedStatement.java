package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * This class contains the example of SQL-Injection with PreparedStatement.
 * This example has been taken from the OWASP Benchmark: BenchmarkTest00037.
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://github.com/OWASP/Benchmark/blob/master/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00037.java" target="_blank">BenchmarkTest00037</a>
 */
public class SQLiWithPreparedStatement extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * This method checks for the credentials and display the credentials information to the authenticated user.
     * This contains the SQL-Injection, since this method does not use any sanitizer and it appends the
     * user input in a unsafe way to the sql query.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");


        String param = request.getParameter("pass");
        String userName = request.getParameter("user");

        try {
            Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");

            String sql = "SELECT * from CRED where USERNAME=? and PASSWORD='" + param + "'";

            PreparedStatement myStatement = myConnection.prepareStatement(sql,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            myStatement.setString(1, userName);

            ResultSet queryResult = myStatement.executeQuery();
            printQueryResults(queryResult, response);

            myConnection.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * This method checks for the credentials and display the credentials information to the authenticated user.
     * This avoids the SQL-Injection, since this method uses sanitizer.
     * Note that, its better to use ? in prepared statement rather than appending the sanitized user
     * input into the query.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");


        String param = request.getParameter("pass");
        String userName = request.getParameter("user");

        try {
            Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");

            String sql = "SELECT * from CRED where USERNAME=? and PASSWORD='" + sanitizeForSQL(param) + "'";

            PreparedStatement stmt = myConnection.prepareStatement(sql,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            stmt.setString(1, userName);

            ResultSet queryResult = stmt.executeQuery();
            printQueryResults(queryResult, response);

            myConnection.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /*
     * This method creates a simple HTML response with the output of the command.
     */
    private static void printQueryResults(ResultSet queryResult, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.write("<html>\r\n" +
                "	<head>\r\n" +
                "		<meta charset=\"UTF-8\">\r\n" +
                "		<title>Your Information: </title>\r\n" +
                "	</head>\r\n" +
                "	<body>\r\n" +
                "		<p>\r\n" +
                "			Info:<br>");

        try {
            if (!queryResult.next()) {
                out.write("username or password is invalid. Please try again.");
            } else {
                do {
                    out.write(queryResult.getString(2) + " : " + queryResult.getString(3) + "\n");
                } while (queryResult.next());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        out.write("<br>		</p>\r\n" +
                "	</body>\r\n" +
                "</html>");
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input for the SQL. See
     * <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @param password Password.
     * @return sanitized password.
     * @throws IOException If fails to suppress ESAPI library output.
     */
    public String sanitizeForSQL(String password) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = ESAPI.encoder().encodeForSQL(new OracleCodec(), password);

        SuppressLibraryOutput.Restore();

        return result;
    }
}
