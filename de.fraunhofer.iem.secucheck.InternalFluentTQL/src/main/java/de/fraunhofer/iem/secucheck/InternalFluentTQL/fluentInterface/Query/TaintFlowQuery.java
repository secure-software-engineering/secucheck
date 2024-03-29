package de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.List;

/**
 * Interface for TaintFlowQuery
 *
 * @author Ranjith Krishnamurthy
 */
public interface TaintFlowQuery extends FluentTQLSpecification {
    /**
     * Returns the List of TaintFlow
     *
     * @return List of TaintFlow
     */
    String getId();

    /**
     * Returns the List of TaintFlow
     *
     * @return List of TaintFlow
     */
    List<TaintFlow> getTaintFlows();

    /**
     * Returns the Report Message
     *
     * @return Report Message
     */
    String getReportMessage();

    /**
     * Returns the QueriesSet
     *
     * @return QueriesSet
     */
    QueriesSet getQueriesSet();

    /**
     * Returns the Report Location
     *
     * @return Report Location
     */
    LOCATION getReportLocation();
}
