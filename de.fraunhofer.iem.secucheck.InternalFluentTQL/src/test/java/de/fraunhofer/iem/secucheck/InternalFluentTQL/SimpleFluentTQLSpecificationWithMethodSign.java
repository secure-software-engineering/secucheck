package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import java.util.ArrayList;
import java.util.List;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

public class SimpleFluentTQLSpecificationWithMethodSign implements FluentTQLUserInterface {
	
	static MethodSignature sourceSignature = new MethodSignatureBuilder()
			.atClass("Test").returns("java.lang.String").named("getSecret").parameter("")
			.configure();
	static Method source = new MethodConfigurator(sourceSignature)
            .out().returnValue()
            .configure();

	static MethodSignature sanitizerSignature = new MethodSignatureBuilder()
			.atClass("Test").returns("java.lang.String").named("sanitize").parameter("java.lang.String")
			.configure();
    static Method sanitizer = new MethodConfigurator(sanitizerSignature)
            .in().param(0)
            .out().returnValue()
            .configure();

    static MethodSignature sinkSignature = new MethodSignatureBuilder()
    		.atClass("Test").returns("void").named("printSecret").parameter("java.lang.String")
    		.configure();
    static Method sink = new MethodConfigurator(sinkSignature)
            .in().param(0)
            .configure();

    static MethodSet myMethodSet = new MethodSet("Testing MethodSet")
            .addMethod(source)
            .addMethod(sanitizer)
            .addMethod(sink);

    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {

        TaintFlowQuery simpleTaintFlow = new TaintFlowQueryBuilder("SimpleSpec")
                .from(source)
                .notThrough(sanitizer)
                .to(sink)
                .report("A simple TaintFlow is present here!!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecification = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecification.add(simpleTaintFlow);

        return myFluentTQLSpecification;
    }
    
}
