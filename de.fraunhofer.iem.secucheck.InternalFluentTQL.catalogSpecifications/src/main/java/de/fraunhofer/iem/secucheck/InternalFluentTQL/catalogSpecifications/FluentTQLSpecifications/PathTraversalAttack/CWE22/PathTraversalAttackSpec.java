package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.PathTraversalAttack.CWE22;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sources.ServletSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal FluentTQL specification for Path-Traversal attack.
 *
 * @author Ranjith Krishnamurthy
 */
public class PathTraversalAttackSpec implements FluentTQLUserInterface {
    /**
     * This is a user defined sanitizer for avoiding path traversal attack. This is not recommended to use this method. This is for example.
     */
    public Method sanitizer = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.PathTraversalAttack.CWE22.PathTraversal: java.lang.String sanitizeForPATH(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * This is a required propagator that is used in the second taint flow to achieve path traversal attack.
     */
    public Method requiredPropagator = new MethodConfigurator("java.io.File: void <init>(java.lang.String)")
            .in().param(0)
            .out().thisObject()
            .configure();

    /**
     * This is the sink in first taint flow where File constructor is not used.
     */
    public Method sink1 = new MethodConfigurator("java.io.FileInputStream: void <init>(java.lang.String)")
            .in().param(0)
            .configure();

    /**
     * This is the sink for second taint flow where File constructor is used as a required propagator.
     */
    public Method sink2 = new MethodConfigurator("java.io.FileInputStream: void <init>(java.io.File)")
            .in().param(0)
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        /*
         * This is the first taint flow that achieves path traversal attack.
         */
        TaintFlowQuery tf1 = new TaintFlowQueryBuilder("PathTraversal_TF1")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .to(sink1)
                .report("Path traversal - CWE22!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery tf2 = new TaintFlowQueryBuilder("PathTraversal_TF2")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .through(requiredPropagator)
                .to(sink2)
                .report("Path traversal attack through File constructor - CWE22!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        //TaintFlowSet for path traversal attack that contains the above both taint flows that achieves path traversal attack.
        QueriesSet pathTraversalTFSet = new QueriesSet("pathTraversalTFSet")
                .addTaintFlowQuery(tf1)
                .addTaintFlowQuery(tf2);

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(pathTraversalTFSet);

        return myFluentTQLSpecs;
    }
}
