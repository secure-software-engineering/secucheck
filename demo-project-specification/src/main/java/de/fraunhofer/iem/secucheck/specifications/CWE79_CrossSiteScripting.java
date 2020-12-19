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
 * CWE-79: Improper Neutralization of Input During Web Page Generation (Cross-site Scripting)
 *
 * The software does not neutralize or incorrectly neutralizes user-controllable input before
 * it is placed in output that is used as a web page that is served to other users.
 */
@FluentTQLSpecificationClass
public class CWE79_CrossSiteScripting implements FluentTQLUserInterface {

    /**
     * Source
     */
    @OutFlowParam(parameterID = {0})
    public Method sourceMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.LoginController: "+
    			"org.springframework.web.servlet.ModelAndView createNewUser("+
    			"de.fraunhofer.iem.secucheck.todolist.model.User,"+
    			"org.springframework.validation.BindingResult,"+
    			"javax.servlet.http.HttpServletRequest,"+
    			"javax.servlet.http.HttpServletResponse)");
    		
    
    /**
     * sanitize method is OWASP HTML sanitizer, that sanitizes the special characters, so that SQL Injection does not occur. It is a simple example, For security its better to use
     * encodeForSQL or make the settings of sanitize method to avoid SQL Injection.
     */
    @InFlowParam(parameterID = {0})
	@OutFlowReturnValue
	public Method sanitizerMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.controllers.LoginController: "+
    			"de.fraunhofer.iem.secucheck.todolist.model.User NameIt("+
				"de.fraunhofer.iem.secucheck.todolist.model.User)");
 
    
    /**
     * Sink
     */
	@InFlowParam(parameterID = {0})
	public Method sinkMethod = new MethodSelector(
    			"de.fraunhofer.iem.secucheck.todolist.service.UserService: "+
    			"void saveUserDefault("+
    			"de.fraunhofer.iem.secucheck.todolist.model.User)");
    
    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(sourceMethod)
                .to(sinkMethod)
                .report("CWE-79 detected: Cross-site Scripting from untrusted value 'String pattern'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}