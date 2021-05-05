package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.Objects;

/**
 * This class represents the incomplete TaintFlow that started from some source.
 *
 * @author Ranjith Krishnamurthy
 */
public class FlowFromSource {
    private final TaintFlowQueryImpl taintFlowQuery;
    private final TaintFlowImpl singleTaintFlow;

    public FlowFromSource(TaintFlowQuery taintFlowQuery, TaintFlow singleTaintFlow) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
        this.singleTaintFlow = (TaintFlowImpl) singleTaintFlow;
    }

    public FlowFromSource notThrough(FlowParticipant sanitizer) {
        Objects.requireNonNull(sanitizer, "notThrough() method's argument is null.");

        singleTaintFlow.addNotThrough(sanitizer);
        return this;
    }

    public FlowFromSource through(FlowParticipant requiredPropagator) {
        Objects.requireNonNull(requiredPropagator, "through() method's argument is null.");

        singleTaintFlow.addThrough(requiredPropagator);
        return this;
    }

    public JustTaintFlow to(FlowParticipant sink) {
        Objects.requireNonNull(sink, "to() method's argument is null.");

        singleTaintFlow.setTo(sink);
        singleTaintFlow.setTaintFlowQuery(taintFlowQuery);
        taintFlowQuery.addTaintFlow(singleTaintFlow);

        return new JustTaintFlow(taintFlowQuery, singleTaintFlow);
    }
}
