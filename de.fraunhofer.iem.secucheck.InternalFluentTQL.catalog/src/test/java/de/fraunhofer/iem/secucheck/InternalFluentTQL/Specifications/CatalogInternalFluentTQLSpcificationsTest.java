package de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.CommandInjection.CWE77.CommandInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.LDAPInjection.CWE90.LDAPInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.LogInjection.CWE117.LogInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.NoSQLInjection.CWE943.NoSQLInjectionWithMultipleSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.OpenRedirectAttack.CWE601.OpenRedirectAttackSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.PathTraversalAttack.CWE22.PathTraversalAttackSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.SQLInjection.CWE89.SQLiWithPreparedStatementsSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.SQLInjection.CWE89.ServletSQLInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.SQLInjection.CWE89.SimpleSQLInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.TrustBoundaryViolation.CWE501.TrustBoundaryViolationSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.XMLInjection.CWE91.XMLInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.XPATHInjection.CWE643.XPATHInjectionSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.XSS.CWE79.ReflexiveXSSSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.XSS.CWE79.StoredXSSSpec;
import org.junit.Test;

public class CatalogInternalFluentTQLSpcificationsTest {
    @Test
    public void commandInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new CommandInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void LdapInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new LDAPInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void LogInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new LogInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void NoSQLInjectionInSpringBootSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new NoSQLInjectionInSpringBoot().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void NoSQLInjectionWithMultipleSourcesSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new NoSQLInjectionWithMultipleSources().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void OpenRedirectAttackSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new OpenRedirectAttackSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void PathTraversalAttackSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new PathTraversalAttackSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void ServletSQLInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new ServletSQLInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void SimpleSQLInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new SimpleSQLInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void SQLiWithPreparedStatementsSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new SQLiWithPreparedStatementsSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void TrustBoundaryViolationSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new TrustBoundaryViolationSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void XMLInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new XMLInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void XPATHInjectionSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new XPATHInjectionSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void ReflexiveXSSSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new ReflexiveXSSSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }

    @Test
    public void StoredXSSSpecTest() {
        System.out.println("\n\n**********************************************************************\n");

        TestUtility.displayFluentTQLSpecifications(
                new StoredXSSSpec().getFluentTQLSpecification()
        );

        System.out.println("\n\n**********************************************************************\n");
    }
}
