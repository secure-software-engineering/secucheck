package de.fraunhofer.iem.secucheck.magpiebridge.http.handlers;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fraunhofer.iem.secucheck.magpiebridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.magpiebridge.FluentTQLMagpieBridgeMainServer;
import de.fraunhofer.iem.secucheck.magpiebridge.http.utility.FreeMarkerUtility;
import de.fraunhofer.iem.secucheck.magpiebridge.http.utility.PrintUtility;
import de.fraunhofer.iem.secucheck.commons.ResourceUtility;
import de.fraunhofer.iem.secucheck.magpiebridge.internal.SecuCheckAnalysisConfigurator;
import org.eclipse.lsp4j.MessageType;

@SuppressWarnings("restriction")
public class SecuMainHandler implements HttpHandler {

    private static final int HTTP_OK_STATUS = 200;

    public void handle(HttpExchange t) throws IOException {
        if (FluentTQLAnalysisConfigurator.isNullJavaProjectService()) {
            FluentTQLAnalysisConfigurator.setProjectInformation();
        }

        String response = "";

        URI uri = t.getRequestURI();
        String path = uri.getPath();

        OutputStream os = t.getResponseBody();
        t.getResponseHeaders().add("Cache-Control", "no-cache, no-store, must-revalidate");
        t.getResponseHeaders().add("Pragma", "no-cache");
        t.getResponseHeaders().add("Expires", "0");

        switch (path) {
            case "/specPathRequest":
                //Todo: verify do we need to cancel the previous analyis run
                SecuCheckAnalysisConfigurator.cancel();

                response = FreeMarkerUtility.setFirstPageFile(
                        FluentTQLAnalysisConfigurator.getSource());
                t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
                os.write(response.getBytes());
                os.close();
                FluentTQLAnalysisConfigurator.setCurrentConfigHtmlPage(response);
                break;

            case "/static/app.css":
                response = ResourceUtility.getResourceAsString("static/app.css");
                t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
                os.write(response.getBytes());
                os.close();
                break;

            case "/img/secu.png":
                File imageFile = ResourceUtility.getResourceAsFile("img/secu.png");
                t.sendResponseHeaders(HTTP_OK_STATUS, imageFile.length());
                Files.copy(imageFile.toPath(), os);
                os.close();
                break;

            case "/runAnalysis":
                FluentTQLAnalysisConfigurator.runAnalysis();
                t.sendResponseHeaders(HTTP_OK_STATUS, 0);
                os.close();
                PrintUtility.printMessageInIDE(MessageType.Info,
                        "SecuCheck’s Analysis has started.");
                break;

            case "/cancelAnalysis":
                SecuCheckAnalysisConfigurator.cancel();
                t.sendResponseHeaders(HTTP_OK_STATUS, 0);
                os.close();
                PrintUtility.printMessageInIDE(MessageType.Info,
                        "SecuCheck’s Analysis has cancelled");
                break;

            case "/clearResultMarkers":
                FluentTQLMagpieBridgeMainServer.fluentTQLMagpieServer.cleanUp();
                t.sendResponseHeaders(HTTP_OK_STATUS, 0);
                os.close();
                PrintUtility.printMessageInIDE(MessageType.Info,
                        "All the result markers are cleared from the IDE.");
                break;
        }
    }

/*
    private String checkSpecPage(URI uri) {
        System.out.println(getResource("firstpage.ftl"));
        return getResource("firstpage.ftl");

        String fName = "";
        String lName = "";
        //Get the request query
        System.out.println();
        String query = uri.getQuery();
        System.out.println("Query = " + query);
        if (query != null) {
            System.out.println("Query: " + query);
            String[] queryParams = query.split(AND_DELIMITER);
            if (queryParams.length > 0) {
                for (String qParam : queryParams) {
                    String[] param = qParam.split(EQUAL_DELIMITER);
                    if (param.length > 0) {
                        for (int i = 0; i < param.length; i++) {
                            if (F_NAME.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
                                fName = param[PARAM_VALUE_IDX];
                            }
                            if (L_NAME.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
                                lName = param[PARAM_VALUE_IDX];
                            }
                        }
                    }
                }
            }
        }
    }
*/
}