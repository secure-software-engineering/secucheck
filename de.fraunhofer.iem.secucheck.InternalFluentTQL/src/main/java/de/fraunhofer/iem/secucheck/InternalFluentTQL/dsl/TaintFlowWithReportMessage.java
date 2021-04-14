package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

/**
 * This class represents that it contains complete taintflows with report message
 *
 * @author Ranjith Krishnamurthy
 */
public class TaintFlowWithReportMessage {
    private final TaintFlowImpl taintFlow;
    private final TaintFlowQueryImpl taintFlowQuery;

    public TaintFlowWithReportMessage(TaintFlowQuery taintFlowQuery, TaintFlow taintFlow) {
        this.taintFlow = (TaintFlowImpl) taintFlow;
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public TaintFlowQuery build() {
        return taintFlowQuery;
    }

    public TaintFlowWithReportLocation at(LOCATION reportLocation) {
        taintFlowQuery.setReportLocation(reportLocation);
        return new TaintFlowWithReportLocation(taintFlowQuery);
    }
}
