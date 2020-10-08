package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the TaintFlow
 *
 * @author Ranjith Krishnamurthy
 */
class TaintFlowImpl implements TaintFlow {
    private FlowParticipant from;
    private final List<FlowParticipant> notThrough = new ArrayList<FlowParticipant>();
    private final List<FlowParticipant> through = new ArrayList<FlowParticipant>();
    private FlowParticipant to;
    private TaintFlowQuery taintFlowQuery;

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
        Objects.requireNonNull(from);

        this.from = from;
    }

    public List<FlowParticipant> getNotThrough() {
        return notThrough;
    }

    public void addNotThrough(FlowParticipant notThrough) {
        Objects.requireNonNull(notThrough);

        this.notThrough.add(notThrough);
    }

    public List<FlowParticipant> getThrough() {
        return through;
    }

    public void addThrough(FlowParticipant through) {
        Objects.requireNonNull(through);

        this.through.add(through);
    }

    public FlowParticipant getTo() {
        return to;
    }

    public void setTo(FlowParticipant to) {
        this.to = to;
    }
}
