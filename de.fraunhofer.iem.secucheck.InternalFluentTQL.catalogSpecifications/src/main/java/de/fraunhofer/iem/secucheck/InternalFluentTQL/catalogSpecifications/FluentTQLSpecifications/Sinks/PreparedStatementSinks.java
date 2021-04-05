package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for Prepared-Statements
 *
 * @author Ranjith Krishnamurthy
 * @author Goran
 */
public class PreparedStatementSinks {
    public static final Method sink1 = new MethodConfigurator("java.sql.PreparedStatement: java.sql.ResultSet executeQuery()")
            .in().thisObject()
            .configure();

    public static final Method sink2 = new MethodConfigurator("java.sql.PreparedStatement: java.sql.ResultSet executeQuery(java.lang.String)")
            .in().thisObject().param(0)
            .configure();

    public static final Method sink3 = new MethodConfigurator("java.sql.PreparedStatement: boolean execute()")
            .in().thisObject()
            .configure();

    public static final Method sink4 = new MethodConfigurator("java.sql.PreparedStatement: boolean execute(java.lang.String)")
            .in().thisObject().param(0)
            .configure();

    /**
     * This MethodSet contains some of the sink methods in Prepared Statements.
     */
    public static MethodSet prepSinks = new MethodSet("PrepSinks")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4);
}
