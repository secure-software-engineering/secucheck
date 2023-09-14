package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;

/**
 * This exception indicates that there is duplicate {@link TaintFlowQuery} ID present.
 *
 * @author Ranjith Krishnamurthy
 */
public class DuplicateTaintFlowQueryIDException extends FluentTQLException {
    /**
     * Constructs the DuplicateTaintFlowQueryIDException with the given message.
     *
     * @param taintFlowQueryId TaintFlowQuery ID.
     */
    public DuplicateTaintFlowQueryIDException(String taintFlowQueryId) {
        super("Duplicate TaintFlowQuery ID [" + taintFlowQueryId + "] found. Please use an unique id for the TaintFlowQuery.");
    }
}
