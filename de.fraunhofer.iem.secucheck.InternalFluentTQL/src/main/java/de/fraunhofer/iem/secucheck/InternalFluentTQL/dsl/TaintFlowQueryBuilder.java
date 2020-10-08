package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

import java.util.Objects;

/**
 * This class is the Builder to build the TaintFlowQuery.
 *
 * @author Ranjith Krishnamurthy
 */
public class TaintFlowQueryBuilder {
    private TaintFlowQueryImpl taintFlowQuery = null;

    /**
     * Constructs the TaintFLowQueryBuilder to build the TaintFLowQuery
     */
    public TaintFlowQueryBuilder() {
        if (taintFlowQuery == null)
            taintFlowQuery = new TaintFlowQueryImpl();
    }

    /**
     * Constructs the TaintFlowQueryBuilder with the given TaintFlowQuery object.
     *
     * @param taintFlowQuery TaintFlowQuery
     */
    public TaintFlowQueryBuilder(TaintFlowQuery taintFlowQuery) {
        this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }

    /**
     * This method is used to specify the Source Method/MethodSet of a TaintFLow.
     *
     * @param source Source Method/MethodSet
     * @return FlowFromSource: Indicates that it contains incomplete TaintFlow started from Source Method/MethodSet
     */
    public FlowFromSource from(FlowParticipant source) {
        Objects.requireNonNull(source, "from() method's argument is null.");

        TaintFlowImpl singleTaintFlow = new TaintFlowImpl();

        singleTaintFlow.setFrom(source);
        return new FlowFromSource(taintFlowQuery, singleTaintFlow);
    }
}
