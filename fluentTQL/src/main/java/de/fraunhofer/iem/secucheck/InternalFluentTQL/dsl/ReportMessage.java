package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

public class ReportMessage {
    private final String message;
    private final CWE cwe;

    public ReportMessage(String message, CWE cwe) {
        this.cwe = cwe;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public CWE getCwe() {
        return cwe;
    }
}
