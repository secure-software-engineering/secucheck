package secucheck.fluentTQL2English;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
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
