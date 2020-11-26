package de.fraunhofer.iem.secucheck.specifications;

import java.util.ArrayList;
import java.util.List;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

public class CWE89_SqlInjection implements FluentTQLUserInterface {
	
    /**
     * Source
     */
    Method sourceMethod = new MethodConfigurator(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
    			"java.lang.String showTasks("+
    			"org.springframework.ui.Model,"+
				"java.lang.String)"
			).out().param(1).configure();
    		
    /**
     * Source
     */
    Method sourceMethod2 = new MethodConfigurator(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
    			"java.lang.String showUrgentTasks("+
    			"org.springframework.ui.Model,"+
				"java.lang.String)"
			).out().param(1).configure();
    
    /**
     * Sink
     */
    Method sinkMethod = new MethodConfigurator(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
    			"java.lang.String getSearchQuery("+
				"java.lang.String,"+
    			"java.lang.String)"
			).in().param(0).configure();
    
    /**
     * Sink
     */
    Method sinkMethod2 = new MethodConfigurator("java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)")
            .in().param(0).configure();
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .to(sinkMethod)
                .report("CWE-89 detected: 'SQL Injection' from untrusted value 'String pattern' (line 71).")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder()
                .from(sourceMethod2)
                .to(sinkMethod)
                .report("CWE-89 detected: 'SQL Injection' from untrusted value 'String shortname' (line 121).")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF3 = new TaintFlowQueryBuilder()
                .from(sourceMethod2)
                .to(sinkMethod2)
                .report("CWE-89 detected: 'SQL Injection' from untrusted value 'String shortname' (line 121).")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);
        myFluentTQLSpecs.add(myTF3);
        
        return myFluentTQLSpecs;
    }
}