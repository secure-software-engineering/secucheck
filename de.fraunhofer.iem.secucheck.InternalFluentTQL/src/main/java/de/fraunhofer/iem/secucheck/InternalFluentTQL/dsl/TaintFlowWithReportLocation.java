package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

public class TaintFlowWithReportLocation {
    private final TaintFlowQueryImpl taintFlowQuery;

    public TaintFlowWithReportLocation(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public TaintFlowQuery build() {
        return taintFlowQuery;
    }
}
