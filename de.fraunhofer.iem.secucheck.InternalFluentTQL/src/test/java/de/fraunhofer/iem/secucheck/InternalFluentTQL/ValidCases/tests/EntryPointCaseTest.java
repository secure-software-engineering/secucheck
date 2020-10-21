package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.EntryPointsCase;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnalysisEntryPointAnnotation;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EntryPointCaseTest {
    @Test
    public void test1() throws FluentTQLException {
        EntryPointsCase entryPointsCase = new EntryPointsCase();

        ProcessAnalysisEntryPointAnnotation processAnalysisEntryPointAnnotation = new ProcessAnalysisEntryPointAnnotation();
        List<String> entryPoints = processAnalysisEntryPointAnnotation.getEntryPoints(entryPointsCase);

        Assert.assertEquals(8, entryPoints.size());

        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret1()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret2()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret3()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret4()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret7()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret8()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret10()"));
        Assert.assertTrue(entryPoints.contains("Test1: java.lang.String getSecret11()"));

        Assert.assertFalse(entryPoints.contains("Test1: java.lang.String getSecret5()"));
        Assert.assertFalse(entryPoints.contains("Test1: java.lang.String getSecret6()"));
        Assert.assertFalse(entryPoints.contains("Test1: java.lang.String getSecret9()"));
    }
}
