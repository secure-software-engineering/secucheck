package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sources;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Source definition for Apache web-logger
 *
 * @author Goran
 */
public class ApacheWebloggerSources {
    public static final Method source1 = new MethodConfigurator("org.apache.roller.weblogger.pojos.Weblog: java.lang.String getName()")
            .out().returnValue()
            .configure();

    public static final Method source2 = new MethodConfigurator("org.apache.roller.weblogger.pojos.Weblog: java.lang.String getTagline()")
            .out().returnValue()
            .configure();

    public static final Method source3 = new MethodConfigurator("org.apache.roller.weblogger.pojos.Weblog: java.lang.String getEmailAddress()")
            .out().returnValue()
            .configure();

    public static final Method source4 = new MethodConfigurator("org.apache.roller.weblogger.pojos.WeblogEntryComment: java.lang.String getContent()")
            .out().returnValue()
            .configure();

    public static final Method source5 = new MethodConfigurator("org.apache.roller.weblogger.pojos.WeblogEntryComment: java.lang.String getId()")
            .out().returnValue()
            .configure();

    public static final Method source6 = new MethodConfigurator("org.apache.roller.weblogger.config.WebloggerRuntimeConfig: java.lang.String getProperty(java.lang.String)")
            .out().returnValue()
            .configure();

    /**
     * This MethodSet contains some of the source methods in Weblogger.
     */
    public static MethodSet apacheWebloggerSources = new MethodSet("webgoatSources")
            .addMethod(source1)
            .addMethod(source2)
            .addMethod(source3)
            .addMethod(source4)
            .addMethod(source5)
            .addMethod(source6);
}
