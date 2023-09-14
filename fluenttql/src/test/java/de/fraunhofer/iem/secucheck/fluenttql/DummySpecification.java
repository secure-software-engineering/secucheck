package de.fraunhofer.iem.secucheck.fluenttql;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;

import java.util.ArrayList;
import java.util.List;

public class DummySpecification {
    static Method method1 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .out().thisObject()
            .configure();

    static Method method2 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .in().thisObject()
            .configure();

    static Method method3 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .out().param(0)
            .configure();

    static Method method4 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .in().param(0)
            .configure();

    static Method method5 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .in().param(5)
            .configure();

    static Method method6 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .out().returnValue()
            .configure();

    static Method method7 = new MethodConfigurator("Test: java.lang.String getSecret()")
            .out().returnValue()
            .configure();

    static Method inputDeclarationTest1 = new MethodConfigurator("Test1: java.lang.String test1()")
            .in().thisObject().param(0).param(5).param(3).param(9)
            .configure();

    static Method inputDeclarationTest2 = new MethodConfigurator("Test2: java.lang.String test2()")
            .in().thisObject().param(0).param(3).param(5).param(9)
            .configure();

    static Method inputDeclarationTest3 = new MethodConfigurator("Test3: java.lang.String test3()")
            .in().thisObject().param(3).param(9)
            .configure();

    static Method outputDeclarationTest4 = new MethodConfigurator("Test1: java.lang.String test1()")
            .out().thisObject().param(0).param(5).param(3).param(9)
            .configure();

    static Method outputDeclarationTest1 = new MethodConfigurator("Test1: java.lang.String test1()")
            .out().thisObject().param(0).param(5).param(3).param(9).returnValue()
            .configure();

    static Method outputDeclarationTest2 = new MethodConfigurator("Test2: java.lang.String test2()")
            .out().thisObject().param(0).param(5).param(3).param(9).returnValue()
            .configure();

    static Method outputDeclarationTest3 = new MethodConfigurator("Test3: java.lang.String test3()")
            .out().thisObject().returnValue()
            .configure();

    static Method methodTest1 = new MethodConfigurator("Method1: java.lang.String methodTest1()")
            .out().thisObject().returnValue().param(0).param(5).param(2).param(8)
            .in().thisObject().param(5).param(2)
            .configure();

    static Method methodTest2 = new MethodConfigurator("Method1: java.lang.String methodTest1()")
            .in().thisObject().param(5).param(2)
            .out().thisObject().returnValue().param(0).param(5).param(2).param(8)
            .configure();

    static Method methodTest3 = new MethodConfigurator("Method3: java.lang.String methodTest3()")
            .in().thisObject().param(5).param(2)
            .out().thisObject().returnValue().param(0).param(5).param(2).param(8)
            .configure();

    public static List<FluentTQLSpecification> getSpecForTaintFlowTest1() {
        TaintFlowQuery taintFlowQuery1 = new TaintFlowQueryBuilder("Dummy1")
                .from(method1)
                .through(method2)
                .through(method3)
                .notThrough(method4)
                .notThrough(method5)
                .to(method6)
                .report("Dummy")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlowQuery2 = new TaintFlowQueryBuilder("Dummy2")
                .from(method1)
                .notThrough(method4)
                .notThrough(method5)
                .through(method2)
                .through(method3)
                .to(method6)
                .report("Dummy")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlowQuery3 = new TaintFlowQueryBuilder("Dummy3")
                .from(method5)
                .through(method4)
                .through(method3)
                .notThrough(method1)
                .notThrough(method3)
                .to(method6)
                .report("Dummy")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> specs = new ArrayList<>();
        specs.add(taintFlowQuery1);
        specs.add(taintFlowQuery2);
        specs.add(taintFlowQuery3);

        return specs;
    }
}