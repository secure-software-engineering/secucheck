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

public class CWE79 implements FluentTQLUserInterface {

    /**
     * Source
     */
    Method sourceMethod = new MethodConfigurator(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.LoginController: "+
    			"org.springframework.web.servlet.ModelAndView createNewUser("+
    			"de.fraunhofer.iem.secucheck.todolist.model.User,"+
    			"org.springframework.validation.BindingResult,"+
    			"javax.servlet.http.HttpServletRequest,"+
    			"javax.servlet.http.HttpServletResponse)")
    		.out().param(0).configure();
    		
    
    /**
     * sanitize method is OWASP HTML sanitizer, that sanitizes the special characters, so that SQL Injection does not occur. It is a simple example, For security its better to use
     * encodeForSQL or make the settings of sanitize method to avoid SQL Injection.
     */
    Method sanitizerMethod = new MethodConfigurator(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.LoginController: "+
    			"de.fraunhofer.iem.secucheck.todolist.model.User NameIt("+
				"de.fraunhofer.iem.secucheck.todolist.model.User)")
    		.in().param(0)
            .out().returnValue().configure();
 
    
    /**
     * Sink
     */    
    Method sinkMethod = new MethodConfigurator(
    			"de.fraunhofer.iem.secucheck.todolist.service.UserService: "+
    			"void saveUserDefault("+
    			"de.fraunhofer.iem.secucheck.todolist.model.User)")
    		.in().param(0).configure();
    
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
                .report("Invalid Information Flow. CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting') detected.")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}