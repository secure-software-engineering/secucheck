package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

/**
 * This class represents the incomplete TaintFlow that started from some source.
 */
public class FlowFromSource {
    private final TaintFlowQueryImpl taintFlowQuery;
    private final TaintFlowImpl singleTaintFlow;

    public FlowFromSource(TaintFlowQuery taintFlowQuery, TaintFlow singleTaintFlow) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
        this.singleTaintFlow = (TaintFlowImpl) singleTaintFlow;
    }

    public FlowFromSource notThrough(FlowParticipant sanitizer) {
        singleTaintFlow.addNotThrough(sanitizer);
        return this;
    }

    public FlowFromSource through(FlowParticipant deSanitizer) {
        singleTaintFlow.addThrough(deSanitizer);
        return this;
    }

    public JustTaintFlow to(FlowParticipant sink) {
        singleTaintFlow.setTo(sink);
        singleTaintFlow.setTaintFlowQuery(taintFlowQuery);
        taintFlowQuery.addTaintFlow(singleTaintFlow);

        return new JustTaintFlow(taintFlowQuery, singleTaintFlow);
    }
}
