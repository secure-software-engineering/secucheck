package de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.CommandInjection.CWE77.CommandInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.LDAPInjection.CWE90.LDAPInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.LogInjection.CWE117.LogInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.NoSQLInjection.CWE943.NoSQLInjectionWithMultipleSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.OpenRedirectAttack.CWE601.OpenRedirectAttackSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.PathTraversalAttack.CWE22.PathTraversalAttackSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.SQLInjection.CWE89.SQLiWithPreparedStatementsSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.SQLInjection.CWE89.ServletSQLInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.SQLInjection.CWE89.SimpleSQLInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.TrustBoundaryViolation.CWE501.TrustBoundaryViolationSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.XMLInjection.CWE91.XMLInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.XPATHInjection.CWE643.XPATHInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.XSS.CWE79.ReflexiveXSSSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.XSS.CWE79.StoredXSSSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

/**
 * Tests all the FluentTQL specifications
 *
 * @author Ranjith Krishnamurthy
 */
public class CatalogInternalFluentTQLSpcificationsTest {
    private final ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();

    @Test
    public void commandInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new CommandInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void LdapInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new LDAPInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void LogInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new LogInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void NoSQLInjectionInSpringBootSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new NoSQLInjectionInSpringBoot()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void NoSQLInjectionWithMultipleSourcesSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new NoSQLInjectionWithMultipleSources()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void OpenRedirectAttackSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new OpenRedirectAttackSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void PathTraversalAttackSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new PathTraversalAttackSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void ServletSQLInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new ServletSQLInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void SimpleSQLInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new SimpleSQLInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void SQLiWithPreparedStatementsSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new SQLiWithPreparedStatementsSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void TrustBoundaryViolationSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new TrustBoundaryViolationSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void XMLInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new XMLInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void XPATHInjectionSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new XPATHInjectionSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void ReflexiveXSSSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new ReflexiveXSSSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void StoredXSSSpecTest() throws FluentTQLException {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                        new StoredXSSSpec()
                ).getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }
}
