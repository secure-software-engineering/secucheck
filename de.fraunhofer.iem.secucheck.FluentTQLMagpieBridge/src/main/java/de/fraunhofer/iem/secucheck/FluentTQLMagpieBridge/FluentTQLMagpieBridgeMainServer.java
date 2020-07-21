package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import magpiebridge.core.*;
import magpiebridge.projectservice.java.JavaProjectService;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

/**
 * This is the main class of the Magpie bridge server for FluentTQL
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLMagpieBridgeMainServer {

    /**
     * Main method of the FluentTQL Magpie bridge server.
     *
     * @param args Arguments to the server. Generally not used.
     */
    public static void main(String[] args) {
        createServer().launchOnStdio();
    }

    /**
     * This method creates the Magpiebridge server.
     *
     * @return MagpieServer
     */
    private static MagpieServer createServer() {
        ServerConfiguration defaultConfig = new ServerConfiguration();
        defaultConfig.setDoAnalysisByOpen(false);
        defaultConfig.setDoAnalysisBySave(false);

        defaultConfig.setShowConfigurationPage(true, true);

        MagpieServer server = new MagpieServer(defaultConfig);

        String language = "java";
        IProjectService javaProjectService = new JavaProjectService();

        ServerAnalysis myAnalysis = new FluentTQLAnalysis();
        server.addProjectService(language, javaProjectService);

        Either<ServerAnalysis, ToolAnalysis> analysis = Either.forLeft(myAnalysis);
        server.addAnalysis(analysis, language);
        return server;
    }
}
