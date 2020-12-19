package de.fraunhofer.iem.secucheck.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * CWE-601: URL Redirection to Untrusted Site (Open Redirect)
 *
 * A web application accepts a user-controlled input that specifies a link to
 * an external site, and uses that link in a Redirect. This simplifies phishing attacks.
 */
@FluentTQLSpecificationClass
public class CWE601_OpenRedirect implements FluentTQLUserInterface {

    /**
     * Source
     */
    @OutFlowParam(parameterID = {1})
    public Method sourceMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
				"void redirectToExternalUrl("+
				"javax.servlet.http.HttpServletResponse,"+
				"java.lang.String)");
    
    /**
     * Source
     */
    @OutFlowParam(parameterID = {0})
    public Method sourceMethod2 = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
				"java.lang.String markDoneTask(de.fraunhofer.iem.secucheck.todolist.model.TaskList,"+
				"javax.servlet.http.HttpServletResponse)");
    	
    /**
     * Source
     */
    @OutFlowParam(parameterID = {0})
    public Method sourceMethod3 = new MethodSelector(
			"javax.servlet.http.HttpServletRequest: "+
			"java.lang.String getParameter(java.lang.String)");
    
    /**
     * Sink
     */
    @InFlowParam(parameterID = {0})
    public Method sinkMethod = new MethodSelector(
				"javax.servlet.http.HttpServletResponse: "+
				"void sendRedirect("+
				"java.lang.String)");
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .to(sinkMethod)
                .report("CWE-601 detected: URL Redirection to Untrusted Site ('Open Redirect') from untrusted value 'String page'")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder()
                .from(sourceMethod2)
                .to(sinkMethod)
                .report("CWE-601 detected: URL Redirection to Untrusted Site ('Open Redirect') from untrusted value 'TaskList requestItems'")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF3 = new TaintFlowQueryBuilder()
                .from(sourceMethod3)
                .to(sinkMethod)
                .report("CWE-601 detected: URL Redirection to Untrusted Site ('Open Redirect') from untrusted value 'HttpServletRequest request'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);
        myFluentTQLSpecs.add(myTF3);

        return myFluentTQLSpecs;
    }
}