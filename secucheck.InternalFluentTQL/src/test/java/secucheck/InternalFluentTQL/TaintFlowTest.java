package secucheck.InternalFluentTQL;

import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import org.junit.Test;

import java.util.List;

public class TaintFlowTest {
    @Test
    public void test1() {
        List<FluentTQLSpecification> specs = DummySpecification.getSpecForTaintFlowTest1();

        TaintFlow taintFlow1 = ((TaintFlowQuery) specs.get(0)).getTaintFlows().get(0);
        TaintFlow taintFlow2 = ((TaintFlowQuery) specs.get(1)).getTaintFlows().get(0);

        System.out.println(taintFlow1.equals(taintFlow2));
        System.out.println(taintFlow2.equals(taintFlow1));
        System.out.println(taintFlow1.hashCode());
        System.out.println(taintFlow2.hashCode());
        System.out.println(taintFlow1.toString());
        System.out.println(taintFlow2.toString());

        TaintFlow taintFlow3 = ((TaintFlowQuery) specs.get(2)).getTaintFlows().get(0);

        System.out.println(taintFlow1.equals(taintFlow3));
        System.out.println(taintFlow3.equals(taintFlow1));
        System.out.println(taintFlow3.hashCode());
        System.out.println(taintFlow3.toString());
    }
}
