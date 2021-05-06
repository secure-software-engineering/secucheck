package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for LDAP-Injection
 *
 * @author Ranjith Krishnamurthy
 * @author Goran
 */
@FluentTQLRepositoryClass
public class LdapSinks {
    // These are sink methods for XPath injection. The list is created based on: https://github.com/julianthome/joanaudit/blob/master/config/sinks.json
    @InFlowParam(parameterID = {1})
    public static final Method sink1 = new MethodSelector("javax.naming.directory.DirContext: javax.naming.NamingEnumeration search(java.lang.String, java.lang.String, javax.naming.directory.SearchControls)");

    @InFlowParam(parameterID = {1})
    public static final Method sink2 = new MethodSelector("javax.naming.directory.DirContext: javax.naming.NamingEnumeration search(java.lang.String, java.lang.String, java.lang.Object[], javax.naming.directory.SearchControls)");

    @InFlowParam(parameterID = {0})
    public static final Method sink3 = new MethodSelector("com.novell.ldap.LDAPConnection: void connect(java.lang.String, int)");

    @InFlowParam(parameterID = {2})
    public static final Method sink4 = new MethodSelector("com.novell.ldap.LDAPConnection: com.novell.ldap.LDAPSearchQueue search(java.lang.String, int, java.lang.String, java.lang.String[], boolean)");

    @InFlowParam(parameterID = {2})
    public static final Method sink5 = new MethodSelector("com.novell.ldap.LDAPConnection: com.novell.ldap.LDAPSearchQueue search(java.lang.String, int, java.lang.String, java.lang.String[], boolean, com.novell.ldap.LDAPSearchConstraints)");

    @InFlowParam(parameterID = {2})
    public static final Method sink6 = new MethodSelector("com.novell.ldap.LDAPConnection: com.novell.ldap.LDAPSearchQueue search(java.lang.String, int, java.lang.String, java.lang.String[], boolean, com.novell.ldap.LDAPSearchQueue, com.novell.ldap.LDAPSearchConstraints)");

    @InFlowParam(parameterID = {2})
    public static final Method sink7 = new MethodSelector("com.novell.ldap.LDAPConnection: com.novell.ldap.LDAPSearchQueue search(java.lang.String, int, java.lang.String, java.lang.String[], boolean, com.novell.ldap.LDAPSearchQueue)");

    @InFlowParam(parameterID = {2})
    public static final Method sink8 = new MethodSelector("com.unboundid.ldap.sdk.LDAPInterface: com.unboundid.ldap.sdk.SearchResult search(java.lang.String, com.unboundid.ldap.sdk.SearchScope, java.lang.String)");

    @InFlowParam(parameterID = {2})
    public static final Method sink9 = new MethodSelector("com.unboundid.ldap.sdk.LDAPInterface: com.unboundid.ldap.sdk.SearchResult search(java.lang.String,com.unboundid.ldap.sdk.SearchScope,java.lang.String,java.lang.String[])");

    /**
     * This MethodSet contains some of the sink methods for LDAP injection.
     */
    public static MethodSet sinksLdapinjection = new MethodSet("sinksLdapinjection")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4)
            .addMethod(sink5)
            .addMethod(sink6)
            .addMethod(sink7)
            .addMethod(sink8)
            .addMethod(sink9);
}
