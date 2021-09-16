package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

/**
 * Represents the Qualified This
 * <p>
 * For now, it supports Extension receiver this object
 * and Dispatch receiver this object
 *
 * @author Ranjith Krishnamurthy
 */
public enum QualifiedThis {
    EXTENSION_RECEIVER("EXTENSION_RECEIVER"),
    DISPATCH_RECEIVER("DISPATCH_RECEIVER");

    private final String thisObjectType;

    QualifiedThis(String thisObjectType) {
        this.thisObjectType = thisObjectType;
    }

    public String getThisObjectType() {
        return this.thisObjectType;
    }
}
