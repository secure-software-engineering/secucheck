package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

import java.util.Objects;

/**
 * Builder for building TaintFlowQuery
 *
 */
public class TaintFlowQueryBuilder {
    private TaintFlowQueryImpl taintFlowQuery = null;

    public TaintFlowQueryBuilder(String taintFlowQueryID) {
        if (taintFlowQuery == null)
            taintFlowQuery = new TaintFlowQueryImpl(taintFlowQueryID);
    }

    public TaintFlowQueryBuilder(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    public FlowFromSource from(FlowParticipant source) {
        Objects.requireNonNull(source, "from() method's argument is null.");

        TaintFlowImpl singleTaintFlow = new TaintFlowImpl();

        singleTaintFlow.setFrom(source);
        return new FlowFromSource(taintFlowQuery, singleTaintFlow);
    }
}
