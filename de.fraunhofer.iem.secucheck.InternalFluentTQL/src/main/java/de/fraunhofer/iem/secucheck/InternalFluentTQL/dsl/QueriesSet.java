package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class combines the multiple TaintFlowQuery into one (equivalent to OR operator).
 *
 * @author Ranjith Krishnamurthy
 */
public class QueriesSet extends FluentTQLSpecificationImpl {
    private final String categoryName;
    private final List<TaintFlowQuery> taintFlowQueries = new ArrayList<>();

    /**
     * Constructor that sets the category name for the QueriesSet
     *
     * @param categoryName Category Name
     */
    public QueriesSet(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * This method adds the TaintFLowQuery to the QueriesSet
     *
     * @param taintFlowQuery TaintFlowQuery
     * @return QueriesSet
     */
    public QueriesSet addTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        Objects.requireNonNull(taintFlowQuery);

        taintFlowQueries.add(taintFlowQuery);
        return this;
    }

    /**
     * This method returns the Category Name of the QueriesSet
     *
     * @return Category Name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * This method returns the List of all the TaintFlowQueries in this QueriesSet
     *
     * @return List of all the TaintFlowQueries
     */
    public List<TaintFlowQuery> getTaintFlowQueries() {
        return taintFlowQueries;
    }
}
