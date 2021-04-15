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
}