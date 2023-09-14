package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;

/**
 * This class represents that it contains complete taint flows as well as report location
 *
 * @author Ranjith Krishnamurthy
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