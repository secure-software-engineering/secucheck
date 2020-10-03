package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

/**
 * This class represents that it contains complete TaintFlow with Report Message.
 *
 * @author Ranjith Krishnamurthy
 */
public class TaintFlowWithReportMessage {
    private final TaintFlowImpl taintFlow;
    private final TaintFlowQueryImpl taintFlowQuery;

    /**
     * This constructs the TaintFlowWithReportMessage with the given TaintFlowQuery and the TaintFlow
     *
     * @param taintFlowQuery TaintFlowQuery
     * @param taintFlow      TaintFlow
     */
    public TaintFlowWithReportMessage(TaintFlowQuery taintFlowQuery, TaintFlow taintFlow) {
        this.taintFlow = (TaintFlowImpl) taintFlow;
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    /**
     * This builds the complete TaintFlowQuery with complete TaintFlow, ReportMessage and ReportLocation default to SOURCEANDSINK
     *
     * @return TaintFlowQuery
     */
    public TaintFlowQuery build() {
        return taintFlowQuery;
    }

    /**
     * This adds the given Report location to the TaintFlowQuery.
     *
     * @param reportLocation Report Location
     * @return TaintFlowWithReportLocation: Indicates that it contains complete TaintFlow, Report Message and the Report Location
     */
    public TaintFlowWithReportLocation at(LOCATION reportLocation) {
        taintFlowQuery.setReportLocation(reportLocation);
        return new TaintFlowWithReportLocation(taintFlowQuery);
    }
}
