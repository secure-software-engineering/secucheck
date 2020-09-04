package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * This class contains a simple SQL-Injection example.
 *
 * @author Ranjith Krishnamurthy
 */
public class SimpleSQLInjection {
    /**
     * This method contains a SQL-Injection vulnerability.
     * This method reads user input from the method {@link java.util.Scanner#nextLine() nextLine} and finally sends this user input to a sink method
     * {@link java.sql.Statement#executeQuery(String) executeQuery} that leads to a security vulnerability. If the attacker performs the SQL-Injection attack on this
     * method, then this method returns employee information of all the employee.
     *
     * @return Returns the information of an employee with the empolyeID.
     */
    public static ResultSet getEmployeeInformationWithoutSanitizer() {
        Scanner mySC = new Scanner(System.in);

        // Method nextLine is a source that takes input from user.
        String employeeID = mySC.nextLine();

        mySC.close();

        try {
            Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");
            Statement myStatement = myConnection.createStatement();

            // Method executeQuery is a sink that perform sensitive operation and leaks the
            // data.
            ResultSet queryResult = myStatement.executeQuery("SELECT * FROM EMPLOYEE where EID = " + employeeID);
            return queryResult;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * This method avoids the SQL-Injection attack shown in {@link #getEmployeeInformationWithoutSanitizer() getEmployeeInformationWithoutSanitizer} method by using the
     * OWASP HTML Sanitizer. See
     * <a href="https://javadoc.io/doc/com.googlecode.owasp-java-html-sanitizer/owasp-java-html-sanitizer/latest/overview-summary.html">
     * OWASP HTML-Sanitizer</a>
     *
     * @return Returns the information of an employee with the empolyeID.
     * @throws SQLException If processing of SQL fails. If the invalid character is
     *                      passed to a SQL query.
     */
    public static ResultSet getEmployeeInformationWithSanitizer() throws SQLException {
        Scanner mySC = new Scanner(System.in);

        // Method nextLine is a source that takes input from user.
        String userInput = mySC.nextLine();

        mySC.close();

        PolicyFactory POLICY_DEFINITION = new HtmlPolicyBuilder().toFactory();

        // Method sanitize is a sanitizer that sanitizes the user input.
        String employeeID = POLICY_DEFINITION.sanitize(userInput);

        Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");
        Statement myStatement = myConnection.createStatement();

        // Method executeQuery is a sink that perform sensitive operation and leaks the
        // data.
        ResultSet queryResult = myStatement.executeQuery("SELECT * FROM EMPLOYEE where EID = " + employeeID);

        return queryResult;

    }
}
