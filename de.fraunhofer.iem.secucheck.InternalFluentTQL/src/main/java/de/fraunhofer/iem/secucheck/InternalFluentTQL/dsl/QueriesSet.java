package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import java.util.ArrayList;
import java.util.List;

public class QueriesSet extends FluentTQLSpecificationImpl {
    //    private String queriesSetName;
    private String categoryName;
    private List<TaintFlowQuery> taintFlowQueries = new ArrayList<TaintFlowQuery>();

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
        return taintFlowQueries;
    }
}
