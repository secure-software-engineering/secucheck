package de.fraunhofer.iem.secucheck;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.EntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.MethodEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import de.fraunhofer.iem.secucheck.analysis.query.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for the SecuCheck-core, used in the secucheck-magpiebridge in order to get the things required for the secuchcek-core
 *
 * @author Ranjith Krishnamurthy
 */
public final class SecuCheckCoreQueryUtility {
    /**
     * Converts the InternalFluentTQL Taintflow queries into secucheck-core composite taintflowqueries
     *
     * @param taintFlowQueries InternalFLuentTQL taintflow queries
     * @return Secuchcek-core taintflow queries
     */
    public static List<SecucheckTaintFlowQueryImpl> getCompositeTaintFlowQueries(List<TaintFlowQuery> taintFlowQueries) {
        List<SecucheckTaintFlowQueryImpl> compositeQueries = new ArrayList<SecucheckTaintFlowQueryImpl>();

        for (TaintFlowQuery flowQuery : taintFlowQueries) {
            SecucheckTaintFlowQueryImpl compositeQuery = getCompositeTaintFlowQuery(flowQuery);
            compositeQueries.add(compositeQuery);
        }

        return compositeQueries;
    }

    /**
     * Converts the InternalFluentTQL single Taintflow query into secucheck-core composite single taintflowquery
     *
     * @param taintFlowQuery InternalFLuentTQL taintflow queries
     * @return Secuchcek-core single taintflow query
     */
    public static SecucheckTaintFlowQueryImpl getCompositeTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        SecucheckTaintFlowQueryImpl compositeQuery = new SecucheckTaintFlowQueryImpl(taintFlowQuery.getId());
        compositeQuery.setReportMessage(taintFlowQuery.getReportMessage());
        compositeQuery.setReportLocation(getReportLocation(taintFlowQuery.getReportLocation()));
        
        List<de.fraunhofer.iem.secucheck.analysis.query.EntryPoint> compositeEntryPoints = new ArrayList<de.fraunhofer.iem.secucheck.analysis.query.EntryPoint>();
        for (EntryPoint entryPoint : taintFlowQuery.getEntryPoints()) {
        	if(entryPoint instanceof MethodEntryPoint) {
        		de.fraunhofer.iem.secucheck.analysis.query.EntryPoint compositeEntryPoint = new de.fraunhofer.iem.secucheck.analysis.query.EntryPoint();
        		String fullyQualifiedNameEntryPoint = ((MethodEntryPoint) entryPoint).getMethodEntryPointName();
        		String methodNameAndParam = fullyQualifiedNameEntryPoint.split(":")[1].trim().split("\\s")[1].trim();
        		compositeEntryPoint.addMethod(methodNameAndParam.substring(0, methodNameAndParam.indexOf("(")));
        		compositeEntryPoint.setAllMethods(false);
        		compositeEntryPoint.setCanonicalClassName(fullyQualifiedNameEntryPoint.split(":")[0]);
        		compositeEntryPoints.add(compositeEntryPoint);
        	}
        }
        
        if(!compositeEntryPoints.isEmpty()) {
        	compositeQuery.setEntryPoint(compositeEntryPoints);
        }
        
        for (TaintFlow taintFlow : taintFlowQuery.getTaintFlows()) {
            TaintFlowImpl taintFlowQueryImpl = getTaintFlowQuery(taintFlow);
            compositeQuery.addQuery(taintFlowQueryImpl);
        }

        return compositeQuery;
    }

    /**
     * Converts the InternalFluentTQL LOCATION into secucheck-core ReportSite
     *
     * @param location InternalFluentTQL LOCATION
     * @return secucheck-core ReportSite
     */
    public static ReportSite getReportLocation(LOCATION location) {
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

    /**
     * Converts the InternalFluentTQL TaintFlow to secucheck-core TaintFlow
     *
     * @param taintFlow InternalFluentTQL Taintflow
     * @return secucheck-core Taintflow
     */
    public static TaintFlowImpl getTaintFlowQuery(TaintFlow taintFlow) {
        TaintFlowImpl taintFlowQuery = new TaintFlowImpl();

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

    /**
     * Builds the secucheck-core methods from the InternalFluentTQL flowparticipants
     *
     * @param participants InternalFluentTQL flowparticipants
     * @return secucheck-core methods
     */
    public static List<MethodImpl> constructMethodsFromParticipants(List<FlowParticipant> participants) {
        List<MethodImpl> methods = new ArrayList<MethodImpl>();

        for (FlowParticipant participant : participants) {
            methods.addAll(constructMethodsFromParticipant(participant));
        }

        return methods;
    }

    /**
     * Builds the secucheck-core methods from the single InternalFluentTQL flowparticipant
     *
     * @param participant Single InternalFluentTQL flowparticipant
     * @return secucheck-core methods
     */
    public static List<MethodImpl> constructMethodsFromParticipant(FlowParticipant participant) {
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

    /**
     * Builds the secucheck-core method from the single InternalFluentTQL method
     *
     * @param method Single InternalFluentTQL method
     * @return single secucheck-core method
     */
    public static MethodImpl getMethodImpl(Method method) {
        MethodImpl methodImpl = new MethodImpl();
        
        if(method.getSignature() != null) {
        	methodImpl.setName(method.getSignature());
            methodImpl.setSignature(method.getSignature());
        }
        else {
        	String signature = method.getMethodSignature().getClassOfMethodSign()+": "
        						+method.getMethodSignature().getReturnOfMethodSign()+" "
        						+method.getMethodSignature().getNameOfMethodSign()+"("
        						+method.getMethodSignature().getParamOfMethodSign()+")";
        	methodImpl.setName(signature);
            methodImpl.setSignature(signature);
        }

        List<InputParameter> inputParams = new ArrayList<InputParameter>();
        if (method.getInputDeclaration() != null &&
                method.getInputDeclaration().getInputs() != null) {
            for (Input input : method.getInputDeclaration().getInputs()) {
                if (input instanceof Parameter) {
                    Parameter parameter = (Parameter) input;
                    InputParameter inputParameter = new InputParameter();
                    inputParameter.setParamID(parameter.getParameterId());
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
                    outputParameter.setParamID(parameter.getParameterId());
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
