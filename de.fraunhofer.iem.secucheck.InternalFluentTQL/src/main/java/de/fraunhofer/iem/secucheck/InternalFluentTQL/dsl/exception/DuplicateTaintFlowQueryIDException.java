package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

/**
 * This exception indicates that there is duplicate {@link TaintFlowQuery} ID present.
 *
 * @author Ranjith Krishnamurthy
 */
public class DuplicateTaintFlowQueryIDException extends FluentTQLException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs the DuplicateTaintFlowQueryIDException with the given message.
     *
     * @param taintFlowQueryId TaintFlowQuery ID.
     */
    public DuplicateTaintFlowQueryIDException(String taintFlowQueryId) {
        super("Duplicate TaintFlowQuery ID [" + taintFlowQueryId + "] found. Please use an unique id for the TaintFlowQuery.");
    }
}
