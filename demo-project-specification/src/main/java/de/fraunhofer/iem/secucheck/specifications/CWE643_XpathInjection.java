package de.fraunhofer.iem.secucheck.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * CWE-643: Improper Neutralization of Data within XPath Expressions (XPath Injection)
 *
 * The software uses external input to dynamically construct an XPath expression used
 * to retrieve data from an XML database, but it does not neutralize or incorrectly
 * neutralizes that input. This allows an attacker to control the structure of the query.
 */
@FluentTQLSpecificationClass
public class CWE643_XpathInjection implements FluentTQLUserInterface{

	/**
	 * Sources
	 */
	@OutFlowReturnValue
	public Method source1 = new MethodSelector(
			"javax.servlet.ServletRequest: java.lang.String getParameter(java.lang.String)");

	@OutFlowReturnValue
	public Method source2 = new MethodSelector(
			"javax.servlet.ServletRequest: java.lang.String[] getParameterValues(java.lang.String)");

	/**
	 * Sink
	 */
	@InFlowParam(parameterID = {0})
	public Method sink1 = new MethodSelector("javax.xml.xpath.XPath: javax.xml.xpath.XPathExpression compile(java.lang.String)");


	/**
	 * Returns the Internal FluentTQL specification
	 *
	 * @return Internal FluentTQL specifications
	 */
	public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(source1)
                .to(sink1)
                .report("CWE-634 detected: XPath Injection from untrusted value 'getPartameter()'")
                .at(LOCATION.SINK)
                .build();

        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder()
                .from(source1)
                .to(sink1)
                .report("CWE-634 detected: XPath Injection from untrusted value 'getParameterValues()'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);

        return myFluentTQLSpecs;
    }
}
