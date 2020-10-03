package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

/**
 * This class represents that it contains complete TaintFlow, Report Message and the Report Location.
 *
 * @author Ranjith Krishnamurthy
 */
public class TaintFlowWithReportLocation {
    private final TaintFlowQueryImpl taintFlowQuery;

    /**
     * Constructs the TaintFlowWithReportLocation with the given TaintFlowQuery
     *
     * @param taintFlowQuery TaintFlowQuery
     */
    public TaintFlowWithReportLocation(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    /**
     * This builds the TaintFlowQuery with the complete TaintFlow, Report Message and the Report Location
     *
     * @return TaintFlowQuery
     */
    public TaintFlowQuery build() {
        return taintFlowQuery;
    }
}
