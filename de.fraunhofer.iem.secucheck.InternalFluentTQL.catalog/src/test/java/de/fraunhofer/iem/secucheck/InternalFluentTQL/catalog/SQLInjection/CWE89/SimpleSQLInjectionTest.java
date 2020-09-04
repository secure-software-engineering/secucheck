package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleSQLInjectionTest {
    /**
     * This method set up the database server required for testing.
     *
     * @throws ClassNotFoundException If the JDBCDriver class is not found.
     * @throws SQLException           If the processing of SQL fails.
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        DatabaseForJUnitTest.setUpDatabase();
    }

    /**
     * This method clears the database after the test case ran.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    @After
    public void clear() throws SQLException {
        DatabaseForJUnitTest.clearDatabase();
    }

    /**
     * This test case tests for the scenario where attacker is trying SQL Injection
     * on an application that does not uses sanitizer, therefore SQL Injection
     * happens.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    @Test
    public void attackerAccessingTheDataWithoutSanitizer() throws SQLException {
        // Malicious user input that is passed to the application.
        String empID = "1 OR 1 = 1";

        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(empID.getBytes());
        System.setIn(in);

        ResultSet myResult = SimpleSQLInjection.getEmployeeInformationWithoutSanitizer();

        System.setIn(sysInBackup);

        int rowCount = 0;
        System.out.println("\nResults when attacker tries to access the data without sanitizer:");
        while (myResult.next()) {
            System.out.println(myResult.getString(1) + " : " + myResult.getString(2) + " : " + myResult.getString(3));
            rowCount++;
        }

        // Assert if the application does not return exactly 12 record.
        assertEquals(12, rowCount);
    }

    /**
     * This test case tests for the scenario where normal user access the data from
     * database that does not uses sanitizer and behaves normally.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    @Test
    public void normalUserAccessingTheDataWithoutSanitizer() throws SQLException {
        // A valid user input.
        String empID = "3";

        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(empID.getBytes());
        System.setIn(in);

        ResultSet myResult = SimpleSQLInjection.getEmployeeInformationWithoutSanitizer();

        System.setIn(sysInBackup);

        int rowCount = 0;
        System.out.println("\nResults when normal user tries to access the data without sanitizer:");
        while (myResult.next()) {
            System.out.println(myResult.getString(1) + " : " + myResult.getString(2) + " : " + myResult.getString(3));
            rowCount++;
        }

        // Assert if the application return returns more than 1 record.
        assertEquals(1, rowCount);
    }

    /**
     * This test case tests for the scenario where attacker trying SQL injection on
     * application that uses sanitizer. In this case, it throws a
     * SQLSyntaxErrorException and avoids SQL Injection.
     */
    @Test
    public void attackerAccessingTheDataWithSanitizer() {
        // Malicious user input that is passed to the application.
        String empID = "1 OR 1 = 1";

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(empID.getBytes());
        System.setIn(in);

        int rowCount = 0;

        ResultSet myResult;
        try {
            myResult = SimpleSQLInjection.getEmployeeInformationWithSanitizer();

            System.out.println("\nResults when normal user tries to access the data without sanitizer:");
            while (myResult.next()) {
                System.out
                        .println(myResult.getString(1) + " : " + myResult.getString(2) + " : " + myResult.getString(3));
                rowCount++;
            }
        } catch (SQLException e) {
            System.out.println("\nResults when attacker tries to access the data with sanitizer:");
            System.out.println("Got SQLSyntaxErrorException.");
        }

        System.setIn(sysInBackup);

        // Assert if the application return returns more than 0 record.
        assertEquals(0, rowCount);
    }

    /**
     * This test case tests for the scenario where normal user accessing the
     * database on application that uses sanitizer. In this case, it behaves
     * normally
     *
     * @throws SQLException If the processing of SQL fails.
     */
    @Test
    public void normalUserAccessingTheDataWithSanitizer() throws SQLException {
        // Valid user input.
        String empID = "3";

        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(empID.getBytes());
        System.setIn(in);

        ResultSet myResult = SimpleSQLInjection.getEmployeeInformationWithSanitizer();

        System.setIn(sysInBackup);

        int rowCount = 0;

        System.out.println("\nResults when normal user tries to access the data with sanitizer:");
        while (myResult.next()) {
            System.out.println(myResult.getString(1) + " : " + myResult.getString(2) + " : " + myResult.getString(3));
            rowCount++;
        }

        // Assert if the application return returns more than 1 record.
        assertEquals(1, rowCount);
    }
}
