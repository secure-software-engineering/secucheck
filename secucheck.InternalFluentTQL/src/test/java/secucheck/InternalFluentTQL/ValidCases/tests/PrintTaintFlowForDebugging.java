package secucheck.InternalFluentTQL.ValidCases.tests;

import secucheck.InternalFluentTQL.dsl.MethodSet;
import secucheck.InternalFluentTQL.dsl.QueriesSet;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;

import java.util.List;

public class PrintTaintFlowForDebugging {
    private void displayMethod(Method method) {
        System.out.println("\t" + method.getSignature());

        if (method.getInputDeclaration() != null &&
                method.getInputDeclaration().getInputs() != null) {
            List<Input> inputs = method.getInputDeclaration().getInputs();
            System.out.println("\t\tInputDeclaration : " + inputs.size());
            for (Input input : inputs) {
                if (input instanceof Parameter) {
                    System.out.println("\t\t\tParameter " + ((Parameter) input).getParameterId());
                } else if (input instanceof ThisObject) {
                    System.out.println("\t\t\tThisObject");
                }
            }
        }

        if (method.getOutputDeclaration() != null &&
                method.getOutputDeclaration().getOutputs() != null) {
            List<Output> outputs = method.getOutputDeclaration().getOutputs();
            System.out.println("\t\tOutputDeclaration : " + outputs.size());
            for (Output output : outputs) {
                if (output instanceof Parameter) {
                    System.out.println("\t\t\tParameter " + ((Parameter) output).getParameterId());
                } else if (output instanceof ThisObject) {
                    System.out.println("\t\t\tThisObject");
                } else if (output instanceof Return) {
                    System.out.println("\t\t\tReturnValue");
                }
            }
        }
    }

    private void displayFlowParticipant(FlowParticipant flowParticipant) {
        if (flowParticipant instanceof Method) {
            displayMethod((Method) flowParticipant);
        } else if (flowParticipant instanceof MethodSet) {
            for (Method method : ((MethodSet) flowParticipant).getMethods()) {
                displayMethod(method);
            }
        }
    }

    private void displayTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        System.out.println("Report Message = " + taintFlowQuery.getReportMessage());
        System.out.println("Report Location = " + taintFlowQuery.getReportLocation());

        List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();

        for (TaintFlow taintFlow : taintFlows) {
            if (taintFlow.getFrom() != null) {
                System.out.println("Source : ");
                displayFlowParticipant(taintFlow.getFrom());
            }

            if (taintFlow.getNotThrough() != null) {
                System.out.println("Required Propagator : ");
                for (FlowParticipant flowParticipant : taintFlow.getThrough()) {
                    displayFlowParticipant(flowParticipant);
                }
            }

            if (taintFlow.getThrough() != null) {
                for (FlowParticipant flowParticipant : taintFlow.getNotThrough()) {
                    System.out.println("Sanitizer : ");
                    displayFlowParticipant(flowParticipant);
                }
            }

            if (taintFlow.getTo() != null) {
                System.out.println("Sink : ");
                displayFlowParticipant(taintFlow.getTo());
            }
        }
    }

    // Use this to print the FluentTQL Specifications for debugging
    public void printFluentTQLSpecifications(List<FluentTQLSpecification> fluentTQLSpecifications) {
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
