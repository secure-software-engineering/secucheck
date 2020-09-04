package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943;

import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

/**
 * This class contains the No-SQL-Injection with multiple sources in single Taint Flow.
 *
 * @author Ranjith Krishnamurthy
 */
public class NoSQLInjectionWithTwoSources {
    private static MongoClient myMongoClient;
    private static String userName = "";
    private static String oldPass = "";
    private static String newPass = "";

    //Get the MongoDB client to interact with the MongoDB database.
    private static void getMongoClient() {
        myMongoClient = new MongoClient("localhost", 8990);
    }

    //This is the simple user defined sanitizer to sanitize the user input for the MongoDB.

    /**
     * This is the simple user defined sanitizer to sanitize the user input for the MongoDB. This
     * is not recommended to use this method as a sanitizer, this is just an example here.
     *
     * @param empID Employee ID.
     * @return Sanitized employee id.
     */
    public static String sanitizeForMongoDB(String empID) {
        String sanitizedEmpID = empID;
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote("'"), "%27");
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote("\\"), "%5C");
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote(";"), "%3B");
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote("\""), "%22");
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote("{"), "%7B");
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote("}"), "%7D");
        sanitizedEmpID = sanitizedEmpID.replaceAll(Pattern.quote("$"), "%24");
        return sanitizedEmpID;
    }

    /*
     * Below 6 getter and setter methods are used to simulate the real world interaction with user
     * like command line arguments, Java swings textbox.
     */
    private static String getUserName() {
        return userName;
    }

    protected static void setUserName(String userName) {
        NoSQLInjectionWithTwoSources.userName = userName;
    }

    private static String getOldPassword() {
        return oldPass;
    }

    protected static void setOldPass(String oldPass) {
        NoSQLInjectionWithTwoSources.oldPass = oldPass;
    }

    private static String getNewPassword() {
        return newPass;
    }

    protected static void setNewPass(String newPass) {
        NoSQLInjectionWithTwoSources.newPass = newPass;
    }

    /**
     * This method changes the password requested by the user. this method contains No-SQL-Injection
     * vulnerability.
     *
     * @return password updated or not
     */
    public static String changeMyPasswordWithoutSafe() {
        String uName = getUserName();
        String oldPass = getOldPassword();
        String newPass = getNewPassword();

        getMongoClient();

        MongoDatabase credDB = myMongoClient.getDatabase("CREDDB");

        MongoCollection<Document> credCollection = credDB.getCollection("CRED",
                Document.class);

        BasicDBObject Filter = new BasicDBObject();
        Filter.put("$where", "(this.username == \"" + uName + "\") & (this.password == \"" + oldPass + "\")");

        BasicDBObject newPassDoc = new BasicDBObject();
        newPassDoc.put("password", newPass);

        BasicDBObject Set = new BasicDBObject();
        Set.put("$set", newPassDoc);

        UpdateResult res = credCollection.updateOne(Filter, Set);

        if (res.getMatchedCount() == 1)
            return userName + " password updated successfully to " + newPass;

        return "Could not update the password!!! Please try again";
    }

    /**
     * This method changes the password requested by the user. This method avoids No-SQL-Injection
     * vulnerability.
     *
     * @return password updated or not
     */
    public static String changeMyPasswordWithSafe() {
        String uName = sanitizeForMongoDB(getUserName());
        String oldPass = sanitizeForMongoDB(getOldPassword());
        String newPass = sanitizeForMongoDB(getNewPassword());

        getMongoClient();

        MongoDatabase credDB = myMongoClient.getDatabase("CREDDB");

        MongoCollection<Document> credCollection = credDB.getCollection("CRED",
                Document.class);

        BasicDBObject Filter = new BasicDBObject();
        Filter.put("$where", "(this.username == \"" + uName + "\") & (this.password == \"" + oldPass + "\")");

        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("password", newPass);

        BasicDBObject Set = new BasicDBObject();
        Set.put("$set", newDoc);

        UpdateResult res = credCollection.updateOne(Filter, Set);

        if (res.getMatchedCount() == 1)
            return userName + " password updated successfully to " + newPass;

        return "Could not update the password!!! Please try again";
    }
}
