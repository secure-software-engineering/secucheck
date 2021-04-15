package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of TaintFlow
 *
 * @author Ranjith Krishnamurthy
 */
class TaintFlowImpl implements TaintFlow {
    private FlowParticipant from;
    private final Set<FlowParticipant> notThrough = new HashSet<>();
    private final Set<FlowParticipant> through = new HashSet<FlowParticipant>();
    private FlowParticipant to;
    private TaintFlowQuery taintFlowQuery;

    private final List<FlowParticipant> notThroughAsList = new ArrayList<>();
    private final List<FlowParticipant> throughAsList = new ArrayList<>();

    public TaintFlowQuery getTaintFlowQuery() {
        return taintFlowQuery;
    }

    public void setTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = taintFlowQuery;
    }

    public FlowParticipant getFrom() {
        return from;
    }

    public void setFrom(FlowParticipant from) {
        this.from = from;
    }

    public List<FlowParticipant> getNotThrough() {
        notThroughAsList.clear();
        notThroughAsList.addAll(notThrough);
        return notThroughAsList;
    }

    public void addNotThrough(FlowParticipant notThrough) {
        this.notThrough.add(notThrough);
    }

    public List<FlowParticipant> getThrough() {
        throughAsList.clear();
        throughAsList.addAll(through);
        return throughAsList;
    }

    public void addThrough(FlowParticipant through) {
        this.through.add(through);
    }

    public FlowParticipant getTo() {
        return to;
    }

    public void setTo(FlowParticipant to) {
        this.to = to;
    }
}
