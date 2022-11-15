package de.fraunhofer.iem.secucheck.fluenttql.english;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import org.junit.Test;

public class BriefFluentTQL2EnglishTest {
    @Test
    public void test1() {
        System.out.println(
                new BriefFluentTQL2Eng().translate(
                        (TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0)
                )
        );
    }
}
