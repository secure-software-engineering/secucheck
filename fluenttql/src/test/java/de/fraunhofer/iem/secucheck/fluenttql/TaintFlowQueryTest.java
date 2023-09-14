package de.fraunhofer.iem.secucheck.fluenttql;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;
import org.junit.Test;

public class TaintFlowQueryTest {
    @Test
    public void test1() {
        TaintFlowQuery taintFlowQuery1 = (TaintFlowQuery) new SimpleFluentTQLSpecification().getFluentTQLSpecification().get(0);
        TaintFlowQuery taintFlowQuery2 = (TaintFlowQuery) new SimpleFluentTQLSpecification().getFluentTQLSpecification().get(0);

        System.out.println(taintFlowQuery1.equals(taintFlowQuery2));
        System.out.println(taintFlowQuery2.equals(taintFlowQuery1));
        System.out.println(taintFlowQuery1.hashCode());
        System.out.println(taintFlowQuery2.hashCode());
        System.out.println(taintFlowQuery1.toString());
        System.out.println(taintFlowQuery2.toString());

        TaintFlowQuery taintFlowQuery3 = (TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0);

        System.out.println(taintFlowQuery1.equals(taintFlowQuery3));
        System.out.println(taintFlowQuery3.equals(taintFlowQuery1));
        System.out.println(taintFlowQuery3.hashCode());
        System.out.println(taintFlowQuery3.toString());
    }
}
