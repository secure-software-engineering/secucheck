package secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that there is duplicate TaintFlowQuery ID present.
 *
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
