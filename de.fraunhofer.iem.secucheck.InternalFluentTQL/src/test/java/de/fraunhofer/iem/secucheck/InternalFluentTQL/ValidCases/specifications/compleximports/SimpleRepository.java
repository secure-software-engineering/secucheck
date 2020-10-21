package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowThisObject;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class SimpleRepository {
    @InFlowThisObject
    public Method sink1 = new MethodSelector("Test1: java.lang.String sink1()");

    @InFlowThisObject
    public Method sink2 = new MethodSelector("Test1: java.lang.String sink2()");

    @InFlowThisObject
    public Method sink3 = new MethodSelector("Test1: java.lang.String sink3()");

    @InFlowThisObject
    public Method sink4 = new MethodSelector("Test1: java.lang.String sink4()");

    @InFlowThisObject
    public Method sink5 = new MethodSelector("Test1: java.lang.String sink5()");

    @InFlowThisObject
    public Method sink6 = new MethodSelector("Test1: java.lang.String sink6()");

    public MethodSet sinks = new MethodSet("dummysink")
            .addMethod(sink1)
            .addMethod(sink2)
            .addMethod(sink3)
            .addMethod(sink4)
            .addMethod(sink5)
            .addMethod(sink6);
}
