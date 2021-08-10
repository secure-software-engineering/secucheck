package secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.result.location.region;

/**
 * Region item in the SARIF format
 *
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

    /**
     * End line
     */
    private int endLine;

    /**
     * End column
     */
    private int endColumn;

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

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }
}
