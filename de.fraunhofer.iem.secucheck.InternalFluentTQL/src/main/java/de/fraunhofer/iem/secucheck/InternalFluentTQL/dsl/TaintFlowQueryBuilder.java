package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.EntryPoint.EntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

import java.util.List;
import java.util.Objects;

/**
 * Builder for building {@link TaintFlowQuery}
 *
 * @author Ranjith Krishnamurthy
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
    
    public TaintFlowWithEntryPoint atAllEntryPoints(List<EntryPoint> entryPoints) {
    	Objects.requireNonNull(entryPoints, "atAllEntryPoints() method's argument is null.");
    	taintFlowQuery.setEntryPoints(entryPoints);
    	taintFlowQuery.setDSLEntryPoints(false);
    	return new TaintFlowWithEntryPoint(taintFlowQuery);
    }
    
    public TaintFlowWithEntryPoint atOnlyDSLEntryPoints(List<EntryPoint> entryPoints) {
    	Objects.requireNonNull(entryPoints, "atOnlyDSLEntryPoints() method's argument is null.");
    	taintFlowQuery.setEntryPoints(entryPoints);
    	taintFlowQuery.setDSLEntryPoints(true);
    	return new TaintFlowWithEntryPoint(taintFlowQuery);
    }

    public FlowFromSource from(FlowParticipant source) {
        Objects.requireNonNull(source, "from() method's argument is null.");

        TaintFlowImpl singleTaintFlow = new TaintFlowImpl();

        singleTaintFlow.setFrom(source);
        return new FlowFromSource(taintFlowQuery, singleTaintFlow);
    }
}
