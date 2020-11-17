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

public class CWE22 implements FluentTQLUserInterface {
	
    /**
     * Source
     */
	Method sourceMethod = new MethodConfigurator(
				"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
				"java.lang.String saveTask("+
				"de.fraunhofer.iem.secucheck.todolist.model.Task,"+
				"org.springframework.web.multipart.MultipartFile,"+
				"org.springframework.web.servlet.mvc.support.RedirectAttributes)")
			.out().param(0).configure();

    /**
     * Sanitizer
     */
	Method sanitizerMethod = new MethodConfigurator(
				"de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
				"java.lang.String correctFileName("+
				"java.lang.String)")
			.in().param(0)
			.out().returnValue().configure();
			
    /**
     * Sink
     */
    Method sinkMethod = new MethodConfigurator("de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService: "+
    				"java.lang.String store("+
    				"org.springframework.web.multipart.MultipartFile,"+
    				"de.fraunhofer.iem.secucheck.todolist.model.Task,"+
    				"java.lang.String)"
    			).in().param(1).configure();
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .notThrough(sanitizerMethod)
                .to(sinkMethod)
                .report("Invalid Information Flow. CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal') detected.")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
