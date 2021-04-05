package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sources;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Source definition for Webgoat
 *
 * @author Goran
 */
public class WebgoatSources {
    public static final Method source1 = new MethodConfigurator("org.owasp.webgoat.session.ParameterParser: java.lang.String getRawParameter(java.lang.String,java.lang.String)")
            .out().returnValue()
            .configure();

    public static final Method source2 = new MethodConfigurator("org.owasp.webgoat.session.ParameterParser: java.lang.String getStringParameter(java.lang.String,java.lang.String)")
            .out().returnValue()
            .configure();

    public static final Method source3 = new MethodConfigurator("org.owasp.webgoat.session.ParameterParser: int getIntParameter(java.lang.String)")
            .out().returnValue()
            .configure();

    public static final Method source4 = new MethodConfigurator("org.owasp.webgoat.session.ParameterParser: java.lang.String[] getParameterValues(java.lang.String)")
            .out().returnValue()
            .configure();

    /**
     * This MethodSet contains some of the source methods in Webgoat.
     */
    public static MethodSet webgoatSources = new MethodSet("webgoatSources")
            .addMethod(source1)
            .addMethod(source2)
            .addMethod(source3)
            .addMethod(source4);
}
