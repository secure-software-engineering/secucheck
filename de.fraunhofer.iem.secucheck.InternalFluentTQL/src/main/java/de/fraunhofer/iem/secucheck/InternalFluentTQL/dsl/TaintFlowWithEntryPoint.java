package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

public class TaintFlowWithEntryPoint {
	
    private final TaintFlowQueryImpl taintFlowQuery;
    
    public TaintFlowWithEntryPoint(TaintFlowQuery taintFlowQuery) {
    	this.taintFlowQuery = (TaintFlowQueryImpl) taintFlowQuery;
    }
    
    public FlowFromSource from(FlowParticipant source) {
        Objects.requireNonNull(source, "from() method's argument is null.");

        TaintFlowImpl singleTaintFlow = new TaintFlowImpl();

        singleTaintFlow.setFrom(source);
        return new FlowFromSource(taintFlowQuery, singleTaintFlow);
    }
    
}
