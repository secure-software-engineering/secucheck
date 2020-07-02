package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

public class TaintFlowQueryBuilder {
    private TaintFlowQueryImpl taintFlowQuery = null;

    public TaintFlowQueryBuilder() {
        if (taintFlowQuery == null)
            taintFlowQuery = new TaintFlowQueryImpl();
    }

    public TaintFlowQueryBuilder(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public FlowFromSource from(FlowParticipant source) {
        TaintFlowImpl singleTaintFlow = new TaintFlowImpl();

        singleTaintFlow.setFrom(source);
        return new FlowFromSource(taintFlowQuery, singleTaintFlow);
    }
}
