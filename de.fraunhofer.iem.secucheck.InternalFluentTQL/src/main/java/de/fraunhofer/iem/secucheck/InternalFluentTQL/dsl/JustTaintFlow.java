package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.Objects;

/**
 * This class represents the complete TaintFlow, but it does not contain any report message or report location.
 *
 * @author Ranjith Krishnamurthy
 */
public class JustTaintFlow {
    private final TaintFlowImpl taintFlow;
    private final TaintFlowQueryImpl taintFlowQuery;

    /**
     * Constructs the JustTaintFlow with the given TaintFLowQuery and TaintFlow.
     *
     * @param taintFlowQuery TaintFlowQuery
     * @param taintFlow      TaintFlow
     */
    public JustTaintFlow(TaintFlowQuery taintFlowQuery, TaintFlow taintFlow) {
        this.taintFlow = (TaintFlowImpl) taintFlow;
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    /**
     * This method adds the Report Message to the TaintFlowQuery.
     *
     * @param reportMessage ReportMessage
     * @return TaintFlowWithReportMessage: Indicates that it contains complete TaintFlow and Report Message.
     */
    public TaintFlowWithReportMessage report(String reportMessage) {
        Objects.requireNonNull(reportMessage, "report() method's argument is null.");

        taintFlowQuery.setReportMessage(reportMessage);
        return new TaintFlowWithReportMessage(taintFlowQuery, taintFlow);
    }

    public TaintFlowQueryBuilder and() {
        return new TaintFlowQueryBuilder(taintFlowQuery);
    }
}
