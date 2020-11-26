package de.fraunhofer.iem.secucheck.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

public class CWE634_XpathInjection implements FluentTQLUserInterface{

	
	Method source1 = new MethodConfigurator(
			"javax.servlet.ServletRequest: java.lang.String getParameter(java.lang.String)")
		.out().returnValue().configure();
	Method source2 = new MethodConfigurator(
			"javax.servlet.ServletRequest: java.lang.String[] getParameterValues(java.lang.String)")
		.out().returnValue().configure();
	
	private static final Method sink1 = new MethodConfigurator("javax.xml.xpath.XPath: javax.xml.xpath.XPathExpression compile(java.lang.String)")
            .in().param(0).configure();
	
	
	public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(source1)
                .to(sink1)
                .report("CWE-634 detected: XPath Injection from untrusted value 'getPartameter()' (line 34).")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder()
                .from(source1)
                .to(sink1)
                .report("CWE-634 detected: XPath Injection from untrusted value 'getParameterValues()' (line 35).")
                .at(LOCATION.SINK)
                .build();
        

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);

        return myFluentTQLSpecs;
    }
	
}
