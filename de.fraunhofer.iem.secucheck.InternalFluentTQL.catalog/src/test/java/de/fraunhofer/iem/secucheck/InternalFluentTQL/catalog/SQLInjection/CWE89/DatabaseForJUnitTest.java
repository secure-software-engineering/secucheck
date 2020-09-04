package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseForJUnitTest {
    /**
     * This method set up the database server required for testing.
     *
     * @throws ClassNotFoundException If the JDBCDriver class is not found.
     * @throws SQLException           If the processing of SQL fails.
     */
    public static void setUpDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // Get the connection to database.
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");
        Statement statement = connection.createStatement();

        // Create a table for testing
        statement.execute("CREATE TABLE EMPLOYEE (" + "EID INT NOT NULL, " + "name VARCHAR(25) NOT NULL,"
                + "age INT NOT NULL, " + "PRIMARY KEY (EID))");

        connection.commit();

        // Add some data to the above table
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (1, 'employee1', 26)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (2, 'employee2', 31)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (3, 'employee3', 24)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (4, 'employee4', 23)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (5, 'employee5', 21)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (6, 'employee6', 54)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (7, 'employee7', 43)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (8, 'employee8', 34)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (9, 'employee9', 23)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (10, 'employee10', 32)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (11, 'employee11', 43)");
        statement.executeUpdate("INSERT INTO EMPLOYEE VALUES (12, 'employee12', 12)");

        // Commit the data to the database.
        connection.commit();

        statement.execute("CREATE TABLE CRED (" + "EID INT NOT NULL, " + "USERNAME VARCHAR(25) NOT NULL,"
                + "PASSWORD VARCHAR(25) NOT NULL, " + "PRIMARY KEY (EID))");

        connection.commit();

        // Add some data to the above table
        statement.executeUpdate("INSERT INTO CRED VALUES (1, 'user1', 'gfs356cfr')");
        statement.executeUpdate("INSERT INTO CRED VALUES (2, 'user2', 'sdb4567')");
        statement.executeUpdate("INSERT INTO CRED VALUES (3, 'user3', 'mjg5646')");
        statement.executeUpdate("INSERT INTO CRED VALUES (4, 'user4', 'qwed456')");
        statement.executeUpdate("INSERT INTO CRED VALUES (5, 'user5', 'nhgt567')");
        statement.executeUpdate("INSERT INTO CRED VALUES (6, 'user6', '5486785dfhd')");
        statement.executeUpdate("INSERT INTO CRED VALUES (7, 'user7', 'xcfs8566')");
        statement.executeUpdate("INSERT INTO CRED VALUES (8, 'user8', 'sdggh684556')");
        statement.executeUpdate("INSERT INTO CRED VALUES (9, 'user9', 'nhh245')");
        statement.executeUpdate("INSERT INTO CRED VALUES (10, 'user10', 'sdgh2414')");
        statement.executeUpdate("INSERT INTO CRED VALUES (11, 'user11', '46ysg3')");
        statement.executeUpdate("INSERT INTO CRED VALUES (12, 'user12', 'adbsr7467')");

        // Commit the data to the database.
        connection.commit();
    }

    /**
     * This method clears the database after the test case ran.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    public static void clearDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:EMPLOYEES", "test", "test");
        Statement statement = connection.createStatement();

        // Clear the database.
        statement.executeUpdate("DROP TABLE EMPLOYEE");
        connection.commit();

        statement.executeUpdate("DROP TABLE CRED");
        connection.commit();
    }
}
