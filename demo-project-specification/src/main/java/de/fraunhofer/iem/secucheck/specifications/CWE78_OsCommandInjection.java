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
 * CWE-78: Improper Neutralization of Special Elements used in an OS Command (OS Command Injection)
 *
 * The software constructs all or part of an OS command using externally-influenced
 * input from an upstream component, but it does not neutralize or incorrectly
 * neutralizes special elements that could modify the intended OS command
 * when it is sent to a downstream component.
 */
@FluentTQLSpecificationClass
public class CWE78_OsCommandInjection implements FluentTQLUserInterface {

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
	@InFlowParam(parameterID = {0})
    public Method sinkMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService: "+
    			"int getFileSizeOnSystem("+
    			"de.fraunhofer.iem.secucheck.todolist.model.Task,"+
    			"java.lang.String)");
    
    /**
     * Sink
     */
	@InFlowParam(parameterID = {1})
    public Method sinkMethod2 = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.service.DirectoryStorageService: "+
    			"java.lang.String store(org.springframework.web.multipart.MultipartFile,"+
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
                .report("CWE-78 detected: 'OS Command Injection' from untrusted value 'Task newTask'")
                .at(LOCATION.SINK)
                .build();
        
        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .notThrough(sanitizerMethod)
                .to(sinkMethod2)
                .report("CWE-78 detected: 'OS Command Injection' from untrusted value 'Task newTask'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);
        
        return myFluentTQLSpecs;
    }
}