package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.SecuHttpServer;
import magpiebridge.core.IProjectService;
import magpiebridge.core.MagpieServer;
import magpiebridge.core.ServerConfiguration;
import magpiebridge.projectservice.java.JavaProjectService;

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

        defaultConfig.setShowConfigurationPage(true, false);
        defaultConfig.setUseMagpieHTTPServer(false);
        fluentTQLMagpieServer = new MagpieServer(defaultConfig);

        String language = "java";
        IProjectService javaProjectService = new JavaProjectService();

        //   ServerAnalysis myAnalysis = new FluentTQLAnalysis();
        fluentTQLMagpieServer.addProjectService(language, javaProjectService);

        //Todo: If necessary (Very Important)
        // Currently, Analysis settings and calling analysis is done through the HttpHandlers in the package SecucheckHttpServer/handler
        // This reduces some of the extra computation compare to using the below commented code.
        // This avoids creating MagpieBridge Configuration options objects, calling MagpieBridge APIs to call and set Analysis.
        // If in case in future need the below feature and creation of MagpieBridge configuration options objects, Please uncomment the below
        // and set the FluentTQLAnalysis accordingly comparing the FluentTQLAnalysisConfigurator
        // Either<ServerAnalysis, ToolAnalysis> analysis = Either.forLeft(myAnalysis);
        //fluentTQLMagpieServer.addAnalysis(analysis, language);

        fluentTQLMagpieServer.addHttpServer(new SecuHttpServer().start());

        return fluentTQLMagpieServer;
    }
}
