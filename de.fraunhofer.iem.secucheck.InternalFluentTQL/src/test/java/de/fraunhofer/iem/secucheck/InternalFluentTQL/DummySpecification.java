package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

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
            .in().thisObject().param(0).param(5).param(3).param(9)
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
}