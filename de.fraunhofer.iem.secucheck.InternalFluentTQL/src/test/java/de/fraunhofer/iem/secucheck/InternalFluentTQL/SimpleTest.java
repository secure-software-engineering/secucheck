package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import org.junit.Test;

import java.util.List;

public class SimpleTest {
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

    @Test
    public void test1() throws FluentTQLException {
        FluentTQLSpecificationTestForNoSQLInjection simpleFluentTQLSpecification = new FluentTQLSpecificationTestForNoSQLInjection();

        List<FluentTQLSpecification> fluentTQLSpecifications = ProcessAnnotatedClass.processFluentTQLSpecificationClassAnnotation(
                simpleFluentTQLSpecification
        ).getFluentTQLSpecification();

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
