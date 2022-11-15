package de.fraunhofer.iem.secucheck.magpiebridge.internal;

import org.eclipse.lsp4j.DiagnosticSeverity;

import com.ibm.wala.cast.tree.CAstSourcePositionMap.Position;
import com.ibm.wala.util.collections.Pair;

import magpiebridge.core.AnalysisResult;
import magpiebridge.core.Kind;

public class SecuCheckMapieBridgeResult implements AnalysisResult {

    private Kind kind;
    private Position position;
    private String message;
    private Iterable<Pair<Position, String>> related;
    private DiagnosticSeverity severity;
    private Pair<Position, String> repair;
    private String code;

    public SecuCheckMapieBridgeResult() {
    }

    public SecuCheckMapieBridgeResult(Kind kind, Position pos, String msg,
                                      Iterable<Pair<Position, String>> relatedInfo, DiagnosticSeverity severity,
                                      Pair<Position, String> repair, String code) {
        this.kind = kind;
        this.position = pos;
        this.message = msg;
        this.related = relatedInfo;
        this.severity = severity;
        this.repair = repair;
        this.code = code;
    }

    @Override
    public Kind kind() {
        return this.kind;
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public Iterable<Pair<Position, String>> related() {
        return related;
    }

    @Override
    public DiagnosticSeverity severity() {
        return severity;
    }

    @Override
    public Pair<Position, String> repair() {
        return repair;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String toString(boolean useMarkdown) {
        return toString();
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRelated(Iterable<Pair<Position, String>> related) {
        this.related = related;
    }

    public void setSeverity(DiagnosticSeverity severity) {
        this.severity = severity;
    }

    public void setRepair(Pair<Position, String> repair) {
        this.repair = repair;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return message;
        //	return "SecuCheckResult [kind=" + kind + ", position=" + position + ", code=" + code + ", message=" + message
        //			+ ", related=" + related + ", severity=" + severity + ", repair=" + repair + "]";
    }


}
