package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import org.junit.Test;

public class QueriesSetTest {
    @Test
    public void test1() {
        QueriesSet queriesSet1 = new QueriesSet("dummy1");
        QueriesSet queriesSet2 = new QueriesSet("dummy2");

        System.out.println(queriesSet1.equals(queriesSet2));
        System.out.println(queriesSet2.equals(queriesSet1));
        System.out.println(queriesSet1.hashCode());
        System.out.println(queriesSet2.hashCode());
        System.out.println(queriesSet1.toString());
        System.out.println(queriesSet2.toString());
    }

    @Test
    public void test2() {
        QueriesSet queriesSet1 = new QueriesSet("dummy1");
        QueriesSet queriesSet2 = new QueriesSet("dummy2");

        queriesSet1.addTaintFlowQuery((TaintFlowQuery) new SimpleFluentTQLSpecification().getFluentTQLSpecification().get(0));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(0));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(1));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(2));

        queriesSet2.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(2));
        queriesSet2.addTaintFlowQuery((TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0));
        queriesSet2.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(0));
        queriesSet2.addTaintFlowQuery((TaintFlowQuery) new SimpleFluentTQLSpecification().getFluentTQLSpecification().get(0));
        queriesSet2.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(1));

        System.out.println(queriesSet1.equals(queriesSet2));
        System.out.println(queriesSet2.equals(queriesSet1));
        System.out.println(queriesSet1.hashCode());
        System.out.println(queriesSet2.hashCode());
        System.out.println(queriesSet1.toString());
        System.out.println(queriesSet2.toString());
    }

    @Test
    public void test3() {
        QueriesSet queriesSet1 = new QueriesSet("dummy1");
        QueriesSet queriesSet2 = new QueriesSet("dummy2");

        queriesSet1.addTaintFlowQuery((TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(0));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(1));
        queriesSet1.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(2));

        queriesSet2.addTaintFlowQuery((TaintFlowQuery) DummySpecification.getSpecForTaintFlowTest1().get(2));
        queriesSet2.addTaintFlowQuery((TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0));
        queriesSet2.addTaintFlowQuery((TaintFlowQuery) new SimpleFluentTQLSpecification().getFluentTQLSpecification().get(0));

        System.out.println(queriesSet1.equals(queriesSet2));
        System.out.println(queriesSet2.equals(queriesSet1));
        System.out.println(queriesSet1.hashCode());
        System.out.println(queriesSet2.hashCode());
        System.out.println(queriesSet1.toString());
        System.out.println(queriesSet2.toString());
    }
}
