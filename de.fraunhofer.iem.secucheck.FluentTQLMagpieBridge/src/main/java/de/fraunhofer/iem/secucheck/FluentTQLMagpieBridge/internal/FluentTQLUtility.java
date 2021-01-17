package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import java.util.ArrayList;
import java.util.List;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import de.fraunhofer.iem.secucheck.analysis.query.CompositeTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.query.InputParameter;
import de.fraunhofer.iem.secucheck.analysis.query.MethodImpl;
import de.fraunhofer.iem.secucheck.analysis.query.OutputParameter;
import de.fraunhofer.iem.secucheck.analysis.query.ReportSite;
import de.fraunhofer.iem.secucheck.analysis.query.ReturnValue;
import de.fraunhofer.iem.secucheck.analysis.query.TaintFlowQueryImpl;

public class FluentTQLUtility {
    public static List<CompositeTaintFlowQueryImpl> getCompositeTaintFlowQueries(List<TaintFlowQuery> taintFlowQueries) {
        List<CompositeTaintFlowQueryImpl> compositeQueries = new ArrayList<CompositeTaintFlowQueryImpl>();

        for (TaintFlowQuery flowQuery : taintFlowQueries) {
            CompositeTaintFlowQueryImpl compositeQuery = getCompositeTaintFlowQuery(flowQuery);
            compositeQueries.add(compositeQuery);
        }

        return compositeQueries;
    }

    public static CompositeTaintFlowQueryImpl getCompositeTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        CompositeTaintFlowQueryImpl compositeQuery = new CompositeTaintFlowQueryImpl();
        compositeQuery.setReportMessage(taintFlowQuery.getReportMessage());
        compositeQuery.setReportLocation(getReportLocation(taintFlowQuery.getReportLocation()));

        for (TaintFlow taintFlow : taintFlowQuery.getTaintFlows()) {
            TaintFlowQueryImpl taintFlowQueryImpl = getTaintFlowQuery(taintFlow);
            compositeQuery.addQuery(taintFlowQueryImpl);
        }

        return compositeQuery;
    }

    private static ReportSite getReportLocation(LOCATION location) {
        switch (location) {
            case SINK:
                return ReportSite.Sink;
            case SOURCE:
                return ReportSite.Source;
            case SOURCEANDSINK:
                return ReportSite.SourceAndSink;
        }
        return ReportSite.Sink;
    }

    private static TaintFlowQueryImpl getTaintFlowQuery(TaintFlow taintFlow) {
        TaintFlowQueryImpl taintFlowQuery = new TaintFlowQueryImpl();

        if (taintFlow.getFrom() != null) {
            for (MethodImpl method : constructMethodsFromParticipant(taintFlow.getFrom())) {
                taintFlowQuery.addFrom(method);
            }
        }

        if (taintFlow.getNotThrough() != null) {
            for (MethodImpl method : constructMethodsFromParticipants(taintFlow.getNotThrough())) {
                taintFlowQuery.addNotThrough(method);
            }
        }

        if (taintFlow.getThrough() != null) {
            for (MethodImpl method : constructMethodsFromParticipants(taintFlow.getThrough())) {
                taintFlowQuery.addThrough(method);
            }
        }

        if (taintFlow.getTo() != null) {
            for (MethodImpl method : constructMethodsFromParticipant(taintFlow.getTo())) {
                taintFlowQuery.addTo(method);
            }
        }

        return taintFlowQuery;
    }

    private static List<MethodImpl> constructMethodsFromParticipants(List<FlowParticipant> participants) {
        List<MethodImpl> methods = new ArrayList<MethodImpl>();

        for (FlowParticipant participant : participants) {
            methods.addAll(constructMethodsFromParticipant(participant));
        }

        return methods;
    }

    private static List<MethodImpl> constructMethodsFromParticipant(FlowParticipant participant) {
        List<Method> methods = new ArrayList<Method>();

        if (participant instanceof Method) {
            methods.add((Method) participant);
        } else if (participant instanceof MethodSet) {
            MethodSet set = (MethodSet) participant;
            methods.addAll(set.getMethods());
        }

        List<MethodImpl> methodImpls = new ArrayList<MethodImpl>();

        for (Method method : methods) {
            methodImpls.add(getMethodImpl(method));
        }

        return methodImpls;
    }

    private static MethodImpl getMethodImpl(Method method) {
        MethodImpl methodImpl = new MethodImpl();
        methodImpl.setName(method.getSignature());
        methodImpl.setSignature(method.getSignature());

        List<InputParameter> inputParams = new ArrayList<InputParameter>();
        if (method.getInputDeclaration() != null &&
                method.getInputDeclaration().getInputs() != null) {
            for (Input input : method.getInputDeclaration().getInputs()) {
                if (input instanceof Parameter) {
                    Parameter parameter = (Parameter) input;
                    InputParameter inputParameter = new InputParameter();
                    inputParameter.setNumber(parameter.getParameterId());
                    inputParams.add(inputParameter);
                } else if (input instanceof ThisObject) {
                    methodImpl.setInputThis(true);
                }
            }
            methodImpl.setInputParameters(inputParams);
        }


        ReturnValue returnValue = null;
        List<OutputParameter> outputParams = new ArrayList<OutputParameter>();

        if (method.getOutputDeclaration() != null &&
                method.getOutputDeclaration().getOutputs() != null) {
            for (Output output : method.getOutputDeclaration().getOutputs()) {
                if (output instanceof Parameter) {
                    Parameter parameter = (Parameter) output;
                    OutputParameter outputParameter = new OutputParameter();
                    outputParameter.setNumber(parameter.getParameterId());
                    outputParams.add(outputParameter);
                } else if (output instanceof ThisObject) {
                    methodImpl.setOutputThis(true);
                } else if (output instanceof Return) {
                    returnValue = new ReturnValue();
                }
            }
            methodImpl.setOutputParameters(outputParams);
            methodImpl.setReturnValue(returnValue);
        }

        return methodImpl;
    }
}
