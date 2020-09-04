package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NoSQLInjectionInSpringBoot.class)
@ActiveProfiles({"test"})
public class NoSQLInjectionInSpringBootTest {
    private MockMvc mockMvc;


    @InjectMocks
    private NoSQLInjectionInSpringBoot noSQLInjectionInSpringBoot;

    @Before
    public void setUpMongoDB() throws Exception {
        MongoDBForNOSQLIJUnit.setUpMongoDB();
        MongoDBForNOSQLIJUnit.setUpAuthenticationDocument();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noSQLInjectionInSpringBoot).build();
    }

    @After
    public void clearMongoDB() throws Exception {
        MongoDBForNOSQLIJUnit.clearMongoDB();
    }

    /*
     * This method test for the scenario where attacker accesses the spring application without sanitizer. This returns information of all employees.
     */
    @Test
    public void attackerAccessingSpringWithoutSafe() throws Exception {

        SuppressLibraryOutput.SupressOutput();
        MvcResult response = mockMvc.perform(
                MockMvcRequestBuilders.post("/webAPP/getInfo")
                        .param("empID", "123\" || \"4\" == \"4"))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        SuppressLibraryOutput.Restore();
        System.out.println("Result when attacker accessing the Spring application without sanitizer\n" + result);
    }

    /*
     * This method test for the scenario where attacker accesses the spring application with sanitizer. This returns no information of employee.
     */
    @Test
    public void attackerAccessingSpringWithSafe() throws Exception {

        SuppressLibraryOutput.SupressOutput();
        MvcResult response = mockMvc.perform(
                MockMvcRequestBuilders.post("/webAPP/getInfoSafely")
                        .param("empID", "123\" || \"4\" == \"4"))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        SuppressLibraryOutput.Restore();
        System.out.println("Result when attacker accessing the Spring application with sanitizer\n" + result);
    }

    /*
     * This method test for the scenario where normal user accesses the spring application without sanitizer. This behaves normally.
     */
    @Test
    public void normalUserAccessingSpringWithoutSafe() throws Exception {

        SuppressLibraryOutput.SupressOutput();
        MvcResult response = mockMvc.perform(
                MockMvcRequestBuilders.post("/webAPP/getInfo")
                        .param("empID", "4"))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        SuppressLibraryOutput.Restore();
        System.out.println("Result when normal user accessing the Spring application without sanitizer\n" + result);
    }

    /*
     * This method test for the scenario where normal user accesses the spring application with sanitizer. This behaves normally.
     */
    @Test
    public void normalUserAccessingSpringWithSafe() throws Exception {

        SuppressLibraryOutput.SupressOutput();
        MvcResult response = mockMvc.perform(
                MockMvcRequestBuilders.post("/webAPP/getInfoSafely")
                        .param("empID", "4"))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        SuppressLibraryOutput.Restore();
        System.out.println("Result when normal user accessing the Spring application with sanitizer\n" + result);
    }

}
