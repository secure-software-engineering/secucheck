package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.Objects;

/**
 * This class represents the incomplete TaintFlow started from the Source Method/MethodSet.
 *
 * @author Ranjith Krishnamurthy
 */
public class FlowFromSource {
    private final TaintFlowQueryImpl taintFlowQuery;
    private final TaintFlowImpl singleTaintFlow;

    /**
     * Constructs the FlowFromSource with the passed TaintFlow (contains only source) and TaintFlowQuery
     *
     * @param taintFlowQuery  TaintFlowQuery
     * @param singleTaintFlow TaintFlow
     */
    public FlowFromSource(TaintFlowQuery taintFlowQuery, TaintFlow singleTaintFlow) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
        this.singleTaintFlow = (TaintFlowImpl) singleTaintFlow;
    }

    /**
     * This method adds the given Sanitizer Method/MethodSet to the TaintFlow.
     *
     * @param sanitizer Sanitizer Method/MethodSet
     * @return FlowFromSource: Indicates that TaintFlow is still incomplete
     */
    public FlowFromSource notThrough(FlowParticipant sanitizer) {
        Objects.requireNonNull(sanitizer, "notThrough() method's argument is null.");

        singleTaintFlow.addNotThrough(sanitizer);
        return this;
    }

    /**
     * This method adds the given requiredPropagator Method/MethodSet to the TaintFlow.
     *
     * @param requiredPropagator RequiredPropagator Method/MethodSet
     * @return FlowFromSource: Indicates that TaintFlow is still incomplete
     */
    public FlowFromSource through(FlowParticipant requiredPropagator) {
        Objects.requireNonNull(requiredPropagator, "through() method's argument is null.");

        singleTaintFlow.addThrough(requiredPropagator);
        return this;
    }

    /**
     * This method adds the given sink Method/MethodSet to the TaintFlow.
     *
     * @param sink Sink Method/MethodSet
     * @return JustTaintFlow: Indicates that TaintFlow is complete, but it does not contain any report message or report location.
     */
    public JustTaintFlow to(FlowParticipant sink) {
        Objects.requireNonNull(sink, "to() method's argument is null.");

        singleTaintFlow.setTo(sink);
        singleTaintFlow.setTaintFlowQuery(taintFlowQuery);
        taintFlowQuery.addTaintFlow(singleTaintFlow);

        return new JustTaintFlow(taintFlowQuery, singleTaintFlow);
    }
}
