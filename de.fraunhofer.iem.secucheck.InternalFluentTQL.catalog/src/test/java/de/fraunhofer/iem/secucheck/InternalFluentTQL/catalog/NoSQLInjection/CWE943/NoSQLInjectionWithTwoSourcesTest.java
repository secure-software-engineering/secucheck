package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

public class NoSQLInjectionWithTwoSourcesTest {
    @Before
    public void setUpMongoDB() throws Exception {
        MongoDBForNOSQLIJUnit.setUpMongoDB();
        MongoDBForNOSQLIJUnit.setUpAuthenticationDocument();
    }

    @After
    public void clearMongoDB() throws Exception {
        MongoDBForNOSQLIJUnit.clearMongoDB();
    }

    /*
     * This test case test for the scenario where attacker changes the password without sanitizer and updates the password successfully.
     */
    @Test
    public void attackerAccessingSpringWithoutSafe() throws Exception {
        NoSQLInjectionWithTwoSources.setUserName("user2");
        NoSQLInjectionWithTwoSources.setOldPass("123\" | \"4\" != \"5");
        ;
        NoSQLInjectionWithTwoSources.setNewPass("IAmEvil");

        SuppressLibraryOutput.SupressOutput();
        String response = NoSQLInjectionWithTwoSources.changeMyPasswordWithoutSafe();
        SuppressLibraryOutput.Restore();
        System.out.println("\nResult when attacker tries to change the password without sanitizer\n" + response + "\n\n");
    }

    /*
     * This test case test for the scenario where attacker changes the password with sanitizer and fails to update the password successfully.
     */
    @Test
    public void attackerAccessingSpringWithSafe() throws Exception {
        NoSQLInjectionWithTwoSources.setUserName("user1");
        NoSQLInjectionWithTwoSources.setOldPass("123\" | \"4\" != \"5");
        ;
        NoSQLInjectionWithTwoSources.setNewPass("IAmEvil");

        SuppressLibraryOutput.SupressOutput();
        String response = NoSQLInjectionWithTwoSources.changeMyPasswordWithSafe();
        SuppressLibraryOutput.Restore();
        System.out.println("\nResult when attacker tries to change the password with sanitizer\n" + response + "\n\n");
    }

    /*
     * This test case test for the scenario where normal user changes the password without sanitizer and updates the password successfully.
     */
    @Test
    public void normalUserAccessingSpringWithoutSafe() throws Exception {
        NoSQLInjectionWithTwoSources.setUserName("user2");
        NoSQLInjectionWithTwoSources.setOldPass("sdb4567");
        NoSQLInjectionWithTwoSources.setNewPass("yvsfdget64537");

        SuppressLibraryOutput.SupressOutput();
        String response = NoSQLInjectionWithTwoSources.changeMyPasswordWithoutSafe();
        SuppressLibraryOutput.Restore();
        System.out.println("\nResult when normal user tries to change the password without sanitizer\n" + response + "\n\n");
    }

    /*
     * This test case test for the scenario where normal user changes the password with sanitizer and updates the password successfully.
     */
    @Test
    public void normalUserAccessingSpringWithSafe() throws Exception {
        NoSQLInjectionWithTwoSources.setUserName("user1");
        NoSQLInjectionWithTwoSources.setOldPass("ser645");
        NoSQLInjectionWithTwoSources.setNewPass("okbxjh767");

        SuppressLibraryOutput.SupressOutput();
        String response = NoSQLInjectionWithTwoSources.changeMyPasswordWithSafe();
        SuppressLibraryOutput.Restore();
        System.out.println("\nResult when normal user tries to change the password with sanitizer\n" + response + "\n\n");
    }
}
