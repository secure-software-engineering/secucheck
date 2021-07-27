package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import java.util.Objects;

/**
 * This class represents that it contains complete taint flows with report message
 *
 * @author Ranjith Krishnamurthy
 */
public class TaintFlowWithReportMessage {
    private final TaintFlowQueryImpl taintFlowQuery;

    public TaintFlowWithReportMessage(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public TaintFlowQuery build() {
        return taintFlowQuery;
    }

    public TaintFlowWithReportLocation at(LOCATION reportLocation) {
        Objects.requireNonNull(reportLocation, "at() method's argument is null.");

        taintFlowQuery.setReportLocation(reportLocation);
        return new TaintFlowWithReportLocation(taintFlowQuery);
    }
}
