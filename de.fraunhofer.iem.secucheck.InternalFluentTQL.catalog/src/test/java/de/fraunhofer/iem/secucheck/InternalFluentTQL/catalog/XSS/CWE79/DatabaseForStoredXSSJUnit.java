package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XSS.CWE79;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseForStoredXSSJUnit {
    /**
     * This method set up the database for the Junit Test cases for Stored XSS.
     *
     * @throws ClassNotFoundException If the JDBC driver class not found.
     * @throws SQLException           If fails in SQL.
     */
    public static void setUpDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // Get the connection to database.
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:COMMENT", "test", "test");
        Statement statement = connection.createStatement();

        // Create a table for testing
        statement.execute("CREATE TABLE LASTCOMMENT (" + "ID INT NOT NULL, " + "lastComment VARCHAR(100) NOT NULL,"
                + "PRIMARY KEY (ID))");

        connection.commit();

        statement.executeUpdate("INSERT INTO LASTCOMMENT VALUES (1, 'Table is Empty')");

        // Commit the data to the database.
        connection.commit();
    }

    /**
     * This method clears the database after the test case ran.
     *
     * @throws SQLException If the processing of SQL fails.
     */
    public static void clearDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:COMMENT", "test", "test");
        Statement statement = connection.createStatement();

        // Clear the database.
        statement.executeUpdate("DROP TABLE LASTCOMMENT");
        connection.commit();
    }
}
