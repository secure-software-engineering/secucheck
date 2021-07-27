package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * This class processes the FluentTQL related annotations and configure the Specification.
 * Use this class to get the processed and complete FluentTQL TaintFLow specifications.
 *
 * @author Ranjith Krishnamurthy
 */
public class ProcessAnnotatedClass {
    private final List<String> listOfImportClassChain = new ArrayList<>();
    private final HashSet<Method> generalPropagators = new HashSet<>();

    /**
     * This checks whether the given Object is a valid FluentTQL related class.
     *
     * @param obj Object
     * @return Boolean: True if it is valid FluentTQL related class.
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     */
    protected boolean isValidFluentTQLRelatedClass(Object obj) throws DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, MissingFluentTQLSpecificationClassAnnotationException {
        if (!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class) &&
                !obj.getClass().isAnnotationPresent(FluentTQLRepositoryClass.class)) {
            throw new NotAFluentTQLRelatedClassException(obj.getClass().getName());
        } else {
            if (!(obj instanceof FluentTQLUserInterface)) {
                if (obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class))
                    throw new DoesNotImplementFluentTQLUserInterfaceException(obj.getClass().getName());
            } else {
                if (!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class))
                    throw new MissingFluentTQLSpecificationClassAnnotationException(obj.getClass().getName());
            }
        }
        return true;
    }

    /**
     * This method tries to process the given object and get the FluentTQL Specification class i.e. FluentTQLUserInterface. Object has to be annotated with FluentTQLSpecificationClass and
     * must implement FluentTQLUserInterface.
     *
     * @param fluentTQLSpec Object
     * @return FluentTQLUserInterface
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     * @throws InvalidFluentTQLSpecificationException                If the empty specifications list is given or methods are not configured completely.
     * @throws NotAFluentTQLSpecificationClassException              If the class is not annotated with FluentTQLSpecificationClass.
     * @throws CyclicImportException                                 If there is a cyclic import annotations
     */
    public FluentTQLUserInterface processFluentTQLSpecificationClassAnnotation(Object fluentTQLSpec) throws DoesNotImplementFluentTQLUserInterfaceException, ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, NotAFluentTQLRelatedClassException, MissingFluentTQLSpecificationClassAnnotationException, NotFoundZeroArgumentConstructorException, InvalidFluentTQLSpecificationException, NotAFluentTQLSpecificationClassException, CyclicImportException {
        isValidFluentTQLRelatedClass(fluentTQLSpec);

        if (!fluentTQLSpec.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class))
            throw new NotAFluentTQLSpecificationClassException(fluentTQLSpec.getClass().getName());

        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processFluentTQLAnnotation(fluentTQLSpec);
        isValidTaintFlowSpecification(fluentTQLUserInterface);
        return fluentTQLUserInterface;
    }

    /**
     * This class processes the given object for the FluentTQL related annotation and configures the methods and returns the Object. This can also be a FluentTQLRepositoryClass.
     *
     * @param fluentTQLRelatedClass Object of a class that has to be processed
     * @return Processed Object
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     * @throws InvalidFluentTQLSpecificationException                If the empty specifications list is given or methods are not configured completely.
     * @throws CyclicImportException                                 If there is a cyclic import annotations
     */
    public Object processFluentTQLAnnotation(Object fluentTQLRelatedClass) throws NotFoundZeroArgumentConstructorException, DoesNotImplementFluentTQLUserInterfaceException, FieldNullPointerException, MissingFluentTQLSpecificationClassAnnotationException, CyclicImportException, NotAFluentTQLRelatedClassException, InvalidFluentTQLSpecificationException, FieldNotPublicException, ImportAndProcessAnnotationException, IncompleteMethodDeclarationException {
        listOfImportClassChain.clear();

        return processFluentTQLAnnotation(fluentTQLRelatedClass, false);
    }

    /**
     * This class processes the given object for the FluentTQL related annotation and configures the methods and returns the Object.
     * This can also be a FluentTQLRepositoryClass.
     *
     * @param fluentTQLRelatedClass Object
     * @param isProcessOnlyStatic   whether process only static field or all field
     * @return Processed Object
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     * @throws InvalidFluentTQLSpecificationException                If the empty specifications list is given or methods are not configured completely.
     * @throws CyclicImportException                                 If there is a cyclic import annotations
     */
    private Object processFluentTQLAnnotation(Object fluentTQLRelatedClass, boolean isProcessOnlyStatic) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, NotFoundZeroArgumentConstructorException, InvalidFluentTQLSpecificationException, CyclicImportException {
        if (listOfImportClassChain.contains(fluentTQLRelatedClass.getClass().getSimpleName())) {
            listOfImportClassChain.add(fluentTQLRelatedClass.getClass().getSimpleName());
            throw new CyclicImportException(listOfImportClassChain.toString()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .replaceAll(",", " -->"));
        }

        listOfImportClassChain.add(fluentTQLRelatedClass.getClass().getSimpleName());

        isValidFluentTQLRelatedClass(fluentTQLRelatedClass);

        if (fluentTQLRelatedClass.getClass().isAnnotationPresent(ImportAndProcessOnlyStaticFields.class)) {
            importAndProcessStaticOnly(fluentTQLRelatedClass);
        }

        processEachField(fluentTQLRelatedClass, isProcessOnlyStatic);

        if (fluentTQLRelatedClass.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) {
            FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) fluentTQLRelatedClass;

            isValidTaintFlowSpecification(fluentTQLUserInterface);
        }
        return fluentTQLRelatedClass;
    }

    /**
     * This method processes field's annotation of a single object recursively and configures it accordingly.
     *
     * @param fluentTQLSpec       Object
     * @param isProcessOnlyStatic whether process only static field or all field
     * @throws ImportAndProcessAnnotationException  If fails to import and process the field's annotation.
     * @throws FieldNullPointerException            If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException              If the annotated field is not in public modifier.
     * @throws CyclicImportException                If there is a cyclic import annotations
     */
    private void processEachField(Object fluentTQLSpec, boolean isProcessOnlyStatic) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, CyclicImportException {
        //First process all the ImportAndProcess Annotation annotated fields
        for (Field field : fluentTQLSpec.getClass().getDeclaredFields()) {
            if (!field.getType().equals(Method.class) && !field.getType().equals(MethodSet.class)) {
                if (field.isAnnotationPresent(ImportAndProcessAnnotation.class)) {
                    Object obj;

                    try {
                        if (Modifier.isStatic(field.getModifiers()))
                            obj = field.get(fluentTQLSpec.getClass());
                        else
                            obj = field.get(fluentTQLSpec);
                    } catch (IllegalAccessException e) {
                        throw new FieldNotPublicException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
                    }


                    if (obj == null) {
                        throw new FieldNullPointerException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
                    }

                    importAndProcess(obj, fluentTQLSpec);
                }
            }
        }

        for (Field field : fluentTQLSpec.getClass().getDeclaredFields()) {
            if (isProcessOnlyStatic) {
                if (Modifier.isStatic(field.getModifiers())) {
                    processSingleField(field, fluentTQLSpec, Modifier.isStatic(field.getModifiers()));
                }
            } else {
                processSingleField(field, fluentTQLSpec, Modifier.isStatic(field.getModifiers()));
            }
        }
    }

    /**
     * This method processes a single field' annotations.
     *
     * @param field         Field
     * @param fluentTQLSpec Object that the field belongs to
     * @param isStaticField whether process only static field or all field
     * @throws ImportAndProcessAnnotationException  If fails to import and process the field's annotation.
     * @throws FieldNullPointerException            If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException              If the annotated field is not in public modifier.
     * @throws CyclicImportException                If there is a cyclic import annotations
     */
    private void processSingleField(Field field, Object fluentTQLSpec, boolean isStaticField) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, CyclicImportException {
        try {
            if (!field.getType().equals(Method.class) && !field.getType().equals(MethodSet.class))
                return;

            Object obj;

            if (isStaticField)
                obj = field.get(fluentTQLSpec.getClass());
            else
                obj = field.get(fluentTQLSpec);

            if (obj == null) {
                throw new FieldNullPointerException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
            }

            if (obj instanceof MethodImpl) {
                processForGeneralPropagatorAnnotation(field, obj);
                return;
            }

            if (field.getType().equals(Method.class) &&
                    !field.isAnnotationPresent(InFlowParam.class) &&
                    !field.isAnnotationPresent(InFlowThisObject.class) &&
                    !field.isAnnotationPresent(OutFlowParam.class) &&
                    !field.isAnnotationPresent(OutFlowReturnValue.class) &&
                    !field.isAnnotationPresent(OutFlowThisObject.class) &&
                    !field.isAnnotationPresent(AnalysisEntryPoint.class))
                throw new IncompleteMethodDeclarationException(field.getName(), fluentTQLSpec.getClass().getSimpleName());

            InputDeclarationImpl inputDeclaration = new InputDeclarationImpl();
            OutputDeclarationImpl outputDeclaration = new OutputDeclarationImpl();

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().equals(InFlowParam.class)) {
                    int[] paramIDs = ((InFlowParam) annotation).parameterID();

                    for (int paramID : paramIDs) {
                        inputDeclaration.addInput(
                                new ParameterImpl(paramID)
                        );
                    }
                } else if (annotation.annotationType().equals(OutFlowParam.class)) {
                    int[] paramIDs = ((OutFlowParam) annotation).parameterID();

                    for (int paramID : paramIDs) {
                        outputDeclaration.addOutput(
                                new ParameterImpl(paramID)
                        );
                    }
                } else if (annotation.annotationType().equals(InFlowThisObject.class)) {
                    inputDeclaration.addInput(
                            new ThisObjectImpl()
                    );
                } else if (annotation.annotationType().equals(OutFlowReturnValue.class)) {
                    outputDeclaration.addOutput(
                            new ReturnImpl()
                    );
                } else if (annotation.annotationType().equals(OutFlowThisObject.class)) {
                    outputDeclaration.addOutput(
                            new ThisObjectImpl()
                    );
                }
            }

            if (obj instanceof MethodSelector) {
                ((MethodSelector) obj).getOutputDeclaration().getOutputsAsSet().clear();
                ((MethodSelector) obj).getInputDeclaration().getInputsAsSet().clear();

                ((MethodSelector) obj).getOutputDeclaration().getOutputsAsSet().addAll(outputDeclaration.getOutputs());
                ((MethodSelector) obj).getInputDeclaration().getInputsAsSet().addAll(inputDeclaration.getInputs());
            }

            processForGeneralPropagatorAnnotation(field, obj);
        } catch (IllegalAccessException e) {
            throw new FieldNotPublicException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
        }
    }

    /**
     * This method checks if a field has a GeneralPropagator annotation, if yes, then it adds that method to the generalPropagators list
     *
     * @param field Field
     * @param obj   Field's object
     */
    private void processForGeneralPropagatorAnnotation(Field field, Object obj) {
        if (!field.isAnnotationPresent(GeneralPropagator.class))
            return;

        if (field.getType().equals(Method.class)) {
            Method method = (Method) obj;
            if (isValidMethod(method)) {
                generalPropagators.add(method);
            }
        } else if (field.getType().equals(MethodSet.class)) {
            MethodSet methodSet = (MethodSet) obj;
            generalPropagators.addAll(isValidMethodSet(methodSet).getMethods());
        }
    }

    /**
     * This method returns the list of general propagators, that is stored in the last call of processFluentTQLAnnotation or
     * processFluentTQLSpecificationClassAnnotation method call.
     *
     * @return Set of General propagators
     */
    public HashSet<Method> getGeneralPropagators() {
        erroneousMethods.clear();
        HashSet<Method> validGeneralPropagators = new HashSet<>();

        for (Method method : generalPropagators) {
            if (isValidMethod(method)) {
                validGeneralPropagators.add(method);
            }
        }

        return validGeneralPropagators;
    }

    /**
     * This method tries to import and process the annotation of a field annotated with ImportAndProcessAnnotation
     *
     * @param obj           Object
     * @param fluentTQLSpec Object that tries to import and process the above Object
     * @throws ImportAndProcessAnnotationException If fails to import and process the field's annotation. It wraps all the exception in this method and adds the reason in this exception
     * @throws CyclicImportException               If there is a cyclic import annotations
     */
    private void importAndProcess(Object obj, Object fluentTQLSpec) throws ImportAndProcessAnnotationException, CyclicImportException {
        if ((!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) &&
                (!obj.getClass().isAnnotationPresent(FluentTQLRepositoryClass.class))) {
            throw new ImportAndProcessAnnotationException(obj.getClass().getSimpleName(),
                    fluentTQLSpec.getClass().getSimpleName(), obj.getClass() + " is not a FluentTQL related class. Use one of the " +
                    "[FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation to make it FluentTQL related class.");
        }

        try {
            processFluentTQLAnnotation(obj, false);
        } catch (ImportAndProcessAnnotationException | FieldNullPointerException | IncompleteMethodDeclarationException | FieldNotPublicException | MissingFluentTQLSpecificationClassAnnotationException | NotAFluentTQLRelatedClassException | DoesNotImplementFluentTQLUserInterfaceException | NotFoundZeroArgumentConstructorException | InvalidFluentTQLSpecificationException e) {
            throw new ImportAndProcessAnnotationException(
                    obj.getClass().getSimpleName(),
                    fluentTQLSpec.getClass().getSimpleName(),
                    "(" + e.getClass().getSimpleName() + ") " + e.getMessage()
            );
        }
    }

    /**
     * This method tries to process the ImportAndProcessOnlyStaticFields annotation of a class.
     *
     * @param fluentTQLRelatedClass Object with ImportAndProcessOnlyStaticFields annotation
     * @throws NotFoundZeroArgumentConstructorException Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     * @throws ImportAndProcessAnnotationException      If fails to import and process the field's annotation. It wraps all the exception in this method and adds the reason in this exception
     * @throws CyclicImportException                    If there is a cyclic import annotations
     */
    private void importAndProcessStaticOnly(Object fluentTQLRelatedClass) throws NotFoundZeroArgumentConstructorException, ImportAndProcessAnnotationException, CyclicImportException {
        ImportAndProcessOnlyStaticFields importAndProcessOnlyStaticFields = fluentTQLRelatedClass.getClass().getAnnotation(ImportAndProcessOnlyStaticFields.class);
        for (Class<?> className : importAndProcessOnlyStaticFields.classList()) {
            Constructor<?> constructor = null;

            boolean hasValidConstructor = false;
            for (Constructor<?> cons : className.getConstructors()) {
                if (cons.getParameterCount() == 0) {
                    hasValidConstructor = true;
                    constructor = cons;
                    break;
                }
            }

            if (!hasValidConstructor)
                throw new NotFoundZeroArgumentConstructorException(className.getSimpleName());

            Object ob = null;
            try {
                ob = constructor.newInstance((Object[]) null);
                //Todo: decide what to do with these exceptions
            } catch (Exception | Error ex) {
                ex.printStackTrace();
                throw new ImportAndProcessAnnotationException(
                        className.getSimpleName(),
                        fluentTQLRelatedClass.getClass().getSimpleName(),
                        "(" + ex.getClass().getSimpleName() + ") " + ex.getMessage()
                );
            }

            try {
                isValidFluentTQLRelatedClass(ob);

                //processEachField(ob, true);

                processFluentTQLAnnotation(ob, true);
            } catch (ImportAndProcessAnnotationException | NotAFluentTQLRelatedClassException | FieldNullPointerException | MissingFluentTQLSpecificationClassAnnotationException | DoesNotImplementFluentTQLUserInterfaceException | FieldNotPublicException | IncompleteMethodDeclarationException | InvalidFluentTQLSpecificationException e) {
                throw new ImportAndProcessAnnotationException(
                        className.getSimpleName(),
                        fluentTQLRelatedClass.getClass().getSimpleName(),
                        "(" + e.getClass().getSimpleName() + ") " + e.getMessage()
                );
            }
        }
    }

    private final List<String> erroneousMethods = new ArrayList<>();

    /**
     * This method checks whether the given FluentTQLSpecification is valid or not.
     *
     * @param fluentTQLUserInterface FluentTQLSpecifcation
     * @throws InvalidFluentTQLSpecificationException If the empty specifications list is given or methods are not configured completely.
     */
    private void isValidTaintFlowSpecification(FluentTQLUserInterface fluentTQLUserInterface) throws InvalidFluentTQLSpecificationException {
        erroneousMethods.clear();

        List<FluentTQLSpecification> fluentTQLSpecifications = fluentTQLUserInterface.getFluentTQLSpecification();
        Objects.requireNonNull(fluentTQLSpecifications, "FluentTQLSpecifications returned by the getFluentTQLSpecification()" +
                " method in " + fluentTQLUserInterface.getClass().getSimpleName() + " is null.");

        int specificationSize = fluentTQLSpecifications.size();

        for (FluentTQLSpecification fluentTQLSpecification : fluentTQLSpecifications) {
            if (fluentTQLSpecification == null) {
                --specificationSize;
                continue;
            }

            if (fluentTQLSpecification instanceof TaintFlowQuery)
                isValidTaintFlowQuery((TaintFlowQuery) fluentTQLSpecification);
            else if (fluentTQLSpecification instanceof QueriesSet)
                isValidTaintQueriesSet((QueriesSet) fluentTQLSpecification);
        }

        if (specificationSize == 0)
            throw new InvalidFluentTQLSpecificationException("FluentTQLSPecificaions list returned by the getFluentTQLSpecification()" +
                    " method in " + fluentTQLUserInterface.getClass().getSimpleName() + " is empty.");

        if (erroneousMethods.size() > 0) {
            throw new InvalidFluentTQLSpecificationException("Below method(s) are not configured and has invalid taint flow. Please " +
                    "use FluentTQL annotation to configure the methods. \n" +
                    erroneousMethods.toString()
                            .replaceAll("\\[", "* ")
                            .replaceAll("]", "")
                            .replaceAll(", ", "\n* "));
        }
    }

    /**
     * This method checks whether the given TaintFlowQuery is valid or not.
     *
     * @param taintFlowQuery TaintFlowQuery
     */
    private void isValidTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();

        for (TaintFlow taintFlow : taintFlows) {
            FlowParticipant from = taintFlow.getFrom();
            List<FlowParticipant> through = taintFlow.getThrough();
            List<FlowParticipant> notThrough = taintFlow.getNotThrough();
            FlowParticipant to = taintFlow.getTo();

            //From
            if (from instanceof Method) {
                isValidMethod((Method) from);
            } else if (from instanceof MethodSet) {
                isValidMethodSet((MethodSet) from);
            }

            //through
            for (FlowParticipant flowParticipant : through) {
                if (flowParticipant instanceof Method) {
                    isValidMethod((Method) flowParticipant);
                } else if (flowParticipant instanceof MethodSet) {
                    isValidMethodSet((MethodSet) flowParticipant);
                }
            }

            //notThrough
            for (FlowParticipant flowParticipant : notThrough) {
                if (flowParticipant instanceof Method) {
                    isValidMethod((Method) flowParticipant);
                } else if (flowParticipant instanceof MethodSet) {
                    isValidMethodSet((MethodSet) flowParticipant);
                }
            }

            //to
            if (to instanceof Method) {
                isValidMethod((Method) to);
            } else if (from instanceof MethodSet) {
                isValidMethodSet((MethodSet) to);
            }
        }
    }

    /**
     * This method checks whether the given Method is valid, If it is valid then it logs the method signature.
     *
     * @param method Method
     */
    private boolean isValidMethod(Method method) {
        //Todo: Also add to check for valid Signature.

        boolean emptyInputs = isEmptyInputs(method.getInputDeclaration().getInputs());
        boolean emptyOutputs = isEmptyOutputs(method.getOutputDeclaration().getOutputs());

        if (emptyInputs && emptyOutputs) {
            erroneousMethods.add(method.getSignature());
            return false;
        }

        return true;
    }

    /**
     * This method checks whether the given Inputs contains at least one Inputs.
     *
     * @param inputs List of Inputs
     * @return true if it is empty
     */
    private boolean isEmptyInputs(List<Input> inputs) {
        return inputs.size() == 0;
    }

    /**
     * This method checks whether the given Outputs contains at least one Output.
     *
     * @param outputs List of Outputs
     * @return true if it is empty
     */
    private boolean isEmptyOutputs(List<Output> outputs) {
        return outputs.size() == 0;
    }

    /**
     * This method recursively checks all the Method in MethodSet are valid.
     *
     * @param methodSet MethodSet
     * @return Returns MethodSet with only the valid Methods in it.
     */
    private MethodSet isValidMethodSet(MethodSet methodSet) {
        MethodSet validMethodSet = new MethodSet(methodSet.getName());

        for (Method method : methodSet.getMethods()) {
            if (isValidMethod(method)) {
                validMethodSet.addMethod(method);
            }
        }

        return validMethodSet;
    }

    /**
     * This method recursively checks all the TaintFLowQuery in QueriesSet are valid.
     *
     * @param queriesSet QueriesSet
     */
    private void isValidTaintQueriesSet(QueriesSet queriesSet) {
        for (TaintFlowQuery taintFlowQuery : queriesSet.getTaintFlowQueries()) {
            isValidTaintFlowQuery(taintFlowQuery);
        }
    }
}
