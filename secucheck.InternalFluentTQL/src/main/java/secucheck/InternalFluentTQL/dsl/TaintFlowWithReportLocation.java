package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

/**
 * This class represents that it contains complete taintflows as well as report location
 *
 */
public class TaintFlowWithReportLocation {
    private final TaintFlowQueryImpl taintFlowQuery;

    public TaintFlowWithReportLocation(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public TaintFlowQuery build() {
        return taintFlowQuery;
    }
}
