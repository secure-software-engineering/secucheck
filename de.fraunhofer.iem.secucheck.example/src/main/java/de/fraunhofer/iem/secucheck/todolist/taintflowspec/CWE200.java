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

public class CWE200 implements FluentTQLUserInterface {

    /**
     * Source
     */
	Method source = new MethodConfigurator(
				"de.fraunhofer.iem.secucheck.todolist.service.UserService: "+
				"de.fraunhofer.iem.secucheck.todolist.model.User findUserByEmail("+
				"java.lang.String)")
			.out().param(0).configure();

    /**
     * Sink
     */
	Method sink = new MethodConfigurator(
				"org.slf4j.Logger: "+
				"void error("+
				"java.lang.String)")
			.in().param(0).configure();
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(source)
                .to(sink)
                .report("Invalid Information Flow. CWE-200: Exposure of Sensitive Information to an Unauthorized Actor detected.")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}