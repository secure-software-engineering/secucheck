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
 * CWE-89: Improper Neutralization of Special Elements used in an SQL Command (SQL Injection)
 *
 * The software constructs all or part of an SQL command using externally-influenced input
 * from an upstream component, but it does not neutralize or incorrectly neutralizes special
 * elements that could modify the intended SQL command when it is sent to a downstream component.
 */
@FluentTQLSpecificationClass
public class CWE89_SqlInjection implements FluentTQLUserInterface {
	
    /**
     * Source
     */
    @OutFlowParam(parameterID = {1})
    public Method sourceMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.DatabaseController: "+
    			"java.lang.String showTasks("+
    			"org.springframework.ui.Model,"+
				"java.lang.String)");
    		
    /**
     * Source
     */
    @OutFlowParam(parameterID = {1})
    public Method sourceMethod2 = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.DatabaseController: "+
    			"java.lang.String showUrgentTasks("+
    			"org.springframework.ui.Model,"+
				"java.lang.String)");
    
    /**
     * Sink
     */
    @InFlowParam(parameterID = {0})
    public Method sinkMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.DatabaseController: "+
    			"java.lang.String getSearchQuery("+
				"java.lang.String,"+
    			"java.lang.String)");
    
    /**
     * Sink
     */
    @InFlowParam(parameterID = {0})
    public Method sinkMethod2 = new MethodSelector("java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)");
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .to(sinkMethod)
                .report("CWE-89 detected: 'SQL Injection' from untrusted value 'String pattern'")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder()
                .from(sourceMethod2)
                .to(sinkMethod)
                .report("CWE-89 detected: 'SQL Injection' from untrusted value 'String shortname'")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF3 = new TaintFlowQueryBuilder()
                .from(sourceMethod2)
                .to(sinkMethod2)
                .report("CWE-89 detected: 'SQL Injection' from untrusted value 'String shortname'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);
        myFluentTQLSpecs.add(myTF3);
        
        return myFluentTQLSpecs;
    }
}