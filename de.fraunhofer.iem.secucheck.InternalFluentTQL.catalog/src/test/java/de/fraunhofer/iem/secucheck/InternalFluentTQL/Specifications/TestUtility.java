package de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.util.List;

public class TestUtility {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Displays the single method.
     *
     * @param method Method
     */
    private static void displayMethod(Method method) {
        System.out.println("\t" + ANSI_YELLOW +
                method.getSignature() + ANSI_RESET);

        if (method.getInputDeclaration() != null &&
                method.getInputDeclaration().getInputs() != null) {
            List<Input> inputs = method.getInputDeclaration().getInputs();
            System.out.println("\t\t" + ANSI_PURPLE +
                    "InputDeclaration : " + ANSI_RESET);
            for (Input input : inputs) {
                if (input instanceof Parameter) {
                    System.out.println("\t\t\t" + ANSI_GREEN +
                            "Parameter " + ((Parameter) input).getParameterId() +
                            ANSI_RESET);
                } else if (input instanceof ThisObject) {
                    System.out.println("\t\t\t" + ANSI_GREEN +
                            "ThisObject" + ANSI_RESET);
                }
            }
        }

        if (method.getOutputDeclaration() != null &&
                method.getOutputDeclaration().getOutputs() != null) {
            List<Output> outputs = method.getOutputDeclaration().getOutputs();
            System.out.println("\t\t" + ANSI_BLUE +
                    "OutputDeclaration : " + ANSI_RESET);
            for (Output output : outputs) {
                if (output instanceof Parameter) {
                    System.out.println("\t\t\t" + ANSI_GREEN +
                            "Parameter " + ((Parameter) output).getParameterId() +
                            ANSI_RESET);
                } else if (output instanceof ThisObject) {
                    System.out.println("\t\t\t" + ANSI_GREEN +
                            "ThisObject" + ANSI_RESET);
                } else if (output instanceof Return) {
                    System.out.println("\t\t\t" + ANSI_GREEN +
                            "ReturnValue" + ANSI_RESET);
                }
            }
        }
    }

    /**
     * Displays the single flowParticipant
     *
     * @param flowParticipant FlowParticipant
     */
    private static void displayFlowParticipant(FlowParticipant flowParticipant) {
        if (flowParticipant instanceof Method) {
            displayMethod((Method) flowParticipant);
        } else if (flowParticipant instanceof MethodSet) {
            for (Method method : ((MethodSet) flowParticipant).getMethods()) {
                displayMethod(method);
            }
        }
    }

    /**
     * Display single TaintFlowQuery
     *
     * @param taintFlowQuery TaintFlowQuery
     */
    private static void displayTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        System.out.println(ANSI_RED +
                "Report Message = " + ANSI_RESET +
                ANSI_GREEN + taintFlowQuery.getReportMessage() + ANSI_RESET);
        System.out.println(ANSI_RED +
                "Report Location = " + ANSI_RESET +
                ANSI_GREEN + taintFlowQuery.getReportLocation() + ANSI_RESET);

        List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();

        for (TaintFlow taintFlow : taintFlows) {
            if (taintFlow.getFrom() != null) {
                System.out.println(ANSI_RED + "Source : " + ANSI_RESET);
                displayFlowParticipant(taintFlow.getFrom());
            }

            if (taintFlow.getNotThrough() != null) {
                System.out.println(ANSI_RED + "Required Propagator : " + ANSI_RESET);
                for (FlowParticipant flowParticipant : taintFlow.getNotThrough()) {
                    displayFlowParticipant(flowParticipant);
                }
            }

            if (taintFlow.getThrough() != null) {
                for (FlowParticipant flowParticipant : taintFlow.getThrough()) {
                    System.out.println(ANSI_RED + "Sanitizer : " + ANSI_RESET);
                    displayFlowParticipant(flowParticipant);
                }
            }

            if (taintFlow.getTo() != null) {
                System.out.println(ANSI_RED + "Sink : " + ANSI_RESET);
                displayFlowParticipant(taintFlow.getTo());
            }
        }
    }

    /**
     * Displays the given FluentTQL spcifications.
     *
     * @param fluentTQLSpecifications List of FluentTQLSpecification
     */
    public static void displayFluentTQLSpecifications(List<FluentTQLSpecification> fluentTQLSpecifications) {
        for (FluentTQLSpecification fluentTQLSpecification : fluentTQLSpecifications) {
            if (fluentTQLSpecification instanceof TaintFlowQuery) {
                displayTaintFlowQuery((TaintFlowQuery) fluentTQLSpecification);
            } else if (fluentTQLSpecification instanceof QueriesSet) {
                for (TaintFlowQuery taintFlowQuery : ((QueriesSet) fluentTQLSpecification).getTaintFlowQueries()) {
                    displayTaintFlowQuery(taintFlowQuery);
                }
            }
        }
    }
}
