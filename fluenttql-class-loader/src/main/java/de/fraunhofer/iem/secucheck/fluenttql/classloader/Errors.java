package de.fraunhofer.iem.secucheck.fluenttql.classloader;

import java.util.ArrayList;
import java.util.List;

/**
 * This class combines the multiple ErrorModel
 *
 * @author Ranjith Krishnamurthy
 */
public class Errors {
    List<ErrorModel> errors = new ArrayList<>();

    /**
     * Returns the List of ErrorModel
     *
     * @return List of ErrorModel
     */
    public List<ErrorModel> getErrors() {
        return errors;
    }

    /**
     * This method adds the single ErrorModel to the List of ErrorModels
     *
     * @param error Single ErrorModel
     */
    public void addError(ErrorModel error) {
        this.errors.add(error);
    }
}