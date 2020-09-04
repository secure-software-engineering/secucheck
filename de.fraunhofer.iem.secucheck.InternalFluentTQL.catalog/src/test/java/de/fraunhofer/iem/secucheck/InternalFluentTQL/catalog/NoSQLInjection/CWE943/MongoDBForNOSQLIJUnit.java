package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943;

import java.io.IOException;
import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

public class MongoDBForNOSQLIJUnit {
    private static MongodExecutable myMongodExe;
    private static MongodProcess myMongod;
    private static MongoClient myMongo;

    public static MongoClient setUpMongoDB() throws IOException {
        SuppressLibraryOutput.SupressOutput();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        String dbIP = "localhost";
        int port = 8990;
        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.LATEST_NIGHTLY)
                .net(new Net(dbIP, port, Network.localhostIsIPv6())).build();
        myMongodExe = starter.prepare(mongodConfig);
        myMongod = myMongodExe.start();
        myMongo = new MongoClient(dbIP, port);

        SuppressLibraryOutput.Restore();

        return myMongo;
    }

    public static void clearMongoDB() throws IOException {
        SuppressLibraryOutput.SupressOutput();

        if (myMongod != null) {
            myMongo.close();
            myMongodExe.stop();
            myMongod.stop();
        }

        SuppressLibraryOutput.Restore();
    }

    public static void setUpAuthenticationDocument() throws IOException {
        SuppressLibraryOutput.SupressOutput();

        MongoDatabase employeeDB = myMongo.getDatabase("EMPLOYEES");
        employeeDB.createCollection("EMPLOYEE");
        MongoCollection<Document> employeeCollection = employeeDB.getCollection("EMPLOYEE",
                Document.class);

        employeeCollection.insertOne(new Document("EID", "1").append("name", "employee1").append("age", "26"));
        employeeCollection.insertOne(new Document("EID", "2").append("name", "employee2").append("age", "31"));
        employeeCollection.insertOne(new Document("EID", "3").append("name", "employee3").append("age", "24"));
        employeeCollection.insertOne(new Document("EID", "4").append("name", "employee4").append("age", "23"));
        employeeCollection.insertOne(new Document("EID", "5").append("name", "employee5").append("age", "21"));
        employeeCollection.insertOne(new Document("EID", "6").append("name", "employee6").append("age", "54"));
        employeeCollection.insertOne(new Document("EID", "7").append("name", "employee7").append("age", "43"));
        employeeCollection.insertOne(new Document("EID", "8").append("name", "employee8").append("age", "34"));
        employeeCollection.insertOne(new Document("EID", "9").append("name", "employee9").append("age", "23"));
        employeeCollection.insertOne(new Document("EID", "10").append("name", "employee10").append("age", "32"));
        employeeCollection.insertOne(new Document("EID", "11").append("name", "employee11").append("age", "43"));
        employeeCollection.insertOne(new Document("EID", "12").append("name", "employee12").append("age", "12"));


        MongoDatabase credDB = myMongo.getDatabase("CREDDB");
        credDB.createCollection("CRED");
        MongoCollection<Document> credCollection = credDB.getCollection("CRED",
                Document.class);

        credCollection.insertOne(new Document("username", "user1").append("password", "ser645"));
        credCollection.insertOne(new Document("username", "user2").append("password", "sdb4567"));
        credCollection.insertOne(new Document("username", "user3").append("password", "mjg5646"));
        credCollection.insertOne(new Document("username", "user4").append("password", "qwed456"));
        credCollection.insertOne(new Document("username", "user5").append("password", "nhgt567"));
        credCollection.insertOne(new Document("username", "user6").append("password", "5486785dfhd"));
        credCollection.insertOne(new Document("username", "user7").append("password", "xcfs8566"));
        credCollection.insertOne(new Document("username", "user8").append("password", "sdggh684556"));
        credCollection.insertOne(new Document("username", "user9").append("password", "nhh245"));
        credCollection.insertOne(new Document("username", "user10").append("password", "sdgh2414"));
        credCollection.insertOne(new Document("username", "user11").append("password", "46ysg3"));
        credCollection.insertOne(new Document("username", "user12").append("password", "adbsr7467"));

        SuppressLibraryOutput.Restore();

    }
}
