package secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage;

import java.util.List;

/**
 * Interface for TaintFlow
 *
 */
public interface TaintFlow {
    /**
     * Returns the From FlowParticipant
     *
     * @return From FlowParticipant
     */
    FlowParticipant getFrom();

    /**
     * Returns the To FlowParticipant
     *
     * @return To FlowParticipant
     */
    FlowParticipant getTo();

    /**
     * Returns the List of notThrough FlowParticipant
     *
     * @return List of notThrough FlowParticipant
     */
    List<FlowParticipant> getNotThrough();

    /**
     * Returns the List of through FlowParticipant
     *
     * @return List of through FlowParticipant
     */
    List<FlowParticipant> getThrough();
}
