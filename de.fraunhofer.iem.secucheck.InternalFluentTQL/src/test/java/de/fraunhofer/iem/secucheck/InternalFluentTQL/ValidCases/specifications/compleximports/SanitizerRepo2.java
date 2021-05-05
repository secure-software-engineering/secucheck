package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessAnnotation;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowThisObject;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class SanitizerRepo2 {
    @ImportAndProcessAnnotation
    public static SanitizerRepo1 sanitizerRepo1 = new SanitizerRepo1();

    @InFlowThisObject
    @OutFlowReturnValue
    public static Method sanitizer7 = new MethodSelector("Test1: java.lang.String sanitizer7()");

    @InFlowThisObject
    @OutFlowReturnValue
    public static Method sanitizer8 = new MethodSelector("Test1: java.lang.String sanitizer8()");

    @InFlowThisObject
    @OutFlowReturnValue
    public static Method sanitizer9 = new MethodSelector("Test1: java.lang.String sanitizer9()");

    @InFlowThisObject
    @OutFlowReturnValue
    public static Method sanitizer10 = new MethodSelector("Test1: java.lang.String sanitizer10()");

    @InFlowThisObject
    @OutFlowReturnValue
    public static Method sanitizer11 = new MethodSelector("Test1: java.lang.String sanitizer11()");

    @InFlowThisObject
    @OutFlowReturnValue
    public static Method sanitizer12 = new MethodSelector("Test1: java.lang.String sanitizer12()");

    public static MethodSet sanitizers = new MethodSet("sanitizerdummy")
            .addMethod(sanitizerRepo1.sanitizer1)
            .addMethod(sanitizerRepo1.sanitizer2)
            .addMethod(sanitizerRepo1.sanitizer3)
            .addMethod(sanitizerRepo1.sanitizer4)
            .addMethod(sanitizerRepo1.sanitizer5)
            .addMethod(sanitizerRepo1.sanitizer6)
            .addMethod(sanitizer7)
            .addMethod(sanitizer8)
            .addMethod(sanitizer9)
            .addMethod(sanitizer10)
            .addMethod(sanitizer11)
            .addMethod(sanitizer12);
}
