package de.fraunhofer.iem.secucheck.todolist.taintflowspec;

import java.util.ArrayList;
import java.util.List;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

public class CWE89 implements FluentTQLUserInterface {
	
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
     * Sink
     */
    Method sinkMethod = new MethodConfigurator(
    			"org.springframework.jdbc.core.JdbcTemplate: "+
    			"java.util.List query("+
				"java.lang.String,"+
    			"org.springframework.jdbc.core.RowMapper)"
			).in().param(0).configure();
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .to(sinkMethod)
                .report("Invalid Information Flow. CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection') detected.")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}