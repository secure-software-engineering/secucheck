package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of TaintFlowQuery.
 *
 * @author Ranjith Krishnamurthy
 */
class TaintFlowQueryImpl extends FluentTQLSpecificationImpl implements TaintFlowQuery {
    private final List<TaintFlow> taintFlows = new ArrayList<TaintFlow>();
    private String reportMessage = "";
    private LOCATION reportLocation = LOCATION.SOURCEANDSINK;
    private QueriesSet queriesSet;

    public void addTaintFlow(TaintFlow taintFlow) {
        taintFlows.add(taintFlow);
    }

    public List<TaintFlow> getTaintFlows() {
        return taintFlows;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public QueriesSet getQueriesSet() {
        return queriesSet;
    }

    public void setQueriesSet(QueriesSet queriesSet) {
        this.queriesSet = queriesSet;
    }

    public LOCATION getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(LOCATION reportLocation) {
        this.reportLocation = reportLocation;
    }
}
