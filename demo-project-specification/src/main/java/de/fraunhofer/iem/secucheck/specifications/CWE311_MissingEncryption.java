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
 * CWE-311: Missing Encryption of Sensitive Data
 *
 * The software does not encrypt sensitive or critical information before storage or transmission.
 */
@FluentTQLSpecificationClass
public class CWE311_MissingEncryption implements FluentTQLUserInterface {

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
	public Method sanitizerMethod = new MethodSelector("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: "+
				"java.lang.String encrypt("+
				"java.lang.String)");

    /**
     * Sink  
     */
	@InFlowParam(parameterID = {0})
	public Method sinkMethod = new MethodSelector(
				"org.springframework.data.repository.CrudRepository: "+
				"java.lang.Object save("+
				"java.lang.Object)");
    
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