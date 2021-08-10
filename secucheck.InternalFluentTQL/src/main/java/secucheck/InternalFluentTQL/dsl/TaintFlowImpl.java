package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.*;

/**
 * Implementation of TaintFlow
 *
 */
class TaintFlowImpl implements TaintFlow {
    private FlowParticipant from;
    private final Set<FlowParticipant> notThrough = new LinkedHashSet<>();
    private final Set<FlowParticipant> through = new LinkedHashSet<>();
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
        Objects.requireNonNull(from, "setFrom() method's argument is null.");

        this.from = from;
    }

    public List<FlowParticipant> getNotThrough() {
        notThroughAsList.clear();
        notThroughAsList.addAll(notThrough);
        return notThroughAsList;
    }

    public void addNotThrough(FlowParticipant notThrough) {
        Objects.requireNonNull(notThrough, "addNotThrough() method's argument is null.");

        this.notThrough.add(notThrough);
    }

    public List<FlowParticipant> getThrough() {
        throughAsList.clear();
        throughAsList.addAll(through);
        return throughAsList;
    }

    public void addThrough(FlowParticipant through) {
        Objects.requireNonNull(through, "addThrough() method's argument is null.");

        this.through.add(through);
    }

    public FlowParticipant getTo() {
        return to;
    }

    public void setTo(FlowParticipant to) {
        this.to = to;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + through.hashCode();
        result = prime * result + notThrough.hashCode();
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        TaintFlowImpl other = (TaintFlowImpl) obj;
        if (from == null) {
            if (other.getFrom() != null)
                return false;
        } else if (!from.equals(other.getFrom()))
            return false;

        if (through.size() != other.getThrough().size()) return false;
        if (!through.containsAll(other.getThrough())) return false;
        if (!other.getThrough().containsAll(through)) return false;

        if (notThrough.size() != other.getNotThrough().size()) return false;
        if (!notThrough.containsAll(other.getNotThrough())) return false;
        if (!other.getNotThrough().containsAll(notThrough)) return false;

        if (to == null) {
            if (other.getTo() != null)
                return false;
        } else if (!to.equals(other.getTo()))
            return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        if (from != null) {
            str.append("from(")
                    .append(from.toString().replaceAll("[\n\r]", "").replaceAll(" ", ""))
                    .append(")\n");
        }

        if (through.size() > 0) {
            for (FlowParticipant flowParticipant : through) {
                str.append("through(")
                        .append(flowParticipant.toString().replaceAll("[\n\r]", "").replaceAll(" ", ""))
                        .append(")\n");
            }
        }

        if (notThrough.size() > 0) {
            for (FlowParticipant flowParticipant : notThrough) {
                str.append("notThrough(")
                        .append(flowParticipant.toString().replaceAll("[\n\r]", "").replaceAll(" ", ""))
                        .append(")\n");
            }
        }

        if (to != null) {
            str.append("to(")
                    .append(to.toString().replaceAll("[\n\r]", "").replaceAll(" ", ""))
                    .append(")\n");
        }

        return str.toString();
    }
}
