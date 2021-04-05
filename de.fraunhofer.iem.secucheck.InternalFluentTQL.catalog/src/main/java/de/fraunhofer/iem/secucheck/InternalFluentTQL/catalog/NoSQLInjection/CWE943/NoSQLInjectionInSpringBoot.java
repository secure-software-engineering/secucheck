package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943;

import java.util.regex.Pattern;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * This class contains the No-SQL-Injection example in spring boot.
 *
 * @author Ranjith Krishnamurthy
 */
@Controller
@RequestMapping("/webAPP")
public class NoSQLInjectionInSpringBoot {

    private static MongoClient myMongoClient;

    /**
     * Simple index page of the Spring boot application.
     *
     * @return Returns the string stating 'Spring Application is running.'
     */
    @RequestMapping("/index")
    public String index() {
        return "Spring Application is running.";
    }

    /**
     * Returns the information of the employee id requested by the user without any safe, this method contains No-SQL-Injection vulnerability.
     *
     * @param empID Employee id.
     * @return Information of the given employee id.
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public ResponseEntity<String> getMyInformation(@RequestParam(defaultValue = "") String empID) {
        getMongoClient();

        //Get the employee information from the MongoDB without applying sanitizeForMongoDB.
        String result = getInfo(empID, false);

        //returns the information as response entity with the status OK.
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    /**
     * Returns the information of the employee id requested by the user with safe, this method avoids No-SQL-Injection vulnerability.
     *
     * @param empID Employee id.
     * @return Information of the given employee id.
     */
    @RequestMapping(value = "/getInfoSafely", method = RequestMethod.POST)
    public ResponseEntity<String> getMyInformationSafely(@RequestParam(defaultValue = "") String empID) {
        getMongoClient();

        //Get the employee information from the MongoDB with applying sanitizeForMongoDB.
        String result = getInfo(empID, true);

        //returns the information as response entity with the status OK.
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    //Get the MongoDB client to interact with the MongoDB database.
    private void getMongoClient() {
        myMongoClient = new MongoClient("localhost", 8990);
    }

    //Returns the information of the employee.
    private String getInfo(String empID, boolean isSafe) {

        MongoDatabase employeeDB = myMongoClient.getDatabase("EMPLOYEES");

        MongoCollection<Document> employeeCollection = employeeDB.getCollection("EMPLOYEE",
                Document.class);

        //sanitizeForMongoDB method avoids the No-SQL-Injection.
        if (isSafe)
            empID = sanitizeForMongoDB(empID);


        BasicDBObject Query = new BasicDBObject();
        Query.put("$where", "this.EID == \"" + empID + "\"");

        String result = "";
        FindIterable<Document> queryResult = employeeCollection.find(Query);
        MongoCursor<Document> mCursor = queryResult.cursor();
        while (mCursor.hasNext())
            result += mCursor.next().toJson() + "\n";

        return result;
    }

    /**
     * This is the simple user defined sanitizer to sanitize the user input for the MongoDB. This is not recommended to use this
     * as a sanitizer, this is used here as an example.
     *
     * @param empID Employee ID
     * @return Sanitized employee id.
     */
    public String sanitizeForMongoDB(String empID) {
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
}
