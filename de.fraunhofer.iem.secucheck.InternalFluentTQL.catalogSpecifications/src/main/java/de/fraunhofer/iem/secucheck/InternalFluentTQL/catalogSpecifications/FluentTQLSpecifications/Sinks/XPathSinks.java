package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for XPATH-Injection.
 *
 * @author Goran
 */
public class XPathSinks {
    // these are sink methods for XPath injection. The list is created based on: https://github.com/julianthome/joanaudit/blob/master/config/sinks.json
    public static final Method sink1 = new MethodConfigurator("javax.xml.xpath.XPath: javax.xml.xpath.XPathExpression compile(java.lang.String)")
            .in().param(0)
            .configure();

    public static final Method sink2 = new MethodConfigurator("javax.xml.xpath.XPath: java.lang.Object evaluate(java.lang.String,org.xml.sax.InputSource,javax.xml.namespace.QName)")
            .in().param(1).param(2)
            .configure();

    public static final Method sink3 = new MethodConfigurator("javax.xml.xpath.XPath: java.lang.Object evaluate(java.lang.String,org.xml.sax.InputSource)")
            .in().param(1)
            .configure();

    public static final Method sink4 = new MethodConfigurator("javax.xml.xpath.XPathExpression: java.lang.Object evaluate(java.lang.Object,javax.xml.namespace.QName)")
            .in().param(0)
            .configure();

    public static final Method sink5 = new MethodConfigurator("javax.xml.xpath.XPath: java.lang.String evaluate(java.lang.String,java.lang.Object)")
            .in().param(1)
            .configure();

    public static final Method sink6 = new MethodConfigurator("javax.xml.xpath.XPath: java.lang.Object evaluate(java.lang.String,java.lang.Object,javax.xml.namespace.QName)")
            .in().param(1).param(2)
            .configure();

    public static final Method sink7 = new MethodConfigurator("org.apache.xpath.XPath: org.apache.xpath.XPath <init>(java.lang.String,javax.xml.transform.SourceLocator,org.apache.xml.utils.PrefixResolver,int)")
            .in().param(1)
            .configure();

    public static final Method sink8 = new MethodConfigurator("org.apache.xpath.XPath: org.apache.xpath.XPath <init>(java.lang.String,javax.xml.transform.SourceLocator,org.apache.xml.utils.PrefixResolver,int,javax.xml.transform.ErrorListener)")
            .in().param(1)
            .configure();

    public static final Method sink9 = new MethodConfigurator("org.apache.xpath.XPath: org.apache.xpath.XPath <init>(java.lang.String,javax.xml.transform.SourceLocator,org.apache.xml.utils.PrefixResolver,int,javax.xml.transform.ErrorListener,org.apache.xpath.compiler.FunctionTable)")
            .in().param(1)
            .configure();

    public static final Method sink10 = new MethodConfigurator("org.apache.commons.jxpath.JXPathContext: java.lang.Object getValue(java.lang.String)")
            .in().param(0)
            .configure();

    public static final Method sink11 = new MethodConfigurator("org.xmldb.api.modules.XPathQueryService: org.xmldb.api.base.ResourceSet query(java.lang.String)")
            .in().param(0)
            .configure();

    public static final Method sink12 = new MethodConfigurator("org.xmldb.api.modules.XMLResource: void setContent(java.lang.Object)")
            .in().param(0)
            .configure();

    /**
     * This MethodSet contains some of the sink methods for XPATH injection.
     */
    public static MethodSet xpathSinks = new MethodSet("xpathSinks").addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4)
            .addMethod(sink5)
            .addMethod(sink6)
            .addMethod(sink7)
            .addMethod(sink8)
            .addMethod(sink9)
            .addMethod(sink10)
            .addMethod(sink11)
            .addMethod(sink12);
}
