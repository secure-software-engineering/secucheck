package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XSS.CWE79;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides the interface to access the database for the
 * {@link StoredXSS} class.
 *
 * @author Ranjith Krishnamurthy
 */
public class DatabaseForStoredXSS {
    /**
     * This method saves the comment into the database.
     *
     * @param comment Comment.
     * @throws ClassNotFoundException If the JDBC driver class not found.
     * @throws SQLException           If fails in SQL.
     */
    public static void saveComment(String comment) throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // Get the connection to database.
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:COMMENT", "test", "test");
        Statement statement = connection.createStatement();

        statement.executeUpdate("UPDATE LASTCOMMENT SET LASTCOMMENT = '" + comment + "' WHERE ID = 1;");

        // Commit the data to the database.
        connection.commit();
    }

    /**
     * This method reads the last comment from the database.
     *
     * @return Last comment.
     * @throws SQLException If fails in SQL.
     */
    public static String getLastComment() throws SQLException {
        Connection myConnection = DriverManager.getConnection("jdbc:hsqldb:mem:COMMENT", "test", "test");
        Statement myStatement = myConnection.createStatement();

        // Method executeQuery is a sink that perform sensitive operation and leaks the
        // data.
        ResultSet queryResult = myStatement.executeQuery("SELECT * FROM LASTCOMMENT where ID = 1");

        queryResult.next();
        return queryResult.getString(2);
    }
}
