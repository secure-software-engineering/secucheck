package de.fraunhofer.iem.secucheck.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * CWE-22: Improper Limitation of a Pathname to a Restricted Directory (Path Traversal)
 *
 * The software uses external input to construct a pathname that is intended to identify
 * a file or directory that is located underneath a restricted parent directory,
 * but the software does not properly neutralize special elements within the pathname
 * that can cause the pathname to resolve to a location that is outside of the restricted directory.
 */
@FluentTQLSpecificationClass
public class CWE22_PathTraversal implements FluentTQLUserInterface {
	
    /**
     * Source
     */
    @OutFlowParam(parameterID = {0})
	public Method sourceMethod = new MethodSelector(
				"de.fraunhofer.iem.secucheck.todolist.controllers.NewTaskController: "+
				"java.lang.String saveTask("+
				"de.fraunhofer.iem.secucheck.todolist.model.Task,"+
				"org.springframework.web.multipart.MultipartFile,"+
				"org.springframework.web.servlet.mvc.support.RedirectAttributes)");

    /**
     * Sanitizer
     */
    @InFlowParam(parameterID = {0})
	@OutFlowReturnValue
	public Method sanitizerMethod = new MethodSelector(
				"de.fraunhofer.iem.secucheck.todolist.controllers.NewTaskController: "+
				"java.lang.String correctFileName("+
				"java.lang.String)");
			
    /**
     * Sink
     */
	@InFlowParam(parameterID = {1})
    public Method sinkMethod = new MethodSelector("de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService: "+
    				"java.lang.String store("+
    				"org.springframework.web.multipart.MultipartFile,"+
    				"de.fraunhofer.iem.secucheck.todolist.model.Task,"+
    				"java.lang.String)");
    
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
                .report("CWE-22 detected: Path Traversal from untrusted value 'Task newTask'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}