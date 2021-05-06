package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for Servlet applications
 *
 * @author Ranjith Krishnamurthy
 * @author Goran
 */
@FluentTQLRepositoryClass
public class ServletSinks {
    //Below are the few sink methods from Servlet application.
    @InFlowParam(parameterID = {0})
    public static final Method sink1 = new MethodSelector("java.io.PrintWriter: java.io.PrintWriter append(java.lang.String)");

    @InFlowParam(parameterID = {0})
    public static final Method sink2 = new MethodSelector("java.io.PrintWriter: java.io.PrintWriter append(java.lang.CharSequence)");

    @InFlowParam(parameterID = {0})
    public static final Method sink3 = new MethodSelector("java.io.PrintWriter: void print(java.lang.String)");

    @InFlowParam(parameterID = {0})
    public static final Method sink4 = new MethodSelector("java.io.PrintWriter: void println(java.lang.String)");
    /**
     * This MethodSet contains some of the sink methods in Servlet application.
     */
    public static MethodSet servletSinks = new MethodSet("servletSinks")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4);
}
