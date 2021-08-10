package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.Objects;

/**
 * This class represents that this contains a complete taintflow without any report message and report location.
 *
 */
public class JustTaintFlow {
    private final TaintFlowImpl taintFlow;
    private final TaintFlowQueryImpl taintFlowQuery;

    public JustTaintFlow(TaintFlowQuery taintFlowQuery, TaintFlow taintFlow) {
        this.taintFlow = (TaintFlowImpl) taintFlow;
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public TaintFlowWithReportMessage report(String reportMessage) {
        Objects.requireNonNull(reportMessage, "report() method's argument is null.");

        taintFlowQuery.setReportMessage(reportMessage);
        return new TaintFlowWithReportMessage(taintFlowQuery, taintFlow);
    }

    public TaintFlowQueryBuilder and() {
        return new TaintFlowQueryBuilder(taintFlowQuery);
    }
}
