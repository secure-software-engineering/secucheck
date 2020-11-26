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

/**
 * CWE-311: Missing Encryption of Sensitive Data
 *
 * The software does not encrypt sensitive or critical information before storage or transmission.
 */
public class CWE311_MissingEncryption implements FluentTQLUserInterface {

    /**
     * Source
     */
	Method sourceMethod = new MethodConfigurator(
				"de.fraunhofer.iem.secucheck.todolist.controllers.NewTaskController: "+
				"java.lang.String saveTask("+
				"de.fraunhofer.iem.secucheck.todolist.model.Task,"+
				"org.springframework.web.multipart.MultipartFile,"+
				"org.springframework.web.servlet.mvc.support.RedirectAttributes)")
			.out().param(0).configure();
					
    /**
     * Sanitizer
     */
	Method sanitizerMethod = new MethodConfigurator("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
				"java.lang.String encrypt("+
				"java.lang.String)")
			.in().param(0)
			.out().returnValue().configure();

    /**
     * Sink  
     */
	Method sinkMethod = new MethodConfigurator(
				"org.springframework.data.repository.CrudRepository: "+
				"java.lang.Object save("+
				"java.lang.Object)")
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
                .report("CWE-311 detected: Missing Encryption of Sensitive Data from 'Task newTask'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}