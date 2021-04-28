package de.fraunhofer.iem.secucheck.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * CWE-601: URL Redirection to Untrusted Site (Open Redirect)
 * <p>
 * A web application accepts a user-controlled input that specifies a link to
 * an external site, and uses that link in a Redirect. This simplifies phishing attacks.
 */
public class CWE601_OpenRedirect implements FluentTQLUserInterface {

    /**
     * Source
     */
    public Method sourceMethod = new MethodConfigurator(
            "de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: " +
                    "void redirectToExternalUrl(" +
                    "javax.servlet.http.HttpServletResponse," +
                    "java.lang.String)")
            .out().param(1)
            .configure();

    /**
     * Source
     */
    public Method sourceMethod2 = new MethodConfigurator(
            "de.fraunhofer.iem.secucheck.todolist.controllers.TaskController: " +
                    "java.lang.String markDoneTask(de.fraunhofer.iem.secucheck.todolist.model.TaskList," +
                    "javax.servlet.http.HttpServletResponse)")
            .out().param(0)
            .configure();

    /**
     * Source
     */
    public Method sourceMethod3 = new MethodConfigurator(
            "javax.servlet.http.HttpServletRequest: " +
                    "java.lang.String getParameter(java.lang.String)")
            .out().returnValue()
            .configure();

    /**
     * Sink
     */
    public Method sinkMethod = new MethodConfigurator(
            "javax.servlet.http.HttpServletResponse: " +
                    "void sendRedirect(" +
                    "java.lang.String)")
            .in().param(0)
            .configure();

    public Method rp1 = new MethodConfigurator(
            "de.fraunhofer.iem.secucheck.todolist.model.TaskList: java.util.ArrayList getTaskList()")
            .in().thisObject()
            .out().returnValue()
            .configure();

    public Method rp2 = new MethodConfigurator(
            "java.util.ArrayList: int size()")
            .in().thisObject()
            .out().returnValue()
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specifications
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("CWE601_OpenRedirect_TF1")
                .from(sourceMethod)
                .to(sinkMethod)
                .report("CWE-601 detected: URL Redirection to Untrusted Site ('Open Redirect') from untrusted value 'String page'")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery myTF2 = new TaintFlowQueryBuilder("CWE601_OpenRedirect_TF2")
                .from(sourceMethod2)
                .through(rp1)
                .through(rp2)
                .to(sinkMethod)
                .report("CWE-601 detected: URL Redirection to Untrusted Site ('Open Redirect') from untrusted value 'TaskList requestItems'")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery myTF3 = new TaintFlowQueryBuilder("CWE601_OpenRedirect_TF3")
                .from(sourceMethod3)
                .to(sinkMethod)
                .report("CWE-601 detected: URL Redirection to Untrusted Site ('Open Redirect') from untrusted value 'HttpServletRequest request'")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);
        myFluentTQLSpecs.add(myTF2);
        myFluentTQLSpecs.add(myTF3);

        return myFluentTQLSpecs;
    }
}