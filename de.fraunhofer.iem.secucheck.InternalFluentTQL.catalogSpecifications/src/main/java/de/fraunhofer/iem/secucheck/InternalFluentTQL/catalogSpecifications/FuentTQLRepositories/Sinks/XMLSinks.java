package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sinks;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Sinks definition for XML.
 *
 * @author Goran
 */
@FluentTQLRepositoryClass
public class XMLSinks {
    // these are sink methods for XPath injection. The list is created based on: https://github.com/julianthome/joanaudit/blob/master/config/sinks.json
    @InFlowParam(parameterID = {0})
    public static final Method sink1 = new MethodSelector("org.xml.sax.XMLReader: void parse(org.xml.sax.InputSource)");

    @InFlowParam(parameterID = {0})
    public static final Method sink2 = new MethodSelector("org.apache.http.client.methods.HttpPost: void setEntity(org.apache.http.HttpEntity)");

    @InFlowParam(parameterID = {1})
    public static final Method sink3 = new MethodSelector("org.apache.http.client.methods.HttpPost: void setHeader(java.lang.String, java.lang.String)"); // first argument is key, second argument is value

    @InFlowParam(parameterID = {0})
    public static final Method sink4 = new MethodSelector("javax.xml.parsers.DocumentBuilder: org.w3c.dom.Document parse(org.xml.sax.InputSource)");

    /**
     * This MethodSet contains some of the sink methods in XML application.
     */
    public static MethodSet sinksXMLinjection = new MethodSet("sinksXMLinjection")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4);
}
