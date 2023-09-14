package de.fraunhofer.iem.secucheck.sarif.runs.result.location.region;

/**
 * Region item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Region {
    /**
     * Start line
     */
    private int startLine;

    /**
     * Start column
     */
    private int startColumn;

    private int charLength;

    private String sourceLanguage;

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getCharLength() {
        return charLength;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }


    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }
}
