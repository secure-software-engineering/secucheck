package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

/**
 * This class represents that this contains a complete taintflow without any report message and report location.
 *
 * @author Ranjith Krishnamurthy
 */
public class JustTaintFlow {
    private final TaintFlowImpl taintFlow;
    private final TaintFlowQueryImpl taintFlowQuery;

    public JustTaintFlow(TaintFlowQuery taintFlowQuery, TaintFlow taintFlow) {
        this.taintFlow = (TaintFlowImpl) taintFlow;
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public TaintFlowWithReportMessage report(String reportMessage) {
        taintFlowQuery.setReportMessage(reportMessage);
        return new TaintFlowWithReportMessage(taintFlowQuery, taintFlow);
    }

    public TaintFlowQueryBuilder and() {
        return new TaintFlowQueryBuilder(taintFlowQuery);
    }
}
