package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for Log-Injection
 *
 * @author Ranjith Krishnamurthy
 * @author Goran
 */
public class LogInjectionSinks {
    //Below are few of the list of sinks for Log-Injection.
    public static final Method sink1 = new MethodConfigurator("java.util.logging.Logger: void info(java.lang.String)")
            .in().param(0)
            .configure();

    public static final Method sink2 = new MethodConfigurator("java.util.logging.Logger: void log(java.util.logging.Level,java.lang.String)")
            .in().param(1)
            .configure();

    public static final Method sink3 = new MethodConfigurator("java.util.logging.Logger: void entering(java.lang.String,java.lang.String)")
            .in().param(0).param(1)
            .configure();

    public static final Method sink4 = new MethodConfigurator("java.util.logging.Logger: void exiting(java.lang.String,java.lang.String)")
            .in().param(0).param(1)
            .configure();

    /**
     * This MethodSet contains some of the sink methods for Log injection.
     */
    public static MethodSet logInjectionSinks = new MethodSet("logInjectionSinks")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4);
}
