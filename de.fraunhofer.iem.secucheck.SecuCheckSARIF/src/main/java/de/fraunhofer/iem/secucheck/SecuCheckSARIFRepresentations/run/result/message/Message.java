package de.fraunhofer.iem.secucheck.SecuCheckSARIFRepresentations.run.result.message;

/**
 * Message item in the SARIF format
 */
public class Message {
    /**
     * Report message text
     */
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
