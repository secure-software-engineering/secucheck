package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sources;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Source definition for Apache web-logger
 *
 * @author Goran
 */
@FluentTQLRepositoryClass
public class ApacheWebloggerSources {
    @OutFlowReturnValue
    public static final Method source1 = new MethodSelector("org.apache.roller.weblogger.pojos.Weblog: java.lang.String getName()");

    @OutFlowReturnValue
    public static final Method source2 = new MethodSelector("org.apache.roller.weblogger.pojos.Weblog: java.lang.String getTagline()");

    @OutFlowReturnValue
    public static final Method source3 = new MethodSelector("org.apache.roller.weblogger.pojos.Weblog: java.lang.String getEmailAddress()");

    @OutFlowReturnValue
    public static final Method source4 = new MethodSelector("org.apache.roller.weblogger.pojos.WeblogEntryComment: java.lang.String getContent()");

    @OutFlowReturnValue
    public static final Method source5 = new MethodSelector("org.apache.roller.weblogger.pojos.WeblogEntryComment: java.lang.String getId()");

    @OutFlowReturnValue
    public static final Method source6 = new MethodSelector("org.apache.roller.weblogger.config.WebloggerRuntimeConfig: java.lang.String getProperty(java.lang.String)");

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
