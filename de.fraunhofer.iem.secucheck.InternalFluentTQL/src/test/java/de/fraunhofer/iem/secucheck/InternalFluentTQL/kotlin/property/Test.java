package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.property;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

public class Test {
    @org.junit.Test
    public void test1() {
        MethodSignature sig1 = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Employee")
                .property("firstName", "String")
                .getter();

        MethodSignature sig2 = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Employee")
                .property("firstName", "String")
                .setter();

        System.out.println(sig1);
        System.out.println(sig2);
    }

    @org.junit.Test
    public void test2() {
        MethodSignature sig1 = new MethodSignatureBuilder()
                .topLevelMember("EmployeeUtil", "de.fraunhofer.iem")
                .extensionProperty("List", "lastIndex", "Int")
                .getter();

        System.out.println(sig1);

        MethodSignature sinkSig = new MethodSignatureBuilder()
                .atClass("javax.persistence.TypedQuery")
                .property("singleResult", "Any")
                .getter();

        System.out.println(sinkSig);
    }
}
