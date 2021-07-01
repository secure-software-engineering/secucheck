package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import java.util.*;

/**
 * QueriesSet contains list of {@link TaintFlowQuery}
 *
 * @author Ranjith Krishnamurthy
 */
public class QueriesSet extends FluentTQLSpecificationImpl {
    private final String categoryName;
    private final Set<TaintFlowQuery> taintFlowQueries = new LinkedHashSet<>();

    private final List<TaintFlowQuery> taintFlowQueriesAsList = new ArrayList<>();

    public QueriesSet(String categoryName) {
        this.categoryName = categoryName;
    }

    public QueriesSet addTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        Objects.requireNonNull(taintFlowQuery, "addTaintFlowQuery() method's argument is null.");

        taintFlowQueries.add(taintFlowQuery);
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<TaintFlowQuery> getTaintFlowQueries() {
        taintFlowQueriesAsList.clear();
        taintFlowQueriesAsList.addAll(taintFlowQueries);
        return taintFlowQueriesAsList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + taintFlowQueries.hashCode();

        // result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        QueriesSet other = (QueriesSet) obj;
        /*if (categoryName == null) {
            if (other.getCategoryName() != null)
                return false;
        } else if (!categoryName.equals(other.getCategoryName()))
            return false;*/

        if (taintFlowQueries.size() != other.getTaintFlowQueries().size()) return false;

        if (!taintFlowQueries.containsAll(other.getTaintFlowQueries())) return false;
        return other.getTaintFlowQueries().containsAll(taintFlowQueries);
    }

    @Override
    public String toString() {
        return "QueriesSet(\"" + categoryName + "\")[" + taintFlowQueries.size() + "]";
    }
}
