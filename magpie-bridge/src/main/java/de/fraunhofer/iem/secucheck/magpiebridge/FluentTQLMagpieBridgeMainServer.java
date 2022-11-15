package de.fraunhofer.iem.secucheck.magpiebridge;

import de.fraunhofer.iem.secucheck.magpiebridge.http.SecuHttpServer;
import magpiebridge.core.*;
import magpiebridge.projectservice.java.JavaProjectService;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

/**
 * This is the main class of the Magpie bridge server for FluentTQL
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLMagpieBridgeMainServer {

    public static MagpieServer fluentTQLMagpieServer;

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
        defaultConfig.setDoAnalysisByFirstOpen(false);
        defaultConfig.setDoAnalysisByIdle(false, 0);

        defaultConfig.setShowConfigurationPage(true, false);
        defaultConfig.setUseMagpieHTTPServer(false);
        fluentTQLMagpieServer = new MagpieServer(defaultConfig);

        String language = "java";
        IProjectService javaProjectService = new JavaProjectService();

        fluentTQLMagpieServer.addProjectService(language, javaProjectService);

        //Todo: If necessary (Very Important)
        // Currently, Analysis settings and calling analysis is done through the HttpHandlers in the package SecucheckHttpServer/handler
        // This reduces some of the extra computation compare to using the below commented code.
        // This avoids creating MagpieBridge Configuration options objects, calling MagpieBridge APIs to call and set Analysis.
        // If in case in future need the below feature and creation of MagpieBridge configuration options objects, Please uncomment the below
        // and set the FluentTQLAnalysis accordingly comparing the FluentTQLAnalysisConfigurator
        ServerAnalysis myAnalysis = new FluentTQLAnalysis();
        Either<ServerAnalysis, ToolAnalysis> analysis = Either.forLeft(myAnalysis);
        fluentTQLMagpieServer.addAnalysis(analysis, language);

        fluentTQLMagpieServer.addHttpServer(new SecuHttpServer().start());

        return fluentTQLMagpieServer;
    }
}
