package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * QueriesSet contains list of TaintFlowQuery
 *
 * @author Ranjith Krishnamurthy
 */
public class QueriesSet extends FluentTQLSpecificationImpl {
    //    private String queriesSetName;
    private final String categoryName;
    private final Set<TaintFlowQuery> taintFlowQueries = new HashSet<>();

    private final List<TaintFlowQuery> taintFlowQueriesAsList = new ArrayList<>();

    public QueriesSet(String categoryName) {
//        this.queriesSetName = queriesSetName;
        this.categoryName = categoryName;
    }

    public QueriesSet addTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        taintFlowQueries.add(taintFlowQuery);
        return this;
    }

/*    public String getName() {
        return queriesSetName;
    }
*/

    public String getCategoryName() {
        return categoryName;
    }

    public List<TaintFlowQuery> getTaintFlowQueries() {
        taintFlowQueriesAsList.clear();
        taintFlowQueriesAsList.addAll(taintFlowQueries);
        return taintFlowQueriesAsList;
    }
}
