package de.fraunhofer.iem.secucheck.fluenttql.english;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import org.junit.Test;

public class FluentTQL2EnglishTest {
    @Test
    public void test1() {
        System.out.println(
                new FluentTQL2English().translate(
                        (TaintFlowQuery) new FluentTQLSpecificationTestForNoSQLInjection().getFluentTQLSpecification().get(0)
                )
        );
    }
}
