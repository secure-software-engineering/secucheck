package secucheck.fluentTQL2English;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
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
