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
 * CWE-20: Improper Input Validation
 *
 * The product receives input or data, but it does not validate or incorrectly
 * validates that the input has the properties that are required to process
 * the data safely and correctly.
 */
public class CWE20_ImproperInputValidation implements FluentTQLUserInterface {

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
     * Sink 
     */	
	Method sinkMethod = new MethodConfigurator(
				"de.fraunhofer.iem.secucheck.todolist.service.UserService: "+
				"void saveUserDefault("+
				"de.fraunhofer.iem.secucheck.todolist.model.User)"
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
                .report("CWE-20 detected: Improper Input Validation from 'User user'")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}