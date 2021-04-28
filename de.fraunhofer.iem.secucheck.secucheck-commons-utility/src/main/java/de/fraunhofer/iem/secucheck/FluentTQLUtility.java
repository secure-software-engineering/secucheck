package de.fraunhofer.iem.secucheck;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import java.util.List;

/**
 * Utility for the FluentTQL DSL
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLUtility {
    /**
     * From the given list of TaintFlowQueries it returns the TaintFLowQuery of the given ID. If not null.
     *
     * @param taintFlowQueries List of TaintFlowQueries
     * @param taintFlowQueryId TaintFlowQuery ID that needs to be returned
     * @return TaintFlowQuery with the given TaintFlowQuery ID. If not found null
     */
    public static TaintFlowQuery getTaintFlowQueryFromID(List<TaintFlowQuery> taintFlowQueries, String taintFlowQueryId) {
        for (TaintFlowQuery taintFlowQuery : taintFlowQueries) {
            if (taintFlowQuery.getId().equals(taintFlowQueryId)) {
                return taintFlowQuery;
            }
        }

        return null;
    }
}
