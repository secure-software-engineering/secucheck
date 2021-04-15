package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of TaintFlowQuery
 *
 * @author Ranjith Krishnamurthy
 */
class TaintFlowQueryImpl extends FluentTQLSpecificationImpl implements TaintFlowQuery {
    private final Set<TaintFlow> taintFlows = new HashSet<>();
    private String reportMessage = "";
    private LOCATION reportLocation = LOCATION.SOURCEANDSINK;
    private QueriesSet queriesSet;

    private final List<TaintFlow> taintFlowsAsList = new ArrayList<>();

    public void addTaintFlow(TaintFlow taintFlow) {
        taintFlows.add(taintFlow);
    }

    public List<TaintFlow> getTaintFlows() {
        taintFlowsAsList.clear();
        taintFlowsAsList.addAll(taintFlows);
        return taintFlowsAsList;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + taintFlows.hashCode();
        result = prime * result + reportMessage.hashCode();
        result = prime * result + reportLocation.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        TaintFlowQueryImpl other = (TaintFlowQueryImpl) obj;

        if (taintFlows.size() != other.getTaintFlows().size()) return false;
        if (!taintFlows.containsAll(other.getTaintFlows())) return false;
        if (!other.getTaintFlows().containsAll(taintFlows)) return false;

        if (!reportMessage.equals(other.getReportMessage())) return false;

        return reportLocation.equals(other.getReportLocation());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (TaintFlow taintFlow : taintFlows) {
            str.append("TaintFlow: \n").append(taintFlow.toString()).append("\n");
        }

        str.append("Report Message = ").append(reportMessage).append("\n");
        str.append("Report Location = ").append(reportLocation).append("\n");

        return str.toString();
    }
}
