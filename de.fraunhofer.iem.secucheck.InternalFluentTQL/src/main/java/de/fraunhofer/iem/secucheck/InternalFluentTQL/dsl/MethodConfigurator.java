package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

public class MethodConfigurator {
    private final MethodImpl method;

    public MethodConfigurator(String methodSignature) {
        method = new MethodImpl(methodSignature);
    }

    public MethodWithIn in() {
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }

    public MethodWithOut out() {
        return new MethodWithOut(new OutputDeclarationImpl(), method);
    }
}
