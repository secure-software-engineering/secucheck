package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for Servlet applications
 *
 * @author Ranjith Krishnamurthy
 * @author Goran
 */
public class ServletSinks {
    //Below are the few sink methods from Servlet application.
    public static final Method sink1 = new MethodConfigurator("java.io.PrintWriter: java.io.PrintWriter append(java.lang.String)")
            .in().param(0)
            .configure();

    public static final Method sink2 = new MethodConfigurator("java.io.PrintWriter: java.io.PrintWriter append(java.lang.CharSequence)")
            .in().param(0)
            .configure();

    public static final Method sink3 = new MethodConfigurator("java.io.PrintWriter: void print(java.lang.String)")
            .in().param(0)
            .configure();

    public static final Method sink4 = new MethodConfigurator("java.io.PrintWriter: void println(java.lang.String)")
            .in().param(0)
            .configure();

    /**
     * This MethodSet contains some of the sink methods in Servlet application.
     */
    public static MethodSet servletSinks = new MethodSet("servletSinks")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4);
}
