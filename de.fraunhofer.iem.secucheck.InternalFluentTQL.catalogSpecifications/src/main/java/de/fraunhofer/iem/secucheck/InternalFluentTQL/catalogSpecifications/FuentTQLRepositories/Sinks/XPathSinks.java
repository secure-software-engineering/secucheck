package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for XPATH-Injection.
 *
 * @author Goran
 */
@FluentTQLRepositoryClass
public class XPathSinks {
    // these are sink methods for XPath injection. The list is created based on: https://github.com/julianthome/joanaudit/blob/master/config/sinks.json
    @InFlowParam(parameterID = {0})
    public static final Method sink1 = new MethodSelector("javax.xml.xpath.XPath: javax.xml.xpath.XPathExpression compile(java.lang.String)");

    @InFlowParam(parameterID = {1, 2})
    public static final Method sink2 = new MethodSelector("javax.xml.xpath.XPath: java.lang.Object evaluate(java.lang.String, org.xml.sax.InputSource, javax.xml.namespace.QName)");

    @InFlowParam(parameterID = {1})
    public static final Method sink3 = new MethodSelector("javax.xml.xpath.XPath: java.lang.Object evaluate(java.lang.String, org.xml.sax.InputSource)");

    @InFlowParam(parameterID = {0})
    public static final Method sink4 = new MethodSelector("javax.xml.xpath.XPathExpression: java.lang.Object evaluate(java.lang.Object, javax.xml.namespace.QName)");

    @InFlowParam(parameterID = {1})
    public static final Method sink5 = new MethodSelector("javax.xml.xpath.XPath: java.lang.String evaluate(java.lang.String, java.lang.Object)");

    @InFlowParam(parameterID = {1, 2})
    public static final Method sink6 = new MethodSelector("javax.xml.xpath.XPath: java.lang.Object evaluate(java.lang.String, java.lang.Object, javax.xml.namespace.QName)");

    @InFlowParam(parameterID = {1})
    public static final Method sink7 = new MethodSelector("org.apache.xpath.XPath: org.apache.xpath.XPath <init>(java.lang.String, javax.xml.transform.SourceLocator, org.apache.xml.utils.PrefixResolver, int)");

    @InFlowParam(parameterID = {1})
    public static final Method sink8 = new MethodSelector("org.apache.xpath.XPath: org.apache.xpath.XPath <init>(java.lang.String, javax.xml.transform.SourceLocator, org.apache.xml.utils.PrefixResolver, int, javax.xml.transform.ErrorListener)");

    @InFlowParam(parameterID = {1})
    public static final Method sink9 = new MethodSelector("org.apache.xpath.XPath: org.apache.xpath.XPath <init>(java.lang.String, javax.xml.transform.SourceLocator, org.apache.xml.utils.PrefixResolver, int, javax.xml.transform.ErrorListener, org.apache.xpath.compiler.FunctionTable)");

    @InFlowParam(parameterID = {0})
    public static final Method sink10 = new MethodSelector("org.apache.commons.jxpath.JXPathContext: java.lang.Object getValue(java.lang.String)");

    @InFlowParam(parameterID = {0})
    public static final Method sink11 = new MethodSelector("org.xmldb.api.modules.XPathQueryService: org.xmldb.api.base.ResourceSet query(java.lang.String)");

    @InFlowParam(parameterID = {0})
    public static final Method sink12 = new MethodSelector("org.xmldb.api.modules.XMLResource: void setContent(java.lang.Object)");

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
