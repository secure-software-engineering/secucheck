package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLCompiler;

import java.util.ArrayList;
import java.util.List;

public class Errors {
    List<ErrorModel> errors = new ArrayList<>();

    public List<ErrorModel> getErrors() {
        return errors;
    }

    public void addError(ErrorModel error) {
        this.errors.add(error);
    }
}
