package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLCompiler;

import java.util.HashMap;

public class ErrorModel {
    private final HashMap<String, String> error = new HashMap<>();

    public void addNewError(String className, String errorMessage) {
        error.put(className, errorMessage);
    }

    public HashMap<String, String> getError() {
        return error;
    }
}
